package com.linkage.bss.crm.ws.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.linkage.bss.commons.exception.BmoException;
import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.commons.util.StringUtil;
import com.linkage.bss.crm.commons.Cn2Spell;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.AccountMailing;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Party;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdCompItem;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferServItem;
import com.linkage.bss.crm.model.OfferSpec;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.model.PartyIdentity;
import com.linkage.bss.crm.model.PartyProfile;
import com.linkage.bss.crm.model.PaymentAccount;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.offerspec.smo.IOfferSpecSMO;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.store.smo.ISoStoreSMO;
import com.linkage.bss.crm.ws.common.CrmServiceErrorCode;
import com.linkage.bss.crm.ws.common.CrmServiceManagerConstants;
import com.linkage.bss.crm.ws.order.model.BoCreateParam;
import com.linkage.bss.crm.ws.order.model.BoCreateParamGrp;
import com.linkage.bss.crm.ws.util.WSUtil;

public class BusinessServiceOrderListFactoryImpl implements BusinessServiceOrderListFactory {

	private static Log logger = Log.getLog(BusinessServiceOrderListFactoryImpl.class);

	private IntfSMO intfSMO;

	private IOfferSpecSMO offerSpecSMO;

	private IOfferSMO offerSMO;

	private RscServiceSMO rscServiceSMO;

	private CustFacade custFacade;

	private ISoStoreSMO soStoreSMO;

	public Node chargeInfosTest = null;

	public void setSoStoreSMO(ISoStoreSMO soStoreSMO) {
		this.soStoreSMO = soStoreSMO;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setOfferSpecSMO(IOfferSpecSMO offerSpecSMO) {
		this.offerSpecSMO = offerSpecSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	public String interfaceId = null;
	public Map<String, String> servInstList = new HashMap<String, String>();

	@Override
	public JSONObject generateOrderList(Document request) {
		Node orderNode = WSUtil.getXmlNode(request, "//request/order");
		String areaCode = WSUtil.getXmlNodeText(request, "//request/areaCode");
		String areaId = WSUtil.getXmlNodeText(request, "//request/areaId");
		String channelId = WSUtil.getXmlNodeText(request, "//request/channelId");
		String staffId = WSUtil.getXmlNodeText(request, "//request/staffId");
		Date date = intfSMO.getCurrentTime();
		String dateStr = DateUtil.getFormatTimeString(date, "yyyy-MM-dd HH:mm:ss");
		String systemId = WSUtil.getXmlNodeText(request, "//request/order/systemId");
		//add wanghongli 20130326  所有的停机和复机 systemId 置空 走动作链
		String orderTypeId = WSUtil.getXmlNodeText(request, "//request/order/orderTypeId");
		chargeInfosTest = WSUtil.getXmlNode(request, "//request/order/listChargeInfo");
		//chargeInfos = WSUtil.getXmlNode(request, "//request/order/listChargeInfo");
		//request.selectSingleNode("//request/order/listChargeInfo");
		if ("19".equals(orderTypeId) || "20".equals(orderTypeId)) {
			systemId = "";
		}
		String remark = WSUtil.getXmlNodeText(request, "//request/order/remark");
		interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
		String olTypeCd = "2";
		formatOrderDate(orderNode);
		// 根据入参构造购物车的JSON对象
		JSONObject rootObj = new JSONObject();
		JSONObject orderListObj = new JSONObject();
		if (request.selectSingleNode("//request/order/chargeInfo") != null) {
			String charge = WSUtil.getXmlNodeText(request, "//request/order/chargeInfo/charge");// 补换卡的钱
			String appCharge = WSUtil.getXmlNodeText(request, "//request/order/chargeInfo/appCharge");
			JSONObject payInfoJs = new JSONObject();
			JSONArray payInfoListJs = new JSONArray();
			payInfoJs.elementOpt("amount", charge);
			payInfoJs.elementOpt("payMethod", "500");
			payInfoJs.elementOpt("appendInfo", appCharge);
			payInfoListJs.add(payInfoJs);
			orderListObj.elementOpt("payInfoList", payInfoListJs);
			logger.debug("---------构造payInfoList 完成！");
		}
		//pad  构造PAY_INFO_LIST
		//	if("6090010023".equals(systemId)||"6090010022".equals(systemId)){
		Node payInfos = WSUtil.getXmlNode(request, "//request/order/payInfoList");
		
		JSONArray payNumListJs = new JSONArray();
		JSONObject payNumJs = new JSONObject();
		if (payInfos != null) {
			List<Node> payInfoList = payInfos.selectNodes("payInfo");
			JSONArray payInfoListJs = new JSONArray();
			for (int i = 0; payInfoList.size() > i; i++) {
				JSONObject payInfoJs = new JSONObject();
				String charge = WSUtil.getXmlNodeText(payInfoList.get(i), "amount");// 补换卡的钱
				String appCharge = WSUtil.getXmlNodeText(payInfoList.get(i), "appendInfo");
				String payMethod = WSUtil.getXmlNodeText(payInfoList.get(i), "method");
				payInfoJs.elementOpt("amount", charge);
				payInfoJs.elementOpt("payMethod", payMethod);
				payInfoJs.elementOpt("appendInfo", appCharge);
				payInfoListJs.add(payInfoJs);
				String outTradeNo = WSUtil.getXmlNodeText(payInfoList.get(i), "outTradeNo");
			    if(outTradeNo!=null && !outTradeNo.equals("")){
			    	payNumJs.element("itemSpecId", "120000001");
			    	payNumJs.element("name","聚合收银台支付流水");
			    	payNumJs.element("value",WSUtil.getXmlNodeText(payInfoList.get(i), "outTradeNo"));
			    	payNumJs.element("state", "ADD");
			    	payNumJs.element("statusCd", "S");
			    	payNumListJs.add(payNumJs);
			    }
			}

			orderListObj.elementOpt("payInfoList", payInfoListJs);

		}
		//	}
		JSONObject orderListInfoJs = new JSONObject();
		orderListInfoJs.elementOpt("olId", "-1");
		orderListInfoJs.elementOpt("olNbr", "-1");
		orderListInfoJs.elementOpt("olTypeCd", olTypeCd);
		orderListInfoJs.elementOpt("remark", remark);
		orderListInfoJs.elementOpt("staffId", staffId);
		orderListInfoJs.elementOpt("channelId", channelId);
		orderListInfoJs.elementOpt("systemId", systemId);
		orderListInfoJs.elementOpt("areaId", areaId);
		orderListInfoJs.elementOpt("areaCode", areaCode);
		orderListInfoJs.elementOpt("soDate", dateStr);
		orderListInfoJs.elementOpt("statusCd", "S");
		orderListInfoJs.elementOpt("statusDt", dateStr);

		if(payNumListJs.size()>0){
			orderListInfoJs.elementOpt("orderListAttrs", payNumListJs);
		}
		
		JSONArray custOrderListArr = new JSONArray();

		JSONObject custOrderListJs = new JSONObject();
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator(); // 把业务动作的seq和原子动作的id复位，从-1开始
		BoCreateParamGrp mainBoCreateParamGrp = createBoCreateParamGrp(orderNode, areaId, dateStr, null,
				boSeqCalculator);
		BoCreateParam mainBoCreateParam = mainBoCreateParamGrp.getSelfBoCreateParam();
		Long partyId = mainBoCreateParam.getPartyId();
		logger.debug("partyId=={}", partyId);
		orderListInfoJs.elementOpt("partyId", partyId);
		orderListObj.elementOpt("orderListInfo", orderListInfoJs);
		logger.debug("---------构造orderListInfo 完成！");

		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId);
		JSONArray busiOrderArr = null;
		// 暂时保存内层订单的业务动作
		JSONArray innerBusiOrderArr = new JSONArray();
		// 外层订单的业务动作后保存
		if (mainBoCreateParam.getIsCompOffer()) {
			// 如果是套餐  套餐为1.0的概念，先不用看
			busiOrderArr = createCompOfferBusiOrders(orderNode, mainBoCreateParamGrp, boSeqCalculator);
		} else {
			busiOrderArr = createBusiOrders(orderNode, mainBoCreateParamGrp, boSeqCalculator, busiOrderArr);
		}
		// 将业务动作集结起来
		busiOrderArr.addAll(innerBusiOrderArr);
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		custOrderListArr.add(custOrderListJs);
		orderListObj.elementOpt("custOrderList", custOrderListArr);
		rootObj.element("orderList", orderListObj);
		return rootObj;
	}

	/**
	 * 构建产生业务动作的参数组，每个产品的业务动作一个组。属于组合产品成员的产品，会有关联组合产品的组
	 * 
	 * @param order
	 * @param areaCode
	 * @param compBoCreateParam
	 * @return
	 */
	private BoCreateParamGrp createBoCreateParamGrp(Node order, String areaId, String curDate,
			BoCreateParam compBoCreateParam, BoSeqCalculator boSeqCalculator) {
		BoCreateParamGrp boCreateParamGrp = new BoCreateParamGrp();
		BoCreateParam selfBoCreateParam = createBoCreateParam(order, areaId, curDate, boSeqCalculator);
		boCreateParamGrp.setSelfBoCreateParam(selfBoCreateParam);
		boCreateParamGrp.setCompBoCreateParam(compBoCreateParam);

		return boCreateParamGrp;
	}

	/**
	 * 构建产生业务动作的参数
	 * 
	 * @param order
	 * @param areaCode
	 * @return
	 */
	private BoCreateParam createBoCreateParam(Node order, String areaId, String curDate, BoSeqCalculator boSeqCalculator) {
		String prodSpecIdStr = order.selectSingleNode("./prodSpecId").getText();
		String orderTypeIdsStr = order.selectSingleNode("./orderTypeId").getText();
		// 根据产品规格判断产品是否可以进行本地业务
		String[] orderTypeIds = orderTypeIdsStr.split(",");
		for(String s :orderTypeIds){
			if (!"17".equals(s)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("prodSpecId", prodSpecIdStr);
				param.put("boActionTypeCd", s);
				boolean isOk = intfSMO.queryProdSpec2BoActionTypeCdBYprodAndAction(param);
				if (!isOk) {
					throw new BmoException(-1, "当前产品规格不允许做此类操作，请核对");
				}
			}
		}
		
		Node prodIdNode = order.selectSingleNode("./prodId");
		Node accessNumberNode = order.selectSingleNode("./accessNumber");
		Node offeringIdNode = order.selectSingleNode("./offeringId");
		Node coLinkManNode = order.selectSingleNode("./coLinkMan");
		Node coLinkNbrNode = order.selectSingleNode("./coLinkNbr");
		Node roleIdNode = order.selectSingleNode("./roleId");
		Node installDateNode = order.selectSingleNode("./installDate");
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		Node installAreaIdNode = order.selectSingleNode("./installAreaId");
		List<Element> coItemList = order.selectNodes("./prodPropertys/property[@propertyType='co']");// 订单属性（业务动作属性）
		Node assistMan = order.selectSingleNode("./assistMan");
		// 关联角色
		String relaRoleCd = null;
		// 角色业务动作类型
		String roleboActionType = null;
		// 角色动作状态
		String roleState = null;
		// 是否是新装
		boolean isNew = false;
		String prodAreaId = null;// 如果不是新装 需要取产品受理的地区ID
		if (roleIdNode != null && roleIdNode.getText() != null && !"".equals(roleIdNode.getText().trim())) {
			relaRoleCd = roleIdNode.getText().trim();
		}
		
		for (int i = 0; i < orderTypeIds.length; i++) {
			String orderTypeIdStr = orderTypeIds[i];
			int orderTypeId = Integer.parseInt(orderTypeIdStr);
			switch (orderTypeId) {
			case 1: // 新装
				isNew = true;
				break;
			case 8: // 纳入电话虚拟网
			case 55: // 纳入小灵通虚拟网
			case 998: // 成员纳入
			case 1194: // 加入本地混合计费虚拟网
			case 1195: // 加入省内混合虚拟网
			case 1196: // 加入省内混合计费虚拟网
			case 1197: // 加入CDMA虚拟网
			case 1198: // 加入PHS计费虚拟网
			case 1199: // 加入CDMA计费虚拟网
			case 1201: // 加入固话计费虚拟网
			case 1202: // 加入省内UNB
			case 1203: // 加入跨省混合虚拟网
			case 1204: // 加入本地UNB
			case 1205: // 加入跨省UNB
			case 1221: // 加入本地混合虚拟网
			case 1225: // 纳入跨省混合军区虚拟网
			case 1233: // 加入虚拟网(虚拟)
			case 1227: // 纳入企业总机组合
				roleboActionType = orderTypeIdStr;
				roleState = "ADD";
				break;
			case 9: // 退出电话虚拟网
			case 56: // 退出小灵通虚拟网
			case 999: // 成员退出
			case 1207: // 退出本地混合计费虚拟网
			case 1208: // 退出省内混合虚拟网
			case 1209: // 退出省内混合计费虚拟网
			case 1210: // 退出CDMA虚拟网
			case 1211: // 退出PHS计费虚拟网
			case 1212: // 退出CDMA计费虚拟网
			case 1214: // 退出固话计费虚拟网
			case 1215: // 退出省内UNB
			case 1216: // 退出跨省混合虚拟网
			case 1217: // 退出本地UNB
			case 1218: // 退出跨省UNB
			case 1222: // 退出本地混合虚拟网
			case 1226: // 退出跨省混合军区虚拟网
			case 1234: // 退出虚拟网(虚拟)
			case 1228: // 退出企业总机组合
				roleboActionType = orderTypeIdStr;
				roleState = "DEL";
				break;
			default:
				break;
			}
		}

		// 受理的商品，查找出这次业务对应的销售品规格
		// String offeringIdstr = null;
		// if (offeringIdNode != null) {
		// offeringIdstr = offeringIdNode.getText();
		// }
		Long offerSpecId = null;
		OfferSpec offerspec = null;
		Integer basePricePlanCd = null;
		String feeType = null;
		boolean isOfferSpec = false;// 传入的prodSpecIdStr是否是销售品规格
		// boolean isCompOffer = isCompOffer(prodSpecIdStr);// 套餐
		// if (isCompOffer) {
		// // 根据传入的prodSpecIdStr去查找销售品规格
		// offerspec =
		// offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(prodSpecIdStr));
		// if (offerspec != null) {
		// isOfferSpec = true;
		// }
		// } else {
		// offerspec = getMainOfferSpecId(pricePlanPakNode);
		// }

		Long prodId = null;
		if (prodIdNode != null && StringUtils.isNotBlank(prodIdNode.getText())) {
			prodId = Long.valueOf(prodIdNode.getText());
		} else {
			if (accessNumberNode != null && StringUtils.isNotBlank(accessNumberNode.getText())) {
				OfferProd prodInfo = intfSMO.getProdByAccessNumber(accessNumberNode.getText());
				if (prodInfo != null) {
					prodId = prodInfo.getProdId();
				}
			}
		}
		if (offerspec != null && offerspec.getFeeType() != null) {
			offerSpecId = offerspec.getOfferSpecId();
			feeType = offerspec.getFeeType().toString();
		}
		// 当前受理业务的客户
		Long partyId = getPartyId(order, isNew, areaId);

		// 联系人
		String linkMan = null;
		if (coLinkManNode != null && coLinkManNode.getText() != null) {
			linkMan = coLinkManNode.getText().trim();
		}
		// 联系电话
		String linkNbr = null;
		if (coLinkNbrNode != null && coLinkNbrNode.getText() != null) {
			linkNbr = coLinkNbrNode.getText().trim();
		}
		// 指定业务时间
		String installDate = null;
		if (installDateNode != null && installDateNode.getText() != null
				&& !"".equals(installDateNode.getText().trim())) {
			installDate = installDateNode.getText();
		}

		BoCreateParam boCreateParam = new BoCreateParam();
		boCreateParam.setProdSpecId(prodSpecIdStr);
		boCreateParam.setPsOfferSpecId(offerSpecId);
		boCreateParam.setProdId(prodId);
		boCreateParam.setLinkMan(linkMan);
		boCreateParam.setLinkNbr(linkNbr);
		// 处理协销售人信息
		if (assistMan != null && !assistMan.getText().equals("")) {
			boCreateParam.setAssistMan(assistMan.getText());
		}
		boCreateParam.setCoItemList(coItemList);
		boCreateParam.setPartyId(partyId);
		boCreateParam.setInstallDate(installDate);
		boCreateParam.setRelaRoleCd(relaRoleCd);
		boCreateParam.setRoleboActionType(roleboActionType);
		boCreateParam.setRoleState(roleState);
		if (installAreaIdNode != null && !installAreaIdNode.getText().equals("")) {
			boCreateParam.setAreaId(installAreaIdNode.getText());
		} else if (prodAreaId != null) {
			boCreateParam.setAreaId(prodAreaId);// 取产品实例地区
		} else {
			boCreateParam.setAreaId(areaId);// 取受理地区
		}
		boCreateParam.setCurDate(curDate);
		boCreateParam.setFeeType(feeType);
		boCreateParam.setIsCompOffer(false);
		boCreateParam.setNew(isNew);// 是否新装标识
		if ("14".equals(orderTypeIdsStr)) {
			String accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodIdNode.getText()));
			Account acctInfo = intfSMO.findAcctByAccNbr(accessNumber, Integer.valueOf(prodSpecIdStr));
			if (acctInfo != null && acctInfo.getAcctCd() != null) {
				boCreateParam.setAcctCd(acctInfo.getAcctCd().toString());
				((Element) order).addElement("acctCd").setText(acctInfo.getAcctCd());
			} else {
				boCreateParam.setNeedCreateAcct(true);
				boCreateParam.setAcctCd(boSeqCalculator.getNextAcctCdString());
				boCreateParam.setAcctId(Long.valueOf(boSeqCalculator.getNextAcctIdInteger()));
			}
		}

