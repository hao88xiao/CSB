package com.linkage.bss.crm.intf.model;

import java.util.List;
import java.util.Map;

public class StaticData {

	private String sqlId;

	private List<Map<String, Object>> ele;

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public List<Map<String, Object>> getEle() {
		return ele;
	}

	public void setEle(List<Map<String, Object>> ele) {
		this.ele = ele;
	}

}
