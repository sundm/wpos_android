package com.zc.app.sebc.lx;

/**
 * 消费日志，用于记录消费信息
 * 
 */

public class PurchaseLog {

	private String amount;
	private String time;
	private String pan;

	public String getAmount() {
		return amount;
	}

	public String getTime() {
		return time;
	}

	public String getPan() {
		return pan;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

}
