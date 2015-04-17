package com.zc.app.sebc.lx;

import java.math.BigDecimal;

public class purchaseLogTotal {
	private String count;
	private String sumOfAmount;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSumOfAmount() {
		return sumOfAmount;
	}

	public void setSumOfAmount(String sumOfAmount) {
		BigDecimal v1 = new BigDecimal(sumOfAmount);
		BigDecimal v2 = new BigDecimal("100.00");
		sumOfAmount = v1.divide(v2, 2, BigDecimal.ROUND_DOWN).toString();
		this.sumOfAmount = sumOfAmount;
	}

	@Override
	public String toString() {
		return "purchaseLogTotal [count=" + count + ", sumOfAmount="
				+ sumOfAmount + "]";
	}

}
