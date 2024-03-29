package org.gob.gim.revenue.action;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.persistence.Query;

import org.gob.gim.coercive.service.InfringementAgreementService;
import org.gob.gim.common.DateUtils;
import org.gob.gim.common.GimUtils;
import org.gob.gim.common.ServiceLocator;
import org.gob.gim.common.action.MunicipalBondUtil;
import org.gob.gim.common.action.UserSession;
import org.gob.gim.common.service.SystemParameterService;
import org.gob.gim.income.action.PaymentHome;
import org.gob.gim.income.facade.IncomeService;
import org.gob.gim.income.service.PaymentAgreementService;
import org.gob.gim.income.view.MunicipalBondItem;
import org.gob.gim.revenue.exception.EntryDefinitionNotFoundException;
import org.gob.gim.revenue.facade.RevenueService;
import org.gob.loja.gim.ws.dto.BondSummary;
import org.gob.loja.gim.ws.dto.FutureBond;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.core.Interpolator;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import ec.gob.gim.common.model.Charge;
import ec.gob.gim.common.model.Delegate;
import ec.gob.gim.common.model.FiscalPeriod;
import ec.gob.gim.common.model.Resident;
import ec.gob.gim.finances.model.DTO.MetadataBondDTO;
import ec.gob.gim.income.model.Payment;
import ec.gob.gim.income.model.PaymentAgreement;
import ec.gob.gim.revenue.model.Entry;
import ec.gob.gim.revenue.model.MunicipalBond;
import ec.gob.gim.revenue.model.MunicipalBondStatus;
import ec.gob.gim.revenue.model.MunicipalBondType;

@Name("municipalBondCondition")
@Scope(ScopeType.CONVERSATION)
public class MunicipalBondCondition extends EntityQuery<MunicipalBond> {

	public static String REVENUE_SERVICE_NAME = "/gim/RevenueService/local";
	private String MUNICIPAL_BOND_STATUS_ID_BLOCKED_NAME = "MUNICIPAL_BOND_STATUS_ID_BLOCKED";
	private String MUNICIPAL_BOND_STATUS_ID_COMPENSATION_NAME = "MUNICIPAL_BOND_STATUS_ID_COMPENSATION";
	private String MUNICIPAL_BOND_STATUS_ID_PAID_NAME = "MUNICIPAL_BOND_STATUS_ID_PAID";
	private String MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL_NAME = "MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL";
	private String ENTRIES_CEM_EXCLUSION_LIST = "ENTRIES_CEM_EXCLUSION_LIST";
	private String ENABLE_EXCLUDE_CEMS = "ENABLE_EXCLUDE_CEMS";
	// private String MUNICIPAL_BOND_STATUS_ID_FUTURE_EMISION_NAME =
	// "MUNICIPAL_BOND_STATUS_ID_FUTURE_EMISION";
	// rfam 2018-05-03 abonos
	// private String MUNICIPAL_BOND_STATUS_ID_SUBSCRIPTION_NAME =
	// "MUNICIPAL_BOND_STATUS_ID_SUBSCRIPTION";

	private Date startDate;
	private Date endDate;
	private Date currentDate;

	private Resident resident;
	private String criteria;
	private String identificationNumber;
	private List<Resident> residents;

	private Entry entry;
	private String entryCode;
	private String criteriaEntry;
	private List<Entry> entries;

	private MunicipalBondType municipalBondType;
	private List<Long> municipalBondStatusIds;
	private List<Long> entriesCEMExclusionIds;

	private List<MunicipalBond> result;

	private List<MunicipalBond> pendingBonds;

	private List<FutureBond> futureBonds;
	private List<BondSummary> bondDownSumary;
	private BigDecimal totalFutereBond;

	private BigDecimal totalInterestRemision= BigDecimal.ZERO;
	private BigDecimal totalSurchargeRemision = BigDecimal.ZERO ;
	
	private String memo;

	private Boolean showAgreementInfo = Boolean.FALSE;

