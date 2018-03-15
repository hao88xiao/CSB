package com.linkage.bss.crm.intf.storeclient;

import java.util.Map;

public interface StoreForOnLineMall {

	public String doStoreInOutSO (String xmlInfo) throws java.rmi.RemoteException;
	
	public String identifyValidateCode(String bcdCode, String password);
	
	public String validateCodeCoupons(String bcdCode,String typeId,String pw);
	
	/**
	 * 统一支付收费成功回调接口
	 * @param map
	 * @return
	 */ 
	public Map<String, String> callBackUnityPayData(Map<String, String> map) ;
	
	/**
	 * 已收费用同步接口
	 * @param xml
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String doUnityPay (String xml) throws java.rmi.RemoteException;

	/**
	 * 根据批次号调用营销资源接口，返回此批次号是否存在。
	 * @param batchId
	 * @return
	 */
	public String queryBatchId(String batchId);

	/**
	 * 根据充值卡号到营销资源校验是否可售和激活状态。
	 * @param cardNo
	 * @return
	 */
	public String checkValueCard(String cardNo);
}
