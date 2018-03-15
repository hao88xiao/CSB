package com.linkage.bss.crm.ws;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.crm.ws.service.AgentService;
import com.linkage.bss.crm.ws.service.CustomerService;
import com.linkage.bss.crm.ws.service.OrderService;

public class zhy_Test extends BaseTest {

//	@Autowired
//	@Qualifier("customerService")
//	private CustomerService customerService;
//
//	@Autowired
//	@Qualifier("orderService")
//	private OrderService orderService;
//
//	@Autowired
//	@Qualifier("agentService")
//	private AgentService agentService;
//
//	@Test
//	public void testQueryAgentInfo() {
//		//代理商查询接口
//		String response = agentService
//				.queryAgentInfo("<request><checkStaffCd>1004320797</checkStaffCd><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testLogin() {
//		//登入校验
//		String response = agentService
//				.login(" <request> <passwd>888888</passwd> <staffNumber>P0016701</staffNumber> </request> ");
//		System.out.println(response);
//	}
//
//	// @Test
//	// public void testQueryCouponInfoByPriceplan() {
//	// String response = orderService
//	// .queryCouponInfoByPriceplan("<request><pricePlanCd>24199</pricePlanCd><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//	// System.out.println(response);
//	// }
//
//	@Test
//	public void testQryProd() {
//		//客户下已有产品查询
//		String response = orderService
//				.qryProd("<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCancelOrder() {
//		//订单取消
//		String response = orderService
//				.cancelOrder("<request><olId>100000454432</olId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testNewAcct() {
//		// TODO
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<partyId>103005065279</partyId>");
//		request.append("<areaId>11000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//		String response = orderService.newAcct(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryNbrByIccid() {
//		//Iccid号反查无线宽带产品号码
//		String response = orderService
//				.queryNbrByIccid("<request><serialNum>8986030981010484943</serialNum><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCreateCust() {
//		//客户信息新增
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<custInfo>");
//		request.append("<custName>").append("robinv11").append("</custName>");
//		request.append("<custType>").append("1").append("</custType>");
//		request.append("<areaCode>").append("").append("</areaCode>");
//		request.append("<cerdAddr>").append("Las Vegas").append("</cerdAddr>");
//		request.append("<cerdType>").append("2").append("</cerdType>");
//		request.append("<cerdValue>").append("54321").append("</cerdValue>");
//		request.append("<contactPhone1>").append("18181818181").append("</contactPhone1>");
//		request.append("<emailAddr>").append("qwe@robin").append("</emailAddr>");
//		request.append("<custAddr>").append("北京金海国际2").append("</custAddr>");
//		request.append("<linkMan>").append("robin").append("</linkMan>");
//		request.append("</custInfo>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = customerService.createCust(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryCust() {
//		//客户信息查询
//		String response = customerService
//				.qryCust("<request><selType>1</selType><value>robinv11</value><identifyType></identifyType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryService() {
//		//查询信息  主查询  最复杂的查询
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<accNbr>58883880</accNbr>");
//		request.append("<accNbrType>1</accNbrType>");
//		request.append("<queryType>1,2,3,4</queryType>");
//		request.append("<queryMode>2</queryMode>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//		
//		String requestTest = "<request> <accNbr>2010525563280000</accNbr>  <accNbrType>13</accNbrType>  <queryType>2,3</queryType>  <queryMode>2</queryMode>   <areaId>11000</areaId>  <channelId>-10020</channelId>  <staffCode>BJ_10020</staffCode>  </request>";
//		
//		String requestTest1 = "<request>  <accNbr>18910842056</accNbr>   <accNbrType>1</accNbrType>   <queryType>1,2</queryType>   <queryMode>2</queryMode>   <areaId>11000</areaId>   <channelId>-10021</channelId>   <staffCode>BJ_10021</staffCode>   </request>";
//		
//		String response = orderService.queryService(requestTest);
////		String response = orderService.queryService(request.toString());
//		System.out.println(response);
//		
//		
//	}
//	@Test
//	public void testQueryOperation() {
//		//查询服务接口
//		//查询接入号办理了哪些基础业务
//		String response = orderService
//				.queryOperation("<request><accNbr>13366497575</accNbr><accNbrType>2</accNbrType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryOffering2pp() {
//		//销售品查询接口
//		String response = orderService
//				.queryOffering2pp("<request><accNbr>13381231332</accNbr><accNbrType>1</accNbrType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryAcctInfo() {
//		//账户信息查询
//		//查询账户实例信息
//		String response = orderService
//				.qryAcctInfo("<request><partyId>103005067949</partyId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId></areaId></request>");
//		System.out.println(response);
//	}
//
////	@Test
////	public void testQryPricePlanService() {
////		String response = orderService
////				.qryPricePlanService("<request><prodId>513030032201</prodId><areaId>0451</areaId><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
////		System.out.println(response);
////	}
//
//	@Test
//	public void testCheckPartyProd() {
//		//判断客户办理的产品数、个人客户等
//		String response = orderService
//				.checkPartyProd("<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testInitStaticData() {
//		//初始化参数接口
//		String response = agentService
//				.initStaticData("<request><id>1,2,3</id><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCheckPassword() {
//		//密码校验
//		String request = "<request><accNbr>59300125</accNbr><accNbrType>2</accNbrType><password>000000</password><passwordType>2</passwordType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>";
//		String result = customerService.checkPassword(request);
//		System.out.println(result);
//	}
//
//	@Test
//	public void testModifyCustom() {
////		StringBuilder request = new StringBuilder();
////		request.append("<request>");
////		request.append("<custInfo>");
////		request.append("<partyId>").append("103005072539").append("</partyId>");
////		request.append("<custName>").append("robinv5").append("</custName>");
////		request.append("<custType>").append("1").append("</custType>");
////		request.append("<areaCode>").append("010").append("</areaCode>");
////		request.append("<cerdAddr>").append("").append("</cerdAddr>");
////		request.append("<cerdType>").append("").append("</cerdType>");
////		request.append("<cerdValue>").append("").append("</cerdValue>");
////		request.append("<contactPhone1>").append("").append("</contactPhone1>");
////		request.append("<custLevel>").append("").append("</custLevel>");
////		request.append("<custBusinessPwd>").append("").append("</custBusinessPwd>");
////		request.append("<custQueryPwd>").append("").append("</custQueryPwd>");
////		request.append("<emailAddr>").append("").append("</emailAddr>");
////		request.append("<linkMan>").append("").append("</linkMan>");
////		request.append("<postCode>").append("").append("</postCode>");
////		request.append("<custAddr>").append("").append("</custAddr>");
////		request.append("<creditsAccNbr>").append("-1").append("</creditsAccNbr>");
////		request.append("</custInfo>");
////		request.append("<areaId>10000</areaId>");
////		request.append("<channelId>11040361</channelId>");
////		request.append("<staffCode>bj1001</staffCode>");
////		request.append("</request>");
////
////		String response = customerService.modifyCustom(request.toString());
//		
//		//
//		String response = customerService.modifyCustom("<request> <custInfo> <partyId>200000371806</partyId> <custName>北京电信测试使用</custName> <custType>1</custType> <postCode>10000</postCode> </custInfo> <areaId>10000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void isYKSXInfo() {
//		//一卡双芯查询
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<accNbr>15340117588</accNbr>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//		
//
//		String response = orderService.isYKSXInfo(request.toString());
//		System.out.println(response);
//
//	}
//
//	@Test
//	public void testQueryAreaInfo() {
//		//区号查询
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<areaCode>010</areaCode>");
//		request.append("<areaName>1</areaName>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = orderService.queryAreaInfo(request.toString());
//		System.out.println(response);
//
//	}
//
//	@Test
//	public void testQueryOperatingOfficeInfo() {
//		//4.37	营业厅位置查询
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<areaCode>0453</areaCode>");
//		request.append("<queryType>1</queryType>");
//		request.append("<areaName>牡丹江市阳明区五林镇天翼手机专卖店</areaName>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//
//		request.append("</request>");
//
//		String response = orderService.queryOperatingOfficeInfo(request.toString());
//		System.out.println(response);
//
//	}
//
//	@Test
//	public void testQueryPpInfo() {
//		//定制包查询接口
//		String response = orderService
//				.queryPpInfo("<request><accNbr>15321356666</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>null</channelId><staffCode>B01681</staffCode><areaCode>010</areaCode><staffId>104004241434</staffId></request>");
//		//<request> <accNbr>13366497575</accNbr> <accNbrType>1</accNbrType> <areaId>11000</areaId> <channelId>11040754</channelId> <staffCode>bj1001</staffCode></request>
//		//PAD:<request><offerSpecId>900565</offerSpecId> <prodSpecId>378</prodSpecId> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode></request>
//		System.out.println(response);
//	}
//
//	@Test
//	public void testNewValidateService() {
//		//新验证接口
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<validateInfo>" + "<accessNumber>13366523736</accessNumber>" + "<custName>-9</custName>"
//				+ "<IDCardType>1</IDCardType>" + "<IDCard>3451146167280000</I2DCard>" + "</validateInfo>");
//		request.append("<validateType>9</validateType>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = orderService.newValidateService(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCheckGlobalroam() {
//		//国际漫游办理查询
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<accNbr>13366523736</accNbr>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//		String response = orderService.checkGlobalroam(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void queryProdByCompProdSpecId() {
//		//可以纳入的产品查询接口
//		String response = orderService
//				.queryProdByCompProdSpecId("<request> <partyId>103005069643</partyId> <prodSpecId>378</prodSpecId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId> </request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void compProdRule() {
//		//可纳入产品的校验接口
//		String response = orderService
//				.compProdRule("<request><partyId>104003067856</partyId><accNum>15321139743</accNum>	<offerId>100106334236</offerId><prodSpecId>378</prodSpecId><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryImsiInfoByMdn() {
//		//根据号码查imsi等信息
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<accNbr>15340117588</accNbr>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = orderService.queryImsiInfoByMdn(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryProdinfoByImsi() {
//		//根据imsi查询产品信息接口
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<cImsi>460030900236077</cImsi>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = orderService.queryProdinfoByImsi(request.toString());
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryOptionalOfferList() {
//		//可选主销售品查询
//		//查询一个主销售品可以变更哪些主销售品
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<partyId>104003067856</partyId>");
//		request.append("<categoryNodeId>100041</categoryNodeId>");
//		request.append("<offerSpecId>25000</offerSpecId>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//
//		String response = orderService.queryOptionalOfferList(request.toString());
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryCouponInfoByPriceplan() {
//		//资费要求物品查询
//		//根据销售品规格id和产品规格id，查询出所有该销售品可选购的物品信息
//		String response = orderService
//				.queryCouponInfoByPriceplan("<request><offerSpecId>25100</offerSpecId><prodSpecId>378</prodSpecId><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testHighFeeQueryUserInfo() {
//		//高额监控系统与CRM系统查询接口
//		//高额监控系统与CRM系统查询接口
//		String response = orderService
//				.highFeeQueryUserInfo("<request><acc_nbr>460030900236077</acc_nbr><flag>2</flag><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryFNSInfo() {
//		//亲情号码查询
//		String response = orderService
//				.queryFNSInfo("<request> <accNbr>13311037781</accNbr> <accNbrType>3</accNbrType> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testIfImportantPartyByPartyId() {
//		//判断是否重保客户
//		String response = orderService
//				.ifImportantPartyByPartyId("<request><partyId>103005069134</partyId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryCardinfoByAcct() {
//		//证件号码查询接口
//		String response = orderService
//				.QueryCardinfoByAcct("<request><AccountID>18910329785</AccountID></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testCheckResultIn() {
//		//封装国政通接口
//		String response = customerService
//				.checkResultIn("<request><name>张荣娣</name><identifyValue>110101194201201024</identifyValue><channelId>11040361</channelId><staffId>1004320797</staffId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testConfirmContinueOrderPPInfo () {
//		//无线宽带续费用户续费确认接口
//		
////		String response = orderService
////				.confirmContinueOrderPPInfo("<request> <accNbr>18911039334</accNbr> <accNbrType>1</accNbrType> <confirmPPInfo> <id>24203</id> <name>(24203)3G-150元包年1103版</name> <startDt>20150101</startDt> <endDt>20160101</endDt> </confirmPPInfo> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>BJ1001</staffCode> </request>");
//		String response = orderService
//		.confirmContinueOrderPPInfo("<request><accNbr>15321349834</accNbr><accNbrType>1</accNbrType><confirmPPInfo><id>24305</id><name>null</name><startDt>20130601</startDt><endDt>20140630</endDt><actionType>0</actionType></confirmPPInfo><areaId>11000</areaId><channelId>-10020</channelId><staffCode>BJ_10020</staffCode></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryAccNBRInfo() {
//		//号码归属地查询
//		String response = orderService
//				.queryAccNBRInfo("<request><accNbr>13001006820</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>1</channelId><staffCode>BJ_10021</staffCode><areaCode>010</areaCode><staffId>-10021</staffId></request>");
//		System.out.println(response);
//	}
//	
//	
//	@Test
//	public void testQueryInventoryStatistics() {
//		//库存量接口
//		String response = orderService
//				.queryInventoryStatistics("<request> <storeId>200100101,200100089</storeId> <materialId>12590,12146</materialId>	 <areaId>10000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryBcdCode() {
//		//可用串码查询接口
//		String response = orderService
//				.queryBcdCode("<request> 	<storeId>200100101,200100089</storeId> 	<materialId>12590</materialId>	 	<count>1</count> 	<areaId>10000</areaId> 	<channelId>11040361</channelId> 	<staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testBankFreeze() {
//		//银行冻结接口
//		String response = orderService
//				.bankFreeze("<request> 	<nodeInfo> 		<partyName>1</partyName> 		<identifyType>2</identifyType> 		<identifyNumber>3</identifyNumber> 		<freezeNo>1</freezeNo> 		<freezeAcctNo>5</freezeAcctNo> 		<freezeSubAcctNo>6</freezeSubAcctNo> 		<freezeMoney>7</freezeMoney> 		<freezeDate>20121212</freezeDate> 		<unfreezeDate>20121212</unfreezeDate> 		<serialNumber>10</serialNumber> 		<preAccessNumber>11</preAccessNumber> 		<systemDate>20121212</systemDate> 	</nodeInfo> 	<actionType>1</actionType> 	<bankCode>bank002</bankCode> 	<bankName>农商银行</bankName> </request> ");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryContinueOrderPPInfo(){
//		//无线宽带续费查询
//		String response = orderService
//		.queryContinueOrderPPInfo("<request> 	<accNbr>18910835156</accNbr> 	<accNbrType>1</accNbrType> 	<areaId>11000</areaId> 	<channelId>11040361</channelId> 	<staffCode>BJ1001</staffCode> </request> ");
//		System.out.println(response);
//	}
//	
//	
	
	
	
}
