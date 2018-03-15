package com.linkage.bss.crm.ws;

import org.junit.Test;


public class TestClient {
	public static final String rsc = "http://172.19.17.152:8888/CrmWebService/rsc?wsdl";
	public static final String sr = "http://172.19.17.152:8888/CrmWebService/sr?wsdl";
	public static final String order = "http://172.19.17.152:8888/CrmWebService/order?wsdl";
	public static final String cust = "http://172.19.17.152:8888/CrmWebService/customer?wsdl";

//	 本地
//	 public static final String rsc =
//	 "http://127.0.0.1:8080/CrmWebService/rsc?wsdl";
//	 public static final String sr =
//	 "http://127.0.0.1:8080/CrmWebService/sr?wsdl";
//	 public static final String order =
//	 "http://127.0.0.1:8080/CrmWebService/order?wsdl";
//	 public static final String cust =
//	 "http://127.0.0.1:8080/CrmWebService/customer?wsdl";

//	@Test
//	public void blackUserCheckClient() {
//		String sb = "<request><staffCode>BJ_10020</staffCode><areaId>11000</areaId><channelId>BJ_10020</channelId><accNum>58720855</accNum></request>";
//		String response = WebServiceAxisClient.CSBTestMethod(sb, "getBrandLevelDetail", "6090010017");
//		System.err.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>不是</resultMsg><isBlack>1</isBlack></response>
//		// 测试数据select * from PARTY_IDENTITY
//	}
//
//	@Test
//	public void qryUserVipType() {
//		String sb = "<request><accNbr>18910063575</accNbr><channelId>95129</channelId><staffCode>1001</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "qryUserVipType", cust);
//		System.err.print(response);
//		// 通
//		// 测试数据
//		// SELECT OFFERPROD.redu_access_number
//		// FROM OFFER_PROD OFFERPROD, OFFER_PROD_NUMBER OPN
//		// WHERE OFFERPROD.STATUS_CD <> '22'
//		// AND OPN.STATUS_CD <> '22'
//		// AND OPN.PROD_ID = OFFERPROD.PROD_ID
//		// and OFFERPROD.redu_owner_id in
//		// (SELECT cust_id
//		// FROM CUST_SERVICE_GRADE G, CUST_SERVICE_GRADE_TYPE T
//		// WHERE G.CUST_SERVICE_LEVEL = T.CUST_SERVICE_LEVEL)
//		// and ROWNUM < 2
//
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <userInfos><vipType>钻石</vipType></userInfos>
//		// </response>
//
//	}
//
//	@Test
//	public void getUimCardInfo() {
//		String sb = "<request><cardNo></cardNo><imsi>8986031090010359198</imsi><channelId>51000</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "getUimCardInfo", rsc);
//		System.err.print(response);
//		// 通
//		// <response>
//		// <RESULT>1</RESULT>
//		// <CAUSE>返回成功</CAUSE>
//		// <TERMINAL_DEV_ID>100134883</TERMINAL_DEV_ID>
//		// <TERMINAL_CODE>8986030731457004573</TERMINAL_CODE>
//		// <DEV_MODEL_NAME>后付费UTK卡-32K卡
//		// </DEV_MODEL_NAME>
//		// <C_IMSI>460030914578649</C_IMSI>
//		// <IMSI_STATE>28</IMSI_STATE>
//		// <G_IMSI></G_IMSI>
//		// <C_AKEY>CC3A57655D638265</C_AKEY>
//		// <G_AKEY></G_AKEY>
//		// <HRPD_UPP></HRPD_UPP>
//		// <HRPD_SS></HRPD_SS>
//		// <STORE_NAME></STORE_NAME>
//		// <HLR_CODE>4512</HLR_CODE>
//		// <UIMID>830F6840</UIMID>
//		// <ESN>830F6840</ESN>
//		// <ICCID>8986030731457004573</ICCID>
//		// <PUK1>35293049</PUK1>
//		// <PUK2>49942965</PUK2>
//		// <PIN1>1234</PIN1>
//		// <PIN2>16451677</PIN2>
//		// </response>
//	}
//
//	@Test
//	public void createUserAddr() {
//		String sb = "<request><addrName>陈腾飞</addrName><channelId>11040698</channelId><staffCode>BJ1001</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "createUserAddr", rsc);
//		System.out.print(response);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <resultInfo>
//		// <addrId>122776089186</addrId>
//		// <addrName>陈腾飞</addrName>
//		// </resultInfo>
//		// </response>
//	}
//
//	@Test
//	public void generateCoNbr() {
//		String sb = "<request><count>1</count><channelId>11040698</channelId><staffCode>BJ1001</staffCode><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "generateCoNbr", order);
//		System.out.print(response);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <coNbrList>
//		// <coNbr>100000355207</coNbr>
//		// </coNbrList>
//		// </response>
//	}
//
//	@Test
//	public void getPreInterimBycond() {
//		String sb = "<request><areaId>11000</areaId><staffCode>BJ1001</staffCode><accessNumber></accessNumber><identifyType>2</identifyType><identityNum>1121111</identityNum><startTime></startTime><endTime></endTime><channelId>11040361</channelId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "getPreInterimBycond", order);
//		System.out.print(response);
//		// 通
//		// SELECT OL.OL_ID,
//		// OL.OL_NBR,
//		// OL.AREA_ID,
//		// (SELECT A.NAME FROM AREA A WHERE A.AREA_ID = OL.AREA_ID) AREA_NAME,
//		// OL.CHANNEL_ID,
//		// (SELECT C.NAME FROM CHANNEL C WHERE C.CHANNEL_ID = OL.CHANNEL_ID)
//		// CHANNEL_NAME,
//		// OL.OL_TYPE_CD,
//		// OL.STAFF_ID,
//		// (SELECT P.NAME FROM PARTY P WHERE P.PARTY_ID = OL.STAFF_ID)
//		// STAFF_NAME,
//		// OL.STATUS_CD,
//		// (SELECT OS.NAME
//		// FROM ORDER_STATUS OS
//		// WHERE OS.STATUS_CD = OL.STATUS_CD) STATUS_NAME,
//		// TO_CHAR(OL.SO_DATE, 'YYYY-MM-DD HH24:MI:SS') DT,
//		// OL.PARTY_ID,
//		// (SELECT P2.NAME FROM PARTY P2 WHERE P2.PARTY_ID = OL.PARTY_ID)
//		// PARTY_NAME,
//		// (SELECT OL1.OL_ID
//		// FROM OL_RELA ORE1, ORDER_LIST OL1
//		// WHERE ORE1.CREATE_DT IN
//		// (SELECT MAX(OLR1.CREATE_DT)
//		// FROM OL_RELA OLR1
//		// WHERE OLR1.OL_ID = OL.OL_ID
//		// AND OLR1.RELA_TYPE_CD = '98')
//		// AND OL1.OL_ID = ORE1.RELA_OL_ID
//		// AND ORE1.OL_ID = OL.OL_ID
//		// AND ORE1.RELA_TYPE_CD = '98'
//		// AND ROWNUM < 2) RELA_OL_ID,
//		// (SELECT OL2.OL_NBR
//		// FROM OL_RELA ORE2, ORDER_LIST OL2
//		// WHERE ORE2.CREATE_DT IN
//		// (SELECT MAX(OLR2.CREATE_DT)
//		// FROM OL_RELA OLR2
//		// WHERE OLR2.OL_ID = OL.OL_ID
//		// AND OLR2.RELA_TYPE_CD = '98')
//		// AND OL2.OL_ID = ORE2.RELA_OL_ID
//		// AND ORE2.OL_ID = OL.OL_ID
//		// AND ORE2.RELA_TYPE_CD = '98'
//		// AND ROWNUM < 2) RELA_OL_NBR,
//		// (SELECT PO1.PRE_ORDER_TYPE
//		// FROM PRE_ORDER PO1
//		// WHERE PO1.SO_ID = OL.OL_ID) PRE_TYPE,
//		// TO_CHAR(OL.STATUS_DT, 'YYYY-MM-DD HH24:MI:SS') STATUS_DT,
//		// NVL((SELECT OLA.VALUE
//		// FROM ORDER_LIST_ATTR OLA
//		// WHERE OLA.OL_ID = OL.OL_ID
//		// AND OLA.ITEM_SPEC_ID = '9999110'),
//		// '3000-01-01') DEL_DT
//		// FROM ORDER_LIST OL
//		// WHERE 1 = 1
//		// AND OL.STATUS_CD NOT IN ('D')
//		// and party_id in
//		//       
//		// (select party_id
//		// from party_identity
//		// where party_id in (select party_id
//		// from order_list t
//		// where t.status_cd = 'PW'))
//		// AND OL.OL_TYPE_CD = '5'
//
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <preOrders>
//		// <preOrder>
//		// <olId>100000454025</olId>
//		// <partyName>汤艳强</partyName>
//		// <channelId>11040361</channelId>
//		// <partyId>103005065283</partyId>
//		// <statusCd>2012-08-20 10:26:03</statusCd>
//		// <dt>2012-08-20 10:26:03</dt>
//		// <relaOlnbr></relaOlnbr>
//		// <preType>2</preType>
//		// <areaId>11000</areaId>
//		// <relaOlId></relaOlId>
//		// <channelName>虚拟测试营业厅</channelName>
//		// <staffId>1004320797</staffId>
//		// <areaName>北京市</areaName>
//		// <statusCd>PW</statusCd>
//		// <staffName>北京洋</staffName>
//		// <delDt>2012-8-21 10:29:33</delDt>
//		// <olNbr>100000354704</olNbr>
//		// <olTypeCd>5</olTypeCd>
//		// <statusName>预受理待转正</statusName>
//		// </preOrder>
//		// </preOrders>
//		// </response>
//	}
//
//	@Test
//	public void checkUimNo() {
//		String sb = "<request><anTypeCd>103</anTypeCd><phoneNumberId>218901036934</phoneNumberId><uimNo>8986030231010155874</uimNo><!-- ISMI卡号 --><channelId>51000000</channelId><staffCode>BJ1001</staffCode><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "checkUimNo", rsc);
//		System.out.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>校验通过</resultMsg></response>
//		// select * from terminal_device(终端设备信息描述)
//	}
//
//	@Test
//	public void allocAuditTickets() {
//		String sb = "<request><auditTicketId>427587</auditTicketId><channelId>11040685</channelId><staffCode>BJ1001</staffCode><campType>exchangeTicket</campType><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "allocAuditTickets", sr);
//		System.out.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>预占成功</resultMsg></response>
//	}
//
//	@Test
//	public void releaseAuditTickets() {
//		String sb = "<request><auditTicketId>427587</auditTicketId><channelId>11040685</channelId><staffCode>BJ1001</staffCode><campType>exchangeTicket</campType><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "releaseAuditTickets", sr);
//		System.out.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>取消预占成功</resultMsg></response>
//	}
//
//	@Test
//	public void queryAuditTicket() {
//		String sb = "<request><ticketCd></ticketCd><couponInstanceNumber></couponInstanceNumber><accessNumber></accessNumber><staffCode>BJ1001</staffCode><channelId>11040685</channelId><startDate></startDate><endDate></endDate><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "queryAuditTicket", sr);
//		System.out.print(response);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <ticketList>
//		// </ticketList>
//		// </response>
//	}
//
//	@Test
//	public void validateAuditTicket() {
//		String sb = "<request><credenceNo></credenceNo><accessNumber></accessNumber><staffCode>BJ1001</staffCode><channelId>110000</channelId><startDate></startDate><endDate></endDate><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "validateAuditTicket", sr);
//		System.out.print(response);
//		// 无数据
//		// select * from AUDITING_TICKET
//	}
//
//	@Test
//	public void exchangeAuditTickets() {
//		String sb = "<request><auditTicketId>427521</auditTicketId><channelId>11040685</channelId><staffCode>BJ1001</staffCode><areaId>11002</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "exchangeAuditTickets", sr);
//		System.out.print(response);
//	}
//
//	@Test
//	public void queryOrderListInfo() {
//		String sb = "<request><curPage>1</curPage><pageSize>20</pageSize><coNbr></coNbr><accNbr></accNbr><startDate></startDate><endDate></endDate><transfer></transfer><channelId>11040361</channelId><staffCode>BJ1001</staffCode><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "queryOrderListInfo", order);
//		System.out.print(response);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <custOrderList>
//		// <custOrder>
//		// <olId>100,000,455,457</olId>
//		// <olNbr>100000356135</olNbr>
//		// <soDate>2012-8-28 16:25:57</soDate>
//		// <soStatusDt>2012-8-28 16:26:14</soStatusDt>
//		// <staffName>亚联测试工号</staffName>
//		// <staffNumber></staffNumber>
//		// <channelName></channelName>
//		// <channelManageCode></channelManageCode>
//		// </custOrder>
//		// </custOrderList>
//		// </response>
//	}
//
//	@Test
//	public void commitPreOrderInfo() {
//		String sb = "<request><areaId>11018</areaId><olId>100000451592</olId><staffCode>BJ1001</staffCode><channelId>11040685</channelId><orderStatus>PW</orderStatus><olTypeCd>5</olTypeCd><preOrderType>1</preOrderType></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "commitPreOrderInfo", order);
//		System.out.print(response);
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
//	public void releaseCartByOlIdForPrepare() {
//		String sb = "<request><areaId>11018</areaId><olId>510000229653</olId><channelId>51000000</channelId><staffCode>BJ1001</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "releaseCartByOlIdForPrepare", order);
//		System.out.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//	}
//
//	@Test
//	public void queryTml() {
//		String sb = "<request><areaId>11000</areaId><deviceCode></deviceCode><tmlName>hello</tmlName><tmlManageCd></tmlManageCd><tmlTypeCd>1</tmlTypeCd><channelId>11040685</channelId><staffCode>BJ1001</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "queryTml", rsc);
//		System.out.print(response);
//		// 通
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <tmls>
//		// <tml>
//		// <tmlId>120138671</tmlId>
//		// <tmlTypeCd>1</tmlTypeCd>
//		// <areaId>11000</areaId>
//		// <manageCd>test000</manageCd>
//		// <name>hello</name>
//		// <description></description>
//		// <createDt>Thu Aug 30 00:00:00 CST 2012</createDt>
//		// <expDt>Thu Jan 01 00:00:00 CST 3001</expDt>
//		// <version>Thu Aug 30 00:00:00 CST 2012</version>
//		// </tml>
//		// </tmls>
//		// </response>
//	}
//
//	@Test
//	public void queryPhoneNumberList() {
//		String sb = "<request><areaId>11000</areaId><tmlId></tmlId><anTypeCd>103</anTypeCd><numberMid></numberMid><poolId>200000031</poolId><pnLevelId></pnLevelId><numberHead></numberHead><numberTail></numberTail><numberCnt>1</numberCnt><channelId>100094003</channelId><staffCode>BJ1001</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "queryPhoneNumberList", rsc);
//		System.out.print(response);
//		//通过
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
//	public void queryPhoneNumberPoolList() {
//		String sb = "<request><areaId>11000</areaId><prodSpecId>379</prodSpecId><preflag></preflag><tmlId></tmlId><anTypeCd>103</anTypeCd><channelId>11040734</channelId><staffCode>BJ1001</staffCode></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "queryPhoneNumberPoolList", rsc);
//		System.out.print(response);
//		// 通
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
//		// <poolId>100000031</poolId>
//		// <name>C网_预付费_北京电信_选号池</name>
//		// <description>Z0000010</description>
//		// </poolInfo>
//		// <poolInfo>
//		// <poolId>100000581</poolId>
//		// <name>C网_预付费_用户在用_号池</name>
//		// <description>已开户的号码</description>
//		// </poolInfo>
//		// </poolInfos>
//		// </response>
//	}
//
//	@Test
//	public void orderSubmit() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<olTypeCd>%s</olTypeCd>", "2"));
//		sb.append("<bindPayForNbr>18911272769</bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "510006603782"));
//		sb.append(String.format("<partyId>%s</partyId>", "200003582202"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//				"900565", "0", "20120727", "30000101"));
//		sb.append("<subOrder>");
//		// sb.append("<offerSpecs>");
//		// sb.append(String.format(
//		// "<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//		// "1030908535", "0", "20120727", "30000101"));
//		// sb.append("</offerSpecs>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "243"));
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
//		sb.append("<tds>");
//		sb.append("<td>");
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986031181010375601"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "30386560"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2870"));
//		sb.append(String.format("<ownerTypeCd>%s</ownerTypeCd>", "1"));
//		sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", "1"));
//		sb.append("</td>");
//		sb.append("</tds>");
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
////		String response = WebServiceAxisClient.webServicePost(sb.toString(), "validateCustOrder", order);
//		String response = WebServiceAxisClient.webServicePost(sb.toString(), "orderSubmit", order);
////		String response = WebServiceAxisClient.webServicePost(sb.toString(), "savePrepare", order);
//		System.out.print(response);
//		// 通
//		// <response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//	}
//
//	@Test
//	public void checkCampusUser() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accessNumber>13366523736</accessNumber></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "checkCampusUser", cust);
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <checkCampusUserListInfo>
//		// <checkCampusUserInfo>
//		// <STATE>在用</STATE>
//		// </checkCampusUserInfo>
//		// </checkCampusUserListInfo>
//		// </response>
//	}
//
//	@Test
//	public void getCustAdInfo() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accessNumber>B49022640</accessNumber></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "getCustAdInfo", cust);
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <custName>北京书博</custName>
//		// <custGroup></custGroup>
//		// <custAddress></custAddress>
//		// <offeringName>校园宽带ADSL</offeringName>
//		// <identifyType></identifyType>
//		// <identityNum></identityNum>
//		// <userAddress>北京朝阳区蓝岛大厦东区香水</userAddress>
//		// <ADSpeed></ADSpeed>
//		// <offerListInfo>
//		// <offerInfo>
//		// <offerSpecId>22717</offerSpecId>
//		// <offerSpecName>[22717]校园宽带ADSL</offerSpecName>
//		// </offerInfo>
//		// </offerListInfo>
//		// <statusCD>12</statusCD>
//		// <accessNumber>B49022640</accessNumber>
//		// </response>
//	}
//
//	@Test
//	public void querySerivceAcct() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>刘德华</serviceName><curPage>1</curPage><pageSize>10</pageSize></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "querySerivceAcct", order);
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
//		// </response>
//	}
//
//	@Test
//	public void addSerivceAcct() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><serviceName>刘德华</serviceName><managerId>123</managerId><linkMan>123</linkMan><linkNbr>123</linkNbr><servicePartyId>513005063694</servicePartyId><buildingId>1</buildingId><buildingType>1</buildingType></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "addSerivceAcct", order);
//		System.out.print(response);
//		// <response><resultCode>0</resultCode><resultMsg>新增服务帐户成功</resultMsg></response>
//	}
//
//	@Test
//	public void qryOfferModel() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><padType>1</padType><areaId>11018</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "qryOfferModel", order);
//		System.out.print(response);
//		// 表PROD.SCENE_INFO中无数据，padType对应表中xml_type字段
//	}
//
//	@Test
//	public void checkBindAccessNumber() {
//		String sb = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accessNumber>C41655389</accessNumber><areaId>11000</areaId><proSpecId>2</proSpecId><type>1</type><partyId>200004696680</partyId></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "checkBindAccessNumber", order);
//		System.out.print(response);
//	}
//
//	@Test
//	public void createBroadbandAccount() {
//		String sb = "<request><areaId>11018</areaId><channelId>5100000</channelId><staffCode>BJ1001</staffCode><prodSpecId>36</prodSpecId><intfType>1</intfType><pgLanAccount>asdf</pgLanAccount></request>";
//		String response = WebServiceAxisClient.webServicePost(sb, "createBroadbandAccount", rsc);
//		System.out.print(response);
//	}
//
//	/**
//	 * 电子有价卡批量获取请求
//	 */
//	@Test
//	public void _testGoodsBatchGet() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<TRADE_ID>201207251114</TRADE_ID>");
//		sb.append("<SALE_TIME>20120725</SALE_TIME>");
//		sb.append("<channelId>123</channelId>");
//		sb.append("<staffCode>1001</staffCode>");
//		sb.append("<areaId>1001</areaId>");
//		sb.append("<VALUE_CARD_TYPE_CODE>121</VALUE_CARD_TYPE_CODE>");
//		sb.append("<VALUE_CODE>2</VALUE_CODE>");
//		sb.append("<APPLY_NUM>10</APPLY_NUM>");
//		sb.append("<Flag>0</Flag>");
//		sb.append("</request>");
//
//		String response = WebServiceAxisClient.webServicePost(sb.toString(), "goodsBatchGet", sr);
//		System.out.println(response);
//	}
//
//	/**
//	 * 有价卡礼品受理确认接口
//	 */
//	@Test
//	public void _testcheckSequence() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<bcd_code>8986030080100837104</bcd_code>");
//		sb.append("<material_id>900000003</material_id>");
//		sb.append("<storeId>120005086</storeId>");
//		sb.append("<productorId>120050003</productorId>");
//		sb.append("<info><sailTime>20120728</sailTime>");
//		sb.append("<validity></validity>");
//		sb.append("<price>0</price></info>");
//		sb.append("<type>1</type>");
//		sb.append("<areaId>10000</areaId>");
//		sb.append("<channelId>11040361</channelId>");
//		sb.append("<staffCode>bj1001</staffCode>");
//		sb.append("</request>");
//		String response = WebServiceAxisClient.webServicePost(sb.toString(), "checkSequence", sr);
//		System.out.println(response);
//	}
//
//	@Test
//	public void _testcheckSaleResourceByCode() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<couponCode>910001450764</couponCode>");
//		sb.append("<couponType>1</couponType>");
//		sb.append("<inoutType>CK</inoutType>");
//		sb.append("<materialId>900000003</materialId>");
//		sb.append("<areaId>10000</areaId>");
//		sb.append("<channelId>11040361</channelId>");
//		sb.append("<staffCode>bj1001</staffCode>");
//		sb.append("</request>");
//
//		String response = WebServiceAxisClient.webServicePost(sb.toString(), "checkSaleResourceByCode", sr);
//		System.out.println(response);
//	}
//
//	/**
//	 * 筛选号码
//	 */
//	@Test
//	public void _testfilterNumber() {
//		String request = "<request><accNbr></accNbr><accNbrType></accNbrType><actionType></actionType><filterType> </filterType><FNinfos><attrType></attrType><zone></zone></FNinfos><areaId>10000</areaId><channelId>11040361</channelId><staffCode>bj1001</staffCode></request>";
//
//		String response = WebServiceAxisClient.webServicePost(request, "filterNumber", order);
//		System.out.println(response);
//	}
//
//	/**
//	 * 修改密码
//	 */
//	@Test
//	public void _testchangePassword() {
//		String request = "<request><channelId>5100000</channelId><staffCode>1001</staffCode><areaId>45101</areaId><accNbr>13329414544</accNbr><accNbrType>4</accNbrType><old_password>520907</old_password><new_password>222222</new_password><passwordTime>30000101</passwordTime><passwordType>2</passwordType></request>";
//
//		String response = WebServiceAxisClient.webServicePost(request, "changePassword", cust);
//		System.out.println(response);
//	}
//
//	/**
//	 * 修改密码
//	 */
//	@Test
//	public void _testResetPassword() {
//		String request = "<request><channelId>5100000</channelId><staffCode>1001</staffCode><areaId>1001</areaId><accNbr>13329414544</accNbr><accNbrType>4</accNbrType><password>333333</password><passwordTime>20120801</passwordTime><passwordType>2</passwordType></request>";
//
//		String response = WebServiceAxisClient.webServicePost(request, "resetPassword", cust);
//		System.out.println(response);
//	}
//
//	/**
//	 * 亲情号码查询
//	 */
//	@Test
//	public void _testqueryFNSInfo() {
//		String request = "<request><offerId>515000713150</offerId><itemSpecId>710000007</itemSpecId><areaId>1001</areaId><channelId>510001</channelId><staffCode>al1001</staffCode></request>";
//
//		String response = WebServiceAxisClient.webServicePost(request, "queryFNSInfo", order);
//		System.out.println(response);
//	}
//
//	/**
//	 * 无线宽带续费
//	 */
//	@Test
//	public void _testconfirmContinueOrderPPInfo() {
//		String request = "<request><accNbr>13349316729</accNbr><accNbrType>2</accNbrType><confirmPPInfo><id>30601100</id><name>[30601100]乐享天翼通信助理包月(3个月优惠）</name><startDt>20120810</startDt><endDt>30000101</endDt></confirmPPInfo><channelId>510001</channelId><staffCode>al1001</staffCode><areaId>45101</areaId></request>";
//
//		String response = WebServiceAxisClient.webServicePost(request, "confirmContinueOrderPPInfo", order);
//		System.out.println(response);
//	}
//
//	/**
//	 * PAD/代理商店员查询
//	 */
//	@Test
//	public void getClerkId() {
//		String request = "<request><accNbr>13370161688</accNbr><channelId>51000000</channelId><staffCode>BJ1001</staffCode><areaId>45101</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "getClerkId", rsc);
//		System.out.println(response);
//	}
//
//	// 回参：<response><resultCode>0</resultCode><resultMsg>查询成功</resultMsg><clerkId>167</clerkId><clerkName>刘德华</clerkName></response>
//
//	/**
//	 * CRM电子签名日志记录
//	 */
//	@Test
//	public void addReceiptPringLog() {
//		String request = "<request><coNbr>123</coNbr><flag>2</flag><channelId>11040361</channelId><staffCode>BJ1001</staffCode><areaId>45101</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "addReceiptPringLog", rsc);
//		System.out.println(response);
//	}
//
//	// 回参：<response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>
//
//	@Test
//	public void queryManager() {
//		String request = "<request><value>P0027203</value><flag>1</flag><channelId>51000000</channelId><staffCode>BJ1001</staffCode><areaId>45101</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "queryManager", cust);
//		System.out.print(response);
//		// <response>
//		// <resultCode>0</resultCode>
//		// <resultMsg>成功</resultMsg>
//		// <staffInfo>
//		// <areaName>北京市</areaName>
//		// <name>北京神州海通科技有限公司（商话）P0027203</name>
//		// <orgName>中国电信北京公司杨闸环岛合作营业厅（神州海通）</orgName>
//		// <staffNumber>P0027203</staffNumber>
//		// <staffId>9200000580</staffId>
//		// </staffInfo>
//		// </response>
//	}
//
//	@Test
//	public void checkSaleResourceByCode() {
//		String request = "<request><couponCode>C3F0A024</couponCode><inoutType>CK</inoutType><materialId>56537</materialId><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkSaleResourceByCode", sr);
//		System.out.print(response);
//	}
//
//	@Test
//	public void testcheckTicket() {
//		String request = "<request><sequenceId>1020120925Y0J82K305371</sequenceId><credenceNo>1020120925Y0J82K305371</credenceNo><pwd>44497927</pwd><terminalCode>C3F0B991</terminalCode><terminalType>111</terminalType><channelId>4</channelId><staffCode>P0050407</staffCode><areaId>11000</areaId></request>";
//		String response = WebServiceAxisClient.webServicePost(request, "checkTicket", sr);
//		System.out.print(response);
//	}
//	
//	@Test
//	public void testqryPreHoldNumber() {
//		String sr = "<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><flag>1</flag><anId>215321313255</anId></request>";
//		String response = WebServiceAxisClient.webServicePost(sr, "qryPreHoldNumber", rsc);
//		System.out.print(response);
//	}
//	@Test
//	public void testcheckExchangeTicket() {
//		String request = "<request><sequenceId>1020121010ZWBABR305237</sequenceId><credenceNo>1020121010ZWBABR305237</credenceNo><pwd>26818208</pwd><terminalCode>99000089626739</terminalCode><terminalType>苹果 iPhone 4S(16G白)</terminalType><channelId>94063</channelId><staffCode>P0057503</staffCode><areaId>11000</areaId></request>";
//		String result = WebServiceAxisClient.webServicePost(request, "checkExchangeTicket", sr);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testconfirmSequence() {
//		String request = "<request><sequenceId>1020121010ZWBABR305237</sequenceId><credenceNo>1020121010ZWBABR305237</credenceNo><pwd>26818208</pwd><terminalCode>99000089626739</terminalCode><terminalType>苹果 iPhone 4S(16G白)</terminalType><channelId>94063</channelId><staffCode>P0057503</staffCode><areaId>11000</areaId></request>";
//		String result = WebServiceAxisClient.webServicePost(request, "confirmSequence", sr);
//		System.out.print(result);
//	}
//	
//	@Test
//	public void getBrandLevelDetail() {
//	String request="<request><staffCode>bj1001</staffCode><areaId>11000</areaId><channelId>11040698</channelId><accNum>18010135183</accNum></request>";
//	String result = WebServiceAxisClient.webServicePost(request, "getBrandLevelDetail", cust);
//	System.out.print(result);}
//	
//	
//	
//	@Test
//	public void queryMdnByUim(){
//		String request = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><uimNo>8986031200010164423</uimNo></request>";
//		String response =  WebServiceAxisClient.webServicePost(request, "queryMdnByUim", rsc);
//		System.out.print(response);
//	}
//	
//	@Test
//	public void getUserZJInfoByAccessNumber(){
//		String request = "<request><channelId>5100000</channelId><staffCode>BJ1001</staffCode><accNum>13301034000</accNum></request>";
//		String result = WebServiceAxisClient.webServicePost(request, "getUserZJInfoByAccessNumber", cust);
//		System.out.print(result);
//	}
//	
//	@Test
//	public void testcomputeChargeInfo() {
//		String request = "<request><ol_id>100000471652</ol_id><channelId>11040361</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>";
//		String result = WebServiceAxisClient.webServicePost(request, "computeChargeInfo", order);
//		System.out.print(result);
//	}
//	
}
