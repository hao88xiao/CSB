package com.linkage.bss.crm.intf.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ServActivatePps implements Serializable {

	/** 用户标识 */
	private Long servId;

	/** 销售品规格对应的code */
	private String manageCode;

	/** 销售品生效 */
	private Date startDate;

	/** 销售品失效时间 */
	private Date endDate;

	public Long getServId() {
		return servId;
	}

	public void setServId(Long servId) {
		this.servId = servId;
	}

	public String getManageCode() {
		return manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
