package org.gob.loja.gim.ws.service.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gob.loja.gim.ws.dto.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.gob.gim.common.DateUtils;
import org.gob.gim.common.GimUtils;
import org.gob.gim.common.service.SystemParameterService;
import org.gob.gim.exception.NotActiveWorkdayException;
import org.gob.gim.exception.ReverseAmongPaymentsIsNotAllowedException;
import org.gob.gim.exception.ReverseNotAllowedException;
import org.gob.gim.income.facade.IncomeService;
import org.gob.gim.income.facade.IncomeServiceBean;
import org.gob.gim.revenue.exception.EntryDefinitionNotFoundException;
import org.gob.loja.gim.ws.dto.Bond;
import org.gob.loja.gim.ws.dto.BondDetail;
import org.gob.loja.gim.ws.dto.Payout;
import org.gob.loja.gim.ws.dto.ServiceRequest;
import org.gob.loja.gim.ws.dto.Taxpayer;
import org.gob.loja.gim.ws.dto.Transfer;
import org.gob.loja.gim.ws.dto.v2.ClosingStatementV2;
import org.gob.loja.gim.ws.dto.v2.DepositStatementV2;
import org.gob.loja.gim.ws.dto.v2.ReverseStatementV2;
import org.gob.loja.gim.ws.dto.v2.StatementV2;
import org.gob.loja.gim.ws.exception.NotOpenTill;
import org.gob.loja.gim.ws.service.Messages;
import org.gob.loja.gim.ws.service.SecurityInterceptor;

import ec.gob.gim.bank.model.BankingEntityLog;
import ec.gob.gim.common.model.Alert;
import ec.gob.gim.common.model.Person;
import ec.gob.gim.income.model.PaymentMethod;
import ec.gob.gim.income.model.Till;
import ec.gob.gim.income.model.TillPermission;
import ec.gob.gim.income.model.Workday;
import ec.gob.gim.revenue.model.MunicipalBondType;
import ec.gob.gim.security.model.User;

import com.google.common.base.Joiner;

@Stateless(name = "PaymentServiceV2")
@Interceptors(SecurityInterceptor.class)
public class PaymentServiceBeanV2 implements PaymentServiceV2 {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private SystemParameterService systemParameterService;

	@EJB
	private IncomeService incomeService;

	public final String ACCOUNT_CODE_FOR_INTEREST = "ACCOUNT_CODE_FOR_INTEREST";

	public final String ACCOUNT_CODE_FOR_SURCHARGE = "ACCOUNT_CODE_FOR_SURCHARGE";

	public final String ACCOUNT_CODE_FOR_TAX = "ACCOUNT_CODE_FOR_TAX";

	public final String ACCOUNT_CODE_FOR_DISCOUNT = "ACCOUNT_CODE_FOR_DISCOUNT";

	private BankingEntityLog serverLog;

