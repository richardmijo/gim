package ec.gob.gim.appraisal.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.envers.Audited;

/**
 * @author IML
 */
@Audited
@Entity
@TableGenerator(
	 name="AppraisalItemExternalGenerator",
	 table="IdentityGenerator",
	 pkColumnName="name",
	 valueColumnName="value",
	 pkColumnValue="AppraisalItemExternal",
	 initialValue=1, allocationSize=1
)

public class AppraisalItemExternal {

	@Id
	@GeneratedValue(generator="AppraisalItemExternalGenerator",strategy=GenerationType.TABLE)
	private Long id;
	private BigDecimal coste;
	private BigDecimal coeficiente;
	private BigDecimal subtotal;

	@ManyToOne
	@JoinColumn(name="appraisalItemBase_id")
	private AppraisalItemBase appraisalItemBase;
	
	@ManyToOne
	private AppraisalTotalExternal appraisalTotalExternal;
 
	public AppraisalItemExternal(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCoste() {
		return coste;
	}

	public void setCoste(BigDecimal coste) {
		this.coste = coste;
	}

	public BigDecimal getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(BigDecimal coeficiente) {
		this.coeficiente = coeficiente;
		this.subtotal = this.coeficiente.multiply(this.coste);
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public AppraisalItemBase getAppraisalItemBase() {
		return appraisalItemBase;
	}

	public void setAppraisalItemBase(AppraisalItemBase appraisalItemBase) {
		this.appraisalItemBase = appraisalItemBase;
		this.coste = appraisalItemBase.getCoste();
	}

	public AppraisalTotalExternal getAppraisalTotalExternal() {
		return appraisalTotalExternal;
	}

	public void setAppraisalTotalExternal(AppraisalTotalExternal appraisalTotalExternal) {
		this.appraisalTotalExternal = appraisalTotalExternal;
	}

}
