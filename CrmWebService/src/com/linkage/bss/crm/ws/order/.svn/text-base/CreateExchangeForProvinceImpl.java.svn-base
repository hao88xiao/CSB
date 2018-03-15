package com.linkage.bss.crm.ws.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.commons.util.StringUtil;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.offerspec.smo.IOfferSpecSMO;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.store.smo.ISoStoreSMO;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.util.WSUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreateExchangeForProvinceImpl implements CreateExchangeForProvince {

	private static Log logger = Log.getLog(CreateExchangeForProvinceImpl.class);

	private IntfSMO intfSMO;

	private IOfferSpecSMO offerSpecSMO;

	private IOfferSMO offerSMO;

	private RscServiceSMO rscServiceSMO;

	private CustFacade custFacade;

	private ISoStoreSMO soStoreSMO;
	
	public IntfSMO getIntfSMO() {
		return intfSMO;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public IOfferSpecSMO getOfferSpecSMO() {
		return offerSpecSMO;
	}

	public void setOfferSpecSMO(IOfferSpecSMO offerSpecSMO) {
		this.offerSpecSMO = offerSpecSMO;
	}

	public IOfferSMO getOfferSMO() {
		return offerSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	public RscServiceSMO getRscServiceSMO() {
		return rscServiceSMO;
	}

	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	public CustFacade getCustFacade() {
		return custFacade;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	public ISoStoreSMO getSoStoreSMO() {
		return soStoreSMO;
	}

	public void setSoStoreSMO(ISoStoreSMO soStoreSMO) {
		this.soStoreSMO = soStoreSMO;
	}

	@Override
	public JSONObject generateQueryUIMList(String terminalCode) {
		
		//String transactionID  = intfSMO.getIntfCommonSeq();
		
		JSONObject attrInfoJs = new JSONObject();
		JSONObject attrInfoJs1 = new JSONObject();
		JSONObject attrInfoJs2 = new JSONObject();
		JSONObject attrInfoJs3 = new JSONObject();
		JSONArray attrArrJs = new JSONArray();
		attrInfoJs.elementOpt("VALUE", "60020001");
		attrArrJs.add(attrInfoJs);
		attrInfoJs1.elementOpt("VALUE", "60020002");
		attrArrJs.add(attrInfoJs1);
		attrInfoJs2.elementOpt("VALUE", "60020003");
		attrArrJs.add(attrInfoJs2);
		attrInfoJs3.elementOpt("VALUE", "60020004");
		attrArrJs.add(attrInfoJs3);
		
		JSONObject fieldInfoJs = new JSONObject();
		JSONObject fieldInfoJs1 = new JSONObject();
		JSONObject fieldInfoJs2 = new JSONObject();
		JSONObject fieldInfoJs3 = new JSONObject();
		JSONObject fieldInfoJs4 = new JSONObject();
		JSONObject fieldInfoJs5 = new JSONObject();
		JSONObject fieldInfoJs6 = new JSONObject();
		JSONArray fieldArrJs = new JSONArray();
		fieldInfoJs.elementOpt("VALUE", "MKT_RES_INST_ID");
		fieldArrJs.add(fieldInfoJs);
		fieldInfoJs.elementOpt("VALUE", "MKT_RES_STORE_ID");
		fieldArrJs.add(fieldInfoJs1);
		fieldInfoJs1.elementOpt("VALUE", "MKT_RES_TYPE");
		fieldArrJs.add(fieldInfoJs2);
		fieldInfoJs2.elementOpt("VALUE", "MKT_RES_CD");
		fieldArrJs.add(fieldInfoJs2);
		fieldInfoJs3.elementOpt("VALUE", "MKT_RES_INST_CODE");
		fieldArrJs.add(fieldInfoJs3);
		fieldInfoJs4.elementOpt("VALUE", "SALES_PRICE");
		fieldArrJs.add(fieldInfoJs4);
		fieldInfoJs5.elementOpt("VALUE", "STATUS_CD");
		fieldArrJs.add(fieldInfoJs5);
		fieldInfoJs6.elementOpt("VALUE", "STATUS_DATE");
		fieldArrJs.add(fieldInfoJs6);
		
		JSONObject tcpContJs = new JSONObject();
		tcpContJs.elementOpt("TransactionID", "99"+generateTimeSeq(1));
		tcpContJs.elementOpt("SrvcInstId", "SRVCINST80049");
		tcpContJs.elementOpt("SrcSysID", "BJ00000006");
		
		JSONArray sooArrJs = new JSONArray();
		JSONObject sooJs = new JSONObject();
		JSONObject attributesJs = new JSONObject();
		attributesJs.elementOpt("type", "QRY_MKT_RES_INST_REQ_TYPE");
		sooJs.elementOpt("@attributes",attributesJs);
		sooJs.elementOpt("LAN_ID",8110000);
		sooJs.elementOpt("MKT_RES_INST_CODE",terminalCode);
		sooJs.elementOpt("FIELD",fieldArrJs);
		sooJs.elementOpt("ATTR",attrArrJs);
		sooArrJs.add(sooJs);
		JSONObject svcContJs = new JSONObject();
		svcContJs.elementOpt("SOO", sooArrJs);
		JSONObject contractRootJs = new JSONObject();
		contractRootJs.elementOpt("TcpCont", tcpContJs);
		contractRootJs.elementOpt("SvcCont", svcContJs);
		JSONObject jsonObject = new JSONObject();
		jsonObject.elementOpt("ContractRoot", contractRootJs);
		return jsonObject;
	}
	
	@Override
	public Map getExchangeForProvinceResponse(String uimReJsStr) {
		Map  map=new HashMap();
		
	    try {
			JSONObject uimReJson =  JSONObject.fromObject(uimReJsStr);
			JSONObject  responsejs=uimReJson.getJSONObject("ContractRoot").getJSONObject("TcpCont").getJSONObject("Response");
			map.put("RspCode", responsejs.getString("RspCode"));
			map.put("RspDesc", responsejs.getString("RspDesc"));
		} catch (Exception e) {
			map.put("RspCode", WSDomain.ExchangeForProvinceCode.EXCHANGE_ERROR);
			map.put("RspDesc", uimReJsStr);
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map getExchangeForProvinceMkt(String uimReJsStr) {
		 Map result = null;
		try {
			JSONObject uimReJson =  JSONObject.fromObject(uimReJsStr);
			JSONObject  mktjs=uimReJson.getJSONObject("ContractRoot")
			.getJSONObject("SvcCont").getJSONArray("SOO").getJSONObject(0).getJSONArray("MKT_RES_INST").getJSONObject(0);
			 //mkt=(ExchangeForProvinceMkt)JSONObject.toBean(mktjs,ExchangeForProvinceMkt.class);
			result=jsonToMap(mktjs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public  Map jsonToMap(JSONObject json)  {

        Map result = new HashMap();
        Iterator iterator = json.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = json.getString(key);
            result.put(key, value);
        }
        return result;
    }

	@Override
	public JSONObject generateCardPreholDingJson(Map mkt) {
		//String transactionID  = intfSMO.getIntfCommonSeq();
		
		JSONArray attrArrJs = new JSONArray();
		JSONObject attrJs = new JSONObject();
		JSONObject attributesJs = new JSONObject();
		attrJs.elementOpt("VALUE", mkt.get("accNbr"));
		attributesJs.elementOpt("CD", "60020009");
		attrJs.elementOpt("@attributes", attributesJs);
		
		attrArrJs.add(attrJs);
		
		JSONObject mktinstJs = new JSONObject();
		JSONArray mktinstarrJs = new JSONArray();
		mktinstJs.elementOpt("STATUS_CD","1102");
		mktinstJs.elementOpt("LAN_ID", mkt.get("MKT_RES_STORE_ID"));
		mktinstJs.elementOpt("MKT_RES_TYPE", mkt.get("MKT_RES_CD"));
		mktinstJs.elementOpt("MKT_RES_INST_CODE", mkt.get("MKT_RES_INST_CODE"));
		mktinstJs.elementOpt("STATUS_DATE", generateTimeSeq(0));
		mktinstJs.elementOpt("CHANNEL_NBR", mkt.get("CHANNEL_NBR"));
		mktinstJs.elementOpt("STAFF_CODE", mkt.get("staffCode"));
		mktinstJs.elementOpt("ATTR", attrArrJs);
		
		mktinstarrJs.add(mktinstJs);
		
		JSONObject sooJs = new JSONObject();
		JSONObject typeJs = new JSONObject();
		JSONArray sooarrJs = new JSONArray();
		typeJs.elementOpt("type", "MOD_MKT_RES_INST_REQ_TYPE");
		sooJs.elementOpt("@attributes",typeJs);
		sooJs.elementOpt("MKT_RES_INST", mktinstarrJs);
		sooarrJs.add(sooJs);
		
		JSONObject svcContJs = new JSONObject();
		svcContJs.elementOpt("SOO", sooarrJs);
		JSONObject tcpContJs = new JSONObject();
		tcpContJs.elementOpt("TransactionID", "99"+generateTimeSeq(1));
		tcpContJs.elementOpt("SrvcInstId", "SRVCINST80050");
		tcpContJs.elementOpt("SrcSysID", mkt.get("SrcSysID"));
		
		JSONObject contractRootJs = new JSONObject();
		contractRootJs.elementOpt("TcpCont", tcpContJs);
		contractRootJs.elementOpt("SvcCont", svcContJs);
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.elementOpt("ContractRoot", contractRootJs);
		return jsonobj;
	}

	@Override
	public JSONObject generateCustOrderJson(Map custResMap) {
		
		
		JSONObject mktinstJs = new JSONObject();
		mktinstJs.elementOpt("CUST_ORDER_ID",custResMap.get("CUST_ORDER_ID"));
		mktinstJs.elementOpt("CUST_ID", custResMap.get("CUST_ID"));
		mktinstJs.elementOpt("CHANNEL_NBR", custResMap.get("CHANNEL_NBR"));
		mktinstJs.elementOpt("STAFF_CODE", custResMap.get("staffCode"));
		mktinstJs.elementOpt("PRE_HANDLE_FLAG", "M");
		mktinstJs.elementOpt("ACCEPT_TIME", generateTimeSeq(0));
		mktinstJs.elementOpt("EXT_SYSTEM", "");
		mktinstJs.elementOpt("LAN_ID",custResMap.get("MKT_RES_STORE_ID"));
		mktinstJs.elementOpt("EXT_CUST_ORDER_ID", custResMap.get("EXT_CUST_ORDER_ID"));
		
		
		JSONObject sooJs = new JSONObject();
		JSONObject typeJs = new JSONObject();
		JSONArray sooarrJs = new JSONArray();
		typeJs.elementOpt("type", "MOD_CUSTOMER_ORDER_REQ_TYPE");
		sooJs.elementOpt("@attributes",typeJs);
		sooJs.elementOpt("CUSTOMER_ORDER", mktinstJs);
		sooarrJs.add(sooJs);
		
		JSONObject svcContJs = new JSONObject();
		svcContJs.elementOpt("SOO", sooarrJs);
		JSONObject tcpContJs = new JSONObject();
		tcpContJs.elementOpt("TransactionID", "99"+generateTimeSeq(1));
		tcpContJs.elementOpt("SrvcInstId", "SRVCINST80056");
		tcpContJs.elementOpt("SrcSysID", custResMap.get("SrcSysID"));
		
		JSONObject contractRootJs = new JSONObject();
		contractRootJs.elementOpt("TcpCont", tcpContJs);
		contractRootJs.elementOpt("SvcCont", svcContJs);
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.elementOpt("ContractRoot", contractRootJs);
		
		
		return jsonobj;
	}
	public  String generateTimeSeq(int count){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		 String nowdate = sdf.format(new Date());
		 if(count>0){
			 nowdate=nowdate+((int)(Math.random()*9000));
		 }
		return nowdate;
	}
	public JSONObject generateOrderJson(Map changeCardMap){
		JSONObject orderJson = (JSONObject) changeCardMap.get("orderJson");
		String prodId = String.valueOf(changeCardMap.get("prodId"));
		String terminalCode = String.valueOf(changeCardMap.get("terminalCode"));
		String staffId = String.valueOf(changeCardMap.get("staffId"));
		
		JSONArray custOrderList = orderJson.getJSONObject("orderList").getJSONArray("custOrderList");
		JSONObject busiOrder =  (JSONObject) orderJson.getJSONObject("orderList").getJSONArray("custOrderList").get(0);
		JSONObject busiOrder1 = (JSONObject) busiOrder.getJSONArray("busiOrder").get(0);
		busiOrder1.remove("linkFlag");
		busiOrder1.element("hasPrepared", "1");
		busiOrder1.element("actionFlag", "1");
		busiOrder1.element("areaId", "11000");
		busiOrder1.element("areaName", "北京市");
		
		JSONObject busiObj = busiOrder1.getJSONObject("busiObj");
		busiObj.element("isComp","N");
		busiObj.element("name", "天翼手机");
		
		JSONObject busiOrderInfo = busiOrder1.getJSONObject("busiOrderInfo");
		busiOrderInfo.element("extOrderItemGroupId", "250008871802");
		busiOrderInfo.element("extOrderItemId", "250009572282");
		//拼接bo2Coupons节点
		JSONArray bo2Coupons = busiOrder1.getJSONObject("data").getJSONArray("bo2Coupons");
		if(bo2Coupons!=null && bo2Coupons.size()>0){
			for(int i=0;i<bo2Coupons.size();i++){
				JSONObject bo2Coupon = (JSONObject) bo2Coupons.get(i);
				bo2Coupons.remove(i);
				bo2Coupon.element("couponSource", "");
				bo2Coupon.element("description", "");
				bo2Coupon.element("instId",prodId);
				bo2Coupon.element("mktResCd", "");
				bo2Coupon.element("mktResInstCode", terminalCode);
				bo2Coupon.element("mktResType", "");
				bo2Coupon.element("offerId", "");
				bo2Coupon.element("ruleId", "");
				bo2Coupon.element("salesPrice", "0");
				bo2Coupon.element("sourceFlag", "0");
				bo2Coupon.element("unit", "");
				bo2Coupon.remove("atomActionId");
				bo2Coupons.add(i,bo2Coupon);
			}
		}
		
		//拼接boAcctItems节点
		JSONArray boAcctItems = busiOrder1.getJSONObject("data").getJSONArray("boAcctItems");
		if(boAcctItems!=null && boAcctItems.size()>0){
			for(int i=0;i<boAcctItems.size();i++){
				JSONObject boAcctItem = (JSONObject) boAcctItems.get(i);
				boAcctItems.remove(i);
				boAcctItem.element("billId", "-1");
				boAcctItem.element("extAcctItemId", "");
				boAcctItem.element("payedDate", "");
				boAcctItem.element("paymentMethodId", "");
				boAcctItem.element("state", "ADD");
				boAcctItem.remove("platId");
				boAcctItem.remove("payMethodId");
				boAcctItems.add(i,boAcctItem);
			}
		}
		//拼接boProd2Tds节点
		/**/JSONArray boProd2Tds = busiOrder1.getJSONObject("data").getJSONArray("boProd2Tds");
		if(boProd2Tds!=null && boProd2Tds.size()>0){
			for(int i=0;i<boProd2Tds.size();i++){
				JSONObject boProd2Td = (JSONObject) boProd2Tds.get(i);
				String state = boProd2Td.getString("state");
				boProd2Tds.remove(i);
				if(state!=null && state.equals("ADD")){
					boProd2Td.element("assitChannelNbrFlag", "1");
					boProd2Td.element("assterminalCode", terminalCode);
					boProd2Td.element("prod2TdId", "-1");
				}
				boProd2Td.element("anId", "");
				boProd2Td.element("anTypeCd", "509");
				if(state!=null && state.equals("DEL")){
					boProd2Td.element("asstInstId", terminalCode);
				}
				
				boProd2Td.element("checked", "TRUE");
				boProd2Td.element("couponId", "");
				boProd2Td.element("deviceModelName", "");
				boProd2Td.element("checked", "TRUE");
				boProd2Td.element("ifInstance", "Y");
				boProd2Td.element("name", "天翼手机卡");

				boProd2Td.element("releaseFlag", "TRUE");
				boProd2Td.element("saved", "TRUE");
				boProd2Td.element("verifyFlag", "TRUE");
				boProd2Tds.add(i,boProd2Td);
			}
		}
		//拼接orderListInfo节点
		JSONObject orderListInfo = orderJson.getJSONObject("orderList").getJSONObject("orderListInfo");
		orderListInfo.element("areaName", "北京市");
		orderListInfo.element("areaId", "11000");
		orderListInfo.element("extCustOrderId", "");
		orderListInfo.element("lanId", "");
		orderListInfo.element("lanId1", "");
		orderListInfo.element("preHandleFlag", "M");
		orderListInfo.element("provIsale", "");
		orderListInfo.element("reqTime", "");
		orderListInfo.element("rollBackFlag", "1");
		orderListInfo.element("sellsBackFlag", "0");
		orderListInfo.element("sellsBackServiceOfferId", "");
		orderListInfo.element("sruleChannelId", "-10026");
		orderListInfo.element("sruleStaffId", "-10026");
		orderListInfo.element("staffId", staffId);
		orderListInfo.element("statusCd", "S");
		orderListInfo.remove("statusDt");
		orderListInfo.remove("systemId");
		orderListInfo.remove("soDate");
		//去掉orderList层
		JSONObject orderList =  orderJson.getJSONObject("orderList");
		orderList.remove("payInfoList");
		orderJson.remove("orderList");
		orderJson.element("custOrderList", custOrderList);
		orderJson.element("localAreaId", "11000");
		orderJson.element("orderListInfo", orderListInfo);
		
		return orderJson;
	}
	public JSONObject generateOrderJson2(Map changeCardMap){
		Map<String, Object> prodInfo = (Map<String, Object>) changeCardMap.get("prodInfo");
		Map<String, Object> couponInfo = (Map<String, Object>) changeCardMap.get("couponInfo");
		Map<String, Object> couponInfo2 = (Map<String, Object>) changeCardMap.get("couponInfo2");
		Map<String, Object> mkt = (Map<String, Object>) changeCardMap.get("mkt");
		String terminalCode = String.valueOf(changeCardMap.get("terminalCode"));
		String staffId = String.valueOf(changeCardMap.get("staffId"));
		String channelId = String.valueOf(changeCardMap.get("channelId"));
		//
		String iPnumber = String.valueOf(changeCardMap.get("iPnumber"));
		String handleCustId = String.valueOf(changeCardMap.get("handleCustId"));
		
		String apCharge=String.valueOf(changeCardMap.get("apCharge"));
		
		JSONObject jsonObj = new JSONObject();
		//1、拼写头
		jsonObj.element("SrcSysID", mkt.get("SrcSysID"));
		jsonObj.element("SrvcInstId", "SRVCINST80058");
		jsonObj.element("TransactionID", changeCardMap.get("provIsale"));
		jsonObj.element("bssInnerAreaId", "11000");
		//jsonObj.element("localAreaId", "11000");
		//2、拼写orderListInfo
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.element("areaId", "11000");
		jsonObj2.element("areaName", "北京市");
		jsonObj2.element("channelId", channelId);
		jsonObj2.element("extCustOrderId", changeCardMap.get("provIsale"));
		jsonObj2.element("lanId", mkt.get("MKT_RES_STORE_ID"));
		jsonObj2.element("lanId1", "");
		jsonObj2.element("olId", "");
		jsonObj2.element("olNbr", "");
		jsonObj2.element("olTypeCd", "15");
		jsonObj2.element("partyId", prodInfo.get("partyId"));
		jsonObj2.element("preHandleFlag", "M");
		jsonObj2.element("provIsale",changeCardMap.get("provIsale"));
		jsonObj2.element("reqTime", generateTimeSeq(0));
		jsonObj2.element("rollBackFlag", "1");
		jsonObj2.element("sellsBackFlag", "0");
		jsonObj2.element("sellsBackServiceOfferId", "");
		jsonObj2.element("sruleChannelId", "11040078");
		jsonObj2.element("sruleStaffId", "11040078");
		jsonObj2.element("staffId", staffId);
		jsonObj2.element("statusCd", "S");
		//新增HANDLE_CUST_ID
		jsonObj2.element("handleCustId", handleCustId);
		//新增ATTR
		JSONArray attrArrJs = new JSONArray();
		JSONObject attrJs = new JSONObject();
		attrJs.elementOpt("value", iPnumber);
		attrJs.elementOpt("attrId", "40010038");
		attrArrJs.add(attrJs);
		jsonObj2.element("custOrderAttrs", attrArrJs);
//		jsonObj2.element("statusCd", "S");
		jsonObj.element("orderListInfo", jsonObj2);
		//3、拼写custOrderList
		JSONArray custOrderList = new JSONArray();
		JSONObject json0 = new JSONObject();
		json0.element("colNbr", "-1");
		json0.element("partyId", prodInfo.get("partyId"));
		//构造busiOrder
		JSONArray busiOrder = new JSONArray();
		JSONObject busiOrderJson = new JSONObject();
		busiOrderJson.element("actionFlag", "1");
		busiOrderJson.element("areaId", "11000");
		busiOrderJson.element("areaName", "北京市");
		busiOrderJson.element("hasPrepared", "1");
		
		//（1）、拼写boActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "4");
		boActionType.element("boActionTypeCd", "14");
		boActionType.element("name", "补机/补卡");
		busiOrderJson.element("boActionType", boActionType);
		//（2）、拼写busiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("accessNumber", mkt.get("accNbr"));
		busiObj.element("instId", prodInfo.get("prodId"));
		busiObj.element("isComp", "N");
		busiObj.element("name", "天翼手机");
		busiObj.element("objId", "378");
		busiOrderJson.element("busiObj", busiObj);
		//（3）、拼写busiOrderInfo
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("extOrderItemGroupId",generateTimeSeq(0));
		busiOrderInfo.element("extOrderItemId", generateTimeSeq(0));
		busiOrderInfo.element("seq", "-1");
		busiOrderInfo.element("statusCd", "S");
		busiOrderJson.element("busiOrderInfo", busiOrderInfo);
		//（4）、拼写data
		JSONObject data = new JSONObject();
		
		//（4-1）、拼写bo2Coupons
		JSONArray bo2Coupons = new JSONArray();
		JSONObject bo2CouponsJson = new JSONObject();
		bo2CouponsJson.element("agentId", "1000");
		bo2CouponsJson.element("agentName", "默认供货商");
		bo2CouponsJson.element("apCharge", StringUtil.isEmpty(apCharge)? 0 :apCharge);//Long.valueOf(String.valueOf(couponInfo.get("apCharge")))*100);
		bo2CouponsJson.element("chargeItemCd", "90013");
		bo2CouponsJson.element("couponId", couponInfo.get("couponId"));
		bo2CouponsJson.element("couponInstanceNumber",terminalCode);
		bo2CouponsJson.element("couponNum", "1");
		bo2CouponsJson.element("couponSource", "L");
		bo2CouponsJson.element("couponUsageTypeCd", "3");
		bo2CouponsJson.element("couponinfoStatusCd", "A");
		bo2CouponsJson.element("description", "");
		bo2CouponsJson.element("id", "-1");
		bo2CouponsJson.element("inOutReasonId", "1");
		bo2CouponsJson.element("inOutTypeId", "1");
		bo2CouponsJson.element("instId", prodInfo.get("prodId"));
		bo2CouponsJson.element("isTransfer", "Y");
		bo2CouponsJson.element("mktResCd", mkt.get("MKT_RES_CD"));
		bo2CouponsJson.element("mktResInstCode", mkt.get("MKT_RES_INST_CODE"));
		bo2CouponsJson.element("mktResType", "A1");
		bo2CouponsJson.element("offerId", "");
		bo2CouponsJson.element("partyId", prodInfo.get("partyId"));
		bo2CouponsJson.element("prodId", prodInfo.get("prodId"));
		bo2CouponsJson.element("ruleId", "");
		bo2CouponsJson.element("saleId", "1");
		bo2CouponsJson.element("salesPrice", "0.0");
		bo2CouponsJson.element("sourceFlag", "0");
		bo2CouponsJson.element("state", "ADD");
		bo2CouponsJson.element("storeId", couponInfo.get("storied"));
		bo2CouponsJson.element("storeName", "政企客户部VIP");
		bo2CouponsJson.element("unit", "1");
		bo2Coupons.add(0, bo2CouponsJson);
		data.element("bo2Coupons", bo2Coupons);
		//（4-2）、拼写boAcctItems
		JSONArray boAcctItems = new JSONArray();
		JSONObject boAcctItemsJson = new JSONObject();
		boAcctItemsJson.element("acctItemTypeId", "90013");
		boAcctItemsJson.element("amount",StringUtil.isEmpty(apCharge)? 0 :apCharge);
		boAcctItemsJson.element("billId", "-1");
		boAcctItemsJson.element("createdDate", "");
		boAcctItemsJson.element("currency", "");
		boAcctItemsJson.element("extAcctItemId", generateTimeSeq(0));
		boAcctItemsJson.element("payedDate",generateTimeSeq(0));
		boAcctItemsJson.element("paymentMethodId", "100000");
		boAcctItemsJson.element("ratio", "2000");
		boAcctItemsJson.element("ratioMethod", "1100");
		boAcctItemsJson.element("realAmount", StringUtil.isEmpty(apCharge)? 0 :apCharge);
		boAcctItemsJson.element("state", "ADD");
		boAcctItemsJson.element("statusCd", "S");
		boAcctItems.add(0,boAcctItemsJson);
		data.element("boAcctItems", boAcctItems);
		//（4-3）、拼写boProd2Tds
		Map<String, Object> code=(Map<String, Object>) changeCardMap.get("code");;
		Map<String, Object> couponInfoDel = intfSMO.getBasicCouponInfoByTerminalCode(code.get("terminalCode").toString());
//		Map<String, Object>  prod2TdIdDel= intfSMO.getCouponInfoByTerminalCode(code.get("terminalCode").toString());
		Map<String, Object>  prod2TdIdDel= intfSMO.getgetprod2TdIdDelCodeCode(code.get("terminalCode").toString());
		JSONArray boProd2Tds = new JSONArray();
		JSONObject boProd2TdsJson = new JSONObject();
		boProd2TdsJson.element("anId", couponInfoDel.get("AN_ID"));
		boProd2TdsJson.element("anTypeCd", "509");
		boProd2TdsJson.element("asstInstId", prodInfo.get("prodId"));
		boProd2TdsJson.element("atomActionId", "-1");
		boProd2TdsJson.element("checked", "TRUE");
		boProd2TdsJson.element("couponId", couponInfo.get("coupon_id"));
		boProd2TdsJson.element("deviceModelId", couponInfoDel.get("DEVICE_MODEL_ID"));
		boProd2TdsJson.element("deviceModelName", couponInfoDel.get("DEVICE_MODEL_NAME"));
		boProd2TdsJson.element("ifInstance", "Y");
		boProd2TdsJson.element("maintainTypeCd", "1");
		boProd2TdsJson.element("name", "天翼手机卡");
		boProd2TdsJson.element("ownerTypeCd", "1");
		boProd2TdsJson.element("prod2TdId", prod2TdIdDel.get("PROD_2_TD_ID"));
		boProd2TdsJson.element("releaseFlag", "TRUE");
		boProd2TdsJson.element("saved", "TRUE");
		boProd2TdsJson.element("state", "DEL");
		boProd2TdsJson.element("statusCd", "S");
		boProd2TdsJson.element("terminalCode",code.get("terminalCode"));
		boProd2TdsJson.element("terminalDevId", couponInfoDel.get("TERMINAL_DEV_ID"));
		boProd2TdsJson.element("terminalDevSpecId", couponInfoDel.get("TERMINAL_DEV_SPEC_ID"));
		boProd2TdsJson.element("verifyFlag", "TRUE");
		boProd2Tds.add(0, boProd2TdsJson);
		
		JSONObject boProd2TdsJson2 = new JSONObject();
		boProd2TdsJson2.element("anId",couponInfo2.get("AN_ID"));
		boProd2TdsJson2.element("anTypeCd","509");
		boProd2TdsJson2.element("assitChannelNbrFlag","1");
		boProd2TdsJson2.element("assterminalCode",mkt.get("MKT_RES_INST_CODE"));
		boProd2TdsJson2.element("atomActionId","-2");
		boProd2TdsJson2.element("checked","TRUE");
		boProd2TdsJson2.element("couponId",couponInfo.get("couponid"));
		boProd2TdsJson2.element("deviceModelId",couponInfo2.get("DEVICE_MODEL_ID"));
		boProd2TdsJson2.element("deviceModelName",couponInfo2.get("DEVICE_MODEL_NAME"));
		boProd2TdsJson2.element("ifInstance","Y");
		boProd2TdsJson2.element("maintainTypeCd","1");
		boProd2TdsJson2.element("name",couponInfo2.get("NAME")); 
		boProd2TdsJson2.element("ownerTypeCd","1");
		boProd2TdsJson2.element("prod2TdId",couponInfo.get("prod2tdid"));
		boProd2TdsJson2.element("releaseFlag","TRUE");
		boProd2TdsJson2.element("saved","TRUE");
		boProd2TdsJson2.element("state","ADD");
		boProd2TdsJson2.element("statusCd","S");
		boProd2TdsJson2.element("terminalCode",mkt.get("MKT_RES_INST_CODE"));
		boProd2TdsJson2.element("terminalDevId",couponInfo2.get("TERMINAL_DEV_ID"));
		boProd2TdsJson2.element("terminalDevSpecId", couponInfo2.get("TERMINAL_DEV_SPEC_ID"));
		boProd2TdsJson2.element("verifyFlag","TRUE");
		boProd2Tds.add(1, boProd2TdsJson2);
		data.element("boProd2Tds", boProd2Tds);
		busiOrderJson.element("data", data);
		
		busiOrder.add(busiOrderJson);
		JSONObject json = new JSONObject();
		json.element("colNbr", "-1");
		json.element("partyId", prodInfo.get("partyId"));
		json.element("busiOrder", busiOrder);
		custOrderList.add(json);
		jsonObj.element("custOrderList", custOrderList);
		return jsonObj;
	}
	@Override
	public JSONObject getFeeInfoByCustOrderId(Map custResMap) {
		JSONObject sooJs = new JSONObject();
		JSONObject typeJs = new JSONObject();
		JSONObject pageReq = new JSONObject();
		
		JSONArray sooarrJs = new JSONArray();
		pageReq.element("PAGE_REQ", 1);
		pageReq.element("PAGE_SIZE", 10);
		
		JSONArray field = new JSONArray();
		JSONObject value1 = new JSONObject();
		JSONObject value2 = new JSONObject();
		JSONObject value3 = new JSONObject();
		JSONObject value4 = new JSONObject();
		JSONObject value5 = new JSONObject();
		JSONObject value6 = new JSONObject();
		JSONObject value7 = new JSONObject();
		JSONObject value8 = new JSONObject();
		JSONObject value9 = new JSONObject();
		value1.element("VALUE", "EXT_ACCT_ITEM_ID");
		value2.element("VALUE", "ORDER_ITEM_ID");
		value3.element("VALUE", "ACCT_ITEM_TYPE_ID");
		value4.element("VALUE", "AMOUNT");
		value5.element("VALUE", "TAX_RATE");
		value6.element("VALUE", "PAYMENT_METHOD_CD");
		value7.element("VALUE", "CURRENCY");
		value8.element("VALUE", "140000020");
		value9.element("VALUE", "140000021");
		field.add(value1);
		field.add(value2);
		field.add(value3);
		field.add(value4);
		field.add(value5);
		field.add(value6);
		field.add(value7);
		field.add(value8);
		field.add(value9);
		
		typeJs.elementOpt("type", "MOD_CUSTOMER_ORDER_REQ_TYPE");
		sooJs.elementOpt("@attributes",typeJs);
		sooJs.elementOpt("CUST_ORDER_ID", custResMap.get("CUST_ORDER_ID"));
		sooJs.elementOpt("FIELD", field);
		sooJs.elementOpt("LAN_ID", custResMap.get("MKT_RES_STORE_ID"));
		sooJs.elementOpt("PAGE_REQ", pageReq);
		sooarrJs.add(sooJs);
		
		JSONObject svcContJs = new JSONObject();
		svcContJs.elementOpt("SOO", sooarrJs);
		JSONObject tcpContJs = new JSONObject();
		tcpContJs.elementOpt("TransactionID", "99"+generateTimeSeq(1));
		tcpContJs.elementOpt("SrvcInstId", "SRVCINST80055");
		tcpContJs.elementOpt("SrcSysID", custResMap.get("SrcSysID"));
		
		JSONObject contractRootJs = new JSONObject();
		contractRootJs.elementOpt("TcpCont", tcpContJs);
		contractRootJs.elementOpt("SvcCont", svcContJs);
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.elementOpt("ContractRoot", contractRootJs);
		
		
		return jsonobj;
	}
	@Override
	public JSONObject getUpdateFeeJsonByFeeInfo(String jsonString,Map<String,Object> paraMap) {
		JSONObject jsonStr =  JSONObject.fromObject(jsonString);
		JSONObject SOO0 = (JSONObject) jsonStr.getJSONObject("ContractRoot").getJSONObject("SvcCont").getJSONArray("SOO").get(0);
		JSONObject acctItem0 = (JSONObject) SOO0.getJSONArray("ACCT_ITEM").get(0);
		acctItem0.element("REAL_AMOUNT", "0");
		JSONObject json1 = (JSONObject) acctItem0.getJSONArray("ATTR").get(0);
		json1.remove("VALUE");
		JSONObject json2 = (JSONObject) acctItem0.getJSONArray("ATTR").get(1);
		json2.remove("VALUE");
		JSONObject mktinstJs = new JSONObject();
		
		mktinstJs.elementOpt("CUST_ORDER_ID",paraMap.get("CUST_ORDER_ID"));
		mktinstJs.elementOpt("CUST_ID", paraMap.get("CUST_ID"));
		mktinstJs.elementOpt("CHANNEL_NBR", paraMap.get("CHANNEL_NBR"));
		mktinstJs.elementOpt("STAFF_CODE", paraMap.get("staffCode"));
		mktinstJs.elementOpt("PRE_HANDLE_FLAG", "M");
		mktinstJs.elementOpt("ACCEPT_TIME", generateTimeSeq(0));
		mktinstJs.elementOpt("EXT_SYSTEM", "");
		mktinstJs.elementOpt("LAN_ID",paraMap.get("MKT_RES_STORE_ID"));
		mktinstJs.elementOpt("EXT_CUST_ORDER_ID", paraMap.get("EXT_CUST_ORDER_ID"));
		
		
		JSONArray sooarrJs = new JSONArray();
		
		JSONObject sooJs1 = new JSONObject();
		
		JSONObject typeJs1 = new JSONObject();
		typeJs1.elementOpt("type", "MOD_ACCT_ITEM_REQ_TYPE");
		sooJs1.elementOpt("@attributes",typeJs1);
		sooJs1.elementOpt("ACCT_ITEM",acctItem0);
		
		JSONObject sooJs = new JSONObject();
		JSONObject typeJs = new JSONObject();
		
		typeJs.elementOpt("type", "MOD_CUSTOMER_ORDER_REQ_TYPE");
		sooJs.elementOpt("@attributes",typeJs);
		sooJs.elementOpt("CUSTOMER_ORDER", mktinstJs);
		sooarrJs.add(sooJs);
		sooarrJs.add(sooJs1);
		
		JSONObject svcContJs = new JSONObject();
		svcContJs.elementOpt("SOO", sooarrJs);
		JSONObject tcpContJs = new JSONObject();
		tcpContJs.elementOpt("TransactionID", "99"+generateTimeSeq(1));
		tcpContJs.elementOpt("SrvcInstId", "SRVCINST80056");
		tcpContJs.elementOpt("SrcSysID", paraMap.get("SrcSysID"));
		
		JSONObject contractRootJs = new JSONObject();
		contractRootJs.elementOpt("TcpCont", tcpContJs);
		contractRootJs.elementOpt("SvcCont", svcContJs);
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.elementOpt("ContractRoot", contractRootJs);
		
		
		return jsonobj;
	}
	@Override
	public String getSendStringXML(Map<String,Object> paramMap){
			StringBuffer strBuffer = new StringBuffer();
			String strXML = "";
			//挂失，解挂
			//if(paramMap.get("orderTypeId").equals("19") || paramMap.get("orderTypeId").equals("20")){
			strBuffer.append("<SAOP_DTO>");
				strBuffer.append("<TRANSACTION_ID>").append(paramMap.get("systemId")+generateTimeSeq(1)).append("</TRANSACTION_ID>");
				strBuffer.append("<SRVC_INST_ID>").append("SRVCINST_SVC80058,SRVCINST_SVC80056").append("</SRVC_INST_ID>");
				strBuffer.append("<SRC_SYS_ID>").append(paramMap.get("systemId")).append("</SRC_SYS_ID>");
				strBuffer.append("<CHANNEL_ID>").append(paramMap.get("channelId")).append("</CHANNEL_ID>");
				strBuffer.append("<STAFF_ID>").append(paramMap.get("staffId")).append("</STAFF_ID>");
				strBuffer.append("<SAOP_PROD_INSTS>");
					strBuffer.append("<SAOP_PROD_INST>");
						//拆机
						//1166
						if("1166".equals(paramMap.get("orderTypeId"))){
							strBuffer.append("<ACTION_TYPE>").append("DEL").append("</ACTION_TYPE>");
							strBuffer.append("<PROD_ID>").append(paramMap.get("PROD_ID")).append("</PROD_ID>");
						}else{
						//strBuffer.append("<MOD_PROD_STATUS ACTION_TYPE='19,20,41,67,1171,1172'>");
							strBuffer.append("<ACTION_TYPE>").append("MOD").append("</ACTION_TYPE>");
							//strBuffer.append("<CUST_ID>").append(paramMap.get("CUST_ID")).append("</CUST_ID>");
							strBuffer.append("<PROD_ID>").append(paramMap.get("PROD_ID")).append("</PROD_ID>");
							strBuffer.append("<MOD_PROD_STATUS ACTION_TYPE='"+paramMap.get("orderTypeId")+"'>");
								strBuffer.append("<ADD_PROD_STATUS>").append("1").append("</ADD_PROD_STATUS>");
								strBuffer.append("<DEL_PROD_STATUS>").append("8").append("</DEL_PROD_STATUS>");
							strBuffer.append("</MOD_PROD_STATUS>");
						}
					strBuffer.append("</SAOP_PROD_INST>");
				strBuffer.append("</SAOP_PROD_INSTS>");
			strBuffer.append("</SAOP_DTO>");
		//}
			strXML = strBuffer.toString();
		return strXML;
	}
	@Override
	public Map<String,String> getOfferInfoXML(Map<String,Object> paramMap){
		StringBuffer strBuffer = new StringBuffer();
		String offerInfo = "";
		String strXML = "";
		Document doc = null;
		doc = (Document) paramMap.get("document");
		Map<String,String> retMap = new HashMap<String,String>();
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		String staffId = intfSMO.queryStaffIdByStaffCode(staffCode);
		//订购功能产品
			strBuffer.append("<SAOP_DTO>");//.append("").append("</SAOP_DTO>");
				strBuffer.append("<TRANSACTION_ID>").append(paramMap.get("systemId")+generateTimeSeq(1)).append("</TRANSACTION_ID>");
				strBuffer.append("<SRVC_INST_ID>").append("SRVCINST_SVC80058,SRVCINST_SVC80056").append("</SRVC_INST_ID>");
				strBuffer.append("<SRC_SYS_ID>").append(paramMap.get("systemId")).append("</SRC_SYS_ID>");
				strBuffer.append("<CHANNEL_ID>").append(channelId).append("</CHANNEL_ID>");
				strBuffer.append("<STAFF_ID>").append(staffId).append("</STAFF_ID>");
					strBuffer.append("<SAOP_PROD_INSTS>");
					strBuffer.append("<SAOP_PROD_INST>");
					strBuffer.append("<ACTION_TYPE>").append("KIP").append("</ACTION_TYPE>");
					//strBuffer.append("<CUST_ID>").append("").append("</CUST_ID>");
					strBuffer.append("<PROD_ID>").append(paramMap.get("PROD_ID")).append("</PROD_ID>");
					Node offerSpecs = WSUtil.getXmlNode(doc, "//request/order/offerSpecs");
					if (offerSpecs != null) {
						List<Node> offerSpecInfoList = offerSpecs.selectNodes("offerSpec");
					//循环销售品
					Map<String,Object>  servInfo = new HashMap<String,Object>();
					for (int i = 0; offerSpecInfoList.size() > i; i++) {
						String id = WSUtil.getXmlNodeText(offerSpecInfoList.get(i), "id");
						//首先判断是可选包还是销售品
						//获取服务id，如果查询结果为空，则为可选包
						servInfo = intfSMO.getServInfoByOfferSpecId(id);//992018140
						//订购可选包
						if(servInfo==null || "".equals(servInfo)){
							offerInfo = orderOfferInfoXML(offerSpecInfoList.get(i), paramMap);
						}
						//订购功能产品
						else{
							offerInfo = orderServInfoXML(offerSpecInfoList.get(i), paramMap);
						}
						if(offerInfo==null || "".equals(offerInfo)){
							retMap.put("flag", "1");
							retMap.put("msg",WSUtil.getXmlNodeText(offerSpecInfoList.get(i), "name"));
							return retMap;
						}
						strBuffer.append(offerInfo);
						}
					}
					strBuffer.append("</SAOP_PROD_INST>");
					strBuffer.append("</SAOP_PROD_INSTS>");
			strBuffer.append("</SAOP_DTO>");
			retMap.put("flag", "0");
			retMap.put("msg", strBuffer.toString());
		return retMap;
	}
	//如果是销售品，则拼接销售品节点，订购退订功能产品
	public String orderOfferInfoXML(Node offerSpenInfo,Map<String,Object> paramMap){
		String strXML="";
		StringBuffer strBuffer = new StringBuffer();
		Document doc = null;
		doc = (Document) paramMap.get("document");
		String actionType = WSUtil.getXmlNodeText(offerSpenInfo, "actionType");
		String id = WSUtil.getXmlNodeText(offerSpenInfo, "id");
		String offerName = WSUtil.getXmlNodeText(offerSpenInfo, "name");
		 if("0".equals(actionType)){
				strBuffer.append("<ADD_OFFER ACTION_TYPE='S1'>");
					strBuffer.append("<PROD_OFFER_INST>");
						strBuffer.append("<OFFER_ID>").append("-1").append("</OFFER_ID>");//
						strBuffer.append("<OFFER_SPEC_ID>").append(WSUtil.getXmlNodeText(offerSpenInfo, "id")).append("</OFFER_SPEC_ID>");//
						strBuffer.append("<ATTR_LIST>");
						//循环销售品属性
						Node propertys = WSUtil.getXmlNode(doc, "//request/order/offerSpecs/offerSpec/properties");
						if(propertys!=null && !"".equals(propertys)){
							List<Node> propertieList = propertys.selectNodes("property");
							if (propertieList != null) {
								for (int j = 0; propertieList.size() > j; j++) {
									strBuffer.append("<ATTR_TYPE>");
									strBuffer.append("<CD>").append(WSUtil.getXmlNodeText(propertieList.get(j), "id")).append("</CD>");
									strBuffer.append("<VAL>").append(WSUtil.getXmlNodeText(propertieList.get(j), "value")).append("</VAL>");
									strBuffer.append("<VAL_NAME>").append(WSUtil.getXmlNodeText(propertieList.get(j), "name")).append("</VAL_NAME>");
									strBuffer.append("</ATTR_TYPE>");
								}
							}
						}
						strBuffer.append("</ATTR_LIST>");
						//<!--销售品成员角色【具体值参考主数据文档，必填 】-->
						strBuffer.append("<ROLE_CD/>");//
					strBuffer.append("</PROD_OFFER_INST>");
				strBuffer.append("</ADD_OFFER>");
			 }
			 //退订可选包
			 else{
				 strBuffer.append("<DEL_OFFER ACTION_TYPE='S2'>");
				 Offer offer = intfSMO.findOfferByProdIdAndOfferSpecId(Long.valueOf(String.valueOf(paramMap.get("PROD_ID"))),Long.valueOf(id));
				 if(offer==null || "".equals(offer)){
					 return null;
				 }
				 strBuffer.append("<PROD_OFFER_INST>").append("<OFFER_ID>").append(offer.getOfferId()).append("</OFFER_ID>").append("</PROD_OFFER_INST>");
				 strBuffer.append("</DEL_OFFER>");
			 }
		 strXML = strBuffer.toString();
		return strXML;
	}
	//订购退订功能产品
	public String orderServInfoXML(Node offerSpenInfo,Map<String,Object> paramMap){
		String strXML="";
		StringBuffer strBuffer = new StringBuffer();
		Document doc = null;
		doc = (Document) paramMap.get("document");
		String actionType = WSUtil.getXmlNodeText(offerSpenInfo, "actionType");
		String id = WSUtil.getXmlNodeText(offerSpenInfo, "id");
		Map<String,Object>  servInfo = new HashMap<String,Object>();
		//0为订购，1为退订
		 if("0".equals(actionType)){
			strBuffer.append("<ADD_PROD_INST ACTION_TYPE='7'>");
				strBuffer.append("<PROD_INST>");
					strBuffer.append("<SERV_ID>").append("-1").append("</SERV_ID>");
					//获取服务id
					servInfo = intfSMO.getServInfoByOfferSpecId(id);//992018140
					strBuffer.append("<SERV_SPEC_ID>").append(servInfo.get("OBJ_ID")).append("</SERV_SPEC_ID>");
					strBuffer.append("<ATTR_LIST>");
					//循环销售品属性
					Node propertys = WSUtil.getXmlNode(doc, "//request/order/offerSpecs/offerSpec/properties");
					if(propertys!=null && !"".equals(propertys)){
						List<Node> propertieList = propertys.selectNodes("property");
						for (int j = 0; propertieList.size() > j; j++) {
							strBuffer.append("<ATTR_TYPE>");
								strBuffer.append("<CD>").append(WSUtil.getXmlNodeText(propertieList.get(j), "id")).append("</CD>");
								strBuffer.append("<VAL>").append(WSUtil.getXmlNodeText(propertieList.get(j), "value")).append("</VAL>");
								strBuffer.append("<VAL_NAME>").append(WSUtil.getXmlNodeText(propertieList.get(j), "name")).append("</VAL_NAME>");
							strBuffer.append("</ATTR_TYPE>");
						}
					}
					strBuffer.append("</ATTR_LIST>");
				strBuffer.append("</PROD_INST>");
				strBuffer.append("</ADD_PROD_INST>");
		 }
		 //退订功能产品
		 else{
			 strBuffer.append("<DEL_PROD_INST ACTION_TYPE='7'>");
			 servInfo = intfSMO.getServInfoByOfferSpecId(id);//992018140
			 OfferServ offerServ = intfSMO.findOfferServByProdIdAndServSpecId(Long.valueOf(String.valueOf(paramMap.get("PROD_ID"))),Long.valueOf(String.valueOf(servInfo.get("OBJ_ID"))));
			 if((servInfo==null || "".equals(servInfo)) || (offerServ==null || "".equals(offerServ))){
				 return null;
			 }
			 strBuffer.append("<PROD_INST>").append("<SERV_ID>").append(offerServ.getServId()).append("</SERV_ID>").append("</PROD_INST>");
			 strBuffer.append("</DEL_PROD_INST>");
		 }
		strXML = strBuffer.toString();
		return strXML;
	}
}
