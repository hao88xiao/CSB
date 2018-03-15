package com.linkage.bss.crm.ws.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import bss.common.BssException;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.dto.PartyPointDto;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.facade.SRFacade;
import com.linkage.bss.crm.intf.facade.SoFacade;
import com.linkage.bss.crm.intf.facade.SysFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.storeclient.StoreForOnLineMall;
import com.linkage.bss.crm.local.bmo.IUnityPaySMO;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.so.audit.smo.ISoAuditSMO;
import com.linkage.bss.crm.so.point.smo.ISoPointSMO;
import com.linkage.bss.crm.soservice.crmservice.PointService;
import com.linkage.bss.crm.soservice.crmservice.TicketService;
import com.linkage.bss.crm.store.smo.StoreServiceSMO;
import com.linkage.bss.crm.unityPay.IndentInvoiceNumMod;
import com.linkage.bss.crm.unityPay.IndentInvoiceNumQry;
import com.linkage.bss.crm.unityPay.IndentInvoicePrint;
import com.linkage.bss.crm.unityPay.IndentInvoiceQuery;
import com.linkage.bss.crm.unityPay.IndentInvoiceRepPrint;
import com.linkage.bss.crm.unityPay.TerminalExchangeSync;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * 
 * 营销资源
 * 
 */
@WebService
public class SRService extends AbstractService {

	private static Log logger = Log.getLog(SRService.class);

	private UnityPayService unityPayService;

	private SysFacade sysFacade;

	private CustFacade custFacade;

	private StoreForOnLineMall storeForOnLineMall;

	private ISoAuditSMO soAuditSmo;

	private IntfSMO intfSMO;

	private SRFacade srFacade;

	private StoreServiceSMO storeClientSMO;

	private TicketService ticketService;

	private SoFacade soFacade;

	private OrderService orderService;

	private ISoPointSMO soPointSMO;

	private IUnityPaySMO unityPayClient;

	private PointService pointService;

	private static boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	public void setUnityPayService(UnityPayService unityPayService) {
		this.unityPayService = unityPayService;
	}

	@WebMethod(exclude = true)
	public void setSysFacade(SysFacade sysFacade) {
		this.sysFacade = sysFacade;
	}

