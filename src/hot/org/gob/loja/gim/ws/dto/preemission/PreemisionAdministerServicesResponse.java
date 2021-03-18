package org.gob.loja.gim.ws.dto.preemission;

public class PreemisionAdministerServicesResponse extends GenericResponseWS {
	private Long bondId;
	private Long emissionOrderId;

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public Long getEmissionOrderId() {
		return emissionOrderId;
	}

	public void setEmissionOrderId(Long emissionOrderId) {
		this.emissionOrderId = emissionOrderId;
	}

	@Override
	public String toString() {
		return "PreemisionAdministerServicesResponse [bondId=" + bondId
				+ ", emissionOrderId=" + emissionOrderId + "]";
	}

}
