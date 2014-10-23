package com.zc.app.utils;

public class WPosInfo {
	private String terminalId;
	private String merchantId;
	private String validateCode;
	private String fingerprint;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	@Override
	public String toString() {
		return "WPosInfo [terminalId=" + terminalId + ", merchantId="
				+ merchantId + ", validateCode=" + validateCode
				+ ", fingerprint=" + fingerprint + "]";
	}

}
