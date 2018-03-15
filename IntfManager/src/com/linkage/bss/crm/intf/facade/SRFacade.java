package com.linkage.bss.crm.intf.facade;

import java.util.Map;


public interface SRFacade {
	/**
	 *  �мۿ���Ʒ����ȷ�Ͻӿ�
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String checkSequence(Map<String, String> map) throws Exception;

	/**
	 * Ӫ����ԴУ��ӿ�
	 * bcdCode ����
	 * typeId	��Ʒ���ͣ�1��ʾ�ֻ� 2 ��ʾ��ȯ
	 * pw ���룬��Ʒ����Ϊ1ʱ������֤���룬������д��Ϊ2ʱ����д��bcdCode��Ӧ������
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
