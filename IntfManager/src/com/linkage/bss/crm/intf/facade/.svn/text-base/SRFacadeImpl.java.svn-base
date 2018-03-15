package com.linkage.bss.crm.intf.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.StringUtil;
import org.dom4j.Document;


import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.client.ICommonClient;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.storeclient.SaleResource;
import com.linkage.bss.crm.intf.storeclient.StoreForOnLineMall;
import com.linkage.bss.crm.intf.util.WSUtil;
import com.linkage.bss.crm.store.smo.StoreServiceSMO;


public class SRFacadeImpl implements SRFacade {
	
	private IntfSMO intfSMO;
	
	private static Log logger = Log.getLog(OfferFacadeImpl.class);
	
	private ICommonClient commonClient;
	
	//营销资源
	private SaleResource saleResource;
	
	
	public SaleResource getSaleResource() {
		return saleResource;
	}
	public void setSaleResource(SaleResource saleResource) {
		this.saleResource = saleResource;
	}
	
	public void setCommonClient(ICommonClient commonClient) {
		this.commonClient = commonClient;
	}

	private StoreForOnLineMall storeForOnLineMall;
	
	public void setStoreForOnLineMall(StoreForOnLineMall storeForOnLineMall) {
		this.storeForOnLineMall = storeForOnLineMall;
	}

	private StoreServiceSMO storeClientSMO;
	
	public StoreServiceSMO getStoreClientSMO() {
		return storeClientSMO;
	}
	public void setStoreClientSMO(StoreServiceSMO storeClientSMO) {
		this.storeClientSMO = storeClientSMO;
	}
	
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}
	
