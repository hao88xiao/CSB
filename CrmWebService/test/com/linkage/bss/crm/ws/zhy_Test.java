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
//		//�����̲�ѯ�ӿ�
//		String response = agentService
//				.queryAgentInfo("<request><checkStaffCd>1004320797</checkStaffCd><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testLogin() {
//		//����У��
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
//		//�ͻ������в�Ʒ��ѯ
//		String response = orderService
//				.qryProd("<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCancelOrder() {
//		//����ȡ��
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
//		//Iccid�ŷ������߿����Ʒ����
//		String response = orderService
//				.queryNbrByIccid("<request><serialNum>8986030981010484943</serialNum><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCreateCust() {
//		//�ͻ���Ϣ����
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
//		request.append("<custAddr>").append("�����𺣹���2").append("</custAddr>");
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
//		//�ͻ���Ϣ��ѯ
//		String response = customerService
//				.qryCust("<request><selType>1</selType><value>robinv11</value><identifyType></identifyType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryService() {
//		//��ѯ��Ϣ  ����ѯ  ��ӵĲ�ѯ
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
//		//��ѯ����ӿ�
//		//��ѯ����Ű�������Щ����ҵ��
//		String response = orderService
//				.queryOperation("<request><accNbr>13366497575</accNbr><accNbrType>2</accNbrType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryOffering2pp() {
//		//����Ʒ��ѯ�ӿ�
//		String response = orderService
//				.queryOffering2pp("<request><accNbr>13381231332</accNbr><accNbrType>1</accNbrType><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryAcctInfo() {
//		//�˻���Ϣ��ѯ
//		//��ѯ�˻�ʵ����Ϣ
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
//		//�жϿͻ�����Ĳ�Ʒ�������˿ͻ���
//		String response = orderService
//				.checkPartyProd("<request><partyId>103005065279</partyId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testInitStaticData() {
//		//��ʼ�������ӿ�
//		String response = agentService
//				.initStaticData("<request><id>1,2,3</id><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCheckPassword() {
//		//����У��
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
//		String response = customerService.modifyCustom("<request> <custInfo> <partyId>200000371806</partyId> <custName>�������Ų���ʹ��</custName> <custType>1</custType> <postCode>10000</postCode> </custInfo> <areaId>10000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void isYKSXInfo() {
//		//һ��˫о��ѯ
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
//		//���Ų�ѯ
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
//		//4.37	Ӫҵ��λ�ò�ѯ
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<areaCode>0453</areaCode>");
//		request.append("<queryType>1</queryType>");
//		request.append("<areaName>ĵ�����������������������ֻ�ר����</areaName>");
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
//		//���ư���ѯ�ӿ�
//		String response = orderService
//				.queryPpInfo("<request><accNbr>15321356666</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>null</channelId><staffCode>B01681</staffCode><areaCode>010</areaCode><staffId>104004241434</staffId></request>");
//		//<request> <accNbr>13366497575</accNbr> <accNbrType>1</accNbrType> <areaId>11000</areaId> <channelId>11040754</channelId> <staffCode>bj1001</staffCode></request>
//		//PAD:<request><offerSpecId>900565</offerSpecId> <prodSpecId>378</prodSpecId> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode></request>
//		System.out.println(response);
//	}
//
//	@Test
//	public void testNewValidateService() {
//		//����֤�ӿ�
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
//		//�������ΰ����ѯ
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
//		//��������Ĳ�Ʒ��ѯ�ӿ�
//		String response = orderService
//				.queryProdByCompProdSpecId("<request> <partyId>103005069643</partyId> <prodSpecId>378</prodSpecId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId> </request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void compProdRule() {
//		//�������Ʒ��У��ӿ�
//		String response = orderService
//				.compProdRule("<request><partyId>104003067856</partyId><accNum>15321139743</accNum>	<offerId>100106334236</offerId><prodSpecId>378</prodSpecId><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryImsiInfoByMdn() {
//		//���ݺ����imsi����Ϣ
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
//		//����imsi��ѯ��Ʒ��Ϣ�ӿ�
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
//		//��ѡ������Ʒ��ѯ
//		//��ѯһ��������Ʒ���Ա����Щ������Ʒ
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
//		//�ʷ�Ҫ����Ʒ��ѯ
//		//��������Ʒ���id�Ͳ�Ʒ���id����ѯ�����и�����Ʒ��ѡ������Ʒ��Ϣ
//		String response = orderService
//				.queryCouponInfoByPriceplan("<request><offerSpecId>25100</offerSpecId><prodSpecId>378</prodSpecId><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testHighFeeQueryUserInfo() {
//		//�߶���ϵͳ��CRMϵͳ��ѯ�ӿ�
//		//�߶���ϵͳ��CRMϵͳ��ѯ�ӿ�
//		String response = orderService
//				.highFeeQueryUserInfo("<request><acc_nbr>460030900236077</acc_nbr><flag>2</flag><channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryFNSInfo() {
//		//��������ѯ
//		String response = orderService
//				.queryFNSInfo("<request> <accNbr>13311037781</accNbr> <accNbrType>3</accNbrType> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> <areaId>10000</areaId> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testIfImportantPartyByPartyId() {
//		//�ж��Ƿ��ر��ͻ�
//		String response = orderService
//				.ifImportantPartyByPartyId("<request><partyId>103005069134</partyId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryCardinfoByAcct() {
//		//֤�������ѯ�ӿ�
//		String response = orderService
//				.QueryCardinfoByAcct("<request><AccountID>18910329785</AccountID></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testCheckResultIn() {
//		//��װ����ͨ�ӿ�
//		String response = customerService
//				.checkResultIn("<request><name>�����</name><identifyValue>110101194201201024</identifyValue><channelId>11040361</channelId><staffId>1004320797</staffId></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testConfirmContinueOrderPPInfo () {
//		//���߿�������û�����ȷ�Ͻӿ�
//		
////		String response = orderService
////				.confirmContinueOrderPPInfo("<request> <accNbr>18911039334</accNbr> <accNbrType>1</accNbrType> <confirmPPInfo> <id>24203</id> <name>(24203)3G-150Ԫ����1103��</name> <startDt>20150101</startDt> <endDt>20160101</endDt> </confirmPPInfo> <areaId>11000</areaId> <channelId>11040361</channelId> <staffCode>BJ1001</staffCode> </request>");
//		String response = orderService
//		.confirmContinueOrderPPInfo("<request><accNbr>15321349834</accNbr><accNbrType>1</accNbrType><confirmPPInfo><id>24305</id><name>null</name><startDt>20130601</startDt><endDt>20140630</endDt><actionType>0</actionType></confirmPPInfo><areaId>11000</areaId><channelId>-10020</channelId><staffCode>BJ_10020</staffCode></request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryAccNBRInfo() {
//		//��������ز�ѯ
//		String response = orderService
//				.queryAccNBRInfo("<request><accNbr>13001006820</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>1</channelId><staffCode>BJ_10021</staffCode><areaCode>010</areaCode><staffId>-10021</staffId></request>");
//		System.out.println(response);
//	}
//	
//	
//	@Test
//	public void testQueryInventoryStatistics() {
//		//������ӿ�
//		String response = orderService
//				.queryInventoryStatistics("<request> <storeId>200100101,200100089</storeId> <materialId>12590,12146</materialId>	 <areaId>10000</areaId> <channelId>11040361</channelId> <staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryBcdCode() {
//		//���ô����ѯ�ӿ�
//		String response = orderService
//				.queryBcdCode("<request> 	<storeId>200100101,200100089</storeId> 	<materialId>12590</materialId>	 	<count>1</count> 	<areaId>10000</areaId> 	<channelId>11040361</channelId> 	<staffCode>bj1001</staffCode> </request>");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testBankFreeze() {
//		//���ж���ӿ�
//		String response = orderService
//				.bankFreeze("<request> 	<nodeInfo> 		<partyName>1</partyName> 		<identifyType>2</identifyType> 		<identifyNumber>3</identifyNumber> 		<freezeNo>1</freezeNo> 		<freezeAcctNo>5</freezeAcctNo> 		<freezeSubAcctNo>6</freezeSubAcctNo> 		<freezeMoney>7</freezeMoney> 		<freezeDate>20121212</freezeDate> 		<unfreezeDate>20121212</unfreezeDate> 		<serialNumber>10</serialNumber> 		<preAccessNumber>11</preAccessNumber> 		<systemDate>20121212</systemDate> 	</nodeInfo> 	<actionType>1</actionType> 	<bankCode>bank002</bankCode> 	<bankName>ũ������</bankName> </request> ");
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testQueryContinueOrderPPInfo(){
//		//���߿�����Ѳ�ѯ
//		String response = orderService
//		.queryContinueOrderPPInfo("<request> 	<accNbr>18910835156</accNbr> 	<accNbrType>1</accNbrType> 	<areaId>11000</areaId> 	<channelId>11040361</channelId> 	<staffCode>BJ1001</staffCode> </request> ");
//		System.out.println(response);
//	}
//	
//	
	
	
	
}
