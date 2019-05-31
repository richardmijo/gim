package org.gob.gim.revenue.action.bankDebit;

import java.util.ArrayList;
import java.util.List;

import org.gob.gim.common.CatalogConstants;
import org.gob.gim.common.ServiceLocator;
import org.gob.gim.common.action.UserSession;
import org.gob.gim.common.service.SystemParameterService;
import org.gob.gim.revenue.service.BankDebitService;
import org.gob.gim.revenue.service.ItemCatalogService;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityController;

import ec.gob.gim.common.model.ItemCatalog;
import ec.gob.gim.revenue.model.bankDebit.BankDebit;
import ec.gob.gim.revenue.model.bankDebit.criteria.BankDebitSearchCriteria;
import ec.gob.gim.revenue.model.bankDebit.dto.BankDebitDTO;
import ec.gob.gim.waterservice.model.WaterSupply;

@Name("bankDebitHome")
@Scope(ScopeType.CONVERSATION)
public class BankDebitHome extends EntityController {

	@In(scope = ScopeType.SESSION, value = "userSession")
	UserSession userSession;

	@In
	FacesMessages facesMessages;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BankDebitService bankDebitService;

	private ItemCatalogService itemCatalogService;

	private SystemParameterService systemParameterService;

	/**
	 * criterios
	 * 
	 * @return
	 */

	// @In(create = true,required= false)
	// ImpugnmentDataModel dataModel;

	private BankDebitSearchCriteria criteria;

	private List<BankDebitDTO> debits = new ArrayList<BankDebitDTO>();

	private BankDebit debitSelected;

	private ItemCatalog typeAccount;

	private List<ItemCatalog> typesAccounts;

	private Integer serviceNumber;
	private WaterSupply waterSupplySelect;
	private String identificacionOwner;
	private String nameOwner;
	private String accountNumber;
	private String accountHolder;
	private Boolean active;

	private Boolean isRegister = Boolean.FALSE;
	private Boolean isEdit = Boolean.FALSE;

	public BankDebitHome() {
		loadDebits();
	}

