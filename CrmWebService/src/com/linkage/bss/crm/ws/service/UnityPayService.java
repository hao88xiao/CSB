package com.linkage.bss.crm.ws.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.intf.facade.SRFacade;
import com.linkage.bss.crm.intf.facade.SysFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.storeclient.StoreForOnLineMall;
import com.linkage.bss.crm.unityPay.IndentItemPay;
import com.linkage.bss.crm.unityPay.IndentItemSync;
import com.linkage.bss.crm.unityPay.ItemListType;
import com.linkage.bss.crm.unityPay.ItemType;
import com.linkage.bss.crm.unityPay.PayItemListType;
import com.linkage.bss.crm.unityPay.PayListType;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.csb.CrmServiceServiceLocator;
import com.linkage.bss.crm.ws.csb.PayInfoType;
import com.linkage.bss.crm.ws.csb.PayItemType;
import com.linkage.bss.crm.ws.csb.WSSPortType;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.util.WSUtil;

public class UnityPayService extends AbstractService {

	private static Log logger = Log.getLog(UnityPayService.class);

	private StoreForOnLineMall storeForOnLineMall;

	private IntfSMO intfSMO;

	private SRFacade srFacade;

	private SysFacade sysFacade;

	private WSSPortType wssPortType = null;

	private boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	@WebMethod(exclude = true)
	public void setStoreForOnLineMall(StoreForOnLineMall storeForOnLineMall) {
		this.storeForOnLineMall = storeForOnLineMall;
	}

	public void setSysFacade(SysFacade sysFacade) {
		this.sysFacade = sysFacade;
	}

