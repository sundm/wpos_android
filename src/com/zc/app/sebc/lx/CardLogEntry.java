package com.zc.app.sebc.lx;

import java.io.Serializable;

public class CardLogEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2568582855476341230L;

	private String serialNo;
	private String overdrawLimit;
	private String amount;
	private String type;
	private String terminalNumber;
	private String date;
	private String time;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOverdrawLimit() {
		return overdrawLimit;
	}

	public void setOverdrawLimit(String overdrawLimit) {
		this.overdrawLimit = overdrawLimit;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CardLogEntry [serialNo=" + serialNo + ", overdrawLimit="
				+ overdrawLimit + ", amount=" + amount + ", type=" + type
				+ ", terminalNumber=" + terminalNumber + ", date=" + date
				+ ", time=" + time + "]";
	}

}
