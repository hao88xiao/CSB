package com.linkage.bss.crm.ws.order;

import net.sf.json.JSONObject;

import org.dom4j.Document;

public interface AcctOrderListFactory {

	public JSONObject generateOrderList(Document doc);

}