	private BigDecimal payed = BigDecimal.ZERO;
	private BigDecimal balanceForPay = BigDecimal.ZERO;
	private BigDecimal canceledValue = BigDecimal.ZERO;
	private List<Payment> payments = new ArrayList<Payment>();
	private BigInteger totalPayments = BigInteger.ZERO;
	private Integer dividendsSize;
	private String agreementType;
	
	private Boolean hasAgreementInfringement = Boolean.FALSE;

	public List<MunicipalBond> getResult() {
		return result;
	}

	public void setResult(List<MunicipalBond> result) {
		this.result = result;
	}

	@In(create = true)
	UserSession userSession;

	@Logger
	Log log;

	@In(create = true)
	private Renderer renderer;
	
	private GimUtils gimUtils;

	@In(create = true)
	PaymentHome paymentHome;

	List<MunicipalBondItem> municipalBondItemsResult;

	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select distinct(municipalBond) from MunicipalBond municipalBond "
			+ "left join fetch municipalBond.resident resident left join fetch municipalBond.municipalBondStatus "
			+ "left join fetch municipalBond.entry entry " + "LEFT JOIN FETCH municipalBond.institution "
			+ "LEFT JOIN FETCH municipalBond.receipt " + "LEFT JOIN FETCH municipalBond.items it "
			+ "LEFT JOIN FETCH it.entry " + "LEFT JOIN FETCH municipalBond.discountItems di "
			+ "LEFT JOIN FETCH di.entry " + "LEFT JOIN FETCH municipalBond.deposits deposit "
			+ "LEFT JOIN FETCH municipalBond.surchargeItems si " + "LEFT JOIN FETCH si.entry "
			+ "LEFT JOIN FETCH municipalBond.taxItems ti " + "LEFT JOIN FETCH ti.tax "
			+ "LEFT JOIN FETCH municipalBond.adjunct ";

	private static final String[] RESTRICTIONS = {
			"resident.identificationNumber = #{municipalBondCondition.identificationNumber}",
			"entry.code = #{municipalBondCondition.entryCodeForSearch}",
			"municipalBond.emisionDate >= #{municipalBondCondition.startDate}",
			"municipalBond.emisionDate <= #{municipalBondCondition.endDate}",
			"municipalBond.municipalBondType = #{municipalBondCondition.municipalBondType}",
			"municipalBond.municipalBondStatus.id IN (#{municipalBondCondition.municipalBondStatusIds})",
			"municipalBond.entry.id NOT IN (#{municipalBondCondition.entriesCEMExclusionIds})" };

	public MunicipalBondCondition() {
		this.findMunicipalBonds();
		municipalBondType = MunicipalBondType.CREDIT_ORDER;
		municipalBondStatusIds = new ArrayList<Long>();
		entriesCEMExclusionIds = new ArrayList<Long>();
		entriesCEMExclusionIds.add(new Long(0));

		SystemParameterService systemParameterService = ServiceLocator.getInstance()
				.findResource(SystemParameterService.LOCAL_NAME);
		Long MUNICIPAL_BOND_STATUS_ID_BLOCKED = systemParameterService
				.findParameter(MUNICIPAL_BOND_STATUS_ID_BLOCKED_NAME);
		Long MUNICIPAL_BOND_STATUS_ID_PAID = systemParameterService.findParameter(MUNICIPAL_BOND_STATUS_ID_PAID_NAME);
		Long MUNICIPAL_BOND_STATUS_ID_COMPENSATION = systemParameterService
				.findParameter(MUNICIPAL_BOND_STATUS_ID_COMPENSATION_NAME);
		Long MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL = systemParameterService
				.findParameter(MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL_NAME);

		// rfam 2018-10-02 se qita porq las futuras se presentan en el esta normla del
		// cuenta
		// richardmijo 2015-09
		// Long MUNICIPAL_BOND_STATUS_ID_FUTURE_EMISION =
		// systemParameterService.findParameter(MUNICIPAL_BOND_STATUS_ID_FUTURE_EMISION_NAME);

		municipalBondStatusIds.add(MUNICIPAL_BOND_STATUS_ID_BLOCKED);
		municipalBondStatusIds.add(MUNICIPAL_BOND_STATUS_ID_PAID);
		municipalBondStatusIds.add(MUNICIPAL_BOND_STATUS_ID_COMPENSATION);
		municipalBondStatusIds.add(MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL);

		// rfam 2018-10-02 se qita porq las futuras se presentan en el esta normla del
		// cuenta
		// municipalBondStatusIds.add(MUNICIPAL_BOND_STATUS_ID_FUTURE_EMISION);

		enabledIncludeCEMs = (Boolean) systemParameterService.findParameter(ENABLE_EXCLUDE_CEMS);
//		if ((enabledIncludeCEMs) &&(!includeCEMs)){
//			String str = systemParameterService.findParameter(ENTRIES_CEM_EXCLUSION_LIST);
//			entriesCEMExclusionIds = GimUtils.convertStringWithCommaToListLong(str);
//		}

		systemParameterService = ServiceLocator.getInstance().findResource(SYSTEM_PARAMETER_SERVICE_NAME);
	}