	public BankDebitSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(BankDebitSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<BankDebitDTO> getDebits() {
		return debits;
	}

	public void setDebits(List<BankDebitDTO> debits) {
		this.debits = debits;
	}

	public BankDebit getDebitSelected() {
		return debitSelected;
	}

	public void setDebitSelected(BankDebit debitSelected) {
		this.debitSelected = debitSelected;
	}

	public ItemCatalog getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(ItemCatalog typeAccount) {
		this.typeAccount = typeAccount;
	}

	public List<ItemCatalog> getTypesAccounts() {
		return typesAccounts;
	}

	public void setTypesAccounts(List<ItemCatalog> typesAccounts) {
		this.typesAccounts = typesAccounts;
	}

	public Integer getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(Integer serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getIdentificacionOwner() {
		return identificacionOwner;
	}

	public void setIdentificacionOwner(String identificacionOwner) {
		this.identificacionOwner = identificacionOwner;
	}

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void findDebits() {

		this.debits = this.bankDebitService.findDebitsForCriteria(criteria);
	}

	public void loadDebits() {
		initializeService();
		this.criteria = new BankDebitSearchCriteria();
		findDebits();

		typesAccounts = itemCatalogService
				.findItemsForCatalogCode(CatalogConstants.CATALOG_TYPES_BANK_ACCOUNT);

	}

	public void initializeService() {
		if (bankDebitService == null) {
			bankDebitService = ServiceLocator.getInstance().findResource(
					BankDebitService.LOCAL_NAME);
		}
		if (itemCatalogService == null) {
			itemCatalogService = ServiceLocator.getInstance().findResource(
					ItemCatalogService.LOCAL_NAME);
		}
		/*
		 * if (systemParameterService == null) { systemParameterService =
		 * ServiceLocator.getInstance().findResource(
		 * SystemParameterService.LOCAL_NAME); }
		 */
	}

	public void searchDebits() {

		this.debits = this.bankDebitService.findDebitsForCriteria(criteria);
	}

	public void preparaRegisterDebit() {

		this.isRegister = Boolean.TRUE;
		this.isEdit = Boolean.FALSE;

		this.identificacionOwner = null;
		this.nameOwner = null;
		this.waterSupplySelect = null;
		this.serviceNumber = null;
		this.typeAccount = null;
		this.accountNumber = null;
		this.accountHolder = null;
		System.out.println("llega al preparaRegisterDebit");

	}

	public void prepareUpdateDebit(Long debitId) {
		this.isRegister = Boolean.FALSE;
		this.isEdit = Boolean.TRUE;
		this.debitSelected = bankDebitService.findById(debitId);
		if (this.debitSelected != null) {
			this.identificacionOwner = this.debitSelected.getWaterSupply()
					.getServiceOwner().getIdentificationNumber();
			this.nameOwner = this.debitSelected.getWaterSupply()
					.getServiceOwner().getName();
			this.waterSupplySelect = this.debitSelected.getWaterSupply();
			this.serviceNumber = this.debitSelected.getWaterSupply().getServiceNumber();
			this.typeAccount = this.debitSelected.getAccountType();
			this.accountNumber = this.debitSelected.getAccountNumber();
			this.accountHolder = this.debitSelected.getAccountHolder();
			this.active = this.debitSelected.getActive();
		} else {
			this.identificacionOwner = null;
			this.nameOwner = null;
			this.waterSupplySelect = null;
			this.serviceNumber = null;
			this.typeAccount = null;
			this.accountNumber = null;
			this.accountHolder = null;
			this.active = null;
		}
	}

	public void searchWaterService() {
		System.out.println("Llega al searchWaterService");
		if (this.serviceNumber != null) {
			WaterSupply ws = this.bankDebitService
					.findWaterSupplyByServiceNumber(this.serviceNumber);
			if (ws == null) {
				this.identificacionOwner = "";
				this.nameOwner = "";
				addFacesMessageFromResourceBundle(
						"revenue.bankDebit.notFoundWaterService",
						this.serviceNumber);
			} else {
				this.waterSupplySelect = ws;
				this.identificacionOwner = ws.getServiceOwner()
						.getIdentificationNumber();
				this.nameOwner = ws.getServiceOwner().getName();
			}

			/*
			 * MunicipalBond mb = impugnmentService
			 * .findMunicipalBondForImpugnment(this.impugnmentSelected
			 * .getNumberInfringement());
			 * 
			 * if (mb == null) { this.impugnmentSelected.setMunicipalBond(new
			 * MunicipalBond()); this.existObligationForInpugnmentNumber =
			 * Boolean.FALSE; addFacesMessageFromResourceBundle(
			 * "revenue.bankDebit.notFoundWaterService",
			 * this.impugnmentSelected.getNumberInfringement()); } else {
			 * this.existObligationForInpugnmentNumber = Boolean.TRUE;
			 * this.impugnmentSelected.setMunicipalBond(mb); }
			 */
		}
	}

	public void registerDebit() {
		if (this.waterSupplySelect == null || this.identificacionOwner == null
				|| this.nameOwner == null || this.typeAccount == null
				|| this.accountNumber == null || this.accountHolder == null) {
			facesMessages.addToControl("",
					org.jboss.seam.international.StatusMessage.Severity.ERROR,
					"Campos obligatorios vacios");
		} else {
			this.debitSelected = new BankDebit();
			this.debitSelected.setAccountHolder(accountHolder);
			this.debitSelected.setAccountNumber(accountNumber);
			this.debitSelected.setAccountType(typeAccount);
			this.debitSelected.setWaterSupply(waterSupplySelect);
			this.bankDebitService.save(debitSelected);
			this.debits = this.bankDebitService.findDebitsForCriteria(criteria);
		}

	}
	
	public void updateDebit() {
		if (this.waterSupplySelect == null || this.identificacionOwner == null
				|| this.nameOwner == null || this.typeAccount == null
				|| this.accountNumber == null || this.accountHolder == null) {
			facesMessages.addToControl("",
					org.jboss.seam.international.StatusMessage.Severity.ERROR,
					"Campos obligatorios vacios");
		} else {
			this.debitSelected.setAccountHolder(accountHolder);
			this.debitSelected.setAccountNumber(accountNumber);
			this.debitSelected.setAccountType(typeAccount);
			this.debitSelected.setWaterSupply(waterSupplySelect);
			this.debitSelected.setActive(this.active);
			this.bankDebitService.update(debitSelected);
			this.debits = this.bankDebitService.findDebitsForCriteria(criteria);
			this.debitSelected = null;
		}

	}

}