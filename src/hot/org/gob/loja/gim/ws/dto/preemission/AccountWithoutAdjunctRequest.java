package org.gob.loja.gim.ws.dto.preemission;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class AccountWithoutAdjunctRequest extends CommonRequest {

	@NotNull(message = "value no puede ser nulo")
	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AccountWithoutAdjunctRequest [value=" + value + "]";
	}

}
