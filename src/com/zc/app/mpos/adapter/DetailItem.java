package com.zc.app.mpos.adapter;

import java.util.Date;

public class DetailItem {
	private String tradeType;
	private String tradeDate;
	private String tradeTime;
	private String tradeAmount;

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Override
	public String toString() {
		return "DetailItem [tradeType=" + tradeType + ", tradeDate="
				+ tradeDate + ", tradeTime=" + tradeTime + ", tradeAmount="
				+ tradeAmount + "]";
	}

}