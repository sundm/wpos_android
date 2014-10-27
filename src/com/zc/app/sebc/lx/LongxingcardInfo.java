package com.zc.app.sebc.lx;

public class LongxingcardInfo {

	private String status;
	private String balance;
	private String cardNumber;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBalance() {
		return balance;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getFloatBalance(){
		String baString = this.balance;
		String endString = "." + baString.substring(baString.length() - 2);
		StringBuffer buffer = new StringBuffer();
		buffer.append(baString.substring(0, baString.length() - 2));
		buffer.append(endString);
		return buffer.toString();	
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "LongxingcardInfo [status=" + status + ", balance=" + balance
				+ ", cardNumber=" + cardNumber + "]";
	}
	
	
}
