package com.zc.app.sebc.lx;

import java.util.Date;

/**
 * 消费日志，用于记录消费信息
 * 
 */

public class PurchaseLog {
	
	private Long id;

	private Long userId;
	/**
	 * 用于对应POS信息
	 */
	private Long posId;
	/**
	 * 日志创建时间，系统自动生成
	 */
	private Date createTime;

	/**
	 * 消费状态，是否成功、失败等信息，失败的记录不需要结算
	 */
	
	private PurchaseStatus purchaseStatus;

	/**
	 * 是否已经结算，自动结算时统计
	 */
	private boolean settled;

	/* =========================START========================= */

	/**
	 * 1字节，密钥索引号
	 */
	private String keyIndex;
	/**
	 * 6字节，终端机编号
	 */
	private String psamId;// psamId

	/* =========================INIT========================= */

	/**
	 * 经度，精确到6位
	 */
	private String lng;
	/**
	 * 纬度，精确到6位
	 */
	private String lat;

	/**
	 * 消费金额，6字节
	 */
	private Long amount;
	/**
	 * 交易前余额，6个字节
	 */
	private Long balance;
	/**
	 * 伪随机数，4字节
	 */
	private String icRandom;
	/**
	 * 脱机交易序号，2字节
	 */
	private String offlineAtc;
	/**
	 * 密钥版本号，1字节
	 */
	private String keyVersion;
	/**
	 * 算法标识， 1字节
	 */
	private String algId;
	/**
	 * 交易卡号，龙行卡卡号16字符
	 */
	private String pan;
	/**
	 * 发卡方标识，8个字节
	 */
	private String issuerId;

	/**
	 * 终端交易序号,PosInfo信息
	 */
	private Long psamSerialNo;
	/**
	 * 日期：yyyyMMdd
	 */
	private String posDate;
	/**
	 * 时间：hhmmss
	 */
	private String posTime;
	/**
	 * 4字节
	 */
	private String mac1;
	/**
	 * 4字节
	 */
	private String mac2;

	/**
	 * 交易类型标识
	 */
	private String transactionType;

	/**
	 * 商户号
	 */
	private String merchantId;

	/**
	 * 终端号（开通时输入）
	 */
	private String terminalId;

	/* =========================PURCHASE========================= */

	/**
	 * 卡片消费返回值，成功为9000
	 */
	private String sw;
	/**
	 * 消费TAC，长度为4字节， 若未上送则全为0
	 */
	private String tac;

	/**
	 * 交易后的余额
	 */
	private Long walletBalanceLater;

	/**
	 * mac2验证状态 通过为true
	 */
	// @Column(name = "mac_state")
	// private boolean macState;

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId
	 *            the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the posId
	 */
	public Long getPosId() {
		return posId;
	}

	/**
	 * @param posId
	 *            the posId to set
	 */
	public void setPosId(Long posId) {
		this.posId = posId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the purchaseStatus
	 */
	public PurchaseStatus getPurchaseStatus() {
		return purchaseStatus;
	}

	/**
	 * @param purchaseStatus
	 *            the purchaseStatus to set
	 */
	public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	/**
	 * @return the settled
	 */
	public boolean isSettled() {
		return settled;
	}

	/**
	 * @param settled
	 *            the settled to set
	 */
	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	/**
	 * @return the keyIndex
	 */
	public String getKeyIndex() {
		return keyIndex;
	}

	/**
	 * @param keyIndex
	 *            the keyIndex to set
	 */
	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * @return the psamId
	 */
	public String getPsamId() {
		return psamId;
	}

	/**
	 * @param psamId
	 *            the psamId to set
	 */
	public void setPsamId(String psamId) {
		this.psamId = psamId;
	}

	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the balance
	 */
	public Long getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}

	/**
	 * @return the icRandom
	 */
	public String getIcRandom() {
		return icRandom;
	}

	/**
	 * @param icRandom
	 *            the icRandom to set
	 */
	public void setIcRandom(String icRandom) {
		this.icRandom = icRandom;
	}

	/**
	 * @return the offlineAtc
	 */
	public String getOfflineAtc() {
		return offlineAtc;
	}

	/**
	 * @param offlineAtc
	 *            the offlineAtc to set
	 */
	public void setOfflineAtc(String offlineAtc) {
		this.offlineAtc = offlineAtc;
	}