	private boolean enabledIncludeCEMs;
	private boolean includeCEMs = false;
	private boolean isFirstTime = true;

	private Boolean isFromNotifications;

	public Boolean getIsFromNotifications() {
		return isFromNotifications;
	}

	public void setIsFromNotifications(Boolean isFromNotifications) {
		this.isFromNotifications = isFromNotifications;
	}

	public boolean isIncludeCEMs() {
		return includeCEMs;
	}

	public void setIncludeCEMs(boolean includeCEMs) {
		this.includeCEMs = includeCEMs;
	}

	public boolean isEnabledIncludeCEMs() {
		return enabledIncludeCEMs;
	}

	public void setEnabledIncludeCEMs(boolean enabledIncludeCEMs) {
		this.enabledIncludeCEMs = enabledIncludeCEMs;
	}

	public List<Long> getEntriesCEMExclusionIds() {
		return entriesCEMExclusionIds;
	}

	public void setEntriesCEMExclusionIds(List<Long> entriesCEMExclusionIds) {
		this.entriesCEMExclusionIds = entriesCEMExclusionIds;
	}

	public Boolean getHasAgreementInfringement() {
		return hasAgreementInfringement;
	}

	public void setHasAgreementInfringement(Boolean hasAgreementInfringement) {
		this.hasAgreementInfringement = hasAgreementInfringement;
	}

	public void initDates() {
		if (!isFirstTime)
			return;
		isFirstTime = false;
		endDate = new Date();
		if (startDate == null) {
			FiscalPeriod fiscalPeriod = userSession.getFiscalPeriod();
			// log.info("====== Ingreso a fijar fecha de inicio");
			startDate = fiscalPeriod.getStartDate();
		}
		if (identificationNumber != null)
			searchResident();
		if (entryCode != null)
			searchEntry();
		if (identificationNumber != null)
			try {
				chargeResults();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// setMaxResults(25);
	}

	public void findMunicipalBonds() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setOrder("municipalBond.emisionDate,municipalBond.municipalBondStatus");
	}

	@In
	FacesMessages facesMessages;

	public List<MunicipalBondItem> getMunicipalBondItems() throws Exception {
		result = new LinkedList<MunicipalBond>();
		if (resident != null) {
			IncomeService incomeService = ServiceLocator.getInstance().findResource(IncomeService.LOCAL_NAME);
			List<MunicipalBond> pendingBonds = null;
			if (entry == null) {
				pendingBonds = incomeService.findOnlyPendingAndInAgreementBonds(resident.getId(), includeCEMs);
			} else {
				pendingBonds = incomeService.findOnlyPendingAndInAgreementBonds(resident.getId(), entry.getId());
			}
			// remision
			// se agrega variable false al final para pago completo, ya que es estado de
			// cuenta
			incomeService.calculatePayment(pendingBonds, new Date(), true, true);
			result.addAll(pendingBonds);
		}

		result.addAll(getResultList());
		MunicipalBondUtil.setMunicipalBondStatus(findPendingStatus());
		MunicipalBondUtil.setInAgreementStatus(findInAgreementStatus());
		// rfam 2018 pagos por abonos
		MunicipalBondUtil.setInSubscriptionStatus(findInSubscriptionStatus());
		return MunicipalBondUtil.fillMunicipalBondItems(result);
	}