	@Override
	public StatementV2 findStatement(ServiceRequest request) {

		StatementV2 statement = new StatementV2();

		serverLog = new BankingEntityLog();
		serverLog.setDateTransaction(new Date());
		serverLog.setTransactionId(null);
		serverLog.setMethodUsed("findStatement_V2");
		serverLog.setBankUsername(request.getUsername());

		String identificationNumber = request.getIdentificationNumber();
		
		// rfam 2021-05-03 ML-JC-2021-009-DI
//		if(avoidPaymentEntry(identificationNumber)) {
//			statement.setMessage("Costas procesales, debe acercarse al Municipio de Loja");
//			statement.setCode("ML.FS.7006");
//			persisBankingEntityLog(false, statement.toString());
//			return statement;
//		}

		if (controlAlertResident(identificationNumber)) {
			statement
					.setMessage("Contribuyente con alertas, debe acercarse al Municipio de Loja");
			statement.setCode("ML.FS.7000");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		} else {
			Taxpayer taxpayer = findTaxpayer(identificationNumber);

			if (taxpayer == null) {
				statement.setMessage("Contribuyente no encontrado");
				statement.setCode("ML.FS.7002");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}

			Date workDayDate;
			/*
			 * if (request.getUsername().compareTo("dabetancourtc") == 0) {
			 * workDayDate = new GregorianCalendar().getTime(); } else
			 */
			workDayDate = findPaymentDate();

			if (workDayDate == null) {
				statement.setMessage("Jornada de trabajo no activa");
				statement.setCode("ML.FS.7003");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}

			Long inPaymentAgreementBondsNumber = findInPaymentAgreementBondsNumber(taxpayer
					.getId());

			if (inPaymentAgreementBondsNumber > 0) {
				statement
						.setMessage("Contribuyente con acuerdo de pago pendiente, debe acercarse al Municipio de Loja");
				statement.setCode("ML.FS.7004");
				persisBankingEntityLog(false, statement.toString());
				return statement;

			} else {
				boolean excludeCEMs = false;
				boolean ENABLE_EXCLUDE_CEMS = (Boolean) systemParameterService.findParameter("ENABLE_EXCLUDE_CEMS");
				List<Long> entriesCEMExclusionIds = new ArrayList<Long>();
				entriesCEMExclusionIds.add(new Long(0));
				if (ENABLE_EXCLUDE_CEMS){
					List<String> usersNotExcludeCEMs = new ArrayList<String>();
					String users = systemParameterService.findParameter("USERS_ONLINE_NOT_EXCLUDE_CEMS");
					usersNotExcludeCEMs = GimUtils.convertStringWithCommaToListString(users);
					if (!usersNotExcludeCEMs.contains(request.getUsername())) { 
						excludeCEMs = true;
						String entriesList = systemParameterService.findParameter("ENTRIES_CEM_EXCLUSION_LIST");
						entriesCEMExclusionIds = GimUtils.convertStringWithCommaToListLong(entriesList);
					}
				}
				List<Long> pendingBondIds = hasPendingBonds(taxpayer.getId(), entriesCEMExclusionIds);
				List<Bond> bonds = new ArrayList<Bond>();
				if (pendingBondIds.size() > 0) {
					try {
						incomeService.calculatePayment(workDayDate,
								pendingBondIds, true, true);
						bonds = findPendingBonds(taxpayer.getId(), excludeCEMs);
						loadBondsDetail(bonds);
					} catch (EntryDefinitionNotFoundException e) {
						e.printStackTrace();

						statement
								.setMessage("Pago no permitido, error en calculo");
						statement.setCode("ML.FS.7005");
						persisBankingEntityLog(false, statement.toString());
						return statement;
					}
				}
				// statement.setData(taxpayer, bonds, workDayDate);
				statement.setData(taxpayer, bonds, new Date());
				statement.setMessage("ok");
				statement.setCode("ML.FS.7200");
				persisBankingEntityLog(true, statement.toString());
				return statement;
			}
		}
	}

