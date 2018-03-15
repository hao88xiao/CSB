/**
 * 
 */
package com.linkage.bss.crm.ws.order;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;

/**
 * ����Domתjson�����ӿ���
 * @author hell
 * @version 1.0 
 * 2013/07/09
 */
public interface CreateTransferOwnerListFactory {

	public JSONObject generateTransferOwner(Document doc) throws Exception;
	public JSONObject generateTransferOwnerDh(Document doc) throws Exception;

	public JSONObject generateCpsxBg(Document doc) throws Exception;

	public JSONObject stringToJsonObj(Map<String, String> inMap);
}
