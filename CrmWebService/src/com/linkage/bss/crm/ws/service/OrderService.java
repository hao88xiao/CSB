package com.linkage.bss.crm.ws.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bss.common.util.LoginedStaffInfo;

import com.ai.skynet.ProducerClient;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEbppInvoiceTitleDynamicGetRequest;
import com.alipay.api.response.AlipayEbppInvoiceTitleDynamicGetResponse;
import com.linkage.bss.commons.spring.SpringBeanInvoker;
import com.linkage.bss.commons.util.BeanUtil;
import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.commons.util.StringUtil;
import com.linkage.bss.crm.acct.smo.IAcctSMO;
import com.linkage.bss.crm.charge.smo.IChargeManagerSMO;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.commons.client.ICommonClient;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.cust.dto.CustClubMemberDto;
import com.linkage.bss.crm.cust.smo.ICustBasicSMO;
import com.linkage.bss.crm.dto.OfferDto;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.facade.OfferFacade;
import com.linkage.bss.crm.intf.facade.OfferSpecFacade;
import com.linkage.bss.crm.intf.facade.SRFacade;
import com.linkage.bss.crm.intf.facade.SoFacade;
import com.linkage.bss.crm.intf.facade.SysFacade;
import com.linkage.bss.crm.intf.model.AreaInfo;
import com.linkage.bss.crm.intf.model.BankTableEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntityInputBean;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntity;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntityInputBean;
import com.linkage.bss.crm.intf.model.OfferInfoKF;
import com.linkage.bss.crm.intf.model.OperatingOfficeInfo;
import com.linkage.bss.crm.intf.model.ProdByCompProdSpec;
import com.linkage.bss.crm.intf.model.ProdServRela;
import com.linkage.bss.crm.intf.model.Tel2Area;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.local.bmo.IEntityServiceSMO;
import com.linkage.bss.crm.local.bmo.IScoreServiceSMO;
import com.linkage.bss.crm.local.bmo.ISpServiceSMO;
import com.linkage.bss.crm.model.BamAcctItemInfo;
import com.linkage.bss.crm.model.Bo2Staff;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;
import com.linkage.bss.crm.offer.dto.OfferParamDto;
import com.linkage.bss.crm.offer.dto.OfferProdStatusDto;
import com.linkage.bss.crm.offer.dto.OfferRolesDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;
import com.linkage.bss.crm.offerspec.dto.CategoryNodeDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecShopDto;
import com.linkage.bss.crm.offerspec.smo.IOfferSpecSMO;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.batch.smo.ISoBatchSMO;
import com.linkage.bss.crm.so.commit.smo.ISoCommitSMO;
import com.linkage.bss.crm.so.common.SoDomain;
import com.linkage.bss.crm.so.detail.dto.CustOrderDto;
import com.linkage.bss.crm.so.detail.dto.OfferOrderDto;
import com.linkage.bss.crm.so.detail.dto.ProdOrderDto;
import com.linkage.bss.crm.so.detail.smo.ISoDetailSMO;
import com.linkage.bss.crm.so.preOrder.smo.ISoPreOrderSMO;
import com.linkage.bss.crm.so.prepare.smo.ISoPrepareSMO;
import com.linkage.bss.crm.so.print.smo.IVoucherPrintSMO;
import com.linkage.bss.crm.so.query.smo.ISoQuerySMO;
import com.linkage.bss.crm.so.save.smo.ISoSaveSMO;
import com.linkage.bss.crm.so.store.smo.ISoStoreSMO;
import com.linkage.bss.crm.soservice.crmservice.GjmyService;
import com.linkage.bss.crm.soservice.crmservice.ICustManagerService;
import com.linkage.bss.crm.soservice.crmservice.IOfferSpecManagerService;
import com.linkage.bss.crm.soservice.crmservice.ISoService;
import com.linkage.bss.crm.soservice.csbservice.ICSBService;
import com.linkage.bss.crm.soservice.syncso.smo.ISoServiceSMO;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.order.AcctOrderListFactory;
import com.linkage.bss.crm.ws.order.BusinessServiceOrderListFactory;
import com.linkage.bss.crm.ws.order.CreateExchangeForProvince;
import com.linkage.bss.crm.ws.order.CreateTransferOwnerListFactory;
import com.linkage.bss.crm.ws.order.DesensitizationFlowFactory;
import com.linkage.bss.crm.ws.order.ISoServiceSavePrepare;
import com.linkage.bss.crm.ws.order.ModifyCustomOrderListFactory;
import com.linkage.bss.crm.ws.order.OrderListFactory;
import com.linkage.bss.crm.ws.order.SoServiceValidateCustOrder;
import com.linkage.bss.crm.ws.order.model.CreateCRAccountREP;
import com.linkage.bss.crm.ws.order.model.CreateCRAccountREQ;
import com.linkage.bss.crm.ws.order.model.DelCRAccountREP;
import com.linkage.bss.crm.ws.order.model.DelCRAccountREQ;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.others.query.QueryProductFactory;
import com.linkage.bss.crm.ws.others.query.QueryProductResultUtil;
import com.linkage.bss.crm.ws.util.BusPayRSAUtil;
import com.linkage.bss.crm.ws.util.CumRSA;
import com.linkage.bss.crm.ws.util.DateOfJudgment;
import com.linkage.bss.crm.ws.util.GetRebateAddress;
import com.linkage.bss.crm.ws.util.HttpUtils;
import com.linkage.bss.crm.ws.util.KeepUtils;
import com.linkage.bss.crm.ws.util.ThreeDes;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * 
 * 订单管理
 * import com.ai.ProducerClient;
 */
@WebService
public class OrderService extends AbstractService {

	private static Log logger = Log.getLog(OrderService.class);

	private ICommonSMO commonSMO;

	private ISoQuerySMO soQuerySMO;

	private CustFacade custFacade;

	private IOfferSMO offerSMO;

	private ISoPrepareSMO soPrepareSMO;

	private ISoPreOrderSMO soPreOrderSMO;

	private OfferFacade offerFacade;

	private ISoDetailSMO soDetailSMO;

	private IChargeManagerSMO chargeManagerSMO;

	private IntfSMO intfSMO;

	private SoFacade soFacade;

	private OrderListFactory orderListFactory;

	private ISoServiceSMO soServiceSMO;
	
	private ICSBService csbService;
	
	private OfferSpecFacade offerSpecFacade;

	private BusinessServiceOrderListFactory businessServiceOrderListFactory;
	
	private CreateExchangeForProvince createExchangeForProvince;
	
	private CreateTransferOwnerListFactory createTransferOwnerListFactory;

	private AcctOrderListFactory acctOrderListFactory;

	private RscServiceSMO rscServiceSMO;

	private ISoServiceSavePrepare soSavePrepare;

	private SoServiceValidateCustOrder soValidateCustOrder;

	private IScoreServiceSMO scoreServiceSMO;

	private IOfferSpecSMO offerSpecSMO;

	private SRFacade srFacade;

	private ISoBatchSMO soBatchSMO;

	private ICommonClient commonClient;

	private IAcctSMO acctSMO;

	private ISoStoreSMO soStoreSMO;

	private ICustBasicSMO custBasicSMO;

	private ISpServiceSMO spServiceSMO;
	
	private IEntityServiceSMO entityService;

	private ISoSaveSMO soSaveSMO;

	private ISoCommitSMO soCommitSMO;

	private ModifyCustomOrderListFactory modifyCustomOrderListFactory;

	private static boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	private IVoucherPrintSMO voucherPrintSMO;

	private ISoService soServiceImpl;

	private IOfferSpecManagerService iofferSpecManagerService;
	
	private GjmyService gjmyService;
	
	private ICustManagerService custManagerSmo;
	private ProducerClient producerClient;
	
	private DesensitizationFlowFactory desensitizationFlowFactory;
	
	@WebMethod(exclude = true)
	public void setDesensitizationFlowFactory(
			DesensitizationFlowFactory desensitizationFlowFactory) {
		this.desensitizationFlowFactory = desensitizationFlowFactory;
	}
	@WebMethod(exclude = true)
	public ProducerClient getProducerClient() {
		return producerClient;
	}
	@WebMethod(exclude = true)
	public void setProducerClient(ProducerClient producerClient) {
		this.producerClient = producerClient;
	}
	@WebMethod(exclude = true)
	public ICustManagerService getCustManagerSmo() {
		return custManagerSmo;
	}
	@WebMethod(exclude = true)
	public void setCustManagerSmo(ICustManagerService custManagerSmo) {
		this.custManagerSmo = custManagerSmo;
	}

	@WebMethod(exclude = true)
	public void setGjmyService(GjmyService gjmyService) {
		this.gjmyService = gjmyService;
	}

	@WebMethod(exclude = true)
	public void setIofferSpecManagerService(IOfferSpecManagerService iofferSpecManagerService) {
		this.iofferSpecManagerService = iofferSpecManagerService;
	}

	@WebMethod(exclude = true)
	public void setVoucherPrintSMO(IVoucherPrintSMO voucherPrintSMO) {
		this.voucherPrintSMO = voucherPrintSMO;
	}

	@Autowired
	@Qualifier("intfManager.sysFacade")
	private SysFacade sysFacade;

	@WebMethod(exclude = true)
	public void setSoCommitSMO(ISoCommitSMO soCommitSMO) {
		this.soCommitSMO = soCommitSMO;
	}

	@WebMethod(exclude = true)
	public void setSoServiceImpl(ISoService soServiceImpl) {
		this.soServiceImpl = soServiceImpl;
	}

	@WebMethod(exclude = true)
	public void setModifyCustomOrderListFactory(ModifyCustomOrderListFactory modifyCustomOrderListFactory) {
		this.modifyCustomOrderListFactory = modifyCustomOrderListFactory;
	}

	@WebMethod(exclude = true)
	public void setSoSaveSMO(ISoSaveSMO soSaveSMO) {
		this.soSaveSMO = soSaveSMO;
	}

	@WebMethod(exclude = true)
	public void setCustBasicSMO(ICustBasicSMO custBasicSMO) {
		this.custBasicSMO = custBasicSMO;
	}

	@WebMethod(exclude = true)
	public void setCommonClient(ICommonClient commonClient) {
		this.commonClient = commonClient;
	}

	@WebMethod(exclude = true)
	public void setSoBatchSMO(ISoBatchSMO soBatchSMO) {
		this.soBatchSMO = soBatchSMO;
	}

	@WebMethod(exclude = true)
	public void setSrFacade(SRFacade srFacade) {
		this.srFacade = srFacade;
	}

	@WebMethod(exclude = true)
	public void setScoreServiceSMO(IScoreServiceSMO scoreServiceSMO) {
		this.scoreServiceSMO = scoreServiceSMO;
	}

	@WebMethod(exclude = true)
	public void setSoValidateCustOrder(SoServiceValidateCustOrder soValidateCustOrder) {
		this.soValidateCustOrder = soValidateCustOrder;
	}

	@WebMethod(exclude = true)
	public void setBusinessServiceOrderListFactory(BusinessServiceOrderListFactory businessServiceOrderListFactory) {
		this.businessServiceOrderListFactory = businessServiceOrderListFactory;
	}

	@WebMethod(exclude = true)
	public void setCreateTransferOwnerListFactory(CreateTransferOwnerListFactory createTransferOwnerListFactory) {
		this.createTransferOwnerListFactory = createTransferOwnerListFactory;
	}

	@WebMethod(exclude = true)
	public void setSoSavePrepare(ISoServiceSavePrepare soSavePrepare) {
		this.soSavePrepare = soSavePrepare;
	}

	@WebMethod(exclude = true)
	public void setSoDetailSMO(ISoDetailSMO soDetailSMO) {
		this.soDetailSMO = soDetailSMO;
	}

	@WebMethod(exclude = true)
	public void setChargeManagerSMO(IChargeManagerSMO chargeManagerSMO) {
		this.chargeManagerSMO = chargeManagerSMO;
	}

	@WebMethod(exclude = true)
	public void setSoPrepareSMO(ISoPrepareSMO soPrepareSMO) {
		this.soPrepareSMO = soPrepareSMO;
	}

	@WebMethod(exclude = true)
	public void setSoPreOrderSMO(ISoPreOrderSMO soPreOrderSMO) {
		this.soPreOrderSMO = soPreOrderSMO;
	}

	@WebMethod(exclude = true)
	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	@WebMethod(exclude = true)
	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	@WebMethod(exclude = true)
	public void setSoQuerySMO(ISoQuerySMO soQuerySMO) {
		this.soQuerySMO = soQuerySMO;
	}

	@WebMethod(exclude = true)
	public void setCommonSMO(ICommonSMO commonSMO) {
		this.commonSMO = commonSMO;
	}

	@WebMethod(exclude = true)
	public void setOfferFacade(OfferFacade offerFacade) {
		this.offerFacade = offerFacade;
	}

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setSoFacade(SoFacade soFacade) {
		this.soFacade = soFacade;
	}

	@WebMethod(exclude = true)
	public void setOrderListFactory(OrderListFactory orderListFactory) {
		this.orderListFactory = orderListFactory;
	}

	@WebMethod(exclude = true)
	public void setSoServiceSMO(ISoServiceSMO soServiceSMO) {
		this.soServiceSMO = soServiceSMO;
	}
	
	
	@WebMethod(exclude = true)
	public void setCsbService(ICSBService csbService) {
		this.csbService = csbService;
	}
	
	@WebMethod(exclude = true)
	public void setOfferSpecFacade(OfferSpecFacade offerSpecFacade) {
		this.offerSpecFacade = offerSpecFacade;
	}

	@WebMethod(exclude = true)
	public void setAcctOrderListFactory(AcctOrderListFactory acctOrderListFactory) {
		this.acctOrderListFactory = acctOrderListFactory;
	}

	@WebMethod(exclude = true)
	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	@WebMethod(exclude = true)
	public void setOfferSpecSMO(IOfferSpecSMO offerSpecSMO) {
		this.offerSpecSMO = offerSpecSMO;
	}

	@WebMethod(exclude = true)
	public void setAcctSMO(IAcctSMO acctSMO) {
		this.acctSMO = acctSMO;
	}

	@WebMethod(exclude = true)
	public void setSoStoreSMO(ISoStoreSMO soStoreSMO) {
		this.soStoreSMO = soStoreSMO;
	}

	@WebMethod(exclude = true)
	public void setSpServiceSMO(ISpServiceSMO spServiceSMO) {
		this.spServiceSMO = spServiceSMO;
	}
	
	@WebMethod(exclude = true)
	public void setEntityServiceSMO(IEntityServiceSMO entityService) {
		this.entityService = entityService;
	}

	@WebMethod(exclude = true)
	public void setCreateExchangeForProvince(CreateExchangeForProvince createExchangeForProvince) {
		this.createExchangeForProvince = createExchangeForProvince;
	}
	/**
	 * 调用crm.generateSeq生成sequence
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/count", caption = "个数") })
	public String generateCoNbr(@WebParam(name = "request") String request) {
		try {
			List<Map<String, Object>> coNbrList = new ArrayList<Map<String, Object>>();
			Map<String, Object> root = new HashMap<String, Object>();
			Document document = WSUtil.parseXml(request);
			String count = WSUtil.getXmlNodeText(document, "//request/count");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			if (Integer.valueOf(count) > 0) {
				for (int i = 0; i < Integer.valueOf(count); i++) {
					String coNbr = commonSMO.generateSeq(Integer.valueOf(areaId), "ORDER_LIST", "2");
					Map<String, Object> nbrMap = new HashMap<String, Object>();
					nbrMap.put("coNbr", coNbr);
					coNbrList.add(nbrMap);
				}
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("coNbrList", coNbrList);
			} else {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR);
			}
			return mapEngine.transform("generateCoNbr", root);
		} catch (Exception e) {
			logger.error("generateCoNbr调用crm.generateSeq生成sequence:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 受理订单查询
	 * 
	 * @author TERFY
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/curPage", caption = "页数"),
			@Node(xpath = "//request/pageSize", caption = "页面信息个数") })
	public String queryOrderListInfo(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String curPage = WSUtil.getXmlNodeText(document, "//request/curPage");
			String pageSize = WSUtil.getXmlNodeText(document, "//request/pageSize");
			String olNbr = WSUtil.getXmlNodeText(document, "//request/coNbr");
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String startDate = WSUtil.getXmlNodeText(document, "//request/startDate");
			String endDate = WSUtil.getXmlNodeText(document, "//request/endDate");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String transfer = WSUtil.getXmlNodeText(document, "//request/transfer");
			String statusCd = WSUtil.getXmlNodeText(document, "//request/statusCd");
			String sysFlag = WSUtil.getXmlNodeText(document, "//request/sysFlag");
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("olNbr", olNbr);
			params.put("accessNumber", accNbr);
			params.put("startDt", startDate);
			params.put("endDt", endDate);
			params.put("statusCd", statusCd);
			params.put("sysFlag", sysFlag);
			//add by helinglong 20130702
			if (channelId != null && !"".equals(channelId))
				params.put("channelId", Integer.valueOf(channelId));
			else
				params.put("channelId", channelId);
			params.put("startNum", (Integer.valueOf(curPage) - 1) * Integer.valueOf(pageSize) + 1);
			params.put("endNum", Integer.valueOf(curPage) * Integer.valueOf(pageSize));
			params.put("transfer", transfer);
			params.put("staffCode", staffCode);
			//add by helinglong 20130702
			if (areaId != null && !"".equals(areaId))
				params.put("areaId", Integer.valueOf(areaId));
			else
				params.put("areaId", areaId);
			long start = System.currentTimeMillis();
			List<Map<String, Object>> simpleInfo = intfSMO.qryOrderSimpleInfoByOlId(params);
			int sum= 0;
			//添加其他信息，卢厚祥需求
			for(Map<String,Object> m : simpleInfo){
				String ol_id = m.get("OL_ID")==null?"":m.get("OL_ID")+"";
//				String anum = m.get("ACCESS_NUMBER")==null?"":m.get("ACCESS_NUMBER")+"";
				m.put("ACCESS_NUMBER", accNbr);
				Map<String,Object> mv = new HashMap<String, Object>();
				mv.put("ol_id", ol_id);
				mv.put("anum", accNbr);
				List<Map<String,Object>> listmv = intfSMO.qryBoInfos(mv);
				simpleInfo.get(sum).put("boInfos", listmv);
//				simpleInfo[i].put("boInfos", listmv);
				
				Long lc = intfSMO.qryChargesByOlid(ol_id);
				simpleInfo.get(sum).put("Charge", lc);
//				m.put("Charge", lc);
				sum++;
			}
			
			if (isPrintLog) {
				System.out.println("queryOrderListInfo.intfSMO.qryOrderSimpleInfoByOlId(根据订单ID查询订单状态、类型和名称) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			int noCnum = 0;
			for (int i = 0; simpleInfo.size() > i; i++) {
				if (simpleInfo.get(i).get("STATUS_CD") != null) {
					String STATUSCD = simpleInfo.get(i).get("STATUS_CD").toString();
					if (!STATUSCD.equals("C")) {
						noCnum = noCnum + 1;
					}
				}
			}
			params.put("resultCode", ResultCode.SUCCESS);
			params.put("resultMsg", ResultCode.SUCCESS.getDesc());
			params.put("paramList", simpleInfo);
			params.put("noCnum", String.valueOf(noCnum));
			params.put("zongNum", simpleInfo.size());
			if (isPrintLog) {
				System.out.println("调用营业接口queryOrderListInfo(受理订单查询)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return mapEngine.transform("orderListInfo", params);
		} catch (Exception e) {
			logger.error("queryOrderListInfo受理订单查询:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询预受理订单信息
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@SuppressWarnings("unchecked")
	@Required
	public String getPreInterimBycond(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String identifyType = WSUtil.getXmlNodeText(document, "//request/identifyType");
			String identityNum = WSUtil.getXmlNodeText(document, "//request/identityNum");
			String startTime = WSUtil.getXmlNodeText(document, "//request/startTime");
			String endTime = WSUtil.getXmlNodeText(document, "//request/endTime");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String accessNumber = WSUtil.getXmlNodeText(document, "//request/accessNumber");
			String olNbr = WSUtil.getXmlNodeText(document, "//request/olNbr");
			String olTypeCd = WSUtil.getXmlNodeText(document, "//request/olTypeCd");
			String partyName = WSUtil.getXmlNodeText(document, "//request/partyName");
			Party party = new Party();
			if (StringUtils.isBlank(accessNumber) && StringUtils.isBlank(identityNum) && StringUtils.isBlank(partyName)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "主接入号或者证件号码和证件类型或者客户名称至少填写一样！");
			} else if (StringUtils.isNotBlank(accessNumber)) {
				party = custFacade.getPartyByAccessNumber(accessNumber);
			} else {
				if (StringUtils.isBlank(identifyType)) {
					party = custFacade.getPartyByName(partyName);
				} else if (identifyType.equals(WSDomain.IdentifyType.ID_CARD)) {
					party = custFacade.getPartyByIDCard(identityNum);
				} else if (WSDomain.IDENTIFY_TYPE_SET.contains(identifyType)) {
					party = custFacade.getPartyByOtherCard(identityNum);
				} else {
					return WSUtil.buildResponse(ResultCode.REQUEST_IDENTIFY_TYPE_IS_NOT_EXIST);
				}
			}
			if (party == null) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partyId", party.getPartyId());
			map.put("staffId", staffId);
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				map.put("startDt", startTime);
				map.put("endDt", endTime);
			}
			if (StringUtils.isNotBlank(olNbr)) {
				map.put("olNbr", olNbr);
			}
			if (StringUtils.isNotBlank(olTypeCd)) {
				map.put("olType", Arrays.asList(StringUtils.split(olTypeCd, ',')));
			}
			map.put("channelId", channelId);
			map.put("itemSpecId", SoDomain.DEALPREORDER_END_DT);
			map.put("status", "PW");
			List<Map> result = soQuerySMO.qryPrepareOrder(map);
			Map<String, Object> root = new HashMap<String, Object>();
			if (CollectionUtils.isEmpty(result)) {
				return WSUtil.buildResponse(ResultCode.PREPARE_ORDER_RESULT_IS_NULL);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				param.put("olId", result.get(i).get("OL_ID"));
				Map<String, Object> offerSpec = intfSMO.queryMainOfferSpecNameAndIdByOlId(param);
				result.get(i).put("offerSpec", offerSpec);
			}
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("result", result);
			return mapEngine.transform("prepareOrederResponse", root);
		} catch (Exception e) {
			logger.error("getPreInterimBycond查询预受理订单信息:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 预受理单撤单
	 * 
	 * @author TERFY
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单号") })
	public String releaseCartByOlIdForPrepare(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(document, "//request/olId");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("olId", olId);
			map.put("areaId", areaId);
			map.put("staffId", staffId);
			map.put("channelId", channelId);
			String result = soPrepareSMO.releaseCartByOlIdForPrepare(map);
			if ("1".equals(result)) {
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} else {
				return WSUtil.buildResponse(ResultCode.PREPARE_ORDER_TO_RELEASE);
			}
		} catch (Exception e) {
			logger.error("releaseCartByOlIdForPrepare预受理单撤单:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 预受理单转正
	 * 
	 * @author TERFY
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单号"),
			@Node(xpath = "//request/preOrderType", caption = "受理类型") })
	public String commitPreOrderInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(document, "//request/olId");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String preOrderType = WSUtil.getXmlNodeText(document, "//request/preOrderType");
			JSONObject params = new JSONObject();
			params.put("olId", olId);
			params.put("olTypeCd", "5");
			params.put("areaId", areaId);
			params.put("staffId", staffId);
			params.put("channelId", channelId);
			params.put("orderStatus", "PW");
			params.put("preOrderType", preOrderType);
			JSONObject returnObj = soPreOrderSMO.commitPreOrderInfo(params);
			if (returnObj == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			logger.debug("预受理转正结果：{}", returnObj.toString());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (returnObj.getString("resultCode").equals("1")) {
				resultMap.put("resultCode", ResultCode.SUCCESS);
				resultMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
				resultMap.put("olId", returnObj.getString("olId"));
				resultMap.put("olNbr", returnObj.getString("olNbr"));
			} else {
				return WSUtil.buildResponse(ResultCode.PREPARE_ORDER_TO_COMMIT_FLASE, returnObj.getString("resultMsg"));
			}
			return mapEngine.transform("commitPreOrderInfo", resultMap);
		} catch (Exception e) {
			logger.error("commitPreOrderInfo预受理单转正:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 预受理订单详情
	 * 
	 * @author TERFY
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单号") })
	public String queryPreOrderDetail(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			Long olId = Long.valueOf(WSUtil.getXmlNodeText(document, "//request/olId"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("olId", olId);
			paramMap.put("status", "PW");
			List orderInfoResult = soQuerySMO.queryOrderDetail(paramMap);
			if (orderInfoResult.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "订单信息为空！");
			}
			logger.debug("订单查询结果：{}", JsonUtil.getJsonString(orderInfoResult));
			List<Map<String, Object>> boList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < orderInfoResult.size(); i++) {
				String user = WSDomain.USER_NAME;
				Long boId = Long.valueOf(((Map) orderInfoResult.get(i)).get("boId").toString());
				Map<String, String> param = new HashMap<String, String>();
				param.put("boId", String.valueOf(boId));
				param.put("user", user);
				// 业务动作信息详情
				String busiOrderInfo = soDetailSMO.queryBusiOrderInfoByBoId(boId, user);
				JSONObject details = new JSONObject();
				Map<String, Object> busiOrderMap = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(busiOrderInfo)) {
					details.elementOpt("busiOrderInfo", busiOrderInfo);
					busiOrderMap = JsonUtil.getMap(details.toString());
				}
				// 根据BOID或OLID获取费用信息
				List<BamAcctItemInfo> list = chargeManagerSMO.getBamAcctInfos(param);
				// 物品信息
				String couponInfo = soDetailSMO.queryCouponInfoByBoId(boId, user);
				JSONObject detail1 = new JSONObject();
				Map<String, Object> couponMap = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(couponInfo)) {
					detail1.elementOpt("couponInfo", couponInfo);
					couponMap = JsonUtil.getMap(detail1.toString());
				}
				// 业务动作发展人信息详情
				List<Bo2Staff> resultBo2Staff = soQuerySMO.queryBo2StaffById(boId, user);
				// 管理属性详情
				String busiOrderAttrInfo = soDetailSMO.queryBusiOrderAttrById(boId, null, user);
				JSONObject detail = new JSONObject();
				Map<String, Object> busiOrderAttrMap = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(busiOrderAttrInfo)) {
					detail.elementOpt("busiOrderAttrInfo", busiOrderAttrInfo);
					busiOrderAttrMap = JsonUtil.getMap(detail.toString());
				}
				// 客户信息
				CustOrderDto resultCustInfo = soDetailSMO.queryCustOrderByBoId(boId, user);
				logger.debug("客户信息：{}", JsonUtil.getJsonString(JSONObject.fromObject(resultCustInfo)));
				// 产品信息
				ProdOrderDto resultProdOrderInfo = soDetailSMO
						.queryProdOrderByBoId(boId, CommonDomain.QRY_BO_ALL, user);
				// 销售品信息
				OfferOrderDto resultOfferOrderInfo = soDetailSMO.queryOfferOrderByBoId(boId, CommonDomain.QRY_BO_ALL,
						user);
				logger.debug("销售品信息：{}", JSONObject.fromObject(resultOfferOrderInfo));
				// 附属销售品信息
				OfferOrderDto resultAttachOfferOrderInfo = soDetailSMO.queryAttachOfferOrderByBoId(boId,
						CommonDomain.QRY_BO_ALL, user);
				logger.debug("附属销售品信息：{}", JSONObject.fromObject(resultAttachOfferOrderInfo));
				Map<String, Object> boInfoMap = new HashMap<String, Object>();
				boInfoMap.put("busiOrderInfo", busiOrderMap);
				boInfoMap.put("acctListInfo", list);
				boInfoMap.put("couponInfo", couponMap);
				boInfoMap.put("resultBo2Staff", resultBo2Staff);
				boInfoMap.put("resultAttachOfferOrderInfo", resultAttachOfferOrderInfo);
				boInfoMap.put("resultOfferOrderInfo", resultOfferOrderInfo);
				boInfoMap.put("resultProdOrderInfo", resultProdOrderInfo);//
				boInfoMap.put("resultCustInfo", resultCustInfo);
				boInfoMap.put("busiOrderAttrInfo", busiOrderAttrMap);
				boList.add(boInfoMap);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("resultCode", ResultCode.SUCCESS);
			resultMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			resultMap.put("olId", olId);
			resultMap.put("olNbr", intfSMO.getOlNbrByOlId(olId));
			resultMap.put("boInfos", orderInfoResult);
			resultMap.put("boDetailInfos", boList);
			return mapEngine.transform("queryPreOrderDetail", resultMap);
		} catch (Exception e) {
			logger.error("queryPreOrderDetail预受理订单详情:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询信息 查询模式(queryMode)分为三种:1当前产品,2客户下的所有产品,3合同号对应的所有产品
	 * 
	 * @param request
	 * @return
	 * @author Helen 维护
	 */

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/accNbrType", caption = "接入号码类型"),
			@Node(xpath = "//request/queryMode", caption = "查询模式") })
	public String queryService(@WebParam(name = "request") String request) {
		try {
			long mstart = System.currentTimeMillis();

			Document doc = WSUtil.parseXml(request);
			//根据accNbr来确定传入的号码，接入号码
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			//输入的产品号码类型：1、接入号码，2、客户ID，3、合同号，13客户标识码。（3、合同暂不考虑）
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");
			//4种查询类型：1、查询客户信息，2、查询产品信息，3、查询账务信息，4、查询销售品信息。可以多查。1,2,3,4 
			String queryType = WSUtil.getXmlNodeText(doc, "//request/queryType");
			//3种类型：1、查询本产品，2、查询拥有该号码的客户的所有信息。3、根据合同号查询时返回所有信息。
			String queryMode = WSUtil.getXmlNodeText(doc, "//request/queryMode");
			//区域标示
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");

			//奔驰休斯新增一个平台编码6090010062
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			if ("6090010062".equals(systemId)) {
				//判断该用户是否订购了“车载用户标识”服务
				if (!intfSMO.isBenzOfferServUser(accNbr)) {
					return WSUtil.buildResponse("1", "用户：" + accNbr + " 没订购奔驰车载用户标识！");
				}
			}

			Long prodId = null;
			Party party = null;
			List<Object> resultProdInfoMapList = new ArrayList<Object>();
			List<CommonOfferProdDto> prodDtoList = new ArrayList<CommonOfferProdDto>();
			// 查找客户信息   Accnbr_Type_set = 1 根据接入号码查找
			long start = 0l;
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				start = System.currentTimeMillis();
				// 根据接入号查找产品信息
				OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
				if (isPrintLog) {
					System.out.println("queryService.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间："
							+ (System.currentTimeMillis() - start));
				}

				if (prod != null) {
					prodId = prod.getProdId();
					start = System.currentTimeMillis();
					// 通过partyId查客户信息
					party = custFacade.getPartyById(prod.getPartyId());
					if (isPrintLog) {
						System.out.println("queryService.custFacade.getPartyById(根据ID取得产品信息) 执行时间："
								+ (System.currentTimeMillis() - start));
					}

				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
				}
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				start = System.currentTimeMillis();
				// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
				if (isPrintLog) {
					System.out.println("queryService.custFacade.getPartyByOtherCard(根据其他证件号查询客户信息) 执行时间："
							+ (System.currentTimeMillis() - start));
				}

			} else if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				start = System.currentTimeMillis();
				// 合同号
				party = custFacade.getPartyByAcctCd(accNbr);
				if (isPrintLog) {
					System.out.println("queryService.custFacade.getPartyByAcctCd(根据账户合同号查询客户信息) 执行时间："
							+ (System.currentTimeMillis() - start));
				}

			} else if (WSDomain.AccNbrType.PARTY_ID.equals(accNbrType)) {
				start = System.currentTimeMillis();
				// 客户Id
				party = custFacade.getPartyById(accNbr);
				if (isPrintLog) {
					System.out.println("queryService.custFacade.getPartyById(根据客户ID查询客户信息) 执行时间："
							+ (System.currentTimeMillis() - start));
				}

			}
			if (party == null) {
				if (isPrintLog) {
					System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}

			// QueryMode = 1 查询本产品
			if (WSDomain.QueryMode.PRODUCT.equals(queryMode)) {
				if (queryType == null || queryType.isEmpty()) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR,
							"queryMode=1时需要queryType信息，当前没有找到queryType节点");
				}
				if (prodId == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					// 入参所给接入号类型 无法进行“QueryMode = 1 查询本产品”的查询
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "入参所给接入号类型无法进行“QueryMode = 1 查询本产品”的查询");
				}
				CommonOfferProdDto prodDto = new CommonOfferProdDto();
				prodDto.setProdId(prodId);
				prodDtoList.add(prodDto);
				resultProdInfoMapList = queryFullInfo(party, prodDtoList, queryType, accNbr);
				//Map<String, Object> map = new HashMap<String, Object>();
//				for(int i = 0;i < resultProdInfoMapList.size();i++){
//					Map<String, Object> hdProMapSnd = (Map<String, Object>)resultProdInfoMapList.get(i);
//					String method = Thread.currentThread().getStackTrace()[1].getMethodName();
//					hdProMapSnd = desensitizationFlowFactory.generateDesensitization(hdProMapSnd ,method );
//					resultProdInfoMapList.remove(i);
//					resultProdInfoMapList.set(i, hdProMapSnd);
//				}
//				for( Object map : resultProdInfoMapList){
//					Map<String, Object> hdProMapSnd = (Map<String, Object>)map;
//					//对map进行脱敏
//					String method = Thread.currentThread().getStackTrace()[1].getMethodName();
//					desensitizationFlowFactory.generateDesensitization(hdProMapSnd ,method );
//				}
			}

			/* QueryMode = 2 查询此号码客户下所有产品列表 */
			else if (WSDomain.QueryMode.PARTY.equals(queryMode)) {
				// 查找客户下所有产品信息
				if ("2".equals(party.getPartyTypeCd().toString())) {
					start = System.currentTimeMillis();
					// 如果是政企客户只查3个产品信息，约定prodId传2
					prodDtoList = offerFacade.queryAllProdByPartyId(party.getPartyId(), 2L);
					if (isPrintLog) {
						System.out.println("queryService.offerFacade.queryAllProdByPartyId(根据客户ID查询产品信息) 执行时间："
								+ (System.currentTimeMillis() - start));
					}

				} else {
					start = System.currentTimeMillis();
					prodDtoList = offerFacade.queryAllProdByPartyId(party.getPartyId(), null);
					if (isPrintLog) {
						System.out.println("queryService.offerFacade.queryAllProdByPartyId(根据客户ID查询产品信息) 执行时间："
								+ (System.currentTimeMillis() - start));
					}

				}
				if (prodDtoList == null || prodDtoList.size() == 0) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PARTY_2_PROD_IS_NULL, "未查找到客户下对应的产品信息 partyId="
							+ party.getPartyId());
				}

				/* 如果queryMode=2接入同时号码类型为1时，将当前号码对应产品放在产品列表第一位 */
				if (WSDomain.QueryMode.PARTY.equals(queryMode) && "1".equals(accNbrType)) {
					int j = -1;// 用于保存当前产品在list中的位置值
					// 判断是否在列表中
					for (int i = 0; i < prodDtoList.size(); i++) {
						if (prodId.equals(prodDtoList.get(i).getProdId())) {
							CommonOfferProdDto prod = prodDtoList.get(i);
							prodDtoList.set(i, prodDtoList.get(0));
							prodDtoList.set(0, prod);
							j = i;
						}
					}
					if (j == -1) {
						start = System.currentTimeMillis();
						// 当前产品不在list中需单独查出来
						List<CommonOfferProdDto> prodList = offerFacade.queryAllProdByPartyId(party.getPartyId(),
								prodId);
						if (isPrintLog) {
							System.out.println("queryService.offerFacade.queryAllProdByPartyId(根据客户ID查询产品信息) 执行时间："
									+ (System.currentTimeMillis() - start));
						}

						if (CollectionUtils.isNotEmpty(prodList)) {
							prodDtoList.add(0, prodList.get(0));
						}
					}
				}
				resultProdInfoMapList = queryFullInfo(party, prodDtoList, queryType , accNbr);
			}

			/* QueryMode = 3 查询合同号对应的所有产品 */
			else if (WSDomain.QueryMode.ACCT.equals(queryMode)) {
				if (!WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR,
							"入参号码类型不是合同号，无法进行‘QueryMode = 3  查询合同号对应的所有产品’");
				}
				start = System.currentTimeMillis();
				// 根据合同号查询产品列表
				prodDtoList = offerFacade.queryCommonOfferProdByAcctCd(accNbr, areaId);
				if (isPrintLog) {
					System.out.println("queryService.offerFacade.queryCommonOfferProdByAcctCd(根据合同号查询产品信息) 执行时间："
							+ (System.currentTimeMillis() - start));
				}

				// 把list中的bean转换一下,直接取会报异常
				int size = prodDtoList.size();
				for (int i = 0; i < size; i++) {
					Map<String, Class> mymap = new HashMap<String, Class>();
					mymap.put("offerProdStatusList", OfferProdStatusDto.class);
					CommonOfferProdDto OfferProdDto = (CommonOfferProdDto) JSONObject.toBean(JSONObject
							.fromObject(prodDtoList.get(i)), CommonOfferProdDto.class, mymap);
					prodDtoList.remove(0);
					prodDtoList.add(OfferProdDto);
				}
				if (prodDtoList == null || prodDtoList.size() == 0) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
					}
					// 根据合同号未查询到产品信息
					return WSUtil
							.buildResponse(ResultCode.PRODID_BY_ACCNBR_NOT_EXIST, "根据合同号未查询到产品信息 accNbr=" + accNbr);
				}

				resultProdInfoMapList = queryFullInfo(party, prodDtoList, queryType, accNbr);
			} else {
				if (isPrintLog) {
					System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				// QueryMode错误, 不在WSDomain.QueryMode中
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "参数错误：未知的QueryMode，QueryMode=" + queryMode);
			}

			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("resultCode", ResultCode.SUCCESS);
			returnMap.put("resultMsg", "成功");
			returnMap.put("resultProdInfoMapList", resultProdInfoMapList);
			if (isPrintLog) {
				System.out.println("调用营业接口queryService执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			String result = mapEngine.transform("queryService5", returnMap);
			
			//加密部分
			if(doc.selectNodes("request/desensitizationSystemId").size() != 0&&doc.selectNodes("request/desensitizationfileGrade").size() != 0){
			String desensitizationSystemId = "";
			if(doc.selectNodes("request/desensitizationSystemId").size() != 0){
				desensitizationSystemId = WSUtil.getXmlNodeText(doc, "request/desensitizationSystemId");
			}else{
				desensitizationSystemId = "";
			}
			
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			result = desensitizationFlowFactory.generateDesensitizationForXml(request,result, method,desensitizationSystemId);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryService查询信息:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@WebMethod(exclude = true)
	private List<Object> queryFullInfo(Party party, List<CommonOfferProdDto> prodDtoList, String queryType, String accessNumber)
			throws Exception {
		Map profilesMap = new HashMap();
		Map custLevelMap = new HashMap();
		Map custBrand = new HashMap();
		Map identifyMap = new HashMap();
		List<OfferDto> offerList = new ArrayList();
		List offerParamMapList = new ArrayList();
		List resultProdInfoMapList = new ArrayList();

		//查询到的产品进行去掉重复

		for (int i = 0; i < prodDtoList.size(); i++) {
			for (int j = 0; j < i; j++) {
				if(prodDtoList.get(j) != null){
					CommonOfferProdDto ss = prodDtoList.get(j);
				if(ss.getProdId() != null){
					if (prodDtoList.get(j).getProdId().equals(prodDtoList.get(i).getProdId())) {
						prodDtoList.remove(i);
						i--;
						break;
					}
				  }
				}
			}
		}

		/* 查询详细信息 */
		for (CommonOfferProdDto prodDto : prodDtoList) {
			Map<String, Object> map = new HashMap<String, Object>();
			String ifRemoveIneffected = "true";
			// 查询类型
			map.put("queryType", queryType);
			if (queryType.contains("1")) {// 客户资料信息
				map.put("party", party);

				// 查询客户联系人等信息
				profilesMap = intfSMO.queryCustProfiles(party.getPartyId());
				map.put("profiles", profilesMap);

				// 查询客户证件信息
				identifyMap = intfSMO.queryIdentifyList(party.getPartyId());
				map.put("identifyMap", identifyMap);

				// 查询主销售品的一级目录作为客户品牌
				custBrand = intfSMO.queryCustBrand(party.getPartyId());
				map.put("custBrand", custBrand);

				/**
				 * 新的等级规则
				 * @author 
				 */
				
				CustClubMemberDto CustClubMemberDto = custBasicSMO.queryCustGrade(Long
						.valueOf(party.getPartyId()));
				// 查询客户级别
//				CustSegmentDto custGradeInfo = custBasicSMO.queryCustGrade(party.getPartyId(), null);
//				if (custGradeInfo != null) {
//					custLevelMap.put("CUSTLEVEL", custGradeInfo.getPartyGradeName());
//					String custLevelId = "";
//					if (custGradeInfo.isDiamond()) {
//						custLevelId = WSDomain.CARD_DIAMOND;
//					} else if (custGradeInfo.isGold()) {
//						custLevelId = WSDomain.CARD_GOLD;
//					} else if (custGradeInfo.isSilver()) {
//						custLevelId = WSDomain.CARD_SILVER;
//					} else {
//						custLevelId = WSDomain.CARD_COMMON;
//					}
//					custLevelMap.put("CUSTLEVELID", custLevelId);
//				}
				String custLevelId = "";
				if(CustClubMemberDto.getClubMember() != null){
					custLevelId= ""+CustClubMemberDto.getClubMember().getMembershipLevel();
					custLevelMap.put("CUSTLEVEL", CustClubMemberDto.getPartyGradeName());
				}
				custLevelMap.put("CUSTLEVELID", custLevelId);
				map.put("custLevel", custLevelMap);

				// 是否批开
				int isPK = intfSMO.queryIfPk(party.getPartyId());
				party.setIfPK(isPK);

				// 获取客户标识码
				String groupCustSeq = intfSMO.getGroupSeqByPartyId(String.valueOf(party.getPartyId()));
				map.put("groupCustSeq", groupCustSeq);

				// 查询邮政编码
				String postCode = intfSMO.getPostCodeByPartyId(party.getPartyId());
				map.put("postCode", postCode);

				// queryType只有1，跳出循环
				if ("1".equals(queryType)) {
					resultProdInfoMapList.add(map);
					break;
				}
			}

			if (queryType.contains("2")) {// 产品信息
				map.put("prodId", prodDto.getProdId());

				/* 地区+起止时间 */
				OfferProd offerProdNumbers = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_AN, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdNumbers", offerProdNumbers);

				/* 查询非主号码信息，非主接入号，非主接入号类型，非主接入号名称 */
				OfferProd offerProd2Numbers = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_2_AN, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProd2Numbers", offerProd2Numbers);

				/* 产品规格 */
				OfferProd offerProdSpecs = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_SPEC, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdSpecs", offerProdSpecs); /* 付费方式 */
				OfferProd offerProdFeeTypes = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_FEE_TYPE, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdFeeTypes", offerProdFeeTypes);

				/* 状态 */
				OfferProd offerProdStatuses = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_STATUS, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdStatuses", offerProdStatuses);

				/* 产品属性 */
				OfferProd offerProdItems = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_ITEM, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdItems", offerProdItems);

				/* 产品对应服务 + 服务依赖互斥关系 */
				OfferProd offerServs = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_SERV, new Boolean(ifRemoveIneffected).booleanValue());
				List prodServRelasMapList = new ArrayList();
				if (offerServs != null) {
					// 查询产品服务关系prodServRelas 互斥、依赖等
					List<OfferServ> OfferServs = offerServs.getOfferServs();
					for (OfferServ OfferServ : OfferServs) {
						Map prodServRelasMap = new HashMap();
						int servSpecId = OfferServ.getServSpecId();
						// 根据servSpecId查找其有关系的服务规格
						List<ProdServRela> prodServRelas = intfSMO.queryProdServRelas(servSpecId);
						prodServRelasMap.put("servSpecId", servSpecId);
						prodServRelasMap.put("prodServRelas", prodServRelas);
						prodServRelasMapList.add(prodServRelasMap);
					}
				}
				map.put("offerServs", offerServs);
				map.put("prodServRelasMapList", prodServRelasMapList);

				/* 组合产品 */
				OfferProd offerProdComps = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_COMP, new Boolean(ifRemoveIneffected).booleanValue());
				if (offerProdComps != null) {
					// 由于prod中的OfferProdComp中没有 prodCompRelaRoleCd的值
					// 只好查出来加进去
					List<OfferProdComp> OfferProdCompsList = offerProdComps.getOfferProdComps();
					for (OfferProdComp oopc : OfferProdCompsList) {
						// 查询组合产品角色
						OfferProdComp opc = intfSMO.queryOfferProdComp(oopc.getProdCompId());
						if (opc != null && oopc.getProdCompId().equals(opc.getProdCompId())) {
							// 把查到的ProdCompRelaRoleCd加进去
							oopc.setProdCompRelaRoleCd(opc.getProdCompRelaRoleCd());
						}
						//查询组合产品的主销售品
						//						String prodName = intfSMO.getOfferProdCompMainProd(oopc.getCompProdId());
						//						oopc.setProdCompSpecName(prodName);
					}
				}
				map.put("offerProdComps", offerProdComps);

				/* 关联产品 */
				OfferProd offerProd2Prods = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_2_PROD, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProd2Prods", offerProd2Prods);

				/* 关联终端 */
				OfferProd offerProd2Tds = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_2_TD, new Boolean(ifRemoveIneffected).booleanValue());
				String flag = "1";
				if (offerProd2Tds != null) {
					List<OfferProd2Td> OfferProd2Tds = offerProd2Tds.getOfferProd2Tds();
					for (OfferProd2Td offerProd2Td : OfferProd2Tds) {
						String bcdCode = offerProd2Td.getTerminalCode();
						// 查询它的materialId
						String resultXml = srFacade.getMaterialByCode("-1", bcdCode);
						Document docu = WSUtil.parseXml(resultXml);
						String materialId = WSUtil.getXmlNodeText(docu, "//GoodsDetailList/GoodsDetail/materialId");
						// 如果此materialId在新双模卡的配置数据中，就是新的 否则是老的
						flag = intfSMO.oldCGFlag(materialId);
						// 新双模卡 flag=0 旧双模卡flag=1
						if ("0".equals(flag)) {
							break;
						}
					}
				}
				map.put("offerProd2Tds", offerProd2Tds);
				map.put("oldCGFlag", flag);

				/* 获取是否组合产品标志 */
				String compProdFlag = intfSMO.ifCompProdByProdSpecId(offerProdSpecs.getProdSpecId());
				map.put("compProdFlag", compProdFlag);

				/* 获取装机地址 */
				  String addressDesc = intfSMO.queryOfferAddressDesc(prodDto.getProdId());
				  map.put("addressDesc", addressDesc);
				/* 接单点 */
				  String description = intfSMO.getTmDescription(prodDto.getProdId());
				  map.put("description", description);
				  
				/* 客户经理 */
				  String managerName = intfSMO.getPartyManagerName(prodDto.getProdId());
				  map.put("managerName", managerName);
				/* 电信管理局 */
				  String tmlName = intfSMO.getOfferProdTmlName(prodDto.getProdId());
				  map.put("tmlName", tmlName);
				/* 发展归属部门 */
				  String staffOrgId = intfSMO.getOrganizStaffOrgId(prodDto.getProdId());
				  map.put("staffOrgId", staffOrgId);
				/* 业务发展人所属部门Business development department*/
				  String bsdevdep = intfSMO.getDevelopmentDepartment(accessNumber);
				  if(bsdevdep != null){
					  map.put("bsdevdep", bsdevdep);
				  }else{
					  map.put("bsdevdep", "");
				  }
			}

			if (queryType.contains("3")) {
				/* 查询账户信息 */
				OfferProd offerProdAccounts = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_ACCOUNT, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdAccounts", offerProdAccounts);

				/* 地区+起止时间 */
				OfferProd offerProdNumbers = offerSMO.queryOfferProdInstDetailById(prodDto.getProdId(),
						CommonDomain.QRY_OFFER_PROD_AN, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdNumbers", offerProdNumbers);
			}

			if (queryType.contains("4") || queryType.contains("2")) {
				/* 查询销售品信息 */
				offerList = offerSMO.queryOfferByProdId(prodDto.getProdId(), true);
				for (OfferDto offer : offerList) {
					// 增加无线宽带号码标识判断
					if ("1".equals(offer.getOfferTypeCd()) && queryType.contains("2")) {
						int cnt = intfSMO.queryIfWLANByOfferSpecId(offer.getOfferSpecId());
						if (cnt > 0) {
							map.put("WLANFlag", "Y");
						} else {
							map.put("WLANFlag", "N");
						}
						if (!queryType.contains("4")) {
							break;
						}
					}
					// 查询所得的offer中只有StatusCd没有名称,在此处查询加入状态名称
					InstStatus instStatus = intfSMO.queryInstStatusByCd(offer.getStatusCd());
					offer.setStateName(instStatus.getName());
					// 获取服务参数
					List<OfferServItemDto> offerServParams = intfSMO.queryOfferServNotInvalid(offer.getOfferId());
					// 获取销售品参数
					List<OfferParamDto> offerParams = offerSMO.queryOfferParamByIdNotInvalid(offer.getOfferId());
					Map offerParamMap = new HashMap();
					if (CollectionUtils.isNotEmpty(offerServParams)) {
						offerParamMap.put("offerServParams", offerServParams);
					} else {
						offerParamMap.put("offerParams", offerParams);
					}
					offerParamMap.put("offerId", offer.getOfferId());
					offerParamMapList.add(offerParamMap);
				}
				map.put("offerList", offerList);
				map.put("offerParamMapList", offerParamMapList);
			}
			resultProdInfoMapList.add(map);
		}
		return resultProdInfoMapList;
	}

	/**
	 * 查询服务接口 营业受理-产品详情 QueryAction.queryProdInfo()
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/accNbrType", caption = "接入号码类型") })
	public String queryOperation(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");
			OfferProd prod = null;
			Map<String, Object> servSpecs = new HashMap<String, Object>();
			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
			}

			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				prod = intfSMO.getProdByAccessNumber(accNbr);
				if (prod == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("prodId", prod.getProdId());
				params.put("prodSpecId", prod.getProdSpecId());
				// 查询产品的服务规格信息
				servSpecs = intfSMO.getServSpecs(params);
			} else {
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}
			if (servSpecs.isEmpty()) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> queryServiceMap = new HashMap<String, Object>();
			queryServiceMap.put("resultCode", "0");
			queryServiceMap.put("resultMsg", "成功");
			queryServiceMap.put("prodId", prod.getProdId());
			queryServiceMap.put("servSpecs", servSpecs);
			return mapEngine.transform("queryOperation", queryServiceMap);
		} catch (Exception e) {
			logger.error("queryOperation查询服务接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 销售品查询接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/accNbrType", caption = "接入号码类型") })
	public String queryOffering2pp(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
			List<OfferIntf> orderOfferList = new ArrayList<OfferIntf>();
			List<Map<String, Object>> saleOfferList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> offerParamsList = new ArrayList<Map<String, Object>>();
			List<String> prodCompNameList = new ArrayList<String>();
			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				Long prodId = soQuerySMO.queryProdIdByAcessNumber(accNbr);
				if (prodId == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
				}

				/* 组合产品 */
				OfferProd offerProdComps = offerSMO.queryOfferProdInstDetailById(prodId,
						CommonDomain.QRY_OFFER_PROD_COMP, new Boolean(true).booleanValue());
				if (offerProdComps != null) {
					// 由于prod中的OfferProdComp中没有 prodCompRelaRoleCd的值
					// 只好查出来加进去
					List<OfferProdComp> OfferProdCompsList = offerProdComps.getOfferProdComps();
					for (OfferProdComp oopc : OfferProdCompsList) {
						// 查询组合产品角色
						//						OfferProdComp opc = intfSMO.queryOfferProdComp(oopc.getProdCompId());
						//						if (opc != null && oopc.getProdCompId().equals(opc.getProdCompId())) {
						//							// 把查到的ProdCompRelaRoleCd加进去
						//							oopc.setProdCompRelaRoleCd(opc.getProdCompRelaRoleCd());
						//						}
						//查询组合产品的主销售品
						String prodName = intfSMO.getOfferProdCompMainProd(oopc.getCompProdId());
						prodCompNameList.add(prodName);
					}
				}

				orderOfferList = intfSMO.queryOfferInstByProdId(prodId);
				if (CollectionUtils.isEmpty(orderOfferList)) {
					return WSUtil.buildResponse(ResultCode.OFFERLIST_BY_PRODID_NOT_EXIST);
				}
				for (OfferIntf offer : orderOfferList) {

					// 根据offerSpecId获取三级目录节点
					List<Long> CNIList = intfSMO.queryCategoryNodeId(offer.getOfferSpecId());
					// 根据三级目录节点查出目录下所有销售品
					for (Long categoryNodeId : CNIList) {
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("partyId", offer.getPartyId());
						param.put("OfferSpecId", offer.getOfferSpecId());
						param.put("staffId", staffId);
						param.put("channelId", channelId);
						param.put("areaId", areaId);
						param.put("queryByCondDim", "Y");// 默认为Y
						param.put("pubByArea", "N");// 默认为N
						param.put("topCategoryNode", "N");
						param.put("maskByDt", "Y");// 默认为Y
						param.put("Upgrade", true);// // 用于判断是否从升档进入销售品选购
						param.put("pageSize", 999);// 分页标识，默认一页10
						// param.put("curPage", curPage);//当前页
						// 加入员工信息
						LoginedStaffInfo loginedStaffInfo = new LoginedStaffInfo();
						loginedStaffInfo.setChannelId(Integer.valueOf(channelId));
						loginedStaffInfo.setStaffId(Long.valueOf(staffId));
						param.put("staffInfo", loginedStaffInfo);
						param.put("categoryNodeId", categoryNodeId.toString());// 查目录,查顶层目录传-1
						// param.put("categoryId", "100");// 不为空 查顶层目录传100
						Map<String, Object> result = offerSpecSMO.getDirOrOfferList(param);
						if (result == null) {
							continue;
						}
						if (result.containsKey("offerSpecList")) {
							List<OfferSpecShopDto> saleOffes = (List<OfferSpecShopDto>) result.get("offerSpecList");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("nodeId", categoryNodeId.toString());
							map.put("offerList", saleOffes);
							saleOfferList.add(map);
						}
					}

					// 获取销售品参数
					List<OfferParamDto> offerParams = offerSMO.queryOfferParamByIdNotInvalid(offer.getOfferId());
					Map<String, Object> offerParmsMap = new HashMap<String, Object>();
					offerParmsMap.put("offerParams", offerParams);
					offerParmsMap.put("offerId", offer.getOfferId());
					offerParamsList.add(offerParmsMap);
				}
			} else {
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}
			// 目录节点去重
			List<Map<String, Object>> newSaleOfferList = new ArrayList<Map<String, Object>>();
			Iterator<Map<String, Object>> it = saleOfferList.iterator();
			while (it.hasNext()) {
				Map<String, Object> a = it.next();
				if (CollectionUtils.isEmpty(newSaleOfferList)) {
					newSaleOfferList.add(a);
				}
				if (newSaleOfferList.get(0).get("nodeId").equals(a.get("nodeId"))) {
					it.remove();
				} else {
					newSaleOfferList.add(a);
				}
			}
			Map<String, Object> queryOffering2ppMap = new HashMap<String, Object>();
			queryOffering2ppMap.put("resultCode", "0");
			queryOffering2ppMap.put("resultMsg", "成功");
			queryOffering2ppMap.put("compProds", prodCompNameList);
			queryOffering2ppMap.put("orderOfferList", orderOfferList);
			queryOffering2ppMap.put("saleOfferList", newSaleOfferList);
			queryOffering2ppMap.put("offerParamsList", offerParamsList);
			return mapEngine.transform("queryOffering2pp", queryOffering2ppMap);
		} catch (Exception e) {
			logger.error("queryOffering2pp销售品查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 账户信息查询 营业受理-产品详情 QueryAction.queryProdInfo()
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "用户实例编号") })
	public String qryAcctInfo(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
			List<Map> acctList = acctSMO.getExistAccountsByPartyId(Long.valueOf(partyId));
			if (CollectionUtils.isEmpty(acctList)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> qryAcctInfoMap = new HashMap<String, Object>();
			qryAcctInfoMap.put("resultCode", "0");
			qryAcctInfoMap.put("resultMsg", "成功");
			qryAcctInfoMap.put("data", acctList);
			return mapEngine.transform("qryAcctInfo", qryAcctInfoMap);
		} catch (Exception e) {
			logger.error("qryAcctInfo账户信息查询:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 判断客户办理的产品数、个人客户等
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户编码") })
	public String checkPartyProd(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
			Party party = custFacade.getPartyById(Long.valueOf(partyId));
			if (party == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			// 查询个人客户下C网产品的数量（含在途单）
			int result = intfSMO.checkProductNumByPartyId(Long.valueOf(partyId));
			Map<String, Object> checkPartyProdMap = new HashMap<String, Object>();
			checkPartyProdMap.put("resultCode", "0");
			checkPartyProdMap.put("resultMsg", "成功");
			checkPartyProdMap.put("data", result);
			checkPartyProdMap.put("party", party);
			return mapEngine.transform("checkPartyProd", checkPartyProdMap);
		} catch (Exception e) {
			logger.error("checkPartyProd判断客户办理的产品数、个人客户等:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 客户下已有产品查询
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户编码") })
	public String qryProd(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
			List<CommonOfferProdDto> prodList = offerFacade.queryAllProdByPartyId(new Long(partyId), null);
			if (CollectionUtils.isEmpty(prodList)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> qryProdMap = new HashMap<String, Object>();
			qryProdMap.put("resultCode", ResultCode.SUCCESS);
			qryProdMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			qryProdMap.put("data", prodList);
			return mapEngine.transform("qryProd", qryProdMap);
		} catch (Exception e) {
			logger.error("qryProd客户下已有产品查询:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 资费要求物品查询接口 营业菜单 物品管理-物品发送（销售品和物品关联）
	 * OfferSpecFacadeHttpImpl.queryOfferRolesInfo SoFacadeHttpImpl
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/offerSpecId", caption = "销售品规格id"),
			@Node(xpath = "//request/prodSpecId", caption = "产品规格编码") })
	public String queryCouponInfoByPriceplan(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
			String prodSpecId = WSUtil.getXmlNodeText(doc, "//request/prodSpecId");
			OfferRoles offerRoles = intfSMO.findProdOfferRoles(Long.valueOf(offerSpecId), Long.valueOf(prodSpecId));
			JSONObject jsonObj = new JSONObject();
			if (offerRoles != null) {
				jsonObj.put("offerSpecId", offerSpecId);
				jsonObj.put("offerRoleId", offerRoles.getOfferRoleId());
				jsonObj.put("objType", 2);// 2为产品规格
				jsonObj.put("objId", prodSpecId);
			}
			JSONArray jaCCR = soStoreSMO.queryCouponCfgRuleByOfferInfo(jsonObj);
			List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jaCCR.size(); i++) {
				Long cfgRuleId = jaCCR.getJSONObject(i).getLong("cfgRuleId");
				JSONArray jaCG = soStoreSMO.queryCouponGrpByCfgRule(cfgRuleId, "");
				for (int j = 0; j < jaCG.size(); j++) {
					Long couponGrpId = jaCG.getJSONObject(j).getLong("couponGrpId");
					List<Map<String, Object>> list = soStoreSMO.queryCouponGrpNumberByCouponGrpId(couponGrpId);
					for (int k = 0; k < list.size(); k++) {
						Map<String, Object> tmp = new HashMap<String, Object>();
						tmp.put("COUPON_ID", list.get(k).get("COUPON_ID"));
						tmp.put("NAME", list.get(k).get("NAME"));
						couponList.add(tmp);
					}
				}
			}
			if (CollectionUtils.isEmpty(couponList)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> queryCouponInfoByPriceplanMap = new HashMap<String, Object>();
			queryCouponInfoByPriceplanMap.put("resultCode", ResultCode.SUCCESS);
			queryCouponInfoByPriceplanMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			queryCouponInfoByPriceplanMap.put("data", couponList);
			return mapEngine.transform("queryCouponInfoByPriceplan", queryCouponInfoByPriceplanMap);
		} catch (Exception e) {
			logger.error("queryCouponInfoByPriceplan资费要求物品查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 服务账户查询接口
	 * 
	 * @author chenjunjie
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/curPage", caption = "页数"),
			@Node(xpath = "//request/pageSize", caption = "页面信息个数") })
	public String querySerivceAcct(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String curPage = WSUtil.getXmlNodeText(document, "//request/curPage");
			String pageSize = WSUtil.getXmlNodeText(document, "//request/pageSize");
			String serviceName = WSUtil.getXmlNodeText(document, "//request/serviceName");
			String linkMan = WSUtil.getXmlNodeText(document, "//request/linkMan");
			String buildingId = WSUtil.getXmlNodeText(document, "//request/buildingId");
			String managerId = WSUtil.getXmlNodeText(document, "//request/managerId");
			String linkNbr = WSUtil.getXmlNodeText(document, "//request/linkNbr");
			String servicePartyId = WSUtil.getXmlNodeText(document, "//request/servicePartyId");
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isBlank(serviceName) && StringUtils.isBlank(linkMan) && StringUtils.isBlank(buildingId)
					&& StringUtils.isBlank(managerId) && StringUtils.isBlank(linkNbr)
					&& StringUtils.isBlank(servicePartyId)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR);
			}
			params.put("curPage", Integer.valueOf(curPage));
			params.put("pageSize", Integer.valueOf(pageSize));
			if (StringUtils.isNotBlank(serviceName)) {
				params.put("serviceName", serviceName);
			}
			if (StringUtils.isNotBlank(linkMan)) {
				params.put("linkMan", linkMan);
			}
			if (StringUtils.isNotBlank(buildingId)) {
				params.put("buildingId", buildingId);
			}
			if (StringUtils.isNotBlank(managerId)) {
				params.put("managerId", managerId);
			}
			if (StringUtils.isNotBlank(linkNbr)) {
				params.put("linkNbr", linkNbr);
			}
			if (StringUtils.isNotBlank(servicePartyId)) {
				params.put("servicePartyId", servicePartyId);
			}
			Map<String, Object> result = offerSMO.querySpComponentListInfo(params);
			if (result == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "服务帐户查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultObj", result);
			return mapEngine.transform("querySerivceAcct", root);
		} catch (Exception e) {
			logger.error("querySerivceAcct服务账户查询接口:" + request, e);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			String stackTraceString = sw.getBuffer().toString();
			pw.close();
			try {
				sw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return WSUtil.buildResponse("0","wftasdf#"+e.getStackTrace()+"#"+e.getCause()+"#"+stackTraceString);
			//return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 服务账户生成接口
	 * 
	 * @author chenjunjie
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/serviceName", caption = "账户名称"),
			@Node(xpath = "//request/managerId", caption = "接应经理"),
			@Node(xpath = "//request/linkMan", caption = "联系人"), @Node(xpath = "//request/linkNbr", caption = "联系电话 "),
			@Node(xpath = "//request/servicePartyId", caption = "服务客户 "),
			@Node(xpath = "//request/buildingId", caption = "楼宇信息"),
			@Node(xpath = "//request/buildingType", caption = "楼宇类型") })
	public String addSerivceAcct(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String serviceName = WSUtil.getXmlNodeText(document, "//request/serviceName");
			String managerId = WSUtil.getXmlNodeText(document, "//request/managerId");
			String linkMan = WSUtil.getXmlNodeText(document, "//request/linkMan");
			String linkNbr = WSUtil.getXmlNodeText(document, "//request/linkNbr");
			String servicePartyId = WSUtil.getXmlNodeText(document, "//request/servicePartyId");
			String linkNbr1 = WSUtil.getXmlNodeText(document, "//request/linkNbr1");
			String buildingId = WSUtil.getXmlNodeText(document, "//request/buildingId");
			String buildingType = WSUtil.getXmlNodeText(document, "//request/buildingType");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			Map<String, Object> params = new HashMap<String, Object>();
			String serviceId = commonClient.generateSeq(Integer.valueOf(areaId), "SA_2_COMPONENT", "1");
			params.put("serviceId", serviceId);
			params.put("serviceName", serviceName);
			params.put("managerId", managerId);
			params.put("linkMan", linkMan);
			params.put("linkNbr", linkNbr);
			params.put("servicePartyId", servicePartyId);
			params.put("buildingId", buildingId);
			params.put("buildingType", buildingType);
			if (StringUtils.isNotBlank(linkNbr1)) {
				params.put("linkNbr1", linkNbr1);
			}
			offerSMO.addSpComponentInfo(params);
			return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "serviceId", serviceId);
		} catch (Exception e) {
			logger.error("addSerivceAcct服务账户生成接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 查询场景模版
	 * 
	 * @author chenjunjie
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/padType", caption = "类型编号") })
	public String qryOfferModel(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String padType = WSUtil.getXmlNodeText(document, "//request/padType");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("padType", Integer.valueOf(padType));
			List<Map<String, Object>> result = intfSMO.qryOfferModel(param);
			if (CollectionUtils.isEmpty(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "场景模版查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultObj", result);
			return mapEngine.transform("qryOfferModel", root);
		} catch (Exception e) {
			logger.error("qryOfferModel查询场景模版:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 号码归属地查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "CDMA号码"),
			@Node(xpath = "//request/accNbrType", caption = "产品号码类型") })
	public String queryAccNBRInfo(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			Tel2Area tel2Area = null;
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(document, "//request/accNbrType");
			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				if (isPrintLog) {
					System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
							+ "ms");
				}
				//				logger.error("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				if (accNbr.length() > 7) {
					String accNbrString = (String) accNbr.subSequence(0, 7);
					long start = System.currentTimeMillis();
					tel2Area = intfSMO.queryAccNBRInfo(accNbrString);
					if (isPrintLog) {
						System.out.println("queryAccNBRInfo.intfSMO.queryAccNBRInfo(号码归属地查询) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//					logger.error("queryAccNBRInfo.intfSMO.queryAccNBRInfo(号码归属地查询) 执行时间:"
					//							+ (System.currentTimeMillis() - start));
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					//					logger
					//							.error("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
					//									+ "ms");
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "号码长度异常，请检查：" + accNbr);
				}
				if (tel2Area == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					//					logger
					//							.error("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
					//									+ "ms");
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "根据接入号未查询到号码归属地信息" + accNbr);
				}
			} else {
				if (isPrintLog) {
					System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
							+ "ms");
				}
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("data", tel2Area);
			if (isPrintLog) {
				System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return mapEngine.transform("queryAccNBRInfo", root);
		} catch (Exception e) {
			logger.error("queryAccNBRInfo号码归属地查询接口:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口queryAccNBRInfo(号码归属地查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 区号查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	public String queryAreaInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String areaCode = WSUtil.getXmlNodeText(document, "//request/areaCode");
			String areaName = WSUtil.getXmlNodeText(document, "//request/areaName");
			if (StringUtils.isEmpty(areaCode) && StringUtils.isEmpty(areaName)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参不能同时为空");
			}
			AreaInfo areaInfo = intfSMO.queryAreaInfo(areaCode, areaName);
			if (areaInfo == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "区号查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("areaCode", areaInfo.getAreaCode());
			root.put("areaName", areaInfo.getAreaName());
			return mapEngine.transform("queryAreaInfo", root);
		} catch (Exception e) {
			logger.error("queryAreaInfo区号查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 营业厅位置查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaCode", caption = "区号"),
			@Node(xpath = "//request/queryType", caption = "查询类型"),
			@Node(xpath = "//request/areaName", caption = "区号名称") })
	public String queryOperatingOfficeInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String areaCode = WSUtil.getXmlNodeText(document, "//request/areaCode");
			String queryType = WSUtil.getXmlNodeText(document, "//request/queryType");
			String areaName = WSUtil.getXmlNodeText(document, "//request/areaName");
			List<OperatingOfficeInfo> result = intfSMO.queryOperatingOfficeInfo(areaCode, queryType, areaName);
			if (CollectionUtils.isEmpty(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "营业厅位置查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("data", result);
			return mapEngine.transform("queryOperatingOfficeInfo", root);
		} catch (Exception e) {
			logger.error("queryOperatingOfficeInfo营业厅位置查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 一点通
	 * 
	 * @param request
	 * @return
	 * @author TERFY
	 */
	@WebMethod
	@Required
	public String businessService(@WebParam(name = "request") String request) {
		return businessAsCommonAction(request);

	}

	private String businessAsCommonAction(String request) {
		String devNumId = null;
		long mstart = System.currentTimeMillis();
		long seqOrderAgain = 0;
		String mainKey = "";
		//获得重复订购控制开关
		String cvalue = "N";
		
		//全业务流程使用参数
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String,String> paraMap = new HashMap<String,String>();
		JSONObject resultJS = new JSONObject();
		Date bDate =  new Date();
		paraMap.put("bDate", df.format(bDate));
		try {
			// 1.报文转换
			Document document = WSUtil.parseXml(request);
			//记录消息的XML报文
			String logId2 = intfSMO.getIntfCommonSeq();
			String infoXML = document.selectSingleNode("//request").asXML();
			intfSMO.saveRequestInfo(logId2, "CrmWebService", "businessServiceXML", infoXML, bDate);
			// 1.1回执单是否需要带协议
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			Element order = (Element) document.selectSingleNode("request/order");
			String orderTypeId = WSUtil.getXmlNodeText(document, "request/order/orderTypeId");
			// 1.2补充用户产品或者主接入号信息
			String prodId = null;
			String accessNumber = null;
			Element prodIdNode = (Element) document.selectSingleNode("request/order/prodId");
			Element accessNumberNode = (Element) document.selectSingleNode("request/order/accessNumber");
//			Element accessNumberNode = (Element) document.selectSingleNode("request/order/subOrder/accessNumber");
			if (accessNumberNode != null) {
				accessNumber = accessNumberNode.getText();
			}
			if (prodIdNode != null) {
				prodId = prodIdNode.getText();
			}
			//全业务流程使用参数
			//订单类型，olTypeId=1则为新装，其他为变更业务
			paraMap.put("systemId",String.valueOf(WSUtil.getXmlNodeText(document, "request/order/systemId")));
			paraMap.put("accNum", accessNumberNode==null?"": accessNumberNode.getText());
			paraMap.put("prodId", prodIdNode==null?"": prodIdNode.getText());
			paraMap.put("staffCode", String.valueOf(WSUtil.getXmlNodeText(document, "request/staffCode")));
			paraMap.put("channelId",String.valueOf(WSUtil.getXmlNodeText(document, "request/channelId")));
			paraMap.put("olNbr", "");
			paraMap.put("isSucc", "");
            if(prodId!=null && !prodId.equals("")){
            	//根据prodId 查询accessNumber
            	paraMap.put("prodId", prodId);
            	paraMap.put("accNum", intfSMO.getAccessNumberByProdId(Long.valueOf(prodId)));
            }
			//获得mainKey 和 orderTypeId 确定唯一主键表 checkBusinessServiceOrder 
			if (StringUtils.isNotBlank(accessNumber)) {
				mainKey = accessNumber;
			} else if (StringUtils.isNotBlank(prodId)) {
				mainKey = prodId;
			}

			try {
				cvalue = intfSMO.getIntfReqCtrlValue("INTF_REQ_CTRL_CONTROL");
			} catch (Exception e) {
				cvalue = "N";
			}

			if (StringUtils.isNotBlank(mainKey) && "Y".equals(cvalue)) {
				Map<String, Object> mk = new HashMap<String, Object>();
				mk.put("mainkey", mainKey);
				mk.put("orderType", orderTypeId);
				if (intfSMO.queryBussinessOrder(mk) > 0) {
					return WSUtil.buildResponse("-1", "业务没竣工，请稍后再试！");
				} else {
					seqOrderAgain = intfSMO.querySeqBussinessOrder();
					mk.put("id", seqOrderAgain);
					intfSMO.saveOrUpdateBussinessOrderCheck(mk, "save");
				}
			}

			if (StringUtils.isNotBlank(accessNumber) && StringUtils.isBlank(prodId)) {
				long start = System.currentTimeMillis();
				OfferProd prodInfo = intfSMO.getProdByAccessNumber(accessNumber);
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getProdByAccessNumber 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (prodInfo != null) {
					prodId = prodInfo.getProdId().toString();
					if (prodIdNode == null) {
						order.addElement("prodId").setText(prodInfo.getProdId().toString());
					} else {
						prodIdNode.setText(prodInfo.getProdId().toString());
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			} else if (StringUtils.isBlank(accessNumber) && StringUtils.isNotBlank(prodId)) {
				long start = System.currentTimeMillis();
				accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodId));
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getAccessNumberByProdId 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (accessNumber != null) {
					if (accessNumberNode == null) {
						order.addElement("accessNumber").setText(accessNumber);
					} else {
						accessNumberNode.setText(accessNumber);
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			}
			// 1.3用户是否欠费，若欠费，限制其做停机保号和停机保号复机业务
			long start = System.currentTimeMillis();
			Map<String, String> accountInfo = intfSMO.qryAccount(accessNumber);
			if (isPrintLog) {
				System.out.println("businessService.intfSMO.qryAccount(余额查询) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			if (accountInfo != null) {
				String amount = accountInfo.get("hisCharge");
				if (StringUtils.isNotBlank(amount)) {
					if (Long.valueOf(amount) > 0 && ("19".equals(orderTypeId) || "20".equals(orderTypeId))) {
						if (isPrintLog) {
							System.out.println("调用营业接口businessService(一点通)执行时间："
									+ (System.currentTimeMillis() - mstart) + "ms");
						}
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "用户处于欠费状态，不能办理该业务！");
					}
				}
			}

			//集团积分商城平台订购销售品过滤判断 add by helinglong 20141219
			String sid = WSUtil.getXmlNodeText(document, "request/order/systemId");
			//判断是否过滤销售品select count(1) from SERVICEOFFERSPEC n where n.offercode = #code#

			if ("6090010066".equals(sid)) {
				//订购动作
				if ("17".equals(orderTypeId)) {
					List<Element> c = document.selectNodes("request/order/offerSpecs/offerSpec");
					for (int i = 0; c.size() > i; i++) {
						String atype = WSUtil.getXmlNodeText(c.get(i), "./actionType");
						String id = WSUtil.getXmlNodeText(c.get(i), "./id");
						if (!"0".equals(atype))
							continue;
						if (!intfSMO.checkOfferSpecBsns(id)) {
							return WSUtil.buildResponse(ResultCode.BUSINESS_CHECK_ISNOTOK);
						}
					}

				}
			}
			
			
			try {
				//预付费开通国际漫游校验
				if (checkOrderType(document, getOrderTypeIds(new Integer[] { 17 }))) {
					org.dom4j.Node offerSpecs = WSUtil.getXmlNode(document, "//request/order/offerSpecs");
					List<org.dom4j.Node> xmlNodeList = null;
					if (offerSpecs != null)
						xmlNodeList = offerSpecs.selectNodes("offerSpec");
					if (xmlNodeList != null && xmlNodeList.size() > 0) {
						String prodSpecId ="";
						try {
							prodSpecId = intfSMO.getOfferSpecidByProdId(Long.parseLong(prodId));
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
						if("379".equals(prodSpecId)){
							Map<String,Object> map = new HashMap<String, Object>();
							Long partyIdByProdId = intfSMO.getPartyIdByProdId(Long.valueOf(prodId));
							map.put("partyId", partyIdByProdId);
							map.put("prodId", prodId);
							for (org.dom4j.Node n : xmlNodeList) {
								String specId = WSUtil.getXmlNodeText(n, "id");
								String actionType = WSUtil.getXmlNodeText(n, "actionType");
								if("992018226".equals(specId) && "0".equals(actionType)){
									if(!gjmyService.checkGMOfferSpec(map))
										return WSUtil.buildResponse("-1","预付费国际漫游办理校验不通过："+map.toString());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				return WSUtil.buildResponse("-1", "预付费国际漫游办理校验不通过发生异常，异常原因：" + e.getMessage());
			}

			try {
				//业务权限优化需求，对销售品订购退订变更业务进行权限判断
				if (checkOrderType(document, getOrderTypeIds(new Integer[] { 17 }))) {
					org.dom4j.Node offerSpecs = WSUtil.getXmlNode(document, "//request/order/offerSpecs");
					List<org.dom4j.Node> xmlNodeList = null;
					if (offerSpecs != null)
						xmlNodeList = offerSpecs.selectNodes("offerSpec");
					if (xmlNodeList != null && xmlNodeList.size() > 0) {
						String prodSpecId = intfSMO.getOfferSpecidByProdId(Long.parseLong(prodId));
						String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
						String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
						for (org.dom4j.Node n : xmlNodeList) {
							String specId = WSUtil.getXmlNodeText(n, "id");
							//对退订不做限制
							String actionType = WSUtil.getXmlNodeText(n, "actionType");
							if ("2".equals(actionType))
								continue;
							String offerTypeCd = intfSMO.getOfferTypeCdByOfferSpecId(specId);
							if (StringUtil.isEmpty(offerTypeCd))
								return WSUtil.buildResponse("-1", "传入的销售品id:" + specId + " 异常,请检查！");
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("staffId", Long.parseLong(staffId));
							param.put("channelId", Integer.parseInt(channelId));
							param.put("offerSpecId", Long.parseLong(specId));
							param.put("offerTypeCd", Integer.parseInt(offerTypeCd));
							if ("2".equals(offerTypeCd)) {
								param.put("prodSpecId", Long.parseLong(prodSpecId));
							}
							//此操作业务权限校验不通过！的bug修改
							String number = intfSMO.getNumByStatus(specId);
							if(number == null){
							Map<String, Object> checkBusiLimit = iofferSpecManagerService.checkBusiLimit(param);
							if (!"0".equals(checkBusiLimit.get("resultCode") + ""))
								return WSUtil.buildResponse(checkBusiLimit.get("resultCode") + "", "此操作业务权限校验不通过！");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("-1", "业务权限校验发生异常，异常原因：" + e.getMessage());
			}
			
			
			
			

			logger.debug("changeOfferSpec2ServicePakAndPricePlanPak order==", order.getText());

			// 1.4把报文中的OfferSpecs转换为ServicePak和PricePlanPak节点
			start = System.currentTimeMillis();
			List<Map<String, String>> erroMap = changeOfferSpec2ServicePakAndPricePlanPak(order);
			if (isPrintLog) {
				System.out
						.println("businessService.changeOfferSpec2ServicePakAndPricePlanPak(报文subOrder层offerSpecs转换) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			// 1.5判断是否有错误属性ID存在
			if (erroMap.size() > 0) {
				String msg = "";
				for (int i = 0; erroMap.size() > i; i++) {
					String startFashionErr = erroMap.get(i).get("startFashionErr");
					if (startFashionErr != null) {
						if ("-1".equals(startFashionErr)) {
							return WSUtil.buildResponse(ResultCode.HUCHI_OFFER_SPEC_FOR_CHANGE_STARTFASHION);
						} else if ("-2".equals(startFashionErr)) {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.HU_CHI_JIAO_YAN_EXCEPTION);
						} else {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.GUO_JI_MAN_YOU_BU_YUN_XU_BAN_LI, "该用户开通【"
									+ startFashionErr + "】需要收取押金，客服系统不允许受理，请确认");
						}
					}
					msg += erroMap.get(i).get("errId");
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), msg);
			}
			//add by helinglong 20130814
			List<Integer> l = getOrderTypeIds(new Integer[] { 19, 20, 1171, 1172 });
			if (checkOrderType(document, l)) {
				if (checkOrderZt(accessNumber, orderTypeId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");
			}

			//add by helinglong 20130423
			if (checkOrderTypeZt(document)) {
				//补换卡业务存在在途工单返回提示
				String prodSpecId = WSUtil.getXmlNodeText(document, "request/order/prodSpecId");
				if (checkOrderOnWay(prodId, prodSpecId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");

				//补换卡业务offer_prod_2_td判断是否存在相同卡号
				String terminalCode = WSUtil.getXmlNodeText(document, "request/order/tds/td/terminalCode");
				if (checkTcodeOnUse(prodId, terminalCode)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "已经提交该用户ID " + terminalCode
							+ " 卡号信息请联络IT管理员！");
				}
			}

			logger.debug("报文格式转换，为转JSON做准备:{}", document.asXML().toString());
			devNumId = WSUtil.getXmlNodeText(document, "request/order/tds/td/devNumId");
			// 2.0XML转换为JSON
			JSONObject orderJson = new JSONObject();
			try {
				System.out.println(document.asXML());
				orderJson = businessServiceOrderListFactory.generateOrderList(document);
			} catch (Exception e) {
				e.printStackTrace();
				paraMap.put("isSucc","失败");
				paraMap.put("returnMsg", "XML转换为JSON失败：" + e.toString());
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			logger.debug("报文转换为JSON:{}", orderJson.toString());
			logger.error("报文转换为JSON:{}--error", orderJson.toString());
			// 3.0 调用营业一点受理
			start = System.currentTimeMillis();
			//新增方法 存json wanghongli 20140318

			//记录给营业json串
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			String jsonString = JsonUtil.getJsonString(orderJson);
			intfSMO.saveRequestInfo(logId, "CrmJson", "businessService", jsonString, requestTime);
			String result = soServiceSMO.soAutoService(orderJson);
			intfSMO.saveResponseInfo(logId, "CrmJson", "businessService", jsonString, requestTime, result, new Date(),
					"1", "0");

			if (isPrintLog) {
				System.out.println("businessService.soServiceSMO.soAutoService(自动受理服务) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			logger.debug("调用营业一点受理返回结果:{}", result);
			 resultJS = JSONObject.fromObject(result);
			if ("0".equals(resultJS.get("resultCode"))) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				param.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJS.getString("olId"))));
				param.put("olId", resultJS.getString("olId"));
				//全业务流程参数
				paraMap.put("isSucc", ResultCode.SUCCESS.getCode());
				paraMap.put("returnMsg", ResultCode.SUCCESS.getDesc());
				paraMap.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJS.getString("olId"))));
				paraMap.put("eDate", df.format(new Date()));
				//全业务流程:根据olId查询接入号和prodId  olId = 100004254546
				Map<String,Object> prodInfo = intfSMO.getProdInfoByAccessNuber(resultJS.getString("olId"));
				if(prodInfo!= null && !prodInfo.equals("")){
					paraMap.put("accNum", String.valueOf(prodInfo.get("accessNumber")));
					paraMap.put("prodId", String.valueOf(prodInfo.get("prodId")));
				}
				String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
				// 4.0补换卡需要返回支付流水号和回执单
				if ((StringUtils.isNotBlank(systemId) && "14".equals(orderTypeId))
						|| ("6090010023".equals(systemId) && ("17".equals(orderTypeId) || "19".equals(orderTypeId)
								|| "20".equals(orderTypeId) || "1171".equals(orderTypeId) || "1172".equals(orderTypeId)
								|| "1179".equals(orderTypeId) || "18".equals(orderTypeId) || "72".equals(orderTypeId)
								|| "1165".equals(orderTypeId) || "3".equals(orderTypeId) || "201407"
								.equals(orderTypeId)))
						|| ("6090010060".equals(systemId) && ("17".equals(orderTypeId) || "19".equals(orderTypeId)
								|| "20".equals(orderTypeId) || "1171".equals(orderTypeId) || "1172".equals(orderTypeId)
								|| "1179".equals(orderTypeId) || "18".equals(orderTypeId) || "72".equals(orderTypeId)
								|| "1165".equals(orderTypeId) || "3".equals(orderTypeId) || "201407"
								.equals(orderTypeId)))) {
					start = System.currentTimeMillis();
					String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.getInterfaceIdBySystemId(获取统一支付平台编码) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

					if ("999".equals(interfaceId)) {
						interfaceId = "11";
					}
					start = System.currentTimeMillis();
					param.put("pageInfo", intfSMO.getPageInfo(resultJS.getString("olId"), "1", ifAgreementStr));
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.getPageInfo(打印回执) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//					logger.error("businessService.intfSMO.getPageInfo(打印回执) 执行时间:"
					//							+ (System.currentTimeMillis() - start));

					param.put("payIndentId", interfaceId + resultJS.getString("olId"));
				}
				// 对卡进行预占
				if (StringUtils.isNotBlank(devNumId)) {
					start = System.currentTimeMillis();
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.consoleUimK(UIMK预占与释放) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//add by wanghongli 具体化返回的信息
					if (!"1".equals(resultMap.get("result").toString())) {
						if (isPrintLog) {
							System.out.println("调用营业接口businessService(一点通)执行时间："
									+ (System.currentTimeMillis() - mstart) + "ms");
						}
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMap.get("cause").toString());
					}
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("orderSubmit", param);
			} else {
				String resultMsg = resultJS.get("resultMsg").toString();
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				//全业务流程参数 2015-11-10
				paraMap.put("isSucc",ResultCode.CALL_METHOD_ERROR.getCode());
				paraMap.put("returnMsg", resultJS.get("resultMsg").toString());
				paraMap.put("eDate", df.format(new Date()));
				
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = resultMsg;
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			logger.error("businessService一点通:" + request, e);
			e.printStackTrace();
			//全业务流程参数 2015-11-10
			paraMap.put("isSucc","失败");
			paraMap.put("returnMsg", "系统错误:"+ e.toString());
			paraMap.put("eDate", df.format(new Date()));
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		} finally {
			try {
				//调用全业务流程
				paraMap.put("eDate", df.format(new Date()));
				paraMap.put("createDate", df.format(new Date()));
				String uuid = UUID.randomUUID().toString().trim().replaceAll("-","");
				paraMap.put("uuid", uuid);
				String resultXML = allBusiFlowInterface(paraMap);
				 
				SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = fo.format(new Date());
				String olNbr = intfSMO.getOlNbrByOlId(Long.valueOf(resultJS.getString("olId")));
				Map<String, Object> saveMap = new HashMap<String, Object>();
				saveMap.put("uuid", uuid);
				saveMap.put("results", resultXML);
				saveMap.put("date", date);
				saveMap.put("systemId", "CSB");
				saveMap.put("olNbr", olNbr);
				saveMap.put("SoAutoService", "SoAutoService");
				Map<String, Object> keyValue = new HashMap<String, Object>();
				keyValue = intfSMO.qryKeyByFlag();
				String value = keyValue.get("KEYVALUE").toString();
				if(value.equals("1")){
					intfSMO.saveSendRecord(saveMap);
				}
				producerClient.sendMessage( "CSB",resultXML.toString());
				System.out.println("resultXML===============" + resultXML);
				if (seqOrderAgain != 0 || "Y".equals(cvalue)) {
					Map<String, Object> mk = new HashMap<String, Object>();
					mk.put("id", seqOrderAgain);
					intfSMO.saveOrUpdateBussinessOrderCheck(mk, "update");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
	/**
	 * 全业务流程：接收办理请求接口调用一点受理接口
	 * @param request
	 * @return
	 */
	
	private String allBusiFlowInterface(Map<String,String> paraMap) {
		StringBuffer result = new StringBuffer("");
		result.append("<PARAM>");
		result.append("<header>");
		result.append("<id>").append(paraMap.get("uuid")).append("</id>");
		//<!-- date，必填 --> <nofifyDate>消息创建时间</nofifyDate> 
		result.append("<nofifyDate>").append(paraMap.get("createDate")).append("</nofifyDate>");
		//<beginDate>业务节点开始时间</beginDate> <!-- date，必填 -->
		result.append("<beginDate>").append(paraMap.get("bDate")).append("</beginDate>");
		//<endDate>业务节点结束时间</endDate><!-- date，必填 -->
		result.append("<endDate>").append(paraMap.get("eDate")).append("</endDate>");

		//<!-- string，必填，见数据字典：平台编码 --> <systemCode>CSB</systemCode>
		result.append("<systemCode>").append("CSB").append("</systemCode>");
		//<!-- string，必填，见数据字典：操作编码 --> <operationCode>操作编码</operationCode>
		result.append("<operationCode>").append("SoAutoService").append("</operationCode>");
		//<!-- string，如果存在上级则必填，见数据字典：平台编码 --> <parentSystemCode>上级节点,见平台编码</parentSystemCode>
		result.append("<parentSystemCode>").append(paraMap.get("systemId")).append("</parentSystemCode>");
		//<!-- string，非必填，如果存在上级则必填，见数据字典，操作编码 --> <parentOperationCode>上级操作编码</parentOperationCode >
		result.append("<parentOperationCode>").append("SoAutoService").append("</parentOperationCode>");
		result.append("</header>");
		result.append("<buss>");
	    
		result.append("<channelId>").append(paraMap.get("channelId")).append("</channelId>");
		result.append("<staffNbr>").append(paraMap.get("staffCode")).append("</staffNbr>");

		//<!-- string，必填 --> /<olNbr>购物车流水</olNbr>
		result.append("<olNbr>").append(paraMap.get("olNbr")).append("</olNbr>");
		//<!-- string，必填 --> <accNum>业务号码</accNum>
		result.append("<accNum>").append(paraMap.get("accNum")).append("</accNum>");
		//<!-- number，必填 --> /<prodId>用户Id</prodId>
		result.append("<prodId>").append(paraMap.get("prodId")).append("</prodId>");
		//<!-- number，必填，1成功 0失败 --> <isSucc>是否成功</isSucc>
		result.append("<isSucc>").append(paraMap.get("isSucc")).append("</isSucc>");
		//<!-- string，非必填 --> <returnMsg>失败原因</returnMsg>
		result.append("<returnMsg>").append(paraMap.get("returnMsg")).append("</returnMsg>");
		result.append("</buss>");
		result.append("</PARAM>");
		return result.toString();
	}
	private List<Integer> getOrderTypeIds(Integer[] i) {
		List<Integer> l = new ArrayList<Integer>();
		if (i == null || i.length == 0)
			return null;
		for (int j = 0; j < i.length; j++) {
			l.add(i[j]);
		}
		return l;
	}

	@WebMethod(exclude = true)
	public String businessCommon(String request) {
		String devNumId = null;
		long mstart = System.currentTimeMillis();

		try {
			// 1.报文转换
			Document document = WSUtil.parseXml(request);
			// 1.1回执单是否需要带协议
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			Element order = (Element) document.selectSingleNode("request/order");
			String orderTypeId = WSUtil.getXmlNodeText(document, "request/order/orderTypeId");
			// 1.2补充用户产品或者主接入号信息
			String prodId = null;
			String accessNumber = null;
			Element prodIdNode = (Element) document.selectSingleNode("request/order/prodId");
			Element accessNumberNode = (Element) document.selectSingleNode("request/order/accessNumber");
			if (accessNumberNode != null) {
				accessNumber = accessNumberNode.getText();
			}
			if (prodIdNode != null) {
				prodId = prodIdNode.getText();
			}

			if (StringUtils.isNotBlank(accessNumber) && StringUtils.isBlank(prodId)) {
				long start = System.currentTimeMillis();
				OfferProd prodInfo = intfSMO.getProdByAccessNumber(accessNumber);
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getProdByAccessNumber 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (prodInfo != null) {
					if (prodIdNode == null) {
						order.addElement("prodId").setText(prodInfo.getProdId().toString());
					} else {
						prodIdNode.setText(prodInfo.getProdId().toString());
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			} else if (StringUtils.isBlank(accessNumber) && StringUtils.isNotBlank(prodId)) {
				long start = System.currentTimeMillis();
				accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodId));
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getAccessNumberByProdId 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (accessNumber != null) {
					if (accessNumberNode == null) {
						order.addElement("accessNumber").setText(accessNumber);
					} else {
						accessNumberNode.setText(accessNumber);
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			}
			// 1.3用户是否欠费，若欠费，限制其做停机保号和停机保号复机业务
			long start = System.currentTimeMillis();
			Map<String, String> accountInfo = intfSMO.qryAccount(accessNumber);
			if (isPrintLog) {
				System.out.println("businessService.intfSMO.qryAccount(余额查询) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			if (accountInfo != null) {
				String amount = accountInfo.get("hisCharge");
				if (StringUtils.isNotBlank(amount)) {
					if (Long.valueOf(amount) > 0 && ("19".equals(orderTypeId) || "20".equals(orderTypeId))) {
						if (isPrintLog) {
							System.out.println("调用营业接口businessService(一点通)执行时间："
									+ (System.currentTimeMillis() - mstart) + "ms");
						}
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "用户处于欠费状态，不能办理该业务！");
					}
				}
			}

			//集团积分商城平台订购销售品过滤判断 add by helinglong 20141219
			String sid = WSUtil.getXmlNodeText(document, "request/order/systemId");
			//判断是否过滤销售品select count(1) from SERVICEOFFERSPEC n where n.offercode = #code#

			if ("6090010066".equals(sid)) {
				//订购动作
				if ("17".equals(orderTypeId)) {
					List<Element> c = document.selectNodes("request/order/offerSpecs/offerSpec");
					for (int i = 0; c.size() > i; i++) {
						String atype = WSUtil.getXmlNodeText(c.get(i), "./actionType");
						String id = WSUtil.getXmlNodeText(c.get(i), "./id");
						if (!"0".equals(atype))
							continue;
						if (!intfSMO.checkOfferSpecBsns(id)) {
							return WSUtil.buildResponse(ResultCode.BUSINESS_CHECK_ISNOTOK);
						}
					}

				}
			}

			logger.debug("changeOfferSpec2ServicePakAndPricePlanPak order==", order.getText());

			// 1.4把报文中的OfferSpecs转换为ServicePak和PricePlanPak节点
			start = System.currentTimeMillis();
			List<Map<String, String>> erroMap = changeOfferSpec2ServicePakAndPricePlanPak(order);
			if (isPrintLog) {
				System.out
						.println("businessService.changeOfferSpec2ServicePakAndPricePlanPak(报文subOrder层offerSpecs转换) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			// 1.5判断是否有错误属性ID存在
			if (erroMap.size() > 0) {
				String msg = "";
				for (int i = 0; erroMap.size() > i; i++) {
					String startFashionErr = erroMap.get(i).get("startFashionErr");
					if (startFashionErr != null) {
						if ("-1".equals(startFashionErr)) {
							return WSUtil.buildResponse(ResultCode.HUCHI_OFFER_SPEC_FOR_CHANGE_STARTFASHION);
						} else if ("-2".equals(startFashionErr)) {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.HU_CHI_JIAO_YAN_EXCEPTION);
						} else {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.GUO_JI_MAN_YOU_BU_YUN_XU_BAN_LI, "该用户开通【"
									+ startFashionErr + "】需要收取押金，客服系统不允许受理，请确认");
						}
					}
					msg += erroMap.get(i).get("errId");
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), msg);
			}
			//add by helinglong 20130814
			List<Integer> l = getOrderTypeIds(new Integer[] { 19, 20, 1171, 1172 });
			if (checkOrderType(document, l)) {
				if (checkOrderZt(accessNumber, orderTypeId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");
			}

			//add by helinglong 20130423
			if (checkOrderTypeZt(document)) {
				//补换卡业务存在在途工单返回提示
				String prodSpecId = WSUtil.getXmlNodeText(document, "request/order/prodSpecId");
				if (checkOrderOnWay(prodId, prodSpecId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");

				//补换卡业务offer_prod_2_td判断是否存在相同卡号
				String terminalCode = WSUtil.getXmlNodeText(document, "request/order/tds/td/terminalCode");
				if (checkTcodeOnUse(prodId, terminalCode)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "已经提交该用户ID " + terminalCode
							+ " 卡号信息请联络IT管理员！");
				}
			}

			logger.debug("报文格式转换，为转JSON做准备:{}", document.asXML().toString());
			devNumId = WSUtil.getXmlNodeText(document, "request/order/tds/td/devNumId");
			// 2.0XML转换为JSON
			JSONObject orderJson = new JSONObject();
			try {
				orderJson = businessServiceOrderListFactory.generateOrderList(document);
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			logger.debug("报文转换为JSON:{}", orderJson.toString());
			logger.error("报文转换为JSON:{}--error", orderJson.toString());
			// 3.0 调用营业一点受理
			start = System.currentTimeMillis();
			//新增方法 存json wanghongli 20140318

			//记录给营业json串
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			String jsonString = JsonUtil.getJsonString(orderJson);
			intfSMO.saveRequestInfo(logId, "CrmJson", "businessService", jsonString, requestTime);
			String result = soServiceSMO.soAutoService(orderJson);
			intfSMO.saveResponseInfo(logId, "CrmJson", "businessService", jsonString, requestTime, result, new Date(),
					"1", "0");

			if (isPrintLog) {
				System.out.println("businessService.soServiceSMO.soAutoService(自动受理服务) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			logger.debug("调用营业一点受理返回结果:{}", result);
			JSONObject resultJS = JSONObject.fromObject(result);
			if ("0".equals(resultJS.get("resultCode"))) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				param.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJS.getString("olId"))));
				param.put("olId", resultJS.getString("olId"));
				String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
				// 4.0补换卡需要返回支付流水号和回执单
				if ((StringUtils.isNotBlank(systemId) && "14".equals(orderTypeId))
						|| ("6090010023".equals(systemId) && ("17".equals(orderTypeId) || "19".equals(orderTypeId)
								|| "20".equals(orderTypeId) || "1171".equals(orderTypeId) || "1172".equals(orderTypeId)
								|| "1179".equals(orderTypeId) || "18".equals(orderTypeId) || "72".equals(orderTypeId)
								|| "1165".equals(orderTypeId) || "3".equals(orderTypeId) || "201407"
								.equals(orderTypeId)))
						|| ("6090010060".equals(systemId) && ("17".equals(orderTypeId) || "19".equals(orderTypeId)
								|| "20".equals(orderTypeId) || "1171".equals(orderTypeId) || "1172".equals(orderTypeId)
								|| "1179".equals(orderTypeId) || "18".equals(orderTypeId) || "72".equals(orderTypeId)
								|| "1165".equals(orderTypeId) || "3".equals(orderTypeId) || "201407"
								.equals(orderTypeId)))) {
					start = System.currentTimeMillis();
					String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.getInterfaceIdBySystemId(获取统一支付平台编码) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

					if ("999".equals(interfaceId)) {
						interfaceId = "11";
					}
					start = System.currentTimeMillis();
					param.put("pageInfo", intfSMO.getPageInfo(resultJS.getString("olId"), "1", ifAgreementStr));
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.getPageInfo(打印回执) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//					logger.error("businessService.intfSMO.getPageInfo(打印回执) 执行时间:"
					//							+ (System.currentTimeMillis() - start));

					param.put("payIndentId", interfaceId + resultJS.getString("olId"));
				}
				// 对卡进行预占
				if (StringUtils.isNotBlank(devNumId)) {
					start = System.currentTimeMillis();
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					if (isPrintLog) {
						System.out.println("businessService.intfSMO.consoleUimK(UIMK预占与释放) 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//add by wanghongli 具体化返回的信息
					if (!"1".equals(resultMap.get("result").toString())) {
						if (isPrintLog) {
							System.out.println("调用营业接口businessService(一点通)执行时间："
									+ (System.currentTimeMillis() - mstart) + "ms");
						}
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMap.get("cause").toString());
					}
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("orderSubmit", param);
			} else {
				String resultMsg = resultJS.get("resultMsg").toString();
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = resultMsg;
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			logger.error("businessService一点通:" + request, e);
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
	}

	/**
	 * 补换卡业务，在offer_prod_2_td表中判断用户是否存在相同的卡号，
	 * 卡号的状态为：10，11，12
	 * @param prodId
	 * @param terminalCode
	 * @return
	 */
	private boolean checkTcodeOnUse(String prodId, String terminalCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodId", prodId);
		map.put("terminalCode", terminalCode);
		return intfSMO.isExistCardByProdId(map);
	}

	/**
	 * 判断补换卡是否存在在途单
	 * @param prodId
	 * @param prodSpecId
	 * @return
	 */
	private boolean checkOrderOnWay(String prodId, String prodSpecId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodId", prodId);
		map.put("prodSpecId", prodSpecId);
		return intfSMO.getIsOrderOnWay(map);
	}

	private boolean checkOrderTypeZt(Document document) {
		String orderTypeIdStr = null;
		org.dom4j.Node orderNode = WSUtil.getXmlNode(document, "//request/order");
		if (orderNode != null) {
			orderTypeIdStr = orderNode.selectSingleNode("./orderTypeId").getText();
			String[] orderTypeIds = orderTypeIdStr.split(",");
			for (int i = 0; i < orderTypeIds.length; i++) {
				int orderTypeId = Integer.parseInt(orderTypeIds[i]);
				//14:补换卡 
				if (orderTypeId == 14)
					return true;
			}
		}
		return false;
	}

	private boolean checkOrderType(Document document, List<Integer> orderTypes) {
		String orderTypeIdStr = null;
		org.dom4j.Node orderNode = WSUtil.getXmlNode(document, "//request/order");
		if (orderNode != null) {
			orderTypeIdStr = orderNode.selectSingleNode("./orderTypeId").getText();
			String[] orderTypeIds = orderTypeIdStr.split(",");
			for (int i = 0; i < orderTypeIds.length; i++) {
				int orderTypeId = Integer.parseInt(orderTypeIds[i]);
				//19:停机保号 20:停机保号复机 1171：挂失 1172:解挂
				if (orderTypes != null && orderTypes.contains(orderTypeId))
					return true;
				//				if (orderTypeId == 19 || orderTypeId == 20 || orderTypeId == 1171 || orderTypeId == 1172)
				//					return true;
			}
		}
		return false;
	}

	/**
	 * 停复机、停机保号、挂失解挂限制不能下重复单子，接口增加在途单判断
	 * @param orderTypeId 
	 * @param document
	 * @return
	 */
	private boolean checkOrderZt(String accessNumber, String orderTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accNbr", accessNumber);
		map.put("typeCd", orderTypeId);
		return intfSMO.qryAccessNumberIsZt(map);
	}
	
	/**
	 * 订单提交规则校验
	 * @author TERFY
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	@Required
	public String orderSubmitRuleCheck(@WebParam(name = "request") String request) throws Exception {
		long mstart = System.currentTimeMillis();
		String coGroupId = "";
		String devNumId = null;
		isPrintLog = true;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		try {
			long start = 0l;
			Document document = WSUtil.parseXml(request);
			//channelId 根据channelId判断是否校园标签
			String channelId = WSUtil.getXmlNodeText(document, "request/channelId");
			coGroupId = WSUtil.getXmlNodeText(document, "request/order/coGroupId");// ABM订单需保存记录
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			// 开户强制订单类型为电子渠道，防止和营业单子混了
			document.selectSingleNode("request/order/olTypeCd").setText("2");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", "insert");
				param.put("coGroupId", coGroupId);
				param.put("reqXml", request);
				param.put("state", "W");// W:未处理,R:成功,F:失败
				start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(param);
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
			}
			devNumId = WSUtil.getXmlNodeText(document, "//devNumId");
			String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
			start = System.currentTimeMillis();
			String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
			if (isPrintLog) {
				System.out.println("orderSubmit.intfSMO.getInterfaceIdBySystemId(根据平台编码获取对应统一支付的平台编码) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			if ("999".equals(interfaceId)) {
				interfaceId = "11";
			}
			if (StringUtils.isBlank(interfaceId)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "平台编码没有对应平台标识！");
			}
			
			String changeResult = changeRequestXml(document);
			if (!"".equals(changeResult)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, changeResult);
			}

			JSONObject order = new JSONObject();
			try {
				//20130517
				logger.error("generateOrderList前====" + df.format(new Date()));
				start = System.currentTimeMillis();
				//新装接口报文拼装
				order = orderListFactory.generateOrderList(document);
				if (isPrintLog) {
					System.out.println("orderSubmit.orderListFactory.generateOrderList 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				logger.error("generateOrderList后====" + df.format(new Date()));
				//20130517	
			} catch (Exception e) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
			String acctCd = order.getJSONObject("orderList").getJSONObject("orderListInfo").getString("acctCd");
			logger.error("生成账户CD{}", acctCd);
			logger.error("购物车JSON，generateOrderList{}", JsonUtil.getJsonString(order));
			//20130517
			logger.error("soAutoService前====" + df.format(new Date()));
			logger.debug("soAutoService前====" + df.format(new Date()));

			//记录给营业json串
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			String jsonString = JsonUtil.getJsonString(order);
			intfSMO.saveRequestInfo(logId, "CrmJson", "orderSubmitRuleCheck", jsonString, requestTime);
			String result="";
			try {
				result = soServiceSMO.soAutoRuleService(order);
			} catch (Exception e) {
				// TODO: handle exception
			}
			intfSMO.saveResponseInfo(logId, "CrmJson", "orderSubmitRuleCheck", jsonString, requestTime, result, new Date(), "1",
					"0");

			logger.error("soAutoService后====" + df.format(new Date()));
			JSONObject resultJson = JSONObject.fromObject(result);
			logger.error("订单提交规则校验返回结果：{}", resultJson);
			StringBuilder res = new StringBuilder();	
			if ((resultJson.getString("resultCode")).equals("0")) {
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				res.append("<olId>").append(resultJson.get("olId")).append("</olId>");
				res.append("</response>");
				return res.toString();
			} else {
				//String resultMsg = resultJson.get("resultMsg").toString();
				String resultMsg = resultJson.getString("resultMsg");
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();

				
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) { 
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else if (resultMsg.indexOf("</errorMsg>") != -1) {
					msg = resultJson.getString("errorMsg");

				} else {
					msg = "调自动受理接口返回 resultMsg:" + resultMsg;
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, msg));
					resParam.put("state", "F");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("getBrandLevelDetail.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
				}
				logger.error("失败返回前====" + df.format(new Date()));
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				res.append("<response>");
				res.append("<resultCode>").append(code).append("</resultCode>");
				res.append("<resultMsg>").append(msg).append("</resultMsg>");
				res.append("<olId>").append(resultJson.get("olId")).append("</olId>");
				res.append("</response>");
				return res.toString();
//				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> resParam = new HashMap<String, Object>();
				resParam.put("opType", "update");
				resParam.put("coGroupId", coGroupId);
				resParam.put("resXml", e.getMessage());
				resParam.put("state", "F");// W:未处理,R:成功,F:失败
				long start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(resParam);
				if (isPrintLog) {
					System.out
							.println("getBrandLevelDetail.intfSMO.getBrandLevelDetailintfSMO.updateOrInsertAbm2crmProvince 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
			}
			logger.error("orderSubmit订单提交:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}

	}
/**
	 * 一点通规则测试
	 * 
	 * @param request
	 * @return
	 * @author TERFY
	 */
	@WebMethod
	@Required
	public String businessServiceForCheck(@WebParam(name = "request") String request) {
		return businessAsCommonActionCheck(request);

	}

	private String businessAsCommonActionCheck(String request) {
		String devNumId = null;
		long mstart = System.currentTimeMillis();
		long seqOrderAgain = 0;
		String mainKey = "";
		//获得重复订购控制开关
		String cvalue = "N";
		
		//全业务流程使用参数
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String,String> paraMap = new HashMap<String,String>();
		Date bDate =  new Date();
		paraMap.put("bDate", df.format(bDate));
		try {
			// 1.报文转换
			Document document = WSUtil.parseXml(request);
			//记录消息的XML报文
			String logId2 = intfSMO.getIntfCommonSeq();
			String infoXML = document.selectSingleNode("//request").asXML();
			intfSMO.saveRequestInfo(logId2, "CrmWebService", "businessServiceCheckXML", infoXML, bDate);
			// 1.1回执单是否需要带协议
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			Element order = (Element) document.selectSingleNode("request/order");
			String orderTypeId = WSUtil.getXmlNodeText(document, "request/order/orderTypeId");
			// 1.2补充用户产品或者主接入号信息
			String prodId = null;
			String accessNumber = null;
			Element prodIdNode = (Element) document.selectSingleNode("request/order/prodId");
			Element accessNumberNode = (Element) document.selectSingleNode("request/order/accessNumber");
//			Element accessNumberNode = (Element) document.selectSingleNode("request/order/subOrder/accessNumber");
			if (accessNumberNode != null) {
				accessNumber = accessNumberNode.getText();
			}
			if (prodIdNode != null) {
				prodId = prodIdNode.getText();
			}
			//全业务流程使用参数
			//订单类型，olTypeId=1则为新装，其他为变更业务
			paraMap.put("systemId",String.valueOf(WSUtil.getXmlNodeText(document, "request/order/systemId")));
			paraMap.put("accNum", accessNumberNode==null?"": accessNumberNode.getText());
			paraMap.put("prodId", prodIdNode==null?"": prodIdNode.getText());
			paraMap.put("staffCode", String.valueOf(WSUtil.getXmlNodeText(document, "request/staffCode")));
			paraMap.put("channelId",String.valueOf(WSUtil.getXmlNodeText(document, "request/channelId")));
			paraMap.put("olNbr", "");
			paraMap.put("isSucc", "");
            if(prodId!=null && !prodId.equals("")){
            	//根据prodId 查询accessNumber
            	paraMap.put("prodId", prodId);
            	paraMap.put("accNum", intfSMO.getAccessNumberByProdId(Long.valueOf(prodId)));
            }
			//获得mainKey 和 orderTypeId 确定唯一主键表 checkBusinessServiceOrder 
			if (StringUtils.isNotBlank(accessNumber)) {
				mainKey = accessNumber;
			} else if (StringUtils.isNotBlank(prodId)) {
				mainKey = prodId;
			}

			try {
				cvalue = intfSMO.getIntfReqCtrlValue("INTF_REQ_CTRL_CONTROL");
			} catch (Exception e) {
				cvalue = "N";
			}

			if (StringUtils.isNotBlank(mainKey) && "Y".equals(cvalue)) {
				Map<String, Object> mk = new HashMap<String, Object>();
				mk.put("mainkey", mainKey);
				mk.put("orderType", orderTypeId);
				if (intfSMO.queryBussinessOrder(mk) > 0) {
					return WSUtil.buildResponse("-1", "业务没竣工，请稍后再试！");
				} else {
					seqOrderAgain = intfSMO.querySeqBussinessOrder();
					mk.put("id", seqOrderAgain);
					intfSMO.saveOrUpdateBussinessOrderCheck(mk, "save");
				}
			}

			if (StringUtils.isNotBlank(accessNumber) && StringUtils.isBlank(prodId)) {
				long start = System.currentTimeMillis();
				OfferProd prodInfo = intfSMO.getProdByAccessNumber(accessNumber);
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getProdByAccessNumber 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (prodInfo != null) {
					prodId = prodInfo.getProdId().toString();
					if (prodIdNode == null) {
						order.addElement("prodId").setText(prodInfo.getProdId().toString());
					} else {
						prodIdNode.setText(prodInfo.getProdId().toString());
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessServiceCheck(一点通)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			} else if (StringUtils.isBlank(accessNumber) && StringUtils.isNotBlank(prodId)) {
				long start = System.currentTimeMillis();
				accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodId));
				if (isPrintLog) {
					System.out.println("businessService.intfSMO.getAccessNumberByProdId 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				if (accessNumber != null) {
					if (accessNumberNode == null) {
						order.addElement("accessNumber").setText(accessNumber);
					} else {
						accessNumberNode.setText(accessNumber);
					}
				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口businessServiceCheck(一点通检测)执行时间：" + (System.currentTimeMillis() - mstart)
								+ "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
				}
			}
			// 1.3用户是否欠费，若欠费，限制其做停机保号和停机保号复机业务
			long start = System.currentTimeMillis();
			Map<String, String> accountInfo = intfSMO.qryAccount(accessNumber);
			if (isPrintLog) {
				System.out.println("businessService.intfSMO.qryAccount(余额查询) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			if (accountInfo != null) {
				String amount = accountInfo.get("hisCharge");
				if (StringUtils.isNotBlank(amount)) {
					if (Long.valueOf(amount) > 0 && ("19".equals(orderTypeId) || "20".equals(orderTypeId))) {
						if (isPrintLog) {
							System.out.println("调用营业接口businessServiceCheck(一点通检测)执行时间："
									+ (System.currentTimeMillis() - mstart) + "ms");
						}
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "用户处于欠费状态，不能办理该业务！");
					}
				}
			}

			//集团积分商城平台订购销售品过滤判断 add by helinglong 20141219
			String sid = WSUtil.getXmlNodeText(document, "request/order/systemId");
			//判断是否过滤销售品select count(1) from SERVICEOFFERSPEC n where n.offercode = #code#

			if ("6090010066".equals(sid)) {
				//订购动作
				if ("17".equals(orderTypeId)) {
					List<Element> c = document.selectNodes("request/order/offerSpecs/offerSpec");
					for (int i = 0; c.size() > i; i++) {
						String atype = WSUtil.getXmlNodeText(c.get(i), "./actionType");
						String id = WSUtil.getXmlNodeText(c.get(i), "./id");
						if (!"0".equals(atype))
							continue;
						if (!intfSMO.checkOfferSpecBsns(id)) {
							return WSUtil.buildResponse(ResultCode.BUSINESS_CHECK_ISNOTOK);
						}
					}

				}
			}
			
			
			try {
				//预付费开通国际漫游校验
				if (checkOrderType(document, getOrderTypeIds(new Integer[] { 17 }))) {
					org.dom4j.Node offerSpecs = WSUtil.getXmlNode(document, "//request/order/offerSpecs");
					List<org.dom4j.Node> xmlNodeList = null;
					if (offerSpecs != null)
						xmlNodeList = offerSpecs.selectNodes("offerSpec");
					if (xmlNodeList != null && xmlNodeList.size() > 0) {
						String prodSpecId ="";
						try {
							prodSpecId = intfSMO.getOfferSpecidByProdId(Long.parseLong(prodId));
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
						if("379".equals(prodSpecId)){
							Map<String,Object> map = new HashMap<String, Object>();
							Long partyIdByProdId = intfSMO.getPartyIdByProdId(Long.valueOf(prodId));
							map.put("partyId", partyIdByProdId);
							map.put("prodId", prodId);
							for (org.dom4j.Node n : xmlNodeList) {
								String specId = WSUtil.getXmlNodeText(n, "id");
								String actionType = WSUtil.getXmlNodeText(n, "actionType");
								if("992018226".equals(specId) && "0".equals(actionType)){
									if(!gjmyService.checkGMOfferSpec(map))
										return WSUtil.buildResponse("-1","预付费国际漫游办理校验不通过："+map.toString());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				return WSUtil.buildResponse("-1", "预付费国际漫游办理校验不通过发生异常，异常原因：" + e.getMessage());
			}

			try {
				//业务权限优化需求，对销售品订购退订变更业务进行权限判断
				if (checkOrderType(document, getOrderTypeIds(new Integer[] { 17 }))) {
					org.dom4j.Node offerSpecs = WSUtil.getXmlNode(document, "//request/order/offerSpecs");
					List<org.dom4j.Node> xmlNodeList = null;
					if (offerSpecs != null)
						xmlNodeList = offerSpecs.selectNodes("offerSpec");
					if (xmlNodeList != null && xmlNodeList.size() > 0) {
						String prodSpecId = intfSMO.getOfferSpecidByProdId(Long.parseLong(prodId));
						String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
						String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
						for (org.dom4j.Node n : xmlNodeList) {
							String specId = WSUtil.getXmlNodeText(n, "id");
							//对退订不做限制
							String actionType = WSUtil.getXmlNodeText(n, "actionType");
							if ("2".equals(actionType))
								continue;
							String offerTypeCd = intfSMO.getOfferTypeCdByOfferSpecId(specId);
							if (StringUtil.isEmpty(offerTypeCd))
								return WSUtil.buildResponse("-1", "传入的销售品id:" + specId + " 异常,请检查！");
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("staffId", Long.parseLong(staffId));
							param.put("channelId", Integer.parseInt(channelId));
							param.put("offerSpecId", Long.parseLong(specId));
							param.put("offerTypeCd", Integer.parseInt(offerTypeCd));
							if ("2".equals(offerTypeCd)) {
								param.put("prodSpecId", Long.parseLong(prodSpecId));
							}
							//此操作业务权限校验不通过！的bug修改
							String number = intfSMO.getNumByStatus(specId);
							if(number == null){
							Map<String, Object> checkBusiLimit = iofferSpecManagerService.checkBusiLimit(param);
							if (!"0".equals(checkBusiLimit.get("resultCode") + ""))
								return WSUtil.buildResponse(checkBusiLimit.get("resultCode") + "", "此操作业务权限校验不通过！");
						
							}
						}
					}
				}
			} catch (Exception e) {
				return WSUtil.buildResponse("-1", "业务权限校验发生异常，异常原因：" + e.getMessage());
			}
			
			
			
			

			logger.debug("changeOfferSpec2ServicePakAndPricePlanPak order==", order.getText());

			// 1.4把报文中的OfferSpecs转换为ServicePak和PricePlanPak节点
			start = System.currentTimeMillis();
			List<Map<String, String>> erroMap = changeOfferSpec2ServicePakAndPricePlanPak(order);
			if (isPrintLog) {
				System.out
						.println("businessService.changeOfferSpec2ServicePakAndPricePlanPak(报文subOrder层offerSpecs转换) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			// 1.5判断是否有错误属性ID存在
			if (erroMap.size() > 0) {
				String msg = "";
				for (int i = 0; erroMap.size() > i; i++) {
					String startFashionErr = erroMap.get(i).get("startFashionErr");
					if (startFashionErr != null) {
						if ("-1".equals(startFashionErr)) {
							return WSUtil.buildResponse(ResultCode.HUCHI_OFFER_SPEC_FOR_CHANGE_STARTFASHION);
						} else if ("-2".equals(startFashionErr)) {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.HU_CHI_JIAO_YAN_EXCEPTION);
						} else {
							if (isPrintLog) {
								System.out.println("调用营业接口businessService(一点通)执行时间："
										+ (System.currentTimeMillis() - mstart) + "ms");
							}
							return WSUtil.buildResponse(ResultCode.GUO_JI_MAN_YOU_BU_YUN_XU_BAN_LI, "该用户开通【"
									+ startFashionErr + "】需要收取押金，客服系统不允许受理，请确认");
						}
					}
					msg += erroMap.get(i).get("errId");
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), msg);
			}
			//add by helinglong 20130814
			List<Integer> l = getOrderTypeIds(new Integer[] { 19, 20, 1171, 1172 });
			if (checkOrderType(document, l)) {
				if (checkOrderZt(accessNumber, orderTypeId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");
			}

			//add by helinglong 20130423
			if (checkOrderTypeZt(document)) {
				//补换卡业务存在在途工单返回提示
				String prodSpecId = WSUtil.getXmlNodeText(document, "request/order/prodSpecId");
				if (checkOrderOnWay(prodId, prodSpecId))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "存在在途工单，不能受理此业务！");

				//补换卡业务offer_prod_2_td判断是否存在相同卡号
				String terminalCode = WSUtil.getXmlNodeText(document, "request/order/tds/td/terminalCode");
				if (checkTcodeOnUse(prodId, terminalCode)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), "已经提交该用户ID " + terminalCode
							+ " 卡号信息请联络IT管理员！");
				}
			}

			logger.debug("报文格式转换，为转JSON做准备:{}", document.asXML().toString());
			devNumId = WSUtil.getXmlNodeText(document, "request/order/tds/td/devNumId");
			// 2.0XML转换为JSON
			JSONObject orderJson = new JSONObject();
			try {
				orderJson = businessServiceOrderListFactory.generateOrderList(document);
			} catch (Exception e) {
				e.printStackTrace();
				paraMap.put("isSucc","失败");
				paraMap.put("returnMsg", "XML转换为JSON失败：" + e.toString());
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			logger.debug("报文转换为JSON:{}", orderJson.toString());
			logger.error("报文转换为JSON:{}--error", orderJson.toString());
			// 3.0 调用营业一点受理
			start = System.currentTimeMillis();
			//新增方法 存json wanghongli 20140318

			//记录给营业json串
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			String jsonString = JsonUtil.getJsonString(orderJson);
			
			//变更状态
			if (seqOrderAgain != 0 && "Y".equals(cvalue)) {
				Map<String, Object> mk = new HashMap<String, Object>();
				mk.put("id", seqOrderAgain);
				intfSMO.saveOrUpdateBussinessOrderCheck(mk, "update");
			}

			intfSMO.saveRequestInfo(logId, "CrmJson", "businessServiceForCheck", jsonString, requestTime);
			String result = soServiceSMO.soAutoRuleService(orderJson);
			intfSMO.saveResponseInfo(logId, "CrmJson", "businessServiceForCheck", jsonString, requestTime, result, new Date(),
					"1", "0");

			if (isPrintLog) {
				System.out.println("businessService.soServiceSMO.soAutoService(自动受理服务) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			logger.debug("调用营业一点受理检测返回结果:{}", result);
			JSONObject resultJS = JSONObject.fromObject(result);
			StringBuilder res = new StringBuilder();
			//结果olId
			if ("0".equals(resultJS.get("resultCode"))) {
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				res.append("<olId>").append(resultJS.get("olId")).append("</olId>");
				res.append("</response>");
				return res.toString();
			} else {
				String resultMsg = resultJS.get("resultMsg").toString();
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				//全业务流程参数 2015-11-10
				paraMap.put("isSucc",ResultCode.CALL_METHOD_ERROR.getCode());
				paraMap.put("returnMsg", resultJS.get("resultMsg").toString());
				paraMap.put("eDate", df.format(new Date()));
				
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = resultMsg;
				}
				if (isPrintLog) {
					System.out
							.println("调用营业接口businessService(一点通)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(msg).append("</resultMsg>");
				res.append("<olId>").append(resultJS.get("olId")).append("</olId>");
				res.append("</response>");
				return res.toString();
			}
		} catch (Exception e) {
			logger.error("businessService一点通:" + request, e);
			e.printStackTrace();
			//全业务流程参数 2015-11-10
			paraMap.put("isSucc","失败");
			paraMap.put("returnMsg", "系统错误:"+ e.toString());
			paraMap.put("eDate", df.format(new Date()));
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
	}

	/**
	 * 订单提交
	 * @author TERFY
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	@Required
	public String orderSubmit(@WebParam(name = "request") String request) throws Exception {
		long mstart = System.currentTimeMillis();
		String coGroupId = "";
		String devNumId = null;
		isPrintLog = true;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		try {
			long start = 0l;
			Document document = WSUtil.parseXml(request);
			//channelId 根据channelId判断是否校园标签
			String channelId = WSUtil.getXmlNodeText(document, "request/channelId");
			coGroupId = WSUtil.getXmlNodeText(document, "request/order/coGroupId");// ABM订单需保存记录
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			// 开户强制订单类型为电子渠道，防止和营业单子混了
			document.selectSingleNode("request/order/olTypeCd").setText("2");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", "insert");
				param.put("coGroupId", coGroupId);
				param.put("reqXml", request);
				param.put("state", "W");// W:未处理,R:成功,F:失败
				start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(param);
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
			}
			devNumId = WSUtil.getXmlNodeText(document, "//devNumId");
			String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
			start = System.currentTimeMillis();
			String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
			if (isPrintLog) {
				System.out.println("orderSubmit.intfSMO.getInterfaceIdBySystemId(根据平台编码获取对应统一支付的平台编码) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			if ("999".equals(interfaceId)) {
				interfaceId = "11";
			}
			if (StringUtils.isBlank(interfaceId)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "平台编码没有对应平台标识！");
			}
			
			String changeResult = changeRequestXml(document);
			if (!"".equals(changeResult)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, changeResult);
			}

			JSONObject order = new JSONObject();
			try {
				//20130517
				logger.error("generateOrderList前====" + df.format(new Date()));
				start = System.currentTimeMillis();
				//新装接口报文拼装
				order = orderListFactory.generateOrderList(document);
				if (isPrintLog) {
					System.out.println("orderSubmit.orderListFactory.generateOrderList 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				System.out.println("----------------------------------------------"+order);
				logger.error("generateOrderList后====" + df.format(new Date()));
				//20130517	
			} catch (Exception e) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
			String acctCd = order.getJSONObject("orderList").getJSONObject("orderListInfo").getString("acctCd");
			logger.error("生成账户CD{}", acctCd);
			logger.error("购物车JSON，generateOrderList{}", JsonUtil.getJsonString(order));
			//20130517
			logger.error("soAutoService前====" + df.format(new Date()));
			logger.debug("soAutoService前====" + df.format(new Date()));

			//记录给营业json串
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			String jsonString = JsonUtil.getJsonString(order);
			intfSMO.saveRequestInfo(logId, "CrmJson", "orderSubmit", jsonString, requestTime);
			String result="";
			try {
				result = soServiceSMO.soAutoService(order);
			} catch (Exception e) {
				// TODO: handle exception
			}
			intfSMO.saveResponseInfo(logId, "CrmJson", "orderSubmit", jsonString, requestTime, result, new Date(), "1",
					"0");

			logger.error("soAutoService后====" + df.format(new Date()));
			JSONObject resultJson = JSONObject.fromObject(result);
			logger.error("订单提交返回结果：{}", resultJson);
			if ((resultJson.getString("resultCode")).equals("0")) {
				Map<String, Object> payInfoListMap = new HashMap<String, Object>();
				payInfoListMap.put("olId", resultJson.getString("olId"));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				param.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJson.getString("olId"))));
				param.put("olId", resultJson.getString("olId"));
				// pad需要回参带回执单信息，其他平台通过回执重打接口调去，减少接口响应时间
				if ("6090010023".equals(systemId) || "6090010060".equals(systemId)) {
					start = System.currentTimeMillis();
					param.put("pageInfo", intfSMO.getPageInfo(resultJson.getString("olId"), "1", ifAgreementStr));
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.getPageInfo 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//					logger.error("orderSubmit.intfSMO.getPageInfo 执行时间:" + (System.currentTimeMillis() - start));

				}
				param.put("payIndentId", interfaceId + resultJson.getString("olId"));
				param.put("acctCd", acctCd);
				start = System.currentTimeMillis();
				Map<String, Object> au = intfSMO.getAuditingTicketInfoByOlId(resultJson.getString("olId"));
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.getAuditingTicketInfoByOlId(根据订单号查询抵用券ID和密码) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}

				if (au != null) {
					param.put("bcdPwd", au.get("VALUE"));
					param.put("bcdCode", au.get("CD"));
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", mapEngine.transform("orderSubmit", resParam));
					resParam.put("state", "R");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

				}
				// 预占UIM卡
				if (StringUtils.isNotBlank(devNumId)) {
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					//add by wanghongli 具体化返回的信息
					if (!"1".equals(resultMap.get("result").toString())) {
						if (isPrintLog) {
							System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart)
									+ "ms");
						}
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, "预占UIM卡失败："
								+ resultMap.get("cause").toString());
					}
				}
				logger.error("成功返回前====" + df.format(new Date()));
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("orderSubmit", param);
			} else {
				//String resultMsg = resultJson.get("resultMsg").toString();
				String resultMsg = resultJson.getString("resultMsg");
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) { 
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else if (resultMsg.indexOf("</errorMsg>") != -1) {
					msg = resultJson.getString("errorMsg");

				} else {
					msg = "调自动受理接口返回 resultMsg:" + resultMsg;
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, msg));
					resParam.put("state", "F");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("getBrandLevelDetail.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
				}
				logger.error("失败返回前====" + df.format(new Date()));
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> resParam = new HashMap<String, Object>();
				resParam.put("opType", "update");
				resParam.put("coGroupId", coGroupId);
				resParam.put("resXml", e.getMessage());
				resParam.put("state", "F");// W:未处理,R:成功,F:失败
				long start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(resParam);
				if (isPrintLog) {
					System.out
							.println("getBrandLevelDetail.intfSMO.getBrandLevelDetailintfSMO.updateOrInsertAbm2crmProvince 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
			}
			logger.error("orderSubmit订单提交:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}

	}

	/**
	 * 订单提交---提供给空写平台
	 * 因为空写平台不会传入channelId，屏蔽对channelId的拦截，所以单独抽出
	 * @author helinglong
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	@Required
	public String orderSubmit_kx(@WebParam(name = "request") String request) throws Exception {
		long mstart = System.currentTimeMillis();
		String coGroupId = "";
		String devNumId = null;
		String accNum = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		try {
			long start = 0l;
			Document document = WSUtil.parseXml(request);
			coGroupId = WSUtil.getXmlNodeText(document, "request/order/coGroupId");// ABM订单需保存记录
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			// 开户强制订单类型为电子渠道，防止和营业单子混了
			document.selectSingleNode("request/order/olTypeCd").setText("2");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", "insert");
				param.put("coGroupId", coGroupId);
				param.put("reqXml", request);
				param.put("state", "W");// W:未处理,R:成功,F:失败
				start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(param);
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
			}
			devNumId = WSUtil.getXmlNodeText(document, "//devNumId");
			accNum = WSUtil.getXmlNodeText(document, "request/order/accessNumber");
			//空写增加devNumId
			if (StringUtils.isBlank(devNumId)) {
				devNumId = intfSMO.getDevNumIdByAccNum(accNum) + "";
				logger.debug("-----------devNumId:" + devNumId);
				document.selectSingleNode("request/order/tds/td/devNumId").setText(devNumId);
			}

			String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
			start = System.currentTimeMillis();
			String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
			if (isPrintLog) {
				System.out.println("orderSubmit.intfSMO.getInterfaceIdBySystemId(根据平台编码获取对应统一支付的平台编码) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			if ("999".equals(interfaceId)) {
				interfaceId = "11";
			}
			if (StringUtils.isBlank(interfaceId)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "平台编码没有对应平台标识！");
			}
			String changeResult = changeRequestXml(document);
			if (!"".equals(changeResult)) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, changeResult);
			}
			//空写平台不会传入channelId，需要根据传入的staffCode去找
			String channelId = WSUtil.getXmlNodeText(document, "request/channelId");
			String imsi4 = WSUtil.getXmlNodeText(document, "request/staffCode");
			if (StringUtils.isBlank(channelId)) {
				Long stid = intfSMO.getStaffIdByDbid(imsi4);
				if (stid == null) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "根据代办号：" + imsi4 + "未查询到员工工号！");
				}
				String staffCode = intfSMO.findStaffNumByStaffId(stid);
				channelId = intfSMO.getChannelIdByStaffCode(staffCode) + "";
				if (StringUtils.isBlank(channelId)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "根据员工工号：" + staffCode + "未查询到渠道信息！");
				}
				String staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
				Document doc = WSUtil.parseXml(staffInfo);
				String staffId = WSUtil.getXmlNodeText(doc, "//staffId");
				Element rootElement = document.getRootElement();
				rootElement.addElement("staffId").addText(staffId);
				document.selectSingleNode("request/channelId").setText(channelId);
			}

			JSONObject order = new JSONObject();
			try {
				//20130517
				logger.error("generateOrderList前====" + df.format(new Date()));
				start = System.currentTimeMillis();
				order = orderListFactory.generateOrderList(document);
				if (isPrintLog) {
					System.out.println("orderSubmit.orderListFactory.generateOrderList 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
				logger.error("generateOrderList后====" + df.format(new Date()));
				//20130517	
			} catch (Exception e) {
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			String acctCd = order.getJSONObject("orderList").getJSONObject("orderListInfo").getString("acctCd");
			logger.error("生成账户CD{}", acctCd);
			logger.error("购物车JSON，generateOrderList{}", JsonUtil.getJsonString(order));
			System.out.println("ttttttttttJson==" + JsonUtil.getJsonString(order));
			//20130517
			logger.error("soAutoService前====" + df.format(new Date()));
			logger.debug("soAutoService前====" + df.format(new Date()));
			String result = soServiceSMO.soAutoService(order);
			logger.error("soAutoService后====" + df.format(new Date()));
			JSONObject resultJson = JSONObject.fromObject(result);
			logger.error("订单提交返回结果：{}", resultJson);
			if ((resultJson.getString("resultCode")).equals("0")) {
				Map<String, Object> payInfoListMap = new HashMap<String, Object>();
				payInfoListMap.put("olId", resultJson.getString("olId"));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				param.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJson.getString("olId"))));
				param.put("olId", resultJson.getString("olId"));
				// pad需要回参带回执单信息，其他平台通过回执重打接口调去，减少接口响应时间
				if ("6090010023".equals(systemId)) {
					start = System.currentTimeMillis();
					param.put("pageInfo", intfSMO.getPageInfo(resultJson.getString("olId"), "1", ifAgreementStr));
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.getPageInfo 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
					//					logger.error("orderSubmit.intfSMO.getPageInfo 执行时间:" + (System.currentTimeMillis() - start));

				}
				param.put("payIndentId", interfaceId + resultJson.getString("olId"));
				param.put("acctCd", acctCd);
				start = System.currentTimeMillis();
				Map<String, Object> au = intfSMO.getAuditingTicketInfoByOlId(resultJson.getString("olId"));
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.getAuditingTicketInfoByOlId(根据订单号查询抵用券ID和密码) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}

				if (au != null) {
					param.put("bcdPwd", au.get("VALUE"));
					param.put("bcdCode", au.get("CD"));
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", mapEngine.transform("orderSubmit", resParam));
					resParam.put("state", "R");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

				}
				// 预占UIM卡
				if (StringUtils.isNotBlank(devNumId)) {
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					//add by wanghongli 具体化返回的信息
					if (!"1".equals(resultMap.get("result").toString())) {
						if (isPrintLog) {
							System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart)
									+ "ms");
						}
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, "预占UIM卡失败："
								+ resultMap.get("cause").toString());
					}
				}
				logger.error("成功返回前====" + df.format(new Date()));
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("orderSubmit", param);
			} else {
				//String resultMsg = resultJson.get("resultMsg").toString();
				String resultMsg = resultJson.getString("resultMsg");
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();

				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rules");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else if (resultMsg.indexOf("</errorMsg>") != -1) {
					msg = resultJson.getString("errorMsg");

				} else {
					msg = "调自动受理接口返回 resultMsg:" + resultMsg;
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, msg));
					resParam.put("state", "F");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("getBrandLevelDetail.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
				}
				logger.error("失败返回前====" + df.format(new Date()));
				if (isPrintLog) {
					System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> resParam = new HashMap<String, Object>();
				resParam.put("opType", "update");
				resParam.put("coGroupId", coGroupId);
				resParam.put("resXml", e.getMessage());
				resParam.put("state", "F");// W:未处理,R:成功,F:失败
				long start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(resParam);
				if (isPrintLog) {
					System.out
							.println("getBrandLevelDetail.intfSMO.getBrandLevelDetailintfSMO.updateOrInsertAbm2crmProvince 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
			}
			logger.error("orderSubmit订单提交:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口orderSubmit(订单提交)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 简化订单提交
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	@Required
	public String orderSubmitSly(@WebParam(name = "request") String request) throws Exception {
		String coGroupId = "";
		String devNumId = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		try {
			long start = 0l;
			Document document = WSUtil.parseXml(request);
			coGroupId = WSUtil.getXmlNodeText(document, "request/order/coGroupId");// ABM订单需保存记录
			String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
			String olId = WSUtil.getXmlNodeText(document, "request/order/olId");
			// 开户强制订单类型为电子渠道，防止和营业单子混了
			//document.selectSingleNode("request/order/olTypeCd").setText("2");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", "insert");
				param.put("coGroupId", coGroupId);
				param.put("reqXml", request);
				param.put("state", "W");// W:未处理,R:成功,F:失败
				start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(param);
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
			}
			devNumId = WSUtil.getXmlNodeText(document, "//devNumId");
			String systemId = WSUtil.getXmlNodeText(document, "request/order/systemId");
			start = System.currentTimeMillis();
			String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
			if (isPrintLog) {
				System.out.println("orderSubmit.intfSMO.getInterfaceIdBySystemId(根据平台编码获取对应统一支付的平台编码) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			if ("999".equals(interfaceId)) {
				interfaceId = "11";
			}
			if (StringUtils.isBlank(interfaceId)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "平台编码没有对应平台标识！");
			}
			String changeResult = changeRequestXml(document);
			if (!"".equals(changeResult)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, changeResult);
			}
			String orderStr = intfSMO.getRequestInfo(olId);
			System.out.println("从数据库里查到的json orderStr===" + orderStr);
			JSONObject order = JSONObject.fromObject(orderStr);
			System.out.println("转换成的json===order" + order);
			/*	try {
					//20130517
					logger.error("generateOrderList前====" + df.format(new Date()));
					start = System.currentTimeMillis();
					order = orderListFactory.generateOrderList(document);
					logger.error("orderSubmit.orderListFactory.generateOrderList 执行时间:"
							+ (System.currentTimeMillis() - start));
					logger.error("generateOrderList后====" + df.format(new Date()));
					//20130517	
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}*/
			String acctCd = order.getJSONObject("orderList").getJSONObject("orderListInfo").getString("acctCd");
			logger.error("生成账户CD{}", acctCd);
			logger.error("购物车JSON，generateOrderList{}", JsonUtil.getJsonString(order));
			//20130517
			logger.error("soAtuoCommitCardService====" + df.format(new Date()));
			//String result = soServiceSMO.soAutoService(order);
			
			//日志
			String logId2 = intfSMO.getIntfCommonSeq();
			Date requestTime2 = new Date();
			intfSMO.saveRequestInfo(logId2, "CrmWebService", "soAtuoCommitCardService", "简化购物车流程提交："+request, requestTime2);
			String result = soServiceSMO.soAtuoCommitCardService(order, Long.parseLong(olId));
			intfSMO.saveResponseInfo(logId2, "CrmWebService", "soAtuoCommitCardService", "简化购物车流程提交："+ request, requestTime2, result, new Date(), "1","0");
			
			logger.error("soAtuoCommitCardService====" + df.format(new Date()));
			JSONObject resultJson = JSONObject.fromObject(result);
			logger.error("订单提交返回结果：{}", resultJson);
			if ((resultJson.getString("resultCode")).equals("0")) {
				Map<String, Object> payInfoListMap = new HashMap<String, Object>();
				payInfoListMap.put("olId", resultJson.getString("olId"));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				param.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJson.getString("olId"))));
				param.put("olId", resultJson.getString("olId"));
				// pad需要回参带回执单信息，其他平台通过回执重打接口调去，减少接口响应时间
				if ("6090010023".equals(systemId)) {
					start = System.currentTimeMillis();
					param.put("pageInfo", intfSMO.getPageInfo(resultJson.getString("olId"), "1", ifAgreementStr));
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.getPageInfo 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

				}
				param.put("payIndentId", interfaceId + resultJson.getString("olId"));
				param.put("acctCd", acctCd);
				start = System.currentTimeMillis();
				Map<String, Object> au = intfSMO.getAuditingTicketInfoByOlId(resultJson.getString("olId"));
				if (isPrintLog) {
					System.out.println("orderSubmit.intfSMO.getAuditingTicketInfoByOlId(根据订单号查询抵用券ID和密码) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}

				if (au != null) {
					param.put("bcdPwd", au.get("VALUE"));
					param.put("bcdCode", au.get("CD"));
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", mapEngine.transform("orderSubmit", resParam));
					resParam.put("state", "R");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("orderSubmit.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}

				}
				// 预占UIM卡
				if (StringUtils.isNotBlank(devNumId)) {
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					//add by wanghongli 具体化返回的信息
					if (!"1".equals(resultMap.get("result").toString())) {
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, "预占UIM卡失败："
								+ resultMap.get("cause").toString());
					}
				}
				logger.error("成功返回前====" + df.format(new Date()));
				return mapEngine.transform("orderSubmit", param);
			} else {
				String resultMsg = resultJson.get("resultMsg").toString();
				String msg = "";
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = "rule!=-1:" + resultMsg;
				}
				if (StringUtils.isNotBlank(coGroupId)) {
					Map<String, Object> resParam = new HashMap<String, Object>();
					resParam.put("opType", "update");
					resParam.put("coGroupId", coGroupId);
					resParam.put("olId", resultJson.getString("olId"));
					resParam.put("resXml", WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, msg));
					resParam.put("state", "F");// W:未处理,R:成功,F:失败
					start = System.currentTimeMillis();
					intfSMO.updateOrInsertAbm2crmProvince(resParam);
					if (isPrintLog) {
						System.out.println("getBrandLevelDetail.intfSMO.updateOrInsertAbm2crmProvince 执行时间:"
								+ (System.currentTimeMillis() - start));
					}
				}
				logger.error("失败返回前====" + df.format(new Date()));
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			if (StringUtils.isNotBlank(coGroupId)) {
				Map<String, Object> resParam = new HashMap<String, Object>();
				resParam.put("opType", "update");
				resParam.put("coGroupId", coGroupId);
				resParam.put("resXml", e.getMessage());
				resParam.put("state", "F");// W:未处理,R:成功,F:失败
				long start = System.currentTimeMillis();
				intfSMO.updateOrInsertAbm2crmProvince(resParam);
				if (isPrintLog) {
					System.out
							.println("getBrandLevelDetail.intfSMO.getBrandLevelDetailintfSMO.updateOrInsertAbm2crmProvince 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				//				logger
				//						.error("getBrandLevelDetail.intfSMO.getBrandLevelDetailintfSMO.updateOrInsertAbm2crmProvince 执行时间:"
				//								+ (System.currentTimeMillis() - start));
			}
			logger.error("orderSubmit订单提交:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}

	}

	/**
	 * 报文subOrder层offerSpecs转换
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod(exclude = true)
	private List<Map<String, String>> changeOfferSpec2ServicePakAndPricePlanPak(Element order) throws Exception {
		List<Map<String, String>> errorMap = new ArrayList<Map<String, String>>();
		// 根据互斥往offerSpecs节点下添加和新订购互斥的已有销售品退订节点
		logger.debug("order", order.toString());
		String changeResult = intfSMO.changeRequestByCheckRelaSub(order);

		// 如果是-1，说说明新订购和已有产品互斥，且生效时间是立即的不允许
		if (!"0".equals(changeResult)) {
			Map<String, String> startFashionErr = new HashMap<String, String>();
			startFashionErr.put("startFashionErr", changeResult);
			errorMap.add(startFashionErr);
			return errorMap;
		}
		Element offerSpecs = (Element) order.selectSingleNode("./offerSpecs");
		if (offerSpecs != null) {
			List<Element> offerSpec = offerSpecs.selectNodes("./offerSpec");

			// 增加判断是否下发重复销售品 wanghongli 20130410
			String[] s = new String[offerSpec.size()];
			Set<String> sSet = new HashSet<String>();
			for (int i = 0; offerSpec.size() > i; i++) {
				String id = WSUtil.getXmlNodeText(offerSpec.get(i), "id");
				s[i] = id;
			}
			for (String str : s) {
				sSet.add(str);
			}
			if (sSet.size() != s.length) {
				Map<String, String> err = new HashMap<String, String>();
				err.put("errId", "销售品下发重复，请修改后重新下发!");
				errorMap.add(err);
				return errorMap;
			}

			for (int i = 0; offerSpec.size() > i; i++) {
				// -------------------------添加服务和资费节点-------------------
				String id = WSUtil.getXmlNodeText(offerSpec.get(i), "id");
				if (StringUtils.isBlank(id)) {
					throw new Exception("销售品节点ID为空！");
				}
				// 根据销售品规格ID将销售品节点分为资费和服务
				String serviceId = intfSMO.findOfferOrService(Long.valueOf(id));
				// 增加资费节点
				Element pricePlanPak = (Element) order.selectSingleNode("./pricePlanPak");
				if (pricePlanPak == null) {
					order.addElement("pricePlanPak");
				}
				Element newPricePlanPak = (Element) order.selectSingleNode("./pricePlanPak");
				newPricePlanPak.add((Element) offerSpec.get(i).clone());

				Element newOfferSpec = (Element) newPricePlanPak.selectSingleNode("./offerSpec");
				newOfferSpec.setName("pricePlan");
				List<Element> pricePlan = newPricePlanPak.selectNodes("./pricePlan");
				Element pricePlanId = (Element) pricePlan.get(i).selectSingleNode("./id");
				pricePlanId.setName("pricePlanCd");
				Element proProperties = (Element) pricePlan.get(i).selectSingleNode("./properties");
				if (proProperties != null) {
					pricePlan.get(i).remove(proProperties);
				}
				// 增加服务节点
				if (StringUtils.isNotBlank(serviceId)) {
					Element servicePak = (Element) order.selectSingleNode("./servicePak");
					if (servicePak == null) {
						order.addElement("servicePak");
					}
					Element newServicePak = (Element) order.selectSingleNode("./servicePak");
					newServicePak.add((Element) offerSpec.get(i).clone());
					Element newOfferSpecTwo = (Element) newServicePak.selectSingleNode("./offerSpec");
					newOfferSpecTwo.setName("service");
					List<Element> service = newServicePak.selectNodes("./service");
					Element oldServiceId = (Element) service.get(service.size() - 1).selectSingleNode("./id");
					oldServiceId.setName("serviceId");
					oldServiceId.setText(serviceId);
					Element serviceProperties = (Element) service.get(service.size() - 1).selectSingleNode(
							"./properties");
					if (serviceProperties != null) {
						service.get(service.size() - 1).remove(serviceProperties);
					}
					// --------------------------添加属性节点---------------------
					// 查询属性规格ID是否属于服务属性规格
					Element oldProperties = (Element) offerSpec.get(i).selectSingleNode("./properties");
					if (oldProperties != null) {
						List<Element> oldProperty = oldProperties.selectNodes("./property");
						for (int j = 0; oldProperty.size() > j; j++) {
							String oldPropertyId = oldProperty.get(j).selectSingleNode("./id").getText();
							String sign = "N";
							List<Map> servSpecIdList = intfSMO.findServSpecItem(Long.valueOf(id), "servicePak");
							for (int p = 0; servSpecIdList.size() > p; p++) {
								String itemSpecId = servSpecIdList.get(p).get("ITEM_SPEC_ID").toString();
								if (itemSpecId.equals(oldPropertyId)) {
									sign = "S";
								}
							}

							if (sign.equals("S")) {
								if (service.get(service.size() - 1).selectSingleNode("./properties") == null) {
									service.get(service.size() - 1).addElement("properties");
								}
								Element newService = (Element) service.get(service.size() - 1).selectSingleNode(
										"./properties");
								newService.add((Element) oldProperty.get(j).clone());
							}
						}
					}
				}
				Element oldProperties = (Element) offerSpec.get(i).selectSingleNode("./properties");
				if (oldProperties != null) {
					List<Element> oldProperty = oldProperties.selectNodes("./property");
					for (int j = 0; oldProperty.size() > j; j++) {
						String oldPropertyId = oldProperty.get(j).selectSingleNode("./id").getText();
						// 查询属性规格ID是否属于资费属性规格
						List<Map> priceSpecIdList = intfSMO.findServSpecItem(Long.valueOf(id), "pricePlanPak");
						String sign = "N";
						for (int k = 0; priceSpecIdList.size() > k; k++) {
							String itemSpecId = priceSpecIdList.get(k).get("ITEM_SPEC_ID").toString();
							if (itemSpecId.equals(oldPropertyId)) {
								sign = "Y";
							}
						}
						if (sign.equals("Y")) {
							if (pricePlan.get(i).selectSingleNode("./properties") == null) {
								pricePlan.get(i).addElement("properties");
							}
							Element newPricePlan = (Element) pricePlan.get(i).selectSingleNode("./properties");
							newPricePlan.add((Element) oldProperty.get(j).clone());
						} else {
							// 判断属性ID是否存在且和销售品对应
							String flag = "N";
							List<Map> servSpecIdList = intfSMO.findServSpecItem(Long.valueOf(id), "servicePak");
							for (int p = 0; servSpecIdList.size() > p; p++) {
								String itemSpecId = servSpecIdList.get(p).get("ITEM_SPEC_ID").toString();
								if (itemSpecId.equals(oldPropertyId)) {
									flag = "S";
								}
							}
							if ("N".equals(flag)) {
								Map<String, String> err = new HashMap<String, String>();
								err.put("errId", String.format("销售品【%s】无ID为【%s】的属性", id, oldPropertyId));
								errorMap.add(err);
							}
						}
					}
				}
				//增加couponInfos

			}
			order.remove(offerSpecs);
		}
		return errorMap;
	}

	/**
	 * 报文转换
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod(exclude = true)
	private String changeRequestXml(Document document) throws Exception {
		Element orderNode = (Element) document.selectSingleNode("request/order");
		String msg = "";
		List<Map<String, String>> erroMap1 = changeOfferSpec2ServicePakAndPricePlanPak(orderNode);
		if (erroMap1.size() > 0) {
			for (int i = 0; erroMap1.size() > i; i++) {
				//String startFashionErr = erroMap.get(i).get("startFashionErr");
				msg += erroMap1.get(i).get("errId");
			}
		}

		// changeOfferSpec(document);
		List<Element> order = document.selectNodes("//order/subOrder");
		for (int i = 0; order.size() > i; i++) {
			// 把报文中的OfferSpecs转换为ServicePak和PricePlanPak节点
			List<Map<String, String>> erroMap2 = changeOfferSpec2ServicePakAndPricePlanPak(order.get(i));
			if (erroMap2.size() > 0) {
				for (int j = 0; erroMap2.size() > j; j++) {
					//String startFashionErr = erroMap.get(i).get("startFashionErr");
					msg += erroMap2.get(j).get("errId");
				}
			}

		}

		return msg;
	}

	/**
	 * 预受理提交
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required
	public String savePrepare(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String changeResult = changeRequestXml(document);
			if (!"".equals(changeResult)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, changeResult);
			}
			// 预受理接口目前只有PAD用，订单类型强制为7，便与局方统计。
			document.selectSingleNode("request/order/olTypeCd").setText("7");
			String devNumId = WSUtil.getXmlNodeText(document, "//devNumId");
			JSONObject order = new JSONObject();
			order = orderListFactory.generateOrderList(document);
			logger.debug("购物车JSON，generateOrderList={}", JsonUtil.getJsonString(order));
			String result = soSavePrepare.savePrepare(order);
			if ("1".equals(result)) {
				// 卡预占
				if (StringUtils.isNotBlank(devNumId)) {
					Map resultMap = intfSMO.consoleUimK(devNumId, "0");
					//add by wanghongli 具体化资源预占返回信息
					if (!"1".equals(resultMap.get("result").toString())) {
						return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMap.get("cause").toString());
					}
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS);
			}
		} catch (Exception e) {
			logger.error("savePrepare预受理提交:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 亲情号码查询
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/accNbrType", caption = "号码类型") })
	public String queryFNSInfo(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(document, "//request/accNbrType");
			Map<String, Object> FNSInfo = new HashMap<String, Object>();
			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				if (isPrintLog) {
					System.out
							.println("调用营业接口queryFNSInfo(亲情号码查询)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
			} else if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType) || "4".equals(accNbrType)) {
				long start = System.currentTimeMillis();
				FNSInfo = intfSMO.queryFNSInfo(accNbr, accNbrType);
				if (isPrintLog) {
					System.out.println("queryFNSInfo.intfSMO.queryFNSInfo(亲情号码查询) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}
			} else {
				if (isPrintLog) {
					System.out
							.println("调用营业接口queryFNSInfo(亲情号码查询)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}// 号码未办理亲情类资费返回空节点，不判空
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resultCode", ResultCode.SUCCESS);
			map.put("resultMsg", ResultCode.SUCCESS.getDesc());
			map.put("FNSInfo", FNSInfo);
			if (isPrintLog) {
				System.out.println("调用营业接口queryFNSInfo(亲情号码查询)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("queryFNSInfo", map);
		} catch (Exception e) {
			logger.error("queryFNSInfo亲情号码查询:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口queryFNSInfo(亲情号码查询)执行时间：" + (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 是否已做话费补贴
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/bcd_code", caption = "bcd_code") })
	public String isSubsidy(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String bcdCode = WSUtil.getXmlNodeText(document, "//request/bcd_code");
			// String offer_spec_id = WSUtil.getXmlNodeText(document,
			// "//request/offer_spec_id");
			String resultMsg = srFacade.getMaterialByCode("-1", bcdCode);
			if (resultMsg == null) {
				// 调用异常
				return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR);
			}
			// 解析调用结果
			Document doc = WSUtil.parseXml(resultMsg);
			String materialStatus = WSUtil.getXmlNodeText(doc, "//GoodsDetailList/GoodsDetail/status");
			if (materialStatus == null) {
				// 查询结果为空
				return WSUtil.buildResponse(ResultCode.MATERIAL_CODE_NOT_EXIST);
			}
			// 如果物品状态为可用（A）认为未作话费补贴
			if (WSDomain.MATERIALSTATUS.AVAILABLE.equals(materialStatus)) {
				return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "isSubsidy", WSDomain.IsSubsidy.NO);
			} else {
				return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "isSubsidy", WSDomain.IsSubsidy.YES);
			}

			// Map returnMap = intfSMO.isSubsidy(coupon_ins_number);
			// String status = null;
			// String couponId = null;
			// if (returnMap != null) {
			// status = ((BigDecimal) returnMap.get("STATUS_CD")).toString();
			// couponId = ((BigDecimal) returnMap.get("COUPON_ID")).toString();
			// }
			// if (WSDomain.AUDIT_STATUS_CD_USED.equals(status)) {
			// // 已做话费补贴
			// return WSUtil.buildValidateResponse(ResultCode.SUCCESS,
			// "isSubsidy", WSDomain.IsSubsidy.YES);
			// } else if (null == status) {
			// // 根据coupon_ins_number未查询到相应audit_ticket信息
			// return WSUtil.buildResponse(ResultCode.IS_SUBSIDY_ERROR);
			// } else {
			// // 未作话费补贴 //添加校验物品对应资费方案校验
			// // 判断此物品规格是否包含在 销售品 的物品方案中
			// int exist = intfSMO.judgeCoupon2OfferSpec(offer_spec_id,
			// couponId);
			// if (exist > 0) {
			// return WSUtil.buildValidateResponse(ResultCode.SUCCESS,
			// "isSubsidy", WSDomain.IsSubsidy.NO);
			// } else {
			// return WSUtil.buildResponse(ResultCode.IS_SUBSIDY_ERROR,
			// "该串码与资费信息不一致，请确认");
			// }
			// }
		} catch (Exception e) {
			logger.error("isSubsidy是否已做话费补贴:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 检验宽带捆绑号码
	 * 
	 * @author CHENJUNJIE
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/type", caption = "竣工类型") })
	public String checkBindAccessNumber(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(document, "//request/accessNumber");
			String proSpecId = WSUtil.getXmlNodeText(document, "//request/proSpecId");
			String type = WSUtil.getXmlNodeText(document, "//request/type");
			String partyId = WSUtil.getXmlNodeText(document, "//request/partyId");
			String prodId = WSUtil.getXmlNodeText(document, "//request/prodId");
			String areaCode = WSUtil.getXmlNodeText(document, "//request/areaCode");
			Long partyIdLong = Long.valueOf(partyId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("accessNumber", accessNumber);
			if (StringUtils.isNotBlank(proSpecId)) {
				params.put("proSpecId", proSpecId);
			}
			if (StringUtils.isNotBlank(prodId)) {
				params.put("prodId", prodId);
			}
			if (StringUtils.isNotBlank(areaCode)) {
				params.put("areaCode", areaCode);
			}
			Map<String, Object> result = new HashMap<String, Object>();
			if (WSDomain.CompletedType.COMPLETED.equals(type)) {
				if ((accessNumber == null) && (prodId == null)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "产品ID和主接入号不能同时为空");
				}
				result = offerSMO.queryInUseProdByMap(params);
			} else if (WSDomain.CompletedType.UNCOMPLETED.equals(type)) {
				if (proSpecId == null) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "缺少产品规格ID的入参");
				}
				if (accessNumber == null) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "缺少主接入号的入参");
				}
				result = soQuerySMO.queryUnCompleteProdByMap(params);
			} else {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "竣工类型错误");
			}
			if (result == null) {
				return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "该接入号对应的产品为空");
			}
			Long anotherPartyId = Long.valueOf(result.get("partyId").toString());
			if (partyIdLong.equals(anotherPartyId)) {
				String resultSignSuccess = "<result>" + WSDomain.CHECK_RESULT_YES + "</result>";
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc(), resultSignSuccess);
			} else {
				String resultSignFalse = "<result>" + WSDomain.CHECK_RESULT_NO + "</result>";
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc(), resultSignFalse);
			}
		} catch (Exception e) {
			logger.error("checkBindAccessNumber检验宽带捆绑号码:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 定制包查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required
	public String queryPpInfo(@WebParam(name = "request") String request) {
		try {
			long mstart = System.currentTimeMillis();
			Document document = WSUtil.parseXml(request);
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(document, "//request/accNbrType");
			String offerSpecId = WSUtil.getXmlNodeText(document, "//request/offerSpecId");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			String systemId = WSUtil.getXmlNodeText(document, "//request/systemId"); //用来区分pad
			Map<String, Object> attachOfferInfo = new HashMap<String, Object>();
			if (StringUtils.isBlank(accNbr) && StringUtils.isBlank(offerSpecId) && StringUtils.isBlank(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参为空");
			} else if (WSDomain.KF_CHANNEL.contains(channelId)) {
				// 客服2.0渠道进行单独处理
				Map<String, Object> offerInfo = new HashMap<String, Object>();
				if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
				} else if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
					long start = System.currentTimeMillis();
					// 根据接入号码取得产品信息
					OfferProd offerProd = intfSMO.getProdByAccessNumber(accNbr);
					if (isPrintLog) {
						System.out.println("queryPpInfo.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间："
								+ (System.currentTimeMillis() - start));
					}
					if (offerProd == null) {
						if (isPrintLog) {
							System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
						}
						return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
					}
					start = System.currentTimeMillis();
					// 查询销售品信息
					offerInfo = intfSMO.queryOfferInfoByProdId(offerProd.getProdId(), offerProd.getProdSpecId());
					if (isPrintLog) {
						System.out.println("queryPpInfo.intfSMO.queryOfferInfoByProdId(根据产品id查询销售品信息(客服2.0渠道)) 执行时间："
								+ (System.currentTimeMillis() - start));
					}
					// 对主销售品进行换挡过滤
					List<OfferInfoKF> orderOffers = (List<OfferInfoKF>) offerInfo.get("orderOffers");
					List<OfferSpecShopDto> convertedOfferList = new ArrayList<OfferSpecShopDto>();
					for (OfferInfoKF offer : orderOffers) {
						if ("1".equals(offer.getOfferTypeCd())) {
							// 根据offerSpecId获取三级目录节点
							List<Long> CNIList = intfSMO.queryCategoryNodeId(offer.getOfferSpecId());
							// 根据三级目录节点查出目录下所有销售品
							for (Long categoryNodeId : CNIList) {
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("partyId", offerProd.getPartyId());
								param.put("OfferSpecId", offer.getOfferSpecId());
								param.put("staffId", staffId);
								param.put("channelId", channelId);
								param.put("areaId", areaId);
								param.put("queryByCondDim", "Y");// 默认为Y
								param.put("pubByArea", "N");// 默认为N
								param.put("topCategoryNode", "N");
								param.put("maskByDt", "Y");// 默认为Y
								param.put("Upgrade", true);// 变更权限标识,true为不能变更
								param.put("pageSize", 999);// 分页标识，默认一页10
								// param.put("curPage", curPage);//当前页
								// 加入员工信息
								LoginedStaffInfo loginedStaffInfo = new LoginedStaffInfo();
								loginedStaffInfo.setChannelId(Integer.valueOf(channelId));
								loginedStaffInfo.setStaffId(Long.valueOf(staffId));
								param.put("staffInfo", loginedStaffInfo);
								param.put("categoryNodeId", categoryNodeId.toString());// 查目录,查顶层目录传-1
								// param.put("categoryId", "100");// 不为空
								// 查顶层目录传100
								start = System.currentTimeMillis();
								Map<String, Object> result = offerSpecSMO.getDirOrOfferList(param);
								if (isPrintLog) {
									System.out
											.println("queryPpInfo.offerSpecSMO.getDirOrOfferList(取得目录节点或者销售品列表,销售品列表分页展示） 执行时间："
													+ (System.currentTimeMillis() - start));
								}
								//								logger
								//										.error("queryPpInfo.offerSpecSMO.getDirOrOfferList(取得目录节点或者销售品列表,销售品列表分页展示） 执行时间："
								//												+ (System.currentTimeMillis() - start));
								if (result == null) {
									continue;
								}
								if (result.containsKey("offerSpecList")) {
									List<OfferSpecShopDto> convertedOffers = (List<OfferSpecShopDto>) result
											.get("offerSpecList");
									convertedOfferList.addAll(convertedOffers);
								}
							}
						}
					}
					List<OfferInfoKF> optionOfferSpecs = (List<OfferInfoKF>) offerInfo.get("optionOfferSpecs");
					List<OfferInfoKF> mainOffers = new ArrayList<OfferInfoKF>();
					// 查询出的所有主销售品
					for (OfferInfoKF offerKF : optionOfferSpecs) {
						if ("1".equals(offerKF.getOfferTypeCd())) {
							mainOffers.add(offerKF);
						}
					}
					// 转换对象
					List<OfferInfoKF> convertedOffers = new ArrayList<OfferInfoKF>();
					for (OfferSpecShopDto offerSpecShopDto : convertedOfferList) {
						OfferInfoKF offer = new OfferInfoKF();
						offer.setOfferSpecId(offerSpecShopDto.getOfferSpecId());
						convertedOffers.add(offer);
					}
					// mainOffers:A ,convertedOffers：B, 做 A-B
					Set<OfferInfoKF> cloneSet = new HashSet<OfferInfoKF>((List<OfferInfoKF>) BeanUtil
							.cloneBean(mainOffers));
					Set<OfferInfoKF> limitSet = new HashSet<OfferInfoKF>((List<OfferInfoKF>) BeanUtil
							.cloneBean(convertedOffers));
					cloneSet.removeAll(limitSet);
					// 将set集合(需要移除的销售品)转为list
					List<OfferInfoKF> removedOffers = new ArrayList<OfferInfoKF>();
					removedOffers.addAll(cloneSet);
					optionOfferSpecs.removeAll(removedOffers);

				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
				}
				if (offerInfo.isEmpty()) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "定制包查询结果为空！");
				}
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("offerInfo", offerInfo);
				return mapEngine.transform("queryPpInfo_kf", root);
			} else if (StringUtils.isNotBlank(offerSpecId)) {
				long start = System.currentTimeMillis();
				// 新装场景
				List<OfferRolesDto> list = offerSMO.queryOfferRoles(Long.valueOf(offerSpecId));
				if (isPrintLog) {
					System.out.println("queryPpInfo.offerSMO.queryOfferRoles(查销售品成员角色信息) 执行时间："
							+ (System.currentTimeMillis() - start));
				}
				if (CollectionUtils.isEmpty(list)) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.OFFERLIST_NOT_EXIST);
				}
				for (OfferRolesDto offerRolesDto : list) {
					if (offerRolesDto.getObjType() == 2 && offerRolesDto.getObjId().equals(Long.valueOf(prodSpecId))) {
						attachOfferInfo = queryAttachOfferInfo(offerRolesDto.getObjId(), null, areaId, staffId,
								channelId, null, Long.valueOf(offerSpecId), systemId);
						break;
					}
				}
			} else {
				if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
				} else if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
					long start = System.currentTimeMillis();
					// 根据接入号码取得产品信息
					OfferProd offerProd = intfSMO.getProdByAccessNumber(accNbr);
					if (isPrintLog) {
						System.out.println("queryPpInfo.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间："
								+ (System.currentTimeMillis() - start));
					}
					if (offerProd == null) {
						if (isPrintLog) {
							System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
						}
						return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
					}
					start = System.currentTimeMillis();
					attachOfferInfo = queryAttachOfferInfo(Long.valueOf(offerProd.getProdSpecId()), offerProd
							.getProdId(), areaId, staffId, channelId, offerProd.getPartyId(), null, systemId);
					if (isPrintLog) {
						System.out.println("queryPpInfo.queryAttachOfferInfo 执行时间："
								+ (System.currentTimeMillis() - start));
					}

				} else {
					if (isPrintLog) {
						System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
					}
					return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
				}
			}
			if (attachOfferInfo.isEmpty()) {
				if (isPrintLog) {
					System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
				}
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "定制包查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("attachOfferInfo", attachOfferInfo);
			if (isPrintLog) {
				System.out.println("调用营业接口queryPpInfo执行时间：" + (System.currentTimeMillis() - mstart));
			}
			return mapEngine.transform("queryPpInfo", root);
		} catch (Exception e) {
			logger.error("queryPpInfo定制包查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	@WebMethod(exclude = true)
	private Map<String, Object> queryAttachOfferInfo(Long prodSpecId, Long prodId, String areaId, String staffId,
			String channelId, Long partyId, Long offerSpecId, String systemId) {
		long start = System.currentTimeMillis();
		// 根据产品号码查询附属销售品（可订购）
		List<AttachOfferSpecDto> attachOfferSpec = offerSpecFacade.queryAttachOfferSpecBySpec(prodSpecId, prodId,
				areaId, staffId, channelId, partyId, offerSpecId);
		if (isPrintLog) {
			System.out
					.println("queryAttachOfferInfo.offerSpecFacade.queryAttachOfferSpecBySpec(根据产品规格查询对应的附属销售品) 执行时间："
							+ (System.currentTimeMillis() - start));
		}
		List<AttachOfferDto> attachOffers = new ArrayList<AttachOfferDto>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (prodId != null) {
			start = System.currentTimeMillis();
			// 根据产品号码查询附属销售品（已订购）
			if ("6090010023".equals(systemId)) {
				attachOffers = intfSMO.queryAttachOfferByProdForPad(prodId);
			} else {
				attachOffers = intfSMO.queryAttachOfferByProd(prodId);

			}
			if (isPrintLog) {
				System.out.println("queryAttachOfferInfo.intfSMO.queryAttachOfferByProd(根据产品号码查询附属销售品(已订购)) 执行时间："
						+ (System.currentTimeMillis() - start));
			}
		} else {
			start = System.currentTimeMillis();
			// 通过销售品规格查询连带的附属销售品
			result = intfSMO.queryAttachOfferByProd(offerSpecId, prodSpecId);
			if (isPrintLog) {
				System.out
						.println("queryAttachOfferInfo.intfSMO.queryAttachOfferByProd(通过销售品规格和产品规格查询销售品连带的附属销售品) 执行时间："
								+ (System.currentTimeMillis() - start));
			}
		}
		// 获取销售品参数
		List<Map<String, Object>> attachOfferParamsList = new ArrayList<Map<String, Object>>();
		List<OfferServItemDto> offerServParams = new ArrayList<OfferServItemDto>();
		for (AttachOfferDto attachOffer : attachOffers) {
			if ("SERV".equals(attachOffer.getOfferSpecFlag())) {
				start = System.currentTimeMillis();
				offerServParams = intfSMO.queryOfferServNotInvalid(attachOffer.getOfferId());
				if (isPrintLog) {
					System.out
							.println("queryAttachOfferInfo.intfSMO.queryOfferServNotInvalid(根据销售品实例ID查找非失效状态的服务实例信息) 执行时间："
									+ (System.currentTimeMillis() - start));
				}
			}
			start = System.currentTimeMillis();
			List<OfferParamDto> offerParams = offerSMO.queryOfferParamByIdNotInvalid(attachOffer.getOfferId());
			if (isPrintLog) {
				System.out
						.println("queryAttachOfferInfo.offerSMO.queryOfferParamByIdNotInvalid(根据销售品实例ID查找非失效状态的销售品参数) 执行时间："
								+ (System.currentTimeMillis() - start));
			}
			Map<String, Object> offerParmsMap = new HashMap<String, Object>();
			offerParmsMap.put("offerParams", offerParams);
			offerParmsMap.put("offerServParams", offerServParams);
			offerParmsMap.put("offerSpecFlag", attachOffer.getOfferSpecFlag());
			offerParmsMap.put("offerId", attachOffer.getOfferId());
			attachOfferParamsList.add(offerParmsMap);
		}
		Map<String, Object> attachOfferInfo = new HashMap<String, Object>();
		attachOfferInfo.put("attachOfferSpec", attachOfferSpec);
		attachOfferInfo.put("attachOffers", attachOffers);
		attachOfferInfo.put("attachOffersByofferSpec", result);
		attachOfferInfo.put("attachOfferParamsList", attachOfferParamsList);
		return attachOfferInfo;
	}

	/**
	 * 新验证接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/validateType", caption = "验证类型") })
	public String newValidateService(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String validateType = WSUtil.getXmlNodeText(document, "//request/validateType");
			String accessNumber = WSUtil.getXmlNodeText(document, "//request/validateInfo/accessNumber");
			String custName = WSUtil.getXmlNodeText(document, "//request/validateInfo/custName");
			String IDCardType = WSUtil.getXmlNodeText(document, "//request/validateInfo/IDCardType");
			String IDCard = WSUtil.getXmlNodeText(document, "//request/validateInfo/IDCard");
			Map<String, Object> outIdMsg = null;
			if (WSDomain.VALIDATE_TYPE.equals(validateType)) {
				outIdMsg = intfSMO.newValidateService(accessNumber, custName, IDCardType, IDCard);
			}
			if (outIdMsg == null || outIdMsg.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "验证结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", outIdMsg.get("outId"));
			root.put("resultMsg", outIdMsg.get("outMsg"));
			return mapEngine.transform("newValidateService", root);
		} catch (Exception e) {
			logger.error("newValidateService新验证接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 一卡双芯查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号") })
	public String isYKSXInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			Map<String, String> yksxinfos = intfSMO.isYKSXInfo(accNbr);
			if (yksxinfos == null || yksxinfos.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "一卡双芯查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("accNbr", accNbr);
			root.put("data", yksxinfos);
			return mapEngine.transform("isYKSXInfo", root);
		} catch (Exception e) {
			logger.error("isYKSXInfo一卡双芯查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 国际漫游办理查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "号码") })
	public String checkGlobalroam(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			long start = System.currentTimeMillis();
			// 有接入号取得产品号
			Long prodId = soQuerySMO.queryProdIdByAcessNumber(accNbr);
			if (isPrintLog) {
				System.out.println("checkGlobalroam.soQuerySMO.queryProdIdByAcessNumber 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			if (prodId == null) {
				if (isPrintLog) {
					System.out.println("调用营业接口checkGlobalroam(国际漫游办理查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
							+ "ms");
				}
				return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
			}
			start = System.currentTimeMillis();
			// 由产品号取得销售品规格
			List<OfferDto> offerList = offerSMO.queryOfferByProdId(prodId, true);
			if (isPrintLog) {
				System.out.println("checkGlobalroam.offerSMO.queryOfferByProdId(根据产品ID查询一级销售品的基本信息) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}

			if (CollectionUtils.isEmpty(offerList)) {
				if (isPrintLog) {
					System.out.println("调用营业接口checkGlobalroam(国际漫游办理查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
							+ "ms");
				}
				return WSUtil.buildResponse(ResultCode.OFFERLIST_BY_PRODID_NOT_EXIST);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			for (OfferDto offerDto : offerList) {
				start = System.currentTimeMillis();
				param = intfSMO.checkGlobalroam(prodId, offerDto.getOfferSpecId());
				if (isPrintLog) {
					System.out.println("checkGlobalroam.intfSMO.checkGlobalroam(国际漫游办理查询接口) 执行时间:"
							+ (System.currentTimeMillis() - start));
				}

				if ("0".equals(param.get("outCode"))) {
					break;
				}
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", param.get("outCode"));
			root.put("resultMsg", param.get("outMsg"));
			if (isPrintLog) {
				System.out.println("调用营业接口checkGlobalroam(国际漫游办理查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return mapEngine.transform("checkGlobalroam", root);
		} catch (Exception e) {
			logger.error("checkGlobalroam国际漫游办理查询接口:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口checkGlobalroam(国际漫游办理查询接口)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 订单取消
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单编号") })
	public String cancelOrder(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
			String result = intfSMO.cancelOrder(olId, areaId, channelId, staffId);
			if (StringUtils.isBlank(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Document resDoc = WSUtil.parseXml(result);
			String reStr = WSUtil.getXmlNodeText(resDoc, "//response/result");
			String resultMsg = WSUtil.getXmlNodeText(resDoc, "//response/resultMsg");
			Map<String, Object> cancelOrderMap = new HashMap<String, Object>();
			if (reStr.equals("0")) {
				cancelOrderMap.put("resultCode", ResultCode.SUCCESS);
				cancelOrderMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
				cancelOrderMap.put("data", resultMsg);
			} else {
				cancelOrderMap.put("resultCode", ResultCode.CALL_METHOD_ERROR);
				cancelOrderMap.put("resultMsg", "第三方接口返回信息：{" + result + "}");
			}
			return mapEngine.transform("cancelOrder", cancelOrderMap);
		} catch (Exception e) {
			logger.error("cancelOrder订单取消:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 新增账户
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户ID") })
	public String newAcct(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			JSONObject order = acctOrderListFactory.generateOrderList(doc);
			JSONArray custOrderListArr = order.getJSONObject("orderList").getJSONArray("custOrderList")
					.getJSONObject(0).getJSONArray("busiOrder");
			JSONObject boAccountInfo = custOrderListArr.getJSONObject(0).getJSONObject("data").getJSONArray(
					"boAccountInfos").getJSONObject(0);
			String result = soServiceSMO.soAutoService(order);
			if (StringUtils.isBlank(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			JSONObject returnJsObj = JSONObject.fromObject(result);
			Map<String, Object> newAcctMap = new HashMap<String, Object>();
			newAcctMap.put("resultCode", returnJsObj.getString("resultCode"));
			newAcctMap.put("resultMsg", returnJsObj.getString("resultMsg"));
			newAcctMap.put("acctCd", boAccountInfo.getString("acctCd"));
			return mapEngine.transform("newAcct", newAcctMap);
		} catch (Exception e) {
			logger.error("newAcct新增账户:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 号码筛选
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/accNbr", caption = "接入号"),
			@Node(xpath = "/request/accNbrType", caption = "接入号类型"),
			@Node(xpath = "/request/actionType", caption = "操作类型"),
			@Node(xpath = "/request/filterType", caption = "筛选类型") })
	public String filterNumber(@WebParam(name = "request") String request) {
		try {
			Document document = null;
			String result = "";
			int prod_spec_id = -1;
			Long prod_Id = null;
			Long partyId = null;

			StringBuffer propertys = new StringBuffer();
			document = WSUtil.parseXml(request);
			String serviceid = null;
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String accNbr = document.selectSingleNode("request/accNbr").getText() == null ? "" : document
					.selectSingleNode("request/accNbr").getText();
			String accNbrType = document.selectSingleNode("request/accNbrType").getText() == null ? "" : document
					.selectSingleNode("request/accNbrType").getText();
			// 查询partyId
			Party party = custFacade.getPartyByAccessNumber(accNbr);
			if (null == party) {
				return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST, "根据接入号未查询到客户信息" + accNbr);
			}
			partyId = party.getPartyId();
			// 查询prodId
			OfferProd prod = null;
			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "根据号码查询产品不支持：接入号码类型为合同号");
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				prod_Id = soQuerySMO.queryProdIdByAcessNumber(accNbr);
				prod = offerSMO.queryOfferProdInstDetailById(prod_Id, CommonDomain.QRY_OFFER_PROD_SERV);
				if (null == prod) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
				}
			}
			prod_spec_id = prod.getProdSpecId();
			Integer filterType = new Integer(document.selectSingleNode("request/filterType").getText() == null ? ""
					: document.selectSingleNode("request/filterType").getText());
			String actionType = document.selectSingleNode("request/actionType").getText() == null ? "" : document
					.selectSingleNode("request/actionType").getText();
			switch (filterType.intValue()) {
			case 1:
				// 按区号被叫筛选
				serviceid = WSDomain.ServSpecIdOfFilterNumber.AreaCode;
				propertys.append("<properties>");
				org.dom4j.Node attrTypeNode = document.selectSingleNode("FNinfos/attrType");
				if (attrTypeNode != null) {
					// 310063:按区号被叫筛选名单
					String attrType = document.selectSingleNode("FNinfos/attrType").getText() == null ? "" : document
							.selectSingleNode("FNinfos/attrType").getText();
					propertys.append("<property><id>310063</id><name></name><value>");
					propertys.append(attrType);
					propertys.append("</value></property>");
				}
				org.dom4j.Node zoneNode = document.selectSingleNode("FNinfos/zone");
				if (zoneNode != null) {
					// 310062:区号
					String zone = document.selectSingleNode("FNinfos/zone").getText() == null ? "" : document
							.selectSingleNode("FNinfos/zone").getText();

					propertys.append("<property><id>310062</id><name></name><value>");
					propertys.append(zone);
					propertys.append("</value></property>");

				}
				propertys.append("</properties>");
				break;
			case 2:
				// 按密码被叫筛选
				serviceid = WSDomain.ServSpecIdOfFilterNumber.PassWord;
				propertys.append("<properties>");
				org.dom4j.Node passwordNode = document.selectSingleNode("FNinfos/password");
				if (passwordNode != null) {
					// 310064:密码
					String password = document.selectSingleNode("FNinfos/password").getText() == null ? "" : document
							.selectSingleNode("FNinfos/password").getText();
					propertys.append("<property><id>310064</id><name></name><value>");
					propertys.append(password);
					propertys.append("</value></property>");
				}

				propertys.append("</properties>");
				break;
			case 3:
				// 按时间段被叫筛选
				serviceid = WSDomain.ServSpecIdOfFilterNumber.Time;
				propertys.append("<properties>");
				attrTypeNode = document.selectSingleNode("FNinfos/attrType");
				if (attrTypeNode != null) {
					// 按时间段被叫筛选名单
					String attrType = document.selectSingleNode("FNinfos/attrType").getText() == null ? "" : document
							.selectSingleNode("FNinfos/attrType").getText();
					propertys.append("<property><id>310065</id><name></name><value>");
					propertys.append(attrType);
					propertys.append("</value></property>");

				}
				org.dom4j.Node beginTimeNode = document.selectSingleNode("FNinfos/timeRange/beginTime");
				org.dom4j.Node endTimeNode = document.selectSingleNode("FNinfos/timeRange/endTime");
				if (beginTimeNode != null && endTimeNode != null) {
					// 310066:开始时间
					String beginTime = document.selectSingleNode("FNinfos/timeRange/beginTime").getText() == null ? ""
							: document.selectSingleNode("FNinfos/timeRange/beginTime").getText();
					String endTime = document.selectSingleNode("FNinfos/timeRange/endTime").getText() == null ? ""
							: document.selectSingleNode("FNinfos/timeRange/endTime").getText();

					propertys.append("<property><id>310066</id><name></name><value>");
					propertys.append(beginTime);
					propertys.append("</value></property>");

					// 310067:结束时间
					propertys.append("<property><id>310067</id><name></name><value>");
					propertys.append(endTime);
					propertys.append("</value></property>");
				}
				propertys.append("</properties>");
				break;
			case 4:
				// 按呼入号码被叫筛选
				serviceid = WSDomain.ServSpecIdOfFilterNumber.InNumber;
				propertys.append("<properties>");
				attrTypeNode = document.selectSingleNode("FNinfos/attrType");
				if (attrTypeNode != null) {
					// 310068:按呼入号码被叫筛选名单
					String attrType = document.selectSingleNode("FNinfos/attrType").getText() == null ? "" : document
							.selectSingleNode("FNinfos/attrType").getText();
					propertys.append("<property><id>310068</id><name></name><value>");
					propertys.append(attrType);
					propertys.append("</value></property>");

				}
				org.dom4j.Node numberNode1 = document.selectSingleNode("FNinfos/number1");
				if (numberNode1 != null) {

					// 310069:号码1
					String number1 = document.selectSingleNode("FNinfos/number1").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number1").getText();
					propertys.append("<property><id>310069</id><name></name><value>");
					propertys.append(number1);
					propertys.append("</value></property>");
				}
				org.dom4j.Node numberNode2 = document.selectSingleNode("FNinfos/number2");
				if (numberNode2 != null) {

					// 310070:号码2
					String number2 = document.selectSingleNode("FNinfos/number2").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number2").getText();
					propertys.append("<property><id>310070</id><name></name><value>");
					propertys.append(number2);
					propertys.append("</value></property>");
				}

				org.dom4j.Node numberNode3 = document.selectSingleNode("FNinfos/number3");
				if (numberNode3 != null) {

					// 310071:号码3
					String number3 = document.selectSingleNode("FNinfos/number3").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number3").getText();
					propertys.append("<property><id>310071</id><name></name><value>");
					propertys.append(number3);
					propertys.append("</value></property>");
				}

				org.dom4j.Node numberNode4 = document.selectSingleNode("FNinfos/number4");
				if (numberNode4 != null) {

					// 310072:号码4
					String number4 = document.selectSingleNode("FNinfos/number4").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number4").getText();
					propertys.append("<property><id>310072</id><name></name><value>");
					propertys.append(number4);
					propertys.append("</value></property>");
				}

				org.dom4j.Node numberNode5 = document.selectSingleNode("FNinfos/number5");
				if (numberNode5 != null) {

					// 310073:号码5
					String number5 = document.selectSingleNode("FNinfos/number5").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number5").getText();
					propertys.append("<property><id>310073</id><name></name><value>");
					propertys.append(number5);
					propertys.append("</value></property>");
				}
				propertys.append("</properties>");
				break;
			case 5:
				// 按号码呼出筛选
				serviceid = WSDomain.ServSpecIdOfFilterNumber.OutNumber;
				propertys.append("<properties>");

				numberNode1 = document.selectSingleNode("FNinfos/number1");
				if (numberNode1 != null) {
					// 310069:号码1
					String number1 = document.selectSingleNode("FNinfos/number1").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number1").getText();
					propertys.append("<property><id>310069</id><name></name><value>");
					propertys.append(number1);
					propertys.append("</value></property>");
				}
				numberNode2 = document.selectSingleNode("FNinfos/number2");
				if (numberNode2 != null) {
					String number2 = document.selectSingleNode("FNinfos/number2").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number2").getText();
					propertys.append("<property><id>310070</id><name></name><value>");
					propertys.append(number2);
					propertys.append("</value></property>");
				}
				numberNode3 = document.selectSingleNode("FNinfos/number3");
				if (numberNode3 != null) {
					String number3 = document.selectSingleNode("FNinfos/number3").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number3").getText();
					propertys.append("<property><id>310071</id><name></name><value>");
					propertys.append(number3);
					propertys.append("</value></property>");
				}

				numberNode4 = document.selectSingleNode("FNinfos/number4");
				if (numberNode4 != null) {
					String number4 = document.selectSingleNode("FNinfos/number4").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number4").getText();
					propertys.append("<property><id>310072</id><name></name><value>");
					propertys.append(number4);
					propertys.append("</value></property>");
				}

				numberNode5 = document.selectSingleNode("FNinfos/number5");
				if (numberNode5 != null) {
					String number5 = document.selectSingleNode("FNinfos/number5").getText() == null ? "" : document
							.selectSingleNode("FNinfos/number5").getText();
					propertys.append("<property><id>310073</id><name></name><value>");
					propertys.append(number5);
					propertys.append("</value></property>");
				}

				propertys.append("</properties>");
				break;
			default:
				return WSUtil.buildResponse(ResultCode.FILTER_TYPE_ERROR);
			}

			StringBuilder sb = new StringBuilder();
			sb.append("<request><order><orderTypeId>7</orderTypeId><prodSpecId>");
			sb.append(String.valueOf(prod_spec_id));
			sb.append("</prodSpecId><partyId>");
			sb.append(partyId.toString());
			sb.append("</partyId><prodId>");
			sb.append(prod_Id.toString());
			sb.append("</prodId>");
			sb.append("<offerSpecs><offerSpec><id>");
			sb.append(serviceid);
			sb.append("</id><actionType>");
			sb.append(actionType);
			sb.append("</actionType>");
			sb.append(propertys.toString());
			sb.append("</offerSpec></offerSpecs></order>");
			sb.append("<areaId>");
			sb.append(areaId);
			sb.append("</areaId><channelId>");
			sb.append(channelId);
			sb.append("</channelId><staffId>");
			sb.append(staffId);
			sb.append("</staffId></request>");
			logger.debug("调用businessService入参:{}", sb.toString());

			result = businessService(sb.toString());
			return result;
		} catch (Exception e) {
			logger.error("filterNumber号码筛选:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 无线宽带续费查询(含固网)
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/accNbr", caption = "接入号"),
			@Node(xpath = "/request/accNbrType", caption = "接入号类型") })
	public String queryContinueOrderPPInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(document, "//request/accNbrType");
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				Map<String, Object> continueOrderOffers = intfSMO.queryContinueOrderPPInfoByAccNbr(accNbr);
				if (continueOrderOffers.isEmpty()) {
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "根据号码查询宽带续费信息为空" + accNbr);
				} else if (continueOrderOffers.containsKey("1")) {
					return WSUtil.buildResponse(ResultCode.CONTINUE_ORDER_OFFERSPEC_MSG, "该号码已续费" + accNbr);
				}
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("continueOrderOffers", continueOrderOffers);
				return mapEngine.transform("queryContinueOrderPPInfo", root);
			} else {
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}
		} catch (Exception e) {
			logger.error("queryContinueOrderPPInfo无线宽带续费查询:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 无线宽带续费
	 * 
	 * @param request
	 * @return
	 * @author ZHANGC
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/accNbr", caption = "接入号"),
			@Node(xpath = "/request/accNbrType", caption = "接入号类型"),
			@Node(xpath = "/request/confirmPPInfo/id", caption = "销售品id"),
			@Node(xpath = "/request/confirmPPInfo/actionType", caption = "动作类型"),
			@Node(xpath = "/request/confirmPPInfo/startDt", caption = "生效时间"),
			@Node(xpath = "/request/confirmPPInfo/endDt", caption = "失效时间") })
	public String confirmContinueOrderPPInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(document, "//request/accNbrType");
			String offerSpecId = WSUtil.getXmlNodeText(document, "//request/confirmPPInfo/id");
			String name = WSUtil.getXmlNodeText(document, "//request/confirmPPInfo/name");
			String actionType = WSUtil.getXmlNodeText(document, "//request/confirmPPInfo/actionType");
			String startDt = WSUtil.getXmlNodeText(document, "//request/confirmPPInfo/startDt");
			String endDt = WSUtil.getXmlNodeText(document, "//request/confirmPPInfo/endDt");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String staffId = WSUtil.getXmlNodeText(document, "//request/staffId");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String chargeInfos = "";
			String payInfos = "";
			//费用项
			boolean isHasCharge = WSUtil.isEmptyNode(document, "//request/listChargeInfo");
			boolean isHasPayInfo = WSUtil.isEmptyNode(document, "//request/payInfoList");
			if (!isHasCharge) {
				chargeInfos = WSUtil.getXmlNode(document, "//request/listChargeInfo").asXML();
			}
			if (!isHasPayInfo) {
				payInfos = WSUtil.getXmlNode(document, "//request/payInfoList").asXML();
			}

			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入号码类型为合同号时，查询不支持");
			} else if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
				if (null == prod) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNbr);
				}

				String actionTypeNum = intfSMO.checkContinueOrderPPInfo(accNbr, offerSpecId, actionType);
				if (!actionType.equals(actionTypeNum) && actionType != actionTypeNum) {
					return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "传入的业务动作类型错误:actionType="
							+ actionType);
				}
				// businessService入参
				logger.debug("开始拼接business报文:prod_Id：{}", prod.getProdId());

				List<OfferIntf> orderOfferList = new ArrayList<OfferIntf>();
				orderOfferList = intfSMO.queryOfferInstByProdId(prod.getProdId());

				String checkOfferSpecId = intfSMO.getOfferTypeCdByOfferSpecId(offerSpecId);

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
				int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				calendar.set(Calendar.DATE, endday);

				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
				String endDate = format.format(calendar.getTime());
				calendar.set(Calendar.DATE, endday + 1);

				String startDate = format.format(calendar.getTime());

				if ("1".equals(checkOfferSpecId)) {
					//主销售品退订

					if (orderOfferList != null && orderOfferList.size() > 0) {
						//原始的有主销售品情况
						if (orderOfferList.get(0).getOfferSpecId().toString().equals(offerSpecId)) {
							//和原来主销售品一样情况   已订购 和续费一样的情况下
							StringBuilder sb = new StringBuilder();
							sb.append("<request>");
							sb.append("<order>");
							sb.append("<systemId>6090010028</systemId>");
							sb.append("<orderTypeId>17</orderTypeId>");
							sb.append("<partyId>" + prod.getPartyId().toString() + "</partyId>");
							sb.append("<prodSpecId>" + prod.getProdSpecId().toString() + "</prodSpecId>");
							sb.append("<accessNumber>" + accNbr + "</accessNumber>");
							sb.append("<prodId></prodId>");
							sb.append("<offerSpecs>");
							//sb.append("<offerSpec>");
							//sb.append("<id>"+orderOfferList.get(0).getOfferSpecId()+"</id>");
							//sb.append("<name>"+name+"</name>");
							//sb.append("<actionType>1</actionType>");
							//sb.append("<startFashion>0</startFashion>");
							//sb.append("<endFashion>0</endFashion>");
							//sb.append("<startDt/>");
							//sb.append("<endDt/>");
							//sb.append("</offerSpec>");
							sb.append("<offerSpec>");
							sb.append("<id>" + offerSpecId + "</id>");
							sb.append("<name>" + name + "</name>");
							sb.append("<actionType>" + actionType + "</actionType>");
							sb.append("<startFashion></startFashion>");
							sb.append("<endFashion></endFashion>");
							//sb.append("<startDt>" + startDt + "</startDt>");
							sb.append("<startDt></startDt>");
							sb.append("<endDt>" + endDt + "</endDt>");
							sb.append("</offerSpec>");
							sb.append("</offerSpecs>");
							if (!isHasCharge) {
								sb.append(chargeInfos);
							}
							if (!isHasPayInfo) {
								sb.append(payInfos);
							}
							sb.append("</order>");
							sb.append("<areaId>" + areaId + "</areaId>");
							sb.append("<channelId>" + channelId + "</channelId>");
							sb.append("<staffId>" + staffId + "</staffId>");
							sb.append("</request>");
							return businessService(sb.toString());

						} else {
							//不合原来主销售品一样情况  已订购不和主销售品一样的情况下
							StringBuilder sb = new StringBuilder();
							sb.append("<request>");
							sb.append("<order>");
							sb.append("<systemId></systemId>");
							sb.append("<orderTypeId>17</orderTypeId>");
							sb.append("<partyId>" + prod.getPartyId().toString() + "</partyId>");
							sb.append("<prodSpecId>" + prod.getProdSpecId().toString() + "</prodSpecId>");
							sb.append("<accessNumber>" + accNbr + "</accessNumber>");
							sb.append("<prodId></prodId>");
							sb.append("<offerSpecs>");
							sb.append("<offerSpec>");
							sb.append("<id>" + orderOfferList.get(0).getOfferSpecId() + "</id>");
							sb.append("<name>" + orderOfferList.get(0).getOfferSpecName() + "</name>");
							sb.append("<actionType>1</actionType>");
							sb.append("<startFashion>0</startFashion>");
							sb.append("<endFashion>0</endFashion>");
							sb.append("<startDt/>");
							sb.append("<endDt>" + endDate + "</endDt>");
							sb.append("</offerSpec>");
							sb.append("<offerSpec>");
							sb.append("<id>" + offerSpecId + "</id>");
							sb.append("<name>" + name + "</name>");
							sb.append("<actionType>" + actionType + "</actionType>");
							sb.append("<startFashion></startFashion>");
							sb.append("<endFashion></endFashion>");
							sb.append("<startDt>" + "</startDt>");
							sb.append("<endDt>" + endDt + "</endDt>");
							sb.append("</offerSpec>");
							sb.append("</offerSpecs>");
							if (!isHasCharge) {
								sb.append(chargeInfos);
							}
							if (!isHasPayInfo) {
								sb.append(payInfos);
							}
							sb.append("</order>");
							sb.append("<areaId>" + areaId + "</areaId>");
							sb.append("<channelId>" + channelId + "</channelId>");
							sb.append("<staffId>" + staffId + "</staffId>");
							sb.append("</request>");
							return businessService(sb.toString());
						}
					} else {
						//原始没有主销售品情况   
						StringBuilder sb = new StringBuilder();
						sb.append("<request>");
						sb.append("<order>");
						sb.append("<systemId>6090010028</systemId>");
						sb.append("<orderTypeId>17</orderTypeId>");
						sb.append("<partyId>" + prod.getPartyId().toString() + "</partyId>");
						sb.append("<prodSpecId>" + prod.getProdSpecId().toString() + "</prodSpecId>");
						sb.append("<accessNumber>" + accNbr + "</accessNumber>");
						sb.append("<prodId></prodId>");
						sb.append("<offerSpecs>");
						sb.append("<offerSpec>");
						sb.append("<id>" + offerSpecId + "</id>");
						sb.append("<name>" + name + "</name>");
						sb.append("<actionType>" + actionType + "</actionType>");
						sb.append("<startFashion></startFashion>");
						sb.append("<endFashion></endFashion>");
						sb.append("<startDt>" + startDt + "</startDt>");
						sb.append("<endDt>" + endDt + "</endDt>");
						sb.append("</offerSpec>");
						sb.append("</offerSpecs>");
						if (!isHasCharge) {
							sb.append(chargeInfos);
						}
						if (!isHasPayInfo) {
							sb.append(payInfos);
						}
						sb.append("</order>");
						sb.append("<areaId>" + areaId + "</areaId>");
						sb.append("<channelId>" + channelId + "</channelId>");
						sb.append("<staffId>" + staffId + "</staffId>");
						sb.append("</request>");
						return businessService(sb.toString());
					}

				} else {
					//附属销售品
					StringBuilder sb = new StringBuilder();
					sb.append("<request>");
					sb.append("<order>");
					sb.append("<systemId>6090010028</systemId>");
					sb.append("<orderTypeId>17</orderTypeId>");
					sb.append("<partyId>" + prod.getPartyId().toString() + "</partyId>");
					sb.append("<prodSpecId>" + prod.getProdSpecId().toString() + "</prodSpecId>");
					sb.append("<accessNumber>" + accNbr + "</accessNumber>");
					sb.append("<prodId></prodId>");
					sb.append("<offerSpecs>");
					sb.append("<offerSpec>");
					sb.append("<id>" + offerSpecId + "</id>");
					sb.append("<name>" + name + "</name>");
					sb.append("<actionType>" + actionType + "</actionType>");
					sb.append("<startFashion></startFashion>");
					sb.append("<endFashion></endFashion>");
					sb.append("<startDt>" + "</startDt>");
					sb.append("<endDt>" + endDt + "</endDt>");
					sb.append("</offerSpec>");
					sb.append("</offerSpecs>");
					if (!isHasCharge) {
						sb.append(chargeInfos);
					}
					if (!isHasPayInfo) {
						sb.append(payInfos);
					}
					sb.append("</order>");
					sb.append("<areaId>" + areaId + "</areaId>");
					sb.append("<channelId>" + channelId + "</channelId>");
					sb.append("<staffId>" + staffId + "</staffId>");
					sb.append("</request>");
					return businessService(sb.toString());
				}

			} else {
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}
		} catch (Exception e) {
			logger.error("confirmContinueOrderPPInfo无线宽带续费:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 可以纳入的产品查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户编号"),
			@Node(xpath = "//request/prodSpecId", caption = "产品规格编号") })
	public String queryProdByCompProdSpecId(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(document, "//request/partyId");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("partyId", partyId);
			param.put("prodSpceId", prodSpecId);
			List<ProdByCompProdSpec> prodByCompProdSpecs = intfSMO.queryProdByCompProdSpecId(param);
			if (CollectionUtils.isEmpty(prodByCompProdSpecs)) {
				return WSUtil.buildResponse(ResultCode.COMPROD_NOT_EXIST);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("data", prodByCompProdSpecs);
			return mapEngine.transform("queryProdByCompProdSpecId", root);
		} catch (Exception e) {
			logger.error("queryProdByCompProdSpecId可以纳入的产品查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 可纳入产品的校验接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNum", caption = "成员号码"),
			@Node(xpath = "//request/comProdId", caption = "组合产品编号") })
	public String compProdRule(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNum = WSUtil.getXmlNodeText(document, "//request/accNum");
			String comProdId = WSUtil.getXmlNodeText(document, "//request/comProdId");
			Long prodId = soQuerySMO.queryProdIdByAcessNumber(accNum);
			if (prodId == null) {
				return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据接入号未查询到产品信息" + accNum);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prodId", prodId);
			param.put("compProdId", comProdId);
			Integer flag = offerSMO.judgeProdInCompProd(param);
			if (flag == 0) {
				return WSUtil.buildResponse("0", "该产品不在组合产品中");
			}
			return WSUtil.buildResponse("1", "该产品已在组合产品中");
		} catch (Exception e) {
			logger.error("compProdRule可纳入产品的校验接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * Iccid号反查无线宽带产品号码 单数据卡：用户输入iccid号码，返回产品号 一卡双芯：用户无论输入a卡或b卡的iccid号均返回用户a卡产品号
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/serialNum", caption = "输入的iccid号码或无线宽带语音卡号或数据卡号") })
	public String queryNbrByIccid(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String iccid = WSUtil.getXmlNodeText(document, "//request/serialNum");
			String result = intfSMO.queryNbrByIccid(iccid);
			if (StringUtils.isBlank(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> queryNbrByIccidMap = new HashMap<String, Object>();
			queryNbrByIccidMap.put("resultCode", ResultCode.SUCCESS);
			queryNbrByIccidMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			queryNbrByIccidMap.put("iccid", iccid);
			queryNbrByIccidMap.put("data", result);
			return mapEngine.transform("queryNbrByIccid", queryNbrByIccidMap);
		} catch (Exception e) {
			logger.error("queryNbrByIccid Iccid号反查无线宽带产品号码 :" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *根据号码查询imsi信息
	 * 
	 * @author 刘志
	 *@param params
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码") })
	public String queryImsiInfoByMdn(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			Map<String, Object> data = new HashMap<String, Object>();
			Long prodId = soQuerySMO.queryProdIdByAcessNumber(accNbr);
			Map<String, String> yksxinfos = intfSMO.isYKSXInfo(accNbr);
			if (yksxinfos == null || yksxinfos.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "一卡双芯查询结果为空！");
			}
			if ("1".equals(yksxinfos.get("flag"))) {
				// 不是一卡双芯
				List<String> terminalCodes = (List<String>) intfSMO.queryImsiInfoByMdn(prodId);
				if (CollectionUtils.isEmpty(terminalCodes)) {
					return WSUtil.buildResponse(ResultCode.TERMINALCODE_BY_PRODID_IS_NULL);
				}
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("cardNo", terminalCodes.get(0));
				Map<String, Object> mapIMSInfo = rscServiceSMO.getUIMCardInfo(tempMap);
				if (mapIMSInfo == null || mapIMSInfo.size() == 0) {
					return WSUtil.buildResponse(ResultCode.IMIINFO_IS_NULL);
				}
				Long partyId = intfSMO.getPartyIdByProdId(prodId);
				if (partyId == null) {
					return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
				}
				String partyName = intfSMO.getPartyNameByPartyId(partyId);
				data.put("partyName", partyName);
				data.put("mdn", accNbr);
				data.put("mdnimsi", mapIMSInfo.get("C_IMSI"));
				data.put("mdnuimcode", mapIMSInfo.get("TERMINAL_CODE"));
			} else {
				String voiceAccNbr = (String) yksxinfos.get("voiceAccNbr");
				String wlaneAccNbr = (String) yksxinfos.get("wlaneAccNbr");
				if (accNbr.equals(voiceAccNbr)) {
					Long prodIdVoice = soQuerySMO.queryProdIdByAcessNumber(voiceAccNbr);
					if (prodIdVoice == null) {
						return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据号码未查询到产品信息" + voiceAccNbr);
					}
					List<String> terminalCodesVoice = (List<String>) intfSMO.queryImsiInfoByMdn(prodIdVoice);
					if (CollectionUtils.isEmpty(terminalCodesVoice)) {
						return WSUtil.buildResponse(ResultCode.TERMINALCODE_BY_PRODID_IS_NULL);
					}
					Map<String, Object> param1 = new HashMap<String, Object>();
					param1.put("cardNo", terminalCodesVoice.get(0));
					Map mapIMSInfoVoice = rscServiceSMO.getUIMCardInfo(param1);
					if (mapIMSInfoVoice == null || mapIMSInfoVoice.size() == 0) {
						return WSUtil.buildResponse(ResultCode.IMIINFOVOICE_IS_NULL);
					}
					Long partyId = intfSMO.getPartyIdByProdId(prodId);
					if (partyId == null) {
						return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
					}
					String partyName = intfSMO.getPartyNameByPartyId(partyId);
					data.put("partyName", partyName);
					data.put("mdn", voiceAccNbr);
					data.put("mdnimsi", mapIMSInfoVoice.get("C_IMSI"));
					data.put("mdnuimcode", mapIMSInfoVoice.get("TERMINAL_CODE"));
					data.put("voiceMdn", voiceAccNbr);
					data.put("voiceUimImsi", mapIMSInfoVoice.get("C_IMSI"));
					data.put("voiceUimCode", mapIMSInfoVoice.get("TERMINAL_CODE"));
					Long prodIdWlane = soQuerySMO.queryProdIdByAcessNumber(wlaneAccNbr);
					if (prodIdWlane == null) {
						return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据号码未查询到产品信息" + wlaneAccNbr);
					}
					List<String> terminalCodesWlane = (List<String>) intfSMO.queryImsiInfoByMdn(prodIdWlane);
					if (CollectionUtils.isEmpty(terminalCodesWlane)) {
						return WSUtil.buildResponse(ResultCode.TERMINALCODE_BY_PRODID_IS_NULL);
					}
					Map<String, Object> param2 = new HashMap<String, Object>();
					param2.put("cardNo", terminalCodesWlane.get(0));
					Map mapIMSInfoWlane = rscServiceSMO.getUIMCardInfo(param2);
					if (mapIMSInfoWlane == null || mapIMSInfoWlane.size() == 0) {
						return WSUtil.buildResponse(ResultCode.IMIINFOWLANCE_IS_NULL);
					}
					data.put("wlanMdn", wlaneAccNbr);
					data.put("wlanUimImsi", mapIMSInfoWlane.get("C_IMSI"));
					data.put("wlanUimCode", mapIMSInfoWlane.get("TERMINAL_CODE"));
				} else if (accNbr.equalsIgnoreCase(wlaneAccNbr)) {
					Long prodIdWlane = soQuerySMO.queryProdIdByAcessNumber(wlaneAccNbr);
					if (prodIdWlane == null) {
						return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据号码未查询到产品信息" + wlaneAccNbr);
					}
					List<String> terminalCodesWlane = (List<String>) intfSMO.queryImsiInfoByMdn(prodIdWlane);
					if (CollectionUtils.isEmpty(terminalCodesWlane)) {
						return WSUtil.buildResponse(ResultCode.TERMINALCODE_BY_PRODID_IS_NULL);
					}
					Map<String, Object> param1 = new HashMap<String, Object>();
					param1.put("cardNo", terminalCodesWlane.get(0));
					Map mapIMSInfoWlane = rscServiceSMO.getUIMCardInfo(param1);
					Long partyId = intfSMO.getPartyIdByProdId(prodId);
					if (partyId == null) {
						return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
					}
					String partyName = intfSMO.getPartyNameByPartyId(partyId);
					if (mapIMSInfoWlane == null || mapIMSInfoWlane.size() == 0) {
						return WSUtil.buildResponse(ResultCode.IMIINFOWLANCE_IS_NULL);
					}
					data.put("partyName", partyName);
					data.put("mdn", wlaneAccNbr);
					data.put("mdnimsi", mapIMSInfoWlane.get("C_IMSI"));
					data.put("mdnuimcode", mapIMSInfoWlane.get("TERMINAL_CODE"));
					data.put("wlanMdn", wlaneAccNbr);
					data.put("wlanUimImsi", mapIMSInfoWlane.get("C_IMSI"));
					data.put("wlanUimCode", mapIMSInfoWlane.get("TERMINAL_CODE"));
					Long prodIdVoice = soQuerySMO.queryProdIdByAcessNumber(voiceAccNbr);
					if (prodIdVoice == null) {
						return WSUtil.buildResponse(ResultCode.PRODID_BY_ACCNBR_NOT_EXIST);
					}
					List<String> terminalCodesVoice = (List<String>) intfSMO.queryImsiInfoByMdn(prodIdVoice);
					if (CollectionUtils.isEmpty(terminalCodesVoice)) {
						return WSUtil.buildResponse(ResultCode.TERMINALCODE_BY_PRODID_IS_NULL);
					}
					Map<String, Object> param2 = new HashMap<String, Object>();
					param2.put("cardNo", terminalCodesVoice.get(0));
					Map mapIMSInfoVoice = rscServiceSMO.getUIMCardInfo(param2);
					if (mapIMSInfoVoice == null || mapIMSInfoVoice.size() == 0) {
						return WSUtil.buildResponse(ResultCode.IMIINFOWLANCE_IS_NULL);
					}
					data.put("voiceMdn", voiceAccNbr);
					data.put("voiceUimImsi", mapIMSInfoVoice.get("C_IMSI"));
					data.put("voiceUimCode", mapIMSInfoVoice.get("TERMINAL_CODE"));
				}
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("data", data);
			return mapEngine.transform("queryImsiInfoByMdn", root);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryImsiInfoByMdn根据号码查询imsi信息 :" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 根据imsi查询产品信息
	 * 
	 * @author 刘志
	 *@param params
	 *@return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/cImsi", caption = "imsi号码") })
	public String queryProdinfoByImsi(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String cImsi = WSUtil.getXmlNodeText(document, "//request/cImsi");
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("imsi", cImsi);
			Map mapIMSInfo = rscServiceSMO.getUIMCardInfo(tempMap);
			String terminalCode = (String) mapIMSInfo.get("TERMINAL_CODE");
			if (StringUtils.isEmpty(terminalCode)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "通过imsi号" + cImsi + "查询terminalCode为空");
			}
			String accNbr = intfSMO.queryAccNumByTerminalCode(terminalCode);
			if (StringUtils.isEmpty(accNbr)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "通过terminalCode号" + terminalCode + "查询号码为空");
			}
			Map<String, String> yksxinfos = intfSMO.isYKSXInfo(accNbr);
			if (yksxinfos == null || yksxinfos.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "通过号码" + accNbr + "查询一卡双芯信息为空！");
			}
			Party party = custFacade.getPartyByAccessNumber(accNbr);
			String isFlag = "1";
			Map<String, Object> data = new HashMap<String, Object>();
			if ("1".equals(yksxinfos.get("flag"))) {
				// 不是一卡双芯
				data.put("isFlag", isFlag);
				data.put("partyName", party.getPartyName());
				data.put("mdn", accNbr);
				data.put("mdnimsi", (String) mapIMSInfo.get("C_IMSI"));
				data.put("mdnuimcode", terminalCode);
			} else {
				isFlag = "0";
				String voiceAccNbr = (String) yksxinfos.get("voiceAccNbr");
				String wlaneAccNbr = (String) yksxinfos.get("wlaneAccNbr");
				data.put("isFlag", isFlag);
				data.put("partyName", party.getPartyName());
				data.put("voiceMdn", voiceAccNbr);
				data.put("voiceUimImsi", (String) mapIMSInfo.get("C_IMSI"));
				data.put("voiceUimCode", terminalCode);
				data.put("wlanMdn", wlaneAccNbr);
				data.put("wlanUimImsi", (String) mapIMSInfo.get("C_IMSI"));
				data.put("wlanUimCode", terminalCode);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("data", data);
			return mapEngine.transform("queryProdinfoByImsi", root);
		} catch (Exception e) {
			logger.error("queryProdinfoByImsi根据imsi查询产品信息:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 算费列表
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required
	public String computeChargeInfo(@WebParam(name = "request") String request) {
		try {
			String jiaoyanResult = jiaoyanIntface(request);
			if (jiaoyanResult.startsWith("订单转换失败")) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), jiaoyanResult);
			}
			if (!isMatchSuccess(jiaoyanResult)) {
				String msg = "";
				if (jiaoyanResult.indexOf("</rule>") != -1) {
					Document b = WSUtil.parseXml(jiaoyanResult);
					List<Element> c = b.selectNodes("ruleList/rules/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = jiaoyanResult;
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), msg);
			}
			long olId = getOlId(jiaoyanResult);
			StringBuffer suanfeiRequst = new StringBuffer();
			suanfeiRequst.append("<request>");
			suanfeiRequst.append("<ol_id>" + olId + "</ol_id>");
			suanfeiRequst.append("</request>");
			String suanfeiResult = chanarInfoList(suanfeiRequst.toString());
			intfSMO.cancelOrderInfo(olId);
			return suanfeiResult;
		} catch (Exception e) {
			logger.error("computeChargeInfo算费列表:" + request, e);
			return WSUtil.buildResponse(ResultCode.UNSUCCESS);
		}
	}

	/**
	 * 算费列表
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required
	public String computeChargeInfoSly(@WebParam(name = "request") String request) {
		try {
			String jiaoyanResult = jiaoyanIntfaceSimplify(request);
			logger.error("jiaoyanNewIntface jiaoyanResult==" + jiaoyanResult);
			System.out.println("jiaoyanNewIntface result====" + jiaoyanResult);
			if (jiaoyanResult.startsWith("订单转换失败")) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), jiaoyanResult);
			}
			if (!isMatchSuccess(jiaoyanResult)) {
				String msg = "";
				if (jiaoyanResult.indexOf("</rule>") != -1) {
					Document b = WSUtil.parseXml(jiaoyanResult);
					List<Element> c = b.selectNodes("ruleList/rules/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else {
					msg = jiaoyanResult;
				}
				return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(), msg);
			}
			long olId = getOlId(jiaoyanResult);
			StringBuffer suanfeiRequst = new StringBuffer();
			suanfeiRequst.append("<request>");
			suanfeiRequst.append("<ol_id>" + olId + "</ol_id>");
			suanfeiRequst.append("</request>");
			String suanfeiResult = chanarInfoListSly(suanfeiRequst.toString());
			//intfSMO.cancelOrderInfo(olId);
			return suanfeiResult;
		} catch (Exception e) {
			logger.error("computeNewChargeInfo 算费列表:" + request, e);
			return WSUtil.buildResponse(ResultCode.UNSUCCESS);
		}
	}

	/**
	 * 代理商算费列表
	 * 
	 * @author WANGHONGLI
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required
	public String computeChargeInfoForAgent(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			Element orderNode = (Element) document.selectSingleNode("request/order");
			Element offerSpecs = (Element) orderNode.selectSingleNode("./offerSpecs");

			if (offerSpecs != null) {
				List<Element> offerSpec = offerSpecs.selectNodes("./offerSpec");
				String offerSpecIds = "";
				for (int i = 0; offerSpec.size() > i + 1; i++) {
					String offerSpecId = offerSpec.get(i).selectSingleNode("./id").getText();
					offerSpecIds += offerSpecId + ",";
				}
				offerSpecIds += offerSpec.get(offerSpec.size() - 1).selectSingleNode("./id").getText();
				// 查询需要展示的信息
				Map<String, Object> root = new HashMap<String, Object>();
				List<Map<String, Object>> listInfo = intfSMO.queryChargeInfoBySpec(offerSpecIds);

				/*	for (Map<String, Object> chargeInfo : listInfo) {
						//String boId = ((BigDecimal) chargeInfo.get("BO_ID")).toString();
						//String specId = intfSMO.getOfferOrProdSpecIdByBoId(boId);
						//chargeInfo.put("SPEC_ID", specId);
					}*/
				Element tds = (Element) orderNode.selectSingleNode("./tds");
				if (tds != null) {
					String prodSpecId = WSUtil.getXmlNodeText(document, "request/order/prodSpecId");
					Map<String, Object> tdMap = new HashMap();
					tdMap.put("CHARGE", "0");
					tdMap.put("ACCT_ITEM_TYPE_NAME", "UIM卡费");
					tdMap.put("ACCT_ITEM_TYPE_ID", "90013");
					tdMap.put("APP_CHARGE", "2000");
					tdMap.put("SPEC_ID", prodSpecId);

					listInfo.add(tdMap);
				}
				root.put("resultCode", ResultCode.SUCCESS.getCode());
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("listInfo", listInfo);
				return mapEngine.transform("computeChargeInfo", root);

			}
			String suanfeiResult = "";
			return suanfeiResult;
		} catch (Exception e) {
			logger.error("computeChargeInfo算费列表:" + request, e);
			return WSUtil.buildResponse(ResultCode.UNSUCCESS);
		}
	}

	/**
	 * 订单校验内部
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod(exclude = true)
	private String jiaoyanIntface(String request) {
		JSONObject order = new JSONObject();
		try {
			Document document = WSUtil.parseXml(request);
			if ("1".equals(WSUtil.getXmlNodeText(document, "request/order/orderTypeId"))) {
				//changeRequestXml(document);
				String changeResult = changeRequestXml(document);
				if (!"".equals(changeResult)) {
					return "订单转换失败：" + changeResult;
				}
				logger.debug("销售品转换结果：{}", document.asXML());
				order = orderListFactory.generateOrderList(document);
				logger.debug("购物车JSON,generateOrderList={}", JsonUtil.getJsonString(order));
			} else {
				Element orderNode = (Element) document.selectSingleNode("request/order");
				//changeOfferSpec2ServicePakAndPricePlanPak(orderNode);
				String msg = "";
				List<Map<String, String>> erroMap = changeOfferSpec2ServicePakAndPricePlanPak(orderNode);
				if (erroMap.size() > 0) {
					for (int i = 0; erroMap.size() > i; i++) {
						msg += erroMap.get(i).get("errId");
						if (!"".equals(msg)) {
							return "订单转换失败：" + msg;
						}
					}
				}

				order = businessServiceOrderListFactory.generateOrderList(document);
			}
			String result = soValidateCustOrder.validateCustOrder(order);
			System.out.println("jiaoyanIntface result====" + result);
			return result;
		} catch (Exception e) {
			logger.error("jiaoyanIntface订单校验内部:" + request, e);
			return e.getMessage();
		}
	}

	/**
	 * 新的订单校验内部
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod(exclude = true)
	private String jiaoyanIntfaceSimplify(String request) {
		JSONObject order = new JSONObject();
		try {
			Document document = WSUtil.parseXml(request);
			if ("1".equals(WSUtil.getXmlNodeText(document, "request/order/orderTypeId"))) {
				//changeRequestXml(document);
				String changeResult = changeRequestXml(document);
				if (!"".equals(changeResult)) {
					return "订单转换失败：" + changeResult;
				}
				logger.debug("销售品转换结果：{}", document.asXML());
				order = orderListFactory.generateOrderList(document);
				logger.debug("购物车JSON,generateOrderList={}", JsonUtil.getJsonString(order));
			} else {
				Element orderNode = (Element) document.selectSingleNode("request/order");
				//changeOfferSpec2ServicePakAndPricePlanPak(orderNode);
				String msg = "";
				List<Map<String, String>> erroMap = changeOfferSpec2ServicePakAndPricePlanPak(orderNode);
				if (erroMap.size() > 0) {
					for (int i = 0; erroMap.size() > i; i++) {
						msg += erroMap.get(i).get("errId");
						if (!"".equals(msg)) {
							return "订单转换失败：" + msg;
						}
					}
				}

				order = businessServiceOrderListFactory.generateOrderList(document);
			}
			//return soValidateCustOrder.validateCustOrder(order);
			//改为调用张弛写的接口
			String result = soServiceSMO.soAtuoFlowCardService(order);
			System.out.println("jiaoyanIntfaceSimply result====" + result);
			logger.error("soServiceSMO.soAtuoFlowCardService result==" + result);
			//存ol_id 和json 到 表里 供新的订单提交方法提取
			long olId = getOlId(result);
			Date requestTime = new Date();
			intfSMO.saveRequestInfo(String.valueOf(olId), "CrmWebService", "computeChargeInfoSly", order.toString(),
					requestTime);
			return result;
		} catch (Exception e) {
			logger.error("jiaoyanIntface订单校验内部:" + request, e);
			return e.getMessage();
		}
	}

	/**
	 * 校验是否成功，true表示成功; false表示失败
	 * 
	 * @param str
	 * @return
	 */
	@WebMethod(exclude = true)
	public boolean isMatchSuccess(String str) {
		String regEx = "<result>0000</result>|<result>0</result>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 获取校验回参中OLID
	 * 
	 * @param str
	 * @return
	 * @throws DocumentException 
	 */
	@WebMethod(exclude = true)
	private long getOlId(String request) throws DocumentException {
		Document document = WSUtil.parseXml(request);
		List<Element> listBo = document.selectNodes("ruleList/bos/bo/olId");
		if (listBo.size() > 0) {
			return Long.valueOf(listBo.get(0).getText());
		} else {
			return -1;
		}

	}

	/**
	 * 客户可办理的销售品列表 （取得目录节点或者销售品列表,销售品列表分页展示）
	 * 
	 * @param param
	 *            Map{"categoryNodeId": 330000000,"pageSize":10,"curPage":2}
	 *            取顶级目录节点 需要参数： Map{"topCategoryNode":"Y"}
	 *            当前页curPage,每页显示的记录数pageSize两参数不传时：默认当前页为第一页，每页显示记录数为：20
	 * @return if 有子目录节点 then Map
	 *         :{"nodeFlag":"Y","nodelist":nodelist<categoryNodeDto>} else Map
	 *         :{"nodeFlag":"N","offerSpecList":offerSpecList<offerSpecShopDto>,
	 *         "page":Page }
	 * @author zhangying
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户编码"),
			@Node(xpath = "//request/categoryNodeId", caption = "目录节点"),
			@Node(xpath = "//request/offerSpecId", caption = "销售品规格id") })
	public String queryOptionalOfferList(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
			String categoryNodeId = WSUtil.getXmlNodeText(doc, "//request/categoryNodeId");
			String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("Upgrade", true);// 用于判断是否从升档进入销售品选购
			param.put("OfferSpecId", offerSpecId);
			param.put("partyId", partyId);
			param.put("staffId", staffId);
			param.put("channelId", channelId);
			param.put("areaId", areaId);
			param.put("queryByCondDim", "Y");// 默认为Y
			param.put("pubByArea", "N");// 默认为N
			param.put("topCategoryNode", "N");
			param.put("maskByDt", "Y");// 默认为Y
			param.put("pageSize", 10);// 分页标识，默认一页10
			// param.put("curPage", curPage);//当前页
			// 加入员工信息
			LoginedStaffInfo loginedStaffInfo = new LoginedStaffInfo();
			loginedStaffInfo.setChannelId(Integer.valueOf(channelId));
			loginedStaffInfo.setStaffId(Long.valueOf(staffId));
			param.put("staffInfo", loginedStaffInfo);
			param.put("categoryNodeId", categoryNodeId);// 查子目录,查顶层目录传-1
			// param.put("categoryId", "");// 不为空 查顶层目录传100
			// 递归查询出的销售品
			List<Object> offerList = new ArrayList<Object>();
			// 输出的销售品列表
			List<Object> offers = new ArrayList<Object>();
			Map<String, Object> result = offerSpecSMO.getDirOrOfferList(param);
			Long parentNode = intfSMO.queryIfRootNode(Long.valueOf(categoryNodeId));// 取父节点
			// 如果是一级或三级目录返回错误
			if (result.containsKey("offerSpecList") || parentNode == null) {
				return WSUtil.buildResponse(ResultCode.CATEGROYNODEID_ERROR);
			} else {
				List<CategoryNodeDto> nodeList = (List<CategoryNodeDto>) result.get("nodeList");
				//递归查询所有节点
				getOfferList(nodeList, offerList, param);
				for (int i = 0; i < offerList.size(); i++) {
					Iterator<Entry<String, Object>> itor = ((Map<String, Object>) offerList.get(i)).entrySet()
							.iterator();
					while (itor.hasNext()) {
						Entry<String, Object> e = itor.next();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("nodeId", e.getKey());
						map.put("offerList", e.getValue());
						offers.add(map);
					}
				}
			}
			if (CollectionUtils.isEmpty(offers)) {
				return WSUtil.buildResponse(ResultCode.QUERY_RESULT_IS_NULL);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("offers", offers);
			return mapEngine.transform("queryOptionalOfferList", root);
		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 取所有子目录销售品
	 */
	@SuppressWarnings("unchecked")
	@WebMethod(exclude = true)
	private void getOfferList(List<CategoryNodeDto> node, List<Object> list, Map<String, Object> param) {
		for (CategoryNodeDto categoryNodeDto : node) {
			Integer nodeId = categoryNodeDto.getCategoryNodeId();
			// param.put("categoryId", "");// 不为空 查顶层目录传100
			param.put("categoryNodeId", nodeId);
			Map<String, Object> result = offerSpecSMO.getDirOrOfferList(param);
			List<CategoryNodeDto> nodeList = (List<CategoryNodeDto>) result.get("nodeList");
			if (result.containsKey("offerSpecList")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(nodeId.toString(), result.get("offerSpecList") != null ? result.get("offerSpecList") : "");
				list.add(map);
			} else if (result.containsKey("nodeList")) {
				getOfferList(nodeList, list, param);
			}
		}
	}

	/**
	 * 业务返销
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/systemId", caption = "平台编码：systemId") })
	public String cancelOrderList(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			String groupId = WSUtil.getXmlNodeText(doc, "//request/groupId");
			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String openType = WSUtil.getXmlNodeText(doc, "//request/openType");// 2是补换卡返销1普通开户返销
			if (StringUtils.isBlank(olId) && StringUtils.isBlank(groupId)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "购物车号和批次号不能同时为空");
			}
			String interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
			if (StringUtils.isBlank(interfaceId)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "平台编码没有对应平台标识！");
			}
			JSONObject param = new JSONObject();
			param.element("strIds", olId);
			param.element("groupId", groupId);
			param.element("staffId", staffId);
			param.element("channelId", channelId);
			param.element("openType", openType);
			// 2.调用营业方法事件
			JSONObject result = soBatchSMO.cancelOrderList(param);
			logger.debug("业务返销返回结果：{}", result.toString());
			if (StringUtils.isBlank(result.toString())) {
				Map<String, Object> params = new HashMap<String, Object>();
				// 3.根据营业回参判断结果并做返回处理
				String newOlId = intfSMO.getNewOlIdByOlIdForPayIndentId(olId);
				if (StringUtils.isBlank(newOlId)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "未找到返销业务订单编号");
				}
				params.put("resultCode", ResultCode.SUCCESS.getCode());
				params.put("resultMsg", ResultCode.SUCCESS.getDesc());
				params.put("payIndentId", interfaceId + newOlId);
				params.put("pageInfo", intfSMO.getPageInfo(newOlId, "1", "N"));
				return mapEngine.transform("cancelOrderList", params);
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, result.getString("v_resultinfo"));
			}
		} catch (Exception e) {
			logger.error("cancelOrderList业务返销:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	@WebMethod(exclude = true)
	private String chanarInfoList(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			Long ol_id = Long.valueOf(WSUtil.getXmlNodeText(doc, "//request/ol_id"));
			// 调用营业算费的存过
			Map<String, Object> returnMap = intfSMO.computeChargeInfo(ol_id);
			String resultId = returnMap.get("resultId").toString();
			if (!resultId.equals("0")) {
				// 算费失败
				return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, "调用营业算费返回失败：" + returnMap.toString());
			} else {
				// 查询需要展示的信息
				Map<String, Object> root = new HashMap<String, Object>();
				List<Map<String, Object>> listInfo = intfSMO.queryChargeInfo(ol_id);
				for (Map<String, Object> chargeInfo : listInfo) {
					String boId = ((BigDecimal) chargeInfo.get("BO_ID")).toString();
					String specId = intfSMO.getOfferOrProdSpecIdByBoId(boId);
					chargeInfo.put("SPEC_ID", specId);
				}
				root.put("resultCode", ResultCode.SUCCESS.getCode());
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("listInfo", listInfo);
				return mapEngine.transform("computeChargeInfo", root);
			}
		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	@WebMethod(exclude = true)
	private String chanarInfoListSly(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			Long ol_id = Long.valueOf(WSUtil.getXmlNodeText(doc, "//request/ol_id"));
			// 调用营业算费的存过
			Map<String, Object> returnMap = intfSMO.computeChargeInfo(ol_id);
			String resultId = returnMap.get("resultId").toString();
			if (!resultId.equals("0")) {
				// 算费失败
				return WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, "调用营业算费返回失败：" + returnMap.toString());
			} else {
				// 查询需要展示的信息
				Map<String, Object> root = new HashMap<String, Object>();
				List<Map<String, Object>> listInfo = intfSMO.queryChargeInfo(ol_id);
				for (Map<String, Object> chargeInfo : listInfo) {
					String boId = ((BigDecimal) chargeInfo.get("BO_ID")).toString();
					String specId = intfSMO.getOfferOrProdSpecIdByBoId(boId);
					chargeInfo.put("SPEC_ID", specId);
				}
				root.put("resultCode", ResultCode.SUCCESS.getCode());
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("olId", String.valueOf(ol_id));
				root.put("listInfo", listInfo);
				return mapEngine.transform("computeChargeInfoSly", root);
			}
		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 高额监控系统与CRM系统查询接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/acc_nbr", caption = "acc_nbr"),
			@Node(xpath = "//request/flag", caption = "flag") })
	public String highFeeQueryUserInfo(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/acc_nbr");
			String flag = WSUtil.getXmlNodeText(doc, "//request/flag");
			OfferProd prodInfo = new OfferProd();
			Map<String, Object> highFeeInfo = new HashMap<String, Object>();
			// flag 1：号码 2：IMSI(C、G) 3:prod_id
			if ("1".equals(flag)) {
				Long prodId = soQuerySMO.queryProdIdByAcessNumber(accessNumber);
				prodInfo = offerFacade.getProdDetailByProdId(prodId);
			} else if ("2".equals(flag)) {
				// 无论是imsi_c还是imsi_g都按imsi_g处理，获取对应的imsi_c
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("imsi", accessNumber);
				Map mapIMSInfo = rscServiceSMO.getUIMCardInfo(tempMap);
				if (mapIMSInfo != null) {
					String terminalCode = (String) mapIMSInfo.get("TERMINAL_CODE");
					accessNumber = intfSMO.queryAccNumByTerminalCode(terminalCode);
					if (accessNumber != null) {
						//Long prodId = soQuerySMO.queryProdIdByAcessNumber(accessNumber);
						//prodInfo = offerFacade.getProdDetailByProdId(prodId);
						//modify by wanghongli 根据之前的方式查不到prodInfo.getPartyId
						prodInfo = intfSMO.getProdByAccessNumber(accessNumber);
					}
				}
			} else if ("3".equals(flag)) {
				//prodInfo = offerFacade.getProdDetailByProdId(Long.valueOf(accessNumber));
				prodInfo = intfSMO.getProdByAccessNumber(accessNumber);
			}
			if (prodInfo == null) {
				return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST, "根据号码未查询到产品信息" + accessNumber);
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("prod_id", prodInfo.getProdId());
				param.put("owner_id", prodInfo.getPartyId());
				highFeeInfo = intfSMO.getHighFeeInfo(param);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("prodInfo", prodInfo);
			root.put("highFeeInfo", highFeeInfo);
			return mapEngine.transform("highFeeQueryUserInfo", root);
		} catch (Exception e) {
			logger.error("highFeeQueryUserInfo高额监控系统与CRM系统查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 判断是否重保客户
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/partyId", caption = "客户编码") })
	public String ifImportantPartyByPartyId(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyId = WSUtil.getXmlNodeText(doc, "//request/partyId");
			int count = intfSMO.ifImportantPartyByPartyId(Long.valueOf(partyId));
			if (count > 0) {
				return WSUtil.buildResponse(ResultCode.SUCCESS, "True");
			}
			return WSUtil.buildResponse(ResultCode.NOTIMPORTANTPARTY_BY_PARTYID);
		} catch (Exception e) {
			logger.error("ifImportantPartyByPartyId判断是否重保客户:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 彩铃开户
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public CreateCRAccountREP createCRAccount(@WebParam(name = "request") CreateCRAccountREQ request) {
		CreateCRAccountREP res = new CreateCRAccountREP();
		try {
			logger.debug("彩铃开户入参：{}", JSONObject.fromObject(request).toString());
			OfferProd offerProd = intfSMO.getProdByAccessNumber(request.getUserID());
			if (offerProd == null) {
				res.setResultCode("000002");
				res.setResultMessage("产品规格未找到!");
				return res;
			}
			if (WSDomain.BLACK_PROD_STATUS.contains(offerProd.getStatusCd())) {
				res.setResultCode("000003");
				res.setResultMessage("产品状态不符合开通条件！");
				return res;
			}
			StringBuilder orderInfo = new StringBuilder();
			orderInfo
					.append("<request><areaId>11000</areaId><channelId>-10040</channelId><staffCode>-10040</staffCode><staffId>-10040</staffId><areaCode>010</areaCode><order><systemId>6090010005</systemId><orderTypeId>17</orderTypeId>");
			orderInfo.append("<prodSpecId>" + offerProd.getProdSpecId() + "</prodSpecId>");
			orderInfo.append("<accessNumber>" + request.getUserID() + "</accessNumber>");
			orderInfo.append("<prodId>" + offerProd.getProdId() + "</prodId>");
			orderInfo
					.append("<offerSpecs><offerSpec><id>992018139</id><actionType>0</actionType><startFashion>0</startFashion></offerSpec></offerSpecs>");
			orderInfo.append("</order></request>");
			String result = businessService(orderInfo.toString());
			logger.debug("营业受理反馈结果:{}", result);
			Document doc = WSUtil.parseXml(result);
			String resultCode = WSUtil.getXmlNodeText(doc, "//resultCode");
			String resultMsg = WSUtil.getXmlNodeText(doc, "//resultMsg");
			if ("0".equals(resultCode)) {
				resultCode = "000001";
				resultMsg = "成功";
			} else if ("3".equals(resultCode)) {
				resultCode = "301000";
				resultMsg = "用户已开通彩铃";
			} else if ("5".equals(resultCode)) {
				resultCode = "200007";
				resultMsg = "访问序列号不对称";
			} else {
				resultCode = "300003";
			}
			res.setResultMessage(resultMsg);
			res.setResultCode(resultCode);
			return res;
		} catch (Exception e) {
			logger.error("createCRAccount彩铃开户:" + request, e);
			res.setResultCode("300003");
			res.setResultMessage("异常信息" + e.getMessage().substring(0, 300));
			return res;
		}
	}

	/**
	 * 彩铃销户
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public DelCRAccountREP delCRAccount(@WebParam(name = "request") DelCRAccountREQ request) {
		DelCRAccountREP res = new DelCRAccountREP();
		try {
			logger.debug("彩铃销户入参：{}", JSONObject.fromObject(request).toString());
			OfferProd offerProd = intfSMO.getProdByAccessNumber(request.getUserID());
			if (offerProd == null) {
				res.setResultCode("000002");
				res.setResultMessage("产品规格未找到!");
				return res;
			}
			if (WSDomain.BLACK_PROD_STATUS.contains(offerProd.getStatusCd())) {
				res.setResultCode("000003");
				res.setResultMessage("产品状态不符合开通条件！,可能目前处于拆机、停机等状态");
				return res;
			}
			StringBuilder orderInfo = new StringBuilder();
			orderInfo
					.append("<request><areaId>11000</areaId><channelId>-10040</channelId><staffCode>-10040</staffCode><staffId>-10040</staffId><areaCode>010</areaCode><order><systemId>6090010005</systemId><orderTypeId>17</orderTypeId>");
			orderInfo.append("<prodSpecId>" + offerProd.getProdSpecId() + "</prodSpecId>");
			orderInfo.append("<accessNumber>" + request.getUserID() + "</accessNumber>");
			orderInfo.append("<prodId>" + offerProd.getProdId() + "</prodId>");
			orderInfo
					.append("<offerSpecs><offerSpec><id>992018139</id><actionType>1</actionType><endFashion>0</endFashion></offerSpec></offerSpecs>");
			orderInfo.append("</order></request>");
			String result = businessService(orderInfo.toString());
			logger.debug("营业受理反馈结果:{}", result);
			Document doc = WSUtil.parseXml(result);
			String resultCode = WSUtil.getXmlNodeText(doc, "//resultCode");
			String resultMsg = WSUtil.getXmlNodeText(doc, "//resultMsg");
			if ("0".equals(resultCode)) {
				resultCode = "000001";
				resultMsg = "成功";
			} else if ("5".equals(resultCode)) {
				resultCode = "200007";
				resultMsg = "访问序列号不对称";
			} else if ("4".equals(resultCode)) {
				resultCode = "301001";
				resultMsg = "用户未开通彩铃";
			} else {
				resultCode = "300003";
			}
			res.setResultMessage(resultMsg);
			res.setResultCode(resultCode);
			return res;
		} catch (Exception e) {
			logger.error("delCRAccount彩铃销户:" + request, e);
			res.setResultCode("300003");
			res.setResultMessage("异常信息" + e.getMessage().substring(0, 300));
			return res;
		}
	}

	/**
	 * 证件号码查询接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/AccountID", caption = "接入号码") })
	public String QueryCardinfoByAcct(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/AccountID");
			List<String> cardInfos = intfSMO.queryCardinfoByAcct(accessNumber);
			String cardInfo = "";
			if (CollectionUtils.isNotEmpty(cardInfos)) {
				for (String card : cardInfos) {
					cardInfo += card + ",";
				}
				cardInfo = cardInfo.substring(0, cardInfo.length() - 1);
			}
			if (StringUtils.isBlank(cardInfo)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "根据接入号码" + accessNumber + "查询结果为空");
			}
			return WSUtil.buildResponse(ResultCode.SUCCESS, cardInfo);
		} catch (Exception e) {
			logger.error("QueryCardinfoByAcct证件号码查询接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 重打回执
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单编号") })
	public String reprintReceipt(@WebParam(name = "request") String request) {
		try {

			System.out.println("调用营业接口reprintReceipt入参：" + request);
			long mstart = System.currentTimeMillis();
			Document doc = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
			String value = "";
			Map<String, Object> response = new HashMap<String, Object>();
			//增加ol_nbr取值 然后转换为ol_id
			String olNbr = WSUtil.getXmlNodeText(doc, "//request/olNbr");
			if (olNbr != null) {
				olId = olNbr;
				logger.error("reprintReceipt olId==" + olId);
			}
			String ifAgreementStr = WSUtil.getXmlNodeText(doc, "//request/ifAgreementStr");
			if (StringUtils.isBlank(ifAgreementStr)) {
				ifAgreementStr = "N";
			}
			long start = System.currentTimeMillis();
			
			//3G/4G需求
			//张喆新需求
			List<Map<String, Object>> olidList= intfSMO.getOlidByg(olId);
			if(olidList.size()>0){
				//走到这个分支说明入参是 集团ol_id
//20160927				value = olidList.get(0).get("VALUE").toString();
				
				olId = intfSMO.queryProvenceIdByGroupNum(olId);
				if(olId.equals("")){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "未下校验单");
				}
				//olId =  olidList.get(0).get("OL_ID").toString();
				List<Map<String, Object>> valueList = intfSMO.getValueByolId(olId);
				if(valueList.size()>0||valueList != null){
					value = valueList.get(0).get("VALUE").toString();
				}
			}else{
				//走到这个分支说明入参是 省内ol_id
				//查201638有没有
				List<Map<String, Object>> valueList = intfSMO.getValueByolId(olId);
				if (valueList != null&&valueList.size()>0){
					//走进这个分支说明入参是省内OLID 但是仍然是4G订单 返回201638的属性值
					value = valueList.get(0).get("VALUE").toString();
				}else{
					//走进这个分支说明入参是省内OLID 是3G订单 返回省内OLNBR
				List<Map<String, Object>> OlnbrList = intfSMO.getOlnbrByg(olId);
				if(OlnbrList.size() > 0){
					value = OlnbrList.get(0).get("OL_NBR").toString();
				}
				}
			}
//			List<Map<String, Object>> valueList = intfSMO.getValueByolId(olId);
//			if(valueList.size()>0||valueList != null){
//				value = valueList.get(0).get("VALUE").toString();
//			}else{
//				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olid没有查到匹配的value数据错误。");
//			}
			
			String pageInfo = intfSMO.getPageInfo(olId, "2", ifAgreementStr);
			if (isPrintLog) {
				System.out.println("当前线程(id:" + Thread.currentThread().getId() + " name:"
						+ Thread.currentThread().getName() + ")reprintReceipt.intfSMO.getPageInfo 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
//			if(value == "" || value.equals("")|| value == null){
//				
//			}
			
			response.put("resultCode", ResultCode.SUCCESS.getCode());
			response.put("resultMsg", ResultCode.SUCCESS.getDesc());
			response.put("pageInfo", pageInfo);
			response.put("value", value);
			if (isPrintLog) {
				System.out.println("当前线程(id:" + Thread.currentThread().getId() + " name:"
						+ Thread.currentThread().getName() + ")调用营业接口reprintReceipt执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("reprintReceipt", response);
		} catch (Exception e) {
			logger.error("reprintReceipt重打回执:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}

	}

	/**
	 * 
	 * 库存量统计接口
	 * @param request
	 * @return
	 * @author Helen 
	 */

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/storeId", caption = "库存Id"),
			@Node(xpath = "//request/materialId", caption = "物品id") })
	public String queryInventoryStatistics(@WebParam(name = "request") String request) {

		try {
			Document doc = WSUtil.parseXml(request);
			//获取仓库id 多条
			String storeId = WSUtil.getXmlNodeText(doc, "//request/storeId");
			//获取物品id 多条
			String materialId = WSUtil.getXmlNodeText(doc, "//request/materialId");
			//获取区域id
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			//获取渠道
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			//员工
			String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");

			InventoryStatisticsEntityInputBean aa = new InventoryStatisticsEntityInputBean();

			//拼装入参
			String[] storeIds = storeId.split(",");
			String[] materialIds = materialId.split(",");

			List<String> storeIdList = new ArrayList<String>();
			List<String> materialIdList = new ArrayList<String>();

			for (String store : storeIds) {
				storeIdList.add(store);
			}

			for (String material : materialIds) {
				materialIdList.add(material);
			}

			aa.setStoreIds(storeIdList);
			aa.setMaterialIds(materialIdList);
			//执行查询 
			List<InventoryStatisticsEntity> returnEnetity = intfSMO.getInventoryStatistics(aa);

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("resultCode", ResultCode.SUCCESS.getCode());
			response.put("resultMsg", ResultCode.SUCCESS.getDesc());
			response.put("statistics", returnEnetity);
			return mapEngine.transform("queryInventoryStatistics", response);

		} catch (Exception e) {
			logger.error("queryInventoryStatistics库存量统计接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 
	 * 获取bcd串码接口
	 * @param request
	 * @return
	 * @author Helen 
	 */

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/storeId", caption = "库存Id"),
			@Node(xpath = "//request/materialId", caption = "物品id"),
			@Node(xpath = "//request/count", caption = "获取bcd数量") })
	public String queryBcdCode(@WebParam(name = "request") String request) {

		try {
			Document doc = WSUtil.parseXml(request);

			//仓库 id  多条
			String storeId = WSUtil.getXmlNodeText(doc, "//request/storeId");
			//物品id 单条
			String materialId = WSUtil.getXmlNodeText(doc, "//request/materialId");
			//获取bcd数量 
			String count = WSUtil.getXmlNodeText(doc, "//request/count");
			//获取区域id
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			//获取渠道
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			//员工
			String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");

			BcdCodeEntityInputBean input = new BcdCodeEntityInputBean();

			//拼装入参
			String[] storeIds = storeId.split(",");

			List<String> storeIdList = new ArrayList<String>();

			for (String store : storeIds) {
				storeIdList.add(store);
			}
			input.setStoreIds(storeIdList);
			input.setMaterialId(materialId);
			input.setCount((Integer.valueOf(count) + 1) + "");
			List<BcdCodeEntity> returnEnetity = null;
			//执行查询 
			try {
				 returnEnetity = intfSMO.getBcdCode(input);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("resultCode", ResultCode.SUCCESS.getCode());
			response.put("resultMsg", ResultCode.SUCCESS.getDesc());
			response.put("bcdCodes", returnEnetity);
			return mapEngine.transform("queryBcdCode", response);

		} catch (Exception e) {
			logger.error("queryBcdCode获取bcd串码接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 
	 * 银行冻结接口协议
	 * @param request
	 * @return
	 * @author Helen 
	 */

	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/actionType", caption = "动作类型"),
			//			@Node(xpath = "//request/nodeInfo/partyName", caption = "客户名称"),
			//			@Node(xpath = "//request/nodeInfo/identifyType", caption = "证件类型"),
			//			@Node(xpath = "//request/nodeInfo/identifyNumber", caption = "证件号码"),
			@Node(xpath = "//request/nodeInfo/freezeNo", caption = "冻结编号"),
			//			@Node(xpath = "//request/nodeInfo/freezeAcctNo", caption = "冻结账号"),
			////			@Node(xpath = "//request/nodeInfo/freezeSubAcctNo", caption = "冻结子账号"),
			//			@Node(xpath = "//request/nodeInfo/freezeMoney", caption = "冻结金额"),
			//			@Node(xpath = "//request/nodeInfo/freezeDate", caption = "冻结日期"),
			//			@Node(xpath = "//request/nodeInfo/unfreezeDate", caption = "解冻日期"),
			@Node(xpath = "//request/nodeInfo/serialNumber", caption = "流水号"),
			////			@Node(xpath = "//request/nodeInfo/preAccessNumber", caption = "预留手机号"),
			@Node(xpath = "//request/bankCode", caption = "银行编码"),
			@Node(xpath = "//request/bankName", caption = "银行名称") })
	public String bankFreeze(@WebParam(name = "request") String request) {

		//根据冻结账号进行
		try {
			Document doc = WSUtil.parseXml(request);
			//动作类型
			String actionType = WSUtil.getXmlNodeText(doc, "//request/actionType");
			//银行编码
			String bankCode = WSUtil.getXmlNodeText(doc, "//request/bankCode");
			//银行名称 
			String bankName = WSUtil.getXmlNodeText(doc, "//request/bankName");
			//流水号
			String serialNumber = WSUtil.getXmlNodeText(doc, "//request/nodeInfo/serialNumber");
			//冻结编号
			String freezeNo = WSUtil.getXmlNodeText(doc, "//request/nodeInfo/freezeNo");

			//执行查询  获得表明
			List<BankTableEntity> bankTable = intfSMO.getBankTable(bankCode);
			String bankTableName = bankTable.get(0).getBankTable();

			if ("1".equals(actionType)) {

				//首先判断冻结信息是否存在
				String checkSql = "select count(1) from crm." + bankTableName + " where freeze_no ='" + freezeNo + "'";
				if (intfSMO.checkBankFreeze(checkSql)) {
					Map<String, Object> response = new HashMap<String, Object>();
					response.put("resultCode", "1");
					response.put("resultMsg", "已存在");
					response.put("serialNumber", serialNumber);
					return mapEngine.transform("bank", response);
				}

				//总sql
				String sql = "";
				//列
				String col = "";
				//值
				String value = "";

				//执行查询  获得表明
				//List<BankTableEntity> bankTable = intfSMO.getBankTable(bankCode);
				Iterator it1 = bankTable.iterator();
				while (it1.hasNext()) {
					BankTableEntity banktableentity = (BankTableEntity) it1.next();
					System.out.println("getTableColumn==" + banktableentity.getTableColumn() + " getXmlNode="
							+ banktableentity.getXmlNode() + " getTableColumn=" + banktableentity.getTableColumn());
				}
				if (bankTable == null || bankTable.size() == 0) {
					//进行错误处理  数据库没有配置信息
					return WSUtil.buildResponse(ResultCode.BANK_TABLE_CONFIG, "根据银行编码" + bankCode + "没有查询到银行数据信息");

				}
				//冻结
				List list = doc.selectNodes("/request/nodeInfo");
				Element el = (Element) list.get(0);
				el.getName();
				Iterator it = el.elementIterator();

				while (it.hasNext()) {
					Element el1 = (Element) it.next();
					Iterator itbt = bankTable.iterator();
					while (itbt.hasNext()) {
						BankTableEntity entity = (BankTableEntity) itbt.next();
						if (el1.getName().equals(entity.getXmlNode())) {
							if ("DATE".equals(entity.getDataType())) {
								col += entity.getTableColumn() + ",";
								value += "to_date(" + el1.getText() + ",'yyyymmdd'),";
							} else {
								col += entity.getTableColumn() + ",";

								if ("PARTY_NAME".equals(entity.getTableColumn())
										|| "FREEZE_ACCTNO".equals(entity.getTableColumn())
										|| "FREEZE_NO".equals(entity.getTableColumn())
										|| "FREEZE_MONEY".equals(entity.getTableColumn())
										|| "PARTY_IDENTITYNO".equals(entity.getTableColumn())) {
									value += "'" + el1.getText() + "',";
								} else {
									value += el1.getText() + ",";
								}

							}
						}
					}
				}
				/*		while (it.hasNext()) {
							Element el1 = (Element) it.next();
							System.out.println("el1.getName()=="+el1.getName());
							  entity = (BankTableEntity) itbt.next();
							 while(el1.getName().equals(entity.getXmlNode())){
								// BankTableEntity entity = (BankTableEntity) itbt.next();
								  entity = (BankTableEntity) itbt.next();
								System.out.println("entity.getXmlNode=="+entity.getXmlNode()+"  entity.getTableColumn=="+entity.getTableColumn());
								//if (el1.getName().equals(entity.getXmlNode())) {
									if ("DATE".equals(entity.getDataType())) {
										col += entity.getTableColumn() + ",";
										value += "to_date(" + el1.getText() + ",'yyyyddmm'),";
									} else {
										col += entity.getTableColumn() + ",";
										value += el1.getText() + ",";
									}
									//bankTable.remove(entity);
								//}
							}
						}*/

				col += "FREEZERENT_ID,";
				value += "crm.seq_freezerent.nextval ,";
				col += " bank_type";
				value += " " + bankTable.get(0).getBankType();

				//拼装完sql进行插入操作。
				sql = "insert into crm." + bankTable.get(0).getBankTable() + " (" + col + ") values (" + value + ")";
				System.out.println("insertBankFreeze sql=" + sql);
				intfSMO.insertBankFreeze(sql);

				//0：成功 1.已存在 2：无记录3：其它
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("resultCode", ResultCode.SUCCESS.getCode());
				response.put("resultMsg", ResultCode.SUCCESS.getDesc());
				response.put("serialNumber", serialNumber);
				return mapEngine.transform("bank", response);
			} else if ("2".equals(actionType)) {
				//解冻
				//首先查询解冻是否有记录;
				String checkSql = "select count(1) from crm." + bankTableName + " where freeze_no ='" + freezeNo + "'";
				if (!intfSMO.checkBankFreeze(checkSql)) {
					Map<String, Object> response = new HashMap<String, Object>();
					response.put("resultCode", "2");
					response.put("resultMsg", "无记录");
					response.put("serialNumber", serialNumber);
					return mapEngine.transform("bank", response);
				}

				//查询出来有记录进行解冻
				String updateSql = "update crm." + bankTableName + " set FREEZE_UNFREEZE_FLAG=1  where FREEZE_NO ='"
						+ freezeNo + "'";
				boolean flag = intfSMO.updateBankFreeze(updateSql);
				if (flag == true) {
					Map<String, Object> response = new HashMap<String, Object>();
					response.put("resultCode", "0");
					response.put("resultMsg", "成功");
					response.put("serialNumber", serialNumber);
					return mapEngine.transform("bank", response);
				} else {
					Map<String, Object> response = new HashMap<String, Object>();
					response.put("resultCode", "3");
					response.put("resultMsg", "其他");
					response.put("serialNumber", serialNumber);
					return mapEngine.transform("bank", response);
				}

			} else {
				//动作类型错误
				return WSUtil.buildResponse(ResultCode.BANK_ACTION_TYPE, "根据动作类型" + actionType + "执行错误");
			}

			//			Map<String, Object> response = new HashMap<String, Object>();
			//			response.put("resultCode", ResultCode.SUCCESS.getCode());
			//			response.put("resultMsg", ResultCode.SUCCESS.getDesc());
			////			response.put("bcdCodes", returnEnetity);
			////			return mapEngine.transform("queryBcdCode", response);
			//			return null;
		} catch (Exception e) {
			logger.error("bankFreeze银行冻结解冻失败:" + request, e);
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}

	}

	/**实名制 客户资料上传
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/identityNum", caption = "证件号码"),
			@Node(xpath = "//request/identityType", caption = "证件类型"),
			@Node(xpath = "//request/partyName", caption = "客户名"),
			@Node(xpath = "//request/accessNumber", caption = "接入号"),
			@Node(xpath = "//request/platId", caption = "系统平台"),
			@Node(xpath = "//request/channelId", caption = "渠道ID"),
			@Node(xpath = "//request/staffNumber", caption = "员工编码"), })
	public String uploadRealNameCust(@WebParam(name = "request") String request) {
		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String identityNum = WSUtil.getXmlNodeText(doc, "/request/identityNum");
			String timeFlag = "";
			String gztFlag = "";
			timeFlag = intfSMO.getFlag("timeFlag");
			gztFlag = intfSMO.getFlag("gztFlag");
			String logId = null;
			if ("0".equals(timeFlag)) {
				//获取序列
				logId = intfSMO.getIntfTimeCommonSeq();
				//时间入库 1 
				Date requestTime = new Date();
				intfSMO.saveRequestTime(logId, "uploadRealNameCust", accessNumber, identityNum, requestTime);
			}

			String identityType = WSUtil.getXmlNodeText(doc, "//request/identityType");
			String partyName = WSUtil.getXmlNodeText(doc, "//request/partyName");
			String partyAddress = WSUtil.getXmlNodeText(doc, "//request/partyAddress");
			String platId = WSUtil.getXmlNodeText(doc, "//request/platId");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
			String staffNumber = WSUtil.getXmlNodeText(doc, "//request/staffNumber");
			if ("0".equals(timeFlag)) {
				//时间入库2
				Date time1 = new Date();
				intfSMO.updateRequestTime(logId, time1, null, null, null);
			}
			if ("0".equals(gztFlag)) {
				//判断是否调国政通
				//调国政通接口验证身份证信息
				try {
					Map<String, String> map = spServiceSMO
							.getGZTCheckResult(partyName, identityNum, channelId, staffId);
					String identifyInfo = map == null ? "-1" : map.get("result");
					if (!"0".equals(identifyInfo)) {
						return WSUtil.buildResponse(ResultCode.GZT_QUERY_FAILED);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return WSUtil.buildResponse("S0001", "调国政通返回错误" + e.getMessage());
				}
			}
			if ("0".equals(timeFlag)) {
				//时间入库3
				Date time2 = new Date();
				intfSMO.updateRequestTime(logId, null, time2, null, null);
			}
			//组json报文
			JSONObject custInfoJs = new JSONObject();
			custInfoJs.elementOpt("identityNum", identityNum);
			custInfoJs.elementOpt("identityType", identityType);
			custInfoJs.elementOpt("partyName", partyName);
			custInfoJs.elementOpt("accessNumber", accessNumber);
			custInfoJs.elementOpt("partyAddress", partyAddress);
			custInfoJs.elementOpt("channelId", channelId);
			custInfoJs.elementOpt("staffNumber", staffNumber);
			custInfoJs.elementOpt("platId", platId);

			logger.debug("报文转换为JSON:{}", custInfoJs.toString());
			//时间入库4
			if ("0".equals(timeFlag)) {
				Date time3 = new Date();
				intfSMO.updateRequestTime(logId, null, null, time3, null);
			}

			//1  根据接入号查询所属客户
			//add by helinglong 20141008 pad需要返回partyId字段
			Long partyIdByAccNbr = intfSMO.getPartyIdByAccNbr(accessNumber);
			Long prodidByAccNbr = intfSMO.getProdidByAccNbr(accessNumber);

			// 3.0 调用营业一点受理
			String result = soServiceSMO.uploadRealNameCust(custInfoJs);
			//时间入库5
			if ("0".equals(timeFlag)) {
				Date time4 = new Date();
				intfSMO.updateRequestTime(logId, null, null, null, time4);
			}
			String code = "";
			String msg = "";
			logger.debug("uploadRealNameCust result:{}", result.toString());
			JSONObject resultJson = JSONObject.fromObject(result);
			if ((resultJson.getString("resultCode")).equals("0")) {
				StringBuilder resultsb = new StringBuilder();
				resultsb.append("<response>");
				resultsb.append("<resultCode>%s</resultCode>");
				resultsb.append("<resultMsg>%s</resultMsg>");
				resultsb.append("<partyId>%s</partyId>");
				resultsb.append("<prodId>%s</prodId>");
				resultsb.append("</response>");
				return String.format(resultsb.toString(), "0", "成功", partyIdByAccNbr + "", prodidByAccNbr + "");
			} else {
				code = "1";
				msg = resultJson.get("resultMsg").toString();
				return WSUtil.buildResponse(code, msg);
			}
		} catch (Exception e) {
			logger.error("uploadRealNameCust实名制客户资料上传:" + request, e);
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 过户
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码"),
			@Node(xpath = "/request/custInfo/custName", caption = "客户名称"),
			@Node(xpath = "/request/custInfo/custType", caption = "客户类型"),
			@Node(xpath = "/request/custInfo/cerdAddr", caption = "证件地址"),
			@Node(xpath = "/request/custInfo/cerdType", caption = "证件类型"),
			@Node(xpath = "/request/custInfo/cerdValue", caption = "证件号码") })
	public String transferOwner(@WebParam(name = "request") String request) {
		return transferOwnerCommon(request);
	}

	/**
	 * 过户(证件信息加密传输)
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码"),
			@Node(xpath = "//request/prodId", caption = "用户id"),
			@Node(xpath = "/request/custInfo/custType", caption = "客户类型"),
			@Node(xpath = "/request/custInfo/cerdType", caption = "证件类型"),
			@Node(xpath = "/request/custInfo/pInfo/mac", caption = "mac值"),
			@Node(xpath = "/request/custInfo/pInfo/msg", caption = "加密字符串"),
			@Node(xpath = "/request/custInfo/contactPhone1", caption = "联系电话1"),
			@Node(xpath = "/request/custInfo/custAddr", caption = "收件地址") })
	public String transferOwnerDes(@WebParam(name = "request") String request) {

		String requestNew = "";
		try {
			Document doc = WSUtil.parseXml(request);
			String mac = WSUtil.getXmlNodeText(doc, "//request/custInfo/pInfo/mac");
			//根据mac地址查找加密向量
			String vector = intfSMO.getIDCardEncryptionVector(mac);
			if (null == vector || "".equals(vector)) {
				return WSUtil.buildResponse("-1", "未找到解密密钥！");
			}
			String msg = WSUtil.getXmlNodeText(doc, "//request/custInfo/pInfo/msg");
			String encodeUtf8ByteToString = ThreeDes.decryptMode(vector, msg);

			int cut = request.indexOf("<custInfo>") + "<custInfo>".length();

			String start = request.substring(0, cut);
			String end = request.substring(cut);

			String req = "<request>" + encodeUtf8ByteToString + "</request>";

			Document doc1 = WSUtil.parseXml(req);
			String custName = WSUtil.getXmlNodeText(doc1, "//request/name");
			String cerdAddr = WSUtil.getXmlNodeText(doc1, "//request/address");
			String cerdValue = WSUtil.getXmlNodeText(doc1, "//request/idNum");
			StringBuffer middle = new StringBuffer("");
			middle.append("<custName>").append(custName.trim()).append("</custName>");
			middle.append("<cerdAddr>").append(cerdAddr.trim()).append("</cerdAddr>");
			middle.append("<cerdValue>").append(cerdValue.trim()).append("</cerdValue>");

			requestNew = start + middle + end;

		} catch (DocumentException e) {
			return WSUtil.buildResponse("-1", e.getMessage());
		} catch (Exception e) {
			return WSUtil.buildResponse("-1", e.getMessage());
		}
		return transferOwnerCommon(requestNew);
	}

	private String transferOwnerCommon(String request) {
		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
			String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId");
			String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
			String ifAgreementStr = WSUtil.getXmlNodeText(doc, "//request/ifAgreementStr");
			String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
			String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
			String custType = WSUtil.getXmlNodeText(doc, "//request/custInfo/custType");
			String areaCode = WSUtil.getXmlNodeText(doc, "//request/custInfo/areaCode");
			String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
			String postCard = WSUtil.getXmlNodeText(doc, "//request/custInfo/postCard");
			String cerdType = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdType");
			String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
			String emailAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/emailAddr");
			emailAddr = (emailAddr == null ? "" : emailAddr);
			String custAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/custAddr");
			String linkMan = WSUtil.getXmlNodeText(doc, "//request/custInfo/linkMan");
			linkMan = (linkMan == null ? "" : linkMan);
			String contactPhone2 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone2");
			linkMan = (contactPhone2 == null ? "" : contactPhone2);
			if (StringUtils.isBlank(accessNumber)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR);
			}
			if (StringUtils.isBlank(cerdValue)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR);
			}
			if (StringUtil.isEmpty(contactPhone1))
				contactPhone1 = "";
			String resultCode2 = "0";
			String resultMsg2 = "";
			Map<String, Object> rmap = new HashMap<String, Object>();//结果返回map

			if (null == prodId || "".equals(prodId))
				prodId = intfSMO.getProdidByAccNbr(accessNumber) + "";
			if (StringUtil.isEmpty(prodId) || "null".equals(prodId))
				return WSUtil.buildResponse("1", "根据接入号码：" + accessNumber + "为查询到用户信息");

			int ifpk = intfSMO.getIfpkByProd(prodId);
			if (ifpk != 1) {
				logger.debug("不是批开用户，不允许此操作！");
				return WSUtil.buildResponse("1", "不是批开用户，不允许此操作！");
			}

			//不在同一组织不允许实名
			String checkIsSameOrg = checkIsSameOrg(staffCode, prodId);
			if (!"".equals(checkIsSameOrg)) {
				return WSUtil.buildResponse("1", checkIsSameOrg);
			}

			String partyId = intfSMO.getPartyIdByCard(Integer.valueOf(cerdType), cerdValue);
			if (!StringUtils.isBlank(partyId)) {

				String smsg = "0";//停机服务消息
				String stopSerServiceMsg = stopSerService(accessNumber, channelId, staffId, partyId, Long
						.valueOf(prodId));
				if (!"0".equals(stopSerServiceMsg)) {
					smsg = stopSerServiceMsg;
				}

				Element rootElement = doc.getRootElement();
				rootElement.addElement("partyId").addText(partyId);

				logger.debug("开始组建过户json报文...");
				//ji路日志
				String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();
				intfSMO.saveRequestInfo(logId2, "CrmJson", "transferOwnerCommon", "订购退订附属产品："+request, requestTime2);
				// 2.0XML转换为JSON
				JSONObject orderJson = new JSONObject();
				try {
					orderJson = createTransferOwnerListFactory.generateTransferOwner(doc);
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
				logger.debug("过户报文转换为JSON:{}", orderJson.toString());

				Date date1 = new Date();
				intfSMO.saveResponseInfo(logId2, "CrmJson", "transferOwnerCommon", "订购退订附属产品："+ request, requestTime2, orderJson.toString(), new Date(), "1","0");

				logger.debug("过户自动受理服务开始...");
				String result = soServiceSMO.soAutoService(orderJson);
				logger.debug("过户自动受理服务结束...");
				logger.debug("过户受理返回结果：" + result);

				JSONObject resultJson = JSONObject.fromObject(result);
				if ((resultJson.getString("resultCode")).equals("0")) {
					if (!"0".equals(smsg)) {
						smsg = ";过户成功，停机服务失败！";
					}
					rmap.put("resultCode", resultJson.getString("resultCode"));
					rmap.put("resultMsg", resultJson.getString("resultMsg") + smsg);
					rmap.put("olNbr", intfSMO.getOlNbrByOlId(Long.valueOf(resultJson.getString("olId"))));
					rmap.put("olId", resultJson.getString("olId"));
					rmap.put("pageInfo", intfSMO.getPageInfo(resultJson.getString("olId"), "1", ifAgreementStr));
				} else {
					String resultMsg = resultJson.getString("resultMsg");
					String msg = "";
					String co = ResultCode.CALL_METHOD_ERROR.getCode();
					resultCode2 = "1";
					resultMsg2 = "过户失败，不进行产品属性变更！";
					if (resultMsg.indexOf("</rule>") != -1) {
						String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
						Document b = WSUtil.parseXml(a);
						List<Element> c = b.selectNodes("response/resultMsg/rule");
						for (int i = 0; c.size() > i; i++) {
							String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
							if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
								co = ResultCode.REPEAT_CODE_MSG.getCode();
							}
							String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
							String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
							msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
						}
					} else if (resultMsg.indexOf("</errorMsg>") != -1) {
						msg = resultJson.getString("errorMsg");

					} else {
						msg = "过户接口返回 resultMsg:" + resultMsg;
					}
					if ("0".equals(smsg)) {
						resultMsg2 += ";过户失败,停机服务成功!";
					}
					return WSUtil.buildResponse(co, msg, resultCode2, resultMsg2);
				}

			} else {
				Party p = custFacade.getPartyByAccessNumber(accessNumber);
				if (p == null)
					return WSUtil.buildResponse("1", "根据接入号未查到客户信息！");
				partyId = p.getPartyId().toString();
				//判断一个partyId下面是否只有一个用户
				Long prodidByPartyId = intfSMO.getProdidByAccNbr(accessNumber);
				if (prodidByPartyId == null || !prodidByPartyId.toString().equals(prodId)) {
					return WSUtil.buildResponse("1", "入参提供的接入号和用户id不匹配或者接入号下有多个用户!", "1", "不进行产品属性变更！");
				}

				String smsg = "0";//停机服务消息
				String stopSerServiceMsg = stopSerService(accessNumber, channelId, staffId, partyId, prodidByPartyId);
				if (!"0".equals(stopSerServiceMsg)) {
					smsg = "," + stopSerServiceMsg;
				}

				logger.debug("开始构造客户资料修改信息...");
				StringBuffer kh = new StringBuffer();
				kh.append("<request>");
				kh.append("<custInfo>");
				kh.append("<partyId>").append(partyId).append("</partyId>");
				kh.append("<custName>").append(custName).append("</custName>");
				kh.append("<custType>").append(custType).append("</custType>");
				kh.append("<areaCode>").append(areaCode).append("</areaCode>");
				kh.append("<cerdAddr>").append(cerdAddr).append("</cerdAddr>");
				kh.append("<cerdType>").append(cerdType).append("</cerdType>");
				kh.append("<cerdValue>").append(cerdValue).append("</cerdValue>");
				kh.append("<contactPhone1>").append(contactPhone1).append("</contactPhone1>");
				kh.append("<emailAddr>").append(emailAddr).append("</emailAddr>");
				kh.append("<custAddr>").append(custAddr).append("</custAddr>");
				kh.append("<linkMan>").append(linkMan).append("</linkMan>");
				kh.append("<postCode>").append(postCard).append("</postCode>");
				kh.append("</custInfo>");
				kh.append("<areaId>").append(areaId).append("</areaId>");
				kh.append("<channelId>").append(channelId).append("</channelId>");
				kh.append("<staffCode>").append(staffCode).append("</staffCode>");
				kh.append("<staffId>").append(staffId).append("</staffId>");
				kh.append("</request>");
				logger.debug("开始修改客户信息，入参:" + kh.toString());
				String modifyCust = modifyCustom(kh.toString());
				Document bcc = WSUtil.parseXml(modifyCust);
				String rc = WSUtil.getXmlNodeText(bcc, "/response/resultCode");
				String resultMsg = WSUtil.getXmlNodeText(bcc, "/response/resultMsg");
				String olNbr = WSUtil.getXmlNodeText(bcc, "/response/olNbr");
				String olId = WSUtil.getXmlNodeText(bcc, "/response/olId");

				if (!"0".equals(rc)) {
					logger.debug("修改客户信息失败:" + resultMsg);
					String rmsg2 = "修改客户信息失败，不进行产品属性变更!";
					if ("0".equals(smsg)) {
						rmsg2 += ";修改客户信息失败,停机服务成功!";
					}
					return WSUtil.buildResponse("1", resultMsg, "1", rmsg2);
				} else {
					if (!"0".equals(smsg)) {
						smsg = ";修改客户信息成功，停机服务失败！";
					}
					rmap.put("resultCode", rc);
					rmap.put("resultMsg", resultMsg + smsg);
					rmap.put("olNbr", olNbr);
					rmap.put("olId", olId);
					rmap.put("pageInfo", intfSMO.getPageInfo(olId, "1", ifAgreementStr));
				}

			}

			String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
			if (!StringUtils.isBlank(schoolID)) {
				//产品属性变更开始。。。
				String cpsxBg = cpsxBg(doc);
				Document parseXml = WSUtil.parseXml(cpsxBg);
				String rcd = WSUtil.getXmlNodeText(parseXml, "/response/resultCode");
				String rms = WSUtil.getXmlNodeText(parseXml, "/response/resultMsg");
				String code = ResultCode.CALL_METHOD_ERROR.getCode();
				if (cpsxBg.indexOf("</rule>") != -1) {
					List<Element> c = parseXml.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleCode = WSUtil.getXmlNodeText(c.get(i), "./ruleCode");
						if (WSDomain.REPEAT_CODES.contains(ruleCode)) {
							code = ResultCode.REPEAT_CODE_MSG.getCode();
						}
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						rms = rms + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				}

				if (!"0".equals(rcd)) {
					logger.debug("产品属性变更失败!");
				} else
					logger.debug("产品属性变更成功！");
				resultCode2 = rcd;
				resultMsg2 = rms;
				//产品属性变更结束。。。
				logger.debug("产品属性变更结束。。。");
			} else {
				resultCode2 = "1";
				resultMsg2 = "传入的schoolID为空，不进行产品属性变更！";
			}
			rmap.put("resultCode2", resultCode2);
			rmap.put("resultMsg2", resultMsg2);

			String result = this.mapEngine.transform("transferOwner", rmap);
			logger.debug("transferOwner返回结果--------" + result);
			return result;
		} catch (Exception e) {
			logger.error("uploadRealNameCust实名制客户资料上传:" + request, e);
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 实名操作必须满足在同一组织下
	 * @param staffNumber
	 * @param prodId
	 * @return
	 */
	private String checkIsSameOrg(String staffNumber, String prodId) {

		// 增加工号校验 state
		Map<String, Object> orgIdMap = intfSMO.queryOrgByStaffNumber(staffNumber.toUpperCase());// 根据工号查询组织和员工ID
		if (null != orgIdMap && !orgIdMap.isEmpty()) {
			String orgId = orgIdMap.get("ORG_ID").toString();// 员工组织ID
			// 根据PROD_ID查询用户对应的业务发展人STAFF_ID
			Long prodIdLong = Long.parseLong(prodId);
			List<Long> staffIdList = intfSMO.queryStaffByProdId(prodIdLong);
			boolean isSame = false;// 用来判断组织一致
			if (null != staffIdList && staffIdList.size() > 0) {
				for (Long sta : staffIdList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("staffId", sta);
					// 获取发展人工号相对应的组织
					Map<String, Object> mapResult = intfSMO.findOrgByStaffId(map);
					if (null != mapResult) {
						if (null != mapResult.get("ORGID")) {
							String orgResult = mapResult.get("ORGID").toString();
							// 验证是否是同一组织
							if (orgResult.equals(orgId)) {
								isSame = true;
								break;
							}
						}
					}
				}
			}
			// 验证为上传工号及发展人工号不在同一组织内，则提示
			if (!isSame) {
				// outMap.put("resultCode", "1");
				// outMap.put("resultMsg", resultMsg+"不在同一组织下不允许实名");
				// return outMap;
				return "不在同一组织下不允许实名!";
			} else
				return "";
		} else
			return "根据工号未查询组织和员工信息！";
		// 增加工号校验 end
	}

	/**
	 * 停机服务退订
	 * @param accessNumber
	 * @param channelId
	 * @param staffId
	 * @param partyId
	 * @param prodidByPartyId
	 */
	private String stopSerService(String accessNumber, String channelId, String staffId, String partyId,
			Long prodidByPartyId) {
		try {
			List<Map<String, Object>> sers = intfSMO.getAserByProd(prodidByPartyId);
			if (sers != null && sers.size() > 0) {
				logger.debug("停机服务退订业务开始...");
				Map<String, Object> map = sers.get(0);
				Object servId = map.get("SERVID");
				Object servSpecId = map.get("SERVSPECID");
				Map<String, String> inMap = new HashMap<String, String>();

				inMap.put("olTypeCd", "2");
				inMap.put("staffId", staffId);
				inMap.put("channelId", channelId);
				inMap.put("partyId", partyId);
				inMap.put("staffId", staffId);

				inMap.put("prodSpecId", intfSMO.getOfferSpecidByProdId(prodidByPartyId));
				inMap.put("prodId", String.valueOf(prodidByPartyId));
				inMap.put("accessNumber", accessNumber);
				inMap.put("servId", servId.toString());
				inMap.put("servSpecId", servSpecId.toString());
				inMap.put("state", CommonDomain.DEL);

				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				intfSMO.saveRequestInfo(logId, "CRMWEBSERVICE", "stopSerService", inMap.toString(), requestTime);
				JSONObject stringToJsonObj = createTransferOwnerListFactory.stringToJsonObj(inMap);
				intfSMO.saveResponseInfo(logId, "CRMWEBSERVICE", "stopSerService", inMap.toString(), requestTime, stringToJsonObj.toString(), new Date(),
						"1", "0");
				
				String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();
				intfSMO.saveRequestInfo(logId2, "CrmJson", "stopSerService", stringToJsonObj.toString(), requestTime2);
				String soAutoService = soServiceSMO.soAutoService(stringToJsonObj);
				intfSMO.saveResponseInfo(logId2, "CrmJson", "stopSerService", stringToJsonObj.toString(), requestTime2, stringToJsonObj.toString(), new Date(),
						"1", "0");
				JSONObject resultJson = JSONObject.fromObject(soAutoService);
				if (!"0".equals(resultJson.get("resultCode"))) {
					logger.debug("停机服务退订失败！");
					if (resultJson.containsKey("resultMsg"))
						return resultJson.get("resultMsg") + "";
					return "停机服务退订失败,自动受理接口没返回错误信息！";
				} else {
					logger.debug("停机服务退订成功！");
					return "0";
				}
			} else {
				logger.debug("prodId为：" + prodidByPartyId + " 无停机服务业务，不办理退订。");
				return "prodId为：" + prodidByPartyId + " 无停机服务业务，不办理退订。";
			}
		} catch (Exception e) {
			logger.debug("停机服务退订失败！失败原因：" + e.getMessage());
			return "停机服务退订失败！失败原因：" + e.getMessage();
		}

	}

	/**
	 * 产品属性变更
	 * @param doc 
	 */
	private String cpsxBg(Document doc) {
		logger.debug("开始组建产品属性变更json报文...");
		// 2.0XML转换为JSON
		JSONObject orderJson = new JSONObject();
		try {
			orderJson = createTransferOwnerListFactory.generateCpsxBg(doc);
			logger.debug("产品属性变更自动受理服务开始...");
			logger.debug("产品属性变更json:" + orderJson.toString());
			String result = soServiceSMO.soAutoService(orderJson);
			logger.debug("产品属性变更自动受理服务结束...");
			logger.debug("产品属性变更受理返回结果：" + result);
			JSONObject resultJson = JSONObject.fromObject(result);
			if (!"0".equals(resultJson.get("resultCode"))) {
				logger.debug("产品属性变更失败！");
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, resultJson.getString("resultMsg"));
			}
			return WSUtil.buildResponse(resultJson.getString("resultCode"), resultJson.getString("resultMsg"));
		} catch (Exception e) {
			logger.debug("产品属性变更失败！");
			logger.debug("产品属性变更失败日志：" + e.getMessage());
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 客户资料修改
	 * @param request
	 * @return
	 */
	private String modifyCustom(String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			//国政通校验
			//			String custName = WSUtil.getXmlNodeText(doc, "/request/custInfo/custName");
			//			String cerdValue = WSUtil.getXmlNodeText(doc, "/request/custInfo/cerdValue");
			//			String channelId = WSUtil.getXmlNodeText(doc, "/request/channelId");
			//			String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId");
			//			Map<String, String> map = spServiceSMO.getGZTCheckResult(custName, cerdValue, channelId, staffId);
			//			String identifyInfo = map.get("result");
			//			if (!"0".equals(identifyInfo)) {
			//				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "客户名和证件号码不匹配");
			//			}

			JSONObject custJsonString = modifyCustomOrderListFactory.generateOrderList2(doc);
			logger.debug("----------------修改客户构造json信息：" + custJsonString.toString());
			// 替换ol_id
			logger.debug("------客户资料修改json字符串：" + custJsonString);
			JSONObject idJSON = soSaveSMO.saveOrderList(custJsonString);
			Long orderListId = idJSON.getJSONObject("ORDER_LIST-OL_ID").getLong("-1");
			custJsonString.getJSONObject("orderList").getJSONObject("orderListInfo").element("olId", orderListId);
			Long result = soCommitSMO.updateCustInfo(custJsonString);
			if (result == null) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("resultCode", ResultCode.SUCCESS.getCode());
			param.put("resultMsg", ResultCode.SUCCESS.getDesc());
			param.put("olNbr", intfSMO.getOlNbrByOlId(orderListId));
			param.put("olId", orderListId);
			param.put("pageInfo", intfSMO.getPageInfo(orderListId.toString(), "1", "N"));
			return mapEngine.transform("orderSubmit", param);
		} catch (Exception e) {
			WSUtil.logError("modifyCustom", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 综合信息查询
	 * @param accNbr
	 * @param accNbrType
	 * @param areaCode
	 * @param channelId
	 * @param staffCode
	 * @param queryType
	 * @param queryMode
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号") })
	public String QueryService(@WebParam(name = "request") String request) throws java.rmi.RemoteException {
		Document doc;
		logger.debug("进入方法QueryService。。。。");
		long time = System.currentTimeMillis();
		try {
			logger.debug(request);
			doc = WSUtil.parseXml(request);
			String queryType = WSUtil.getXmlNodeText(doc, "//request/queryType");
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");

			String verifyParams = QueryProductResultUtil.verifyParams(request);
			if (!"0".equals(verifyParams)) {
				logger.debug("入参验证不通过：原因：" + verifyParams);
				return QueryProductResultUtil.builtResponse("1", verifyParams);
			}

			int num = intfSMO.qryUsefulOfferNumByAccnum(accNbr);
			if (num > 1) {
				String errorMsg = "数据错误！接入号：" + accNbr + "对应多个有效状态，请联系管理员！";
				logger.debug(errorMsg);
				return QueryProductResultUtil.builtResponse("1", errorMsg);
			}

			logger.debug("综合信息查询入参：" + request);
			String[] qTy = queryType.split(",");
			Map<String, Object> prodRecords = new HashMap<String, Object>();
			for (String qt : qTy) {
				QueryProductFactory qpf = new QueryProductFactory(Integer.valueOf(qt), request, intfSMO, offerSMO,
						custFacade);
				prodRecords.put(qpf.getQueryPro().getProductName(), qpf.getQueryPro().getProductResult());
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("resultCode", ResultCode.SUCCESS);
			result.put("resultMsg", "成功");
			result.put("prodRecords", prodRecords);
			System.out.println("QueryService 总耗时：" + (System.currentTimeMillis() - time));
			return mapEngine.transform("queryServiceHll", result);
		} catch (Exception e) {
			logger.debug("QueryService抛出异常:" + e.getMessage());
			return QueryProductResultUtil.builtResponse("1", e.getMessage());
		}

	}

	/**
	 * 北京银行传送冻结信息
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/freezeNo", caption = "冻结编号") })
	public String queryFreezeOfBank(String request) {
		StringBuffer resXml = new StringBuffer();
		/*<serialNumber></serialNumber>
		<freezeNo></freezeNo>
		</nodeInfo>
		<systemDate></systemDate>
		<bankCode> bank105</bankCode>  
		<bankName>北京银行</bankName>*/

		/*<request>
		<bankCode>bank001</bankCode>
		<bankName>北京银行</bankName>
		<actionType>1</actionType>
		<nodeInfo>
			<unfreezeDate>20130710</unfreezeDate>
			<freezeNo>004118</freezeNo>
			<freezeMoney>44.44</freezeMoney>
			<freezeSubAcctNo>001</freezeSubAcctNo>
			<identifyType>1</identifyType>
			<freezeDate>20130709</freezeDate>
			<identifyNumber>110101197905010</identifyNumber>
			<partyName>赵海风</partyName>
			<freezeAcctNo>6029693000105169</freezeAcctNo>
			<serialNumber>00000000000000000000000000000000002013080800112884</serialNumber>
		</nodeInfo>
		</request>
		*/
		Document document;
		try {
			document = WSUtil.parseXml(request);
			String bankCode = WSUtil.getXmlNodeText(document, "//request/bankCode");
			String serialNumber = WSUtil.getXmlNodeText(document, "//request/serialNumber");
			String freezeNo = WSUtil.getXmlNodeText(document, "//request/freezeNo");
			String systemDate = WSUtil.getXmlNodeText(document, "//request/systemDate");
			String bankName = WSUtil.getXmlNodeText(document, "//request/bankName");

			List<Map<String, Object>> bankEntity = intfSMO.queryFreeOfBank(freezeNo, bankCode, bankName, serialNumber);
			Map<String, Object> map = bankEntity.get(0);
			Object unfreezeDate = map.get("UNFREEZE_DATE");
			String freezeMoney = (String) map.get("FREEZE_MONEY");
			String freezeSubAcctNo = (String) map.get("FREEZE_SUB_ACCTNO");
			Object identifyType = map.get("PARTY_IDENTITYTYPE");
			Object freezeDate = map.get("FREEZE_DATE");
			String identifyNumber = (String) map.get("PARTY_IDENTITYNO");
			String partyName = (String) map.get("PARTY_NAME");
			String freezeAcctNo = (String) map.get("FREEZE_ACCTNO");

			resXml.append("<response>");
			resXml.append("<bankCode>").append(bankCode).append("</bankCode>");
			resXml.append("<bankName>").append(bankName).append("</bankName>");
			resXml.append("<bankCode>").append(bankCode).append("</bankCode>");
			resXml.append("<nodeInfo>");
			resXml.append("<unfreezeDate>").append(unfreezeDate).append("</unfreezeDate>");
			resXml.append("<freezeNo>").append(freezeNo).append("</freezeNo>");
			resXml.append("<freezeMoney>").append(freezeMoney).append("</freezeMoney>");
			resXml.append("<freezeSubAcctNo>").append(freezeSubAcctNo).append("</freezeSubAcctNo>");
			resXml.append("<identifyType>").append(identifyType).append("</identifyType>");
			resXml.append("<freezeDate>").append(freezeDate).append("</freezeDate>");
			resXml.append("<identifyNumber>").append(identifyNumber).append("</identifyNumber>");
			resXml.append("<partyName>").append(partyName).append("</partyName>");
			resXml.append("<freezeAcctNo>").append(freezeAcctNo).append("</freezeAcctNo>");
			resXml.append("<serialNumber>").append(serialNumber).append("</serialNumber>");
			resXml.append("</nodeInfo>");
			resXml.append("</response>");

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resXml.toString();
	}

	/**
	 * iphone预约网厅接口
	 * @param request
	 * @return
	 */
	@WebMethod
	public String reservatIPhone(@WebParam(name = "request") String request) {
		Document doc;
		StringBuffer result;
		logger.debug("进入方法reservatIPhone。。。。");
		try {
			logger.debug(request);
			doc = WSUtil.parseXml(request);
			List<JSONObject> orderJsons = convertToJson(doc);
			logger.debug("order节点个数：" + orderJsons.size());
			String dragonNbrs = "";
			String dragonIds = "";
			String orderNbrs = "";
			for (JSONObject s : orderJsons) {
				logger.debug("reservatIPhone构造json串：" + s.toString());
				String preOrderTerminal = soServiceSMO.preOrderTerminal(s);
				JSONObject j = JSONObject.fromObject(preOrderTerminal);
				String resultCode = j.get("resultCode").toString();
				if ("0".equals(resultCode)) {
					String orderNbr = j.get("orderNbr") == null ? "" : j.get("orderNbr").toString();
					String nbr = j.get("dragonNbr") == null ? "" : j.get("dragonNbr").toString();
					String id = j.get("dragonId") == null ? "" : j.get("dragonId").toString();
					dragonNbrs += (nbr + ",");
					dragonIds += (id + ",");
					orderNbrs += (orderNbr + ",");
				} else {
					String resultMsg = j.get("resultMsg") == null ? "" : j.get("resultMsg").toString();
					logger.debug("调用营业方法preOrderTerminal返回信息错误！错误原因：" + resultMsg);
					return WSUtil.buildResponse("1", resultMsg);
				}
			}
			result = new StringBuffer("");
			result.append("<response>");
			result.append("<resultCode>POR-0000</resultCode>");
			result.append("<resultMsg>成功</resultMsg>");
			result.append("<dragonNbr>").append(dragonNbrs.substring(0, dragonNbrs.length() - 1))
					.append("</dragonNbr>");
			result.append("<dragonId>").append(dragonIds.substring(0, dragonIds.length() - 1)).append("</dragonId>");
			result.append("<orderNbr>").append(orderNbrs.substring(0, orderNbrs.length() - 1)).append("</orderNbr>");

			result.append("</response>");

			logger.debug("调用reservatIPhone成功！");
			return result.toString();
		} catch (Exception e) {
			WSUtil.logError("reservatIPhone", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * iphone预约网厅接口转换为json
	 * @param doc
	 */
	@SuppressWarnings("unchecked")
	private List<JSONObject> convertToJson(Document doc) throws Exception {

		List<JSONObject> jsons = new ArrayList<JSONObject>();
		org.dom4j.Node orderList = WSUtil.getXmlNode(doc, "//request/orderList");
		if (orderList != null) {
			List<org.dom4j.Node> orders = orderList.selectNodes("order");
			for (org.dom4j.Node node : orders) {
				String terminalType = WSUtil.getXmlNodeText(node, "terminalType");
				String pricePlanCd = WSUtil.getXmlNodeText(node, "pricePlanCd");
				String orderType = WSUtil.getXmlNodeText(node, "orderType");
				String terminalNum = WSUtil.getXmlNodeText(node, "terminalNum");
				String prodType = WSUtil.getXmlNodeText(node, "prodType");

				String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
				String soChannelId = WSUtil.getXmlNodeText(doc, "//request/soChannelId");
				String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
				String linkNum = WSUtil.getXmlNodeText(doc, "//request/linkNum");
				String identTypeCd = WSUtil.getXmlNodeText(doc, "//request/identTypeCd");
				String preStaffId = WSUtil.getXmlNodeText(doc, "//request/preStaffId");
				String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String preDate = sd.format(new Date());

				String identNum = WSUtil.getXmlNodeText(doc, "//request/identNum");
				String custName = WSUtil.getXmlNodeText(doc, "//request/custName");
				JSONObject orderJson = new JSONObject();
				orderJson.put("channelId", channelId);
				orderJson.put("orderType", orderType);
				orderJson.put("terminalType", terminalType);
				orderJson.put("soChannelId", soChannelId);
				orderJson.put("areaId", areaId);
				orderJson.put("linkNum", linkNum);
				orderJson.put("identTypeCd", identTypeCd);
				orderJson.put("preStaffId", preStaffId);
				orderJson.put("identNum", identNum);
				orderJson.put("prodType", prodType);
				orderJson.put("pricePlanCd", pricePlanCd);
				orderJson.put("custName", custName);
				orderJson.put("terminalNum", terminalNum);
				orderJson.put("systemId", systemId);
				orderJson.put("preDate", preDate);
				jsons.add(orderJson);
			}
		}
		return jsons;
	}

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/applyCode", caption = "申请编码"),
			@Node(xpath = "//request/result", caption = "状态") })
	public String updateAndDenyLoan(@WebParam(name = "request") String request) {
		Document doc;
		Map<String, Object> root = new HashMap<String, Object>();
		try {
			doc = WSUtil.parseXml(request);
			String applyCode = WSUtil.getXmlNodeText(doc, "//request/applyCode");
			String applyDesc = WSUtil.getXmlNodeText(doc, "//request/resultDesc");
			String result = WSUtil.getXmlNodeText(doc, "//request/result");
			if ("997".equals(result)) {
				soServiceSMO.updateAndApproveLoan(applyCode);
			} else {
				soServiceSMO.updateAndDenyLoan(applyCode, applyDesc);
			}
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());

		} catch (Exception e) {
			root.put("resultMsg", e.getMessage());
			e.printStackTrace();
		}
		return mapEngine.transform("updateAndDenyLoan", root);
	}

	/**
	 * 查询同一证件下办理预付费（379）个数，后付费（378）个数
	 * 
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/identityNum", caption = "身份证信息") })
	public String qryProdCount(@WebParam(name = "request") String request) {
		StringBuffer sb = new StringBuffer();
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			String identityNumValue = WSUtil.getXmlNodeText(doc, "//request/identityNum");
			//验证身份证是否有效 
			Boolean ifTrue = intfSMO.checkIfIdentityNum(identityNumValue);
			if (ifTrue) {
				//获得 预付费和后付费的总量
				int count = intfSMO.queryCountProd(identityNumValue);
				sb.append("<response>");
				sb.append("<result>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("成功").append("</resultMsg>");
				sb.append("<count>").append(count).append("</count>");
				sb.append("</result>");
				sb.append("</response>");
			} else {
				sb.append("<response>");
				sb.append("<result>");
				sb.append("<resultCode>").append("1").append("</resultCode>");
				sb.append("<resultMsg>").append("该身份证信息不存在").append("</resultMsg>");
				sb.append("</result>");
				sb.append("</response>");
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * 新受理单补打接口
	 * 报文格式 			<request><olId>100004307396</olId><channelId>510001</channelId><staffCode>bj1001</staffCode><caFlag>6</caFlag></request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单id"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码"),
			@Node(xpath = "//request/caFlag", caption = "打印回执状态") })
	public String acceptancePlay(@WebParam(name = "request") String request) {
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
			String caFlag = WSUtil.getXmlNodeText(doc, "//request/caFlag");
			List<Map<String, Object>> olidList= intfSMO.getOlidByg(olId);
			if(olidList.size()>0){
				olId =  olidList.get(0).get("OL_ID").toString();
			}
			
			
			if(request.contains("modifyType")){
				String modifyType = WSUtil.getXmlNodeText(doc, "//request/modifyType");
				if(modifyType.equals("2")){
					String olIdr = WSUtil.getXmlNodeText(doc, "//request/olId");
					//2类型是集团ol_id 28开头的12位短号
					olId = intfSMO.queryProvenceIdByGroupNum(olIdr);
					if(olId.equals("")){
						return WSUtil.buildResponse("1", "没有可以修改的记录");
					}
				}
//				if(modifyType.equals("3")){
//					//3类型是是集团ol_nbr 28 开头20位长号
//					//通过28开头20位长号查询省内olid
//					olId = intfSMO.queryProvenceIdByGroupNum(olId);
//					if(olId.equals("")){
//						return WSUtil.buildResponse("1", "没有可以修改的记录");
//					}
//				}
				
			}
			
			Map<String, String> m = voucherPrintSMO.updateCustOlForIntf(olId, Integer.valueOf(caFlag));
			String code = m.get("code");
			String msg = m.get("msg");
			if ("0".equals(code)) {
				logger.debug("olId:" + olId + ";caFlag:" + caFlag + " 受理单回执打印成功！");
			} else
				logger.debug("olId:" + olId + ";caFlag:" + caFlag + " 受理单回执打印失败！，原因：" + msg);
			return WSUtil.buildResponse(code, msg);

		} catch (DocumentException e) {
			logger.debug("受理单回执打印失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 新增未登记客户
	 * @param request
	 * 入参：<request><staffId>1001</staffId><areaId>21</areaId><channelId>-10000</channelId></request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/staffId", caption = "员工id"),
			@Node(xpath = "//request/areaId", caption = "地区id") })
	public String addAnonymousParty(@WebParam(name = "request") String request) {
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			String errorMsg = "";
			//传过来的是代办号,需要转换为staffid
			String dbid = WSUtil.getXmlNodeText(doc, "//request/staffId");
			Long staffId = intfSMO.getStaffIdByDbid(dbid);
			if (staffId == null || staffId == 0l) {
				errorMsg = "根据代办号未查询到工号信息！代办号：" + dbid;
				logger.debug(errorMsg);
				return WSUtil.buildResponse("1", errorMsg);
			}
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			Long channelId = intfSMO.getChannelIdByStaffId(staffId);
			if (channelId == null || channelId == 0l) {
				errorMsg = "根据员工号未查询到平台信息！员工号：" + staffId;
				logger.debug(errorMsg);
				return WSUtil.buildResponse("1", errorMsg);
			}
			Long party_id = soCommitSMO.addAnonymousParty(staffId, Integer.valueOf(areaId), channelId, null);
			if (party_id != null) {
				logger.debug("新增未登记客户成功，party_id:" + party_id);
				return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId", String.valueOf(party_id));
			}
			errorMsg = "新增未登记客户失败，原因:调用soCommitSMO.addAnonymousParty为返回正确结果！";
			logger.debug(errorMsg);
			return WSUtil.buildResponse("1", errorMsg);
		} catch (DocumentException e) {
			logger.debug("新增未登记客户失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 银行基金校验接口，校验
	 * 1：用户是否有效
	 * 2：用户是否办理租机
	 * @param request
	 * 入参：<request>
	 * 			<partyName>1001</partyName>
	 * 			<identifyType>21</identifyType>
	 * 			<identifyNumber>-10000</identifyNumber>
	 * 			<accessNumber></accessNumber>
	 * 			<offerName></offerName>
	 * 		</request>
	 * @return
	 */
	@WebMethod
	@Required
	public String validateYH(@WebParam(name = "request") String request) throws RemoteException {
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			String errMsg = "";
			Long prodId = null;
			Map<String, Object> paraMap = null;
			Map<String, Object> offerMap = null;
			String partyName = WSUtil.getXmlNodeText(doc, "//request/partyName");
			String identifyType = WSUtil.getXmlNodeText(doc, "//request/identifyType");
			String identifyNumber = WSUtil.getXmlNodeText(doc, "//request/identifyNumber");
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String offerName = WSUtil.getXmlNodeText(doc, "//request/offerName");
			if (StringUtils.isBlank(accessNumber)) {
				errMsg = "传入的接入号为空";
				logger.debug(errMsg);
				return WSUtil.buildResponse("2", errMsg);
			}
			if (StringUtils.isBlank(offerName)) {
				errMsg = "传入的租机名称为空";
				logger.debug(errMsg);
				return WSUtil.buildResponse("2", errMsg);
			}
			// 根据接入号查找产品信息
			OfferProd prod = intfSMO.getProdByAccessNumber(accessNumber);
			if (prod == null) {
				errMsg = "用户无效！";
				logger.debug(errMsg);
				return WSUtil.buildResponse("3", errMsg);
			}
			prodId = prod.getProdId();
			paraMap = new HashMap<String, Object>();
			paraMap.put("accNum", accessNumber);
			if (StringUtils.isNotBlank(partyName)) {
				paraMap.put("partyName", partyName);
			}
			if (StringUtils.isNotBlank(identifyType)) {
				paraMap.put("identifyType", identifyType);
			}
			if (StringUtils.isNotBlank(identifyNumber)) {
				paraMap.put("identifyNumber", identifyNumber);
			}
			//检查入参传入的客户证件信息
			Long count = intfSMO.getValidateYHParams(paraMap);
			if (count < 1) {
				errMsg = "客户证件信息与用户不符！";
				logger.debug(errMsg);
				return WSUtil.buildResponse("4", errMsg);
			}
			//验证是否办理租机
			offerMap = new HashMap<String, Object>();
			offerMap.put("offerName", offerName);
			offerMap.put("prodId", prodId);
			Long num = intfSMO.getValidateYHOffer(offerMap);
			if (num >= 1) {
				errMsg = "用户已办理" + offerName;
				logger.debug(errMsg);
				return WSUtil.buildResponse("5", errMsg);
			}
			return WSUtil.buildResponse(ResultCode.SUCCESS);
		} catch (DocumentException e) {
			logger.debug("入参解析错误");
			return WSUtil.buildResponse("20", e.getMessage());
		}
	}

	/**
	 * 备卡激活
	 * @param request
	 * @return
	 * @throws RemoteException
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "员工编号"),
			@Node(xpath = "//request/order/systemId", caption = "系统编码"),
			@Node(xpath = "//request/order/prodId", caption = "用户id") })
	public String uimBakActivite(@WebParam(name = "request") String request) throws RemoteException {
		Document doc;
		String errorMsg = "";
		StringBuffer output = new StringBuffer("");
		try {
			logger.debug("****************备卡激活入参：" + request);
			doc = WSUtil.parseXml(request);
			String prodid = WSUtil.getXmlNodeText(doc, "//request/order/prodId");
			Map<String, Object> prodsms = intfSMO.getProdSmsByProdId(prodid);
			if (prodsms == null) {
				errorMsg = "根据用户id未查询到记录！";
				logger.debug(errorMsg);
				return WSUtil.buildResponse("-1", errorMsg);
			}
			//判断该用户是否有备卡领取
			if (!intfSMO.isUimBak(prodid)) {
				errorMsg = "该用户没有备卡领取信息！";
				logger.debug(errorMsg);
				return WSUtil.buildResponse("-2", errorMsg);
			}

			// 2.0XML转换为JSON
			JSONObject orderJson = new JSONObject();
			try {
				orderJson = businessServiceOrderListFactory.generateOrderList(doc);
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			logger.debug("-------------------------拼装自动受理入参json：" + orderJson.toString());
			String result = soServiceSMO.soAutoService(orderJson);

			logger.debug("-----------------调用自动受理工程返回结果：" + result);

			JSONObject resultJS = JSONObject.fromObject(result);
			if ("0".equals(resultJS.get("resultCode"))) {
				String olId = resultJS.getString("olId");
				output.append("<response>");
				output.append("<resultCode>0</resultCode>");
				output.append("<resultMsg>备卡激活成功</resultMsg>");
				output.append("<olId>").append(olId).append("</olId>");
				output.append("</response>");
				return output.toString();
			} else {
				String resultMsg = resultJS.getString("resultMsg");
				return WSUtil.buildResponse("1001", resultMsg);
			}

		} catch (Exception e) {
			logger.debug("其它错误：" + e.getMessage());
			return WSUtil.buildResponse("20", e.getMessage());
		}

	}

	/**
	 * 补换卡新接口
	 * @param request
	 * @return
	 * @throws RemoteException
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "购物车Id"),
			@Node(xpath = "//request/staffId", caption = "员工Id"),
			@Node(xpath = "//request/channelId", caption = "渠道Id"), @Node(xpath = "//request/prodId", caption = "用户Id") })
	public String cancelOrderListNew(@WebParam(name = "request") String request) throws RemoteException {
		String returnStr = null;
		Document doc = null;
		StringBuffer output = new StringBuffer("");
		try {
			doc = WSUtil.parseXml(request);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
		String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		JSONObject paramJson = new JSONObject();
		paramJson.put("groupId", "");
		paramJson.put("strIds", olId);
		paramJson.put("staffId", staffId);
		paramJson.put("channelId", channelId);
		paramJson.put("platId", "12");
		paramJson.put("openType", "");
		//返销 走返销接口
		returnStr = soServiceImpl.cancelOrderListNew(paramJson.toString());
		JSONObject resultJS = JSONObject.fromObject(returnStr);
		if ("0".equals(resultJS.get("resultCode"))) {
			output.append("<response>");
			output.append("<resultCode>0</resultCode>");
			output.append("<resultMsg>返销成功</resultMsg>");
			output.append("</response>");
			return output.toString();
		} else {
			String resultMsg = resultJS.getString("resultMsg");
			return WSUtil.buildResponse("1001", resultMsg);
		}
	}

	/**
	 * CRM主副卡关系信息查询需求
	 * <request>
		<queryType>1</queryType>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
		<billingNo>13311021392</billingNo>
	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/queryType", caption = "业务号类型"),
			@Node(xpath = "//request/areaNo", caption = "区号"), @Node(xpath = "//request/billingNo", caption = "业务号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String billingCardRelationQry(@WebParam(name = "request") String request) {
		Document doc;
		Map<String, Object> result = null;
		try {
			doc = WSUtil.parseXml(request);
			String billingNo = WSUtil.getXmlNodeText(doc, "//request/billingNo");
			String queryType = WSUtil.getXmlNodeText(doc, "//request/queryType");
			if (!"1".equals(queryType)) {
				return WSUtil.buildResponse("1", "业务号类型错误或不支持此业务号类型，请检查！");
			}
			// 根据接入号查找产品信息
			OfferProd prod = intfSMO.getProdByAccessNumber(billingNo);
			if (prod == null) {
				return WSUtil.buildResponse("1", "根据业务号未查询到产品信息" + billingNo);
			}
			result = new HashMap<String, Object>();
			Map<String, Object> imsiMap = intfSMO.getImsiInfoByBillingNo(billingNo);
			result.put("IMSI_LTE", imsiMap.get("LTEIMSI"));
			result.put("IMSI_CDMA", imsiMap.get("CIMSI"));
			result.put("IMSI_CTOG", imsiMap.get("GIMSI"));
			result.put("MSISDN", billingNo);

			List<Map<String, Object>> relation = intfSMO.getBillingCardRelation(billingNo);
			result.put("cardRelation", relation);

			result.put("resultCode", "0");
			result.put("resultMsg", "成功");

			return mapEngine.transform("billingCardRelationQry", result);

		} catch (DocumentException e) {
			logger.debug("CRM主副卡关系信息查询失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 查询表空间信息
	 * <request>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String queryTableSpace(@WebParam(name = "request") String request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String resultCode = "0";
			String resultMsg = "成功";
			List<Map<String, Object>> list = intfSMO.queryTableSpace();
			if (list == null || list.size() <= 0) {
				resultCode = "1";
				resultMsg = "没有查询到记录！";
			}
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			result.put("lists", list);

			return mapEngine.transform("queryTableSpace", result);

		} catch (Exception e) {
			logger.debug("查询表空间信息失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 查询表空间使用信息
	 * queryType:1表中对应非SYS_LOB字段进行核查表占用的空间大小
	 * queryType:非1 核查表空间中对应的SYS_LOB字段对应的实体表信息
	 * <request>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
		<tableSpaceName>010</tableSpaceName>
		<queryType>1</queryType>
	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码"),
			@Node(xpath = "//request/tableSpaceName", caption = "表空间名字") })
	public String queryUseSpaceBySysLob(@WebParam(name = "request") String request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Document doc;
		String resultCode = "0";
		String resultMsg = "成功";
		List<Map<String, Object>> list = null;
		try {
			doc = WSUtil.parseXml(request);
			String tableSpaceName = WSUtil.getXmlNodeText(doc, "//request/tableSpaceName");
			String queryType = WSUtil.getXmlNodeText(doc, "//request/queryType");

			if (StringUtils.isBlank(tableSpaceName)) {
				resultMsg = "查询表空间信息失败，原因：传入表空间名字为空！";
				logger.debug(resultMsg);
				return WSUtil.buildResponse("1", resultMsg);
			}
			if ("1".equals(queryType)) {
				list = intfSMO.queryUseSpaceSysLob(tableSpaceName);
			} else {
				list = intfSMO.queryUseSpaceNotSysLob(tableSpaceName);
			}

			if (list == null || list.size() <= 0) {
				resultCode = "1";
				resultMsg = "没有查询到记录！";
			}
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			result.put("lists", list);

			return mapEngine.transform("queryUseSpaceNotSysLob", result);

		} catch (Exception e) {
			logger.debug("queryUseSpaceNotSysLob接口调用失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * CRM库锁表信息
	 * <request>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String queryCrmLockInfo(@WebParam(name = "request") String request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String resultCode = "0";
			String resultMsg = "成功";
			List<Map<String, Object>> list = intfSMO.queryCrmLockInfo();
			if (list == null || list.size() <= 0) {
				resultCode = "1";
				resultMsg = "没有查询到记录！";
			}
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			result.put("lists", list);

			return mapEngine.transform("queryCrmLockInfo", result);

		} catch (Exception e) {
			logger.debug("CRM库锁表信息失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 数据连接session的使用情况
	 * <request>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String queryDBSessionInfo(@WebParam(name = "request") String request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String resultCode = "0";
			String resultMsg = "成功";
			Long dbcount = intfSMO.queryDBSessionInfo();
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			result.put("SESSIONNUM", dbcount);

			return mapEngine.transform("queryDBSessionInfo", result);

		} catch (Exception e) {
			logger.debug("数据连接session的使用情况失败，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 奔驰业务变更申请接口
	 * <request>
		<channelId>510001</channelId>
		<staffCode>bj1001</staffCode>
		<areaNo>010</areaNo>
		<reqMsg>
		<reqNo>201406090001</reqNo>
		<reqTime>2014-06-09 10:30:08</reqTime>
		<accNbr>13366495097</accNbr>
		<oldOfferId> 20130344</oldOfferId>
		<oldOfferName>奔驰休斯套餐二</oldOfferName>
		<newOfferId> 20130345</newOfferId>
		<newOfferName>奔驰休斯套餐三</newOfferName>
		<offerEffType>0</offerEffType>
		<closeDataServ>1</closeDataServ>
		<intoVPDN>1</intoVPDN>
		<subOffer>
			<offerId>20140123</offerId>
			<offerName>奔驰流量包</offerName>
			<offerEffType>0</offerEffType>
		</subOffer>
		</reqMsg>

	   </request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String benzBusiOrderRequest(@WebParam(name = "request") String request) {
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			List<Element> reqMsgs = doc.selectNodes("request/reqMsg");
			if (reqMsgs != null && reqMsgs.size() > 0) {
				for (Element e : reqMsgs) {
					Map<String, Object> result = new HashMap<String, Object>();

					String reqNo = WSUtil.getXmlNodeText(e, "./reqNo");
					String reqTime = WSUtil.getXmlNodeText(e, "./reqTime");
					String accNbr = WSUtil.getXmlNodeText(e, "./accNbr");
					String oldOfferId = WSUtil.getXmlNodeText(e, "./oldOfferId");
					String oldOfferName = WSUtil.getXmlNodeText(e, "./oldOfferName");
					String newOfferId = WSUtil.getXmlNodeText(e, "./newOfferId");
					String newOfferName = WSUtil.getXmlNodeText(e, "./newOfferName");
					String offerEffType = WSUtil.getXmlNodeText(e, "./offerEffType");
					String closeDataServ = WSUtil.getXmlNodeText(e, "./closeDataServ");
					String intoVPDN = WSUtil.getXmlNodeText(e, "./intoVPDN");

					result.put("REQ_NO", reqNo);
					try {
						result.put("REQ_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reqTime));
					} catch (ParseException e1) {
						result.put("REQ_TIME", new Date());
					}
					result.put("ACC_NBR", accNbr);
					result.put("OLD_OFFER_ID", oldOfferId);
					result.put("OLD_OFFER_NAME", oldOfferName);
					result.put("NEW_OFFER_ID", newOfferId);
					result.put("NEW_OFFER_NAME", newOfferName);
					result.put("OFFER_EFF_TYPE", offerEffType);
					result.put("CLOSE_DATA_SERV", closeDataServ);
					result.put("INTO_VPN", intoVPDN);

					result.put("CREATE_DT", new Date());
					result.put("AUTO_MANU_TYPE", 1);

					result.put("RESULT_CODE", 10);

					if (intfSMO.saveBenzBusiOrder(result)) {

					}

					List<Element> subOffers = e.selectNodes("./subOffer");
					if (subOffers != null && subOffers.size() > 0) {
						Map<String, Object> resultSub = null;
						for (Element subOffer : subOffers) {
							resultSub = new HashMap<String, Object>();
							String offerId = WSUtil.getXmlNodeText(subOffer, "./offerId");
							String offerName = WSUtil.getXmlNodeText(subOffer, "./offerName");
							String offerEffTypeS = WSUtil.getXmlNodeText(subOffer, "./offerEffType");
							resultSub.put("reqNo", reqNo);
							resultSub.put("offerId", offerId);
							resultSub.put("offerName", offerName);
							resultSub.put("offerEffTypeS", offerEffTypeS);
							intfSMO.saveBenzBusiOrderSub(resultSub);
						}
					}
				}
			}

		} catch (Exception e) {
			return WSUtil.buildResponse("1", e.getMessage());
		}
		return WSUtil.buildResponse("0", "成功");
	}

	/**
	 * 产品属性变更
	 * <request>
	<accessNumber>13301379601</accessNumber>
	<prodId>103030099216</prodId>
	<staffCode>Bj1001</staffCode>
	<channelId>90094</channelId>
	<areaNo>010</areaNo>
	<prodItems>
		<prodItem>
			<itemSpecId></itemSpecId>
			<itemValue></itemValue>
		</prodItem>
		<prodItem>
			<itemSpecId></itemSpecId>
			<itemValue></itemValue>
		</prodItem>
	</prodItems>
	</request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码") })
	public String offerProdItemChange(@WebParam(name = "request") String request) {
		Document doc;
		StringBuffer output = new StringBuffer("");
		try {
			doc = WSUtil.parseXml(request);
			JSONObject offerProdItemChangeMsg = this.orderListFactory.offerProdItemChangeMsg(doc);
			logger.debug("产品属性变更自动受理服务开始...");
			logger.debug("产品属性变更json:" + offerProdItemChangeMsg.toString());
			String result = soServiceSMO.soAutoService(offerProdItemChangeMsg);
			logger.debug("产品属性变更自动受理服务结束...");
			logger.debug("产品属性变更受理返回结果：" + result);
			JSONObject resultJson = JSONObject.fromObject(result);
			if (!"0".equals(resultJson.get("resultCode"))) {
				logger.error("产品属性变更失败！");
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, resultJson.getString("resultMsg"));
			}
			output.append("<response>");
			output.append("<resultCode>").append("0").append("</resultCode>");
			output.append("<resultMsg>").append(resultJson.getString("resultMsg")).append("</resultMsg>");
			output.append("<olNbr>").append(intfSMO.getOlNbrByOlId(Long.valueOf(resultJson.getString("olId")))).append(
					"</olNbr>");
			output.append("<olId>").append(resultJson.getString("olId")).append("</olId>");
			output.append("<pageInfo>").append("<![CDATA[").append(
					intfSMO.getPageInfo(resultJson.getString("olId"), "1", "Y")).append("]]>").append("</pageInfo>");
			output.append("</response>");
			//			return WSUtil.buildResponse(resultJson.getString("resultCode"), resultJson.getString("resultMsg"));
			return output.toString();
		} catch (Exception e) {
			logger.error("产品属性变更异常，原因：" + e.getMessage());
			e.printStackTrace();
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 查询CustClassInfo信息
	 * @param request
	 * <request>
	  		<channelId>90094</channelId>
	  		<staffCode>Bj1001</staffCode>
	  		<areaNo>010</areaNo>
	  		<custId>C41196379</custId>
	  </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "平台id"),
			@Node(xpath = "//request/staffCode", caption = "平台编码"),
			@Node(xpath = "//request/accessNumber", caption = "接入号码") })
	public String getCustClassInfo(@WebParam(name = "request") String request) {
		Document doc;
		String accessNumber = "";
		String custId = "";
		StringBuffer result = null;
		try {
			doc = WSUtil.parseXml(request);
			accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			custId = intfSMO.getCustIdByAccNum(accessNumber);
			if (null == custId || "".equals(custId)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "根据用户号码：" + accessNumber + "未查询到账户id信息！");
			}
			List<Map<String, Object>> custInfos = intfSMO.getCustClassInfoByCustId(custId);
			if (custInfos == null || custInfos.size() == 0) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "根据custId：" + custId + "未查询到信息！");
			}
			Map<String, Object> custInfosMap = custInfos.get(0);
			result = new StringBuffer("");
			result.append("<response>");
			result.append("<resultCode>0</resultCode>");
			result.append("<resultMsg>成功</resultMsg>");
			result.append("<custInfo>");
			result.append("<OP_TIME>").append(custInfosMap.get("OP_TIME")).append("</OP_TIME>");
			result.append("<CUST_ID>").append(custInfosMap.get("CUST_ID")).append("</CUST_ID>");
			result.append("<CUST_CLASS_ID>").append(custInfosMap.get("CUST_CLASS_ID")).append("</CUST_CLASS_ID>");
			result.append("<BILL_FEE>").append(custInfosMap.get("BILL_FEE")).append("</BILL_FEE>");
			result.append("</custInfo>");
			result.append("</response>");
			return result.toString();

		} catch (NumberFormatException e) {
			logger.error("查询CustClassInfo信息异常，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", "custId:" + custId + "输入错误！");
		} catch (Exception e) {
			logger.error("查询CustClassInfo信息异常，原因：" + e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 过户（给多卡平台用）
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbrLose", caption = "过户用户号码"),
			@Node(xpath = "//request/partyId", caption = "被过户的客户id"),
			@Node(xpath = "//request/accNbrType", caption = "号码类型"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"), @Node(xpath = "//request/areaNo", caption = "区号"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String transferOwnerDh(@WebParam(name = "request") String request) {
		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accNbrLose = WSUtil.getXmlNodeText(doc, "//request/accNbrLose");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId");
			String accNbrType = WSUtil.getXmlNodeText(doc, "/request/accNbrType");
			String partyId = WSUtil.getXmlNodeText(doc, "/request/partyId");

			if (!"1".equals(accNbrType)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "暂不支持号码类型！");
			}
			Long prodIdLose = intfSMO.getProdidByAccNbr(accNbrLose);
			Map<String, Object> rmap = new HashMap<String, Object>();//结果返回map
			//根据用户号码查询过户客户id
			Long partyIdLose = intfSMO.getPartyIdByAccNbr(accNbrLose);

			String smsg = "0";//停机服务消息
			String stopSerServiceMsg = stopSerService(accNbrLose, channelId, staffId, String.valueOf(partyIdLose),
					prodIdLose);
			if (!"0".equals(stopSerServiceMsg)) {
				smsg = stopSerServiceMsg;
			}

			Element rootElement = doc.getRootElement();
			rootElement.addElement("partyId").addText(partyId + "");
			rootElement.addElement("accessNumber").addText(accNbrLose);

			logger.debug("开始组建过户json报文...");
			// 2.0XML转换为JSON
			JSONObject orderJson = new JSONObject();
			try {
				orderJson = createTransferOwnerListFactory.generateTransferOwnerDh(doc);
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			logger.debug("过户报文转换为JSON:{}", orderJson.toString());

			logger.debug("过户自动受理服务开始...");
			String result = soServiceSMO.soAutoService(orderJson);
			logger.debug("过户自动受理服务结束...");
			logger.debug("过户受理返回结果：" + result);

			JSONObject resultJson = JSONObject.fromObject(result);
			if ((resultJson.getString("resultCode")).equals("0")) {
				if (!"0".equals(smsg)) {
					smsg = ";过户成功，停机服务失败！";
				}
				rmap.put("resultCode", resultJson.getString("resultCode"));
				rmap.put("resultMsg", resultJson.getString("resultMsg") + smsg);
				rmap.put("olId", resultJson.getString("olId"));
			} else {
				String resultMsg = resultJson.getString("resultMsg");
				String msg = "";
				if (resultMsg.indexOf("</rule>") != -1) {
					String a = WSUtil.buildResponse(ResultCode.CALL_METHOD_ERROR, resultMsg);
					Document b = WSUtil.parseXml(a);
					List<Element> c = b.selectNodes("response/resultMsg/rule");
					for (int i = 0; c.size() > i; i++) {
						String ruleId = WSUtil.getXmlNodeText(c.get(i), "./ruleId");
						String ruleDesc = WSUtil.getXmlNodeText(c.get(i), "./ruleDesc");
						msg = msg + "规则ID[" + ruleId + "]" + ruleDesc + " ";
					}
				} else if (resultMsg.indexOf("</errorMsg>") != -1) {
					msg = resultJson.getString("errorMsg");

				} else {
					msg = "过户接口返回 resultMsg:" + resultMsg;
				}
				rmap.put("resultCode", resultJson.getString("resultCode"));
				rmap.put("resultMsg", msg + smsg);
			}
			String resultf = this.mapEngine.transform("transferOwnerDh", rmap);
			logger.debug("transferOwner返回结果--------" + resultf);
			return resultf;
		} catch (Exception e) {
			logger.error("transferOwnerDh实名制客户资料上传:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 查询用户状态
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "用户号码"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"), @Node(xpath = "//request/areaId", caption = "区号"),
			@Node(xpath = "//request/systemId", caption = "系统编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String queryOfferProdStatus(@WebParam(name = "request") String request) {
		try {
			StringBuffer result = new StringBuffer("");
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			Map<String, Object> queryOfferProdStatus = intfSMO.queryOfferProdStatus(accessNumber);
			//限制订购
			if ("6090010063".equals(systemId)) {
				if (!intfSMO.isFtWifiSystem(accessNumber)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "用户号码：" + accessNumber + "不是丰田wifi系统用户！");
				}
			}
			if (queryOfferProdStatus == null)
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "根据用户号码：" + accessNumber + "未查询到信息！");
			String partyId = queryOfferProdStatus.get("PARTYID") + "";
			String prod_status_cd = queryOfferProdStatus.get("PROD_STATUS_CD") + "";

			String pstName = queryOfferProdStatus.get("PSTNAME") + "";

			result.append("<response>");
			result.append("<resultCode>0</resultCode>");
			result.append("<resultMsg>").append(partyId).append("</resultMsg>");
			result.append("<statusCode>").append(prod_status_cd).append("</statusCode>");
			result.append("<statusMsg>").append(pstName).append("</statusMsg>");
			result.append("</response>");
			return result.toString();
		} catch (Exception e) {
			logger.error("transferOwnerDh实名制客户资料上传:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 校验华翼宽带订购资费参数
	 * <request>
				<accessNumber>13381086648</accessNumber>
				<cust_id>103005224372</cust_id>
				<staffCode>bj1001</staffCode>
				<areaId>11000</areaId>
				<channelId>-10020</channelId>
			</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "用户号码"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"), @Node(xpath = "//request/areaId", caption = "区号"),
			@Node(xpath = "//request/cust_id", caption = "客户id"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String checkHykdOrder(@WebParam(name = "request") String request) {
		try {
			StringBuffer result = new StringBuffer("");
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String custId = WSUtil.getXmlNodeText(doc, "//request/cust_id");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("accNum", accessNumber);
			param.put("custId", custId);
			int count = intfSMO.checkHykdOrder(param);
			if (count < 1)
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "参数C网后付费号码值必须是同客户C网后付费在网已办理租机并且租机在用用户!");

			result.append("<response>");
			result.append("<resultCode>0</resultCode>");
			result.append("<resultMsg>校验通过</resultMsg>");
			result.append("</response>");
			return result.toString();
		} catch (Exception e) {
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 客户姓名信息校验
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required
	public String checkCustName(@WebParam(name = "request") String request) {
		StringBuffer result = new StringBuffer("");
		try {
			Document doc = WSUtil.parseXml(request);
			result.append("<ContractRoot><TcpCont>");
			//获取数据
			String TransactionID = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/TransactionID");
			result.append("<TransactionID>").append(TransactionID).append("</TransactionID>");
			result.append("<RspTime>").append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).append(
					"</RspTime>");
			result.append("</TcpCont>");
			result.append("<SvcCont><CheckInfos>");
			List<org.dom4j.Node> CustInfos = WSUtil.getXmlNodeList(doc, "//ContractRoot/SvcCont/CustInfos/CustInfo");
			for (org.dom4j.Node payInfoNode : CustInfos) {
				StringBuffer one = new StringBuffer("");
				try {
					one.append("<CheckInfo>");

					String PHONE_NUMBER = WSUtil.getXmlNodeText(payInfoNode, "PHONE_NUMBER");
					String CUST_NAME = WSUtil.getXmlNodeText(payInfoNode, "CUST_NAME");
					one.append("<PHONE_NUMBER>").append(PHONE_NUMBER).append("</PHONE_NUMBER>");
					one.append("<CUST_NAME>").append(CUST_NAME).append("</CUST_NAME>");

					if (StringUtil.isEmpty(PHONE_NUMBER) || StringUtil.isEmpty(PHONE_NUMBER)) {
						one.append("<RspCode>3</RspCode>");
						one.append("<RspDesc>参数传入错误</RspDesc>");
						one.append("</CheckInfo>");
						continue;
					}

					if (!intfSMO.qryAccessNumberIsOk(PHONE_NUMBER)) {
						one.append("<RspCode>2</RspCode>");
						one.append("<RspDesc>号码不存在</RspDesc>");
						one.append("</CheckInfo>");
					}
					Map<String, String> map = intfSMO.checkCustName(PHONE_NUMBER, CUST_NAME);
					Set<Entry<String, String>> entrySet = map.entrySet();
					for (Entry<String, String> en : entrySet) {
						one.append("<RspCode>").append(en.getKey()).append("</RspCode>");
						one.append("<RspDesc>").append(en.getValue()).append("</RspDesc>");
					}
					one.append("</CheckInfo>");
					result.append(one);
				} catch (Exception e) {
					result.append("<CheckInfo></CheckInfo>");
				}

			}
			result.append("</CheckInfos></SvcCont></ContractRoot>");
			return result.toString();
		} catch (Exception e) {
			return "<ContractRoot><errorMsg>" + e.getMessage() + "</errorMsg></ContractRoot>";
		}
	}

	/**
	 * 客服直兑话费开发文档
	 * <request>
	<accessNumber></accessNumber>
	<areaId>10000</areaId>
	<staffCode></staffCode>
	<channelId></channelId>
	</request>
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required
	public String creditsExchangeCheck(@WebParam(name = "request") String request) {
		StringBuffer result = new StringBuffer("");
		String errorMsg = "";
		String errorCode = "";
		String prodSpecId = "";
		result.append("<response>");
		try {
			Document doc = WSUtil.parseXml(request);
			//获取数据
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			Long prodidByAccNbr = intfSMO.getProdidByAccNbr(accNbr);
			OfferProd prodByAccessNumber = intfSMO.getProdByAccessNumber(accNbr);
			if (prodByAccessNumber != null)
				prodSpecId = prodByAccessNumber.getProdSpecId() + "";

			if (null == prodidByAccNbr) {
				errorMsg = "01";
				errorCode = "用户不存在";
				result.append("<resultCode>").append(errorMsg).append("</resultCode>");
				result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
				result.append("<prod_id></prod_id>");
				result.append("<prodSpecId></prodSpecId>");
				result.append("</response>");
				return result.toString();
			}
			Map<String, Object> status = intfSMO.queryOfferProdStatus(accNbr);
			if (null == status) {
				errorMsg = "01";
				errorCode = "用户不存在";
				result.append("<resultCode>").append(errorMsg).append("</resultCode>");
				result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
				result.append("<prod_id></prod_id>");
				result.append("<prodSpecId></prodSpecId>");
				result.append("</response>");
				return result.toString();
			}
			String prod_status_cd = status.get("PROD_STATUS_CD") + "";
			if ("2".equals(prod_status_cd) || "5".equals(prod_status_cd) || "6".equals(prod_status_cd)
					|| "7".equals(prod_status_cd) || "17".equals(prod_status_cd)) {
				errorMsg = "02";
				errorCode = "用户已停机";
				result.append("<resultCode>").append(errorMsg).append("</resultCode>");
				result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
				result.append("<prod_id></prod_id>");
				result.append("<prodSpecId></prodSpecId>");
				result.append("</response>");
				return result.toString();
			}
			if ("16".equals(prod_status_cd)) {
				errorMsg = "03";
				errorCode = "用户未激活";
				result.append("<resultCode>").append(errorMsg).append("</resultCode>");
				result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
				result.append("<prod_id></prod_id>");
				result.append("<prodSpecId></prodSpecId>");
				result.append("</response>");
				return result.toString();
			}
			Map<String, Object> partysmg = intfSMO.getPartyTypeCdByProdId(prodidByAccNbr);
			if ("1".equals(partysmg.get("PARTY_TYPE_CD"))||("2".equals(partysmg.get("PARTY_TYPE_CD")))) {
				
				
				List<String> identityList = new ArrayList<String>();
				boolean identityCheck = false;
				identityList.add("1");
				identityList.add("6");
				identityList.add("9");
				identityList.add("10");
				identityList.add("38");
				identityList.add("39");
				identityList.add("47");
				identityList.add("48");
				//2,12,13,18,37
				identityList.add("2");
				identityList.add("12");
				identityList.add("13");
				identityList.add("18");
				identityList.add("37");

				List<Map<String, Object>> partyIdentityList = intfSMO.getPartyIdentityList(partysmg.get("PARTY_ID") + "");
				for (Map<String, Object> m : partyIdentityList) {
					Object t = m.get("TYPE");
					if (identityList.contains(t)) {
						identityCheck = true;
						break;
					}
				}
				if (!identityCheck) {
					errorMsg = "05";
					errorCode = "客户证件类型不允许兑换";
					result.append("<resultCode>").append(errorMsg).append("</resultCode>");
					result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
					result.append("<prod_id></prod_id>");
					result.append("<prodSpecId></prodSpecId>");
					result.append("</response>");
					return result.toString();
				}

				result.append("<resultCode>00</resultCode>");
				result.append("<resultMsg>成功</resultMsg>");
				result.append("<prod_id>").append(prodidByAccNbr).append("</prod_id>");
				result.append("<prodSpecId>").append(prodSpecId).append("</prodSpecId>");
				result.append("</response>");
				
				
				return result.toString();
			}else{
				errorMsg = "04";
				errorCode = "客户不是个人客户或政企客户不允许兑换";
				result.append("<resultCode>").append(errorMsg).append("</resultCode>");
				result.append("<resultMsg>").append(errorCode).append("</resultMsg>");
				result.append("<prod_id></prod_id>");
				result.append("<prodSpecId></prodSpecId>");
				result.append("</response>");
			}
			
			return result.toString();
		} catch (Exception e) {
			result.append("<resultCode>06</resultCode>");
			result.append("<resultMsg>").append(e.getMessage()).append("</resultMsg>");
			result.append("<prod_id></prod_id>");
			result.append("<prodSpecId></prodSpecId>");
			result.append("</response>");
			return result.toString();
		}

	}

	/**
	 * 根据olId查找订单信息
	 * <request>
			<staffCode>bj1001</staffCode>
			<areaId>11000</areaId>
			<channelId>-10020</channelId>
			<systemId>6090010023</systemId>
			<olId>100004221564</olId>
		</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/olId", caption = "订单id"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"), @Node(xpath = "//request/areaId", caption = "区号"),
			@Node(xpath = "//request/systemId", caption = "系统编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String qryOrderListByOlId(@WebParam(name = "request") String request) {
		try {
			StringBuffer result = new StringBuffer("");
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String olId = WSUtil.getXmlNodeText(doc, "//request/olId");

			Map<String, Object> m = intfSMO.qryOrderListByOlId(olId);

			if (m == null) {
				return WSUtil.buildResponse("1", "订单id无记录：" + olId);
			}
			result.append("<response>");
			result.append("<resultCode>0</resultCode>");
			result.append("<resultMsg>成功</resultMsg>");
			result.append("<olId>").append(olId).append("</olId>");
			result.append("<olNbr>").append(m.get("OL_NBR")).append("</olNbr>");
			result.append("<statusCd>").append(m.get("STATUS_CD")).append("</statusCd>");
			result.append("<partyId>").append(m.get("PARTY_ID")).append("</partyId>");
			result.append("</response>");

			return result.toString();
		} catch (Exception e) {
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 测试接口
	 * @param request
	 */
	private void ghTest(@WebParam(name = "request") String request) {
		int count = 0;
		List<Map<String, Object>> addressStr = intfSMO.getGhAddressTemp();
		try {
			for (Map<String, Object> m : addressStr) {
				count++;
				System.out.println(count);
				String ghlyid = m.get("GHLYID") + "";
				String xqmc1 = m.get("XQMC1") + "";
				String xqmc2 = m.get("XQMC2") + "";
				String dxssqj = m.get("DXSSQJ") + "";
				String jtlh = m.get("JTLH") + "";
				String addr = getAddress9Level(xqmc1, xqmc2, dxssqj, jtlh);
				String response = callWebService(ghlyid);
				try {
					Document parseXml = WSUtil.parseXml(response);
					List<Element> c = parseXml.selectNodes("XMLInfo/list/info");
					if (null == c || c.size() == 0)
						continue;
					for (int i = 0; c.size() > i; i++) {
						String send = "";
						Map<String, Object> param = new HashMap<String, Object>();
						String unitname = WSUtil.getXmlNodeText(c.get(i), "./unitname");
						if (null != unitname && !"".equals(unitname) && (unitname.indexOf("-")) != -1) {
							String[] split = unitname.split("-");
							String lc = "";
							if (split.length == 2) {
								try {
									lc = split[1].substring(0, split[1].length() - 2);
								} catch (Exception e) {
									System.out.println(split[1]);
									lc = "";
								}
								send = (split[0] + "," + lc + "," + split[1]);
							} else {
								send = ",," + unitname;
							}
						} else
							send = ",," + unitname;
						String address = addr + send;
						param.put("address", address);
						param.put("ghlyid", ghlyid);
						param.put("unitname", unitname);
						String[] addressArray = address.split(",");
						param.put("city", addressArray[0]);
						param.put("area", addressArray[1]);
						param.put("mainLoad", addressArray[2]);
						param.put("secondLoad", addressArray[3]);
						param.put("villageName", addressArray[4]);
						param.put("buildingNumber", addressArray[5]);
						param.put("unitNumber", addressArray[6]);
						param.put("houseLever", addressArray[7]);
						param.put("houseNumber", addressArray[8]);

						intfSMO.insertGhAddressUnit(param);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}

	private String getAddress9Level(String xq1, String xq2, String dxssqj, String jtlh) {
		String level1 = "北京市";
		String level2 = dxssqj;
		String road = "";
		String buildingNumber = "";
		if (null != xq1 && !"".equals(xq1) && xq2.indexOf(xq1) != -1) {
			road = xq2.substring(0, xq2.indexOf(xq1));
		}
		//根据小区名称1截取楼栋号
		if (null != xq1 && !"".equals(xq1) && jtlh.indexOf(xq1) != -1) {
			buildingNumber = jtlh.substring(jtlh.indexOf(xq1) + xq1.length());
		}
		//根据小区名称2截取楼栋号
		if ("".equals(buildingNumber) && null != xq2 && !"".equals(xq2) && jtlh.indexOf(xq2) != -1) {
			buildingNumber = jtlh.substring(jtlh.indexOf(xq2) + xq2.length());
		}

		return level1 + "," + level2 + "," + road + ",," + xq1 + "," + buildingNumber + ",";
	}

	private String callWebService(String request) {
		String s = "<XMLInfo><listnumber>1000</listnumber><beginnumber>1</beginnumber><info><busType>yxkd</busType><buildingId>"
				+ request + "</buildingId><key></key></info></XMLInfo>";
		String response = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call
					.setTargetEndpointAddress(new java.net.URL(
							"http://172.19.100.180:9300/BJCSB/services/CrmService?wsdl"));
			call.setOperationName("getInstallationAreaUnit");
			call.setTimeout(new Integer(30000));
			response = (String) call.invoke(new Object[] { s });
		} catch (Exception e) {
			logger.error("调用webService异常:", e);
		}
		return response;
	}

	/**
	 * 判断是否为pk用户
	 * <request>
			<staffCode>bj1001</staffCode>
			<areaId>11000</areaId>
			<channelId>-10020</channelId>
			<systemId>6090010040</systemId>
			<accNbr>100004221564</accNbr>
		</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"), @Node(xpath = "//request/areaId", caption = "区号"),
			@Node(xpath = "//request/systemId", caption = "系统编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String isAccNumRealNameparty(@WebParam(name = "request") String request) {
		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			OfferProd prodInfo = intfSMO.getProdByAccessNumber(accNbr);
			if (prodInfo == null)
				return WSUtil.buildResponse("-1", "无此用户：" + accNbr);
			Long prodId = prodInfo.getProdId();
			int ifsm = intfSMO.isAccNumRealNameparty(prodId);
			if (ifsm == 1) {
				return WSUtil.buildResponse("0", "实名用户");
			} else
				return WSUtil.buildResponse("2", "非实名用户");

		} catch (Exception e) {
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 查询客户责任人信息
	 * <request>
	<accessNumber>13301379601</accessNumber>
	<custName>103030099216</ custName >
	<staffCode>Bj1001</staffCode>
	<channelId>90094</channelId>
	</request>

	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号码"),
			@Node(xpath = "//request/custName", caption = "客户名称"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String qryResponsibleDetail(@WebParam(name = "request") String request) {

		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			Long partyId = null;
			String oldZrrXm = "";
			String oldZrrZjlx = "";
			String oldZrrZjhm = "";
			String oldZrrLxr = "";
			String partyTypeCd = "";

			StringBuffer output = new StringBuffer("");

			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			String custName = WSUtil.getXmlNodeText(doc, "//request/custName");

			//去除关键字
			custName = custName.replace("北京", "").replace("股份", "").replace("有限", "").replace("责任", "").replace("公司",
					"");
			if (StringUtil.isEmpty(custName))
				return WSUtil.buildResponse("-1", "请输入正确的客户名称！");

			Map<String, Object> m = new HashMap<String, Object>();
			m.put("accNbr", accNbr);
			m.put("custName", custName);

			  //partyId = intfSMO.checkParByIdCust(m);
			   Map   custmap=intfSMO.checkParByIdCust(m);
			   if(custmap !=null){
				   partyId=Long.valueOf(String.valueOf(custmap.get("PARTY_ID")));
				   custName=String.valueOf(custmap.get("NAME"));
			   }
			if (null == partyId || 0 == partyId) {
				return WSUtil.buildResponse("-1", "号码：" + accNbr + "和客户名：" + custName + "未定位到客户！");
			}
			Long prodidByAccNbr = intfSMO.getProdidByAccNbr(accNbr);

			Map<String, Object> partyTypeCdByProdId = intfSMO.getPartyTypeCdByProdId(prodidByAccNbr);
			if (null != partyTypeCdByProdId) {
				Object p = partyTypeCdByProdId.get("PARTY_TYPE_CD");
				partyTypeCd = (p == null ? "" : p.toString());
			}
			Map custProfiles = intfSMO.queryCustProfiles(Long.valueOf(partyId));
			if (custProfiles != null) {
				oldZrrXm = (String) custProfiles.get("ZRRXM");
				oldZrrZjlx = (String) custProfiles.get("ZRRZJLX");
				oldZrrZjhm = (String) custProfiles.get("ZRRZJHM");
				oldZrrLxr = (String) custProfiles.get("ZRRLXDH");
			}
			if (!StringUtil.isEmpty(oldZrrXm)) {
				oldZrrXm = "*" + oldZrrXm.substring(oldZrrXm.length() - 1);
			}
			if (!StringUtil.isEmpty(oldZrrZjhm) && oldZrrZjhm.length() > 3) {
				oldZrrZjhm = "*" + oldZrrZjhm.substring(oldZrrZjhm.length() - 3);
			}
			output.append("<response>");
			output.append("<resultCode>").append(0).append("</resultCode>");
			output.append("<partyId>").append(partyId).append("</partyId>");
			output.append("<partyTypeCd>").append(partyTypeCd).append("</partyTypeCd>");
			output.append("<custName>").append(custName).append("</custName>");
			output.append("<responsible>");
			output.append("<name>").append(oldZrrXm != null ? oldZrrXm : "").append("</name>");
			output.append("<idType>").append(oldZrrZjlx != null ? oldZrrZjlx : "").append("</idType>");
			output.append("<idNum>").append(oldZrrZjhm != null ? oldZrrZjhm : "").append("</idNum>");
			output.append("<linkPhone>").append(oldZrrLxr != null ? oldZrrLxr : "").append("</linkPhone>");
			output.append("</responsible>");
			output.append("</response>");

			return output.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 查询使用人信息
	 * <request>
	<accessNumber>13381079000</accessNumber>
	<staffCode>Bj1001</staffCode>
	<channelId>90094</channelId>
	</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号码"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String qryProdUserDetail(@WebParam(name = "request") String request) {

		try {
			Long prodId = null;
			Integer specId = null;
			String useName = null;
			String useId = null;
			String useParyId = null;
			StringBuffer output = new StringBuffer("");
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
			OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);

			if (prod != null) {
				prodId = prod.getProdId();
				specId = prod.getProdSpecId();
			} else {
				return WSUtil.buildResponse("-1", "根据接入号未查询到用户信息！");
			}

			Map<String, Object> m = intfSMO.queryOfferProdItemsByProdId(prodId);
			if (null != m) {
				useName = m.get("USENAME") == null ? "" : m.get("USENAME").toString();
				useParyId = m.get("USEID") == null ? "" : m.get("USEID").toString();
			}

			if (!StringUtil.isEmpty(useParyId)) {
				Map queryIdentifyList = intfSMO.queryIdentifyList(Long.parseLong(useParyId));
				if (queryIdentifyList != null) {
					useId = queryIdentifyList.get("IDENTITY_NUM") + "";
				}

				//					Long partyIdByIdentifyNum = intfSMO.getPartyIdByIdentifyNum(useId);
				//					if(partyIdByIdentifyNum != null)
				//						useParyId = partyIdByIdentifyNum+"";
				if (useId.length() > 3)
					useId = "*" + useId.substring(useId.length() - 3);

			}

			output.append("<response>");
			output.append("<resultCode>").append(0).append("</resultCode>");
			output.append("<prodId>").append(prodId).append("</prodId>");
			output.append("<prodSpecId>").append(specId).append("</prodSpecId>");
			output.append("<prodUser>");
			output.append("<name>").append(useName).append("</name>");
			output.append("<partyId>").append(useParyId == null ? "" : useParyId).append("</partyId>");
			output.append("<idNum>").append(useId).append("</idNum>");
			output.append("</prodUser>");
			output.append("</response>");

			return output.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 根据身份证号查询客户信息
	 * <request>
	<id></id>
	<staffCode>Bj1001</staffCode>
	<channelId>90094</channelId>
	  </request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/id", caption = "证件号码"),
			@Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String qryPartyDetailByIDNbr(@WebParam(name = "request") String request) {

		try {
			Long partyId = null;
			String partyName = null;
			StringBuffer output = new StringBuffer("");
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String id = WSUtil.getXmlNodeText(doc, "//request/id");
			partyId = intfSMO.getPartyIdByIdentifyNum(id);
			if (null == partyId || partyId == 0) {
				return WSUtil.buildResponse("-1", "根据接证件号码" + id + "未查询到客户信息！");
			}
			partyName = intfSMO.getPartyNameByPartyId(partyId);
			if (id.length() > 3)
				id = "*" + id.substring(id.length() - 3);
			if (null != partyName && partyName.length() > 2)
				partyName = "*" + partyName.substring(partyName.length() - 1);

			output.append("<response>");
			output.append("<resultCode>").append(0).append("</resultCode>");
			output.append("<partyId>").append(partyId).append("</partyId>");
			output.append("<partyName>").append(partyName).append("</partyName>");
			output.append("<id>").append(id).append("</id>");
			output.append("</response>");
			return output.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 发送短信接口
	 * <request>
	<accNbr>13313601459</accNbr>
	<msg>4</msg>
	<smsSystem>12010038</smsSystem>
	<areaId>10000</areaId>
	<channelId>11040361</channelId>
	<staffCode>bj1001</staffCode>
	</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "号码"),
			@Node(xpath = "//request/msg", caption = "短信内容"), @Node(xpath = "//request/staffCode", caption = "员工编码"),
			@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String sendSms(@WebParam(name = "request") String request) {

		try {
			//获取数据
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			String msg = WSUtil.getXmlNodeText(doc, "//request/msg");
			String smsSystem = WSUtil.getXmlNodeText(doc, "//request/smsSystem");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accNbr", accNbr);
			map.put("msg", msg);
			map.put("smsSystem", smsSystem);

			boolean flag = intfSMO.insertSms(map);

			if (flag)
				return WSUtil.buildResponse("0", "成功");
			return WSUtil.buildResponse("1", "短信发送失败");

		} catch (Exception e) {
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}

	/**
	 * 业务受理接口（含预拆机（复机），属性变更）
	 * <request>
	<order>
		<orderTypeId>1179,72</orderTypeId>
		<accessNumber></accessNumber>
		<systemId>6090010023</systemId>
		<prodPropertys>
			<property>
				<id>350050</id>
				<name>客户信用度</name>
				<value>1</value>
				<actionType>2</actionType>
			</property>
		</prodPropertys>
	</order>
	<channelId>90094</channelId>
	<staffCode>bj1001</staffCode>
	</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/order/orderTypeId", caption = "业务类型"),
			@Node(xpath = "//request/order/accessNumber", caption = "号码"),
			@Node(xpath = "//request/order/systemId", caption = "系统编码"),
			@Node(xpath = "//request/order/prodPropertys/property/id", caption = "属性id"),
			@Node(xpath = "//request/order/prodPropertys/property/name", caption = "属性名称"),
			@Node(xpath = "//request/order/prodPropertys/property/value", caption = "属性值"),
			@Node(xpath = "//request/order/prodPropertys/property/actionType", caption = "属性操作类型"),
			@Node(xpath = "//request/channelId", caption = "短信内容"),
			@Node(xpath = "//request/staffCode", caption = "渠道的业务编码") })
	public String businessAChange(@WebParam(name = "request") String request) {
		  return setBusinessAChangeRequest(request);
	}

	private String setBusinessAChangeRequest(String request) {
		Document doc;
		try {
			doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/order/accessNumber");
			OfferProd prodByAccessNumber = intfSMO.getProdByAccessNumber(accNbr);
			if (prodByAccessNumber == null)
				return WSUtil.buildResponse("-1", "根据接入号未查询到产品信息");
			Integer prodSpecId = prodByAccessNumber.getProdSpecId();
			
            if (!"20130060".equals(String.valueOf(prodSpecId)))
            	return WSUtil.buildResponse("-1", "根据接入号不是歌华用户不能办理此业务");
            
			String start = request.substring(0, request.indexOf("<prodPropertys>") - 1);
			String end = request.substring(request.indexOf("<prodPropertys>"));
			String middle = "<prodSpecId>" + prodSpecId + "</prodSpecId>";

			return  businessAsCommonAction(start + middle + end);

		} catch (DocumentException e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}

	}
	
	/**
	 * 小区折扣查询接口
	 * <request>
		<offerSpecId>13313601459</offerSpecId>
		<serviceId>4</serviceId>
		<channelId>11040361</channelId>
		<staffCode>bj1001</staffCode>
	</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/offerSpecId", caption = "资费ID"),
						@Node(xpath = "//request/serviceId", caption = "服务ID"),
						@Node(xpath = "//request/staffCode", caption = "员工编码"),
						@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String queryCommunityDiscount(@WebParam(name = "request") String request) {
		Document doc;
		Document doc2;
		try {
			doc = WSUtil.parseXml(request);
			doc2 = WSUtil.parseXml(request);
			String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
			String serviceId = WSUtil.getXmlNodeText(doc, "//request/serviceId");
			int  countSpec = intfSMO.getOfferSpecAction2Count(offerSpecId);
			StringBuffer output = new StringBuffer("");
			if (countSpec==0)
				return WSUtil.buildResponse("-1", "此资费ID不能查询楼宇折扣信息");
			String buildingId= intfSMO.getComponentBuildingId(serviceId);
			if (buildingId==null)
				return WSUtil.buildResponse("-1", "此资费ID没有折扣信息");
			
			//String paidDiscount = intfSMO.getCommunityPolicy(buildingId);
			String paidDiscountStr = spServiceSMO.queryCommunityPolicy(buildingId);
			doc2 = DocumentHelper.parseText(paidDiscountStr);
			String paidDiscount = WSUtil.getXmlNodeText(doc2, "//communityPolicy/postPaidDiscount");
			if(paidDiscount ==null || paidDiscount.equals("")){
				paidDiscount = "100";
			}
			List<Map<String,Object>> baseInfoList = intfSMO.getComBasicInfoByOfferSpecId(offerSpecId);
			if (baseInfoList==null || baseInfoList.size() == 0)
				return WSUtil.buildResponse("-1", "此资费ID没有折扣信息");
			String resultMsg = "成功";
			output.append("<paidDiscount>").append(paidDiscount).append("</paidDiscount>");
			if(baseInfoList!=null && baseInfoList.size()>0){
				output.append("<discountInfoList>");
				for(int i=0;i<baseInfoList.size();i++){
					output.append("<discountInfo>");
					output.append("<discountNbr>").append(String.valueOf(baseInfoList.get(i).get("itemSpecId"))).append("</discountNbr>");
					output.append("<discountName>").append(String.valueOf(baseInfoList.get(i).get("name"))).append("</discountName>");
					output.append("</discountInfo>");
				}
				output.append("</discountInfoList>");
			}
			
			return WSUtil.buildResponse(ResultCode.SUCCESS,resultMsg,output.toString());

		} catch (DocumentException e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}
	
	
	/**
	 * 查询是否能够续费
	 *  
	 *  <request>
		<accnbr>102017001179</accnbr>
		<channelId>11040361</channelId>
		<staffCode>bj1001</staffCode>
	</request>
	 * @author hell
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accnbr", caption = "接入号"),
						@Node(xpath = "//request/staffCode", caption = "员工编码"),
						@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String queryRenewOfferSpecAttr(@WebParam(name = "request") String request) {
		Document doc;
		     Map<String, Object> result = new HashMap<String, Object>();
		try {
			String resultCode = "0";
			String resultMsg = "成功";
			doc = WSUtil.parseXml(request);
			String prodId = WSUtil.getXmlNodeText(doc, "//request/accnbr");
			
			List<Map> list = intfSMO.getOfferMemberInfo(prodId);
			if (list == null || list.size() <= 0) {
				resultCode = "1";
				resultMsg = "没有查询到记录！";
			}
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			result.put("lists", list);
			
			return mapEngine.transform("offerMemberInfo", result);
		} catch (DocumentException e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}
	
	/**
	 * 增加客户意向单
	 *  
	 *  <request>
		<channelId>11040361</channelId>
		<staffCode>bj1001</staffCode>
	</request>
	 * @author chelj
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/custId", caption = "客户编号"),
						@Node(xpath = "//request/staffCode", caption = "员工编码"),
						@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
	public String addIntentionOrder(@WebParam(name = "request") String request) {
		Document doc;
		     Map<String, Object> result = new HashMap<String, Object>();
		try {
			String resultMsg = "成功";
			String resultStr="";
			doc = WSUtil.parseXml(request);
			String custId = WSUtil.getXmlNodeText(doc, "//request/custId");
			String custName = intfSMO.getPartyNameByPartyId(Long.valueOf(custId));
			if(StringUtils.isBlank(custName))
				return WSUtil.buildResponse("-1", "根据此"+custId+"未查询到客户信息！");
				
				try {
					 resultStr = custManagerSmo.addIntentionOrder(request);
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
			return WSUtil.buildResponse(ResultCode.SUCCESS,resultMsg,resultStr);
		} catch (DocumentException e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
		}
	}
	 /**
		 * 查询客户意向单
		 *  
		 *  <request>
			<channelId>11040361</channelId>
			<staffCode>bj1001</staffCode>
		</request>
		 * @author chelj
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/staffCode", caption = "员工编码"),
							@Node(xpath = "//request/channelId", caption = "渠道的业务编码") })
		public String queryIntentionOrder(@WebParam(name = "request") String request) {
			Document doc;
			     Map<String, Object> result = new HashMap<String, Object>();
			try {
				String resultMsg = "成功";
				String resultStr="";
				doc = WSUtil.parseXml(request);
					try {
						 resultStr = custManagerSmo.queryIntentionOrder(request);
					} catch (Exception e) {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
					}
				return WSUtil.buildResponse(ResultCode.SUCCESS,resultMsg,resultStr);
			} catch (DocumentException e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}

			
		}
		
		 /**
		 *集团4G补换卡
		 *  
		 *  <request>
			<channelId>11040361</channelId>
			<staffCode>bj1001</staffCode>
		</request>
		 * @author chelj
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/staffCode", caption = "员工编码"),
							@Node(xpath = "//request/channelId", caption = "渠道的业务编码"),
							@Node(xpath = "//request/order/accNbr", caption = "接入号"),
							@Node(xpath = "//request/order/terminalCode", caption = "新卡卡号"),
							@Node(xpath = "//request/order/systemId", caption = "系统ID")
							
							})
		public String changeCardService(@WebParam(name = "request") String request) {
			Document doc;
			 Map<String, Object> preholdingMap = null;
			 String preholdingReJsStr="";
			try {
				
				doc = WSUtil.parseXml(request);
				
				String accNbr = WSUtil.getXmlNodeText(doc, "//request/order/accNbr");
				String apCharge = WSUtil.getXmlNodeText(doc, "//request/order/apCharge");
				String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
				String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
				String systemId = WSUtil.getXmlNodeText(doc, "//request/order/systemId");
				String terminalCode = WSUtil.getXmlNodeText(doc, "//request/order/terminalCode");
				//新增节点ip/与员工id
				String iPnumber = WSUtil.getXmlNodeText(doc, "//request/order/iPnUMBER");
				String handleCustId = WSUtil.getXmlNodeText(doc, "//request/order/handleCustId");
				
				//查询员工
				int staffCodeCout=intfSMO.getCmsStaffCodeByStaffCode(staffCode);
				
				if(staffCodeCout==0)
					return WSUtil.buildResponse(ResultCode.STAFF_NOT_EXIST);
				
				   //受理渠道
			    String channelNbr=intfSMO.getChannelNbrByChannelID(channelId);
			    
			    if(StringUtil.isEmpty(channelNbr))
			    	return WSUtil.buildResponse(ResultCode.QUERY_CHANNEL_NULL);
				
				//根据接入号获取产品信息
				Map<String, Object> prodInfo = intfSMO.getProdInfoByAccNbr(accNbr);
				
				if (prodInfo==null) 
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				
				Map<String, Object> code=intfSMO.queryTerminalCodeByProdId(prodInfo.get("prodId").toString());
                 
				if(code==null)
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"用户原始卡号不是在用状态,不能办理补换卡");
				
				
				
				
				JSONObject jsonUIM=createExchangeForProvince.generateQueryUIMList(terminalCode);
				//UIM卡查询
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				intfSMO.saveRequestInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+jsonUIM.toString(), requestTime);
				String uimReJsStr=spServiceSMO.exchangeForProvince(jsonUIM.toString(),"exchangeForProvince");
				Map<String, Object>  uimMap =createExchangeForProvince.getExchangeForProvinceResponse(uimReJsStr);
				intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+jsonUIM.toString(), requestTime, uimReJsStr, new Date(), "1","0");
			   
				if(StringUtil.isEmpty(uimReJsStr))
			    	return WSUtil.buildResponse(ResultCode.IMIINFO_IS_NULL);
			    
			    if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(uimMap.get("RspCode")))
			    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,uimMap.toString());
			    
			    Map<String, Object> mkt =createExchangeForProvince.getExchangeForProvinceMkt(uimReJsStr);
			    
			    if(mkt==null)
			    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,"无法解析UIM卡信息"+uimReJsStr);
			    
			    
				mkt.put("CHANNEL_NBR", channelNbr);
				mkt.put("SrcSysID", systemId);
				mkt.put("accNbr", accNbr); 
				mkt.put("staffCode", staffCode); 
			    
				String status_cd=String.valueOf(mkt.get("STATUS_CD"));
			    //预占
				if(WSDomain.ExchangeForProvinceCode.PREHOLDING_STATUS
						.equals(status_cd)){
					JSONObject preholdingjson=createExchangeForProvince.generateCardPreholDingJson(mkt);
					System.out.println(preholdingjson.toString());
					//预占
					String log2Id = intfSMO.getIntfCommonSeq();
					Date request2Time = new Date();
					intfSMO.saveRequestInfo(log2Id, "CrmJson", "changeCardOrder", "UIM预占："+preholdingjson.toString(), request2Time);
				    preholdingReJsStr=spServiceSMO.exchangeForProvince(preholdingjson.toString(),"exchangeForProvince");
				    intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "UIM预占："+preholdingjson.toString(), request2Time, preholdingReJsStr, new Date(), "1","0");
				    preholdingMap =createExchangeForProvince.getExchangeForProvinceResponse(preholdingReJsStr);
				}
			    
				if(WSDomain.ExchangeForProvinceCode.PREHOLDING_TYPE
						.equals(status_cd))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"：新UIM卡状态："+status_cd+",已补换过,请重新输入卡号");
				
				
				 if(preholdingMap!=null&&!WSDomain
						 .ExchangeForProvinceCode.EXCHANGE_OK.equals(preholdingMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,"预占失败："+preholdingMap.toString());
				 
				 
				 	//订单id 时间+4位随机数
					String  transID=createExchangeForProvince.generateTimeSeq(1);
					
					//根据终端设备号查询物品信息
					Map<String, Object> couponInfo = intfSMO.getCouponInfoByTerminalCode(terminalCode);
					
					//根据终端设备号查询询物品基本信息
					Map<String, Object> couponInfo2 = intfSMO.getBasicCouponInfoByTerminalCode(terminalCode);
					
					//Map<String, Object> devInfo = intfSMO.getDevInfoByCode(terminalCode);
					
					//根据staff_code查询staffId
					Map<String, Object> staffIdMap = intfSMO.getStaffIdByStaffCode(staffCode);
					Map<String, Object>  changeCardMap = new HashMap<String, Object>();
					changeCardMap.put("terminalCode", terminalCode);
					changeCardMap.put("staffId", staffIdMap.get("staffId"));
					changeCardMap.put("prodInfo", prodInfo);
					changeCardMap.put("couponInfo", couponInfo);
					changeCardMap.put("couponInfo2", couponInfo2);
					changeCardMap.put("channelId", channelId);
					changeCardMap.put("mkt", mkt);
					changeCardMap.put("code", code);
					changeCardMap.put("provIsale", transID);
					changeCardMap.put("apCharge", apCharge);
					//新增参数
					changeCardMap.put("iPnumber", iPnumber);
					changeCardMap.put("handleCustId", handleCustId);
					
					JSONObject jsonStr2=null;
					String	OrderResStr = "";
					try {
						jsonStr2 = createExchangeForProvince.generateOrderJson2(changeCardMap);
					} catch (Exception e) {
						e.printStackTrace();
						return  WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "用户当前使用的卡信息异常，查找不到对应的配置数据"+e.getMessage());
					}	
					//调用接口返回json
					String logId5 = intfSMO.getIntfCommonSeq();
					Date requestTime5 = new Date();
					intfSMO.saveRequestInfo(logId5, "CrmJson", "changeCardOrder", "提交订单："+jsonStr2.toString(), requestTime5);
					    	OrderResStr=spServiceSMO.exchangeForProvince(jsonStr2.toString(),"exchangeForProvince");
				    intfSMO.saveResponseInfo(logId5, "CrmJson", "changeCardOrder", "提交订单："+jsonStr2.toString(), requestTime5, OrderResStr, new Date(), "1","0");
					
				 Map<String, Object>  custResMap =createExchangeForProvince.getExchangeForProvinceResponse(OrderResStr);
				 if(custResMap==null)
					 return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR,"获取补换卡失败");
				 
				 if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(custResMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,OrderResStr);
				 
				 mkt.put("CUST_ID", prodInfo.get("partyId").toString());
				 //此处集团数据日志插入延迟，无法查询SaopRuleIntfLog中记录  所以休眠
				 Thread.sleep(5000);
				 Map<String, Object> logMap=intfSMO.getSaopRuleIntfLogByTransactionID(transID);
				 
				 if(logMap==null)
					 return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR,"获取交费订单失败");
				 
				 mkt.put("CUST_ORDER_ID", logMap.get("cust_order_id"));
				 mkt.put("EXT_CUST_ORDER_ID", transID);
				 JSONObject custorderjs=createExchangeForProvince.generateCustOrderJson(mkt);
				 //订单校验成功之后调用集团:查询订单费用信息   2015-11-11
				 JSONObject  feeInfoJson =createExchangeForProvince.getFeeInfoByCustOrderId(mkt);
				 String jsonString = spServiceSMO.exchangeForProvince(feeInfoJson.toString(),"exchangeForProvince");
				 JSONObject  getUpdateFeeJson =createExchangeForProvince.getUpdateFeeJsonByFeeInfo(jsonString,mkt);
				 
				 String log3Id = intfSMO.getIntfCommonSeq();
				 Date request3Time = new Date();
				 System.out.println(custorderjs.toString());
				 intfSMO.saveRequestInfo(log3Id, "CrmJson", "changeCardOrder", "4G补换卡交费通知："+custorderjs.toString(), request3Time);
				 //订单校验成功之后调用集团:更正费用信息   2015-11-11
				 String updateFeeInfoRelust = spServiceSMO.exchangeForProvince(getUpdateFeeJson.toString(),"exchangeForProvince");
				 //String custResJsStr=spServiceSMO.exchangeForProvince(custorderjs.toString(),"exchangeForProvince");
				 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + updateFeeInfoRelust);
				 Map<String, Object>   ResMap =createExchangeForProvince.getExchangeForProvinceResponse(updateFeeInfoRelust);
				 intfSMO.saveResponseInfo(log3Id, "CrmJson", "changeCardOrder", "4G补换卡交费通知："+custorderjs.toString(), request3Time, updateFeeInfoRelust, new Date(), "1","0");
				
				 if(ResMap==null)
					   return WSUtil.buildResponse(ResultCode.UNSUCCESS,"交费通知失败");
				 
				 if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(ResMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,ResMap.toString());
				 	
				 //增加返回值
				 return WSUtil.buildValidateResponse(ResultCode.SUCCESS,"custOrderId",logMap.get("cust_order_id").toString());
				 
//				 return WSUtil.buildResponse(ResultCode.SUCCESS);
			   } catch (Exception e) {
					e.printStackTrace();
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
				}
			}
		 /**
		 * 以旧换新旧串码同步
		 * @author jinsh
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/mktResInstCode", caption = "UIM卡号"),
							@Node(xpath = "//request/identityNumber", caption = "身份证件") })
		public String syncOldMktResInstService(@WebParam(name = "request") String request) {
			Document doc;
			     JSONObject preholdingjson = new JSONObject();
			     String resultStr="";
		     try {
				doc = WSUtil.parseXml(request);
				String mktResInstCode = WSUtil.getXmlNodeText(doc, "//request/mktResInstCode");
				String price = WSUtil.getXmlNodeText(doc, "//request/price");
				String phoneType = WSUtil.getXmlNodeText(doc, "//request/phoneType");
				String custName = WSUtil.getXmlNodeText(doc, "//request/custName");
				String identityNumber = WSUtil.getXmlNodeText(doc, "//request/identityNumber");
				
				preholdingjson.element("mktResInstCode", mktResInstCode);
				preholdingjson.element("price", price);
				preholdingjson.element("phoneType", phoneType);
				preholdingjson.element("custName", custName);
				preholdingjson.element("identityNumber", identityNumber);
				
						//调用服开依旧换新接口
						String logId = intfSMO.getIntfCommonSeq();
						Date requestTime = new Date();
						intfSMO.saveRequestInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+preholdingjson.toString(), requestTime);
						resultStr = spServiceSMO.syncOldMktResInstInfo(preholdingjson.toString(),"syncOldMktResInstInfo");
						intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+resultStr.toString(), requestTime, resultStr, new Date(), "1","0");
					   
					} catch (Exception e) {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
					}
					if(StringUtil.isEmpty(resultStr))
				    	return WSUtil.buildResponse(ResultCode.IMIINFO_IS_NULL);
					JSONObject jsonStr = new JSONObject();
					try {
						jsonStr = JSONObject.fromObject(resultStr);
					} catch (Exception e) {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,resultStr.toString());
					}
					String retCode = String.valueOf(jsonStr.get("retCode"));
					if(!retCode.equals("0")){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,resultStr);
					}
				return WSUtil.buildResponse(ResultCode.SUCCESS,resultStr);
		}
		 /**
		 * 校验号码是否可光改接口
		 * @since 2015-10-30
		 * @param request
		 * 校验规则：用户状态：在用用户产品：ADSL,VDSL,LAN,FTTH且接口方式为ADSL、VDSL、LAN、FTTB+ADSL、FTTB+VDSL、FTTB+LAN。
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码")})
		public String validatePhoneCanChangeBand(@WebParam(name = "request") String request) {
			Document doc;
		     List<Map<String,Object>> bandList;
		     try {
				doc = WSUtil.parseXml(request);
				String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
				//accNbr = B23226891
				bandList = intfSMO.validatePhoneCanChangeBand(accNbr);
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
				if(bandList==null || bandList.size()<=0){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"该号码不能做光改");
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS);
		}
		 /**
		 * 根据主销售品查询打包关系
		 * @since 2015-10-30
		 * @param request
		 * 校验规则：1．校验入参的主销售是否为主销售品 2．查询打包关系（offer_package_2_offer_spec）
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/offerSpecId", caption = "主销售品")})
		public String queryMainOfferPackageRelation(@WebParam(name = "request") String request) {
			Document doc;
		     List<Map<String,Object>> packageList;
		     StringBuffer result = new StringBuffer("");
		     try {
					doc = WSUtil.parseXml(request);
					String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
					packageList = intfSMO.queryMainOfferPackageRelation(offerSpecId);
					if(packageList!=null && packageList.size()>0){
						for(Map<String,Object> m : packageList){
							result.append("<offerSpec>");
							result.append("<offerSpecId>").append(m.get("OFFER_SPEC_ID")).append("</offerSpecId>");
							result.append("<offerTypeCd>").append(m.get("NAME")).append("</offerTypeCd>");
							result.append("</offerSpec>");
						}
					}else {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,"没有查询到相关销售品打包关系");
					}
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS,result.toString());
		}
		
		/**
		 * 查询号码信息及是否存在组合
		 * @since 2015-10-30
		 * @param request
		 * 校验规则：1．入参号码是否为在用用户2．查询相关信息 
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码")})
		public String queryAccNbrInfoByAccessNumber(@WebParam(name = "request") String request) {
			Document doc;
			StringBuffer result = new StringBuffer("");
			String prodSpecId = "";
			result.append("<response>");
		     List<Map<String,Object>> accNbrInfoList;
		     List<Map<String,Object>> BAccNbrInfoList;
		     //组合号码
		     String compAccNbr = "";
		     //光改业务动作
		     String busiType = "";
		     String compProdId = "";
		     try {
				doc = WSUtil.parseXml(request);
				String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
				//1、查询产品规格，B号的主销售品、B 号的附属销售品订购 accNbr = B22160678/B22698522
				accNbrInfoList = intfSMO.queryAccNbrInfoList(accNbr);
				if(accNbrInfoList!=null && accNbrInfoList.size()>0){
					prodSpecId = accNbrInfoList.get(0).get("REDU_PROD_SPEC_ID").toString();
					result.append("<prodSpecId>").append(prodSpecId).append("</prodSpecId>");
					for(Map<String,Object> m : accNbrInfoList){
						result.append("<bOfferInfo>");
						result.append("<offerSpecId>").append(m.get("OFFER_SPEC_ID")).append("</offerSpecId>");
						result.append("<offerTypeCd>").append(m.get("OFFER_TYPE_CD")).append("</offerTypeCd>");
						result.append("</bOfferInfo>");
					}
				}
				//2、根据接入号码查询：item_spec_id = 12389为接入方式，item_spec_id in(12,13),为产品速率属性、属性值
				String itemSpecIds = "";
				Map<String,Object> speedInfo2 = null;
				itemSpecIds = "12389";
				Map<String,Object> accessType = intfSMO.queryAccessTypeByAccessNumber(accNbr,itemSpecIds);
				result.append("<accessType>").append(accessType.get("NAME")).append("</accessType>");
				//产品速率属性、属性值
				itemSpecIds = "12";
				Map<String,Object> speedInfo = intfSMO.queryAccessTypeByAccessNumber(accNbr,itemSpecIds);
				if(speedInfo == null || speedInfo.equals("")){
					itemSpecIds = "13";
				    speedInfo2 = intfSMO.queryAccessTypeByAccessNumber(accNbr,itemSpecIds);
				}
				if(speedInfo!=null && !speedInfo.equals("")){
					result.append("<speedCode>").append(speedInfo.get("ITEM_SPEC_ID")).append("</speedCode>");
					result.append("<speedValue>").append(speedInfo.get("VALUE")).append("</speedValue>");
				}else if(speedInfo2!=null && !speedInfo2.equals("")){
					result.append("<speedCode>").append(speedInfo2.get("ITEM_SPEC_ID")).append("</speedCode>");
					result.append("<speedValue>").append(speedInfo2.get("VALUE")).append("</speedValue>");
				}
				//3、查询是否存在组合标识、组合号码
				List<Map<String,Object>> getProdCompList = intfSMO.getProdCompList(accNbr);
				//getProdCompList有值，说明存在组合标示
				//如果B号的组合号码存在，则查询组合号码的主销售品、组合号码的附属销售品订购
				if(getProdCompList!=null && getProdCompList.size()>0){
					result.append("<isComp>").append("Y").append("</isComp>");
					compProdId = getProdCompList.get(0).get("COMP_PROD_ID").toString();
					result.append("<compInfo>");
					//根据compProdId查询查询B号的组合号码
					compAccNbr = intfSMO.queryCompAccNbrByCompProdId(compProdId).get("REDU_ACCESS_NUMBER").toString();
					result.append("<compAccNbr>").append(compAccNbr).append("</compAccNbr>");
					BAccNbrInfoList = intfSMO.queryAccNbrInfoList(compAccNbr);
					for(Map<String,Object> n : BAccNbrInfoList){
						result.append("<compOfferInfo>");
						result.append("<offerSpecId>").append(n.get("OFFER_SPEC_ID")).append("</offerSpecId>");
						result.append("<offerTypeCd>").append(n.get("OFFER_TYPE_CD")).append("</offerTypeCd>");
						result.append("</compOfferInfo>");
					}
					result.append("</compInfo>");
				}else{
					result.append("<isComp>").append("N").append("</isComp>");
				}
				//4、查询光改业务动作
				List<Map<String,Object>> busiTypeList = intfSMO.validatePhoneCanChangeBand(accNbr);
			    String prodSpecId2 = busiTypeList.get(0).get("REDU_PROD_SPEC_ID").toString();
				String value = String.valueOf(busiTypeList.get(0).get("VALUE"));
				if(prodSpecId2.equals("890000050")||prodSpecId2.equals("9")){
					busiType = "106";
				}else if(prodSpecId2.equals("10")){
					busiType = "183";
				}
				//FTTH离散值对应接入方式 1:LAN, 4:ADSL,5:VDSL ,6:FTTB+ADSL, 7:FTTB+VDSL,8:FTTB+LAN 
				 if(value!=null && !value.equals("") && (value.equals("1")||value.equals("8"))){
					 busiType = "184";
				}else if(value!=null && !value.equals("") && (value.equals("4"))){
					busiType = "198";
			    }else if(value!=null && !value.equals("") && (value.equals("6"))){
			    	 busiType = "200";
			    }else if(value!=null && !value.equals("") && (value.equals("5"))){
			    	 busiType = "199";
			    }else if(value!=null && !value.equals("") && (value.equals("7"))){
			    	 busiType = "201";
			    }
				 result.append("<busiType>").append(busiType).append("</busiType>");
				 result.append("<resultCode>").append(0).append("</resultCode>");
				 result.append("</response>");
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
				if(accNbrInfoList==null || accNbrInfoList.size()<=0){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"没有查询到相关销售品打包关系");
				}
				//return WSUtil.buildResponse(ResultCode.SUCCESS,result.toString());
				return result.toString();
		}
		 /**
		 * 根据当前销售品获得速率属性及值
		 * @since 2015-11-04
		 * @param request
		 * 校验规则：1．校验入参的主销售品是否为主销售品 2．查询销售品的速率属性ID及值信息（查询销售品的速率配置要求）
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/offerSpecId", caption = "主销售品")})
		public String querySpeedInfoByOfferSpecId(@WebParam(name = "request") String request) {
			Document doc;
		     Map<String,Object> speedInfo ;
		     StringBuffer result = new StringBuffer("");
		     try {
					doc = WSUtil.parseXml(request);
					String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
					//判断当前销售品是否为主销售品
					boolean flag = intfSMO.judgeMainOfferByOfferSpecId(offerSpecId);
					if(!flag){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,"当前销售品不是主销售品，请确认");
					}
					speedInfo = intfSMO.querySpeedInfoByOfferSpecId(offerSpecId);
					if(speedInfo!=null && !speedInfo.equals("")){
						result.append("<itemSpecId>").append(speedInfo.get("ITEM_SPEC_ID").toString()).append("</itemSpecId>");
						result.append("<value>").append(speedInfo.get("VALUE").toString()).append("</value>");
					}else {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,"没有查询到相关销售品速率");
					}
				} catch (Exception e) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS,result.toString());
		}
		/**
		 * 根据当前销售品查询可换档的主销售品
		 * @since 2015-11-04
		 * @param request
		 * 校验规则：分为两种情况：
		 *1.宽带产品
		 *1）校验入参的主销售品是否为ADSL、VDSL、LAN、FTTH宽带这四个产品的主销售品
		 *2）查询FTTH宽带产品的所有主销售品
		 *2.组合产品（E家、自主整合）
		 *1）判断当前主销售品是哪种产品的主销售品
		 *2）根据产品获得当前产品除当前主销售品外的其它主销售品
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/offerSpecId", caption = "主销售品")})
		public String queryChangeOffersByOfferSpecId(@WebParam(name = "request") String request) {
			Document doc;
			List<Map<String,Object>> changeOffersList ;
			List<Map<String,Object>> compInfoList ;
			List<Map<String,Object>> compMainOfferList ;
			boolean flag = false;
			StringBuffer result = new StringBuffer("");
			try {
				doc = WSUtil.parseXml(request);
				String offerSpecId = WSUtil.getXmlNodeText(doc, "//request/offerSpecId");
				//1、判断当前销售品是否为主销售品
				flag = intfSMO.judgeMainOfferByOfferSpecId(offerSpecId);
				if(!flag){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"当前销售品不是主销售品，请确认");
				}
				//2、判断当前主销售品对应的产品是否为组合产品 offerSpecId = 10001782,获取组合产品信息
				String prodSpecId = "";
				compInfoList = intfSMO.queryCompInfoListByOfferSpecId(offerSpecId);
				Map<String,Object> mv = new HashMap<String, Object>(); 
				if(compInfoList!=null && compInfoList.size()>0){
					prodSpecId = compInfoList.get(0).get("PROD_SPEC_ID").toString();
					mv.put("offerSpecId", offerSpecId);
					mv.put("prodSpecId", prodSpecId);
				//3、如果是组合产品则根据当前产品查询该产品对应的所有主销售品（除当前主销售品外） prod_spec_id = 600012009
				    compMainOfferList = intfSMO.queryCompMainOfferByProdSpecId(mv);
				    if(compMainOfferList!=null && compMainOfferList.size()>0){
						for(Map<String,Object> m : compMainOfferList){
							result.append("<offerSpec>");
							result.append("<offerSpecId>").append(m.get("offerSpecId")).append("</offerSpecId>");
							result.append("<name>").append(m.get("name")).append("</name>");
							result.append("</offerSpec>");
						}
					}
				}
				//4、如果不是组合产品，则查询FTTH宽带产品的所有主销售品
				changeOffersList = intfSMO.queryChangeOffersByOfferSpecId(offerSpecId);
				if(changeOffersList!=null && changeOffersList.size()>0){
					for(Map<String,Object> m : changeOffersList){
						result.append("<offerSpec>");
						result.append("<offerSpecId>").append(m.get("offerSpecRelaRange")).append("</offerSpecId>");
						result.append("<name>").append(m.get("name")).append("</name>");
						result.append("</offerSpec>");
					}
				} if((compInfoList==null || compInfoList.size()< 0) &&(changeOffersList==null || changeOffersList.size()<0)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"没有查询到可换档的主销售品");
				}
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
			return WSUtil.buildResponse(ResultCode.SUCCESS,result.toString());
		}
		/**
		 * 渠道单元（社会渠道、自有渠道）、渠道单元与经营主体关系同步
		 * @since 2016-01-14
		 * @param request
		 * @return
		 */
		@WebMethod
		public String sYncChannel2Prm(@WebParam(name = "request") String request) {
			Document doc = null;
			Document doc2;
			String resultStr = null;
			String transactionID = "";
			String rspCode = "";
			String channelType = "";
			StringBuffer strBuffer = new StringBuffer();
			Map<String, String> retStr = new HashMap<String,String>();
			try {
				doc = WSUtil.parseXml(request);
				String infoXML = doc.selectSingleNode("//request").asXML();
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				intfSMO.saveRequestInfo(logId, "CrmWebService", "sYncChannel2Prm", "渠道单元（社会渠道、自有渠道）、渠道单元与经营主体关系同步："+infoXML.toString(), requestTime);
				resultStr = soServiceImpl.syncChannelFCsb(infoXML);
				//根据返回值判断是否为社会渠道，如果是则调用社会渠道同步接口，同步esb，如果不是，则调用非社会渠道同步接口，同步esb
				doc2 = DocumentHelper.parseText(resultStr);
				rspCode = WSUtil.getXmlNodeText(doc2, "//response/RspCode");
				channelType = WSUtil.getXmlNodeText(doc2, "//response/ChannelType");
			    //如果调用crm成功
				String channelClass = WSUtil.getXmlNodeText(doc, "//request/info/CHANNEL/CHANNEL_CLASS");//feixu新增店中商走ESB流程
				if("CRM_000".equals(rspCode) && "3".equals(channelType)||"CRM_000".equals(rspCode) && "20".equals(channelClass)){
					//String channelOperRela = WSUtil.getXmlNodeText(doc, "//request/info/CHANNEL_OPERATORS_RELA");
					Element channelOperRelas = (Element) doc.selectSingleNode("request/info/CHANNEL_OPERATORS_RELAS");
					Element channel  = (Element) doc.selectSingleNode("request/info/CHANNEL");
					//如果只有社会渠道或渠道关系和社会渠道都有
					if (channel != null) {
						retStr = entityService.sYncCh2ChannelFCrm(infoXML);
					}
					//如果只有渠道关系
					else if(channel == null && channelOperRelas != null){
						retStr = entityService.sYncCh2OpFCrm(infoXML);
					}
					//如果渠道关系和社会渠道都有
//					else if(channel != null && channelOperRelas != null){
//						retStr = entityService.sYncCh2ChannelFCrm(infoXML);
//						if("0".equals(retStr.get("RspCode"))){
//							retStr = entityService.sYncCh2OpFCrm(infoXML);
//						}
//					}
					strBuffer.append("<response>");
					strBuffer.append("<TransactionID>").append(retStr.get("TransactionID")).append("</TransactionID>");
					strBuffer.append("<RspTime>").append(new Date()).append("</RspTime>");
					strBuffer.append("<RspCode>").append(retStr.get("RspCode")).append("</RspCode>");
					strBuffer.append("<RspDesc>").append(retStr.get("RspDesc")).append("</RspDesc>");
					strBuffer.append("</response>");
					intfSMO.saveResponseInfo(logId, "CrmWebService", "sYncChannel2Prm", "返回数据："+strBuffer.toString(), requestTime, resultStr, new Date(), "1","0");
					return String.valueOf(strBuffer);
				}else{
					intfSMO.saveResponseInfo(logId, "CrmWebService", "sYncChannel2Prm", "返回数据："+strBuffer.toString(), requestTime, resultStr, new Date(), "1","0");
					return resultStr;
				}
			} catch (Exception e) {
				Date rspTime = new Date();
				strBuffer.append("<response>");
				strBuffer.append("<TransactionID>").append(WSUtil.getXmlNodeText(doc, "//request/TransactionID")).append("</TransactionID>");
				strBuffer.append("<RspTime>").append(rspTime).append("</RspTime>");
				strBuffer.append("<RspCode>").append("1").append("</RspCode>");
				strBuffer.append("<RspDesc>").append("系统异常："+e.getMessage().substring(0,1000)).append("</RspDesc>");
				strBuffer.append("</response>");
				return String.valueOf(strBuffer);
//				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 * 员工信息同步:同步系管
		 * @since 2016-01-20
		 * @param request
		 * @return
		 */
		@WebMethod
		public String sYncStaff2Crm(@WebParam(name = "request") String request) {
			Document doc = null;
			String resultStr = null;
			StringBuffer strBuffer= new StringBuffer();
			try {
				doc = WSUtil.parseXml(request);
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String infoXML = doc.selectSingleNode("//request").asXML();
				intfSMO.saveRequestInfo(logId, "CrmWebService", "sYncStaff2Crm", "员工信息同步:同步系管："+infoXML.toString(), requestTime);
				resultStr = intfSMO.sYncStaff2Crm(infoXML);
				intfSMO.saveResponseInfo(logId, "CrmWebService", "sYncStaff2Crm", "返回数据："+infoXML.toString(), requestTime, resultStr, new Date(), "1","0");
			} catch (Exception e) {
				Date rspTime = new Date();
				strBuffer.append("<response>");
				strBuffer.append("<TransactionID>").append(WSUtil.getXmlNodeText(doc, "//request/TransactionID")).append("</TransactionID>");
				strBuffer.append("<RspTime>").append(rspTime).append("</RspTime>");
				strBuffer.append("<RspCode>").append("1").append("</RspCode>");
				strBuffer.append("<RspDesc>").append("系统异常："+e.getMessage().substring(0,100)).append("</RspDesc>");
				strBuffer.append("</response>");
				return String.valueOf(strBuffer);
			}
			return resultStr;
		}
		/**
		 * 员工与渠道关系同步：同步crm
		 * @since 2016-01-14
		 * @param request
		 * @return
		 */
		@WebMethod
		public String sYncStaff2ChTCrm(@WebParam(name = "request") String request) {
			Document doc = null;
			String resultStr = null;
			StringBuffer strBuffer= new StringBuffer();
			try {
				doc = WSUtil.parseXml(request);
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String infoXML = doc.selectSingleNode("//request").asXML();
				intfSMO.saveRequestInfo(logId, "CrmWebService", "sYncStaff2Crm", "员工与渠道关系同步：同步crm："+infoXML.toString(), requestTime);
				resultStr = soServiceImpl.syncStaff2ChFCsb(infoXML);
				intfSMO.saveResponseInfo(logId, "CrmWebService", "sYncStaff2Crm", "返回数据："+resultStr.toString(), requestTime, resultStr, new Date(), "1","0");
			} catch (Exception e) {
				Date rspTime = new Date();
				strBuffer.append("<response>");
				strBuffer.append("<TransactionID>").append(WSUtil.getXmlNodeText(doc, "//request/TransactionID")).append("</TransactionID>");
				strBuffer.append("<RspTime>").append(rspTime).append("</RspTime>");
				strBuffer.append("<RspCode>").append("1").append("</RspCode>");
				strBuffer.append("<RspDesc>").append("系统异常："+e.getMessage().substring(0,1000)).append("</RspDesc>");
				strBuffer.append("</response>");
				return String.valueOf(strBuffer);
			}
			return resultStr;
		}
		 /**
		 *  电信客服受理集团4G业务
		 *  可选包变更、附属产品变更、挂失、解挂、停机保号、复机、国际漫游、国际长途业务
		 * @author jinsh
		 * @param request
		 * @return
		 */
		@WebMethod
		public String businessServiceFor4G(@WebParam(name = "request") String request) {
			String mainKey = "";
			Document doc;
			String resultDesc = "";
			try {
				// 1.报文转换
				Document document = WSUtil.parseXml(request);
				// 1.1回执单是否需要带协议
				String ifAgreementStr = WSUtil.getXmlNodeText(document, "request/order/ifAgreementStr");
				if (StringUtils.isBlank(ifAgreementStr)) {
					ifAgreementStr = "N";
				}
				Element order = (Element) document.selectSingleNode("request/order");
				String orderTypeId = WSUtil.getXmlNodeText(document, "request/order/orderTypeId");
				// 1.2补充用户产品或者主接入号信息
				String prodId = null;
				String accessNumber = null;
				Element prodIdNode = (Element) document.selectSingleNode("request/order/prodId");
				Element accessNumberNode = (Element) document.selectSingleNode("request/order/accessNumber");
				if (accessNumberNode != null) {
					accessNumber = accessNumberNode.getText();
				}
				if (prodIdNode != null) {
					prodId = prodIdNode.getText();
				}else{
					 prodId = String.valueOf(soQuerySMO.queryProdIdByAcessNumber(accessNumber));
				}
				if(prodId==null || "".equals(prodId) || prodId.equals("null")){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"没有找到对应的产品Id，请确认！");
				}
				//获得mainKey 和 orderTypeId 确定唯一主键表 checkBusinessServiceOrder 
				if (StringUtils.isNotBlank(accessNumber)) {
					mainKey = accessNumber;
				} else if (StringUtils.isNotBlank(prodId)) {
					mainKey = prodId;
				}
				if (StringUtils.isBlank(accessNumber) && StringUtils.isNotBlank(prodId)) {
					accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodId));
					if (accessNumber != null) {
						if (accessNumberNode == null) {
							order.addElement("accessNumber").setText(accessNumber);
						} else {
							accessNumberNode.setText(accessNumber);
						}
					} else {
						return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST, "用户不存在！");
					}
				}
				// 1.3用户是否欠费，若欠费，限制其做停机保号和停机保号复机业务
				long start = System.currentTimeMillis();
				String sid = WSUtil.getXmlNodeText(document, "//request/order/systemId");
				String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
				String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
				// 1.4把报文中的OfferSpecs转换为ServicePak和PricePlanPak节点
				start = System.currentTimeMillis();
				//根据prodId取得prodStatus
				Long prodStatusCd = intfSMO.getProdStatusByProdId(prodId);
				//根据StaffNumber 查询staffId
				String staffId = intfSMO.queryStaffIdByStaffCode(WSUtil.getXmlNodeText(document, "//request/staffCode"));
				if(staffId==null || "".equals(staffId)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"您输入的员工不存在，请确认！");
				}
				//集团4G报文拼接：
				Map<String,Object>  paramMap = new HashMap<String,Object>();
				//1、 用户状态变更（挂失、解挂、停机保号、复机）:获取4G报文
				if("1171".equals(orderTypeId) || "1172".equals(orderTypeId)||"19".equals(orderTypeId)||"20".equals(orderTypeId)||"1166".equals(orderTypeId)){
					paramMap.put("ACTION_TYPE", "");
					paramMap.put("CUST_ID", staffCode);
					paramMap.put("channelId", channelId);
					paramMap.put("systemId", sid);
					paramMap.put("PROD_ID", prodId);
					paramMap.put("staffId", staffId);
					paramMap.put("orderTypeId", orderTypeId);
					paramMap.put("prodStatusCd", prodStatusCd);
					String sendStrXML = createExchangeForProvince.getSendStringXML(paramMap);
					
					String logId = intfSMO.getIntfCommonSeq();
					Date requestTime = new Date();
					intfSMO.saveRequestInfo(logId, "CrmJson", "changeCardOrder", "用户状态变更："+sendStrXML, requestTime);
					//解析集团返回XML报文
					Date date1 = new Date();
					String returnStr=spServiceSMO.exchangeForProvince(sendStrXML,"exchangeForProvince");
					System.out.println(">>>>>>date2-date1:" + (new Date().getSeconds()-date1.getSeconds()));
					doc = DocumentHelper.parseText(returnStr);
					String rspCode = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspCode");
					String rspDesc = WSUtil.getXmlNodeText(doc, "//ContractRoot/TcpCont/Response/RspDesc");
					intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "用户状态变更："+ sendStrXML, requestTime, returnStr, new Date(), "1","0");
					//如果不成功 
					if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(rspCode)){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS,rspDesc);
					}else{
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					}
				}
				//2、订购功能产品、订购可选包:获取4G报文
				String offerInfoXML="";
				if("17".equals(orderTypeId)){
					paramMap.put("document", document);
					paramMap.put("PROD_ID", prodId);
					paramMap.put("systemId", sid);
					paramMap.put("ACTION_TYPE", "");
					//样例
					Map<String,String> retMap = new HashMap<String,String>();
					retMap = createExchangeForProvince.getOfferInfoXML(paramMap);
					String flag = retMap.get("flag");
					if("1".equals(flag)){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "您订购的" + retMap.get("msg") + "不存在,请确认！"); 
					}
					offerInfoXML = retMap.get("msg");
					System.out.println(">>>>>>>>>>>>offerInfoXML:" + offerInfoXML);
					try {
						String logId2 = intfSMO.getIntfCommonSeq();
						Date requestTime2 = new Date();
						intfSMO.saveRequestInfo(logId2, "CrmJson", "changeCardOrder", "订购退订附属产品："+offerInfoXML, requestTime2);
						Date date1 = new Date();
						String uimReJsStr=spServiceSMO.exchangeForProvince(offerInfoXML,"exchangeForProvince");
						intfSMO.saveResponseInfo(logId2, "CrmJson", "changeCardOrder", "订购退订附属产品："+ offerInfoXML, requestTime2, uimReJsStr, new Date(), "1","0");
						System.out.println(">>>>>>date2-date1:" + (new Date().getSeconds()-date1.getSeconds()));
						System.out.println(">>>>>>>>>>>>>>>>>" + uimReJsStr);
						Document document2 = WSUtil.parseXml(uimReJsStr);
						String resultCode = WSUtil.getXmlNodeText(document2, "//ContractRoot/TcpCont/Response/RspCode");
						resultDesc = WSUtil.getXmlNodeText(document2, "//ContractRoot/TcpCont/Response/RspDesc");
						if(resultCode.equals("0")||resultCode.equals("0000")){
							return WSUtil.buildResponse(ResultCode.SUCCESS);
						}else{
							return WSUtil.buildResponse(ResultCode.UNSUCCESS, resultDesc);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
					}
				}
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} catch (Exception e) {
				logger.error("businessService一点通:" + request, e);
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		 /**
		 *  查询历史附属销售品
		 * @author jinsh
		 * @param request
		 * @since 2016-02-02
		 * @return
		 */
		@WebMethod
		public String queryHistoryAttachInfo(@WebParam(name = "request") String request) {
			long start = System.currentTimeMillis();
			try {
			Document document = WSUtil.parseXml(request);
			StringBuffer strBuffer = new StringBuffer();
			// 1.1回执单是否需要带协议
			String accNbr = WSUtil.getXmlNodeText(document, "request/accNbr");
			Long prodId = soQuerySMO.queryProdIdByAcessNumber(accNbr);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			List<AttachOfferDto> attachOffers = new ArrayList<AttachOfferDto>();
			if (prodId != null) {
				start = System.currentTimeMillis();
				// 根据产品号码查询附属销售品（已订购）
				attachOffers = intfSMO.queryAttachOfferInfo(prodId);
			}else{
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询prodId为空，请确认");
			}
			if(attachOffers==null || attachOffers.size()<0){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS,"查询结果为空");
			}else{
				strBuffer.append("<response>");
				strBuffer.append("<offerSpecs>");
				for(int i=0;i<attachOffers.size();i++){
					Date startDt = attachOffers.get(i).getOfferMembers().get(0).getStartDt();
					Date endDt = attachOffers.get(i).getOfferMembers().get(0).getEndDt();
					String startDate = "";
					String endDate = "";
					if(startDt!= null && !startDt.equals("")){
						startDate  = df.format(startDt);
					}
					if(endDt!= null && !endDt.equals("")){
						endDate  = df.format(endDt);
					}
					strBuffer.append("<offerSpec>");
						strBuffer.append("<offerSpecId>").append(attachOffers.get(i).getOfferSpecId()).append("</offerSpecId>");
						strBuffer.append("<offerSpecName>").append(attachOffers.get(i).getOfferSpecName()).append("</offerSpecName>");
						strBuffer.append("<offerId>").append(attachOffers.get(i).getOfferId()).append("</offerId>");
						strBuffer.append("<statusCd>").append(attachOffers.get(i).getStatusCd()).append("</statusCd>");
						strBuffer.append("<startDt>").append(startDate).append("</startDt>");
						strBuffer.append("<endDt>").append(endDate).append("</endDt>");
					strBuffer.append("</offerSpec>");
				}
				strBuffer.append("</offerSpecs>");
				strBuffer.append("<resultCode>").append("0").append("</resultCode>");
				strBuffer.append("</response>");
				return String.valueOf(strBuffer.toString());
			}
			}catch (Exception e) {
				logger.error("queryHistoryAttachInfo:" + request, e);
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		 /**
		 *  查询历史附属销售品
		 * @author 
		 * @param request
		 * @since 2016-02-02
		 * @return
		 */
		@WebMethod
		public String queryInfoForFTTH(@WebParam(name = "request") String request) {
			long start = System.currentTimeMillis();
			StringBuffer strBuffer = new StringBuffer();
			try {
				Document document = WSUtil.parseXml(request);
				String prodId = WSUtil.getXmlNodeText(document, "request/prodId");
				if (prodId != null) {
						// 通过partyId查offerId,prodSpecId,offerSpecId
						Map<String, Object> methForFtth = null;
						Map<String, Object> addresForFtth = null;
						Map<String, Object> TmlForFtth = null;
						String offerId = "",offerSpecId = "",prodSpecId = "",value ="";
						String addressId = "",addressDesc = "",reasonCd = "",areaId ="";
						String tmlId = "",reasonCdm = "",tmlName = "";
						start = System.currentTimeMillis();
						methForFtth = intfSMO.getMethForFtth(prodId);
						if(methForFtth != null){
							offerId = (String) methForFtth.get("OFFER_ID").toString()+"";
							offerSpecId = (String) methForFtth.get("OFFER_SPEC_ID").toString()+"";
							prodSpecId = (String) methForFtth.get("REDU_PROD_SPEC_ID").toString()+"";
						}else{
							return WSUtil.buildResponse(ResultCode.UNSUCCESS,"查询结果为空");
						}
						//通过prodId查询property的value值
						value = intfSMO.getProValue(prodId);
						//通过prodId查询地址参数
						addresForFtth = intfSMO.getaddresForFtth(prodId);
						if(addresForFtth != null){
							addressId = addresForFtth.get("ADDRESS_ID").toString()+"";
							addressDesc = addresForFtth.get("ADDRESS_DESC").toString()+"";
							reasonCd = addresForFtth.get("REASON_CD").toString()+"";
							areaId = addresForFtth.get("AREA_ID").toString()+"";
						}else{
							addressId = "";
							addressDesc = "";
							reasonCd = "";
							areaId = "";
						}
						//通过prodId查询tml参数
						TmlForFtth = intfSMO.getTmlForFtth(prodId);
						if(TmlForFtth != null){
							tmlId = TmlForFtth.get("TML_ID").toString();
							reasonCdm = TmlForFtth.get("REASON_CD").toString();
							tmlName = TmlForFtth.get("NAME").toString();
						}else{
							tmlId = "";
							reasonCdm = "";
							tmlName = "";
						}
						strBuffer.append("<response>");
						strBuffer.append("<resultCode>0</resultCode><resultMsg>成功</resultMsg>");
						strBuffer.append("<OrderOffer>");
						strBuffer.append("<offerId>").append(offerId).append("</offerId>");
						strBuffer.append("<prodSpecId>").append(prodSpecId).append("</prodSpecId>");
						strBuffer.append("<offerSpecId>").append(offerSpecId).append("</offerSpecId>");
						strBuffer.append("<prodPropertys><property><id>12389</id><name>FTTX属性标识</name>");
						strBuffer.append("<value>").append(value).append("</value>");
						strBuffer.append("<actionType>0</actionType></property></prodPropertys></OrderOffer><boProdAddresses><Addr>");
						strBuffer.append("<addrId>").append(addressId).append("</addrId>");
						strBuffer.append("<addrStd>").append(addressDesc).append("</addrStd>");
						strBuffer.append("<addrDetail>").append(addressDesc).append("</addrDetail>");
						strBuffer.append("<reasonCd>").append(reasonCd).append("</reasonCd>");
						strBuffer.append("<areaId>").append(areaId).append("</areaId>");
						strBuffer.append("<areaName>北京</areaName>");
						strBuffer.append("</Addr></boProdAddresses><boProdTml><Tml>");
						strBuffer.append("<tmlId>").append(tmlId).append("</tmlId>");
						strBuffer.append("<tmlName>").append(tmlName).append("</tmlName>");
						strBuffer.append("<reasonCd>").append(reasonCdm).append("</reasonCd>");
						strBuffer.append("</Tml></boProdTml></response>");
				 if (isPrintLog) {
							System.out.println("拼装Ftth报文 执行时间："
									+ (System.currentTimeMillis() - start));
						}
					}else{
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询prodId为空，请确认");
					}
				return strBuffer.toString();
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		/**
		 *  光改订单提交
		 * @author 
		 * @param request
		 * @since 2016-02-02
		 * @return
		 */
		@WebMethod
		public String soAutoServiceForFTTH(@WebParam(name = "request") String request) {
			long start = System.currentTimeMillis();
			try {
				StringBuilder sb = new StringBuilder();
				String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "soAutoServiceForFTTH", "光改订单提交："+request, requestTime2);
				String result ="";
				result = soServiceSMO.soAutoServiceForFTTH(request);
				intfSMO.saveResponseInfo(logId2, "CrmWebService", "soAutoServiceForFTTH", "光改订单提交："+ request, requestTime2, result, new Date(), "1","0");
				JSONObject resultJson = JSONObject.fromObject(result);
				logger.error("订单提交返回结果：{}", resultJson);
				if ((resultJson.getString("resultCode")).equals("0")) {
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<olId>").append(resultJson.getString("olId")).append("</olId>");
//					sb.append("<olNbr>").append(resultJson.getString("olNbr")).append("</olNbr>");
//					sb.append("<pageInfo>").append(resultJson.getString("pageInfo")).append("</pageInfo>");
//					sb.append("<payIndentId>").append(resultJson.getString("payIndentId")).append("</payIndentId>");
					sb.append("</response>");
					return sb.toString();
				}if((resultJson.getString("resultCode")).equals("1")) {
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(resultJson.getString("resultMsg")).append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}
				if (isPrintLog) {
						System.out.println("调用光改订单提交 执行时间："
								+ (System.currentTimeMillis() - start));
					}
				return result;
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		/**
		 *  FTTH正装机同网络部资源系统
		 * @author 
		 * @param request
		 * @since 
		 * @return
		 */
		@WebMethod
		public String netWorkResourcesForFTTH(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String name = WSUtil.getXmlNodeText(document, "request/name");
				List<Map<String,Object>> tmllist ;
				tmllist = intfSMO.queryExchangeByName(name);
				StringBuilder res = new StringBuilder();
				if(tmllist.size() < 1){
					res.append("<response>");
					res.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					res.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					res.append("</response>");
					return res.toString();
				}
				
				logger.error("订单提交返回结果：{}", res.toString());
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				res.append("<tmllist>");
					for(Map<String,Object> tml :tmllist){
						res.append("<tml>");
						res.append("<tmlId>").append(tml.get("TML_ID")).append("</tmlId>");
						res.append("<name>").append(tml.get("NAME")).append("</name>");
						res.append("<description>").append(tml.get("DESCRIPTION")).append("</description>");
						res.append("</tml>");
					}
				res.append("</tmllist>");
				res.append("</response>");
				
				return res.toString();
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		/**
		 *  根据B号查C号
		 * @author 
		 * @param request
		 * @since 
		 * @return
		 */
		@WebMethod
		public String checkCinfoByBcode(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String code = WSUtil.getXmlNodeText(document, "request/code");
				List<Map<String,Object>> Cinfolist ;
				Cinfolist = intfSMO.checkCinfoByb(code);
				StringBuilder res = new StringBuilder();
				if(Cinfolist.size() < 1){
					res.append("<response>");
					res.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					res.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					res.append("</response>");
					return res.toString();
				}
				
				logger.error("订单提交返回结果：{}", res.toString());
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
//				res.append("<CinfoList>");
					for(Map<String,Object> Cinfo :Cinfolist){
						res.append("<cCodeInfo>");
//						res.append("<reduAccessNumber>").append(Cinfo.get("REDU_ACCESS_NUMBER")).append("</reduAccessNumber>");
						res.append("<reduAccessNumber>").append(Cinfo.get("C_CODE")).append("</reduAccessNumber>");
						res.append("<prodId>").append(Cinfo.get("C_PRO_ID")).append("</prodId>");
						
						res.append("<reduProdSpecId>").append(Cinfo.get("REDU_PROD_SPEC_ID")).append("</reduProdSpecId>");
						res.append("<reduProdSpecName>").append(Cinfo.get("PRODNAME")).append("</reduProdSpecName>");
//						res.append("<prodId>").append(Cinfo.get("PROD_ID")).append("</prodId>");
						res.append("<offerId>").append(Cinfo.get("OFFER_ID")).append("</offerId>");
						res.append("<offerSpecId>").append(Cinfo.get("OFFER_SPEC_ID")).append("</offerSpecId>");
						res.append("<offerSpecName>").append(Cinfo.get("OFFERNAME")).append("</offerSpecName>");
						res.append("</cCodeInfo>");
					}
//				res.append("</CinfoList>");
				res.append("</response>");
				
				return res.toString();
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		/**
		 *  通过物品的串码，查询出对应的渠道和费用项。
		 * @author 
		 * @param request
		 * @since CHANNEL_ID=11040032, CHARGE_ITEM_CD=91001
		 * @return
		 */
		@WebMethod
		public String getchannelByCode(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String code = WSUtil.getXmlNodeText(document, "request/code");
				List<Map<String,Object>> tmllist ;
				tmllist = intfSMO.getchannelByCode(code);
				StringBuilder res = new StringBuilder();
				if(tmllist.size() < 1){
					res.append("<response>");
					res.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					res.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					res.append("</response>");
					return res.toString();
				}
				
				logger.error("通过物品的串码，查询出对应的渠道和费用项结果：{}", res.toString());
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					for(Map<String,Object> tml :tmllist){
						res.append("<channelId>").append(tml.get("CHANNEL_ID")).append("</channelId>");
						res.append("<chargeItemCd>").append(tml.get("CHARGE_ITEM_CD")).append("</chargeItemCd>");
					}
				res.append("</response>");
				
				return res.toString();
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		/**
		 *  为营销赠卡提供-- 部门查询的接口
		 * @author 
		 * @param request
		 * @since queryDeptInfo 
		 * @return
		 */
		@WebMethod
		public String queryDeptInfo(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String name = WSUtil.getXmlNodeText(document, "request/name");
				String level = WSUtil.getXmlNodeText(document, "request/level");
				if(name.equals("")||name == null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "部门名称不能为空!");
				}
				List<Map<String,Object>> DeptInfo ;
				DeptInfo = intfSMO.queryDeptInfo(name,level);
				StringBuilder res = new StringBuilder();
				if(DeptInfo.size() < 1){
					res.append("<response>");
					res.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					res.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					res.append("</response>");
					return res.toString();
				}
				logger.error("通过物品的串码，查询出对应的渠道和费用项结果：{}", res.toString());
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				res.append("<retArray>");
				for(Map<String,Object> dep :DeptInfo){
					res.append("<retInfo>");
					res.append("<deptId>").append(dep.get("ORG_ID")!=null?dep.get("ORG_ID"):"").append("</deptId>");
					res.append("<deptName>").append(dep.get("NAME")!=null?dep.get("NAME"):"").append("</deptName>");
					res.append("<deptLevel>").append(dep.get("LEVEL")!=null?dep.get("LEVEL"):"").append("</deptLevel>");
					res.append("<parentDeptId>").append(dep.get("PARENT_ORG_ID")!=null?dep.get("PARENT_ORG_ID"):"").append("</parentDeptId>");
					res.append("<parentDeptName>").append(dep.get("PARNAME")!=null?dep.get("PARNAME"):"").append("</parentDeptName>");
					res.append("</retInfo>");
				}
				res.append("</retArray>");
				res.append("</response>");
				
				return res.toString();
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			} 
		}
		@WebMethod
		public String queryIdentityNumByPartyId(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				if(partyId.equals("")||partyId==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "partyId不能为空!");
				}
				Map<String,Object> identity = intfSMO.queryByPartyId(partyId);
				StringBuilder sb = new StringBuilder();
				if( identity == null ){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}
				logger.error("思特奇用户查询返回结果：{}", sb.toString());
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<identityNum>").append(identity.get("IDENTITY_NUM")).append("</identityNum>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 为意向单提供
		 * @param request
		 * @return
		 */
		@WebMethod
		public String findStaffForCrm(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				if(partyId.equals("")||partyId==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "partyId不能为空!");
				}
				Map<String,Object> identity = intfSMO.queryEmailByPartyId(partyId);
				StringBuilder sb = new StringBuilder();
				if(identity == null){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}
				logger.error("思特奇用户查询返回结果：{}", sb.toString());
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<mailAddressStr>").append(identity.get("MAIL_ADDRESS_STR")).append("</mailAddressStr>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 为pad提供--明细指标接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String DetailedIndicators(@WebParam(name = "request") String request) {
			try {
				StringBuilder sb = new StringBuilder();
				Document document = WSUtil.parseXml(request);
				String indicatorsCode = WSUtil.getXmlNodeText(document, "request/indicatorsCode");//指标编号
				String statisticalTime = WSUtil.getXmlNodeText(document, "request/statisticalTime");
				String startRown = WSUtil.getXmlNodeText(document, "request/startRown");
				String endRown = WSUtil.getXmlNodeText(document, "request/endRown");
				String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
				String accessNumber = WSUtil.getXmlNodeText(document, "request/accessNumber");
				
				if(indicatorsCode.equals("")||indicatorsCode==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "indicatorsCode不能为空!");
				}
				if(statisticalTime.equals("")||statisticalTime==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "statisticalTime不能为空!");
				}
				if(staffId.equals("")||staffId==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "staffId不能为空!");
				}
				 List<Object> numbersList = new ArrayList<Object>();
				if(statisticalTime.length() == 8){
					//查询某天的统计指标
					
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<indicators>");
					sb.append("<indicatorsCode>").append(indicatorsCode).append("</indicatorsCode>");
					sb.append("<statisticalTime>").append(statisticalTime).append("</statisticalTime>");
					sb.append("<staffId>").append(staffId).append("</staffId>");
					
					sb.append("</indicators>");
					sb.append("<indicatorsListDays>");
					
					if(indicatorsCode.equals("1")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators1(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					if(indicatorsCode.equals("2")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators2(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					if(indicatorsCode.equals("3")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators3(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					if(indicatorsCode.equals("4")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators4(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("5")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators5(timeTwo , staffId);
								numbersList.add(indicatorsList.get("TOTAL")!=null?indicatorsList.get("TOTAL"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("6")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators6(timeTwo , staffId);
								numbersList.add(indicatorsList.get("TOTAL")!=null?indicatorsList.get("TOTAL"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("7")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators7(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("8")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators8(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("9")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators9(timeTwo , staffId);
								numbersList.add(indicatorsList.get("STATISTICS_NUM")!=null?indicatorsList.get("STATISTICS_NUM"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("10")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators10(timeTwo , staffId);
								numbersList.add(indicatorsList.get("TOTAL")!=null?indicatorsList.get("TOTAL"):"0");
								
							}
						}
						
					}
					
					if(indicatorsCode.equals("11")){
						for(int dayNum= 0;dayNum < 7;dayNum++){
							int time = Integer.parseInt(statisticalTime) - dayNum;
							String timeTwo = String.valueOf(time);
							int t = Integer.parseInt(timeTwo.substring(6, 8));
							if(t > 0 && t < 32){
								Map<String,Object> indicatorsList = intfSMO.queryIndicators11(timeTwo , staffId);
								numbersList.add(indicatorsList.get("TOTAL")!=null?indicatorsList.get("TOTAL"):"0");
								
							}
						}
						
					}
					
					
					for(int i= 0;i < 7;i++){
						sb.append("<day").append(i).append(">");
						if(i < numbersList.size()){
							sb.append(numbersList.get(i));
						}
						sb.append("</day").append(i).append(">");
					}
					sb.append("</indicatorsListDays>");
					sb.append("<particularsList>");
					
					List<Map<String,Object>> indicatorsList = intfSMO.queryDetailedIndicatorsList(statisticalTime,startRown,endRown,staffId,accessNumber);
					Map<String,Object> map = intfSMO.queryIndicatorsNumber(statisticalTime,staffId,accessNumber);
					sb.append("<numbers>").append(map.get("NUMBERS")!=null?map.get("NUMBERS"):"").append("</numbers>");
					for(Map<String,Object> indicatorsInfo :indicatorsList){
						sb.append("<indicatorsInfo>");
						sb.append("<name>").append(indicatorsInfo.get("NAME")!=null?indicatorsInfo.get("NAME"):"").append("</name>");
						sb.append("<accessNumber>").append(indicatorsInfo.get("REDU_ACCESS_NUMBER")!=null?indicatorsInfo.get("REDU_ACCESS_NUMBER"):"").append("</accessNumber>");
						sb.append("<begin_date>").append(indicatorsInfo.get("BEGIN_DT")!=null?indicatorsInfo.get("BEGIN_DT"):"").append("</begin_date>");
						sb.append("</indicatorsInfo>");
					}
					sb.append("</particularsList>");
					sb.append("</response>");
					return sb.toString();
				}if(statisticalTime.length() == 6){
					//查询某月的统计周期
					Map<String,Object> mapr = DateOfJudgment.DateOfJudgment(statisticalTime);
					String startTime = mapr.get("startTime").toString();
					String endTime = mapr.get("endTime").toString();
					List<Map<String,Object>> indicatorsList = intfSMO.queryDetailedIndicatorsListMouth(startTime,endTime,startRown,endRown, staffId,accessNumber);
					if(indicatorsList.size() <1){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
					}
					Map<String,Object> map = intfSMO.queryIndicatorsNumberMouth(startTime,endTime,staffId,accessNumber);
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<indicators>");
					sb.append("<statisticalTime>").append(statisticalTime).append("</statisticalTime>");
					sb.append("<staffId>").append(staffId).append("</staffId>");
					sb.append("</indicators>");
					sb.append("<particularsList>");
					sb.append("<numbers>").append(map.get("NUMBERS")!=null?map.get("NUMBERS"):"").append("</numbers>");
					for(Map<String,Object> indicatorsInfo :indicatorsList){
						sb.append("<indicatorsInfo>");
						sb.append("<name>").append(indicatorsInfo.get("NAME")!=null?indicatorsInfo.get("NAME"):"").append("</name>");
						sb.append("<accessNumber>").append(indicatorsInfo.get("REDU_ACCESS_NUMBER")!=null?indicatorsInfo.get("REDU_ACCESS_NUMBER"):"").append("</accessNumber>");
						sb.append("<begin_date>").append(indicatorsInfo.get("BEGIN_DT")!=null?indicatorsInfo.get("BEGIN_DT"):"").append("</begin_date>");
						sb.append("</indicatorsInfo>");
					}
					sb.append("</particularsList>");
					sb.append("</response>");
					return sb.toString();
				}
				else{
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "statisticalTime入参日期不正确!");
				}
			}catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 为pad提供--统计指标接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String StatisticalIndicators(@WebParam(name = "request") String request) {
			try {
				StringBuilder sb = new StringBuilder();
				Document document = WSUtil.parseXml(request);
				String statisticalTime = WSUtil.getXmlNodeText(document, "request/statisticalTime");
				String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
				if(statisticalTime.equals("")||statisticalTime==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "statisticalTime不能为空!");
				}
				if(statisticalTime.length() == 8){
					//查询某天的统计指标
					List<Map<String,Object>> indicatorsList = intfSMO.queryIndicatorsList(statisticalTime,staffId);
					if(indicatorsList.size() <1){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
					}
					
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<indicatorsList>");
					sb.append("<indicatorsInfo>");
					
					sb.append("<idx01>").append("1").append("</idx01>");
					sb.append("<idx01Num>").append(intfSMO.queryIndicators1(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx01Num>");
						
					sb.append("<idx02>").append("2").append("</idx02>");
					sb.append("<idx02Num>").append(intfSMO.queryIndicators2(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx02Num>");
						
					
					sb.append("<idx03>").append("3").append("</idx03>");
					sb.append("<idx03Num>").append(intfSMO.queryIndicators3(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx03Num>");
						
						
					sb.append("<idx04>").append("4").append("</idx04>");
					sb.append("<idx04Num>").append(intfSMO.queryIndicators4(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx04Num>");
					
					sb.append("<idx05>").append("5").append("</idx05>");
					sb.append("<idx05Num>").append(intfSMO.queryIndicators5(statisticalTime,staffId).get("TOTAL")).append("</idx05Num>");
						
					sb.append("<idx06>").append("6").append("</idx06>");
					sb.append("<idx06Num>").append(intfSMO.queryIndicators6(statisticalTime,staffId).get("TOTAL")).append("</idx06Num>");
					
					sb.append("<idx07>").append("7").append("</idx07>");
					sb.append("<idx07Num>").append(intfSMO.queryIndicators7(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx07Num>");

					
					sb.append("<idx08>").append("8").append("</idx08>");
					sb.append("<idx08Num>").append(intfSMO.queryIndicators8(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx08Num>");

					
					sb.append("<idx09>").append("9").append("</idx09>");
					sb.append("<idx09Num>").append(intfSMO.queryIndicators9(statisticalTime,staffId).get("STATISTICS_NUM")).append("</idx09Num>");
						
					sb.append("<idx10>").append("10").append("</idx10>");
					sb.append("<idx10Num>").append(intfSMO.queryIndicators10(statisticalTime,staffId).get("TOTAL")).append("</idx10Num>");
						
					sb.append("<idx11>").append("11").append("</idx11>");
					sb.append("<idx11Num>").append(intfSMO.queryIndicators11(statisticalTime,staffId).get("TOTAL")).append("</idx11Num>");
					
					
					sb.append("</indicatorsInfo>");
					sb.append("</indicatorsList>");
					sb.append("</response>");
					return sb.toString();
				}if(statisticalTime.length() == 6){
					//查询某月的统计指标
					Map<String,Object> map = DateOfJudgment(statisticalTime);
					String startTime = map.get("startTime").toString();
					String endTime = map.get("endTime").toString();
					List<Map<String,Object>> indicatorsList = intfSMO.queryIndicatorsListMouth(startTime,endTime,staffId);
					if(indicatorsList.size() <1){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
					}
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<indicatorsList>");
					sb.append("<indicatorsInfo>");
					sb.append("<idx01>").append("1").append("</idx01>");
					sb.append("<idx01Num>").append(intfSMO.queryIndicatorsMouth1(startTime,endTime,staffId).get("TOTAL")).append("</idx01Num>");
					
					sb.append("<idx02>").append("2").append("</idx02>");
					sb.append("<idx02Num>").append(intfSMO.queryIndicatorsMouth2(startTime,endTime,staffId).get("TOTAL")).append("</idx02Num>");
					
					sb.append("<idx03>").append("3").append("</idx03>");
					sb.append("<idx03Num>").append(intfSMO.queryIndicatorsMouth3(startTime,endTime,staffId).get("TOTAL")).append("</idx03Num>");
					
					sb.append("<idx04>").append("4").append("</idx04>");
					sb.append("<idx04Num>").append(intfSMO.queryIndicatorsMouth4(startTime,endTime,staffId).get("TOTAL")).append("</idx04Num>");
					
					sb.append("<idx05>").append("5").append("</idx05>");
					sb.append("<idx05Num>").append(intfSMO.queryIndicatorsMouth5(startTime,endTime,staffId).get("TOTAL")).append("</idx05Num>");
					
					sb.append("<idx06>").append("6").append("</idx06>");
					sb.append("<idx06Num>").append(intfSMO.queryIndicatorsMouth6(startTime,endTime,staffId).get("TOTAL")).append("</idx06Num>");
					
					sb.append("<idx07>").append("7").append("</idx07>");
					sb.append("<idx07Num>").append(intfSMO.queryIndicatorsMouth7(startTime,endTime,staffId).get("TOTAL")).append("</idx07Num>");

					sb.append("<idx08>").append("8").append("</idx08>");
					sb.append("<idx08Num>").append(intfSMO.queryIndicatorsMouth8(startTime,endTime,staffId).get("TOTAL")).append("</idx08Num>");

					
					sb.append("<idx09>").append("9").append("</idx09>");
					sb.append("<idx09Num>").append(intfSMO.queryIndicatorsMouth9(startTime,endTime,staffId).get("TOTAL")).append("</idx09Num>");
					
					sb.append("<idx10>").append("10").append("</idx10>");
					sb.append("<idx10Num>").append(intfSMO.queryIndicatorsMouth10(startTime,endTime,staffId).get("TOTAL")).append("</idx10Num>");
					
					sb.append("<idx11>").append("11").append("</idx11>");
					sb.append("<idx11Num>").append(intfSMO.queryIndicatorsMouth11(startTime,endTime,staffId).get("TOTAL")).append("</idx11Num>");
					
					sb.append("<idx12>").append("12").append("</idx12>");
					sb.append("<idx12Num>").append(intfSMO.queryIndicatorsMouth12(statisticalTime).get("TOTAL")).append("</idx12Num>");
					sb.append("</indicatorsInfo>");
					sb.append("</indicatorsList>");
					sb.append("</response>");
					return sb.toString();
				}
				else{
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "statisticalTime入参日期不正确!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 日期转换
		 */
		@WebMethod(exclude = true)
		private  Map<String, Object> DateOfJudgment(String statisticalTime){
			String startTime;
			String endTime;
			Map<String,Object> map = new HashMap<String, Object>();
			String date = statisticalTime;
			int year = Integer.parseInt(date.substring(0,4));//年份
			String time = date.substring(4,6);//月份
			List<String> list = new ArrayList<String>();
			list.add("04");
			list.add("06");
			list.add("09");
			list.add("11");
			if(list.contains(time)){
				 startTime = statisticalTime +"01";
				 endTime = statisticalTime + "30";
			}else if(time.equals("02")){
				if((year%4==0 && year%100!=0) || year%400==0){
					 startTime = statisticalTime +"01";
					 endTime = statisticalTime + "29";
				}else{
					 startTime = statisticalTime +"01";
					 endTime = statisticalTime + "28";
				}
			}else{
				 startTime = statisticalTime +"01";
				 endTime = statisticalTime + "31";
			}
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			return map;
			
		}
		/**
		 * 获取地址预占请求序列号
		 * 
		 * 
		 */
		@WebMethod
		public String getAddressPreemptedSeq(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				Map<String,Object> addressPreempted = intfSMO.getAddressPreemptedSeq();
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<value>").append(addressPreempted.get("NEXTVAL")).append("</value>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 一键激励
		 * @param request
		 * @return
		 */
		@WebMethod
		public String RebateInterfaceForCrm(@WebParam(name = "request") String request) {
			try {
				String url = new GetRebateAddress().getAddress("url");
				if(url == null ){
					JSONObject result = new JSONObject();
					result.put("code", ResultCode.SYSTEM_ERROR.getCode());
					result.put("content", url+"地址为空");
					return result.toString();
				}
				Document document = WSUtil.parseXml(request);
				String channelNo = new GetRebateAddress().getAddress("channelNo");
				String platCode = new GetRebateAddress().getAddress("platCode"); 
				String merchantCode = new GetRebateAddress().getAddress("merchantCode");
				String merchantAcc = new GetRebateAddress().getAddress("merchantAcc");
				
				String customerAcc = WSUtil.getXmlNodeText(document, "request/customerAcc");
				String outOrderNo = WSUtil.getXmlNodeText(document, "request/outOrderNo");
				String rebateAmount = WSUtil.getXmlNodeText(document, "request/rebateAmount");

				String keep=KeepUtils.getKeep();//keep
				//日志
				String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();

				JSONObject reqJson = new JSONObject();
				reqJson.put("channelNo", channelNo);
				reqJson.put("platCode", platCode);
				reqJson.put("merchantAcc", merchantAcc);
				reqJson.put("customerAcc", customerAcc);
				reqJson.put("outOrderNo", outOrderNo);
				reqJson.put("rebateAmount", rebateAmount);
				reqJson.put("keep", keep);
				reqJson.put("merchantCode", merchantCode);

				String requester = reqJson.toString();
				System.out.println("请求：" + requester);
				String realReqJson = toRSASign(requester);//参数之间不用&连接
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "RebateInterfaceForCrm", "入参："+realReqJson, requestTime2);
				String respJson = null;
				System.out.println("请求：远程服务" + url+realReqJson);
				try {
					respJson = HttpUtils.queryJsonData(url, realReqJson);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					String msg = e.toString()+"超时地址"+url;
					JSONObject result = new JSONObject();
					result.put("code", ResultCode.SYSTEM_ERROR.getCode());
					result.put("content", msg);
					
					intfSMO.saveResponseInfo(logId2, "CrmWebService", "RebateInterfaceForCrm", "入参："+ realReqJson, requestTime2, msg, new Date(), "1","0");
					return result.toString();
				}
				
				intfSMO.saveResponseInfo(logId2, "CrmWebService", "RebateInterfaceForCrm", "入参："+ realReqJson, requestTime2, respJson, new Date(), "1","0");
				System.out.println("返回："+ respJson);
				//System.out.println("返回："+ respJson);
				Map<String, String> respMap = JsonUtil.getMap(respJson);
				JSONObject result = new JSONObject();
				if (null != respMap.get("code") && "000000".equals(respMap.get("code").toString())) {//
					// 返回验证签名：
					boolean rsaResp = verifySign(respMap);//参数之间有&
					System.out.println("返回数据RSA验证签名结果:" + rsaResp);
					result.put("code", ResultCode.SUCCESS.getCode());
					result.put("content", ResultCode.SUCCESS.getDesc());
					result.put("rebateTime", respMap.get("rebateTime"));
					result.put("rebateAmount", respMap.get("rebateAmount"));
					result.put("outOrderNo", respMap.get("outOrderNo"));
					result.put("orderNo", respMap.get("orderNo"));
					result.put("orderState", respMap.get("orderState"));
					result.put("sign", rsaResp);
					return result.toString();
				} else {
					result.put("code", ResultCode.UNSUCCESS.getCode());
					result.put("content", respMap.get("content"));
					return result.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				String msg = e.toString();
				JSONObject result = new JSONObject();
				result.put("code", ResultCode.SYSTEM_ERROR.getCode());
				result.put("content", msg);
				return result.toString();
			}
		}

		// RSA验证签名（请求数据加签）
		public static String toRSASign(String json) {
			String signJson = json;
			
			Map<String, String> signMap = JsonUtil.getMap(json);
			String sign = "";
			// 设置私钥
			CumRSA rsa = new CumRSA();
			String RSA_MODULU = new GetRebateAddress().getAddress("RSA_MODULU");
			String pri_p = new GetRebateAddress().getAddress("PRI_P");
			String pri_q = new GetRebateAddress().getAddress("PRI_Q");
			String pri_dp = new GetRebateAddress().getAddress("PRI_DP");
			String pri_dq = new GetRebateAddress().getAddress("PRI_DQ");
			String pri_qinv = new GetRebateAddress().getAddress("PRI_QINV");
			String pri_exponet = new GetRebateAddress().getAddress("PRI_EXPONET");
			rsa.setPriDp(pri_dp);
			rsa.setPriDq(pri_dq);
			rsa.setPriExponet(pri_exponet);
			rsa.setPriP(pri_p);
			rsa.setPriQ(pri_q);
			rsa.setPriQinv(pri_qinv);
			rsa.setRsaModulu(RSA_MODULU);

			sign = BusPayRSAUtil.getSign(signMap, rsa);
			signMap.put("sign", sign);
			signJson = JsonUtil.getJsonString(signMap);
			return signJson;
		}

		public static String toRSASign1(String json) {
			String signJson = json;
			Map<String, String> signMap = JsonUtil.getMap(json);
			String sign = "";
			// 设置私钥
			CumRSA rsa = new CumRSA();
//			LoadConfigUtil rsaConfig = LoadConfigUtil.getInstance();
			String RSA_MODULU = new GetRebateAddress().getAddress("RSA_MODULU");
			String pri_p = new GetRebateAddress().getAddress("PRI_P");
			String pri_q = new GetRebateAddress().getAddress("PRI_Q");
			String pri_dp = new GetRebateAddress().getAddress("PRI_DP");
			String pri_dq = new GetRebateAddress().getAddress("PRI_DQ");
			String pri_qinv = new GetRebateAddress().getAddress("PRI_QINV");
			String pri_exponet = new GetRebateAddress().getAddress("PRI_EXPONET");
			rsa.setPriDp(pri_dp);
			rsa.setPriDq(pri_dq);
			rsa.setPriExponet(pri_exponet);
			rsa.setPriP(pri_p);
			rsa.setPriQ(pri_q);
			rsa.setPriQinv(pri_qinv);
			rsa.setRsaModulu(RSA_MODULU);

			sign = BusPayRSAUtil.getSign1(signMap, rsa);
			signMap.put("sign", sign);
			signJson = JsonUtil.getJsonString(signMap);
			return signJson;
		}
		// 验证RSA（返回数据验证,参数之间有&连接）
		public static boolean verifySign(Map jsonMap) {
			String THR_RSA_MODULU = new GetRebateAddress().getAddress("THR_RSA_MODULU");
			
			return BusPayRSAUtil.verifyOfMD5withRSA(jsonMap, jsonMap.get("sign").toString(), THR_RSA_MODULU);
		}
		public static void main(String[] args) {
			String address = new GetRebateAddress().getAddress("THR_RSA_MODULU");
			
			System.out.println(address);
		}
		/**
		 * 校园展示名称
		 * 
		 * 
		 */
		@WebMethod
		public String showSchoolName(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String bcdCode = WSUtil.getXmlNodeText(document, "request/bcdCode");
				
				if(bcdCode==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "入参为空!");
				}
				Map<String,Object> showSchoolName = intfSMO.showSchoolName(bcdCode);
				if(showSchoolName == null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<name>").append(showSchoolName.get("NAME")).append("</name>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 营业新增需求单转预受理单数据接收接口
		 * @param request
		 * @return
		 */
		/*@WebMethod
		public String saveOrderListForDemandExt(@WebParam(name = "request") String request) {
			try {
				String result = soServiceSMO.saveOrderListForDemandExt(request);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}*/
		/**
		 * 需求单转预受理单通用接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String checkIsDemandExt(@WebParam(name = "request") String request) {
			try {
				//第一部校验
				String result = soServiceSMO.checkIsDemandExt(request);
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				JSONObject jsObj = JSONObject.fromObject(result);
				intfSMO.saveRequestInfo(logId, "CrmWebService", "saveOrderListForDemandExt", "需求单转预受理："+request, requestTime);

				if(jsObj.get("resultCode").equals("0")){
					//校验成功转预受理
					result = soServiceSMO.saveOrderListForDemandExt(request);
				    intfSMO.saveResponseInfo(logId, "CrmWebService", "saveOrderListForDemandExt", "需求单转预受理："+request, requestTime, result, new Date(), "1","0");
				}else{
				    intfSMO.saveResponseInfo(logId, "CrmWebService", "saveOrderListForDemandExt", "需求单转预受理："+request, requestTime, result, new Date(), "1","0");
					return result;
				}
				return result;
					
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 根据部门ID查询部门信息
		 */
		@WebMethod
		public String qryDeptById(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String dept_id = WSUtil.getXmlNodeText(document, "request/dept_id");
				if(dept_id==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "入参为空!");
				}
				List<Map<String,Object>> DeptMessage = intfSMO.qryDeptById(dept_id);
				if(DeptMessage.size()<1){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				for(Map<String,Object> Dept : DeptMessage){
					sb.append("<dept_id>").append(Dept.get("DEPT_ID")).append("</dept_id>");
					sb.append("<dept_name>").append(Dept.get("DEPT_NAME")).append("</dept_name>");
					sb.append("<parent_dept_id>").append(WSUtil.dealReturnParam(Dept, "PARENT_DEPT_ID")).append("</parent_dept_id>");
					sb.append("<parent_dept_name>").append(WSUtil.dealReturnParam(Dept, "PARENT_DEPT_NAME")).append("</parent_dept_name>");
				}
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 校园客户校园标示
		 */
		@WebMethod
		public String campusCustomerCampusMark(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String subjectId = WSUtil.getXmlNodeText(document, "request/subjectId");
				String subjectNameId = WSUtil.getXmlNodeText(document, "request/subjectNameId");
				if(subjectId==null && subjectNameId==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "入参为空!");
				}
				List<Map<String,Object>> CampusMark = intfSMO.campusCustomerCampusMark(subjectId,subjectNameId);
				if(CampusMark.size()<1){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<schoolInfos>");
				
				for(Map<String,Object> Mark : CampusMark){
				sb.append("<schoolInfo>");
				sb.append("<itemSpecId>").append(Mark.get("ITEM_SPEC_ID")).append("</itemSpecId>");
				sb.append("<subjectId>").append(Mark.get("SUBJECT_ID")).append("</subjectId>");
				sb.append("<subjectName>").append(Mark.get("SUBJECT_NAME")).append("</subjectName>");
				sb.append("</schoolInfo>");
				}
				
				sb.append("</schoolInfos>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 校园客户信息查询
		 */
		@WebMethod
		public String queryCampusCustomerInformation(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String num = WSUtil.getXmlNodeText(document, "request/num");
				if(num==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "入参为空!");
				}
				List<Map<String,Object>> Information = intfSMO.queryCampusCustomerInformation(num);
				if(Information.size()<1){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<custInfos>");
				
				for(Map<String,Object> mation : Information){
				sb.append("<custInfo>");
				sb.append("<prodId>").append(mation.get("PROD_ID")).append("</prodId>");
				sb.append("<reduOwnerId>").append(mation.get("REDU_OWNER_ID")).append("</reduOwnerId>");
				sb.append("<reduAcctId>").append(mation.get("REDU_ACCT_ID")).append("</reduAcctId>");
				sb.append("<name>").append(mation.get("NAME")).append("</name>");
				sb.append("<terminalCode>").append(mation.get("TERMINAL_CODE")).append("</terminalCode>");
				sb.append("<reduAccessNumber>").append(mation.get("REDU_ACCESS_NUMBER")).append("</reduAccessNumber>");
				sb.append("</custInfo>");
				}
				
				sb.append("</custInfos>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 根据服务号码查询客户名称
		 * @param request
		 * @return
		 */
		@WebMethod
		public String qryCustNameByServiceNumber(@WebParam(name = "request") String request) {
			try {
				String result = csbService.qryCustNameByServiceNumber(request);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 根据购物车号或接入号查询撤单信息
		 * @param request
		 * @return
		 */
		@WebMethod
		public String queryUnCompleteBoByJson(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String queryType = WSUtil.getXmlNodeText(document, "request/queryType");
				String queryValue = WSUtil.getXmlNodeText(document, "request/queryValue");
//				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				
				JSONObject jsObj = new JSONObject();
				jsObj.elementOpt("queryType", queryType);
				jsObj.elementOpt("queryValue", queryValue);
//				jsObj.elementOpt("partyId", partyId);

				String result = soServiceSMO.queryUnCompleteBoByJson(jsObj.toString());
				
//				Object jsm = JsonUtil.getObjectArray(result);
				JSONArray jsonAttrs = JSONArray.fromObject(result);
				JSONArray jsm = JSONArray.fromObject(result);
				String jstr;
				JSONObject js = new JSONObject();
				
				
				
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<orderList>");
				for(int i =0 ;i< jsonAttrs.size();i++){
					js =  jsonAttrs.getJSONObject(i);
					sb.append("<order>");
					sb.append("<olNbr>").append(js.get("olNbr")).append("</olNbr>");
					sb.append("<boId>").append(js.get("boId")).append("</boId>");
					sb.append("<prodSpecName>").append(js.get("prodSpecName")).append("</prodSpecName>");
					sb.append("<accessNumber>").append(js.get("accessNumber")).append("</accessNumber>");
					sb.append("<boActionTypeName>").append(js.get("boActionTypeName")).append("</boActionTypeName>");
					sb.append("<OLStatusName>").append(js.get("OLStatusName")).append("</OLStatusName>");
					sb.append("<olStatusDate>").append(js.get("ol_status_date")).append("</olStatusDate>");
					sb.append("</order>");
				}
				sb.append("</orderList>");
				sb.append("</response>");
				System.out.println(sb.toString());
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 撤单
		 * @param request
		 * @return
		 */
		@WebMethod
		public String createUnfinishOrder(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String channelId = WSUtil.getXmlNodeText(document, "request/channelId");
				String areaId = WSUtil.getXmlNodeText(document, "request/areaId");
				String staffCode = WSUtil.getXmlNodeText(document, "request/staffCode");
				String boId = WSUtil.getXmlNodeText(document, "request/boId");
				String result = soServiceSMO.unfinishOrder(channelId, areaId, Long.parseLong(staffCode),Long.parseLong(boId));
				StringBuilder sb = new StringBuilder();
				
				JSONObject jsonAttrs = JSONObject.fromObject(result);
				sb.append("<response>");
				sb.append("<resultCode>").append(jsonAttrs.get("resultCode")).append("</resultCode>");
				sb.append("<resultMsg>").append(jsonAttrs.get("resultMsg")).append("</resultMsg>");
				sb.append("<olId>").append(jsonAttrs.get("olId")).append("</olId>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 根据受理员工与时间查询佣金
		 */
		@WebMethod
		public String queryRewardInfoByStaff(@WebParam(name = "request") String request) {
			try {
				StringBuilder sb = new StringBuilder();
				Document document = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
				String stardTime = WSUtil.getXmlNodeText(document, "request/stardTime");
				String endTime = WSUtil.getXmlNodeText(document, "request/endTime");
				String startNum = WSUtil.getXmlNodeText(document, "request/startNum");
				String endNum = WSUtil.getXmlNodeText(document, "request/endNum");
				if(staffId == null || stardTime == null || endTime ==null ){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("入参不全").append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}
				Map<String,Object>  RewardSum = intfSMO.queryRewardSum(staffId,stardTime,endTime);
				List<Map<String,Object>> rewardInfos = intfSMO.queryRewardInfoById(staffId,stardTime,endTime,
						startNum,endNum);
				
				if(Integer.parseInt(String.valueOf(RewardSum.get("SUM"))) > 0){
					
				sb.append("<response>");
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<sum>").append(RewardSum.get("SUM")).append("</sum>");
				sb.append("<rewardInfos>");
				for(Map<String,Object> rewardInfo: rewardInfos ){
				sb.append("<rewardInfo>");
					sb.append("<rewardId>").append(rewardInfo.get("REWARD_ID")).append("</rewardId>");
					sb.append("<soDate>").append(rewardInfo.get("SO_DATE")).append("</soDate>");
					sb.append("<staffId>").append(rewardInfo.get("STAFF_ID")).append("</staffId>");
					sb.append("<staffName>").append(rewardInfo.get("STAFF_NAME")).append("</staffName>");
					sb.append("<rewardAmount>").append(rewardInfo.get("REWARD_AMOUNT")).append("</rewardAmount>");
				sb.append("</rewardInfo>");
					}
				sb.append("</rewardInfos>");
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * r.reward_id, r.ol_nbr, o.redu_access_number, t.Name
		 * 按支付id(REWARD_ID)查询详细信息的接口
		 */
		@WebMethod
		public String queryRewardInfoById(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String rewardId = WSUtil.getXmlNodeText(document, "request/rewardId");
				StringBuilder sb = new StringBuilder();
				if(rewardId == null ){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("入参为空").append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}
				Map<String,Object>  RewardInfo = intfSMO.queryRewardInfo(rewardId);
				
				if(RewardInfo != null || RewardInfo.size()>1){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					sb.append("<rewardId>").append(RewardInfo.get("REWARD_ID")).append("</rewardId>");
					sb.append("<olNbr>").append(RewardInfo.get("OL_NBR")).append("</olNbr>");
					sb.append("<reduAccessNumber>").append(RewardInfo.get("REDU_ACCESS_NUMBER")).append("</reduAccessNumber>");
					sb.append("<name>").append(RewardInfo.get("NAME")).append("</name>");
					sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		@WebMethod
		public String queryUmiInfoByIdCard(@WebParam(name = "request") String request) {
			
			try {
				StringBuilder sb = new StringBuilder();
				Document document = WSUtil.parseXml(request);
				String idCard = WSUtil.getXmlNodeText(document, "request/idCard");
				if(idCard == null|| idCard.equals("") ){
					sb.append("<response>");
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("入参为空").append("</resultMsg>");
					sb.append("</response>");
				}
				//通过 证件号查询party_id 获取 姓名，party_id
				List<Map<String,Object>> partyList = intfSMO.querypartyListBypartyList(idCard);
				//通过party_id查询号码
				sb.append("<response>");
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<orderList>");
				for (Map<String,Object> partyInfo: partyList){
					String prodId = String.valueOf(partyInfo.get("PRODID"));
					
					//根据partyId查询prod_id
					List<Map<String,Object>> prodIdList = intfSMO.queryProListByProId(prodId);
					int i = 0;
					
					if(prodIdList.size()>0 ){
						Map<String, Object> olIdInfo = intfSMO.queryOlidByProId(prodId);
						if(olIdInfo == null){
							break;
						}
						
//						//只返回5条
//						if(i<6){
//							break;
//						}
						prodIdList.get(i);
						sb.append("<order>");
						sb.append("<partyId>").append(partyInfo.get("PARTYID")).append("</partyId>");
						sb.append("<partyName>").append(partyInfo.get("PARTY_NAME")).append("</partyName>");
						sb.append("<accessNumber>").append(partyInfo.get("ACCESSNUMBER")).append("</accessNumber>");
						sb.append("<prodSpecId>").append(partyInfo.get("PRODSPEC")).append("</prodSpecId>");
						sb.append("<prodId>").append(partyInfo.get("PRODID")).append("</prodId>");
						sb.append("<olId>").append(olIdInfo.get("OL_ID")).append("</olId>");
						sb.append("<terminalCode>").append(prodIdList.get(0).get("TERMINAL_CODE")).append("</terminalCode>");
						sb.append("</order>");
					}
				}
				sb.append("</orderList>");
				sb.append("</response>");
				return sb.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 客户资料查询认证客服接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String custSaveAuthorizationInfo(@WebParam(name = "request") String request) {
			String logId = intfSMO.getIntfCommonSeq();
			try {
				/**
<response>
	<resultCode>0</resultCode>
	<resultMsg>成功</resultMsg>
</response>

				 */
				Document document = WSUtil.parseXml(request);
				String serialNbr = WSUtil.getXmlNodeText(document, "request/serialNbr");
				String authorizationSource = WSUtil.getXmlNodeText(document, "request/authorizationSource");
				String StateCd = WSUtil.getXmlNodeText(document, "request/StateCd");
				String staffNumber = WSUtil.getXmlNodeText(document, "request/staffNumber");
				String accessNbr = WSUtil.getXmlNodeText(document, "request/accessNbr");
				String startDt = WSUtil.getXmlNodeText(document, "request/startDt");
				String endDt = WSUtil.getXmlNodeText(document, "request/endDt");
				String authorizationType = WSUtil.getXmlNodeText(document, "request/authorizationType");
				String queryNbr = WSUtil.getXmlNodeText(document, "request/queryNbr");

				JSONObject js = new JSONObject();
				JSONObject resultJS = new JSONObject();
				js.put("serialNbr", serialNbr);
				js.put("authorizationSource", authorizationSource);
				js.put("StateCd", StateCd);
				js.put("staffNumber", staffNumber);
				js.put("accessNbr", accessNbr);
				js.put("startDt", startDt);
				js.put("endDt", endDt);
				js.put("authorizationType", authorizationType);
				js.put("queryNbr", queryNbr);
				js.put("partyId", "");
				System.out.println(js.toString());
				Date requestTime = new Date();
				//记录入参
				intfSMO.saveRequestInfo(logId, "CrmJson", "custSaveAuthorizationInfo", js.toString(), requestTime);
				
				String result = soServiceSMO.custSaveAuthorizationInfo(js.toString());
				
				
				resultJS = JSONObject.fromObject(result);
				String resultCode = resultJS.get("resultCode").toString();
				String resultMsg = resultJS.get("resultMsg").toString();
				//记录出参
				intfSMO.saveResponseInfo(logId, "CrmJson", "custSaveAuthorizationInfo", js.toString(), requestTime, result, new Date(),
						"1", resultCode);
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append(resultCode).append("</resultCode>");
				sb.append("<resultMsg>").append(resultMsg).append("</resultMsg>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 通过业务号码查询uim卡卡号
		 */
		@WebMethod
		public String queryUimCodeByAccessNumber(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String accessNumber = WSUtil.getXmlNodeText(document, "request/accNbr");
				if(accessNumber==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "号码为空!");
				}
				Map<String,Object> terminalCode = intfSMO.queryUimCodeByAccessNumber(accessNumber);
				if(terminalCode==null || terminalCode.size()<1){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<terminalCode>").append(terminalCode.get("TERMINAL_CODE")).append("</terminalCode>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 通过业务号码查询订单号
		 */
		@WebMethod
		public String queryOlIdByAccessNumber(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String accessNumber = WSUtil.getXmlNodeText(document, "request/accNbr");
				if(accessNumber==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "入参为空!");
				}
				List<Map<String,Object>> olId = intfSMO.queryOlIdByAccessNumber(accessNumber);
				if(olId==null || olId.size()<1){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空!");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<response>");             
				sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				sb.append("<infos>");
				for(Map<String,Object> ol : olId){
				sb.append("<info>");
				sb.append("<olId>").append(ol.get("OL_ID")).append("</olId>");
				String id = ol.get("OL_ID").toString();
				Map<String, Object> nbr = intfSMO.queryOlNbrByOlId(id);
				sb.append("<olNbr>").append(nbr.get("OL_NBR")).append("</olNbr>");
				String receipt = reprintReceipt("<request><olId>"+id+"</olId></request>");
				Document doc = DocumentHelper.parseText(receipt);
				sb.append("<receipt>").append(doc.selectSingleNode("response/pageInfo").asXML()).append("</receipt>");
				sb.append("</info>");
				}
				sb.append("</infos>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		
		/**
		 * 为SCRM提供查询组织人员接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String queryOrganizationforScrm(@WebParam(name = "request") String request) {
			try {
				StringBuffer sb = new StringBuffer();
				Document document = WSUtil.parseXml(request);
				String staffNumber = WSUtil.getXmlNodeText(document, "request/staffNumber");
				String staffName = WSUtil.getXmlNodeText(document, "request/staffName");
				String organizationId = WSUtil.getXmlNodeText(document, "request/organizationId");
				String ruleNumber = WSUtil.getXmlNodeText(document, "request/ruleNumber");
				
				List<Map<String,Object>> staffInfoList = intfSMO.queryOrganizationforScrm(staffNumber,staffName,organizationId
						,ruleNumber);
				sb.append("<request>");
				if(staffInfoList.size() > 0){
					sb.append("<resultCode>").append(ResultCode.SUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("成功").append("</resultMsg>");
					sb.append("<staffInfos>");
					for(Map<String,Object> staffInfo : staffInfoList ){
						sb.append("<staffInfo>");
						sb.append("<staffId>").append(staffInfo.get("STAFFID")).append("</staffId>");
						sb.append("<staffNumber>").append(staffInfo.get("STAFFNUMBER")).append("</staffNumber>");
						sb.append("<staffName>").append(staffInfo.get("STAFFNAME")).append("</staffName>");
						sb.append("<organizationName>").append(staffInfo.get("ORGANIZATIONNAME")).append("</organizationName>");
						sb.append("<pOrganizationName>").append(staffInfo.get("PORGANIZATIONNAME")).append("</pOrganizationName>");
						sb.append("</staffInfo>");
					}
					sb.append("</staffInfos>");
				}else{
					sb.append("<resultCode>").append(ResultCode.UNSUCCESS).append("</resultCode>");
					sb.append("<resultMsg>").append("未查询到相应数据").append("</resultMsg>");
				}
				sb.append("</request>");
				
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 装维翼支付返现奖励
		 */
		@WebMethod
		public String CashBackReward(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				String rewardAmount = WSUtil.getXmlNodeText(document, "request/rewardAmount");
				String staffNumber = WSUtil.getXmlNodeText(document, "request/staffNumber");
				String rewardSource = WSUtil.getXmlNodeText(document, "request/rewardSource");
				String bestPayAccount = WSUtil.getXmlNodeText(document, "request/bestPayAccount");
				if(StringUtils.isBlank(olNbr)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr不可为空");
				}
				Map<String, Object> staffId = intfSMO.getStaffIdBystaffNumber(staffNumber);
				if(staffId==null){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "奖励人工号不存在");
				}
				SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = fo.format(new Date());
				String rewardId = intfSMO.queryRewardId();
				Map<String,String> para = new HashMap<String,String>();
				para.put("rewardId", rewardId);
				para.put("olNbr", olNbr);
				para.put("staffId", staffId.get("STAFF_ID").toString());
				para.put("soDate", date);
				para.put("rewardAmount", rewardAmount);
				para.put("Date", date);
				para.put("rewardSource", rewardSource);
				para.put("bestPayAccount", bestPayAccount);
				List<Map<String, Object>> RewardSource = intfSMO.queryRewardSource(olNbr);
				if(RewardSource==null){
					intfSMO.insertReward(para);
				}else{
					for(Map<String, Object> reward : RewardSource){
						if(rewardSource.equals(reward.get("REWARD_SOURCE"))){
							return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr已存在");
						}
					}
					intfSMO.insertReward(para);
				}
				
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 员工对应渠道和仓库查询
		 */
		@WebMethod
		public String queryStoreByStaffId(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
				if(StringUtils.isBlank(staffId)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "staffId不可为空");
				}
				List<Map<String, Object>> store = intfSMO.queryStoreByStaffId(staffId);
				StringBuilder sb = new StringBuilder();
				if(store!=null && store.size()>0){
					sb.append("<response>");             
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					for(Map<String, Object> st : store){
						sb.append("<staffId>").append(st.get("STAFF_ID")).append("</staffId>");
						sb.append("<channelId>").append(st.get("CHANNEL_ID")).append("</channelId>");
						sb.append("<storeId>").append(st.get("STORE_ID")).append("</storeId>");
					}
					sb.append("</response>");
					return sb.toString();
				}else{
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 有价卡仓库及状态查询
		 */
		@WebMethod
		public String queryStatusByInstCode(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String mktResInstCode = WSUtil.getXmlNodeText(document, "request/mktResInstCode");
				if(StringUtils.isBlank(mktResInstCode)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "mktResInstCode不可为空");
				}
				List<Map<String, Object>> status = intfSMO.queryStatusByInstCode(mktResInstCode);
				StringBuilder sb = new StringBuilder();
				if(status!=null && status.size()>0){
					sb.append("<response>");             
					sb.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
					sb.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
					for(Map<String, Object> st : status){
						sb.append("<mktResInstCode>").append(st.get("MKT_RES_INST_CODE")).append("</mktResInstCode>");
						sb.append("<mktResStoreId>").append(st.get("MKT_RES_STORE_ID")).append("</mktResStoreId>");
						sb.append("<statusCd>").append(st.get("STATUS_CD")).append("</statusCd>");
						sb.append("<statusName>").append(st.get("STATUS_NAME")).append("</statusName>");
					}
					sb.append("</response>");
					return sb.toString();
				}else{
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询结果为空");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 通过购物车流水查询pay_identity信息
		 */
		@WebMethod
		public String queryPayIdentityInfoByOlNbr(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				if(StringUtils.isBlank(olNbr)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr不可为空");
				}
				//记录日志
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String jsonString = olNbr;
				intfSMO.saveRequestInfo(logId, "CrmWebService", "queryPayIdentityInfoByOlNbr", jsonString, requestTime);
				String result="";
				try {
					result = soServiceSMO.queryPayIdentityInfoByOlNbr(olNbr);
				} catch (Exception e) {
					result = e.getMessage();
				}
				intfSMO.saveResponseInfo(logId, "CrmWebService", "queryPayIdentityInfoByOlNbr", jsonString, requestTime, result, new Date(), "1",
						"0");
				Document respon = WSUtil.parseXml(result);
				StringBuffer sb = new StringBuffer();
				
				sb.append("<response>");
				sb.append("<resultCode>").append(WSUtil.getXmlNodeText(respon, "response/resultCode")).append("</resultCode>");
				sb.append("<resultMsg>").append(WSUtil.getXmlNodeText(respon, "response/resultMsg")).append("</resultMsg>");
				sb.append("<payIdentityId>").append(WSUtil.getXmlNodeText(respon, "response/payIdentityId")).append("</payIdentityId>");
				sb.append("</response>");
				
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 通过购物车流水查询是否欠费信息
		 */
		@WebMethod
		public String queryPayIdentityInfo(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				if(StringUtils.isBlank(olNbr)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr不可为空");
				}
				//记录日志
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String jsonString = olNbr;
				intfSMO.saveRequestInfo(logId, "CrmWebService", "queryPayIdentityInfo", jsonString, requestTime);
				String result="";
				try {
					result = soServiceSMO.queryPayIdentityInfoByOlNbr(olNbr);
				} catch (Exception e) {
					result = e.getMessage();
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
				}
				intfSMO.saveResponseInfo(logId, "CrmWebService", "queryPayIdentityInfo", jsonString, requestTime, result, new Date(), "1",
						"0");
				Document respon = WSUtil.parseXml(result);
				StringBuffer sb = new StringBuffer();
				String resultter = WSUtil.getXmlNodeText(respon, "response/resultCode");
				String msg = WSUtil.getXmlNodeText(respon, "response/resultMsg");
				if(!resultter.equals("0")){
					if(msg.equals("查询结果为空")){
						StringBuffer rt = new StringBuffer();
						rt.append("<response><resultCode>0</resultCode><resultMsg>成功</resultMsg>");
						rt.append("<responseId>").append("0").append("</responseId>");
						rt.append("<responseTime>").append("0").append("</responseTime>");
						rt.append("<staffId>").append("0").append("</staffId>");
						rt.append("<indentCharge>").append("0").append("</indentCharge>");
						rt.append("<charge>").append("0").append("</charge>");
						rt.append("<payIndentId>").append("0").append("</payIndentId>");
						rt.append("<channelId>").append("0").append("</channelId>");
						rt.append("</response>");
						return rt.toString();					
						}else{
							return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, WSUtil.getXmlNodeText(respon, "response/resultMsg"));
						}
				}
				if(!resultter.equals("0")){
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, WSUtil.getXmlNodeText(respon, "response/resultMsg"));
				}
				sb.append("<response>");
				sb.append("<resultCode>").append(WSUtil.getXmlNodeText(respon, "response/resultCode")).append("</resultCode>");
				sb.append("<resultMsg>").append(WSUtil.getXmlNodeText(respon, "response/resultMsg")).append("</resultMsg>");
				sb.append("<payIdentityId>").append(WSUtil.getXmlNodeText(respon, "response/payIdentityId")).append("</payIdentityId>");
				sb.append("</response>");
				//调用ABM查询接口
				String lsNumber = srFacade.getUnitySeq();
				result = intfSMO.queryPayIdentityInfo(WSUtil.getXmlNodeText(respon, "response/payIdentityId") , lsNumber);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		//修复数据接口
		/**
		@WebMethod
		public String edidInfo(@WebParam(name = "request") String request) {
			try {
				int m = 0;
				Document document = WSUtil.parseXml(request);
				String edid = WSUtil.getXmlNodeText(document, "request/edid");
				if(StringUtils.isBlank(edid)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "指令不对");
				}
				List<Map<String, Object>> edidInfoList = intfSMO.getEdidInfo();
				
//				Map<String, Object> edidInfo =null;
				
				//记录成功结果的
				List<Map<String, Object>> tureResultList = null;
				List<Map<String, Object>> falseResultList = null;
				
				int n = 0;
				//调用接口返回
				String resultter = "";
				for(Map<String, Object> edidInfo:edidInfoList){
					String partyId = edidInfo.get("REDU_OWNER_ID").toString();
					String accessNumber =edidInfo.get("REDU_ACCESS_NUMBER").toString();
					String prodId = edidInfo.get("PROD_ID").toString();
					String offerSpecId = edidInfo.get("OFFER_SPEC_ID").toString();
					//拼接报文
					String xmlinfo = edidInfoxml(partyId,accessNumber,prodId,offerSpecId);
					System.out.println(xmlinfo);
					//调接口
					//resultter = spServiceSMO.businessService(xmlinfo);
					Document resultterXml = WSUtil.parseXml(resultter);
					String resultCode = WSUtil.getXmlNodeText(resultterXml, "response/resultCode");
					if(resultCode.equals("0")){
						
						m = m+1;
//						tureResultList.get(m).put("resultCode", resultCode);
//						tureResultList.get(m).put("partyId", partyId);
						//更新状态
						intfSMO.edidType(accessNumber);
					}else{
//						n = n+1;
//						falseResultList.get(n).put("resultCode", resultCode);
//						falseResultList.get(n).put("partyId", partyId);
					}
				}
//				System.out.println(tureResultList.toString());
//				System.out.println(falseResultList.toString());
				StringBuilder sb = new StringBuilder();
					return "cheg";
					
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		private String edidInfoxml(String partyId, String accessNumber,
				String prodId, String offerSpecId) {
			StringBuilder sb = new StringBuilder();
			sb.append("<request><order><orderTypeId>17</orderTypeId><prodSpecId>600012009</prodSpecId>");
			sb.append("<partyId>").append(partyId).append("</partyId>");
			sb.append("<accessNumber>").append(accessNumber).append("</accessNumber>");
			sb.append("<prodId>").append(prodId).append("</prodId>");
			sb.append("<systemId>6090010020</systemId><offerSpecs>");  
            sb.append("<offerSpec>");
            sb.append("<id>").append(offerSpecId).append("</id>");
            sb.append("<name/><actionType>0</actionType><startFashion></startFashion><endFashion/><startDt/><endDt/></offerSpec></offerSpecs></order>");
            sb.append("<areaId>11000</areaId><channelId>11040361</channelId><staffCode>A00882</staffCode></request>");
            System.out.println("请求报文拼装组合"+sb.toString());
            return sb.toString();
		}
		**/
		/**
		 * 通过olnbr查询pay_identity和name信息
		 */
		@WebMethod
		public String queryIdentityInfoByOlnbr(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				if(StringUtils.isBlank(olNbr)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr不可为空");
				}
				//记录日志
				Map<String, Object> result = null ;
				try {
					result = intfSMO.queryIdentityInfoByOlnbr(olNbr);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(result != null){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<olId>").append(result.get("OL_ID")).append("</olId>");
				sb.append("<Name>").append(result.get("PARTY_NAME")).append("</Name>");
				sb.append("<identityNum>").append(result.get("IDENTITY_NUM")).append("</identityNum>");
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 通过ali接口查询信息
		 */
		@WebMethod
		public String aliPayClent(@WebParam(name = "request") String request) {
			try {
				StringBuffer sb = new StringBuffer();
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				if(StringUtils.isBlank(olNbr)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "olNbr不可为空");
				}
				String AlipayClientURL = new GetRebateAddress().getAddress("AlipayClientURL");
				String clentNum = new GetRebateAddress().getAddress("clentNum");
				String private_key = new GetRebateAddress().getAddress("private_key");
				String alipay_public_key = new GetRebateAddress().getAddress("alipay_public_key");
				
				//alipay远程调用
//				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2014072300007148",
//				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016073100137394",
//						//私钥
//						"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN/ALt0Gb07SsH3tQXdkDev5dbUmhdqkt5wn5gf9wiqtb6cy+YfWlgKwZ68hR+iiQm+V8ZitkxLWaLgy8b5mKlOTnlnsvo41gEDxT7mjg+2RqHVfWkJJJ3e90UN75SKVLJze2NUDiaHzNngsZU96VrHRNTjyVA2WI1dEX3q/TwMzAgMBAAECgYACttcasUhKYX3omScxJpKQqT8HSodXJqZfi5BbynU/hUkUcybZwf2h3Lf1ROcVaFp79gqvOnPNR7KPgXazQ5QB17jxE+SGT1UXQTB0h00+8ynE/Np4XApjRDbmj/44nPxRMMjEe32Vvnd20TpfhHgeYTmqT5tABDTO7Bkq84MNMQJBAPyV9adQYluSPFvgrdyWo5MHZAjtzuuCW3OJEyHB6nDYahTkL3xjFFB9WQfTTUMvnRbg1m7nD3IDsUb8hZRRXekCQQDixnGG7U0QiviUPTmUw/2LwtSZQTt+P+ZR9NwE37kzztQVpGEtnAMnU0Y0nBKwWLgzEeCnoeybALGcwPgrT9q7AkAKHPb4+/70K6a/Bv/vFRj3ihQ3R05Bn7aJqbNaZqk8W23j6D7+e/kUP/CQn6U4S2++usHbLuUva3ZtoS64AYoBAkBVTIVpVhsVrOBBiiH9hhb5mggzuSiynUB59VtI7goQpax6k4EGEdfR0+3lXgtdj5GBjCjvhOWljAZCywbN69R/AkEA2lYGe0XfCXeZbLUuvy+2gDiuf32rukp6DkQw/v3jqH2m0De/+jopZ8RldQgEb7qvYUcob/xxG8WnNe0nt+FUtA==",
//						"JSON","UTF-8",
//						//公钥
//						"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB");
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayClientURL,clentNum,private_key,"JSON","UTF-8",alipay_public_key);

				AlipayEbppInvoiceTitleDynamicGetRequest requestAli = new AlipayEbppInvoiceTitleDynamicGetRequest(); 
				requestAli.setBizContent("{"+"\"bar_code\":\""+olNbr.trim()+"\"" + "}");
				AlipayEbppInvoiceTitleDynamicGetResponse response = alipayClient.execute(requestAli); 
				String alResultCode = response.getCode();
				if(response.isSuccess()){
				System.out.println("调用成功");
				String userId = response.getTitle().getUserId();
//				String alResultCode = response.getCode();
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<userId>").append(userId).append("</userId>");
				sb.append("</response>");
				} else { System.out.println("调用失败");
				
				sb.append("<response>");
				sb.append("<resultCode>").append("1").append("</resultCode>");
				sb.append("<alResultCode>").append(alResultCode).append("</alResultCode>");
				sb.append("<resultMsg>").append("查询失败").append("</resultMsg>");
				sb.append("</response>");
				}
				System.out.println(sb.toString());
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append("1").append("</resultCode>");
				sb.append("<resultMsg>").append(e.toString()).append("</resultMsg>");
				sb.append("</response>");
				return sb.toString();
			}
		}
		/**
		 * 通过olnbr查询pay_identity和name信息
		 */
		@WebMethod
		public String queryProdInfoByAccs(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String accessNumber = WSUtil.getXmlNodeText(document, "request/accessNumber");
				if(StringUtils.isBlank(accessNumber)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "accessNumber不可为空");
				}
				//记录日志
				Map<String, Object> result = null ;
				try {
					result = intfSMO.queryProdInfoByAccs(accessNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(result != null){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<prodId>").append(result.get("PROD_ID")).append("</prodId>");
				sb.append("<prodSpecId>").append(result.get("REDU_PROD_SPEC_ID")).append("</prodSpecId>");
				sb.append("<partyId>").append(result.get("REDU_OWNER_ID")).append("</partyId>");
				sb.append("<accessNumber>").append(result.get("REDU_ACCESS_NUMBER")).append("</accessNumber>");
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		
		/**
		 * 根据partyId查询部门信息（为Scrm提供）
		 */
		@WebMethod
		public String queryDepinfoForScrm(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				if(StringUtils.isBlank(partyId)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "partyId不可为空");
				}
				//记录日志
				List<Map<String, Object>> results = null ;
//				Map<String, Object> resultInfo = null;
				try {
					results = intfSMO.queryDepinfoForScrm(partyId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(results.size() >0){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				for(Map<String, Object> resultInfo :results){
					sb.append("<depInfo>");
					sb.append("<channelId>").append(resultInfo.get("channelId")).append("</channelId>");
					sb.append("<name>").append(resultInfo.get("name")).append("</name>");
					sb.append("<channelManageCode>").append(resultInfo.get("channelManageCode")).append("</channelManageCode>");
					sb.append("<channelSpecId>").append(resultInfo.get("channelSpecId")).append("</channelSpecId>");
					sb.append("</depInfo>");
				}
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		
		/**
		 * 一证五号--根据接入号查询使用人
		 */
		@WebMethod
		public String queryUserdByAccess(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String accessNuber = WSUtil.getXmlNodeText(document, "request/accessNuber");
				if(StringUtils.isBlank(accessNuber)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "accessNuber不可为空");
				}
				//记录日志
				List<Map<String, Object>> results = null ;
//				Map<String, Object> resultInfo = null;
				try {
					results = intfSMO.queryUserdByAccess(accessNuber);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(results.size() >0){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				for(Map<String, Object> resultInfo :results){
					sb.append("<userdId>").append(resultInfo.get("PARTY_ID")).append("</userdId>");
					sb.append("<userdName>").append(resultInfo.get("NAME")).append("</userdName>");
					sb.append("<defaultIdType>").append(resultInfo.get("DEFAULT_ID_TYPE")).append("</defaultIdType>");
					sb.append("<identityNum>").append(resultInfo.get("IDENTITY_NUM")).append("</identityNum>");
				}
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 支付宝uid查询号码
		 */
		@WebMethod
		public String queryPhoneNoByAliUid(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String aliUid = WSUtil.getXmlNodeText(document, "request/aliUid");
				if(StringUtils.isBlank(aliUid)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "aliUid不可为空");
				}
				//记录日志
				List<Map<String, Object>> results  = null ;
				try {
					results = intfSMO.queryPhoneNoByAliUid(aliUid);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(results.size() != 0){
					
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
					sb.append("<accNbrs>");
					for(Map<String, Object> resultInfo :results){
						sb.append("<accNbr>").append(resultInfo.get("REDU_ACCESS_NUMBER")).append("</accNbr>");
					}
					sb.append("</accNbrs>");
					sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 查询集团流水号接口
		 */
		@WebMethod
		public String queryExtCustOrder(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String custOrderId = WSUtil.getXmlNodeText(document, "request/custOrderId");
				if(StringUtils.isBlank(custOrderId)){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "CustOrderId不可为空");
				}
				if(custOrderId.length() < 14){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "CustOrderId小于14位不满足要求");
				}
				
				//记录日志
				Map<String, Object> resultCount = null ;
				try {
					resultCount = intfSMO.queryExtCustOrder(custOrderId.substring(0, 14));
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				if(resultCount != null){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<count>").append(resultCount.get("COUNT")).append("</count>");
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
		}
		/**
		 * 星级
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/custId", caption = "俱乐部标识。"),
				@Node(xpath = "//request/memberShipLevel", caption = "星级等级"),
				@Node(xpath = "//request/effDate", caption = "当前有效会员卡生效日期。"),
				@Node(xpath = "//request/expDate", caption = "当前有效会员卡失效日期。"),
				@Node(xpath = "//request/rateMethod", caption = "类型") })
		public String updateStarMember(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String custId = WSUtil.getXmlNodeText(document, "request/custId");
				String memberShipLevel = WSUtil.getXmlNodeText(document, "request/memberShipLevel");
				String effDate = WSUtil.getXmlNodeText(document, "request/effDate");
				String expDate = WSUtil.getXmlNodeText(document, "request/expDate");
				String rateMethod = WSUtil.getXmlNodeText(document, "request/rateMethod");
				Map<String, Object> parameter = new HashMap<String, Object>() ;
				parameter.put("custId", custId);
				parameter.put("memberShipLevel", memberShipLevel);
				parameter.put("effDate", effDate);
				parameter.put("expDate", expDate);
				parameter.put("rateMethod", rateMethod);
				
					intfSMO.upStarMember(parameter);
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("星级更新成功").append("</resultMsg>");
				sb.append("</response>");
				
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		
		public String queryCaflagByOlnbr(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				
				Map<String, Object> parameter = new HashMap<String, Object>() ;
				
				parameter = intfSMO.queryCaflagByOlnbr(olNbr);
				StringBuffer sb = new StringBuffer();
				if(parameter != null){
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<CaFlag>").append(parameter.get("CA_FLAG")).append("</CaFlag>");
				sb.append("<CaType>").append(parameter.get("CA_TYPE")).append("</CaType>");
				sb.append("</response>");
				}else{
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append("查询结果为空").append("</resultMsg>");
					sb.append("</response>");
				}
				
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		
		public String checkAuthorizationRules(@WebParam(name = "request") String request) {
			try {
				String result = csbService.checkAuthorizationRules(request);
				
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		/**
		 * 提供外围补传照片
		 * @param request
		 * @return
		 */
		public String saveOutskirts(@WebParam(name = "request") String request) {
			try {
				Map<String, Object> parameter = new HashMap<String, Object>() ;
				Document document = WSUtil.parseXml(request);
				String olId = WSUtil.getXmlNodeText(document, "request/olId");
				String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
				String areaFlag = WSUtil.getXmlNodeText(document, "request/areaFlag");
				String custName = WSUtil.getXmlNodeText(document, "request/custName");
				String cerdValue = WSUtil.getXmlNodeText(document, "request/cerdValue");
				String replenishPhote = WSUtil.getXmlNodeText(document, "request/replenishPhote");
				String staffCode = WSUtil.getXmlNodeText(document, "request/staffCode");
				
				//省外
				if(areaFlag.equals("2")){
					//集团olid转为省内olid
					olId = intfSMO.queryBlocOlidToProOlid(olId);
					if(olId.equals("")){
						//省集团olnbr转为省内olid
						olId = intfSMO.queryBlocOlidToProOlNbr(olNbr);
					}
					
				}
				//--省内
				if(olId.equals("")||olId == null){
					//省内olnbr转省内olid
					olId = intfSMO.queryBlocOlidToBlocOlNbr(olNbr);
				}
				
				SimpleDateFormat versonpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String verson = versonpl.format(new Date());
				
				parameter.put("olId", olId);
				parameter.put("staffCode", staffCode);
				parameter.put("cerdValue", cerdValue);
				parameter.put("replenishPhote", replenishPhote);
				parameter.put("creatDate", verson);
				
				//查询是否存在olId
				Boolean code = intfSMO.queryOlidIfHave(olId);
				
				if(code){
					intfSMO.updataOutskirts(parameter);
				}else{
					//当不存在olid的时候插入操作
					intfSMO.InsertOutskirts(parameter);
				}
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("照片更新成功").append("</resultMsg>");
				sb.append("</response>");
				
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		/**
		 * 根据接入号查询上行和下行速率信息
		 * @param doc 
		 */
		public  String qrySpeedInfo(@WebParam(name = "request") String request) {
			try {
				String result = soServiceImpl.qrySpeedInfo(request);
				return result;
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		
		/**
		 * 校园用户信息变更
		 * @param doc 
		 */
		public  String changeInfoForschool(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				
				String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
				String staffId = WSUtil.getXmlNodeText(document, "/request/staffId");
				String prodId = WSUtil.getXmlNodeText(document, "//request/prodId");
				String cerdType = WSUtil.getXmlNodeText(document, "//request/custInfo/cerdType");
				String cerdValue = WSUtil.getXmlNodeText(document, "//request/custInfo/cerdValue");
				String custName = WSUtil.getXmlNodeText(document, "//request/custInfo/custName");
				
				
				//国政通返回报文
				String idCheckResult = "";
				//判断是否是特权工号
				Boolean rule = intfSMO.getSchoolRole(staffId);
				//根据特权 -过国政通校验
				if(rule){
					//拼装校验国政通的报文
					StringBuffer strBuffer = new StringBuffer();
					strBuffer.append("<request>");
					strBuffer.append("<name>").append(custName).append("</name>");
					strBuffer.append("<identifyValue>").append(cerdValue).append("</identifyValue>");
					strBuffer.append("<channelId>").append(channelId).append("</channelId>");
					strBuffer.append("<staffId>").append(staffId).append("</staffId>");
					strBuffer.append("</request>");
					
					String checkResult = spServiceSMO.checkResultIn(strBuffer.toString());
					Document docResult = WSUtil.parseXml(checkResult);
					String checkResultCode = WSUtil.getXmlNodeText(docResult,
					"/response/resultCode");
					String checkResultMsg = WSUtil.getXmlNodeText(docResult,
					"/response/resultMsg");
					if(!checkResultCode.equals("0")){
						return WSUtil.buildResponse("1", checkResultMsg);
					}
				}
				int ifpk = intfSMO.getIfpkByProd(prodId);
				if (ifpk != 1) {
					logger.debug("不是批开用户，不允许此操作！");
					return WSUtil.buildResponse("1", "不是批开用户，不允许此操作！");
				}
				JSONObject orderJson = orderListFactory.changeInfoForschool(document);
				
				Date bDate =  new Date();
				String logId2 = intfSMO.getIntfCommonSeq();
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "changeInfoForschool", request, bDate);
				
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String jsonString = JsonUtil.getJsonString(orderJson);
				intfSMO.saveRequestInfo(logId, "CrmJson", "changeInfoForschool", jsonString, requestTime);
				String result = soServiceSMO.soAutoService(orderJson);
				intfSMO.saveResponseInfo(logId, "CrmJson", "changeInfoForschool", jsonString, requestTime, result, new Date(),
						"1", "0");
//				String result =  jsonString;
//				System.out.println(result);
				JSONObject resultJS = JSONObject.fromObject(result);
				if ("0".equals(resultJS.get("resultCode"))) {
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
					sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
					sb.append("</response>");
					intfSMO.saveResponseInfo(logId2, "CrmWebService", "changeInfoForschool", request, requestTime, sb.toString(), new Date(),
							"1", "0");
					return sb.toString();
					}else{
						StringBuffer sb = new StringBuffer();
						sb.append("<response>");
						sb.append("<resultCode>").append("1").append("</resultCode>");
						sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
						sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
						sb.append("</response>");
						intfSMO.saveResponseInfo(logId2, "CrmWebService", "changeInfoForschool", request, requestTime, sb.toString(), new Date(),
								"1", "0");
						return sb.toString();	
					}
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 * 校园标签修改
		 * @param request
		 * @return
		 */
		public  String changespecInfoForschool(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				//生成json报文
				JSONObject orderJson = orderListFactory.changespecInfoForschool(document);
				
				Date bDate =  new Date();
				String logId2 = intfSMO.getIntfCommonSeq();
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "changespecInfoForschool", request, bDate);
				
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String jsonString = JsonUtil.getJsonString(orderJson);
				intfSMO.saveRequestInfo(logId, "CrmJson", "changespecInfoForschool", jsonString, requestTime);
				String result = soServiceSMO.soAutoService(orderJson);
				intfSMO.saveResponseInfo(logId, "CrmJson", "changespecInfoForschool", jsonString, requestTime, result, new Date(),
						"1", "0");
				JSONObject resultJS = JSONObject.fromObject(result);
				if ("0".equals(resultJS.get("resultCode"))) {
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
					sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
					sb.append("</response>");
					intfSMO.saveResponseInfo(logId2, "CrmWebService", "changespecInfoForschool", request, requestTime, sb.toString(), new Date(),
							"1", "0");
					return sb.toString();
					}else{
						StringBuffer sb = new StringBuffer();
						sb.append("<response>");
						sb.append("<resultCode>").append("1").append("</resultCode>");
						sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
						sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
						sb.append("</response>");
						intfSMO.saveResponseInfo(logId2, "CrmWebService", "changespecInfoForschool", request, requestTime, sb.toString(), new Date(),
								"1", "0");
						return sb.toString();	
					}
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		
		/**
		 * 校园过户接口
		 * @param request
		 * @return
		 */
		public  String transferForschool(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String prodId = WSUtil.getXmlNodeText(document, "//request/prodId");
				int ifpk = intfSMO.getIfpkByProd(prodId);
				if (ifpk != 1) {
					logger.debug("不是批开用户，不允许此操作！");
					return WSUtil.buildResponse("1", "不是批开用户，不允许此操作！");
				}
				//生成json报文
				JSONObject orderJson = orderListFactory.transferForschool(document);
				Date bDate =  new Date();
				String logId2 = intfSMO.getIntfCommonSeq();
				intfSMO.saveRequestInfo(logId2, "CrmWebService", "transferForschool", request, bDate);
				
				
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				String jsonString = JsonUtil.getJsonString(orderJson);
				intfSMO.saveRequestInfo(logId, "CrmJson", "transferForschool", jsonString, requestTime);
				String result = soServiceSMO.soAutoService(orderJson);
				intfSMO.saveResponseInfo(logId, "CrmJson", "transferForschool", jsonString, requestTime, result, new Date(),
						"1", "0");
				JSONObject resultJS = JSONObject.fromObject(result);
				if ("0".equals(resultJS.get("resultCode"))) {
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
					sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
					sb.append("</response>");
					intfSMO.saveResponseInfo(logId2, "CrmWebService", "transferForschool", request, requestTime, sb.toString(), new Date(),
							"1", "0");
					return sb.toString();
					}else{
						StringBuffer sb = new StringBuffer();
						sb.append("<response>");
						sb.append("<resultCode>").append("1").append("</resultCode>");
						sb.append("<resultMsg>").append(resultJS.get("resultMsg")).append("</resultMsg>");
						sb.append("<olId>").append(resultJS.get("olId")).append("</olId>");
						sb.append("</response>");
						intfSMO.saveResponseInfo(logId2, "CrmWebService", "transferForschool", request, requestTime, sb.toString(), new Date(),
								"1", "0");
						return sb.toString();	
					}
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 * 退订服务
		 * @param request
		 * @return
		 */
		public  String unsubscribeService(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
				String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
				String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId");
				String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
				String cerdType = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdType");
				String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
				String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
				
				String partyId = intfSMO.getPartyIdByCard(Integer.valueOf(cerdType), cerdValue);
				Long prodidByPartyId = intfSMO.getProdidByAccNbr(accessNumber);
				if (prodidByPartyId == null || !prodidByPartyId.toString().equals(prodId)) {
					return WSUtil.buildResponse("1", "入参提供的接入号和用户id不匹配或者接入号下有多个用户!", "1", "不进行产品属性变更！");
				}
				String stopSerServiceMsg = stopSerService(accessNumber, channelId, staffId, partyId, prodidByPartyId);

				if ("0".equals(stopSerServiceMsg)) {
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("退订停机服务成功").append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("1").append("</resultCode>");
					sb.append("<resultMsg>").append(stopSerServiceMsg).append("</resultMsg>");
					sb.append("</response>");
					return sb.toString();	
				}
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 * 根据号码查询局向
		 * @param request
		 * @return
		 */
		@WebMethod
		public String queryBureauDirectionByPhoneNum(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String phoneNumber = WSUtil.getXmlNodeText(document, "request/phoneNumber");

				Map<String, Object> bureauDirectionInfo = intfSMO.queryBureauDirectionByPhoneNum(phoneNumber);
				if(bureauDirectionInfo != null){
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("成功").append("</resultMsg>");
					sb.append("<phoneNumber>").append(bureauDirectionInfo.get("PHONE_NUMBER")).append("</phoneNumber>");
					sb.append("<bureauDirection>").append(bureauDirectionInfo.get("NAME")).append("</bureauDirection>");
					sb.append("</response>");
					
					return sb.toString();
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("根据号码查询局向结果为空").append("</resultMsg>");
					sb.append("</response>");
					
					return sb.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		/**
		 * 查询客户是否实名
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/custInfo", caption = "用户信息"),
				@Node(xpath = "//request/custType", caption = "信息类型")})
		public String queryRealNameFlagByPhoneCustInfo(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String custInfo = WSUtil.getXmlNodeText(document, "request/custInfo");
				String custType = WSUtil.getXmlNodeText(document, "request/custType");
				List<Map<String, Object>> bureauDirectionInfo = null;
				if(custType.equals("1")){
					//根据接入号查询
					bureauDirectionInfo = intfSMO.queryRealNameFlagByPhoneAccssnumber(custInfo);
				}
				if(custType.equals("2")){
					//根据身份证
					bureauDirectionInfo = intfSMO.queryRealNameFlagByIdent(custInfo);
				}
				if(custType.equals("3")){
					//根据客户id
					bureauDirectionInfo = intfSMO.queryRealNameFlagByParttId(custInfo);
				}
				if(bureauDirectionInfo.size() > 0 ){
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("成功").append("</resultMsg>");
					sb.append("<count>").append(bureauDirectionInfo.size()).append("</count>");
					if(bureauDirectionInfo.size() > 6){
						sb.append("<prodRecords>");
						sb.append("<realcounter>").append("6").append("</realcounter>");
						for(int i =0;i < 6;i++){
							sb.append("<custInfo>");
							sb.append("<ifReal>").append(bureauDirectionInfo.get(i).get("IF_REALNAME")).append("</ifReal>");
							sb.append("<partyId>").append(bureauDirectionInfo.get(i).get("PARTY_ID")).append("</partyId>");
							sb.append("<partyName>").append(bureauDirectionInfo.get(i).get("PARTY_NAME")).append("</partyName>");
							sb.append("</custInfo>");
						}
						sb.append("</prodRecords>");
					}else{
						sb.append("<realcounter>").append(bureauDirectionInfo.size()).append("</realcounter>");
						sb.append("<prodRecords>");
						for(int i =0;i < bureauDirectionInfo.size();i++){
							sb.append("<custInfo>");
							sb.append("<ifReal>").append(bureauDirectionInfo.get(i).get("IF_REALNAME")).append("</ifReal>");
							sb.append("<partyId>").append(bureauDirectionInfo.get(i).get("PARTY_ID")).append("</partyId>");
							sb.append("<partyName>").append(bureauDirectionInfo.get(i).get("PARTY_NAME")).append("</partyName>");
							sb.append("</custInfo>");
						}
						sb.append("</prodRecords>");
					}
					sb.append("</response>");
					
					return sb.toString();
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append("<response>");
					sb.append("<resultCode>").append("0").append("</resultCode>");
					sb.append("<resultMsg>").append("根据号码查询局向结果为空").append("</resultMsg>");
					sb.append("</response>");
					
					return sb.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		 /**
		 *集团4G补换卡为vtm提供
		 *  
		 *  <request>
			<channelId>11040361</channelId>
			<staffCode>bj1001</staffCode>
		</request>
		 * @author
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { @Node(xpath = "//request/staffCode", caption = "员工编码"),
							@Node(xpath = "//request/channelId", caption = "渠道的业务编码"),
							@Node(xpath = "//request/order/accNbr", caption = "接入号"),
							@Node(xpath = "//request/order/terminalCode", caption = "新卡卡号"),
							@Node(xpath = "//request/order/systemId", caption = "系统ID")
							
							})
		public String changeCardServiceByVTM(@WebParam(name = "request") String request) {
			Document doc;
			 Map<String, Object> preholdingMap = null;
			 String preholdingReJsStr="";
			try {
				doc = WSUtil.parseXml(request);
				
				String accNbr = WSUtil.getXmlNodeText(doc, "//request/order/accNbr");
				String apCharge = WSUtil.getXmlNodeText(doc, "//request/order/apCharge");
				String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
				String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
				String systemId = WSUtil.getXmlNodeText(doc, "//request/order/systemId");
				String terminalCode = WSUtil.getXmlNodeText(doc, "//request/order/terminalCode");
				//新增节点ip/与员工id
				String iPnumber = WSUtil.getXmlNodeText(doc, "//request/order/iPnUMBER");
				String handleCustId = WSUtil.getXmlNodeText(doc, "//request/order/handleCustId");
				String iccserial = WSUtil.getXmlNodeText(doc, "//request/order/iccserial");
				
				//查询员工
				int staffCodeCout=intfSMO.getCmsStaffCodeByStaffCode(staffCode);
				
				if(staffCodeCout==0)
					return WSUtil.buildResponse(ResultCode.STAFF_NOT_EXIST);
				
				   //受理渠道
			    String channelNbr=intfSMO.getChannelNbrByChannelID(channelId);
			    
			    if(StringUtil.isEmpty(channelNbr))
			    	return WSUtil.buildResponse(ResultCode.QUERY_CHANNEL_NULL);
				
				//根据接入号获取产品信息
				Map<String, Object> prodInfo = intfSMO.getProdInfoByAccNbr(accNbr);
				
				if (prodInfo==null) 
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				
				Map<String, Object> code=intfSMO.queryTerminalCodeByProdId(prodInfo.get("prodId").toString());
                 
				if(code==null)
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"用户原始卡号不是在用状态,不能办理补换卡");
				
				
				
				
				JSONObject jsonUIM=createExchangeForProvince.generateQueryUIMList(terminalCode);
				//UIM卡查询
				String logId = intfSMO.getIntfCommonSeq();
				Date requestTime = new Date();
				intfSMO.saveRequestInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+jsonUIM.toString(), requestTime);
				String uimReJsStr=spServiceSMO.exchangeForProvince(jsonUIM.toString(),"exchangeForProvince");
				Map<String, Object>  uimMap =createExchangeForProvince.getExchangeForProvinceResponse(uimReJsStr);
				intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "UIM卡查询："+jsonUIM.toString(), requestTime, uimReJsStr, new Date(), "1","0");
			   
				if(StringUtil.isEmpty(uimReJsStr))
			    	return WSUtil.buildResponse(ResultCode.IMIINFO_IS_NULL);
			    
			    if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(uimMap.get("RspCode")))
			    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,uimMap.toString());
			    
			    Map<String, Object> mkt =createExchangeForProvince.getExchangeForProvinceMkt(uimReJsStr);
			    
			    if(mkt==null)
			    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,"无法解析UIM卡信息"+uimReJsStr);
			    
			    
				mkt.put("CHANNEL_NBR", channelNbr);
				mkt.put("SrcSysID", systemId);
				mkt.put("accNbr", accNbr); 
				mkt.put("staffCode", staffCode); 
			    
				String status_cd=String.valueOf(mkt.get("STATUS_CD"));
//				if(iccserial.equals("")){
					
				/****/
			    //预占
				if(WSDomain.ExchangeForProvinceCode.PREHOLDING_STATUS
						.equals(status_cd) ){
					JSONObject preholdingjson=createExchangeForProvince.generateCardPreholDingJson(mkt);
					System.out.println(preholdingjson.toString());
					//预占
					String log2Id = intfSMO.getIntfCommonSeq();
					Date request2Time = new Date();
					intfSMO.saveRequestInfo(log2Id, "CrmJson", "changeCardOrder", "UIM预占："+preholdingjson.toString(), request2Time);
				    preholdingReJsStr=spServiceSMO.exchangeForProvince(preholdingjson.toString(),"exchangeForProvince");
				    intfSMO.saveResponseInfo(logId, "CrmJson", "changeCardOrder", "UIM预占："+preholdingjson.toString(), request2Time, preholdingReJsStr, new Date(), "1","0");
				    preholdingMap =createExchangeForProvince.getExchangeForProvinceResponse(preholdingReJsStr);
				}
			    
				if(WSDomain.ExchangeForProvinceCode.PREHOLDING_TYPE
						.equals(status_cd))
					return WSUtil.buildResponse(ResultCode.UNSUCCESS,"：新UIM卡状态："+status_cd+",已补换过,请重新输入卡号");
				
				
				 if(preholdingMap!=null&&!WSDomain
						 .ExchangeForProvinceCode.EXCHANGE_OK.equals(preholdingMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,"预占失败："+preholdingMap.toString());
				 
//				}
				 
				 	//订单id 时间+4位随机数
					String  transID=createExchangeForProvince.generateTimeSeq(1);
					
					//根据终端设备号查询物品信息
					Map<String, Object> couponInfo = intfSMO.getCouponInfoByTerminalCode(terminalCode);
					
					//根据终端设备号查询询物品基本信息
					Map<String, Object> couponInfo2 = intfSMO.getBasicCouponInfoByTerminalCode(terminalCode);
					
					//Map<String, Object> devInfo = intfSMO.getDevInfoByCode(terminalCode);
					
					//根据staff_code查询staffId
					Map<String, Object> staffIdMap = intfSMO.getStaffIdByStaffCode(staffCode);
					Map<String, Object>  changeCardMap = new HashMap<String, Object>();
					changeCardMap.put("terminalCode", terminalCode);
					changeCardMap.put("staffId", staffIdMap.get("staffId"));
					changeCardMap.put("prodInfo", prodInfo);
					changeCardMap.put("couponInfo", couponInfo);
					changeCardMap.put("couponInfo2", couponInfo2);
					changeCardMap.put("channelId", channelId);
					changeCardMap.put("mkt", mkt);
					changeCardMap.put("code", code);
					changeCardMap.put("provIsale", transID);
					changeCardMap.put("apCharge", apCharge);
					//新增参数
					changeCardMap.put("iPnumber", iPnumber);
					changeCardMap.put("handleCustId", handleCustId);
					
					JSONObject jsonStr2=null;
					String	OrderResStr = "";
					try {
						jsonStr2 = createExchangeForProvince.generateOrderJson2(changeCardMap);
					} catch (Exception e) {
						e.printStackTrace();
						return  WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "用户当前使用的卡信息异常，查找不到对应的配置数据"+e.getMessage());
					}	
					//调用接口返回json
					String logId5 = intfSMO.getIntfCommonSeq();
					Date requestTime5 = new Date();
					intfSMO.saveRequestInfo(logId5, "CrmJson", "changeCardOrder", "提交订单："+jsonStr2.toString(), requestTime5);
					    	OrderResStr=spServiceSMO.exchangeForProvince(jsonStr2.toString(),"exchangeForProvince");
				    intfSMO.saveResponseInfo(logId5, "CrmJson", "changeCardOrder", "提交订单："+jsonStr2.toString(), requestTime5, OrderResStr, new Date(), "1","0");
					
				 Map<String, Object>  custResMap =createExchangeForProvince.getExchangeForProvinceResponse(OrderResStr);
				 if(custResMap==null)
					 return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR,"获取补换卡失败");
				 
				 if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(custResMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,OrderResStr);
				 
				 mkt.put("CUST_ID", prodInfo.get("partyId").toString());
				 //此处集团数据日志插入延迟，无法查询SaopRuleIntfLog中记录  所以休眠
				 Thread.sleep(5000);
				 Map<String, Object> logMap=intfSMO.getSaopRuleIntfLogByTransactionID(transID);
				 
				 if(logMap==null)
					 return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR,"获取交费订单失败");
				 
				 mkt.put("CUST_ORDER_ID", logMap.get("cust_order_id"));
				 mkt.put("EXT_CUST_ORDER_ID", transID);
				 JSONObject custorderjs=createExchangeForProvince.generateCustOrderJson(mkt);
				 //订单校验成功之后调用集团:查询订单费用信息   2015-11-11
				 JSONObject  feeInfoJson =createExchangeForProvince.getFeeInfoByCustOrderId(mkt);
				 String jsonString = spServiceSMO.exchangeForProvince(feeInfoJson.toString(),"exchangeForProvince");
				 JSONObject  getUpdateFeeJson =createExchangeForProvince.getUpdateFeeJsonByFeeInfo(jsonString,mkt);
				 
				 String log3Id = intfSMO.getIntfCommonSeq();
				 Date request3Time = new Date();
				 System.out.println(custorderjs.toString());
				 intfSMO.saveRequestInfo(log3Id, "CrmJson", "changeCardOrder", "4G补换卡交费通知："+custorderjs.toString(), request3Time);
				 //订单校验成功之后调用集团:更正费用信息   2015-11-11
				 String updateFeeInfoRelust = spServiceSMO.exchangeForProvince(getUpdateFeeJson.toString(),"exchangeForProvince");
				 //String custResJsStr=spServiceSMO.exchangeForProvince(custorderjs.toString(),"exchangeForProvince");
				 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + updateFeeInfoRelust);
				 Map<String, Object>   ResMap =createExchangeForProvince.getExchangeForProvinceResponse(updateFeeInfoRelust);
				 intfSMO.saveResponseInfo(log3Id, "CrmJson", "changeCardOrder", "4G补换卡交费通知："+custorderjs.toString(), request3Time, updateFeeInfoRelust, new Date(), "1","0");
				
				 if(ResMap==null)
					   return WSUtil.buildResponse(ResultCode.UNSUCCESS,"交费通知失败");
				 
				 if(!WSDomain.ExchangeForProvinceCode.EXCHANGE_OK.equals(ResMap.get("RspCode")))
				    	return WSUtil.buildResponse(ResultCode.UNSUCCESS,ResMap.toString());
				 	
				 //增加返回值
				 return WSUtil.buildValidateResponse(ResultCode.SUCCESS,"custOrderId",logMap.get("cust_order_id").toString());
				 
//				 return WSUtil.buildResponse(ResultCode.SUCCESS);
			   } catch (Exception e) {
					e.printStackTrace();
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
				}
			}
		/**
		 * CSB接口测试
		 * @param doc 
		 */
		public  String csbInterFaceTest(@WebParam(name = "request") String request) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("成功").append("</resultMsg>");
				sb.append("<resultRow>").append("0").append("</resultRow>");
				sb.append("</response>");
				return sb.toString();
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 * 需要根据购物车实时查看拍照及身份证照片
		 * @param doc 
		 */
		public  String queryPhotoByOlid(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				
				String gztPictureInfo = "";
				String pictureInfo = "";
				String partyId = "";			
				String newPartyPhoto = "";
				
				String olId = WSUtil.getXmlNodeText(doc, "//request/olId");
				
				//查出finger——photo——cust里面的照片信息
				Map<String, Object> pictureInfoResult = intfSMO.queryPictureByolId(olId);
				if(pictureInfoResult != null){
					if(pictureInfoResult.get("PHOTOCUT") != null){
						pictureInfo = pictureInfoResult.get("PHOTOCUT").toString();}
					if(pictureInfoResult.get("PARTYID") != null){
						partyId = pictureInfoResult.get("PARTYID").toString();
					}
				}
				//查出GZT中的照片信息
				Map<String, Object> gztMsg = intfSMO.querygztPictureInfoByolId(olId);
				if(gztMsg != null){
					if(gztMsg.size() != 0){
						if(gztMsg.get("GZTMSG") != null)
						gztPictureInfo = gztMsg.get("GZTMSG").toString();
						Document picturedoc = WSUtil.parseXml(gztPictureInfo);
						gztPictureInfo = WSUtil.getXmlNodeText(picturedoc, "//CheckResult/Photo");
					}
				}
				
				//add by jxx 20180122 
				//现在该接口回参包含了购物车拍照照片以及购物车所属客户对应的国政通照片
				//根据olid 获得partyId
				Map newPartyPhotoMsg = intfSMO.queryNewPartyPhotoByOlId(olId);
				
				if(newPartyPhotoMsg != null){
					if(newPartyPhotoMsg.size() != 0){
						if(newPartyPhotoMsg.get("RETURN_MSG") != null){
							String newPartyPhotoString = newPartyPhotoMsg.get("RETURN_MSG").toString();
							Document newPartyPhotoPicturedoc = WSUtil.parseXml(newPartyPhotoString);
							newPartyPhoto = WSUtil.getXmlNodeText(newPartyPhotoPicturedoc, "//CheckResult/Photo");
						}
					}
				}				
				
				//组织返回内容
				ArrayList<String> response = new ArrayList<String>();
				response.add("partyId");
				response.add(partyId);
				response.add("pictureInfo");
				response.add(pictureInfo);
				response.add("gztPictureInfo");
				response.add(gztPictureInfo);
				//add by jxx 20180122
				response.add("newPartyPhoto");
				response.add(newPartyPhoto);
				return WSUtil.buildResponse(response);
			} catch (Exception e) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.getMessage());
			}
		}
		/**
		 *照片补拍接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String saveSupplementalFingerprintAndPhoto(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olIdstr = WSUtil.getXmlNodeText(document, "request/olIdstr");
				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				String photoCut = WSUtil.getXmlNodeText(document, "request/photoCut");
				String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
				String channelId = WSUtil.getXmlNodeText(document, "request/channelId");
				String olSource = WSUtil.getXmlNodeText(document, "request/olSource");

				String[] olIdArray = olIdstr.split(",");
				Long[] olIds = new Long[olIdArray.length];
				for (int i = 0; i < olIdArray.length; i++) {
					olIds[i] = Long.valueOf(olIdArray[i]);
		        }
				String result = csbService.saveSupplementalFingerprintAndPhoto(olIds,
						 Long.valueOf(partyId), photoCut, Long.valueOf(staffId), Integer.parseInt(channelId), olSource);
					return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		/**
		 *查询拍照稽核不通过接口
		 * @param request
		 * @return
		 */
		@WebMethod
		public String queryFingerprintAndPhotoInfo(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String partyId = WSUtil.getXmlNodeText(document, "request/partyId");
				
				String result = csbService.queryFingerprintAndPhotoInfo(partyId);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		/**
		 *olid查询账户ID/及账户信息
		 * @param request
		 * @return
		 */
		@WebMethod
		public String queryAccIdbyAccCd(@WebParam(name = "request") String request) {
			try {
				Document document = WSUtil.parseXml(request);
				String olIdStr = WSUtil.getXmlNodeText(document, "request/olId");
				
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				
				com.ai.bss.crm.soquery.offer.smo.ISoQuerySMO smo = (com.ai.bss.crm.soquery.offer.smo.ISoQuerySMO)SpringBeanInvoker.getBean("soQueryManager.soQuerySMO");
				result = smo.getBoNewAcctInfoByOlId(Long.valueOf(olIdStr));

				if(result.size() == 0){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS.getCode(),"查询结果为空");
				}
				StringBuffer sb = new StringBuffer();
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("成功").append("</resultMsg>");
				sb.append("<count>").append(result.size()).append("</count>");
				if(result.size() > 0){
					sb.append("<infos>");
					for(int i =0;i < result.size();i++){
						sb.append("<info>");
						sb.append("<ACCT_CD>").append(result.get(i).get("ACCT_CD")).append("</ACCT_CD>");
						sb.append("<ACCESS_NUMBER>").append(result.get(i).get("ACCESS_NUMBER")).append("</ACCESS_NUMBER>");
						sb.append("<ACCT_ID>").append(result.get(i).get("ACCT_ID")).append("</ACCT_ID>");
						sb.append("<PROD_ID>").append(result.get(i).get("PROD_ID")).append("</PROD_ID>");
						sb.append("</info>");
					}
					sb.append("</infos>");
				sb.append("</response>");
				}
				return sb.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
			}
		}
		
		
		
	/**
	 * * 获取IVPN的序列
	 * CRMT_REQ_20171106_0001 - 新增校园IVPN服务
	 * @param request
	 * @return
	 * @throws RemoteException
	 */
	@WebMethod
	public String getIvpnSequence(@WebParam(name = "request") String request) {
		try {
			Map<String, Object> result = offerSMO.getIvpnSequence();
			
			StringBuffer sb = new StringBuffer();
			sb.append("<response>");
			if(result != null){
				sb.append("<resultCode>").append(result.get("resultCode")).append("</resultCode>");
				sb.append("<resultMsg>").append(result.get("resultMsg")).append("</resultMsg>");
			}
			sb.append("</response>");
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
		}
	}
	
	/**
	 * 将PPM库里的
	 * 销售品id 销售品名称 销售品描述 
	 * 提供WebService接口能力给到知识库
	 * @param request
	 * @return
	 * @throws RemoteException
	 */
	@WebMethod
	public String queryOfferSpecInfo(@WebParam(name = "request") String request) {
		try {
			String msg = "";
			boolean flag = true;
			StringBuffer sb = new StringBuffer();
			
			Document document = WSUtil.parseXml(request);
			String name = WSUtil.getXmlNodeText(document, "request/name");
			
			//验证参数name 不能为空 并且 大于2 小于250 offer_spec.name length
			if(name != null && !"".equals(name)){
				if(name.length() < 2 || name.length() > 250){
					flag = false;
					msg += "参数name长度必须在2到250之间汉字";
				}
			}else{
				flag = false;
				msg += "参数name为空 ";
			}
			
			//验证结果判断
			if(!flag){
				sb.append("<response>");
				sb.append("<resultCode>").append("1").append("</resultCode>");
				sb.append("<resultMsg>").append(msg).append("</resultMsg>");
				sb.append("</response>");
				return sb.toString();
			}
			
			//准备执行参数
			HashMap param = new HashMap();
			param.put("name", name);
			
			//查询数据返回固定20条
			List<Object> offerSpecList = intfSMO.queryOfferSpecInfoList(param);
						
			//拼装返回结果
			sb.append("<response>");
			if(offerSpecList != null){
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("").append("</resultMsg>");
				sb.append("<list>");
				Iterator itera = offerSpecList.iterator();
				while(itera.hasNext()){
					Map offerSpecItem = (Map) itera.next();
					String id = String.valueOf(offerSpecItem.get("ID"));
					String offerSpecName = (String) offerSpecItem.get("NAME");
					String summary = (String) offerSpecItem.get("SUMMARY");
					sb.append("<item>");
					sb.append("<id>").append(id).append("</id>");
					sb.append("<name>").append(offerSpecName).append("</name>");
					sb.append("<summary>").append(summary).append("</summary>");
					sb.append("</item>");
				}
				sb.append("</list>");
			}
			sb.append("</response>");
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
		}
	}
	
	@WebMethod
	public String queryChannelInfoByIdentityNum(@WebParam(name = "request") String request) {
		try {
			StringBuffer sb = new StringBuffer();
			
			Document document = WSUtil.parseXml(request);
			String identityNum = WSUtil.getXmlNodeText(document, "request/identityNum");
			
			//验证参数
			if(StringUtils.isBlank(identityNum)){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "身份证号码为空");
			}
			
			//查询数据
			List<Map<String,Object>> info=intfSMO.queryChannelInfoByIdentityNum(identityNum);
						
			//拼装返回结果
			if(info != null){
				Map map=info.get(0);
				if(!"0".equals(String.valueOf(map.get("STATE"))) || !"1".equals(String.valueOf(map.get("STAFF_STATUS_CD")))){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "请联系部门系统管理员核查工号状态");
				}
				sb.append("<response>");
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append("查询成功").append("</resultMsg>");
				sb.append("<staffNumber>").append(String.valueOf(map.get("STAFF_NUMBER"))).append("</staffNumber>");
				sb.append("<partyId>").append(String.valueOf(map.get("PARTY_ID"))).append("</partyId>");
				sb.append("<channels>");
				for(Map infoMap:info){
					sb.append("<channel>");	
					sb.append("<channelId>").append(String.valueOf(infoMap.get("CHANNEL_ID"))).append("</channelId>");
					sb.append("<channelName>").append(String.valueOf(infoMap.get("CHANNEL_NAME"))).append("</channelName>");
					sb.append("</channel>");
				}
				sb.append("</channels>");
				sb.append("</response>");
			}else{
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查无此员工身份证号，请联系部门系统管理员处理");
			}
			return sb.toString();		
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.UNSUCCESS, e.toString());
		}
	}
}
