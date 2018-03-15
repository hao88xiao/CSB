package com.linkage.bss.crm.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.crm.ws.service.AgentService;
import com.linkage.bss.crm.ws.service.OrderService;

public class OrderServiceTest extends BaseTest {

//	@Autowired
//	@Qualifier("orderService")
//	private OrderService orderService;
//
//	@Autowired
//	@Qualifier("agentService")
//	private AgentService agentService;

	// @Test
	// public void queryFNSInfo() {
	// String xml =
	// "<request><offerId>105000784387</offerId><itemSpecId>13379510</itemSpecId><channelId>510001</channelId><staffCode>al1001</staffCode><areaId>1001</areaId></request>";
	// String returnXml = orderService.queryFNSInfo(xml);
	// System.out.println(returnXml);
	// }

//	@Test
//	public void isSubsidy() {
//		String xml = "<request><bcd_code>C3F0B865</bcd_code><channelId>510001</channelId><staffCode>bj1001</staffCode></request>";
//		String returnXml = orderService.isSubsidy(xml);
//		System.out.println(returnXml);
//	}
//
//	@Test
//	public void testQueryProdByCompProdSpecId() {
//		String result = orderService
//				.queryProdByCompProdSpecId("<request><partyId></partyId><compProdSpecId></compProdSpecId><subProdSpecId></subProdSpecId><areaCode>45101</areaCode><StaffCode>al1001</StaffCode><channelId>10000000</channelId></request>");
//		System.out.print(result);
//	}
//
//	@Test
//	public void testBusinessService() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<orderInfo>");
//		sb.append("<order>");
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", 1));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", 379));
//		sb.append(String.format("<feeType>%s</feeType>", 1));
//		sb.append(String.format("<anId>%s</anId>", "113394551814"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "13394551814"));
//		sb.append(String.format("<acctCd>%s</acctCd>", -100));
//		sb.append(String.format("<partyId>%s</partyId>", "513005065158"));
//		sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "13313697955", "0"));
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
//		sb.append("<pricePlanPak>");
//		sb
//				.append(String
//						.format(
//								"<pricePlan><pricePlanCd>%s</pricePlanCd><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></pricePlan>",
//								"1030908535", "0", "20120727", "30000101"));
//		// sb.append("<pricePlan>");
//		// sb.append(String.format("<pricePlanCd>%s</pricePlanCd>",
//		// "830504404"));
//		// sb.append(String.format("<actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt>","0",
//		// "20120727", "30000101"));
//		// sb.append("<properties>");
//		// sb.append(String.format("<property><id>%s</id><value>%s</value></property>",
//		// "230504404", "111111"));
//		// sb.append("</properties>");
//		// sb.append("</pricePlan>");
//		sb.append("</pricePlanPak>");
//		sb.append("<servicePak>");
//		sb.append("<service>");
//		sb.append(String.format(
//				"<serviceId>%s</serviceId><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt>",
//				"30504404", "0", "20120727", "30000101"));
//		sb.append("<properties>");
//		sb.append(String.format("<property><id>%s</id><name>%s</name><value>%s</value></property>", "230504404",
//				"C+W 密码", "111111"));
//		sb.append("</properties>");
//		sb.append("</service>");
//		sb.append("</servicePak>");
//		sb.append("<tds>");
//		sb.append("<td>");
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986030970456021186"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "100000353"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2801"));
//		sb.append(String.format("<ownerTypeCd>%s</ownerTypeCd>", "1"));
//		sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", "1"));
//		sb.append("</td>");
//		sb.append("</tds>");
//		sb.append("</order>");
//		sb.append("</orderInfo>");
//		sb.append("<areaCode>0451</areaCode>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//
//		System.out.println(sb.toString());
//
//		String response = orderService.businessService(sb.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testOrderSubmit() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", 1));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", 379));
//		sb.append(String.format("<feeType>%s</feeType>", 1));
//		sb.append(String.format("<anId>%s</anId>", "113394551814"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "13394551814"));
//		sb.append(String.format("<acctCd>%s</acctCd>", -100));
//		sb.append(String.format("<partyId>%s</partyId>", "513005065158"));
//		sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "13313697955", "0"));
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
//		sb.append("<offerSpecs>");
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//				"1030908535", "0", "20120727", "30000101"));
//		// sb.append("<pricePlan>");
//		// sb.append(String.format("<pricePlanCd>%s</pricePlanCd>",
//		// "830504404"));
//		// sb.append(String.format("<actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt>","0",
//		// "20120727", "30000101"));
//		// sb.append("<properties>");
//		// sb.append(String.format("<property><id>%s</id><value>%s</value></property>",
//		// "230504404", "111111"));
//		// sb.append("</properties>");
//		// sb.append("</pricePlan>");
//		// sb.append("<offerSpec>");
//		// sb.append(String.format(
//		// "<id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt>",
//		// "830504404", "0", "20120727", "30000101"));
//		// sb.append("<properties>");
//		// sb.append(String.format("<property><id>%s</id><name>%s</name><value>%s</value></property>",
//		// "230504404",
//		// "C+W 密码", "111111"));
//		// sb.append("</properties>");
//		// sb.append("</offerSpec>");
//		sb.append("</offerSpecs>");
//		sb.append("<tds>");
//		sb.append("<td>");
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986030970456021186"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "100000353"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2801"));
//		sb.append(String.format("<ownerTypeCd>%s</ownerTypeCd>", "1"));
//		sb.append(String.format("<maintainTypeCd>%s</maintainTypeCd>", "1"));
//		sb.append("</td>");
//		sb.append("</tds>");
//		sb.append("</order>");
//		sb.append("<areaCode>0451</areaCode>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//
//		System.out.println(sb.toString());
//
//		// String response = orderService.changeRequestXml(sb.toString());
//		String response = orderService.orderSubmit(sb.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testchange() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<olTypeCd>%s</olTypeCd>", "1"));
//		sb.append("<bindPayForNbr>18945283489</bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "510006603782"));
//		sb.append(String.format("<partyId>%s</partyId>", "513005062853"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//				"1030908535", "0", "20120727", "30000101"));
//		sb.append("<subOrder>");
//		// sb.append("<offerSpecs>");
//		// sb.append(String.format(
//		// "<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//		// "1030908535", "0", "20120727", "30000101"));
//		// sb.append("</offerSpecs>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "309"));
//		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "379"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "118945283489"));
//		sb.append(String.format("<anId>%s</anId>", "18945283489"));
//		sb.append("<bindPayForNbr></bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "379"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "test"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
//		sb.append(String.format("<assistMan>%s</assistMan>", "al1001"));
//		sb.append(String.format("<password>%s</password>", "111111"));
//		sb.append(String.format("<passwordType>%s</passwordType>", "2"));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "13313697955", "0"));
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
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986030890454011380"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "100001917"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2803"));
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
//
//		sb.append("<subOrder>");
//		// sb.append("<offerSpecs>");
//		// sb.append(String.format(
//		// "<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//		// "1030908535", "0", "20120727", "30000101"));
//		// sb.append("</offerSpecs>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "309"));
//		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "379"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "18945283489"));
//		sb.append(String.format("<anId>%s</anId>", "118945283489"));
//		sb.append("<bindPayForNbr></bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "379"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "test"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
//		sb.append(String.format("<assistMan>%s</assistMan>", "al1001"));
//		sb.append(String.format("<password>%s</password>", "111111"));
//		sb.append(String.format("<passwordType>%s</passwordType>", "2"));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "13313697955", "0"));
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
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986030890454011380"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "100001917"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2803"));
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
//		sb.append("<areaCode>0451</areaCode>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//
//		String response = orderService.orderSubmit(sb.toString());
//		System.out.print(response);
//	}
//
//	@Test
//	public void testSavePropare() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<olTypeCd>%s</olTypeCd>", "5"));
//		sb.append("<bindPayForNbr>18945283489</bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "510006603782"));
//		sb.append(String.format("<partyId>%s</partyId>", "513005061702"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format(
//				"<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//				"1030908535", "0", "20120727", "30000101"));
//		sb.append("<subOrder>");
//		// sb.append("<offerSpecs>");
//		// sb.append(String.format(
//		// "<offerSpec><id>%s</id><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt></offerSpec>",
//		// "1030908535", "0", "20120727", "30000101"));
//		// sb.append("</offerSpecs>");
//		sb.append(String.format("<roleCd>%s</roleCd>", "309"));
//		sb.append(String.format("<prodId>%s</prodId>", ""));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", "379"));
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", "1"));
//		sb.append(String.format("<accessNumber>%s</accessNumber>", "118945283489"));
//		sb.append(String.format("<anId>%s</anId>", "18945283489"));
//		sb.append("<bindPayForNbr></bindPayForNbr>");
//		sb.append(String.format("<acctCd>%s</acctCd>", "-100"));
//		sb.append(String.format("<bindNumberProdSpec>%s</bindNumberProdSpec>", "379"));
//		sb.append(String.format("<coLinkMan>%s</coLinkMan>", "test"));
//		sb.append(String.format("<coLinkNbr>%s</coLinkNbr>", "18945283484"));
//		sb.append(String.format("<assistMan>%s</assistMan>", "al1001"));
//		sb.append(String.format("<password>%s</password>", "111111"));
//		sb.append(String.format("<passwordType>%s</passwordType>", "2"));
//		sb.append("<prodPropertys>");
//		sb.append(String.format(
//				"<property><id>%s</id><name>%s</name><value>%s</value><actionType>%s</actionType></property>",
//				"300204", "催缴号码", "13313697955", "0"));
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
//		sb.append(String.format("<terminalCode>%s</terminalCode>", "8986030890454011380"));
//		sb.append(String.format("<terminalDevSpecId>%s</terminalDevSpecId>", "10302057"));
//		sb.append(String.format("<terminalDevId>%s</terminalDevId>", "100001917"));
//		sb.append(String.format("<devModelId>%s</devModelId>", "2803"));
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
//
//		sb.append("</order>");
//		sb.append("<areaCode>0451</areaCode>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//
//		String response = orderService.savePrepare(sb.toString());
//		System.out.print(response);
//	}
//
//	// 套餐档次变更
//	@Test
//	public void testchangeWare() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<order>");
//		sb.append(String.format("<orderTypeId>%s</orderTypeId>", 17));
//		sb.append(String.format("<partyId>%s</partyId>", "200003582207"));
//		sb.append(String.format("<prodSpecId>%s</prodSpecId>", 379));
//		sb.append(String.format("<prodId>%s</prodId>", "200001089234"));
//		sb.append("<offerSpecs>");
//		sb.append("<offerSpec>");
//		sb.append(String.format(
//				"<id>%s</id><name>%s</name><actionType>%s</actionType><startDt>%s</startDt><endDt>%s</endDt>",
//				"900565", "900565]乐享3G套餐全能版（共享）-89", "0", "20120806", "30000101"));
//		sb.append("</offerSpec>");
//		sb.append("<offerSpec>");
//		sb.append(String.format("<id>%s</id><name>%s</name><actionType>%s</actionType>", "992015065",
//				"[992015065]普通套餐-CDMA_预付费", "1"));
//		sb.append("</offerSpec>");
//		sb.append("</offerSpecs>");
//		sb.append("</order>");
//		sb.append("<areaId>11000</areaId>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//		String response = orderService.businessService(sb.toString());
//		System.out.println(response);
//	}
//
//	// 续费
//	@Test
//	public void testconfirmContinueOrderPPInfo() {
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("<request>");
//		sb.append("<areaId>11000</areaId>");
//		sb.append("<accNbr>18910424249</accNbr>");
//		sb.append("<accNbrType>2</accNbrType>");
//		sb.append("<confirmPPInfo><id>22557</id>");
//		sb.append("<name>[22557]通信助理-通信秘书5元</name>");
//		sb.append("<startDt>20120830</startDt>");
//		sb.append("<endDt>30000101</endDt></confirmPPInfo>");
//		sb.append("<channelId>510001</channelId>");
//		sb.append("<staffCode>bj1001</staffCode>");
//		sb.append("</request>");
//		String response = orderService.confirmContinueOrderPPInfo(sb.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryAgentInfo() {
//		String response = agentService
//				.queryAgentInfo("<request><checkStaffCd>348086751</checkStaffCd><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testLogin() {
//		String response = agentService
//				.login("<request><staffNumber>A00332</staffNumber><passwd>1234qwe!</passwd><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryCouponInfoByPriceplan() {
//		String response = orderService
//				.queryCouponInfoByPriceplan("<request><pricePlanCd>82242</pricePlanCd><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQryProd() {
//		String response = orderService
//				.qryProd("<request><partyId>4510542222</partyId><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCheckGlobalroam() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<accNbr>13349316713</accNbr>");
//		sb.append("<channelId>53203343</channelId>");
//		sb.append("<staffCode>al1001</staffCode>");
//		sb.append("</request>");
//		String response = orderService.checkGlobalroam(sb.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCancelOrder() {
//		String response = orderService
//				.cancelOrder("<request><coNbr>510000228881</coNbr><areaCode>45101</areaCode><channelId>510001</channelId><staffCode>1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testNewAcct() {
//		StringBuilder request = new StringBuilder();
//		request.append("<request>");
//		request.append("<partyId>104003067856</partyId>");
//		request.append("<areaId>10000</areaId>");
//		request.append("<channelId>11040361</channelId>");
//		request.append("<staffCode>bj1001</staffCode>");
//		request.append("</request>");
//		String response = orderService.newAcct(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryAttachOfferSpecBySpec() {
//		String response = orderService
//				.queryPpInfo("<request><accNbr>13314517504</accNbr><accNbrType>1</accNbrType><areaCode>45101</areaCode><channelId>510001</channelId><staffCode>1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testQueryNbrByIccid() {
//		String response = orderService
//				.queryNbrByIccid("<request><serialNum>8986031170451048977</serialNum><channelId>510001</channelId><staffCode>al1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testfilterNumber() {
//		File f = new File("filterNumber.xml");
//		InputStream input = null;
//		try {
//			input = new FileInputStream(f);
//			byte b[] = new byte[(int) f.length()];
//			input.read(b);
//			input.close();
//			String s = new String(b);
//			String response = orderService.filterNumber(s);
//			System.out.println(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testbusinessService() {
//		File f = new File("businessService3.xml");
//		InputStream input = null;
//		String ss = "";
//		try {
//			input = new FileInputStream(f);
//			byte b[] = new byte[(int) f.length()];
//			input.read(b);
//			input.close();
//			String s = new String(b);
//			String response = orderService.businessService(s);
//			System.out.println(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testcomputeChargeInfo() {
//		// File f = new File("computeChargeInfo.xml");
//		// InputStream input = null ;
//		// String ss = "";
//		// try {
//		// input = new FileInputStream(f);
//		// byte b[] = new byte[(int)f.length()] ;
//		// input.read(b) ;
//		// input.close() ;
//		// String s = new String(b);
//		// String response = orderService.computeChargeInfo(s);
//		// System.out.println(response);
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//
//		//		String request = "<request>" + "<ol_id>100000471652</ol_id>"
//		//				+ "<channelId>11040361</channelId>"
//		//				+ "<staffCode>bj1001</staffCode>" + "<areaId>10000</areaId>"
//		//				+ "</request>";
//		//		String response = orderService.computeChargeInfo(request);
//		//		System.out.println(response);
//
//		long start = System.currentTimeMillis();
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("<request>");
//		sb.append("<olId>100004159577</olId>");
//		sb.append("</request>");
//		String response = orderService.reprintReceipt(sb.toString());
//		System.out.println(response);
//		System.out.println("csb打印回执执行时间是：" + (System.currentTimeMillis() - start) + "ms");
//	}
//
//	@Test
//	public void testqueryContinueOrderPPInfo() {
//		File f = new File("queryContinueOrderPPInfo.xml");
//		InputStream input = null;
//		String ss = "";
//		try {
//			input = new FileInputStream(f);
//			byte b[] = new byte[(int) f.length()];
//			input.read(b);
//			input.close();
//			String s = new String(b);
//			String response = orderService.queryContinueOrderPPInfo(s);
//			System.out.println(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testTransferOwnerOthers() {
//		StringBuffer sbf = new StringBuffer();
//		sbf
//				.append("<request><accessNumber>13301008574</accessNumber><staffCode>bj1001</staffCode><channelId>11040082</channelId>");
//		sbf.append("<custInfo>");
//		sbf.append("<custName>hlll</custName>");
//		sbf.append("<custType>1</custType>");
//		sbf.append("<areaCode>010</areaCode>");
//		sbf.append("<cerdAddr>lianhua</cerdAddr>");
//		sbf.append("<cerdType>2</cerdType>");
//		sbf.append("<cerdValue>333333</cerdValue>");
//		sbf.append("<contactPhone1>01011111</contactPhone1>");
//		sbf.append("<custAddr>通信地址</custAddr>");
//		sbf.append("<emailAddr>junqilong</emailAddr>");
//		sbf.append("<linkMan>cm</linkMan>");
//		sbf.append("<contactPhone2>012</contactPhone2>");
//		sbf.append("<postCard>337109</postCard>");
//		sbf.append("<schoolInfo>高校信息</schoolInfo>");
//		sbf.append("</custInfo></request>");
//		try {
//			String transferOwnerOthers = orderService.transferOwner(sbf.toString());
//			System.out.println(transferOwnerOthers);
//		} catch (Exception e) {
//		}
//		//		try {
//		//			//获取数据刚来不久，业务方面还不太了解，做的不好还请多多指教哈
//		//			Document doc = WSUtil.parseXml(request);
//		//			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
//		//			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
//		//			//			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
//		//			//			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
//		//			//			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
//		//
//		//			//			if (StringUtils.isBlank(accessNumber) || StringUtils.isBlank(partyId)) {
//		//			//				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR);
//		//			//			}
//		//
//		//			//			logger.debug("开始组建json报文...");
//		//			// 2.0XML转换为JSON
//		//			JSONObject orderJson = new JSONObject();
//		//			//			orderJson = orderService.getCreateTransferOwnerListFactory().generateTransferOwner(doc);
//		//
//		//			System.out.println("过户报文转换为JSON:{}" + orderJson.toString());
//		//			//			String result = soServiceSMO.soAutoService(orderJson);
//		//			//			return result;
//		//
//		//		} catch (Exception e) {
//		//			//			logger.error("uploadRealNameCust实名制客户资料上传:" + request, e);
//		//			//			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
//		//		}
//	}
//	
//	
//	@Test
//	public void testGhTest() {
////		orderService.ghTest("");
//	}
}
