package com.linkage.bss.crm.ws.order;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.rule.smo.IRuleSMO;
import com.linkage.bss.crm.so.prepare.smo.ISoPrepareSMO;
import com.linkage.bss.crm.so.save.smo.ISoSaveSMO;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.util.WSUtil;

import net.sf.json.JSONObject;

public class SoServiceValidateCustOrderImpl implements SoServiceValidateCustOrder {

	private static Log log = Log.getLog(SoServiceValidateCustOrderImpl.class);

	// 受理准备服务
	private ISoPrepareSMO soPrepareSMO;

	// 业务动作单保存服务
	private ISoSaveSMO soSaveSMO;

	// 业务规则服务
	private IRuleSMO ruleSMO;

	private IntfSMO intfSMO;

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public IRuleSMO getRuleSMO() {
		return ruleSMO;
	}

	public void setRuleSMO(IRuleSMO ruleSMO) {
		this.ruleSMO = ruleSMO;
	}

	public void setSoPrepareSMO(ISoPrepareSMO soPrepareSMO) {
		this.soPrepareSMO = soPrepareSMO;
	}

	public void setSoSaveSMO(ISoSaveSMO soSaveSMO) {
		this.soSaveSMO = soSaveSMO;
	}

	public String validateCustOrder(JSONObject jsObj) throws Exception {
		Long olId = null;
		try {

			// 第一步校验&生成订单
			log.debug("自动接口入参:{}", jsObj.toString());
			// 1. 初始化保存购物车
			JSONObject idJSON = soSaveSMO.saveOrderList(jsObj);
			log.debug("初始化购物车返回值:{}", idJSON.toString());

			jsObj.put("idJSON", idJSON);
			// 1.1 更新购物车ID
			olId = idJSON.getJSONObject("ORDER_LIST-OL_ID").getLong("-1");
			jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").element("olId", olId);
			String olTypeCd = jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").getString("olTypeCd");
			log.debug("更新购物车ID:{},购物车类型:{}", olId, olTypeCd);
			// 1.2规则校验
			String ruleXml = soPrepareSMO.checkRule(jsObj);
			log.debug("规则校验结果：{}", ruleXml);
			// 3. 购物车提交
			log.debug("调用购物车提交业务规则。。。");
			String commitXml = ruleSMO.checkRuleCartCommit(olId);
			if (!isMatchSuccess(commitXml)) {
				intfSMO.cancelOrderInfo(olId);
			}
			ruleSMO.updateBusiOrderAtomActionByOlId(olId, "P");
			return commitXml;
		} catch (Exception e) {
			WSUtil.logError("validateCustOrder", jsObj.toString(), e);
			if (olId != null) {
				intfSMO.cancelOrderInfo(olId);
			}
			return e.getMessage();
		}
	}

	/**
	 * 校验是否成功，true表示成功; false表示失败
	 * 
	 * @param str
	 * @return
	 */
	@WebMethod(exclude = true)
	public boolean isMatchSuccess(String str) {
		String regEx = "<result>0000</result>|<result>0</result>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

}