	@Override
	public DepositStatementV2 registerDeposit(ServiceRequest request,
			Payout payout) {

		DepositStatementV2 statement = new DepositStatementV2();
		try {

			serverLog = new BankingEntityLog();
			serverLog.setDateTransaction(new Date());
			serverLog.setTransactionId(payout.getTransactionId());
			serverLog.setMethodUsed("registerDeposit_V2");
			serverLog.setBankUsername(request.getUsername());

			Person cashier = findCashier(request.getUsername());
			if (cashier == null) {
				statement.setMessage("Cajero no existente");
				statement.setCode("ML.RD.7000");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}

			Till till = findTill(cashier.getId());
			if (till == null) {
				statement.setMessage("Caja no existente");
				statement.setCode("ML.RD.7001");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}

			Long tillId = till.getId();
			Date workDayDate = findPaymentDate();
			if (workDayDate == null) {
				statement.setMessage("Jornada de trabajo no activa");
				statement.setCode("ML.RD.7002");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}
			
			// buscar id transaccions
			// rfam 2022-03-08
			Boolean idTransacction = checkTransactionId(payout, cashier);
			if(idTransacction){
				statement.setMessage("externalTransactionId duplicado");
				statement.setCode("ML.RD.7201");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}

			TillPermission tillPermission = findTillPermission(cashier.getId(),
					workDayDate);
			if (tillPermission == null
					|| tillPermission.getOpeningTime() == null) {
				statement.setMessage("Permiso de caja no asignado");
				statement.setCode("ML.RD.7003");
				persisBankingEntityLog(false, statement.toString());
				return statement;
				// throw new NotOpenTill();
			} else {
				Date payoutDate = DateUtils.truncate(payout.getPaymentDate());

				Joiner joiner = Joiner.on(",");
				String list = joiner.join(payout.getBondIds());

				Query query = em
						.createNativeQuery("select * from checkPaymentSequence(?1)");
				query.setParameter(1, list);

				String jsonData = (String) query.getSingleResult();

				CheckPaidDTO checkData;
				checkData = new ObjectMapper().readValue(jsonData,
						CheckPaidDTO.class);
				if (checkData.getRequest() == null) {
					statement
							.setMessage("Obligacion no se encuentra en estado pendiente de pago");
					statement.setCode("ML.RD.7014");
					persisBankingEntityLog(false, statement.toString());
					return statement;
				}
				for (int i = 0; i < checkData.getRequest().size(); i++) {
					CheckPaidByEntryDTO req = checkData.getRequest().get(i);
					for (int j = 0; j < checkData.getDebts().size(); j++) {
						CheckPaidByEntryDTO debt = checkData.getDebts().get(j);
						if (req.getEntry().intValue() == debt.getEntry()
								.intValue()
								&& req.getGroup().equals(debt.getGroup())) {

							if (req.getMindate().compareTo(req.getMaxdate()) != 0
									|| req.getMindate().compareTo(
											debt.getMindate()) != 0) {
								int auxCountReq = 0;
								int auxCountDebts = 0;
								for (int k = 0; k < req.getBonds().size(); k++) {
									CheckPaidBondDTO bondReq = req.getBonds()
											.get(k);
									if (bondReq.getEmisiondate().compareTo(
											req.getMaxdate()) < 0) {
										auxCountReq++;
									}
								}

								for (int k = 0; k < debt.getBonds().size(); k++) {
									CheckPaidBondDTO bondDebt = debt.getBonds()
											.get(k);
									if (bondDebt.getEmisiondate().compareTo(
											req.getMaxdate()) < 0) {
										auxCountDebts++;
									}
								}
								if (auxCountReq != auxCountDebts) {
									statement
											.setMessage("No se puede realizar el pago, existen obligaciones que deben ser canceladas previamente");
									statement.setCode("ML.RD.7009");
									persisBankingEntityLog(false,
											statement.toString());
									return statement;
								}
							}
						}
					}
				}

				if (workDayDate.compareTo(payoutDate) == 0) {
					if (BigDecimal.ZERO.compareTo(payout.getAmount()) < 0) {

						Taxpayer taxpayer = findTaxpayer(request
								.getIdentificationNumber());
						if (taxpayer == null) {
							statement.setMessage("Contribuyente no encontrado");
							statement.setCode("ML.RD.7004");
							persisBankingEntityLog(false, statement.toString());
							return statement;
						}

						BigDecimal totalToPay = findAmountToPay(
								taxpayer.getId(), payout.getBondIds());

						if (totalToPay != null
								&& totalToPay.compareTo(payout.getAmount()) == 0) {
							try {
								incomeService.save(payout.getPaymentDate(),
										payout.getBondIds(), cashier, tillId,
										payout.getTransactionId(),
										PaymentMethod.NORMAL.name());
							} catch (Exception e) {
								e.printStackTrace();
								statement
										.setMessage("Pago invalido, valores no concuerdan");
								statement.setCode("ML.RD.7005");
								persisBankingEntityLog(false,
										statement.toString());
								return statement;
								// throw new InvalidPayout();
							}
							em.flush();
							Long paidFromExternalBondStatusId = systemParameterService
									.findParameter(IncomeServiceBean.PAID_FROM_EXTERNAL_CHANNEL_BOND_STATUS);
							persistChangeStatus(payout.getBondIds(),
									paidFromExternalBondStatusId);

							// //// buscar los datos para el pago efectuado
							DepositStatementV2 localInformation = incomeService
									.findDepositInformation(cashier,
											payout.getPaymentDate(), payout);

							statement.setMessage("Pago registrado con exito");
							statement.setCode("ML.RD.7200");
							statement.setReference(localInformation
									.getReference());
							statement
									.setResidentIdentificaciton(localInformation
											.getResidentIdentificaciton());
							statement.setResidentName(localInformation
									.getResidentName());
							statement.setTotal(localInformation.getTotal());

							persisBankingEntityLog(true, statement.toString());
							return statement;
						} else {
							statement.setMessage("Valor de pago incorrecto");
							statement.setCode("ML.RD.7006");
							persisBankingEntityLog(false, statement.toString());
							return statement;
						}
					} else {
						statement
								.setMessage("Valor de pago debe ser mayor a cero");
						statement.setCode("ML.RD.7007");
						persisBankingEntityLog(false, statement.toString());
						return statement;
					}
				} else {
					statement
							.setMessage("Fecha de pago no concuerda con fecha de jornada");
					statement.setCode("ML.RD.7008");
					persisBankingEntityLog(false, statement.toString());
					return statement;
					// throw new NotActiveWorkday();
				}

			}

		} catch (JsonParseException e) {
			statement.setMessage("JsonParseException: " + e.getMessage());
			statement.setCode("ML.RD.7010");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		} catch (JsonMappingException e) {
			statement.setMessage("JsonMappingException: " + e.getMessage());
			statement.setCode("ML.RD.7011");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		} catch (IOException e) {
			statement.setMessage("IOException: " + e.getMessage());
			statement.setCode("ML.RD.7012");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		} catch (Exception e) {
			e.printStackTrace();
			statement.setMessage(e.getMessage());
			statement.setCode("ML.RD.7013");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}

	}

