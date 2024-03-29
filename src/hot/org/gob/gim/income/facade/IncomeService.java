package org.gob.gim.income.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.gob.gim.exception.CreditNoteValueNotValidException;
import org.gob.gim.exception.InvoiceNumberOutOfRangeException;
import org.gob.gim.exception.NotActiveWorkdayException;
import org.gob.gim.exception.ReverseAmongPaymentsIsNotAllowedException;
import org.gob.gim.exception.ReverseNotAllowedException;
import org.gob.gim.revenue.exception.EntryDefinitionNotFoundException;
import org.gob.loja.gim.ws.dto.BondSummary;
import org.gob.loja.gim.ws.dto.FutureBond;
import org.gob.loja.gim.ws.dto.Payout;
import org.gob.loja.gim.ws.dto.v2.DepositStatementV2;

import ec.gob.gim.common.model.FinancialStatus;
import ec.gob.gim.common.model.Person;
import ec.gob.gim.common.model.Resident;
import ec.gob.gim.income.model.CreditNote;
import ec.gob.gim.income.model.Deposit;
import ec.gob.gim.income.model.LegalStatus;
import ec.gob.gim.income.model.PaymentAgreement;
import ec.gob.gim.income.model.PaymentFraction;
import ec.gob.gim.income.model.Receipt;
import ec.gob.gim.income.model.TaxpayerRecord;
import ec.gob.gim.income.model.Workday;
import ec.gob.gim.revenue.model.ExemptionCem;
import ec.gob.gim.revenue.model.MunicipalBond;
import ec.gob.gim.security.model.MunicipalbondAux;

@Local
public interface IncomeService {
	public String LOCAL_NAME = "/gim/IncomeService/local";
	
	public final String PENDING_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_PENDING";
	public final String IN_PAYMENT_AGREEMENT_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_IN_PAYMENT_AGREEMENT";
	public final String BLOCKED_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_BLOCKED";
	
	public final String PAID_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_PAID";
	public final String PAID_FROM_EXTERNAL_CHANNEL_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_PAID_FROM_EXTERNAL_CHANNEL";
	public final String COMPENSATION_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_COMPENSATION";
	
	public final String REVERSED_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_REVERSED"; 
	public final String VOID_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_VOID";
	public final String CORRECTION_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_ERRORS_CORRECTION";
	
	public final String FUTURE_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_FUTURE";
	
	//@author macartuche
	public final String SUBSCRIPTION_BOND_STATUS = "MUNICIPAL_BOND_STATUS_ID_SUBSCRIPTION";
	
	public final String ENABLE_RECEIPT_GENERATION = "ENABLE_RECEIPT_GENERATION";
	public final String ELECTRONIC_INVOICE_ENABLE = "ELECTRONIC_INVOICE_ENABLE";
	public final String ELECTRONIC_INVOICE_BASE_URI = "ELECTRONIC_INVOICE_BASE_URI";
	public final String ELECTRONIC_INVOICE_XML_DIR = "ELECTRONIC_INVOICE_XML_DIR";
	public final String ELECTRONIC_INVOICE_ENVIRONMENT = "ELECTRONIC_INVOICE_ENVIRONMENT";
	

	/*
	List<InterestRate> findInterestRateByExpirationDate(Date endDate);
	InterestRate findInterestRateById(Long interestRateId);
	*/
	void calculatePayment(MunicipalBond municipalBond, boolean isForPay, boolean applyDiscount) throws EntryDefinitionNotFoundException;
	void calculatePayment(MunicipalBond municipalBond, Date paymentServiceDate, boolean isForPay, boolean applyDiscount) throws EntryDefinitionNotFoundException;   //2
	void calculatePayment(List<MunicipalBond> municipalBond, Date paymentServiceDate, boolean isForPay, boolean applyDiscount) throws EntryDefinitionNotFoundException; //1 desde vista cajerita
	void calculatePayment(Date paymentDate, List<Long> municipalBondIds, boolean isForPay, boolean applyDiscount) throws EntryDefinitionNotFoundException;
	
	void calculatePayment(MunicipalBond municipalBond, boolean isForPay, boolean applyDiscount, Object ... facts) throws EntryDefinitionNotFoundException;
	void calculatePayment(MunicipalBond municipalBond, Date paymentServiceDate, boolean isForPay, boolean applyDiscount, Object ... facts) throws EntryDefinitionNotFoundException; //3
	void calculatePayment(List<MunicipalBond> municipalBond, Date paymentServiceDate, boolean isForPay, boolean applyDiscount, Object ... facts) throws EntryDefinitionNotFoundException;
	public void deactivateCreditNotes(List<PaymentFraction> paymentFractions);
	
	public void save(Date paymentDate, List<Long> municipalBondIds, Person cashier, Long tillId, String externalTransactionId, String paymentMethod) throws Exception;
	public void save(List<Deposit> deposits, Long paymentAgreementId, Long tillId, String paymentMethod) throws Exception;
	public void saveForCompensationPayment(List<MunicipalBond> municipalBonds, Long tillId) throws Exception;
	public void setAsPrinted(List<Long> printedDepositIds);
	