	private MunicipalBondStatus findPendingStatus() {
		SystemParameterService systemParameterService = ServiceLocator.getInstance()
				.findResource(SystemParameterService.LOCAL_NAME);
		return systemParameterService.materialize(MunicipalBondStatus.class, "MUNICIPAL_BOND_STATUS_ID_PENDING");
	}

	private MunicipalBondStatus findInAgreementStatus() {
		SystemParameterService systemParameterService = ServiceLocator.getInstance()
				.findResource(SystemParameterService.LOCAL_NAME);
		return systemParameterService.materialize(MunicipalBondStatus.class,
				"MUNICIPAL_BOND_STATUS_ID_IN_PAYMENT_AGREEMENT");
	}

	private MunicipalBondStatus findInSubscriptionStatus() {
		SystemParameterService systemParameterService = ServiceLocator.getInstance()
				.findResource(SystemParameterService.LOCAL_NAME);
		return systemParameterService.materialize(MunicipalBondStatus.class, "MUNICIPAL_BOND_STATUS_ID_SUBSCRIPTION");
	}

	public void maxResultsReports() {
		setMaxResults(null);
	}

	@Override
	@Transactional
	public List<MunicipalBond> getResultList() {
		// log.info("==== INGRESO AL RESULTLIST: #0", identificationNumber);
		if (identificationNumber != null && !identificationNumber.equals("")) {
			return super.getResultList();
		}

		return new ArrayList<MunicipalBond>();
	}

	public void notifyPendingBonds() {
		currentDate = Calendar.getInstance().getTime();
		pendingBonds = findPendingBonds(resident.getId());
		if (this.getResident() != null) {
			sendMail(renderer);
		}
	}

	public String toStringCurrentDate() {
		return DateUtils.formatFullDate(currentDate);
	}

