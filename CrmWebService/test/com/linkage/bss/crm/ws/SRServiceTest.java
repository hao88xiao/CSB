package com.linkage.bss.crm.ws;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.crm.store.smo.StoreServiceSMO;
import com.linkage.bss.crm.ws.service.SRService;

public class SRServiceTest extends BaseTest {

//	@Autowired
//	@Qualifier("srService")
//	private SRService srService;
//
//	@Autowired
//	@Qualifier("storeClient.storeServiceSMO")
//	private StoreServiceSMO storeServiceSMO;

//	@Test
//	public void testCheckSequence() {
//		String response = srService
//				.checkSequence("<request><bcd_code>8986030080100837104</bcd_code><material_id>900000003</material_id><storeId>120005086</storeId><productorId>120050003</productorId><info><sailTime>20120728</sailTime><validity></validity><price>0</price></info><type>1</type><channelId>54003521</channelId><staffCode>1001</staffCode></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void testCheckSaleResourceByCode() {
//		String response = srService
//				.checkSaleResourceByCode("<request><couponCode>C3F07544</couponCode><inoutType>CK</inoutType><materialId>56537</materialId><channelId>54003521</channelId><staffCode>bj1001</staffCode><areaId>10000</areaId></request>");
//		System.out.println(response);
//	}
//
//	@Test
//	public void _testGoodsBatchGet() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<request>");
//		sb.append("<TRADEId>201209031114</TRADEId>");
//		sb.append("<saleTime>20120725</saleTime>");
//		sb.append("<valueCardTypeCode>121</valueCardTypeCode>");
//		sb.append("<valueCode>2</valueCode>");
//		sb.append("<applyNum>10</applyNum>");
//		sb.append("<flag>0</flag>");
//		sb.append("<channelId>123</channelId>");
//		sb.append("<areaId>10000</areaId>");
//		sb.append("<staffCode>bj1001</staffCode>");
//		sb.append("</request>");
//
//		String response = srService.goodsBatchGet(sb.toString());
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
//		String response = srService.checkSequence(sb.toString());
//		System.out.println(response);
//	}
//
//	@Test
//	public void testcheckTicket() {
//		String request = "<request><sequenceId>010-20111103-00894</sequenceId><credenceNo>010-20111103-00894</credenceNo><pwd>26102300</pwd><channelId>11040361</channelId><staffCode>LIUYANG123</staffCode><areaId>10000</areaId></request>";
//		String result = srService.checkTicket(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testcheckExchangeTicket() {
//		String request = "<request><sequenceId>1020120920TYY0R6305209</sequenceId><credenceNo>1020120920TYY0R6305209</credenceNo><pwd>08912893</pwd><terminalCode>C3F0B991</terminalCode><terminalType>酷派N900S加密版</terminalType><channelId>11040361</channelId><staffCode>P0050407</staffCode><areaId>11000</areaId></request>";
//		String result = srService.checkExchangeTicket(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testconfirmSequence() {
//		String request = "<request><terminalType>60578</terminalType><terminalCode>iphone4s16hei</terminalCode><credenceNo>10201212148J4R7Z305511</credenceNo><pwd>91660118</pwd><channelId>11040082</channelId><staffCode>P0056017</staffCode></request>";
//		String result = srService.confirmSequence(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testCheckGiftCert() {
//		String request = "<request><bcd_code>0100001681661040</bcd_code><channelId>11040860</channelId><staffCode>P0050407</staffCode><areaId>11000</areaId></request>";
//		String result = srService.checkGiftCert(request);
//		System.out.print(result);
//	}
//
//	@Test
//	public void testconfirmCertGift() {
//		String request = "<request><bcd_code>ALX3111200027099</bcd_code><partyId>104002705824</partyId><offerSpecId>25100</offerSpecId><offerSpecName>[25100]【1200元礼券】</offerSpecName><prodId>102008470418</prodId><prodSpecId>378</prodSpecId><startDt>20121012</startDt><endDt>30000101</endDt><channelId>91376</channelId><staffCode>P0050407</staffCode><areaId>11000</areaId></request>";
//		String result = srService.confirmCertGift(request);
//		System.out.print(result);
//	}
//
//	// @Test
//	// public void testValidateAuditTicket() {
//	// String request =
//	// "<request><areaId>11000</areaId><credenceNo>010-20111103-00932</credenceNo><accessNumber>13349315384</accessNumber><staffCode>bj1001</staffCode><channelId>5100000</channelId><startDate></startDate><endDate></endDate></request>";
//	// String result = srService.validateAuditTicket(request);
//	// System.out.print(result);
//	// }
//	//	
//
//	// @Test
//	// public void testExchangeAuditTickets() {
//	// String request =
//	// "<request><areaId>10000</areaId><auditTicketId>354296</auditTicketId><channelId>510000</channelId><staffCode>bj1001</staffCode></request>";
//	// String result = srService.exchangeAuditTickets(request);
//	// System.out.print(result);
//	// }
//	//
//
//	// @Test
//	// public void testAllocAuditTickets() {
//	// String s =
//	// "<request><areaId>45101</areaId><auditTicketId>354296</auditTicketId><channelId>510000</channelId><staffCode>bj1001</staffCode><campType>exchangeTicket</campType></request>";
//	// String result = srService.allocAuditTickets(s);
//	// System.out.print(result);
//	// }
//
//	@Test
//	public void testGetMaterialByCode() {
//		try {
//			String result = storeServiceSMO.getMaterialByCode("-1", "iphone4s16hei");
//			System.out.println("result=" + result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
