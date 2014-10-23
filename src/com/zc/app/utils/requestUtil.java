package com.zc.app.utils;

public class requestUtil {
	private String code;
	private Object detail;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "requestUtil [code=" + code + ", detail=" + detail + "]";
	}
}
