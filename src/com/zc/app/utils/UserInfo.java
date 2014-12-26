package com.zc.app.utils;

public class UserInfo {
	private String username;
	private String password;
	private String validateCode;
	private String phonenumber;
	private String oldpassword;
	private String newpassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + password
				+ ", validateCode=" + validateCode + ", phonenumber="
				+ phonenumber + ", oldpassword=" + oldpassword
				+ ", newpassword=" + newpassword + "]";
	}

	

}
