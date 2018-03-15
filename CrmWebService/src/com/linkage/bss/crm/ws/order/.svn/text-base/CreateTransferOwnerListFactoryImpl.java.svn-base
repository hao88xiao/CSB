/**
 * 
 */
package com.linkage.bss.crm.ws.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * 过户Dom转json工厂实现类
 * @author hell
 * 2013/07/09
 */
public class CreateTransferOwnerListFactoryImpl implements CreateTransferOwnerListFactory {

	private IntfSMO intfSMO;
	private CustFacade custFacade;
	private Long partyId;
	private static Log logger = Log.getLog(CreateTransferOwnerListFactoryImpl.class);

	public JSONObject generateTransferOwner(Document doc) throws Exception {
		return generateTransferOwnerCommon(doc,"31");//批开客户资料维护
	}
	
	public JSONObject generateTransferOwnerDh(Document doc) throws Exception {
		return generateTransferOwnerCommon(doc,"11");//过户
	}

	private JSONObject generateTransferOwnerCommon(Document doc,String boActionTypeCd)
			throws Exception {
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		String parid = WSUtil.getXmlNodeText(doc, "//request/partyId");
		String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
		Integer prodSpecId = 0;

		// 根据接入号查找产品信息
		OfferProd prod = intfSMO.getProdByAccessNumber(accessNumber);
		if (prod != null) {
			partyId = prod.getPartyId();
			prodSpecId = prod.getProdSpecId();
		} else {
			logger.debug("根据接入号未查询到产品信息" + accessNumber);
			throw new Exception("根据接入号未查询到产品信息" + accessNumber);
		}

		if (parid.equals(partyId + "")) {
			logger.debug("过户操作异常：不能给自己过户！");
			throw new Exception("过户操作异常：不能给自己过户！");
		}

		Party party = custFacade.getPartyByAccessNumber(accessNumber);
		if (null == party) {
			logger.debug("根据接入号未查询到客户信息:" + accessNumber);
			throw new Exception("根据接入号未查询到客户信息:" + accessNumber);
		}

		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();

		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("systemId", "6090010023");
		orderListInfoatt.elementOpt("areaId", areaId);
		orderListInfoatt.elementOpt("areaName", "北京");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		//待确认
		//		orderListInfoatt.elementOpt("channelManageCode", "3206000000");

		JSONArray co = new JSONArray();

		JSONObject busiOrderatt = new JSONObject();

		JSONArray b0array = new JSONArray();
		JSONObject b0 = new JSONObject();

		JSONObject busiObjatt = new JSONObject();

		busiObjatt.elementOpt("objId", prodSpecId);
		busiObjatt.elementOpt("name", prodSpecId);
		List<OfferIntf> queryOfferInstByProdId = intfSMO.queryOfferInstByProdId(prod.getProdId());
		if (queryOfferInstByProdId != null && queryOfferInstByProdId.size() > 0)
			busiObjatt.elementOpt("name", queryOfferInstByProdId.get(0).getOfferSpecName());
		busiObjatt.elementOpt("accessNumber", accessNumber);
		busiObjatt.elementOpt("isComp", "N");
		busiObjatt.elementOpt("instId", prod.getProdId());

		JSONObject busiOrderInfoatt = new JSONObject();
		busiOrderInfoatt.elementOpt("seq", -1);
		busiOrderInfoatt.elementOpt("statusCd", "S");

		JSONObject boActionTypeatt = new JSONObject();
		boActionTypeatt.elementOpt("actionClassCd", 4);
		boActionTypeatt.elementOpt("boActionTypeCd", boActionTypeCd);
		boActionTypeatt.elementOpt("name", "过户");

		JSONObject boCusts = new JSONObject();
		JSONArray boCustsarr = new JSONArray();
		JSONObject b1 = new JSONObject();
		b1.elementOpt("atomActionId", -3);

		b1.elementOpt("detail", party.getPartyName());
		b1.elementOpt("partyId", partyId);
		b1.elementOpt("partyProductRelaRoleCd", 0);
		b1.elementOpt("partyTypeCd", "1");
		b1.elementOpt("state", "DEL");
		b1.elementOpt("statusCd", "S");
		b1.elementOpt("saved", true);
		b1.elementOpt("commitTimeStamp", 9);
		JSONObject b2 = new JSONObject();
		b2.elementOpt("atomActionId", -1);
		party = custFacade.getPartyById(parid);
		if (null == party) {
			throw new Exception("根据客户号id未查询到客户信息:" + parid);
		}
		b2.elementOpt("detail", party.getPartyName());
		b2.elementOpt("partyId", parid);
		b2.elementOpt("partyProductRelaRoleCd", 0);
		b2.elementOpt("partyTypeCd", "1");
		b2.elementOpt("state", "ADD");
		b2.elementOpt("statusCd", "S");
		b2.elementOpt("saved", true);
		b2.elementOpt("commitTimeStamp", 9);
		boCustsarr.add(b1);
		boCustsarr.add(b2);
		boCusts.elementOpt("boCusts", boCustsarr);

		b0.elementOpt("busiOrderInfo", busiOrderInfoatt);
		b0.elementOpt("busiObj", busiObjatt);
		b0.elementOpt("areaId", areaId);
		b0.elementOpt("boActionType", boActionTypeatt);
		b0.elementOpt("data", boCusts);

		b0array.add(b0);

		busiOrderatt.elementOpt("busiOrder", b0array);
		busiOrderatt.elementOpt("colNbr", "-1");
		busiOrderatt.elementOpt("partyId", partyId);
		busiOrderatt.element("name", party.getPartyName());

		co.add(busiOrderatt);

		rootatt.elementOpt("custOrderList", co);

		rootatt.elementOpt("orderListInfo", orderListInfoatt);

		root.elementOpt("orderList", rootatt);
		logger.debug("过户json拼接完毕...");

		return root;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	@Override
	public JSONObject generateCpsxBg(Document doc) throws Exception {
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		Integer prodSpecId = 0;

		// 根据接入号查找产品信息
		OfferProd prod = intfSMO.getProdByAccessNumber(accessNumber);

		if (prod != null) {
			partyId = prod.getPartyId();
			prodSpecId = prod.getProdSpecId();
		} else {
			logger.debug("根据接入号未查询到产品信息" + accessNumber);
			throw new Exception("根据接入号未查询到产品信息" + accessNumber);
		}
		Party party = custFacade.getPartyByAccessNumber(accessNumber);
		if (null == party) {
			logger.debug("根据接入号未查询到客户信息:" + accessNumber);
			throw new Exception("根据接入号未查询到客户信息:" + accessNumber);
		}

		Long gxProdItemIdByProdid = intfSMO.getGxProdItemIdByProdid(prod.getProdId());

		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();

		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("areaId", areaId);
		orderListInfoatt.elementOpt("areaName", "北京");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		//待确认
		//		orderListInfoatt.elementOpt("channelManageCode", "3206000000");

		JSONArray co = new JSONArray();

		JSONObject busiOrderatt = new JSONObject();

		JSONArray b0array = new JSONArray();
		JSONObject b0 = new JSONObject();

		JSONObject busiObjatt = new JSONObject();

		busiObjatt.elementOpt("objId", prodSpecId);
		busiObjatt.elementOpt("name", prodSpecId);
		List<OfferIntf> queryOfferInstByProdId = intfSMO.queryOfferInstByProdId(prod.getProdId());
		if (queryOfferInstByProdId != null && queryOfferInstByProdId.size() > 0)
			busiObjatt.elementOpt("name", queryOfferInstByProdId.get(0).getOfferSpecName());
		busiObjatt.elementOpt("instId", prod.getProdId());
		busiObjatt.elementOpt("accessNumber", accessNumber);
		busiObjatt.elementOpt("isComp", "N");

		JSONObject busiOrderInfoatt = new JSONObject();
		busiOrderInfoatt.elementOpt("seq", -1);
		busiOrderInfoatt.elementOpt("statusCd", "S");

		JSONObject boActionTypeatt = new JSONObject();
		boActionTypeatt.elementOpt("actionClassCd", 4);
		boActionTypeatt.elementOpt("boActionTypeCd", "1179");
		boActionTypeatt.elementOpt("name", "修改产品属性");

		JSONObject boCusts = new JSONObject();
		JSONArray boCustsarr = new JSONArray();
		JSONObject b1 = new JSONObject();
		b1.elementOpt("atomActionId", "-1");
		b1.elementOpt("itemSpecId", "13380379");
		b1.elementOpt("name", "高校信息");
		b1.elementOpt("value", schoolID);
		b1.elementOpt("state", "ADD");
		b1.element("statusCd", "S");
		boCustsarr.add(b1);

		if (gxProdItemIdByProdid != null && gxProdItemIdByProdid != -1) {
			JSONObject b2 = new JSONObject();
			b2.elementOpt("atomActionId", "-2");
			b2.elementOpt("itemSpecId", "13380379");
			b2.elementOpt("name", "高校信息");
			b2.elementOpt("value", gxProdItemIdByProdid);
			b2.elementOpt("state", "DEL");
			b2.element("statusCd", "S");
			boCustsarr.add(b2);
		}

		Long size = this.intfSMO.getOfferItemByAccNum(accessNumber);
		if (size == 0l) {
			JSONObject b3 = new JSONObject();
			b3.elementOpt("atomActionId", "-3");
			b3.elementOpt("itemSpecId", "20130048");
			b3.elementOpt("name", "校园标签");
			b3.elementOpt("value", 0);
			b3.elementOpt("state", "ADD");
			b3.element("statusCd", "S");
			boCustsarr.add(b3);
		}

		boCusts.elementOpt("boProdItems", boCustsarr);

		b0.elementOpt("busiOrderInfo", busiOrderInfoatt);
		b0.elementOpt("busiObj", busiObjatt);
		b0.elementOpt("areaId", areaId);
		b0.elementOpt("boActionType", boActionTypeatt);
		b0.elementOpt("data", boCusts);

		b0array.add(b0);

		busiOrderatt.elementOpt("busiOrder", b0array);
		busiOrderatt.elementOpt("colNbr", "-1");
		busiOrderatt.elementOpt("partyId", partyId);
		busiOrderatt.element("name", party.getPartyName());

		co.add(busiOrderatt);

		rootatt.elementOpt("custOrderList", co);

		rootatt.elementOpt("orderListInfo", orderListInfoatt);

		root.elementOpt("orderList", rootatt);
		logger.debug("产品属性变更json拼接完毕...");

		return root;
	}

	/**
	 * 拼装json对象
	 * @param iMap
	 * @return
	 */
	public JSONObject stringToJsonObj(Map<String, String> inMap) {

		//生成一个JSONObject对象
		String data = "{}";
		JSONObject orderListInfo = JSONObject.fromObject(data);

		int i = 0;
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", inMap.get("olTypeCd"));
		orderListInfo.put("staffId", inMap.get("staffId"));
		orderListInfo.put("channelId", inMap.get("channelId"));
		orderListInfo.elementOpt("systemId", "6090010023");
		orderListInfo.put("partyId", inMap.get("partyId"));
		orderListInfo.put("areaId", CommonDomain.AREA_ID);
		orderListInfo.put("statusCd", "S");

		Map<String, Object> busiOrderInfo = new HashMap<String, Object>();
		busiOrderInfo.put("seq", "-1");
		busiOrderInfo.put("staffId", inMap.get("staffId"));
		busiOrderInfo.put("appStartDt", "");
		busiOrderInfo.put("statusCd", "S");

		Map<String, Object> busiObj = new HashMap<String, Object>();
		busiObj.put("objId", inMap.get("prodSpecId"));
		busiObj.put("instId", inMap.get("prodId"));
		busiObj.put("accessNumber", inMap.get("accessNumber"));
		busiObj.put("name", "");
		busiObj.put("offerTypeCd", "");

		//6.put Map
		Map<String, Object> boActionType = new HashMap<String, Object>();
		boActionType.put("actionClassCd", "4");
		boActionType.put("boActionTypeCd", "7");
		boActionType.put("name", "服务信息变动");

		JSONObject dataJson = JSONObject.fromObject(data);

		List<JSONObject> boServOrders = new ArrayList<JSONObject>();
		JSONObject j = null;
		for (i = 0; i < 1; i++) {
			j = JSONObject.fromObject(data);
			j.put("servId", inMap.get("servId"));
			j.put("servSpecId", inMap.get("servSpecId"));
			boServOrders.add(j);
		}
		dataJson.put("boServOrders", boServOrders);

		List<JSONObject> boServs = new ArrayList<JSONObject>();
		for (i = 0; i < 1; i++) {
			j = JSONObject.fromObject(data);
			j.put("servId", inMap.get("servId"));
			j.put("state", inMap.get("state"));
			j.put("atomActionId", "-1");
			j.put("statusCd", "S");
			j.put("appStartDt", "");
			j.put("startDt", "");
			boServs.add(j);
		}
		dataJson.put("boServs", boServs);

		List<JSONObject> busiOrder = new ArrayList<JSONObject>();
		for (i = 0; i < 1; i++) {
			j = JSONObject.fromObject(data);
			j.put("busiOrderInfo", busiOrderInfo);
			j.put("busiObj", busiObj);
			j.put("areaId", CommonDomain.AREA_ID);
			j.put("linkFlag", "Y");
			j.put("boActionType", boActionType);
			j.put("data", dataJson);
			busiOrder.add(j);
		}

		List<JSONObject> custOrderList = new ArrayList<JSONObject>();
		for (i = 0; i < 1; i++) {
			j = JSONObject.fromObject(data);
			j.put("colNbr", "-1");
			j.put("partyId", inMap.get("partyId"));
			j.put("busiOrder", busiOrder);
			custOrderList.add(j);
		}
		//10.组合: 可以任意嵌套
		JSONObject json = JSONObject.fromObject(data);
		json.put("orderListInfo", orderListInfo);
		json.put("custOrderList", custOrderList);

		JSONObject superJson = JSONObject.fromObject(data);
		superJson.put("orderList", json);
		//System.out.println(superJson.toString());
		return superJson;

	}

}