	private List<MunicipalBond> findPendingBonds(Long residentId) {
		IncomeService incomeService = ServiceLocator.getInstance().findResource(IncomeService.LOCAL_NAME);
		List<MunicipalBond> mbs = null;

		if (entry == null) {
			mbs = incomeService.findPendingBonds(residentId, includeCEMs);
		} else {
			mbs = incomeService.findOnlyPendingAndInAgreementBonds(resident.getId(), entry.getId());
		}

		try {
			incomeService.calculatePayment(mbs, new Date(), true, true);
		} catch (EntryDefinitionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mbs;
	}

	@Asynchronous
	private void sendMail(Renderer r) {
		try {
			r.render("/common/PendingBondsMail.xhtml");
			addFacesMessageFromResourceBundle("#{messages['common.mailSent']}");
		} catch (Exception e) {
			e.printStackTrace();
			addFacesMessageFromResourceBundle("#{messages['common.mailNotSent']}");
		}
	}

	@Transactional
	@Override
	public Long getResultCount() {
		if (identificationNumber != null && !identificationNumber.equals("")) {
			return super.getResultCount();
		}
		return null;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public List<Resident> getResidents() {
		return residents;
	}

	public void setResidents(List<Resident> residents) {
		this.residents = residents;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public BigDecimal getTotalInterestRemision() {
		return totalInterestRemision;
	}

	public void setTotalInterestRemision(BigDecimal totalInterestRemision) {
		this.totalInterestRemision = totalInterestRemision;
	}

	public BigDecimal getTotalSurchargeRemision() {
		return totalSurchargeRemision;
	}

	public void setTotalSurchargeRemision(BigDecimal totalSurchargeRemision) {
		this.totalSurchargeRemision = totalSurchargeRemision;
	}

	@SuppressWarnings("unchecked")
	public void searchResidentByCriteria() {
		// System.out.println("SEARCH RESIDENT BY CRITERIA " + this.criteria);
		if (this.criteria != null && !this.criteria.isEmpty()) {
			Query query = getEntityManager().createNamedQuery("Resident.findByCriteria");
			query.setParameter("criteria", this.criteria);
			setResidents(query.getResultList());
		}
	}

	public void searchResident() {
		// REMISION
		// encerar valores
		totalInterestRemision = BigDecimal.ZERO;
		totalSurchargeRemision = BigDecimal.ZERO;

		// System.out.println("RESIDENT CHOOSER CRITERIA... " +
		// this.identificationNumber);
		Query query = getEntityManager().createNamedQuery("Resident.findByIdentificationNumber");
		query.setParameter("identificationNumber", this.identificationNumber);
		try {
			Resident resident = (Resident) query.getSingleResult();
			setResident(resident);

			if (resident.getId() == null) {
				addFacesMessageFromResourceBundle("resident.notFound");
			} else {
				// TIPO DE CONVENIO
				PaymentAgreementService agreementService = ServiceLocator.getInstance()
						.findResource(PaymentAgreementService.LOCAL_NAME);
				Integer numberPayments = agreementService.numberAgreementsActivesByResident(resident.getId());
				if (numberPayments > 0) {
					showAgreementInfo = Boolean.TRUE;
					PaymentAgreement agreement = agreementService.findActivePaymentResident(resident.getId());
					this.calculatePayedValue(agreement.getId());
					this.calculateCanceledBonds(agreement.getId());
					paymentHome.setPaymentAgreement(agreement);
					this.calculateBalanceForPay();
					this.dividendsSize = agreement.getDividends().size();
					this.loadTotalDeposit(agreement.getId());
					this.agreementType = agreement.getAgreementType().name();
					
					
				} else {
					showAgreementInfo = Boolean.FALSE;
				}
				
				InfringementAgreementService infringementAgreementService = ServiceLocator.getInstance()
						.findResource(InfringementAgreementService.LOCAL_NAME);
				hasAgreementInfringement = infringementAgreementService.hasInfringementAgreementActive(resident.getIdentificationNumber());

			}

		} catch (Exception e) {
			setResident(null);
			addFacesMessageFromResourceBundle("resident.notFound");
		}
		municipalBondItemsResult = null;
		futureBonds = null;
	}

	public void calculatePayedValue(Long agreementId) {

		Query q = getEntityManager().createNativeQuery(
				"Select coalesce(SUM(d.value),0) " + "from Deposit d join municipalbond m on m.id = d.municipalbond_id "
						+ "where m.paymentagreement_id=:id and d.status='VALID' "
						+ "and m.municipalbondstatus_id <> :canceled");

		q.setParameter("id", agreementId);
		q.setParameter("canceled", new Long(9));

		this.payed = (BigDecimal) q.getSingleResult();

	}

	public void calculateBalanceForPay() {
		balanceForPay = BigDecimal.ZERO;
		
		paymentHome.calculateTotals();
		for (MunicipalBond bond : paymentHome.getMunicipalBonds()) {
			balanceForPay = balanceForPay.add(bond.getPaidTotal());
		}
	}

	public void calculateCanceledBonds(Long agreementId) {
		Query q = getEntityManager().createNativeQuery("select coalesce(sum(m.value),0) from municipalbond m "
				+ "where m.paymentagreement_id=:agreement and m.municipalbondstatus_id=:canceled");
		q.setParameter("canceled", 9L); // anulados o dados de baja
		q.setParameter("agreement", agreementId);
		this.canceledValue = (BigDecimal) q.getSingleResult();

	}

	public void loadTotalDeposit(Long agreementId) {

		Query q = getEntityManager().createNativeQuery(
				"select count(distinct(p.id)) " + "from payment  p join deposit d on d.payment_id= p.id  "
						+ "join municipalbond m on m.id= d.municipalbond_id "
						+ "where m.paymentagreement_id=:id and p.status='VALID' and d.status='VALID' ");
		q.setParameter("id", agreementId);

		List<Object> data = q.getResultList();
		if (data.size() > 0) {
			totalPayments = (BigInteger) data.get(0);
		}

	}

	public List<MunicipalBondItem> getMunicipalBondItemsResult() {
		return municipalBondItemsResult;
	}

	private BigDecimal pendingTotal;

	public BigDecimal calculateTotal() {
		pendingTotal = BigDecimal.ZERO;
		// System.out.println("antes for.............................
		// "+municipalBondItemsResult.size());
		for (MunicipalBondItem mbi : municipalBondItemsResult) {
			// System.out.println(" for------ "+mbi.getMunicipalBond().getNumber()+"
			// "+mbi.getMunicipalBond().getEntry().getName());
			pendingTotal = pendingTotal.add(mbi.getMunicipalBond().getPaidTotal());
		}
		return pendingTotal.setScale(2, RoundingMode.HALF_UP);
	}

	public void setMunicipalBondItemsResult(List<MunicipalBondItem> municipalBondItemsResult) {
		this.municipalBondItemsResult = municipalBondItemsResult;
	}

	public void chargeResults() throws Exception {
		try {
			this.totalInterestRemision = BigDecimal.ZERO;
			this.totalSurchargeRemision = BigDecimal.ZERO;
			totalFutereBond = BigDecimal.ZERO;
			municipalBondItemsResult = getMunicipalBondItems();
			findFutureEmision(resident.getId());
			calculateTotal();
			this.calculateValuesRemision();
		} catch (Exception e) {
			log.info("==== EXCEPTION CHARGE RESULTS: #0", e);
			String message = Interpolator.instance()
					.interpolate("#{messages['entryDefinition.entryDefinitionNotFoundException']}", new Object[0]);
			facesMessages.addToControl("", org.jboss.seam.international.StatusMessage.Severity.ERROR, message);
		}
	}

	public void calculateValuesRemision() throws JsonParseException, JsonMappingException, IOException {

		for (MunicipalBondItem item : this.municipalBondItemsResult) {

			String _json = item.getMunicipalBond().getMetadata();
			MetadataBondDTO meta = new ObjectMapper().readValue(item.getMunicipalBond().getMetadata(),
					MetadataBondDTO.class);
			this.totalInterestRemision = this.totalInterestRemision.add(meta.getInterest());
			this.totalSurchargeRemision = this.totalSurchargeRemision.add(meta.getSurcharge());
		}

	}

	public void residentSelectedListener(ActionEvent event) {
		UIComponent component = event.getComponent();
		Resident resident = (Resident) component.getAttributes().get("resident");
		// TIPO DE CONVENIO
		PaymentAgreementService agreementService = ServiceLocator.getInstance()
				.findResource(PaymentAgreementService.LOCAL_NAME);
		Integer numberPayments = agreementService.numberAgreementsActivesByResident(resident.getId());
		if (numberPayments > 0) {
			showAgreementInfo = Boolean.TRUE;
			PaymentAgreement agreement = agreementService.findActivePaymentResident(resident.getId());
			this.calculatePayedValue(agreement.getId());
			this.calculateCanceledBonds(agreement.getId());
			paymentHome.setPaymentAgreement(agreement);
			this.calculateBalanceForPay();
			this.dividendsSize = agreement.getDividends().size();
			this.loadTotalDeposit(agreement.getId());
			this.agreementType = agreement.getAgreementType().name();
		} else {
			showAgreementInfo = Boolean.FALSE;
		}
		setResident(resident);
		this.setIdentificationNumber(resident.getIdentificationNumber());
		municipalBondItemsResult = null;
		futureBonds = null;
		InfringementAgreementService infringementAgreementService = ServiceLocator.getInstance()
				.findResource(InfringementAgreementService.LOCAL_NAME);
		hasAgreementInfringement = infringementAgreementService.hasInfringementAgreementActive(resident.getIdentificationNumber());
	}

	public void clearSearchResidentPanel() {
		this.setCriteria(null);
		setResidents(null);
	}

	private String entryCodeForSearch;

	public String getEntryCodeForSearch() {
		return entryCodeForSearch;
	}

	public void setEntryCodeForSearch(String entryCodeForSearch) {
		this.entryCodeForSearch = entryCodeForSearch;
	}

	public void searchEntry() {
		if (entryCode != null) {
			RevenueService revenueService = ServiceLocator.getInstance().findResource(REVENUE_SERVICE_NAME);
			Entry entry = revenueService.findEntryByCode(entryCode);
			if (entry != null) {
				this.entry = entry;
				this.setEntry(entry);
				if (entry.getAccount() != null) {
					setEntryCode(entry.getAccount().getAccountCode());
				} else {
					setEntryCode(entry.getCode());
				}
				setEntryCodeForSearch(entry.getCode());
			} else {
				setEntry(null);
				setEntryCodeForSearch(null);
			}
			municipalBondItemsResult = null;
			futureBonds = null;
		}
	}

	public void searchEntryByCriteria() {
		// logger.info("SEARCH Entry BY CRITERIA "+this.criteriaEntry);
		if (this.criteriaEntry != null && !this.criteriaEntry.isEmpty()) {
			RevenueService revenueService = (RevenueService) ServiceLocator.getInstance()
					.findResource(REVENUE_SERVICE_NAME);
			entries = revenueService.findEntryByCriteria(criteriaEntry);
		}
	}

	public void clearEntryChooserPanel() {
		clearSearchEntryPanel();
		setEntryCode(null);
		setEntryCodeForSearch(null);
		setEntry(null);
	}

	public void clearSearchEntryPanel() {
		this.setCriteriaEntry(null);
		entries = null;
	}

	private List<MunicipalBond> municipalBonds;

	public List<MunicipalBond> getMunicipalBonds() {
		return municipalBonds;
	}

	public void setMunicipalBonds(List<MunicipalBond> municipalBonds) {
		this.municipalBonds = municipalBonds;
	}

	private SystemParameterService systemParameterService;

	public static String SYSTEM_PARAMETER_SERVICE_NAME = "/gim/SystemParameterService/local";

	private Boolean hasDetailCheckerRole;

	public Boolean getHasDetailCheckerRole() {
		if (hasDetailCheckerRole != null)
			return hasDetailCheckerRole;
		if (systemParameterService == null)
			systemParameterService = ServiceLocator.getInstance().findResource(SYSTEM_PARAMETER_SERVICE_NAME);
		String role = systemParameterService.findParameter(UserSession.ROLE_NAME_DETAIL_CHECKER);
		if (role == null) {
			hasDetailCheckerRole = false;
		} else {
			hasDetailCheckerRole = userSession.getUser().hasRole(role);
		}
		return hasDetailCheckerRole;
	}

	public void loadMunicipalBond(Long municipalBondId) {
		municipalBonds = new ArrayList<MunicipalBond>();
		if (municipalBondId != null) {
			IncomeService incomeService = ServiceLocator.getInstance().findResource(IncomeService.LOCAL_NAME);
			MunicipalBond municipalBond = incomeService.loadForPrinting(municipalBondId);
			municipalBonds.add(municipalBond);
			if (resident == null)
				resident = municipalBond.getResident();
		}
	}

	public void entrySelectedListener(ActionEvent event) {
		UIComponent component = event.getComponent();
		Entry entry = (Entry) component.getAttributes().get("entry");
		this.setEntry(entry);
		if (entry.getAccount() != null) {
			setEntryCode(entry.getAccount().getAccountCode());
		} else {
			setEntryCode(entry.getCode());
		}
		setEntryCodeForSearch(entry.getCode());
		municipalBondItemsResult = null;
		futureBonds = null;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getCriteriaEntry() {
		return criteriaEntry;
	}

	public void setCriteriaEntry(String criteriaEntry) {
		this.criteriaEntry = criteriaEntry;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public MunicipalBondType getMunicipalBondType() {
		return municipalBondType;
	}

	public void setMunicipalBondType(MunicipalBondType municipalBondType) {
		this.municipalBondType = municipalBondType;
	}

	public List<Long> getMunicipalBondStatusIds() {
		return municipalBondStatusIds;
	}

	public void setMunicipalBondStatusIds(List<Long> municipalBondStatusIds) {
		this.municipalBondStatusIds = municipalBondStatusIds;
	}

	public BigDecimal getPendingTotal() {
		return pendingTotal;
	}

	public void setPendingTotal(BigDecimal pendingTotal) {
		this.pendingTotal = pendingTotal;
	}

	public List<MunicipalBond> getPendingBonds() {
		return pendingBonds;
	}

	public void setPendingBonds(List<MunicipalBond> pendingBonds) {
		this.pendingBonds = pendingBonds;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/*
	 * public List<MunicipalBond> getFutureBonds() { return futureBonds; }
	 * 
	 * public void setFutureBonds(List<MunicipalBond> futureBonds) {
	 * this.futureBonds = futureBonds; }
	 */

	public void findFutureEmision(Long residentId) {
		try {
			IncomeService incomeService = ServiceLocator.getInstance().findResource(IncomeService.LOCAL_NAME);
			this.futureBonds = incomeService.findFutureBonds(residentId);
			/*
			 * for (FutureBond mb : futureBonds) { totalFutereBond =
			 * totalFutereBond.add(mb.getTotal()); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getBalanceForPay() {
		return balanceForPay;
	}

	public void setBalanceForPay(BigDecimal balanceForPay) {
		this.balanceForPay = balanceForPay;
	}

	public BigDecimal getCanceledValue() {
		return canceledValue;
	}

	public void setCanceledValue(BigDecimal canceledValue) {
		this.canceledValue = canceledValue;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public BigInteger getTotalPayments() {
		return totalPayments;
	}

	public void setTotalPayments(BigInteger totalPayments) {
		this.totalPayments = totalPayments;
	}

	public List<FutureBond> getFutureBonds() {
		return futureBonds;
	}

	public void setFutureBonds(List<FutureBond> futureBonds) {
		this.futureBonds = futureBonds;
	}

	public BigDecimal getTotalFutereBond() {
		return totalFutereBond;
	}

	public void setTotalFutereBond(BigDecimal totalFutereBond) {
		this.totalFutereBond = totalFutereBond;
	}

	public List<BondSummary> getBondDownSumary() {
		return bondDownSumary;
	}

	public void setBondDownSumary(List<BondSummary> bondDownSumary) {
		this.bondDownSumary = bondDownSumary;
	}

	public Boolean getShowAgreementInfo() {
		return showAgreementInfo;
	}

	public void setShowAgreementInfo(Boolean showAgreementInfo) {
		this.showAgreementInfo = showAgreementInfo;
	}

	public BigDecimal getPayed() {
		return payed;
	}

	public void setPayed(BigDecimal payed) {
		this.payed = payed;
	}

	public Integer getDividendsSize() {
		return dividendsSize;
	}

	public void setDividendsSize(Integer dividendsSize) {
		this.dividendsSize = dividendsSize;
	}

	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * rfam 2018-08-31 ML-JC-2048-267 visualizar bajas
	 * 
	 * @param residentId
	 */
	public void findBondsDown() {
		try {
			IncomeService incomeService = ServiceLocator.getInstance().findResource(IncomeService.LOCAL_NAME);
			this.bondDownSumary = incomeService.findBondsDownStatus(resident.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public String letterValues(String value){
		return GimUtils.Convertir(value, "dólar", "dólares", "centavo", "centavos", "con", true);
	}

	public String getIncomeName() {
		Charge incomeCharge = getCharge("DELEGATE_ID_INCOME");
		if (incomeCharge != null) {
			for (Delegate d : incomeCharge.getDelegates()) {
				if (d.getIsActive())
					return d.getName();
			}
		}
		return "";
	}
	
	public Charge getCharge(String systemParameter) {
		if (systemParameterService == null)
			systemParameterService = ServiceLocator.getInstance().findResource(SYSTEM_PARAMETER_SERVICE_NAME);
		Charge charge = systemParameterService.materialize(Charge.class,systemParameter);
		return charge;
	}
	
}
