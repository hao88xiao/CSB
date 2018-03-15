package com.linkage.bss.crm.ws.order;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;

import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.ws.util.WSUtil;

public class AcctOrderListFactoryImpl implements AcctOrderListFactory {

	private static Log logger = Log.getLog(AcctOrderListFactoryImpl.class);

	private IntfSMO intfSMO;

	private CustFacade custFacade;

	private ICommonSMO commonSMO;

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	public void setCommonSMO(ICommonSMO commonSMO) {
		this.commonSMO = commonSMO;
	}

	@Override
	public JSONObject generateOrderList(Document request) {
		String partyId = WSUtil.getXmlNodeText(request, "/request/partyId");
		String areaCode = WSUtil.getXmlNodeText(request, "/request/areaCode");
		String areaId = WSUtil.getXmlNodeText(request, "/request/areaId");
		String channelId = WSUtil.getXmlNodeText(request, "/request/channelId");
		String staffId = WSUtil.getXmlNodeText(request, "/request/staffId");
		Date date = intfSMO.getCurrentTime();
		String dateStr = DateUtil.getFormatTimeString(date, "yyyy-MM-dd HH:mm:ss");
		String olTypeCd = "2";
		// 根据入参构造购物车的JSON对象
		JSONObject rootObj = new JSONObject();
		JSONObject orderListObj = new JSONObject();
		JSONObject orderListInfoJs = new JSONObject();
		orderListInfoJs.elementOpt("olId", "-1");
		orderListInfoJs.elementOpt("olNbr", "-1");
		orderListInfoJs.elementOpt("olTypeCd", olTypeCd);
		orderListInfoJs.elementOpt("staffId", staffId);
		orderListInfoJs.elementOpt("channelId", channelId);
		orderListInfoJs.elementOpt("areaId", areaId);
		orderListInfoJs.elementOpt("areaCode", areaCode);
		orderListInfoJs.elementOpt("soDate", dateStr);
		orderListInfoJs.elementOpt("statusCd", "S");
		orderListInfoJs.elementOpt("statusDt", dateStr);
		orderListInfoJs.elementOpt("partyId", partyId);
		orderListObj.elementOpt("orderListInfo", orderListInfoJs);
		logger.debug("---------构造orderListInfo 完成！");

		JSONArray custOrderListArr = new JSONArray();
		JSONObject custOrderListJs = new JSONObject();
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator(); // 把业务动作的seq和原子动作的id复位，从-1开始
		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId);
		JSONArray busiOrderArr = new JSONArray();
		String acctId = boSeqCalculator.getNextAtomActionIdString();
		String acctCd = commonSMO.generateSeq(Integer.valueOf(areaId), "ACCOUNT", "2");
		// 创建新增帐户业务动作
		JSONObject createAcctBusiOrder = createAddAccountBusiOrder(partyId, areaId, acctId, acctCd, dateStr,
				boSeqCalculator);
		// 将业务动作集结起来
		busiOrderArr.add(createAcctBusiOrder);
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		custOrderListArr.add(custOrderListJs);
		orderListObj.elementOpt("custOrderList", custOrderListArr);
		rootObj.element("orderList", orderListObj);
		return rootObj;
	}

	/**
	 * 创建新增帐户业务动作
	 * 
	 * @param order
	 * @param areaId
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @param curDate
	 * @return
	 */
	private JSONObject createAddAccountBusiOrder(String partyId, String areaId, String acctId, String acctCd,
			String dateStr, BoSeqCalculator boSeqCalculator) {
		String addAccountBusiOrderTemplate = "{'areaId':0,'data':{'boAccountInfos':[{'partyId':0,'acctName':'','acctCd':'','acctId':-1,'businessPassword':'','prodId':'','state':'ADD','statusCd':'S','atomActionId':-1}],'boPaymentAccounts':[{'paymentAccountId':-1,'paymentAcctTypeCd':1,'bankId':'','bankAcct':'','paymentMan':'','limitQty':'','atomActionId':-2,'state':'ADD','statusCd':'S'}],'boAcct2PaymentAccts':[{'paymentAccountId':-1,'priority':1,'startDate':'','endDate':'3000-01-01','state':'ADD','statusCd':'S','atomActionId':-3}],'boAccountRelas':[{'atomActionId':-4,'acctId':'','chargeItemCd':0,'percent':100,'priority':1,'state':'ADD','statusCd':'S'}]},'boActionType':{'actionClassCd':2,'boActionTypeCd':'A1'},'busiObj':{'instId':-1},'busiOrderInfo':{'seq':-1,'statusCd':'S'}}";
		JSONObject busiOrderJs = JSONObject.fromObject(addAccountBusiOrderTemplate);
		String partyName = null;

		Party party = custFacade.getPartyById(partyId);
		if (party != null) {
			partyName = party.getPartyName();
		} else {
			logger.error("新增帐户查不到客户信息：partyId=" + partyId);
			throw new RuntimeException("新增帐户查不到客户信息：partyId=" + partyId);
		}

		busiOrderJs.getJSONObject("busiOrderInfo").put("seq", boSeqCalculator.getNextSeqString());
		// 数据节点
		busiOrderJs.put("areaId", areaId);
		JSONObject boAccountInfo = busiOrderJs.getJSONObject("data").getJSONArray("boAccountInfos").getJSONObject(0);
		boAccountInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAccountInfo.put("acctId", Long.valueOf(acctId));
		boAccountInfo.put("partyId", partyId);
		boAccountInfo.put("acctName", partyName);
		boAccountInfo.put("acctCd", acctCd);
		logger.debug("填充boAccountInfo完成!");
		JSONObject boPaymentAccounts = busiOrderJs.getJSONObject("data").getJSONArray("boPaymentAccounts")
				.getJSONObject(0);
		boPaymentAccounts.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boPaymentAccounts.put("paymentMan", partyName);
		JSONObject boAcct2PaymentAcct = busiOrderJs.getJSONObject("data").getJSONArray("boAcct2PaymentAccts")
				.getJSONObject(0);
		boAcct2PaymentAcct.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAcct2PaymentAcct.put("startDate", dateStr);
		logger.debug("填充boAcct2PaymentAcct完成!");

		JSONObject boAccountRelaJs = busiOrderJs.getJSONObject("data").getJSONArray("boAccountRelas").getJSONObject(0);
		boAccountRelaJs.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boAccountRelaJs.put("acctId", Long.valueOf(acctId));
		boAccountRelaJs.put("chargeItemCd", "0");
		boAccountRelaJs.put("percent", "100");
		boAccountRelaJs.put("priority", "1");
		boAccountRelaJs.put("state", "ADD");
		boAccountRelaJs.put("statusCd", "S");
		logger.debug("填充boAccountRelas完成!");
		return busiOrderJs;
	}

}
