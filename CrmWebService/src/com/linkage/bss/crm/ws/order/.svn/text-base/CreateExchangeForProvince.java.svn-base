package com.linkage.bss.crm.ws.order;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;


public interface CreateExchangeForProvince {

	
	public JSONObject generateQueryUIMList(String terminalCode);

	public Map<String, Object> getExchangeForProvinceResponse(String uimReJsStr);

	public Map<String, Object> getExchangeForProvinceMkt(String uimReJsStr);

	public JSONObject generateCardPreholDingJson(Map<String, Object> mkt);

	public JSONObject generateCustOrderJson(Map<String, Object> custResMap);
	
	public String  generateTimeSeq(int num);
	
	public JSONObject generateOrderJson(Map<String, Object> custResMap);
	
	public JSONObject generateOrderJson2(Map<String, Object> changeCardMap);
	public JSONObject getFeeInfoByCustOrderId(Map<String, Object> changeCardMap);
	public JSONObject getUpdateFeeJsonByFeeInfo(String jsonString,Map<String,Object> paraMap);

	public String getSendStringXML(Map<String, Object> parmMap);
	public Map<String,String> getOfferInfoXML(Map<String, Object> parmMap);
	
}
