package com.linkage.bss.crm.ws.service;

import com.linkage.bss.crm.ws.map.MapEngine;

public abstract class AbstractService {

	/** ����ת������ */
	protected MapEngine mapEngine;

	public void setMapEngine(MapEngine mapEngine) {
		this.mapEngine = mapEngine;
	}
}