	public List<Deposit> findDepositsForReverse(Long residentId, Long cashierId);
	public List<Deposit> findDepositsReverseReport(Date startDate, Date endDate);
	public void reverse(List<Long> depositIds, String concept, Resident userReversed) throws ReverseNotAllowedException, ReverseAmongPaymentsIsNotAllowedException;
	public void eliminateReverse(Long depositId, Resident userReversed);
	public void createCreditNote(CreditNote creditNote, LegalStatus legalStatus) throws CreditNoteValueNotValidException;
	public List<Deposit> findDeposits(Long municipalBondId);
	public void updateReprintings(Long municipalBondId);
	public void updateMunicipalBonds(List<MunicipalBond> bonds);
	
	public List<MunicipalBond> findPendingBonds(Long residentId, boolean includeCEMs);
	public List<MunicipalBond> findPendingBondsSubscriptions(Long residentId);
	public List<MunicipalBond> findOnlyPendingBonds(Long residentId, boolean includeCEMs);
	public List<MunicipalBond> findOnlyPendingBonds(Long residentId, Long EntryId);	
	public List<MunicipalBond> findOnlyPendingAndInAgreementBonds(Long residentId, boolean includeCEMs);
	public List<MunicipalBond> findOnlyPendingAndInAgreementBonds(Long residentId, Long EntryId);
	public List<MunicipalBond> findPendingBondsBetweenDates(Long residentId, Date startDate, Date endDate);
	public List<MunicipalBond> findPendingBondsBetweenDates(Long residentId, Long entryId, Date startDate, Date endDate);
	/**
	 * @created 2015-09-20
	 * @author rarmijos
	 * @param residentId
	 * @return
	 */
	public List<FutureBond> findFutureBonds(Long residentId);
	public void save(PaymentAgreement paymentAgreement, List<Long> municipalBondsIds);
	public MunicipalBond loadForPrinting(Long municipalBondId);
	public MunicipalBond loadMunicipalBond(Long municipalBondId);
	public TaxpayerRecord findDefaultInstitution();
	public void createSequence(Long taxpayerRecordId, Long receiptTypeId, Integer branchNumber, Integer tillNumber);
	public Workday findActiveWorkday() throws NotActiveWorkdayException;
	
	public List<Receipt> findReceipts(Date startDate, Date endDate, FinancialStatus finantialStatus);
	
	/** @created 2015-06-11
	 * @author macartuche
	 * @param sequenceName
	 */
	public void createSequenceComplementVoucher(String sequenceName);	
	public Long generateNextValue(String sequenceName) throws InvoiceNumberOutOfRangeException;
	
	/**
	 * @author macartuche
	 * Poner a true que la compensacion ha sido pagada
	 */
	public void compensationPayment(List<Deposit> deposits);
	
	
	//@author
	//@tag recaudacionCoactivas
	//@date 2016-07-06T12:27
	//realizar el reverso en municipalbondaux - abonos compensaciones
	public void reversePaymentAgreements(List<Long> depositIds) throws ReverseNotAllowedException; 

	//@author
	//@tag recaudacionesCoactivas
	//@date 2016-07-08T15:25:11
	public List<MunicipalbondAux> getBondsAuxByIdAndStatus(Long id, Boolean coverInterest, String status, String type, String paymentType);
	
	public BigDecimal sumAccumulatedInterest(Long id, Boolean coverInterest, String status, String type, String paymentType);
	
	//Jock Samaniego
	//actualizar estado de convenios 21/09/2016
	public void updatePaymentAgreement(PaymentAgreement paymentAgreement);
	
	public boolean checkIsPayed(Long municipalbondid, String type);
	
	/**
	 * @author macartuche
	 * @param residentId
	 * @return
	 */
	public List<BondSummary> findBondsDownStatus(Long residentId);
	
	/**
	 * Para remision caso en que se pague todo el convenio
	 * @author macartuche
	 * @param paymentAgreement
	 */
	public void update(PaymentAgreement paymentAgreement); 
	
	public boolean verifyApplyDiscount(Long entryId, String groupingCode, Long residentId);
	
	/**
	 * rfam 2020-03-22
	 * retorna la informacion para  la impresion del detalle en bancos
	 * @return
	 */
	public DepositStatementV2 findDepositInformation(Person cashier, Date paymentDate, Payout payout);
	
	public void saveForBankLiquidation(Date paymentDate, List<Long> municipalBondIds, Person cashier, Long tillId, String externalTransactionId, String paymentMethod) throws Exception;
	
	//macartuche
	//2021-07-22 08:04
	//Exoneraciones para tercera edad y discapacidad
	public BigDecimal checkHasDiscountCEM(String itemCode, String catalogCode, Long resident, Long adjunctid); 
	
}