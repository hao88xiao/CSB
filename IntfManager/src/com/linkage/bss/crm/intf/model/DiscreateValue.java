package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

public class DiscreateValue implements Serializable {

	private static final long serialVersionUID = 1L;
	private String discreateValueName;
	private String discreateValue;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscreateValueName() {
		return discreateValueName;
	}

	public void setDiscreateValueName(String discreateValueName) {
		this.discreateValueName = discreateValueName;
	}

	public String getDiscreateValue() {
		return discreateValue;
	}

	public void setDiscreateValue(String discreateValue) {
		this.discreateValue = discreateValue;
	}
}
