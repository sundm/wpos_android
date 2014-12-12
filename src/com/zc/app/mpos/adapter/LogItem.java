package com.zc.app.mpos.adapter;

public class LogItem {
	private String tradeTime;
	private String tradeAmount;

	public String getTradeTime() {
		return tradeTime;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Override
	public String toString() {
		return "LogItem [tradeTime=" + tradeTime + ", tradeAmount="
				+ tradeAmount + "]";
	}

}