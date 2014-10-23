/**
 * 
 */
package com.zc.app.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * 保存POS信息
 * 
 * @author whj
 * 
 */

public class PosInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773594589468033054L;

	private Long id;

	/**
	 * Pos存入数据库信息，使用数据库默认值
	 */
	private Date createTime;

	/**
	 * 商户号
	 */
	private String merchantId;

	/**
	 * 终端号（开通时输入）
	 */
	private String terminalId;

	/**
	 * 终端名称
	 */
	private String terminalName;

	/**
	 * 终端序列号（开通时输入）
	 */
	private String terminalSeq;

	/**
	 * 终端机编号（PSAM卡属性，需自己配置，用于算MAC)，自己生成、管理
	 */
	private String psamId;

	/**
	 * 硬件序列号,自己生成，导入tsm
	 */
	private String hardwareSerial;

	/**
	 * 批次号，需要计算,最大6位
	 */
	private Long batchNo;

	/**
	 * 流水号，需要计算，最大6位,和POSP交易用
	 */
	private Long serialNo;

	/**
	 * 终端交易流水号，以卡片脱机消费交易终端交易序号为准,和卡交易用
	 */
	private Long psamSerialNo;

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
	 * @return the terminalName
	 */
	public String getTerminalName() {
		return terminalName;
	}

	/**
	 * @param terminalName
	 *            the terminalName to set
	 */
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	/**
	 * @return the terminalSeq
	 */
	public String getTerminalSeq() {
		return terminalSeq;
	}

	/**
	 * @param terminalSeq
	 *            the terminalSeq to set
	 */
	public void setTerminalSeq(String terminalSeq) {
		this.terminalSeq = terminalSeq;
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
	 * @return the hardwareSerial
	 */
	public String getHardwareSerial() {
		return hardwareSerial;
	}

	/**
	 * @param hardwareSerial
	 *            the hardwareSerial to set
	 */
	public void setHardwareSerial(String hardwareSerial) {
		this.hardwareSerial = hardwareSerial;
	}

	/**
	 * @return the batchNo
	 */
	public Long getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 *            the batchNo to set
	 */
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the serialNo
	 */
	public Long getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PosInfo [id=");
		builder.append(id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", merchantId=");
		builder.append(merchantId);
		builder.append(", terminalId=");
		builder.append(terminalId);
		builder.append(", terminalName=");
		builder.append(terminalName);
		builder.append(", terminalSeq=");
		builder.append(terminalSeq);
		builder.append(", psamId=");
		builder.append(psamId);
		builder.append(", hardwareSerial=");
		builder.append(hardwareSerial);
		builder.append(", batchNo=");
		builder.append(batchNo);
		builder.append(", serialNo=");
		builder.append(serialNo);
		builder.append(", psamSerialNo=");
		builder.append(psamSerialNo);
		builder.append("]");
		return builder.toString();
	}

}
