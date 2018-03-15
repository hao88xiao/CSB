package com.linkage.bss.crm.intf.model;

import java.util.List;

public class BcdCodeEntityInputBean {

	
	private String materialId;
	
	private List<String> storeIds;
	
	private String count;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public List<String> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	
	
}
