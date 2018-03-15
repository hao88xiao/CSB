package com.linkage.bss.crm.intf.facade;

import java.util.List;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;

public interface OfferSpecFacade {

	/**
	 * 根据产品规格查询对应的附属销售品
	 * 
	 * @author 刘志
	 */
	List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Long prodSpecId, Long prodId, String areaId, String staffId,
			String channelId, Long partyId, Long offerSpecId);

}
