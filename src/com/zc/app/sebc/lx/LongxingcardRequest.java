package com.zc.app.sebc.lx;

public class LongxingcardRequest {
	private boolean isOK;
	private String swString;
	
	private String amountString;
	private String balanceString;
	private String panString;
	private String issuerIdString;
	private String appSerialNoString;
	private String terminalNoString;
	
	private String tacString;
	private String mac2String;
	
	private String responseString;

	public boolean isOK() {
		return isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}

	public String getSwString() {
		return swString;
	}

	public void setSwString(String swString) {
		this.swString = swString;
	}

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public String getBalanceString() {
		return balanceString;
	}

	public void setBalanceString(String balanceString) {
		this.balanceString = balanceString;
	}

	public String getPanString() {
		return panString;
	}

	public void setPanString(String panString) {
		this.panString = panString;
	}

	public String getIssuerIdString() {
		return issuerIdString;
	}

	public void setIssuerIdString(String issuerIdString) {
		this.issuerIdString = issuerIdString;
	}

	public String getAppSerialNoString() {
		return appSerialNoString;
	}

	public void setAppSerialNoString(String appSerialNoString) {
		this.appSerialNoString = appSerialNoString;
	}

	public String getTerminalNoString() {
		return terminalNoString;
	}

	public void setTerminalNoString(String terminalNoString) {
		this.terminalNoString = terminalNoString;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public String getTacString() {
		return tacString;
	}

	public void setTacString(String tacString) {
		this.tacString = tacString;
	}

	public String getMac2String() {
		return mac2String;
	}

	public void setMac2String(String mac2String) {
		this.mac2String = mac2String;
	}

	@Override
	public String toString() {
		return "LongxingcardRequest [isOK=" + isOK + ", swString=" + swString
				+ ", amountString=" + amountString + ", balanceString="
				+ balanceString + ", panString=" + panString
				+ ", issuerIdString=" + issuerIdString + ", appSerialNoString="
				+ appSerialNoString + ", terminalNoString=" + terminalNoString
				+ ", tacString=" + tacString + ", mac2String=" + mac2String
				+ ", responseString=" + responseString + "]";
	}

	
	
}
