/**
 * 
 */
package org.gob.gim.coercive.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gob.gim.coercive.dto.criteria.DataInfractionSearchCriteria;
import org.gob.gim.coercive.pagination.DataInfractionDataModel;
import org.gob.gim.coercive.service.DatainfractionService;
import org.gob.gim.common.ServiceLocator;
import org.gob.gim.common.action.UserSession;
import org.gob.gim.revenue.service.ItemCatalogService;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityController;

import ec.gob.gim.coercive.model.infractions.Datainfraction;
import ec.gob.gim.coercive.model.infractions.HistoryStatusInfraction;
import ec.gob.gim.common.model.ItemCatalog;

/**
 * @author René
 *
 */

@Name("dataInfractionListHome")
@Scope(ScopeType.CONVERSATION)
public class DataInfractionListHome extends EntityController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	FacesMessages facesMessages;

	private DataInfractionSearchCriteria criteria;

	private List<ItemCatalog> statuses = new ArrayList<ItemCatalog>();
	private ItemCatalog status;

	private String changeStatusExplanation;

	private Datainfraction currentItem;

	private ItemCatalogService itemCatalogService;

	private DatainfractionService datainfractionService;
	
	@In(create = true)
	UserSession userSession;

	/**
	 * 
	 */
	public DataInfractionListHome() {
		super();
		this.criteria = new DataInfractionSearchCriteria();
		this.initializeService();
		this.statuses = this.itemCatalogService
				.findItemsForCatalogCodeOrderById("CATALOG_STATUS_INFRACTIONS");
		this.search();
	}

	public void initializeService() {
		if (datainfractionService == null) {
			datainfractionService = ServiceLocator.getInstance().findResource(
					DatainfractionService.LOCAL_NAME);
		}
		if (itemCatalogService == null) {
			itemCatalogService = ServiceLocator.getInstance().findResource(
					ItemCatalogService.LOCAL_NAME);
		}
	}

	public DataInfractionSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(DataInfractionSearchCriteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the statuses
	 */
	public List<ItemCatalog> getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses
	 *            the statuses to set
	 */
	public void setStatuses(List<ItemCatalog> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return the status
	 */
	public ItemCatalog getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ItemCatalog status) {
		this.status = status;
	}

	/**
	 * @return the changeStatusExplanation
	 */
	public String getChangeStatusExplanation() {
		return changeStatusExplanation;
	}

	/**
	 * @param changeStatusExplanation
	 *            the changeStatusExplanation to set
	 */
	public void setChangeStatusExplanation(String changeStatusExplanation) {
		this.changeStatusExplanation = changeStatusExplanation;
	}

	/**
	 * @return the currentItem
	 */
	public Datainfraction getCurrentItem() {
		return currentItem;
	}

	/**
	 * @param currentItem
	 *            the currentItem to set
	 */
	public void setCurrentItem(Datainfraction currentItem) {
		this.currentItem = currentItem;
	}

	public void search() {
		getDataModel().setCriteria(this.criteria);
		getDataModel().setRowCount(getDataModel().getObjectsNumber());
	}

	private DataInfractionDataModel getDataModel() {

		DataInfractionDataModel dataModel = (DataInfractionDataModel) Component
				.getInstance(DataInfractionDataModel.class, true);

		return dataModel;
	}

	public void prepareChangeStatus(Datainfraction infraction) {
		this.currentItem = infraction;
		this.status = null;
		this.changeStatusExplanation = null;
	}
	
	public void prepareViewDetail(Datainfraction infraction) {
		this.currentItem = infraction;
	}
	
	public void prepareViewHistoryChangeStatus(Datainfraction infraction) {
		this.currentItem = this.datainfractionService.getDataInfractionWithHistoryById(infraction.getId());
	}
	

	public void saveChangeStatus() {

		if (this.status == null
				|| (this.changeStatusExplanation == null && this.changeStatusExplanation
						.isEmpty())) {
			facesMessages.addToControl("",
					org.jboss.seam.international.StatusMessage.Severity.ERROR,
					"Campos obligatorios vacios");
		} else {
			if (datainfractionService == null) {
				datainfractionService = ServiceLocator.getInstance()
						.findResource(datainfractionService.LOCAL_NAME);
			}

			this.currentItem.setState(this.status);
			this.currentItem = this.datainfractionService.updateDataInfraction(this.currentItem);
			
			// agregar al historial
			HistoryStatusInfraction record = new HistoryStatusInfraction();
			record.setDate(new Date());
			record.setInfraction(this.currentItem);
			record.setObservation(this.changeStatusExplanation);
			record.setStatus(this.status);
			record.setUser(this.userSession.getUser());
			
			this.datainfractionService.saveHIstoryRecord(record);
		}

	}

}
