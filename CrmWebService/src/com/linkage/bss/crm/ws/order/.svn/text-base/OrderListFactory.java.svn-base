package com.linkage.bss.crm.ws.order;

import net.sf.json.JSONObject;

import org.dom4j.Document;

public interface OrderListFactory {

	/**
	 * 新装接口报文拼装
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject generateOrderList(Document doc) throws Exception;
	
	/**
	 * 产品属性变更报文拼装
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject offerProdItemChangeMsg(Document doc) throws Exception;

	/**
	 * 校园信息修改
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject changeInfoForschool(Document doc) throws Exception;
	/**
	 * 过户报文拼装
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject transferForschool(Document doc) throws Exception;

	/**
	 * 修改产品属性
	 * @param document
	 * @return
	 */
	public JSONObject changespecInfoForschool(Document document);

	/**
	 * 退订服务
	 * @param document
	 * @return
	 */
	public JSONObject unsubscribeService(Document document);
}
