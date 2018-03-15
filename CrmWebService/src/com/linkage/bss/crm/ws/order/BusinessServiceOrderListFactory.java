package com.linkage.bss.crm.ws.order;

import net.sf.json.JSONObject;

import org.dom4j.Document;

public interface BusinessServiceOrderListFactory {

	public JSONObject generateOrderList(Document doc) throws Exception;

}
