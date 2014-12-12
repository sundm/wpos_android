package com.zc.app.sebc.lx;

import java.util.List;

public class PurchaseLogPage {
	private List<PurchaseLog> purchaseLogQueryList;
	private String count;

	public List<PurchaseLog> getPurchaseLogQueryList() {
		return purchaseLogQueryList;
	}

	public String getCount() {
		return count;
	}

	public void setPurchaseLogQueryList(List<PurchaseLog> purchaseLogQueryList) {
		this.purchaseLogQueryList = purchaseLogQueryList;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "PurchaseLogPage [purchaseLogQueryList=" + purchaseLogQueryList
				+ ", count=" + count + "]";
	}

}
