package com.linkage.bss.crm.ws;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.crm.ws.service.AgentService;
import com.linkage.bss.crm.ws.service.CustomerService;
import com.linkage.bss.crm.ws.service.OrderService;
import com.linkage.bss.crm.ws.service.RscService;
import com.linkage.bss.crm.ws.service.SRService;

public class CustomerServiceTest extends BaseTest {

//	@Autowired
//	@Qualifier("customerService")
//	private CustomerService customerService;
//
//	@Autowired
//	@Qualifier("orderService")
//	private OrderService orderService;
//
//	@Autowired
//	@Qualifier("srService")
//	private SRService srService;
//
//	@Autowired
//	@Qualifier("rscService")
//	private RscService rscService;
//
//	@Autowired
//	@Qualifier("agentService")
//	private AgentService agentService;

//	@Test
//	public void testDemo() {
//		String response = customerService
//				.demo("<request><a></a><staffCode>al1001</staffCode><channelId>123123</channelId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCreateCust() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<custInfo>");
//		request.append("<custName>").append("方天畫戟").append("</custName>");
//		request.append("<custSimple>").append("ZFS").append("</custSimple>");
//		request.append("<custType>").append("1").append("</custType>");
//		request.append("<areaCode>").append("0451").append("</areaCode>");
//		request.append("<cerdAddr>").append("张飞的证件地址").append("</cerdAddr>");
//		request.append("<cerdType>").append("2").append("</cerdType>");
//		request.append("<cerdValue>").append("654321").append("</cerdValue>");
//		request.append("<contactPhone1>").append("13111111111").append("</contactPhone1>");
//		request.append("<contactPhone2>").append("13222222222").append("</contactPhone2>");
//		request.append("<custBusinessPwd>").append("111111").append("</custBusinessPwd>");
//		request.append("<custQueryPwd>").append("222222").append("</custQueryPwd>");
//		request.append("<postCode>").append("100086").append("</postCode>");
//		request.append("<custAddr>").append("北京大郊亭桥").append("</custAddr>");
//		request.append("</custInfo>");
//		request.append("<areaCode>0451</areaCode>");
//		request.append("<channelId>51000000</channelId>");
//		request.append("<staffCode>al1001</staffCode>");
//		request.append("</request>");
//
//		String response = customerService.createCust(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testGenerateCoNbr() {
//		String response = orderService
//				.generateCoNbr("<request><count>2</count><channelId>5100000</channelId><staffCode>1001</staffCode></request>");
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <coNbrList>
//		// <coNbr>510000254063</coNbr>
//		// <coNbr>510000254064</coNbr>
//		// </coNbrList>
//		// </response>
//		// 通过
//	}
//
//	@Test
//	public void testIsInCustBlackList() {
//		String response = customerService
//				.blackUserCheck("<request><checkId>2</checkId><typeCode>3213213</typeCode><channelId>5100000</channelId><staffCode>1001</staffCode></request>");
//		System.out.println(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>是</resultMsg>
//		// <resultSign>1</resultSign>
//		// </response>
//		// 通过
//	}
//
//	@Test
//	public void testQueryCustGrade() {
//		String response = customerService
//				.qryUserVipType("<request><staffCode>1001</staffCode><channelId>5100000</channelId><accNbr>13351204432</accNbr></request>");
//		System.out.printf(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <userInfos><vipType>普通</vipType></userInfos>
//		// </response>
//		// 通过
//	}
//
//	@Test
//	public void testQryPrepareOrder() {
//		String response = orderService
//				.getPreInterimBycond("<request><staffCode>1001</staffCode><accessNumber></accessNumber><identifyType>10</identifyType><identityNum>11111</identityNum><startTime></startTime><endTime></endTime><channelId>210000</channelId></request>");
//		System.out.printf(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <preOrders>
//		// <preOrder>
//		// <olId>510,000,230,124</olId>
//		// <partyName>cs1</partyName>
//		// <channelId>210,000</channelId>
//		// <partyId>513,005,061,249</partyId>
//		// <statusCd>2012-03-12 14:11:03</statusCd>
//		// <dt>2012-03-12 14:11:03</dt>
//		// <relaOlnbr></relaOlnbr>
//		// <preType>2</preType>
//		// <areaId>45,101</areaId>
//		// <relaOlId></relaOlId>
//		// <channelName>测试环境补渠道</channelName>
//		// <staffId>1,001</staffId>
//		// <areaName>CRM研发部</areaName>
//		// <statusCd>PW</statusCd>
//		// <staffName>黑龙江</staffName>
//		// <delDt>2012-3-13 14:10:0</delDt>
//		// <olNbr>510000230802</olNbr>
//		// <olTypeCd>5</olTypeCd>
//		// <statusName>预受理待转正</statusName>
//		// </preOrder>
//		// </preOrders>
//		// </response>
//		// 通过
//	}
//
//	@Test
//	public void testCheckUimNo() {
//		String response = rscService
//				.checkUimNo("<request><uimNo>002500000002</uimNo><!-- ISMI卡号 --><prodSpecId>10302057</prodSpecId><channelId>51000000</channelId><staffCode>1001</staffCode></request>");
//		System.out.printf(response);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><terminalDevice><accessNumbers><accessNumber><resulte>-1</resulte><cause>终端串号(卡号)无效,请确认输入正确</cause></accessNumber></accessNumbers></terminalDevice></response>
//		// 通过
//	}
//
////	@Test
////	public void testAllocAuditTickets() {
////		// <request>
////		// <auditTicketId>510000304944</auditTicketId>
////		// <channelId>510000</channelId>
////		// <staffCode>1001</staffCode>
////		// <campType>exchangeTicket</campType><!--类型有exchangeTicket-convertTicket-->
////		// </request>
////		String s = "<request><auditTicketId>510000209103</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode><campType>exchangeTicket</campType></request>";
////		String result = srService.allocAuditTickets(s);
////		System.out.print(result);
////		// <response><resultCode>0</resultCode><resultmsg>成功</resultMsg></response>
////		// 通过
////	}
//
////	@Test
////	public void testReleaseAuditTickets() {
////		String s = "<request><auditTicketId>510000209103</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode><campType>exchangeTicket</campType></request>";
////		String result = srService.releaseAuditTickets(s);
////		System.out.print(result);
////		// <response>
////		// <resultCode>0</resultCode>
////		// <resultMsg>
////		// 成功
////		// </resultMsg>
////		// </response>
////		// 通过
////	}
//
////	@Test
////	public void testQueryAuditTicket() {
////		String request = "<request><ticketCd></ticketCd><couponInstanceNumber></couponInstanceNumber><accessNumber></accessNumber><staffCode>1001</staffCode><channelId>5100000</channelId><startDate></startDate><endDate></endDate></request>";
////		String s = srService.queryAuditTicket(request);
////		System.out.print(s);
////		// <response>
////		// <resultCode>0</resultCode>
////		// <resultMsg>成功</resultMsg>
////		// <ticketList>
////		// <ticketInfo>
////		// <auditTicketName>终端抵用券</auditTicketName>
////		// <auditTicketCd>6720110720W3TV2A304159</auditTicketCd>
////		// <auditTicketId>670,000,304,158</auditTicketId>
////		// <auditTicketType>4</auditTicketType>
////		// <areaId>467</areaId>
////		// <price>654</price>
////		// <createDt>2011-07-20</createDt>
////		// <startDt>2011-07-20</startDt>
////		// <endDt>2011-08-20</endDt>
////		// <statusCd>6</statusCd>
////		// <statusName>已使用</statusName>
////		// <boId></boId>
////		// <partyId></partyId>
////		// <partyName></partyName>
////		// <channelId></channelId>
////		// <staffId></staffId>
////		// <staffName></staffName>
////		// <channelName></channelName>
////		// <accessNumber></accessNumber>
////		// <offerSpecId></offerSpecId>
////		// <offerId></offerId>
////		// <offerName></offerName>
////		// <ruleCfgId></ruleCfgId>
////		// <bcdCode>A0000013BC83A6</bcdCode>
////		// <materialName>华为C2906</materialName>
////		// <operationType>抵用券兑换</operationType>
////		// </ticketInfo>
////		// </ticketInfos>
////		// </response>
////		// 通过
////	}
//
////	@Test
////	public void testValidateAuditTicket() {
////		String request = "<request><credenceNo>5120120315Z6UF2Y304385</credenceNo><accessNumber>13349316296</accessNumber><staffCode>1001</staffCode><channelId>5100000</channelId></request>";
////		String result = srService.validateAuditTicket(request);
////		System.out.print(result);
////		// <response>
////		// <resultCode>S0001</resultCode>
////		// <resultMsg>根据抵用券编号未能找到券，请确认券编号！</resultMsg>
////		// </response>
////		// 通过
////	}
//
////	@Test
////	public void testExchangeAuditTickets() {
////		String request = "<request><auditTicketId>510000304944</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode></request>";
////		String result = srService.exchangeAuditTickets(request);
////		System.out.print(result);
////		// <response>
////		// <resultCode>0</resultCode>
////		// <resultMsg>成功</resultMsg>
////		// </response>
////		// 通过
////
////	}
//
//	@Test
//	public void testQueryOrderListInfo() {
//		String request = "<request><curPage>1</curPage><pageSize>20</pageSize><coNbr></coNbr><accNbr></accNbr><startDate></startDate><endDate></endDate><transfer></transfer><channelId>51000000</channelId><staffCode>1001</staffCode></request>";
//		String result = orderService.queryOrderListInfo(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <custOrderList>
//		// <custOrder>
//		// <custOrderId>510,000,251,854</custOrderId>
//		// <olNbr>510000252501</olNbr>
//		// </custOrder>
//		// <custOrder>
//		// <custOrderId>510,000,251,866</custOrderId>
//		// <olNbr>510000252513</olNbr>
//		// </custOrder>
//		// <custOrder>
//		// <custOrderId>510,000,251,875</custOrderId>
//		// <olNbr>510000252522</olNbr>
//		// </custOrder>
//		// <custOrder>
//		// <custOrderId>510,000,251,884</custOrderId>
//		// <olNbr>510000252531</olNbr>
//		// </custOrder>
//		// <custOrder>
//		// </custOrderList>
//		// </response>
//		// 通过
//
//	}
//
//	@Test
//	public void testCommitPreOrderInfo() {
//		String request = "<request><olId>50000000000003269531</olId><staffCode>1001</staffCode><channelId>51000000</channelId><orderStatus>PW</orderStatus><olTypeCd>PW</olTypeCd><preOrderType>1</preOrderType></request>";
//		String result = orderService.commitPreOrderInfo(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>B0007</resultCode>
//		// <resultMsg>预受理单转正失败</resultMsg>
//		// </response>
//	}
//
//	@Test
//	public void testReleaseCartByOlIdForPrepare() {
//		String request = "<request><olId>510000229653</olId><channelId>51000000</channelId><staffCode>1001</staffCode></request>";
//		String result = orderService.releaseCartByOlIdForPrepare(request);
//		System.out.print(result);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//		// 通过
//
//	}
//
//	@Test
//	public void testCreateUserAddr() {
//		String request = "<request><addrName>陈腾飞</addrName><channelId>51000000</channelId><staffCode>1001</staffCode></request>";
//		String result = rscService.createUserAddr(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <resultInfo>
//		// <addrId>122,776,089,024</addrId>
//		// <addrName>陈腾飞</addrName>
//		// </resultInfo>
//		// </response>
//	}
//
//	@Test
//	public void testQueryTml() {
//		String request = "<request><areaId>452</areaId><deviceCode></deviceCode><tmlName>克东交换局1327C</tmlName><tmlManageCd>5224-76_76编号002</tmlManageCd><tmlTypeCd>1</tmlTypeCd><channelId>51002995</channelId><staffCode>1001</staffCode></request>";
//		String result = rscService.queryTml(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <tmls>
//		// <tml>
//		// <tmlId>522476</tmlId>
//		// <tmlTypeCd>1</tmlTypeCd>
//		// <areaId>452</areaId>
//		// <manageCd>5224-76_76编号002</manageCd>
//		// <name>克东交换局1327C</name>
//		// <description>克东交换局1327C-76_76编号002</description>
//		// <createDt>Mon Apr 18 00:00:00 CST 2011</createDt>
//		// <expDt>Wed Jan 01 00:00:00 CST 3000</expDt>
//		// <version>Mon Apr 18 00:00:00 CST 2011</version>
//		// </tml>
//		// </tmls>
//		// </response>
//	}
//
//	@Test
//	public void testQueryPhoneNumberList() {
//		String request = "<request><tmlId></tmlId><anTypeCd>103</anTypeCd><numberMid></numberMid><poolId></poolId><pnLevelId></pnLevelId><numberHead></numberHead><numberTail></numberTail><numberCnt></numberCnt><channelId>51003012</channelId><staffCode>1001</staffCode></request>";
//		String result = rscService.queryPhoneNumberList(request);
//		System.out.print(result);
//		// <response><resultCode>B0011</resultCode><resultMsg>调用第3方接口返回信息为空</resultMsg></response>
//	}
//
//	@Test
//	public void testGetUimCardInfo() {
//		String response = rscService
//				.getUimCardInfo("<request><cardNo></cardNo><imsi>460030914578649</imsi><channelId>54003521</channelId><staffCode>AL1001</staffCode><areaCode>45001</areaCode></request>");
//		System.out.println(response);
////		{HLR_CODE=4512, HRPD_SS=null, TERMINAL_DEV_ID=100134883, HRPD_UPP=null, TERMINAL_CODE=8986030731457004573, ISEVDO=null, DEV_MODEL_NAME=后付费UTK卡-32K卡
////			, C_IMSI=460030914578649, UIMID=830F6840, G_AKEY=null, PIN2=16451677, ICCID=8986030731457004573, PIN1=1234, CAUSE=返回成功, RESULT=1, STORE_NAME=null, G_IMSI=null, PUK2=49942965, ESN=830F6840, PUK1=35293049, IMSI_STATE=28, C_AKEY=CC3A57655D638265}
//	}
//
//	@Test
//	public void testQueryPhoneNumberPoolList() {
//		String request = "<request><areaCode>452</areaCode><prodSpecId>379</prodSpecId><preflag></preflag><tmlId></tmlId><anTypeCd>103</anTypeCd><channelId>51002995</channelId><staffCode>1001</staffCode></request>";
//		String result = rscService.queryPhoneNumberPoolList(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <poolInfos>
//		// <poolInfo>
//		// <poolId>-1</poolId>
//		// <name>-----所有号池选号----</name>
//		// <description></description>
//		// </poolInfo>
//		// <poolInfo>
//		// <poolId>510,029,952</poolId>
//		// <name>延寿县永恒手机维修部_CDMA号池</name>
//		// <description>延寿县永恒手机维修部_CDMA号池</description>
//		// </poolInfo>
//		// </poolInfos>
//		// </response>
//	}
//
//	@Test
//	public void testQueryPreOrderDetail() {
//		String result = orderService
//				.queryPreOrderDetail("<request><channelId>5100000</channelId><staffCode>1001</staffCode><boId>510032740749</boId><user>so</user></request>");
//		System.out.print(result);
//	}
//
//	@Test
//	public void testQryCust() {
//		String response = customerService
//				.qryCust("<request><staffid>1001</staffid><SelType>1</SelType><value>test</value><identifyType>1</identifyType><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryService() {
//		String response = orderService
//				.queryService("<request><accNbr>13351204432</accNbr><accNbrType></accNbrType><areaCode></areaCode><channelId></channelId><staffCode></staffCode><queryType></queryType><queryMode></queryMode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryOperation() {
//		String response = orderService
//				.queryOperation("<request><accNbr>13351204432</accNbr><accNbrType>1</accNbrType><areaCode>45101</areaCode><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryOffering2pp() {
//		String response = orderService
//				.queryOffering2pp("<request><accNbr>13351204432</accNbr><accNbrType>1</accNbrType><areaCode>45101</areaCode><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryAcctInfo() {
//		String response = orderService
//				.qryAcctInfo("<request><partyId></partyId><prodId>513030032332</prodId><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
////	@Test
////	public void testQryPricePlanService() {
////		String response = orderService
////				.qryPricePlanService("<request><prodId>513030032201</prodId><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
////		System.out.println(response);
////	}
//
//	@Test
//	public void testCheckPartyProd() {
//		String response = orderService
//				.checkPartyProd("<request><partyId>513005063694</partyId><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQuerySerivceAcct() {
//		String request = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><linkMan>王飞</linkMan><curPage>1</curPage><pageSize>10</pageSize></request>";
//		String result = orderService.querySerivceAcct(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testAddSerivceAcct() {
//		String request = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>1001</staffCode><serviceName>qwe</serviceName><managerId>123</managerId><linkMan>123</linkMan><linkNbr>123</linkNbr><servicePartyId>513005063694</servicePartyId><buildingId>1</buildingId><buildingType>1</buildingType></request>";
//		String result = orderService.addSerivceAcct(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testInitStaticData() {
//		String response = agentService
//				.initStaticData("<request><id>1,2,3</id><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryOfferModel() {
//		String request = "<request><channelId>5100000</channelId><staffCode>1001</staffCode><padType>1</padType></request>";
//		String result = orderService.qryOfferModel(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testCheckCampusUser() {
//		String request = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accessNumber>13329412856</accessNumber></request>";
//		String result = customerService.checkCampusUser(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testCheckPassword() {
//		String request = "<request><accNbr>13329413052</accNbr><accNbrType>1</accNbrType><password>11111</password><passwordType>5</passwordType><channelId>5100000</channelId><staffCode>1001</staffCode></request>";
//		String result = customerService.checkPassword(request);
//		System.out.println(result);
//	}
//
//	@Test
//	public void testGetCustAdInfo() {
//		String request = "<request><channelId>5100000</channelId><staffCode>1001</staffCode><accessNumber>11981987</accessNumber></request>";
//		String result = customerService.getCustAdInfo(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void checkBindAccessNumber() {
//		String request = "<request><channelId>5100000</channelId><staffCode>1001</staffCode><accessNumber>53932468</accessNumber><areaId>45101</areaId><proSpecId>2</proSpecId><type>3</type><partyId>513005063694</partyId></request>";
//		String result = orderService.checkBindAccessNumber(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testChangePassword() {
//		String request = "<request><accNbr>18901395401</accNbr><accNbrType>4</accNbrType><old_password>000000</old_password><new_password>222222</new_password><passwordTime>30000101</passwordTime><passwordType>1</passwordType><areaId>11000</areaId><channelId>5100000</channelId><staffCode>bj1001</staffCode></request>";
//		String result = customerService.changePassword(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testResetPassword() {
//		String request = "<request><accNbr>15301391751</accNbr><accNbrType>4</accNbrType><password>222222</password><passwordTime>20120801</passwordTime><passwordType>2</passwordType><areaId>11000</areaId><channelId>5100000</channelId><staffCode>bj1001</staffCode></request>";
//		String result = customerService.resetPassword(request);
//		System.out.print(result);
//	}
//	
//	@Test
//	public void testModifyCustom() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<custInfo>");
//		request.append("<partyId>").append("513005065721").append("</partyId>");
//		request.append("<custName>").append("张飞六").append("</custName>");
//		request.append("<custSimple>").append("ZFS").append("</custSimple>");
//		request.append("<custType>").append("1").append("</custType>");
//		request.append("<areaCode>").append("0451").append("</areaCode>");
//		request.append("<cerdAddr>").append("张飞的证件地址").append("</cerdAddr>");
//		request.append("<cerdType>").append("2").append("</cerdType>");
//		request.append("<cerdValue>").append("888888").append("</cerdValue>");
//		request.append("<contactPhone1>").append("13111111111").append("</contactPhone1>");
//		request.append("<contactPhone2>").append("13222222222").append("</contactPhone2>");
//		request.append("<custBusinessPwd>").append("111111").append("</custBusinessPwd>");
//		request.append("<custQueryPwd>").append("222222").append("</custQueryPwd>");
//		request.append("<postCode>").append("100086").append("</postCode>");
//		request.append("<custAddr>").append("北京大郊亭桥金海国际").append("</custAddr>");
//		request.append("</custInfo>");
//		request.append("<areaCode>0451</areaCode>");
//		request.append("<channelId>51000000</channelId>");
//		request.append("<staffCode>al1001</staffCode>");
//		request.append("</request>");
//
//		String response = customerService.modifyCustom(request.toString());
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testGetClerkId() {
//		String request = "<request><accNbr>13370161688</accNbr><channelId>51000000</channelId><staffCode>1001</staffCode><areaId>45101</areaId></request>";
//		String result = rscService.getClerkId(request);
//		System.out.print(result);
//	}
//	
//	@Test
//	public void testAddReceiptPringLog() {
//		String request = "<request><coNbr>123</coNbr><flag>2</flag><channelId>51000000</channelId><staffCode>1001</staffCode><areaId>45101</areaId></request>";
//		String result = rscService.addReceiptPringLog(request);
//		System.out.print(result);
//	}
//	
////	@Test
////	public void testCreateBroadbandAccount() {
////		String request = "<request><prodSpecId>9</prodSpecId><intfType>1</intfType><nwkInfoTD>1</nwkInfoTD><loginName>789012345682</loginName><speedValue>4</speedValue><connectType>3</connectType><areaId>45101</areaId><channelId>51000000</channelId><staffCode>BJ1001</staffCode><custId>200003582215</custId><ppStr>22677</ppStr></request>";
////		String result = rscService.createBroadbandAccount(request);
////		System.out.print(result);
////	}
////	
////	@Test
////	public void testBroadBandCancel() {
////		String request = "<request><prodSpecId>9</prodSpecId><intfType>3</intfType><areaId>45101</areaId><channelId>51000000</channelId><staffCode>BJ1001</staffCode><pgLanAccount>B22234426</pgLanAccount><anId>120052943974</anId></request>";
////		String result = rscService.createBroadbandAccount(request);
////		System.out.print(result);
////	}
////	
////	@Test
////	public void testPasswordReset() {
////		String request = "<request><prodSpecId>9</prodSpecId><intfType>2</intfType><loginName>B22862861</loginName><areaId>45101</areaId><channelId>51000000</channelId><staffCode>BJ1001</staffCode></request>";
////		String result = rscService.createBroadbandAccount(request);
////		System.out.print(result);
////	}
}
