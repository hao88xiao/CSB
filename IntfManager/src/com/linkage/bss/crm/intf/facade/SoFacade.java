package com.linkage.bss.crm.intf.facade;

import java.util.List;

import com.linkage.bss.crm.model.CfgRule;

public interface SoFacade {
	/**
	 * ��ѯ��Ʒ����
	 * 
	 * @param req
	 * @param rsp
	 */
	public List<CfgRule>  queryCouponCfgRuleByOfferInfo(Long offerSpecId);
	
	/**
	 * �ֿ���������Ӧ��ϵ
	 * @param channelId
	 * @param storeId
	 * @return
	 * @author ZHANGC
	 */
	public Integer existsByStoreIdAndChannelId(Long channelId, Long storeId);

}