	@WebMethod(exclude = true)
	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}

	@WebMethod(exclude = true)
	public void setStoreForOnLineMall(StoreForOnLineMall storeForOnLineMall) {
		this.storeForOnLineMall = storeForOnLineMall;
	}

	@WebMethod(exclude = true)
	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	@WebMethod(exclude = true)
	public void setUnityPayClient(IUnityPaySMO unityPayClient) {
		this.unityPayClient = unityPayClient;
	}

	@WebMethod(exclude = true)
	public void setSoPointSMO(ISoPointSMO soPointSMO) {
		this.soPointSMO = soPointSMO;
	}

	@WebMethod(exclude = true)
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@WebMethod(exclude = true)
	public void setSoFacade(SoFacade soFacade) {
		this.soFacade = soFacade;
	}

	@WebMethod(exclude = true)
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@WebMethod(exclude = true)
	public void setStoreClientSMO(StoreServiceSMO storeClientSMO) {

		this.storeClientSMO = storeClientSMO;
	}

	@WebMethod(exclude = true)
	public void setSrFacade(SRFacade srFacade) {
		this.srFacade = srFacade;
	}

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setSoAuditSmo(ISoAuditSMO soAuditSmo) {
		this.soAuditSmo = soAuditSmo;
	}

	/**
	 * 电子有价卡批量获取请求
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/tradeId", caption = "tradeId"),
			@Node(xpath = "//request/valueCardTypeCode", caption = "valueCardTypeCode") })
	public String goodsBatchGet(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String tradeId = "";
			String saleTime = "";
			String channelId = "";
			String staffCode = "";
			String valueCardTypeCode = "";
			String valueCode = "";
			String applyNum = "";
			int flag = -1;
			tradeId = WSUtil.getXmlNodeText(document, "//request/tradeId");
			saleTime = WSUtil.getXmlNodeText(document, "//request/saleTime");
			channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			valueCardTypeCode = WSUtil.getXmlNodeText(document, "//request/valueCardTypeCode");
			valueCode = WSUtil.getXmlNodeText(document, "//request/valueCode");
			applyNum = WSUtil.getXmlNodeText(document, "//request/applyNum");
			flag = Integer.valueOf(WSUtil.getXmlNodeText(document, "//request/flag"));
			String ReturnMsg = intfSMO.goodsBatchGet(tradeId, saleTime, channelId, staffCode, valueCardTypeCode,
					valueCode, applyNum, flag);
			if ("1".equals(ReturnMsg)) {
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
			} else {
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, ReturnMsg);
			}
		} catch (Exception e) {
			logger.error("goodsBatchGet电子有价卡批量获取请求:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 有价卡礼品受理确认接口 有价卡出库 modify in 2012/11/15
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/info/validity", caption = "串码"),
			@Node(xpath = "//request/platId", caption = "统一支付平台编码，网厅为06"),
			@Node(xpath = "//request/saleNo", caption = "销售流水号"),
			@Node(xpath = "//request/info/sailTime", caption = "销售时间"),
			@Node(xpath = "//request/info/price", caption = "实际价格"),
			@Node(xpath = "//request/info/priceStd", caption = "标价"),
			@Node(xpath = "//request/validateCode", caption = "打发票的验证码"),
			@Node(xpath = "//request/type", caption = "销售类型"),
			@Node(xpath = "//request/payInfo/method", caption = "付款方式"),
			@Node(xpath = "//request/payInfo/amount", caption = "付款金额"),
			@Node(xpath = "//request/payInfo/appendInfo", caption = "付款备注") })
	public String checkSequence(@WebParam(name = "request") String request) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/info/validity");
			paramMap.put("bcdCode", bcdCode);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			paramMap.put("platId", platId);
			paramMap.put("saleNo", WSUtil.getXmlNodeText(document, "//request/saleNo"));
			paramMap.put("saleDate", WSUtil.getXmlNodeText(document, "//request/info/sailTime"));
			paramMap.put("price", WSUtil.getXmlNodeText(document, "//request/info/price"));
			paramMap.put("inOutTypeId", "1");
			paramMap.put("validateCode", WSUtil.getXmlNodeText(document, "//request/validateCode"));
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			long start = System.currentTimeMillis();
			String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
			if (isPrintLog) {
				System.out.println("checkSequence.sysFacade.findStaffIdByStaffCode(根据staffCode取得staffId) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			Document documentStaffInfo = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(documentStaffInfo, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
			paramMap.put("staffId", staffId);
			// 1 为有价卡 2为礼品 现只支持 有价卡
			if ("1".equals(WSUtil.getXmlNodeText(document, "//request/type"))) {
				start = System.currentTimeMillis();
				
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				intfSMO.saveRequestInfo(logId, "CrmWebService", "checkSequenceInfo", request, requestTime);
				String out = srFacade.checkSequence(paramMap);
				intfSMO.saveResponseInfo(logId, "CrmWebService", "checkSequenceInfo", request, requestTime, out, new Date(), "1",
						"0");
				if (isPrintLog) {
					System.out.println("checkSequence.srFacade.checkSequence(有价卡礼品受理确认接口) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				Document doc = WSUtil.parseXml(out);
				String result = WSUtil.getXmlNodeText(doc, "//DoStoreInOutCrm/result");
				String payIndentId = WSUtil.getXmlNodeText(doc, "//DoStoreInOutCrm/Coupons/Coupon/inOutIds");
				payIndentId = platId + payIndentId;
				if (!"0".equals(result)) {
					return WSUtil.buildResponse(ResultCode.SR_SALE_CARD_FAILED, "调用营销资源失败");
				}
				start = System.currentTimeMillis();
				//如果调同步费用失败 就调用 有价卡礼品受理确认 返销接口
				String unityResponse = unityPayService.callIndentItemPay(request, payIndentId);
				if (isPrintLog) {
					System.out.println("checkSequence.unityPayService.callIndentItemPay 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				Document unityDoc = WSUtil.parseXml(unityResponse);
				String unityResult = WSUtil.getXmlNodeText(unityDoc, "//response/resultCode");
				if (!"0".equals(unityResult)) {
					paramMap.put("inOutTypeId", "2"); //inOutTypeId 为2 代表返销
					srFacade.checkSequence(paramMap);
					return WSUtil.buildResponse(ResultCode.UNITYPAY_FAILED, ResultCode.UNITYPAY_FAILED.getDesc());
				}
				String[] bcdCodes = bcdCode.split("\\|");
				for (int i = 0; i < bcdCodes.length; i++) {
					savePayIndentId(bcdCodes[i], payIndentId);
				}
//				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
				StringBuilder sb = new StringBuilder();
				sb.append("<PAY_INDENT_ID>"+payIndentId+"</PAY_INDENT_ID>");
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc(),sb.toString());
			} else {
				return WSUtil.buildResponse("1", "type值错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("checkSequence有价卡礼品受理确认接口 有价卡出库:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	private void savePayIndentId(String bcdCode, String payIndentId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bcdCode", bcdCode);
		map.put("payIndentId", payIndentId);
		intfSMO.savePayIndentId(map);
	}

	/**
	 * 营销资源 销售出、退库
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String doStoreInOutCrm(@WebParam(name = "request") String request) {
		try {
//			 srFacade storeClientSMO
			String result = srFacade.doStoreInOutCrm(request);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("doStoreInOutCrm销售出、退库:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 抵扣券校验接口
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "主接入号：credenceNo") })
	public String checkTicket(@WebParam(name = "request") String request) {
		try {
			// 入参报文转换
			String inputXml = changeRequestXml(request);
			String returnStr = ticketService.checkTicket(inputXml);
			// 出参转换
			String returnXml = changeReturnXml(returnStr, "checkSequenceResp");
			return returnXml;
		} catch (Exception e) {
			logger.error("checkTicket抵扣券校验接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 抵扣券兑换校验接口
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/credenceNo", caption = "抵扣卷编号：credenceNo"),
			@Node(xpath = "//request/pwd", caption = "抵扣卷密码：pwd"),
			@Node(xpath = "//request/terminalCode", caption = "终端串号：terminalCode") })
	public String checkExchangeTicket(@WebParam(name = "request") String request) {
		try {
			// 入参报文转换
			String inputXml = changeRequestXml(request);
			String returnStr = ticketService.checkExchangeTicket(inputXml);
			// 出参转换
			String returnXml = changeReturnXml(returnStr, "confirmSequenceResp");
			return returnXml;
		} catch (Exception e) {
			logger.error("checkExchangeTicket抵扣券兑换校验接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 *抵扣券兑换接口
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/credenceNo", caption = "抵扣卷编号：credenceNo"),
			@Node(xpath = "//request/pwd", caption = "抵扣卷密码：pwd"),
			@Node(xpath = "//request/terminalCode", caption = "终端串号：terminalCode") })
	public String confirmSequence(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			// 入参报文转换
			String inputXml = changeRequestXml(request);
			long start = System.currentTimeMillis();
			String returnStr = ticketService.commitExchangeTicket(inputXml);
			if (isPrintLog) {
				System.out.println("confirmSequence.ticketService.commitExchangeTicket(抵扣券兑换) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			// 出参转换
			String returnXml = changeReturnXml(returnStr, "confirmSequenceResp");

			if ((returnXml != null) && (returnXml.contains("<resultCode>0</resultCode>"))) {
				try {
					Map<String, Object> saveMapTemp = new HashMap<String, Object>();
					saveMapTemp.put("bcdCode", "1234567890");
					saveMapTemp.put("xmlIn", returnStr);
					saveMapTemp.put("xmlOut", returnXml);
					start = System.currentTimeMillis();
					intfSMO.saveSRInOut(saveMapTemp);
					if (isPrintLog) {
						System.out.println("confirmSequence.intfSMO.saveSRInOut(记录调用营销资源出入库接口的参数) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					Document documentReturn = WSUtil.parseXml(returnStr);
					String servId = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/servId");
					String accNbr = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/accNbr");
					String ticketGenDate = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/ticketGenDate");
					String staffIdGen = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/staffIdGen");
					String staffIdReverse = WSUtil.getXmlNodeText(documentReturn,
							"//confirmSequenceResp/staffIdReverse");
					String offerSpecName = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/offerSpecName");
					String couponName = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/couponName");
					String couponId = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/couponId");
					String bcdCode = WSUtil.getXmlNodeText(documentReturn, "//confirmSequenceResp/bcdCode");

					TerminalExchangeSync terminalExchangeSync = new TerminalExchangeSync();
					String requestId = "";
					String requestTime = "";
					String platId = "03"; // 目前外围系统中，网厅和代理商都可以受理抵扣券兑换，经和左一村讨论，暂时先填写03
					String cycleId = "";
					String exchangeDate = "";
					String staffId = "";
					String channelId = "";
					String payIndentId = "";
					String charge = "";
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					requestTime = format.format(new Date());
					requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
					exchangeDate = requestTime;
					Document doc = WSUtil.parseXml(request);
					channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
					String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
					start = System.currentTimeMillis();
					staffId = sysFacade.findStaffIdByStaffCode(staffCode);
					if (isPrintLog) {
						System.out
								.println("confirmSequence.sysFacade.findStaffIdByStaffCode(根据staffCode取得staffId) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
					Document document = WSUtil.parseXml(staffId);
					staffId = WSUtil.getXmlNodeText(document, "/StaffInfos/StaffInfo/staffNumberInfo/staffId");
					String auditTicketCd = WSUtil.getXmlNodeText(doc, "//request/sequenceId");
					//					Map<String, Object> pamaMap = new HashMap<String, Object>();
					//					pamaMap.put("auditTicketCd", auditTicketCd);
					//					Map<String, Object> busiInfo = intfSMO.queryAuditingTicketBusiInfo(pamaMap);
					//					String boId = busiInfo.get("BO_ID") != null ? busiInfo.get("BO_ID").toString() : "";
					//					if ("".equals(boId)) {
					//						logger.error("抵扣券兑换时调用统一支付终端兑换款同步接口发生异常:boId为空  " + request);
					//					}
					//					pamaMap.put("boId", boId);
					//					pamaMap.put("olId", "-1");
					//					String strOlId = intfSMO.queryOlIdByBoId(pamaMap);
					//					if ("".equals(strOlId)) {
					//						logger.error("抵扣券兑换时调用统一支付终端兑换款同步接口发生异常:olId为空  " + request);
					//					}
					//				payIndentId = "03" + strOlId;
					start = System.currentTimeMillis();
					String auditTicketId = intfSMO.getTicketIdByCd(auditTicketCd);
					if (isPrintLog) {
						System.out.println("confirmSequence.intfSMO.getTicketIdByCd(通过抵扣券编码查询抵扣券Id) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					payIndentId = "03" + auditTicketId;
					cycleId = requestTime.substring(0, 8);
					start = System.currentTimeMillis();
					charge = intfSMO.getChargeByTicketCd(auditTicketCd);
					if (isPrintLog) {
						System.out.println("confirmSequence.intfSMO.getChargeByTicketCd(根据auditTicketCd得到券的价值属性) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					start = System.currentTimeMillis();
					String toChannelId = intfSMO.getChannelIdByTicketCd(auditTicketCd);
					if (isPrintLog) {
						System.out
								.println("confirmSequence.intfSMO.getChannelIdByTicketCd(通过抵扣券编码的到发放该抵扣券的channel) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
					terminalExchangeSync.setRequestId(requestId);
					terminalExchangeSync.setRequestTime(requestTime);
					terminalExchangeSync.setPlatId(platId);
					terminalExchangeSync.setCycleId(Integer.parseInt(cycleId));
					terminalExchangeSync.setExchangeDate(exchangeDate);
					terminalExchangeSync.setStaffId(staffId);
					terminalExchangeSync.setChannelId(toChannelId);
					terminalExchangeSync.setPayIndentId(payIndentId);
					terminalExchangeSync.setCharge((int) Double.parseDouble(charge));
					terminalExchangeSync.setAudit_ticket_cd(auditTicketCd);
					start = System.currentTimeMillis();
					String facevalue = intfSMO.getFaceValueByTicketCd(auditTicketCd);
					if (isPrintLog) {
						System.out
								.println("confirmSequence.intfSMO.getFaceValueByTicketCd(根据 auditTicketCd 查询 抵扣券的面值) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
					terminalExchangeSync.setFacevalue(facevalue);
					terminalExchangeSync.setRemark("外围系统通过接口兑换");
					terminalExchangeSync.setServId(servId);
					terminalExchangeSync.setAccNbr(accNbr);
					terminalExchangeSync.setTicketGenDate(ticketGenDate);
					terminalExchangeSync.setStaffidGen(staffIdGen);
					terminalExchangeSync.setStaffidReverse(staffIdReverse);
					terminalExchangeSync.setOfferSpecName(offerSpecName);
					terminalExchangeSync.setCouponName(couponName);
					terminalExchangeSync.setCouponId(couponId);
					terminalExchangeSync.setBcdCode(bcdCode);
					start = System.currentTimeMillis();
					Map<String, Object> resultMap = unityPayClient.terminalExchangeSync(terminalExchangeSync);
					if (isPrintLog) {
						System.out.println("confirmSequence.unityPayClient.terminalExchangeSync(终端兑换款同步接口) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

					String request_Obj = JSONObject.fromObject(terminalExchangeSync).toString();

					String result = "";
					result = String.valueOf(resultMap.get("result"));
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("auditTicketCd", auditTicketCd);
					saveMap.put("requestId", requestId);
					saveMap.put("requestTime", requestTime);
					saveMap.put("platId", platId);
					saveMap.put("cycleId", cycleId);
					saveMap.put("exchangeDate", exchangeDate);
					saveMap.put("staffId", staffId);
					saveMap.put("channelId", toChannelId);
					saveMap.put("payIndentId", payIndentId);
					saveMap.put("charge", charge);
					saveMap.put("result", result);
					saveMap.put("request", request_Obj);
					start = System.currentTimeMillis();
					intfSMO.saveTicketCd2terminal(saveMap);
					if (isPrintLog) {
						System.out.println("confirmSequence.intfSMO.saveTicketCd2terminal(存储抵扣券兑换时传给统一支付的数据) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
				} catch (Exception e) {
					logger.error("抵扣券兑换时调用统一支付终端兑换款同步接口发生异常" + request, e);
				}
			}
			if (isPrintLog) {
				System.out
						.println("调用营业接口confirmSequence(抵扣券兑换接口)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return returnXml;
		} catch (Exception e) {
			logger.error("confirmSequence抵扣券兑换接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 修改外系统的入参格式以满足营业需要
	 * 
	 * @param XML
	 * @return
	 * @author ZHANGC
	 * @throws DocumentException
	 */
	@WebMethod(exclude = true)
	private String changeRequestXml(String xml) {
		String returnXml = "";
		Document doc;
		try {
			doc = WSUtil.parseXml(xml);
			org.dom4j.Node root = WSUtil.getXmlNode(doc, "/request");
			root.setName("root");
			//			root.selectSingleNode("./credenceNo").setName("sequenceId");
			returnXml = doc.asXML();
			return returnXml;
		} catch (DocumentException e) {
			logger.error("changeRequestXml修改外系统的入参格式以满足营业需要:" + xml, e);
			throw new RuntimeException("解析报文异常: ", e);
		}
	}

	/**
	 * 修改营业出参格式以满足外系统需要
	 * 
	 * @param XML
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod(exclude = true)
	private String changeReturnXml(String xml, String rootNodeName) {
		String returnXml = "";
		Document doc;
		try {
			doc = WSUtil.parseXml(xml);
			org.dom4j.Node root = WSUtil.getXmlNode(doc, rootNodeName);
			org.dom4j.Node resultNode = root.selectSingleNode("result");
			resultNode.setName("resultCode");
			root.setName("response");
			returnXml = doc.asXML();
			return returnXml;
		} catch (DocumentException e) {
			logger.error("changeReturnXml修改营业出参格式以满足外系统需要:" + xml + "  " + rootNodeName, e);
			throw new RuntimeException("解析报文异常: ", e);
		}
	}

	/**
	 * 礼券校验
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/bcd_code", caption = "券实例编码:bcd_code") })
	public String checkGiftCert(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcd_code");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			// 校验实例
			String resultMsg = srFacade.getMaterialByCode("-1", bcdCode);
			if (resultMsg == null) {
				// 调用异常
				return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR);
			}
			// 解析调用结果
			Document doc = WSUtil.parseXml(resultMsg);
			String materialStatus = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/status");
			String storeId = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/storeId");
			if (materialStatus == null || storeId == null) {
				// 查询结果为空
				return WSUtil.buildResponse(ResultCode.MATERIAL_CODE_NOT_EXIST);
			}
			// 如果物品状态为可用（A）认为校验成功
			if (WSDomain.MATERIALSTATUS.AVAILABLE.equals(materialStatus)) {
				// 校验物品仓库和当前渠道是否匹配
				int flag = soFacade.existsByStoreIdAndChannelId(Long.valueOf(channelId), Long.valueOf(storeId));
				if (flag > 0) {
					// 匹配
					return WSUtil.buildResponse(ResultCode.SUCCESS, "校验成功，可兑换");
				} else {
					// 物品所在仓库与渠道不匹配
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "校验失败，此实例对应的仓库：" + storeId + "不在当前渠道" + channelId
							+ "下");
				}
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "校验失败，此实例状态为：" + materialStatus + "不可用");
			}
		} catch (Exception e) {
			logger.error("checkGiftCert礼券校验:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 礼券兑换
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/bcd_code", caption = "券实例编码:bcd_code") })
	public String confirmCertGift(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcd_code");
			String partyId = WSUtil.getXmlNodeText(document, "//request/partyId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String offerSpecId = WSUtil.getXmlNodeText(document, "//request/offerSpecId");
			String offerSpecName = WSUtil.getXmlNodeText(document, "//request/offerSpecName");
			String startDt = WSUtil.getXmlNodeText(document, "//request/startDt");
			String endDt = WSUtil.getXmlNodeText(document, "//request/endDt");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			String prodId = WSUtil.getXmlNodeText(document, "//request/prodId");
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			// <GoodsDetailList><GoodsDetail><storeId>220000000</storeId><materialId>54896</materialId><productorId>1000</productorId><batchId>10143567</batchId><status>C</status><referPrice>1200.0</referPrice><remark>全国话费礼券1200元-3G</remark><storeName>北京电信</storeName><productorName>默认供货商</productorName><materialName>全国话费礼券1200元-3G</materialName><statusName>已售</statusName><meid/><bcdCode>ALX3111200027085</bcdCode><materialTypeId>44</materialTypeId><materialTypeName>话费礼券</materialTypeName><materialTypeDesc>B01A7</materialTypeDesc></GoodsDetail></GoodsDetailList>
			// 校验实例
			String resultMsg = srFacade.getMaterialByCode("-1", bcdCode);
			// 解析返回结果
			Document doc = WSUtil.parseXml(resultMsg);
			String mktResId = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/materialId");

			String materialStatus = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/status");
			String storeId = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/storeId");
			String storeName = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/storeName");
			String agentId = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/productorId");
			String agentName = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/productorName");
			// 校验一下物品状态
			if (!WSDomain.MATERIALSTATUS.AVAILABLE.equals(materialStatus)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "此实例当前状态为：" + materialStatus + "不为可用状态");
			}
			// 创建一点通入参报文
			StringBuffer sb = new StringBuffer();
			sb.append("<request><order><orderTypeId>17</orderTypeId>");
			sb.append("<systemId>6090010028</systemId>");
			sb.append("<partyId>" + partyId + "</partyId>");
			sb.append("<prodSpecId>" + prodSpecId + "</prodSpecId>");
			sb.append("<prodId>" + prodId + "</prodId>");
			sb.append("<offerSpecs><offerSpec>");
			sb.append("<id>" + offerSpecId + "</id>");
			sb.append("<name>" + offerSpecName + "</name>");
			sb.append("<actionType>0</actionType>");
			sb.append("<startDt>" + startDt + "</startDt>");
			sb.append("<endDt>" + endDt + "</endDt>");
			sb.append("<couponInfos><couponInfo>");
			sb.append("<couponInstanceNumber>" + bcdCode + "</couponInstanceNumber>");
			sb.append("<couponUsageTypeCd>3</couponUsageTypeCd>");
			sb.append("<inOutTypeId>1</inOutTypeId>");
			sb.append("<inOutReasonId>3</inOutReasonId>");
			sb.append("<saleId>1</saleId>");
			sb.append("<couponId>" + mktResId + "</couponId>");
			sb.append("<couponinfoStatusCd>A</couponinfoStatusCd>");
			sb.append("<chargeItemCd>62</chargeItemCd>");
			sb.append("<couponNum>1</couponNum>");
			sb.append("<storeId>" + storeId + "</storeId>");
			sb.append("<storeName>" + storeName + "</storeName>");
			sb.append("<agentId>" + agentId + "</agentId>");
			sb.append("<agentName>" + agentName + "</agentName>");
			sb.append("<apCharge>0</apCharge>");
			sb.append("<ruleId></ruleId>");
			sb.append("</couponInfo></couponInfos></offerSpec></offerSpecs></order>");
			sb.append("<channelId>" + channelId + "</channelId>");
			sb.append("<staffCode>" + staffCode + "</staffCode>");
			sb.append("<areaId>" + areaId + "</areaId>");
			sb.append("</request>");

			String paramStr = sb.toString();

			String resultStr = orderService.businessService(paramStr);
			return resultStr;
		} catch (Exception e) {
			logger.error("confirmCertGift礼券兑换:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 积分兑换物品:积分扣减
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/couponId", caption = "实例编码:couponId"),
			@Node(xpath = "//request/partyId", caption = "客户编码"),
			@Node(xpath = "//request/pointUse", caption = "扣减的积分"),
			@Node(xpath = "//request/accessNumber", caption = "接入号"),
			@Node(xpath = "//request/areaId", caption = "区域标识") })
	public String insertPoint(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String partyId = WSUtil.getXmlNodeText(document, "//request/partyId");
			String couponId = WSUtil.getXmlNodeText(document, "//request/couponId");
			String pointUse = WSUtil.getXmlNodeText(document, "//request/pointUse");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String accessNumber = WSUtil.getXmlNodeText(document, "//request/accessNumber");

			// 根据传进来的实例编码 查一下物品的仓库
			String resultMsg = srFacade.getMaterialByCode("-1", couponId);
			Document doc = WSUtil.parseXml(resultMsg);
			String storeId = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/storeId");
			if (storeId == null) {
				return WSUtil.buildResponse(ResultCode.MATERIAL_INST_NOT_EXIST, "没有查到物品所属仓库信息，请确认实例编码：" + couponId);
			}

			//调整积分起兑标准
			//关于开放20户以下军官证士官证用户在网厅兑换权限需求
			Map<String, Object> mc = new HashMap<String, Object>();
			mc.put("partyId", partyId);
			Map<String, Object> checkIsCanExchangePoint = pointService.checkIsCanExchangePoint(mc);
			Object object = checkIsCanExchangePoint.get("resultCode");
			if (!"0".equals(object))
				return WSUtil.buildResponse(ResultCode.SCORE_CHECK_CRM, "客户:" + partyId + " 积分业务校验不通过，具体原因："
						+ checkIsCanExchangePoint.get("resultMessage"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("staffId", staffId);
			paramMap.put("channelId", channelId);
			paramMap.put("areaId", areaId);
			paramMap.put("couponId", couponId);
			paramMap.put("storeId", storeId);
			paramMap.put("pointUse", pointUse);
			paramMap.put("partyId", partyId);
			paramMap.put("accessNumber", accessNumber);
			// 调用营业的方法 做积分的扣减
			String boid = soPointSMO.insertPoint4LocalIntf(paramMap);
			return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "boid", boid);

		} catch (BssException bssE) {
			logger.error("insertPoint积分兑换物品:积分扣减:" + request, bssE);
			return WSUtil.buildResponse(ResultCode.INSERTPOINT_BSS_ERROR, bssE.getMessage());
		} catch (Exception e) {
			logger.error("insertPoint积分兑换物品:积分扣减:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 积分兑换物品:物品出库
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/boid", caption = "积分兑换流水号:boid") })
	public String updateAndConfirmPoint(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String boid = WSUtil.getXmlNodeText(document, "//request/boid");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("boId", boid);
			// 调用营业方法 做物品出库
			soPointSMO.updateAndConfirmPoint4LocalIntf(paramMap);
			return WSUtil.buildResponse(ResultCode.SUCCESS, "物品出库成功");
		} catch (Exception e) {
			logger.error("updateAndConfirmPoint积分兑换物品:物品出库:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 积分兑换物品:返销
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/boid", caption = "积分兑换流水号:boid") })
	public String insertPointRefund(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String boid = WSUtil.getXmlNodeText(document, "//request/boid");
			String partyId = WSUtil.getXmlNodeText(document, "//request/partyId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");

			Party party = custFacade.getPartyById(partyId);
			String partyAreaId = String.valueOf(party.getAreaId());

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("staffId", staffId);
			paramMap.put("partyId", partyId);
			paramMap.put("channelId", channelId);
			paramMap.put("areaId", areaId);
			paramMap.put("boId", boid);
			paramMap.put("partyAreaId", partyAreaId);
			// 调用营业方法 做返销
			soPointSMO.insertPointRefund4LocalIntf(paramMap);
			return WSUtil.buildResponse(ResultCode.SUCCESS, "返销成功");
		} catch (Exception e) {
			logger.error("insertPointRefund积分兑换物品:物品出库:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "返销失败" + e.getMessage());
		}
	}

	/**
	 * 获取票据号
	 * 
	 * @author TERFY
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/platId", caption = "费用来源的平台标识:platId"),
			@Node(xpath = "//request/staffId", caption = "营业员标识：staffId"),
			@Node(xpath = "//request/invoiceType", caption = "票据类型：invoiceType") })
	public String indentInvoiceNumQryIntf(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String invoiceType = WSUtil.getXmlNodeText(document, "//request/invoiceType");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String cycleId = requestTime.substring(0, 8);
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			IndentInvoiceNumQry indentInvoiceNumQry = new IndentInvoiceNumQry();
			indentInvoiceNumQry.setCycleId(Integer.valueOf(cycleId));
			indentInvoiceNumQry.setPlatId(platId);
			indentInvoiceNumQry.setRequestId(requestId);
			indentInvoiceNumQry.setRequestTime(requestTime);
			indentInvoiceNumQry.setStaffId(staffId);
			indentInvoiceNumQry.setInvoiceType(Integer.valueOf(invoiceType));
			long start = System.currentTimeMillis();
			Map<String, Object> result = unityPayClient.indentInvoiceNumQry(indentInvoiceNumQry);
			if (isPrintLog) {
				System.out.println("indentInvoiceNumQryIntf.unityPayClient.indentInvoiceNumQry(当前票据查询接口) 执行时间:"
						+ (System.currentTimeMillis() - start));
				System.out.println("调用营业接口indentInvoiceNumQryIntf(获取票据号)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return mapEngine.transform("indentInvoiceNumQry", result);
		} catch (Exception e) {
			logger.error("indentInvoiceNumQryIntf:获取票据号" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口indentInvoiceNumQryIntf(获取票据号)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "获取发票号异常信息：" + e.getMessage());
		}
	}

	/**
	 * 修改票据号
	 * 
	 * @author TERFY
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/platId", caption = "费用来源的平台标识:platId"),
			@Node(xpath = "//request/staffId", caption = "营业员标识：staffId"),
			@Node(xpath = "//request/invoiceId", caption = "票据号：invoiceId"),
			@Node(xpath = "//request/invoiceType", caption = "票据类型：invoiceType") })
	public String indentInvoiceNumModIntf(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String invoiceId = WSUtil.getXmlNodeText(document, "//request/invoiceId");
			String invoiceType = WSUtil.getXmlNodeText(document, "//request/invoiceType");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String cycleId = requestTime.substring(0, 8);
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			IndentInvoiceNumMod indentInvoiceNumMod = new IndentInvoiceNumMod();
			indentInvoiceNumMod.setCycleId(Integer.valueOf(cycleId));
			indentInvoiceNumMod.setPlatId(platId);
			indentInvoiceNumMod.setRequestId(requestId);
			indentInvoiceNumMod.setRequestTime(requestTime);
			indentInvoiceNumMod.setStaffId(staffId);
			indentInvoiceNumMod.setInvoiceId(invoiceId);
			indentInvoiceNumMod.setInvoiceType(Integer.valueOf(invoiceType));
			Map<String, Object> result = unityPayClient.indentInvoiceNumMod(indentInvoiceNumMod);
			return mapEngine.transform("indentInvoiceNumMod", result);
		} catch (Exception e) {
			logger.error("indentInvoiceNumModIntf:修改票据号" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "修改票据号异常信息：" + e.getMessage());
		}
	}

	/**
	 * 发票补打
	 * 
	 * @author TERFY
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/platId", caption = "费用来源的平台标识:platId"),
			@Node(xpath = "//request/staffId", caption = "营业员标识：staffId"),
			/*@Node(xpath = "//request/invoiceId", caption = "票据号：invoiceId"),*/
			@Node(xpath = "//request/invoiceType", caption = "票据类型：invoiceType"),
			@Node(xpath = "//request/channelId", caption = "渠道ID：channelId"),
			@Node(xpath = "//request/printType", caption = "分合单标识：printType"),
			/*@Node(xpath = "//request/email", caption = "邮件地址：email"),
			@Node(xpath = "//request/phoneNbr", caption = "电话号：phoneNbr"),*/
			@Node(xpath = "//request/payIndentId", caption = "支付订单号：payIndentId") })
	public String indentInvoicePrintIntf(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String printType = WSUtil.getXmlNodeText(document, "//request/printType");
			String invoiceType = WSUtil.getXmlNodeText(document, "//request/invoiceType");
			String invoiceId = WSUtil.getXmlNodeText(document, "//request/invoiceId");
			//新增
			String payIndentId = WSUtil.getXmlNodeText(document, "//request/payIndentId");
			String phoneNbr = WSUtil.getXmlNodeText(document, "//request/phoneNbr");
			String email = WSUtil.getXmlNodeText(document, "//request/email");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String cycleId = requestTime.substring(0, 8);
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			IndentInvoicePrint indentInvoicePrint = new IndentInvoicePrint();
			indentInvoicePrint.setPayIndentId(payIndentId);
			indentInvoicePrint.setChannelId(channelId);
			indentInvoicePrint.setPrintType(Integer.valueOf(printType));
			indentInvoicePrint.setCycleId(Integer.valueOf(cycleId));
			indentInvoicePrint.setPlatId(platId);
			indentInvoicePrint.setRequestId(requestId);
			indentInvoicePrint.setRequestTime(requestTime);
			indentInvoicePrint.setStaffId(staffId);
			indentInvoicePrint.setInvoiceId(invoiceId);
			indentInvoicePrint.setInvoiceType(Integer.valueOf(invoiceType));
			Map<String, Object> result = unityPayClient.indentInvoicePrint(indentInvoicePrint);
			logger.debug("发票补打结果：{}", JSONObject.fromObject(result).toString());
			result = WSUtil.json2Map(JSONObject.fromObject(result));
			return mapEngine.transform("indentInvoicePrint", result);
		} catch (Exception e) {
			logger.error("indentInvoicePrintIntf:发票补打" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "发票补打异常信息：" + e.getMessage());
		}
	}

	/**
	 * 发票重打接口
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/platId", caption = "费用来源标识:platId"),
			@Node(xpath = "//request/payIndentId", caption = "支付订单号:payIndentId"),
			@Node(xpath = "//request/staffCode", caption = "员工工号:staffCode"),
			@Node(xpath = "//request/invoiceType", caption = "票据类型:invoiceType") })
	public String indentInvoiceRepPrintIntf(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String platId = WSUtil.getXmlNodeText(document, "request/platId");
			String payIndentId = WSUtil.getXmlNodeText(document, "request/payIndentId");
			String staffCode = WSUtil.getXmlNodeText(document, "request/staffCode");
			String invoiceType = WSUtil.getXmlNodeText(document, "request/invoiceType");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			IndentInvoiceRepPrint indentInvoiceRepPrint = new IndentInvoiceRepPrint();
			indentInvoiceRepPrint.setInvoiceType(Integer.valueOf(invoiceType));
			indentInvoiceRepPrint.setPayIndentId(payIndentId);
			indentInvoiceRepPrint.setPlatId(platId);
			indentInvoiceRepPrint.setRequestId(requestId);
			indentInvoiceRepPrint.setRequestTime(requestTime);
			indentInvoiceRepPrint.setStaffCode(staffCode);
			Map<String, Object> result = unityPayClient.indentInvoiceRepPrint(indentInvoiceRepPrint);
			logger.debug("发票重打结果：{}", JSONObject.fromObject(result).toString());
			return mapEngine.transform("indentInvoiceRepPrintIntf", result);
		} catch (Exception e) {
			logger.error("indentInvoiceRepPrintIntf:发票重打接口" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "发票重打接口异常信息：" + e.getMessage());
		}
	}

	/**
	 * 有价卡补打发票查询
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	public String queryInvoiceReprint(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcdCode");
			String password = WSUtil.getXmlNodeText(document, "//request/password");
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String resultSR = "";
			try {
				resultSR = storeForOnLineMall.identifyValidateCode(bcdCode, password);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				resultSR = "<ContractRoot><TcpCont><Response><RspCode>1</RspCode>"
					+  
				"<RspDesc>账号与密码不对应</RspDesc></Response></TcpCont></ContractRoot>";
				
			}
			
			Document doc = WSUtil.parseXml(resultSR);
//			Document doc = WSUtil.parseXml(request);
			String rspCode = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspCode");
			if ("0".equals(rspCode)) {
				String payIndentId = intfSMO.getPayIndentIdByBcdCode(bcdCode);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String requestTime = format.format(new Date());
				String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
				IndentInvoiceQuery indentInvoiceQuery = new IndentInvoiceQuery();
				indentInvoiceQuery.setPayIndentId(payIndentId);
				indentInvoiceQuery.setPlatId(platId);
				indentInvoiceQuery.setRequestId(requestId);
				indentInvoiceQuery.setRequestTime(requestTime);
				Map<String, Object> resultUnityPay = unityPayClient.indentInvoiceQuery(indentInvoiceQuery);
				resultUnityPay.put("resultCode", ResultCode.SUCCESS);
				resultUnityPay.put("resultMsg", "");
				if(payIndentId == null){
					payIndentId = "";
				}
				resultUnityPay.put("payIndentId", payIndentId);
				return mapEngine.transform("queryInvoiceReprint", resultUnityPay);
			} else {
				String resultMsg = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspDesc");
				return WSUtil.buildResponse("-1", resultMsg);
			}
		} catch (Exception e) {
			logger.error("queryInvoiceReprint:有价卡补打发票查询" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 有价卡补打发票确认（打印发票）
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/bcdCode", caption = "bcdCode"),
			@Node(xpath = "//request/password", caption = "密码"), @Node(xpath = "//request/platId", caption = "平台码") })
	public String confirmPrint(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcdCode");
			String password = WSUtil.getXmlNodeText(document, "//request/password");
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String result = storeForOnLineMall.identifyValidateCode(bcdCode, password);
			Document doc = WSUtil.parseXml(result);
			String rspCode = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspCode");
			String RspType = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspType");
			if ("0".equals(RspType)) {
				if (!"0".equals(rspCode)) {
					return WSUtil.buildResponse(ResultCode.PWD_ERROR, "串号或密码错误");
				}
			} else {
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "与营销资源接口交互失败");
			}
			String payIndentId = intfSMO.getPayIndentIdByBcdCode(bcdCode);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String cycleId = requestTime.substring(0, 8);
			String staffId = sysFacade.findStaffIdByStaffCode(staffCode);
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String printType = "0";
			String invoiceType = WSUtil.getXmlNodeText(document, "//request/invoiceType");
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			StringBuffer getInvoiceIdBuffer = new StringBuffer();
			getInvoiceIdBuffer.append("<request>");
			getInvoiceIdBuffer.append("<requestId>").append(requestId).append("</requestId>");
			getInvoiceIdBuffer.append("<requestTime>").append(requestTime).append("</requestTime>");
			getInvoiceIdBuffer.append("<platId>").append(platId).append("</platId>");
			getInvoiceIdBuffer.append("<cycleId>").append(cycleId).append("</cycleId>");
			getInvoiceIdBuffer.append("<staffId>").append(staffId).append("</staffId>");
			getInvoiceIdBuffer.append("<invoiceType>").append(invoiceType).append("</invoiceType>");
			getInvoiceIdBuffer.append("</request>");
			String getInvoiceIdOut = this.indentInvoiceNumQryIntf(getInvoiceIdBuffer.toString());
			Document getInvoiceIdDoc = WSUtil.parseXml(getInvoiceIdOut);
			String getInvoiceIdCode = WSUtil.getXmlNodeText(getInvoiceIdDoc, "//response/resultCode");
			if (!"0".equals(getInvoiceIdCode)) {
				return WSUtil.buildResponse(ResultCode.GETINCOICEID_ERROR, ResultCode.GETINCOICEID_ERROR.getDesc());
			}
			String invoiceId = WSUtil.getXmlNodeText(getInvoiceIdDoc, "//response/invoiceNbr");
			requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			StringBuffer xmlBuffer = new StringBuffer();
			xmlBuffer.append("<request>");
			xmlBuffer.append("<requestId>").append(requestId).append("</requestId>");
			xmlBuffer.append("<requestTime>").append(requestTime).append("</requestTime>");
			xmlBuffer.append("<platId>").append(platId).append("</platId>");
			xmlBuffer.append("<cycleId>").append(cycleId).append("</cycleId>");
			xmlBuffer.append("<payIndentId>").append(payIndentId).append("</payIndentId>");
			xmlBuffer.append("<staffId>").append(staffId).append("</staffId>");
			xmlBuffer.append("<channelId>").append(channelId).append("</channelId>");
			xmlBuffer.append("<printType>").append(printType).append("</printType>");
			xmlBuffer.append("<invoiceType>").append(invoiceType).append("</invoiceType>");
			xmlBuffer.append("<invoiceId>").append(invoiceId).append("</invoiceId>");
			xmlBuffer.append("</request>");
			String xmlIn = xmlBuffer.toString();
			String xmlOut = this.indentInvoicePrintIntf(xmlIn);
			return xmlOut;
		} catch (Exception e) {
			logger.error("confirmPrint:有价卡补打发票确认（打印发票）:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 有价卡补打发票确认（打印发票）(给自助终端)
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/bcdCode", caption = "串码:bcdCode"),
			@Node(xpath = "//request/password", caption = "验证码:password"),
			@Node(xpath = "//request/platId", caption = "费用来源标识:platId"),
			@Node(xpath = "//request/staffCode", caption = "员工工号:staffCode"),
			@Node(xpath = "//request/invoiceId", caption = "票据号:invoiceId") })
	public String confirmPrintSelf(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcdCode");
			String password = WSUtil.getXmlNodeText(document, "//request/password");
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			String invoiceId = WSUtil.getXmlNodeText(document, "//request/invoiceId");
			String result = storeForOnLineMall.identifyValidateCode(bcdCode, password);
			Document doc = WSUtil.parseXml(result);
			String rspCode = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspCode");
			String RspType = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspType");
			if ("0".equals(RspType)) {
				if (!"0".equals(rspCode)) {
					return WSUtil.buildResponse(ResultCode.PWD_ERROR, "串号或密码错误");
				}
			} else {
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "与营销资源接口交互失败");
			}
			String payIndentId = intfSMO.getPayIndentIdByBcdCode(bcdCode);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String requestTime = format.format(new Date());
			String cycleId = requestTime.substring(0, 8);
			//			String staffId = "100000031";
			String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
			Document documentStaffInfo = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(documentStaffInfo, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String printType = "0";
			String invoiceType = "2"; // 自助终端卷式发票
			String requestId = String.format("%s%s10%s", requestTime, getUnitySeq(), platId);
			StringBuffer xmlBuffer = new StringBuffer();
			xmlBuffer.append("<request>");
			xmlBuffer.append("<requestId>").append(requestId).append("</requestId>");
			xmlBuffer.append("<requestTime>").append(requestTime).append("</requestTime>");
			xmlBuffer.append("<platId>").append(platId).append("</platId>");
			xmlBuffer.append("<cycleId>").append(cycleId).append("</cycleId>");
			xmlBuffer.append("<payIndentId>").append(payIndentId).append("</payIndentId>");
			xmlBuffer.append("<staffId>").append(staffId).append("</staffId>");
			xmlBuffer.append("<channelId>").append(channelId).append("</channelId>");
			xmlBuffer.append("<printType>").append(printType).append("</printType>");
			xmlBuffer.append("<invoiceType>").append(invoiceType).append("</invoiceType>");
			xmlBuffer.append("<invoiceId>").append(invoiceId).append("</invoiceId>");
			xmlBuffer.append("</request>");
			String xmlIn = xmlBuffer.toString();
			String xmlOut = this.indentInvoicePrintIntf(xmlIn);
			return xmlOut;
		} catch (Exception e) {
			logger.error("confirmPrintSelf:有价卡补打发票确认（打印发票）(给自助终端):" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 营销资源校验接口 根据物品串码，校验是否可以出库、兑换等
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/couponCode", caption = "couponCode"),
			@Node(xpath = "//request/typeId", caption = "typeId") })
	public String checkSaleResourceByCode(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/couponCode");
			String typeId = WSUtil.getXmlNodeText(document, "//request/typeId");
			String pw = WSUtil.getXmlNodeText(document, "//request/password");
			if ("2".equals(typeId)) {
				if (StringUtils.isBlank(pw)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST, "类型为礼券时，密码不能为空");
				}
			} else {
				if (!"1".equals(typeId)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "类型只能为终端或者礼券");
				}
			}
			String code = srFacade.validateCodeCoupons(bcdCode, typeId, pw);
			if ("0".equals(code)) {
				String srcInfo = srFacade.getMaterialByCode("-1", bcdCode);
				Document srcInfoDoc = WSUtil.parseXml(srcInfo);
				String status = WSUtil.getXmlNodeText(srcInfoDoc, "//GoodsDetailList/GoodsDetail/status");
				if ("A".equals(status)) {
					String materialId = WSUtil.getXmlNodeText(srcInfoDoc, "//GoodsDetailList/GoodsDetail/materialId");
					String materialName = WSUtil.getXmlNodeText(srcInfoDoc,
							"//GoodsDetailList/GoodsDetail/materialName");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("resultCode", ResultCode.SUCCESS);
					map.put("resultMsg", ResultCode.SUCCESS.getDesc());
					map.put("materialId", materialId);
					map.put("materialName", materialName);
					return mapEngine.transform("checkSaleResourceByCode", map);
				} else {
					return WSUtil.buildResponse(ResultCode.RESOURSE_STATE_UNVALIBLE);
				}
			} else {
				return WSUtil.buildResponse(ResultCode.RESOURSE_INFO_ERROR);
			}
		} catch (Exception e) {
			logger.error("checkSaleResourceByCode:营销资源校验接口 根据物品串码，校验是否可以出库、兑换等:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	private String getUnitySeq() {
		return srFacade.getUnitySeq();
	}

	/**
	 * 有价卡对账时向统一支付补发
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/info/validity", caption = "串码"),
			@Node(xpath = "//request/platId", caption = "统一支付平台编码，网厅为06"),
			@Node(xpath = "//request/saleNo", caption = "销售流水号"),
			@Node(xpath = "//request/info/sailTime", caption = "销售时间"),
			@Node(xpath = "//request/info/price", caption = "实际价格"),
			@Node(xpath = "//request/info/priceStd", caption = "标价"),
			@Node(xpath = "//request/validateCode", caption = "打发票的验证码"),
			@Node(xpath = "//request/type", caption = "销售类型"),
			@Node(xpath = "//request/payInfo/method", caption = "付款方式"),
			@Node(xpath = "//request/payInfo/amount", caption = "付款金额"),
			@Node(xpath = "//request/payInfo/appendInfo", caption = "付款备注") })
	private String checkSequenceAfterOut(@WebParam(name = "request") String request) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/info/validity");
			paramMap.put("bcdCode", bcdCode);
			String platId = WSUtil.getXmlNodeText(document, "//request/platId");
			paramMap.put("platId", platId);
			paramMap.put("saleNo", WSUtil.getXmlNodeText(document, "//request/saleNo"));
			paramMap.put("saleDate", WSUtil.getXmlNodeText(document, "//request/info/sailTime"));
			paramMap.put("price", WSUtil.getXmlNodeText(document, "//request/info/price"));
			paramMap.put("inOutTypeId", "1");
			paramMap.put("validateCode", WSUtil.getXmlNodeText(document, "//request/validateCode"));
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
			Document documentStaffInfo = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(documentStaffInfo, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
			paramMap.put("staffId", staffId);
			// 1 为有价卡 2为礼品 现只支持 有价卡
			if ("1".equals(WSUtil.getXmlNodeText(document, "//request/type"))) {
				//				String out = srFacade.checkSequence(paramMap);
				//				Document doc = WSUtil.parseXml(out);
				//				String result = WSUtil.getXmlNodeText(doc, "//DoStoreInOutCrm/result");
				//				String payIndentId = WSUtil.getXmlNodeText(doc, "//DoStoreInOutCrm/Coupons/Coupon/inOutIds");
				//				payIndentId = platId + payIndentId;
				//				if (!"0".equals(result)) {
				//					return WSUtil.buildResponse(ResultCode.SR_SALE_CARD_FAILED, "调用营销资源失败");
				//				}
				String inoutId = WSUtil.getXmlNodeText(document, "//request/inoutId");
				String payIndentId = platId + inoutId;
				String unityResponse = unityPayService.callIndentItemPayAfterOut(request, payIndentId);
				Document unityDoc = WSUtil.parseXml(unityResponse);
				String unityResult = WSUtil.getXmlNodeText(unityDoc, "//response/resultCode");
				if (!"0".equals(unityResult)) {
					//					paramMap.put("inOutTypeId", "2");
					//					srFacade.checkSequence(paramMap);
					return WSUtil.buildResponse(ResultCode.UNITYPAY_FAILED, ResultCode.UNITYPAY_FAILED.getDesc());
				}
				String[] bcdCodes = bcdCode.split("\\|");
				for (int i = 0; i < bcdCodes.length; i++) {
					savePayIndentId(bcdCodes[i], payIndentId);
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
			} else {
				return WSUtil.buildResponse("1", "type值错误");
			}
		} catch (Exception e) {
			logger.error("checkSequence有价卡礼品受理确认接口 有价卡出库:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	public String rechargeCardCheck() {
		try {
			int errorItem = 0;
			while (true) {
				List<Map<String, Object>> cardList = intfSMO.getUnCheckedCardInfo();
				if ((cardList == null) || (cardList.size() == 0)) {
					break;
				}
				for (Map<String, Object> map : cardList) {
					String ifExist = intfSMO.ifPayIndentIdExists(map);
					Map<String, Object> updateMap = new HashMap<String, Object>();
					if (ifExist == null) {
						String xml = getCheckXml(map);
						String response = checkSequenceAfterOut(xml);
						Document document = WSUtil.parseXml(response);
						String resultCode = WSUtil.getXmlNodeText(document, "//response/resultCode");
						updateMap.put("bcdCode", map.get("BCD_CODE"));
						updateMap.put("resultCode", resultCode);
						updateMap.put("status", "1");
						if (!"0".equals(resultCode)) {
							errorItem++;
						}
						intfSMO.updaCradCheckResult(updateMap);
						intfSMO.updaCradCheckStatus(updateMap);
					} else {
						updateMap.put("bcdCode", map.get("BCD_CODE"));
						updateMap.put("status", "2");
						updateMap.put("resultCode", "原接口成功，不需要补发");
						intfSMO.updaCradCheckResult(updateMap);
						intfSMO.updaCradCheckStatus(updateMap);
					}
					Thread.sleep(500);
				}

			}
			if (errorItem == 0) {
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
			} else {
				return WSUtil.buildResponse(ResultCode.RECHARGE_CARD_CHECK_ERROR, "有 " + errorItem + " 条数据还需要再处理！");
			}
		} catch (Exception e) {
			logger.error("rechargeCardCheck:有价卡对账方法发生异常", e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	private String getCheckXml(Map<String, Object> map) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<request>");
		sBuffer.append("<custId>").append(map.get("CUST_ID").toString()).append("</custId>");
		sBuffer.append("<custName>").append(map.get("CUST_NAME").toString()).append("</custName>");
		sBuffer.append("<custType>0</custType>");
		sBuffer.append("<validateCode>").append(map.get("VALIDATE_CODE").toString()).append("</validateCode>");
		sBuffer.append("<platId>").append(map.get("PLAT_ID").toString()).append("</platId>");
		sBuffer.append("<saleNo>").append(map.get("SALE_NO").toString()).append("</saleNo>");
		sBuffer.append("<inoutId>").append(map.get("IN_OUT_ID").toString()).append("</inoutId>");
		sBuffer.append("<cycleId>").append(map.get("CYCLE_ID").toString()).append("</cycleId>");
		sBuffer.append("<info>");
		sBuffer.append("<validity>").append(map.get("BCD_CODE").toString()).append("</validity>");
		String priceStd = map.get("PRICE_STD").toString();
		sBuffer.append("<priceStd>").append((int) (Double.parseDouble(priceStd) * 100)).append("</priceStd>");
		String price = map.get("PRICE").toString();
		sBuffer.append("<price>").append((int) (Double.parseDouble(price) * 100)).append("</price>");
		sBuffer.append("</info>");
		sBuffer.append("<type>1</type>");
		sBuffer.append("<payInfo>");
		sBuffer.append("<method>").append(map.get("PAY_METHOD").toString()).append("</method>");
		sBuffer.append("<amount>").append((int) (Double.parseDouble(price) * 100)).append("</amount>");
		sBuffer.append("<appendInfo>").append(map.get("SALE_NO").toString()).append("</appendInfo>");
		sBuffer.append("</payInfo>");
		sBuffer.append("<channelId>").append(map.get("CHANNEL_ID").toString()).append("</channelId>");
		sBuffer.append("<staffCode>").append(map.get("STAFF_CODE").toString()).append("</staffCode>");
		sBuffer.append("</request>");
		return sBuffer.toString();
	}

	/**
	 * 自助终端打印发票，临时方法
	 * @param request
	 * @return
	 */
	@WebMethod
	public String queryInvoiceReprintTemp(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String cardNo = WSUtil.getXmlNodeText(document, "//request/cardNo");
			String validNum = WSUtil.getXmlNodeText(document, "//request/validNum");
			String payIndentId = intfSMO.getPayIndentIdByBcdCode(cardNo);
			double salePrice = 0;
			String result = "2";
			String msg = "其他错误";
			String chargeString = null;
			if ((cardNo != null) && (validNum != null)) {
				if (payIndentId != null) {
					chargeString = intfSMO.getChargeByPayId(payIndentId);
					if (chargeString != null) {
						String resultSR = storeForOnLineMall.identifyValidateCode(cardNo, validNum);
						Document doc = WSUtil.parseXml(resultSR);
						String rspCode = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspCode");
						if ("0".equals(rspCode)) {
							result = "0";
							msg = "发票可以补打";
							salePrice = Double.parseDouble(chargeString);
						} else {
							msg = WSUtil.getXmlNodeText(document, "//ContractRoot/TcpCont/Response/RspDesc");
						}
					}
				} else {
					result = "1";
					msg = "未找到销售记录";
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<queryInvoiceReprintResp>");
			sb.append("<result>").append(result).append("</result>");
			sb.append("<resultMsg>").append(msg).append("</resultMsg>");
			sb.append("<cardSaleInfo>");
			sb.append("<cardNo>").append(cardNo).append("</cardNo>");
			sb.append("<cardMianzhi>").append("0.000").append("</cardMianzhi>");
			DecimalFormat df = new DecimalFormat("#0.000");
			sb.append("<salePrice>").append(df.format(salePrice / 100)).append("</salePrice>");
			sb.append("</cardSaleInfo></queryInvoiceReprintResp>");
			return sb.toString();
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append("<queryInvoiceReprintResp>");
			sb.append("<result>").append(2).append("</result>");
			sb.append("<resultMsg>").append("其他错误,方法发生异常").append("</resultMsg>");
			sb.append("</queryInvoiceReprintResp>");
			return sb.toString();
		}
	}

	/**
	 * 积分明细查询
	 * @param request
	 * @return
	 */

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户id") })
	public String queryUserScoreAll(@WebParam(name = "request") String request) {
		Document doc = null;
		try {
			doc = WSUtil.parseXml(request);
		} catch (DocumentException e1) {
			return WSUtil.buildResponse("1", "xml解析异常");
		}
		String partyId = WSUtil.getXmlNodeText(doc, "/request/partyId");
		Map<String, Object> mapIn = new HashMap<String, Object>();
		mapIn.put("partyId", partyId);
		Map<String, Object> mapOut = new HashMap<String, Object>();
		mapOut = soPointSMO.queryPartyPointPay(mapIn);
		System.out.println("mapOut===" + mapOut.toString());
		mapOut.put("resultCode", "0");
		mapOut.put("resultMsg", "成功");
		List<PartyPointDto> listPartyPD = (List<PartyPointDto>) mapOut.get("partyPointConsume");

		List<PartyPointDto> listConsumeCenter = new ArrayList<PartyPointDto>();
		List<PartyPointDto> listConsumeLocal = new ArrayList<PartyPointDto>();
		List<PartyPointDto> listConsumeOs = new ArrayList<PartyPointDto>();
		for (PartyPointDto partyPointDot : listPartyPD) {
			System.out.println("typeStr==" + partyPointDot.getConsumeTypeString());
			if ("集团兑换".equals(partyPointDot.getConsumeTypeString())) {
				listConsumeCenter.add(partyPointDot);
			} else if ("实物礼品".equals(partyPointDot.getConsumeTypeString())) {
				listConsumeLocal.add(partyPointDot);
			} else if ("附属销售品".equals(partyPointDot.getConsumeTypeString())) {
				listConsumeOs.add(partyPointDot);
				System.out.println("listConsumeOs size==" + listConsumeOs.size());
			}
			mapOut.put("ConsumeCenter", listConsumeCenter);
			mapOut.put("ConsumeLocal", listConsumeLocal);
			mapOut.put("ConsumeOs", listConsumeOs);
		}
		return mapEngine.transform("queryUserScoreAll", mapOut);
	}
	
	/**
	 * 根据批次号调用营销资源接口，返回此批次号是否存在。
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/batchId", caption = "batchId")})
	public String queryBatchId(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String batchId = WSUtil.getXmlNodeText(document, "//request/batchId");

			String result = srFacade.queryBatchId(batchId);
			return result;
		} catch (Exception e) {
			logger.error("queryBatchId:根据批次号调用营销资源接口，返回此批次号是否存在出现异常:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * 根据充值卡号到营销资源校验是否可售和激活状态。
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/cardNo", caption = "cardNo")})
	public String checkValueCard(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String cardNo = WSUtil.getXmlNodeText(document, "//request/cardNo");

			String result = srFacade.checkValueCard(cardNo);
			return result;
		} catch (Exception e) {
			logger.error("checkSaleResourceByCode:营销资源校验接口 根据物品串码，校验是否可以出库、兑换等:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * 根据营销资源规格（配置）id和物品实例化编码查询物品实例信息。
	 */
	@WebMethod
	@Required(nodes = { 
			@Node(xpath = "//request/mateialId", caption = "mateialId"),
			@Node(xpath = "//request/bcdCode", caption = "bcdCode")})
	public String getMaterialByCode(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String mateialId = WSUtil.getXmlNodeText(document, "//request/mateialId");
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcdCode");

			String result = srFacade.getMaterialByCode(mateialId , bcdCode);
			return result;
		} catch (Exception e) {
			logger.error("checkSaleResourceByCode:根据营销资源规格（配置）id和物品实例化编码查询物品实例信息:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

}