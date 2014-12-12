package com.zc.app.mpos.adapter;

public class LogDateItem {
	private String date;
	private String counter;

	public String getDate() {
		return date;
	}

	public String getCounter() {
		return counter;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "LogDateItem [date=" + date + ", counter=" + counter + "]";
	}

}
