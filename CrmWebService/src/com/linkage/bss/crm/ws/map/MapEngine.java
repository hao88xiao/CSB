package com.linkage.bss.crm.ws.map;

import java.util.Map;

public interface MapEngine {

	/**
	 * 根据模板转换报文
	 * @param templateName
	 * @param model
	 * @return 转换后的报文
	 */
	public String transform(String templateName, Map<String, Object> model);
}
