package com.linkage.bss.crm.intf.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ServActivate implements Serializable {

	/** 流水号 */
	private Long sequence;

	/** 用户标识 */
	private Long servId;

	/** 主接入号码 */
	private String accNbr;

	/** 付费方式 */
	private Integer billingMode;

	/** 激活类型 */
	private String activateType;

	/** 激活时间 */
	private Date activateDate;

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Long getServId() {
		return servId;
	}

	public void setServId(Long servId) {
		this.servId = servId;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public Integer getBillingMode() {
		return billingMode;
	}

	public void setBillingMode(Integer billingMode) {
		this.billingMode = billingMode;
	}

	public String getActivateType() {
		return activateType;
	}

	public void setActivateType(String activateType) {
		this.activateType = activateType;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}
}