	@Override
	public ClosingStatementV2 findDeposits(ServiceRequest request,
			Date paymentDate) {

		ClosingStatementV2 statement = new ClosingStatementV2();

		serverLog = new BankingEntityLog();
		serverLog.setDateTransaction(new Date());
		serverLog.setTransactionId(null);
		serverLog.setMethodUsed("findDeposits");
		serverLog.setBankUsername(request.getUsername());

		Person cashier = findCashier(request.getUsername());
		if (cashier == null) {
			statement.setMessage("Cajero no existente");
			statement.setCode("ML.FD.7000");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}
		statement.setPaymentDate(paymentDate);
		statement.setTransfers(findTransfers(paymentDate, cashier.getId()));
		statement.setTotal(findTotal(paymentDate, cashier.getId()));
		statement.setMessage("ok");
		statement.setCode("ML.FD.7200");
		persisBankingEntityLog(true, statement.toString());

		return statement;
	}

	@Override
	public Boolean isTillOpen(ServiceRequest request) throws NotOpenTill {
		Boolean isOpen = true;
		Person cashier = findCashier(request.getUsername());
		Date workDayDate = findPaymentDate();
		TillPermission tillPermission = findTillPermission(cashier.getId(),
				workDayDate);
		if (tillPermission == null || tillPermission.getOpeningTime() == null) {
			isOpen = Boolean.FALSE;
		}
		return isOpen;
	}

	@Override
	public List<Long> findDepositsIdsForReverse(ServiceRequest request,
			List<Long> municipalBondsIds) {
		Query query = em.createNamedQuery("Deposit.findByMunicipalBondIds");
		query.setParameter("municipalBondIds", municipalBondsIds);
		return query.getResultList();
	}

	@Override
	public ReverseStatementV2 reversePaymentBank(ServiceRequest request,
			Payout payout) {

		ReverseStatementV2 statement = new ReverseStatementV2();

		serverLog = new BankingEntityLog();
		serverLog.setDateTransaction(new Date());
		serverLog.setTransactionId(payout.getTransactionId());
		serverLog.setMethodUsed("queryPayment");
		serverLog.setBankUsername(request.getUsername());

		if (payout.getTransactionId().trim().isEmpty()) {
			statement.setMessage(Messages.TRANSACTIONID_EMPTY);
			statement.setCode("ML.RS.7000");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}
		
		BigDecimal value=  findDepositValue(payout);
		
		//rfam 2022-03-10 comprobar que los valores a reversar sean los correctos 
		if(value.doubleValue() != payout.getAmount().doubleValue()){
			statement.setMessage("Valor a reversar no concuerda");
			statement.setCode("ML.RS.7030");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}
		
		Query query = this.em.createNativeQuery("SELECT "
				+ "dep.municipalbond_id " + "FROM " + "gimprod.payment pay, "
				+ "gimprod.deposit dep " + "WHERE "
				+ "dep.payment_id = pay.id AND "
				+ "pay.externaltransactionid = ?1 "
				+ "ORDER BY dep.municipalbond_id ASC");

		query.setParameter(1, payout.getTransactionId());
		List<BigInteger> bondsForTransaction = query.getResultList();

		if (bondsForTransaction.isEmpty()) {
			statement.setMessage(Messages.TRANSACTIONID_NOT_FOUND);
			statement.setCode("ML.RS.7001");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}

		int[] bondsForTransactionAux = new int[bondsForTransaction.size()];

		for (int i = 0; i < bondsForTransaction.size(); i++) {
			String s = String.valueOf(bondsForTransaction.get(i).intValue());
			bondsForTransactionAux[i] = Integer.parseInt(s);
		}

		Collections.sort(payout.getBondIds());

		int[] bondIdsAux = new int[payout.getBondIds().size()];

		for (int i = 0; i < payout.getBondIds().size(); i++) {
			bondIdsAux[i] = Integer.parseInt(payout.getBondIds().get(i)
					.toString());
		}

		Boolean equalsBonds = Arrays.equals(bondsForTransactionAux, bondIdsAux);

		if (equalsBonds) {
			try {
				incomeService.reverse(this.findDepositsIdsForReverse(request,
						payout.getBondIds()), Messages.REVERSED_DESCRIPTION,
						this.findUserByUsername(request, request.getUsername())
								.getResident());

				statement.setMessage(Messages.REVERSED_OK);
				statement.setCode("ML.RS.7200");
				persisBankingEntityLog(true, statement.toString());
				return statement;
			} catch (ReverseNotAllowedException e) {
				e.printStackTrace();
				statement.setMessage(Messages.REVERSED_NOT_ALLOWED);
				statement.setCode("ML.RS.7002");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			} catch (ReverseAmongPaymentsIsNotAllowedException e) {
				statement
						.setMessage(Messages.REVERSED_AMONG_PAYMENT_NOT_ALLOWED);
				statement.setCode("ML.RS.7003");
				persisBankingEntityLog(false, statement.toString());
				return statement;
			}
		} else {
			statement.setMessage(Messages.REVERSED_IDS_ERROR);
			statement.setCode("ML.RS.7004");
			persisBankingEntityLog(false, statement.toString());
			return statement;
		}
	}
	
