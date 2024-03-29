/**
 * 
 */
package org.gob.gim.coercive.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gob.gim.coercive.service.NotificationInfractionsService;
import org.gob.gim.common.ServiceLocator;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityController;

import ec.gob.gim.coercive.model.infractions.Datainfraction;
import ec.gob.gim.coercive.model.infractions.NotificationInfractions;

/**
 * @author René
 *
 */
@Name("overdueInfractionsNotificationReport")
public class OverdueInfractionsNotificationReport extends EntityController{
	
	private NotificationInfractionsService notificationInfractionsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectedItems = "";
	
	private boolean isFirstTime = true;
	
	@In(required = true, create = true)
	OverdueInfractionsListHome overdueInfractionsListHome;
	
	private List<NotificationInfractions> notifications = new ArrayList<NotificationInfractions>();
	
	public void initialize(){
		if (isFirstTime) {
			if (notificationInfractionsService == null) {
				notificationInfractionsService = ServiceLocator.getInstance().findResource(
						notificationInfractionsService.LOCAL_NAME);
			}
		}
		this.notifications = this.notificationInfractionsService.getNotifications(overdueInfractionsListHome.getGeneratedNotificationIds());
	}
	
	/**
	 * @return the notifications
	 */
	public List<NotificationInfractions> getNotifications() {
		return notifications;
	}



	/**
	 * @param notifications the notifications to set
	 */
	public void setNotifications(List<NotificationInfractions> notifications) {
		this.notifications = notifications;
	}



	/**
	 * @return the selectedItems
	 */
	public String getSelectedItems() {
		return selectedItems;
	}

	/**
	 * @param selectedItems the selectedItems to set
	 */
	public void setSelectedItems(String selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public List<Long> getSelectedItemAsList() {
		return org.gob.gim.common.Util.splitArrayString(this.getSelectedItems());
	}
	
	public BigDecimal getTotal(NotificationInfractions notification){
		BigDecimal total = BigDecimal.ZERO;
		for (Datainfraction infraction : notification.getInfractions()) {
			total = total.add(infraction.getValue());
		}
		return total;
	}
}
