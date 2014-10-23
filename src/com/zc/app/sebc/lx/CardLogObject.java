package com.zc.app.sebc.lx;

import java.io.Serializable;
import java.util.List;

public class CardLogObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1890194763170295077L;

	private String status;
	private List<CardLogEntry> details;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CardLogEntry> getDetails() {
		return details;
	}

	public void setDetails(List<CardLogEntry> details) {
		this.details = details;
	}

}
