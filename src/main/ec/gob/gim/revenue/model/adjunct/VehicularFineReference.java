package ec.gob.gim.revenue.model.adjunct;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gob.gim.common.DateUtils;
import org.hibernate.envers.Audited;

import ec.gob.gim.revenue.model.Adjunct;
import ec.gob.gim.revenue.model.adjunct.detail.VehicleType;

@Audited
@Entity
@DiscriminatorValue("VEFIR")
public class VehicularFineReference extends Adjunct {

	private String numberPlate;
	private String notificationNumber;
	@Temporal(TemporalType.DATE)
	private Date infringementDate;

	@ManyToOne
	private VehicleType vehicleType;

	public VehicularFineReference() {
		this.infringementDate = new Date();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vehicleType != null ? vehicleType.getName().toUpperCase() : "ND");
		
		sb.append(" - ");
		sb.append(numberPlate != null ? numberPlate : "ND");
		
		sb.append(" - ");
		sb.append(notificationNumber != null ? notificationNumber.toUpperCase() : "ND");
		return sb.toString().toUpperCase();
	}

	@Override
	public List<ValuePair> getDetails() {
		List<ValuePair> details = new LinkedList<ValuePair>();
		ValuePair pair = new ValuePair("Placa Nro.: ", numberPlate != null ? numberPlate : "-");
		details.add(pair);

		pair = new ValuePair("Nro. Infracci\u00F3n", notificationNumber != null ? notificationNumber.toUpperCase() : "-");
		details.add(pair);
		
		pair = new ValuePair("Fecha citaci\u00F3n",
				infringementDate != null ? DateUtils.formatFullDate(infringementDate) : "-");
		details.add(pair);
		
		pair = new ValuePair("Tipo", vehicleType != null ? vehicleType.getName().toUpperCase() : "-");
		details.add(pair);

		return details;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate.toUpperCase().trim();
	}

	public String getNotificationNumber() {
		return notificationNumber;
	}

	public void setNotificationNumber(String notificationNumber) {
		this.notificationNumber = notificationNumber.toUpperCase().trim();
	}

	public Date getInfringementDate() {
		return infringementDate;
	}

	public void setInfringementDate(Date infringementDate) {
		this.infringementDate = infringementDate;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

}
