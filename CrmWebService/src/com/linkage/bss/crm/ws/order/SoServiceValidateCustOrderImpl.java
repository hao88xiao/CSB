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

	// ����׼������
	private ISoPrepareSMO soPrepareSMO;

	// ҵ�������������
	private ISoSaveSMO soSaveSMO;

	// ҵ��������
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

			// ��һ��У��&���ɶ���
			log.debug("�Զ��ӿ����:{}", jsObj.toString());
			// 1. ��ʼ�����湺�ﳵ
			JSONObject idJSON = soSaveSMO.saveOrderList(jsObj);
			log.debug("��ʼ�����ﳵ����ֵ:{}", idJSON.toString());

			jsObj.put("idJSON", idJSON);
			// 1.1 ���¹��ﳵID
			olId = idJSON.getJSONObject("ORDER_LIST-OL_ID").getLong("-1");
			jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").element("olId", olId);
			String olTypeCd = jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").getString("olTypeCd");
			log.debug("���¹��ﳵID:{},���ﳵ����:{}", olId, olTypeCd);
			// 1.2����У��
			String ruleXml = soPrepareSMO.checkRule(jsObj);
			log.debug("����У������{}", ruleXml);
			// 3. ���ﳵ�ύ
			log.debug("���ù��ﳵ�ύҵ����򡣡���");
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
	 * У���Ƿ�ɹ���true��ʾ�ɹ�; false��ʾʧ��
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
