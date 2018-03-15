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
//		String order = "{\"orderList\":{\"custOrderList\":[{\"partyId\":\"4008153037\",\"colNbr\":\"-1\",\"busiOrder\":[{\"offerNbr\":\"100122150177\",\"linkFlag\":\"N\",\"data\":{\"ooOwners\":[{\"statusCd\":\"S\",\"partyId\":\"4008153037\",\"atomActionId\":\"-1\",\"name\":\"�����\",\"state\":\"ADD\"}]},\"busiObj\":{\"offerTypeCd\":\"1\",\"instId\":\"-1\",\"name\":\"���ҵ��ģ��\",\"objId\":\"810000000042\"},\"boActionType\":{\"name\":\"����\",\"boActionTypeCd\":\"S1\",\"actionClassCd\":\"3\"},\"busiOrderInfo\":{\"statusCd\":\"S\",\"seq\":\"-1\"},\"areaId\":\"11000\"}]}],\"orderListInfo\":{\"olId\":\"-1\",\"staffId\":\"104003875394\",\"areaName\":\"������\",\"statusCd\":\"S\",\"channelId\":\"100103759\",\"olNbr\":\"-1\",\"olTypeCd\":\"1\",\"areaId\":\"11000\"}}}";
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
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg><coNbrList><coNbr>100000371586</coNbr><coNbr>100000371587</coNbr></coNbrList></response>
//	}
//
//	@Test
//	public void testIsInCustBlackList() {
//		String response = customerService
//				.blackUserCheck("<request><checkId>13</checkId><typeCode>2010566051557000</typeCode><channelId>5100000</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>��</resultMsg>
//		// <resultSign>1</resultSign>
//		// </response>
//		// ͨ��
//	}
//
//	@Test
//	public void testQueryCustGrade() {
//		String response = customerService
//				.qryUserVipType("<request><accNbr>18910263133</accNbr><channelId>95129</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>");
//		System.out.printf(response);
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg><userInfos><vipType>��ʯ</vipType></userInfos></response>
//	}
//
//	@Test
//	public void testQryPrepareOrder() {
//		String response = orderService
//				.getPreInterimBycond("<request><partyName></partyName><olTypeCd></olTypeCd><olNbr></olNbr><staffCode>BJ1001</staffCode><accessNumber></accessNumber><identifyType>13</identifyType><identityNum>2010599825851000</identityNum><startTime></startTime><endTime></endTime><channelId>11040082</channelId></request>");
//		System.out.printf(response);
//		// ��ѯ���SQL
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
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg><preOrders><preOrder><olId>100000454025</olId><partyName>����ǿ</partyName><channelId>11040361</channelId><partyId>103005065283</partyId><statusCd>2012-08-20
//		// 10:26:03</statusCd><dt>2012-08-20
//		// 10:26:03</dt><relaOlnbr></relaOlnbr><preType>2</preType><areaId>11000</areaId><relaOlId></relaOlId><channelName>�������Ӫҵ��</channelName><staffId>1004320797</staffId><areaName>������</areaName><statusCd>PW</statusCd><staffName>������</staffName><delDt>2012-8-21
//		// 10:29:33</delDt><olNbr>100000354704</olNbr><olTypeCd>5</olTypeCd><statusName>Ԥ�����ת��</statusName></preOrder></preOrders></response>
//	}
//
//	@Test
//	public void testCheckUimNo() {
//		String response = rscService
//				.checkUimNo("<request>	<anTypeCd>103</anTypeCd>	<phoneNumber>13381119709</phoneNumber>	<phoneNumberId/>	<uimNo>8986031271010204601</uimNo>	<channelId>11040611</channelId>	<staffCode>C01275</staffCode>	<areaId>10000</areaId></request>");
//		System.out.printf(response);
//		// <response><id>24003703917</id><code>8986030231010155874</code><terminalDevSpec><id>10302057</id><name>�����ֻ���</name><name>�ն˹��</name></terminalDevSpec><deviceModel><id>2839</id><name>����󸶷�-UTK-32K</name><manufacturer><id>4</id><name>��Ѷ</name></manufacturer></deviceModel><accessNumbers><accessNumber><anTypeCd>508</anTypeCd><name>CDMA��Ȩ��</name><anId>22003703917</anId><number>AAA6C1A1BD948E83</number><rscStatusCd>3</rscStatusCd><resulte>0</resulte><cause>�ɹ�</cause></accessNumber><accessNumber><anTypeCd>509</anTypeCd><name>IMSI��</name><anId>20003703917</anId><number>460030911165874</number><rscStatusCd>17</rscStatusCd><resulte>0</resulte><cause>�ɹ�</cause></accessNumber></accessNumbers><items><item><id>200220</id><name>UIM��ESN��</name><value>C315ABB1</value></item><item><id>200221</id><name>UIM��ICCID��</name><value>8986030231010155874</value></item><item><id>200222</id><name>UIM��PUK��</name><value>78199157</value></item><item><id>200223</id><name>UIM��PUK2��</name><value>34788817</value></item><item><id>200224</id><name>UIM��PIN��</name><value>1234</value></item><item><id>200225</id><name>UIM��PIN2��</name><value>22320781</value></item></items><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg></response>
//		// ͨ��
//	}
//
//	// ��17�����У�---��14����ʱԤռ��---��2��Ԥռ��
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
//	// <campType>exchangeTicket</campType><!--������exchangeTicket-convertTicket-->
//	// // </request>
//	// String s =
//	// "<request><areaId>45101</areaId><auditTicketId>510000304944</auditTicketId><channelId>510000</channelId><staffCode>1001</staffCode><campType>exchangeTicket</campType></request>";
//	// String result = srService.allocAuditTickets(s);
//	// System.out.print(result);
//	// //
//	// <response><resultCode>0</resultCode><resultmsg>�ɹ�</resultMsg></response>
//	// // ͨ��
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
//		// �ɹ�
//		// </resultMsg>
//		// </response>
//		// ͨ��
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
//	// // <resultMsg>�ɹ�</resultMsg>
//	// // <ticketList>
//	// // <ticketInfo>
//	// // <auditTicketName>�ն˵���ȯ</auditTicketName>
//	// // <auditTicketCd>6720110720W3TV2A304159</auditTicketCd>
//	// // <auditTicketId>670,000,304,158</auditTicketId>
//	// // <auditTicketType>4</auditTicketType>
//	// // <areaId>467</areaId>
//	// // <price>654</price>
//	// // <createDt>2011-07-20</createDt>
//	// // <startDt>2011-07-20</startDt>
//	// // <endDt>2011-08-20</endDt>
//	// // <statusCd>6</statusCd>
//	// // <statusName>��ʹ��</statusName>
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
//	// // <materialName>��ΪC2906</materialName>
//	// // <operationType>����ȯ�һ�</operationType>
//	// // </ticketInfo>
//	// // </ticketInfos>
//	// // </response>
//	// // ͨ��
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
//	// // <resultMsg>���ݵ���ȯ���δ���ҵ�ȯ����ȷ��ȯ��ţ�</resultMsg>
//	// // </response>
//	// // ͨ��
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
//	// // <resultMsg>�ɹ�</resultMsg>
//	// // </response>
//	// // ͨ��
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
//		// <resultMsg>�ɹ�</resultMsg>
//		// <custOrderList>
//		// <zongNum>2</zongNum>
//		// <noCnum>0</noCnum>
//		// <custOrder>
//		// <olId>100000451983</olId>
//		// <olNbr>100000352661</olNbr>
//		// <soDate>2012-7-31 16:51:27</soDate>
//		// <soStatusDt>2012-7-31 16:53:33</soStatusDt>
//		// <statusCd>C</statusCd>
//		// <statusName>���</statusName>
//		// <olTypeCd>1</olTypeCd>
//		// <olTypeName>����Ӫҵ����Ĺ��ﳵ</olTypeName>
//		// <staffName>BJ1001</staffName>
//		// <staffNumber>BJ1001</staffNumber>
//		// <channelName>�������Ӫҵ��</channelName>
//		// <channelManageCode>2501000000</channelManageCode>
//		// <boInfos>
//		// <boInfo>
//		// <boActionTypeName>����</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108������129Ԫ</name>
//		// <num></num>
//		// <statusName>���</statusName>
//		// </boInfo>
//		// <boInfo>
//		// <boActionTypeName>����</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108������129Ԫ</name>
//		// <num></num>
//		// <statusName>���</statusName>
//		// </boInfo>
//		// <boInfo>
//		// <boActionTypeName>����</boActionTypeName>
//		// <boId>100032724117</boId>
//		// <name>[901613]201108������129Ԫ</name>
//		// <num></num>
//		// <statusName>���</statusName>
//		// </boInfo>
//		// </boInfos>
//		// </custOrder>
//		// <custOrder>
//		// <olId>100000451983</olId>
//		// <olNbr>100000352661</olNbr>
//		// <soDate>2012-7-31 16:51:27</soDate>
//		// <soStatusDt>2012-7-31 16:53:33</soStatusDt>
//		// <statusCd>C</statusCd>
//		// <statusName>���</statusName>
//		// <olTypeCd>1</olTypeCd>
//		// <olTypeName>����Ӫҵ����Ĺ��ﳵ</olTypeName>
//		// <staffName>BJ1001</staffName>
//		// <staffNumber>BJ1001</staffNumber>
//		// <channelName>�������Ӫҵ��</channelName>
//		// <channelManageCode>2501000000</channelManageCode>
//		// <boInfos>
//		// </boInfos>
//		// </custOrder>
//		// </custOrderList>
//		// </response>
//		// ͨ��
//
//	}
//
//	@Test
//	public void testCommitPreOrderInfo() {
//		String request = "<request><areaId>11018</areaId><olId>100000451592</olId><staffCode>BJ1001</staffCode><channelId>11040685</channelId><orderStatus>PW</orderStatus><olTypeCd>5</olTypeCd><preOrderType>1</preOrderType></request>";
//		String result = orderService.commitPreOrderInfo(request);
//		System.out.print(result);
//		// ͨ��
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>�ɹ�</resultMsg>
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
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg></response>
//		// ͨ��
//
//	}
//
//	@Test
//	public void testCreateUserAddr() {
//		String request = "<request><areaId>10000</areaId><addrName>���ڷ�</addrName><channelId>51000000</channelId><staffCode>bj1001</staffCode></request>";
//		String result = rscService.createUserAddr(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>�ɹ�</resultMsg>
//		// <resultInfo>
//		// <addrId>122,776,089,024</addrId>
//		// <addrName>���ڷ�</addrName>
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
//		// <resultMsg>�ɹ�</resultMsg>
//		// <tmls>
//		// <tml>
//		// <tmlId>522476</tmlId>
//		// <tmlTypeCd>1</tmlTypeCd>
//		// <areaId>452</areaId>
//		// <manageCd>5224-76_76���002</manageCd>
//		// <name>�˶�������1327C</name>
//		// <description>�˶�������1327C-76_76���002</description>
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
//		// <resultMsg>�ɹ�</resultMsg>
//		// <phoneNumberList>
//		// <phoneNumberInfo>
//		// <phoneNumberId>218901036934</phoneNumberId>
//		// <phoneNumber>18901036934</phoneNumber>
//		// <anTypeCd>103</anTypeCd>
//		// <pnLevelId>10411</pnLevelId>
//		// <pnLevelName>C��_��ʮһ�����</pnLevelName>
//		// <pnLevelDesc>��</pnLevelDesc>
//		// <minCharge>0</minCharge>
//		// <preCharge>0</preCharge>
//		// <reuseFlag>��</reuseFlag>
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
//		// <response><RESULT>1</RESULT><CAUSE>���سɹ�</CAUSE><TERMINAL_DEV_ID>31602526</TERMINAL_DEV_ID><TERMINAL_CODE>8986031270010188357</TERMINAL_CODE><DEV_MODEL_NAME>����Ԥ����(˫ģ)-OTA-128K</DEV_MODEL_NAME><C_IMSI>460030931079430</C_IMSI><IMSI_STATE>3</IMSI_STATE><G_IMSI>204043641827467</G_IMSI><C_AKEY>AAA6CB2ED21DB0DF</C_AKEY><G_AKEY>AAA0471C0C0F085BDB0A3941770B268C</G_AKEY><HRPD_UPP>460030931079430@mycdma.cn</HRPD_UPP><HRPD_SS>AAA179586EA4E5B7</HRPD_SS><STORE_NAME>�ƶ�����������</STORE_NAME><HLR_CODE>CH11</HLR_CODE><UIMID>AAAE687D</UIMID><ISEVDO>0</ISEVDO><ESN>AAAE687D</ESN><ICCID>89860312700101883576</ICCID><PUK1>03560835</PUK1><PUK2>35593140</PUK2><PIN1>1234</PIN1><PIN2>47847847</PIN2></response>
//	}
//
//	@Test
//	public void queryCodeByNum() {
//		String response = rscService.queryCodeByNum("<request><num>18046543815</num></request>");
//		System.out.println(response.toString());
//		// <response><RESULT>1</RESULT><CAUSE>���سɹ�</CAUSE><TERMINAL_DEV_ID>31602526</TERMINAL_DEV_ID><TERMINAL_CODE>8986031270010188357</TERMINAL_CODE><DEV_MODEL_NAME>����Ԥ����(˫ģ)-OTA-128K</DEV_MODEL_NAME><C_IMSI>460030931079430</C_IMSI><IMSI_STATE>3</IMSI_STATE><G_IMSI>204043641827467</G_IMSI><C_AKEY>AAA6CB2ED21DB0DF</C_AKEY><G_AKEY>AAA0471C0C0F085BDB0A3941770B268C</G_AKEY><HRPD_UPP>460030931079430@mycdma.cn</HRPD_UPP><HRPD_SS>AAA179586EA4E5B7</HRPD_SS><STORE_NAME>�ƶ�����������</STORE_NAME><HLR_CODE>CH11</HLR_CODE><UIMID>AAAE687D</UIMID><ISEVDO>0</ISEVDO><ESN>AAAE687D</ESN><ICCID>89860312700101883576</ICCID><PUK1>03560835</PUK1><PUK2>35593140</PUK2><PIN1>1234</PIN1><PIN2>47847847</PIN2></response>
//	}
//
//	@Test
//	public void testQueryPhoneNumberPoolList() {
//		String request = "<request><accNbr>18910444002</accNbr><accNbrType>1</accNbrType><areaId>11000</areaId><channelId>-10020</channelId><staffCode>BJ_10020</staffCode></request>";
//		String result = rscService.queryPhoneNumberPoolList(request);
//		System.out.print(result);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>�ɹ�</resultMsg>
//		// <poolInfos>
//		// <poolInfo>
//		// <poolId>-1</poolId>
//		// <name>-----���кų�ѡ��----</name>
//		// <description></description>
//		// </poolInfo>
//		// <poolInfo>
//		// <poolId>510,029,952</poolId>
//		// <name>�����������ֻ�ά�޲�_CDMA�ų�</name>
//		// <description>�����������ֻ�ά�޲�_CDMA�ų�</description>
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
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "���»�"));
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
////				"FTTX���Ա�ʶ", "100", "0"));
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
//		// "ҵ��Э����#11#1004319549#�������Թ���#C00636#��ҵ��Ϣ����#��ҵ��Ϣ����#3206000000"));
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
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "���»�"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
////		sb.append("<bindPayForNbr>15301392723</bindPayForNbr>");
////		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "2"));
//		// sb.append(String.format("<assistMan>%s</assistMan>",
//		// "ҵ��Э����#11#1004319549#�������Թ���#C00636#��ҵ��Ϣ����#��ҵ��Ϣ����#3206000000"));
//		sb.append("</subOrder>");
//		sb.append("<listChargeInfo>");
//		sb.append("<chargeInfo>");
//		sb.append("<payMethod>10</payMethod>");
//		sb.append("<offerName>(993018678)��ͨ3Ԫ��</offerName>");
//		sb.append("<charge>2000</charge>");
//		sb.append("<servName></servName>");
//		sb.append("<acctItemTypeName>Ԥ���(�ײ�/����/���)</acctItemTypeName>");
//		sb.append("<acctItemTypeId>50002</acctItemTypeId>");
//		sb.append("<appCharge>2000</appCharge>");
//		sb.append("<specId>860143</specId>");
//		sb.append("</chargeInfo>");
//		sb.append("</listChargeInfo>");
//		sb.append("<payInfoList>");
//		sb.append("<payInfo>");
//		sb.append("<method>10</method>");
//		sb.append("<amount>2000</amount>");
//		sb.append("<appendInfo>ˢ����֧Ʊ֧������д������Ϣ</appendInfo>");
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
//				"300204", "�߽ɺ���", "18911272769", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300203", "�߽ɷ�ʽ", "2", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"400080840", "�ײ͵�������", "2", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"2100315", "��ѡ����", "13311111111", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"2100314", "����ѡ", "1", "0"));
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>", "10001",
//				"������ʽ", "0", "0"));
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
//		// <resultMsg>�ɹ�</resultMsg>
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
////			sb.append("<assistManInfoList><assistManInfo><staffTypeName>��չ��</staffTypeName><staffType>11</staffType><staffNumber>bj1001</staffNumber><staffName>chentengfei</staffName><staffId>132131</staffId><orgName>��ҵ</orgName><orgId>1</orgId></assistManInfo><assistManInfo><staffTypeName>��չ��</staffTypeName><staffType>13</staffType><staffNumber>bj1001</staffNumber><staffName>chentengfei</staffName><staffId>132131</staffId><orgName>��ҵ</orgName><orgId>1</orgId></assistManInfo></assistManInfoList>");
//			sb.append("<offerSpecs>");
//			sb.append("<offerSpec><id>900595</id><name>�����캽��ҵ���ײͣ�����-60</name><actionType>0</actionType><startDt></startDt><endDt></endDt><startFashion>0</startFashion></offerSpec>");
//			sb.append("<offerSpec><id>992018145</id><name>(992018145)��������</name><actionType>0</actionType><startDt></startDt><endDt></endDt><startFashion>0</startFashion></offerSpec>");
//			sb.append("</offerSpecs>");
//			// sb.append("<prodPropertys>");
//			// sb.append(String.format(
//			// "<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//			// " 111111116", "Э����",
//			// "ҵ��Э����#11#1004320797#�������Թ���#BJ1001#��ҵ��Ϣ����#��ҵ��Ϣ����#2500000000",
//			// "0"));
//			// sb.append("</prodPropertys>");
//			sb.append(String.format("<prodSpecId>%s</prodSpecId>", "378"));
//			sb.append(String.format("<roleCd>%s</roleCd>", "1"));
////			sb.append(String.format("<prodId>%s</prodId>", ""));
//			sb.append(String.format("<anId>%s</anId>", "218901076493"));
//			sb.append(String.format("<coLinkMan>%s</coLinkMan>", "��˿1"));
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
////			sb.append("<offerName>(992018127)����+���ڳ�;������ͨ��</offerName>");
////			sb.append("<charge>10000</charge>");
////			sb.append("<servName></servName>");
////			sb.append("<acctItemTypeName>���ʳ�;Ѻ��</acctItemTypeName>");
////			sb.append("<acctItemTypeId>3005</acctItemTypeId>");
////			sb.append("<appCharge>10000</appCharge>");
////			sb.append("<specId>992018229</specId>");
////			sb.append("</chargeInfo>");
////			sb.append("<chargeInfo>");
////			sb.append("<payMethod>500</payMethod>");
////			sb.append("<offerName>����</offerName>");
////			sb.append("<charge>20</charge>");
////			sb.append("<servName></servName>");
////			sb.append("<acctItemTypeName>����</acctItemTypeName>");
////			sb.append("<acctItemTypeId>90013</acctItemTypeId>");
////			sb.append("<appCharge>20</appCharge>");
////			sb.append("<specId>378</specId>");
////			sb.append("</chargeInfo>");
////			sb.append("</listChargeInfo>");
////			sb.append("<payInfoList>");
////			sb.append("<payInfo>");
////			sb.append("<method>500</method>");
////			sb.append("<amount>20</amount>");
////			sb.append("<appendInfo>����68226</appendInfo>");
////			sb.append("</payInfo>");
////			sb.append("</payInfoList>");
//			sb.append("</order>");
//			sb.append("<areaId>11000</areaId>");
//			sb.append("<channelId>95141</channelId>");
//			sb.append("<staffCode>P0059125</staffCode>");
//			sb.append("</request>");
//			// String request
//			// ="<request><order><olTypeCd>7</olTypeCd><orderTypeId>1</orderTypeId><partyId>103005069112</partyId><acctCd>100006610413</acctCd><offerSpec><id>25000</id><name>(25000)����3G�ײ������-59</name><actionType>0</actionType><startDt>2012-15-11</startDt><endDt>3000-01-01</endDt></offerSpec><subOrder><roleCd>1</roleCd><orderTypeId>1</orderTypeId><prodId></prodId><prodSpecId>378</prodSpecId><accessNumber>15321812357</accessNumber><anId>215321812357</anId><assistMan>BJ1001</assistMan><prodPropertys><property><id>350050</id><name>���öȿ��Ʊ�ʶ</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>993018693</id><name>(993018693)���з��ڸ���</name><actionType>0</actionType></offerSpec></offerSpecs><tds><td><terminalCode>8986031100010019646</terminalCode><terminalDevSpecId>10302057</terminalDevSpecId><terminalDevId>27343615</terminalDevId><devModelId>2866</devModelId><ownerTypeCd>1</ownerTypeCd><maintainTypeCd>1</maintainTypeCd></td></tds></subOrder></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>bj1001</staffCode></request>";
//			// response = orderService.paymentCountList(request);
//			// response = orderService.orderSubmit(sb.toString());
//			// <request><order><olTypeCd>7</olTypeCd><orderTypeId>1</orderTypeId><partyId>103005069112</partyId><acctCd>100006610413</acctCd><offerSpec><id>900521</id><name>(900521)��ͨ3Ԫ��</name><actionType>0</actionType><startDt>2012-15-06</startDt><endDt>3000-01-01</endDt></offerSpec></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>bj1001</staffCode></request>
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
////	String request = "<request><order><partyId>200003302600</partyId><orderTypeId>1</orderTypeId><systemId>6090010023</systemId><roleCd>90000</roleCd><prodSpecId>600012009</prodSpecId><acctCd>9109071806506803</acctCd><coLinkMan>CTF</coLinkMan><coLinkNbr>58504439</coLinkNbr><olTypeCd>2</olTypeCd><offerSpecs><offerSpec><id>20080205</id><name>�����Ϳ���ں��ײ�-8M</name><actionType>0</actionType></offerSpec><offerSpec><id>20081208</id><name>(20081208)�����Ϳ���ں��ײ�-8M(һ�꣩��2012��</name><actionType>0</actionType></offerSpec></offerSpecs><subOrder><orderTypeId>1</orderTypeId><roleCd>249</roleCd><prodSpecId>10</prodSpecId><accessNumber>B23285995</accessNumber><anId>120052945422</anId><prod2accNbr>01010717594</prod2accNbr><anId2>120052945422</anId2><assistMan>Bj1001</assistMan><password>209126</password><prodPropertys><property><id>30018</id><name>Զ��һ��-��Ϊ�����ģ�</name><value>100000548</value><actionType>0</actionType></property><property><id>900000013</id><name>������</name><value>11005</value><actionType>0</actionType></property><property><id>310056</id><name>��-������Ӫҵ��</name><value>6</value><actionType>0</actionType></property><property><id>3210047</id><name>Ggg</name><value>122776091736</value><actionType>0</actionType></property><property><id>310051</id><name>Ҫ���������</name><value>2013-01-16</value><actionType>0</actionType></property><property><id>310142</id><name>������-��ͤ</name><value>100000001718</value><actionType>0</actionType></property><property><id>12389</id><name>LAN</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>990015002</id><name>���Ƽ�ͥ�ն�500ԪѺ��</name><actionType>0</actionType></offerSpec><offerSpec><id>980010250</id><name>��ͨ�������èѺ��360Ԫ</name><actionType>0</actionType></offerSpec><offerSpec><id>992018603</id><name>4M�����������12M��ѡ��</name><actionType>0</actionType></offerSpec><offerSpec><id>992015285</id><name>�������-8M����ʱסլ���£�2012�棩-LAN</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec><offerSpec><id>100172</id><name>LAN�˿�ռ�÷�10Ԫ</name><actionType>0</actionType></offerSpec></offerSpecs></subOrder><listChargeInfo><chargeInfo><payMethod>500</payMethod><offerName>�����Ϳ���ں��ײ�-8M(һ�꣩��2012��</offerName><charge>0</charge><servName></servName><acctItemTypeName>Ԥ���(�ײ�/����/���)</acctItemTypeName><acctItemTypeId>50002</acctItemTypeId><appCharge>180000</appCharge><specId>20081208</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName>���Ƽ�ͥ�ն�500ԪѺ��</offerName><charge>0</charge><servName></servName><acctItemTypeName>�����֤��</acctItemTypeName><acctItemTypeId>1500</acctItemTypeId><appCharge>50000</appCharge><specId>990015002</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName>��ͨ�������èѺ��360Ԫ</offerName><charge>0</charge><servName></servName><acctItemTypeName>��ͨ�������èѺ��</acctItemTypeName><acctItemTypeId>1001</acctItemTypeId><appCharge>36000</appCharge><specId>980010250</specId></chargeInfo><chargeInfo><payMethod>500</payMethod><offerName></offerName><charge>0</charge><servName></servName><acctItemTypeName>����_�ۺϹ��Ϸ�</acctItemTypeName><acctItemTypeId>121</acctItemTypeId><appCharge>30000</appCharge><specId>10</specId></chargeInfo></listChargeInfo><payInfoList><payInfo><method>500</method><amount>296000</amount><appendInfo>�ֽ�</appendInfo></payInfo></payInfoList></order><areaId>10000</areaId><channelId>11040082</channelId><staffCode>Bj1001</staffCode></request>";
//	String request ="<request>	<order>		<prodStatusCd>0</prodStatusCd>		<ifAgreementStr>Y</ifAgreementStr>		<assistManInfoList>			<assistManInfo>				<staffTypeName/>				<staffType>16</staffType>				<staffNumber/>				<staffName/>				<staffId>104008165337</staffId>				<orgName/>				<orgId/>			</assistManInfo>			<assistManInfo>				<staffTypeName>��������</staffTypeName>				<staffType>13</staffType>				<staffNumber/>				<staffName/>				<staffId>104008165337</staffId>				<orgName/>				<orgId/>			</assistManInfo>		</assistManInfoList>		<partyId>104009169886</partyId>		<orderTypeId>1</orderTypeId>		<systemId>6090010023</systemId>		<roleCd>1</roleCd>		<prodSpecId>378</prodSpecId>		<accessNumber>18910891155</accessNumber>		<anId>218910891155</anId>		<acctCd></acctCd>		<coLinkMan>�۴潫</coLinkMan>		<olTypeCd>7</olTypeCd>		<prodProperties>			<property>				<id>350050</id>				<name>���öȿ��Ʊ�ʶ</name>				<value>1</value>				<actionType>0</actionType>			</property>		</prodProperties>		<offerSpecs>			<offerSpec>				<id>992018126</id>				<name>����-���ڳ�;+����ͨ��</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018229</id>				<name>����-��������</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018143</id>				<name>����-���߿��(1X)</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>992018156</id>				<name>����-���߿��(3G)</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>700000030</id>				<name>�߲��������ܣ���ѣ�</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>720000021</id>				<name>�����Ķ�����ѣ�</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>720000023</id>				<name>����Ϸ����ѣ�</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>902612</id>				<name>201108�����89Ԫ</name>				<startFashion>0</startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>25376</id>				<name>�����ʷ�(2012)-�ھ���</name>				<startFashion></startFashion>				<actionType>0</actionType>			</offerSpec>			<offerSpec>				<id>8401</id>				<name>iPhone4S16GB-ֱ��1200�Ż�-289Ԫ-�ն˲���-24��-��ữ</name>				<startFashion></startFashion>				<actionType>0</actionType>				<properties>					<property>						<id>23990</id>						<name>Ԥ����(Ԫ)</name>						<value>4081</value>					</property>					<property>						<id>999</id>						<name>�ۿ�</name>						<value>100</value>					</property>				</properties>			</offerSpec>		</offerSpecs>	</order>	<areaId>10000</areaId>	<channelId>11040082</channelId>	<staffCode>BJ1001</staffCode></request>";
////		String request ="<request><order><busiOrderTimeList>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>			<property>				<id>2862</id>				<value>180027404822</value>			</property>		</busiOrderTimeList><prodStatusCd>0</prodStatusCd><ifAgreementStr>Y</ifAgreementStr><partyId>104009169886</partyId><orderTypeId>1</orderTypeId><systemId>6090010023</systemId><roleCd>90000</roleCd><prodSpecId>600012009</prodSpecId><acctCd>9108110433002393</acctCd><coLinkMan>�۴潫</coLinkMan><coLinkNbr>76787663</coLinkNbr><olTypeCd>2</olTypeCd><offerSpecs><offerSpec><id>20080205</id><name>�����Ϳ���ں��ײ�-8M</name><startFashion></startFashion><actionType>0</actionType></offerSpec></offerSpecs><subOrder><orderTypeId>1</orderTypeId><roleCd>249</roleCd><prodSpecId>10</prodSpecId><accessNumber>B23504663</accessNumber><anId>120053027300</anId><prod2accNbr>01010791730</prod2accNbr><anId2>120053027300</anId2><assistMan>Sqjl0002</assistMan><password>519816</password><prodPropertys><property><id>30018</id><name>Զ��һ��</name><value>100000536</value><actionType>0</actionType></property><property><id>900000013</id><name>������</name><value>11002</value><actionType>0</actionType></property><property><id>310056</id><name>��-������Ӫҵ��</name><value>6</value><actionType>0</actionType></property><property><id>3210047</id><name>��</name><value>122776188072</value><actionType>0</actionType></property><property><id>310051</id><name>Ҫ���������</name><value>2013-03-03</value><actionType>0</actionType></property><property><id>310142</id><name>�۴潫-uuuu</name><value>219887</value><actionType>0</actionType></property><property><id>12389</id><name>LAN</name><value>1</value><actionType>0</actionType></property></prodPropertys><offerSpecs><offerSpec><id>112518697</id><name>���LAN-8M����-LAN</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec><offerSpec><id>100172</id><name>LAN�˿�ռ�÷�10Ԫ</name><startFashion>0</startFashion><actionType>0</actionType></offerSpec></offerSpecs></subOrder><subOrder><orderTypeId>1</orderTypeId><roleCd>251</roleCd><prodId>103030348032</prodId><prodSpecId>378</prodSpecId><accessNumber>18910709466</accessNumber><assistMan>Sqjl0002</assistMan><prodPropertys><property><id>310056</id><name>��-������Ӫҵ��</name><value>6</value><actionType>0</actionType></property><property><id>310051</id><name>Ҫ���������</name><value>2013-03-03</value><actionType>0</actionType></property><property><id>30018</id><name>Զ��һ��</name><value>100000536</value><actionType>0</actionType></property></prodPropertys></subOrder></order><areaId>10000</areaId><channelId>100092996</channelId><staffCode>Sqjl0002</staffCode></request>";
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
//		// <orgName>�����������������</orgName>
//		// <posts/>
//		// <staffNumberInfo>
//		// <party>
//		// <area>
//		// <areaId>11000</areaId>
//		// <name>������</name>
//		// </area>
//		// <name>�����������������1P0024300</name>
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
//		// <response> <resultCode>0</resultCode> <resultMsg>�ɹ�</resultMsg>
//		// <partyInfos> <partyInfo> <identityNum>2010568309817000</identityNum>
//		// <name>�������Ų���ʹ��</name> </partyInfo> <partyInfo>
//		// <identityNum>2010568309827000</identityNum> <name>�������Ų���ʹ��</name>
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
//		// <response> <resultCode>0</resultCode> <resultMsg>�ɹ�</resultMsg>
//		// <partyInfo> <staffNumber>VG1432</staffNumber>
//		// <partyName>�������Ų���ʹ��</partyName> </partyInfo> </response>
//	}
//
//	@Test
//	public void getBrandLevelDetail() {
//		String result = customerService
//				.getBrandLevelDetail("<request><staffCode>BJ_10000</staffCode><areaId>010</areaId><channelId>-10000</channelId><accNum>18001223533</accNum></request>");
////				.getBrandLevelDetail("<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><accNum>13404171778</accNum></request>");
//		System.out.print(result);
//		// ͨ
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>�ɹ�</resultMsg>
//		// <partyBrand>
//		// <prodId>102013318985</prodId>
//		// <partyName>����ϸ��</partyName>
//		// <identityNum>391623195911291128</identityNum>
//		// <contactPhone>96325874 </contactPhone>
//		// <inNetDate>2011-08-02 22:23:52</inNetDate>
//		// <creditNumber>���ö��</creditNumber>
//		// <partyAddress>��ͤ</partyAddress>
//		// <partyPost>100000</partyPost>
//		// <identidiesTypeCd>1</identidiesTypeCd>
//		// <partyStatus>1</partyStatus>
//		// <greatLevel>���ֵȼ�</greatLevel><!--���ֵȼ�-->
//		// <brandCode></brandCode>
//		// <managerName></managerName>
//		// <vprepayFlag>����</vprepayFlag>
//		// <prodSpecId>378</prodSpecId>
//		// <userType>����</userType>
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
//		sb.append("		<coLinkMan>�°���</coLinkMan>                                                       ");
//		sb.append("		<coLinkNbr>13301173555</coLinkNbr>                                                  ");
//		sb.append("		<assistMan>С��</assistMan>                                                         ");
//		sb.append("			<tds> <!-- �ն���Ϣ -->                                                           ");
//		sb.append("				<td>                                                                            ");
//		sb.append("					<terminalCode>8986035730010051440</terminalCode> <!-- �ն��豸��� -->        ");
//		sb.append("					<terminalDevSpecId>10302057</terminalDevSpecId> <!-- �ն��豸�ͺ� -->         ");
//		sb.append("					<terminalDevId>24003721151</terminalDevId> <!-- �ն��豸ID -->                ");
//		sb.append("					<devModelId>2839</devModelId> <!-- �豸�ͺ�ID -->                             ");
//		sb.append("					<ownerTypeCd>1</ownerTypeCd> <!-- �ն˲�Ȩ����1.�Թ� 2.�ֹ� -->               ");
//		sb.append("					<maintainTypeCd>1</maintainTypeCd> <!-- �ն�ά����ʽ1.��ά 2.��ά -->         ");
//		sb.append("				</td>                                                                           ");
//		sb.append("			</tds>                                                                            ");
//		// sb.append("				<coupons> <!-- ��Ʒ��Ϣ -->                                                     ");
//		// sb.append("				<coupon>                                                                        ");
//		// sb.append("					<couponcode>900000003</couponcode> <!-- ��Ʒ��ʵ���ţ�����Ϊ�գ� -->          ");
//		// sb.append("					<couponspecid>379</couponspecid> <!-- ��Ʒ��� -->                            ");
//		// sb.append("					<storeId></storeId> <!-- �ֿ�id������Ϊ�գ� -->                               ");
//		// sb.append("					<storeName>NB</storeName> <!-- �ֿ����ƣ�����Ϊ�գ�-->                        ");
//		// sb.append("					<chargeItemCd>1</chargeItemCd> <!-- �շ�ϸ����� -->                          ");
//		// sb.append("					<couponCharge>249</couponCharge> <!-- Ӧ�շ��� -->                            ");
//		// sb.append("					<count>1</count> <!-- ��Ʒ������ -->                                          ");
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
//		// -----ͣ������|ͣ�����Ÿ���
//		eb.append("<request>                                                                   ");
//		eb.append("	<order>                                                                    ");
//		eb.append("		<orderTypeId>19</orderTypeId><!--19Ϊͣ������|20Ϊͣ�����Ÿ���-->        ");
//		eb.append("		<partyId>104004497104</partyId>                                          ");
//		eb.append("		<prodSpecId>379</prodSpecId>                                             ");
//		eb.append("		<prodId>102011412888</prodId>                                            ");
//		eb.append("		<coLinkMan>�°���</coLinkMan>                                            ");
//		eb.append("		<coLinkNbr>13301173555</coLinkNbr>                                       ");
//		eb.append("		<assistMan>С��</assistMan>                                              ");
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
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>���»�</serviceName><managerId>123</managerId><linkMan>123</linkMan><linkNbr>123</linkNbr><servicePartyId>513005063694</servicePartyId><buildingId>1</buildingId><buildingType>1</buildingType></request>";
//		String response = orderService.addSerivceAcct(sb);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>���������ʻ��ɹ�</resultMsg></response>
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
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>���»�</serviceName><curPage>1</curPage><pageSize>10</pageSize></request>";
//		String response = orderService.querySerivceAcct(sb);
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>�ɹ�</resultMsg>
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
//		// <serviceName>���»�</serviceName>
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
////		sb.append("<!--��������-->");
////		sb.append("<mailAddressId>1043</mailAddressId>");
////		sb.append("<!--������ID-->");
////		sb.append("<agentCode>HZDL-000518</agentCode>");
////		sb.append("<!--��������-->");
////		sb.append("<agentName>AAA</agentName>");
////		sb.append("<!--��������-->");
////		sb.append("<linkMobilePhone>13404171778</linkMobilePhone>");
////		sb.append("<!--��ϵ���ƶ��绰-->");
////		sb.append("<agentAddr>�����й����Ų���</agentAddr>");
////		sb.append("<!--�����̵�ַ-->");
////		sb.append("<parentMailAddressId>1043</parentMailAddressId>");
////		sb.append("<!--����������ID-->");
////		sb.append("<opType>1</opType>");
////		sb.append("<!--��������-->");
////		sb.append("<aFlag>1</aFlag>");
////		sb.append("<!--������־(1���ǣ�0����)-->");
////		sb.append("<agentFlag>0</agentFlag>");
////		sb.append("<!--������or�����־-->");
////		sb.append("<parentAgentFlag>0</parentAgentFlag>");
////		sb.append("<!--�������������(-1��������0������)-->");
////		sb.append("<orgTypeCd>6</orgTypeCd> ");
////		sb.append("<!--��֯����CD-->");
////		sb.append("<agentTypeFlag>1</agentTypeFlag>");
////		sb.append("<!--�������� 1�ƶ� 0 ����-->");
////		sb.append("<parentOrg>2005201000</parentOrg>");
////		sb.append("<!--������֯ID-->");
////		 sb.append("<!--������-->");
////		 sb.append("<agentType>3</agentType>");
////		 sb.append("<!--�ƶ��������� 3 ֱ��  ����Ϊ0--> ");
////		 sb.append("<linkManList>                                                              ");
////		 sb.append("<linkMan>                                                                  ");
////		 sb.append("<agentKind>1</agentKind><!--��ϵ������-->                                   ");
////		 sb.append("<buniessType>1</buniessType><!--��Ӫ��ʽ-->                                 ");
////		 sb.append("<telecomOfficial>���ڷ�</telecomOfficial><!--���Ÿ�����-->                       ");
////		 sb.append("<officialPhone>13404171778</officialPhone><!--���Ÿ����˵绰-->                       ");
////		 sb.append("<linkManName>������</linkManName><!--��������ϵ��-->                             ");
////		 sb.append("<linkPhone>5555555</linkPhone><!--��������ϵ�˵绰-->                             ");
////		 sb.append("<linkSex>��</linkSex><!--��������ϵ���Ա�-->                                 ");
////		 sb.append("<linkBirthday>30000111</linkBirthday><!--��������ϵ�˳�������-->                   ");
////		 sb.append("<linkAddress>����</linkAddress><!--��������ϵ��סַ-->                         ");
////		 sb.append("<linkEmail>jianchen@1334.com</linkEmail><!--��������ϵ���ʼ�-->                             ");
////		 sb.append("<supType>5</supType><!--4:ҵ����ϵ�� 5:������ϵ�� 6:�ͷ���ϵ�� 7:����-->    ");
////		 sb.append("<state>A</state><!--״̬-->                                                 ");
////		 sb.append("<cardType>1</cardType><!--֤������-->                                       ");
////		 sb.append("<cardNum>321381238113213</cardNum><!-- ֤������-->                                        ");
////		 sb.append("<linkPost>11111</linkPost><!--�ʱ�-->                                           ");
////		 sb.append("</linkMan>                                                                 ");
////		 sb.append("</linkManList>                                                             ");
////		 
////		sb.append("<!--����-->");
////		sb.append("<storeName>����</storeName>");
////		sb.append("<!--�ŵ�����-->");
////		sb.append("<location>��ͤ</location>");
////		sb.append("<!--�����ַ-->");
////		sb.append("<direction>132</direction>");
////		sb.append("<!--����-->");
////		sb.append("<district>1</district>");
////		sb.append("<!--������-->");
////		sb.append("<openDate>20121113</openDate>");
////		sb.append("<!--��ͥʱ��-->");
////		sb.append("<storeLevel>1</storeLevel>");
////		sb.append("<!--�����Ǽ�-->");
////		sb.append("<pointType>1</pointType>");
////		sb.append("<!--��������-->");
////		sb.append("<managerId>1004319648</managerId>");
////		sb.append("<!--����/�ŵ�/Ƭ��������-->");
////		sb.append("<longitude>1</longitude>");
////		sb.append("<!--����-->");
////		sb.append("<latitude>1</latitude>");
////		sb.append("<!--γ��-->");
////		sb.append("<storePhone>13404171778</storePhone>");
////		sb.append("<!--���ڵ绰-->");
////		sb.append("</request>");
//		String request ="<request><mailAddressId>107689</mailAddressId><agentCode>HZDL-001280</agentCode><agentName>20130225�ƶ������̲���</agentName><linkMobilePhone>13228282828</linkMobilePhone><agentAddr>����</agentAddr><parentMailAddressId>2005000000</parentMailAddressId><aFlag>1</aFlag><agentFlag>0</agentFlag><parentAgentFlag>-1</parentAgentFlag><orgTypeCd>6</orgTypeCd><opType>2</opType><agentTypeFlag>1</agentTypeFlag><parentOrg>2005000000</parentOrg><agentType>3</agentType><channelManager>A00321</channelManager><channelSpecId>2</channelSpecId><linkManList><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>rr</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>4</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>test</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>5</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>test</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>6</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan><linkMan><agentKind>0</agentKind><buniessType>0</buniessType><telecomOfficial></telecomOfficial><officialPhone>13228282828</officialPhone><linkManName>rr</linkManName><linkPhone>13228282828</linkPhone><linkSex></linkSex><linkBirthday></linkBirthday><linkAddress></linkAddress><linkEmail>222@11.com</linkEmail><supType>7</supType><state>A</state><cardType></cardType><cardNum></cardNum><linkPost></linkPost></linkMan></linkManList></request>";
//		String response = agentService.syncDate4Prm2Crm(request);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg><partyId>103005069681</partyId></response>
//
//	}
//
//	@Test
//	public void checkDeviceId() {
//		String sb = "<request><deviceId>10010000000009</deviceId></request>";
//		String response = agentService.checkDeviceId(sb);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>�ɹ�</resultMsg><random>207970</random></response>
//	}
//
//	// --PAD��������У��
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
//			a = smService.findStaffNumberByStaffName("����");
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
////		sb.append("<name>[902612]201108�����89Ԫ</name>                   ");
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
////		sb.append("<name>���öȿ��Ʊ�ʶ</name>                             ");
////		sb.append("<value>1</value>                                        ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("</property>                                             ");
////		sb.append("</prodPropertys>                                       ");
////		sb.append("<offerSpecs>                                            ");
////		sb.append("<offerSpec>                                             ");
////		sb.append("<id>24113</id>                                          ");
////		sb.append("<name>[24113]ͨ������-����̨�����ײ�</name>             ");
////		sb.append("<actionType>0</actionType>                              ");
////		sb.append("</offerSpec>                                            ");
////		sb.append("<offerSpec>                                             ");
////		sb.append("<id>129</id>                                            ");
////		sb.append("<name>[129]У԰���˿�(��Ѷ��)</name>                    ");
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
//	// ��ȡ��Ʊ��
//	@Test
//	public void indentInvoiceNumQryIntf() {
//		String sb = "<request><requestId>20120906111111876543241003</requestId><requestTime>20120906111111</requestTime><platId>03</platId><cycleId>20120801</cycleId><staffId>100000031</staffId><invoiceType>1</invoiceType></request>";
//		String response = srService.indentInvoiceNumQryIntf(sb);
//		System.out.print(response);
//	}
//
//	// �޸ķ�Ʊ��
//	@Test
//	public void indentInvoiceNumModIntf() {
//		String sb = "<request><channelId>110000</channelId><staffCode>BJ1001</staffCode><requestId>20121219135105100000001008</requestId><requestTime>20121219135105</requestTime><platId>08</platId><cycleId>20120801</cycleId><staffId>100000031</staffId><invoiceType>1</invoiceType><invoiceId>121231231231212122</invoiceId></request>";
//		String response = srService.indentInvoiceNumModIntf(sb);
//		System.out.print(response);
//	}
//
//	// ��Ʊ����
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
//		/** �������� */
////		 String request =
////		 "<request><order><systemId>6090010023</systemId><chargeInfo><appCharge>20</appCharge><charge>20</charge></chargeInfo><prodId>102011410398</prodId><partyId>104004494614</partyId><orderTypeId>14</orderTypeId><prodSpecId>379</prodSpecId><coLinkMan>�°���</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>С��</assistMan>			<tds> <!-- �ն���Ϣ -->				<td>					<terminalCode>8986035730010051440</terminalCode> <!-- �ն��豸��� -->					<terminalDevSpecId>10302057</terminalDevSpecId> <!-- �ն��豸�ͺ� -->					<terminalDevId>24003721151</terminalDevId> <!-- �ն��豸ID -->					<devModelId>2839</devModelId> <!-- �豸�ͺ�ID -->					<ownerTypeCd>1</ownerTypeCd> <!-- �ն˲�Ȩ����1.�Թ� 2.�ֹ� -->					<maintainTypeCd>1</maintainTypeCd> <!-- �ն�ά����ʽ1.��ά 2.��ά -->				</td>			</tds>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// String request =
//		// "<request><order><orderTypeId>7</orderTypeId><partyId>104008557331</partyId><prodSpecId>378</prodSpecId><prodId>102017275419</prodId><offerSpecs><offerSpec><id>600000245</id><name>(600000245)����Ⱥ���ֵ100Ԫ��30Ԫ</name><actionType>0</actionType><startDt>20121201</startDt><endDt>20130430</endDt></offerSpec></offerSpecs></order><channelId>100092756</channelId><staffCode>BJ1001</staffCode></request>";
//		// ���忪ͨ
////		 String request
////		 ="<request><order><orderTypeId>18</orderTypeId><prodSpecId>378</prodSpecId><feeType>1</feeType><prodId>200004384245</prodId><anId>18910591492</anId><accessNumber>18910591492</accessNumber><acctCd>null</acctCd><partyId>1004319507</partyId><orderFlag>2</orderFlag><password><newPassword>222222</newPassword><oldPassword>937336</oldPassword><prodPwTypeCd>2</prodPwTypeCd></password></order><areaId>11000</areaId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode></request>";
//		 String request
//		 ="<request><order><orderTypeId>17</orderTypeId><prodSpecId>379</prodSpecId><systemId>6090010017</systemId><accNbr>15300303276</accNbr><offerSpecs><offerSpec><id>23768</id><actionType>2</actionType><startFashion>0</startFashion><endFashion></endFashion><property><id>13379918</id><value>13370161102</value></property></offerSpec></offerSpecs></order><channelId>-10000</channelId><staffCode>BJ_10000</staffCode><areaId>010</areaId></request>";
//		// String request =
//		// "<request> <order> <orderTypeId>17</orderTypeId> <partyId>104008557331</partyId> <prodSpecId>378</prodSpecId> <prodId>102017275419</prodId> <offerSpecs> <offerSpec> <id>600000245</id> <name>����Ⱥ���ֵ100Ԫ��30Ԫ</name> <actionType>0</actionType> <startDt>20121201</startDt> <endDt>20120430</endDt> </offerSpec> </offerSpecs> </order> <areaId>11000</areaId>   <channelId>11000</channelId> <staffCode>BJ1001</staffCode> </request>";
//		// ���
//		// String request =
//		// "<request>	<order>		<orderTypeId>3</orderTypeId>		<partyId>104004497104</partyId>		<prodSpecId>379</prodSpecId>		<prodId>102011412888</prodId>		<coLinkMan>�°���</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>ҵ��Э����#11#1004319549#�������Թ���#C00636#��ҵ��Ϣ����#��ҵ��Ϣ����#3206000000</assistMan>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// 1171��ʧ 1172���
//		// String request =
//		// "<request><order><orderTypeId>1172</orderTypeId><prodSpecId>379</prodSpecId><accessNumber>13301055963</accessNumber><partyId>104004497104</partyId><prodId></prodId><coLinkMan></coLinkMan><coLinkNbr></coLinkNbr><assistMan></assistMan></order><areaId>10000</areaId><channelId>11040361</channelId><staffCode>bj1001</staffCode></request>";
//		// �޸Ĳ�Ʒ����18
////		 String request =
////		 "<request><order><orderTypeId>18</orderTypeId><prodSpecId>378</prodSpecId><feeType>1</feeType><prodId>200001631270</prodId><anId>13370164186</anId><accessNumber>13370164186</accessNumber><acctCd>null</acctCd><partyId>1004319237</partyId><orderFlag>2</orderFlag><password><newPassword>222222</newPassword><oldPassword>101200</oldPassword><prodPwTypeCd>2</prodPwTypeCd></password></order><areaId>11000</areaId><channelId>11040361</channelId><staffCode>BJ_10020</staffCode></request>";
//		// ����1187 �ⶳ1188
//		// String request =
//		// "<request>	<order>		<orderTypeId>1187</orderTypeId>		<partyId>104004497104</partyId>		<prodSpecId>379</prodSpecId>		<prodId>102011412888</prodId>		<coLinkMan>�°���</coLinkMan>		<coLinkNbr>13301173555</coLinkNbr>		<assistMan>ҵ��Э����#11#1004319549#�������Թ���#C00636#��ҵ��Ϣ����#��ҵ��Ϣ����#3206000000</assistMan>	</order>	<channelId>11040361</channelId>	<staffCode>bj1001</staffCode>	<areaId>10000</areaId></request>";
//		// �޸Ĳ�Ʒ����
////		String request = " <request>    <areaId>11040361</areaId>    <channelId>11040361</channelId>    <staffCode>BJ1001</staffCode>    <order>      <orderTypeId>17</orderTypeId>      <partyId>104004494124</partyId>      <prodSpecId>379</prodSpecId>      <prodId>102011409908</prodId>      <offerSpecs>        <offerSpec>          <actionType>2</actionType>          <id>992018145</id>          <name>(992018145)��������</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018229</id>          <name>(992018229)��������</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018145</id>          <name>(992018145)��������</name>        </offerSpec>        <offerSpec>          <actionType>2</actionType>          <id>992018229</id>          <name>(992018229)��������</name>        </offerSpec>      </offerSpecs>    </order>  </request>";
////		String request ="<request><order><orderTypeId>17</orderTypeId><partyId>104002857574</partyId><prodSpecId>378</prodSpecId><accessNumber>18901395401</accessNumber><prodId>102008715409</prodId><offerSpecs><offerSpec><id>22560</id><name>ͨ������-����̨15Ԫ���Ʒѣ�</name><actionType>0</actionType><startFashion>0</startFashion><endFashion></endFashion><startDt>20130108</startDt><endDt>30000131</endDt></offerSpec></offerSpecs></order><areaCode>0451</areaCode><channelId>510001</channelId><staffCode>BJ1001</staffCode></request>";
////		String request = "<request>- <order>  <orderTypeId>17</orderTypeId>   <prodSpecId>378</prodSpecId>   <partyId>104008391620</partyId>   <accessNumber>13311081830</accessNumber>   <prodId /> - <offerSpecs>- <offerSpec>  <id>20615</id>   <actionType>2</actionType>   <startFashion>0</startFashion>   <endFashion></endFashion>   <startDt />   <endDt /> - <properties>- <property>  <id>13379506</id>   <name/>   <value>13311081830</value>   </property>  </properties>  </offerSpec>  </offerSpecs>  </order>  <areaId>11000</areaId>   <channelId>11040361</channelId>   <staffCode>BJ_10020</staffCode>   </request>";
////		String request="<request>	- <order>		<orderTypeId>1179</orderTypeId>		<partyId>104003510018</partyId>		<prodSpecId>378</prodSpecId>		<accessNumber>18910444002</accessNumber>		<prodId/>		<coLinkMan/>		<coLinkNbr/>		<assistMan/>		- <prodPropertys>			- <property>				<id>100000334</id>				<name>wlane����</name>				<value>123123</value>				<actionType>2</actionType>			</property>		</prodPropertys>	</order>	<areaId>11000</areaId>	<channelId>11040361</channelId>	<staffCode>BJ_10020</staffCode></request>";
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
