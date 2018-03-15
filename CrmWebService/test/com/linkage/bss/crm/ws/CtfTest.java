package com.linkage.bss.crm.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bss.common.BssException;
import bss.systemmanager.provide.SmService;

import com.linkage.bss.crm.soservice.syncso.smo.ISoServiceSMO;
import com.linkage.bss.crm.ws.service.AgentService;
import com.linkage.bss.crm.ws.service.CustomerService;
import com.linkage.bss.crm.ws.service.OrderService;
import com.linkage.bss.crm.ws.service.RscService;
import com.linkage.bss.crm.ws.service.SRService;

public class CtfTest extends BaseTest {
//	@Autowired
//	@Qualifier("customerService")
//	private CustomerService customerService;
//
//	@Autowired
//	@Qualifier("orderService")
//	private OrderService orderService;
//
//	@Autowired
//	@Qualifier("rscService")
//	private RscService rscService;
//
//	@Autowired
//	@Qualifier("smServiceHttp")
//	private SmService smService;
//
//	@Autowired
//	@Qualifier("agentService")
//	private AgentService agentService;
//
//	@Autowired
//	@Qualifier("soServiceManager.soServiceSMO")
//	private ISoServiceSMO soServiceSMO;
//	
//	@Autowired
//	@Qualifier("srService")
//	private SRService srService;
//	@Test
//	public void testLYW(){
//		String order = "{\"orderList\":{\"custOrderList\":[{\"partyId\":\"4008153037\",\"colNbr\":\"-1\",\"busiOrder\":[{\"offerNbr\":\"100122150177\",\"linkFlag\":\"N\",\"data\":{\"ooOwners\":[{\"statusCd\":\"S\",\"partyId\":\"4008153037\",\"atomActionId\":\"-1\",\"name\":\"北京楠\",\"state\":\"ADD\"}]},\"busiObj\":{\"offerTypeCd\":\"1\",\"instId\":\"-1\",\"name\":\"宽带业务模板\",\"objId\":\"810000000042\"},\"boActionType\":{\"name\":\"订购\",\"boActionTypeCd\":\"S1\",\"actionClassCd\":\"3\"},\"busiOrderInfo\":{\"statusCd\":\"S\",\"seq\":\"-1\"},\"areaId\":\"11000\"}]}],\"orderListInfo\":{\"olId\":\"-1\",\"staffId\":\"104003875394\",\"areaName\":\"北京市\",\"statusCd\":\"S\",\"channelId\":\"100103759\",\"olNbr\":\"-1\",\"olTypeCd\":\"1\",\"areaId\":\"11000\"}}}";
//		order = String.format(order, "UTF-8");
//		String response = soServiceSMO.soAutoService(JSONObject.fromObject(order));
//		System.out.print(response);
//	}
//
//	@Test
//	public void testGenerateCoNbr() {
//		String response = orderService
//				.generateCoNbr("<request><count>2</count><channelId>11040698</channelId><staffCode>BJ1001</staffCode><areaId>11000</areaId></request>");
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><coNbrList><coNbr>100000371586</coNbr><coNbr>100000371587</coNbr></coNbrList></response>
//	}
//
//	@Test
//	public void testIsInCustBlackList() {
//		String response = customerService
//				.blackUserCheck("<request><checkId>13</checkId><typeCode>2010566051557000</typeCode><channelId>5100000</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>");
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
//				.qryUserVipType("<request><accNbr>18910263133</accNbr><channelId>95129</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>");
//		System.out.printf(response);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><userInfos><vipType>钻石</vipType></userInfos></response>
//	}
//
//	@Test
//	public void testQryPrepareOrder() {
//		String response = orderService
//				.getPreInterimBycond("<request><partyName></partyName><olTypeCd></olTypeCd><olNbr></olNbr><staffCode>BJ1001</staffCode><accessNumber></accessNumber><identifyType>13</identifyType><identityNum>2010599825851000</identityNum><startTime></startTime><endTime></endTime><channelId>11040082</channelId></request>");
//		System.out.printf(response);
//		// 查询入参SQL
//		// select *
//		// from offer_prod_number t, offer_prod a
//		// where t.prod_id = a.prod_id
//		// and a.redu_owner_id in
//		// (select party_id
//		// from order_list
//		// where status_cd = 'PW'
//		// and staff_id = 1004320797
//		// and channel_id = 11040361)
//		// and t.an_type_cd = 103;
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><preOrders><preOrder><olId>100000454025</olId><partyName>汤艳强</partyName><channelId>11040361</channelId><partyId>103005065283</partyId><statusCd>2012-08-20
//		// 10:26:03</statusCd><dt>2012-08-20
//		// 10:26:03</dt><relaOlnbr></relaOlnbr><preType>2</preType><areaId>11000</areaId><relaOlId></relaOlId><channelName>虚拟测试营业厅</channelName><staffId>1004320797</staffId><areaName>北京市</areaName><statusCd>PW</statusCd><staffName>北京洋</staffName><delDt>2012-8-21
//		// 10:29:33</delDt><olNbr>100000354704</olNbr><olTypeCd>5</olTypeCd><statusName>预受理待转正</statusName></preOrder></preOrders></response>
//	}
//
//	@Test
//	public void testCheckUimNo() {
//		String response = rscService
//				.checkUimNo("<request>	<anTypeCd>103</anTypeCd>	<phoneNumber>13381119709</phoneNumber>	<phoneNumberId/>	<uimNo>8986031271010204601</uimNo>	<channelId>11040611</channelId>	<staffCode>C01275</staffCode>	<areaId>10000</areaId></request>");
//		System.out.printf(response);
//		// <response><id>24003703917</id><code>8986030231010155874</code><terminalDevSpec><id>10302057</id><name>天翼手机卡</name><name>终端规格</name></terminalDevSpec><deviceModel><id>2839</id><name>天翼后付费-UTK-32K</name><manufacturer><id>4</id><name>朗讯</name></manufacturer></deviceModel><accessNumbers><accessNumber><anTypeCd>508</anTypeCd><name>CDMA鉴权码</name><anId>22003703917</anId><number>AAA6C1A1BD948E83</number><rscStatusCd>3</rscStatusCd><resulte>0</resulte><cause>成功</cause></accessNumber><accessNumber><anTypeCd>509</anTypeCd><name>IMSI号</name><anId>20003703917</anId><number>460030911165874</number><rscStatusCd>17</rscStatusCd><resulte>0</resulte><cause>成功</cause></accessNumber></accessNumbers><items><item><id>200220</id><name>UIM卡ESN号</name><value>C315ABB1</value></item><item><id>200221</id><name>UIM卡ICCID号</name><value>8986030231010155874</value></item><item><id>200222</id><name>UIM卡PUK码</name><value>78199157</value></item><item><id>200223</id><name>UIM卡PUK2码</name><value>34788817</value></item><item><id>200224</id><name>UIM卡PIN码</name><value>1234</value></item><item><id>200225</id><name>UIM卡PIN2码</name><value>22320781</value></item></items><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//		// 通过
//	}
//
//	// 卡17（空闲）---》14（临时预占）---》2（预占）
//	@Test
//	public void releaseAn() {
//		String sb = "<request><rscStatusCd>14</rscStatusCd><anTypeCd>509</anTypeCd><anId>20003703917</anId><channelId>51000000</channelId><staffCode>BJ1001</staffCode></request>";
//		String response = rscService.releaseAn(sb);
//		System.out.print(response);
//	}
//
//	// @Test
//	// public void testAllocAuditTickets() {
//	// // <request>
//	// // <auditTicketId>510000304944</auditTicketId>
//	// // <channelId>510000</channelId>
//	// // <staffCode>1001</staffCode>
//	// //
//	// <campType>exchangeTicket</campType><!--类型有exchangeTicket-convertTicket-->
//	// // </request>
//	// String s =
//	// "<request><areaId>45101</areaId><auditTicketId>510000304944</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode><campType>exchangeTicket</campType></request>";
//	// String result = srService.allocAuditTickets(s);
//	// System.out.print(result);
//	// //
//	// <response><resultCode>0</resultCode><resultmsg>成功</resultMsg></response>
//	// // 通过
//	// }
//
//	@Test
//	public void testQueryPreOrderDetail() {
//		String s = "<request><areaId>45101</areaId><channelId>510000</channelId><staffCode>bj1001</staffCode><olId>100000487243</olId></request>";
//		String result = orderService.queryPreOrderDetail(s);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>
//		// 成功
//		// </resultMsg>
//		// </response>
//		// 通过
//	}
//
//	//
//	// @Test
//	// public void testQueryAuditTicket() {
//	// String request =
//	// "<request><areaId>10000</areaId><ticketCd></ticketCd><couponInstanceNumber></couponInstanceNumber><accessNumber></accessNumber><staffCode>1001</staffCode><channelId>5100000</channelId><startDate></startDate><endDate></endDate></request>";
//	// String s = srService.queryAuditTicket(request);
//	// System.out.print(s);
//	// // <response>
//	// // <resultCode>0</resultCode>
//	// // <resultMsg>成功</resultMsg>
//	// // <ticketList>
//	// // <ticketInfo>
//	// // <auditTicketName>终端抵用券</auditTicketName>
//	// // <auditTicketCd>6720110720W3TV2A304159</auditTicketCd>
//	// // <auditTicketId>670,000,304,158</auditTicketId>
//	// // <auditTicketType>4</auditTicketType>
//	// // <areaId>467</areaId>
//	// // <price>654</price>
//	// // <createDt>2011-07-20</createDt>
//	// // <startDt>2011-07-20</startDt>
//	// // <endDt>2011-08-20</endDt>
//	// // <statusCd>6</statusCd>
//	// // <statusName>已使用</statusName>
//	// // <boId></boId>
//	// // <partyId></partyId>
//	// // <partyName></partyName>
//	// // <channelId></channelId>
//	// // <staffId></staffId>
//	// // <staffName></staffName>
//	// // <channelName></channelName>
//	// // <accessNumber></accessNumber>
//	// // <offerSpecId></offerSpecId>
//	// // <offerId></offerId>
//	// // <offerName></offerName>
//	// // <ruleCfgId></ruleCfgId>
//	// // <bcdCode>A0000013BC83A6</bcdCode>
//	// // <materialName>华为C2906</materialName>
//	// // <operationType>抵用券兑换</operationType>
//	// // </ticketInfo>
//	// // </ticketInfos>
//	// // </response>
//	// // 通过
//	// }
//
//	// @Test
//	// public void testValidateAuditTicket() {
//	// String request =
//	// "<request><areaId>10000</areaId><credenceNo>510000304384</credenceNo><accessNumber>13349315384</accessNumber><staffCode>1001</staffCode><channelId>5100000</channelId><startDate></startDate><endDate></endDate></request>";
//	// String result = srService.validateAuditTicket(request);
//	// System.out.print(result);
//	// // <response>
//	// // <resultCode>S0001</resultCode>
//	// // <resultMsg>根据抵用券编号未能找到券，请确认券编号！</resultMsg>
//	// // </response>
//	// // 通过
//	// }
//
//	// @Test
//	// public void testExchangeAuditTickets() {
//	// String request =
//	// "<request><areaId>10000</areaId><auditTicketId>510000304944</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode></request>";
//	// String result = srService.exchangeAuditTickets(request);
//	// System.out.print(result);
//	// // <response>
//	// // <resultCode>0</resultCode>
//	// // <resultMsg>成功</resultMsg>
//	// // </response>
//	// // 通过
//	//
//	// }
//
//	@Test
//	public void testQueryOrderListInfo() {
////		String request = "<request>	<curPage>1</curPage><sysFlag>03</sysFlag><pageSize>10</pageSize><statusCd></statusCd>	<coNbr></coNbr>	<accNbr></accNbr>	<startDate></startDate>	<endDate></endDate>	<transfer></transfer><areaId></areaId>	<channelId></channelId>	<staffCode></staffCode></request>";
//		String request ="<request><curPage>2</curPage><pageSize>10</pageSize><statusCd></statusCd><actionClassCd></actionClassCd><boActionType></boActionType><coNbr></coNbr><accNbr></accNbr><startDate></startDate><endDate></endDate><transfer>Y</transfer>><channelId>11040361</channelId><staffCode>BJ1002</staffCode></request>";
//		String result = orderService.queryOrderListInfo(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <custOrderList>
//		// <zongNum>2</zongNum>
//		// <noCnum>0</noCnum>
//		// <custOrder>
//		// <olId>100000451983</olId>
//		// <olNbr>100000352661</olNbr>
//		// <soDate>2012-7-31 16:51:27</soDate>
//		// <soStatusDt>2012-7-31 16:53:33</soStatusDt>
//		// <statusCd>C</statusCd>
//		// <statusName>完成</statusName>
//		// <olTypeCd>1</olTypeCd>
//		// <olTypeName>正常营业受理的购物车</olTypeName>
//		// <staffName>BJ1001</staffName>
//		// <staffNumber>BJ1001</staffNumber>
//		// <channelName>虚拟测试营业厅</channelName>
//		// <channelManageCode>2501000000</channelManageCode>
//		// <boInfos>
//		// <boInfo>
//		// <boActionTypeName>订购</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108上网版129元</name>
//		// <num></num>
//		// <statusName>完成</statusName>
//		// </boInfo>
//		// <boInfo>
//		// <boActionTypeName>订购</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108上网版129元</name>
//		// <num></num>
//		// <statusName>完成</statusName>
//		// </boInfo>
//		// <boInfo>
//		// <boActionTypeName>订购</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108上网版129元</name>
//		// <num></num>
//		// <statusName>完成</statusName>
//		// </boInfo>
//		// </boInfos>
//		// </custOrder>
//		// <custOrder>
//		// <olId>100000451983</olId>
//		// <olNbr>100000352661</olNbr>
//		// <soDate>2012-7-31 16:51:27</soDate>
//		// <soStatusDt>2012-7-31 16:53:33</soStatusDt>
//		// <statusCd>C</statusCd>
//		// <statusName>完成</statusName>
//		// <olTypeCd>1</olTypeCd>
//		// <olTypeName>正常营业受理的购物车</olTypeName>
//		// <staffName>BJ1001</staffName>
//		// <staffNumber>BJ1001</staffNumber>
//		// <channelName>虚拟测试营业厅</channelName>
//		// <channelManageCode>2501000000</channelManageCode>
//		// <boInfos>
//		// </boInfos>
//		// </custOrder>
//		// </custOrderList>
//		// </response>
//		// 通过
//
//	}
//
//	@Test
//	public void testCommitPreOrderInfo() {
//		String request = "<request><areaId>11018</areaId><olId>100000451592</olId><staffCode>BJ1001</staffCode><channelId>11040685</channelId><orderStatus>PW</orderStatus><olTypeCd>5</olTypeCd><preOrderType>1</preOrderType></request>";
//		String result = orderService.commitPreOrderInfo(request);
//		System.out.print(result);
//		// 通过
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <newOlId>100000455893</newOlid>
//		// <newOlNbr>100000477297</newOlNbr>
//		// </response>
//	}
//
//	@Test
//	public void testReleaseCartByOlIdForPrepare() {
//		String request = "<request><areaId>10000</areaId><olId>100003757260</olId><channelId>51000000</channelId><staffCode>bj1001</staffCode></request>";
//		String result = orderService.releaseCartByOlIdForPrepare(request);
//		System.out.print(result);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//		// 通过
//
//	}
//
//	@Test
//	public void testCreateUserAddr() {
//		String request = "<request><areaId>10000</areaId><addrName>陈腾飞</addrName><channelId>51000000</channelId><staffCode>bj1001</staffCode></request>";
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
//		String request = "<request><areaId>452</areaId><deviceCode>8986030982010296417</deviceCode><tmlName></tmlName><tmlManageCd>2</tmlManageCd><tmlTypeCd>1</tmlTypeCd><channelId>51002995</channelId><staffCode>BJ1001</staffCode></request>";
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
//		String request = "<request><areaId>11000</areaId><tmlId></tmlId><anTypeCd>103</anTypeCd><numberMid></numberMid><poolId>200000031</poolId><pnLevelId></pnLevelId><numberHead></numberHead><numberTail></numberTail><numberCnt>5</numberCnt><channelId>100094003</channelId><staffCode>BJ1001</staffCode></request>";
//		String result = rscService.queryPhoneNumberList(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <phoneNumberList>
//		// <phoneNumberInfo>
//		// <phoneNumberId>218901036934</phoneNumberId>
//		// <phoneNumber>18901036934</phoneNumber>
//		// <anTypeCd>103</anTypeCd>
//		// <pnLevelId>10411</pnLevelId>
//		// <pnLevelName>C网_新十一类号码</pnLevelName>
//		// <pnLevelDesc>无</pnLevelDesc>
//		// <minCharge>0</minCharge>
//		// <preCharge>0</preCharge>
//		// <reuseFlag>是</reuseFlag>
//		// <cardCode></cardCode>
//		// <imsi></imsi>
//		// <description></description>
//		// </phoneNumberInfo>
//		// </phoneNumberList>
//		// </response>
//	}
//
//	@Test
//	public void testGetUimCardInfo() {
//		String response = rscService
//				.getUimCardInfo("<request><areaId>10000</areaId><cardNo>8986031270010188357</cardNo><imsi></imsi><channelId>54003521</channelId><staffCode>BJ1001</staffCode><areaCode>45001</areaCode></request>");
//		System.out.println(response.toString());
//		// <response><RESULT>1</RESULT><CAUSE>返回成功</CAUSE><TERMINAL_DEV_ID>31602526</TERMINAL_DEV_ID><TERMINAL_CODE>8986031270010188357</TERMINAL_CODE><DEV_MODEL_NAME>天翼预付费(双模)-OTA-128K</DEV_MODEL_NAME><C_IMSI>460030931079430</C_IMSI><IMSI_STATE>3</IMSI_STATE><G_IMSI>204043641827467</G_IMSI><C_AKEY>AAA6CB2ED21DB0DF</C_AKEY><G_AKEY>AAA0471C0C0F085BDB0A3941770B268C</G_AKEY><HRPD_UPP>460030931079430@mycdma.cn</HRPD_UPP><HRPD_SS>AAA179586EA4E5B7</HRPD_SS><STORE_NAME>移动合作渠道部</STORE_NAME><HLR_CODE>CH11</HLR_CODE><UIMID>AAAE687D</UIMID><ISEVDO>0</ISEVDO><ESN>AAAE687D</ESN><ICCID>89860312700101883576</ICCID><PUK1>03560835</PUK1><PUK2>35593140</PUK2><PIN1>1234</PIN1><PIN2>47847847</PIN2></response>
//	}
//
//	@Test
//	public void queryCodeByNum() {
//		String response = rscService.queryCodeByNum("<request><num>18046543815</num></request>");
//		System.out.println(response.toString());
//		// <response><RESULT>1</RESULT><CAUSE>返回成功</CAUSE><TERMINAL_DEV_ID>31602526</TERMINAL_DEV_ID><TERMINAL_CODE>8986031270010188357</TERMINAL_CODE><DEV_MODEL_NAME>天翼预付费(双模)-OTA-128K</DEV_MODEL_NAME><C_IMSI>460030931079430</C_IMSI><IMSI_STATE>3</IMSI_STATE><G_IMSI>204043641827467</G_IMSI><C_AKEY>AAA6CB2ED21DB0DF</C_AKEY><G_AKEY>AAA0471C0C0F085BDB0A3941770B268C</G_AKEY><HRPD_UPP>460030931079430@mycdma.cn</HRPD_UPP><HRPD_SS>AAA179586EA4E5B7</HRPD_SS><STORE_NAME>移动合作渠道部</STORE_NAME><HLR_CODE>CH11</HLR_CODE><UIMID>AAAE687D</UIMID><ISEVDO>0</ISEVDO><ESN>AAAE687D</ESN><ICCID>89860312700101883576</ICCID><PUK1>03560835</PUK1><PUK2>35593140</PUK2><PIN1>1234</PIN1><PIN2>47847847</PIN2></response>
//	}
//
//	@Test
//	public void testQueryPhoneNumberPoolList() {
//		String request = "<request><accNbr>18910444002</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>-10020</channelId><staffCode>BJ_10020</staffCode></request>";
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
//	public void testchange() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<olTypeCd>%s</olTypeCd>", "5"));
//		sb.append(String.format("<systemId>%s</systemId>", "6090010028"));
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));//9108120916861921
//		sb.append(String.format("<partyId>%s</partyId>", "103005071897"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
////		sb.append("<bindPayForNbr>13366468856</bindPayForNbr>");
////		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "9"));
//		sb.append("<offerSpecs>");
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt><startFashion></startFashion></offerSpec>",
//				"860143", "0", "", ""));
//		sb.append("</offerSpecs>");
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "90000001"));
//		sb.append(String.format("<roleCd>%s</roleCd>", "90000"));
////		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<anId>%s</anId>", "213311057312"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "刘德华"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "13366468856"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "13311057312"));
//		sb.append("<subOrder>");
//		sb.append("<offerSpecs>");
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt><startFashion></startFashion></offerSpec>",
//				"992015288", "0", "", ""));
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt><startFashion></startFashion></offerSpec>",
//				"100252", "0", "", ""));
//		sb.append("</offerSpecs>");
////		sb.append("<prodPropertys>");
////		sb.append(String.format(
////				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>", "12389",
////				"FTTX属性标识", "100", "0"));
////		sb.append("</prodPropertys>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "123"));
////		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "890000050"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "C42002052"));
//		sb.append(String.format("<anId>%s</anId>", "215321313255"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "test"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
////		sb.append("<bindPayForNbr>13366468856</bindPayForNbr>");
////		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "9"));
//		// sb.append(String.format("<assistMan>%s</assistMan>",
//		// "业务协销人#11#1004319549#北京测试工号#C00636#企业信息化部#企业信息化部#3206000000"));
//		sb.append(String.format("<password>%s</password>", "111111"));
//		sb.append(String.format("<passwordType>%s</passwordType>", "2"));
//		sb.append("</subOrder>");
//
//		sb.append("<subOrder>");
//		sb.append("<offerSpecs>");
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt><startFashion></startFashion></offerSpec>",
//				"22655", "0", "", ""));
//		sb.append("</offerSpecs>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "122"));
////		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "600000207"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "C42002052"));
//		sb.append(String.format("<anId>%s</anId>", "215321313255"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "刘德华"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
////		sb.append("<bindPayForNbr>15301392723</bindPayForNbr>");
////		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "2"));
//		// sb.append(String.format("<assistMan>%s</assistMan>",
//		// "业务协销人#11#1004319549#北京测试工号#C00636#企业信息化部#企业信息化部#3206000000"));
//		sb.append("</subOrder>");
//		sb.append("<listChargeInfo>");
//		sb.append("<chargeInfo>");
//		sb.append("<payMethod>10</payMethod>");
//		sb.append("<offerName>(993018678)易通3元卡</offerName>");
//		sb.append("<charge>2000</charge>");
//		sb.append("<servName></servName>");
//		sb.append("<acctItemTypeName>预存款(套餐/靓号/租机)</acctItemTypeName>");
//		sb.append("<acctItemTypeId>50002</acctItemTypeId>");
//		sb.append("<appCharge>2000</appCharge>");
//		sb.append("<specId>860143</specId>");
//		sb.append("</chargeInfo>");
//		sb.append("</listChargeInfo>");
//		sb.append("<payInfoList>");
//		sb.append("<payInfo>");
//		sb.append("<method>10</method>");
//		sb.append("<amount>2000</amount>");
//		sb.append("<appendInfo>刷卡或支票支付请填写描述信息</appendInfo>");
//		sb.append("</payInfo>");
//		sb.append("</payInfoList>");
//		sb.append("</order>");
//		sb.append("<channelId>-10091</channelId>");
//		sb.append("<staffCode>bj1001</staffCode>");
//		sb.append("</request>");
//
//		String response = orderService.orderSubmit(sb.toString());
//		// String response=orderService.validateCustOrder(sb.toString());
//		System.out.print(response);
//	}
//
//	@Test
//	public void testSavePropare() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<olTypeCd>%s</olTypeCd>", "7"));
//		sb.append("<bindPayForNbr>18911272769</bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//		sb.append(String.format("<partyId>%s</partyId>", "200003582207"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//				"992015248", "0", "20120727", "30000101"));
//		sb.append("<subOrder>");
//		// sb.append("<offerSpecs>");
//		// sb.append(String.format(
//		// "<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//		// "1030908535", "0", "20120727", "30000101"));
//		// sb.append("</offerSpecs>");
//		// sb.append(String.format("<prod2accNbr>%s</prod2accNbr>",
//		// "18945283489"));
//		// sb.append(String.format("<anId2>%s</anId2>", "5495120"));
//		sb.append(String.format("<roleCd>%s</roleCd>", "1"));
//		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "378"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "15321313257"));
//		sb.append(String.format("<anId>%s</anId>", "215321313257"));
//		sb.append("<bindPayForNbr></bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "378"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "test"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18911272769"));
//		sb.append(String.format("<assistMan>%s</assistMan>", "al1001"));
//		sb.append(String.format("<password>%s</password>", "111111"));
//		sb.append(String.format("<passwordType>%s</passwordType>", "2"));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "18911272769", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300203", "催缴方式", "2", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"400080840", "套餐到期提醒", "2", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"2100315", "连选号码", "13311111111", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"2100314", "加连选", "1", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>", "10001",
//				"入网方式", "0", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"491000003", "EVDOSIGNUP", "0", "0"));
//		sb.append("</prodPropertys>");
////		sb.append("<tds>");
////		sb.append("<td>");
////		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986035730010051440"));
////		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
////		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "24003721151"));
////		sb.append(String.format("<devModelId>%s</devModelId>", "2839"));
////		sb.append(String.format("<ownerTypeCd>%s</ownerTypeCd>", "1"));
////		sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", "1"));
////		sb.append("</td>");
////		sb.append("</tds>");
//		// sb.append("<coupons>");
//		// sb.append("<coupon>");
//		// sb.append(String.format("<couponcode>%s</couponcode>", "900000003"));
//		// sb.append(String.format("<couponspecid>%s</couponspecid>",
//		// "900000003"));
//		// sb.append(String.format("<storeId>%s</storeId>", "379"));
//		// sb.append(String.format("<storeName>%s</storeName>", "NB"));
//		// sb.append(String.format("<chargeItemCd>%s</chargeItemCd>", "1"));
//		// sb.append(String.format("<couponCharge>%s</couponCharge>", "249"));
//		// sb.append(String.format("<count>%s</count>", "1"));
//		// sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", 379));
//		// sb.append("</coupon>");
//		// sb.append("</coupons>");
//		sb.append("</subOrder>");
//		sb.append("</order>");
//		sb.append("<areaId>11000</areaId>");
//		sb.append("<channelId>11040698</channelId>");
//		sb.append("<staffCode>BJ1001</staffCode>");
//		sb.append("</request>");
//		System.out.print(sb.toString());
//
////		 String response = orderService.savePrepare(sb.toString());
//		// String response = orderService.validateCustOrder(sb.toString());
//		String response = orderService.orderSubmit(sb.toString());
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <olId>100000470883</olId>
//		// <boIds>
//		// <boId>100032913647</boId>
//		// <boId>100032913648</boId>
//		// <boId>100032913649</boId>
//		// </boIds>
//		// </response>
//	}
//
//	@Test
//	public void fsfs() {
//		String response = "";
//		try {
//			StringBuilder sb = new StringBuilder();
//			sb.append("<request>");
//			sb.append("<order>");
//			sb.append(String.format("<olTypeCd>%s</olTypeCd>", "2"));
//			sb.append(String.format("<systemId>%s</systemId>", "6090010023"));
////			sb.append("<bindPayForNbr>13366468856</bindPayForNbr>");
//			sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//			sb.append(String.format("<partyId>%s</partyId>", ""));
//			sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
////			sb.append("<assistManInfoList><assistManInfo><staffTypeName>发展人</staffTypeName><staffType>11</staffType><staffNumber>bj1001</staffNumber><staffName>chentengfei</staffName><staffId>132131</staffId><orgName>企业</orgName><orgId>1</orgId></assistManInfo><assistManInfo><staffTypeName>发展人</staffTypeName><staffType>13</staffType><staffNumber>bj1001</staffNumber><staffName>chentengfei</staffName><staffId>132131</staffId><orgName>企业</orgName><orgId>1</orgId></assistManInfo></assistManInfoList>");
//			sb.append("<offerSpecs>");
//			sb.append("<offerSpec><id>900595</id><name>商务领航行业版套餐（共享）-60</name><actionType>0</actionType><startDt></startDt><endDt></endDt><startFashion>0</startFashion></offerSpec>");
//			sb.append("<offerSpec><id>992018145</id><name>(992018145)本地漫游</name><actionType>0</actionType><startDt></startDt><endDt></endDt><startFashion>0</startFashion></offerSpec>");
//			sb.append("</offerSpecs>");
//			// sb.append("<prodPropertys>");
//			// sb.append(String.format(
//			// "<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//			// " 111111116", "协销人",
//			// "业务协销人#11#1004320797#北京测试工号#BJ1001#企业信息化部#企业信息化部#2500000000",
//			// "0"));
//			// sb.append("</prodPropertys>");
//			sb.append(String.format("<prodSpecId>%s</prodSpecId>", "378"));
//			sb.append(String.format("<roleCd>%s</roleCd>", "1"));
////			sb.append(String.format("<prodId>%s</prodId>", ""));
//			sb.append(String.format("<anId>%s</anId>", "218901076493"));
//			sb.append(String.format("<coLinkMan>%s</coLinkMan>", "屌丝1"));
//			sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "13366468856"));
//			sb.append(String.format("<accessNumber>%s</accessNumber>", "13772551164"));
//			 sb.append("<tds>");
//			 sb.append("<td>");
//			 sb.append(String.format("<terminalCode>%s</terminalCode>",
//			 "8986031290010082044"));
//			 sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>",
//			 "10302057"));
//			 sb.append(String.format("<terminalDevId>%s</terminalDevId>",
//			 "34937096"));
//			 sb.append(String.format("<devModelId>%s</devModelId>", "441063"));
//			 sb.append(String.format("<ownerTypeCd>%s</ownerTypeCd>", "1"));
//			 sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", "1"));
//			 sb.append(String.format("<devNumId>%s</devNumId>", "180029888096"));
//			 sb.append("</td>");
//			 sb.append("</tds>");
////			sb.append("<listChargeInfo>");
////			sb.append("<chargeInfo>");
////			sb.append("<payMethod>500</payMethod>");
////			sb.append("<offerName>(992018127)国际+国内长途、本地通话</offerName>");
////			sb.append("<charge>10000</charge>");
////			sb.append("<servName></servName>");
////			sb.append("<acctItemTypeName>国际长途押金</acctItemTypeName>");
////			sb.append("<acctItemTypeId>3005</acctItemTypeId>");
////			sb.append("<appCharge>10000</appCharge>");
////			sb.append("<specId>992018229</specId>");
////			sb.append("</chargeInfo>");
////			sb.append("<chargeInfo>");
////			sb.append("<payMethod>500</payMethod>");
////			sb.append("<offerName>卡费</offerName>");
////			sb.append("<charge>20</charge>");
////			sb.append("<servName></servName>");
////			sb.append("<acctItemTypeName>卡费</acctItemTypeName>");
////			sb.append("<acctItemTypeId>90013</acctItemTypeId>");
////			sb.append("<appCharge>20</appCharge>");
////			sb.append("<specId>378</specId>");
////			sb.append("</chargeInfo>");
////			sb.append("</listChargeInfo>");
////			sb.append("<payInfoList>");
////			sb.append("<payInfo>");
////			sb.append("<method>500</method>");
////			sb.append("<amount>20</amount>");
////			sb.append("<appendInfo>卡号68226</appendInfo>");
////			sb.append("</payInfo>");
////			sb.append("</payInfoList>");
//			sb.append("</order>");
//			sb.append("<areaId>11000</areaId>");
//			sb.append("<channelId>95141</channelId>");
//			sb.append("<staffCode>P0059125</staffCode>");
//			sb.append("</request>");
//			// String request
//			// ="<request><order><olTypeCd>7</olTypeCd><orderTypeId>1</orderTypeId><partyId>103005069112</partyId><acctCd>100006610413</acctCd><offerSpec><id>25000</id><name>(25000)乐享3G套餐聊天版-59</name><actionType>0</actionType><startDt>2012-15-11</startDt><endDt>3000-01-01</endDt></offerSpec><subOrder><roleCd>1</roleCd><orderTypeId>1</orderTypeId><prodId></prodId><prodSpecId>378</prodSpecId><accessNumber>15321812357</accessNumber><anId>215321812357</anId><assistMan>BJ1001</assistMan><prodPropertys><property><id>350050</id><name>信用度控制标识</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>993018693</id><name>(993018693)银行分期付款</name><actionType>0</actionType></offerSpec></offerSpecs><tds><td><terminalCode>8986031100010019646</terminalCode><terminalDevSpecId>10302057</terminalDevSpecId><terminalDevId>27343615</terminalDevId><devModelId>2866</devModelId><ownerTypeCd>1</ownerTypeCd><maintainTypeCd>1</maintainTypeCd></td></tds></subOrder></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>bj1001</staffCode></request>";
//			// response = orderService.paymentCountList(request);
//			// response = orderService.orderSubmit(sb.toString());
//			// <request><order><olTypeCd>7</olTypeCd><orderTypeId>1</orderTypeId><partyId>103005069112</partyId><acctCd>100006610413</acctCd><offerSpec><id>900521</id><name>(900521)易通3元卡</name><actionType>0</actionType><startDt>2012-15-06</startDt><endDt>3000-01-01</endDt></offerSpec></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>bj1001</staffCode></request>
////			response = orderService.validateCustOrder(sb.toString());
////			response = orderService.computeChargeInfo(sb.toString());
//			System.out.print(sb.toString());
//			response = orderService.orderSubmit(sb.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.print(response);
//	}
//	@Test
//	public void fsfsfs(){
////	String request = "<request><order><partyId>200003302600</partyId><orderTypeId>1</orderTypeId><systemId>6090010023</systemId><roleCd>90000</roleCd><prodSpecId>600012009</prodSpecId><acctCd>9109071806506803</acctCd><coLinkMan>CTF</coLinkMan><coLinkNbr>58504439</coLinkNbr><olTypeCd>2</olTypeCd><offerSpecs><offerSpec><id>20080205</id><name>自主型宽带融合套餐-8M</name><actionType>0</actionType></offerSpec><offerSpec><id>20081208</id><name>(20081208)自主型宽带融合套餐-8M(一年）（2012）</name><actionType>0</actionType></offerSpec></offerSpecs><subOrder><orderTypeId>1</orderTypeId><roleCd>249</roleCd><prodSpecId>10</prodSpecId><accessNumber>B23285995</accessNumber><anId>120052945422</anId><prod2accNbr>01010717594</prod2accNbr><anId2>120052945422</anId2><assistMan>Bj1001</assistMan><password>209126</password><prodPropertys><property><id>30018</id><name>远洋一方-华为（东四）</name><value>100000548</value><actionType>0</actionType></property><property><id>900000013</id><name>朝阳区</name><value>11005</value><actionType>0</actionType></property><property><id>310056</id><name>否-朝阳门营业厅</name><value>6</value><actionType>0</actionType></property><property><id>3210047</id><name>Ggg</name><value>122776091736</value><actionType>0</actionType></property><property><id>310051</id><name>要求服务日期</name><value>2013-01-16</value><actionType>0</actionType></property><property><id>310142</id><name>北京喆-大郊亭</name><value>100000001718</value><actionType>0</actionType></property><property><id>12389</id><name>LAN</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>990015002</id><name>送云家庭终端500元押金</name><actionType>0</actionType></offerSpec><offerSpec><id>980010250</id><name>普通宽带无线猫押金360元</name><actionType>0</actionType></offerSpec><offerSpec><id>992018603</id><name>4M免费提速体验12M可选包</name><actionType>0</actionType></offerSpec><offerSpec><id>992015285</id><name>固网宽带-8M不限时住宅包月（2012版）-LAN</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec><offerSpec><id>100172</id><name>LAN端口占用费10元</name><actionType>0</actionType></offerSpec></offerSpecs></subOrder><listChargeInfo><chargeInfo><payMethod>500</payMethod><offerName>自主型宽带融合套餐-8M(一年）（2012）</offerName><charge>0</charge><servName></servName><acctItemTypeName>预存款(套餐/靓号/租机)</acctItemTypeName><acctItemTypeId>50002</acctItemTypeId><appCharge>180000</appCharge><specId>20081208</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName>送云家庭终端500元押金</offerName><charge>0</charge><servName></servName><acctItemTypeName>租机保证金</acctItemTypeName><acctItemTypeId>1500</acctItemTypeId><appCharge>50000</appCharge><specId>990015002</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName>普通宽带无线猫押金360元</offerName><charge>0</charge><servName></servName><acctItemTypeName>普通宽带无线猫押金</acctItemTypeName><acctItemTypeId>1001</acctItemTypeId><appCharge>36000</appCharge><specId>980010250</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName></offerName><charge>0</charge><servName></servName><acctItemTypeName>固网_综合工料费</acctItemTypeName><acctItemTypeId>121</acctItemTypeId><appCharge>30000</appCharge><specId>10</specId></chargeInfo></listChargeInfo><payInfoList><payInfo><method>500</method><amount>296000</amount><appendInfo>现金</appendInfo></payInfo></payInfoList></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>Bj1001</staffCode></request>";
//	String request ="<request>	<order>		<prodStatusCd>0</prodStatusCd>		<ifAgreementStr>Y</ifAgreementStr>		<assistManInfoList>			<assistManInfo>				<staffTypeName/>				<staffType>16</staffType>				<staffNumber/>				<staffName/>				<staffId>104008165337</staffId>				<orgName/>				<orgId/>			</assistManInfo>			<assistManInfo>				<staffTypeName>合作渠道</staffTypeName>				<staffType>13</staffType>				<staffNumber/>				<staffName/>				<staffId>104008165337</staffId>				<orgName/>				<orgId/>			</assistManInfo>		</assistManInfoList>		<partyId>104009169886</partyId>		<orderTypeId>1</orderTypeId>		<systemId>6090010023</systemId>		<roleCd>1</roleCd>		<prodSpecId>378</prodSpecId>		<accessNumber>18910891155</accessNumber>		<anId>218910891155</anId>		<acctCd></acctCd>		<coLinkMan>邵存将</coLinkMan>		<olTypeCd>7</olTypeCd>		<prodProperties>			<property>				<id>350050</id>				<name>信用度控制标识</name>				<value>1</value>				<actionType>0</actionType>			</property>		</prodProperties>		<offerSpecs>			<offerSpec>				<id>992018126</id>				<name>服务-国内长途+本地通话</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018229</id>				<name>服务-国内漫游</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018143</id>				<name>服务-无线宽带(1X)</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018156</id>				<name>服务-无线宽带(3G)</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>700000030</id>				<name>七彩铃音功能（免费）</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>720000021</id>				<name>天翼阅读（免费）</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>720000023</id>				<name>爱游戏（免费）</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>902612</id>				<name>201108聊天版89元</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>25376</id>				<name>靓号资费(2012)-第九类</name>				<startFashion></startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>8401</id>				<name>iPhone4S16GB-直降1200优惠-289元-终端补贴-24月-社会化</name>				<startFashion></startFashion>				<actionType>0</actionType>				<properties>					<property>						<id>23990</id>						<name>预存金额(元)</name>						<value>4081</value>					</property>					<property>						<id>999</id>						<name>折扣</name>						<value>100</value>					</property>				</properties>			</offerSpec>		</offerSpecs>	</order>	<areaId>10000</areaId>	<channelId>11040082</channelId>	<staffCode>BJ1001</staffCode></request>";
////		String request ="<request><order><busiOrderTimeList>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>		</busiOrderTimeList><prodStatusCd>0</prodStatusCd><ifAgreementStr>Y</ifAgreementStr><partyId>104009169886</partyId><orderTypeId>1</orderTypeId><systemId>6090010023</systemId><roleCd>90000</roleCd><prodSpecId>600012009</prodSpecId><acctCd>9108110433002393</acctCd><coLinkMan>邵存将</coLinkMan><coLinkNbr>76787663</coLinkNbr><olTypeCd>2</olTypeCd><offerSpecs><offerSpec><id>20080205</id><name>自主型宽带融合套餐-8M</name><startFashion></startFashion><actionType>0</actionType></offerSpec></offerSpecs><subOrder><orderTypeId>1</orderTypeId><roleCd>249</roleCd><prodSpecId>10</prodSpecId><accessNumber>B23504663</accessNumber><anId>120053027300</anId><prod2accNbr>01010791730</prod2accNbr><anId2>120053027300</anId2><assistMan>Sqjl0002</assistMan><password>519816</password><prodPropertys><property><id>30018</id><name>远洋一方</name><value>100000536</value><actionType>0</actionType></property><property><id>900000013</id><name>西城区</name><value>11002</value><actionType>0</actionType></property><property><id>310056</id><name>否-朝阳门营业厅</name><value>6</value><actionType>0</actionType></property><property><id>3210047</id><name>测</name><value>122776188072</value><actionType>0</actionType></property><property><id>310051</id><name>要求服务日期</name><value>2013-03-03</value><actionType>0</actionType></property><property><id>310142</id><name>邵存将-uuuu</name><value>219887</value><actionType>0</actionType></property><property><id>12389</id><name>LAN</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>112518697</id><name>外包LAN-8M包月-LAN</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec><offerSpec><id>100172</id><name>LAN端口占用费10元</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec></offerSpecs></subOrder><subOrder><orderTypeId>1</orderTypeId><roleCd>251</roleCd><prodId>103030348032</prodId><prodSpecId>378</prodSpecId><accessNumber>18910709466</accessNumber><assistMan>Sqjl0002</assistMan><prodPropertys><property><id>310056</id><name>否-朝阳门营业厅</name><value>6</value><actionType>0</actionType></property><property><id>310051</id><name>要求服务日期</name><value>2013-03-03</value><actionType>0</actionType></property><property><id>30018</id><name>远洋一方</name><value>100000536</value><actionType>0</actionType></property></prodPropertys></subOrder></order><areaId>10000</areaId><channelId>100092996</channelId><staffCode>Sqjl0002</staffCode></request>";
//		String response = "";
//	try {
//		response = orderService.savePrepare(request);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.print(response);
//	
//	}
//	
//	@Test
//	public void qryPreHoldNumber() {
//		String ss = "<request><anId></anId><phoneNumber>13391630183</phoneNumber><flag>0</flag><channelId>11040611</channelId><areaId>10000</areaId><staffCode>C00878</staffCode></request>";
//		// String sb =
//		// "<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><flag>2</flag><anId>215321313255</anId></request>";
//		String result = rscService.qryPreHoldNumber(ss);
//		System.out.print(result);
//	}
//	@Test
//	public void testQueryManager() {
//		String response = customerService
//				.queryManager("<request><value>BJBSS</value><flag>1</flag><channelId>51000000</channelId><staffCode>BJ1001</staffCode><areaId>45101</areaId></request>");
//		System.out.print(response);
//	}
//
//	@Test
//	public void testSysManager() {
//		try {
//			String result2 = smService.findStaffInfoByStaffNumber("P0024300");
//			System.err.print(result2);
//		} catch (BssException e) {
//			e.printStackTrace();
//		}
//
//		// <?xml version='1.0' encoding='UTF-8'?> <StaffInfos> <StaffInfo>
//		// <orgId/>
//		// <orgName>北京邮政电子商务局</orgName>
//		// <posts/>
//		// <staffNumberInfo>
//		// <party>
//		// <area>
//		// <areaId>11000</areaId>
//		// <name>北京市</name>
//		// </area>
//		// <name>北京邮政电子商务局1P0024300</name>
//		// <partyId>9200000584</partyId>
//		// </party>
//		// <staffId>9200000584</staffId>
//		// <staffNumber>P0024300</staffNumber>
//		// </staffNumberInfo>
//		// </StaffInfo>
//		// </StaffInfos>
//	}
//
//	@Test
//	public void qryPartyInfo() {
//		String result2 = customerService
//				.selectPartyInfo("<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><partyName>tmd</partyName><page>1</page><pageSize>2</pageSize></request>");
//		System.err.print(result2);
//		// <response> <resultCode>0</resultCode> <resultMsg>成功</resultMsg>
//		// <partyInfos> <partyInfo> <identityNum>2010568309817000</identityNum>
//		// <name>北京电信测试使用</name> </partyInfo> <partyInfo>
//		// <identityNum>2010568309827000</identityNum> <name>北京电信测试使用</name>
//		// </partyInfo> </partyInfos> </response>
//	}
//
//	@Test
//	public void qryPartyManager() {
//		String result = customerService
//				.selectPartyManager("<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><identityNum>2010572773401000</identityNum></request>");
//		// select a.identity_num partyId
//		// from party_identity a, party_role_party b, party c
//		// where a.party_id = b.party_id
//		// and b.role_party_id = c.party_id
//		// and a.identidies_type_cd = '13'
//		System.err.print(result);
//		// <response> <resultCode>0</resultCode> <resultMsg>成功</resultMsg>
//		// <partyInfo> <staffNumber>VG1432</staffNumber>
//		// <partyName>北京电信测试使用</partyName> </partyInfo> </response>
//	}
//
//	@Test
//	public void getBrandLevelDetail() {
//		String result = customerService
//				.getBrandLevelDetail("<request><staffCode>BJ_10000</staffCode><areaId>010</areaId><channelId>-10000</channelId><accNum>18001223533</accNum></request>");
////				.getBrandLevelDetail("<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><accNum>13404171778</accNum></request>");
//		System.out.print(result);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <partyBrand>
//		// <prodId>102013318985</prodId>
//		// <partyName>北京细花</partyName>
//		// <identityNum>391623195911291128</identityNum>
//		// <contactPhone>96325874 </contactPhone>
//		// <inNetDate>2011-08-02 22:23:52</inNetDate>
//		// <creditNumber>信用额度</creditNumber>
//		// <partyAddress>大郊亭</partyAddress>
//		// <partyPost>100000</partyPost>
//		// <identidiesTypeCd>1</identidiesTypeCd>
//		// <partyStatus>1</partyStatus>
//		// <greatLevel>积分等级</greatLevel><!--积分等级-->
//		// <brandCode></brandCode>
//		// <managerName></managerName>
//		// <vprepayFlag>仅后付</vprepayFlag>
//		// <prodSpecId>378</prodSpecId>
//		// <userType>政企</userType>
//		// </partyBrand>
//		// </response>
//	}
//
//	@Test
//	public void addTermOrCard() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("<request>                                                                              ");
//		sb.append("	<order>                                                                               ");
//		sb.append("		<orderTypeId>14</orderTypeId>                                                       ");
//		sb.append("		<partyId>104004497104</partyId>                                                     ");
//		sb.append("		<prodSpecId>379</prodSpecId>                                                        ");
//		sb.append("		<prodId>102011412888</prodId>                                                       ");
//		sb.append("		<coLinkMan>陈霸先</coLinkMan>                                                       ");
//		sb.append("		<coLinkNbr>13301173555</coLinkNbr>                                                  ");
//		sb.append("		<assistMan>小二</assistMan>                                                         ");
//		sb.append("			<tds> <!-- 终端信息 -->                                                           ");
//		sb.append("				<td>                                                                            ");
//		sb.append("					<terminalCode>8986035730010051440</terminalCode> <!-- 终端设备编号 -->        ");
//		sb.append("					<terminalDevSpecId>10302057</terminalDevSpecId> <!-- 终端设备型号 -->         ");
//		sb.append("					<terminalDevId>24003721151</terminalDevId> <!-- 终端设备ID -->                ");
//		sb.append("					<devModelId>2839</devModelId> <!-- 设备型号ID -->                             ");
//		sb.append("					<ownerTypeCd>1</ownerTypeCd> <!-- 终端产权类型1.自购 2.局购 -->               ");
//		sb.append("					<maintainTypeCd>1</maintainTypeCd> <!-- 终端维护方式1.自维 2.代维 -->         ");
//		sb.append("				</td>                                                                           ");
//		sb.append("			</tds>                                                                            ");
//		// sb.append("				<coupons> <!-- 物品信息 -->                                                     ");
//		// sb.append("				<coupon>                                                                        ");
//		// sb.append("					<couponcode>900000003</couponcode> <!-- 物品的实例号（可以为空） -->          ");
//		// sb.append("					<couponspecid>379</couponspecid> <!-- 物品规格 -->                            ");
//		// sb.append("					<storeId></storeId> <!-- 仓库id（可以为空） -->                               ");
//		// sb.append("					<storeName>NB</storeName> <!-- 仓库名称（可以为空）-->                        ");
//		// sb.append("					<chargeItemCd>1</chargeItemCd> <!-- 收费细项编码 -->                          ");
//		// sb.append("					<couponCharge>249</couponCharge> <!-- 应收费用 -->                            ");
//		// sb.append("					<count>1</count> <!-- 物品的数量 -->                                          ");
//		// sb.append("		      </coupon>                                                                     ");
//		// sb.append("		     </coupons>                                                                     ");
//		sb.append("	</order>                                                                              ");
//		sb.append("	<channelId>11040361</channelId>                                                       ");
//		sb.append("	<staffCode>bj1001</staffCode>                                                         ");
//		sb.append("	<areaId>10000</areaId>                                                                ");
//		sb.append("</request>                                                                             ");
//		sb.toString();
//		StringBuffer eb = new StringBuffer();
//
//		// -----停机保号|停机保号复机
//		eb.append("<request>                                                                   ");
//		eb.append("	<order>                                                                    ");
//		eb.append("		<orderTypeId>19</orderTypeId><!--19为停机保号|20为停机保号复机-->        ");
//		eb.append("		<partyId>104004497104</partyId>                                          ");
//		eb.append("		<prodSpecId>379</prodSpecId>                                             ");
//		eb.append("		<prodId>102011412888</prodId>                                            ");
//		eb.append("		<coLinkMan>陈霸先</coLinkMan>                                            ");
//		eb.append("		<coLinkNbr>13301173555</coLinkNbr>                                       ");
//		eb.append("		<assistMan>小二</assistMan>                                              ");
//		eb.append("	</order>                                                                   ");
//		eb.append("	<channelId>11040361</channelId>                                            ");
//		eb.append("	<staffCode>bj1001</staffCode>                                              ");
//		eb.append("	<areaId>10000</areaId>                                                     ");
//		eb.append("</request>                                                                  ");
//
//		// String request = eb.toString();
//		String request = sb.toString();
//
//		String result = orderService.businessService(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void addSerivceAcct() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>刘德华</serviceName><managerId>123</managerId><linkMan>123</linkMan><linkNbr>123</linkNbr><servicePartyId>513005063694</servicePartyId><buildingId>1</buildingId><buildingType>1</buildingType></request>";
//		String response = orderService.addSerivceAcct(sb);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>新增服务帐户成功</resultMsg></response>
//	}
//
//	public static void main(String args[]) {
//		URL url;
//		InputStream is = null;
//		InputStreamReader isr = null;
//		BufferedReader br = null;
//		String line;
//		String result = "";
//		String msgUrl="http://crm.bjcrm2.ctbss.net:7020/VoucherPrintService/voucherPrintAction!voucherInfoPrint.printAction?olId=100000513254&runFlag=1&printType=1";
//		try {
//			url = new URL(msgUrl);
//			is = url.openStream();
//			isr = new InputStreamReader(is);
//			br = new BufferedReader(isr);
//			while (null != (line = br.readLine())) {
//				System.out.print(new String(line.getBytes(), "UTF-8")) ;
//			}
//		} catch (MalformedURLException e) {
//			result = e.getMessage();
//		} catch (IOException e) {
//			result = e.getMessage();
//		} finally {
//			try {
//				isr.close();
//				br.close();
//				is.close();
//			} catch (IOException e) {
//				result = e.getMessage();
//			}
//		}
//
//	}
//
//	@Test
//	public void queryMdnByUim() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><uimNo>8986031200010164423</uimNo></request>";
//		String response = rscService.queryMdnByUim(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void getUserZJInfoByAccessNumber() {
//		String sb = "<request><phoneNumber>13404171778</phoneNumber>		<offerSpecId>6000</offerSpecId><channelId>1</channelId><staffCode>1</staffCode></request>";
//		String response = customerService.getUserZJInfoByAccessNumber(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void validateContractInfo() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accNum>13301034000</accNum></request>";
//		String response = customerService.validateContractInfo(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void qryCreditrating() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><prodId>200001194279</prodId><flag>CRM</flag></request>";
//		String response = customerService.qryCreditrating(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void querySerivceAcct() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>刘德华</serviceName><curPage>1</curPage><pageSize>10</pageSize></request>";
//		String response = orderService.querySerivceAcct(sb);
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <ComponentListInfo>
//		// <ComponentInfo>
//		// <buildingId>1</buildingId>
//		// <buildingType>1</buildingType>
//		// <createDt>2012-8-29 11:28:38</createDt>
//		// <linkMan>123</linkMan>
//		// <linkNbr>123</linkNbr>
//		// <linkNbr1></linkNbr1>
//		// <managerId>123</managerId>
//		// <managerName></managerName>
//		// <prodId></prodId>
//		// <serviceId>1025</serviceId>
//		// <serviceName>刘德华</serviceName>
//		// <servicePartyId>513005063694</servicePartyId>
//		// <servicePartyName></servicePartyName>
//		// <serviceStateCd>12</serviceStateCd>
//		// <serviceTypeId>1</serviceTypeId>
//		// <staffName></staffName>
//		// <version>2012-8-29 11:28:38</version>
//		// </ComponentInfo>
//		// </ComponentListInfo>
//		// </response>http://172.19.77.12:8001/abm/services/ABMWebservice
//	}
//
//	@Test
//	public void syncDate4Prm2Crm() {
////		StringBuffer sb = new StringBuffer();
////		sb.append("<request> ");
////		sb.append("<channelId>100000</channelId>");
////		sb.append("<staffCode>BJ1001</staffCode>");
////		sb.append("<!--公共部份-->");
////		sb.append("<mailAddressId>1043</mailAddressId>");
////		sb.append("<!--代理商ID-->");
////		sb.append("<agentCode>HZDL-000518</agentCode>");
////		sb.append("<!--渠道编码-->");
////		sb.append("<agentName>AAA</agentName>");
////		sb.append("<!--渠道名称-->");
////		sb.append("<linkMobilePhone>13404171778</linkMobilePhone>");
////		sb.append("<!--联系人移动电话-->");
////		sb.append("<agentAddr>北京中国电信测试</agentAddr>");
////		sb.append("<!--代理商地址-->");
////		sb.append("<parentMailAddressId>1043</parentMailAddressId>");
////		sb.append("<!--父级代理商ID-->");
////		sb.append("<opType>1</opType>");
////		sb.append("<!--操作类型-->");
////		sb.append("<aFlag>1</aFlag>");
////		sb.append("<!--龙厅标志(1：是；0：否)-->");
////		sb.append("<agentFlag>0</agentFlag>");
////		sb.append("<!--代理商or网点标志-->");
////		sb.append("<parentAgentFlag>0</parentAgentFlag>");
////		sb.append("<!--父级渠道到标记(-1：代理商0：网点)-->");
////		sb.append("<orgTypeCd>6</orgTypeCd> ");
////		sb.append("<!--组织类型CD-->");
////		sb.append("<agentTypeFlag>1</agentTypeFlag>");
////		sb.append("<!--渠道类型 1移动 0 固网-->");
////		sb.append("<parentOrg>2005201000</parentOrg>");
////		sb.append("<!--父级组织ID-->");
////		 sb.append("<!--代理商-->");
////		 sb.append("<agentType>3</agentType>");
////		 sb.append("<!--移动代理类型 3 直销  其他为0--> ");
////		 sb.append("<linkManList>                                                              ");
////		 sb.append("<linkMan>                                                                  ");
////		 sb.append("<agentKind>1</agentKind><!--联系人类型-->                                   ");
////		 sb.append("<buniessType>1</buniessType><!--经营方式-->                                 ");
////		 sb.append("<telecomOfficial>陈腾飞</telecomOfficial><!--电信负责人-->                       ");
////		 sb.append("<officialPhone>13404171778</officialPhone><!--电信负责人电话-->                       ");
////		 sb.append("<linkManName>王尼玛</linkManName><!--代理商联系人-->                             ");
////		 sb.append("<linkPhone>5555555</linkPhone><!--代理商联系人电话-->                             ");
////		 sb.append("<linkSex>男</linkSex><!--代理商联系人性别-->                                 ");
////		 sb.append("<linkBirthday>30000111</linkBirthday><!--代理商联系人出生日期-->                   ");
////		 sb.append("<linkAddress>火星</linkAddress><!--代理商联系人住址-->                         ");
////		 sb.append("<linkEmail>jianchen@1334.com</linkEmail><!--代理商联系人邮件-->                             ");
////		 sb.append("<supType>5</supType><!--4:业务联系人 5:技术联系人 6:客服联系人 7:法人-->    ");
////		 sb.append("<state>A</state><!--状态-->                                                 ");
////		 sb.append("<cardType>1</cardType><!--证件类型-->                                       ");
////		 sb.append("<cardNum>321381238113213</cardNum><!-- 证件号码-->                                        ");
////		 sb.append("<linkPost>11111</linkPost><!--邮编-->                                           ");
////		 sb.append("</linkMan>                                                                 ");
////		 sb.append("</linkManList>                                                             ");
////		 
////		sb.append("<!--网点-->");
////		sb.append("<storeName>测试</storeName>");
////		sb.append("<!--门店名称-->");
////		sb.append("<location>大郊亭</location>");
////		sb.append("<!--网点地址-->");
////		sb.append("<direction>132</direction>");
////		sb.append("<!--方向-->");
////		sb.append("<district>1</district>");
////		sb.append("<!--行政区-->");
////		sb.append("<openDate>20121113</openDate>");
////		sb.append("<!--开庭时间-->");
////		sb.append("<storeLevel>1</storeLevel>");
////		sb.append("<!--网点星级-->");
////		sb.append("<pointType>1</pointType>");
////		sb.append("<!--网点类型-->");
////		sb.append("<managerId>1004319648</managerId>");
////		sb.append("<!--督导/门店/片区经理工号-->");
////		sb.append("<longitude>1</longitude>");
////		sb.append("<!--精度-->");
////		sb.append("<latitude>1</latitude>");
////		sb.append("<!--纬度-->");
////		sb.append("<storePhone>13404171778</storePhone>");
////		sb.append("<!--厅内电话-->");
////		sb.append("</request>");
//		String request ="<request><mailAddressId>107689</mailAddressId><agentCode>HZDL-001280</agentCode><agentName>20130225移动代理商测试</agentName><linkMobilePhone>13228282828</linkMobilePhone><agentAddr>北京</agentAddr><parentMailAddressId>2005000000</parentMailAddressId><aFlag>1</aFlag><agentFlag>0</agentFlag><parentAgentFlag>-1</parentAgentFlag><orgTypeCd>6</orgTypeCd><opType>2</opType><agentTypeFlag>1</agentTypeFlag><parentOrg>2005000000</parentOrg><agentType>3</agentType><channelManager>A00321</channelManager><channelSpecId>2</channelSpecId><linkManList><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>rr</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>4</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>test</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>5</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>test</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>6</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>rr</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>7</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan></linkManList></request>";
//		String response = agentService.syncDate4Prm2Crm(request);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><partyId>103005069681</partyId></response>
//
//	}
//
//	@Test
//	public void checkDeviceId() {
//		String sb = "<request><deviceId>10010000000009</deviceId></request>";
//		String response = agentService.checkDeviceId(sb);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg><random>207970</random></response>
//	}
//
//	// --PAD耳机密码校验
//	@Test
//	public void checkSnPwd() {
//		String sb = "<request><value>591870340a</value><operType>1</operType><deviceId>10010000000009</deviceId></request>";
//		String response = agentService.checkSnPwd(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void transmitRandom() {
//		String sb = "<request><deviceId>10010000000009</deviceId><random>659169</random></request>";
//		String response = agentService.transmitRandom(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void checkDeviceIdNew() {
//		String sb = "<request><staffNumber>BJ1001</staffNumber><operType>3</operType></request>";
//		String response = agentService.checkDeviceIdNew(sb);
//		System.out.print(response);
//	}
//
//	@Test
//	public void testsm() {
//		List a = new ArrayList();
//		try {
//			a = smService.findStaffNumberByStaffName("测试");
//			String b = smService.findStaffInfoByPartyId(Long.valueOf(1111));
//			System.out.print(a.toString());
//		} catch (BssException e) {
//			e.printStackTrace();
//		}
//
//	}
//
////	@Test
////	public void tessss() {
////		StringBuffer sb = new StringBuffer();
////
////		sb.append("<request>                                               ");
////		sb.append("<order>                                                 ");
////		sb.append("<bindPayForNbr></bindPayForNbr>                         ");
////		sb.append("<olTypeCd>2</olTypeCd>                                  ");
////		sb.append("<orderTypeId>1</orderTypeId>                            ");
////		sb.append("<partyId>103005069112</partyId>                         ");
////		sb.append("<acctCd>100006610871</acctCd>                           ");
////		sb.append("<offerSpec>                                             ");
////		sb.append("<id>902612</id>                                         ");
////		sb.append("<name>[902612]201108聊天版89元</name>                   ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("<startDt>2012-11-01</startDt>                           ");
////		sb.append("<endDt>3000-01-01</endDt>                               ");
////		sb.append("<properties></properties>                               ");
////		sb.append("</offerSpec>                                            ");
////		sb.append("<subOrder>                                              ");
////		sb.append("<orderTypeId>1</orderTypeId>                            ");
////		sb.append("<roleCd>1</roleCd>                                      ");
////		sb.append("<prodId></prodId>                                       ");
////		sb.append("<prodSpecId>378</prodSpecId>                            ");
////		sb.append("<accessNumber>15321813267</accessNumber>                ");
////		sb.append("<anId>215321813267</anId>                               ");
////		sb.append("<prod2accNbr></prod2accNbr>                             ");
////		sb.append("<anId2></anId2>                                         ");
////		sb.append("<acctCd></acctCd>                                       ");
////		sb.append("<bindPayForNbr></bindPayForNbr>                         ");
////		sb.append("<bindNumberProdSpec></bindNumberProdSpec>               ");
////		sb.append("<coLinkMan></coLinkMan>                                 ");
////		sb.append("<assistMan></assistMan>                                 ");
////		sb.append("<password></password>                                   ");
////		sb.append("<passwordType></passwordType>                           ");
////		sb.append("<prodPropertys>                                        ");
////		sb.append("<property>                                              ");
////		sb.append("<id>350050</id>                                         ");
////		sb.append("<name>信用度控制标识</name>                             ");
////		sb.append("<value>1</value>                                        ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("</property>                                             ");
////		sb.append("</prodPropertys>                                       ");
////		sb.append("<offerSpecs>                                            ");
////		sb.append("<offerSpec>                                             ");
////		sb.append("<id>24113</id>                                          ");
////		sb.append("<name>[24113]通信助理-秘书台包年套餐</name>             ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("</offerSpec>                                            ");
////		sb.append("<offerSpec>                                             ");
////		sb.append("<id>129</id>                                            ");
////		sb.append("<name>[129]校园人人看(视讯版)</name>                    ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("</offerSpec>                                            ");
////		sb.append("</offerSpecs>                                           ");
////		sb.append("<tds>                                                   ");
////		sb.append("<td>                                                    ");
////		sb.append("<terminalCode>8986031100010019646</terminalCode>        ");
////		sb.append("<terminalDevSpecId>10302057</terminalDevSpecId>         ");
////		sb.append("<terminalDevId>27343615</terminalDevId>                 ");
////		sb.append("<devModelId></devModelId>                               ");
////		sb.append("<ownerTypeCd></ownerTypeCd>                             ");
////		sb.append("<maintainTypeCd></maintainTypeCd>                       ");
////		sb.append("</td>                                                   ");
////		sb.append("</tds>                                                  ");
////		sb.append("<coupons></coupons>                                     ");
////		sb.append("</subOrder>                                             ");
////		sb.append("</order>                                                ");
////		sb.append("<areaId>10000</areaId>                                  ");
////		sb.append("<channelId>11040082</channelId>                         ");
////		sb.append("<staffCode>Bj1001</staffCode>                           ");
////		sb.append("</request>                                              ");
////
////		String response = orderService.validateCustOrder(sb.toString());
////		System.out.print(response);
////	}
//
//	@Test
//	public void computeChargeInfo() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><ol_id>100000496803</ol_id></request>";
//		String response = orderService.computeChargeInfo(sb);
//		System.out.print(response);
//	}
//
//	// 获取发票号
//	@Test
//	public void indentInvoiceNumQryIntf() {
//		String sb = "<request><requestId>20120906111111876543241003</requestId><requestTime>20120906111111</requestTime><platId>03</platId><cycleId>20120801</cycleId><staffId>100000031</staffId><invoiceType>1</invoiceType></request>";
//		String response = srService.indentInvoiceNumQryIntf(sb);
//		System.out.print(response);
//	}
//
//	// 修改发票号
//	@Test
//	public void indentInvoiceNumModIntf() {
//		String sb = "<request><channelId>110000</channelId><staffCode>BJ1001</staffCode><requestId>20121219135105100000001008</requestId><requestTime>20121219135105</requestTime><platId>08</platId><cycleId>20120801</cycleId><staffId>100000031</staffId><invoiceType>1</invoiceType><invoiceId>121231231231212122</invoiceId></request>";
//		String response = srService.indentInvoiceNumModIntf(sb);
//		System.out.print(response);
//	}
//
//	// 发票补打
//	@Test
//	public void indentInvoicePrintIntf() {
//		String sb = "<request>	<channelId>123</channelId>	<cycleId>20121128</cycleId>	<invoiceId>123456123400000057</invoiceId>	<invoiceType>1</invoiceType>	<payIndentId>CRM11290641</payIndentId>	<platId>06</platId>	<printType>1</printType>	<requestId>20121128135533101004331006</requestId>	<requestTime>20121128135533</requestTime>	<staffId>100000031</staffId></request>";
//		String response = srService.indentInvoicePrintIntf(sb);
//		System.out.print(response);
//		// {"result":"0","responseId":"20121128135533101004331006","invoiceLists":[{"invoiceItem":[],"invoiceTxt":[]}],"msg":"","payIndentId":"CRM11290630","responseTime":"20121129152950"}
//	}
//
//	@Test
//	public void queryUimNum() {
//		String sb = "<request><phoneNumber>18010022090</phoneNumber></request>";
//		String response = rscService.queryUimNum(sb);
//		System.out.print(response);
//	}
//	@Test
//	public void businissService() {
//		/** 补机补卡 */
////		 String request =
////		 "<request><order><systemId>6090010023</systemId><chargeInfo><appCharge>20</appCharge><charge>20</charge></chargeInfo><prodId>102011410398</prodId><partyId>104004494614</partyId><orderTypeId>14</orderTypeId><prodSpecId>379</prodSpecId><coLinkMan>陈霸先</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>小二</assistMan>			<tds> <!-- 终端信息 -->				<td>					<terminalCode>8986035730010051440</terminalCode> <!-- 终端设备编号 -->					<terminalDevSpecId>10302057</terminalDevSpecId> <!-- 终端设备型号 -->					<terminalDevId>24003721151</terminalDevId> <!-- 终端设备ID -->					<devModelId>2839</devModelId> <!-- 设备型号ID -->					<ownerTypeCd>1</ownerTypeCd> <!-- 终端产权类型1.自购 2.局购 -->					<maintainTypeCd>1</maintainTypeCd> <!-- 终端维护方式1.自维 2.代维 -->				</td>			</tds>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// String request =
//		// "<request><order><orderTypeId>7</orderTypeId><partyId>104008557331</partyId><prodSpecId>378</prodSpecId><prodId>102017275419</prodId><offerSpecs><offerSpec><id>600000245</id><name>(600000245)年轻群体充值100元赠30元</name><actionType>0</actionType><startDt>20121201</startDt><endDt>20130430</endDt></offerSpec></offerSpecs></order><channelId>100092756</channelId><staffCode>BJ1001</staffCode></request>";
//		// 彩铃开通
////		 String request
////		 ="<request><order><orderTypeId>18</orderTypeId><prodSpecId>378</prodSpecId><feeType>1</feeType><prodId>200004384245</prodId><anId>18910591492</anId><accessNumber>18910591492</accessNumber><acctCd>null</acctCd><partyId>1004319507</partyId><orderFlag>2</orderFlag><password><newPassword>222222</newPassword><oldPassword>937336</oldPassword><prodPwTypeCd>2</prodPwTypeCd></password></order><areaId>11000</areaId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode></request>";
//		 String request
//		 ="<request><order><orderTypeId>17</orderTypeId><prodSpecId>379</prodSpecId><systemId>6090010017</systemId><accNbr>15300303276</accNbr><offerSpecs><offerSpec><id>23768</id><actionType>2</actionType><startFashion>0</startFashion><endFashion></endFashion><property><id>13379918</id><value>13370161102</value></property></offerSpec></offerSpecs></order><channelId>-10000</channelId><staffCode>BJ_10000</staffCode><areaId>010</areaId></request>";
//		// String request =
//		// "<request> <order> <orderTypeId>17</orderTypeId> <partyId>104008557331</partyId> <prodSpecId>378</prodSpecId> <prodId>102017275419</prodId> <offerSpecs> <offerSpec> <id>600000245</id> <name>年轻群体充值100元赠30元</name> <actionType>0</actionType> <startDt>20121201</startDt> <endDt>20120430</endDt> </offerSpec> </offerSpecs> </order> <areaId>11000</areaId>   <channelId>11000</channelId> <staffCode>BJ1001</staffCode> </request>";
//		// 拆机
//		// String request =
//		// "<request>	<order>		<orderTypeId>3</orderTypeId>		<partyId>104004497104</partyId>		<prodSpecId>379</prodSpecId>		<prodId>102011412888</prodId>		<coLinkMan>陈霸先</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>业务协销人#11#1004319549#北京测试工号#C00636#企业信息化部#企业信息化部#3206000000</assistMan>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// 1171挂失 1172解挂
//		// String request =
//		// "<request><order><orderTypeId>1172</orderTypeId><prodSpecId>379</prodSpecId><accessNumber>13301055963</accessNumber><partyId>104004497104</partyId><prodId></prodId><coLinkMan></coLinkMan><coLinkNbr></coLinkNbr><assistMan></assistMan></order><areaId>10000</areaId><channelId>11040361</channelId><staffCode>bj1001</staffCode></request>";
//		// 修改产品密码18
////		 String request =
////		 "<request><order><orderTypeId>18</orderTypeId><prodSpecId>378</prodSpecId><feeType>1</feeType><prodId>200001631270</prodId><anId>13370164186</anId><accessNumber>13370164186</accessNumber><acctCd>null</acctCd><partyId>1004319237</partyId><orderFlag>2</orderFlag><password><newPassword>222222</newPassword><oldPassword>101200</oldPassword><prodPwTypeCd>2</prodPwTypeCd></password></order><areaId>11000</areaId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode></request>";
//		// 冻结1187 解冻1188
//		// String request =
//		// "<request>	<order>		<orderTypeId>1187</orderTypeId>		<partyId>104004497104</partyId>		<prodSpecId>379</prodSpecId>		<prodId>102011412888</prodId>		<coLinkMan>陈霸先</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>业务协销人#11#1004319549#北京测试工号#C00636#企业信息化部#企业信息化部#3206000000</assistMan>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// 修改产品属性
////		String request = " <request>    <areaId>11040361</areaId>    <channelId>11040361</channelId>    <staffCode>BJ1001</staffCode>    <order>      <orderTypeId>17</orderTypeId>      <partyId>104004494124</partyId>      <prodSpecId>379</prodSpecId>      <prodId>102011409908</prodId>      <offerSpecs>        <offerSpec>          <actionType>2</actionType>          <id>992018145</id>          <name>(992018145)本地漫游</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018229</id>          <name>(992018229)国内漫游</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018145</id>          <name>(992018145)本地漫游</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018229</id>          <name>(992018229)国内漫游</name>        </offerSpec>      </offerSpecs>    </order>  </request>";
////		String request ="<request><order><orderTypeId>17</orderTypeId><partyId>104002857574</partyId><prodSpecId>378</prodSpecId><accessNumber>18901395401</accessNumber><prodId>102008715409</prodId><offerSpecs><offerSpec><id>22560</id><name>通信助理-秘书台15元（计费）</name><actionType>0</actionType><startFashion>0</startFashion><endFashion></endFashion><startDt>20130108</startDt><endDt>30000131</endDt></offerSpec></offerSpecs></order><areaCode>0451</areaCode><channelId>510001</channelId><staffCode>BJ1001</staffCode></request>";
////		String request = "<request>- <order>  <orderTypeId>17</orderTypeId>   <prodSpecId>378</prodSpecId>   <partyId>104008391620</partyId>   <accessNumber>13311081830</accessNumber>   <prodId /> - <offerSpecs>- <offerSpec>  <id>20615</id>   <actionType>2</actionType>   <startFashion>0</startFashion>   <endFashion></endFashion>   <startDt />   <endDt /> - <properties>- <property>  <id>13379506</id>   <name/>   <value>13311081830</value>   </property>  </properties>  </offerSpec>  </offerSpecs>  </order>  <areaId>11000</areaId>   <channelId>11040361</channelId>   <staffCode>BJ_10020</staffCode>   </request>";
////		String request="<request>	- <order>		<orderTypeId>1179</orderTypeId>		<partyId>104003510018</partyId>		<prodSpecId>378</prodSpecId>		<accessNumber>18910444002</accessNumber>		<prodId/>		<coLinkMan/>		<coLinkNbr/>		<assistMan/>		- <prodPropertys>			- <property>				<id>100000334</id>				<name>wlane密码</name>				<value>123123</value>				<actionType>2</actionType>			</property>		</prodPropertys>	</order>	<areaId>11000</areaId>	<channelId>11040361</channelId>	<staffCode>BJ_10020</staffCode></request>";
//		String response = orderService.businessService(request);
//		System.out.print(response);
//	}
//	
//	
//	@Test
//	public void cancelOrderList(){
//	String result =	orderService.cancelOrderList("<request><channelId>95141</channelId><staffCode>BJ1001</staffCode><olId>100003761056</olId><systemId>6090010028</systemId ><groupId></groupId><openType>1</openType></request>");
//	System.out.print(result);
//		
//	}
//
//	
//	@Test
//	public void reprintReceipt(){
//		String result =	orderService.reprintReceipt("<request><ifAgreementStr>Y</ifAgreementStr><channelId>21312</channelId><staffCode>sqjl0002</staffCode><olId>100003774388</olId></request>");
//	System.out.print(result);
//	}
//	
//	@Test
//	public void checkTicket(){
//		String result  = srService.confirmSequence("<request><terminalType>60578</terminalType><terminalCode>iphone4s16hei</terminalCode><credenceNo>10201212148J4R7Z305511</credenceNo><pwd>91660118</pwd><channelId>11040082</channelId><staffCode>P0056017</staffCode></request>");
//	System.out.print(result);
//	}
//	
//	@Test
//	public void checkSequence(){
//		String result  = srService.checkSequence("<request>	<custId>18910444002</custId>	<custName>18910444002</custName>	<custType>0</custType>	<validateCode>123456</validateCode>	<platId>07</platId>	<saleNo>120000184011</saleNo>	<info>		<sailTime>20110214134033</sailTime>		<validity>0102001001320001470</validity>		<priceStd>20</priceStd>		<price>20</price>	</info>	<type>1</type>	<payInfo>		<method>503</method>		<amount>20</amount>		<appendInfo>test</appendInfo>	</payInfo>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode></request>");
//	System.out.print(result);
//	}
//	
//	@Test
//	public void agentService(){
//		String result  = rscService.getClerkId("<request><accNbr>13370161688</accNbr><channelId>51000000</channelId><staffCode>BJ1001</staffCode><areaId>45101</areaId></request>");
//	System.out.print(result);
//	}
//	
//	
//
//	@Test
//	public void queryInvoiceReprint(){
//		String result  = srService.queryInvoiceReprint("<request><bcdCode>0102001001320000787</bcdCode><password>123456</password><platId>11</platId><invoiceId>1234567890</invoiceId><staffCode>BJ1001</staffCode><channelId>11000</channelId></request>");
//	System.out.print(result);
//	}
//	
//	
//	@Test
//	public void chanarInfoList(){
////		String result  = orderService.chanarInfoList("<request><ol_id>100003747991</ol_id><channelId>12321231</channelId><staffCode>BJ1001</staffCode></request>");
////	System.out.print(result);
//	}
}
