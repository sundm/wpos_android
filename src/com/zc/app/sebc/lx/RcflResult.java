package com.zc.app.sebc.lx;

public class RcflResult {

	private String status;
	private String cardIssuerId;
	private String pan;
	private String random;
	private String keyIndex;
	private String algoId;
	private String keyVersion;
	private String onlineTradeSeq;
	private String balance;
	private String tradeAmount;
	private String terminalCode;
	private String strMAC1;

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCardIssuerId() {
		return cardIssuerId;
	}

	public void setCardIssuerId(String cardIssuerId) {
		this.cardIssuerId = cardIssuerId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	public String getAlgoId() {
		return algoId;
	}

	public void setAlgoId(String algoId) {
		this.algoId = algoId;
	}

	public String getKeyVersion() {
		return keyVersion;
	}

	public void setKeyVersion(String keyVersion) {
		this.keyVersion = keyVersion;
	}

	public String getOnlineTradeSeq() {
		return onlineTradeSeq;
	}

	public void setOnlineTradeSeq(String onlineTradeSeq) {
		this.onlineTradeSeq = onlineTradeSeq;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getStrMAC1() {
		return strMAC1;
	}

	public void setStrMAC1(String strMAC1) {
		this.strMAC1 = strMAC1;
	}

	@Override
	public String toString() {
		return "PosCreditForLoadRequest [\ncardIssuerId=" + cardIssuerId
				+ ", \npan=" + pan + ", \nrandom=" + random + ", \nkeyIndex="
				+ keyIndex + ", \nalgoId=" + algoId + ", \nkeyVersion="
				+ keyVersion + ", \nonlineTradeSeq=" + onlineTradeSeq
				+ ", \nbalance=" + balance + ", \ntradeAmount=" + tradeAmount
				+ ", \nterminalCode=" + terminalCode + ", \nstrMAC1=" + strMAC1
				+ "\n]";
	}
}