	/**
	 * @return the keyVersion
	 */
	public String getKeyVersion() {
		return keyVersion;
	}

	/**
	 * @param keyVersion
	 *            the keyVersion to set
	 */
	public void setKeyVersion(String keyVersion) {
		this.keyVersion = keyVersion;
	}

	/**
	 * @return the algId
	 */
	public String getAlgId() {
		return algId;
	}

	/**
	 * @param algId
	 *            the algId to set
	 */
	public void setAlgId(String algId) {
		this.algId = algId;
	}

	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @param pan
	 *            the pan to set
	 */
	public void setPan(String pan) {
		this.pan = pan;
	}

	/**
	 * @return the issuerId
	 */
	public String getIssuerId() {
		return issuerId;
	}

	/**
	 * @param issuerId
	 *            the issuerId to set
	 */
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	/**
	 * @return the psamSerialNo
	 */
	public Long getPsamSerialNo() {
		return psamSerialNo;
	}

	/**
	 * @param psamSerialNo
	 *            the psamSerialNo to set
	 */
	public void setPsamSerialNo(Long psamSerialNo) {
		this.psamSerialNo = psamSerialNo;
	}

	/**
	 * @return the posDate
	 */
	public String getPosDate() {
		return posDate;
	}

	/**
	 * @param posDate
	 *            the posDate to set
	 */
	public void setPosDate(String posDate) {
		this.posDate = posDate;
	}

	/**
	 * @return the posTime
	 */
	public String getPosTime() {
		return posTime;
	}

	/**
	 * @param posTime
	 *            the posTime to set
	 */
	public void setPosTime(String posTime) {
		this.posTime = posTime;
	}

	/**
	 * @return the mac1
	 */
	public String getMac1() {
		return mac1;
	}

	/**
	 * @param mac1
	 *            the mac1 to set
	 */
	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}

	/**
	 * @return the mac2
	 */
	public String getMac2() {
		return mac2;
	}

	/**
	 * @param mac2
	 *            the mac2 to set
	 */
	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the sw
	 */
	public String getSw() {
		return sw;
	}

	/**
	 * @param sw
	 *            the sw to set
	 */
	public void setSw(String sw) {
		this.sw = sw;
	}

	/**
	 * @return the tac
	 */
	public String getTac() {
		return tac;
	}

	/**
	 * @param tac
	 *            the tac to set
	 */
	public void setTac(String tac) {
		this.tac = tac;
	}

	/**
	 * @return the walletBalanceLater
	 */
	public Long getWalletBalanceLater() {
		return walletBalanceLater;
	}

	/**
	 * @param walletBalanceLater
	 *            the walletBalanceLater to set
	 */
	public void setWalletBalanceLater(Long walletBalanceLater) {
		this.walletBalanceLater = walletBalanceLater;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseLog [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", posId=");
		builder.append(posId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", purchaseStatus=");
		builder.append(purchaseStatus);
		builder.append(", settled=");
		builder.append(settled);
		builder.append(", keyIndex=");
		builder.append(keyIndex);
		builder.append(", psamId=");
		builder.append(psamId);
		builder.append(", lng=");
		builder.append(lng);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", icRandom=");
		builder.append(icRandom);
		builder.append(", offlineAtc=");
		builder.append(offlineAtc);
		builder.append(", keyVersion=");
		builder.append(keyVersion);
		builder.append(", algId=");
		builder.append(algId);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", issuerId=");
		builder.append(issuerId);
		builder.append(", psamSerialNo=");
		builder.append(psamSerialNo);
		builder.append(", posDate=");
		builder.append(posDate);
		builder.append(", posTime=");
		builder.append(posTime);
		builder.append(", mac1=");
		builder.append(mac1);
		builder.append(", mac2=");
		builder.append(mac2);
		builder.append(", transactionType=");
		builder.append(transactionType);
		builder.append(", merchantId=");
		builder.append(merchantId);
		builder.append(", terminalId=");
		builder.append(terminalId);
		builder.append(", sw=");
		builder.append(sw);
		builder.append(", tac=");
		builder.append(tac);
		builder.append(", walletBalanceLater=");
		builder.append(walletBalanceLater);
		builder.append("]");
		return builder.toString();
	}
}
