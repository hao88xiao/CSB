package com.linkage.bss.crm.intf.model;

public class YKSXInfo {

	private Long prodId;

	private Integer roleCd;

	public Integer getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(Integer roleCd) {
		if (roleCd != null) {
			this.roleCd = roleCd;
		} else {
			this.roleCd = 0;
		}
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Long getProdId() {
		return prodId;
	}

}
