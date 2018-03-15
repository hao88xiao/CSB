package com.linkage.bss.crm.intf.facade;

import java.util.Map;


public interface SRFacade {
	/**
	 *  有价卡礼品受理确认接口
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String checkSequence(Map<String, String> map) throws Exception;

	/**
	 * 营销资源校验接口
	 * bcdCode 串码
	 * typeId	物品类型，1表示手机 2 表示礼券
	 * pw 密码，物品类型为1时，不验证密码，任意填写；为2时，填写与bcdCode对应的密码
	 * @return
	 * @throws Exception 
	 */
	public String validateCodeCoupons (String bcdCode, String typeId, String pw) throws Exception;

	public String getMaterialByCode(String materialId, String bcdCode)throws Exception;
	
	public void getMaterialByCode()throws Exception;
	
	public void checkInOut()throws Exception;
	
	public String getUnitySeq();

	public String queryBatchId(String batchId);

	public String checkValueCard(String cardNo);

	public String doStoreInOutCrm(String request) throws Exception;
	
}
