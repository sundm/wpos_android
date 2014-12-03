package com.zc.app.mpos.adapter;

public class LogItem {
	private String tradePan;
	private String tradeDate;
	private String tradeTime;
	private String tradeAmount;

	public String getTradePan() {
		return tradePan;
	}

	public void setTradePan(String tradePan) {
		this.tradePan = tradePan;
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
		return "DetailItem [tradePan=" + tradePan + ", tradeDate="
				+ tradeDate + ", tradeTime=" + tradeTime + ", tradeAmount="
				+ tradeAmount + "]";
	}

}