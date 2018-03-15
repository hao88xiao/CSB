package com.linkage.bss.crm.ws;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.crm.ws.service.HBService;

public class HBServiceTest extends BaseTest {

//	@Autowired
//	@Qualifier("hbService")
//	private HBService hbService;

//	@Test
//	public void testActivateUser() {
//		StringBuilder request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		String response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("abc").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("abc").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA1").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("2").append("</billingMode>");
//		request.append("<activateDate>").append("20120101131313").append("</activateDate>");
//		request.append("</request>");
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<sequence>").append("101").append("</sequence>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<accNbr>").append("1010101").append("</accNbr>");
//		request.append("<activateType>").append("2HA").append("</activateType>");
//		request.append("<billingMode>").append("0").append("</billingMode>");
//		request.append("<activateDate>").append("123123").append("</activateDate>");
//		request.append("</request>");
//
//		response = hbService.activateUser(request.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testRechargeChangePriceplan() {
//		StringBuilder request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("88888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		String response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("").append("</servId>");
//		request.append("<pricePlanCd>").append("88888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("88888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("88888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("ddd").append("</servId>");
//		request.append("<pricePlanCd>").append("8888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("8888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("sdfdsf").append("</startDate>");
//		request.append("<endDate>").append("20121212151515").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//
//		request = new StringBuilder("");
//		request.append("<request>");
//		request.append("<servId>").append("10101").append("</servId>");
//		request.append("<pricePlanCd>").append("8888888").append("</pricePlanCd>");
//		request.append("<startDate>").append("20121212141414").append("</startDate>");
//		request.append("<endDate>").append("sdfsdfdsf").append("</endDate>");
//		request.append("</request>");
//
//		response = hbService.rechargeChangePriceplan(request.toString());
//		System.out.println(response);
//	}
}
