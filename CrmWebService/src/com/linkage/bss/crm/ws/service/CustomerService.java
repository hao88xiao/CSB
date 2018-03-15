package com.linkage.bss.crm.ws.service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.easymock.internal.Results;

import bss.common.BssException;
import bss.systemmanager.provide.SmService;

import com.linkage.bss.commons.spring.SpringBeanInvoker;
import com.linkage.bss.commons.util.DESEncryptUtil;
import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.commons.util.StringUtil;
import com.linkage.bss.crm.charge.smo.IChargeManagerSMO;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.cust.dto.CustClubMemberDto;
import com.linkage.bss.crm.cust.dto.CustSegmentDto;
import com.linkage.bss.crm.cust.smo.ICustBasicSMO;
import com.linkage.bss.crm.dto.OfferDto;
import com.linkage.bss.crm.intf.facade.AcctFacade;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.facade.OfferFacade;
import com.linkage.bss.crm.intf.facade.SysFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.storeclient.StoreForOnLineMall;
import com.linkage.bss.crm.local.bmo.ISpServiceSMO;
import com.linkage.bss.crm.local.smo.BillServiceImpl;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.IdentifyType;
import com.linkage.bss.crm.model.ItemSpec;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Addr;
import com.linkage.bss.crm.model.OfferProd2Party;
import com.linkage.bss.crm.model.OfferProd2Prod;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.model.PartyIdentity;
import com.linkage.bss.crm.model.ProdSpec;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.so.batch.smo.ISoBatchFileSMO;
import com.linkage.bss.crm.so.commit.smo.ISoCommitSMO;
import com.linkage.bss.crm.so.query.smo.ISoQuerySMO;
import com.linkage.bss.crm.so.save.smo.ISoSaveSMO;
import com.linkage.bss.crm.soservice.csbservice.ICSBService;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.order.CreateCustOrderListFactory;
import com.linkage.bss.crm.ws.order.ModifyCustomOrderListFactory;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.util.Base64NoWrap;
import com.linkage.bss.crm.ws.util.Base64Util;
import com.linkage.bss.crm.ws.util.DES3N;
import com.linkage.bss.crm.ws.util.GetRebateAddress;
import com.linkage.bss.crm.ws.util.Md5Check;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * 
 * 客户管理
 * 
 */
@WebService
public class CustomerService extends AbstractService {

	private static Log logger = Log.getLog(CustomerService.class);

	private SysFacade sysFacade;

	private ISpServiceSMO spServiceSMO;

	private StoreForOnLineMall storeForOnLineMall;

	private ICustBasicSMO custBasicSMO;

	private OfferFacade offerFacade;

	private CustFacade custFacade;

	private AcctFacade acctFacade;

	private IntfSMO intfSMO;

	private ISoQuerySMO soQuerySMO;

	private IOfferSMO offerSMO;

	private OrderService orderService;

	private CreateCustOrderListFactory createCustOrderListFactory;

	private ISoCommitSMO soCommitSMO;

	private ModifyCustomOrderListFactory modifyCustomOrderListFactory;

	private ISoSaveSMO soSaveSMO;

	private SmService smService;

	private IChargeManagerSMO chargeManager;

	private ISoBatchFileSMO soBatchFileSMO;
	
	private ICSBService csbService;
	
	@WebMethod(exclude = true)
	public void setCsbService(ICSBService csbService) {
		this.csbService = csbService;
	}

	private boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	@WebMethod(exclude = true)
	public void setSoBatchFileSMO(ISoBatchFileSMO soBatchFileSMO) {
		this.soBatchFileSMO = soBatchFileSMO;
	}

	@WebMethod(exclude = true)
	public void setSysFacade(SysFacade sysFacade) {
		this.sysFacade = sysFacade;
	}

	@WebMethod(exclude = true)
	public void setStoreForOnLineMall(StoreForOnLineMall storeForOnLineMall) {
		this.storeForOnLineMall = storeForOnLineMall;
	}

	@WebMethod(exclude = true)
	public void setSpServiceSMO(ISpServiceSMO spServiceSMO) {
		this.spServiceSMO = spServiceSMO;
	}

	@WebMethod(exclude = true)
	public void setChargeManager(IChargeManagerSMO chargeManager) {
		this.chargeManager = chargeManager;
	}

	private BillServiceImpl billServiceImpl;

	@WebMethod(exclude = true)
	public void setBillServiceImpl(BillServiceImpl billServiceImpl) {
		this.billServiceImpl = billServiceImpl;
	}

