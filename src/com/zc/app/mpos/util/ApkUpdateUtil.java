package com.zc.app.mpos.util;

import java.io.Serializable;

public class ApkUpdateUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3657401801169924263L;
	private String version;
	private String description;
	private String path;
	private String md5;

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

	public String getPath() {
		return path;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return "ApkUpdateUtil [version=" + version + ", description="
				+ description + ", path=" + path + ", md5=" + md5 + "]";
	}

	
}
