package com.linkage.bss.crm.intf.facade;

import java.util.List;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;

public interface OfferSpecFacade {

	/**
	 * ���ݲ�Ʒ����ѯ��Ӧ�ĸ�������Ʒ
	 * 
	 * @author ��־
	 */
	List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Long prodSpecId, Long prodId, String areaId, String staffId,
			String channelId, Long partyId, Long offerSpecId);

}
