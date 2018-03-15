package com.linkage.bss.crm.so.audit.smo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.linkage.bss.BaseTest;
import com.linkage.bss.crm.so.common.SoDomain;

public class SoAuditSMOTest extends BaseTest{

//	private ISoAuditSMO soAuditSmo;
//
//	@Before
//	public void init() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/intfManager-spring-all.xml");
//		soAuditSmo = (ISoAuditSMO) ctx.getBean("soManager.soAuditSMO");
//	}
//	
//
//	/**
//	 * 根据审批券和接入号码校验终端抵用券是否可以兑换
//	 * 入参：审批权和接入号码
//	 * 回参：validateFlag 0=校验通过  1=校验未通过
//	 *       msg 校验结果描述 / 校验通过！/ 接入号码与券不匹配，请确认接入号码和券！/ 该券处于XXX状态,不可对其进行兑换！
//	 *       data 审批权信息,包括以下字段
//	 *      	auditTicketCd	审批券编号
//	 *       	auditTicketId	审批券ID	
//	 *			auditId			审批单ID
//	 *			auditTicketName	审批券名称
//	 *			areaId			区域ID
//	 *			createDt		审批券创建时间
//	 *			endDt			审批券失效时间			
// 	 *			startDt			审批券生效时间
// 	 *			price			审批券金额
//   	 *			ruleCfgId		物品方案ID
//   	 *			boId			
//	 *			busiOrderStatusCd	
//	 *			offerId			
//	 *			accessNumber	
//	 *			offerSpecId		
//	 *			partyId			
//	 *			partyName		
//	 *			channelName		
//	 *			channelId		
//	 *			staffId			
//	 *			staffName		
//	 *			statusCd		审批券状态
//	 *			statusName		审批券状态名称
//	 *			ticketFlag"		审批券标志?
//	 */
//	@Test
//	public void testValidateAuditTicket() {
//		String ticketCd = "51201204066ED0IM305005";
//		String accessNumber = "13349315384";
//		JSONObject result = soAuditSmo.validateAuditTicket(ticketCd, accessNumber);
//		System.out.printf(result.toString());
//	}
//	
//	/**
//	 * 查询审批券
//	 * 回參：page 翻页信息
//			 ticketList 审批券列表
//	 */
//	@Test
//	public void testQueryAuditTicket() {
//		JSONObject ticketParam = new JSONObject();
//		ticketParam.put("ticketCd", "51201204066ED0IM305005");
//		ticketParam.put("accessNumber", "");
//		ticketParam.put("partyName", "");
//		ticketParam.put("startDate","");
//		ticketParam.put("endDate","");
//		ticketParam.put("auditStatusCd","");
//		ticketParam.put("staffId","");
//		ticketParam.put("bcd_code","");
//		ticketParam.put("couponInstanceNumber","");
//		
//		soAuditSmo.queryAuditTicket(ticketParam);
//	}
//	
//	/**
//	 * 审批券预占
//	 */
//	@Test
//	public String testAllocAuditTickets() {
//		JSONArray ticketArray = new JSONArray();
//		JSONObject ticketId = new JSONObject();
//		ticketId.put("auditTicketId", "51201204066ED0IM305005");
//		ticketArray.add(ticketArray);
//		JSONObject str1=soAuditSmo.allocAuditTickets(ticketArray, SoDomain.TICKET_SCENE_EXCHANGE); //兑换预占
//		JSONObject str2=soAuditSmo.allocAuditTickets(ticketArray, SoDomain.TICKET_SCENE_CONVERT); //调换预占
//		return str2.toString();
//	}
//	
//	/**
//	 * 审批券取消预占
//	 */
//	@Test
//	public void testReleaseAuditTickets() {
//		JSONArray ticketArray = new JSONArray();
//		JSONObject ticketId = new JSONObject();
//		ticketId.put("auditTicketId", "51201204066ED0IM305005");
//		ticketArray.add(ticketArray);
//		soAuditSmo.releaseAuditTickets(ticketArray, SoDomain.TICKET_SCENE_EXCHANGE); //兑换预占取消
//		soAuditSmo.releaseAuditTickets(ticketArray, SoDomain.TICKET_SCENE_CONVERT); //调换预占取消
//	}
//	
//	/**
//	 * 审批券兑换
//	 */
//	@Test
//	public void testExchangeAuditTickets() {
//		JSONArray ticketArray = new JSONArray();
//		JSONObject ticketId = new JSONObject();
//		ticketId.put("auditTicketId", "51201204066ED0IM305005");
//		ticketArray.add(ticketArray);
//		
//		JSONObject staffInfo = new JSONObject();
//		staffInfo.put("channelId", "");
//		staffInfo.put("staffId", "");
//		
//		soAuditSmo.exchangeAuditTickets(ticketArray, staffInfo);
//	}
}