	private BigDecimal findDepositValue(Payout payout){
		
		Query query = em.createNativeQuery("select COALESCE(sum(de.value),0) valor_comprobar "
				+ "from deposit de "
				+ "where de.municipalbond_id in (:ids) "
				+ "and de.status='VALID' ");
		query.setParameter("ids", payout.getBondIds());
		
		BigDecimal value = (BigDecimal) query.getSingleResult();
				
		return value;		
	}

	private Boolean controlAlertResident(String identificationNumber) {
		List<Alert> alerts = new ArrayList<Alert>();
		Query query = em
				.createNamedQuery("Alert.findPendingAlertsByResidentIdNum");
		query.setParameter("residentIdNum", identificationNumber);
		alerts = query.getResultList();
		if (alerts.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private Taxpayer findTaxpayer(String identificationNumber) {
		Query query = em
				.createNamedQuery("Taxpayer.findResidentFullByIdentification");
		query.setParameter("identificationNumber", identificationNumber);
		try {
			Taxpayer taxpayer = (Taxpayer) query.getSingleResult();
			if (taxpayer == null || taxpayer.getId() == null) {
				return null;
			}
			return taxpayer;
		} catch (Exception e) {
			return null;
		}
	}

	private Date findPaymentDate() {
		try {
			Workday workday = incomeService.findActiveWorkday();
			return workday.getDate();
		} catch (NotActiveWorkdayException e) {
			// throw new NotActiveWorkday();
			return null;
		}
	}
	
	/***
	 * comprueba si existe el id de transaccion para un cajeron de sw en especifico
	 * @param payout
	 * @param cashier
	 * @return
	 */
	private Boolean checkTransactionId(Payout payout, Person cashier){
		Query query = em.createNativeQuery("select count(*) "
				+ "from payment p "
				+ "where p.cashier_id = :cashierId "
				+ "and p.externaltransactionid = :transactionId ");
		query.setParameter("cashierId", cashier.getId());
		query.setParameter("transactionId", payout.getTransactionId());
		
		BigInteger amount = (BigInteger) query.getSingleResult();
		
		if(amount.intValue()>0){
			return true;
		}
		return false;
	}

	private Long findInPaymentAgreementBondsNumber(Long taxpayerId) {
		Long inPaymentAgreementBondStatusId = systemParameterService
				.findParameter(IncomeServiceBean.IN_PAYMENT_AGREEMENT_BOND_STATUS);
		Query query = em.createNamedQuery("Bond.countByStatusAndResidentId");
		query.setParameter("residentId", taxpayerId);
		query.setParameter("municipalBondType", MunicipalBondType.CREDIT_ORDER);
		query.setParameter("pendingBondStatusId",
				inPaymentAgreementBondStatusId);
		Long size = (Long) query.getSingleResult();
		return size;
	}

	private List<Long> hasPendingBonds(Long taxpayerId, List<Long> entriesCEMExclusionIds) {
		Long pendingBondStatusId = systemParameterService
				.findParameter(IncomeServiceBean.PENDING_BOND_STATUS);
		Query query = em.createNamedQuery("Bond.findIdsByStatusAndResidentId");
		query.setParameter("residentId", taxpayerId);
		query.setParameter("municipalBondType", MunicipalBondType.CREDIT_ORDER);
		query.setParameter("pendingBondStatusId", pendingBondStatusId);
		query.setParameter("entriesCEMExclusionIds", entriesCEMExclusionIds);
		List<Long> ids = query.getResultList();
		return ids;
	}

	private List<Bond> findPendingBonds(Long taxpayerId, boolean excludeEntries) {
		String entries = systemParameterService.findParameter("AVOID_PAYMENT_ENTRIES");
		if (excludeEntries){
			String entries2 = systemParameterService.findParameter("ENTRIES_CEM_EXCLUSION_LIST");
			entries = entries + "," + entries2;
		}
		List<Long> entryList = GimUtils.convertStringWithCommaToListLong(entries);
		Long pendingBondStatusId = systemParameterService
				.findParameter(IncomeServiceBean.PENDING_BOND_STATUS);
		Query query = em.createNamedQuery("Bond.findByStatusAndResidentIdForWS");
		query.setParameter("residentId", taxpayerId);
		query.setParameter("municipalBondType", MunicipalBondType.CREDIT_ORDER);
		query.setParameter("pendingBondStatusId", pendingBondStatusId);
		query.setParameter("entries", entryList);
		List<Bond> bonds = query.getResultList();

		return bonds;

	}

	private void loadBondsDetail(List<Bond> bonds) {
		String accountCodeDiscount = systemParameterService
				.findParameter(ACCOUNT_CODE_FOR_DISCOUNT);
		String accountCodeInterest = systemParameterService
				.findParameter(ACCOUNT_CODE_FOR_INTEREST);
		String accountCodeSurcharge = systemParameterService
				.findParameter(ACCOUNT_CODE_FOR_SURCHARGE);
		String accountCodeTax = systemParameterService
				.findParameter(ACCOUNT_CODE_FOR_TAX);
		List<Long> bondIds = new ArrayList<Long>();
		for (Bond bond : bonds) {
			bondIds.add(bond.getId());
		}
		Query query = em.createNamedQuery("BondDetail.findBondDetailByIds");
		query.setParameter("bondIds", bondIds);
		List<BondDetail> bondDetails = new ArrayList<BondDetail>();
		bondDetails = query.getResultList();
		for (Bond bond : bonds) {
			List<BondDetail> bdAux = new ArrayList<BondDetail>();
			for (BondDetail bd : bondDetails) {
				if (bd.getBondId().equals(bond.getId())) {
					bdAux.add(bd);
				}
			}
			bond.setBondsDetail(bdAux);
			BondDetail bondDetail = new BondDetail(bond.getId(),
					accountCodeInterest, "intereses", bond.getInterests());
			bond.getBondsDetail().add(bondDetail);
			bondDetail = new BondDetail(bond.getId(), accountCodeSurcharge,
					"recargos", bond.getSurcharges());
			bond.getBondsDetail().add(bondDetail);
			bondDetail = new BondDetail(bond.getId(), accountCodeTax,
					"impuestos", bond.getTaxes());
			bond.getBondsDetail().add(bondDetail);
			bondDetail = new BondDetail(bond.getId(), accountCodeDiscount,
					"descuentos", bond.getDiscounts().multiply(
							BigDecimal.valueOf(-1)));
			bond.getBondsDetail().add(bondDetail);
		}
	}

	private Person findCashier(String username) {
		Query query = em.createNamedQuery("User.findByUsername");
		query.setParameter("name", username);
		try {
			User user = (User) query.getSingleResult();
			query = em.createNamedQuery("User.findResidentByUserId");
			query.setParameter("userId", user.getId());
			Person person = null;
			person = (Person) query.getSingleResult();
			// TODO Es necesario verificar si tiene ROL de cajero?
			return person;
		} catch (Exception e) {
			e.printStackTrace();
		}
		serverLog.setMethodCompleted(false);
		serverLog.setCodeError("InvalidUser");
		em.persist(serverLog);
		// throw new InvalidUser();
		return null;
	}

	private Till findTill(Long personId) {
		Query query = em.createNamedQuery("Till.findByPersonId");
		query.setParameter("personId", personId);
		Till till = null;
		till = (Till) query.getSingleResult();
		if (till != null) {
			if (till.isActive() == true) {
				return till;
			}
		}
		// throw new NotOpenTill();
		return null;
	}

	private TillPermission findTillPermission(Long personId, Date workDayDate) {
		Query query = em
				.createNamedQuery("TillPermission.findByCashierAndWorkdayDate");
		query.setParameter("date", workDayDate);
		query.setParameter("cashierId", personId);
		TillPermission tillPermission = null;
		tillPermission = (TillPermission) query.getSingleResult();
		if (tillPermission != null) {
			if ((tillPermission.getClosingTime() == null)
					&& (tillPermission.isEnabled())) {
				return tillPermission;
			}
		}
		return null;
		// throw new NotOpenTill();
	}

	private BigDecimal findAmountToPay(Long taxpayerId, List<Long> selectedBonds) {
		Long pendingBondStatusId = systemParameterService
				.findParameter(IncomeServiceBean.PENDING_BOND_STATUS);
		Query query = em.createNamedQuery("Bond.findTotalToPay");
		query.setParameter("residentId", taxpayerId);
		query.setParameter("municipalBondType", MunicipalBondType.CREDIT_ORDER);
		query.setParameter("pendingBondStatusId", pendingBondStatusId);
		query.setParameter("selectedBonds", selectedBonds);
		BigDecimal totalToPay = (BigDecimal) query.getSingleResult();
		return totalToPay;
	}

	private void persistChangeStatus(List<Long> municipalBondIds,
			Long municipalBondStatusId) {
		Query query = em.createNamedQuery("MunicipalBond.changeStatus");
		query.setParameter("municipalBondIds", municipalBondIds);
		query.setParameter("municipalBondStatusId", municipalBondStatusId);
		query.executeUpdate();
	}

	private void persisBankingEntityLog(Boolean methodCompleted,
			String codeError) {
		serverLog.setMethodCompleted(methodCompleted);
		serverLog.setCodeError(codeError);
		em.persist(serverLog);
	}

	@SuppressWarnings("unchecked")
	private List<Transfer> findTransfers(Date paymentDate, Long cashierId) {
		Query query = em
				.createNamedQuery("Transfer.findByPaymentDateAndCashier");
		query.setParameter("paymentDate", paymentDate);
		query.setParameter("cashierId", cashierId);
		List<Transfer> transfers = query.getResultList();
		return transfers;
	}

	@SuppressWarnings("unchecked")
	private BigDecimal findTotal(Date paymentDate, Long cashierId) {
		Query query = em
				.createNamedQuery("Transfer.findTotalByPaymentDateAndCashier");
		query.setParameter("paymentDate", paymentDate);
		query.setParameter("cashierId", cashierId);
		List<BigDecimal> totals = query.getResultList();
		// System.out.println("ESCALAR CLASS ----> " + totals.get(0));
		BigDecimal total = BigDecimal.ZERO;
		if (totals.get(0) != null)
			total = totals.get(0);
		return total;
	}

	private User findUserByUsername(ServiceRequest request, String username) {
		Query query = em.createNamedQuery("User.findByUsername");
		query.setParameter("name", username);
		User user = (User) query.getSingleResult();
		return user;
	}
	
	private Boolean avoidPaymentEntry(String identificationNumber) {
		
		String entries = systemParameterService.findParameter("AVOID_PAYMENT_ENTRIES");
		List<Long> entryList = GimUtils.convertStringWithCommaToListLong(entries);

		Query query = em.createNativeQuery("select count(*) "
				+ "from municipalbond m2 "
				+ "join resident r2 on m2.resident_id = r2.id "
				+ "where r2.identificationnumber = :identificacionNumber "
				+ "and m2.entry_id in (:entries) "
				+ "and m2.municipalbondstatus_id in (3,4,5,7,13,14) ");
		query.setParameter("identificacionNumber", identificationNumber);
		query.setParameter("entries", entryList);
		int amount = ((BigInteger) query.getSingleResult()).intValue();
		
		// System.out.println("amount................ "+amount);

		if (amount == 0) {
			return false;
		} else {
			return true;
		}	
	}

}