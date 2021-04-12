package org.gob.loja.gim.ws.dto.preemission.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.gob.gim.common.validators.InList;
import org.gob.gim.common.validators.NotEmpty;

public class ApprovalPlansRequest extends CommonRequest {

	@NotNull(message = "accountCode no puede ser nulo")
	@NotEmpty(message = "accountCode no puede ser vacío")
	@InList(values = { "00507" }, message = "accountCode no autorizado")
	private String accountCode;

	@NotNull(message = "value no puede ser nulo")
	private BigDecimal value;

	@NotNull(message = "cadastralCode no puede ser nulo")
	@NotEmpty(message = "cadastralCode no puede ser vacío")
	private String cadastralCode;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCadastralCode() {
		return cadastralCode;
	}

	public void setCadastralCode(String cadastralCode) {
		this.cadastralCode = cadastralCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	@Override
	public String toString() {
		return "ApprovalPlansRequest [value=" + value + ", cadastralCode="
				+ cadastralCode + ", getValue()=" + getValue()
				+ ", getCadastralCode()=" + getCadastralCode()
				+ ", getEmiterIdentification()=" + getEmiterIdentification()
				+ ", getResidentIdentification()="
				+ getResidentIdentification() + ", getAccountCode()="
				+ getAccountCode() + ", getExplanation()=" + getExplanation()
				+ ", getReference()=" + getReference() + ", getAddress()="
				+ getAddress() + "]";
	}

}
