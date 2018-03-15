package com.linkage.bss.crm.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CSBTest extends BaseTest {

//	@Autowired
//	@Qualifier("testCsb")
//	private testCSB testCsb;
//
//	public testCSB getTestCsb() {
//		return testCsb;
//	}
//	public void setTestCsb(testCSB testCsb) {
//		this.testCsb = testCsb;
//	}
//	@Test
//	public void testvalidateAuditTicket() {
//		String response;
//		try {
//			File f = new File("validateAuditTicket.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "validateAuditTicket";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("validateAuditTicket"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testallocAuditTickets() {
//		String response;
//		try {
//			File f = new File("allocAuditTickets.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "allocAuditTickets";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("allocAuditTickets"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testgetPreInterimBycond() {
//		String response;
//		try {
//			File f = new File("getPreInterimBycond.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "getPreInterimBycond";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("getPreInterimBycond"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	
//	
//	@Test
//	public void testqueryPreOrderDetail() {
//		String response;
//		try {
//			File f = new File("queryPreOrderDetail.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryPreOrderDetail";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryPreOrderDetail"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testreleaseAuditTickets() {
//		String response;
//		try {
//			File f = new File("releaseAuditTickets.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "releaseAuditTickets";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("releaseAuditTickets"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testexchangeAuditTickets() {
//		String response;
//		try {
//			File f = new File("exchangeAuditTickets.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "exchangeAuditTickets";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("exchangeAuditTickets"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryAuditTicket() {
//		String response;
//		try {
//			File f = new File("queryAuditTicket.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryAuditTicket";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryAuditTicket"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testcheckPassword() {
//		String response;
//		try {
//			File f = new File("checkPassword.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkPassword";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkPassword"+response+"***********************************");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testchangePassword() {
//		String response;
//		try {
//			File f = new File("changePassword.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "changePassword";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("changePassword"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testresetPassword() {
//		String response;
//		try {
//			File f = new File("resetPassword.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "resetPassword";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("resetPassword"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryService() {
//		String response;
//		try {
//			File f = new File("queryService.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryService";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryService"+response+"***********************************");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryOrderListInfo() {
//		String response;
//		try {
//			File f = new File("queryOrderListInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryOrderListInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryOrderListInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryNbrByIccid() {
//		String response;
//		try {
//			File f = new File("queryNbrByIccid.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryNbrByIccid";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryNbrByIccid"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//
//	@Test
//	public void testisYKSXInfo() {
//		String response;
//		try {
//			File f = new File("isYKSXInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "isYKSXInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("isYKSXInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//
//	@Test
//	public void testnewValidateService() {
//		String response;
//		try {
//			File f = new File("newValidateService.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "newValidateService";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("newValidateService"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryAccNBRInfo() {
//		String response;
//		try {
//			File f = new File("queryAccNBRInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryAccNBRInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryAccNBRInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryAreaInfo() {
//		String response;
//		try {
//			File f = new File("queryAreaInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryAreaInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryAreaInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryOperatingOfficeInfo() {
//		String response;
//		try {
//			File f = new File("queryOperatingOfficeInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryOperatingOfficeInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryOperatingOfficeInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testbusinessService() {
//		String response;
//		try {
//			File f = new File("businessService3.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "businessService";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("businessService"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testmodifyCustom() {
//		String response;
//		try {
//			File f = new File("modifyCustom.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "modifyCustom";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("modifyCustom"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryFNSInfo() {
//		String response;
//		try {
//			File f = new File("queryFNSInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryFNSInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryFNSInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testconfirmContinueOrderPPInfo() {
//		String response;
//		try {
//			File f = new File("confirmContinueOrderPPInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "confirmContinueOrderPPInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("confirmContinueOrderPPInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryOperation() {
//		String response;
//		try {
//			File f = new File("queryOperation.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryOperation";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryOperation"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryPpInfo() {
//		String response;
//		try {
//			File f = new File("queryPpInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryPpInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryPpInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqueryOffering2pp() {
//		String response;
//		try {
//			File f = new File("queryOffering2pp.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryOffering2pp";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryOffering2pp"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testblackUserCheck() {
//		String response;
//		try {
//			File f = new File("blackUserCheck.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "blackUserCheck";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("blackUserCheck"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryAgentInfo() {
//		String response;
//		try {
//			File f = new File("queryAgentInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryAgentInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryAgentInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcheckSequence() {
//		String response;
//		try {
//			File f = new File("checkSequence.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkSequence";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkSequence"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcheckSaleResourceByCode() {
//		String response;
//		try {
//			File f = new File("checkSaleResourceByCode.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ;
//			        input.read(b) ;
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkSaleResourceByCode";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkSaleResourceByCode"+response+"***********************************");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
////	@Test
////	public void testqueryCouponInfoByPriceplan() {
////		String response;
////		try {
////			File f = new File("queryCouponInfoByPriceplan.xml");
////			InputStream input = null ;  
////				input = new FileInputStream(f);
////				  byte b[] = new byte[(int)f.length()] ; 
////			        input.read(b) ; 
////			        input.close() ;
////		       String s =  new String(b);
////		       String name = "queryCouponInfoByPriceplan";
////		       response = testCsb.exchange(s,name);
////		       System.out.println("queryCouponInfoByPriceplan"+response+"***********************************");
////		       
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		}
//	
//	@Test
//	public void testgoodsBatchGet() {
//		String response;
//		try {
//			File f = new File("goodsBatchGet.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "goodsBatchGet";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("goodsBatchGet"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcancelOrder() {
//		String response;
//		try {
//			File f = new File("cancelOrder.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "cancelOrder";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("cancelOrder"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcheckGlobalroam() {
//		String response;
//		try {
//			File f = new File("checkGlobalroam.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkGlobalroam";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkGlobalroam"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryUserVipType() {
//		String response;
//		try {
//			File f = new File("qryUserVipType.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryUserVipType";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryUserVipType返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcheckCampusUser() {
//		String response;
//		try {
//			File f = new File("checkCampusUser.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkCampusUser";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkCampusUser返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testgetCustAdInfo() {
//		String response;
//		try {
//			File f = new File("getCustAdInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "getCustAdInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("getCustAdInfo返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testfilterNumber() {
//		String response;
//		try {
//			File f = new File("filterNumber.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "filterNumber";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("filterNumber返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryImsiInfoByMdn() {
//		String response;
//		try {
//			File f = new File("queryImsiInfoByMdn.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryImsiInfoByMdn";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryImsiInfoByMdn返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcreateCust() {
//		String response;
//		try {
//			File f = new File("createCust.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "createCust";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("createCust返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testnewResCheck() {
//		String response;
//		try {
//			File f = new File("newResCheck.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "newResCheck";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("newResCheck返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testqryCust() {
//		String response;
//		try {
//			File f = new File("qryCust.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryCust";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryCust返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testlogin() {
//		String response;
//		try {
//			File f = new File("login.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "login";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("login返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	
//	@Test
//	public void testqueryPhoneNumberPoolList() {
//		String response;
//		try {
//			File f = new File("queryPhoneNumberPoolList.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryPhoneNumberPoolList";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryPhoneNumberPoolList返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryPhoneNumberList() {
//		String response;
//		try {
//			File f = new File("queryPhoneNumberList.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryPhoneNumberList";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryPhoneNumberList返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testpreHoldOrReleaseNumber() {
//		String response;
//		try {
//			File f = new File("preHoldOrReleaseNumber.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "preHoldOrReleaseNumber";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("preHoldOrReleaseNumber返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	
//	@Test
//	public void testisSubsidy() {
//		String response;
//		try {
//			File f = new File("isSubsidy.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "isSubsidy";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("isSubsidy返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	
//	@Test
//	public void testqueryProdinfoByImsi() {
//		String response;
//		try {
//			File f = new File("queryProdinfoByImsi.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryProdinfoByImsi";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryProdinfoByImsi返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryUserScore() {
//		String response;
//		try {
//			File f = new File("qryUserScore.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryUserScore";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryUserScore返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testexchangeUserScore() {
//		String response;
//		try {
//			File f = new File("exchangeUserScore.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "exchangeUserScore";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("exchangeUserScore返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testpresentUserScore() {
//		String response;
//		try {
//			File f = new File("presentUserScore.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "presentUserScore";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("presentUserScore返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryUserScoreDetail() {
//		String response;
//		try {
//			File f = new File("qryUserScoreDetail.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryUserScoreDetail";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryUserScoreDetail返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testupdatePreInterim() {
//		String response;
//		try {
//			File f = new File("updatePreInterim.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "updatePreInterim";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("updatePreInterim返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryTml() {
//		String response;
//		try {
//			File f = new File("queryTml.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryTml";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryTml返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcreateUserAddr() {
//		String response;
//		try {
//			File f = new File("createUserAddr.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "createUserAddr";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("createUserAddr返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testinitStaticData() {
//		String response;
//		try {
//			File f = new File("initStaticData.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "initStaticData";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("initStaticData返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryProd() {
//		String response;
//		try {
//			File f = new File("qryProd.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryProd";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryProd返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testorderSubmit() {
//		String response;
//		try {
//			File f = new File("orderSubmit.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "orderSubmit";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("orderSubmit返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testquerySerivceAcct() {
//		String response;
//		try {
//			File f = new File("querySerivceAcct.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "querySerivceAcct";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("querySerivceAcct返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testaddSerivceAcct() {
//		String response;
//		try {
//			File f = new File("addSerivceAcct.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "addSerivceAcct";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("addSerivceAcct返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcreateBroadbandAccount() {
//		String response;
//		try {
//			File f = new File("createBroadbandAccount.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "createBroadbandAccount";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("createBroadbandAccount返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqueryProdByCompProdSpecId() {
//		String response;
//		try {
//			File f = new File("queryProdByCompProdSpecId.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "queryProdByCompProdSpecId";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("queryProdByCompProdSpecId返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcompProdRule() {
//		String response;
//		try {
//			File f = new File("compProdRule.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "compProdRule";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("compProdRule返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testgenerateCoNbr() {
//		String response;
//		try {
//			File f = new File("generateCoNbr.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "generateCoNbr";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("generateCoNbr返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//
//	@Test
//	public void testcheckBindAccessNumber() {
//		String response;
//		try {
//			File f = new File("checkBindAccessNumber.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkBindAccessNumber";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkBindAccessNumber返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//
//
//	@Test
//	public void testqryPricePlanService() {
//		String response;
//		try {
//			File f = new File("qryPricePlanService.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryPricePlanService";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryPricePlanService返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryAcctInfo() {
//		String response;
//		try {
//			File f = new File("qryAcctInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryAcctInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryAcctInfo返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testcheckPartyProd() {
//		String response;
//		try {
//			File f = new File("checkPartyProd.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkPartyProd";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkPartyProd返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryOfferModel() {
//		String response;
//		try {
//			File f = new File("qryOfferModel.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "qryOfferModel";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("qryOfferModel返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	
//	@Test
//	public void testgetUimCardInfo() {
//		String response;
//		try {
//			File f = new File("getUimCardInfo.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "getUimCardInfo";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("getUimCardInfo返回结果："+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
//	@Test
//	public void testqryUserScoreDetailAll() {
//		String response;
//		try {
//			String request = "<Query><zoneNumber>B22363179</zoneNumber><objCustId>100000041189</objCustId><objUserId></objUserId><beginMonth>201004</beginMonth><endMonth>201004</endMonth><qryDate>Mon Sep 17 09:08:05 CST 2012</qryDate></Query>";
//			String method = "qryUserScoreDetailAll";
//			String nicode = "6090010024";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("qryUserScoreDetailAll返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//通过
//		}
//	
//	@Test
//	public void testadjustUserScore() {
//		String response;
//		try {
//			String request = "<Query><zoneNumber>B22363179</zoneNumber><objCustId>100000041189</objCustId><objUserId>102008249722</objUserId><adjustValue>-1</adjustValue><adjustReason>qwe</adjustReason><staffId>1</staffId><scoreTypeId>10</scoreTypeId></Query>";
//			String method = "adjustUserScore";
//			String nicode = "6090010024";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("adjustUserScore返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//通过
//		}
//	
//	@Test
//	public void testqryCSUserScoreDetailAll() {
//		String response;
//		try {
//			String request = "<Query><zoneNumber>B22363179</zoneNumber><objCustId>100000041189</objCustId><objUserId></objUserId><beginMonth>201004</beginMonth><endMonth>201004</endMonth><qryDate>Mon Sep 17 10:15:04 CST 2012</qryDate><qryFlag>1</qryFlag></Query>";
//			String method = "qryUserScoreDetail";
//			String nicode = "6090010024";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("qryUserScoreDetail返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//通过
//		}
//	
//	@Test
//	public void testrscNumberPreUse() {
//		String response;
//		try {
//			String request = "<preUseInfo><areaId>11000</areaId><channelId>11040670</channelId><numberId></numberId>用上面查询的结果，号码id <number></number>用上面查询的结果，号码 <numberType>103</numberType> <handleType></handleType>4是取消预订5是预订 <idCard></idCard>身份证号，数字 <userName></userName> <contactTel></contactTel> <chargeName></chargeName> <staffCode>1001</staffCode> <preTimeLevel></preTimeLevel> </preUseInfo>";
//			String method = "rscNumberPreUse";
//			String nicode = "6090010027";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("rscNumberPreUse返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	//已调到，入参不好
//	
//	@Test
//	public void testrscNumberQuery() {
//		String response;
//		try {
//			String request = "<qryCondition> <preGetFlag>1</preGetFlag> <areaId>11000</areaId> <channelId>11040670</channelId> <prodSpecId></prodSpecId> <numberType>103</numberType> <number></number> <showCount>20</showCount> <staffCode></staffCode> <specifySelNum> <matchNum></matchNum> <minMatchLen></minMatchLen> <numberFee></numberFee> <numberHead>189</numberHead> <numberClass></numberClass> </specifySelNum> </qryCondition>";
//			String method = "rscNumberQuery";
//			String nicode = "6090010027";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("rscNumberPreUse返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	//已调到，入参不好
//	
//	@Test
//	public void testrscLoverNumberQuery() {
//		String response;
//		try {
//			String request = "<qryCondition><preGetFlag></preGetFlag><areaId>11000</areaId><channelId>11040057</channelId><prodSpecId></prodSpecId><numberType>103</numberType><number></number><showCount>6</showCount><staffCode>bj1001</staffCode><specifySelNum><dealFlag>0</dealFlag><loverNum>1</loverNum></specifySelNum></qryCondition>";
//			String method = "rscLoverNumberQuery";
//			String nicode = "6090010027";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("rscLoverNumberQuery返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	//已调到，入参不好
//	
//	@Test
//	public void testgetPUK() {
//		String response;
//		try {
//			String request = "<root><phoneNumber>18911272769</phoneNumber></root>";
//			String method = "getPUK";
//			String nicode = "6090010027";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("getPUK返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	//已调到，入参不好
//	
//	@Test
//	public void testqueryProjProcessInfo() {
//		String response;
//		try {
//			String request = "<DZQDParam><IdentityId>443726196510063825</IdentityId><CustName>test</CustName></DZQDParam>";
//			String method = "queryProjProcessInfo";
//			String nicode = "6090010027";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("queryProjProcessInfo返回结果：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	//已调到，入参不好
//	
//	@Test
//	public void testcardManage() {
//		String response;
//		try {
//			String request = "<DZQDParam><IdentityId>443726196510063825</IdentityId><CustName>test</CustName></DZQDParam>";
//			String method = "cardManage";
//			String nicode = "6090010009";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("cardManage：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//缺入参报文
//		}
//	
//	@Test
//	public void testqueryCardRecord() {
//		String response;
//		try {
//			String request = "<DZQDParam><IdentityId>443726196510063825</IdentityId><CustName>test</CustName></DZQDParam>";
//			String method = "queryCardRecord";
//			String nicode = "6090010009";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("queryCardRecord：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//缺入参报文
//		}
//	
//	@Test
//	public void testcrmFillRecord() {
//		String response;
//		try {
//			String request = "<DZQDParam><IdentityId>443726196510063825</IdentityId><CustName>test</CustName></DZQDParam>";
//			String method = "crmFillRecord";
//			String nicode = "6090010009";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("crmFillRecord：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//缺入参报文
//		}
//	
//	@Test
//	public void testqryPreLock() {
//		String response;
//		try {
//			String request = "<DZQDParam><IdentityId>443726196510063825</IdentityId><CustName>test</CustName></DZQDParam>";
//			String method = "qryPreLock";
//			String nicode = "6090010009";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("qryPreLock：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//这个还不对，报文没找
//		}
//	
//	@Test
//	public void testqueryManager() {
//		String response;
//		try {
//			String request = "<request> <flag>1</flag> <value>P0027203</value> <channelId>510000</channelId> <areaId>11000</areaId> <staffCode>BJ1001</staffCode> </request>";
//			String method = "queryManager";
//			String nicode = "6090010023";
//			response = WebServiceAxisClient.CSBTestMethod(request, method, nicode);
//			System.out.println("queryManager：" + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//这个还不对，报文没找
//		}
//	
//	
//	@Test
//	public void testconfirmSequence() {
//		String response;
//		try {
//			File f = new File("confirmSequence.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "confirmSequence";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("confirmSequence"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testcheckExchangeTicket() {
//		String response;
//		try {
//			File f = new File("checkExchangeTicket.xml");
//			InputStream input = null ;  
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkExchangeTicket";
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkExchangeTicket"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testcheckTicket() {
//		String response;
//		try {
//			File f = new File("checkTicket.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkTicket";
////		       testCSB ts = new testCSB();
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkTicket"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test
//	public void testcheckGiftCert() {
//		String response;
//		try {
//			File f = new File("checkGiftCert.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "checkGiftCert";
////		       testCSB ts = new testCSB();
//		       response = testCsb.exchange(s,name);
//		       System.out.println("checkGiftCert"+response+"***********************************");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	@Test														
//	public void testconfirmCertGift() {
//		String response;
//		try {
//			File f = new File("confirmCertGift.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "confirmCertGift";
////		       testCSB ts = new testCSB();
//		       response = testCsb.exchange(s,name);
//		       System.out.println("confirmCertGift"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testcomputeChargeInfo() {
//		String response;
//		try {
//			File f = new File("computeChargeInfo.xml");
//			InputStream input = null ;
//				input = new FileInputStream(f);
//				  byte b[] = new byte[(int)f.length()] ; 
//			        input.read(b) ; 
//			        input.close() ;
//		       String s =  new String(b);
//		       String name = "computeChargeInfo";
////		       testCSB ts = new testCSB();
//		       response = testCsb.exchange(s,name);
//		       System.out.println("computeChargeInfo"+response+"***********************************");
//		       
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	
	
	}


