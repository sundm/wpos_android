package com.zc.app.sebc.lx;

import java.math.BigDecimal;

public class purchaselLogItem {
	private String amount;
	private String createTime;
	private String pan;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		BigDecimal v1 = new BigDecimal(amount);
		BigDecimal v2 = new BigDecimal("100.00");
		amount = v1.divide(v2, 2, BigDecimal.ROUND_DOWN).toString();
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	@Override
	public String toString() {
		return "purchaselLogItem [amount=" + amount + ", createTime="
				+ createTime + ", pan=" + pan + "]";
	}

}
