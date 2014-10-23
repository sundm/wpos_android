package com.zc.app.utils;

public class requestLoginUtil {
	private String username;
	private String nickname;
	private String phoneNumber;
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "requestLoginUtil [username=" + username + ", nickname="
				+ nickname + ", phoneNumber=" + phoneNumber + ", role=" + role
				+ "]";
	}

}