		return boCreateParam;
	}

	/**
	 * 从所有的资费计划中查询主销品规格
	 * 
	 * @param pricePlanPakNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private OfferSpec getMainOfferSpecId(Node pricePlanPakNode) {
		if (pricePlanPakNode == null) {
			return null;
		}

		List pricePlanNodes = pricePlanPakNode.selectNodes("./pricePlan");
		if (CollectionUtils.isEmpty(pricePlanNodes)) {
			return null;
		}

		for (Node pricePlanNode : (List<Node>) pricePlanNodes) {
			if (pricePlanNode.selectSingleNode("./pricePlanCd") == null) {
				continue;
			}
			String pricePlanCdStr = pricePlanNode.selectSingleNode("./pricePlanCd").getText();
			if (StringUtils.isNotBlank(pricePlanCdStr)) {
				OfferSpec offerSpec = offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(pricePlanCdStr));
				if (offerSpec != null && offerSpec.getOfferTypeCd().intValue() == CommonDomain.OFFER_TYPE_CD_INT1) {
					return offerSpec;
				}
			}
		}
		return null;
	}

	/**
	 * 获取受理客户
	 * 
	 * @param order
	 * @return
	 */
	private Long getPartyId(Node order, boolean isNew, String areaId) {
		Long partyId = null;
		Node partyIdNode = order.selectSingleNode("./partyId");
		Node acctCdNode = order.selectSingleNode("./acctCd");
		String prodSpecId = order.selectSingleNode("./prodSpecId").getText();
		Node accessNumberNode = order.selectSingleNode("./accessNumber");
		String accessNumber = null;
		if (accessNumberNode != null) {
			accessNumber = accessNumberNode.getText();
		}
		// 当前受理业务的客户
		String partyIdstr = null;
		if (partyIdNode != null) {
			partyIdstr = partyIdNode.getText();
		}
		if (partyIdstr != null && !("").equals(partyIdstr.trim())) {
			partyId = new Long(partyIdstr.trim());
		}
		if (partyId == null) {
			if (isNew) {
				if (acctCdNode != null) {
					String acctCd = acctCdNode.getText();
					Party party = custFacade.getPartyByAcctCd(acctCd);
					partyId = party != null ? party.getPartyId() : null;
				}
			} else {
				if (prodSpecId != null && StringUtils.isNotBlank(accessNumber)) {
					Party party = custFacade.getPartyByAccessNumber(accessNumber);
					partyId = party != null ? party.getPartyId() : null;
				}
			}
		}
		return partyId;
	}

	/**
	 * 构造业务动作的json数组
	 * 
	 * @param order
	 * @param boCreateParamGrp
	 * @param subBoCreateParamGrps
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONArray createCompOfferBusiOrders(Node order, BoCreateParamGrp boCreateParamGrp,
			BoSeqCalculator boSeqCalculator) {
		String orderTypeIdStr = order.selectSingleNode("./orderTypeId").getText();
		Node newPartyIdNode = order.selectSingleNode("./newPartyId"); // 过户的新客户ID
		// 子节点
		Node ownerInfosNode = order.selectSingleNode("./ownerInfos");
		Node chargePakNode = order.selectSingleNode("./chargePak"); // 一次性费用信息节点
		Node couponsNode = order.selectSingleNode("./coupons"); // 物品信息
		Node acctCdNode = order.selectSingleNode("./acctCd");
		BoCreateParam boCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String areaId = boCreateParam.getAreaId();
		// 受理的产品
		// 当前受理业务的客户
		Long partyId = boCreateParam.getPartyId();

		JSONArray busiOrderArr = new JSONArray();
		// 如果需要新增帐户就先生成账务动作
		if (boCreateParam.isNeedCreateAcct()) {// 如果需要生成帐户
			if (acctCdNode == null) {
				acctCdNode = ((Element) order).addElement("acctCd");
			}
			// 需要添加新增帐户的业务动作
			acctCdNode.setText(boCreateParam.getAcctCd());
			JSONObject createAcctBusiOrder = createAddAccountBusiOrder(order, areaId, boCreateParam, boSeqCalculator);
			busiOrderArr.add(createAcctBusiOrder);
		}
		String[] orderTypeIds = orderTypeIdStr.split(",");
		for (int i = 0; i < orderTypeIds.length; i++) {
			int orderTypeId = Integer.parseInt(orderTypeIds[i]);
			switch (orderTypeId) {
			case 1: // 新装
				JSONArray newOrderJsonArrObj = processCompOfferNewOrder(order, boCreateParam, boSeqCalculator);
				busiOrderArr.addAll(newOrderJsonArrObj);
				break;

			case 5:// 修改客户信息
				if (ownerInfosNode != null) {
					JSONObject busiOrderJs = processOwnerInfos(ownerInfosNode, areaId, partyId, boSeqCalculator,
							boCreateParam);
					busiOrderArr.add(busiOrderJs);
				}
				break;
			case 11:// 过户
				if (newPartyIdNode != null) {
					JSONArray ownerChangeJsonArrObj = processOfferOwnerChange(newPartyIdNode, boCreateParam,
							boSeqCalculator);
					busiOrderArr.addAll(ownerChangeJsonArrObj);
				}
				break;
			case 1000: // 成员变更
				JSONArray mainJsonArrObj = processOfferMemberChangeMain(boCreateParamGrp, areaId, boSeqCalculator);
				busiOrderArr.addAll(mainJsonArrObj);
				break;
			default:
				// 暂时不支持
				break;
			}
		}
		// TODO：如果费用不为空，则进行一次性费用的处理
		if (chargePakNode != null) {
			logger.debug("-----------------有一次性费用需要处理");
		}
		return busiOrderArr;
	}

	// 处理新订购(组合套餐)
	@SuppressWarnings("unchecked")
	private JSONArray processCompOfferNewOrder(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator) {
		Long partyId = boCreateParam.getPartyId();
		String areaId = boCreateParam.getAreaId();
		String date = boCreateParam.getCurDate();
		String prodSpecId = boCreateParam.getProdSpecId();
		JSONArray busiOrderArrJs = new JSONArray();
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		// 处理资费信息 ：目前不会受理销售品套销售品的情况，所以 只会传入一个标准资费
		Element pricePlanNode = (Element) pricePlanPakNode.selectSingleNode("./pricePlan");
		String pricePlanCdStr = pricePlanNode.selectSingleNode("./pricePlanCd").getText();
		Node startDtNode = pricePlanNode.selectSingleNode("./startDt");
		Node endDtNode = pricePlanNode.selectSingleNode("./endDt");
		Node properties = pricePlanNode.selectSingleNode("./properties");
		String startDt = "";
		String endDt = "";
		if (startDtNode != null) {
			startDt = ("").equals(startDtNode.getText().trim()) ? startDt : startDtNode.getText().trim();
		}
		if (endDtNode != null) {
			endDt = ("").equals(endDtNode.getText().trim()) ? endDt : endDtNode.getText().trim();
		}
		OfferSpec offerSpec = null;
		String offerSpecId = null;
		String offerTypeCd = null;
		if (pricePlanCdStr != null && !"".equals(pricePlanCdStr.trim())) {
			pricePlanCdStr = pricePlanCdStr.trim();
			if (prodSpecId != null && !prodSpecId.equals("")) {
				if (offerSpec == null) {
					// 检查prodSpecId传入的是否是销售品规格
					offerSpec = offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(pricePlanCdStr));
				}
			}
		}
		if (offerSpec == null) {
			throw new BmoException(-1, "根据传入的pricePlanCdStr：" + pricePlanCdStr + "无法找到销售品规格");
		}
		offerSpecId = offerSpec.getOfferSpecId().toString();
		offerTypeCd = offerSpec.getOfferTypeCd().toString();
		// 下面开始处理该销售品的订购
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt(CommonDomain.XSD_SEQ_DICTIONARY, seq);
		busiOrderInfoJs.elementOpt(CommonDomain.XSD_STATUS_CD_DICTIONARY,
				CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
		busiOrderInfoJs.elementOpt("appStartDt", startDt);
		busiOrderInfoJs.elementOpt("appEndDt", endDt);
		busiOrderJs.elementOpt(CommonDomain.XSD_BUSI_ORDER_INFO_DICTIONARY, busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt(CommonDomain.XSD_OBJ_ID_DICTIONARY, offerSpecId);
		busiObjJs.elementOpt("instId", boCreateParam.getProdId());
		busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "3");
		boActionTypeJs.elementOpt("boActionTypeCd", "S1");
		boActionTypeJs.elementOpt("name", "订购");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");
		JSONObject dataJs = new JSONObject();
		// 处理销售品属主关系
		// 构造boOffers
		JSONArray boOfferArrJs = new JSONArray();
		JSONObject boOfferJs = new JSONObject();
		boOfferJs.elementOpt("state", "ADD");
		boOfferJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boOfferJs.elementOpt("statusCd", "S");
		boOfferJs.elementOpt("appStartDt", startDt);
		boOfferJs.elementOpt("appEndDt", endDt);
		boOfferArrJs.add(boOfferJs);
		dataJs.elementOpt("boOffers", boOfferArrJs);
		logger.debug("---------boOffers 完成！");
		List propertyNodes = null;
		if (properties != null) {
			propertyNodes = properties.selectNodes("./property");
		}
		if (propertyNodes != null && !propertyNodes.isEmpty()) {
			JSONArray ooParamArrJs = new JSONArray();
			for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
				Node propertie = (Node) itr1.next();
				String offerSpecParamId = propertie.selectSingleNode("./id").getText();
				String newValue = propertie.selectSingleNode("./value").getText();
				JSONObject ooParamJs = new JSONObject();
				Long offerParamId = null;
				offerParamId = Long.valueOf(boSeqCalculator.getNextOfferParamIdInteger());
				ooParamJs.elementOpt("offerParamId", offerParamId);
				ooParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
				ooParamJs.elementOpt("itemSpecId", offerSpecParamId);
				ooParamJs.elementOpt("value", newValue);
				ooParamJs.elementOpt("state", CommonDomain.ADD);
				ooParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				ooParamJs.elementOpt("statusCd", "S");
				ooParamJs.elementOpt("appStartDt", startDt);
				ooParamJs.elementOpt("appEndDt", endDt);
				ooParamArrJs.add(ooParamJs);
			}
			dataJs.elementOpt("ooParams", ooParamArrJs);
			logger.debug("---------ooParams 完成！");
		}
		// 构造ooOwners
		JSONArray ooOwnerArrJs = new JSONArray();
		JSONObject ooOwnerJs = new JSONObject();
		ooOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooOwnerJs.elementOpt("partyId", partyId.toString());
		ooOwnerJs.elementOpt("state", "ADD");
		ooOwnerJs.elementOpt("statusCd", "S");
		ooOwnerArrJs.add(ooOwnerJs);
		dataJs.elementOpt("ooOwners", ooOwnerArrJs);
		JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
		// 联系人和联系电话处理等订单属性
		if (!busiOrderAttrArrObj.isEmpty()) {
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);
		return busiOrderArrJs;
	}

	/**
	 * 处理销售品属主关系变更
	 * 
	 * @param newPartyIdNode
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONArray processOfferOwnerChange(Node newPartyIdNode, BoCreateParam boCreateParam,
			BoSeqCalculator boSeqCalculator) {
		Offer offer = null;
		JSONArray busiOrderArrJs = new JSONArray();
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt(CommonDomain.XSD_AREA_ID_DICTIONARY, boCreateParam.getAreaId());
		busiOrderJs.elementOpt("linkFlag", "Y");
		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt(CommonDomain.XSD_SEQ_DICTIONARY, seq);
		busiOrderInfoJs.elementOpt(CommonDomain.XSD_STATUS_CD_DICTIONARY,
				CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
		busiOrderJs.elementOpt(CommonDomain.XSD_BUSI_ORDER_INFO_DICTIONARY, busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");
		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt(CommonDomain.XSD_OBJ_ID_DICTIONARY, offer.getOfferSpecId());
		busiObjJs.elementOpt("instId", boCreateParam.getProdId());// tangminjun
		busiObjJs.elementOpt("offerTypeCd", "1");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "3");
		boActionTypeJs.elementOpt("boActionTypeCd", "S5");
		boActionTypeJs.elementOpt("name", "改归属");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 构造ooOwners
		JSONArray ooOwnerArrJs = new JSONArray();
		JSONObject ooNewOwnerJs = new JSONObject();
		ooNewOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooNewOwnerJs.elementOpt("partyId", newPartyIdNode.getText());
		ooNewOwnerJs.elementOpt("state", "ADD");
		ooNewOwnerJs.elementOpt("statusCd", "S");
		ooOwnerArrJs.add(ooNewOwnerJs);
		JSONObject ooOldOwnerJs = new JSONObject();
		ooOldOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooOldOwnerJs.elementOpt("partyId", offer.getOffer2Partys().get(0).getPartyId());
		ooOldOwnerJs.elementOpt("state", "DEL");
		ooOldOwnerJs.elementOpt("statusCd", "S");
		ooOwnerArrJs.add(ooOldOwnerJs);
		dataJs.elementOpt("ooOwners", ooOwnerArrJs);
		// 订单属性
		JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
		if (!busiOrderAttrArrObj.isEmpty()) {
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);
		return busiOrderArrJs;
	}

	// 处理销售品实例成员变更信息
	private JSONArray processOfferMemberChangeMain(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderMemberChange = createBusiOrderOfferMemberChangeMain(boCreateParamGrp, areaId,
				boSeqCalculator);
		busiOrderArrJs.addAll(busiOrderMemberChange);
		return busiOrderArrJs;
	}

	// 构建销售品实例成员变更动作数据
	private JSONArray createBusiOrderOfferMemberChangeMain(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		BoCreateParam compBoCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		Long offerSpecId = compBoCreateParam.getPsOfferSpecId();
		String offerId = compBoCreateParam.getProdId().toString();

		JSONArray busiOrderArrJs = new JSONArray();

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", offerSpecId);
		busiObjJs.elementOpt("instId", offerId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "3");
		boActionTypeJs.elementOpt("boActionTypeCd", "S3");
		boActionTypeJs.elementOpt("name", "成员变更");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 处理订单属性
		JSONArray busiOrderAttrsJs = buildBusiOrderAttrObj(compBoCreateParam);
		dataJs.elementOpt("busiOrderAttrs", busiOrderAttrsJs);
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);

		return busiOrderArrJs;
	}

	/**
	 * 构造业务动作的json数组
	 * 
	 * @param order
	 * @param areaCode
	 * @param channelId
	 * @param staffCode
	 * @param date
	 * @return
	 */
	private JSONArray createBusiOrders(Node order, BoCreateParamGrp boCreateParamGrp, BoSeqCalculator boSeqCalculator,
			JSONArray mainOrderBusiOrderArr) {
		String orderTypeIdStr = order.selectSingleNode("./orderTypeId").getText();
		Node acctCdNode = order.selectSingleNode("./acctCd");
		Node bindPayNumberNode = order.selectSingleNode("./bindPayForNbr");
		Node bindNumberProdSpec = order.selectSingleNode("./bindNumberProdSpec");
		Node newPartyIdNode = order.selectSingleNode("./newPartyId"); // 过户的新客户ID
		Node passwordNode = order.selectSingleNode("./password");
		// 子节点
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		Node servicePakNode = order.selectSingleNode("./servicePak");
		Node prodPropertysNode = order.selectSingleNode("./prodPropertys");
		Node prodCompPropertysNode = order.selectSingleNode("./prodCompPropertys");
		Node ownerInfosNode = order.selectSingleNode("./ownerInfos");
		Node couponsNode = order.selectSingleNode("./coupons"); // 物品信息
		Node tdsNode = order.selectSingleNode("./tds"); // 终端信息
		Node accoutNode = order.selectSingleNode("./account"); // 帐单寄送
		Node chargePakNode = order.selectSingleNode("./chargePak"); // 一次性费用信息节点
		List<Node> assistManInfoList = order.selectNodes("assistManInfoList/assistManInfo");
		
		//根据平台编码，拼装接单点信息
		List<Node> busiOrderTimeList = order.selectNodes("./busiOrderTimeList/property");
		Node systemIdNode = order.selectSingleNode("./systemId");
		
		//价格节点
		Node chargeInfos =null;
		chargeInfos = WSUtil.getXmlNode(order, "//order/listChargeInfo");;
		
		BoCreateParam boCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		BoCreateParam compBoCreateParam = boCreateParamGrp.getCompBoCreateParam();
		String prodSpecIdStr = boCreateParam.getProdSpecId();
		String date = boCreateParam.getCurDate();
		String areaId = boCreateParam.getAreaId();
		// 受理的产品
		Long prodId = boCreateParam.getProdId();
		String prodIdStr = boCreateParam.getProdId() != null ? boCreateParam.getProdId().toString() : null;
		JSONObject busiOrderJs = null;
		// 当前受理业务的客户
		Long partyId = boCreateParam.getPartyId();

		JSONArray busiOrderArr = new JSONArray();
		// 如果需要新增帐户就先生成账务动作
		if (boCreateParam.isNeedCreateAcct()) {// 如果需要生成帐户
			if (acctCdNode == null) {
				acctCdNode = ((Element) order).addElement("acctCd");
			}

			// 如果订单是子单而且外层单已经生成了帐户，就使用外层单生成的帐户不再新增帐户
			if (compBoCreateParam != null && compBoCreateParam.isNeedCreateAcct()) {
				acctCdNode.setText(compBoCreateParam.getAcctCd());
				boCreateParam.setAcctCd(compBoCreateParam.getAcctCd());
				boCreateParam.setAcctId(compBoCreateParam.getAcctId());
				boCreateParam.setPaymentAccountId(compBoCreateParam.getPaymentAccountId());
			} else {
				// 需要添加新增帐户的业务动作
				acctCdNode.setText(boCreateParam.getAcctCd());
				JSONObject createAcctBusiOrder = createAddAccountBusiOrder(order, areaId, boCreateParam,
						boSeqCalculator);
				busiOrderArr.add(createAcctBusiOrder);
			}
		}
		String[] orderTypeIds = orderTypeIdStr.split(",");
		for (int i = 0; i < orderTypeIds.length; i++) {
			int orderTypeId = Integer.parseInt(orderTypeIds[i]);
			switch (orderTypeId) {
			case 1: // 新装 （作废）
				JSONArray newOrderJsonArrObj = processNewOrder(order, boCreateParam, boSeqCalculator);
				busiOrderArr.addAll(newOrderJsonArrObj);
				break;

			case 5:// 修改客户信息
				if (ownerInfosNode != null) {
					busiOrderJs = processOwnerInfos(ownerInfosNode, areaId, partyId, boSeqCalculator, boCreateParam);
					busiOrderArr.add(busiOrderJs);
				}
				break;

			case 6:// 修改帐务信息
				JSONArray accountJsonArrObj = processAccount(acctCdNode, bindPayNumberNode, bindNumberProdSpec,
						accoutNode, prodId, areaId, boSeqCalculator, boCreateParam);
				busiOrderArr.addAll(accountJsonArrObj);
				break;

			case 11:// 过户
				if (newPartyIdNode != null) {
					JSONArray ownerChangeJsonArrObj = processOwnerChange(newPartyIdNode, acctCdNode, prodSpecIdStr,
							prodId, areaId, boSeqCalculator, boCreateParam);
					busiOrderArr.addAll(ownerChangeJsonArrObj);
				}
				break;

			case 14:// 补机/补卡
				if (tdsNode != null) {
					JSONObject tdsJsonObj = processTds(order, boCreateParam, boSeqCalculator);
					busiOrderArr.add(tdsJsonObj);
				}
				break;
			//add by helinglong 20140424
			case 1184:// 备卡激活
				if (tdsNode != null) {
					JSONObject tdsJsonObj = processTdsActive(order, boCreateParam, boSeqCalculator);
					busiOrderArr.add(tdsJsonObj);
				}
				break;

			case 17: // 附属销售品变更
				if (pricePlanPakNode != null) {
					JSONArray ppJsonArrObj = processPricePlanPak(pricePlanPakNode, boCreateParam, boSeqCalculator,
							assistManInfoList,busiOrderTimeList,systemIdNode,chargeInfos);
					busiOrderArr.addAll(ppJsonArrObj);
				}
				if (servicePakNode != null) {
					JSONArray serJsonArrObj = processServicePak(servicePakNode, prodSpecIdStr, prodId, areaId, date,
							boSeqCalculator);
					busiOrderArr.addAll(serJsonArrObj);
				}
				break;

			case 18: // 改产品密码
				if (passwordNode != null) {
					JSONObject passwordJsonObj = processPassword(passwordNode, prodSpecIdStr, prodId, areaId,
							boSeqCalculator);
					busiOrderArr.add(passwordJsonObj);
				}
				break;
			case 201407: // 重置产品密码
				if (passwordNode != null) {
					JSONObject passwordJsonObj = processPasswordReset(passwordNode, prodSpecIdStr, prodId, areaId,
							boSeqCalculator);
					busiOrderArr.add(passwordJsonObj);
				}
				break;
			case 51: // 改速率
				if (prodPropertysNode != null) {
					JSONObject speedRateJsonObj = processSpeedRate(prodPropertysNode, prodSpecIdStr, prodId, areaId,
							boSeqCalculator);
					busiOrderArr.add(speedRateJsonObj);
				}
				break;
			case 54:// 改短号
				if (prodCompPropertysNode != null) {
					JSONObject prodPropertysJsonObj = processProdCompPropertys(prodCompPropertysNode, prodSpecIdStr,
							prodId, areaId, boSeqCalculator);
					busiOrderArr.add(prodPropertysJsonObj);
				}
				break;
			case 1179: // 修改产品属性
				if (prodPropertysNode != null) {
					JSONObject prodPropertysJsonObj = processProdPropertys(prodPropertysNode, prodSpecIdStr, prodId,
							areaId, boSeqCalculator);
					busiOrderArr.add(prodPropertysJsonObj);
				}
				break;

			case 1000: // 成员变更
				JSONArray mainJsonArrObj = processMemberChangeMain(boCreateParamGrp, areaId, boSeqCalculator);
				busiOrderArr.addAll(mainJsonArrObj);
				break;

			case 8: // 纳入电话虚拟网
			case 9: // 退出电话虚拟网
			case 55: // 纳入小灵通虚拟网
			case 56: // 退出小灵通虚拟网
			case 998: // 成员纳入
//				JSONArray mainJsonArrAdd = processMemberAddMain(boCreateParamGrp, areaId, boSeqCalculator);
//				busiOrderArr.addAll(mainJsonArrAdd);
//				break;
			case 999: // 成员退出
			case 1194: // 加入本地混合计费虚拟网
			case 1195: // 加入省内混合虚拟网
			case 1196: // 加入省内混合计费虚拟网
			case 1197: // 加入CDMA虚拟网
			case 1198: // 加入PHS计费虚拟网
			case 1199: // 加入CDMA计费虚拟网
			case 1201: // 加入固话计费虚拟网
			case 1202: // 加入省内UNB
			case 1203: // 加入跨省混合虚拟网
			case 1204: // 加入本地UNB
			case 1205: // 加入跨省UNB
			case 1207: // 退出本地混合计费虚拟网
			case 1208: // 退出省内混合虚拟网
			case 1209: // 退出省内混合计费虚拟网
			case 1210: // 退出CDMA虚拟网
			case 1211: // 退出PHS计费虚拟网
			case 1212: // 退出CDMA计费虚拟网
			case 1214: // 退出固话计费虚拟网
			case 1215: // 退出省内UNB
			case 1216: // 退出跨省混合虚拟网
			case 1217: // 退出本地UNB
			case 1218: // 退出跨省UNB
			case 1221: // 加入本地混合虚拟网
			case 1222: // 退出本地混合虚拟网
			case 1225: // 纳入跨省混合军区虚拟网
			case 1226: // 退出跨省混合军区虚拟网
			case 1233: // 加入虚拟网(虚拟)
			case 1234: // 退出虚拟网(虚拟)
			case 1227: // 纳入企业总机组合
			case 1228: // 退出企业总机组合
				JSONArray jsonArrObj = null;
				if (boCreateParamGrp.getCompBoCreateParam().getIsCompOffer()) {
					// 套餐类成员变更
					jsonArrObj = processOfferMemberChange(boCreateParamGrp, areaId, boSeqCalculator,
							mainOrderBusiOrderArr);
				} else {
					if (orderTypeId == 1233 || orderTypeId == 1234) {
						boCreateParamGrp.getSelfBoCreateParam().setNeedDealCompOrder(false);
					}
					jsonArrObj = processMemberChange(prodCompPropertysNode, boCreateParamGrp, areaId, boSeqCalculator);

				}
				busiOrderArr.addAll(jsonArrObj);
				break;
			case 3:// 拆机
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_ACTIVE, CommonDomain.DEL,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			case 19:// 停机保号/暂停
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_PAUSE, CommonDomain.ADD,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			case 20:// 停机保号复机/恢复
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_PAUSE, CommonDomain.DEL,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			case 1171:// 挂失
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_USER_STOP, CommonDomain.ADD,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			case 1172:// 解除挂失
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_USER_STOP, CommonDomain.DEL,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;

			case 72:// 预销户
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				busiOrderJs.elementOpt("actionFlag", 2);
				busiOrderArr.add(busiOrderJs);
				break;
			case 1165:// 预销号复机
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_DIS, CommonDomain.DEL, boCreateParam.getCurDate(), 
						boSeqCalculator);
				busiOrderJs.elementOpt("actionFlag", 2);
				busiOrderArr.add(busiOrderJs);
				break;

			case 1187:// 冻结
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_FROZEN, CommonDomain.ADD,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			case 1188:// 解冻
				busiOrderJs = createBlankBusiOrder(boCreateParam.getAreaId(), boCreateParam.getProdSpecId(), prodIdStr,
						String.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), String.valueOf(orderTypeId),
						boSeqCalculator);
				// 添加产品状态变化
				addProdStateChangeInfo(busiOrderJs, CrmServiceManagerConstants.PROD_STATUS_FROZEN, CommonDomain.DEL,
						boCreateParam.getCurDate(), boSeqCalculator);
				busiOrderArr.add(busiOrderJs);
				break;
			default:
				JSONArray otherJsonArrObj = processOtherOrder(order, boCreateParam, boSeqCalculator, orderTypeIds[i]);
				busiOrderArr.addAll(otherJsonArrObj);
				break;
			}
		}
		// TODO：如果费用不为空，则进行一次性费用的处理
		if (chargePakNode != null) {
			logger.debug("-----------------有一次性费用需要处理");
		}
		return busiOrderArr;
	}

	// 处理新订购
	private JSONArray processNewOrder(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArr = new JSONArray();

		Node acctCdNode = order.selectSingleNode("./acctCd");
		Node partyIdNode = order.selectSingleNode("./partyId");
		Node accessNumberNode = order.selectSingleNode("./accessNumber");
		Node bindPayNumberNode = order.selectSingleNode("./bindPayForNbr");
		Node bindNumberProdSpec = order.selectSingleNode("./bindNumberProdSpec");
		Node prod2accNbrNode = order.selectSingleNode("./prod2accNbr");
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		Node servicePakNode = order.selectSingleNode("./servicePak");
		Node prodPropertysNode = order.selectSingleNode("./prodPropertys");
		Node tdsNode = order.selectSingleNode("./tds"); // 终端信息
		Node couponsNode = order.selectSingleNode("./coupons"); // 物品信息
		Node passwordNode = order.selectSingleNode("./password");
		Node passwordTypeNode = order.selectSingleNode("./passwordType");
		String prodSpecId = boCreateParam.getProdSpecId();
		String areaId = boCreateParam.getAreaId();
		String date = boCreateParam.getCurDate();
		// 付费类型处理
		String feeType = boCreateParam.getFeeType();

		// 资费信息不为空，处理资费
		if (pricePlanPakNode != null) {
			JSONArray boArrJs = processPricePlanPak(pricePlanPakNode, boCreateParam, boSeqCalculator, null,null,null,null);
			busiOrderArr.addAll(boArrJs);
		}

		// 产品的业务动作构造
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("instId", boCreateParam.getProdId());// tangminjun
		// add
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "1");
		boActionTypeJs.elementOpt("name", "新装");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();

		JSONArray boProdArrJs = createBoProds(boSeqCalculator);
		dataJs.elementOpt("boProds", boProdArrJs);
		logger.debug("---------boProds 完成！");
		JSONArray boProdSpecArrJs = createBoProdSpecs(prodSpecId, null, boSeqCalculator);
		dataJs.elementOpt("boProdSpecs", boProdSpecArrJs);
		logger.debug("---------boProdSpecs 完成！");
		JSONArray boProdStatusArrJs = createBoProdStatuses("1", "ADD", boSeqCalculator);
		dataJs.elementOpt("boProdStatuses", boProdStatusArrJs);
		logger.debug("---------boProdStatuses 完成！");
		JSONArray boProdFeeTypeArrJs = createBoProdFeeTypes(feeType, "ADD", boSeqCalculator);
		dataJs.elementOpt("boProdFeeTypes", boProdFeeTypeArrJs);
		logger.debug("---------boProdFeeTypes 完成！");

		String newPartyIdStr = partyIdNode.getText();
		// 构造boCusts
		JSONArray boCustArrJs = createBoCustsJson(newPartyIdStr, null, "0", boSeqCalculator);
		dataJs.elementOpt("boCusts", boCustArrJs);
		// 产品属性不为空，处理产品属性
		if (prodPropertysNode != null) {
			JSONArray boProdItemArrJs = createProdItemsJson(prodPropertysNode, null, boSeqCalculator);
			dataJs.elementOpt("boProdItems", boProdItemArrJs);
		}
		// 新装业务时，处理密码设置
		if (passwordNode != null && passwordTypeNode != null) {
			String newPwd = passwordNode.getText();
			String passwordTypeStr = passwordTypeNode.getText();
			// 构造boProdPasswords
			JSONArray boProdPasswordArrJs = createBoProdPasswordsJson(newPwd, null, passwordTypeStr, boSeqCalculator);
			dataJs.elementOpt("boProdPasswords", boProdPasswordArrJs);
		}
		// 生成终端信息
		if (tdsNode != null) {
			JSONArray boProd2TdJsonArrObj = createboProd2TdsJson(tdsNode, null, boSeqCalculator);
			dataJs.elementOpt("boProd2Tds", boProd2TdJsonArrObj);
		}
		// 添加物品信息
		if (couponsNode != null) {
			// 构造bo2coupon
			JSONArray boProd2CouponArrJs = createboProd2CouponsJson(couponsNode, boCreateParam.getProdId(), null,
					boCreateParam.getPartyId(), boSeqCalculator, null);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// 号码不为空则处理号码
		if (accessNumberNode != null) {
			Node anIdNode = order.selectSingleNode("./anId"); // 主接入号ID
			JSONArray boProdAnArrObj = createBoProdAns(accessNumberNode, anIdNode, prodSpecId, areaId, partyIdNode,
					boSeqCalculator);
			dataJs.elementOpt("boProdAns", boProdAnArrObj);
		}
		// 如果非主接入号不为空，处理非主接入号
		if (prod2accNbrNode != null && !"".equals(prod2accNbrNode.getText().trim())) {
			Node anId2Node = order.selectSingleNode("./anId2"); // 非主接入号ID
			JSONArray boProd2AnArrObj = createBoProd2Ans(prodSpecId, areaId, prod2accNbrNode, anId2Node, passwordNode,
					boSeqCalculator);
			dataJs.elementOpt("boProd2Ans", boProd2AnArrObj);
		}
		// 帐户节点不为空或绑定付费号码不为空，处理帐户关系
		if (acctCdNode != null || (bindPayNumberNode != null && bindNumberProdSpec != null)) {
			JSONArray boAccountRelaArrJs = processAccountRela(acctCdNode, bindPayNumberNode, bindNumberProdSpec,
					boSeqCalculator, boCreateParam);
			dataJs.elementOpt("boAccountRelas", boAccountRelaArrJs);
			// 如果是捆绑支付需要生成捆绑关系
			if (acctCdNode == null && (bindPayNumberNode != null && bindNumberProdSpec != null)) {
				String bindPayNumber = bindPayNumberNode.getText();
				Integer bindNumberProdSpecId = Integer.valueOf(bindNumberProdSpec.getText());
				JSONObject boProdRela = processProdRela(bindPayNumber, bindNumberProdSpecId,
						CommonDomain.PROD_RELA_REASON_4, CommonDomain.ADD, boSeqCalculator);
				if (boProdRela != null) {
					JSONArray boProdRelasArrJs = new JSONArray();
					dataJs.elementOpt("boProdRelas", boProdRelasArrJs);
				}
			}
		}
		// 添加订单属性
		JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
		if (!busiOrderAttrArrObj.isEmpty()) {
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArr.add(busiOrderJs);

		// 服务信息不为空，处理产品服务的业务动作构造
		if (servicePakNode != null) {
			JSONArray serJsonArrObj = processServicePak(servicePakNode, prodSpecId, boCreateParam.getProdId(), areaId,
					date, boSeqCalculator);
			busiOrderArr.addAll(serJsonArrObj);
		}
		return busiOrderArr;
	}

	/**
	 * 处理修改帐务信息
	 * 
	 * @param acctCdNode
	 * @param bindPayNumberNode
	 * @param bindNumberProdSpec
	 * @param accoutNode
	 * @return
	 */
	private JSONArray processAccount(Node acctCdNode, Node bindPayNumberNode, Node bindNumberProdSpec, Node accoutNode,
			Long prodId, String areaId, BoSeqCalculator boSeqCalculator, BoCreateParam boCreateParam) {
		logger.debug("---------processAccount start");
		JSONArray busiOrderArrJs = new JSONArray();
		String acctCdStr = null;
		if (acctCdNode != null) {
			acctCdStr = acctCdNode.getText();
			if ((acctCdStr == null || acctCdStr.trim().equals("")) && bindPayNumberNode != null
					&& bindNumberProdSpec != null) {
				String bindPayNumberNodeStr = bindPayNumberNode.getText();
				if (bindPayNumberNodeStr != null && !bindPayNumberNodeStr.trim().equals("")) {
					Integer bindNumberProdSpecStr = Integer.valueOf(bindNumberProdSpec.getText());
					Account account = null;
					acctCdStr = account.getAcctCd();
				}
			}
		} else if (acctCdNode == null && bindPayNumberNode != null && bindNumberProdSpec != null) {
			String bindPayNumberNodeStr = bindPayNumberNode.getText();
			if (bindPayNumberNodeStr != null && !bindPayNumberNodeStr.trim().equals("")) {
				Integer bindNumberProdSpecStr = Integer.valueOf(bindNumberProdSpec.getText());
				Account account = null;
				acctCdStr = account.getAcctCd();
			}
		}
		logger.debug("---------newAcctCdStr={}", acctCdStr);
		Account oldAccount = null;
		logger.debug("---------prodId={}，oldAccount={}", prodId, oldAccount);

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject dataJs = new JSONObject();
		if (acctCdStr != null && !acctCdStr.trim().equals("")) {
			// 如果是实例变更付费帐户或者变更捆绑付费号码，则生成产品动作
			JSONObject busiObjJs = new JSONObject();
			busiObjJs.elementOpt("instId", prodId);
			busiOrderJs.elementOpt("busiObj", busiObjJs);
			logger.debug("---------busiObj 完成！");

			JSONObject boActionTypeJs = new JSONObject();
			boActionTypeJs.elementOpt("actionClassCd", "4");
			boActionTypeJs.elementOpt("boActionTypeCd", "6");
			boActionTypeJs.elementOpt("name", "修改帐户信息");
			busiOrderJs.elementOpt("boActionType", boActionTypeJs);
			logger.debug("---------boActionType 完成！");
			Account newAccount = null;

			JSONArray boAccountRelaArrJs = new JSONArray();
			JSONObject newBoAccountRelaJs = new JSONObject();
			newBoAccountRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoAccountRelaJs.elementOpt("acctId", newAccount.getAcctId());
			logger.debug("---------newAccount.getAcctId()={}", newAccount.getAcctId());
			newBoAccountRelaJs.elementOpt("chargeItemCd", "0");
			newBoAccountRelaJs.elementOpt("percent", "100");
			newBoAccountRelaJs.elementOpt("priority", "1");
			newBoAccountRelaJs.elementOpt("state", "ADD");
			newBoAccountRelaJs.elementOpt("statusCd", "S");
			boAccountRelaArrJs.add(newBoAccountRelaJs);

			if (oldAccount != null) {
				JSONObject oldBoAccountRelaJs = new JSONObject();
				oldBoAccountRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				oldBoAccountRelaJs.elementOpt("acctId", "-1");
				oldBoAccountRelaJs.elementOpt("acctId", oldAccount.getAcctId());
				logger.debug("---------oldAccount.getAcctId()={}", oldAccount.getAcctId());
				oldBoAccountRelaJs.elementOpt("chargeItemCd", "0");
				oldBoAccountRelaJs.elementOpt("percent", "100");
				oldBoAccountRelaJs.elementOpt("priority", "1");
				oldBoAccountRelaJs.elementOpt("state", "DEL");
				oldBoAccountRelaJs.elementOpt("statusCd", "S");
				boAccountRelaArrJs.add(oldBoAccountRelaJs);
			}
			dataJs.elementOpt("boAccountRelas", boAccountRelaArrJs);
			logger.debug("---------boAccountRelas 完成！");

			// 如果捆绑支付号码不为空，还需要变更捆绑支付关系
			if (bindPayNumberNode != null && bindNumberProdSpec != null) {
				String bindPayNumberNodeStr = bindPayNumberNode.getText();
				if (bindPayNumberNodeStr != null && !bindPayNumberNodeStr.trim().equals("")) {
					Integer bindNumberProdSpecStr = Integer.valueOf(bindNumberProdSpec.getText());
					Map newMap = null;
					logger.debug("---------customerServiceDAO.fingProdInfo return map = {} ", newMap);
					String newRelatedProdId = newMap.get("prodId").toString();
					logger.debug("---------newRelatedProdId = {} ", newRelatedProdId);

					JSONArray boProdRelaArrJs = new JSONArray();
					JSONObject newBoProdRelaJs = new JSONObject();
					newBoProdRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					newBoProdRelaJs.elementOpt("reasonCd", "4");
					newBoProdRelaJs.elementOpt("relatedProdId", newRelatedProdId);
					newBoProdRelaJs.elementOpt("state", "ADD");
					newBoProdRelaJs.elementOpt("statusCd", "S");
					boProdRelaArrJs.add(newBoProdRelaJs);

					List<Long> relaList = null;
					logger.debug("---------customerServiceDAO.fingProdRelasByProdId return relaList = {} ", relaList);
					String oldRelatedProdId = null;
					if (relaList != null && relaList.size() > 0) {
						oldRelatedProdId = relaList.get(0).toString();
					}
					logger.debug("---------oldRelatedProdId = {} ", oldRelatedProdId);
					if (oldRelatedProdId != null && !oldRelatedProdId.trim().equals("")) {
						// 如果实例已经有捆绑支付号码信息，则需要将原捆绑支付信息删除
						JSONObject oldBoProdRelaJs = new JSONObject();
						oldBoProdRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						oldBoProdRelaJs.elementOpt("reasonCd", "4");
						oldBoProdRelaJs.elementOpt("relatedProdId", oldRelatedProdId);
						oldBoProdRelaJs.elementOpt("state", "DEL");
						oldBoProdRelaJs.elementOpt("statusCd", "S");
						boProdRelaArrJs.add(oldBoProdRelaJs);
					}
					dataJs.elementOpt("boProdRelas", boProdRelaArrJs);
					logger.debug("---------boProdRelas 完成！");
				}
			}
			// 订单属性
			JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
			if (!busiOrderAttrArrObj.isEmpty()) {
				dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
			}
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}

		if (accoutNode != null) {
			// 如果是变更付费帐户的账单寄送信息，则生成帐户动作
			JSONObject busiObjJs = new JSONObject();
			dataJs = new JSONObject();
			busiObjJs.elementOpt("instId", oldAccount.getAcctId());
			logger.debug("---------Account.getAcctId()={}", oldAccount.getAcctId());
			busiOrderJs.elementOpt("busiObj", busiObjJs);
			logger.debug("---------busiObj 完成！");

			JSONObject boActionTypeJs = new JSONObject();
			boActionTypeJs.elementOpt("actionClassCd", "2");
			boActionTypeJs.elementOpt("boActionTypeCd", "A2");
			boActionTypeJs.elementOpt("name", "修改帐户信息");
			busiOrderJs.elementOpt("boActionType", boActionTypeJs);
			logger.debug("---------boActionType 完成！");

			Node accoutMailingNode = accoutNode.selectSingleNode("./accountMailing");
			Node acctNameNode = accoutNode.selectSingleNode("./acctName");// 内部支付帐户帐户名
			Node paymentManNode = accoutNode.selectSingleNode("./paymentAccount/paymentMan");// 外部支付帐户节点
			Node acctOwnerIdNode = accoutNode.selectSingleNode("./ownerId");// 内部支付帐户帐户名
			if (accoutMailingNode != null) {
				Node mailingTypeNode = accoutMailingNode.selectSingleNode("./mailingType");
				Node mailAddressNode = accoutMailingNode.selectSingleNode("./mailAddress");
				Node mailItemNode = accoutMailingNode.selectSingleNode("./mailItem");
				Node mailFormatNode = accoutMailingNode.selectSingleNode("./mailFormat");
				Node mailPeriodNode = accoutMailingNode.selectSingleNode("./mailPeriod");
				String mailingType = (null != mailingTypeNode ? mailingTypeNode.getText() : null);
				String mailAddress = (null != mailAddressNode ? mailAddressNode.getText() : null);
				String mailItem = (null != mailItemNode ? mailItemNode.getText() : null);
				String mailFormat = (null != mailFormatNode ? mailFormatNode.getText() : null);
				String mailPeriod = (null != mailPeriodNode ? mailPeriodNode.getText() : null);
				if (mailingType != null) {
					JSONArray boAccountMailingArrJs = new JSONArray();
					JSONObject newBoAccountMailingJs = new JSONObject();
					newBoAccountMailingJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					newBoAccountMailingJs.elementOpt("mailingType", mailingType);
					newBoAccountMailingJs.elementOpt("param1", mailAddress);
					newBoAccountMailingJs.elementOpt("param2", mailFormat);
					newBoAccountMailingJs.elementOpt("param3", mailPeriod);
					newBoAccountMailingJs.elementOpt("param7", mailItem);
					newBoAccountMailingJs.elementOpt("state", "ADD");
					newBoAccountMailingJs.elementOpt("statusCd", "S");
					boAccountMailingArrJs.add(newBoAccountMailingJs);

					if (oldAccount != null) {// 删除原账单寄送信息
						List<AccountMailing> oldMailList = oldAccount.getAccountMailing();
						if (oldMailList != null && oldMailList.size() > 0) {
							for (AccountMailing oldAccountMailing : oldMailList) {
								logger.debug("---------oldAccountMailing={}", oldAccountMailing);
								JSONObject oldBoAccountMailingJs = new JSONObject();
								oldBoAccountMailingJs.elementOpt("atomActionId", boSeqCalculator
										.getNextAtomActionIdInteger());
								oldBoAccountMailingJs.elementOpt("mailingType", oldAccountMailing.getMailingType());
								oldBoAccountMailingJs.elementOpt("param1", oldAccountMailing.getParam1());
								oldBoAccountMailingJs.elementOpt("param2", oldAccountMailing.getParam2());
								oldBoAccountMailingJs.elementOpt("param3", oldAccountMailing.getParam3());
								oldBoAccountMailingJs.elementOpt("param7", oldAccountMailing.getParam7());
								oldBoAccountMailingJs.elementOpt("state", "DEL");
								oldBoAccountMailingJs.elementOpt("statusCd", "S");
								boAccountMailingArrJs.add(oldBoAccountMailingJs);
							}
						}
					}
					dataJs.elementOpt("boAccountMailings", boAccountMailingArrJs);
					logger.debug("---------boAccountMailings 完成！");
				}
			}
			// 修改系统内部账户信息BO_ACCOUNT_INFO
			if ((acctNameNode != null && !acctNameNode.getText().equals(""))
					|| (acctOwnerIdNode != null && !acctOwnerIdNode.getText().equals(""))) {
				Account account = null;
				JSONArray boAccountInfoArrJs = new JSONArray();
				JSONObject boAccountInfoJs = new JSONObject();
				boAccountInfoJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boAccountInfoJs.elementOpt("prodId", account.getProdId());
				boAccountInfoJs.elementOpt("acctCd", account.getAcctCd());
				if (acctNameNode != null && !acctNameNode.getText().equals("")) {
					boAccountInfoJs.elementOpt("acctName", acctNameNode.getText());
				} else {
					boAccountInfoJs.elementOpt("acctName", account.getAcctName());
				}
				boAccountInfoJs.elementOpt("businessPassword", account.getBusinessPassword());
				boAccountInfoJs.elementOpt("limitQty", account.getLimitQty());
				if (acctOwnerIdNode != null && !acctOwnerIdNode.getText().equals("")) {
					boAccountInfoJs.elementOpt("partyId", acctOwnerIdNode.getText());
				} else {
					boAccountInfoJs.elementOpt("partyId", account.getPartyId());
				}
				boAccountInfoJs.elementOpt("statusCd", CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
				boAccountInfoJs.elementOpt("state", CommonDomain.ADD);
				boAccountInfoArrJs.add(boAccountInfoJs);
				// 删除旧的
				JSONObject oldBoAccountInfoJs = new JSONObject();
				oldBoAccountInfoJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				oldBoAccountInfoJs.elementOpt("prodId", account.getProdId());
				oldBoAccountInfoJs.elementOpt("acctCd", account.getAcctCd());
				oldBoAccountInfoJs.elementOpt("acctName", account.getAcctName());
				oldBoAccountInfoJs.elementOpt("businessPassword", account.getBusinessPassword());
				oldBoAccountInfoJs.elementOpt("limitQty", account.getLimitQty());
				oldBoAccountInfoJs.elementOpt("partyId", account.getPartyId());
				oldBoAccountInfoJs.elementOpt("statusCd", CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
				oldBoAccountInfoJs.elementOpt("state", CommonDomain.DEL);
				boAccountInfoArrJs.add(oldBoAccountInfoJs);
				dataJs.elementOpt("boAccountInfos", boAccountInfoArrJs);
				logger.debug("---------boAccountInfos 完成！");
			}
			// 修改外部支付帐户BO_PAYMENT_ACCOUNT
			if (paymentManNode != null && !paymentManNode.getText().trim().equals("")) {
				PaymentAccount paymentAccount = null;
				JSONArray boPaymentAccountArrJs = new JSONArray();
				JSONObject boPaymentAccountJs = new JSONObject();
				boPaymentAccountJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boPaymentAccountJs.elementOpt("paymentAccountId", paymentAccount.getPaymentAccountId());
				boPaymentAccountJs.elementOpt("paymentAcctTypeCd", paymentAccount.getPaymentAccountTypeCd());
				boPaymentAccountJs.elementOpt("bankId", paymentAccount.getBankId());
				boPaymentAccountJs.elementOpt("bankAcct", paymentAccount.getBankAcctCd());
				boPaymentAccountJs.elementOpt("limitQty", paymentAccount.getLimitQty());
				boPaymentAccountJs.elementOpt("paymentMan", paymentManNode.getText().trim());
				boPaymentAccountJs.elementOpt("statusCd", CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
				boPaymentAccountJs.elementOpt("state", CommonDomain.ADD);
				boPaymentAccountArrJs.add(boPaymentAccountJs);
				// 删除旧的
				JSONObject oldBoPaymentAccountJs = new JSONObject();
				oldBoPaymentAccountJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				oldBoPaymentAccountJs.elementOpt("paymentAccountId", paymentAccount.getPaymentAccountId());
				oldBoPaymentAccountJs.elementOpt("paymentAcctTypeCd", paymentAccount.getPaymentAccountTypeCd());
				oldBoPaymentAccountJs.elementOpt("bankId", paymentAccount.getBankId());
				oldBoPaymentAccountJs.elementOpt("bankAcct", paymentAccount.getBankAcctCd());
				oldBoPaymentAccountJs.elementOpt("limitQty", paymentAccount.getLimitQty());
				oldBoPaymentAccountJs.elementOpt("paymentMan", paymentAccount.getPaymentMan());
				oldBoPaymentAccountJs.elementOpt("statusCd", CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
				oldBoPaymentAccountJs.elementOpt("state", CommonDomain.DEL);
				boPaymentAccountArrJs.add(oldBoPaymentAccountJs);
				dataJs.elementOpt("boPaymentAccounts", boPaymentAccountArrJs);
				logger.debug("---------boPaymentAccounts 完成！");
			}
			// 订单属性
			JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
			if (!busiOrderAttrArrObj.isEmpty()) {
				dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
			}
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		return busiOrderArrJs;
	}

	/**
	 * 处理服务节点
	 * 
	 * @param servicePakNode
	 * @param prod
	 * @param areaId
	 * @param date
	 * @return
	 */
	private JSONArray processServicePak(Node servicePakNode, String prodSpecId, Long prodId, String areaId,
			String date, BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		List serviceNodes = servicePakNode.selectNodes("./service");
		// 开始循环处理服务的变更信息
		for (Iterator itr = serviceNodes.iterator(); itr.hasNext();) {
			Node serviceNode = (Node) itr.next();
			String serviceIdStr = serviceNode.selectSingleNode("./serviceId").getText();
			String actionTypeStr = serviceNode.selectSingleNode("./actionType").getText();
			Node startDtNode = serviceNode.selectSingleNode("./startDt");// 服务开始时间
			Node endDtNode = serviceNode.selectSingleNode("./endDt");// 服务结束时间
			Node time_unitNode = serviceNode.selectSingleNode("./time_unit");// 账期字段
			String startFashion = WSUtil.getXmlNodeText(serviceNode, "./startFashion");
			String endFashion = WSUtil.getXmlNodeText(serviceNode, "./endFashion");
			Node properties = serviceNode.selectSingleNode("./properties");
			String startDt = "";
			String endDt = "";
			String time_unit = "";
			if (startDtNode != null) {
				startDt = ("").equals(startDtNode.getText().trim()) ? startDt : startDtNode.getText().trim();
			}
			if (endDtNode != null) {
				endDt = ("").equals(endDtNode.getText().trim()) ? endDt : endDtNode.getText().trim();
			}
			if (time_unitNode != null) {
				time_unit = ("").equals(time_unitNode.getText().trim()) ? time_unit : time_unitNode.getText().trim();
			}
			String servId = null;
			List<OfferServItem> offerServItems = null;
			if (actionTypeStr != null && (actionTypeStr.equals("1") || actionTypeStr.equals("2"))) {
				// 找出老的servId
				OfferServ offerServ = intfSMO.findOfferServByProdIdAndServSpecId(prodId, Long.parseLong(serviceIdStr));
				if (offerServ.getServId() != null) {
					servId = offerServ.getServId().toString();
					offerServItems = offerServ.getOfferServItems();
				} else {
					throw new BmoException(-1, "产品" + prodId + "没有对应的服务" + serviceIdStr);
				}
			} else {
				servId = servInstList.get(serviceIdStr);// 开服务需要生成新的服务实例序列号
			}

			JSONObject busiOrderJs = new JSONObject();

			busiOrderJs.elementOpt("areaId", areaId);
			busiOrderJs.elementOpt("linkFlag", "Y");

			Integer seq = boSeqCalculator.getNextSeqInteger();
			JSONObject busiOrderInfoJs = new JSONObject();
			busiOrderInfoJs.elementOpt("seq", seq);
			busiOrderInfoJs.elementOpt("statusCd", "S");
			busiOrderInfoJs.elementOpt("appStartDt", startDt);
			busiOrderInfoJs.elementOpt("appEndDt", endDt);
			busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
			logger.debug("---------busiOrderInfo 完成！");

			JSONObject busiObjJs = new JSONObject();
			busiObjJs.elementOpt("objId", prodSpecId);
			busiObjJs.elementOpt("instId", prodId);
			busiObjJs.elementOpt("offerTypeCd", "");
			busiOrderJs.elementOpt("busiObj", busiObjJs);
			logger.debug("---------busiObj 完成！");

			JSONObject boActionTypeJs = new JSONObject();
			boActionTypeJs.elementOpt("actionClassCd", "4");
			boActionTypeJs.elementOpt("boActionTypeCd", "7");
			boActionTypeJs.elementOpt("name", "服务信息变更");
			busiOrderJs.elementOpt("boActionType", boActionTypeJs);
			logger.debug("---------boActionType 完成！");

			JSONObject dataJs = new JSONObject();
			// 构造boServOrders
			JSONArray boServOrderArrJs = new JSONArray();
			JSONObject boServOrderJs = new JSONObject();
			boServOrderJs.elementOpt("servId", servId);
			boServOrderJs.elementOpt("servSpecId", serviceIdStr);
			boServOrderArrJs.add(boServOrderJs);
			dataJs.elementOpt("boServOrders", boServOrderArrJs);
			logger.debug("---------boServOrders 完成！");

			if (actionTypeStr != null && (actionTypeStr.equals("0") || actionTypeStr.equals("1"))) {
				// 构造boServs
				JSONArray boServArrJs = new JSONArray();
				JSONObject boServJs = new JSONObject();
				if (actionTypeStr != null && actionTypeStr.equals("0")) {
					boServJs.elementOpt("servId", servId);
					boServJs.elementOpt("state", "ADD");
				} else if (actionTypeStr != null && actionTypeStr.equals("1")) {
					boServJs.elementOpt("servId", servId);
					boServJs.elementOpt("state", "DEL");
				}
				boServJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boServJs.elementOpt("statusCd", "S");
				boServJs.elementOpt("appStartDt", startDt);
				boServJs.elementOpt("appEndDt", endDt);
				boServArrJs.add(boServJs);
				dataJs.elementOpt("boServs", boServArrJs);
				logger.debug("---------boServs 完成！");

				// 关闭服务时候不必处理服务属性节点，动作链自动带出
				List propertyNodes = null;
				if (properties != null) {
					// 服务属性的
					propertyNodes = properties.selectNodes("./property");
				}
				if (propertyNodes != null && !propertyNodes.isEmpty() && actionTypeStr.equals("0")) {
					JSONArray boServItemArrJs = new JSONArray();
					for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
						Node propertie = (Node) itr1.next();
						String itemSpecId = propertie.selectSingleNode("./id").getText();
						String newValue = propertie.selectSingleNode("./value").getText();
						JSONObject boServItemJs = new JSONObject();
						boServItemJs.elementOpt("servId", servId);
						boServItemJs.elementOpt("itemSpecId", itemSpecId);
						boServItemJs.elementOpt("value", newValue);
						boServItemJs.elementOpt("state", "ADD");
						boServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						boServItemJs.elementOpt("statusCd", "S");
						boServItemJs.elementOpt("appStartDt", startDt);
						boServItemJs.elementOpt("appEndDt", endDt);
						boServItemArrJs.add(boServItemJs);
					}
					dataJs.elementOpt("boServItems", boServItemArrJs);
					logger.debug("---------boServItems 完成！");
				}
			} else if (actionTypeStr != null && actionTypeStr.equals("2") && properties != null) {
				// 服务属性的变更
				JSONArray boServItemArrJs = new JSONArray();
				List propertyNodes = properties.selectNodes("./property");
				for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
					Node propertie = (Node) itr1.next();
					String itemSpecId = propertie.selectSingleNode("./id").getText();
					String newValue = propertie.selectSingleNode("./value").getText();
					// 新属性
					if (StringUtils.isNotBlank(newValue)) {
						JSONObject newBoServItemJs = new JSONObject();
						newBoServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						newBoServItemJs.elementOpt("servId", servId);
						newBoServItemJs.elementOpt("itemSpecId", itemSpecId);
						newBoServItemJs.elementOpt("value", newValue);
						newBoServItemJs.elementOpt("state", "ADD");
						newBoServItemJs.elementOpt("statusCd", "S");
						newBoServItemJs.elementOpt("appStartDt", startDt);
						newBoServItemJs.elementOpt("appEndDt", endDt);
						boServItemArrJs.add(newBoServItemJs);
					} else {
						actionTypeStr = "1";
					}
					// 删除老的属性
					JSONObject oldBoServItemJs = new JSONObject();
					String oldValue = null;
					boolean existFlag = false;
					for (OfferServItem offerServItem : offerServItems) {
						if (itemSpecId.equals(offerServItem.getItemSpecId().toString())) {
							oldValue = offerServItem.getValue();
							existFlag = true;
							break;
						}
					}
					if (existFlag) {
						oldBoServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						oldBoServItemJs.elementOpt("servId", servId);
						oldBoServItemJs.elementOpt("itemSpecId", itemSpecId);
						oldBoServItemJs.elementOpt("value", oldValue);
						oldBoServItemJs.elementOpt("state", "DEL");
						oldBoServItemJs.elementOpt("statusCd", "S");
						oldBoServItemJs.elementOpt("appStartDt", startDt);
						oldBoServItemJs.elementOpt("appEndDt", endDt);
						boServItemArrJs.add(oldBoServItemJs);
					}
				}
				dataJs.elementOpt("boServItems", boServItemArrJs);
				logger.debug("---------boServItems 完成！");
			}
			// 构造ooTimes
			JSONArray ooTimes = CreateOoTimes(boSeqCalculator, startDt, endDt, startFashion, endFashion, actionTypeStr,time_unit);
			dataJs.elementOpt("ooTimes", ooTimes);
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		return busiOrderArrJs;
	}

	/**
	 * 处理过户信息
	 * 
	 * @param newPartyIdNode
	 * @param acctCdNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONArray processOwnerChange(Node newPartyIdNode, Node acctCdNode, String prodSpecId, Long prodId,
			String areaId, BoSeqCalculator boSeqCalculator, BoCreateParam boCreateParam) {
		JSONArray busiOrderArrJs = new JSONArray();
		String newPartyIdStr = newPartyIdNode.getText();

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "11");
		boActionTypeJs.elementOpt("name", "过户");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		String partyProductRelaRoleCd = "0";
		OfferProd2Party offerProd2Party = null;
		String oldPartyIdStr = offerProd2Party.getPartyId().toString();

		// 构造boCusts
		JSONArray boCustArrJs = createBoCustsJson(newPartyIdStr, oldPartyIdStr, partyProductRelaRoleCd, boSeqCalculator);
		dataJs.elementOpt("boCusts", boCustArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);

		JSONArray boAcctArrJs = processAccount(acctCdNode, null, null, null, prodId, areaId, boSeqCalculator,
				boCreateParam);
		busiOrderArrJs.addAll(boAcctArrJs);

		return busiOrderArrJs;
	}

	/**
	 * 补机/补卡
	 * 
	 * @param order
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processTdsActive(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator) {
		String charge = WSUtil.getXmlNodeText(order, "./chargeInfo/charge");// 补换卡的钱
		String appCharge = WSUtil.getXmlNodeText(order, "./chargeInfo/appCharge");
		Node tdsNode = order.selectSingleNode("./tds"); // 终端信息
		Node couponsNode = order.selectSingleNode("./coupons"); // 物品信息
		Node acctCdNode = order.selectSingleNode("./acctCd");
		Node bindPayNumberNode = order.selectSingleNode("./bindPayForNbr");
		Node bindNumberProdSpec = order.selectSingleNode("./bindNumberProdSpec");
		Long prodId = boCreateParam.getProdId();
		String accessNumber = intfSMO.getAccessNumberByProdId(prodId);
		if (StringUtils.isBlank(accessNumber)) {
			throw new BmoException(-1, "产品不存在！");
		}
		String prodSpec = boCreateParam.getProdSpecId();
		String areaId = boCreateParam.getAreaId();
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");
		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpec);
		busiObjJs.elementOpt("accessNumber", accessNumber);
		busiObjJs.elementOpt("instId", prodId);
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "1184");
		boActionTypeJs.elementOpt("name", "UIM卡备卡激活");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 构造boProd2Tds
		if (tdsNode != null) {
			JSONArray boProd2TdArrJs = createboProd2TdsJson(tdsNode, prodId, boSeqCalculator);
			dataJs.elementOpt("boProd2Tds", boProd2TdArrJs);
			// 构造bo2coupon NND UIM卡也要啊
			JSONArray boProd2CouponArrJs = createbo2Coupon(tdsNode, boCreateParam.getProdId(), null, boCreateParam
					.getPartyId(), boSeqCalculator);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// 构造bo2coupon
		if (couponsNode != null) {
			JSONArray boProd2CouponArrJs = createboProd2CouponsJson(couponsNode, prodId, null, boCreateParam
					.getPartyId(), boSeqCalculator, null);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// 补机补卡给附带写死一个收费节点进行收费
		if (StringUtils.isNotBlank(charge)) {
			JSONArray boAcctItems = createBoAcctItems(charge, appCharge);
			dataJs.elementOpt("boAcctItems", boAcctItems);
		}
		//补换卡去掉bo_account_rela过程表新增
		/*JSONArray boAccountRelaArrJs = processAccountRela(acctCdNode, bindPayNumberNode, bindNumberProdSpec,
				boSeqCalculator, boCreateParam);
		dataJs.elementOpt("boAccountRelas", boAccountRelaArrJs);*/
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 补机/补卡
	 * 
	 * @param order
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processTds(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator) {
		String charge = WSUtil.getXmlNodeText(order, "./chargeInfo/charge");// 补换卡的钱
		String appCharge = WSUtil.getXmlNodeText(order, "./chargeInfo/appCharge");
		Node tdsNode = order.selectSingleNode("./tds"); // 终端信息
		Node couponsNode = order.selectSingleNode("./coupons"); // 物品信息
		Node acctCdNode = order.selectSingleNode("./acctCd");
		Node bindPayNumberNode = order.selectSingleNode("./bindPayForNbr");
		Node bindNumberProdSpec = order.selectSingleNode("./bindNumberProdSpec");
		Long prodId = boCreateParam.getProdId();
		String accessNumber = intfSMO.getAccessNumberByProdId(prodId);
		if (StringUtils.isBlank(accessNumber)) {
			throw new BmoException(-1, "产品不存在！");
		}
		String prodSpec = boCreateParam.getProdSpecId();
		String areaId = boCreateParam.getAreaId();
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");
		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpec);
		busiObjJs.elementOpt("accessNumber", accessNumber);
		busiObjJs.elementOpt("instId", prodId);
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "14");
		boActionTypeJs.elementOpt("name", "补机/补卡");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 构造boProd2Tds
		if (tdsNode != null) {
			JSONArray boProd2TdArrJs = createboProd2TdsJson(tdsNode, prodId, boSeqCalculator);
			dataJs.elementOpt("boProd2Tds", boProd2TdArrJs);
			// 构造bo2coupon NND UIM卡也要啊
			JSONArray boProd2CouponArrJs = createbo2Coupon(tdsNode, boCreateParam.getProdId(), null, boCreateParam
					.getPartyId(), boSeqCalculator);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// 构造bo2coupon
		if (couponsNode != null) {
			JSONArray boProd2CouponArrJs = createboProd2CouponsJson(couponsNode, prodId, null, boCreateParam
					.getPartyId(), boSeqCalculator, null);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// 补机补卡给附带写死一个收费节点进行收费
		if (StringUtils.isNotBlank(charge)) {
			JSONArray boAcctItems = createBoAcctItems(charge, appCharge);
			dataJs.elementOpt("boAcctItems", boAcctItems);
		}
		//补换卡去掉bo_account_rela过程表新增
		/*JSONArray boAccountRelaArrJs = processAccountRela(acctCdNode, bindPayNumberNode, bindNumberProdSpec,
				boSeqCalculator, boCreateParam);
		dataJs.elementOpt("boAccountRelas", boAccountRelaArrJs);*/
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 处理资费节点
	 * 
	 * @param pricePlanPakNode
	 * @param systemIdNode 
	 * @param busiOrderTimeList 
	 * @param prod
	 * @param areaId
	 * @param date
	 * @return
	 */
	private JSONArray processPricePlanPak(Node pricePlanPakNode, BoCreateParam boCreateParam,
			BoSeqCalculator boSeqCalculator, List<Node> assistManInfoList, List<Node> busiOrderTimeList, Node systemIdNode,Node chargeInfos) {

		Integer prodSpecId = Integer.valueOf(boCreateParam.getProdSpecId());
		Long prodId = boCreateParam.getProdId();
		Long partyId = boCreateParam.getPartyId();
		String areaId = boCreateParam.getAreaId();
		String date = boCreateParam.getCurDate();

		JSONArray busiOrderArrJs = new JSONArray();
		List<Node> pricePlanNodes = pricePlanPakNode.selectNodes("./pricePlan");
		// 开始循环处理资费节点信息
		for (Node pricePlanNode : pricePlanNodes) {
			String pricePlanCdStr = pricePlanNode.selectSingleNode("./pricePlanCd").getText();
			String actionTypeStr = pricePlanNode.selectSingleNode("./actionType").getText();
			Node startDtNode = pricePlanNode.selectSingleNode("./startDt");
			Node endDtNode = pricePlanNode.selectSingleNode("./endDt");
			Node time_unitNode = pricePlanNode.selectSingleNode("./time_unit");
			Node properties = pricePlanNode.selectSingleNode("./properties");
			Node couponInfos = pricePlanNode.selectSingleNode("./couponInfos");
			Node couponsNode = pricePlanNode.selectSingleNode("./coupons");
			String startFashion = WSUtil.getXmlNodeText(pricePlanNode, "./startFashion");
			String endFashion = WSUtil.getXmlNodeText(pricePlanNode, "./endFashion");
			String actionType = WSUtil.getXmlNodeText(pricePlanNode, "./actionType");

			String startDt = "";
			String endDt = "";
			String time_unit = "";
			if (startDtNode != null) {
				startDt = ("").equals(startDtNode.getText().trim()) ? startDt : startDtNode.getText().trim();
			}
			if (endDtNode != null) {
				endDt = ("").equals(endDtNode.getText().trim()) ? endDt : endDtNode.getText().trim();
			}
			if (time_unitNode != null) {
				time_unit = ("").equals(time_unitNode.getText().trim()) ? time_unit : time_unitNode.getText().trim();
			}

			if (StringUtils.isNotBlank(endDt)) {
				endDt = endDt + " 23:59:59";
			}

			String offerSpecId = null;
			String offerTypeCd = null;
			OfferSpec offerSpec = null;
			if (StringUtils.isNotBlank(pricePlanCdStr)) {
				offerSpec = offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(pricePlanCdStr));
			}
			if (offerSpec == null) {
				throw new BmoException(-1, "pricePlanCdStr=" + pricePlanCdStr + "没有匹配的销售品规格");
			}

			offerSpecId = offerSpec.getOfferSpecId().toString();
			offerTypeCd = offerSpec.getOfferTypeCd().toString();
			logger.debug("offerSpecId={},pricePlanCd={}", offerSpecId, pricePlanCdStr);

			String oldOfferId = null;
			String oldOfferMemberId = null;
			List<OfferParam> offerParams = null;
			if (actionTypeStr != null
					&& (actionTypeStr.equals("1") || actionTypeStr.equals("2") || (actionTypeStr.equals("3")))) {
				Offer offer = intfSMO.findOfferByProdIdAndOfferSpecId(prodId, offerSpec.getOfferSpecId());
				if (offer != null) {
					oldOfferId = offer.getOfferId().toString();
					oldOfferMemberId = offer.getExtProdOfferInstId();
					// 找出销售品实例参数
					offerParams = intfSMO.queryOfferParamByOfferId(Long.parseLong(oldOfferId));
				} else {
					throw new BmoException(-1, "该产品没有对应的销售品" + offerSpecId);
				}
			}

			List<Map<String, Object>> offerRoles = new ArrayList<Map<String, Object>>();
			// 查找出成员是产品规格的销售品角色
			OfferRoles prodOfferRoles = intfSMO.findProdOfferRoles(offerSpec.getOfferSpecId(), prodSpecId.longValue());
			if (prodOfferRoles != null) {
				Map<String, Object> prodOfferRoleMap = new HashMap<String, Object>();
				prodOfferRoleMap.put("objType", CommonDomain.OBJ_TYPE_PROD_SPEC);
				prodOfferRoleMap.put("offerRoles", prodOfferRoles);
				offerRoles.add(prodOfferRoleMap);
			}
			// 查找出成员是服务规格的销售品角色
			List<OfferRoles> servOfferRoleList = intfSMO.findServOfferRoles(offerSpec.getOfferSpecId());

			if (servOfferRoleList != null && servOfferRoleList.size() > 0) {
				for (OfferRoles servOfferRoles : servOfferRoleList) {
					Map<String, Object> servOfferRoleMap = new HashMap<String, Object>();
					servOfferRoleMap.put("objType", CommonDomain.OBJ_TYPE_SERV_SPEC);
					servOfferRoleMap.put("offerRoles", servOfferRoles);
					offerRoles.add(servOfferRoleMap);
				}
			}

			JSONObject busiOrderJs = new JSONObject();
			busiOrderJs.elementOpt(CommonDomain.XSD_AREA_ID_DICTIONARY, areaId);
			busiOrderJs.elementOpt("linkFlag", "Y");
			//
			busiOrderJs.elementOpt("isFacOffer", "N");
			busiOrderJs.elementOpt("actionFlag", 1);
			//

			Integer seq = boSeqCalculator.getNextSeqInteger();
			JSONObject busiOrderInfoJs = new JSONObject();
			busiOrderInfoJs.elementOpt(CommonDomain.XSD_SEQ_DICTIONARY, seq);
			busiOrderInfoJs.elementOpt(CommonDomain.XSD_STATUS_CD_DICTIONARY,
					CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
			busiOrderInfoJs.elementOpt("appStartDt", startDt);
			busiOrderInfoJs.elementOpt("appEndDt", endDt);
			busiOrderJs.elementOpt(CommonDomain.XSD_BUSI_ORDER_INFO_DICTIONARY, busiOrderInfoJs);
			logger.debug("---------busiOrderInfo 完成！");
			String instId = boSeqCalculator.getNextOfferIdString();
			if (actionTypeStr != null && actionTypeStr.equals("0")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt(CommonDomain.XSD_OBJ_ID_DICTIONARY, offerSpecId);
				busiObjJs.elementOpt("instId", instId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj 完成！");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S1");
				boActionTypeJs.elementOpt("name", "订购");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType 完成！");
			} else if (actionTypeStr != null && actionTypeStr.equals("1")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", offerSpecId);
				busiObjJs.elementOpt("instId", oldOfferId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj 完成！");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S2");
				boActionTypeJs.elementOpt("name", "退订");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType 完成！");
			} else if (actionTypeStr != null && actionTypeStr.equals("2")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", offerSpecId);
				busiObjJs.elementOpt("instId", oldOfferId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj 完成！");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S4");
				boActionTypeJs.elementOpt("name", "改参数");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType 完成！");
			} else if (actionTypeStr != null && actionTypeStr.equals("3")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", offerSpecId);
				busiObjJs.elementOpt("name", offerSpec.getName());
				busiObjJs.elementOpt("instId", oldOfferId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiObjJs.elementOpt("isComp", "N");
				busiObjJs.elementOpt("ifSelStruct", "False");
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj 完成！");

				JSONArray busiComponentInfos = new JSONArray();
				JSONObject busiComponentInfo = new JSONObject();
				busiComponentInfo.elementOpt("behaviorFlag", "010101");
				busiComponentInfo.elementOpt("busiComponentCode", "boOffers");
				busiComponentInfos.add(busiComponentInfo);
				busiOrderJs.elementOpt("busiComponentInfos", busiComponentInfos);

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S6");
				boActionTypeJs.elementOpt("name", "续订");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType 完成！");
			}

			JSONObject dataJs = new JSONObject();

			// 续订或者退订
			if (actionTypeStr != null && (actionTypeStr.equals("3") || actionTypeStr.equals("1"))) {
				String state = CommonDomain.ADD;
				if ("1".equals(actionTypeStr)) {
					state = CommonDomain.DEL;
				}
				// 构造ooRoles
				if (offerRoles.size() > 0) {
					JSONArray ooRoleArrJs = new JSONArray();
					for (Map<String, Object> offerRoleMap : offerRoles) {
						JSONObject ooRoleJs = new JSONObject();
						ooRoleJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						ooRoleJs.elementOpt("compOffer", "N");
						ooRoleJs.elementOpt("compOrder", 1);
						ooRoleJs.elementOpt("state", state);
						ooRoleJs.elementOpt("statusCd", "S");
						ooRoleJs.elementOpt("startDt", startDt);
						ooRoleJs.elementOpt("endDt", endDt);
						logger.debug("prodId={}", prodId);
						if (prodId != null) {
							ooRoleJs.elementOpt("prodId", prodId);
							ooRoleJs.elementOpt("exist", prodId);
						}
						// 获得销售品成员角色ID
						OfferRoles offerRole = (OfferRoles) offerRoleMap.get("offerRoles");
						if (CommonDomain.DEL.equals(state)) {
							ooRoleJs.elementOpt("offerMemberId", oldOfferMemberId);
						}
						ooRoleJs.elementOpt("offerRoleId", offerRole.getOfferRoleId());
						Integer objType = (Integer) offerRoleMap.get("objType");
						ooRoleJs.elementOpt("objType", objType);
						logger.debug("offerRole.getOfferRoleId()={}, objType={}", offerRole.getOfferRoleId(), objType);
						if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_PROD_SPEC)) {
							// 如果成员角色对象类型是产品规格时
							ooRoleJs.elementOpt("objId", prodSpecId);
							if (prodId != null) {
								ooRoleJs.elementOpt("objInstId", prodId);
							} else {
								ooRoleJs.elementOpt("objInstId", "-1");
							}
						} else if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_SERV_SPEC)) {
							// 如果成员角色对象类型是产服务规格时
							// 获取销售品成员角色对象ID
							RoleObj roleObj = intfSMO.findRoleObjByOfferRoleIdAndObjType(offerRole.getOfferRoleId(),
									objType);
							ooRoleJs.elementOpt("objId", roleObj.getObjId());
							logger.debug("roleObj.getObjId()={}", roleObj.getObjId());
							// 获得服务实例ID
							OfferServ offerServ = intfSMO
									.findOfferServByProdIdAndServSpecId(prodId, roleObj.getObjId());
							Long servId = null;
							if (offerServ != null) {
								servId = offerServ.getServId();
							}
							logger.debug("servId={}", servId);
							if (servId != null) {
								ooRoleJs.elementOpt("objInstId", servId);
								servInstList.put(roleObj.getObjId().toString(), servId.toString());
							} else {
								String a = String.valueOf(boSeqCalculator.getNextServId());
								ooRoleJs.elementOpt("objInstId", a);
								servInstList.put(roleObj.getObjId().toString(), a);
							}
						}
						ooRoleArrJs.add(ooRoleJs);
					}
					if (!ooRoleArrJs.isEmpty()) {
						dataJs.elementOpt("ooRoles", ooRoleArrJs);
						logger.debug("---------ooRoles 完成！");
					}
				}
				
				// 构造ooOwners
				//续订和退订增加此节点 add by hll 2015119
				JSONArray ooOwnerArrJs = new JSONArray();
				JSONObject ooOwnerJs = new JSONObject();
				ooOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				ooOwnerJs.elementOpt("partyId", partyId.toString());
				ooOwnerJs.elementOpt("state", state);
				ooOwnerJs.elementOpt("statusCd", "S");
				ooOwnerArrJs.add(ooOwnerJs);
				dataJs.elementOpt("ooOwners", ooOwnerArrJs);
				logger.debug("---------ooOwners 完成！");
			}

			if (actionTypeStr != null && actionTypeStr.equals("0")) {
				String state = CommonDomain.ADD;
				// bo_2_staff
				if (assistManInfoList.size() != 0) {
					List<Map<String, String>> staffInfoList = new ArrayList<Map<String, String>>();
					for (int i = 0; assistManInfoList.size() > i; i++) {
						Map<String, String> param = new HashMap<String, String>();
						String staffTypeName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffTypeName");
						String staffType = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffType");
						String staffNumber = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffNumber");
						String staffName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffName");
						String staffId = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffId");
						String orgName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "orgName");
						String orgId = WSUtil.getXmlNodeText(assistManInfoList.get(i), "orgId");
						param.put("staffTypeName", staffTypeName);
						param.put("staffType", staffType);
						param.put("staffNumber", staffNumber);
						param.put("staffName", staffName);
						param.put("staffId", staffId);
						param.put("orgName", orgName);
						param.put("orgId", orgId);
						staffInfoList.add(param);
					}
					JSONArray bo2Staffs = bo2staff(staffInfoList, boSeqCalculator);
					dataJs.elementOpt("bo2Staffs", bo2Staffs);
					logger.debug("---------bo2Staffs 完成！");
				}
				
				//构造接单点节点,目前只对客服2.0受理接单点
				if(busiOrderTimeList != null && systemIdNode != null){
					if("6090010042".equals(systemIdNode.getText())){
						List<Map<String, String>> busiOrderTimeInfoList = new ArrayList<Map<String, String>>();
						if (busiOrderTimeList != null && busiOrderTimeList.size() != 0) {
							for (int i = 0; busiOrderTimeList.size() > i; i++) {
								Map<String, String> param = new HashMap<String, String>();
								String itemSpecId = WSUtil.getXmlNodeText(busiOrderTimeList.get(i), "id");
								String value = WSUtil.getXmlNodeText(busiOrderTimeList.get(i), "value");
								param.put("itemSpecId", itemSpecId);
								param.put("value", value);
								busiOrderTimeInfoList.add(param);
							}
						}
						JSONArray busiOrderTimes = createBusiOrderTimes(busiOrderTimeInfoList, prodId, prodSpecId, boSeqCalculator);
						dataJs.elementOpt("busiOrderTimes", busiOrderTimes);
					}
				}

				// 构造boOffers
				JSONArray boOfferArrJs = new JSONArray();
				JSONObject boOfferJs = new JSONObject();
				boOfferJs.elementOpt("state", state);
				boOfferJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boOfferJs.elementOpt("statusCd", "S");
				boOfferJs.elementOpt("appStartDt", startDt);
				boOfferJs.elementOpt("appEndDt", endDt);
				boOfferArrJs.add(boOfferJs);
				dataJs.elementOpt("boOffers", boOfferArrJs);
				logger.debug("---------boOffers 完成！");

				// 构造ooOwners
				JSONArray ooOwnerArrJs = new JSONArray();
				JSONObject ooOwnerJs = new JSONObject();
				ooOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				ooOwnerJs.elementOpt("partyId", partyId.toString());
				ooOwnerJs.elementOpt("state", state);
				ooOwnerJs.elementOpt("statusCd", "S");
				ooOwnerArrJs.add(ooOwnerJs);
				dataJs.elementOpt("ooOwners", ooOwnerArrJs);
				logger.debug("---------ooOwners 完成！");

				// 退订销售品只需要构造基础数据
				if ("0".equals(actionTypeStr)) {
					// 构造ooParams
					// 属性处理
					List<Node> propertyNodes = null;
					if (properties != null) {
						propertyNodes = properties.selectNodes("./property");
					}
					if (propertyNodes != null && !propertyNodes.isEmpty()) {
						JSONArray ooParamArrJs = new JSONArray();
						for (Node propertie : propertyNodes) {
							String itemSpecId = propertie.selectSingleNode("./id").getText();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("offerSpecId", pricePlanCdStr);
							map.put("itemSpecId", itemSpecId);
							String offerSpecParamId = intfSMO.queryOfferSpecParamIdByItemSpecId(map);
							String newValue = propertie.selectSingleNode("./value").getText();
							if (StringUtils.isBlank(newValue)) {
								throw new BmoException(-1, "销售品" + pricePlanCdStr + "新增属性，值不能为空");
							}
							JSONObject ooParamJs = new JSONObject();
							ooParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							ooParamJs.elementOpt("itemSpecId", itemSpecId);
							ooParamJs.elementOpt("value", newValue);
							ooParamJs.elementOpt("state", CommonDomain.ADD);
							ooParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							ooParamJs.elementOpt("statusCd", "S");
							ooParamJs.elementOpt("appStartDt", startDt);
							ooParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(ooParamJs);
						}
						if (!ooParamArrJs.isEmpty()) {
							dataJs.elementOpt("ooParams", ooParamArrJs);
							logger.debug("---------ooParams 完成！");
						}
					}

					// 构造ooRoles
					if (offerRoles.size() > 0) {
						JSONArray ooRoleArrJs = new JSONArray();
						for (Map<String, Object> offerRoleMap : offerRoles) {
							JSONObject ooRoleJs = new JSONObject();
							ooRoleJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							ooRoleJs.elementOpt("state", state);
							ooRoleJs.elementOpt("statusCd", "S");
							ooRoleJs.elementOpt("startDt", startDt);
							ooRoleJs.elementOpt("endDt", endDt);
							logger.debug("prodId={}", prodId);
							if (prodId != null) {
								ooRoleJs.elementOpt("prodId", prodId);
							}
							// 获得销售品成员角色ID
							OfferRoles offerRole = (OfferRoles) offerRoleMap.get("offerRoles");
							ooRoleJs.elementOpt("offerRoleId", offerRole.getOfferRoleId());
							Integer objType = (Integer) offerRoleMap.get("objType");
							ooRoleJs.elementOpt("objType", objType);
							logger.debug("offerRole.getOfferRoleId()={}, objType={}", offerRole.getOfferRoleId(),
									objType);
							if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_PROD_SPEC)) {
								// 如果成员角色对象类型是产品规格时
								ooRoleJs.elementOpt("objId", prodSpecId);
								if (prodId != null) {
									ooRoleJs.elementOpt("objInstId", prodId);
								} else {
									ooRoleJs.elementOpt("objInstId", "-1");
								}
							} else if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_SERV_SPEC)) {
								// 如果成员角色对象类型是产服务规格时
								// 获取销售品成员角色对象ID
								RoleObj roleObj = intfSMO.findRoleObjByOfferRoleIdAndObjType(
										offerRole.getOfferRoleId(), objType);
								ooRoleJs.elementOpt("objId", roleObj.getObjId());
								logger.debug("roleObj.getObjId()={}", roleObj.getObjId());
								// 获得服务实例ID(现订购无需查看是否已有老服务，)
								String a = String.valueOf(boSeqCalculator.getNextServId());
								ooRoleJs.elementOpt("objInstId", a);
								servInstList.put(roleObj.getObjId().toString(), a);
							}
							ooRoleArrJs.add(ooRoleJs);
						}
						if (!ooRoleArrJs.isEmpty()) {
							dataJs.elementOpt("ooRoles", ooRoleArrJs);
							logger.debug("---------ooRoles 完成！");
						}

					} else {
						throw new BmoException(-1, "成员角色不存在");
					}
					//新的构造 bo2coupon wanghongli
					JSONArray boProd2CouponArrJs = createboProd2CouponsJson(couponsNode, boCreateParam.getProdId(),
							Long.valueOf(instId), boCreateParam.getPartyId(), boSeqCalculator, offerSpecId);
					dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
					// 构造bo_2_Coupons 这个好像不支持
					/*	List<Node> bo_Coupons = null;
					if (couponInfos != null) {
						bo_Coupons = couponInfos.selectNodes("./couponInfo");
					}
					if (bo_Coupons != null && !bo_Coupons.isEmpty()) {
						JSONArray boCoupons = new JSONArray();
						for (Node bo_Coupon : bo_Coupons) {
							String couponInstanceNumber = bo_Coupon.selectSingleNode("./couponInstanceNumber")
									.getText();
							String couponId = bo_Coupon.selectSingleNode("./couponId").getText();
							String couponinfoStatusCd = bo_Coupon.selectSingleNode("./couponinfoStatusCd").getText();
							String chargeItemCd = bo_Coupon.selectSingleNode("./chargeItemCd").getText();
							String couponUsageTypeCd = bo_Coupon.selectSingleNode("./couponUsageTypeCd").getText();
							String inOutTypeId = bo_Coupon.selectSingleNode("./inOutTypeId").getText();
							String inOutReasonId = bo_Coupon.selectSingleNode("./inOutReasonId").getText();
							String saleId = bo_Coupon.selectSingleNode("./saleId").getText();
							String agentId = bo_Coupon.selectSingleNode("./agentId").getText();
							String agentName = bo_Coupon.selectSingleNode("./agentName").getText();
							String storeName = bo_Coupon.selectSingleNode("./storeName").getText();
							String storeId = bo_Coupon.selectSingleNode("./storeId").getText();
							String couponNum = bo_Coupon.selectSingleNode("./couponNum").getText();
							String apCharge = bo_Coupon.selectSingleNode("./apCharge").getText();
							String ruleId = bo_Coupon.selectSingleNode("./ruleId").getText();

							JSONObject boCoupon = new JSONObject();
							boCoupon.elementOpt("id", "-1");
							boCoupon.elementOpt("couponInstanceNumber", couponInstanceNumber);
							boCoupon.elementOpt("couponUsageTypeCd", couponUsageTypeCd);
							boCoupon.elementOpt("inOutTypeId", inOutTypeId);
							boCoupon.elementOpt("inOutReasonId", inOutReasonId);
							boCoupon.elementOpt("saleId", saleId);
							boCoupon.elementOpt("couponId", couponId);
							boCoupon.elementOpt("couponinfoStatusCd", couponinfoStatusCd);
							boCoupon.elementOpt("chargeItemCd", chargeItemCd);
							boCoupon.elementOpt("couponNum", couponNum);
							boCoupon.elementOpt("storeId", storeId);
							boCoupon.elementOpt("storeName", storeName);
							boCoupon.elementOpt("agentId", agentId);
							boCoupon.elementOpt("agentName", agentName);
							boCoupon.elementOpt("apCharge", apCharge);
							boCoupon.elementOpt("ruleId", ruleId);
							boCoupon.elementOpt("partyId", partyId);
							boCoupon.elementOpt("offerId", instId);
							boCoupon.elementOpt("state", "ADD");
							boCoupon.elementOpt("instId", instId);
							boCoupon.elementOpt("prodId", prodId);
							boCoupons.add(boCoupon);
						}
						if (!boCoupons.isEmpty()) {
							dataJs.elementOpt("bo2Coupons", boCoupons);
							logger.debug("---------bo_2_Coupons 完成！");
						}
					}*/

					// 拼装费用节点 boAcctItems
					JSONArray boAcctItems = new JSONArray();
					JSONObject boAcctItem = new JSONObject();
					if(chargeInfos!= null&&chargeInfosTest == null){
						//做日志存储
						String logId = intfSMO.getIntfCommonSeq();
						Date requestTime = new Date();
						intfSMO.saveRequestInfo(logId, "test", "findQuestion", "应该费用节点："+chargeInfos.toString(), requestTime);
						
						
						intfSMO.saveResponseInfo(logId, "test", "findQuestion", "应该费用节点："+chargeInfos.asXML(), requestTime, "实际拿到费用节点"+chargeInfosTest.asXML(), new Date(), "1","0");
					}
					if (chargeInfos != null) {
						List<Node> chargeInfoList = chargeInfos.selectNodes("chargeInfo");
						for (int i = 0; chargeInfoList.size() > i; i++) {
							String specId = WSUtil.getXmlNodeText(chargeInfoList.get(i), "specId");
							if (offerSpecId.equals(specId)) {
								Map<String, String> itemAcctMap = new HashMap<String, String>();
								itemAcctMap.put("payMethod", WSUtil.getXmlNodeText(chargeInfoList.get(i), "payMethod"));
								itemAcctMap.put("acctItemTypeId", WSUtil.getXmlNodeText(chargeInfoList.get(i),
										"acctItemTypeId"));
								itemAcctMap.put("charge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "charge"));
								itemAcctMap.put("appCharge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "appCharge"));
								boAcctItem = createBoAcctItems(itemAcctMap);
								boAcctItems.add(boAcctItem);
							}
						}
						if (boAcctItems.size() != 0) {
							dataJs.elementOpt("boAcctItems", boAcctItems);
						}

					}
				}
			} else if (actionTypeStr != null && actionTypeStr.equals("2")) {
				// 构造ooParams
				// 属性的变更
				JSONArray ooParamArrJs = new JSONArray();
				if (properties != null) {
					String newValue = "";
					List propertyNodes = properties.selectNodes("./property");
					Set<String> repeatProperty = new HashSet<String>();
					for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("offerSpecId", pricePlanCdStr);
						Node propertie = (Node) itr1.next();
						String itemSpecId = propertie.selectSingleNode("./id").getText();
						if (repeatProperty.contains(itemSpecId)) {
							throw new BmoException(-1, "销售品同一属性重复修改");
						}
						map.put("itemSpecId", itemSpecId);
						String offerSpecParamId = intfSMO.queryOfferSpecParamIdByItemSpecId(map);
						//String newValueStr = propertie.selectSingleNode("./value").getText();
						//String newName = propertie.selectSingleNode("./name").getText();
						// modify wanghongli 防止空节点报空指针异常
						String newValueStr = WSUtil.getXmlNodeText(propertie, "./value");
						String newName = WSUtil.getXmlNodeText(propertie, "./name");

						repeatProperty.add(itemSpecId);
						// 删除老的属性
						JSONObject oldOoParamJs = new JSONObject();
						// 变更的时候传入oldValue
						String oldValue = "";
						Long oldOfferParamId = null;
						String flag = "N";
						for (OfferParam offerParam : offerParams) {
							if (offerSpecParamId.equals(offerParam.getOfferSpecParamId().toString())) {
								oldValue = offerParam.getValue();
								oldOfferParamId = offerParam.getOfferParamId();
								flag = "Y";
							}
						}
						if ("Y".equals(flag)) {
							if (StringUtils.isNotBlank(oldValue) && oldValue.equals(newValueStr)) {
								throw new BmoException(-1, newName + "新属性值与旧值相同，无需修改");
							}
							oldOoParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							oldOoParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							oldOoParamJs.elementOpt("itemSpecId", itemSpecId);
							oldOoParamJs.elementOpt("value", oldValue);
							oldOoParamJs.elementOpt("offerParamId", oldOfferParamId);
							oldOoParamJs.elementOpt("state", CommonDomain.DEL);
							oldOoParamJs.elementOpt("statusCd", "S");
							oldOoParamJs.elementOpt("appStartDt", startDt);
							oldOoParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(oldOoParamJs);
						}
						if (StringUtils.isNotBlank(newValueStr)) {
							// 新属性
							JSONObject newOoParamJs = new JSONObject();
							newOoParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							newOoParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							newOoParamJs.elementOpt("itemSpecId", itemSpecId);
							newOoParamJs.elementOpt("value", newValueStr);
							newOoParamJs.elementOpt("state", CommonDomain.ADD);
							newOoParamJs.elementOpt("statusCd", "S");
							newOoParamJs.elementOpt("appStartDt", startDt);
							newOoParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(newOoParamJs);
						}

					}
					dataJs.elementOpt("ooParams", ooParamArrJs);
					logger.debug("---------ooParams 完成！");
				}
			}
			// 构造ooTimes
			JSONArray ooTimes = CreateOoTimes(boSeqCalculator, startDt, endDt, startFashion, endFashion, actionType,time_unit);
			dataJs.elementOpt("ooTimes", ooTimes);

			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		return busiOrderArrJs;
	}
	
	/**
	 * 创建busiOrderTimes
	 * 
	 * @return
	 */
	private JSONArray createBusiOrderTimes(List<Map<String, String>> orderTimeItemInfo, Long prodId, Integer prodSpecId,
			BoSeqCalculator boSeqCalculator) {
		// 构造boProds
		JSONArray busiOrderTimes = new JSONArray();
		for (int i = 0; i < orderTimeItemInfo.size(); i++) {
			Map<String, String> itemInfo = orderTimeItemInfo.get(i);
			JSONObject busiOrderTime = new JSONObject();
			busiOrderTime.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
			busiOrderTime.elementOpt("state", "ADD");
			busiOrderTime.elementOpt("statusCd", "S");
			busiOrderTime.elementOpt("areaId", "11000");
			busiOrderTime.elementOpt("prodId", prodId);
			busiOrderTime.elementOpt("prodSpecId", prodSpecId);
			busiOrderTime.elementOpt("itemSpecId", itemInfo.get("itemSpecId"));
			busiOrderTime.elementOpt("value", itemInfo.get("value"));
			busiOrderTimes.add(busiOrderTime);
		}
		return busiOrderTimes;
	}

	// 构造boAcctItems
	private JSONObject createBoAcctItems(Map<String, String> boAcctItemMap) {

		JSONObject boAcctItem = new JSONObject();
		boAcctItem.elementOpt("acctItemTypeId", boAcctItemMap.get("acctItemTypeId"));
		boAcctItem.elementOpt("amount", boAcctItemMap.get("charge"));
		boAcctItem.element("platId", interfaceId == null || "".equals(interfaceId) ? "08" : interfaceId);
		boAcctItem.elementOpt("createdDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("payedDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("statusCd", "5JA");
		boAcctItem.elementOpt("payMethodId", boAcctItemMap.get("payMethod"));
		boAcctItem.elementOpt("realAmount", boAcctItemMap.get("appCharge"));
		boAcctItem.elementOpt("currency", "");
		boAcctItem.elementOpt("ratioMethod", "");
		boAcctItem.elementOpt("ratio", "");
		return boAcctItem;
	}

	/**
	 * 处理客户信息
	 * 
	 * @param ownerInfosNode
	 * @param areaId
	 * @param partyId
	 * @return
	 */
	private JSONObject processOwnerInfos(Node ownerInfosNode, String areaId, Long partyId,
			BoSeqCalculator boSeqCalculator, BoCreateParam boCreateParam) {
		JSONObject busiOrderJs = new JSONObject();

		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("instId", (partyId != null) ? partyId.toString() : "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "1");
		boActionTypeJs.elementOpt("boActionTypeCd", "C2");
		boActionTypeJs.elementOpt("name", "修改客户信息");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		List infos = ownerInfosNode.selectNodes("./indentInfo");

		JSONArray boCustIdentitiesArrJs = new JSONArray();
		logger.debug("ownerInfoNode's xml:" + ownerInfosNode.asXML());
		Party party = null;
		for (Iterator itr = infos.iterator(); itr.hasNext();) {
			Node infoNode = (Node) itr.next();
			List<PartyIdentity> partyIdentityList = party.getIdentities();// 先查找出客户所有的证件信息
			Map<String, Object> IdentityMap = new HashMap<String, Object>();
			if (partyIdentityList != null && partyIdentityList.size() > 0) {
				for (PartyIdentity pi : partyIdentityList) {
					IdentityMap.put(pi.getIdentidiesTypeCd().toString(), pi.getIdentityNum());
				}
			}
			String indentNbrType = infoNode.selectSingleNode("./indentNbrType").getText();
			String indentNbr = infoNode.selectSingleNode("./indentNbr") != null ? infoNode.selectSingleNode(
					"./indentNbr").getText() : null;
			String actionType = infoNode.selectSingleNode("./actionType").getText();
			if (actionType != null && actionType.equals("0")) {
				JSONObject boCustIdentitiesJs = new JSONObject();
				boCustIdentitiesJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustIdentitiesJs.elementOpt("identidiesTypeCd", indentNbrType);
				boCustIdentitiesJs.elementOpt("identityNum", indentNbr);
				boCustIdentitiesJs.elementOpt("state", "ADD");
				boCustIdentitiesJs.elementOpt("statusCd", "S");
				boCustIdentitiesArrJs.add(boCustIdentitiesJs);
			} else if (actionType != null && actionType.equals("1") && IdentityMap.get(indentNbrType) != null) {
				JSONObject boCustIdentitiesJs = new JSONObject();
				boCustIdentitiesJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustIdentitiesJs.elementOpt("identidiesTypeCd", indentNbrType);
				boCustIdentitiesJs.elementOpt("identityNum", IdentityMap.get(indentNbrType).toString());
				boCustIdentitiesJs.elementOpt("state", "DEL");
				boCustIdentitiesJs.elementOpt("statusCd", "S");
				boCustIdentitiesArrJs.add(boCustIdentitiesJs);
			} else if (actionType != null && actionType.equals("2")) {
				// 新增客户证件
				JSONObject newBoCustIdentitiesJs = new JSONObject();
				newBoCustIdentitiesJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				newBoCustIdentitiesJs.elementOpt("identidiesTypeCd", indentNbrType);
				newBoCustIdentitiesJs.elementOpt("identityNum", indentNbr);
				newBoCustIdentitiesJs.elementOpt("state", "ADD");
				newBoCustIdentitiesJs.elementOpt("statusCd", "S");
				boCustIdentitiesArrJs.add(newBoCustIdentitiesJs);
				// 如果存在就得证件就删除旧客户证件
				if (IdentityMap.get(indentNbrType) != null) {
					JSONObject oldBoCustIdentitiesJs = new JSONObject();
					oldBoCustIdentitiesJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					oldBoCustIdentitiesJs.elementOpt("identidiesTypeCd", indentNbrType);
					oldBoCustIdentitiesJs.elementOpt("identityNum", IdentityMap.get(indentNbrType).toString());
					oldBoCustIdentitiesJs.elementOpt("state", "DEL");
					oldBoCustIdentitiesJs.elementOpt("statusCd", "S");
					boCustIdentitiesArrJs.add(oldBoCustIdentitiesJs);
				}
			}
		}
		dataJs.element("boCustIdentities", boCustIdentitiesArrJs);
		// 设置客户基本信息
		Element baseInfo = (Element) ownerInfosNode.selectSingleNode("./baseInfo");
		if (baseInfo != null) {
			Node nameNode = baseInfo.selectSingleNode("name");
			Node areaIdNode = baseInfo.selectSingleNode("areaId");
			Node partyTypeCdNode = baseInfo.selectSingleNode("partyTypeCd");
			Node defaultIdTypeNode = baseInfo.selectSingleNode("defaultIdType");
			Node industryClassCdNode = baseInfo.selectSingleNode("industryClassCd");
			Node professionCdNode = baseInfo.selectSingleNode("professionCd");
			Node addressIdNode = baseInfo.selectSingleNode("addressId");
			Node addressStrNode = baseInfo.selectSingleNode("addressStr");
			Node mailAddressIdNode = baseInfo.selectSingleNode("mailAddressId");
			Node mailAddressStrNode = baseInfo.selectSingleNode("mailAddressStr");
			Node businessPasswordNode = baseInfo.selectSingleNode("businessPassword");
			Node queryPasswordNode = baseInfo.selectSingleNode("queryPassword");
			JSONArray jsonArrayBoCustInfos = new JSONArray();
			JSONObject jsonBoCustInfos = new JSONObject();
			jsonBoCustInfos.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			if (businessPasswordNode != null && businessPasswordNode.getText().length() > 0) {
				jsonBoCustInfos.put("businessPassword", businessPasswordNode.getText());
			} else {
				jsonBoCustInfos.put("businessPassword", party.getBusinessPassword());
			}
			if (queryPasswordNode != null && queryPasswordNode.getText().length() > 0) {
				jsonBoCustInfos.put("queryPassword", queryPasswordNode.getText());
			} else {
				jsonBoCustInfos.put("queryPassword", party.getQueryPassword());
			}
			if (nameNode != null && nameNode.getText().length() > 0) {
				jsonBoCustInfos.put("name", nameNode.getText());
				jsonBoCustInfos.put("simpleSpell", Cn2Spell.getCapSpell(nameNode.getText()));
			} else {
				jsonBoCustInfos.put("name", party.getPartyName());
				jsonBoCustInfos.put("simpleSpell", party.getSimpleSpell());
			}
			if (areaIdNode != null && areaIdNode.getText().length() > 0) {
				jsonBoCustInfos.put("areaId", areaIdNode.getText());
			} else {
				jsonBoCustInfos.put("areaId", party.getAreaId());
			}
			if (addressIdNode != null && addressIdNode.getText().length() > 0) {
				jsonBoCustInfos.put("addressId", addressIdNode.getText());
			} else {
				jsonBoCustInfos.put("addressId", party.getAddressId());
			}
			if (addressStrNode != null && addressStrNode.getText().length() > 0) {
				jsonBoCustInfos.put("addressStr", addressStrNode.getText());
			} else {
				jsonBoCustInfos.put("addressStr", party.getAddressStr());
			}
			if (defaultIdTypeNode != null && defaultIdTypeNode.getText().length() > 0) {
				jsonBoCustInfos.put("defaultIdType", defaultIdTypeNode.getText());
			} else {
				jsonBoCustInfos.put("defaultIdType", party.getDefaultIdType());
			}
			if (mailAddressIdNode != null && mailAddressIdNode.getText().length() > 0) {
				jsonBoCustInfos.put("mailAddressId", mailAddressIdNode.getText());
			} else {
				jsonBoCustInfos.put("mailAddressId", party.getMailAddressId());
			}
			if (mailAddressStrNode != null && mailAddressStrNode.getText().length() > 0) {
				jsonBoCustInfos.put("mailAddressStr", mailAddressStrNode.getText());
			} else {
				jsonBoCustInfos.put("mailAddressStr", party.getMailAddressStr());
			}
			if (partyTypeCdNode != null && partyTypeCdNode.getText().length() > 0) {
				jsonBoCustInfos.put("partyTypeCd", partyTypeCdNode.getText());
			} else {
				jsonBoCustInfos.put("partyTypeCd", party.getPartyTypeCd());
			}
			if (industryClassCdNode != null && industryClassCdNode.getText().length() > 0) {
				jsonBoCustInfos.put("industryClassCd", industryClassCdNode.getText());
			} else {
				jsonBoCustInfos.put("industryClassCd", party.getIndustryClassCd());
			}
			if (professionCdNode != null && professionCdNode.getText().length() > 0) {
				jsonBoCustInfos.put("professionCd", professionCdNode.getText());
			} else {
				jsonBoCustInfos.put("professionCd", party.getIndividual() != null ? party.getIndividual()
						.getProfessionCd() : "");
			}
			jsonBoCustInfos.put("segmentId", 1);
			jsonBoCustInfos.put("state", "ADD");
			jsonBoCustInfos.put("statusCd", "S");
			logger.debug("客户业务动作信息：\n{}", jsonBoCustInfos.toString());
			jsonArrayBoCustInfos.add(0, jsonBoCustInfos);
			dataJs.put("boCustInfos", jsonArrayBoCustInfos);
		}
		// 客户扩展信息
		Node profileInfo = ownerInfosNode.selectSingleNode("./profileInfo");
		if (profileInfo != null) {
			// 客户扩展信息
			List profiles = profileInfo.selectNodes("./profile");
			JSONArray jsonArrayBoCustProfiles = new JSONArray();
			List<PartyProfile> partyProfileList = party.getProfiles();// 先查找出客户所有的自然特征信息
			Map<String, String> profileMap = new HashMap<String, String>();
			if (partyProfileList != null && partyProfileList.size() > 0) {
				for (PartyProfile pp : partyProfileList) {
					profileMap.put(pp.getPartyProfileCatgCd().toString(), pp.getProfileValue());
				}
			}
			for (Iterator itr = profiles.iterator(); itr.hasNext();) {
				Node profile = (Node) itr.next();
				String profileCatgCd = profile.selectSingleNode("./profileCatgCd").getText();
				String profileValue = profile.selectSingleNode("./profileValue").getText();
				String actionType = profile.selectSingleNode("./actionType").getText();
				if (actionType != null && actionType.equals("0")) {
					// 新增
					JSONObject boCustProfile = new JSONObject();
					boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					boCustProfile.put("partyProfileCatgCd", profileCatgCd);
					boCustProfile.put("state", "ADD");
					boCustProfile.put("statusCd", "S");
					boCustProfile.put("profileValue", profileValue);
					jsonArrayBoCustProfiles.add(boCustProfile);
				} else if (actionType != null && actionType.equals("1")) {
					// 删除
					JSONObject boCustProfile = new JSONObject();
					boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					boCustProfile.put("partyProfileCatgCd", profileCatgCd);
					boCustProfile.put("state", "DEL");
					boCustProfile.put("statusCd", "S");
					boCustProfile.put("profileValue", profileValue);
					jsonArrayBoCustProfiles.add(boCustProfile);
				} else if (actionType != null && actionType.equals("2")) {
					// 如果存在就先删除
					if (profileMap.get(profileCatgCd) != null) {
						JSONObject oldBoCustProfile = new JSONObject();
						oldBoCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						oldBoCustProfile.put("partyProfileCatgCd", profileCatgCd);
						oldBoCustProfile.put("state", "DEL");
						oldBoCustProfile.put("statusCd", "S");
						oldBoCustProfile.put("profileValue", profileMap.get(profileCatgCd));
						jsonArrayBoCustProfiles.add(oldBoCustProfile);
					}
					// 新增
					JSONObject newBoCustProfile = new JSONObject();
					newBoCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					newBoCustProfile.put("partyProfileCatgCd", profileCatgCd);
					newBoCustProfile.put("state", "ADD");
					newBoCustProfile.put("statusCd", "S");
					newBoCustProfile.put("profileValue", profileValue);
					jsonArrayBoCustProfiles.add(newBoCustProfile);
				}
			}
			dataJs.put("boCustProfiles", jsonArrayBoCustProfiles);
		}
		// 订单属性
		JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
		if (!busiOrderAttrArrObj.isEmpty()) {
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
		}
		busiOrderJs.elementOpt("data", dataJs);

		return busiOrderJs;
	}

	/**
	 * 处理密码节点
	 * 
	 * @param passwordNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONObject processPassword(Node passwordNode, String prodSpecId, Long prodId, String areaId,
			BoSeqCalculator boSeqCalculator) {
		String oldPwd = null;
		String newPwd = null;
		String passwordType = "2";
		oldPwd = WSUtil.getXmlNodeText(passwordNode, "./oldPassword");
		newPwd = WSUtil.getXmlNodeText(passwordNode, "./newPassword");
		passwordType = WSUtil.getXmlNodeText(passwordNode, "./prodPwTypeCd");
		if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
			if (oldPwd.equals(newPwd)) {
				throw new BmoException(-1, "新旧密码相同，请重新输入。");
			}
		} else if (StringUtils.isBlank(oldPwd) && StringUtils.isBlank(newPwd)) {
			throw new BmoException(-1, "新旧密码为空，请重新输入");
		}

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "18");
		boActionTypeJs.elementOpt("name", "改产品密码");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 构造boProdPasswords
		JSONArray boProdPasswordArrJs = createBoProdPasswordsJson(newPwd, oldPwd, passwordType, boSeqCalculator);

		dataJs.elementOpt("boProdPasswords", boProdPasswordArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 处理密码节点
	 * 
	 * @param passwordNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONObject processPasswordReset(Node passwordNode, String prodSpecId, Long prodId, String areaId,
			BoSeqCalculator boSeqCalculator) {
		String oldPwd = null;
		String newPwd = null;
		String passwordType = "2";
		oldPwd = WSUtil.getXmlNodeText(passwordNode, "./oldPassword");
		newPwd = WSUtil.getXmlNodeText(passwordNode, "./newPassword");
		passwordType = WSUtil.getXmlNodeText(passwordNode, "./prodPwTypeCd");
		if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
			if (oldPwd.equals(newPwd)) {
				throw new BmoException(-1, "新旧密码相同，请重新输入。");
			}
		} else if (StringUtils.isBlank(oldPwd) && StringUtils.isBlank(newPwd)) {
			throw new BmoException(-1, "新旧密码为空，请重新输入");
		}

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "201407");
		boActionTypeJs.elementOpt("name", "用户密码重置");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 构造boProdPasswords
		JSONArray boProdPasswordArrJs = createBoProdPasswordsJson(newPwd, oldPwd, passwordType, boSeqCalculator);

		dataJs.elementOpt("boProdPasswords", boProdPasswordArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 处理改速率信息
	 * 
	 * @param prodPropertysNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONObject processSpeedRate(Node prodPropertysNode, String prodSpecId, Long prodId, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "51");
		boActionTypeJs.elementOpt("name", "改速率");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		JSONArray boProdItemArrJs = createProdItemsJson(prodPropertysNode, prodId, boSeqCalculator);
		dataJs.elementOpt("boProdItems", boProdItemArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 处理改产品加入组合的属性信息
	 * 
	 * @param prodPropertysNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONObject processProdCompPropertys(Node prodCompPropertysNode, String prodSpecId, Long prodId,
			String areaId, BoSeqCalculator boSeqCalculator) {

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "54");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		if (prodCompPropertysNode != null) {
			List compProdNodes = prodCompPropertysNode.selectNodes("./compProd");
			if (compProdNodes != null && !compProdNodes.isEmpty()) {
				for (Iterator itr1 = compProdNodes.iterator(); itr1.hasNext();) {
					Element compProdNode = (Element) itr1.next();
					String compProdIdStr = compProdNode.attributeValue("compProdId");
					if (compProdIdStr == null || "".equals(compProdIdStr.trim())) {
						throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30013, "入参缺少关联的组合产品的ID");
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("prodId", prodId);
					param.put("compProdId", Long.valueOf(compProdIdStr.trim()));
					Map<String, Object> infoComp = intfSMO.findOfferProdComp(param);
					if (infoComp == null) {
						throw new BmoException(-1, "prodId：" + prodId + "与compProdId：" + compProdIdStr.trim()
								+ "组合关系不存在");
					}
					String prodCompId = infoComp.get("PROD_COMP_ID").toString();
					String prodCompRelaRoleCd = infoComp.get("PROD_COMP_RELA_ROLE_CD").toString();

					JSONArray boProdCompOrdersJs = createBoProdCompOrders(compProdIdStr, prodCompId, prodCompRelaRoleCd);
					dataJs.elementOpt("boProdCompOrders", boProdCompOrdersJs);

					JSONArray boProdCompItemArrJs = createBoProdCompItems(compProdNode, prodId, Long
							.valueOf(compProdIdStr.trim()), prodCompId, boSeqCalculator);
					if (!boProdCompItemArrJs.isEmpty()) {
						dataJs.elementOpt("boProdCompItems", boProdCompItemArrJs);
					}
				}
			}
		}
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	/**
	 * 处理改产品属性信息
	 * 
	 * @param prodPropertysNode
	 * @param prod
	 * @param areaId
	 * @return
	 */
	private JSONObject processProdPropertys(Node prodPropertysNode, String prodSpecId, Long prodId, String areaId,
			BoSeqCalculator boSeqCalculator) {

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("instId", prodId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "1179");
		boActionTypeJs.elementOpt("name", "修改产品属性");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		JSONArray boProdItemArrJs = createProdItemsJson(prodPropertysNode, prodId, boSeqCalculator);
		dataJs.elementOpt("boProdItems", boProdItemArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		return busiOrderJs;
	}

	// 处理成员变更信息（主产品动作）
	private JSONArray processMemberChangeMain(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderCompChange = createBusiOrderCompChange(boCreateParamGrp, areaId, boSeqCalculator);
		busiOrderArrJs.addAll(busiOrderCompChange);
		return busiOrderArrJs;
	}
	// 处理成员新增（主产品动作）
	private JSONArray processMemberAddMain(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderCompChange = createBusiOrderCompAdd(boCreateParamGrp, areaId, boSeqCalculator);
		busiOrderArrJs.addAll(busiOrderCompChange);
		return busiOrderArrJs;
	}

	// 处理销售品实例成员变更信息（销售品动作）
	private JSONArray processOfferMemberChange(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator, JSONArray mainOrderBusiOrderArr) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderMemberChange = createBusiOrderOfferMemberChange(boCreateParamGrp, areaId, boSeqCalculator,
				mainOrderBusiOrderArr);
		busiOrderArrJs.addAll(busiOrderMemberChange);
		return busiOrderArrJs;
	}

	// 处理成员变更信息（成员产品动作）
	private JSONArray processMemberChange(Node prodCompPropertysNode, BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderMemberChange = createBusiOrderMemberChange(prodCompPropertysNode, boCreateParamGrp, areaId,
				boSeqCalculator);
		busiOrderArrJs.addAll(busiOrderMemberChange);
		return busiOrderArrJs;
	}

	/**
	 * 添加产品状态变化信息到业务动作
	 * 
	 * @param busiOrderJs
	 * @param prodStatusCd
	 * @param state
	 * @param curDate
	 * @param boSeqCalculator
	 */
	private void addProdStateChangeInfo(JSONObject busiOrderJs, Integer prodStatusCd, String state, String curDate,
			BoSeqCalculator boSeqCalculator) {
		JSONObject dataJs = null;
		if (busiOrderJs.containsKey("data")) {
			dataJs = busiOrderJs.getJSONObject("data");
		} else {
			dataJs = new JSONObject();
		}
		JSONArray boProdStatusArrJs = null;
		if (dataJs.containsKey("boProdStatuses")) {
			boProdStatusArrJs = dataJs.getJSONArray("boProdStatuses");
		} else {
			boProdStatusArrJs = new JSONArray();
		}
		JSONObject boProdStatusJs = createProdStatusChange(prodStatusCd, state, curDate, boSeqCalculator);
		boProdStatusArrJs.add(boProdStatusJs);
		dataJs.elementOpt("boProdStatuses", boProdStatusArrJs);
		busiOrderJs.elementOpt("data", dataJs);
		logger.debug("--boProdStatuses 完成！");
	}

	/**
	 * 处理其他订单
	 * 
	 * @param order
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @param orderTypeId
	 * @return
	 */
	private JSONArray processOtherOrder(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator,
			String orderTypeId) {

		String areaId = boCreateParam.getAreaId();
		String prodId = boCreateParam.getProdId() != null ? boCreateParam.getProdId().toString() : null;
		String prodSpecId = boCreateParam.getProdSpecId();
		JSONArray busiOrderArr = new JSONArray();
		// 产品的业务动作构造
		JSONObject busiOrderJs = createBlankBusiOrder(areaId, prodSpecId, prodId, String
				.valueOf(CommonDomain.ACTION_CLASS_PROD_ACTION), orderTypeId, boSeqCalculator);
		busiOrderArr.add(busiOrderJs);
		return busiOrderArr;
	}

	/**
	 * 构造一个空的业务动作（没有过程数据）
	 * 
	 * @param areaId
	 * @param objId
	 * @param instId
	 * @param actionClassCd
	 * @param boActionTypeCd
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject createBlankBusiOrder(String areaId, String objId, String instId, String actionClassCd,
			String boActionTypeCd, BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArr = new JSONArray();
		// 产品的业务动作构造
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", objId);
		busiObjJs.elementOpt("instId", instId);
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", actionClassCd);
		boActionTypeJs.elementOpt("boActionTypeCd", boActionTypeCd);
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");
		return busiOrderJs;
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
	private JSONObject createAddAccountBusiOrder(Node order, String areaId, BoCreateParam boCreateParam,
			BoSeqCalculator boSeqCalculator) {
		String addAccountBusiOrderTemplate = "{'areaId':0,'data':{'boAccountInfos':[{'partyId':0,'acctName':'','acctCd':'','acctId':-1,'businessPassword':'','prodId':'','state':'ADD','statusCd':'S','atomActionId':-1}],'boPaymentAccounts':[{'paymentAccountId':-1,'paymentAcctTypeCd':1,'bankId':'','bankAcct':'','paymentMan':'','limitQty':'','atomActionId':-2,'state':'ADD','statusCd':'S'}],'boAcct2PaymentAccts':[{'paymentAccountId':-1,'priority':1,'startDate':'','endDate':'3000-01-01','state':'ADD','statusCd':'S','atomActionId':-3}]},'boActionType':{'actionClassCd':2,'boActionTypeCd':'A1'},'busiObj':{'instId':-1},'busiOrderInfo':{'seq':-1,'statusCd':'S'}}";
		JSONObject busiOrderJs = JSONObject.fromObject(addAccountBusiOrderTemplate);
		String partyId = null;
		String partyName = null;
		// 取订单类型，过户时新增帐户要增加到新客户名下
		Node orderTypeIdNode = order.selectSingleNode("./orderTypeId");
		String orderTypeId = "," + orderTypeIdNode.getText().trim() + ",";
		if (orderTypeId.indexOf(",11,") != -1) {// 有过户动作
			Node newPartyIdNode = order.selectSingleNode("./newPartyId");
			if (newPartyIdNode != null) {
				partyId = newPartyIdNode.getText();
			} else {
				throw new BmoException(-1, "过户同时新增帐户没有传入新客户ID");
			}
		} else {
			partyId = boCreateParam.getPartyId().toString();
		}

		Party party = custFacade.getPartyById(partyId);
		if (party != null) {
			partyName = party.getPartyName();
		} else {
			throw new BmoException(-1, "新增帐户查不到客户信息：partyId=" + partyId);
		}

		busiOrderJs.getJSONObject("busiOrderInfo").put("seq", boSeqCalculator.getNextSeqString());
		// 数据节点
		busiOrderJs.put("areaId", areaId);

		JSONObject boAccountInfo = busiOrderJs.getJSONObject("data").getJSONArray("boAccountInfos").getJSONObject(0);
		boAccountInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAccountInfo.put("acctId", boCreateParam.getAcctId());
		boAccountInfo.put("partyId", partyId);
		boAccountInfo.put("acctName", partyName);
		boAccountInfo.put("acctCd", boCreateParam.getAcctCd());
		logger.debug("填充boAccountInfo完成!");
		JSONObject boPaymentAccounts = busiOrderJs.getJSONObject("data").getJSONArray("boPaymentAccounts")
				.getJSONObject(0);
		boPaymentAccounts.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boPaymentAccounts.put("paymentMan", partyName);
		JSONObject boAcct2PaymentAcct = busiOrderJs.getJSONObject("data").getJSONArray("boAcct2PaymentAccts")
				.getJSONObject(0);
		boAcct2PaymentAcct.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAcct2PaymentAcct.put("startDate", boCreateParam.getCurDate());
		logger.debug("填充boAcct2PaymentAcct完成!");
		return busiOrderJs;
	}

	/**
	 * 创建订单属性公共方法
	 * 
	 * @param boCreateParam
	 * @return
	 */
	private JSONArray buildBusiOrderAttrObj(BoCreateParam boCreateParam) {
		JSONArray busiOrderAttrArrObj = new JSONArray();
		if (boCreateParam != null) {
			// 联系人和联系电话处理
			String linkMan = null;
			String linkNbr = null;
			if (boCreateParam.getLinkMan() != null && !boCreateParam.getLinkMan().equals("")) {
				linkMan = boCreateParam.getLinkMan();
			}
			if (boCreateParam.getLinkNbr() != null && !boCreateParam.getLinkNbr().equals("")) {
				linkNbr = boCreateParam.getLinkNbr();
			}
			JSONArray busiOrderAttrArrObj1 = createBusiOrderAttrs(linkMan, linkNbr);
			busiOrderAttrArrObj.addAll(busiOrderAttrArrObj1);
		}
		// 添加协销售人处理
		String assistMan = boCreateParam.getAssistMan();
		if (assistMan != null && !assistMan.trim().equals("")) {
			JSONObject busiOrderAttrJs = new JSONObject();
			busiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_ASSIST_MAN);
			busiOrderAttrJs.elementOpt("value", assistMan);
			busiOrderAttrJs.elementOpt("state", "ADD");
			busiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrArrObj.add(busiOrderAttrJs);
		}
		// 如果还有其他的订单属性，则对其进行处理
		List<Element> coItemList = boCreateParam.getCoItemList();
		if (coItemList != null && coItemList.size() > 0) {
			JSONArray busiOrderAttrArrObj2 = createBusiOrderAttrs(coItemList);
			busiOrderAttrArrObj.addAll(busiOrderAttrArrObj2);
		}
		return busiOrderAttrArrObj;
	}

	/**
	 * 业务动作属性数据
	 * 
	 * @param coLinkManNode
	 * @param coLinkNbrNode
	 * @return
	 */
	private JSONArray createBusiOrderAttrs(String linkMan, String linkNbr) {
		// 构造busiOrderAttrs
		JSONArray busiOrderAttrs = new JSONArray();

		if (linkMan != null && !linkMan.trim().equals("")) {
			JSONObject linkManBusiOrderAttrJs = new JSONObject();
			linkManBusiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_LINK_MAN);
			linkManBusiOrderAttrJs.elementOpt("value", linkMan);
			linkManBusiOrderAttrJs.elementOpt("state", "ADD");
			linkManBusiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrs.add(linkManBusiOrderAttrJs);
		}

		if (linkNbr != null && !linkNbr.trim().equals("")) {
			JSONObject linkNbrBusiOrderAttrJs = new JSONObject();
			linkNbrBusiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_LINK_TEL);
			linkNbrBusiOrderAttrJs.elementOpt("value", linkNbr);
			linkNbrBusiOrderAttrJs.elementOpt("state", "ADD");
			linkNbrBusiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrs.add(linkNbrBusiOrderAttrJs);
		}
		return busiOrderAttrs;
	}

	/**
	 * 业务动作属性数据
	 * 
	 * @param coItemList
	 * @return
	 */
	private JSONArray createBusiOrderAttrs(List<Element> coItemList) {
		// 构造busiOrderAttrs
		JSONArray busiOrderAttrs = new JSONArray();
		if (coItemList != null && coItemList.size() > 0) {
			for (Element coItem : coItemList) {
				JSONObject busiOrderAttrJs = new JSONObject();
				Node itemSpecIdNode = coItem.selectSingleNode("./id");
				Node valueNode = coItem.selectSingleNode("./value");
				if (itemSpecIdNode != null && itemSpecIdNode.getText().length() > 0 && valueNode != null
						&& valueNode.getText().length() > 0) {
					busiOrderAttrJs.elementOpt("itemSpecId", itemSpecIdNode.getText());
					busiOrderAttrJs.elementOpt("value", valueNode.getText());
					busiOrderAttrJs.elementOpt("state", "ADD");
					busiOrderAttrJs.elementOpt("statusCd", "S");
					busiOrderAttrs.add(busiOrderAttrJs);
				}
			}
		}
		return busiOrderAttrs;
	}

	/**
	 * 创建boProds
	 * 
	 * @return
	 */
	private JSONArray createBoProds(BoSeqCalculator boSeqCalculator) {
		// 构造boProds
		JSONArray boProdArrJs = new JSONArray();
		JSONObject boProdJs = new JSONObject();
		boProdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boProdJs.elementOpt("state", "ADD");
		boProdJs.elementOpt("statusCd", "S");
		boProdJs.elementOpt("appStartDt", "");
		boProdJs.elementOpt("appEndDt", "");
		boProdJs.elementOpt("startDt", "");
		boProdJs.elementOpt("endDt", "");
		boProdArrJs.add(boProdJs);
		return boProdArrJs;
	}

	/**
	 * 创建boProdStatuses
	 * 
	 * @return
	 */
	private JSONArray createBoProdStatuses(String prodStatusCd, String action, BoSeqCalculator boSeqCalculator) {
		// 构造boProdStatuses
		JSONArray boProdStatusArrJs = new JSONArray();
		JSONObject boProdStatusJs = new JSONObject();
		boProdStatusJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdStatusJs.elementOpt("prodStatusCd", prodStatusCd);
		boProdStatusJs.elementOpt("state", action);
		boProdStatusJs.elementOpt("statusCd", "S");
		boProdStatusArrJs.add(boProdStatusJs);
		return boProdStatusArrJs;
	}

	/**
	 * 创建boProdSpecs
	 * 
	 * @return
	 */
	private JSONArray createBoProdSpecs(String newProdSpecId, String oldProdSpecId, BoSeqCalculator boSeqCalculator) {
		// 构造boProdSpecs
		JSONArray boProdSpecArrJs = new JSONArray();
		if (newProdSpecId != null) {
			JSONObject newBoProdSpecJs = new JSONObject();
			newBoProdSpecJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProdSpecJs.elementOpt("prodSpecId", newProdSpecId);
			newBoProdSpecJs.elementOpt("state", "ADD");
			newBoProdSpecJs.elementOpt("statusCd", "S");
			boProdSpecArrJs.add(newBoProdSpecJs);
		}
		if (oldProdSpecId != null) {
			JSONObject oldBoProdSpecJs = new JSONObject();
			oldBoProdSpecJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			oldBoProdSpecJs.elementOpt("prodSpecId", oldProdSpecId);
			oldBoProdSpecJs.elementOpt("state", "DEL");
			oldBoProdSpecJs.elementOpt("statusCd", "S");
			boProdSpecArrJs.add(oldBoProdSpecJs);
		}
		return boProdSpecArrJs;
	}

	/**
	 * 创建boProdFeeTypes
	 * 
	 * @return
	 */
	private JSONArray createBoProdFeeTypes(String feeType, String action, BoSeqCalculator boSeqCalculator) {
		// 构造boProdStatuses
		JSONArray boProdFeeTypeArrJs = new JSONArray();
		JSONObject boProdFeeTypeJs = new JSONObject();
		boProdFeeTypeJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdFeeTypeJs.elementOpt("feeType", feeType);
		boProdFeeTypeJs.elementOpt("state", action);
		boProdFeeTypeJs.elementOpt("statusCd", "S");
		boProdFeeTypeArrJs.add(boProdFeeTypeJs);
		return boProdFeeTypeArrJs;
	}

	/**
	 * 构造boCust的json数组
	 * 
	 * @param newPartyId
	 * @param oldPartyId
	 * @param partyProductRelaRoleCd
	 * @return
	 */
	private JSONArray createBoCustsJson(String newPartyId, String oldPartyId, String partyProductRelaRoleCd,
			BoSeqCalculator boSeqCalculator) {
		// 构造boCusts
		JSONArray boCustArrJs = new JSONArray();

		if (newPartyId != null) {
			JSONObject newBoCustJs = new JSONObject();
			newBoCustJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoCustJs.elementOpt("partyId", newPartyId);
			newBoCustJs.elementOpt("partyProductRelaRoleCd", partyProductRelaRoleCd);
			newBoCustJs.elementOpt("state", "ADD");
			newBoCustJs.elementOpt("statusCd", "S");
			boCustArrJs.add(newBoCustJs);
		}
		if (oldPartyId != null) {
			JSONObject oldBoCustJs = new JSONObject();
			oldBoCustJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			oldBoCustJs.elementOpt("partyId", oldPartyId);
			oldBoCustJs.elementOpt("partyProductRelaRoleCd", partyProductRelaRoleCd);
			oldBoCustJs.elementOpt("state", "DEL");
			oldBoCustJs.elementOpt("statusCd", "S");
			boCustArrJs.add(oldBoCustJs);
		}
		return boCustArrJs;
	}

	/**
	 * 创建产品属性集合ProdItems的json
	 * 
	 * @param prodPropertysNode
	 * @param prodId
	 * @return
	 */
	private JSONArray createProdItemsJson(Node prodPropertysNode, Long prodId, BoSeqCalculator boSeqCalculator) {
		List propertyNodes = prodPropertysNode.selectNodes("./property");

		// 构造boProdItems
		JSONArray boProdItemArrJs = new JSONArray();
		for (Iterator itr = propertyNodes.iterator(); itr.hasNext();) {
			Node propertyNode = (Node) itr.next();
			String itemSpecIdStr = propertyNode.selectSingleNode("./id").getText();
			String nameStr = propertyNode.selectSingleNode("./name").getText();
			String newValueStr = propertyNode.selectSingleNode("./value").getText();
			String actionTypeStr = propertyNode.selectSingleNode("./actionType").getText();
			String propertyType = propertyNode.selectSingleNode("./@propertyType") != null ? propertyNode
					.selectSingleNode("./@propertyType").getText() : null;
			if (propertyType != null && propertyType.equals("co")) {
				// 如果注明是订单属性就不处理
				continue;
			}
			if (actionTypeStr != null && ("0".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
				JSONObject newBoProdItemJs = new JSONObject();
				newBoProdItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				newBoProdItemJs.elementOpt("itemSpecId", itemSpecIdStr);
				newBoProdItemJs.elementOpt("name", nameStr);
				newBoProdItemJs.elementOpt("value", newValueStr);
				newBoProdItemJs.elementOpt("state", "ADD");
				newBoProdItemJs.elementOpt("statusCd", "S");
				boProdItemArrJs.add(newBoProdItemJs);
			}
			if (actionTypeStr != null && ("1".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
				OfferProdItem offerProdItem = intfSMO.findOfferProdItem(prodId, Long.parseLong(itemSpecIdStr));
				if (offerProdItem != null) {
					JSONObject oldBoProdItemJs = new JSONObject();
					oldBoProdItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					oldBoProdItemJs.elementOpt("itemSpecId", itemSpecIdStr);
					oldBoProdItemJs.elementOpt("name", nameStr);
					oldBoProdItemJs.elementOpt("value", offerProdItem.getValue());
					oldBoProdItemJs.elementOpt("state", "DEL");
					oldBoProdItemJs.elementOpt("statusCd", "S");
					boProdItemArrJs.add(oldBoProdItemJs);
				}
			}
		}
		return boProdItemArrJs;
	}

	/**
	 * 创建boProdPasswords的集合
	 * 
	 * @param newPwd
	 * @param oldPwd
	 * @param prodPwTypeCd
	 * @return
	 */
	private JSONArray createBoProdPasswordsJson(String newPwd, String oldPwd, String prodPwTypeCd,
			BoSeqCalculator boSeqCalculator) {
		// 构造boProdPasswords
		JSONArray boProdPasswordArrJs = new JSONArray();
		if (newPwd != null) {
			JSONObject newBoProdPasswordJs = new JSONObject();
			newBoProdPasswordJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProdPasswordJs.elementOpt("prodPwTypeCd", prodPwTypeCd);
			newBoProdPasswordJs.elementOpt("pwd", newPwd);
			newBoProdPasswordJs.elementOpt("state", "ADD");
			newBoProdPasswordJs.elementOpt("statusCd", "S");
			boProdPasswordArrJs.add(newBoProdPasswordJs);
		}
		if (oldPwd != null && !"".equals(oldPwd)) {
			JSONObject oldBoProdPasswordJs = new JSONObject();
			oldBoProdPasswordJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			oldBoProdPasswordJs.elementOpt("prodPwTypeCd", prodPwTypeCd);
			oldBoProdPasswordJs.elementOpt("pwd", oldPwd);
			oldBoProdPasswordJs.elementOpt("state", "DEL");
			oldBoProdPasswordJs.elementOpt("statusCd", "S");
			boProdPasswordArrJs.add(oldBoProdPasswordJs);
		}
		return boProdPasswordArrJs;
	}

	/**
	 * 构建boProd2Tds的json对象
	 * 
	 * @param tdsNode
	 * @param prodId
	 * @return
	 */
	private JSONArray createboProd2TdsJson(Node tdsNode, Long prodId, BoSeqCalculator boSeqCalculator) {
		List tdNodes = tdsNode.selectNodes("./td");
		// 构造boProd2Tds
		JSONArray boProd2TdArrJs = new JSONArray();
		for (Iterator itr = tdNodes.iterator(); itr.hasNext();) {
			Node tdNode = (Node) itr.next();
			Node terminalCodeNode = tdNode.selectSingleNode("./terminalCode");
			Node terminalDevSpecIdNode = tdNode.selectSingleNode("./terminalDevSpecId");
			Node terminalDevIdNode = tdNode.selectSingleNode("./terminalDevId");
			Node devModelIdNode = tdNode.selectSingleNode("./devModelId");
			Node ownerTypeCdNode = tdNode.selectSingleNode("./ownerTypeCd");
			Node maintainTypeCdNode = tdNode.selectSingleNode("./maintainTypeCd");

			String terminalDevSpecIdStr = (null != terminalDevSpecIdNode ? terminalDevSpecIdNode.getText() : "");
			String newTerminalCodeStr = (null != terminalCodeNode ? terminalCodeNode.getText() : "");
			String newTerminalDevIdStr = (null != terminalDevIdNode ? terminalDevIdNode.getText() : "");
			String newDevModelIdStr = (null != devModelIdNode ? devModelIdNode.getText() : "");
			String newOwnerTypeCdStr = (null != ownerTypeCdNode ? ownerTypeCdNode.getText() : "");
			String newMaintainTypeCdStr = (null != maintainTypeCdNode ? maintainTypeCdNode.getText() : "");
			String devNumId = WSUtil.getXmlNodeText(tdNode, "./devNumId");
			if (StringUtils.isBlank(devNumId)) {
				throw new BmoException(-1, "卡信息不全devNumId值为空");
			}
			if (prodId != null) {

				OfferProd2Td offerProd2Td = intfSMO.findOfferProd2Td(prodId, Long.parseLong(terminalDevSpecIdStr));
				if (offerProd2Td != null) {
					String oldTerminalCodeStr = (null != offerProd2Td.getTerminalCode() ? offerProd2Td
							.getTerminalCode().trim() : "");
					String oldTerminalDevIdStr = (null != offerProd2Td.getTerminalDevId() ? offerProd2Td
							.getTerminalDevId().toString() : "");
					String oldDevModelIdStr = (null != offerProd2Td.getDeviceModelId() ? offerProd2Td
							.getDeviceModelId().toString() : "");
					String oldOwnerTypeCdStr = (null != offerProd2Td.getOwnerTypeCd() ? offerProd2Td.getOwnerTypeCd()
							.toString() : "");
					String oldMaintainTypeCdStr = (null != offerProd2Td.getMaintainTypeCd() ? offerProd2Td
							.getMaintainTypeCd().toString() : "");
					String prod2TdIdStr = (null != offerProd2Td.getProd2TdId() ? offerProd2Td.getProd2TdId().toString()
							: "");
					JSONObject oldBoProd2TdJs = new JSONObject();
					oldBoProd2TdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					oldBoProd2TdJs.elementOpt("terminalDevSpecId", terminalDevSpecIdStr);
					oldBoProd2TdJs.elementOpt("maintainTypeCd", oldMaintainTypeCdStr);
					oldBoProd2TdJs.elementOpt("ownerTypeCd", oldOwnerTypeCdStr);
					oldBoProd2TdJs.elementOpt("terminalDevId", oldTerminalDevIdStr);
					oldBoProd2TdJs.elementOpt("terminalCode", oldTerminalCodeStr);
					oldBoProd2TdJs.elementOpt("deviceModelId", oldDevModelIdStr);
					oldBoProd2TdJs.elementOpt("prod2TdId", prod2TdIdStr);
					oldBoProd2TdJs.elementOpt("state", "DEL");
					oldBoProd2TdJs.elementOpt("statusCd", "S");
					boProd2TdArrJs.add(oldBoProd2TdJs);
				}
			}
			JSONObject newBoProd2TdJs = new JSONObject();
			newBoProd2TdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2TdJs.elementOpt("terminalDevSpecId", terminalDevSpecIdStr);
			newBoProd2TdJs.elementOpt("maintainTypeCd", newMaintainTypeCdStr);
			newBoProd2TdJs.elementOpt("ownerTypeCd", newOwnerTypeCdStr);
			newBoProd2TdJs.elementOpt("terminalDevId", newTerminalDevIdStr);
			newBoProd2TdJs.elementOpt("terminalCode", newTerminalCodeStr);
			newBoProd2TdJs.elementOpt("deviceModelId", newDevModelIdStr);
			newBoProd2TdJs.elementOpt("state", "ADD");
			newBoProd2TdJs.elementOpt("statusCd", "S");
			boProd2TdArrJs.add(newBoProd2TdJs);
		}
		return boProd2TdArrJs;
	}

	private JSONArray createboProd2CouponsJson(Node couponsNode, Long prodId, Long offerId, Long partyId,
			BoSeqCalculator boSeqCalculator, String offerSpecId) {
		// 构造boProd2Coupons
		JSONArray boProd2CouponArrJs = new JSONArray();
		if (couponsNode == null) {
			return boProd2CouponArrJs;
		}
		List couponNodes = couponsNode.selectNodes("./coupon");
		for (Iterator itr = couponNodes.iterator(); itr.hasNext();) {
			Node couponNode = (Node) itr.next();
			Node bcdCodeNode = couponNode.selectSingleNode("./couponcode");
			Node countNode = couponNode.selectSingleNode("./count");
			String bcdCodeStr = (null != bcdCodeNode ? bcdCodeNode.getText() : "");
			String materialIdStr = "";
			String storeIdStr = "";
			String storeNameStr = "";
			String chargeItemCdStr = "90019";// charge_item购机费费用编码
			String couponChargeStr = "";
			String countStr = (null != countNode ? countNode.getText() : "");
			// 查找供货商信息
			// 此处需恢复
			String agentId = "";
			String agentName = "";
			try {
				String materialInfo = soStoreSMO.getMaterialByCode(bcdCodeStr);
				JSONObject couponJson = null;
				logger.debug("物品校验xml格式:{}", materialInfo);
				if (materialInfo == null || materialInfo.equals("-1")) {
					logger.debug("查找物品信息失败：bcdcode={}", bcdCodeStr);
					throw new BmoException(-1, "查找物品信息失败!bcdcode=" + bcdCodeStr);
				}
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON json = xmlSerializer.read(materialInfo);
				couponJson = JSONObject.fromObject(json);
				JSONObject goodsDetail = couponJson.getJSONObject("GoodsDetail");
				agentId = goodsDetail.getString("productorId");
				agentName = goodsDetail.getString("productorName");
				storeIdStr = goodsDetail.getString("storeId");
				materialIdStr = goodsDetail.getString("materialId");
				storeNameStr = goodsDetail.getString("storeName");
				couponChargeStr = goodsDetail.getString("referPrice");
			} catch (Exception e) {
				logger.debug("查找物品信息失败：bcdcode={},{}", bcdCodeStr, e);
				throw new BmoException(-1, "查找物品信息失败!bcdcode=" + bcdCodeStr);
			}
			int chargeItemCd = 0;
			int charge = 0;
			if (StringUtils.isNotBlank(offerSpecId) || offerSpecId != null) {
				//根据 offerSpecId 和 couponId获得  cfg_rule_id
				String cfgRuleId = intfSMO.getCtfRuleIdByOCId(Long.valueOf(offerSpecId), Long.valueOf(materialIdStr));
				//从营业表里获取物品对应的费用项和费用
				JSONArray jsArray = soStoreSMO.queryCouponGrpByRuleAndCoupon(Long.valueOf(cfgRuleId), Long
						.valueOf(materialIdStr));

				for (int i = 0; i < jsArray.size(); i++) {
					JSONObject js = jsArray.getJSONObject(i);
					JSONObject couponJs = js.getJSONObject("coupon");
					chargeItemCd = couponJs.getInt("CHARGE_ITEM_CD");
					//js.getString("CHARGE_ITEM_CD");
					charge = couponJs.getInt("PRICE");
				}
				chargeItemCdStr = chargeItemCd + "";
			}

			JSONObject newBoProd2couponJs = new JSONObject();
			newBoProd2couponJs.elementOpt("id", "-1");
			newBoProd2couponJs.elementOpt("offerId", "-1");
			newBoProd2couponJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2couponJs.elementOpt("isTransfer", "Y");
			newBoProd2couponJs.elementOpt("inOutReasonId", "1");
			newBoProd2couponJs.elementOpt("saleId", "1");// 前台
			newBoProd2couponJs.elementOpt("couponId", materialIdStr);
			newBoProd2couponJs.elementOpt("couponinfoStatusCd", "A");// 可售
			newBoProd2couponJs.elementOpt("couponUsageTypeCd", "3");// 销售
			newBoProd2couponJs.elementOpt("inOutTypeId", "1");
			newBoProd2couponJs.elementOpt("chargeItemCd", chargeItemCdStr);
			newBoProd2couponJs.elementOpt("couponNum", countStr);
			newBoProd2couponJs.elementOpt("storeId", storeIdStr);
			newBoProd2couponJs.elementOpt("storeName", storeNameStr);
			newBoProd2couponJs.elementOpt("agentId", agentId);
			newBoProd2couponJs.elementOpt("agentName", agentName);
			if (StringUtils.isNotBlank(couponChargeStr) && offerSpecId == null) {
				long apCharge = (long) (Double.valueOf(couponChargeStr) / 100);
				logger.debug("价格转换，前台使用的是元,价格为{}元", apCharge);
				newBoProd2couponJs.elementOpt("apCharge", apCharge);
			} else if (offerSpecId != null && !"".equals(offerSpecId)) {
				newBoProd2couponJs.elementOpt("apCharge", charge);
			}

			newBoProd2couponJs.elementOpt("couponInstanceNumber", bcdCodeStr);
			newBoProd2couponJs.elementOpt("ruleId", null);
			newBoProd2couponJs.elementOpt("description", null);
			newBoProd2couponJs.elementOpt("partyId", partyId);
			newBoProd2couponJs.elementOpt("prodId", prodId);
			newBoProd2couponJs.elementOpt("offerId", offerId);
			newBoProd2couponJs.elementOpt("state", CommonDomain.ADD);
			boProd2CouponArrJs.add(newBoProd2couponJs);

		}
		return boProd2CouponArrJs;
	}

	/**
	 * 创建boProdAns
	 * 
	 * @return
	 */
	private JSONArray createBoProdAns(Node accessNumberNode, Node anIdNode, String prodSpecId, String areaId,
			Node partyIdNode, BoSeqCalculator boSeqCalculator) {
		// 构造boProdAns
		JSONArray boProdAnArrJs = new JSONArray();
		String accessNumberStr = accessNumberNode.getText();
		String anIdStr = null;
		ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType(Long.parseLong(prodSpecId));
		boolean needVali = false;// 是否需要资源校验
		// 校验分两步，一是根据产品规格对特殊的业务要求进行校验，并判断是否需要资源校验
		if (Integer.valueOf(prodSpecId).intValue() == CommonDomain.PROD_SPEC_GR118114) {
			// 个人通信助理的接入号码需要特殊校验
			String partyId = partyIdNode.getText();
			needVali = true;
		} else if (Integer.valueOf(prodSpecId).intValue() == CommonDomain.PROD_SPEC_YXZC_QQ) {
			needVali = true;
		} else if (accessNumberStr == null || accessNumberStr.trim().equals("")) {
			// 如果没有传入接入号码需要自动生成
			// AccessNumberToResource[] accessNumberToResources = new
			// AccessNumberToResource[] { new AccessNumberToResource() };
			// accessNumberToResources[0].setAreaId(Integer.valueOf(areaId));
			// accessNumberToResources[0].setAnTypeCd(prodSpec2AccessNumType.getAnTypeCd());
			// List list =
			// numberElementManager.assignAn(accessNumberToResources);
			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<root>");
			xml.append("<AccessNumberInfo>");
			xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
			xml.append("<areaId>").append(areaId).append("</areaId>");
			xml.append("</AccessNumberInfo>");
			xml.append("</root>");
			try {
				List list = rscServiceSMO.assignAn(xml.toString());
				if (list != null && list.size() > 0) {
					Map anMap = (Map) list.get(0);
					accessNumberStr = anMap.get("name").toString();
					anIdStr = anMap.get("anId").toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (anIdNode != null) {
				anIdStr = anIdNode.getText();
				if (anIdStr == null && "".equals(anIdStr.trim())) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30008, "主接入号的anId获取失败!");
				}
			} else {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30008, "主接入号的anId获取失败!");
			}
		}

		// 此处理主接入号的资源校验
		if (needVali) {
			// AccessNumberToResource[] accessNumberToResources = new
			// AccessNumberToResource[] { new AccessNumberToResource() };
			// accessNumberToResources[0].setAreaId(Integer.valueOf(areaId));
			// accessNumberToResources[0].setCanChangePwd(prodSpec2AccessNumType.getNeedPw());
			// accessNumberToResources[0].setName(accessNumberStr);
			// accessNumberToResources[0].setAnTypeCd(prodSpec2AccessNumType.getAnTypeCd());
			// accessNumberToResources[0].setRscStatusCd(CommonDomain.RSC_STATUS_CD_TEMP_PRE_SAVE);
			try {
				StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				xml.append("<root>");
				xml.append("<AccessNumberInfo>");
				xml.append("<canChangePwd>").append(prodSpec2AccessNumType.getNeedPw()).append("</canChangePwd>");
				xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
				xml.append("<rscStatusCd>").append(CommonDomain.RSC_STATUS_CD_TEMP_PRE_SAVE).append("</rscStatusCd>");
				xml.append("<areaId>").append(areaId).append("</areaId>");
				xml.append("<name>").append(accessNumberStr).append("</name>");
				xml.append("<password>1</password>");
				xml.append("</AccessNumberInfo>");
				xml.append("</root>");

				List list = rscServiceSMO.addAn(xml.toString());
				if (list == null) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "号码资源校验失败!");
				}

				Map m = (Map) list.get(0);
				String r = null;
				String c = null;
				String id = null;
				if (m.get("result") == null) {
					if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
						throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "号码资源校验失败!");
					} else {
						r = "1";
						c = m.get("name").toString();
						id = m.get("anId").toString();
					}
				} else if ("-1".equals(m.get("result").toString())) {
					if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
						throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "号码资源校验失败!");
					} else {
						r = m.get("result").toString();
						c = m.get("cause").toString();
						id = m.get("anId").toString();
					}
				} else {
					r = m.get("result").toString();
					c = m.get("cause").toString();
					id = m.get("anId").toString();
				}
				anIdStr = id;
			} catch (Exception e) {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "号码资源校验失败!");
			}
		}

		JSONObject boProdAnJs = new JSONObject();
		boProdAnJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdAnJs.elementOpt("anId", anIdStr);
		boProdAnJs.elementOpt("anTypeCd", prodSpec2AccessNumType.getAnTypeCd());
		boProdAnJs.elementOpt("accessNumber", accessNumberStr);
		boProdAnJs.elementOpt("state", "ADD");
		boProdAnJs.elementOpt("statusCd", "S");
		boProdAnArrJs.add(boProdAnJs);

		return boProdAnArrJs;
	}

	/**
	 * 创建boProd2Ans
	 * 
	 * @return
	 */
	private JSONArray createBoProd2Ans(String prodSpecId, String areaId, Node prod2accNbrNode, Node anId2Node,
			Node passwordNode, BoSeqCalculator boSeqCalculator) {
		// 构造boProd2Ans
		JSONArray boProd2AnArrJs = new JSONArray();
		String prod2accNbr = prod2accNbrNode.getText();
		String password = "";
		if (passwordNode != null) {
			password = passwordNode.getText();
		}
		String anId = null;
		Integer anTypeCd = null;
		Integer reasonCd = null;
		if (prod2accNbr != null && !"".equals(prod2accNbr.trim())) {
			ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType2(Long
					.parseLong(prodSpecId));
			if (anId2Node != null && anId2Node.getText() != null && !"".equals(anId2Node.getText().trim())) {
				anId = anId2Node.getText().trim();
			} else {
				try {
					StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					xml.append("<root>");
					xml.append("<AccessNumberInfo>");
					xml.append("<canChangePwd>Y</canChangePwd>");
					xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
					xml.append("<rscStatusCd>0</rscStatusCd>");
					xml.append("<areaId>").append(areaId).append("</areaId>");
					xml.append("<name>").append(prod2accNbr.trim()).append("</name>");
					xml.append("<password>").append(password).append("</password>");
					xml.append("</AccessNumberInfo>");
					xml.append("</root>");

					List rusultList = rscServiceSMO.addAn(xml.toString());
					Map m = (Map) rusultList.get(0);
					String r = null;
					String c = null;
					String id = null;
					logger.debug("资源返回结果：result={}，anId={},name={},cause={}", new Object[] { m.get("result"),
							m.get("anId"), m.get("name"), m.get("cause") });
					if (m.get("result") == null) {
						if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
							throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "非主接入号码校验失败!");
						} else {
							r = "1";
							c = m.get("name").toString();
							id = m.get("anId").toString();
						}
					} else if ("-1".equals(m.get("result").toString())) {
						if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
							c = m.get("cause") != null ? m.get("cause").toString() : "";
							throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "非主接入号码校验失败!" + c);
						} else {
							r = m.get("result").toString();
							c = m.get("cause").toString();
							id = m.get("anId").toString();
						}
					} else {
						r = m.get("result").toString();
						c = m.get("cause").toString();
						id = m.get("anId").toString();
					}
					anId = id;

				} catch (Exception e) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "非主接入号码校验失败!", e);
				}
			}

			JSONObject boProd2AnJs = new JSONObject();
			boProd2AnJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boProd2AnJs.elementOpt("anId", anId);
			boProd2AnJs.elementOpt("anTypeCd", anTypeCd);
			boProd2AnJs.elementOpt("accessNumber", prod2accNbr);
			boProd2AnJs.elementOpt("reasonCd", reasonCd);
			boProd2AnJs.elementOpt("state", "ADD");
			boProd2AnJs.elementOpt("statusCd", "S");
			boProd2AnArrJs.add(boProd2AnJs);
		}

		return boProd2AnArrJs;
	}

	/**
	 * 生成帐户关联关系动作信息
	 * 
	 * @param acctCdNode
	 * @param bindPayNumberNode
	 * @param bindNumberProdSpec
	 * @param boCreateParam
	 * @return
	 */
	private JSONArray processAccountRela(Node acctCdNode, Node bindPayNumberNode, Node bindNumberProdSpec,
			BoSeqCalculator boSeqCalculator, BoCreateParam boCreateParam) {
		String acctId = null;
		Account account = null;
		if (acctCdNode != null) {
			String acctCdStr = acctCdNode.getText();
			if (boCreateParam.isNeedCreateAcct()) {
				// 帐户是新增的,不用去数据库中查找,直接使用预生成的帐户Id
				acctId = boCreateParam.getAcctId().toString();
			} else {
				account = intfSMO.findAcctByAcctCd(acctCdStr);
				if (account != null) {
					acctId = account.getAcctId().toString();
				} else {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "不存在帐户编码为" + acctCdStr
							+ "的帐户，请检查！");
				}
			}
		} else if (bindPayNumberNode != null && bindNumberProdSpec != null) {
			String bindPayNumberNodeStr = bindPayNumberNode.getText();
			if (bindPayNumberNodeStr != null && !bindPayNumberNodeStr.trim().equals("")) {
				Integer bindNumberProdSpecStr = Integer.valueOf(bindNumberProdSpec.getText());
				account = intfSMO.findAcctByAccNbr(bindPayNumberNodeStr, bindNumberProdSpecStr);
				if (account != null) {
					acctId = account.getAcctId().toString();
				} else {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30021, "产品规格[" + bindNumberProdSpecStr
							+ "]号码[" + bindPayNumberNodeStr + "]找不到帐户，请确认输入的付费号码和产品规格！");
				}
			}
		}
		JSONArray boAccountRelaArrJs = createBoAccountRelas(acctId, boSeqCalculator);
		return boAccountRelaArrJs;
	}

	/**
	 * 处理产品关联关系
	 * 
	 * @param relaAccessNumber
	 * @param relaProdSpecId
	 * @param reasonCd
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processProdRela(String relaAccessNumber, Integer relaProdSpecId, Integer reasonCd, String state,
			BoSeqCalculator boSeqCalculator) {
		OfferProd offerProd = intfSMO.getProdByAccessNumber(relaAccessNumber);
		if (offerProd != null) {
			return processProdRela(offerProd.getProdId(), reasonCd, state, boSeqCalculator);
		}
		return null;
	}

	/**
	 * 处理产品关联关系
	 * 
	 * @param relaProdId
	 * @param reasonCd
	 * @param state
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processProdRela(Long relaProdId, Integer reasonCd, String state, BoSeqCalculator boSeqCalculator) {
		JSONObject boProdRelaJs = new JSONObject();
		boProdRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdRelaJs.elementOpt("reasonCd", reasonCd);
		boProdRelaJs.elementOpt("relatedProdId", relaProdId);
		boProdRelaJs.elementOpt("state", state);
		boProdRelaJs.elementOpt("statusCd", "S");
		return boProdRelaJs;
	}

	/**
	 * 构建boProdCompOrders
	 * 
	 * @param compProdId
	 * @param prodCompId
	 * @param prodCompRelaRoleCd
	 * @return
	 */
	private JSONArray createBoProdCompOrders(String compProdId, String prodCompId, String prodCompRelaRoleCd) {
		JSONArray boProdCompOrderArrJs = new JSONArray();
		JSONObject boProdCompOrderJs = new JSONObject();
		boProdCompOrderJs.elementOpt("compProdId", compProdId);
		boProdCompOrderJs.elementOpt("prodCompId", prodCompId);
		boProdCompOrderJs.elementOpt("prodCompRelaRoleCd", prodCompRelaRoleCd);
		boProdCompOrderArrJs.add(boProdCompOrderJs);
		return boProdCompOrderArrJs;
	}

	// 构建加入组合属性的业务动作数据
	private JSONArray createBoProdCompItems(Node prodCompPropertysNode, Long prodId, Long compProdId,
			String prodCompId, BoSeqCalculator boSeqCalculator) {
		logger.debug("--call createBoProdCompItems");
		JSONArray boProdCompItemArrJs = new JSONArray();
		if (prodCompPropertysNode != null) {
			// 加入组合属性
			List propertyNodes = prodCompPropertysNode.selectNodes("./property");
			logger.debug("propertyNodes={}", propertyNodes);
			if (propertyNodes != null && !propertyNodes.isEmpty()) {
				for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
					Node propertie = (Node) itr1.next();
					String itemSpecId = propertie.selectSingleNode("./id").getText();
					String newValue = propertie.selectSingleNode("./value").getText();
					String actionTypeStr = propertie.selectSingleNode("./actionType").getText();

					if (actionTypeStr != null && ("0".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
						JSONObject newBoProdCompItemJs = new JSONObject();
						newBoProdCompItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						newBoProdCompItemJs.elementOpt("prodCompId", prodCompId);
						newBoProdCompItemJs.elementOpt("itemSpecId", itemSpecId);
						newBoProdCompItemJs.elementOpt("value", newValue);
						newBoProdCompItemJs.elementOpt("state", "ADD");
						newBoProdCompItemJs.elementOpt("statusCd", "S");
						boProdCompItemArrJs.add(newBoProdCompItemJs);
					}
					if (actionTypeStr != null && ("1".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
						OfferProdCompItem offerProdCompItem = null;
						if (offerProdCompItem != null) {
							JSONObject oldBoProdCompItemJs = new JSONObject();
							oldBoProdCompItemJs
									.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							oldBoProdCompItemJs.elementOpt("prodCompId", prodCompId);
							oldBoProdCompItemJs.elementOpt("itemSpecId", itemSpecId);
							oldBoProdCompItemJs.elementOpt("value", offerProdCompItem.getValue());
							oldBoProdCompItemJs.elementOpt("state", "DEL");
							oldBoProdCompItemJs.elementOpt("statusCd", "S");
							boProdCompItemArrJs.add(oldBoProdCompItemJs);
						}
					}
				}
			}
		}
		return boProdCompItemArrJs;
	}

	// 构建成员变更业务动作数据
	private JSONArray createBusiOrderCompChange(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		BoCreateParam compBoCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		String compProdSPecId = compBoCreateParam.getProdSpecId();
		String compProdId = compBoCreateParam.getProdId().toString();

		JSONArray busiOrderArrJs = new JSONArray();

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", compProdSPecId);
		busiObjJs.elementOpt("instId", compProdId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "1000");
		boActionTypeJs.elementOpt("name", "成员变更");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		JSONArray busiOrderAttrsJs = this.buildBusiOrderAttrObj(compBoCreateParam);// 订单属性
		dataJs.elementOpt("busiOrderAttrs", busiOrderAttrsJs);
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);

		return busiOrderArrJs;
	}

	// 构建成员新增业务动作数据
	private JSONArray createBusiOrderCompAdd(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		BoCreateParam compBoCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		String compProdSPecId = compBoCreateParam.getProdSpecId();
		String compProdId = compBoCreateParam.getProdId().toString();

		JSONArray busiOrderArrJs = new JSONArray();

		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("actionFlag", "1");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");
//只有结构数据不对
		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", compProdSPecId);
		busiObjJs.elementOpt("instId", compProdId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "998");
		boActionTypeJs.elementOpt("name", "加入套餐");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		JSONObject boProdCompOrdersJs = new JSONObject();
//		JSONArray busiOrderAttrsJs = this.buildBusiOrderAttrObj(compBoCreateParam);// 订单属性
		JSONArray boProdCompOrdersJsList = new JSONArray();
		boProdCompOrdersJs.elementOpt("boId",1);
		boProdCompOrdersJs.elementOpt("compProdId","103030229965");
		boProdCompOrdersJs.elementOpt("prodCompId","103030161069");
		boProdCompOrdersJs.elementOpt("prodCompRelaRoleCd","2016");
		JSONArray boProdCompRelasJsList = new JSONArray();
		dataJs.elementOpt("boProdCompOrders", boProdCompOrdersJsList);
		dataJs.elementOpt("boProdCompRelas", boProdCompRelasJsList);
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);

		busiOrderJs.elementOpt("hasPrepared", "1");
		return busiOrderArrJs;
	}
	// 构建销售品实例成员加入或退出销售品业务动作数据
	private JSONArray createBusiOrderOfferMemberChange(BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator, JSONArray mainOrderBusiOrderArr) {
		JSONArray busiOrderArrJs = new JSONArray();
		BoCreateParam compBoCreateParam = boCreateParamGrp.getCompBoCreateParam();
		BoCreateParam selfBoCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		Long offerSPecId = compBoCreateParam.getPsOfferSpecId();
		String offerId = compBoCreateParam.getProdId().toString();
		JSONObject dataJs = null;
		// 查找出销售品角色ID
		JSONArray ooRoleArrJs = null;
		OfferRoles offerRoles = null;
		JSONObject ooRoleJs = new JSONObject();
		ooRoleJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooRoleJs.elementOpt("offerRoleId", offerRoles.getOfferRoleId());
		ooRoleJs.elementOpt("objType", CommonDomain.OBJ_TYPE_PROD_SPEC);
		ooRoleJs.elementOpt("objId", selfBoCreateParam.getProdSpecId());
		ooRoleJs.elementOpt("objInstId", selfBoCreateParam.getProdId());
		ooRoleJs.elementOpt("prodId", selfBoCreateParam.getProdId());
		ooRoleJs.elementOpt("state", selfBoCreateParam.getRoleState());
		ooRoleJs.elementOpt("statusCd", "S");
		ooRoleJs.elementOpt("startDt", selfBoCreateParam.getCurDate());
		ooRoleJs.elementOpt("endDt", "");
		Short minQty = offerRoles.getMinQty();
		// 如果外层套餐是新装 则基础成员的加入要挂到订购的业务动作的OOROLES中即可,套餐新装时非基础成员需要重新创建业务动作
		if (minQty <= 0 && compBoCreateParam.isNew()) {
			JSONObject busiOrderJs = new JSONObject();
			busiOrderJs.elementOpt("areaId", areaId);
			busiOrderJs.elementOpt("linkFlag", "Y");

			Integer seq = boSeqCalculator.getNextSeqInteger();
			JSONObject busiOrderInfoJs = new JSONObject();
			busiOrderInfoJs.elementOpt("seq", seq);
			busiOrderInfoJs.elementOpt("statusCd", "S");
			busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
			logger.debug("---------busiOrderInfo 完成！");

			JSONObject busiObjJs = new JSONObject();
			busiObjJs.elementOpt("objId", offerSPecId);
			busiObjJs.elementOpt("instId", offerId);
			busiObjJs.elementOpt("offerTypeCd", "");
			busiOrderJs.elementOpt("busiObj", busiObjJs);
			logger.debug("---------busiObj 完成！");

			JSONObject boActionTypeJs = new JSONObject();
			boActionTypeJs.elementOpt("actionClassCd", "3");
			boActionTypeJs.elementOpt("boActionTypeCd", "S3");
			boActionTypeJs.elementOpt("name", "成员变更");
			busiOrderJs.elementOpt("boActionType", boActionTypeJs);
			logger.debug("---------boActionType 完成！");
			dataJs = new JSONObject();
			// 处理销售品角色变更
			ooRoleArrJs = new JSONArray();
			ooRoleArrJs.add(ooRoleJs);
			dataJs.elementOpt("ooRoles", ooRoleArrJs);
			// 添加订单属性
			JSONArray busiOrderAttrsJs = buildBusiOrderAttrObj(selfBoCreateParam);
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrsJs);
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		// 取出外层套餐订购业务动作
		if (mainOrderBusiOrderArr != null) {
			for (int i = 0; i < mainOrderBusiOrderArr.size(); i++) {
				JSONObject jsBusiOrder = mainOrderBusiOrderArr.getJSONObject(i);
				JSONObject jsBoActionType = jsBusiOrder.getJSONObject("boActionType");
				JSONObject jsBusiObj = jsBusiOrder.getJSONObject("busiObj");
				if (jsBoActionType != null
						&& jsBusiObj != null
						&& jsBoActionType.containsKey("boActionTypeCd")
						&& (jsBoActionType.getString("boActionTypeCd").equals("S1") || jsBoActionType.getString(
								"boActionTypeCd").equals("S3")) && jsBusiObj.containsKey("instId")
						&& jsBusiObj.getString("instId").equals(compBoCreateParam.getProdId().toString())) {
					if (jsBusiOrder.containsKey("data")) {
						dataJs = jsBusiOrder.getJSONObject("data");
						if (dataJs.containsKey("ooRoles")) {
							ooRoleArrJs = dataJs.getJSONArray("ooRoles");
							ooRoleArrJs.add(ooRoleJs);
							dataJs.elementOpt("ooRoles", ooRoleArrJs);
						} else {
							ooRoleArrJs = new JSONArray();
							ooRoleArrJs.add(ooRoleJs);
							dataJs.elementOpt("ooRoles", ooRoleArrJs);
						}
					} else {
						dataJs = new JSONObject();
						ooRoleArrJs = new JSONArray();
						ooRoleArrJs.add(ooRoleJs);
						dataJs.elementOpt("ooRoles", ooRoleArrJs);
					}
					jsBusiOrder.elementOpt("data", dataJs);
					break;
				}
			}
		}
		return busiOrderArrJs;
	}

	// 构建加入或退出业务动作数据
	private JSONArray createBusiOrderMemberChange(Node prodCompPropertysNode, BoCreateParamGrp boCreateParamGrp,
			String areaId, BoSeqCalculator boSeqCalculator) {
		BoCreateParam compBoCreateParam = boCreateParamGrp.getCompBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		String compProdId = compBoCreateParam.getProdId().toString();
		BoCreateParam boCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String memberProdSPecId = boCreateParam.getProdSpecId();
		String boActionType = boCreateParam.getRoleboActionType();
		String prodCompRelaRoleCd = boCreateParam.getRelaRoleCd();
		Long prodId = boCreateParam.getProdId();
		String state = boCreateParam.getRoleState();
		String memberProdId = null;
		if (boCreateParam.getProdId() != null) {
			memberProdId = boCreateParam.getProdId().toString();
		}
		String prodCompId = null;
		// 如果是加入动作，先判断是否组合关系已经存在，如果存在就取老的compProdId
		OfferProdComp offerProdComp = null;
		if (state.equals("ADD")) {
			if (offerProdComp != null) {
				prodCompId = offerProdComp.getProdCompId().toString();
			} else {
				prodCompId = boSeqCalculator.getNextProdCompIdString();
			}
		} else {
			// 如果是退出，显然之前应该已经存在组合关系了，所以需要取旧的产品组成ID
			if (offerProdComp != null) {
				prodCompId = offerProdComp.getProdCompId().toString();
			}
		}
		JSONArray busiOrderArrJs = new JSONArray();
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo 完成！");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", memberProdSPecId);
		busiObjJs.elementOpt("instId", memberProdId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj 完成！");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", boActionType);
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType 完成！");

		JSONObject dataJs = new JSONObject();
		// 处理订单属性
		JSONArray busiOrderAttrsJs = this.buildBusiOrderAttrObj(boCreateParam);
		dataJs.elementOpt("busiOrderAttrs", busiOrderAttrsJs);
		Node compProdNode = null;
		if (prodCompPropertysNode != null) {
			// 组合产品成员变更时只支持对当前变更的组合产品相关的组合产品成员属性变更，所以只支持一个compProd节点
			compProdNode = prodCompPropertysNode.selectSingleNode("./compProd");
		}
		// 处理产品加入组合的过程信息
		if (boCreateParam.getNeedDealCompOrder()) {
			// 如果需要处理组合产品成员动作就处理
			processBoProdCompOrder(dataJs, compProdId, prodCompId, prodCompRelaRoleCd, compProdNode, prodId,
					boSeqCalculator, state);
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);
		return busiOrderArrJs;
	}

	/**
	 * 处理成员与组合产品之间关系的公共方法
	 * 
	 * @param dataJs
	 * @param compProdId
	 * @param prodCompId
	 * @param prodCompRelaRoleCd
	 * @param compProdNode
	 * @param prodId
	 * @param boSeqCalculator
	 * @param state
	 */
	private void processBoProdCompOrder(JSONObject dataJs, String compProdId, String prodCompId,
			String prodCompRelaRoleCd, Node compProdNode, Long prodId, BoSeqCalculator boSeqCalculator, String state) {
		JSONArray boProdCompOrdersJs = createBoProdCompOrders(compProdId, prodCompId, prodCompRelaRoleCd);
		JSONArray boProdCompItemArrJs = null;
		if (compProdNode != null) {
			boProdCompItemArrJs = createBoProdCompItems(compProdNode, prodId, Long.valueOf(compProdId), prodCompId,
					boSeqCalculator);
		}
		JSONArray boProdCompRelasJs = null;
		if (Long.parseLong(prodCompId) < 0) {
			boProdCompRelasJs = createBoProdCompRelas(prodCompId, CommonDomain.ADD, boSeqCalculator);
		} else {
			if (state.equals(CommonDomain.DEL)) {
				boProdCompRelasJs = createBoProdCompRelas(prodCompId, state, boSeqCalculator);
			} else if ((boProdCompItemArrJs != null && boProdCompItemArrJs.size() > 0)) {
				// 如果有组合属性变动，而组合关系之前又存在，送KIP
				boProdCompRelasJs = createBoProdCompRelas(prodCompId, CommonDomain.KIP, boSeqCalculator);
			}
		}
		// 如果组合属性和组合关系都没有变动，就没有组合产品成员动作
		if ((boProdCompRelasJs != null && !boProdCompRelasJs.isEmpty())
				|| (boProdCompItemArrJs != null && !boProdCompItemArrJs.isEmpty())) {
			dataJs.elementOpt("boProdCompOrders", boProdCompOrdersJs);
			if (boProdCompRelasJs != null && !boProdCompRelasJs.isEmpty()) {
				dataJs.elementOpt("boProdCompRelas", boProdCompRelasJs);
			}
			if (boProdCompItemArrJs != null && !boProdCompItemArrJs.isEmpty()) {
				dataJs.elementOpt("boProdCompItems", boProdCompItemArrJs);
			}
		}
	}

	/**
	 * 构建boProdCompRelas
	 * 
	 * @param prodCompId
	 * @param state
	 * @return
	 */
	private JSONArray createBoProdCompRelas(String prodCompId, String state, BoSeqCalculator boSeqCalculator) {
		JSONArray boProdCompRelaArrJs = new JSONArray();
		JSONObject boProdCompRelaJs = new JSONObject();
		boProdCompRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdCompRelaJs.elementOpt("prodCompId", prodCompId);
		boProdCompRelaJs.elementOpt("state", state);
		boProdCompRelaJs.elementOpt("statusCd", "S");
		boProdCompRelaArrJs.add(boProdCompRelaJs);
		return boProdCompRelaArrJs;
	}

	/**
	 * 产品状态修改
	 * 
	 * @param prodStatusCd
	 * @param state
	 * @param curDate
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject createProdStatusChange(Integer prodStatusCd, String state, String curDate,
			BoSeqCalculator boSeqCalculator) {
		// 成员产品状态变更BO_PROD_STATUS
		JSONObject boProdStatusJs = new JSONObject();
		boProdStatusJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdStatusJs.elementOpt("prodStatusCd", prodStatusCd);
		boProdStatusJs.elementOpt("appEndDt", "");
		boProdStatusJs.elementOpt("appStartDt", "");
		boProdStatusJs.elementOpt("state", state);
		boProdStatusJs.elementOpt("statusCd", "S");
		return boProdStatusJs;
	}

	/**
	 * 生成帐户关联关系动作信息
	 * 
	 * @param acctId
	 * @return
	 */
	private JSONArray createBoAccountRelas(String acctId, BoSeqCalculator boSeqCalculator) {
		// 构造boAccountRelas
		JSONArray boAccountRelaArrJs = new JSONArray();

		JSONObject boAccountRelaJs = new JSONObject();
		boAccountRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boAccountRelaJs.elementOpt("acctId", acctId);
		boAccountRelaJs.elementOpt("chargeItemCd", "0");
		boAccountRelaJs.elementOpt("percent", "100");
		boAccountRelaJs.elementOpt("priority", "1");
		boAccountRelaJs.elementOpt("state", "ADD");
		boAccountRelaJs.elementOpt("statusCd", "S");
		boAccountRelaArrJs.add(boAccountRelaJs);

		return boAccountRelaArrJs;
	}

	/**
	 * 格式化订单中的时间
	 * 
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	private void formatOrderDate(Node order) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		// 修改开始日期格式
		List<Element> startDateElementList = order.selectNodes("//startDt");
		for (Element date : startDateElementList) {
			if (StringUtils.isNotBlank(date.getText())) {
				try {
					date.setText(format2.format(format1.parse(date.getText().trim())));
				} catch (ParseException e) {
					logger.debug("日期格式错误dateString={}", date.getText());
					throw new BmoException(-1, "日期格式错误dateString={}");
				}
			}
		}

		// 修改结束日期格式
		List<Element> endDateElementList = order.selectNodes("//endDt");
		for (Element date : endDateElementList) {
			if (StringUtils.isNotBlank(date.getText())) {
				try {
					date.setText(format2.format(format1.parse(date.getText().trim())));
				} catch (ParseException e) {
					logger.debug("日期格式错误dateString={}", date.getText());
					throw new BmoException(-1, "日期格式错误dateString={}");
				}
			}
		}

		// 修改安装日期格式
		List<Element> installDateElementList = order.selectNodes("//installDate");
		for (Element date : installDateElementList) {
			if (StringUtils.isNotBlank(date.getText())) {
				try {
					date.setText(format2.format(format1.parse(date.getText().trim())));
				} catch (ParseException e) {
					logger.debug("日期格式错误dateString={}", date.getText());
					throw new BmoException(-1, "日期格式错误dateString={}");
				}
			}
		}
	}

	/** ooTimes 
	 * @param time_unit */

	private JSONArray CreateOoTimes(BoSeqCalculator boSeqCalculator, String startDt, String endDt, String startFashion,
			String endFashion, String actionType, String time_unit) {
		// 全空为此月生效|
		// 1默认生效|startDt不为空为指定时间生效
		String startTime = "";
		String startTimeUnitCd = "";
		String isDefaultStart = "";

		String effTime = "";
		String effTimeUnitCd = "";
		String endTime = "";
		String endTimeUnitCd = "";
		String isDefaultEnd = "";
		if (StringUtils.isBlank(startDt) && !"1".equals(actionType)) {
			// 空 次月生效|1走营业默认生效 方式|0立即生效
			if (StringUtils.isBlank(startFashion)) {
				startTime = "1";
				startTimeUnitCd = "7";
			} else if ("1".equals(startFashion)) {
				isDefaultStart = "Y";
			}
		}
		if(!StringUtil.isEmpty(time_unit))
			effTime = time_unit;
		
		if (StringUtils.isBlank(endDt)) {
			if ("1".equals(actionType)) {
				// 空次月1日失效|0立即失效|1走营业默认失效方式|大于1 N账期后失效
				if (StringUtils.isBlank(endFashion)) {
					endTime = "1";
					endTimeUnitCd = "7";
				} else if ("1".equals(endFashion)) {
					isDefaultEnd = "Y";
				} else if (StringUtils.isNotBlank(endFashion) && Integer.valueOf(endFashion) > 1) {
					effTime = endFashion;
					effTimeUnitCd = "7";
				}
			} else {
				isDefaultEnd = "Y";
			}
		}
		if ("0".equals(actionType) && "0".equals(endFashion)) {
			throw new BmoException(-1, "生效方式错误，开通服务不可设置立即失效");
		}

		// 构造ooTimes
		JSONArray ooTimes = new JSONArray();
		JSONObject ooTime = new JSONObject();
		ooTime.elementOpt("state", "ADD");
		ooTime.elementOpt("statusCd", "S");
		ooTime.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooTime.elementOpt("startTime", startTime);
		ooTime.elementOpt("startDt", startDt);
		ooTime.elementOpt("startTimeUnitCd", startTimeUnitCd);
		ooTime.elementOpt("isDefaultStart", isDefaultStart);
		ooTime.elementOpt("endDt", endDt);
		ooTime.elementOpt("effTime", effTime);
		ooTime.elementOpt("endTime", endTime);
		ooTime.elementOpt("effTimeUnitCd", effTimeUnitCd);
		ooTime.elementOpt("endTimeUnitCd", endTimeUnitCd);
		ooTime.elementOpt("isDefaultEnd", isDefaultEnd);
		ooTimes.add(ooTime);
		return ooTimes;
	}

	// 构造boAcctItems
	private JSONArray createBoAcctItems(String charge, String appCharge) {
		JSONArray boAcctItems = new JSONArray();
		JSONObject boAcctItem = new JSONObject();
		boAcctItem.elementOpt("acctItemTypeId", "90013");
		boAcctItem.elementOpt("amount", appCharge);
		//boAcctItem.element("platId", interfaceId);
		boAcctItem.element("platId", interfaceId == null || "".equals(interfaceId) ? "08" : interfaceId);
		boAcctItem.elementOpt("createdDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("payedDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("statusCd", "5JA");
		boAcctItem.elementOpt("payMethodId", "500");
		boAcctItem.elementOpt("realAmount", charge);
		boAcctItem.elementOpt("currency", "");
		boAcctItem.elementOpt("ratioMethod", "");
		boAcctItem.elementOpt("ratio", "");
		boAcctItems.add(boAcctItem);
		return boAcctItems;
	}

	/** bo_2_staff */
	private JSONArray bo2staff(List<Map<String, String>> staffInfoList, BoSeqCalculator boSeqCalculator) {
		JSONArray bo2staffs = new JSONArray();
		for (int i = 0; staffInfoList.size() > i; i++) {
			Map<String, String> staffInfo = staffInfoList.get(i);
			JSONObject oo2Staff = new JSONObject();
			oo2Staff.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
			oo2Staff.elementOpt("areaName", "北京");
			oo2Staff.elementOpt("orgName", staffInfo.get("orgName"));
			oo2Staff.elementOpt("orgId", staffInfo.get("orgId"));
			oo2Staff.elementOpt("staffId", staffInfo.get("staffId"));
			oo2Staff.elementOpt("staffType", staffInfo.get("staffType"));
			oo2Staff.elementOpt("staffTypeName", staffInfo.get("staffTypeName"));
			oo2Staff.elementOpt("staffNumber", staffInfo.get("staffNumber"));
			oo2Staff.elementOpt("state", "ADD");
			oo2Staff.elementOpt("statusCd", "S");
			bo2staffs.add(oo2Staff);
		}
		return bo2staffs;
	}

	private JSONArray createbo2Coupon(Node tdsNode, Long prodId, Long offerId, Long partyId,
			BoSeqCalculator boSeqCalculator) {
		// 构造boProd2Coupons
		JSONArray boProd2CouponArrJs = new JSONArray();
		List tdNodes = tdsNode.selectNodes("./td");
		for (Iterator itr = tdNodes.iterator(); itr.hasNext();) {
			Node couponNode = (Node) itr.next();
			Node bcdCodeNode = couponNode.selectSingleNode("./terminalCode");
			String bcdCodeStr = (null != bcdCodeNode ? bcdCodeNode.getText() : "");
			String materialIdStr = "";
			String storeIdStr = "";
			String storeNameStr = "";
			String chargeItemCdStr = CrmServiceManagerConstants.CHARGE_ITEM_CD_UIM;// UIM卡费用规格ID
			String couponChargeStr = "";
			String countStr = "1";
			// 查找供货商信息
			// 此处需恢复
			String agentId = "";
			String agentName = "";
			try {
				String materialInfo = soStoreSMO.getMaterialByCode(bcdCodeStr);
				JSONObject couponJson = null;
				logger.debug("物品校验xml格式:{}", materialInfo);
				if (materialInfo == null || materialInfo.equals("-1")) {
					logger.debug("查找物品信息失败：bcdcode={}", bcdCodeStr);
					throw new BmoException(-1, "查找物品信息失败!bcdcode=" + bcdCodeStr);
				}
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON json = xmlSerializer.read(materialInfo);
				couponJson = JSONObject.fromObject(json);
				JSONObject goodsDetail = couponJson.getJSONObject("GoodsDetail");
				agentId = goodsDetail.getString("productorId");
				agentName = goodsDetail.getString("productorName");
				storeIdStr = goodsDetail.getString("storeId");
				materialIdStr = goodsDetail.getString("materialId");
				storeNameStr = goodsDetail.getString("storeName");
				couponChargeStr = goodsDetail.getString("referPrice");
			} catch (Exception e) {
				logger.debug("查找物品信息失败：bcdcode={},{}", bcdCodeStr, e);
				throw new BmoException(-1, "查找物品信息失败!bcdcode=" + bcdCodeStr);
			}
			JSONObject newBoProd2couponJs = new JSONObject();
			newBoProd2couponJs.elementOpt("id", "-1");
			newBoProd2couponJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2couponJs.elementOpt("isTransfer", "Y");
			newBoProd2couponJs.elementOpt("inOutReasonId", "1");
			newBoProd2couponJs.elementOpt("saleId", "1");// 前台
			newBoProd2couponJs.elementOpt("couponId", materialIdStr);
			newBoProd2couponJs.elementOpt("couponinfoStatusCd", "A");// 可售
			newBoProd2couponJs.elementOpt("couponUsageTypeCd", "3");// 销售
			newBoProd2couponJs.elementOpt("inOutTypeId", "1");
			newBoProd2couponJs.elementOpt("chargeItemCd", chargeItemCdStr);
			newBoProd2couponJs.elementOpt("couponNum", countStr);
			newBoProd2couponJs.elementOpt("storeId", storeIdStr);
			newBoProd2couponJs.elementOpt("storeName", storeNameStr);
			newBoProd2couponJs.elementOpt("agentId", agentId);
			newBoProd2couponJs.elementOpt("agentName", agentName);
			newBoProd2couponJs.elementOpt("apCharge", couponChargeStr);
			newBoProd2couponJs.elementOpt("couponInstanceNumber", bcdCodeStr);
			newBoProd2couponJs.elementOpt("ruleId", null);
			newBoProd2couponJs.elementOpt("description", null);
			newBoProd2couponJs.elementOpt("partyId", partyId);
			newBoProd2couponJs.elementOpt("prodId", prodId);
			newBoProd2couponJs.elementOpt("offerId", offerId);
			newBoProd2couponJs.elementOpt("state", CommonDomain.ADD);
			boProd2CouponArrJs.add(newBoProd2couponJs);

		}
		return boProd2CouponArrJs;
	}
}
