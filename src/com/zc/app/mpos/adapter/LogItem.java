package com.zc.app.mpos.adapter;

import java.math.BigDecimal;

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
		BigDecimal v1 = new BigDecimal(tradeAmount);
		BigDecimal v2 = new BigDecimal("100.00");
		tradeAmount = v1.divide(v2, 2, BigDecimal.ROUND_DOWN).toString();
		this.tradeAmount = tradeAmount;
	}

	@Override
	public String toString() {
		return "LogItem [tradeTime=" + tradeTime + ", tradeAmount="
				+ tradeAmount + "]";
	}

}