//	/**
//	 * 有价卡礼品受理确认接口
//	 * 
//	 * @throws Exception
//	 */
//	@Override
//	public String checkSequence(Map<String, String> map) throws Exception {
//		String returnMsg = "";
//		String xmlInfo = "";
//		String bcdCode = map.get("bcdCode");
//		String materialId = map.get("materialId");
//		String staffCode = map.get("staffCode");
//		String sailtime = map.get("sailTime");
//		String price = map.get("price");
//		String validity = map.get("validity");
//		String storeId = map.get("storeId");
//		String productorId = map.get("productorId");
//		String saleNo = map.get("saleNo");
//		xmlInfo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//				+ "<DoStoreInOutCrm><SaleTag>"
//				+ "<saleOutType>1</saleOutType>"
//				+ "<actionCd>XSCK</actionCd>"
//				+ // actionCd(动作类型): XSCK 销售出库
//				"<saleType>1</saleType>"
//				+ // saleType(单据类型):CRM即为“BSS订单”，值为1
//				"<saleNo>" + sailtime + "</saleNo>"
//				+ "<state>C</state>"
//				+ // state(操作状态): “C为竣工”，“D为临时状态”，“R为需要认证”
//				"<storeId>" + storeId + "</storeId>" + "<toStoreId>-1</toStoreId>" + "<staffId>" + staffCode
//				+ "</staffId>" + "<remark/>" + "<saleDate>" + sailtime + "</saleDate>" + "</SaleTag>"
//				+ "<SystemId>crm</SystemId>" + "<operaterSource>S</operaterSource>" + "<Coupons>";
//
//		String[] accNbrs = bcdCode.split("\\|");
//		if (accNbrs.length > 0) {
//			price = String.valueOf(Float.parseFloat(price) / accNbrs.length);
//		}
//		for (int i = 0; i < accNbrs.length; i++) {
//			logger.debug("===checksequence====获取的 accNbrs=======" + accNbrs[i]);
//			xmlInfo = xmlInfo + "<Coupon>" + "<id>1</id>" + "<materialId>" + materialId + "</materialId>"
//					+ "<co2CouponId>-1</co2CouponId>"
//					+ "<inOutTypeId>1</inOutTypeId>"
//					+ // “1”出库
//					"<productorId>" + productorId + "</productorId>" + "<storeId>" + storeId + "</storeId>"
//					+ "<toStoreId>-1</toStoreId>" + "<cnt>1</cnt><totalPrice>" + price + "</totalPrice>"
//					+ "<instanceCodes>" + accNbrs[i] + "</instanceCodes><status>0</status>" + // status（物品状态）：A
//					// 可售，B// 损坏，F// 周转，S// 烧码，L// 封锁，P// 维修，O// 报废，0// 所有
//					"<validateCode>" + validity + "</validateCode></Coupon>";
//		}
//		xmlInfo = xmlInfo + "</Coupons></DoStoreInOutCrm>";
//		
//		StringBuffer xmlInBuffer = new StringBuffer();
//		xmlInBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		xmlInBuffer.append("<DoStoreInOutCrm><SystemId>WANGYING</SystemId><operaterSource>W</operaterSource>");
//		xmlInBuffer.append("<SaleTag><actionCd>XS</actionCd><saleType>1</saleType>");
//		xmlInBuffer.append("<saleNo>").append(saleNo).append("</saleNo>");
//		xmlInBuffer.append("<oldSaleNo/><reasonType>0</reasonType><state>C</state>");
//		xmlInBuffer.append("<storeId>").append(storeId).append("</storeId>");
//		xmlInBuffer.append("<toStoreId>-1</toStoreId><staffId>1001</staffId><remark/>");
//		xmlInBuffer.append("<saleDate>").append(sailtime).append("</saleDate>");
//		xmlInBuffer.append("<saleOutType>3</saleOutType></SaleTag><Coupons>");
//		for (int i = 0; i < accNbrs.length; i++) {
//			logger.debug("===checksequence====获取的 accNbrs=======" + accNbrs[i]);
//			xmlInBuffer.append("<Coupon><id>1</id><inOutStatusId>1</inOutStatusId><co2CouponId>-1</co2CouponId>");
//			xmlInBuffer.append("<inOutTypeId>1</inOutTypeId><status>A</status><sourceInoutNbr/>");
//			xmlInBuffer.append("<isOld>B</isOld><cnt>1</cnt><caseTag/><ruleId/>");
//			xmlInBuffer.append("<materialId>").append(materialId).append("</materialId>");
//			xmlInBuffer.append("productorId").append(productorId).append("</productorId>");
//			xmlInBuffer.append("<storeId>").append(storeId).append("</storeId>");
//			xmlInBuffer.append("<toStoreId>-1</toStoreId>");
//			xmlInBuffer.append("<totaPrice>").append(price).append("<totalPrice>");
//			xmlInBuffer.append("<instanceCodes>").append(accNbrs[i]).append("</instanceCodes>");
//			xmlInBuffer.append("<validateCode>").append(validity).append("</validateCode>");
//		}
//		String xmlInString = xmlInBuffer.toString();
//		String returnXml = null;
//		returnXml = storeForOnLineMall.doStoreInOutSO(xmlInString);
////		returnMsg = storeClientSMO.doStoreInOutCrm(xmlInfo);
////		return returnMsg;
//		return returnXml;
//	}
	
	// 营销资源校验接口
	@Override
	public String validateCodeCoupons(String bcdCode, String typeId, String pw) throws Exception {
		try {
			String code = storeForOnLineMall.validateCodeCoupons(bcdCode, typeId, pw);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String getMaterialByCode(String materialId,String bcdCode) throws Exception {
		String msg = storeClientSMO.getMaterialByCode(materialId, bcdCode);
		return msg;
	}
	
	@Override
	public String checkSequence(Map<String, String> map) throws Exception {
		String bcdCode = map.get("bcdCode");
		String platId = map.get("platId");
		String saleNo = map.get("saleNo");
		String staffId = map.get("staffId");
		String saleDate = map.get("saleDate");
		String inOutTypeId = map.get("inOutTypeId");
		String price = map.get("price");
		String validateCode = map.get("validateCode");
		String systemId = "";
		String operaterSource = "";
		String storeId = "";
		if ("06".equals(platId)) {
			systemId = "WANGYING";
			operaterSource = "W";
		} else if ("07".equals(platId)) {
			systemId = "ZIZHU";
			operaterSource = "Z";
		} else if ("10".equals(platId)){
			systemId = "NSHANG";
			operaterSource = "N";
		}
		String[] bcdCodes = bcdCode.split("\\|");
		StringBuffer xmlBuffer = new StringBuffer();
		xmlBuffer.append("<DoStoreInOutCrm><Coupons>");
		for(int i=0,size=bcdCodes.length;i<size;i++){
			String materialId = "";
			String productorId = "";
			String instanceCodes = bcdCodes[i];
			String outXml = storeClientSMO.getMaterialByCode("-1", instanceCodes);
			Document document = WSUtil.parseXml(outXml);
			materialId = WSUtil.getXmlNodeText(document, "//GoodsDetailList/GoodsDetail/materialId");
			productorId = WSUtil.getXmlNodeText(document, "//GoodsDetailList/GoodsDetail/productorId");
			storeId = WSUtil.getXmlNodeText(document, "//GoodsDetailList/GoodsDetail/storeId");
			xmlBuffer.append("<Coupon>");
			xmlBuffer.append("<id>").append(i+1).append("</id>");
			xmlBuffer.append("<materialId>").append(materialId).append("</materialId>");
			xmlBuffer.append("<inOutStatusId>1</inOutStatusId>");
			xmlBuffer.append("<co2CouponId>-1</co2CouponId>");
			xmlBuffer.append("<inOutTypeId>").append(inOutTypeId).append("</inOutTypeId>");
			xmlBuffer.append("<productorId>").append(productorId).append("</productorId>");
			xmlBuffer.append("<storeId>").append(storeId).append("</storeId>");
			xmlBuffer.append("<toStoreId>").append(storeId).append("</toStoreId>");
			xmlBuffer.append("<status>A</status>");
			xmlBuffer.append("<sourceInoutNbr />");
			xmlBuffer.append("<isOld>B</isOld>");
			xmlBuffer.append("<cnt>1</cnt>");
			xmlBuffer.append("<totalPrice>").append(Double.parseDouble(price)/(size*100)).append("</totalPrice>");
			xmlBuffer.append("<caseTag />");
			xmlBuffer.append("<ruleId />");
			xmlBuffer.append("<instanceCodes>").append(instanceCodes).append("</instanceCodes>");
			xmlBuffer.append("<validateCode>").append(validateCode).append("</validateCode>");
			xmlBuffer.append("</Coupon>");
		}
		xmlBuffer.append("</Coupons>");
		xmlBuffer.append("<SystemId>").append(systemId).append("</SystemId>");
		xmlBuffer.append("<operaterSource>").append(operaterSource).append("</operaterSource>");
		xmlBuffer.append("<SaleTag><actionCd>XS</actionCd>");
		xmlBuffer.append("<saleType>1</saleType>");
		xmlBuffer.append("<saleNo>").append(saleNo).append("</saleNo>");
		xmlBuffer.append("<oldSaleNo />");
		xmlBuffer.append("<reasonType>0</reasonType>");
		xmlBuffer.append("<state>C</state>");
		xmlBuffer.append("<storeId>").append(storeId).append("</storeId>");
		xmlBuffer.append("<toStoreId>-1</toStoreId>");
		xmlBuffer.append("<staffId>").append(staffId).append("</staffId>");
		xmlBuffer.append("<remark />");
		xmlBuffer.append("<saleDate>").append(saleDate).append("</saleDate>");
		xmlBuffer.append("<saleOutType>3</saleOutType></SaleTag>");
		xmlBuffer.append("</DoStoreInOutCrm>");
		String xmlIn = xmlBuffer.toString();
		String returnXml = null;
		try {
			returnXml = storeForOnLineMall.doStoreInOutSO(xmlIn);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Map<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put("bcdCode", bcdCodes[0]);
		saveMap.put("xmlIn", xmlIn);
		saveMap.put("xmlOut", returnXml);
		intfSMO.saveSRInOut(saveMap);
		return returnXml;
	}
	
	@Override
	public void getMaterialByCode() throws Exception {
		String meterailId = "-1";
		String bcdCode = "0102001001320001557";
		String response = storeClientSMO.getMaterialByCode(meterailId, bcdCode);
	}
	
	@Override
	public void checkInOut() throws Exception {
		File f = new File("1111.txt");
		InputStream input = null ;  
			input = new FileInputStream(f);
			  byte b[] = new byte[(int)f.length()] ; 
		        input.read(b) ; 
		        input.close() ;
	       String s =  new String(b);
		String returnXml = storeForOnLineMall.doStoreInOutSO(s);
	}
	
	@Override
	public String getUnitySeq() {
		return commonClient.generateSeq(11000, "UNITYPAY_INTERFACE", "1"); 
	}
	@Override
	public String queryBatchId(String batchId) {
		String result= "";
		try {
			result = saleResource.queryBatchId(batchId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public String checkValueCard(String cardNo) {
		String result= "";
		try {
			result = storeForOnLineMall.checkValueCard(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public String doStoreInOutCrm(String request) throws Exception {
		String msg = storeClientSMO.doStoreInOutCrm(request);
		return msg;
	}
}
