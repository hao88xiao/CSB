package com.linkage.bss.crm.intf.facade;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.linkage.bss.crm.model.CfgRule;
import com.linkage.bss.crm.so.store.smo.ISoStoreSMO;

public class SoFacadeImpl implements SoFacade {

	private ISoStoreSMO soStoreSMO;

	public void setSoStoreSMO(ISoStoreSMO soStoreSMO) {
		this.soStoreSMO = soStoreSMO;
	}

	@Override
	public List<CfgRule> queryCouponCfgRuleByOfferInfo(Long offerSpecId) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("offerSpecId", offerSpecId);
		jsonObj.put("offerRoleId", 8733327L);
		jsonObj.put("objType", 2);
		jsonObj.put("objId", 378L);
		JSONArray ja = soStoreSMO.queryCouponCfgRuleByOfferInfo(jsonObj);
		List<CfgRule> cfgRuleList = new ArrayList<CfgRule>();
		for (int i = 0; i < ja.size(); i++) {
			CfgRule cr = new CfgRule();
			cr.setCfgRuleId(ja.getJSONObject(i).getLong("cfgRuleId"));
			cr.setName(ja.getJSONObject(i).getString("name"));
			cfgRuleList.add(cr);
		}
		return cfgRuleList;
	}

	@Override
	public Integer existsByStoreIdAndChannelId(Long channelId, Long storeId) {
		return soStoreSMO.existsByStoreIdAndChannelId(channelId, storeId);
	}

}
