package com.linkage.bss.crm.intf.storeclient;

import java.util.Map;

public interface StoreForOnLineMall {

	public String doStoreInOutSO (String xmlInfo) throws java.rmi.RemoteException;
	
	public String identifyValidateCode(String bcdCode, String password);
	
	public String validateCodeCoupons(String bcdCode,String typeId,String pw);
	
	/**
	 * ͳһ֧���շѳɹ��ص��ӿ�
	 * @param map
	 * @return
	 */ 
	public Map<String, String> callBackUnityPayData(Map<String, String> map) ;
	
	/**
	 * ���շ���ͬ���ӿ�
	 * @param xml
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String doUnityPay (String xml) throws java.rmi.RemoteException;

	/**
	 * �������κŵ���Ӫ����Դ�ӿڣ����ش����κ��Ƿ���ڡ�
	 * @param batchId
	 * @return
	 */
	public String queryBatchId(String batchId);

	/**
	 * ���ݳ�ֵ���ŵ�Ӫ����ԴУ���Ƿ���ۺͼ���״̬��
	 * @param cardNo
	 * @return
	 */
	public String checkValueCard(String cardNo);
}