	public void setSrFacade(SRFacade srFacade) {
		this.srFacade = srFacade;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public UnityPayService() throws Exception {
		CrmServiceServiceLocator crmServiceServiceLocator = new CrmServiceServiceLocator();
		wssPortType = crmServiceServiceLocator.getCrmService();
	}

	public String callIndentItemPay(String request, String payIndentId) throws Exception {
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String saleNo = WSUtil.getXmlNodeText(document, "//request/saleNo");
			String custType = WSUtil.getXmlNodeText(document, "//request/custType");
			String custId = WSUtil.getXmlNodeText(document, "//request/custId");
			String custName = WSUtil.getXmlNodeText(document, "//request/custName");
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String chargeStd = WSUtil.getXmlNodeText(document, "//request/info/priceStd");
			String charge = WSUtil.getXmlNodeText(document, "//request/info/price");
			String validity = WSUtil.getXmlNodeText(document, "//request/info/validity");
			String method = WSUtil.getXmlNodeText(document, "//request/payInfo/method");
			String amount = WSUtil.getXmlNodeText(document, "//request/payInfo/amount");
			String appendInfo = WSUtil.getXmlNodeText(document, "//request/payInfo/appendInfo");
			String[] validities = validity.split("\\|");
			int count = validities.length;
			long start = System.currentTimeMillis();
			String itemTypeId = intfSMO.getItemTypeByBcdCode(validities[0]);
			if (isPrintLog) {
				System.out.println("callIndentItemPay.intfSMO.getItemTypeByBcdCode(根据资源的实例编码查询费用项) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			start = System.currentTimeMillis();
			String requestId = requestTime + srFacade.getUnitySeq() + "10" + platId;
			if (isPrintLog) {
				System.out.println("callIndentItemPay.srFacade.getUnitySeq 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			String cycleId = requestTime.substring(0, 8);
			start = System.currentTimeMillis();
			String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());// TODO
			if (isPrintLog) {
				System.out.println("callIndentItemPay.sysFacade.findStaffIdByStaffCode(根据staffCode取得staffId) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			Document documentStaffInfo = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(documentStaffInfo, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
			String itemId = "9" + platId + intfSMO.getPayItemId();
			IndentItemPay indentItemPay = new IndentItemPay();
			indentItemPay.setRequestId(requestId);
			indentItemPay.setRequestTime(requestTime);
			indentItemPay.setPlatId(platId);
			indentItemPay.setChannelId(channelId);
			indentItemPay.setCycleId(Integer.parseInt(cycleId));
			indentItemPay.setPayIdentId(payIndentId);
			indentItemPay.setCustType(custType);
			indentItemPay.setCustId(custId);
			indentItemPay.setCustName(custName);
			indentItemPay.setStaffId(staffId);
			indentItemPay.setInterfaceSerial(saleNo);
			PayItemListType payItemListType = new PayItemListType();
			List<com.linkage.bss.crm.unityPay.PayItemType> payList = payItemListType.getItem();
			com.linkage.bss.crm.unityPay.PayItemType payItemType = new com.linkage.bss.crm.unityPay.PayItemType();
			payItemType.setRefundPayIdentId("0");
			payItemType.setCustIndentNbr(saleNo);
			payItemType.setItemId(itemId);
			payItemType.setItemFlag("0");
			payItemType.setItemTypeId(itemTypeId);
			payItemType.setChargeStd(String.valueOf(Double.parseDouble(chargeStd) * count));
			payItemType.setCharge(charge);
			payItemType.setCommissionType(1);
			payItemType.setCommission(0);
			payItemType.setProductOfferId(0);
			payItemType.setProductOfferInstanceId("0");
			payItemType.setCount(String.valueOf(count));
			payItemType.setServId("");
			payItemType.setAcctId("");
			payItemType.setAccNbr("0");
			payItemType.setBillingMode(0);
			payItemType.setProductSpecId(0);
			payItemType.setProductSpecName("");
//			payItemType.setMaterialKind(materialKind)
			payList.add(payItemType);
			indentItemPay.setItemList(payItemListType);
			PayListType payListType = new PayListType();
			List<com.linkage.bss.crm.unityPay.PayInfoType> payInfoTypeList = payListType.getPayInfo();
			com.linkage.bss.crm.unityPay.PayInfoType payInfoType = new com.linkage.bss.crm.unityPay.PayInfoType();
			payInfoType.setAmount(Integer.parseInt(amount));
			payInfoType.setMethod(method);
			payInfoType.setAppendInfo(appendInfo);
			payInfoTypeList.add(payInfoType);
			indentItemPay.setPayList(payListType);
			String response = "";
			start = System.currentTimeMillis();
			response = intfSMO.indentItemPayIntf(indentItemPay);
			if (isPrintLog) {
				System.out.println("callIndentItemPay.intfSMO.indentItemPayIntf(封装3.4 已收订单费用传送接口) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			Document unityDoc = WSUtil.parseXml(response);
			String unityResult = WSUtil.getXmlNodeText(unityDoc, "//response/resultCode");
			String srResult="";
			if ("0".equals(unityResult)) {
				// 调用营销资源方法，同步调用统一支付的数据
				StringBuffer sb = new StringBuffer();
				sb.append("<UnityPay>");
				sb.append("<requestId>").append(requestId).append("</requestId>");
				sb.append("<requestTime>").append(requestTime).append("</requestTime>");
				sb.append("<platId>").append(platId).append("</platId>");
				sb.append("<channelId>").append(channelId).append("</channelId>");
				sb.append("<cycleId>").append(cycleId).append("</cycleId>");
				sb.append("<payIndentId>").append(payIndentId).append("</payIndentId>");
				sb.append("<custType>").append(custType).append("</custType>");
				sb.append("<custId>").append(requestId).append("</custId>");
				sb.append("<custName>").append(custName).append("</custName>");
				sb.append("<staffId>").append(staffId).append("</staffId>");
				sb.append("<interfaceSerial>").append(saleNo).append("</interfaceSerial>");
				sb.append("<itemList><item>");
				sb.append("<refundPayIndentId>").append("0").append("</refundPayIndentId>");
				sb.append("<custIndentNbr>").append(saleNo).append("</custIndentNbr>");
				sb.append("<itemId>").append(itemId).append("</itemId>");
				sb.append("<itemFlag>").append("0").append("</itemFlag>");
				sb.append("<itemTypeId>").append(itemTypeId).append("</itemTypeId>");
				sb.append("<chargeStd>").append(Double.parseDouble(chargeStd) * count).append("</chargeStd>");
				sb.append("<charge>").append(charge).append("</charge>");
				sb.append("<commissionType>").append("1").append("</commissionType>");
				sb.append("<commission>").append("0").append("</commission>");
				sb.append("<productOfferId>").append("0").append("</productOfferId>");
				sb.append("<productOfferInstanceId>").append("0").append("</productOfferInstanceId>");
				sb.append("<count>").append(count).append("</count>");
				sb.append("<servId>").append("").append("</servId>");
				sb.append("<acctId>").append("").append("</acctId>");
				sb.append("<accNbr>").append("0").append("</accNbr>");
				sb.append("<billingMode>").append(0).append("</billingMode>");
				sb.append("<productSpecId>").append(0).append("</productSpecId>");
				sb.append("<productSpecName>").append("").append("</productSpecName>");
				sb.append("</item></itemList><payList><payInfo>");
				sb.append("<method>").append(method).append("</method>");
				sb.append("<amount>").append(amount).append("</amount>");
				sb.append("<appendInfo>").append(appendInfo).append("</appendInfo>");
				sb.append("</payInfo></payList></UnityPay>");
				String xmlSR = sb.toString();
				//新加保存
				String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "doUnityPay", "如果调统一支付入参："+request, requestTime2);

				srResult = storeForOnLineMall.doUnityPay(xmlSR);
				
				intfSMO.saveResponseInfo(logId2, "CrmWebService", "doUnityPay", "如果调统一支付返回："+ request, requestTime2, srResult, new Date(), "1","0");
	 
				 if (!"0".equals(srResult)){
					    //如果调统一支付失败
						IndentItemPay reIndentItemPay = new IndentItemPay();
						reIndentItemPay.setRequestId(requestId);
						reIndentItemPay.setRequestTime(requestTime);
						reIndentItemPay.setPlatId(platId);
						reIndentItemPay.setChannelId(channelId);
						reIndentItemPay.setCycleId(Integer.parseInt(cycleId));
						reIndentItemPay.setPayIdentId(payIndentId);
						reIndentItemPay.setCustType(custType);
						reIndentItemPay.setCustId(custId);
						reIndentItemPay.setCustName(custName);
						reIndentItemPay.setStaffId(staffId);
						reIndentItemPay.setInterfaceSerial(saleNo);
						PayItemListType rePayItemListType = new PayItemListType();
						List<com.linkage.bss.crm.unityPay.PayItemType> rePayList = rePayItemListType.getItem();
						com.linkage.bss.crm.unityPay.PayItemType rePayItemType = new com.linkage.bss.crm.unityPay.PayItemType();
						rePayItemType.setRefundPayIdentId(payIndentId);
						rePayItemType.setCustIndentNbr(saleNo);
						rePayItemType.setItemId(itemId);
						rePayItemType.setItemFlag("0");
						rePayItemType.setItemTypeId(itemTypeId);
						rePayItemType.setChargeStd(String.valueOf(Double.parseDouble(chargeStd) * count));
						rePayItemType.setCharge(charge);
						rePayItemType.setCommissionType(1);
						rePayItemType.setCommission(0);
						rePayItemType.setProductOfferId(0);
						rePayItemType.setProductOfferInstanceId("0");
						rePayItemType.setCount(String.valueOf(count));
						rePayItemType.setServId("");
						rePayItemType.setAcctId("");
						rePayItemType.setAccNbr("0");
						rePayItemType.setBillingMode(0);
						rePayItemType.setProductSpecId(0);
						rePayItemType.setProductSpecName("");
						rePayList.add(rePayItemType);
						reIndentItemPay.setItemList(rePayItemListType);
						PayListType repayListType = new PayListType();
						List<com.linkage.bss.crm.unityPay.PayInfoType> rePayInfoTypeList = repayListType.getPayInfo();
						com.linkage.bss.crm.unityPay.PayInfoType rePayInfoType = new com.linkage.bss.crm.unityPay.PayInfoType();
						rePayInfoType.setAmount(-Integer.parseInt(amount));
						rePayInfoType.setMethod(method);
						rePayInfoType.setAppendInfo(appendInfo);
						rePayInfoTypeList.add(rePayInfoType);
						reIndentItemPay.setPayList(repayListType);
						String response1 = "";
						start = System.currentTimeMillis();
						response1 = intfSMO.indentItemPayIntf(reIndentItemPay);
						StringBuffer sbResponse = new StringBuffer();
						sbResponse.append("<response>");
						sbResponse.append("<resultCode>-1</resultCode>");
						sbResponse.append("</response>");
						return sbResponse.toString();
						
					}
			}
			
			return response;
		} catch (Exception e) {
			logger.error("callIndentItemPay:" + request, e);
			throw e;
		}
	}

	/**
	 * 封装 统一支付3.4已收费订单同步接口
	 * 
	 * @return
	 */
	@WebMethod
	// @Required(nodes = { @Node(xpath = "//request/checkId", caption = "证件类型"),
	// @Node(xpath = "//request/typeCode", caption = "证件编号") })
	public String indentItemPay(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String requestId = WSUtil.getXmlNodeText(document, "//request/requestId");
			String requestTime = WSUtil.getXmlNodeText(document, "//request/requestTime");
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String cycleId = WSUtil.getXmlNodeText(document, "//request/cycleId");
			int cycleIdint = Integer.parseInt(cycleId);
			String payIdentId = WSUtil.getXmlNodeText(document, "//request/payIndentId");
			String custType = WSUtil.getXmlNodeText(document, "//request/custType");
			String custId = WSUtil.getXmlNodeText(document, "//request/custId");
			String custName = WSUtil.getXmlNodeText(document, "//request/custName");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String interfaceSerial = WSUtil.getXmlNodeText(document, "//request/interfaceSerial");
			Element requestNode = (Element) document.selectSingleNode("//request");
			Element itemList = (Element) requestNode.selectSingleNode("./itemList");
			List<Element> listItemNodes = itemList.selectNodes("./item");
			Element payList = (Element) requestNode.selectSingleNode("./payList");
			PayItemType[] payItemTypes = new PayItemType[listItemNodes.size()];
			for (int i = 0; i < listItemNodes.size(); i++) {
				PayItemType payItemType = new PayItemType();
				Element element = listItemNodes.get(i);
				String refundPayIndentId = element.selectSingleNode("./refundPayIndentId").getText();
				String custIndentNbr = element.selectSingleNode("./custIndentNbr").getText();
				String itemId = element.selectSingleNode("./itemId").getText();
				String itemFlag = element.selectSingleNode("./itemFlag").getText();
				String itemTypeId = element.selectSingleNode("./itemTypeId").getText();
				String chargeStd = element.selectSingleNode("./chargeStd").getText();
				String charge = element.selectSingleNode("./charge").getText();
				String commissionType = element.selectSingleNode("./commissionType").getText();
				int commissionTypeint = Integer.parseInt(commissionType);
				String commission = element.selectSingleNode("./commission").getText();
				int commissionint = Integer.parseInt(commission);
				String productOfferId = element.selectSingleNode("./productOfferId").getText();
				int productOfferIdint = Integer.parseInt(productOfferId);
				String productOfferInstanceId = element.selectSingleNode("./productOfferInstanceId").getText();
				String count = element.selectSingleNode("./count").getText();
				String servId = element.selectSingleNode("./servId").getText();
				String acctId = element.selectSingleNode("./acctId").getText();
				String accNbr = element.selectSingleNode("./accNbr").getText();
				String billingMode = element.selectSingleNode("./billingMode").getText();
				int billingModeint = Integer.parseInt(billingMode);
				String productSpecId = element.selectSingleNode("./productSpecId").getText();
				int productSpecIdint = Integer.parseInt(productSpecId);
				String productSpecName = element.selectSingleNode("./productSpecName").getText();
				payItemType.setRefundPayIdentId(refundPayIndentId);
				payItemType.setCustIndentNbr(custIndentNbr);
				payItemType.setItemId(itemId);
				payItemType.setItemFlag(itemFlag);
				payItemType.setItemTypeId(itemTypeId);
				payItemType.setChargeStd(chargeStd);
				payItemType.setCharge(charge);
				payItemType.setCommissionType(commissionTypeint);
				payItemType.setCommission(commissionint);
				payItemType.setProductOfferId(productOfferIdint);
				payItemType.setProductOfferInstanceId(productOfferInstanceId);
				payItemType.setCount(count);
				payItemType.setServId(servId);
				payItemType.setAcctId(acctId);
				payItemType.setAccNbr(accNbr);
				payItemType.setBillingMode(billingModeint);
				payItemType.setProductSpecId(productSpecIdint);
				payItemType.setProductSpecName(productSpecName);
				payItemTypes[i] = payItemType;
			}
			List<Element> listpayNodes = payList.selectNodes("./payInfo");
			PayInfoType[] payInfoTypes = new PayInfoType[listpayNodes.size()];
			for (int i = 0; i < listpayNodes.size(); i++) {
				PayInfoType payInfoType = new PayInfoType();
				Element element = listpayNodes.get(i);
				String method = element.selectSingleNode("./method").getText();
				String amount = element.selectSingleNode("./amount").getText();
				int amountint = Integer.parseInt(amount);
				String appendInfo = element.selectSingleNode("./appendInfo").getText();
				payInfoType.setAmount(amountint);
				payInfoType.setAppendInfo(appendInfo);
				payInfoType.setMethod(method);
				payInfoTypes[i] = payInfoType;
			}
			StringHolder responseId = new StringHolder();
			StringHolder responseTime = new StringHolder();
			StringHolder payIndentId = new StringHolder();
			IntHolder result = new IntHolder();
			wssPortType.indentItemPay(requestId, requestTime, platId, channelId, cycleIdint, payIdentId, custType,
					custId, custName, staffId, interfaceSerial, payItemTypes, payInfoTypes, responseId, responseTime,
					payIndentId, result);
			System.out.println("responseId:" + responseId.value);
			System.out.println("responseTime:" + responseTime.value);
			System.out.println("payIndentId:" + payIndentId.value);
			System.out.println("result:" + result.value);
			return "ssss";
		} catch (Exception e) {
			logger.error("indentItemPay封装 统一支付3.4已收费订单同步接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	@WebMethod
	// @Required(nodes = { @Node(xpath = "//request/checkId", caption = "证件类型"),
	// @Node(xpath = "//request/typeCode", caption = "证件编号") })
	public String indentInvoiceNumQry(@WebParam(name = "request") String request) {
		try {

			return "indentInvoiceNumQry";
		} catch (Exception e) {
			logger.error("indentInvoiceNumQry:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	private static IndentItemSync getIndentItemSyncObj(String str) {
		JSONObject json = JSONObject.fromObject(str);
		IndentItemSync indentItemSync = new IndentItemSync();
		String channelId = json.getString("channelId");
		String custId = json.getString("custId");
		String custName = json.getString("custName");
		String custType = json.getString("custType");
		String cycleId = json.getString("cycleId");
		String payIndentId = json.getString("payIndentId");
		String platId = json.getString("platId");
		String requestId = json.getString("requestId");
		String requestTime = json.getString("requestTime");
		String staffId = json.getString("staffId");
		indentItemSync.setChannelId(channelId);
		indentItemSync.setCustId(custId);
		indentItemSync.setCustName(custName);
		indentItemSync.setCustType(custType);
		indentItemSync.setCycleId(Integer.parseInt(cycleId));
		indentItemSync.setPayIndentId(payIndentId);
		indentItemSync.setPlatId(platId);
		indentItemSync.setRequestId(requestId);
		indentItemSync.setRequestTime(requestTime);
		indentItemSync.setStaffId(staffId);
		JSONObject itemList = json.getJSONObject("itemList");
		JSONArray list = itemList.getJSONArray("item");
		ItemListType itemListType = new ItemListType();
		List<ItemType> itemtypeList = itemListType.getItem();
		for (int i = 0; i < list.size(); i++) {
			JSONObject itemJson = list.getJSONObject(i);
			ItemType itemType = new ItemType();
			String accNbr = itemJson.getString("accNbr");
			String acctId = itemJson.getString("acctId");
			String billingMode = itemJson.getString("billingMode");
			String charge = itemJson.getString("charge");
			String chargeStd = itemJson.getString("chargeStd");
			String commission = itemJson.getString("commission");
			String commissionType = itemJson.getString("commissionType");
			String count = itemJson.getString("count");
			String custIndentNbr = itemJson.getString("custIndentNbr");
			String itemFlag = itemJson.getString("itemFlag");
			String itemId = itemJson.getString("itemId");
			String itemTypeId = itemJson.getString("itemTypeId");
			String productOfferId = itemJson.getString("productOfferId");
			String productOfferInstanceId = itemJson.getString("productOfferInstanceId");
			String productSpecId = itemJson.getString("productSpecId");
			String productSpecName = itemJson.getString("productSpecName");
			String refundPayIndentId = itemJson.getString("refundPayIndentId");
			String servId = itemJson.getString("servId");
			itemType.setAccNbr(accNbr);
			itemType.setAcctId(acctId);
			itemType.setBillingMode(Integer.parseInt(billingMode));
			itemType.setCharge(Integer.parseInt(charge));
			itemType.setChargeStd(Integer.parseInt(chargeStd));
			itemType.setCommission(Integer.parseInt(commission));
			itemType.setCommissionType(Integer.parseInt(commissionType));
			itemType.setCount(Integer.parseInt(count));
			itemType.setCustIndentNbr(custIndentNbr);
			itemType.setItemFlag(itemFlag);
			itemType.setItemId(itemId);
			itemType.setItemTypeId(itemTypeId);
			itemType.setProductOfferId(Integer.parseInt(productOfferId));
			itemType.setProductOfferInstanceId(productOfferInstanceId);
			itemType.setProductSpecId(Integer.parseInt(productSpecId));
			itemType.setProductSpecName(productSpecName);
			itemType.setRefundPayIndentId(refundPayIndentId);
			itemType.setServId(servId);
			itemtypeList.add(itemType);
		}
		indentItemSync.setItemList(itemListType);
		System.out.println(JSONObject.fromObject(indentItemSync).toString());
		return indentItemSync;
	}

	private Map<String, Object> callSyn(IndentItemSync indentItemSync) {
		Map<String, Object> map = intfSMO.indentItemSync(indentItemSync);
		return map;
	}

	private static IndentItemPay getIndentItemPayObj(String str) {
		JSONObject json = JSONObject.fromObject(str);
		IndentItemPay indentItemPay = new IndentItemPay();
		String channelId = json.getString("channelId");
		String custId = json.getString("custId");
		String custName = json.getString("custName");
		String custType = json.getString("custType");
		String cycleId = json.getString("cycleId");
		String payIndentId = json.getString("payIndentId");
		String platId = json.getString("platId");
		String requestId = json.getString("requestId");
		String requestTime = json.getString("requestTime");
		String staffId = json.getString("staffId");
		indentItemPay.setChannelId(channelId);
		indentItemPay.setCustId(custId);
		indentItemPay.setCustName(custName);
		indentItemPay.setCustType(custType);
		indentItemPay.setCycleId(Integer.parseInt(cycleId));
		indentItemPay.setPayIdentId(payIndentId);
		indentItemPay.setPlatId(platId);
		indentItemPay.setRequestId(requestId);
		indentItemPay.setRequestTime(requestTime);
		indentItemPay.setStaffId(staffId);

		return indentItemPay;
	}

	public Map<String, Object> reCallSyn(String str) {
		return callSyn(getIndentItemSyncObj(str));
	}

	public String callIndentItemPayAfterOut(String request, String payIndentId) throws Exception {
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String saleNo = WSUtil.getXmlNodeText(document, "//request/saleNo");
			String custType = WSUtil.getXmlNodeText(document, "//request/custType");
			String custId = WSUtil.getXmlNodeText(document, "//request/custId");
			String custName = WSUtil.getXmlNodeText(document, "//request/custName");
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String chargeStd = WSUtil.getXmlNodeText(document, "//request/info/priceStd");
			String charge = WSUtil.getXmlNodeText(document, "//request/info/price");
			String validity = WSUtil.getXmlNodeText(document, "//request/info/validity");
			String method = WSUtil.getXmlNodeText(document, "//request/payInfo/method");
			String amount = WSUtil.getXmlNodeText(document, "//request/payInfo/amount");
			String appendInfo = WSUtil.getXmlNodeText(document, "//request/payInfo/appendInfo");
			String cycleId = WSUtil.getXmlNodeText(document, "//request/cycleId");
			String[] validities = validity.split("\\|");
			int count = validities.length;
			String itemTypeId = intfSMO.getItemTypeByBcdCode(validities[0]);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			date.setYear(Integer.parseInt(cycleId.substring(0, 4)) - 1900);
			date.setMonth(Integer.parseInt(cycleId.substring(4, 6)) - 1);
			date.setDate(Integer.parseInt(cycleId.substring(6, 8)));
			String requestTime = format.format(date);
			String requestId = requestTime + srFacade.getUnitySeq() + "10" + platId;
			String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());// TODO
			Document documentStaffInfo = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(documentStaffInfo, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
			String itemId = "9" + platId + intfSMO.getPayItemId();
			IndentItemPay indentItemPay = new IndentItemPay();
			indentItemPay.setRequestId(requestId);
			indentItemPay.setRequestTime(requestTime);
			indentItemPay.setPlatId(platId);
			indentItemPay.setChannelId(channelId);
			indentItemPay.setCycleId(Integer.parseInt(cycleId));
			indentItemPay.setPayIdentId(payIndentId);
			indentItemPay.setCustType(custType);
			indentItemPay.setCustId(custId);
			indentItemPay.setCustName(custName);
			indentItemPay.setStaffId(staffId);
			indentItemPay.setInterfaceSerial(saleNo);
			PayItemListType payItemListType = new PayItemListType();
			List<com.linkage.bss.crm.unityPay.PayItemType> payList = payItemListType.getItem();
			com.linkage.bss.crm.unityPay.PayItemType payItemType = new com.linkage.bss.crm.unityPay.PayItemType();
			payItemType.setRefundPayIdentId("0");
			payItemType.setCustIndentNbr(saleNo);
			payItemType.setItemId(itemId);
			payItemType.setItemFlag("0");
			payItemType.setItemTypeId(itemTypeId);
			payItemType.setChargeStd(String.valueOf(Double.parseDouble(chargeStd) * count));
			payItemType.setCharge(charge);
			payItemType.setCommissionType(1);
			payItemType.setCommission(0);
			payItemType.setProductOfferId(0);
			payItemType.setProductOfferInstanceId("0");
			payItemType.setCount(String.valueOf(count));
			payItemType.setServId("");
			payItemType.setAcctId("");
			payItemType.setAccNbr("0");
			payItemType.setBillingMode(0);
			payItemType.setProductSpecId(0);
			payItemType.setProductSpecName("");
			payList.add(payItemType);
			indentItemPay.setItemList(payItemListType);
			PayListType payListType = new PayListType();
			List<com.linkage.bss.crm.unityPay.PayInfoType> payInfoTypeList = payListType.getPayInfo();
			com.linkage.bss.crm.unityPay.PayInfoType payInfoType = new com.linkage.bss.crm.unityPay.PayInfoType();
			payInfoType.setAmount(Integer.parseInt(amount));
			payInfoType.setMethod(method);
			payInfoType.setAppendInfo(appendInfo);
			payInfoTypeList.add(payInfoType);
			indentItemPay.setPayList(payListType);
			String response = "";
			response = intfSMO.indentItemPayIntf(indentItemPay);
			Document unityDoc = WSUtil.parseXml(response);
			String unityResult = WSUtil.getXmlNodeText(unityDoc, "//response/resultCode");

			if ("0".equals(unityResult)) {
				// 调用营销资源方法，同步调用统一支付的数据
				StringBuffer sb = new StringBuffer();
				sb.append("<UnityPay>");
				sb.append("<requestId>").append(requestId).append("</requestId>");
				sb.append("<requestTime>").append(requestTime).append("</requestTime>");
				sb.append("<platId>").append(platId).append("</platId>");
				sb.append("<channelId>").append(channelId).append("</channelId>");
				sb.append("<cycleId>").append(cycleId).append("</cycleId>");
				sb.append("<payIndentId>").append(payIndentId).append("</payIndentId>");
				sb.append("<custType>").append(custType).append("</custType>");
				sb.append("<custId>").append(requestId).append("</custId>");
				sb.append("<custName>").append(custName).append("</custName>");
				sb.append("<staffId>").append(staffId).append("</staffId>");
				sb.append("<interfaceSerial>").append(saleNo).append("</interfaceSerial>");
				sb.append("<itemList><item>");
				sb.append("<refundPayIndentId>").append("0").append("</refundPayIndentId>");
				sb.append("<custIndentNbr>").append(saleNo).append("</custIndentNbr>");
				sb.append("<itemId>").append(itemId).append("</itemId>");
				sb.append("<itemFlag>").append("0").append("</itemFlag>");
				sb.append("<itemTypeId>").append(itemTypeId).append("</itemTypeId>");
				sb.append("<chargeStd>").append(chargeStd).append("</chargeStd>");
				sb.append("<charge>").append(charge).append("</charge>");
				sb.append("<commissionType>").append("1").append("</commissionType>");
				sb.append("<commission>").append("0").append("</commission>");
				sb.append("<productOfferId>").append("0").append("</productOfferId>");
				sb.append("<productOfferInstanceId>").append("0").append("</productOfferInstanceId>");
				sb.append("<count>").append(count).append("</count>");
				sb.append("<servId>").append("").append("</servId>");
				sb.append("<acctId>").append("").append("</acctId>");
				sb.append("<accNbr>").append("0").append("</accNbr>");
				sb.append("<billingMode>").append(0).append("</billingMode>");
				sb.append("<productSpecId>").append(0).append("</productSpecId>");
				sb.append("<productSpecName>").append("").append("</productSpecName>");
				sb.append("</item></itemList><payList><payInfo>");
				sb.append("<method>").append(method).append("</method>");
				sb.append("<amount>").append(amount).append("</amount>");
				sb.append("<appendInfo>").append(appendInfo).append("</appendInfo>");
				sb.append("</payInfo></payList></UnityPay>");
				String xmlSR = sb.toString();
				String srResult = storeForOnLineMall.doUnityPay(xmlSR);
			}
			return response;
		} catch (Exception e) {
			logger.error("callIndentItemPay:" + request, e);
			throw e;
		}
	}

	public static void main(String[] args) {
		Date date = new Date();
		date.setYear(2013 - 1900);
		date.setMonth(3 - 1);
		date.setDate(24);
		System.out.println("date:" + date);
	}
}
