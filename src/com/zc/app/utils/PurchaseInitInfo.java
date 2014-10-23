package com.zc.app.utils;

public class PurchaseInitInfo {
	private String initResponse;
	private String amount;
	private String pan;
	private String issuerId;
	private String lng;
	private String lat;

	public String getInitResponse() {
		return initResponse;
	}

	public void setInitResponse(String initResponse) {
		this.initResponse = initResponse;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "PurchaseInitInfo [initResponse=" + initResponse + ", amount="
				+ amount + ", pan=" + pan + ", issuerId=" + issuerId + ", lng="
				+ lng + ", lat=" + lat + "]";
	}

}
