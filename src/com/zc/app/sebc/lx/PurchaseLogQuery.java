package com.zc.app.sebc.lx;

import java.util.Date;

/**
 * 消费日志查询，考虑日志时间段(三个月？）
 */
public class PurchaseLogQuery {

	/**
	 * 开始时间，格式yyyy-MM-dd
	 */
	private String start;
	/**
	 * 结束时间，格式yyyy-MM-dd
	 */
	private String end;

	private String page = "0";

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "PurchaseLogQuery [start=" + start + ", end=" + end + ", page="
				+ page + "]";
	}

}
