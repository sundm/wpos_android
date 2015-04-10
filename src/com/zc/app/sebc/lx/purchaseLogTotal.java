package com.zc.app.sebc.lx;

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
		this.sumOfAmount = sumOfAmount;
	}

	@Override
	public String toString() {
		return "purchaseLogTotal [count=" + count + ", sumOfAmount="
				+ sumOfAmount + "]";
	}

}
