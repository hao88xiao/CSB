package com.linkage.bss.crm.ws;

import org.junit.Test;

public class zhy_TestClient {
	public static String rsc = "http://172.19.17.152:8888/CrmWebService/rsc?wsdl";
	public static String sr = "http://172.19.17.152:8888/CrmWebService/sr?wsdl";
//	public static String order = "http://172.19.17.152:7101/BJCSB/services/CrmService?p=6090010020";
//	public static String cust = "http://172.19.17.152:8888/CrmWebService/customer?wsdl";
//	public static String agent = "http://172.19.17.152:8888/CrmWebService/agent?wsdl";
	
//	public static String rsc = "http://172.19.17.152:8888/CrmWebService/rsc?wsdl";
//	public static String sr = "http://172.19.17.152:8888/CrmWebService/sr?wsdl";
	public static String order = "http://172.19.100.180:9300/BJCSB/services/CrmService?p=6090010020";
	public static String cust = "http://172.19.100.180:9300/BJCSB/services/CrmService?p=6090010028";
	public static String agent = "http://172.19.100.180:9300/BJCSB/services/CrmService?p=6090010020";

//	@Test
//	public void queryAgentInfo() {
//		String request = "<request><checkStaffCd>P0059152</checkStaffCd><channelId>10000</channelId><staffCode>A00919</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryAgentInfo", agent);
//		System.out.println(response);
//	}
//
//	@Test
//	public void login() {
//		String request = "<request><staffNumber>P0059152</staffNumber><passwd>88270088</passwd></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "login", agent);
//		System.out.println(response);
//	}
//
//	@Test
//	public void qryProd() {
//		String request = "<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "qryProd", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void cancelOrder() {
//		String request = "<request><olId>100032749524</olId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "cancelOrder", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void queryNbrByIccid() {
//		String request = "<request><serialNum>8986030981010484943</serialNum><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryNbrByIccid", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void createCust() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<custInfo>");
//		request.append("<custName>").append("qwe").append("</custName>");
//		request.append("<custType>").append("1").append("</custType>");
//		request.append("<areaCode>").append("010").append("</areaCode>");
//		request.append("<cerdAddr>").append("ZHENGJIAN").append("</cerdAddr>");
//		request.append("<cerdType>").append("2").append("</cerdType>");
//		request.append("<cerdValue>").append("shiguan").append("</cerdValue>");
//		request.append("<contactPhone1>").append("LIANXIDIANHUA").append("</contactPhone1>");
//		request.append("<contactPhone2>").append("QITA DIANHUA").append("</contactPhone2>");
//		request.append("<custBusinessPwd>").append("custBusinessPwd").append("</custBusinessPwd>");
//		request.append("<custQueryPwd>").append("custQueryPwd").append("</custQueryPwd>");
//		request.append("<emailAddr>").append("emailAddr").append("</emailAddr>");
//		request.append("<custAddr>").append("tongxin").append("</custAddr>");
//		request.append("<linkMan>").append("linkMan").append("</linkMan>");
//		request.append("<contactPhone2>").append("contactPhone2").append("</contactPhone2>");
//		request.append("</custInfo>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>A00919</staffCode>");
//		request.append("</request>");
//
//		String response = WebServiceAxisClient.webServicePost(request.toString(), "createCust", cust);
//		System.out.println(response);
//	}
//
//	@Test
//	public void qryCust() {
//		String request = "<request><selType>1</selType><value>联想(北京)有限公司</value><identifyType></identifyType><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		// <request><selType>1</selType><value>test</value><identifyType>2</identifyType><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>
//		String response = WebServiceAxisClient.webServicePost(request, "qryCust", cust);
//		System.out.println(response);
//	}
//
//	@Test
//	public void queryService() {
//		String request = "<request><accNbr>13370165593</accNbr><accNbrType>1</accNbrType><queryType>1,2</queryType><queryMode>2</queryMode><areaId>10000</areaId><channelId>11040361</channelId><staffCode>cs007</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(request.toString(), "queryService", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void queryOperation() {
//		String request = "<request><accNbr>13311068294</accNbr><accNbrType>1</accNbrType><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryOperation", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void queryOffering2pp() {
//		String request = "<request><accNbr>13370162166</accNbr><accNbrType>1</accNbrType><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryOffering2pp", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void qryAcctInfo() {
//		String request = "<request><partyId>104008512432</partyId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId></areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "qryAcctInfo", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void checkPartyProd() {
//		String request = "<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkPartyProd", order);
//		System.out.println(response);
//	}
//
//	@Test
//	public void initStaticData() {
//		String request = "<request><id>1,2,3</id><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "initStaticData", agent);
//		System.out.println(response);
//	}
//
//	@Test
//	public void checkPassword() {
//		String request = "<request><accNbr>2010575370197000</accNbr><accNbrType>13</accNbrType><password>888888</password><passwordType>4</passwordType><channelId>11040361</channelId><staffCode>BJ_10020</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkPassword", cust);
//		System.out.println(response);
//	}
//
//	@Test
//	public void modifyCustom() {
////		StringBuilder request = new StringBuilder();
////		request.append("<request>");
////		request.append("<custInfo>");
////		request.append("<partyId>").append("103005069720").append("</partyId>");
////		request.append("<custName>").append("robinv11").append("</custName>");
////		request.append("<custType>").append("1").append("</custType>");
////		request.append("<areaCode>").append("").append("</areaCode>");
////		request.append("<cerdAddr>").append("").append("</cerdAddr>");
////		request.append("<cerdType>").append("").append("</cerdType>");
////		request.append("<cerdValue>").append("").append("</cerdValue>");
////		request.append("<contactPhone1>").append("").append("</contactPhone1>");
////		request.append("<custLevel>").append("").append("</custLevel>");
////		request.append("<custBusinessPwd>").append("").append("</custBusinessPwd>");
////		request.append("<custQueryPwd>").append("").append("</custQueryPwd>");
////		request.append("<emailAddr>").append("2@qq.com").append("</emailAddr>");
////		request.append("<linkMan>").append("").append("</linkMan>");
////		request.append("<postCode>").append("").append("</postCode>");
////		request.append("<custAddr>").append("").append("</custAddr>");
////		request.append("</custInfo>");
////		request.append("<areaId>10000</areaId>");
////		request.append("<channelId>11040361</channelId>");
////		request.append("<staffCode>BJ_10020</staffCode>");
////		request.append("</request>");
//
//		String request = "<request> <custInfo> <partyId>200000371806</partyId> <custName>联想(北京)有限公司</custName> <custType>1</custType>  </custInfo> <areaId>10000</areaId> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> </request>";
//		String response = WebServiceAxisClient.webServicePost(request.toString(), "modifyCustom", cust);
//		
//		System.out.println(response);
//	}
//
//	@Test
//	public void isYKSXInfo() {
//		String request = "<request><accNbr>15340117588</accNbr><channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "isYKSXInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void newValidateService() {
//		String request = "<request><validateInfo><accessNumber>13366523736</accessNumber><custName>-9</custName><IDCardType>1</IDCardType><IDCard>3451146167280000</IDCard>	</validateInfo>	<validateType>9</validateType>	<areaCode></areaCode><channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "newValidateService", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryAccNBRInfo() {
//		String request = "<request><accNbr>15947421234</accNbr><accNbrType>1</accNbrType><areaId>10000</areaId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryAccNBRInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryAreaInfo() {
//		String request = "<request>	<areaCode>0757</areaCode>	<areaName></areaName>	<channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryAreaInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryOperatingOfficeInfo() {
//		String request = "<request>	<areaCode>010</areaCode>	<queryType>3</queryType>	<areaName>大郊亭</areaName>	<channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryOperatingOfficeInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void checkGlobalroam() {
//		String request = "<request>	<accNbr>13366523736</accNbr>	<channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkGlobalroam", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryImsiInfoByMdn() {
//		String request = "<request>	<accNbr>15340117588</accNbr>	<channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryImsiInfoByMdn", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryProdinfoByImsi() {
//		String request = "<request>	<cImsi>460030900236077</cImsi>	<channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryProdinfoByImsi", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryProdByCompProdSpecId() {
//		String request = "<request> <partyId>104004501884</partyId> <prodSpecId>379</prodSpecId> <roleCD>1</roleCD> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId> </request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryProdByCompProdSpecId", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void compProdRule() {
//		String request = "<request><partyId>104004501884</partyId><accNum>13366523736</accNum>	<offerId>100106334236</offerId><prodSpecId>379</prodSpecId><channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "compProdRule", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryPpInfo() {
//		String request = "<request> <accNbr>18911903788</accNbr> <accNbrType>1</accNbrType> <areaId>11000</areaId> <channelId>11040754</channelId> <staffCode>BJ_10020</staffCode></request>";
//		// <request> <accNbr>13366497575</accNbr> <accNbrType>1</accNbrType><areaId>11000</areaId> <channelId>11040754</channelId><staffCode>BJ_10020</staffCode></request>
//		//PAD:<request><offerSpecId>900565</offerSpecId> <prodSpecId>378</prodSpecId> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode></request>
//		String response = WebServiceAxisClient.webServicePost(request, "queryPpInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void newAcct() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<partyId>103005065279</partyId>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>BJ_10020</staffCode>");
//		request.append("</request>");
//		String response = WebServiceAxisClient.webServicePost(request.toString(), "newAcct", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryOptionalOfferList() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<partyId>104003067856</partyId>");
//		request.append("<categoryNodeId>100041</categoryNodeId>");
//		request.append("<offerSpecId>25000</offerSpecId>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>BJ_10020</staffCode>");
//		request.append("</request>");
//		String response = WebServiceAxisClient.webServicePost(request.toString(), "queryOptionalOfferList", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryCouponInfoByPriceplan() {
//		String request = "<request><offerSpecId>25100</offerSpecId><prodSpecId>378</prodSpecId><channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryCouponInfoByPriceplan", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void highFeeQueryUserInfo() {
//		String request = "<request><acc_nbr>460030900236077</acc_nbr><flag>2</flag></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "highFeeQueryUserInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryFNSInfo() {
//		String request = "<request> <accNbr>15321781739</accNbr> <accNbrType>1</accNbrType> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> <areaId>10000</areaId> </request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryFNSInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void ifImportantPartyByPartyId() {
//		String request = "<request><partyId>103006069136</partyId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "ifImportantPartyByPartyId", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryCardinfoByAcct() {
//		String request = "<request><AccountID>18910329785</AccountID></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "QueryCardinfoByAcct", order);
//		System.out.print(response);
//	}
//	
//	@Test
//	public void businessService() {
//		String request = "<request> <order>  <orderTypeId>17</orderTypeId>  <partyId></partyId>  <prodSpecId>378</prodSpecId>  <accessNumber>18901395401</accessNumber>  <prodId></prodId>  <offerSpecs>   <offerSpec>    <id>992018374</id>    <name></name>    <!-- 0开通 | 1 退订 | 2 修改  | 3 续订-->    <actionType>0</actionType>    <!--生效方式 不填 次月1号生效 | 0 立即生效 | 1 默认生效 -->    <startFashion></startFashion>    <!--失效方式 不填 次月1号失效 | 0 立即失效 | 1 默认失效方式3000年 | 大于1生效后N账期失效  -->    <endFashion></endFashion>    <!-- 生效时间 -->    <startDt></startDt>    <!-- 失效时间 -->    <endDt></endDt> <properties> <property> <id>20217</id> <name>电子邮箱</name> <value>robin@ailk.com</value> <!-- 0开通 | 1 退订 | 2 修改 --> <actionType>0</actionType> </property> </properties> </offerSpec>  </offerSpecs> </order> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "businessService", order);
//		System.out.print(response);
//	}
//	
//	@Test
//	public void checkResultIn() {
//		String request = "<request><name>张荣娣</name><identifyValue>110101194201201024</identifyValue><staffId>1001</staffId><channelId>11040611</channelId><staffCode>C00878</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkResultIn", cust);
//		System.out.print(response);
//	}
//	
//	@Test
//	public void queryContinueOrderPPInfo() {
//		String request = "<request> <accNbr>18910403498</accNbr> <accNbrType>1</accNbrType> <areaId>11000</areaId> <channelId>11040754</channelId> <staffCode>P0059125</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryContinueOrderPPInfo", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void confirmContinueOrderPPInfo() {
//		String request = "<request> <accNbr>18910403498</accNbr> <accNbrType>1</accNbrType> <confirmPPInfo> <id>24305</id> <name>(24203)3G-150元包年1103版</name> <actionType>0</actionType> <startDt>20130228</startDt> <endDt>20140301</endDt> </confirmPPInfo> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>BJ_10020</staffCode> </request>";
//		String response = WebServiceAxisClient.webServicePost(request, "confirmContinueOrderPPInfo", order);
//		System.out.print(response);
//	}
}
