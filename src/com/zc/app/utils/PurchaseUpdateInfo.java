package com.zc.app.utils;

public class PurchaseUpdateInfo {
	private String sw;
	private String logId;
	private String mac2;
	private String tac;
	private String balance;

	public String getSw() {
		return sw;
	}

	public void setSw(String sw) {
		this.sw = sw;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getMac2() {
		return mac2;
	}

	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "PurchaseUpdateInfo [sw=" + sw + ", logId=" + logId + ", mac2="
				+ mac2 + ", tac=" + tac + ", balance=" + balance + "]";
	}

}
