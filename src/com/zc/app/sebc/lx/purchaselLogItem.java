package com.zc.app.sebc.lx;

public class purchaselLogItem {
	private String amount;
	private String createTime;
	private String pan;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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
