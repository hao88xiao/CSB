package com.linkage.bss.crm.intf.storeclient;

public interface SaleResource {

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