	@WebMethod(exclude = true)
	public void setSmService(SmService smService) {
		this.smService = smService;
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
	public void setCustBasicSMO(ICustBasicSMO custBasicSMO) {
		this.custBasicSMO = custBasicSMO;
	}

	@WebMethod(exclude = true)
	public void setOfferFacade(OfferFacade offerFacade) {
		this.offerFacade = offerFacade;
	}

	@WebMethod(exclude = true)
	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	@WebMethod(exclude = true)
	public void setAcctFacade(AcctFacade acctFacade) {
		this.acctFacade = acctFacade;
	}

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@WebMethod(exclude = true)
	public void setCreateCustOrderListFactory(
			CreateCustOrderListFactory createCustOrderListFactory) {
		this.createCustOrderListFactory = createCustOrderListFactory;
	}

	@WebMethod(exclude = true)
	public void setSoCommitSMO(ISoCommitSMO soCommitSMO) {
		this.soCommitSMO = soCommitSMO;
	}

	@WebMethod(exclude = true)
	public void setModifyCustomOrderListFactory(
			ModifyCustomOrderListFactory modifyCustomOrderListFactory) {
		this.modifyCustomOrderListFactory = modifyCustomOrderListFactory;
	}

	@WebMethod(exclude = true)
	public void setSoSaveSMO(ISoSaveSMO soSaveSMO) {
		this.soSaveSMO = soSaveSMO;
	}

	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/a", caption = "测试A"),
			@Node(xpath = "//request/b", caption = "测试B") })
	public String demo(@WebParam(name = "request") String request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("resultCode", "0");
		model.put("resultMsg", "成功");

		return mapEngine.transform("demo", model);
	}

	/**
	 * 黑名单查询
	 * 
	 * @author TERFY
	 * @param partyId
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/checkId", caption = "证件类型"),
			@Node(xpath = "//request/typeCode", caption = "证件编号") })
	public String blackUserCheck(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String identifyType = WSUtil.getXmlNodeText(document,
					"//request/checkId");
			String identityNum = WSUtil.getXmlNodeText(document,
					"//request/typeCode");
			Party party = null;
			// 1 为身份证
			if (identifyType.equals(WSDomain.IdentifyType.ID_CARD)) {
				party = custFacade.getPartyByIDCard(identityNum);
			} else if (WSDomain.IDENTIFY_TYPE_SET.contains(identifyType)) {
				party = custFacade.getPartyByOtherCard(identityNum);
			}
			if (party != null) {
				boolean result = custBasicSMO.isInCustBlackList(party
						.getPartyId());
				logger.debug("该客户是否在黑名单中查询结果={}", result);
				if (result) {
					String resultSignSuccess = "<isBlack>"
							+ WSDomain.CHECK_RESULT_YES + "</isBlack>";
					return WSUtil.buildResponse(ResultCode.SUCCESS,
							ResultCode.SUCCESS.getDesc(), resultSignSuccess);
				} else {
					String resultSignFalse = "<isBlack>"
							+ WSDomain.CHECK_RESULT_NO + "</isBlack>";
					return WSUtil.buildResponse(ResultCode.SUCCESS,
							ResultCode.SUCCESS.getDesc(), resultSignFalse);
				}
			} else {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
		} catch (Exception e) {
			WSUtil.logError("BlackUserCheck", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询客户等级
	 * 
	 * @author TERFY
	 * @param partyId
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号") })
	public String qryUserVipType(@WebParam(name = "request") String request) {
		try {
			Map<String, Object> custInfoMap = new HashMap<String, Object>();
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			Party party = custFacade.getPartyByAccessNumber(accNbr);
			if (party != null) {
				long partyId = Long.valueOf(party.getPartyId());
				CustSegmentDto custGradeInfo = custBasicSMO.queryCustGrade(
						partyId, null);
				if (custGradeInfo != null) {
					String grade = custGradeInfo.getPartyGradeName();
					logger.debug("客户等级信息，custGrade={}", grade);
					custInfoMap.put("grade", grade);
					custInfoMap.put("resultCode", ResultCode.SUCCESS);
					custInfoMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
				} else {
					custInfoMap.put("resultCode",
							ResultCode.CUST_GRADE_INFO_IS_NOT_EXIST);
					custInfoMap.put("resultMsg",
							ResultCode.CUST_GRADE_INFO_IS_NOT_EXIST.getDesc());
				}
			} else {
				custInfoMap.put("resultCode", ResultCode.CUSTOMER_NOT_EXIST);
				custInfoMap.put("resultMsg", ResultCode.CUSTOMER_NOT_EXIST
						.getDesc());
			}
			logger.debug("返回信息，custInfoMap={}", custInfoMap.toString());
			return mapEngine.transform("custGradeResponse", custInfoMap);
		} catch (Exception e) {
			WSUtil.logError("qryUserVipType", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());

		}
	}

	/**
	 * 客户信息查询 营业受理-客户查询
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/value", caption = "查询值"),
			@Node(xpath = "//request/selType", caption = "查询类型") })
	public String qryCust(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			// 查询类别
			String selTypeInt = WSUtil.getXmlNodeText(doc, "//request/selType");
			// 证件类型编号
			String identifyType = WSUtil.getXmlNodeText(doc,
					"//request/identifyType");
			// 查询值
			String queryValue = WSUtil.getXmlNodeText(doc, "//request/value");
			// 客户等级查询
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");

			Party party = null;
			List<Party> partyList = new ArrayList<Party>();
			List<Map<String, Object>> partyInfoList = new ArrayList<Map<String, Object>>();
			if (selTypeInt.equals(WSDomain.CustQueryType.QUERY_BY_IDENTIFY)) { // 证件查询
				if (StringUtils.isBlank(identifyType)) {
					return WSUtil
							.buildResponse(ResultCode.IDENTITY_TYPE_IS_NOT_EXIST);
				} else if (identifyType.equals(WSDomain.IdentifyType.ID_CARD)) {
					partyList = custFacade.getPartyListByIDCard(queryValue);
				} else {
					party = custFacade.getPartyByOtherCard(queryValue);
					if (party != null) {
						partyList.add(party);
					}
				}
			} else if (selTypeInt
					.equals(WSDomain.CustQueryType.QUERY_BY_ACCNBR)) {// 接入号查询
				party = custFacade.getPartyByAccessNumber(queryValue);
				if (party != null) {
					partyList.add(party);
				}
			} else if (selTypeInt.equals(WSDomain.CustQueryType.QUERY_BY_NAME)) { // 姓名查询
				partyList = custFacade.getPartyListByName(queryValue);
			} else if (selTypeInt
					.equals(WSDomain.CustQueryType.QUERY_BY_PARTY_ID)) {// 客户ID查询
				party = custFacade.getPartyById(Long.valueOf(queryValue));
				if (party != null) {
					partyList.add(party);
				}
			}
			if (CollectionUtils.isEmpty(partyList)) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			} else {
				// 要查询客户级别
				if (systemId != null && systemId.equals("6090010057")) {// 6090010057=政企

					for (Party pl : partyList) {
						Map<String, Object> fullInfo = new HashMap<String, Object>();
						// 查询客户联系人等信息
						Map profiles = intfSMO.queryCustProfiles(pl
								.getPartyId());
						// 查询客户证件信息
						Map identify = intfSMO.queryIdentifyList(pl
								.getPartyId());
						// 查询客户经理信息
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("prod_id", 0L);// 参数无用，设置为任意Long型
						param.put("owner_id", pl.getPartyId());
						Map<String, Object> highFeeInfo = intfSMO
								.getHighFeeInfo(param);
						// 查询邮政编码
						String postCode = intfSMO.getPostCodeByPartyId(pl
								.getPartyId());
						// 查询客户等级
						String grade = null;
						CustSegmentDto custGradeInfo = custBasicSMO
								.queryCustGrade(Long.valueOf(pl.getPartyId()),
										null);

						if (custGradeInfo != null) {
							grade = custGradeInfo.getPartyGradeName();
						}

						// 拼凑完整客户信息
						fullInfo.put("basic", pl);
						fullInfo.put("profiles", profiles);
						fullInfo.put("identify", identify);
						fullInfo.put("highFeeInfo", highFeeInfo);
						fullInfo.put("postCode", postCode);
						fullInfo.put("grade", grade);

						partyInfoList.add(fullInfo);
					}
				} else {// 不需要查询客户级别
					for (Party pl : partyList) {
						Map<String, Object> fullInfo = new HashMap<String, Object>();
						// 查询客户联系人等信息
						Map profiles = intfSMO.queryCustProfiles(pl
								.getPartyId());
						// 查询客户证件信息
						Map identify = intfSMO.queryIdentifyList(pl
								.getPartyId());
						// 查询客户经理信息
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("prod_id", 0L);// 参数无用，设置为任意Long型
						param.put("owner_id", pl.getPartyId());
						Map<String, Object> highFeeInfo = intfSMO
								.getHighFeeInfo(param);
						// 查询邮政编码
						String postCode = intfSMO.getPostCodeByPartyId(pl
								.getPartyId());

						// 拼凑完整客户信息
						fullInfo.put("basic", pl);
						fullInfo.put("profiles", profiles);
						fullInfo.put("identify", identify);
						fullInfo.put("highFeeInfo", highFeeInfo);
						fullInfo.put("postCode", postCode);

						partyInfoList.add(fullInfo);
					}

				}

			}
			Map<String, Object> qryCustMap = new HashMap<String, Object>();
			qryCustMap.put("resultCode", ResultCode.SUCCESS);
			qryCustMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			qryCustMap.put("partyInfoList", partyInfoList);
			if (systemId != null && systemId.equals("6090010057")) {// 6090010057=政企

				return mapEngine.transform("qryCust1", qryCustMap);
			} else {

				return mapEngine.transform("qryCust", qryCustMap);
			}
		} catch (Exception e) {
			WSUtil.logError("qryCust", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 新增客户
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "/request/custInfo/custName", caption = "客户名称"),
			@Node(xpath = "/request/custInfo/custType", caption = "客户类型"),
			@Node(xpath = "/request/custInfo/cerdAddr", caption = "证件地址"),
			@Node(xpath = "/request/custInfo/cerdType", caption = "证件类型"),
			@Node(xpath = "/request/custInfo/cerdValue", caption = "证件号码"),
			@Node(xpath = "/request/custInfo/contactPhone1", caption = "联系电话1"),
			@Node(xpath = "/request/custInfo/custAddr", caption = "收件地址") })
	public String createCust(@WebParam(name = "request") String request) {
		try {
			logger.debug("开始创建客户 request={}", request);
			Document doc = WSUtil.parseXml(request);
			String custType = WSUtil.getXmlNodeText(doc,
					"/request/custInfo/custType");
			String partyGrade = WSUtil.getXmlNodeText(doc,
					"/request/custInfo/partyGrade");
			
			String cerdValue = WSUtil.getXmlNodeText(doc,
			"/request/custInfo/cerdValue");
			String custName = WSUtil.getXmlNodeText(doc,
			"/request/custInfo/custName");
			//同一证件号不能创建多个用户校验
			String cerdType = WSUtil.getXmlNodeText(doc,
			"/request/custInfo/cerdType");
			//左一村加的证件地址校验
			String cerdAddr = WSUtil.getXmlNodeText(doc,
			"/request/custInfo/cerdAddr");
			
			String channelId = WSUtil.getXmlNodeText(doc,
			"/request/channelId");
			
			String staffId = WSUtil.getXmlNodeText(doc,
			"/request/staffCode");
			//不能为空或全空格或全数字,2、长度≥6个汉字
			
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("cerdType", cerdType);
			m.put("cerdValue", cerdValue);
			//拼装校验国政通的报文
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append("<request>");
			strBuffer.append("<name>").append(custName).append("</name>");
			strBuffer.append("<identifyValue>").append(cerdValue).append("</identifyValue>");
			strBuffer.append("<channelId>").append(channelId).append("</channelId>");
			strBuffer.append("<staffId>").append(staffId).append("</staffId>");
			strBuffer.append("</request>");
			
			String checkResult = checkResultIn(strBuffer.toString());
			Document docResult = WSUtil.parseXml(checkResult);
			String checkResultCode = WSUtil.getXmlNodeText(docResult,
			"/response/resultCode");
			String checkResultMsg = WSUtil.getXmlNodeText(docResult,
			"/response/resultMsg");
			if(!checkResultCode.equals("0")){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, checkResultMsg);
			}
			
			if(intfSMO.isManyPartyByIDNum(m))
				return WSUtil.buildResponse(ResultCode.MANYPARTY_BY_ID,
						"一个证件号码不能创建多个客户:" + cerdValue);
			
			// 判断客户等级和客户类型是否匹配
			if (StringUtils.isBlank(partyGrade)
					&& StringUtils.isNotBlank(WSDomain.PARTY_LEVEL
							.get(custType))) {
				// 设置客户等级的缺省值
				org.dom4j.Node node = doc
						.selectSingleNode("/request/custInfo/partyGrade");
				if (node == null) {
					Element custInfo = (Element) doc
							.selectSingleNode("/request/custInfo");
					custInfo.addElement("partyGrade").setText(
							WSDomain.PARTY_LEVEL.get(custType));
				} else {
					node.setText(WSDomain.PARTY_LEVEL.get(custType));
				}
			} else if (StringUtils.isNotBlank(WSDomain.PARTY_LEVEL
					.get(custType))) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custType", WSDomain.PARTY_LEVEL.get(custType));
				params.put("partyGrade", partyGrade);
				Integer cnt = intfSMO
						.ifRightPartyGradeByCustTypeAndPartyGrade(params);
				if (cnt == 0) {
					return WSUtil.buildResponse(
							ResultCode.REQUEST_PARAME_IS_ERROR, "客户等级跟客户类型不匹配:"
									+ partyGrade);
				}
			} else {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR,
						"客户类型不正确:" + custType);
			}
			JSONObject custJsonString = createCustOrderListFactory
					.generateOrderList(doc);
			if (custJsonString == null) {
				return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
			}
			// 替换ol_id
			JSONObject idJSON = soSaveSMO.saveOrderList(custJsonString);
			Long orderListId = idJSON.getJSONObject("ORDER_LIST-OL_ID")
					.getLong("-1");
			custJsonString.getJSONObject("orderList").getJSONObject(
					"orderListInfo").element("olId", orderListId);
			Long result = soCommitSMO.saveCustInfo(custJsonString);
			if (result == null) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
			return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
					result.toString());
		} catch (Exception e) {
			WSUtil.logError("createCust", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 密码校验
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/accNbrType", caption = "接入号码类型"),
			@Node(xpath = "//request/password", caption = "密码"),
			@Node(xpath = "//request/passwordType", caption = "密码类型") })
	public String checkPassword(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc,
					"//request/accNbrType");
			String password = WSUtil.getXmlNodeText(doc, "//request/password");
			String passwordType = WSUtil.getXmlNodeText(doc,
					"//request/passwordType");

			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)
					&& (WSDomain.PasswordType.PROD_QUERY.equals(passwordType) || WSDomain.PasswordType.PROD_BUSINESS
							.equals(passwordType))) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR,
						"接入号码类型为合同号时，不能校验用户密码");
			}

			OfferProd prod = null;
			Long partyId = null;
			Account acctount = null;
			Party party = null;

			if (WSDomain.AccNbrType.ACCOUNT.equals(accNbrType)) {
				acctount = acctFacade.getAccountById(Long.parseLong(accNbr));
				if (acctount != null) {
					party = custFacade.getPartyById(acctount.getPartyId());
				}
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				prod = intfSMO.getProdByAccessNumber(accNbr);
				// if (prod != null) {
				// party = offerFacade.getPartyByProdId(prod.getProdId());
				// acctount = offerFacade.getAccountByProdId(prod.getProdId());
				// }
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				// 客户标识码
				partyId = intfSMO.getPartyIdByIdentifyNum(accNbr);
			} else {
				return WSUtil.buildResponse(ResultCode.ACCNBR_TYPE_ERROR);
			}

			int checkResult = 0;
			if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
				if (prod == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}

				checkResult = intfSMO.isValidProdQryPwd(prod.getProdId(),
						password);
			} else if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)) {
				if (prod == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}

				checkResult = intfSMO.isValidProdBizPwd(prod.getProdId(),
						password);
			} else if (WSDomain.PasswordType.CUSTOMER_QUERY
					.equals(passwordType)) {
				if (partyId == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}

				checkResult = custFacade.isValidQryPwd(Long.valueOf(partyId),
						password);
			} else if (WSDomain.PasswordType.CUSTOMER_BUSINESS
					.equals(passwordType)) {
				if (partyId == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}

				// checkResult = custFacade.isValidQryPwd(partyId, password);
				checkResult = custFacade.isValidBizPwd(Long.valueOf(partyId),
						password);
			} else if (WSDomain.PasswordType.ACCOUNT.equals(passwordType)) {
				if (acctount == null) {
					return WSUtil.buildResponse(ResultCode.ACCOUNT_NOT_EXIST);
				}

				checkResult = intfSMO.isValidAcctPwd(acctount.getAcctId(),
						password);
			}

			if (checkResult == 1) {
				return WSUtil.buildResponse(ResultCode.SUCCESS, "密码有效");
			} else if (checkResult == -1) {
				return WSUtil.buildResponse(ResultCode.PASSWORD_UNSET, "未设置密码");
			} else {
				return WSUtil
						.buildResponse(ResultCode.PASSWORD_INVALID, "密码无效");
			}
		} catch (Exception e) {
			logger.error(String.format("系统错误,checkPassword=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 获取手机卡的激活状态
	 * 
	 * @author CHENJUNJIE
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号码") })
	public String checkCampusUser(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(document,
					"//request/accessNumber");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("accessNumber", accessNumber);
			params.put("instStatusIneffect", String
					.valueOf(CommonDomain.INST_STATUS_INEFFECT));
			params.put("bCnt", WSDomain.QUERY_ROWNUM_BCNT);
			params.put("eCnt", WSDomain.QUERY_ROWNUM_ECNT);
			List<Map> result = soQuerySMO.qryCardInfoPage(params);
			if (result.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL,
						"手机卡的激活状态查询结果为空！");
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultObj", result);
			return mapEngine.transform("checkCampusUser", root);
		} catch (Exception e) {
			WSUtil.logError("checkCampusUser", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 查询宽带信息
	 * 
	 * @author CHENJUNJIE
	 * @param params
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accessNumber", caption = "接入号码") })
	public String getCustAdInfo(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String accessNumber = WSUtil.getXmlNodeText(document,
					"//request/accessNumber");
			Map<String, Object> root = new HashMap<String, Object>();
			String custName = "";
			String custGroup = "";
			String identifyTypeName = "";
			String identityNum = "";
			String custAddress = "";
			String userAddress = "";
			String aDSpeed = "";
			String prodName = "";
			Long status = null;
			long start = System.currentTimeMillis();
			OfferProd offerProd = intfSMO.getProdByAccessNumber(accessNumber);
			if (isPrintLog) {
				System.out
						.println("getCustAdInfo.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}
			if (offerProd == null) {
				if (isPrintLog) {
					System.out.println("调用营业接口getCustAdInfo(查询宽带信息)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL,
						"该接入号没有对应的产品");
			}

			Long prodId = offerProd.getProdId();
			String accNbr = offerProd.getAccessNumber();
			start = System.currentTimeMillis();
			String secAccNbr = intfSMO.getSecondAccNbrByProdId(String
					.valueOf(prodId));
			if (isPrintLog) {
				System.out
						.println("getCustAdInfo.intfSMO.getSecondAccNbrByProdId(根据prodId查询非主接入号) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			root.put("accNbr", accNbr);
			root.put("secAccNbr", secAccNbr);
			List<OfferDto> offerList = offerSMO
					.queryOfferByProdId(prodId, true);
			if (CollectionUtils.isEmpty(offerList)) {
				if (isPrintLog) {
					System.out.println("调用营业接口getCustAdInfo(查询宽带信息)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL,
						"宽带信息查询结果为空！");
			}
			start = System.currentTimeMillis();
			OfferProd offerProduct = offerSMO.queryOfferProdInstDetailById(
					prodId, CommonDomain.QRY_INST_ALL);
			if (isPrintLog) {
				System.out
						.println("getCustAdInfo.offerSMO.queryOfferProdInstDetailById 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			List<OfferProd2Prod> offerProd2Prods = offerProduct
					.getOfferProd2Prods();
			String relateAccNbr = "";
			for (OfferProd2Prod op : offerProd2Prods) {
				int reasonCd = op.getReasonCd();
				if (WSDomain.STATUS_CD_PHONE.equals(reasonCd)) {
					Long relatedProdId = op.getRelatedProdId();
					OfferProd relaOfferProduct = offerSMO
							.queryOfferProdInstDetailById(relatedProdId,
									CommonDomain.QRY_INST_ALL);
					relateAccNbr = relaOfferProduct.getAccessNumber();
					root.put("relateAccNbr", relateAccNbr);
				}
			}

			if (offerProduct != null) {
				List<OfferProdSpec> offerProdSpecs = offerProduct
						.getOfferProdSpecs();
				if (CollectionUtils.isNotEmpty(offerProdSpecs)) {
					OfferProdSpec offerProdSpec = offerProdSpecs.get(0);
					if (offerProdSpec != null) {
						ProdSpec prodSpec = offerProdSpec.getProdSpec();
						if (prodSpec != null) {
							prodName = prodSpec.getName();
							root.put("prodName", prodName);
						}
					}
				}
			}
			start = System.currentTimeMillis();
			Map deviceMap = intfSMO.queryCodeByNum(relateAccNbr);
			if (isPrintLog) {
				System.out
						.println("getCustAdInfo.intfSMO.queryCodeByNum(直接调用业务资源的接口) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			String reCode = (deviceMap.get("reCode") == null) ? "" : deviceMap
					.get("reCode").toString();
			if ("0".equals(reCode)) {
				String deviceCode = (deviceMap.get("deviceCode") == null) ? ""
						: deviceMap.get("deviceCode").toString();
				String deviceName = (deviceMap.get("deviceName") == null) ? ""
						: deviceMap.get("deviceName").toString();
				root.put("hlrNum", deviceCode);
				root.put("hlrName", deviceName);
			}

			Party cust = null;
			for (OfferProd2Party opp : offerProduct.getOfferProd2Parties()) {
				if (opp.getPartyProductRelaRoleCd() == 0
						&& isStatusAvailable(opp.getStatusCd())) {
					cust = opp.getParty();
					break;
				}
			}
			if (cust != null) {
				start = System.currentTimeMillis();
				// 查询客户等级
				CustSegmentDto custGradeInfo = custBasicSMO.queryCustGrade(cust
						.getPartyId(), null);
				if (isPrintLog) {
					System.out
							.println("getCustAdInfo.custBasicSMO.queryCustGrade 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				root.put("custLevel", custGradeInfo.getPartyGradeName());

				custName = cust.getPartyName();
				root.put("custName", custName);
				// List<PartySegmentMemberList> listPartySegmentMemberList =
				// cust.getStrategySegMember();
				// if (CollectionUtils.isNotEmpty(listPartySegmentMemberList)) {
				// PartySegmentMemberList partySegmentMemberList =
				// listPartySegmentMemberList.get(0);
				// if (partySegmentMemberList != null) {
				// List<Segment> listSegment =
				// partySegmentMemberList.getListSegment();
				// if (CollectionUtils.isNotEmpty(listSegment)) {
				// Segment segment = listSegment.get(0);
				// if (segment != null) {
				// custGroup = segment.getName();
				// root.put("custGroup", custGroup);
				// }
				// }
				// }
				// }
				int partyTypeCd = cust.getPartyTypeCd();
				if (partyTypeCd == 1) {
					custGroup = "个人客户";
				} else if (partyTypeCd == 2) {
					custGroup = "政企客户";
				} else if (partyTypeCd == 3) {
					custGroup = "家庭客户";
				}
				root.put("custGroup", custGroup);

				List<PartyIdentity> identities = cust.getIdentities();
				if (CollectionUtils.isNotEmpty(identities)) {
					PartyIdentity partyIdentity = identities.get(0);
					if (partyIdentity != null) {
						IdentifyType identifyType = partyIdentity
								.getIdentifyType();
						if (identifyType != null) {
							identifyTypeName = identifyType.getName();
							root.put("identifyTypeName", identifyTypeName);
						}
						identityNum = partyIdentity.getIdentityNum();
						root.put("identityNum", identityNum);
					}
				}
				custAddress = cust.getAddressStr();
				root.put("custAddress", custAddress);
				List<OfferProd2Addr> offerProd2AddrList = offerProduct
						.getOfferProd2Addrs();
				if (CollectionUtils.isNotEmpty(offerProd2AddrList)) {
					OfferProd2Addr offerProd2Addr = offerProd2AddrList.get(0);
					if (offerProd2Addr != null) {
						userAddress = offerProd2Addr.getAddressDesc();
						root.put("userAddress", userAddress);
					}
				}
			}
			List<OfferProdItem> offerProdItemList = offerProduct
					.getOfferProdItems();
			if (CollectionUtils.isNotEmpty(offerProdItemList)) {
				for (OfferProdItem offerProdItem : offerProdItemList) {
					if (offerProdItem.getItemSpecId() != null) {
						if (WSDomain.BROADBAND_SPEED_TYPE_SET
								.contains(offerProdItem.getItemSpecId()
										.toString())) {
							ItemSpec itemSpec = offerProdItem.getItemSpec();
							if (itemSpec != null && itemSpec.getDsTypeCd() == 4) {
								if (CollectionUtils.isNotEmpty(itemSpec
										.getDiscreteValueLists())) {
									aDSpeed = itemSpec.getDiscreteValueLists()
											.get(0).getName();
									root.put("ADSpeed", aDSpeed);
								}
							}
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(offerProduct.getOfferProdNumbers())) {
				String mainAccessNumber = offerProduct.getOfferProdNumbers()
						.get(0).getAccessNumber();
				if (StringUtils.isNotBlank(mainAccessNumber)) {
					root.put("mainAccessNumber", mainAccessNumber);
				}
			}
			start = System.currentTimeMillis();
			status = intfSMO.getProdStatusByProdId(String.valueOf(prodId));
			if (isPrintLog) {
				System.out
						.println("getCustAdInfo.intfSMO.getProdStatusByProdId(根据prodId取得prodStatus) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			String statusStr = "";
			if (status != null) {
				start = System.currentTimeMillis();
				statusStr = intfSMO.getProdStatusNameByCd(status);
				if (isPrintLog) {
					System.out
							.println("getCustAdInfo.intfSMO.getProdStatusNameByCd(通过prodStatusCd得到相应的产品状态名称) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

			}
			root.put("status", statusStr);
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultObj", offerList);
			if (isPrintLog) {
				System.out.println("调用营业接口getCustAdInfo(查询宽带信息)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("getCustAdInfoTrue", root);
		} catch (Exception e) {
			e.printStackTrace();
			WSUtil.logError("getCustAdInfoTrue", request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口getCustAdInfo(查询宽带信息)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	private boolean isStatusAvailable(String status) {
		if ("10".equals(status) || "11".equals(status) || "12".equals(status)
				|| "13".equals(status)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/new_password", caption = "新密码"),
			@Node(xpath = "//request/old_password", caption = "旧密码"),
			@Node(xpath = "//request/passwordType", caption = "密码类型") })
	public String changePassword(@WebParam(name = "request") String request) {
		try {
			int checkResult = 0;
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc,
					"//request/accNbrType");
			String old_password = WSUtil.getXmlNodeText(doc,
					"//request/old_password");
			String new_password = WSUtil.getXmlNodeText(doc,
					"//request/new_password");
			String passwordType = WSUtil.getXmlNodeText(doc,
					"//request/passwordType");
			String channelId = WSUtil
					.getXmlNodeText(doc, "//request/channelId");
			String staffCode = WSUtil
					.getXmlNodeText(doc, "//request/staffCode");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			String systemIdComponentId = WSUtil.getXmlNodeText(doc, "//request/systemIdComponentId");
			
			//对密码进行des解密：前提是平台送过来的密码是加密的
			//加密平台在intf.encryptvectorconfig中判断
			/*if(!"".equals(systemIdComponentId)&& systemIdComponentId != null && !"6090010018".equals(systemIdComponentId)){
				new_password = getEncrytStr(new_password,systemIdComponentId,"2");
				old_password = getEncrytStr(old_password,systemIdComponentId,"2");
			}*/
			
			OfferProd prod = null;
			Party party = null;
			long start = 0l;
			if ("".equals(new_password) || "".equals(old_password)
					|| "".equals(passwordType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST);
			}
			// add by wanghongli 检验新密码的格式 1，长度为六位 2，必须为数字
			char[] c = new_password.toCharArray();
			if (c.length != 6) {
				System.out.print("new_password.length==" + c.length);
				return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
			}
			for (int i = 0; i < c.length; i++) {
				if (!Character.isDigit(c[i])) {
					return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
				}
			}

			// 密码修改在途单判断
			// add by helinglong 20141009
			boolean checkOrderZt = checkOrderZt(accNbr, new String[] {
					WSDomain.OrderTypeId.CHANGE_PASSWORD,
					WSDomain.OrderTypeId.RESET_PASSWORD });
			if (checkOrderZt) {
				return WSUtil.buildResponse(ResultCode.ORDER_ONWAY,
						"存在在途单，不能受理密码修改业务！");
			}

			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				start = System.currentTimeMillis();
				prod = intfSMO.getProdByAccessNumber(accNbr);
				if (isPrintLog) {
					System.out
							.println("changePassword.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				if (prod != null) {
					start = System.currentTimeMillis();
					party = offerFacade.getPartyByProdId(prod.getProdId());
					if (isPrintLog) {
						System.out
								.println("changePassword.offerFacade.getPartyByProdId(查询客户信息) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				start = System.currentTimeMillis();
				// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.getPartyByOtherCard(根据其他证件号查询客户信息) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

			} else {
				return WSUtil.buildResponse(ResultCode.INVALID_ACCNBR_TYPE);
			}
			// 产品密码
			if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)
					|| WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
				if (prod == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}
				if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
					start = System.currentTimeMillis();
					checkResult = intfSMO.isValidProdQryPwd(prod.getProdId(),
							old_password);
					if (isPrintLog) {
						System.out
								.println("changePassword.intfSMO.isValidProdQryPwd(校验用户查询密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
				if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)) {
					start = System.currentTimeMillis();
					checkResult = intfSMO.isValidProdBizPwd(prod.getProdId(),
							old_password);
					if (isPrintLog) {
						System.out
								.println("changePassword.intfSMO.isValidProdBizPwd(校验用户业务密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}

					// 密码有效 修改密码
					// 构建报文
					StringBuilder sb = new StringBuilder();
					sb.append("<request>");
					sb.append("<order>");
					sb.append(String.format("<orderTypeId>%s</orderTypeId>",
							WSDomain.OrderTypeId.CHANGE_PASSWORD)); // 修改产品密码
					if (systemId != null
							&& !"".equals(systemId)
							&& ("6090010023".equals(systemId) || "6090010060"
									.equals(systemId))) {
						sb.append(String.format("<systemId>%s</systemId>",
								systemId));
					}
					sb.append(String.format("<prodSpecId>%s</prodSpecId>", prod
							.getProdSpecId()));
					sb.append(String.format("<prodId>%s</prodId>", prod
							.getProdId()));
					sb.append(String.format("<feeType>%s</feeType>", 1));
					sb.append(String.format("<anId>%s</anId>", "accNbr"));
					sb.append(String.format("<accessNumber>%s</accessNumber>",
							accNbr));
					sb.append(String.format("<acctCd>%s</acctCd>", party
							.getAcctCd()));
					sb.append(String.format("<partyId>%s</partyId>", party
							.getPartyId()));
					sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
					sb.append(String.format(
							"<password><oldPassword>%s</oldPassword>",
							old_password));
					sb.append(String.format("<newPassword>%s</newPassword>",
							new_password));
					sb.append(String.format(
							"<prodPwTypeCd>%s</prodPwTypeCd></password>",
							passwordType));
					sb.append("</order>");
					sb.append(String.format("<areaId>%s</areaId>", areaId));
					sb.append(String.format("<channelId>%s</channelId>",
							channelId));
					sb.append(String.format("<staffCode>%s</staffCode>",
							staffCode));
					
					String staffId = WSUtil.getXmlNodeText(doc, "//staffId");
					sb
					.append(String.format("<staffId>%s</staffId>",
							staffId));
					sb.append("</request>");
					String response = orderService.businessCommon(sb
							.toString());
					return response;
				}
			}
			// 客户查询密码
			else if (WSDomain.PasswordType.CUSTOMER_QUERY.equals(passwordType)) {
				if (party == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				start = System.currentTimeMillis();
				checkResult = custFacade.isValidQryPwd(party.getPartyId(),
						old_password);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.isValidQryPwd(校验客户查询密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}

					// 密码有效 修改密码
					// custFacade.changeCustomerPsd(party.getPartyId(),
					// new_password, null);
					StringBuffer sb = new StringBuffer();
					sb.append("<request><areaId>").append(areaId).append(
							"</areaId>");
					sb.append("<channelId>").append(channelId).append(
							"</channelId>");
					sb.append("<staffCode>").append(staffCode).append(
							"</staffCode>");
					sb.append("<custInfo><partyId>").append(party.getPartyId())
							.append("</partyId>");
					sb.append("<custName>").append(party.getPartyName())
							.append("</custName>");
					sb.append("<custType>").append(party.getPartyTypeCd())
							.append("</custType>");
					sb.append("<custQueryPwd>").append(new_password).append(
							"</custQueryPwd>");
					sb.append("</custInfo></request>");
					String reXml = modifyCustom(sb.toString());
					Document reXmlDoc = WSUtil.parseXml(reXml);
					String reCode = WSUtil.getXmlNodeText(reXmlDoc,
							"/response/resultCode");
					if ("0".equals(reCode)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
					}
				}
			}
			// 客户业务密码
			else if (WSDomain.PasswordType.CUSTOMER_BUSINESS
					.equals(passwordType)) {
				if (party == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				start = System.currentTimeMillis();
				checkResult = custFacade.isValidBizPwd(party.getPartyId(),
						old_password);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.isValidBizPwd(校验客户业务密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}

					// 密码有效 修改密码
					// custFacade.changeCustomerPsd(party.getPartyId(),
					// new_password, null);
					StringBuffer sb = new StringBuffer();
					sb.append("<request><areaId>").append(areaId).append(
							"</areaId>");
					sb.append("<channelId>").append(channelId).append(
							"</channelId>");
					sb.append("<staffCode>").append(staffCode).append(
							"</staffCode>");
					sb.append("<custInfo><partyId>").append(party.getPartyId())
							.append("</partyId>");
					sb.append("<custName>").append(party.getPartyName())
							.append("</custName>");
					sb.append("<custType>").append(party.getPartyTypeCd())
							.append("</custType>");
					sb.append("<custBusinessPwd>").append(new_password).append(
							"</custBusinessPwd>");
					sb.append("</custInfo></request>");
					String reXml = modifyCustom(sb.toString());
					Document reXmlDoc = WSUtil.parseXml(reXml);
					String reCode = WSUtil.getXmlNodeText(reXmlDoc,
							"/response/resultCode");
					if ("0".equals(reCode)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
					}
				}
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		} catch (Exception e) {
			WSUtil.logError("changePassword", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 重置密码 无需校验旧密码
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/password", caption = "密码"),
			@Node(xpath = "//request/passwordType", caption = "密码类型") })
	public String resetPassword(@WebParam(name = "request") String request) {
		//获得重复订购控制开关
		String cvalue = "N";
		long seqOrderAgain = 0;
		try {
			long mstart = System.currentTimeMillis();
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc,
					"//request/accNbrType");
			String new_password = WSUtil.getXmlNodeText(doc,
					"//request/password");
			String passwordTime = WSUtil.getXmlNodeText(doc,
					"//request/passwordTime");
			String passwordType = WSUtil.getXmlNodeText(doc,
					"//request/passwordType");
			String channelId = WSUtil
					.getXmlNodeText(doc, "//request/channelId");
			String staffCode = WSUtil
					.getXmlNodeText(doc, "//request/staffCode");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			String systemIdComponentId = WSUtil.getXmlNodeText(doc, "//request/systemIdComponentId");
			OfferProd prod = null;
			Party party = null;
			String old_password = "";
			
			//重复提交拦截
			try {
				cvalue = intfSMO.getIntfReqCtrlValue("INTF_REQ_CTRL_CONTROL");
			} catch (Exception e) {
				cvalue = "N";
			}
			if(StringUtils.isNotBlank(accNbr) && "Y".equals(cvalue)){
				Map<String,Object> mk = new HashMap<String, Object>();
				mk.put("mainkey", accNbr);
				mk.put("orderType", "201407");
				if(intfSMO.queryBussinessOrder(mk)>0){
					return WSUtil.buildResponse("-1", "业务没竣工，请稍后再试！");
				}else{
					seqOrderAgain = intfSMO.querySeqBussinessOrder();
					mk.put("id", seqOrderAgain);
					intfSMO.saveOrUpdateBussinessOrderCheck(mk,"save");
				}
			}
			
			//对密码进行des解密：前提是平台送过来的密码是加密的
			//加密平台在intf.encryptvectorconfig中判断
			/*if(!"".equals(systemIdComponentId)&& systemIdComponentId != null && !"6090010018".equals(systemIdComponentId)){
				new_password = getEncrytStr(new_password,systemIdComponentId,"2");
			}*/
			
			
			if ("".equals(new_password) || "".equals(passwordType)
					|| "".equals(accNbr)) {
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST);
			}
			// add by wanghongli 检验新密码的格式 1，长度为六位 2，必须为数字
			char[] c = new_password.toCharArray();
			if (c.length != 6) {
				System.out.print("new_password.length==" + c.length);
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
			}
			for (int i = 0; i < c.length; i++) {
				if (!Character.isDigit(c[i])) {
					return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
				}
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				long start = System.currentTimeMillis();
				prod = intfSMO.getProdByAccessNumber(accNbr);
				if (isPrintLog) {
					System.out
							.println("resetPassword.intfSMO.getProdByAccessNumber(根据接入号取得产品) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				if (prod == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST,
							"根据接入号未查询到产品信息" + accNbr);
				}
				start = System.currentTimeMillis();
				party = offerFacade.getPartyByProdId(prod.getProdId());
				if (isPrintLog) {
					System.out
							.println("resetPassword.offerFacade.getPartyByProdId(查询客户信息) 执行时间"
									+ (System.currentTimeMillis() - start));
				}
				if (party == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST,
							"根据接入号未查询到客户信息" + accNbr);
				}
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				long start = System.currentTimeMillis();
				// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
				if (isPrintLog) {
					System.out
							.println("resetPassword.custFacade.getPartyByOtherCard(根据其它证件号查询客户信息) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
			} else {
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.INVALID_ACCNBR_TYPE);
			}
			// 产品密码
			if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)
					|| WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
				if (prod == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}
				// 产品查询密码
				if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
					long start = System.currentTimeMillis();
					// 查询其old密码
					old_password = intfSMO.queryProdQryPwdByProdId(prod
							.getProdId());
					if (isPrintLog) {
						System.out
								.println("resetPassword.intfSMO.queryProdQryPwdByProdId(查询产品查询密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
				}
				// 产品业务密码
				if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)) {
					long start = System.currentTimeMillis();
					// 查询其old密码
					old_password = intfSMO.queryProdBizPwdByProdId(prod
							.getProdId());
					if (isPrintLog) {
						System.out
								.println("resetPassword.intfSMO.queryProdBizPwdByProdId(查询产品业务密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
				}

				// 重置密码在途单判断 add by helinglong 20140915

				boolean checkOrderZt = checkOrderZt(accNbr, new String[] {
						WSDomain.OrderTypeId.CHANGE_PASSWORD,
						WSDomain.OrderTypeId.RESET_PASSWORD });
				if (checkOrderZt) {
					return WSUtil.buildResponse(ResultCode.ORDER_ONWAY,
							"存在在途单，不能受理密码重置业务！");
				}

				// 构建报文
				StringBuilder sb = new StringBuilder();
				sb.append("<request>");
				sb.append("<order>");
				sb.append(String.format("<orderTypeId>%s</orderTypeId>",
						WSDomain.OrderTypeId.RESET_PASSWORD)); // 修改产品密码
				if (systemId != null) {
					sb.append(String
							.format("<systemId>%s</systemId>", systemId));
				}
				sb.append(String.format("<prodSpecId>%s</prodSpecId>", prod
						.getProdSpecId()));
				sb.append(String.format("<feeType>%s</feeType>", 1));
				sb.append(String
						.format("<prodId>%s</prodId>", prod.getProdId()));
				sb.append(String.format("<anId>%s</anId>", accNbr));
				sb.append(String.format("<accessNumber>%s</accessNumber>",
						accNbr));
				sb.append(String.format("<acctCd>%s</acctCd>", party
						.getAcctCd()));
				sb.append(String.format("<partyId>%s</partyId>", party
						.getPartyId()));
				sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
				sb.append(String
						.format("<password><newPassword>%s</newPassword>",
								new_password));
				if (old_password != null) {
					sb.append(String.format("<oldPassword>%s</oldPassword>",
							old_password));
				}
				sb.append(String.format(
						"<prodPwTypeCd>%s</prodPwTypeCd></password>",
						passwordType));
				sb.append("</order>");
				sb.append(String.format("<areaId>%s</areaId>", areaId));
				sb
						.append(String.format("<channelId>%s</channelId>",
								channelId));
				sb
						.append(String.format("<staffCode>%s</staffCode>",
								staffCode));
				String staffId = WSUtil.getXmlNodeText(doc, "//staffId");
				sb
				.append(String.format("<staffId>%s</staffId>",
						staffId));
				sb.append("</request>");
				long start = System.currentTimeMillis();
				String response = orderService.businessCommon(sb.toString());
				if (isPrintLog) {
					System.out
							.println("resetPassword.orderService.businessService 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				Document docBusiness = WSUtil.parseXml(response);
				String resultBusiness = WSUtil.getXmlNodeText(docBusiness,
						"//response/resultCode");
				String pageInfo = WSUtil.getXmlNodeText(docBusiness,
						"//response/pageInfo");
				String payIndentId = WSUtil.getXmlNodeText(docBusiness,
						"//response/payIndentId");
				String olNbr = null;
				String olId = null;
				if ("6090010023".equals(systemId)
						|| "6090010060".equals(systemId)) {
					olNbr = WSUtil.getXmlNodeText(docBusiness,
							"//response/olNbr");
					olId = WSUtil
							.getXmlNodeText(docBusiness, "//response/olId");
				}
				if ("0".equals(resultBusiness)) {
					Map<String, Object> root = new HashMap<String, Object>();
					root.put("resultCode", ResultCode.SUCCESS);
					root.put("resultMsg", ResultCode.SUCCESS.getDesc());
					root.put("password", new_password);
					root.put("pageInfo", pageInfo);
					root.put("payIndentId", payIndentId);
					root.put("password", new_password);
					root.put("olNbr", olNbr);
					root.put("olId", olId);
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return mapEngine.transform("resetPassword", root);
				} else {
					return response;
				}
			}
			// 客户查询密码
			else if (WSDomain.PasswordType.CUSTOMER_QUERY.equals(passwordType)) {
				if (party == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				long start = System.currentTimeMillis();
				custFacade.changeCustomerPsd(party.getPartyId(), new_password,
						passwordTime);
				if (isPrintLog) {
					System.out
							.println("resetPassword.custFacade.changeCustomerPsd(修改客户密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("password", new_password);
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("resetPassword", root);
			}
			// 客户业务密码
			else if (WSDomain.PasswordType.CUSTOMER_BUSINESS
					.equals(passwordType)) {
				if (party == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}

					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				long start = System.currentTimeMillis();
				// 密码有效 修改密码
				custFacade.changeCustomerPsd(party.getPartyId(), new_password,
						passwordTime);
				if (isPrintLog) {
					System.out
							.println("resetPassword.custFacade.changeCustomerPsd(修改客户密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("password", new_password);
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}

				return mapEngine.transform("resetPassword", root);
			}
			if (isPrintLog) {
				System.out.println("调用营业接口resetPassword(重置密码)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			WSUtil.logError("resetPassword", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}finally{
			try {
				if(seqOrderAgain != 0 && "Y".equals(cvalue)){
					Map<String,Object> mk = new HashMap<String,Object>();
					mk.put("id", seqOrderAgain);
					intfSMO.saveOrUpdateBussinessOrderCheck(mk, "update");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private boolean checkOrderZt(String accessNumber, String[] orderTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		for (String s : orderTypeId) {
			list.add(s);
		}
		map.put("accNbr", accessNumber);
		map.put("typeCd", list);
		return intfSMO.qryProdOrderIsZtByOrderTypes(map);
	}

	/**
	 * 客户资料修改
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "/request/custInfo/partyId", caption = "客户Id"),
			@Node(xpath = "/request/custInfo/custName", caption = "客户名称"),
			@Node(xpath = "/request/custInfo/custType", caption = "客户类型") })
	public String modifyCustom(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document doc = WSUtil.parseXml(request);
			long start = System.currentTimeMillis();
			JSONObject custJsonString = modifyCustomOrderListFactory
					.generateOrderList(doc);
			if (isPrintLog) {
				System.out
						.println("modifyCustom.modifyCustomOrderListFactory.generateOrderList 执行时间:"
								+ (System.currentTimeMillis() - start));
			}
			if (custJsonString == null) {
				if (isPrintLog) {
					System.out.println("调用营业接口modifyCustom(客户资料修改)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
			}
			// 替换ol_id
			start = System.currentTimeMillis();
			JSONObject idJSON = soSaveSMO.saveOrderList(custJsonString);
			if (isPrintLog) {
				System.out
						.println("modifyCustom.soSaveSMO.saveOrderList(保存购物车) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			Long orderListId = idJSON.getJSONObject("ORDER_LIST-OL_ID")
					.getLong("-1");
			custJsonString.getJSONObject("orderList").getJSONObject(
					"orderListInfo").element("olId", orderListId);
			start = System.currentTimeMillis();
			Long result = soCommitSMO.updateCustInfo(custJsonString);
			if (isPrintLog) {
				System.out
						.println("modifyCustom.soCommitSMO.updateCustInfo(修改客户信息) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			if (result == null) {
				if (isPrintLog) {
					System.out.println("调用营业接口modifyCustom(客户资料修改)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("resultCode", ResultCode.SUCCESS.getCode());
			param.put("resultMsg", ResultCode.SUCCESS.getDesc());
			param.put("olNbr", intfSMO.getOlNbrByOlId(orderListId));
			param.put("olId", orderListId);
			start = System.currentTimeMillis();
			param.put("pageInfo", intfSMO.getPageInfo(orderListId.toString(),
					"1", "N"));
			if (isPrintLog) {
				System.out.println("modifyCustom.intfSMO.getPageInfo 执行时间:"
						+ (System.currentTimeMillis() - start));
				System.out.println("调用营业接口modifyCustom(客户资料修改)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("orderSubmit", param);
		} catch (Exception e) {
			WSUtil.logError("modifyCustom", request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口modifyCustom(客户资料修改)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 接应经理查询接口
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/flag", caption = "flag"),
			@Node(xpath = "/request/value", caption = "工号或姓名") })
	public String queryManager(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			// 1为工号 2为姓名
			String flag = WSUtil.getXmlNodeText(doc, "/request/flag");
			String value = WSUtil.getXmlNodeText(doc, "/request/value");
			logger.debug("接应经理查询开始，flag={}，value={}", new Object[] { flag,
					value });
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			if (flag.equals("1")) {
				resultList = intfSMO.queryStaffInfoByStaffNumber(value);
				logger.debug("接应查询结果{}", resultList);
			} else if (flag.equals("2")) {
				resultList = intfSMO.queryStaffInfoByStaffName(value);
			} else {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR,
						"flag无效！");
			}
			if (resultList.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("result", resultList);
			return mapEngine.transform("queryManagerList", root);
		} catch (Exception e) {
			WSUtil.logError("queryManager", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询客户信用度
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/prodId", caption = "用户：prodId") })
	public String qryCreditrating(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String prodId = WSUtil.getXmlNodeText(doc, "/request/prodId");// 客户ID
			String flag = WSUtil.getXmlNodeText(doc, "/request/flag");// 调用方标识
			Map<String, Object> result = billServiceImpl.qryCreditValue(flag,
					Long.valueOf(prodId));
			logger.debug("信用度查询结果:{}", result.toString());
			return mapEngine.transform("qryCreditrating", result);
		} catch (Exception e) {
			WSUtil.logError("qryCreditrating", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 合同管理客户查询
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/partyName", caption = "客户名称") })
	public String selectPartyInfo(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String partyName = WSUtil.getXmlNodeText(doc, "/request/partyName");// 客户名称
			String page = WSUtil.getXmlNodeText(doc, "/request/page");// 页码
			String pageSize = WSUtil.getXmlNodeText(doc, "/request/pageSize");// 页面个数
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("partyName", partyName.trim());
			if (StringUtils.isNotBlank(pageSize)
					&& StringUtils.isNotBlank(page)) {
				Integer startNum = (Integer.valueOf(page) - 1)
						* Integer.valueOf(pageSize);
				Integer endNum = Integer.valueOf(page)
						* Integer.valueOf(pageSize) + 1;
				param.put("startNum", startNum);
				param.put("endNum", endNum);
			}
			List<Map<String, String>> result = intfSMO.qryPartyInfo(param);
			if (result.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			logger.debug("合同管理客户查询结果：{}", result.toString());
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("result", result);
			return mapEngine.transform("qryPartyInfo", root);
		} catch (Exception e) {
			WSUtil.logError("qryPartyInfo", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 合同管理客户经理查询
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/identityNum", caption = "客户标识码") })
	public String selectPartyManager(@WebParam(name = "request") String request) {
		try {
			String xml = "";// 系统管理返回员工信息XML
			Document doc = WSUtil.parseXml(request);
			String identityNum = WSUtil.getXmlNodeText(doc,
					"/request/identityNum");// 客户标识码
			Map<String, Object> partyInfo = intfSMO
					.qryPartyManager(identityNum);
			if (partyInfo == null) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST,
						"客户信息为空");
			}
			logger.debug("客户信息：identityNum={}，party={}", new Object[] {
					identityNum, partyInfo.toString() });
			try {
				xml = smService.findStaffInfoByPartyId(Long.valueOf(partyInfo
						.get("PARTYID").toString()));
				logger.debug("员工信息：{}", new Object[] { xml });
			} catch (NumberFormatException e) {
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e
						.getMessage());
			} catch (BssException e) {
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e
						.getMessage());
			}
			if (StringUtils.isBlank(xml)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Document returnXml = WSUtil.parseXml(xml);
			String staffNumber = WSUtil.getXmlNodeText(returnXml,
					"//staffNumber");// 员工工号
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("staffNumber", staffNumber);
			root.put("partyName", partyInfo.get("NAME"));
			return mapEngine.transform("qryPartyManager", root);
		} catch (Exception e) {
			WSUtil.logError("qryPartyManager", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 客户品牌查询
	 * 客户等级改造
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/accNum", caption = "接入号") })
	public String getBrandLevelDetail(@WebParam(name = "request") String request) {
		try {
			System.out.println("调用营业接口getBrandLevelDetail入参：" + request);
			Long mstart = System.currentTimeMillis();
			Document doc = WSUtil.parseXml(request);
			String accNum = WSUtil.getXmlNodeText(doc, "/request/accNum");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("accNum", accNum);
			long start = System.currentTimeMillis();
			Map<String, Object> result = intfSMO.getBrandLevelDetail(param);
			if (isPrintLog) {
				System.out
						.println("当前线程(id:"
								+ Thread.currentThread().getId()
								+ " name:"
								+ Thread.currentThread().getName()
								+ ")getBrandLevelDetail.intfSMO.getBrandLevelDetail(客户品牌查询) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}
			if (result == null) {
				if (isPrintLog) {
					System.out.println("当前线程(id:"
							+ Thread.currentThread().getId() + " name:"
							+ Thread.currentThread().getName()
							+ ")调用营业接口getBrandLevelDetail执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL,
						"客户基础信息为空");
			}
			logger.debug("客户品牌信息查询结果：{}", new Object[] { result.toString() });

			result.put("GREATLEVEL", "积分等级");// TODO:调用积分平台接口
			/*
			 * start = System.currentTimeMillis(); Map<String, Object> creditMap
			 * = billServiceImpl.qryCreditValue("CRM",
			 * Long.valueOf(result.get("PRODID") .toString())); if (creditMap !=
			 * null) { result.put("CREDITNUMBER", creditMap.get("creditValue"));
			 * }
			 */
			result.put("CREDITNUMBER", null);
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("prodId", result.get("PRODID"));
			start = System.currentTimeMillis();
			// 查询客户等级
//			CustSegmentDto custGradeInfo = custBasicSMO.queryCustGrade(Long
//					.valueOf(result.get("PARTYID").toString()), null);
			CustClubMemberDto CustClubMemberDto = custBasicSMO.queryCustGrade(Long
			.valueOf(result.get("PARTYID").toString()));
			
			
			if (isPrintLog) {
				System.out
						.println("当前线程(id:"
								+ Thread.currentThread().getId()
								+ " name:"
								+ Thread.currentThread().getName()
								+ ")getBrandLevelDetail.custBasicSMO.queryCustGrade(查询客户等级) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}
			logger.debug("客户等级查询结果：{}", new Object[] { JSONObject.fromObject(
					CustClubMemberDto).toString() });
			result.put("CUSTLEVELNAME", CustClubMemberDto.getPartyGradeName());
			if (CustClubMemberDto != null) {
//				
				String custLevelId = "";
//				if (custGradeInfo.isDiamond()) {
//					custLevelId = WSDomain.CARD_DIAMOND;
//				} else if (custGradeInfo.isGold()) {
//					custLevelId = WSDomain.CARD_GOLD;
//				} else if (custGradeInfo.isSilver()) {
//					custLevelId = WSDomain.CARD_SILVER;
//				} else {
//					custLevelId = WSDomain.CARD_COMMON;
//				}
				if(CustClubMemberDto.getClubMember() != null){
					custLevelId= ""+CustClubMemberDto.getClubMember().getMembershipLevel();
				}
				
				result.put("CUSTLEVELNAME", CustClubMemberDto.getPartyGradeName());
//				String custLevelId = "" ;
				
				//新需求的custLevelId
				result.put("CUSTLEVELID", custLevelId);
			}
			Map<String, Object> offerInfo = intfSMO.isHaveInOffer(req);
			if (offerInfo != null) {
				result.put("OFFERNAME", offerInfo.get("OFFERNAME"));
				result.put("OFFERSPECID", offerInfo.get("OFFERSPECID"));
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("result", result);
			if (isPrintLog) {
				System.out.println("当前线程(id:" + Thread.currentThread().getId()
						+ " name:" + Thread.currentThread().getName()
						+ ")调用营业接口getBrandLevelDetail执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("getBrandLevelDetail", root);
		} catch (Exception e) {
			WSUtil.logError("getBrandLevelDetail", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 封装国政通校验 商城身份证校验+crm业务规则校验
	 * 
	 * @author CHENJNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/name", caption = "姓名"),
			@Node(xpath = "/request/identifyValue", caption = "身份证号"),
			@Node(xpath = "/request/channelId", caption = "渠道Id"),
			@Node(xpath = "/request/staffId", caption = "员工Id") })
	public String checkResultIn(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document doc = WSUtil.parseXml(request);
			String name = WSUtil.getXmlNodeText(doc, "/request/name");
			String identifyValue = WSUtil.getXmlNodeText(doc,
					"/request/identifyValue");
			String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId");
			String channelId = WSUtil.getXmlNodeText(doc, "/request/channelId");
			long start = System.currentTimeMillis();
			Map<String, String> map = spServiceSMO.getGZTCheckResult(name,
					identifyValue, channelId, staffId);
			if (isPrintLog) {
				System.out
						.println("checkResultIn.spServiceSMO.getGZTCheckResult(封装国政通接口) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}
			String identifyInfo = map.get("result");
			if (!"0".equals(identifyInfo)) {
				if (isPrintLog) {
					System.out
							.println("调用营业接口checkResultIn(封装国政通校验 商城身份证校验+crm业务规则校验)执行时间："
									+ (System.currentTimeMillis() - mstart)
									+ "ms");
				}
				return WSUtil.buildResponse(ResultCode.GZT_QUERY_FAILED);
			}
			start = System.currentTimeMillis();
			// CRM业务规则校验，1、是否黑名单 2、个人客户下是否超过5个C网用户
			Long partyId = intfSMO.getPartyIdByIdentityNum(identifyValue);
			if (isPrintLog) {
				System.out
						.println("checkResultIn.intfSMO.getPartyIdByIdentityNum(根据证件号码得到客户id) 执行时间:"
								+ (System.currentTimeMillis() - start));
			}

			int cnt = 0;
			if (partyId != null) {
				start = System.currentTimeMillis();
				boolean res = custBasicSMO.isInCustBlackList(partyId);
				if (isPrintLog) {
					System.out
							.println("checkResultIn.custBasicSMO.isInCustBlackList(该客户是否在黑名单中) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				if (res) {
					if (isPrintLog) {
						System.out
								.println("调用营业接口checkResultIn(封装国政通校验 商城身份证校验+crm业务规则校验)执行时间："
										+ (System.currentTimeMillis() - mstart)
										+ "ms");
					}
					return WSUtil.buildResponse(
							ResultCode.GZT_VALIDATION_FAILED,
							"该身份证办理客户为黑名单，无法办理");
				}
				start = System.currentTimeMillis();
				cnt = intfSMO.checkProductNumByPartyId(partyId);
				if (isPrintLog) {
					System.out
							.println("checkResultIn.intfSMO.checkProductNumByPartyId(查询客户下C网产品的数量（含在途单）) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				/*
				 * if (cnt >= 5) { if (isPrintLog) {System.out.println(
				 * "调用营业接口checkResultIn(封装国政通校验 商城身份证校验+crm业务规则校验)执行时间：" +
				 * (System.currentTimeMillis() - mstart) + "ms"); } return
				 * WSUtil.buildResponse(ResultCode.GZT_VALIDATION_FAILED,
				 * "该身份证办理的客户下C网用户数已到5个，无法办理"); }
				 */
			}
			cnt = 5 - cnt;
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("cnt", cnt);
			root.put("data", map);
			if (isPrintLog) {
				System.out
						.println("调用营业接口checkResultIn(封装国政通校验 商城身份证校验+crm业务规则校验)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return mapEngine.transform("checkResultIn", root);
		} catch (Exception e) {
			WSUtil.logError("checkResultIn", request, e);
			if (isPrintLog) {
				System.out
						.println("调用营业接口checkResultIn(封装国政通校验 商城身份证校验+crm业务规则校验)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 用户是否已有租金产品校验查询
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/phoneNumber", caption = "接入号"),
			@Node(xpath = "/request/offerSpecId", caption = "销售品规格") })
	public String getUserZJInfoByAccessNumber(
			@WebParam(name = "request") String request) {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			Document doc = WSUtil.parseXml(request);
			String phoneNumber = WSUtil.getXmlNodeText(doc,
					"/request/phoneNumber");
			String offerSpecId = WSUtil.getXmlNodeText(doc,
					"/request/offerSpecId");
			param.put("accNum", phoneNumber);
			List<Map<String, Object>> zjInfoList = intfSMO
					.getUserZJInfoByAccessNumber(param);
			String resultMsg = "";

			// 补充报文掉一点通
			Map<String, Object> offerProdInfo = intfSMO
					.queryOfferProdInfoByAccessNumber(phoneNumber);
			if (offerProdInfo == null) {
				return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
			}
			if (zjInfoList.size() == 0) {

				boolean result = custBasicSMO.isInCustBlackList(Long
						.valueOf(offerProdInfo.get("PARTYID").toString()));
				if (result) {
					// return WSUtil.buildResponse(ResultCode.UNSUCCESS,
					// "黑名单用户不能办理该业务！");
					resultMsg = "黑名单用户不能办理该业务! ";
				}
				// XXX: 根据mainId判断是否允许办理租机（是否判断已订购销售品和订购的租机是否互斥再看）
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mainId", offerProdInfo.get("MAINID").toString());
				map.put("id", offerSpecId);
				boolean live = intfSMO.yesOrNoAliveInOfferSpecRoles(map);
				if (!live) {
					return WSUtil.buildResponse(ResultCode.ZJ_INFO_IS_NOTBLZJ,
							resultMsg + "主套餐不能办理该业务");
				}
				param.put("prodInfo", offerProdInfo);
				param.put("resultCode", ResultCode.SUCCESS.getCode());
				param.put("resultMsg", ResultCode.SUCCESS.getDesc());
				return mapEngine
						.transform("getUserZJInfoByAccessNumber", param);
			} else {

				// XXX: 根据mainId判断是否允许办理租机（是否判断已订购销售品和订购的租机是否互斥再看）
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mainId", offerProdInfo.get("MAINID").toString());
				map.put("id", offerSpecId);
				boolean live = intfSMO.yesOrNoAliveInOfferSpecRoles(map);
				if (!live) {
					// return WSUtil.buildResponse(ResultCode.UNSUCCESS,
					// resultMsg+"主套餐不能办理该业务");
					param.put("prodInfo", offerProdInfo);
					param.put("zjInfo", zjInfoList.get(0));
					param.put("resultCode",
							ResultCode.ZJ_INFO_IS_NOT_NULL_NOTBLZJ.getCode());
					param.put("resultMsg",
							ResultCode.ZJ_INFO_IS_NOT_NULL_NOTBLZJ.getDesc());
				} else {
					param.put("zjInfo", zjInfoList.get(0));
					param.put("resultCode", ResultCode.ZJ_INFO_IS_NOT_NULL
							.getCode());
					param.put("resultMsg", ResultCode.ZJ_INFO_IS_NOT_NULL
							.getDesc());
				}

				return mapEngine
						.transform("getUserZJInfoByAccessNumber", param);
			}
		} catch (Exception e) {
			WSUtil.logError("getUserZJInfoByAccessNumber", request, e);
			param.put("resultCode", ResultCode.SYSTEM_ERROR.getCode());
			param.put("resultMsg", e.getMessage());
			return mapEngine.transform("getUserZJInfoByAccessNumber", param);
		}
	}

	/**
	 * 用户租金到期时间查询
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/accNum", caption = "接入号") })
	public String validateContractInfo(
			@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String accNum = WSUtil.getXmlNodeText(doc, "/request/accNum");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("accNum", accNum);
			List<Map<String, Object>> zjInfoList = intfSMO
					.getUserZJInfoByAccessNumber(param);
			if (zjInfoList.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL,
						"未查到用户租金信息！");
			}
			logger.debug("用户租金信息查询结果：", zjInfoList.toString());
			param.put("zjInfoList", zjInfoList);
			param.put("resultCode", ResultCode.SUCCESS.getCode());
			param.put("resultMsg", ResultCode.SUCCESS.getDesc());
			return mapEngine.transform("validateContractInfo", param);
		} catch (Exception e) {
			WSUtil.logError("validateContractInfo", request, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 统一支付 订单支付成功通知接口
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "/request/requestId", caption = "请求流水"),
			@Node(xpath = "/request/payIndentId", caption = "订单号"),
			@Node(xpath = "/request/requestTime", caption = "请求时间"),
			@Node(xpath = "/request/platId", caption = "平台标识"),
			@Node(xpath = "/request/cycleId", caption = "支付周期"),
			@Node(xpath = "/request/staffCode", caption = "营业员编码") })
	public String indentPayNotice(@WebParam(name = "request") String request) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		Map<String, String> mapReq = new HashMap<String, String>();
		Map<String, String> mapResp = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = WSUtil.parseXml(request);
		} catch (DocumentException e1) {
			return WSUtil.buildResponse("-999", "xml解析异常");
		}
		String requestId = WSUtil.getXmlNodeText(doc, "/request/requestId");
		String payIndentId = WSUtil.getXmlNodeText(doc, "/request/payIndentId");
		String requestTime = WSUtil.getXmlNodeText(doc, "/request/requestTime");
		String platId = WSUtil.getXmlNodeText(doc, "/request/platId");
		String cycleId = WSUtil.getXmlNodeText(doc, "/request/cycleId");
		String staffCode = WSUtil.getXmlNodeText(doc, "/request/staffCode");
		String staffId = "";
		staffId = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
		if (StringUtils.isBlank(staffId)) {
			return WSUtil.buildResponse("-999", "staffCode错误");
		}
		try {
			Document document = WSUtil.parseXml(staffId);
			staffId = WSUtil.getXmlNodeText(document,
					"/StaffInfos/StaffInfo/staffNumberInfo/staffId");
		} catch (DocumentException e1) {
			WSUtil.logError("indentPayNotice", request, e1);
		}
		try {
			mapReq.put("REQUEST_ID", requestId);
			mapReq.put("PAY_INDENT_ID", payIndentId);
			mapReq.put("REQUEST_TIME", requestTime);
			mapReq.put("PLAT_ID", platId);
			mapReq.put("CYCLE_ID", cycleId);
			mapReq.put("STAFF_ID", staffId);
			if ("04".equals(platId)) {
				mapResp = storeForOnLineMall.callBackUnityPayData(mapReq);
			} else if ("03".equals(platId)) {
				String isDLS = intfSMO.isPKagent(payIndentId);
				if (isDLS != null) {
					mapResp = soBatchFileSMO.updateBamAcctForUnitPay(mapReq);
				} else if (isDLS == null) {
					mapResp = chargeManager.callBackUnityPayData(mapReq);
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			String resultRe = "";
			if (mapResp.keySet().contains("RESULT")) {
				resultRe = mapResp.get("RESULT");
			}
			String responseIdRe = requestId;
			if (mapResp.keySet().contains("RESPONSE_ID")) {
				responseIdRe = mapResp.get("RESPONSE_ID");
			}
			String responseTimeRe = "";
			if (mapResp.keySet().contains("RESPONSE_TIME")) {
				responseTimeRe = mapResp.get("RESPONSE_TIME");
			}
			String payIndentIdRe = "";
			if (mapResp.keySet().contains("PAY_INDENT_ID")) {
				payIndentIdRe = mapResp.get("PAY_INDENT_ID");
			}
			param.put("result", resultRe);
			param.put("resultMsg", "");
			param.put("responseId", responseIdRe);
			param.put("responseTime", responseTimeRe);
			param.put("payIndentId", payIndentIdRe);
			return mapEngine.transform("callBackUnityPayData", param);
		} catch (Exception e) {
			WSUtil.logError("callBackUnityPayData", request, e);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("result", -999);
			param.put("resultMsg", "系统异常");
			param.put("responseId", requestId);
			param.put("payIndentId", payIndentId);
			param.put("responseTime", requestTime);
			return mapEngine.transform("callBackUnityPayData", param);
		}
	}

	/**
	 * 楼宇查询
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return 做成多入参而不是xml入参的原因是1.0就是这样的，这样大家改动小
	 */
	@WebMethod
	public String queryBuilding(String code, String name, String address,
			int pageSize, int currentPage) {
		try {
			Map<String, Object> mapIn = new HashMap<String, Object>();
			mapIn.put("pageSize", pageSize);
			mapIn.put("curPage", currentPage);
			if (StringUtils.isNotBlank(code)) {
				mapIn.put("buildingCode", code);
			}
			if (StringUtils.isNotBlank(name)) {
				mapIn.put("buildingName", name);
			}
			if (StringUtils.isNotBlank(address)) {
				mapIn.put("buildingAddress", address);
			}
			Map<String, Object> mapOut = offerSMO.queryBuildingListInfo(mapIn);
			if (mapOut != null && mapOut.get("buildingListInfo") != null) {
				mapOut.put("resultCode", ResultCode.SUCCESS);
				mapOut.put("resultMsg", ResultCode.SUCCESS.getDesc());
				return mapEngine.transform("queryBuilding", mapOut);
			} else {
				return WSUtil.buildResponse(ResultCode.BUILDING_IS_NOT_EXIST);
			}
		} catch (Exception e) {
			WSUtil.logError("queryBuilding", "code=" + code + " name=" + name
					+ " address=" + address + " pageSize=" + pageSize
					+ " curPage=" + currentPage, e);
			return WSUtil
					.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	@WebMethod
	public String queryTaxPayer(@WebParam(name = "request") String request) {
		Document doc = null;
		try {
			doc = WSUtil.parseXml(request);
		} catch (DocumentException e1) {
			return WSUtil.buildResponse("1", "xml解析异常");
		}
		String type = WSUtil.getXmlNodeText(doc, "/requestinfo/userType");
		String value = WSUtil.getXmlNodeText(doc, "/requestinfo/value");
		// 判断客户是否存在
		boolean isExistsParty = intfSMO.checkIsExistsParty(value);
		Map<String, Object> mapOut = new HashMap<String, Object>();
		List<Map<String, Object>> listTaxPayer = new ArrayList<Map<String, Object>>();
		if (isExistsParty) {
			listTaxPayer = intfSMO.queryTaxPayer(value);
		} else {
			mapOut.put("resultCode", "1");
			mapOut.put("resultMsg", "该客户信息: " + value + " 不存在");
			mapOut.put("listTaxPayer", listTaxPayer);
			return mapEngine.transform("queryTaxPayer", mapOut);
		}
		if (listTaxPayer.size() > 0) {
			mapOut.put("resultCode", ResultCode.SUCCESS);
			mapOut.put("resultMsg", ResultCode.SUCCESS.getDesc());
			mapOut.put("listTaxPayer", listTaxPayer);
			return mapEngine.transform("queryTaxPayer", mapOut);
		} else {
			mapOut.put("resultCode", "1");
			mapOut.put("resultMsg", "纳税人信息不存在");
			mapOut.put("listTaxPayer", listTaxPayer);
			return mapEngine.transform("queryTaxPayer", mapOut);
		}
	}

	/**
	 * 提供给统一支付的查询接口
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/payIdentityId", caption = "订单Id") })
	public String queryOrderList(@WebParam(name = "request") String request) {
		Document doc;
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();
		try {
			doc = WSUtil.parseXml(request);
			String payIdentityId = WSUtil.getXmlNodeText(doc,
					"/request/payIdentityId");
			String orderId = payIdentityId.substring(2);
			orderInfoMap = intfSMO.queryOrderList(orderId);

		} catch (Exception e) {
			orderInfoMap.clear();
			orderInfoMap.put("resultCode", "1");
			orderInfoMap.put("resultMsg", e.getMessage());
		}
		return mapEngine.transform("queryOrderList", orderInfoMap);
	}

	/**
	 * 通过uim卡号查询对应手机号码
	 * 
	 * @param request
	 *            <request> <terminalCode>8986035731010040433</terminalCode>
	 *            <channelId>510001</channelId> <staffCode>bj1001</staffCode>
	 *            </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/terminalCode", caption = "卡号") })
	public String findTelphoneByCardno(
			@WebParam(name = "request") String request) {
		Document doc;
		String terminalCode;
		String telphone;
		try {
			doc = WSUtil.parseXml(request);
			terminalCode = WSUtil.getXmlNodeText(doc, "/request/terminalCode");
			if (StringUtil.isEmpty(terminalCode)) {
				return WSUtil.buildResponse("1", "传入的卡号错误，请检查！");
			}
			telphone = intfSMO.findTelphoneByCardno(terminalCode);
			if (StringUtil.isEmpty(telphone)) {
				return WSUtil.buildResponse("1", "根据卡号未查询到手机号码！卡号："
						+ terminalCode);
			}
			return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "telphone",
					telphone);
		} catch (DocumentException e) {
			logger.error("findTelphoneByCardno方法异常，异常信息%s", e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 通过作废的UIM卡号查询对应的补卡手机号码
	 * 
	 * @param request
	 *            <request> <terminalCode>8986030991010081033</terminalCode>
	 *            <channelId>510001</channelId> <staffCode>bj1001</staffCode>
	 *            </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/terminalCode", caption = "卡号") })
	public String findTelphoneByDiscard(
			@WebParam(name = "request") String request) {
		Document doc;
		String terminalCode;
		String telphone;
		try {
			doc = WSUtil.parseXml(request);
			terminalCode = WSUtil.getXmlNodeText(doc, "/request/terminalCode");
			if (StringUtil.isEmpty(terminalCode)) {
				return WSUtil.buildResponse("1", "传入的卡号错误，请检查！");
			}
			telphone = intfSMO.findTelphoneByDiscard(terminalCode);
			if (StringUtil.isEmpty(telphone)) {
				return WSUtil.buildResponse("1", "根据卡号未查询到补卡手机号码！卡号："
						+ terminalCode);
			}
			return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "telphone",
					telphone);
		} catch (DocumentException e) {
			logger.error("findTelphoneByDiscard方法异常，异常信息%s", e.getMessage());
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * 重置密码 无需校验旧密码（改造，调uam接口同步）
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/password", caption = "密码"),
			@Node(xpath = "//request/passwordType", caption = "密码类型") })
	public String resetPasswordDo(@WebParam(name = "request") String request) {
		try {
			long mstart = System.currentTimeMillis();
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc,
					"//request/accNbrType");
			String new_password = WSUtil.getXmlNodeText(doc,
					"//request/password");
			String passwordTime = WSUtil.getXmlNodeText(doc,
					"//request/passwordTime");
			String passwordType = WSUtil.getXmlNodeText(doc,
					"//request/passwordType");
			String channelId = WSUtil
					.getXmlNodeText(doc, "//request/channelId");
			String staffCode = WSUtil
					.getXmlNodeText(doc, "//request/staffCode");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			OfferProd prod = null;
			Party party = null;
			String systemIdComponentId = WSUtil.getXmlNodeText(doc, "//request/systemIdComponentId");
			

			// :todo new_password 对密码进行3des解密
			//对外围系统传过来的密码进行解密，根据encryptVectorConfig表里面进行判断是否需要解密
			if(!"".equals(systemIdComponentId)&& systemIdComponentId != null){
				new_password = getEncrytStr(new_password,systemIdComponentId,"2");
			}

			String old_password = "";
			if ("".equals(new_password) || "".equals(passwordType)
					|| "".equals(accNbr)) {
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST);
			}
			// add by wanghongli 检验新密码的格式 1，长度为六位 2，必须为数字
			char[] c = new_password.toCharArray();
			if (c.length != 6) {
				System.out.print("new_password.length==" + c.length);
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
			}
			for (int i = 0; i < c.length; i++) {
				if (!Character.isDigit(c[i])) {
					return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
				}
			}
			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				long start = System.currentTimeMillis();
				prod = intfSMO.getProdByAccessNumber(accNbr);
				if (isPrintLog) {
					System.out
							.println("resetPassword.intfSMO.getProdByAccessNumber(根据接入号取得产品) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				if (prod == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST,
							"根据接入号未查询到产品信息" + accNbr);
				}
				start = System.currentTimeMillis();
				party = offerFacade.getPartyByProdId(prod.getProdId());
				if (isPrintLog) {
					System.out
							.println("resetPassword.offerFacade.getPartyByProdId(查询客户信息) 执行时间"
									+ (System.currentTimeMillis() - start));
				}
				if (party == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST,
							"根据接入号未查询到客户信息" + accNbr);
				}
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				long start = System.currentTimeMillis();
				// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
				if (isPrintLog) {
					System.out
							.println("resetPassword.custFacade.getPartyByOtherCard(根据其它证件号查询客户信息) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
			} else {
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return WSUtil.buildResponse(ResultCode.INVALID_ACCNBR_TYPE);
			}

			// 产品密码
			if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)
					|| WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
				if (prod == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}
				// 产品查询密码
				if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
					long start = System.currentTimeMillis();
					// 查询其old密码
					old_password = intfSMO.queryProdQryPwdByProdId(prod
							.getProdId());
					if (isPrintLog) {
						System.out
								.println("resetPassword.intfSMO.queryProdQryPwdByProdId(查询产品查询密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
				}
				// 产品业务密码
				if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)) {
					long start = System.currentTimeMillis();
					// 查询其old密码
					old_password = intfSMO.queryProdBizPwdByProdId(prod
							.getProdId());
					if (isPrintLog) {
						System.out
								.println("resetPassword.intfSMO.queryProdBizPwdByProdId(查询产品业务密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
				}

				// 重置密码在途单判断 add by helinglong 20140915

				boolean checkOrderZt = checkOrderZt(accNbr, new String[] {
						WSDomain.OrderTypeId.CHANGE_PASSWORD,
						WSDomain.OrderTypeId.RESET_PASSWORD });
				if (checkOrderZt) {
					return WSUtil.buildResponse(ResultCode.ORDER_ONWAY,
							"存在在途单，不能受理密码重置业务！");
				}

				if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
					// 构建报文
					StringBuilder sb = new StringBuilder();
					sb.append("<request>");
					sb.append("<order>");
					sb.append(String.format("<orderTypeId>%s</orderTypeId>",
							WSDomain.OrderTypeId.RESET_PASSWORD)); // 修改产品密码
					if (systemId != null) {
						sb.append(String.format("<systemId>%s</systemId>",
								systemId));
					}
					sb.append(String.format("<prodSpecId>%s</prodSpecId>", prod
							.getProdSpecId()));
					sb.append(String.format("<feeType>%s</feeType>", 1));
					sb.append(String.format("<prodId>%s</prodId>", prod
							.getProdId()));
					sb.append(String.format("<anId>%s</anId>", accNbr));
					sb.append(String.format("<accessNumber>%s</accessNumber>",
							accNbr));
					sb.append(String.format("<acctCd>%s</acctCd>", party
							.getAcctCd()));
					sb.append(String.format("<partyId>%s</partyId>", party
							.getPartyId()));
					sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
					sb.append(String.format(
							"<password><newPassword>%s</newPassword>",
							new_password));
					if (old_password != null) {
						sb.append(String.format(
								"<oldPassword>%s</oldPassword>", old_password));
					}
					sb.append(String.format(
							"<prodPwTypeCd>%s</prodPwTypeCd></password>",
							passwordType));
					sb.append("</order>");
					sb.append(String.format("<areaId>%s</areaId>", areaId));
					sb.append(String.format("<channelId>%s</channelId>",
							channelId));
					sb.append(String.format("<staffCode>%s</staffCode>",
							staffCode));
					sb.append("</request>");
					long start = System.currentTimeMillis();
					String response = orderService.businessCommon(sb
							.toString());
					if (isPrintLog) {
						System.out
								.println("resetPassword.orderService.businessService 执行时间:"
										+ (System.currentTimeMillis() - start));
					}
					Document docBusiness = WSUtil.parseXml(response);
					String resultBusiness = WSUtil.getXmlNodeText(docBusiness,
							"//response/resultCode");
					String pageInfo = WSUtil.getXmlNodeText(docBusiness,
							"//response/pageInfo");
					String payIndentId = WSUtil.getXmlNodeText(docBusiness,
							"//response/payIndentId");
					String olNbr = null;
					String olId = null;
					if ("6090010023".equals(systemId)
							|| "6090010060".equals(systemId)) {
						olNbr = WSUtil.getXmlNodeText(docBusiness,
								"//response/olNbr");
						olId = WSUtil.getXmlNodeText(docBusiness,
								"//response/olId");
					}
					if ("0".equals(resultBusiness)) {
						Map<String, Object> root = new HashMap<String, Object>();
						root.put("resultCode", ResultCode.SUCCESS);
						root.put("resultMsg", ResultCode.SUCCESS.getDesc());
						root.put("password", new_password);
						root.put("pageInfo", pageInfo);
						root.put("payIndentId", payIndentId);
						root.put("password", new_password);
						root.put("olNbr", olNbr);
						root.put("olId", olId);
						if (isPrintLog) {
							System.out.println("调用营业接口resetPassword(重置密码)执行时间："
									+ (System.currentTimeMillis() - mstart)
									+ "ms");
						}
						return mapEngine.transform("resetPassword", root);
					} else {
						return response;
					}
				} else {// 业务密码送uam处理
					
					
					// 组装送uam接口报文
					String sendUamParamStr = getSendUamParamStr(accNbr,getNbrTypeByAccNbr(accNbr), new_password,appendStr(request,"2","2"));
					System.out.println("密码重置业务密码："+sendUamParamStr);
					String result = sendMsg(sendUamParamStr);
					System.out.println("uam:"+result);
					// 调用uam接口
					Map<String, Object> root = new HashMap<String, Object>();
					root.put("resultCode", ResultCode.SUCCESS);
					root.put("resultMsg", ResultCode.SUCCESS.getDesc());
					root.put("password", new_password);
					return mapEngine.transform("resetPassword", root);
				}
			}
			// 客户查询密码
			else if (WSDomain.PasswordType.CUSTOMER_QUERY.equals(passwordType)) {
				if (party == null) {
					if (isPrintLog) {
						System.out.println("调用营业接口resetPassword(重置密码)执行时间："
								+ (System.currentTimeMillis() - mstart) + "ms");
					}
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				long start = System.currentTimeMillis();
				custFacade.changeCustomerPsd(party.getPartyId(), new_password,
						passwordTime);
				if (isPrintLog) {
					System.out
							.println("resetPassword.custFacade.changeCustomerPsd(修改客户密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("password", new_password);
				if (isPrintLog) {
					System.out.println("调用营业接口resetPassword(重置密码)执行时间："
							+ (System.currentTimeMillis() - mstart) + "ms");
				}
				return mapEngine.transform("resetPassword", root);
			}
			// 客户业务密码
			else if (WSDomain.PasswordType.CUSTOMER_BUSINESS
					.equals(passwordType)) {
				if (party == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				// 密码有效 修改密码
//				custFacade.changeCustomerPsd(party.getPartyId(), new_password,
//						passwordTime);
				List<PartyIdentity> identities = party.getIdentities();
				if(identities == null){
					return WSUtil.buildResponse("1", "无客户证件信息！");
				}
				String identityNum = "";
				for(PartyIdentity p:identities){
					Integer identidiesTypeCd = p.getIdentidiesTypeCd();
					if(identidiesTypeCd == 13 || identidiesTypeCd == 18){
						identityNum = p.getIdentityNum();
						break;
					}
				}
				String sendUamParamStr = getSendUamParamStr(identityNum,"0000000", new_password,appendStr(request,"2","4"));
				//调uam接口
				System.out.println("密码重置客户业务密码："+sendUamParamStr);
				
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", ResultCode.SUCCESS);
				root.put("resultMsg", ResultCode.SUCCESS.getDesc());
				root.put("password", new_password);

				return mapEngine.transform("resetPassword", root);
			}
			if (isPrintLog) {
				System.out.println("调用营业接口resetPassword(重置密码)执行时间："
						+ (System.currentTimeMillis() - mstart) + "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		} catch (Exception e) {
			WSUtil.logError("resetPassword", request, e);
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	
	
	
	
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码"),
			@Node(xpath = "//request/new_password", caption = "新密码"),
			@Node(xpath = "//request/old_password", caption = "旧密码"),
			@Node(xpath = "//request/passwordType", caption = "密码类型") })
	public String changePasswordDo(@WebParam(name = "request") String request) {
		try {
			int checkResult = 0;
			Document doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc,
					"//request/accNbrType");
			String old_password = WSUtil.getXmlNodeText(doc,
					"//request/old_password");
			String new_password = WSUtil.getXmlNodeText(doc,
					"//request/new_password");
			String passwordType = WSUtil.getXmlNodeText(doc,
					"//request/passwordType");
			String channelId = WSUtil
					.getXmlNodeText(doc, "//request/channelId");
			String staffCode = WSUtil
					.getXmlNodeText(doc, "//request/staffCode");
			String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			
			String systemIdComponentId = WSUtil.getXmlNodeText(doc, "//request/systemId");
			OfferProd prod = null;
			Party party = null;
			long start = 0l;
			
			//对外围系统传过来的密码进行解密，根据encryptVectorConfig表里面进行判断是否需要解密
			if(!"".equals(systemIdComponentId)&& systemIdComponentId != null){
				new_password = getEncrytStr(new_password,systemIdComponentId,"2");
				old_password = getEncrytStr(old_password,systemIdComponentId,"2");
			}
			
			
			
			if ("".equals(new_password) || "".equals(old_password)
					|| "".equals(passwordType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST);
			}
			// add by wanghongli 检验新密码的格式 1，长度为六位 2，必须为数字
			char[] c = new_password.toCharArray();
			if (c.length != 6) {
				System.out.print("new_password.length==" + c.length);
				return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
			}
			for (int i = 0; i < c.length; i++) {
				if (!Character.isDigit(c[i])) {
					return WSUtil.buildResponse(ResultCode.PW_RULEERROR);
				}
			}

			// 密码修改在途单判断
			// add by helinglong 20141009
			boolean checkOrderZt = checkOrderZt(accNbr, new String[] {
					WSDomain.OrderTypeId.CHANGE_PASSWORD,
					WSDomain.OrderTypeId.RESET_PASSWORD });
			if (checkOrderZt) {
				return WSUtil.buildResponse(ResultCode.ORDER_ONWAY,
						"存在在途单，不能受理密码修改业务！");
			}

			if (WSDomain.ACCNBR_TYPE_SET.contains(accNbrType)) {
				start = System.currentTimeMillis();
				prod = intfSMO.getProdByAccessNumber(accNbr);
				if (isPrintLog) {
					System.out
							.println("changePassword.intfSMO.getProdByAccessNumber(根据接入号码取得产品) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}
				if (prod != null) {
					start = System.currentTimeMillis();
					party = offerFacade.getPartyByProdId(prod.getProdId());
					if (isPrintLog) {
						System.out
								.println("changePassword.offerFacade.getPartyByProdId(查询客户信息) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
			} else if (WSDomain.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {
				start = System.currentTimeMillis();
				// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.getPartyByOtherCard(根据其他证件号查询客户信息) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

			} else {
				return WSUtil.buildResponse(ResultCode.INVALID_ACCNBR_TYPE);
			}
			// 产品密码
			if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)
					|| WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
				if (prod == null) {
					return WSUtil.buildResponse(ResultCode.PROD_NOT_EXIST);
				}
				if (WSDomain.PasswordType.PROD_QUERY.equals(passwordType)) {
					start = System.currentTimeMillis();
					checkResult = intfSMO.isValidProdQryPwd(prod.getProdId(),
							old_password);
					if (isPrintLog) {
						System.out
								.println("changePassword.intfSMO.isValidProdQryPwd(校验用户查询密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
				if (WSDomain.PasswordType.PROD_BUSINESS.equals(passwordType)) {
					start = System.currentTimeMillis();
					checkResult = intfSMO.isValidProdBizPwd(prod.getProdId(),
							old_password);
					if (isPrintLog) {
						System.out
								.println("changePassword.intfSMO.isValidProdBizPwd(校验用户业务密码) 执行时间:"
										+ (System.currentTimeMillis() - start));
					}

				}
				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}
					if(WSDomain.PasswordType.PROD_QUERY.equals(passwordType)){
						// 密码有效 修改密码
						// 构建报文
						StringBuilder sb = new StringBuilder();
						sb.append("<request>");
						sb.append("<order>");
						sb.append(String.format("<orderTypeId>%s</orderTypeId>",
								WSDomain.OrderTypeId.CHANGE_PASSWORD)); // 修改产品密码
						if (systemId != null
								&& !"".equals(systemId)
								&& ("6090010023".equals(systemId) || "6090010060"
										.equals(systemId))) {
							sb.append(String.format("<systemId>%s</systemId>",
									systemId));
						}
						sb.append(String.format("<prodSpecId>%s</prodSpecId>", prod
								.getProdSpecId()));
						sb.append(String.format("<prodId>%s</prodId>", prod
								.getProdId()));
						sb.append(String.format("<feeType>%s</feeType>", 1));
						sb.append(String.format("<anId>%s</anId>", "accNbr"));
						sb.append(String.format("<accessNumber>%s</accessNumber>",
								accNbr));
						sb.append(String.format("<acctCd>%s</acctCd>", party
								.getAcctCd()));
						sb.append(String.format("<partyId>%s</partyId>", party
								.getPartyId()));
						sb.append(String.format("<orderFlag>%s</orderFlag>", 2));
						sb.append(String.format(
								"<password><oldPassword>%s</oldPassword>",
								old_password));
						sb.append(String.format("<newPassword>%s</newPassword>",
								new_password));
						sb.append(String.format(
								"<prodPwTypeCd>%s</prodPwTypeCd></password>",
								passwordType));
						sb.append("</order>");
						sb.append(String.format("<areaId>%s</areaId>", areaId));
						sb.append(String.format("<channelId>%s</channelId>",
								channelId));
						sb.append(String.format("<staffCode>%s</staffCode>",
								staffCode));
						sb.append("</request>");
						String response = orderService.businessCommon(sb
								.toString());
						return response;
					}else{//业务密码送uam处理
						//组装送uam侧报文
						getSendUamParamStr(accNbr, getNbrTypeByAccNbr(accNbr), new_password,appendStr(request,"1","2"));
						//调用uam接口
						
						//todo 
						//这种返回结果还需要观察，是否对每个外围平台返回解析都没问题
						Map<String, Object> root = new HashMap<String, Object>();
						root.put("resultCode", ResultCode.SUCCESS);
						root.put("resultMsg", ResultCode.SUCCESS.getDesc());
						root.put("password", new_password);

						return mapEngine.transform("resetPassword", root);
					}
					
				}
			}
			// 客户查询密码
			else if (WSDomain.PasswordType.CUSTOMER_QUERY.equals(passwordType)) {
				if (party == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				start = System.currentTimeMillis();
				checkResult = custFacade.isValidQryPwd(party.getPartyId(),
						old_password);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.isValidQryPwd(校验客户查询密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}

					// 密码有效 修改密码
					// custFacade.changeCustomerPsd(party.getPartyId(),
					// new_password, null);
					StringBuffer sb = new StringBuffer();
					sb.append("<request><areaId>").append(areaId).append(
							"</areaId>");
					sb.append("<channelId>").append(channelId).append(
							"</channelId>");
					sb.append("<staffCode>").append(staffCode).append(
							"</staffCode>");
					sb.append("<custInfo><partyId>").append(party.getPartyId())
							.append("</partyId>");
					sb.append("<custName>").append(party.getPartyName())
							.append("</custName>");
					sb.append("<custType>").append(party.getPartyTypeCd())
							.append("</custType>");
					sb.append("<custQueryPwd>").append(new_password).append(
							"</custQueryPwd>");
					sb.append("</custInfo></request>");
					String reXml = modifyCustom(sb.toString());
					Document reXmlDoc = WSUtil.parseXml(reXml);
					String reCode = WSUtil.getXmlNodeText(reXmlDoc,
							"/response/resultCode");
					if ("0".equals(reCode)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
					}
				}
			}
			// 客户业务密码
			else if (WSDomain.PasswordType.CUSTOMER_BUSINESS
					.equals(passwordType)) {
				if (party == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				start = System.currentTimeMillis();
				checkResult = custFacade.isValidBizPwd(party.getPartyId(),
						old_password);
				if (isPrintLog) {
					System.out
							.println("changePassword.custFacade.isValidBizPwd(校验客户业务密码) 执行时间:"
									+ (System.currentTimeMillis() - start));
				}

				if (checkResult == 0) {
					// 老密码校验失败
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_ERROR);
				}
				if (checkResult == -1) {
					// 老密码 未设置
					return WSUtil
							.buildResponse(ResultCode.CHANGE_PASSWORD_NO_OLD_PSD);
				}
				if (checkResult == 1) {

					// 新旧密码一致，直接返回成功
					if (new_password.equals(old_password)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS,
								ResultCode.SUCCESS.getDesc());
					}
					//送uam处理
					//组装送uam侧报文
					getSendUamParamStr(accNbr, "0000000", new_password,appendStr(request,"2","4"));
					//调用uam接口
					
					return WSUtil.buildResponse(ResultCode.SUCCESS);
					
					// 密码有效 修改密码
					// custFacade.changeCustomerPsd(party.getPartyId(),
					// new_password, null);
					/*StringBuffer sb = new StringBuffer();
					sb.append("<request><areaId>").append(areaId).append(
							"</areaId>");
					sb.append("<channelId>").append(channelId).append(
							"</channelId>");
					sb.append("<staffCode>").append(staffCode).append(
							"</staffCode>");
					sb.append("<custInfo><partyId>").append(party.getPartyId())
							.append("</partyId>");
					sb.append("<custName>").append(party.getPartyName())
							.append("</custName>");
					sb.append("<custType>").append(party.getPartyTypeCd())
							.append("</custType>");
					sb.append("<custBusinessPwd>").append(new_password).append(
							"</custBusinessPwd>");
					sb.append("</custInfo></request>");
					String reXml = modifyCustom(sb.toString());
					Document reXmlDoc = WSUtil.parseXml(reXml);
					String reCode = WSUtil.getXmlNodeText(reXmlDoc,
							"/response/resultCode");
					if ("0".equals(reCode)) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
					}*/
				}
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		} catch (Exception e) {
			WSUtil.logError("changePassword", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 拼装送uam报文
	 * @param accountId
	 * @param nbrType
	 * @param new_password
	 * @param request
	 */
	private String getSendUamParamStr(String accountId,String nbrType, String new_password,String request) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<CAPRoot>");
		sb.append(getSessionHeader());
		sb.append("<SessionBody>");
		sb.append("<ResetPassword>");
		sb.append("<AccountID>").append(accountId).append(
				"</AccountID>");
		sb.append("<NewPassword>").append(getEncrytStr(new_password,"6090010018","1")).append(
				"</NewPassword>");// new_password进行3des加密
		sb.append("<AccountType>").append(nbrType
				)
				.append("</AccountType>");
		String requestStr = getEncrytStr(request,"6090010018","1");
		System.out.println("uam加密报文："+requestStr);
		sb.append("<Remark>").append(requestStr
		)
		.append("</Remark>");
		sb.append("</ResetPassword>");
		sb.append("</SessionBody>");
		sb.append("</CAPRoot>");
		return sb.toString();
	}
	
	/**
	 * 对于6090010018（uam）平台，按照uam指定方式进行加密，但配置表里面必须配置
	 * 一条数据。
	 * @param pwd 明文
	 * @param componentId 平台编码，为空查询所有
	 * @param active 1：加密 2：解密
	 * @return
	 */
	private String getEncrytStr(String pwd,String componentId,String active){
		//对密码进行3des加密解密
		if(StringUtil.isEmpty(pwd))
			return pwd;
		String encrytStr = "";
		Map<String,String> param = new HashMap<String,String>();
		if(componentId != null && !"".equals(componentId))
			param.put("componentId", componentId);
		List<Map<String,Object>> resultMap = intfSMO.qryEncryptStrByParam(param);
		if(resultMap != null && resultMap.size()> 0){
			if("1".equals(resultMap.get(0).get("ISACTIVE"))){//启用
				encrytStr = resultMap.get(0).get("ENCRYTSTR")+"";
				if("6090010018".equals(componentId)){//uam平台加密解密
					if("1".equals(active))//加密
						return DES3N.encryptBy3DesAndBase64(pwd);
					else if("2".equals(active)){//解密
						return DES3N.decryptBy3DesAndBase64(pwd);
					}
				}else{
					if("1".equals(active))//加密
						return DESEncryptUtil.encrypt(pwd, encrytStr);
					else if("2".equals(active)){//解密
						return DESEncryptUtil.decrypt(pwd, encrytStr);
					}
				}
				
			}
		}
		return pwd;
	}

	/**
	 * 送uam接口拼接报文头信息
	 * 
	 * @return
	 */
	private String getSessionHeader() {
		StringBuffer sb = new StringBuffer("");
		sb.append("<SessionHeader>");
		sb.append("<ServiceCode>CAP04009</ServiceCode>");// 联调看这个地方要不要改
		sb.append("<ActionCode>0</ActionCode>");
		sb.append("<SrcSysID>99999</SrcSysID>");
		sb.append("<DstSysID>01</DstSysID>");
		sb.append("<DigitalSign/>");
		sb.append("<Version>").append("CAP04009").append(
				new SimpleDateFormat("yyyyMMdd").format(new Date())).append(
				"</Version>");
		String reqtime = new SimpleDateFormat("yyyyMMddhh24mmss")
				.format(new Date());
		sb.append("<ReqTime>").append(reqtime).append("</ReqTime>");
		sb.append("<TransactionID>").append("99999").append(reqtime).append(
				(int) (Math.random() * 10000)).append("</TransactionID>");
		sb.append("</SessionHeader>");
		return sb.toString();
	}

	/**
	 * 根据接入号码查找映射给uam的号码类型
	 * @param accNbr
	 * @return
	 */
	private String getNbrTypeByAccNbr(String accNbr) {
		return intfSMO.getNbrTypeByAccNbr(accNbr);
	}
	
	/**
	 * 送uam密码修改重置拼接业务动作，uam反向接口调用
	 * @param request
	 * @param action 1：密码修改  2：密码重置
	 * @param pwdType 2：产品业务密码  4：客户业务密码
	 * @return
	 */
	private  String appendStr(String request,String action,String pwdType){
		int indexOf = request.indexOf("<request>");
		String appendEnd = request.substring(indexOf+9);
		String str = "<action>"+action+","+pwdType+"</action>";
		return "<request>"+str+appendEnd;
	}
	
	
	
	/**
	 * uam反向同步密码接口
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/action", caption = "业务动作")})
	public String syncPasswordFromUam(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String str = WSUtil.getXmlNodeText(doc, "/request/action");
			String[] split = str.split(",");
			String action = split[0];
			if("1".equals(action)){//修改密码
				return this.changePassword(request);
			}else if("2".equals(action)){//重置密码
				return this.resetPassword(request);
			}else
				return WSUtil.buildResponse("1", "业务动作格式错误！");
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}
	
	
		private String sendMsg(String msg) throws Exception {
		String uamLocation = "http://172.19.17.183:7103/sync/services/CrmInterfaces?wsdl";
//		String esbWsdlLocation = PropertiesUtils.getPropertiesValue("UAM_WEBSERVICE_URL");
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(uamLocation));
		call.setOperationName(new QName("http://www.mbossuac.com.cn/ua", "resetPassword"));
		call.addParameter("request", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(uamLocation);
		String result = (String) call.invoke(new Object[] { msg });
		return result;
//		Document document = null;
//		document = XMLDom4jUtils.fromXML(result, null);
//		Element root = document.getRootElement();
//		Element element = root.element("TcpCont");
//		Element response = element.element("Response");
//		int resutCode = Integer.parseInt(response.elementText("RspType"));
//		String errCode = response.elementText("RspCode");
//		String desc = response.elementText("RspDesc");
//		System.out.println(desc + "==========================");
//		System.out.println(errCode + "+++++++++++++++++++++++++++++");
//		//	xml = msg;
//		if (resutCode != 0) {
//			throw new Exception("[" + errCode + "]" + desc);
//		}

	}
	
		
		
		
		/**
		 * 查询员工维度范围接口
		 * <request>
		 	<staffCode>BJ1001</staffCode>
		 	<operManageCd>SML0104</operManageCd>
		 	<dimManageCd>SMD1002</dimManageCd>
		 	<channelId>100226398</channelId>
		 </request>
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String findDimensionRange(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId_xg");
				String operManageCd = WSUtil.getXmlNodeText(doc, "/request/operManageCd");
				String dimManageCd = WSUtil.getXmlNodeText(doc, "/request/dimManageCd");
				String findDimensionRange = smService.findDimensionRange(Long.parseLong(staffId), operManageCd, dimManageCd);
				return findDimensionRange;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		
		/**
		 * 根据员工查询员工名称、联系电话等相关信息接口
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 </request>
		 * @return
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String findStaffInfoByPartyId(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId_xg");
				String findStaffInfoByPartyId = smService.findStaffInfoByPartyId(Long.parseLong(staffId));
				return findStaffInfoByPartyId;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		
		
		
		/**
		 * 查询员工维度范围接口
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 </request>
		 * @return
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String findOperationSpecByStaffId(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(doc, "/request/staffId_xg");
				String findStaffInfoByPartyId = smService.findOperationSpecByStaffId(Long.parseLong(staffId));
				if (findStaffInfoByPartyId != null && findStaffInfoByPartyId.contains("&lt;"))
					findStaffInfoByPartyId = findStaffInfoByPartyId.replace("&lt;", "<");
				return findStaffInfoByPartyId;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		
		/**
		 * 根据操作权限编码查询具有该权限的所有员工的员工信息
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 	<operManageCd>SMD1002</operManageCd>
		 </request>
		 * @return
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String findStaffInfoByOperManageCd(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String staffId = WSUtil.getXmlNodeText(doc, "/request/operManageCd");
				/*String findStaffInfoByPartyId =smService.findStaffInfoByOperManageCd(staffId);
				if (findStaffInfoByPartyId != null && findStaffInfoByPartyId.contains("&lt;"))
					findStaffInfoByPartyId = findStaffInfoByPartyId.replace("&lt;", "<");*/
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		
		/**
		 * 根据员工工号查询员工是否存在
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 	<staffNumber>SMD1002</staffNumber>
		 </request>
		 * @return  boolean型 true表示存在该员工,false表示不存在该员工
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String existStaffInfoByStaffNumber(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				/*String staffId = WSUtil.getXmlNodeText(doc, "/request/staffNumber");//输入的员工工号
				boolean fag =smService.existStaffInfoByStaffNumber(staffId);*/
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		/**
		 * 查询员工信息
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 	<staffNumber>SMD1002</staffNumber>
		 </request>
		 * @return  String型 
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/type", caption = "员工类型"),
				@Node(xpath = "//request/qryValue", caption = "渠道的业务编码")})
		public String findStaff(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
				String type = WSUtil.getXmlNodeText(doc, "/request/type");//输入的员工工号
				String qryValue = WSUtil.getXmlNodeText(doc, "/request/qryValue");//输入的员工工号
				String result =smService.findStaff(type,qryValue);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		/**
		 * 根据员工工号查询员工信息
		 * @param request
		 <request>
		 	<staffCode>BJ1001</staffCode>
		 	<channelId>100226398</channelId>
		 	<staffNumber>C02221</staffNumber>
		 </request>
		 * @return xml String 字符串
		 */
		@WebMethod
		@Required(nodes = { 
				@Node(xpath = "//request/staffCode", caption = "员工编码"),
				@Node(xpath = "//request/channelId", caption = "渠道的业务编码")})
		public String findStaffInfoByStaffNumber(@WebParam(name = "request") String request) {
			try {
				Document doc = WSUtil.parseXml(request);
			    String staffNumber = WSUtil.getXmlNodeText(doc, "/request/staffNumber");//输入的员工工号
				String staffInfo = smService.findStaffInfoByStaffNumber(staffNumber);
				return staffInfo;
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse("1", e.getMessage());
			}
		}
		/**
		 * 新增（2016-12-29）新增客户
		 * 
		 * @param request
		 * @return
		 */
		@WebMethod
		@Required(nodes = {
				@Node(xpath = "/request/ifRealname", caption = "是否实名"),
				@Node(xpath = "/request/systemId", caption = "平台编码"),
				@Node(xpath = "/request/custInfo/custName", caption = "客户名称"),
				@Node(xpath = "/request/custInfo/custType", caption = "客户类型"),
				@Node(xpath = "/request/custInfo/cerdAddr", caption = "证件地址"),
				
				@Node(xpath = "/request/custInfo/cerdType", caption = "证件类型"),
				@Node(xpath = "/request/custInfo/cerdValue", caption = "证件号码"),
				@Node(xpath = "/request/custInfo/contactPhone1", caption = "联系电话1"),
				@Node(xpath = "/request/custInfo/custAddr", caption = "收件地址") })
		public String createCustNew(@WebParam(name = "request") String request) {
			try {
				logger.debug("开始创建客户 request={}", request);
				Document doc = WSUtil.parseXml(request);
				String custName = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/custName");
				
				String cerdType = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/cerdType");
				String cerdValue = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/cerdValue");
				
				//是否实名
				String ifRealname = WSUtil.getXmlNodeText(doc,
				"/request/ifRealname");
				//核验等级
				String realNameGrade = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/realNameGrade");
				String systemId = WSUtil.getXmlNodeText(doc,
				"/request/systemId");
				
				String photoInfo = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/photoInfo");
				
				
				String channelId = WSUtil.getXmlNodeText(doc,
				"/request/channelId");
				
				String staffId = WSUtil.getXmlNodeText(doc,
				"/request/staffCode");
				
				//国政通的返回结果
				String idCheckResult = "";
				String result ="";
				
				//政企客户新建资料
				if(systemId.equals("6090010028")&& cerdType.equals("3")){
					result = creatUser(request);
					Document document = WSUtil.parseXml(result);
					String resulrCode = WSUtil.getXmlNodeText(document, "response/resultCode");
					String resulmsg = WSUtil.getXmlNodeText(document, "response/resultMsg");
					if(!resulrCode.equals("0")){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, resulmsg);
					}
					
					
					//查询olId jsOlid
					String jsOlid = WSUtil.getXmlNodeText(document, "response/jsOlid");
					JSONObject jbj = new JSONObject();
					JSONObject jbj1 = new JSONObject();
					jbj = JSONObject.fromObject(jsOlid);
					jbj1 = jbj.getJSONObject("orderList");
					String olId = jbj1.getJSONObject("orderListInfo").get("olId").toString();
					String partyId = jbj1.getJSONObject("orderListInfo").get("partyId").toString();
					
					Map<String, Object> autonymFlag = intfSMO.getFingerInfo(olId);
					SimpleDateFormat versonpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String verson = versonpl.format(new Date());
					//将结果插入finger_photo_cut表
					autonymFlag.put("PARTY_ID", partyId);
					autonymFlag.put("PARTY_PROFILE_CATG_CD", "211738");
					autonymFlag.put("CREAT_DT", verson);
					autonymFlag.put("PROFILE_VALUE", "0");
					intfSMO.insertPartyFlagInfo(autonymFlag);
					return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
							partyId);
				}
				Map<String,Object> requestOnetoOne = new HashMap<String,Object>();
				requestOnetoOne.put("custName", custName);
				requestOnetoOne.put("cerdValue", cerdValue);
				requestOnetoOne.put("InphotoInfo", photoInfo);
				requestOnetoOne.put("channelId", channelId);
				requestOnetoOne.put("staffId", staffId);
				
				String response = spServiceSMO.Onetoone(requestOnetoOne);
				Document documentOneToOne = WSUtil.parseXml(response);
				
				String OneToOneCode = WSUtil.getXmlNodeText(documentOneToOne, "response/resultCode");
				String OneToOnemsg = WSUtil.getXmlNodeText(documentOneToOne, "response/resultMsg");
				if(!OneToOneCode.equals("0")){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, OneToOnemsg);
				}
				String verifySimilarity = WSUtil.getXmlNodeText(documentOneToOne, "response/verifySimilarity");
				double  doverifySimilarity = 0.0;
				double  flverifySimilarity = 0.0;
				flverifySimilarity = 60;
				doverifySimilarity =Double.valueOf(verifySimilarity.toString());
				if(doverifySimilarity - flverifySimilarity < 0){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "表示认为不是同一个人");
				}
				
				if(ifRealname.equals("1") == false || realNameGrade.isEmpty()){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "未实名不允许开户");
				}
				else if(systemId.equals("6090010028")&& realNameGrade.equals("5")){
					result = creatUser(request);
					Document document = WSUtil.parseXml(result);
					String resulrCode = WSUtil.getXmlNodeText(document, "response/resultCode");
					String resulmsg = WSUtil.getXmlNodeText(document, "response/resultMsg");
					if(!resulrCode.equals("0")){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, resulmsg);
					}
					
					
					//查询olId jsOlid
					String jsOlid = WSUtil.getXmlNodeText(document, "response/jsOlid");
					JSONObject jbj = new JSONObject();
					JSONObject jbj1 = new JSONObject();
					jbj = JSONObject.fromObject(jsOlid);
					jbj1 = jbj.getJSONObject("orderList");
					String olId = jbj1.getJSONObject("orderListInfo").get("olId").toString();
					String partyId = jbj1.getJSONObject("orderListInfo").get("partyId").toString();
					
					Map<String, Object> autonymFlag = intfSMO.getFingerInfo(olId);
					SimpleDateFormat versonpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String verson = versonpl.format(new Date());
					//将结果插入finger_photo_cut表
					autonymFlag.put("PARTY_ID", partyId);
					autonymFlag.put("PARTY_PROFILE_CATG_CD", "211738");
					autonymFlag.put("CREAT_DT", verson);
					autonymFlag.put("PROFILE_VALUE", "0");
					intfSMO.insertPartyFlagInfo(autonymFlag);
					
					//根据 olId查询bo_id、partyid、staff_id、create_dt，bo_action_type、prod_id
					Map<String, Object> fingerInfo = intfSMO.getFingerInfo(olId);
					
					SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = da.format(new Date());
					//将结果插入finger_photo_cut表
					fingerInfo.put("CREAT_DT", date);
					fingerInfo.put("PHOTO_CUT", photoInfo);
					intfSMO.insertFingerInfo(fingerInfo);
					
					return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
							partyId);
				}
				else if(systemId.equals("6090010020")&& realNameGrade.equals("1")){
					//调用国政通校验身份证信息
					idCheckResult = spServiceSMO.getCheckResult(cerdType,custName, cerdValue);
					Document document = WSUtil.parseXml(idCheckResult);
					String GResultCode = WSUtil.getXmlNodeText(document, "CheckResult/ResultCode");
					if(GResultCode.equals("00")){
						result = creatUser(request);
					}else{
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "国政通身份验证不通过");
					}
					return result;
				}
				//pad用户增加照片
				else if(systemId.equals("6090010023")&& (realNameGrade.equals("5")||realNameGrade.equals("3"))){
					
					result = creatUser(request);
					Document document = WSUtil.parseXml(result);
					String resulrCode = WSUtil.getXmlNodeText(document, "response/resultCode");
					String resulmsg = WSUtil.getXmlNodeText(document, "response/resultMsg");
					if(!resulrCode.equals("0")){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, resulmsg);
					}
					
					
					//查询olId jsOlid
					String jsOlid = WSUtil.getXmlNodeText(document, "response/jsOlid");
					JSONObject jbj = new JSONObject();
					JSONObject jbj1 = new JSONObject();
					jbj = JSONObject.fromObject(jsOlid);
					jbj1 = jbj.getJSONObject("orderList");
					String olId = jbj1.getJSONObject("orderListInfo").get("olId").toString();
					String partyId = jbj1.getJSONObject("orderListInfo").get("partyId").toString();
					
					//接口增加修改补充客户国政通标识的逻辑
					Map<String, Object> autonymFlag = intfSMO.getFingerInfo(olId);
					SimpleDateFormat versonpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String verson = versonpl.format(new Date());
					//将结果插入finger_photo_cut表
					autonymFlag.put("PARTY_ID", partyId);
					autonymFlag.put("PARTY_PROFILE_CATG_CD", "211738");
					
					autonymFlag.put("PROFILE_VALUE", "0");
					autonymFlag.put("CREAT_DT", verson);
					intfSMO.insertPartyFlagInfo(autonymFlag);
					//根据 olId查询bo_id、partyid、staff_id、create_dt，bo_action_type、prod_id
					Map<String, Object> fingerInfo = intfSMO.getFingerInfo(olId);
					
					SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = da.format(new Date());
					//将结果插入finger_photo_cut表
					fingerInfo.put("CREAT_DT", date);
					fingerInfo.put("PHOTO_CUT", photoInfo);
					intfSMO.insertFingerInfo(fingerInfo);
					
					return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
							partyId);
				}
				else if(systemId.equals("6090010087")&& (realNameGrade.equals("7"))){
					
					result = creatUser(request);
					Document document = WSUtil.parseXml(result);
					String resulrCode = WSUtil.getXmlNodeText(document, "response/resultCode");
					String resulmsg = WSUtil.getXmlNodeText(document, "response/resultMsg");
					if(!resulrCode.equals("0")){
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, resulmsg);
					}
					
					
					//查询olId jsOlid
					String jsOlid = WSUtil.getXmlNodeText(document, "response/jsOlid");
					JSONObject jbj = new JSONObject();
					JSONObject jbj1 = new JSONObject();
					jbj = JSONObject.fromObject(jsOlid);
					jbj1 = jbj.getJSONObject("orderList");
					String olId = jbj1.getJSONObject("orderListInfo").get("olId").toString();
					String partyId = jbj1.getJSONObject("orderListInfo").get("partyId").toString();
					
					//接口增加修改补充客户国政通标识的逻辑
					Map<String, Object> autonymFlag = intfSMO.getFingerInfo(olId);
					SimpleDateFormat versonpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String verson = versonpl.format(new Date());
					//将结果插入finger_photo_cut表
					autonymFlag.put("PARTY_ID", partyId);
					autonymFlag.put("PARTY_PROFILE_CATG_CD", "211738");
					
					autonymFlag.put("PROFILE_VALUE", "0");
					autonymFlag.put("CREAT_DT", verson);
					intfSMO.insertPartyFlagInfo(autonymFlag);
					//根据 olId查询bo_id、partyid、staff_id、create_dt，bo_action_type、prod_id
					Map<String, Object> fingerInfo = intfSMO.getFingerInfo(olId);
					
					SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = da.format(new Date());
					//将结果插入finger_photo_cut表
					fingerInfo.put("CREAT_DT", date);
					fingerInfo.put("PHOTO_CUT", photoInfo);
					intfSMO.insertFingerInfo(fingerInfo);
					
					return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
							partyId);
				}
				else if(systemId.equals("6090010030")&& realNameGrade.equals("1")){
					//调用国政通校验身份证信息
					idCheckResult = spServiceSMO.getCheckResult(cerdType,custName, cerdValue);
					Document document = WSUtil.parseXml(idCheckResult);
					String GResultCode = WSUtil.getXmlNodeText(document, "CheckResult/ResultCode");
					if(GResultCode.equals("00")){
						result = creatUser(request);
					}else{
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, "国政通身份验证不通过");
					}
					result = creatUser(request);
					return result;
				}
				else if(systemId.equals("6090010040")&& realNameGrade.equals("1")){
					result = creatUser(request);
					return result;
				}else{
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "核验等级与平台对应不一致");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				WSUtil.logError("createCust", request, e);
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
			}
		}

		private String creatUser(String request) {
			
			try{
				logger.debug("开始创建客户 request={}", request);
				Document doc = WSUtil.parseXml(request);
				String custType = WSUtil.getXmlNodeText(doc,
						"/request/custInfo/custType");
				String partyGrade = WSUtil.getXmlNodeText(doc,
						"/request/custInfo/partyGrade");
				//同一证件号不能创建多个用户校验
				String cerdType = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/cerdType");
				String cerdValue = WSUtil.getXmlNodeText(doc,
				"/request/custInfo/cerdValue");
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("cerdType", cerdType);
				m.put("cerdValue", cerdValue);
				if(intfSMO.isManyPartyByIDNum(m))
					return WSUtil.buildResponse(ResultCode.MANYPARTY_BY_ID,
							"一个证件号码不能创建多个客户:" + cerdValue);
				
				// 判断客户等级和客户类型是否匹配
				if (StringUtils.isBlank(partyGrade)
						&& StringUtils.isNotBlank(WSDomain.PARTY_LEVEL
								.get(custType))) {
					// 设置客户等级的缺省值
					org.dom4j.Node node = doc
							.selectSingleNode("/request/custInfo/partyGrade");
					if (node == null) {
						Element custInfo = (Element) doc
								.selectSingleNode("/request/custInfo");
						custInfo.addElement("partyGrade").setText(
								WSDomain.PARTY_LEVEL.get(custType));
					} else {
						node.setText(WSDomain.PARTY_LEVEL.get(custType));
					}
				} else if (StringUtils.isNotBlank(WSDomain.PARTY_LEVEL
						.get(custType))) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("custType", WSDomain.PARTY_LEVEL.get(custType));
					params.put("partyGrade", partyGrade);
					Integer cnt = intfSMO
							.ifRightPartyGradeByCustTypeAndPartyGrade(params);
					if (cnt == 0) {
						return WSUtil.buildResponse(
								ResultCode.REQUEST_PARAME_IS_ERROR, "客户等级跟客户类型不匹配:"
										+ partyGrade);
					}
				} else {
					return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR,
							"客户类型不正确:" + custType);
				}
				JSONObject custJsonString = createCustOrderListFactory
						.generateOrderList(doc);
				if (custJsonString == null) {
					return WSUtil.buildResponse(ResultCode.PARTY_NOT_EXIST);
				}
				// 替换ol_id
				JSONObject idJSON = soSaveSMO.saveOrderList(custJsonString);
				Long orderListId = idJSON.getJSONObject("ORDER_LIST-OL_ID")
						.getLong("-1");
				custJsonString.getJSONObject("orderList").getJSONObject(
						"orderListInfo").element("olId", orderListId);
				Long result = soCommitSMO.saveCustInfo(custJsonString);
				if (result == null) {
					return WSUtil.buildResponse(ResultCode.CUSTOMER_NOT_EXIST);
				}
				return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "partyId",
						result.toString(),"jsOlid",custJsonString.toString());
			} catch (Exception e) {
				WSUtil.logError("createCust", request, e);
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
			}
		}
		/**
		 * 一比一照片接口
		 */
		@WebMethod
		public String Onetoone(@WebParam(name = "request") String request) {
			try {
				
				Document document = WSUtil.parseXml(request);
				String custName = WSUtil.getXmlNodeText(document, "request/custName");
				String cerdValue = WSUtil.getXmlNodeText(document, "request/cerdValue");
				String InphotoInfo = WSUtil.getXmlNodeText(document, "request/InphotoInfo");
				
	          //调用国政通校验身份证信息
	            String idCheckResult = spServiceSMO.getCheckResult("1002",custName, cerdValue);
				Document documentInfo = WSUtil.parseXml(idCheckResult);
				String resultCode = WSUtil.getXmlNodeText(documentInfo, "/CheckResult/ResultCode");
				if(!resultCode.equals("00")){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询国政通失败"+resultCode);
				}
				String baseImg = WSUtil.getXmlNodeText(documentInfo, "CheckResult/Photo");
				//国政通的照片
	            // #######################################################################################
	            String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	            // #######################################################################################
	            String accessId = new GetRebateAddress().getAddress("accessId");
	            String accessPwd = new GetRebateAddress().getAddress("accessPwd");
//	            String accessId = "bjdx0824";
//	            String accessPwd = "FVGBNHMJKL";
	            String trueNegativeRate = new GetRebateAddress().getAddress("trueNegativeRate");
	            
	            //日志记录
	            String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();

	            JSONObject jo = new JSONObject();
	            jo.put("accessId",  accessId);
	            jo.put("accessKey", Md5Check.MD5(accessId + accessPwd + strTimestamp));
	            jo.put("timeStamp", strTimestamp);
	            jo.put("faceImg", InphotoInfo);
	            jo.put("baseImg", baseImg);
	            jo.put("baseImgType", 2);
	            jo.put("faceImgType", 3);
	            jo.put("trueNegativeRate", trueNegativeRate);
	            jo.put("sign", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	            
	            intfSMO.saveRequestInfo(logId2, "CrmWebService", "Onetoone", "入参："+jo.toString(), requestTime2);
	            // #################################################################
	             //String url = "http://124.192.161.110:8080/face/p2/decrypt/check";
	             String url = new GetRebateAddress().getAddress("GZTurl_oneToOne");
	            // #################################################################
	            logger.info(String.format(">>>> HttpPost URL: %s", url));
	            // #######################################################################################
	            long lSP = System.currentTimeMillis();
	            HttpClient hc = new org.apache.http.impl.client.DefaultHttpClient();
	            HttpPost hp = new HttpPost(url);
	            System.out.println(url);
	            hp.setEntity(new StringEntity(jo.toString()));
	            HttpResponse hr = hc.execute(hp);
	            long lEP = System.currentTimeMillis();
	            int iCode = hr.getStatusLine().getStatusCode();
	            String strPhrase = hr.getStatusLine().getReasonPhrase();
	            logger.info(String.format(">>>> HttpResponse status[%s:%s], cost: %s ms.", iCode, strPhrase, (lEP - lSP)));
	            // #######################################################################################
	            if (iCode == 200) {
	                String strSrc = EntityUtils.toString(hr.getEntity());
	                
	                JSONObject obj = JSONObject.fromObject(strSrc);
	                
					System.out.println(obj.toString());
					String resultCodeNum = (String) obj.get("result");
					String message =(String) obj.get("message");
					if(resultCodeNum.equals("0")){
						String transactionId = (String) obj.get("transaction_id");
						String verifyResult = (String) obj.get("verify_result");
						String verifySimilarity = (String) obj.get("verify_similarity");
						StringBuilder sb = new StringBuilder();
						sb.append("<response>");             
						sb.append("<resultCode>").append(resultCodeNum).append("</resultCode>");
						sb.append("<resultMsg>").append(java.net.URLDecoder.decode(message,"UTF-8")).append("</resultMsg>");
						sb.append("<transactionId>").append(transactionId).append("</transactionId>");
						sb.append("<verifyResult>").append(verifyResult).append("</verifyResult>");
						sb.append("<verifySimilarity>").append(verifySimilarity).append("</verifySimilarity>");
						sb.append("</response>");
						request = sb.toString();
					}else{
						StringBuilder sb = new StringBuilder();
						sb.append("<response>");             
						sb.append("<resultCode>").append(resultCodeNum).append("</resultCode>");
						sb.append("<resultMsg>").append(java.net.URLDecoder.decode(message,"UTF-8")).append("</resultMsg>");
						sb.append("</response>");
						request = sb.toString();
					}
	            }
	            if (hc != null) {
	                    hc.getConnectionManager().shutdown();
	            }
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
			return request;
		}
		/**
		 * 一比三照片接口
		 */
		@WebMethod
		public String OnetoThree(@WebParam(name = "request") String request) {
			try {
				
				Document document = WSUtil.parseXml(request);
				String custName = WSUtil.getXmlNodeText(document, "request/custName");
				String cerdValue = WSUtil.getXmlNodeText(document, "request/cerdValue");
				String dataPackage = WSUtil.getXmlNodeText(document, "request/dataPackage");
				
	            //调用国政通校验身份证信息
	            String idCheckResult = spServiceSMO.getCheckResult("1002",custName, cerdValue);
				Document documentInfo = WSUtil.parseXml(idCheckResult);
				String resultCode = WSUtil.getXmlNodeText(documentInfo, "/CheckResult/ResultCode");
				if(!resultCode.equals("00")){
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "查询国政通失败"+resultCode);
				}
				String baseImg = WSUtil.getXmlNodeText(documentInfo, "CheckResult/Photo");
				//国政通的照片
	            // #######################################################################################
	            String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	            // #######################################################################################
	            String accessId = new GetRebateAddress().getAddress("accessId");
	            String accessPwd = new GetRebateAddress().getAddress("accessPwd");
//	            String accessId = "bjdx0824";
//	            String accessPwd = "FVGBNHMJKL";
	            String trueNegativeRate = new GetRebateAddress().getAddress("trueNegativeRate");
	            
	            //日志记录
	            String logId2 = intfSMO.getIntfCommonSeq();
				Date requestTime2 = new Date();

	            JSONObject jo = new JSONObject();
	            jo.put("accessId",  accessId);
	            jo.put("accessKey", Md5Check.MD5(accessId + accessPwd + strTimestamp));
	            jo.put("timeStamp", strTimestamp);
	            jo.put("dataPackage", dataPackage);
	            jo.put("baseImg", baseImg);
	            jo.put("baseImgType", 2);
	            jo.put("trueNegativeRate", trueNegativeRate);
	            jo.put("sign", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	            
	            intfSMO.saveRequestInfo(logId2, "CrmWebService", "OnetoThree", "入参："+jo.toString(), requestTime2);
	            // #################################################################
	             //String url = "http://124.192.161.110:8080/face/p2/decrypt/check";
	             String url = new GetRebateAddress().getAddress("GZTurl_oneToThree");
	            // #################################################################
	            logger.info(String.format(">>>> HttpPost URL: %s", url));
	            // #######################################################################################
	            long lSP = System.currentTimeMillis();
	            HttpClient hc = new org.apache.http.impl.client.DefaultHttpClient();
	            HttpPost hp = new HttpPost(url);
	            System.out.println(url);
	            hp.setEntity(new StringEntity(jo.toString()));
	            HttpResponse hr = hc.execute(hp);
	            long lEP = System.currentTimeMillis();
	            int iCode = hr.getStatusLine().getStatusCode();
	            String strPhrase = hr.getStatusLine().getReasonPhrase();
	            logger.info(String.format(">>>> HttpResponse status[%s:%s], cost: %s ms.", iCode, strPhrase, (lEP - lSP)));
	            // #######################################################################################
	            if (iCode == 200) {
	                String strSrc = EntityUtils.toString(hr.getEntity());
	                
	                //日志
	                intfSMO.saveResponseInfo(logId2, "CrmWebService", "OnetoThree", "入参："+jo.toString(), requestTime2, strSrc, new Date(), "1","0");

	                JSONObject obj = JSONObject.fromObject(strSrc);
	                
					System.out.println(obj.toString());
					String resultCodeNum = (String) obj.get("result");
					String message =(String) obj.get("message");
					if(resultCodeNum.equals("0")){
						String transactionId = (String) obj.get("transaction_id");
						String verifyResult = (String) obj.get("verify_result");
						String verifySimilarity = (String) obj.get("verify_similarity");
						StringBuilder sb = new StringBuilder();
						sb.append("<response>");             
						sb.append("<resultCode>").append(resultCodeNum).append("</resultCode>");
						sb.append("<resultMsg>").append(java.net.URLDecoder.decode(message,"UTF-8")).append("</resultMsg>");
						sb.append("<transactionId>").append(transactionId).append("</transactionId>");
						sb.append("<verifyResult>").append(verifyResult).append("</verifyResult>");
						sb.append("<verifySimilarity>").append(verifySimilarity).append("</verifySimilarity>");
						sb.append("</response>");
						request = sb.toString();
					}else{
						StringBuilder sb = new StringBuilder();
						sb.append("<response>");             
						sb.append("<resultCode>").append(resultCodeNum).append("</resultCode>");
						sb.append("<resultMsg>").append(java.net.URLDecoder.decode(message,"UTF-8")).append("</resultMsg>");
						sb.append("</response>");
						request = sb.toString();
					}
	            }
	            if (hc != null) {
	                    hc.getConnectionManager().shutdown();
	            }
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
			return request;
		}

	/**
     * 2.2.3资源可用性校验接口
     * 包括 2.2.3.1串码可用性校验——营销资源 and 2.2.3.2PRODID可用性校验——CRM
     * @param request
     * @return
     * @throws RemoteException
     */
	public String checkInstIMEIService(@WebParam(name = "request") String request) throws RemoteException{
    	try {
    		StringBuffer sb = new StringBuffer();
    		boolean flag = true;
    		String msg = "";

			Document document = WSUtil.parseXml(request);
			String imei1 = WSUtil.getXmlNodeText(document, "request/IMEI1");
			String emei2 = WSUtil.getXmlNodeText(document, "request/EMEI2");
			String meid = WSUtil.getXmlNodeText(document, "request/MEID");
			String prodId = WSUtil.getXmlNodeText(document, "request/PRODID");
			
			//1. 验证传入参数
			if(imei1.equals("") && imei1==null&&emei2.equals("")&&emei2==null&&meid.equals("")&&meid==null){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "IMEI1,EMEI2,MEID 有空值!");
			}
			
			//2. 准备营销资源参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imei1", imei1);
			map.put("emei2",  emei2);
			map.put("meid", meid);
			
			//3. 营销资源 串口验证
			String result = spServiceSMO.checkInstIMEILocal(map);
			Map resultMap = JSONObject.fromObject(result);
			
			//4. 验证 营销资源返回结果内容
			if(result != null && resultMap != null){
				//返回状态验证
				String resultState = String.valueOf(resultMap.get("state"));
				if(resultState == null || resultState.equals("1")){
					flag = false;
					msg += resultMap.get("msg");				
				}
			}
			
			HashMap crmResultMap = null;
			if(flag){
				//5. 组织 crm 参数 prodId  materialId
				String materialId = String.valueOf(resultMap.get("materialId"));
				Long prodIdd = Long.valueOf(prodId);
				
				//6. crm 串口验证
				String crmResult = csbService.queryImeiStatus(prodIdd, materialId);
				crmResultMap = (HashMap) JsonUtil.getMap(crmResult);
				
				//7. 验证crm 返回结果
				if(crmResult != null){
					String resultState = (String)crmResultMap.get("code");
					if(resultState == null || resultState.equals("1")){
						flag = false;
						msg += crmResultMap.get("msg");
					}
				}
			}
			
			//8. 执行判断 返回
			if(flag){
				sb.append("<response>");
				sb.append("<state>").append("0").append("</state>");
				sb.append("<msg>").append("").append("</msg>");
				sb.append("<offerSpecs>").append(crmResultMap.get("offerSpecs")).append("</offerSpecs>");
				sb.append("</response>");
			}else{
				sb.append("<response>");
				sb.append("<state>").append("1").append("</state>");
				sb.append("<msg>").append(msg).append("</msg>");
				sb.append("</response>");
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
    }
	
	/**
     * 2.2.4串码状态更新接口
     * 包括 2.2.4.1串码状态更新——营销资源 and 2.2.4.2PROID插入
     * @param request
     * @return
     * @throws RemoteException
     */
	public String updateInstIMEIService(@WebParam(name = "request") String request) throws RemoteException{
		try {
			StringBuffer sb = new StringBuffer();
			boolean flag = true;
			String msg = "";
			
			Document document = WSUtil.parseXml(request);			
			String prodId = WSUtil.getXmlNodeText(document, "request/PRODID");
			String accessNumber = WSUtil.getXmlNodeText(document, "request/accessNumber");
			String olNbr = WSUtil.getXmlNodeText(document, "request/olNbr");
			String staffId = WSUtil.getXmlNodeText(document, "request/staffId");
			String imeiId = WSUtil.getXmlNodeText(document, "request/imeiId");
			
			//1. 验证传入参数
			if(prodId.equals("")||prodId==null||accessNumber.equals("")||accessNumber==null|| olNbr.equals("")||olNbr==null
				|| staffId.equals("")||staffId==null||imeiId.equals("")||imeiId==null){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "prodId,accessNumber,olNbr,staffId,imeiId 有空值!");
			}
	
			//2. 准备营销资源参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mktResInstId", imeiId);
			map.put("staffId",  staffId);			
			
			//3. 营销资源 串口 更新
			String result = spServiceSMO.updateInstIMEILocal(map);
			Map resultMap = JSONObject.fromObject(result);
			
			//4. 验证营销资源返回结果
			if(result != null && resultMap != null){
				String resultState = String.valueOf(resultMap.get("state"));
				if(resultState == null || resultState.equals("1")){
					flag = false;
					msg += resultMap.get("msg");
				}
			}
			
			if(flag){
				//5. 准备crm 参数 PROID phoneNum trolleyId staffId mktResInstId 
				Long prodIdd = Long.valueOf(prodId);
				Long staffIdd = Long.valueOf(staffId);
				
				//6. crm 更新
				String crmResult = csbService.saveImeiStatus(prodIdd, accessNumber, olNbr, staffIdd, imeiId);;

				//7. 验证crm 返回结果
				if(crmResult != null){
					if("失败".equals(crmResult)){
						flag = false;
						msg += crmResult;
					}
				}
			}
			
			//8. 执行判断 返回
			if(flag){
				sb.append("<response>");
				sb.append("<state>").append("0").append("</state>");
				sb.append("<msg>").append("成功").append("</msg>");
				sb.append("</response>");
			}else{
				sb.append("<response>");
				sb.append("<state>").append("1").append("</state>");
				sb.append("<msg>").append("失败:"+msg).append("</msg>");
				sb.append("</response>");
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
	}

}
