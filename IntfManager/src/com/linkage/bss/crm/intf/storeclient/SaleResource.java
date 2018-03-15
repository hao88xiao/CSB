package com.linkage.bss.crm.intf.storeclient;

public interface SaleResource {

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
