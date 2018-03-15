package com.linkage.bss.crm.ws.order;

import net.sf.json.JSONObject;

import org.dom4j.Document;

public interface OrderListFactory {

	/**
	 * ��װ�ӿڱ���ƴװ
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject generateOrderList(Document doc) throws Exception;
	
	/**
	 * ��Ʒ���Ա������ƴװ
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject offerProdItemChangeMsg(Document doc) throws Exception;

	/**
	 * У԰��Ϣ�޸�
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject changeInfoForschool(Document doc) throws Exception;
	/**
	 * ��������ƴװ
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public JSONObject transferForschool(Document doc) throws Exception;

	/**
	 * �޸Ĳ�Ʒ����
	 * @param document
	 * @return
	 */
	public JSONObject changespecInfoForschool(Document document);

	/**
	 * �˶�����
	 * @param document
	 * @return
	 */
	public JSONObject unsubscribeService(Document document);
}
