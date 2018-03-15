package com.linkage.bss.crm.ws.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.linkage.bss.commons.exception.BmoException;
import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.commons.util.StringUtil;
import com.linkage.bss.crm.commons.Cn2Spell;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdCompItem;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferServItem;
import com.linkage.bss.crm.model.OfferSpec;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.offerspec.smo.IOfferSpecSMO;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.common.bmo.ISoCommonBMO;
import com.linkage.bss.crm.so.store.smo.ISoStoreSMO;
import com.linkage.bss.crm.ws.common.CrmServiceErrorCode;
import com.linkage.bss.crm.ws.common.CrmServiceManagerConstants;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.order.model.BoCreateParam;
import com.linkage.bss.crm.ws.order.model.BoCreateParamGrp;
import com.linkage.bss.crm.ws.service.CustomerService;
import com.linkage.bss.crm.ws.util.WSUtil;

public class OrderListFactoryImpl implements OrderListFactory {

	private static Log logger = Log.getLog(OrderListFactoryImpl.class);

	private IntfSMO intfSMO;

	private IOfferSpecSMO offerSpecSMO;

	private IOfferSMO offerSMO;

	private RscServiceSMO rscServiceSMO;

	private CustFacade custFacade;

	private ISoStoreSMO soStoreSMO;

	private ISoCommonBMO soCommonBMO;

	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setSoCommonBMO(ISoCommonBMO soCommonBMO) {
		this.soCommonBMO = soCommonBMO;
	}

	public void setSoStoreSMO(ISoStoreSMO soStoreSMO) {
		this.soStoreSMO = soStoreSMO;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setOfferSpecSMO(IOfferSpecSMO offerSpecSMO) {
		this.offerSpecSMO = offerSpecSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	public Node chargeInfos = null;
	public String interfaceId = null;
	public String staffCode = null;
	public String channelId = null;
	public String prodStatusCd = "";// ���Ӽ��ʽ0ֱ�Ӽ���|1�׻���������|2��ֵ������
	public String ssCd = "";
	public Map<String, String> servInstList = new HashMap<String, String>();

	// �����Ͻ���ֻ�����ߣ����������¡�
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject generateOrderList(Document request) throws Exception {

		Node orderNode = request.selectSingleNode("//request/order");
		staffCode = WSUtil.getXmlNodeText(request, "/request/staffCode");
		String areaCode = WSUtil.getXmlNodeText(request, "//request/areaCode");
		String areaId = WSUtil.getXmlNodeText(request, "//request/areaId");
		channelId = WSUtil.getXmlNodeText(request, "//request/channelId");
		String staffId = WSUtil.getXmlNodeText(request, "//request/staffId");
		Date date = intfSMO.getCurrentTime();
		String dateStr = DateUtil.getFormatTimeString(date, "yyyy-MM-dd HH:mm:ss");
		List<Node> subOrderNodes = orderNode.selectNodes("./subOrder");
		String orderTypeId = WSUtil.getXmlNodeText(request, "//request/order/orderTypeId");
		String systemId = WSUtil.getXmlNodeText(request, "//request/order/systemId");
		String remark = WSUtil.getXmlNodeText(request, "//request/order/remark");
		ssCd = WSUtil.getXmlNodeText(request, "//request/order/prodStatusCd");
		String prepayFlag=WSUtil.getXmlNodeText(request, "//request/order/prepayFlag");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ

		if ("0".equals(ssCd)) {
			prodStatusCd = "1";
		} else if ("1".equals(ssCd)) {
			prodStatusCd = "16";
		} else if ("2".equals(ssCd)) {
			prodStatusCd = "18";
		} else if (StringUtils.isBlank(ssCd)) {
			prodStatusCd = "1";
			ssCd = "0";
		} else {
			throw new Exception("���������ʽ��Ч��--0ֱ�Ӽ���|1�׻���������|2��ֵ������");
		}

		interfaceId = intfSMO.getInterfaceIdBySystemId(systemId);
		chargeInfos = request.selectSingleNode("//request/order/listChargeInfo");
		List<Node> payInfoList = WSUtil.getXmlNodeList(request, "//request/order/payInfoList/payInfo");
		//�ۺ�����̨֧����ˮ 2015-10-29
		JSONArray payNumListJs = new JSONArray();
		JSONObject payNumJs = new JSONObject();
		JSONObject payInfoJs = new JSONObject();
		JSONArray payInfoListJs = new JSONArray();
		for (Node payInfoNode : payInfoList) {
			payInfoJs.elementOpt("amount", WSUtil.getXmlNodeText(payInfoNode, "amount"));
			payInfoJs.elementOpt("payMethod", WSUtil.getXmlNodeText(payInfoNode, "method"));
			payInfoJs.elementOpt("appendInfo", WSUtil.getXmlNodeText(payInfoNode, "appendInfo"));
			payInfoListJs.add(payInfoJs);
		    String outTradeNo = WSUtil.getXmlNodeText(payInfoNode, "outTradeNo");
		    if(outTradeNo!=null && !outTradeNo.equals("")){
		    	payNumJs.element("itemSpecId", "120000001");
		    	payNumJs.element("name","�ۺ�����̨֧����ˮ");
		    	payNumJs.element("value",WSUtil.getXmlNodeText(payInfoNode, "outTradeNo"));
		    	payNumJs.element("state", "ADD");
		    	payNumJs.element("statusCd", "S");
		    	payNumListJs.add(payNumJs);
		    }
		}
		// 2������������Ĺ��ﳵ;7PADԤ�����ﳵ
		String olTypeCd = WSUtil.getXmlNodeText(request, "request/order/olTypeCd");
		if (StringUtils.isBlank(orderTypeId) || !"1".equals(orderTypeId)) {
			throw new Exception("ҵ������olTypeId�����󣬱��ӿ�ֻ֧�ֿ���ҵ��");
		}

		formatOrderDate(orderNode);

		// ������ι��칺�ﳵ��JSON����
		JSONObject rootObj = new JSONObject();
		JSONObject orderListObj = new JSONObject();
		orderListObj.elementOpt("payInfoList", payInfoListJs);
		logger.debug("---------����payInfoList ��ɣ�");
		JSONObject orderListInfoJs = new JSONObject();
		orderListInfoJs.elementOpt("olId", "-1");
		orderListInfoJs.elementOpt("olNbr", "-1");
		orderListInfoJs.elementOpt("remark", remark);// ������ע
		orderListInfoJs.elementOpt("systemId", systemId);
		orderListInfoJs.elementOpt("olTypeCd", olTypeCd);
		orderListInfoJs.elementOpt("staffId", staffId);
		orderListInfoJs.elementOpt("channelId", channelId);
		orderListInfoJs.elementOpt("areaId", areaId);
		orderListInfoJs.elementOpt("areaCode", areaCode);
		orderListInfoJs.elementOpt("soDate", dateStr);
		orderListInfoJs.elementOpt("statusCd", "S");
		orderListInfoJs.elementOpt("statusDt", dateStr);

		JSONArray custOrderListArr = new JSONArray();

		JSONObject custOrderListJs = new JSONObject();
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator(); // ��ҵ������seq��ԭ�Ӷ�����id��λ����-1��ʼ
		BoCreateParamGrp mainBoCreateParamGrp = orderCreateBoCreateParamGrp(orderNode, areaId, dateStr, null,
				boSeqCalculator);
		BoCreateParam mainBoCreateParam = mainBoCreateParamGrp.getSelfBoCreateParam();
		Long partyId = mainBoCreateParam.getPartyId();
		logger.debug("partyId=={}", partyId);
		orderListInfoJs.elementOpt("partyId", partyId);
		orderListInfoJs.elementOpt("acctCd", mainBoCreateParam.getAcctCd());
		if(payNumListJs.size()>0){
			orderListInfoJs.elementOpt("orderListAttrs", payNumListJs);
		}
		orderListObj.elementOpt("orderListInfo", orderListInfoJs);
		logger.debug("---------����orderListInfo ��ɣ�");

		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId);

		JSONArray busiOrderArr = new JSONArray();
		// ��ʱ�����ڲ㶩����ҵ����
		JSONArray innerBusiOrderArr = new JSONArray();
		// ��㶩����ҵ�����󱣴� ��㱣�ֺ��ڲ�ͬ���Ĵ�������
		logger.error("createBusiOrders ǰ====" + df.format(new Date()));
		JSONArray createOrderBusiOrderArr = createBusiOrders(orderNode, mainBoCreateParamGrp, boSeqCalculator,
				busiOrderArr,channelId);
		busiOrderArr.addAll(createOrderBusiOrderArr);
		for (Node subOrderNode : subOrderNodes) {
			BoCreateParamGrp boCreateParamGrp = createBoCreateParamGrp(subOrderNode, areaId, dateStr,
					mainBoCreateParam, boSeqCalculator);
			JSONArray subBusiOrderArr = createBusiOrders(subOrderNode, boCreateParamGrp, boSeqCalculator, busiOrderArr,channelId);
			innerBusiOrderArr.addAll(subBusiOrderArr);
		}
		logger.error("createBusiOrders ��====" + df.format(new Date()));
		// ��ҵ������������
		busiOrderArr.addAll(innerBusiOrderArr);
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		custOrderListArr.add(custOrderListJs);
		orderListObj.elementOpt("custOrderList", custOrderListArr);
		rootObj.elementOpt("prepayFlag", prepayFlag);
		rootObj.element("orderList", orderListObj);
		return rootObj;
	}

	/**
	 * ��������ҵ�����Ĳ����飬ÿ����Ʒ��ҵ����һ���顣������ϲ�Ʒ��Ա�Ĳ�Ʒ�����й�����ϲ�Ʒ����
	 * 
	 * @param order
	 * @param areaCode
	 * @param compBoCreateParam
	 * @return
	 */
	private BoCreateParamGrp createBoCreateParamGrp(Node order, String areaId, String curDate,
			BoCreateParam compBoCreateParam, BoSeqCalculator boSeqCalculator) {
		BoCreateParamGrp boCreateParamGrp = new BoCreateParamGrp();
		BoCreateParam selfBoCreateParam = createBoCreateParam(order, areaId, curDate, boSeqCalculator,
				compBoCreateParam);
		boCreateParamGrp.setSelfBoCreateParam(selfBoCreateParam);
		boCreateParamGrp.setCompBoCreateParam(compBoCreateParam);

		return boCreateParamGrp;
	}

	private BoCreateParamGrp orderCreateBoCreateParamGrp(Node order, String areaId, String curDate,
			BoCreateParam compBoCreateParam, BoSeqCalculator boSeqCalculator) {
		BoCreateParamGrp boCreateParamGrp = new BoCreateParamGrp();
		BoCreateParam selfBoCreateParam = orderCreateBoCreateParam(order, areaId, curDate, boSeqCalculator);
		boCreateParamGrp.setSelfBoCreateParam(selfBoCreateParam);
		boCreateParamGrp.setCompBoCreateParam(compBoCreateParam);

		return boCreateParamGrp;
	}

	/**
	 * ��������ҵ�����Ĳ���
	 * 
	 * @param order
	 * @param areaCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private BoCreateParam createBoCreateParam(Node order, String areaId, String curDate,
			BoSeqCalculator boSeqCalculator, BoCreateParam compBoCreateParam) {
		String prodSpecIdStr = WSUtil.getXmlNodeText(order, "./prodSpecId");
		String LinkMan = WSUtil.getXmlNodeText(order, "./coLinkMan");
		String LinkNbr = WSUtil.getXmlNodeText(order, "./coLinkNbr");
		List<Element> coItemList = order.selectNodes("./prodPropertys/property[@propertyType='co']");// �������ԣ�ҵ�������ԣ�
		String assistMan = WSUtil.getXmlNodeText(order, "./assistMan");
		String roleCd = WSUtil.getXmlNodeText(order, "./roleCd");
		// String partyId = WSUtil.getXmlNodeText(order, "./partyId");
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		String prodId = WSUtil.getXmlNodeText(order, "./prodId");
		if (StringUtils.isBlank(prodId)) {
			prodId = soCommonBMO.generateSeq(Integer.valueOf("11000"), "OFFER_PROD", "1");
		}
		OfferSpec offerSpec = null;
		if (isNewOrder(prodId)) {
			offerSpec = getMainOfferSpecId(pricePlanPakNode);
			if (offerSpec == null) {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "������Ʒ��ϢΪ��");
			}
		}
		// �����µ�prodId�����к�ֵ
		BoCreateParam boCreateParam = new BoCreateParam();
		// ��ϵ��
		if (StringUtils.isNotBlank(LinkMan)) {
			boCreateParam.setLinkMan(LinkMan);
		}
		// ��ϵ�绰
		if (StringUtils.isNotBlank(LinkNbr)) {
			boCreateParam.setLinkNbr(LinkNbr);
		}
		boCreateParam.setProdId(Long.valueOf(prodId));
		// boCreateParam.setPartyId(compBoCreateParam.getPartyId());
		boCreateParam.setProdSpecId(prodSpecIdStr);
		if (isNewOrder(prodId)) {
			boCreateParam.setPsOfferSpecId(offerSpec.getOfferSpecId());
			boCreateParam.setFeeType(offerSpec.getFeeType().toString());
		}
		boCreateParam.setLinkNbr(LinkNbr);
		// ����Э��������Ϣ
		if (StringUtils.isNotBlank(assistMan)) {
			boCreateParam.setAssistMan(assistMan);
		}
		boCreateParam.setCoItemList(coItemList);
		boCreateParam.setPartyId(compBoCreateParam.getPartyId());
		boCreateParam.setInstallDate("");
		boCreateParam.setAreaId(areaId);
		boCreateParam.setCurDate(curDate);
		boCreateParam.setRelaRoleCd(roleCd);
		boCreateParam.setIsCompOffer(false);
		boCreateParam.setRoleState("ADD");
		boCreateParam.setRoleboActionType("998");
		boCreateParam.setNew(true);
		boCreateParam.setAcctId(compBoCreateParam.getAcctId());
		boCreateParam.setAcctCd(compBoCreateParam.getAcctCd());
		boCreateParam.setPaymentAccountId(compBoCreateParam.getPaymentAccountId());
		boCreateParam.setNeedCreateAcct(false);
		return boCreateParam;
	}

	private BoCreateParam orderCreateBoCreateParam(Node order, String areaId, String curDate,
			BoSeqCalculator boSeqCalculator) {
		String prodSpecIdStr = WSUtil.getXmlNodeText(order, "./prodSpecId");
		String LinkMan = WSUtil.getXmlNodeText(order, "./coLinkMan");
		String LinkNbr = WSUtil.getXmlNodeText(order, "./coLinkNbr");
		List<Element> coItemList = order.selectNodes("./prodPropertys/property[@propertyType='co']");// �������ԣ�ҵ�������ԣ�
		String assistMan = WSUtil.getXmlNodeText(order, "./assistMan");
		String roleCd = WSUtil.getXmlNodeText(order, "./roleCd");
		String partyId = WSUtil.getXmlNodeText(order, "./partyId");
		if (StringUtils.isBlank(partyId)) {
			// ���������ͻ�
			StringBuilder request = new StringBuilder();
			request.append("<request>");
			request.append("<custInfo>");
			request.append("<custName>").append("δ�Ǽǿͻ�").append("</custName>");
			request.append("<custType>").append("1").append("</custType>");
			request.append("<cerdAddr>").append("δ�Ǽ�").append("</cerdAddr>");
			request.append("<cerdType>").append("1").append("</cerdType>");
			request.append("<cerdValue>").append("000000").append("</cerdValue>");
			request.append("<contactPhone1>").append("10000").append("</contactPhone1>");
			request.append("<custAddr>").append("δ�Ǽ�").append("</custAddr>");
			request.append("<linkMan>").append("δ�Ǽ�").append("</linkMan>");
			request.append("</custInfo>");
			request.append("<areaId>11000</areaId>");
			request.append("<channelId>" + channelId + "</channelId>");
			request.append("<staffCode>" + staffCode + "</staffCode>");
			request.append("</request>");
			String response = customerService.createCust(request.toString());
			logger.debug("�����ͻ������{}", response);
			Document doc = null;
			try {
				doc = WSUtil.parseXml(response);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			if ("0".equals(doc.selectSingleNode("/response/resultCode").getText())) {
				partyId = doc.selectSingleNode("/response/partyId").getText();
			} else {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "�ͻ�����ʧ��");
			}
		}
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		
		// �����µ�prodId�����к�ֵ
		String prodId = WSUtil.getXmlNodeText(order, "./prodId");
		if (StringUtils.isBlank(prodId)) {
			prodId = soCommonBMO.generateSeq(Integer.valueOf("11000"), "OFFER_PROD", "1");
		}
		OfferSpec offerSpec = null;
		if (isNewOrder(prodId)) {
			 offerSpec = getMainOfferSpecId(pricePlanPakNode);
			if (offerSpec == null) {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "������Ʒ��ϢΪ��");
			}
		}
		
		
		BoCreateParam boCreateParam = new BoCreateParam();
		// ��ϵ��
		if (StringUtils.isNotBlank(LinkMan)) {
			boCreateParam.setLinkMan(LinkMan);
		}
		// ��ϵ�绰
		if (StringUtils.isNotBlank(LinkNbr)) {
			boCreateParam.setLinkNbr(LinkNbr);
		}
		boCreateParam.setProdId(Long.valueOf(prodId));
		boCreateParam.setPartyId(Long.valueOf(partyId));
		boCreateParam.setProdSpecId(prodSpecIdStr);
		if (isNewOrder(prodId)) {
		boCreateParam.setPsOfferSpecId(offerSpec.getOfferSpecId());
		boCreateParam.setFeeType(offerSpec.getFeeType().toString());
		}
		boCreateParam.setLinkNbr(LinkNbr);
		// ����Э��������Ϣ
		if (StringUtils.isNotBlank(assistMan)) {
			boCreateParam.setAssistMan(assistMan);
		}
		boCreateParam.setCoItemList(coItemList);
		// boCreateParam.setPartyId(compBoCreateParam.getPartyId());
		boCreateParam.setInstallDate("");
		boCreateParam.setAreaId(areaId);
		boCreateParam.setCurDate(curDate);
		boCreateParam.setRelaRoleCd(roleCd);
		boCreateParam.setIsCompOffer(true);
		boCreateParam.setNew(true);
		// ���Ƿ���Ҫ�����ʻ�
		if (isNeedAddAcct(order)) {
			boCreateParam.setAcctId(Long.valueOf(soCommonBMO.generateSeq(Integer.valueOf(areaId), "ACCOUNT", "1")));
			boCreateParam.setAcctCd(soCommonBMO.generateSeq(Integer.valueOf(areaId), "ACCOUNT", "2"));
			boCreateParam.setPaymentAccountId(Long.valueOf(boSeqCalculator.getNextPaymentAccountIdString()));
			boCreateParam.setNeedCreateAcct(true);
		} else {
			Account account = intfSMO.findAcctByAcctCd(WSUtil.getXmlNodeText(order, "acctCd"));
			if (account != null) {
				boCreateParam.setAcctId(Long.valueOf(account.getAcctId().toString()));
			} else {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "�������ʻ�����Ϊ"
						+ WSUtil.getXmlNodeText(order, "acctCd") + "���ʻ������飡");
			}
			boCreateParam.setAcctCd(WSUtil.getXmlNodeText(order, "acctCd"));
			boCreateParam.setPaymentAccountId(Long.valueOf(boSeqCalculator.getNextPaymentAccountIdString()));
			boCreateParam.setNeedCreateAcct(false);
		}
		return boCreateParam;
	}

	/**
	 * �����е��ʷѼƻ��в�ѯ����Ʒ���
	 * 
	 * @param pricePlanPakNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private OfferSpec getMainOfferSpecId(Node pricePlanPakNode) {
		if (pricePlanPakNode == null) {
			return null;
		}

		List pricePlanNodes = pricePlanPakNode.selectNodes("./pricePlan");
		if (CollectionUtils.isEmpty(pricePlanNodes)) {
			return null;
		}

		for (Node pricePlanNode : (List<Node>) pricePlanNodes) {
			if (pricePlanNode.selectSingleNode("./pricePlanCd") == null) {
				continue;
			}
			String pricePlanCdStr = pricePlanNode.selectSingleNode("./pricePlanCd").getText();
			if (StringUtils.isNotBlank(pricePlanCdStr)) {
				OfferSpec offerSpec = offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(pricePlanCdStr));
				if (offerSpec != null && offerSpec.getOfferTypeCd().intValue() == CommonDomain.OFFER_TYPE_CD_INT1) {
					return offerSpec;
				}
			}
		}
		return null;
	}

	/**
	 * �ж��Ƿ���Ҫ�����ʻ�
	 * 
	 * @param order
	 * @return
	 */
	private boolean isNeedAddAcct(Node order) {
		boolean addAcctFlag = false;
		String acctCd = WSUtil.getXmlNodeText(order, "./acctCd");
		String bindPayForNbr = WSUtil.getXmlNodeText(order, "./bindPayForNbr");
		// ��װ��ʱ������ʺŽڵ������֧�����붼Ϊ�գ������ʻ��ڵ�Ϊ��-100������Ҫ�����ʺ�
		if (StringUtils.isBlank(acctCd) && StringUtils.isBlank(bindPayForNbr)) {
			addAcctFlag = true;
		} else if (acctCd.equals("-100")) {
			addAcctFlag = true;
		}
		return addAcctFlag;
	}

	/**
	 * ����ҵ������json����
	 * 
	 * @param order
	 * @param areaCode
	 * @param channelId
	 * @param staffCode
	 * @param date
	 * @return
	 */
	private JSONArray createBusiOrders(Node order, BoCreateParamGrp boCreateParamGrp, BoSeqCalculator boSeqCalculator,
			JSONArray mainOrderBusiOrderArr,String channelId) {
		Node acctCdNode = order.selectSingleNode("./acctCd");
		String prodId = WSUtil.getXmlNodeText(order, "./prodId");
		BoCreateParam boCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String areaId = boCreateParam.getAreaId();
		JSONArray busiOrderArr = new JSONArray();
		// �����Ҫ�����ʻ���������������
		if (boCreateParam.isNeedCreateAcct()) {// �����Ҫ�����ʻ�
			if (acctCdNode == null) {
				acctCdNode = ((Element) order).addElement("acctCd");
			}

			// ����������ӵ�������㵥�Ѿ��������ʻ�����ʹ����㵥���ɵ��ʻ����������ʻ�
			if (boCreateParam != null && boCreateParam.isNeedCreateAcct()) {
				// ��Ҫ��������ʻ���ҵ����
				acctCdNode.setText(boCreateParam.getAcctCd());
				JSONObject createAcctBusiOrder = createAddAccountBusiOrder(order, areaId, boCreateParam,
						boSeqCalculator);
				busiOrderArr.add(createAcctBusiOrder);
			}
		}
		// ��ϳ�Ա����
		JSONArray jsonArrObj = null;
		if (!boCreateParam.getIsCompOffer()) {
			jsonArrObj = processMemberChange(null, boCreateParamGrp, areaId, boSeqCalculator);
			busiOrderArr.addAll(jsonArrObj);
		}
		// �¶�������
		if (isNewOrder(boCreateParam.getProdId().toString())) {
			JSONArray newOrderJsonArrObj = processNewOrder(order, boCreateParam, boSeqCalculator,channelId);
			busiOrderArr.addAll(newOrderJsonArrObj);
		}
		// �ʷ���Ϣ��Ϊ�գ������ʷ�
		else if (order.selectSingleNode("./pricePlanPak") != null) {
			System.out.println(order.asXML());
			JSONArray boArrJs = processPricePlanPak(order.selectSingleNode("./pricePlanPak"), boCreateParam, boSeqCalculator, null);
			busiOrderArr.addAll(boArrJs);
		}
		
		return busiOrderArr;
	}

	// �����Ա�����Ϣ����Ա��Ʒ������
	private JSONArray processMemberChange(Node prodCompPropertysNode, BoCreateParamGrp boCreateParamGrp, String areaId,
			BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		JSONArray busiOrderMemberChange = createBusiOrderMemberChange(prodCompPropertysNode, boCreateParamGrp, areaId,
				boSeqCalculator);
		busiOrderArrJs.addAll(busiOrderMemberChange);
		return busiOrderArrJs;
	}

	// ����������˳�ҵ��������
	private JSONArray createBusiOrderMemberChange(Node prodCompPropertysNode, BoCreateParamGrp boCreateParamGrp,
			String areaId, BoSeqCalculator boSeqCalculator) {
		BoCreateParam compBoCreateParam = boCreateParamGrp.getCompBoCreateParam();
		String linkMan = compBoCreateParam.getLinkMan();
		String linkNbr = compBoCreateParam.getLinkNbr();
		String compProdId = compBoCreateParam.getProdId().toString();
		BoCreateParam boCreateParam = boCreateParamGrp.getSelfBoCreateParam();
		String memberProdSPecId = boCreateParam.getProdSpecId();
		String boActionType = boCreateParam.getRoleboActionType();
		String prodCompRelaRoleCd = boCreateParam.getRelaRoleCd();
		Long prodId = boCreateParam.getProdId();
		String state = boCreateParam.getRoleState();
		String memberProdId = null;
		if (boCreateParam.getProdId() != null) {
			memberProdId = boCreateParam.getProdId().toString();
		}
		String prodCompId = null;
		OfferProdComp offerProdComp = null;
		if (state.equals("ADD")) {
			if (offerProdComp != null) {
				prodCompId = offerProdComp.getProdCompId().toString();
			} else {
				prodCompId = boSeqCalculator.getNextProdCompIdString();
			}
		} else {
			if (offerProdComp != null) {
				prodCompId = offerProdComp.getProdCompId().toString();
			}
		}
		JSONArray busiOrderArrJs = new JSONArray();
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo ��ɣ�");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("objId", memberProdSPecId);
		busiObjJs.elementOpt("instId", memberProdId);
		busiObjJs.elementOpt("offerTypeCd", "");
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj ��ɣ�");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", boActionType);
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType ��ɣ�");

		JSONObject dataJs = new JSONObject();
		// ����������
		JSONArray busiOrderAttrsJs = this.buildBusiOrderAttrObj(boCreateParam);
		dataJs.elementOpt("busiOrderAttrs", busiOrderAttrsJs);
		Node compProdNode = null;
		if (prodCompPropertysNode != null) {
			// ��ϲ�Ʒ��Ա���ʱֻ֧�ֶԵ�ǰ�������ϲ�Ʒ��ص���ϲ�Ʒ��Ա���Ա��������ֻ֧��һ��compProd�ڵ�
			compProdNode = prodCompPropertysNode.selectSingleNode("./compProd");
		}
		// �����Ʒ������ϵĹ�����Ϣ
		if (boCreateParam.getNeedDealCompOrder()) {
			// �����Ҫ������ϲ�Ʒ��Ա�����ʹ���
			processBoProdCompOrder(dataJs, compProdId, prodCompId, prodCompRelaRoleCd, compProdNode, prodId,
					boSeqCalculator, state);
			logger.debug("--------boProdCompOrder�������!------");
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArrJs.add(busiOrderJs);
		return busiOrderArrJs;
	}

	/**
	 * �����Ա����ϲ�Ʒ֮���ϵ�Ĺ�������
	 * 
	 * @param dataJs
	 * @param compProdId
	 * @param prodCompId
	 * @param prodCompRelaRoleCd
	 * @param compProdNode
	 * @param prodId
	 * @param boSeqCalculator
	 * @param state
	 */
	private void processBoProdCompOrder(JSONObject dataJs, String compProdId, String prodCompId,
			String prodCompRelaRoleCd, Node compProdNode, Long prodId, BoSeqCalculator boSeqCalculator, String state) {
		JSONArray boProdCompOrdersJs = createBoProdCompOrders(compProdId, prodCompId, prodCompRelaRoleCd);
		JSONArray boProdCompItemArrJs = null;
		if (compProdNode != null) {
			boProdCompItemArrJs = createBoProdCompItems(compProdNode, prodId, Long.valueOf(compProdId), prodCompId,
					boSeqCalculator);
		}
		JSONArray boProdCompRelasJs = null;
		if (Long.parseLong(prodCompId) < 0) {
			boProdCompRelasJs = createBoProdCompRelas(prodCompId, CommonDomain.ADD, boSeqCalculator);
		} else {
			if (state.equals(CommonDomain.DEL)) {
				boProdCompRelasJs = createBoProdCompRelas(prodCompId, state, boSeqCalculator);
			} else if ((boProdCompItemArrJs != null && boProdCompItemArrJs.size() > 0)) {
				// �����������Ա䶯������Ϲ�ϵ֮ǰ�ִ��ڣ���KIP
				boProdCompRelasJs = createBoProdCompRelas(prodCompId, CommonDomain.KIP, boSeqCalculator);
			}
		}
		// ���������Ժ���Ϲ�ϵ��û�б䶯����û����ϲ�Ʒ��Ա����
		if ((boProdCompRelasJs != null && !boProdCompRelasJs.isEmpty())
				|| (boProdCompItemArrJs != null && !boProdCompItemArrJs.isEmpty())) {
			dataJs.elementOpt("boProdCompOrders", boProdCompOrdersJs);
			if (boProdCompRelasJs != null && !boProdCompRelasJs.isEmpty()) {
				dataJs.elementOpt("boProdCompRelas", boProdCompRelasJs);
			}
			if (boProdCompItemArrJs != null && !boProdCompItemArrJs.isEmpty()) {
				dataJs.elementOpt("boProdCompItems", boProdCompItemArrJs);
			}
		}
	}

	/**
	 * ����boProdCompOrders
	 * 
	 * @param compProdId
	 * @param prodCompId
	 * @param prodCompRelaRoleCd
	 * @return
	 */
	private JSONArray createBoProdCompOrders(String compProdId, String prodCompId, String prodCompRelaRoleCd) {
		JSONArray boProdCompOrderArrJs = new JSONArray();
		JSONObject boProdCompOrderJs = new JSONObject();
		boProdCompOrderJs.elementOpt("compProdId", compProdId);
		boProdCompOrderJs.elementOpt("prodCompId", prodCompId);
		boProdCompOrderJs.elementOpt("prodCompRelaRoleCd", prodCompRelaRoleCd);
		boProdCompOrderArrJs.add(boProdCompOrderJs);
		return boProdCompOrderArrJs;
	}

	// ��������������Ե�ҵ��������
	private JSONArray createBoProdCompItems(Node prodCompPropertysNode, Long prodId, Long compProdId,
			String prodCompId, BoSeqCalculator boSeqCalculator) {
		logger.debug("--call createBoProdCompItems");
		JSONArray boProdCompItemArrJs = new JSONArray();
		if (prodCompPropertysNode != null) {
			// �����������
			List propertyNodes = prodCompPropertysNode.selectNodes("./property");
			logger.debug("propertyNodes={}", propertyNodes);
			if (propertyNodes != null && !propertyNodes.isEmpty()) {
				for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
					Node propertie = (Node) itr1.next();
					String itemSpecId = propertie.selectSingleNode("./id").getText();
					String newValue = propertie.selectSingleNode("./value").getText();
					String actionTypeStr = propertie.selectSingleNode("./actionType").getText();

					if (actionTypeStr != null && ("0".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
						JSONObject newBoProdCompItemJs = new JSONObject();
						newBoProdCompItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						newBoProdCompItemJs.elementOpt("prodCompId", prodCompId);
						newBoProdCompItemJs.elementOpt("itemSpecId", itemSpecId);
						newBoProdCompItemJs.elementOpt("value", newValue);
						newBoProdCompItemJs.elementOpt("state", "ADD");
						newBoProdCompItemJs.elementOpt("statusCd", "S");
						boProdCompItemArrJs.add(newBoProdCompItemJs);
					}
					if (actionTypeStr != null && ("1".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
						OfferProdCompItem offerProdCompItem = null;
						if (offerProdCompItem != null) {
							JSONObject oldBoProdCompItemJs = new JSONObject();
							oldBoProdCompItemJs
									.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							oldBoProdCompItemJs.elementOpt("prodCompId", prodCompId);
							oldBoProdCompItemJs.elementOpt("itemSpecId", itemSpecId);
							oldBoProdCompItemJs.elementOpt("value", offerProdCompItem.getValue());
							oldBoProdCompItemJs.elementOpt("state", "DEL");
							oldBoProdCompItemJs.elementOpt("statusCd", "S");
							boProdCompItemArrJs.add(oldBoProdCompItemJs);
						}
					}
				}
			}
		}
		return boProdCompItemArrJs;
	}

	/**
	 * ����boProdCompRelas
	 * 
	 * @param prodCompId
	 * @param state
	 * @return
	 */
	private JSONArray createBoProdCompRelas(String prodCompId, String state, BoSeqCalculator boSeqCalculator) {
		JSONArray boProdCompRelaArrJs = new JSONArray();
		JSONObject boProdCompRelaJs = new JSONObject();
		boProdCompRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdCompRelaJs.elementOpt("prodCompId", prodCompId);
		boProdCompRelaJs.elementOpt("state", state);
		boProdCompRelaJs.elementOpt("statusCd", "S");
		boProdCompRelaArrJs.add(boProdCompRelaJs);
		return boProdCompRelaArrJs;
	}

	// �����¶���
	private JSONArray processNewOrder(Node order, BoCreateParam boCreateParam, BoSeqCalculator boSeqCalculator,String channelId) {
		JSONArray busiOrderArr = new JSONArray();
		String bindFixedLine = WSUtil.getXmlNodeText(order, "./bindFixedLine");// ������ʺ�
		String bindFixedLineIsNew = WSUtil.getXmlNodeText(order, "./bindFixedLineIsNew");// ������ʺ�
		Node acctCdNode = order.selectSingleNode("./acctCd");
		Node accessNumberNode = order.selectSingleNode("./accessNumber");
		Node bindPayNumberNode = order.selectSingleNode("./bindPayForNbr");
		Node bindNumberProdSpec = order.selectSingleNode("./bindNumberProdSpec");
		Node prod2accNbrNode = order.selectSingleNode("./prod2accNbr");
		Node pricePlanPakNode = order.selectSingleNode("./pricePlanPak");
		Node servicePakNode = order.selectSingleNode("./servicePak");
		Node prodPropertysNode = order.selectSingleNode("./prodPropertys");
		Node tdsNode = order.selectSingleNode("./tds"); // �ն���Ϣ
		Node couponsNode = order.selectSingleNode("./coupons"); // ��Ʒ��Ϣ
		Node passwordNode = order.selectSingleNode("./password");
		Node passwordTypeNode = order.selectSingleNode("./passwordType");

		String serviceId = WSUtil.getXmlNodeText(order, "./saComponent/serviceId");
		List<Node> assistManInfoList = order.selectNodes("assistManInfoList/assistManInfo");
		Node anIdNode = order.selectSingleNode("./anId"); // �������ID
		// busi_order_time
		List<Node> busiOrderTimeList = order.selectNodes("busiOrderTimeList/property");
		Node remarkNode = order.selectSingleNode("./remark");

		String prodSpecId = boCreateParam.getProdSpecId();
		String areaId = boCreateParam.getAreaId();
		String date = boCreateParam.getCurDate();
		Long prodId = boCreateParam.getProdId();
		// �������ʹ���
		String feeType = boCreateParam.getFeeType();
		if (accessNumberNode == null || StringUtils.isBlank(accessNumberNode.getText())) {
			if (accessNumberNode == null) {
				accessNumberNode = ((Element) order).addElement("accessNumber");
			}
			if (anIdNode == null) {
				anIdNode = ((Element) order).addElement("anId");
			}
			ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType(Long
					.parseLong(prodSpecId));
			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<root>");
			xml.append("<AccessNumberInfo>");
			xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
			xml.append("<areaId>").append(areaId).append("</areaId>");
			xml.append("</AccessNumberInfo>");
			xml.append("</root>");
			try {
				List list = rscServiceSMO.assignAn(xml.toString());
				if (list != null && list.size() > 0) {
					Map anMap = (Map) list.get(0);
					accessNumberNode.setText(anMap.get("name").toString());
					anIdNode.setText(anMap.get("anId").toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			boolean pp = intfSMO.qryAccessNumberIsOk(accessNumberNode.getText());
			if (pp) {
				throw new BmoException(-1, "�����Ѿ���ͨ���ߴ�����;�����������ظ���ͨ");
			}
		}
		if (anIdNode == null || StringUtils.isBlank(anIdNode.getText())) {
			if (anIdNode == null) {
				anIdNode = ((Element) order).addElement("anId");
				// ��ѯANDI ������MD����ANID �������ΰ�
				Map<String, Object> phoneInfo = intfSMO.qryPhoneNumberInfoByAccessNumber(accessNumberNode.getText());
				if (phoneInfo != null) {
					anIdNode.setText(phoneInfo.get("PHONE_NUMBER_ID").toString());
				} else {
					throw new BmoException(-1, "���벻����");
				}
			}
		}

		// �ʷ���Ϣ��Ϊ�գ������ʷ�
		if (pricePlanPakNode != null) {
			JSONArray boArrJs = processPricePlanPak(pricePlanPakNode, boCreateParam, boSeqCalculator, couponsNode);
			busiOrderArr.addAll(boArrJs);
		}

		// ��Ʒ��ҵ��������
		JSONObject busiOrderJs = new JSONObject();
		busiOrderJs.elementOpt("areaId", areaId);
		busiOrderJs.elementOpt("linkFlag", "Y");

		Integer seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfoJs = new JSONObject();
		busiOrderInfoJs.elementOpt("seq", seq);
		busiOrderInfoJs.elementOpt("statusCd", "S");
		busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
		logger.debug("---------busiOrderInfo ��ɣ�");

		JSONObject busiObjJs = new JSONObject();
		busiObjJs.elementOpt("instId", boCreateParam.getProdId());// tangminjun
		busiObjJs.elementOpt("objId", prodSpecId);
		busiObjJs.elementOpt("accessNumber", accessNumberNode.getText());
		// add
		busiOrderJs.elementOpt("busiObj", busiObjJs);
		logger.debug("---------busiObj ��ɣ�");

		JSONObject boActionTypeJs = new JSONObject();
		boActionTypeJs.elementOpt("actionClassCd", "4");
		boActionTypeJs.elementOpt("boActionTypeCd", "1");
		boActionTypeJs.elementOpt("name", "��װ");
		busiOrderJs.elementOpt("boActionType", boActionTypeJs);
		logger.debug("---------boActionType ��ɣ�");

		JSONObject dataJs = new JSONObject();
		// ƴװ���ýڵ�
		JSONArray boAcctItems = new JSONArray();
		JSONObject boAcctItem = new JSONObject();
		if (chargeInfos != null) {
			List<Node> chargeInfoList = chargeInfos.selectNodes("chargeInfo");
			for (int i = 0; chargeInfoList.size() > i; i++) {
				String specId = WSUtil.getXmlNodeText(chargeInfoList.get(i), "specId");
				if (prodSpecId.equals(specId)) {
					Map<String, String> itemAcctMap = new HashMap<String, String>();
					itemAcctMap.put("payMethod", WSUtil.getXmlNodeText(chargeInfoList.get(i), "payMethod"));
					itemAcctMap.put("acctItemTypeId", WSUtil.getXmlNodeText(chargeInfoList.get(i), "acctItemTypeId"));
					itemAcctMap.put("charge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "charge"));
					itemAcctMap.put("appCharge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "appCharge"));
					boAcctItem = createBoAcctItems(itemAcctMap);
					boAcctItems.add(boAcctItem);
				}
			}
			if (boAcctItems.size() != 0) {
				dataJs.elementOpt("boAcctItems", boAcctItems);
			}

		}
		// bo_2_staff
		if (assistManInfoList != null && assistManInfoList.size() != 0) {
			List<Map<String, String>> staffInfoList = new ArrayList<Map<String, String>>();
			for (int i = 0; assistManInfoList.size() > i; i++) {
				Map<String, String> param = new HashMap<String, String>();
				String staffTypeName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffTypeName");
				String staffType = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffType");
				String staffNumber = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffNumber");
				String staffName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffName");
				String staffId = WSUtil.getXmlNodeText(assistManInfoList.get(i), "staffId");
				String orgName = WSUtil.getXmlNodeText(assistManInfoList.get(i), "orgName");
				String orgId = WSUtil.getXmlNodeText(assistManInfoList.get(i), "orgId");
				//��������������� orgId �ŵ� staffId��
				if ("13".equals(staffType)) {
					staffId = orgId;
				}
				param.put("staffTypeName", staffTypeName);
				param.put("staffType", staffType);
				param.put("staffNumber", staffNumber);
				param.put("staffName", staffName);
				param.put("staffId", staffId);
				param.put("orgName", orgName);
				param.put("orgId", orgId);
				staffInfoList.add(param);
			}
			JSONArray bo2Staffs = bo2staff(staffInfoList, boSeqCalculator);
			dataJs.elementOpt("bo2Staffs", bo2Staffs);
			logger.debug("---------bo2Staffs ��ɣ�");
		}

		//����¥����Ϣ   boSaComponent  ��Ӧ���̱�  bo_sa_component
		logger.debug("����¥����Ϣ   boSaComponent  serviceId====" + serviceId);
		logger.error("����¥����Ϣ   boSaComponent  serviceId====" + serviceId);
		if (serviceId != null && !"".equals(serviceId)) {
			//ȥ�����˻����¥����Ϣ
			JSONArray boSaComponentJs = createboSaComponentJs(boSeqCalculator, serviceId);
			dataJs.elementOpt("boSaComponent", boSaComponentJs);
			logger.debug("---------boSaComponentJs ��ɣ�");
		}

		JSONArray boProdArrJs = createBoProds(boSeqCalculator);
		dataJs.elementOpt("boProds", boProdArrJs);
		// busiOrderTimes
		if ((busiOrderTimeList != null && busiOrderTimeList.size() != 0) || remarkNode != null) {
			List<Map<String, String>> busiOrderTimeInfoList = new ArrayList<Map<String, String>>();
			if (busiOrderTimeList != null && busiOrderTimeList.size() != 0) {
				for (int i = 0; busiOrderTimeList.size() > i; i++) {
					Map<String, String> param = new HashMap<String, String>();
					String itemSpecId = WSUtil.getXmlNodeText(busiOrderTimeList.get(i), "id");
					String value = WSUtil.getXmlNodeText(busiOrderTimeList.get(i), "value");
					param.put("itemSpecId", itemSpecId);
					param.put("value", value);
					busiOrderTimeInfoList.add(param);
				}
			}
			//����������� Ӧ������ wanghongli
			if (remarkNode != null) {
				Map<String, String> paramRemark = new HashMap<String, String>();
				paramRemark.put("itemSpecId", "111111118");
				paramRemark.put("value", remarkNode.getText());
				busiOrderTimeInfoList.add(paramRemark);
			}

			JSONArray busiOrderTimes = createBusiOrderTimes(busiOrderTimeInfoList, prodId, prodSpecId, boSeqCalculator);
			dataJs.elementOpt("busiOrderTimes", busiOrderTimes);
			logger.debug("---------busiOrderTimes ��ɣ�");
		}

		logger.debug("---------boProds ��ɣ�");
		JSONArray boProdSpecArrJs = createBoProdSpecs(prodSpecId, null, boSeqCalculator);
		dataJs.elementOpt("boProdSpecs", boProdSpecArrJs);
		logger.debug("---------boProdSpecs ��ɣ�");
		JSONArray boProdStatusArrJs = createBoProdStatuses(prodStatusCd, "ADD", boSeqCalculator);
		dataJs.elementOpt("boProdStatuses", boProdStatusArrJs);
		logger.debug("---------boProdStatuses ��ɣ�");
		JSONArray boProdFeeTypeArrJs = createBoProdFeeTypes(feeType, "ADD", boSeqCalculator);
		dataJs.elementOpt("boProdFeeTypes", boProdFeeTypeArrJs);
		logger.debug("---------boProdFeeTypes ��ɣ�");

		String newPartyIdStr = String.valueOf(boCreateParam.getPartyId());
		// ����boCusts
		JSONArray boCustArrJs = createBoCustsJson(newPartyIdStr, null, "0", boSeqCalculator);
		dataJs.elementOpt("boCusts", boCustArrJs);
		// ��Ʒ���Բ�Ϊ�գ������Ʒ����
		if (prodPropertysNode != null) {
		//����ж�ѧУ
			JSONArray boProdItemArrJs = createProdItemsJson(boCreateParam, prodPropertysNode, null, boSeqCalculator,channelId);
			dataJs.elementOpt("boProdItems", boProdItemArrJs);
		//�����֤һ������
			JSONArray boUsers = createboUsersJson(boCreateParam, prodPropertysNode, null, boSeqCalculator,channelId);
			dataJs.elementOpt("boUsers", boUsers);
			
			List<Element> properties = (List<Element>) prodPropertysNode.selectNodes("./property");
			String tmlName = "";
			for (int i = 0; i < properties.size(); i++) {
				String id = WSUtil.getXmlNodeText(properties.get(i), "./id");
				// ������̱�
				if ("30018".equals(id)) {
					String tmlValue = WSUtil.getXmlNodeText(properties.get(i), "./value");
					tmlName = WSUtil.getXmlNodeText(properties.get(i), "./name");
					JSONArray boProdTmls = createBoPordTmls(tmlName, tmlValue, boSeqCalculator);
					dataJs.elementOpt("boProdTml", boProdTmls);
				}
				// ��װ��ַ���̱�
				if ("3210047".equals(id)) {
					String addrId = WSUtil.getXmlNodeText(properties.get(i), "./value");
					String addrDetail = WSUtil.getXmlNodeText(properties.get(i), "./name");
					JSONArray boProdAddresses = createBoPordAddress(addrId, tmlName, addrDetail, boSeqCalculator);
					dataJs.elementOpt("boProdAddresses", boProdAddresses);
				}
			}

		}
		// ��װҵ��ʱ��������������
		String newPwd = "";
		String passwordTypeStr = "2";
		if ("378".equals(prodSpecId) || "379".equals(prodSpecId)) {
			newPwd = accessNumberNode.getText().substring(5);
		} else {
			newPwd = "888888";
		}
		// ����boProdPasswords
		JSONArray boProdPasswordArrJs = createBoProdPasswordsJson(newPwd, null, passwordTypeStr, boSeqCalculator);
		dataJs.elementOpt("boProdPasswords", boProdPasswordArrJs);

		// �����ն���Ϣ
		if (tdsNode != null) {
			JSONArray boProd2TdJsonArrObj = createboProd2TdsJson(tdsNode, null, boSeqCalculator);
			dataJs.elementOpt("boProd2Tds", boProd2TdJsonArrObj);
			// ����bo2coupon NND UIM��ҲҪ��
			JSONArray boProd2CouponArrJs = createbo2Coupon(tdsNode, boCreateParam.getProdId(), null, boCreateParam
					.getPartyId(), boSeqCalculator);
			dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
		}
		// ��������ʺ�
		if (StringUtils.isNotBlank(bindFixedLine)) {
			// ����bo_prod_rela�ڵ�
			JSONArray boProdRelasJs = createBoProdRelas(bindFixedLine, boSeqCalculator, bindFixedLineIsNew);
			dataJs.elementOpt("boProdRelas", boProdRelasJs);
		}
		// ���벻Ϊ���������
		if (accessNumberNode != null) {
			JSONArray boProdAnArrObj = createBoProdAns(accessNumberNode, anIdNode, prodSpecId, areaId, String
					.valueOf(boCreateParam.getPartyId()), boSeqCalculator);
			dataJs.elementOpt("boProdAns", boProdAnArrObj);
			if (passwordNode != null) {
				//	JSONArray boProdAccessAccountObj = createBoProdAccessAccounts(accessNumberNode.getText(), passwordNode.getText(), boSeqCalculator);
				JSONArray boProdAccessAccountObj = createBoProdAccessAccounts(prod2accNbrNode.getText(), passwordNode
						.getText(), boSeqCalculator);
				dataJs.elementOpt("boProdAccessAccounts", boProdAccessAccountObj);
			}
		}
		// �����������Ų�Ϊ�գ�������������
		if (prod2accNbrNode != null && !"".equals(prod2accNbrNode.getText().trim())) {
			Node anId2Node = order.selectSingleNode("./anId2"); // ���������ID
			JSONArray boProd2AnArrObj = createBoProd2Ans(prodSpecId, areaId, prod2accNbrNode, anId2Node, passwordNode,
					boSeqCalculator);
			dataJs.elementOpt("boProd2Ans", boProd2AnArrObj);
		}
		// �ʻ��ڵ㲻Ϊ�ջ�󶨸��Ѻ��벻Ϊ�գ������ʻ���ϵ
		JSONArray boAccountRelaArrJs = processAccountRela(acctCdNode, bindPayNumberNode, bindNumberProdSpec,
				boSeqCalculator, boCreateParam);
		dataJs.elementOpt("boAccountRelas", boAccountRelaArrJs);
		// ���������֧����Ҫ���������ϵ
		if (acctCdNode == null && (bindPayNumberNode != null && bindNumberProdSpec != null)) {
			String bindPayNumber = bindPayNumberNode.getText();
			Integer bindNumberProdSpecId = Integer.valueOf(bindNumberProdSpec.getText());
			JSONObject boProdRela = processProdRela(bindPayNumber, bindNumberProdSpecId,
					CommonDomain.PROD_RELA_REASON_4, CommonDomain.ADD, boSeqCalculator);
			if (boProdRela != null) {
				JSONArray boProdRelasArrJs = new JSONArray();
				dataJs.elementOpt("boProdRelas", boProdRelasArrJs);
			}
		}
		// }
		// ��Ӷ�������
		JSONArray busiOrderAttrArrObj = buildBusiOrderAttrObj(boCreateParam);
		if (!busiOrderAttrArrObj.isEmpty()) {
			dataJs.elementOpt("busiOrderAttrs", busiOrderAttrArrObj);
		}
		busiOrderJs.elementOpt("data", dataJs);
		busiOrderArr.add(busiOrderJs);

		// ������Ϣ��Ϊ�գ������Ʒ�����ҵ��������
		if (servicePakNode != null) {
			JSONArray serJsonArrObj = processServicePak(servicePakNode, prodSpecId, boCreateParam.getProdId(), areaId,
					date, boSeqCalculator);
			busiOrderArr.addAll(serJsonArrObj);
		}
		return busiOrderArr;
	}

	private JSONArray createboSaComponentJs(BoSeqCalculator boSeqCalculator, String serviceId) {

		List saComponentList = intfSMO.getSaComponentInfo(serviceId);
		if (saComponentList.size() < 0) {

		}
		Map saComponentMap = (Map) saComponentList.get(0);
		JSONArray boSaComponentArrJs = new JSONArray();
		JSONObject boSaComponentJs = new JSONObject();
		boSaComponentJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boSaComponentJs.elementOpt("state", "ADD");
		boSaComponentJs.elementOpt("serviceId", serviceId);
		//[{BUILDING_TYPE=null, SERVICE_PARTY_ID=104006610525, MANAGER_ID=1004320797, SERVICE_TYPE_ID=1, LINK_NBR=18911198858, SERVICE_NAME=�����ʷ�-��, SERVICE_ID=100000002415, LINK_MAN=�����ʷ�, BUILDING_ID=10000130}]
		boSaComponentJs.elementOpt("serviceName", saComponentMap.get("SERVICE_NAME") == null ? ""
				: saComponentMap.get("SERVICE_NAME").toString());
		boSaComponentJs.elementOpt("managerId", saComponentMap.get("MANAGER_ID") == null ? ""
				: saComponentMap.get("MANAGER_ID").toString());
		boSaComponentJs.elementOpt("linkMan", saComponentMap.get("LINK_MAN") == null ? "" : saComponentMap
				.get("LINK_MAN").toString());
		boSaComponentJs.elementOpt("linkNbr", saComponentMap.get("LINK_NBR") == null ? "" : saComponentMap
				.get("LINK_NBR").toString());
		boSaComponentJs.elementOpt("servicePartyId", saComponentMap.get("SERVICE_PARTY_ID") == null ? ""
				: saComponentMap.get("SERVICE_PARTY_ID").toString());
		boSaComponentJs.elementOpt("buildingId", saComponentMap.get("BUILDING_ID") == null ? ""
				: saComponentMap.get("BUILDING_ID").toString());
		boSaComponentJs.elementOpt("buildingType", saComponentMap.get("BUILDING_TYPE") == null ? ""
				: saComponentMap.get("BUILDING_TYPE").toString());
		boSaComponentJs.elementOpt("statusCd", saComponentMap.get("STATUS_CD") == null ? "" : saComponentMap
				.get("STATUS_CD").toString());
		boSaComponentArrJs.add(boSaComponentJs);
		return boSaComponentArrJs;
	}

	/** bo_2_staff */
	private JSONArray bo2staff(List<Map<String, String>> staffInfoList, BoSeqCalculator boSeqCalculator) {
		JSONArray bo2staffs = new JSONArray();
		for (int i = 0; staffInfoList.size() > i; i++) {
			Map<String, String> staffInfo = staffInfoList.get(i);
			JSONObject oo2Staff = new JSONObject();
			oo2Staff.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
			oo2Staff.elementOpt("areaName", "����");
			oo2Staff.elementOpt("orgName", staffInfo.get("orgName"));
			oo2Staff.elementOpt("orgId", staffInfo.get("orgId"));
			oo2Staff.elementOpt("staffId", staffInfo.get("staffId"));
			oo2Staff.elementOpt("staffType", staffInfo.get("staffType"));
			oo2Staff.elementOpt("staffTypeName", staffInfo.get("staffTypeName"));
			oo2Staff.elementOpt("staffNumber", staffInfo.get("staffNumber"));
			oo2Staff.elementOpt("state", "ADD");
			oo2Staff.elementOpt("statusCd", "S");
			bo2staffs.add(oo2Staff);
		}
		return bo2staffs;

	}

	/**
	 * �������ڵ�
	 * 
	 * @param servicePakNode
	 * @param prod
	 * @param areaId
	 * @param date
	 * @return
	 */
	private JSONArray processServicePak(Node servicePakNode, String prodSpecId, Long prodId, String areaId,
			String date, BoSeqCalculator boSeqCalculator) {
		JSONArray busiOrderArrJs = new JSONArray();
		List serviceNodes = servicePakNode.selectNodes("./service");
		// ��ʼѭ���������ı����Ϣ
		for (Iterator itr = serviceNodes.iterator(); itr.hasNext();) {
			Node serviceNode = (Node) itr.next();
			String serviceIdStr = serviceNode.selectSingleNode("./serviceId").getText();
			String actionTypeStr = serviceNode.selectSingleNode("./actionType").getText();
			Node startDtNode = serviceNode.selectSingleNode("./startDt");// ����ʼʱ��
			Node endDtNode = serviceNode.selectSingleNode("./endDt");// �������ʱ��
			Node properties = serviceNode.selectSingleNode("./properties");
			String startDt = "";
			String endDt = "";
			if (startDtNode != null) {
				startDt = ("").equals(startDtNode.getText().trim()) ? startDt : startDtNode.getText().trim();
			}
			if (endDtNode != null) {
				endDt = ("").equals(endDtNode.getText().trim()) ? endDt : endDtNode.getText().trim();
			}
			String servId = null;
			List<OfferServItem> offerServItems = null;
			if (actionTypeStr != null && (actionTypeStr.equals("1") || actionTypeStr.equals("2"))) {
				// �ҳ��ϵ�servId
				OfferServ offerServ = intfSMO.findOfferServByProdIdAndServSpecId(prodId, Long.parseLong(serviceIdStr));
				servId = offerServ.getServId().toString();
				offerServItems = offerServ.getOfferServItems();
			} else {
				servId = servInstList.get(serviceIdStr);// ��������Ҫ�����µķ���ʵ�����к�
			}

			JSONObject busiOrderJs = new JSONObject();

			busiOrderJs.elementOpt("areaId", areaId);
			busiOrderJs.elementOpt("linkFlag", "Y");

			Integer seq = boSeqCalculator.getNextSeqInteger();
			JSONObject busiOrderInfoJs = new JSONObject();
			busiOrderInfoJs.elementOpt("seq", seq);
			busiOrderInfoJs.elementOpt("statusCd", "S");
			busiOrderInfoJs.elementOpt("appStartDt", startDt);
			busiOrderInfoJs.elementOpt("appEndDt", endDt);
			busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
			logger.debug("---------busiOrderInfo ��ɣ�");

			JSONObject busiObjJs = new JSONObject();
			busiObjJs.elementOpt("objId", prodSpecId);
			busiObjJs.elementOpt("instId", prodId);
			busiObjJs.elementOpt("offerTypeCd", "");
			busiOrderJs.elementOpt("busiObj", busiObjJs);
			logger.debug("---------busiObj ��ɣ�");

			JSONObject boActionTypeJs = new JSONObject();
			boActionTypeJs.elementOpt("actionClassCd", "4");
			boActionTypeJs.elementOpt("boActionTypeCd", "7");
			boActionTypeJs.elementOpt("name", "������Ϣ���");
			busiOrderJs.elementOpt("boActionType", boActionTypeJs);
			logger.debug("---------boActionType ��ɣ�");

			JSONObject dataJs = new JSONObject();
			// ����boServOrders
			JSONArray boServOrderArrJs = new JSONArray();
			JSONObject boServOrderJs = new JSONObject();
			boServOrderJs.elementOpt("servId", servId);
			boServOrderJs.elementOpt("servSpecId", serviceIdStr);
			boServOrderArrJs.add(boServOrderJs);
			dataJs.elementOpt("boServOrders", boServOrderArrJs);
			logger.debug("---------boServOrders ��ɣ�");

			if (actionTypeStr != null && (actionTypeStr.equals("0") || actionTypeStr.equals("1"))) {
				// ����boServs
				JSONArray boServArrJs = new JSONArray();
				JSONObject boServJs = new JSONObject();
				if (actionTypeStr != null && actionTypeStr.equals("0")) {
					boServJs.elementOpt("servId", servId);
					boServJs.elementOpt("state", "ADD");
				} else if (actionTypeStr != null && actionTypeStr.equals("1")) {
					boServJs.elementOpt("servId", servId);
					boServJs.elementOpt("state", "DEL");
				}
				boServJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boServJs.elementOpt("statusCd", "S");
				boServJs.elementOpt("appStartDt", startDt);
				boServJs.elementOpt("appEndDt", endDt);
				boServArrJs.add(boServJs);
				dataJs.elementOpt("boServs", boServArrJs);
				logger.debug("---------boServs ��ɣ�");

				// �رշ���ʱ�򲻱ش���������Խڵ㣬�������Զ�����
				List propertyNodes = null;
				if (properties != null) {
					// �������Ե�
					propertyNodes = properties.selectNodes("./property");
				}
				if (propertyNodes != null && !propertyNodes.isEmpty() && actionTypeStr.equals("0")) {
					JSONArray boServItemArrJs = new JSONArray();
					for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
						Node propertie = (Node) itr1.next();
						String itemSpecId = propertie.selectSingleNode("./id").getText();
						String newValue = propertie.selectSingleNode("./value").getText();
						JSONObject boServItemJs = new JSONObject();
						boServItemJs.elementOpt("servId", servId);
						boServItemJs.elementOpt("itemSpecId", itemSpecId);
						boServItemJs.elementOpt("value", newValue);
						boServItemJs.elementOpt("state", "ADD");
						boServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						boServItemJs.elementOpt("statusCd", "S");
						boServItemJs.elementOpt("appStartDt", startDt);
						boServItemJs.elementOpt("appEndDt", endDt);
						boServItemArrJs.add(boServItemJs);
					}
					dataJs.elementOpt("boServItems", boServItemArrJs);
					logger.debug("---------boServItems ��ɣ�");
				}
			} else if (actionTypeStr != null && actionTypeStr.equals("2")) {
				// �������Եı��
				JSONArray boServItemArrJs = new JSONArray();
				List propertyNodes = properties.selectNodes("./property");
				for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
					Node propertie = (Node) itr1.next();
					String itemSpecId = propertie.selectSingleNode("./id").getText();
					String newValue = propertie.selectSingleNode("./value").getText();
					// ������
					JSONObject newBoServItemJs = new JSONObject();
					newBoServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					newBoServItemJs.elementOpt("servId", servId);
					newBoServItemJs.elementOpt("itemSpecId", itemSpecId);
					newBoServItemJs.elementOpt("value", newValue);
					newBoServItemJs.elementOpt("state", "ADD");
					newBoServItemJs.elementOpt("statusCd", "S");
					newBoServItemJs.elementOpt("appStartDt", startDt);
					newBoServItemJs.elementOpt("appEndDt", endDt);
					boServItemArrJs.add(newBoServItemJs);
					// ɾ���ϵ�����
					JSONObject oldBoServItemJs = new JSONObject();
					String oldValue = null;
					boolean existFlag = false;
					for (OfferServItem offerServItem : offerServItems) {
						if (itemSpecId.equals(offerServItem.getItemSpecId().toString())) {
							oldValue = offerServItem.getValue();
							existFlag = true;
							break;
						}
					}
					if (existFlag) {
						oldBoServItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						oldBoServItemJs.elementOpt("servId", servId);
						oldBoServItemJs.elementOpt("itemSpecId", itemSpecId);
						oldBoServItemJs.elementOpt("value", oldValue);
						oldBoServItemJs.elementOpt("state", "DEL");
						oldBoServItemJs.elementOpt("statusCd", "S");
						oldBoServItemJs.elementOpt("appStartDt", startDt);
						oldBoServItemJs.elementOpt("appEndDt", endDt);
						boServItemArrJs.add(oldBoServItemJs);
					}
				}
				dataJs.elementOpt("boServItems", boServItemArrJs);
				logger.debug("---------boServItems ��ɣ�");
			}
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		return busiOrderArrJs;
	}

	/**
	 * �����ʷѽڵ�
	 * 
	 * @param pricePlanPakNode
	 * @param prod
	 * @param areaId
	 * @param date
	 * @return
	 */
	private JSONArray processPricePlanPak(Node pricePlanPakNode, BoCreateParam boCreateParam,
			BoSeqCalculator boSeqCalculator, Node couponsNode) {

		Integer prodSpecId = Integer.valueOf(boCreateParam.getProdSpecId());
		Long prodId = boCreateParam.getProdId();
		Long partyId = boCreateParam.getPartyId();
		String areaId = boCreateParam.getAreaId();

		JSONArray busiOrderArrJs = new JSONArray();
		List<Node> pricePlanNodes = pricePlanPakNode.selectNodes("./pricePlan");
		// ��ʼѭ�������ʷѽڵ���Ϣ
		for (Node pricePlanNode : pricePlanNodes) {
			String pricePlanCdStr = pricePlanNode.selectSingleNode("./pricePlanCd").getText();
			String actionTypeStr = pricePlanNode.selectSingleNode("./actionType").getText();
			String startDt = WSUtil.getXmlNodeText(pricePlanNode, "./startDt");
			String endDt = WSUtil.getXmlNodeText(pricePlanNode, "./endDt");
			String time_unit = WSUtil.getXmlNodeText(pricePlanNode, "./time_unit");
			String startFashion = WSUtil.getXmlNodeText(pricePlanNode, "./startFashion");
			String endFashion = WSUtil.getXmlNodeText(pricePlanNode, "./endFashion");
			String actionType = WSUtil.getXmlNodeText(pricePlanNode, "./actionType");
			Node properties = pricePlanNode.selectSingleNode("./properties");

			if (StringUtils.isNotBlank(endDt) && "2".equals(actionTypeStr)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-01");
				try {
					Date edate = format.parse(endDt);
					Date curDate = intfSMO.getCurrentTime();
					String dateStr = format2.format(curDate);
					curDate = format2.parse(dateStr);
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(curDate);
					calendar.add(Calendar.MONTH, 1);
					curDate = calendar.getTime();
					if (edate.before(curDate)) {
						startDt = "";
						endDt = "";
						actionTypeStr = "1";// �˶�
					} else {
						throw new BmoException(-1, "�ݲ�֧�ִ����ʷѱ��ҵ��");
					}

				} catch (ParseException e) {
					throw new BmoException(-1, "���ڸ�ʽ����");
				}
			}
			// add by wanghongli ʧЧʱ������ʱ����
			if (StringUtils.isNotBlank(endDt)) {
				endDt = endDt + " 23:59:59";
			}

			String offerSpecId = null;
			String offerTypeCd = null;
			OfferSpec offerSpec = null;
			if (StringUtils.isNotBlank(pricePlanCdStr)) {
				offerSpec = offerSpecSMO.queryMainOfferSpecByOfferSpecId(Long.parseLong(pricePlanCdStr));
			}
			if (offerSpec == null) {
				throw new BmoException(-1, "pricePlanCdStr=" + pricePlanCdStr + "û��ƥ�������Ʒ���");
			}

			offerSpecId = offerSpec.getOfferSpecId().toString();
			offerTypeCd = offerSpec.getOfferTypeCd().toString();
			logger.debug("offerSpecId={},pricePlanCd={}", offerSpecId, pricePlanCdStr);

			String oldOfferId = null;
			List<OfferParam> offerParams = null;
			if (actionTypeStr != null && (actionTypeStr.equals("1") || actionTypeStr.equals("2"))) {
				Offer offer = intfSMO.findOfferByProdIdAndOfferSpecId(prodId, offerSpec.getOfferSpecId());
				if (offer != null) {
					oldOfferId = offer.getOfferId().toString();
					// �ҳ�����Ʒʵ������
					offerParams = offerSMO.queryOfferParamByOfferId(Long.parseLong(oldOfferId));
				}
			}

			List<Map<String, Object>> offerRoles = new ArrayList<Map<String, Object>>();
			OfferRoles prodOfferRoles = intfSMO.findProdOfferRoles(offerSpec.getOfferSpecId(), prodSpecId.longValue());
			if (prodOfferRoles != null) {
				Map<String, Object> prodOfferRoleMap = new HashMap<String, Object>();
				prodOfferRoleMap.put("objType", CommonDomain.OBJ_TYPE_PROD_SPEC);
				prodOfferRoleMap.put("offerRoles", prodOfferRoles);
				offerRoles.add(prodOfferRoleMap);
			}
			List<OfferRoles> servOfferRoleList = intfSMO.findServOfferRoles(offerSpec.getOfferSpecId());

			if (servOfferRoleList != null && servOfferRoleList.size() > 0) {
				for (OfferRoles servOfferRoles : servOfferRoleList) {
					Map<String, Object> servOfferRoleMap = new HashMap<String, Object>();
					servOfferRoleMap.put("objType", CommonDomain.OBJ_TYPE_SERV_SPEC);
					servOfferRoleMap.put("offerRoles", servOfferRoles);
					offerRoles.add(servOfferRoleMap);
				}
			}

			JSONObject busiOrderJs = new JSONObject();
			busiOrderJs.elementOpt(CommonDomain.XSD_AREA_ID_DICTIONARY, areaId);
			busiOrderJs.elementOpt("linkFlag", "Y");

			Integer seq = boSeqCalculator.getNextSeqInteger();
			JSONObject busiOrderInfoJs = new JSONObject();
			busiOrderInfoJs.elementOpt(CommonDomain.XSD_SEQ_DICTIONARY, seq);
			busiOrderInfoJs.elementOpt(CommonDomain.XSD_STATUS_CD_DICTIONARY,
					CommonDomain.ORDER_STATUS_SAVED_AND_NOT_PASSED_CHECK);
			busiOrderInfoJs.elementOpt("appStartDt", startDt);
			busiOrderInfoJs.elementOpt("appEndDt", endDt);
			busiOrderJs.elementOpt(CommonDomain.XSD_BUSI_ORDER_INFO_DICTIONARY, busiOrderInfoJs);
			logger.debug("---------busiOrderInfo ��ɣ�");

			if (actionTypeStr != null && actionTypeStr.equals("0")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt(CommonDomain.XSD_OBJ_ID_DICTIONARY, offerSpecId);
				busiObjJs.elementOpt("instId", boSeqCalculator.getNextOfferIdString());
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj ��ɣ�");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S1");
				boActionTypeJs.elementOpt("name", "����");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType ��ɣ�");
			} else if (actionTypeStr != null && actionTypeStr.equals("1")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", offerSpecId);
				busiObjJs.elementOpt("instId", oldOfferId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj ��ɣ�");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S2");
				boActionTypeJs.elementOpt("name", "�˶�");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType ��ɣ�");
			} else if (actionTypeStr != null && actionTypeStr.equals("2")) {
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", offerSpecId);
				busiObjJs.elementOpt("instId", oldOfferId);
				busiObjJs.elementOpt("offerTypeCd", offerTypeCd);
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				logger.debug("---------busiObj ��ɣ�");

				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "3");
				boActionTypeJs.elementOpt("boActionTypeCd", "S4");
				boActionTypeJs.elementOpt("name", "�Ĳ���");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				logger.debug("---------boActionType ��ɣ�");
			}

			JSONObject dataJs = new JSONObject();
			if (actionTypeStr != null && actionTypeStr.equals("0")) {
				String state = "ADD";
				// ƴ���ýڵ�
				JSONArray boAcctItems = new JSONArray();
				JSONObject boAcctItem = new JSONObject();
				if (chargeInfos != null) {
					List<Node> chargeInfoList = chargeInfos.selectNodes("chargeInfo");
					for (int i = 0; chargeInfoList.size() > i; i++) {
						String specId = WSUtil.getXmlNodeText(chargeInfoList.get(i), "specId");
						if (pricePlanCdStr.equals(specId)) {
							Map<String, String> itemAcctMap = new HashMap<String, String>();
							itemAcctMap.put("payMethod", WSUtil.getXmlNodeText(chargeInfoList.get(i), "payMethod"));
							itemAcctMap.put("acctItemTypeId", WSUtil.getXmlNodeText(chargeInfoList.get(i),
									"acctItemTypeId"));
							itemAcctMap.put("charge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "charge"));
							itemAcctMap.put("appCharge", WSUtil.getXmlNodeText(chargeInfoList.get(i), "appCharge"));
							boAcctItem = createBoAcctItems(itemAcctMap);
							boAcctItems.add(boAcctItem);
						}
					}
					if (boAcctItems.size() != 0) {
						dataJs.elementOpt("boAcctItems", boAcctItems);
					}
				}
				// �ж��������Ʒ������Ʒ�ҹ�������bo_2_coupons
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("offerSpecId", pricePlanCdStr);
				if (intfSMO.yesOrNoNeedAddCoupon(param) && couponsNode != null) {
					JSONArray boProd2CouponArrJs = createboProd2CouponsJson(couponsNode, boCreateParam.getProdId(),
							null, boCreateParam.getPartyId(), boSeqCalculator);
					dataJs.elementOpt("bo2Coupons", boProd2CouponArrJs);
				}

				// ����ooTimes
				JSONArray ooTimes = CreateOoTimes(boSeqCalculator, startDt, endDt, startFashion, endFashion, actionType,time_unit);
				dataJs.elementOpt("ooTimes", ooTimes);

				// ����boOffers
				JSONArray boOfferArrJs = new JSONArray();
				JSONObject boOfferJs = new JSONObject();
				boOfferJs.elementOpt("state", state);
				boOfferJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boOfferJs.elementOpt("statusCd", "S");
				boOfferJs.elementOpt("appStartDt", startDt);
				boOfferJs.elementOpt("appEndDt", endDt);
				boOfferArrJs.add(boOfferJs);
				dataJs.elementOpt("boOffers", boOfferArrJs);
				logger.debug("---------boOffers ��ɣ�");

				// ����ooOwners
				JSONArray ooOwnerArrJs = new JSONArray();
				JSONObject ooOwnerJs = new JSONObject();
				ooOwnerJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				ooOwnerJs.elementOpt("partyId", partyId.toString());
				ooOwnerJs.elementOpt("state", state);
				ooOwnerJs.elementOpt("statusCd", "S");
				ooOwnerArrJs.add(ooOwnerJs);
				dataJs.elementOpt("ooOwners", ooOwnerArrJs);
				logger.debug("---------ooOwners ��ɣ�");

				// �˶�����Ʒֻ��Ҫ�����������
				if (actionTypeStr != null && actionTypeStr.equals("0")) {
					// ����ooParams
					// ���Դ���
					List<Node> propertyNodes = null;
					if (properties != null) {
						propertyNodes = properties.selectNodes("./property");
					}
					if (propertyNodes != null && !propertyNodes.isEmpty()) {
						JSONArray ooParamArrJs = new JSONArray();
						for (Node propertie : propertyNodes) {
							String itemSpecId = propertie.selectSingleNode("./id").getText();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("offerSpecId", pricePlanCdStr);
							map.put("itemSpecId", itemSpecId);
							String offerSpecParamId = intfSMO.queryOfferSpecParamIdByItemSpecId(map);
							String newValue = propertie.selectSingleNode("./value").getText();
							if (StringUtils.isBlank(newValue)) {
								throw new BmoException(-1, "����Ʒ" + pricePlanCdStr + "�������ԣ�ֵ����Ϊ��");
							}
							JSONObject ooParamJs = new JSONObject();
							ooParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							ooParamJs.elementOpt("itemSpecId", itemSpecId);
							ooParamJs.elementOpt("value", newValue);
							ooParamJs.elementOpt("state", CommonDomain.ADD);
							ooParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							ooParamJs.elementOpt("statusCd", "S");
							ooParamJs.elementOpt("appStartDt", startDt);
							ooParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(ooParamJs);
						}
						if (!ooParamArrJs.isEmpty()) {
							dataJs.elementOpt("ooParams", ooParamArrJs);
							logger.debug("---------ooParams ��ɣ�");
						}
					}

					// ����ooRoles
					if (offerRoles.size() > 0) {
						JSONArray ooRoleArrJs = new JSONArray();

						for (Map<String, Object> offerRoleMap : offerRoles) {
							JSONObject ooRoleJs = new JSONObject();
							ooRoleJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							ooRoleJs.elementOpt("state", state);
							ooRoleJs.elementOpt("statusCd", "S");
							ooRoleJs.elementOpt("startDt", startDt);
							ooRoleJs.elementOpt("endDt", endDt);
							logger.debug("prodId={}", prodId);
							if (prodId != null) {
								ooRoleJs.elementOpt("prodId", prodId);
							}
							// �������Ʒ��Ա��ɫID
							OfferRoles offerRole = (OfferRoles) offerRoleMap.get("offerRoles");
							ooRoleJs.elementOpt("offerRoleId", offerRole.getOfferRoleId());
							Integer objType = (Integer) offerRoleMap.get("objType");
							ooRoleJs.elementOpt("objType", objType);
							logger.debug("offerRole.getOfferRoleId()={}, objType={}", offerRole.getOfferRoleId(),
									objType);
							if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_PROD_SPEC)) {
								// �����Ա��ɫ���������Ƿ�����ʱ
								ooRoleJs.elementOpt("objId", prodSpecId);
								if (prodId != null) {
									ooRoleJs.elementOpt("objInstId", prodId);
								} else {
									ooRoleJs.elementOpt("objInstId", "-1");
								}
							} else if (objType != null && objType.equals(CommonDomain.OBJ_TYPE_SERV_SPEC)) {
								// �����Ա��ɫ���������ǲ�������ʱ
								// ��ȡ����Ʒ��Ա��ɫ����ID
								RoleObj roleObj = intfSMO.findRoleObjByOfferRoleIdAndObjType(
										offerRole.getOfferRoleId(), objType);
								ooRoleJs.elementOpt("objId", roleObj.getObjId());
								logger.debug("roleObj.getObjId()={}", roleObj.getObjId());
								String instId = boSeqCalculator.getNextServIdInteger().toString();
								ooRoleJs.elementOpt("objInstId", instId);
								servInstList.put(roleObj.getObjId().toString(), instId);
							}
							ooRoleArrJs.add(ooRoleJs);
						}
						if (!ooRoleArrJs.isEmpty()) {
							dataJs.elementOpt("ooRoles", ooRoleArrJs);
							logger.debug("---------ooRoles ��ɣ�");
						}
					} else {
						throw new BmoException(-1, "��Ա��ɫ������,��Ʒ���ID��" + prodSpecId + "������Ʒ���ID+��" + offerSpecId + "��");
					}
				}
			} else if (actionTypeStr != null && actionTypeStr.equals("2")) {
				// ����ooParams
				// ���Եı��
				JSONArray ooParamArrJs = new JSONArray();

				if (properties != null) {
					List propertyNodes = properties.selectNodes("./property");
					for (Iterator itr1 = propertyNodes.iterator(); itr1.hasNext();) {
						Node propertie = (Node) itr1.next();
						String itemSpecId = propertie.selectSingleNode("./id").getText();
						String newValue = propertie.selectSingleNode("./value").getText();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("offerSpecId", pricePlanCdStr);
						map.put("itemSpecId", itemSpecId);
						String offerSpecParamId = intfSMO.queryOfferSpecParamIdByItemSpecId(map);
						// ������
						if (StringUtils.isNotBlank(newValue)) {
							JSONObject newOoParamJs = new JSONObject();
							newOoParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							newOoParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							newOoParamJs.elementOpt("itemSpecId", itemSpecId);
							newOoParamJs.elementOpt("value", newValue);
							newOoParamJs.elementOpt("state", CommonDomain.ADD);
							newOoParamJs.elementOpt("statusCd", "S");
							newOoParamJs.elementOpt("appStartDt", startDt);
							newOoParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(newOoParamJs);
						}
						// ɾ���ϵ�����
						JSONObject oldOoParamJs = new JSONObject();
						String oldValue = null;
						Long oldOfferParamId = null;
						boolean existFlag = false;
						for (OfferParam offerParam : offerParams) {
							if (offerSpecParamId.equals(offerParam.getOfferSpecParamId().toString())) {
								oldValue = offerParam.getValue();
								oldOfferParamId = offerParam.getOfferParamId();
								existFlag = true;
								break;
							}
						}
						if (existFlag) {
							oldOoParamJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
							oldOoParamJs.elementOpt("offerSpecParamId", offerSpecParamId);
							oldOoParamJs.elementOpt("value", oldValue);
							oldOoParamJs.elementOpt("offerParamId", oldOfferParamId);
							oldOoParamJs.elementOpt("state", CommonDomain.DEL);
							oldOoParamJs.elementOpt("statusCd", "S");
							oldOoParamJs.elementOpt("appStartDt", startDt);
							oldOoParamJs.elementOpt("appEndDt", endDt);
							ooParamArrJs.add(oldOoParamJs);
						}
					}
					dataJs.elementOpt("ooParams", ooParamArrJs);
					logger.debug("---------ooParams ��ɣ�");
				}
			}
			busiOrderJs.elementOpt("data", dataJs);
			busiOrderArrJs.add(busiOrderJs);
		}
		return busiOrderArrJs;
	}

	/** ooTimes 
	 * @param time_unit */

	private JSONArray CreateOoTimes(BoSeqCalculator boSeqCalculator, String startDt, String endDt, String startFashion,
			String endFashion, String actionType, String time_unit) {
		// ȫ��Ϊ������Ч|
		// 1Ĭ����Ч|startDt��Ϊ��Ϊָ��ʱ����Ч
		String startTime = "";
		String startTimeUnitCd = "";
		
		String isDefaultStart = "";

		String effTime = "";
		String effTimeUnitCd = "";
		String endTime = "";
		String endTimeUnitCd = "";
		String isDefaultEnd = "";
		if (StringUtils.isBlank(startDt) && !"1".equals(actionType)) {
			// �� ������Ч|1Ĭ����Ч |0������Ч
			if (StringUtils.isBlank(startFashion)) {
				startTime = "1";
				startTimeUnitCd = "7";
			} else if ("1".equals(startFashion)) {
				isDefaultStart = "Y";
			}
		}
		if(!StringUtil.isEmpty(time_unit))
			effTime = time_unit;
		
		if (StringUtils.isBlank(endDt)) {
			if ("1".equals(actionType)) {
				// �մ���1��ʧЧ|0����ʧЧ|1Ĭ��ʧЧ|����1 N���ں�ʧЧ
				if (StringUtils.isBlank(endFashion)) {
					endTime = "1";
					endTimeUnitCd = "7";
				} else if ("1".equals(endFashion)) {
					isDefaultEnd = "Y";
				} else if (StringUtils.isNotBlank(endFashion) && Integer.valueOf(endFashion) > 1) {
					effTime = endFashion;
					effTimeUnitCd = "7";
				}
			} else {
				isDefaultEnd = "Y";
			}
		}
		// �¶���������ʧЧ�����������
		if ("0".equals(actionType) && "0".equals(endFashion)) {
			throw new BmoException(-1, "��Ч��ʽ���󣬿�ͨ���񲻿���������ʧЧ");
		}
		// ����ooTimes
		JSONArray ooTimes = new JSONArray();
		JSONObject ooTime = new JSONObject();
		ooTime.elementOpt("state", "ADD");
		ooTime.elementOpt("statusCd", "S");
		ooTime.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		ooTime.elementOpt("startTime", startTime);
		ooTime.elementOpt("startDt", startDt);
		ooTime.elementOpt("startTimeUnitCd", startTimeUnitCd);
		ooTime.elementOpt("isDefaultStart", isDefaultStart);

		ooTime.elementOpt("endDt", endDt);
		ooTime.elementOpt("effTime", effTime);
		ooTime.elementOpt("endTime", endTime);
		ooTime.elementOpt("effTimeUnitCd", effTimeUnitCd);
		ooTime.elementOpt("endTimeUnitCd", endTimeUnitCd);
		ooTime.elementOpt("isDefaultEnd", isDefaultEnd);
		ooTimes.add(ooTime);
		return ooTimes;
	}

	// ����boAcctItems
	private JSONObject createBoAcctItems(Map<String, String> boAcctItemMap) {

		JSONObject boAcctItem = new JSONObject();
		boAcctItem.elementOpt("acctItemTypeId", boAcctItemMap.get("acctItemTypeId"));
		boAcctItem.elementOpt("amount", boAcctItemMap.get("appCharge"));
		boAcctItem.element("platId", interfaceId);
		boAcctItem.elementOpt("createdDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("payedDate", WSUtil.getSysDt("yyyy-MM-dd"));
		boAcctItem.elementOpt("statusCd", "5JA");
		boAcctItem.elementOpt("payMethodId", boAcctItemMap.get("payMethod"));
		boAcctItem.elementOpt("realAmount", boAcctItemMap.get("charge"));
		boAcctItem.elementOpt("currency", "");
		boAcctItem.elementOpt("ratioMethod", "");
		boAcctItem.elementOpt("ratio", "");
		return boAcctItem;
	}

	/**
	 * ���������ʻ�ҵ����
	 * 
	 * @param order
	 * @param areaId
	 * @param boCreateParam
	 * @param boSeqCalculator
	 * @param curDate
	 * @return
	 */
	private JSONObject createAddAccountBusiOrder(Node order, String areaId, BoCreateParam boCreateParam,
			BoSeqCalculator boSeqCalculator) {
		String addAccountBusiOrderTemplate = "{'areaId':0,'data':{'boAccountInfos':[{'partyId':0,'acctName':'','acctCd':'','businessPassword':'','prodId':'','state':'ADD','statusCd':'S','atomActionId':-1}],'boPaymentAccounts':[{'paymentAccountId':-1,'paymentAcctTypeCd':1,'bankId':'','bankAcct':'','paymentMan':'','limitQty':'','atomActionId':-2,'state':'ADD','statusCd':'S'}],'boAcct2PaymentAccts':[{'paymentAccountId':-1,'priority':1,'startDate':'','endDate':'3000-01-01','state':'ADD','statusCd':'S','atomActionId':-3}]},'boActionType':{'actionClassCd':2,'boActionTypeCd':'A1'},'busiObj':{'instId':-1},'busiOrderInfo':{'seq':-1,'statusCd':'S'}}";
		JSONObject busiOrderJs = JSONObject.fromObject(addAccountBusiOrderTemplate);
		String partyId = null;
		String partyName = null;
		// ȡ�������ͣ�����ʱ�����ʻ�Ҫ���ӵ��¿ͻ�����
		Node orderTypeIdNode = order.selectSingleNode("./orderTypeId");
		String orderTypeId = "," + orderTypeIdNode.getText().trim() + ",";
		if (orderTypeId.indexOf(",11,") != -1) {// �й�������
			Node newPartyIdNode = order.selectSingleNode("./newPartyId");
			if (newPartyIdNode != null) {
				partyId = newPartyIdNode.getText();
			} else {
				throw new BmoException(-1, "����ͬʱ�����ʻ�û�д����¿ͻ�ID");
			}
		} else {
			partyId = boCreateParam.getPartyId().toString();
		}

		Party party = custFacade.getPartyById(partyId);
		if (party != null) {
			partyName = party.getPartyName();
		} else {
			throw new BmoException(-1, "�����ʻ��鲻���ͻ���Ϣ��partyId=" + partyId);
		}

		busiOrderJs.getJSONObject("busiOrderInfo").put("seq", boSeqCalculator.getNextSeqString());
		// ���ݽڵ�
		busiOrderJs.put("areaId", areaId);

		busiOrderJs.getJSONObject("busiObj").put("instId", boCreateParam.getAcctId());

		JSONObject boAccountInfo = busiOrderJs.getJSONObject("data").getJSONArray("boAccountInfos").getJSONObject(0);
		boAccountInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAccountInfo.put("partyId", partyId);
		boAccountInfo.put("prodId", boCreateParam.getProdId());
		boAccountInfo.put("acctName", partyName);
		boAccountInfo.put("acctCd", boCreateParam.getAcctCd());
		logger.debug("���boAccountInfo���!");
		JSONObject boPaymentAccounts = busiOrderJs.getJSONObject("data").getJSONArray("boPaymentAccounts")
				.getJSONObject(0);
		boPaymentAccounts.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boPaymentAccounts.put("paymentMan", partyName);
		JSONObject boAcct2PaymentAcct = busiOrderJs.getJSONObject("data").getJSONArray("boAcct2PaymentAccts")
				.getJSONObject(0);
		boAcct2PaymentAcct.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAcct2PaymentAcct.put("startDate", boCreateParam.getCurDate());
		logger.debug("���boAcct2PaymentAcct���!");
		JSONArray boAccountRelas = new JSONArray();
		JSONObject boAccountRela = new JSONObject();
		boAccountRela.put("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boAccountRela.put("statusCd", "S");
		boAccountRela.put("acctId", boCreateParam.getAcctId());
		boAccountRela.put("state", "ADD");
		boAccountRela.put("mailingBill", "");
		boAccountRela.put("mailingDetail", "");
		boAccountRela.put("percent", Integer.valueOf("100"));
		boAccountRela.put("priority", Integer.valueOf("1"));
		boAccountRela.put("acctRelaTypeCd", "1");
		boAccountRela.put("acctProdId", "");
		boAccountRela.put("assignTypeCd", "");
		boAccountRela.put("assignValue", "");
		boAccountRela.put("conChargeItemCd", "");
		boAccountRela.put("chargeItemCd", "0");
		boAccountRela.put("prodAcctId", "");
		boAccountRela.put("extProdAcctId", "");
		boAccountRelas.add(boAccountRela);
		busiOrderJs.getJSONObject("data").elementOpt("boAccountRelas", boAccountRelas);
		return busiOrderJs;
	}

	/**
	 * �����������Թ�������
	 * 
	 * @param boCreateParam
	 * @return
	 */
	private JSONArray buildBusiOrderAttrObj(BoCreateParam boCreateParam) {
		JSONArray busiOrderAttrArrObj = new JSONArray();
		if (boCreateParam != null) {
			// ��ϵ�˺���ϵ�绰����
			String linkMan = null;
			String linkNbr = null;
			if (boCreateParam.getLinkMan() != null && !boCreateParam.getLinkMan().equals("")) {
				linkMan = boCreateParam.getLinkMan();
			}
			if (boCreateParam.getLinkNbr() != null && !boCreateParam.getLinkNbr().equals("")) {
				linkNbr = boCreateParam.getLinkNbr();
			}
			JSONArray busiOrderAttrArrObj1 = createBusiOrderAttrs(linkMan, linkNbr);
			busiOrderAttrArrObj.addAll(busiOrderAttrArrObj1);
		}
		// ���Э�����˴���
		String assistMan = boCreateParam.getAssistMan();
		if (assistMan != null && !assistMan.trim().equals("")) {
			JSONObject busiOrderAttrJs = new JSONObject();
			busiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_ASSIST_MAN);
			busiOrderAttrJs.elementOpt("value", assistMan);
			busiOrderAttrJs.elementOpt("state", "ADD");
			busiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrArrObj.add(busiOrderAttrJs);
		}
		// TODO:���ʽ����
		if (ssCd != null && !ssCd.equals("")) {
			JSONObject busiOrderAttrJs = new JSONObject();
			busiOrderAttrJs.elementOpt("itemSpecId", WSDomain.BUSI_ORDER_ATTR_PROD_STATUS);
			busiOrderAttrJs.elementOpt("value", ssCd);
			busiOrderAttrJs.elementOpt("state", "ADD");
			busiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrArrObj.add(busiOrderAttrJs);
		}

		// ������������Ķ������ԣ��������д���
		List<Element> coItemList = boCreateParam.getCoItemList();
		if (coItemList != null && coItemList.size() > 0) {
			JSONArray busiOrderAttrArrObj2 = createBusiOrderAttrs(coItemList);
			busiOrderAttrArrObj.addAll(busiOrderAttrArrObj2);
		}
		return busiOrderAttrArrObj;
	}

	/**
	 * ҵ������������
	 * 
	 * @param coLinkManNode
	 * @param coLinkNbrNode
	 * @return
	 */
	private JSONArray createBusiOrderAttrs(String linkMan, String linkNbr) {
		// ����busiOrderAttrs
		JSONArray busiOrderAttrs = new JSONArray();

		if (linkMan != null && !linkMan.trim().equals("")) {
			JSONObject linkManBusiOrderAttrJs = new JSONObject();
			linkManBusiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_LINK_MAN);
			linkManBusiOrderAttrJs.elementOpt("value", linkMan);
			linkManBusiOrderAttrJs.elementOpt("state", "ADD");
			linkManBusiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrs.add(linkManBusiOrderAttrJs);
		}

		if (linkNbr != null && !linkNbr.trim().equals("")) {
			JSONObject linkNbrBusiOrderAttrJs = new JSONObject();
			linkNbrBusiOrderAttrJs.elementOpt("itemSpecId", CommonDomain.ITEM_SPEC_LINK_TEL);
			linkNbrBusiOrderAttrJs.elementOpt("value", linkNbr);
			linkNbrBusiOrderAttrJs.elementOpt("state", "ADD");
			linkNbrBusiOrderAttrJs.elementOpt("statusCd", "S");
			busiOrderAttrs.add(linkNbrBusiOrderAttrJs);
		}
		return busiOrderAttrs;
	}

	/**
	 * ҵ������������
	 * 
	 * @param coItemList
	 * @return
	 */
	private JSONArray createBusiOrderAttrs(List<Element> coItemList) {
		// ����busiOrderAttrs
		JSONArray busiOrderAttrs = new JSONArray();
		if (coItemList != null && coItemList.size() > 0) {
			for (Element coItem : coItemList) {
				JSONObject busiOrderAttrJs = new JSONObject();
				Node itemSpecIdNode = coItem.selectSingleNode("./id");
				Node valueNode = coItem.selectSingleNode("./value");
				if (itemSpecIdNode != null && itemSpecIdNode.getText().length() > 0 && valueNode != null
						&& valueNode.getText().length() > 0) {
					busiOrderAttrJs.elementOpt("itemSpecId", itemSpecIdNode.getText());
					busiOrderAttrJs.elementOpt("value", valueNode.getText());
					busiOrderAttrJs.elementOpt("state", "ADD");
					busiOrderAttrJs.elementOpt("statusCd", "S");
					busiOrderAttrs.add(busiOrderAttrJs);
				}
			}
		}
		return busiOrderAttrs;
	}

	/**
	 * ����boProds
	 * 
	 * @return
	 */
	private JSONArray createBoProds(BoSeqCalculator boSeqCalculator) {
		// ����boProds
		JSONArray boProdArrJs = new JSONArray();
		JSONObject boProdJs = new JSONObject();
		boProdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
		boProdJs.elementOpt("state", "ADD");
		boProdJs.elementOpt("statusCd", "S");
		boProdJs.elementOpt("appStartDt", "");
		boProdJs.elementOpt("appEndDt", "");
		boProdJs.elementOpt("startDt", "");
		boProdJs.elementOpt("endDt", "");
		boProdArrJs.add(boProdJs);
		return boProdArrJs;
	}

	/**
	 * ����busiOrderTimes
	 * 
	 * @return
	 */
	private JSONArray createBusiOrderTimes(List<Map<String, String>> orderTimeItemInfo, Long prodId, String prodSpecId,
			BoSeqCalculator boSeqCalculator) {
		// ����boProds
		JSONArray busiOrderTimes = new JSONArray();
		for (int i = 0; i < orderTimeItemInfo.size(); i++) {
			Map<String, String> itemInfo = orderTimeItemInfo.get(i);
			JSONObject busiOrderTime = new JSONObject();
			busiOrderTime.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdString());
			busiOrderTime.elementOpt("state", "ADD");
			busiOrderTime.elementOpt("statusCd", "S");
			busiOrderTime.elementOpt("areaId", "11000");
			busiOrderTime.elementOpt("prodId", prodId);
			busiOrderTime.elementOpt("prodSpecId", prodSpecId);
			busiOrderTime.elementOpt("itemSpecId", itemInfo.get("itemSpecId"));
			busiOrderTime.elementOpt("value", itemInfo.get("value"));
			busiOrderTimes.add(busiOrderTime);
		}
		return busiOrderTimes;
	}

	/**
	 * ����boProdStatuses
	 * 
	 * @return
	 */
	private JSONArray createBoProdStatuses(String prodStatusCd, String action, BoSeqCalculator boSeqCalculator) {
		// ����boProdStatuses
		JSONArray boProdStatusArrJs = new JSONArray();
		JSONObject boProdStatusJs = new JSONObject();
		boProdStatusJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdStatusJs.elementOpt("prodStatusCd", prodStatusCd);
		boProdStatusJs.elementOpt("state", action);
		boProdStatusJs.elementOpt("statusCd", "S");
		boProdStatusArrJs.add(boProdStatusJs);
		return boProdStatusArrJs;
	}

	/**
	 * ����boProdSpecs
	 * 
	 * @return
	 */
	private JSONArray createBoProdSpecs(String newProdSpecId, String oldProdSpecId, BoSeqCalculator boSeqCalculator) {
		// ����boProdSpecs
		JSONArray boProdSpecArrJs = new JSONArray();
		if (newProdSpecId != null) {
			JSONObject newBoProdSpecJs = new JSONObject();
			newBoProdSpecJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProdSpecJs.elementOpt("prodSpecId", newProdSpecId);
			newBoProdSpecJs.elementOpt("state", "ADD");
			newBoProdSpecJs.elementOpt("statusCd", "S");
			boProdSpecArrJs.add(newBoProdSpecJs);
		}
		if (oldProdSpecId != null) {
			JSONObject oldBoProdSpecJs = new JSONObject();
			oldBoProdSpecJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			oldBoProdSpecJs.elementOpt("prodSpecId", oldProdSpecId);
			oldBoProdSpecJs.elementOpt("state", "DEL");
			oldBoProdSpecJs.elementOpt("statusCd", "S");
			boProdSpecArrJs.add(oldBoProdSpecJs);
		}
		return boProdSpecArrJs;
	}

	/**
	 * ����boProdFeeTypes
	 * 
	 * @return
	 */
	private JSONArray createBoProdFeeTypes(String feeType, String action, BoSeqCalculator boSeqCalculator) {
		// ����boProdStatuses
		JSONArray boProdFeeTypeArrJs = new JSONArray();
		JSONObject boProdFeeTypeJs = new JSONObject();
		boProdFeeTypeJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdFeeTypeJs.elementOpt("feeType", feeType);
		boProdFeeTypeJs.elementOpt("state", action);
		boProdFeeTypeJs.elementOpt("statusCd", "S");
		boProdFeeTypeArrJs.add(boProdFeeTypeJs);
		return boProdFeeTypeArrJs;
	}

	/**
	 * ����boCust��json����
	 * 
	 * @param newPartyId
	 * @param oldPartyId
	 * @param partyProductRelaRoleCd
	 * @return
	 */
	private JSONArray createBoCustsJson(String newPartyId, String oldPartyId, String partyProductRelaRoleCd,
			BoSeqCalculator boSeqCalculator) {
		// ����boCusts
		JSONArray boCustArrJs = new JSONArray();

		if (newPartyId != null) {
			JSONObject newBoCustJs = new JSONObject();
			newBoCustJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoCustJs.elementOpt("partyId", newPartyId);
			newBoCustJs.elementOpt("partyProductRelaRoleCd", partyProductRelaRoleCd);
			newBoCustJs.elementOpt("state", "ADD");
			newBoCustJs.elementOpt("statusCd", "S");
			boCustArrJs.add(newBoCustJs);
		}
		if (oldPartyId != null) {
			JSONObject oldBoCustJs = new JSONObject();
			oldBoCustJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			oldBoCustJs.elementOpt("partyId", oldPartyId);
			oldBoCustJs.elementOpt("partyProductRelaRoleCd", partyProductRelaRoleCd);
			oldBoCustJs.elementOpt("state", "DEL");
			oldBoCustJs.elementOpt("statusCd", "S");
			boCustArrJs.add(oldBoCustJs);
		}
		return boCustArrJs;
	}

	/**
	 * ������Ʒ���Լ���ProdItems��json
	 * 
	 * @param prodPropertysNode
	 * @param prodId
	 * @return
	 */
	private JSONArray createProdItemsJson(BoCreateParam boCreateParam, Node prodPropertysNode, Long prodId,
			BoSeqCalculator boSeqCalculator,String channelId) {
		List propertyNodes = prodPropertysNode.selectNodes("./property");
		String prodSpecId = boCreateParam.getProdSpecId();
		// ����boProdItems
		JSONArray boProdItemArrJs = new JSONArray();
		for (Iterator itr = propertyNodes.iterator(); itr.hasNext();) {
			Node propertyNode = (Node) itr.next();
			String itemSpecIdStr = propertyNode.selectSingleNode("./id").getText();
			String nameStr = propertyNode.selectSingleNode("./name").getText();
			String newValueStr = propertyNode.selectSingleNode("./value").getText();
			String actionTypeStr = propertyNode.selectSingleNode("./actionType").getText();
			String propertyType = propertyNode.selectSingleNode("./@propertyType") != null ? propertyNode
					.selectSingleNode("./@propertyType").getText() : null;
			if (propertyType != null && propertyType.equals("co")) {
				// ���ע���Ƕ������ԾͲ�����
				continue;
			}
			if ( itemSpecIdStr.equals("201518") ||itemSpecIdStr.equals("800000011")) {
				// ���ע�������һ֤������ID�Ͳ�����
				continue;
			}
			if (actionTypeStr != null && ("0".equals(actionTypeStr) || "2".equals(actionTypeStr))) {

				JSONObject newBoProdItemJs = new JSONObject();
				newBoProdItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				newBoProdItemJs.elementOpt("itemSpecId", itemSpecIdStr);
				newBoProdItemJs.elementOpt("name", nameStr);
				newBoProdItemJs.elementOpt("value", newValueStr);
				newBoProdItemJs.elementOpt("state", "ADD");
				newBoProdItemJs.elementOpt("statusCd", "S");
				boProdItemArrJs.add(newBoProdItemJs);
			}
			if (actionTypeStr != null && ("1".equals(actionTypeStr) || "2".equals(actionTypeStr))) {
				OfferProdItem offerProdItem = intfSMO.findOfferProdItem(prodId, Long.parseLong(itemSpecIdStr));
				if (offerProdItem != null) {
					JSONObject oldBoProdItemJs = new JSONObject();
					oldBoProdItemJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					oldBoProdItemJs.elementOpt("itemSpecId", itemSpecIdStr);
					oldBoProdItemJs.elementOpt("name", nameStr);
					oldBoProdItemJs.elementOpt("value", offerProdItem.getValue());
					oldBoProdItemJs.elementOpt("state", "DEL");
					oldBoProdItemJs.elementOpt("statusCd", "S");
					boProdItemArrJs.add(oldBoProdItemJs);
				}
			}
		}
		Map<String, Object> param = intfSMO.queryDefaultValueByMainOfferSpecId(boCreateParam.getPsOfferSpecId()
				.toString());
		// ΪPAD�ṩ�����������Ĭ����������
		if (param != null) {
			JSONObject addForItemValue = new JSONObject();
			addForItemValue.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			String speedValue=intfSMO.querySpeedValue(prodSpecId);
			System.out.println("speedValue==="+speedValue);
			if ("10".equals(prodSpecId)) {
				addForItemValue.elementOpt("itemSpecId", "13");
			} else {
				//addForItemValue.elementOpt("itemSpecId", "10");
				addForItemValue.elementOpt("itemSpecId", speedValue);
			}
			//����������������
			addForItemValue.elementOpt("name", "����");
			addForItemValue.elementOpt("value", param.get("VALUE").toString());
			addForItemValue.elementOpt("state", "ADD");
			addForItemValue.elementOpt("statusCd", "S");
			boProdItemArrJs.add(addForItemValue);
		}
		//��ѧУ
		boolean res= intfSMO.checkSchool(channelId);
		if(res){
			JSONObject addForIsSchool = new JSONObject();
			addForIsSchool.elementOpt("itemSpecId", "2015401");
			addForIsSchool.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			addForIsSchool.elementOpt("name", "����-У԰");
			addForIsSchool.elementOpt("value", "1");
			addForIsSchool.elementOpt("state", "ADD");
			addForIsSchool.elementOpt("statusCd", "S");
			boProdItemArrJs.add(addForIsSchool);
		}
		return boProdItemArrJs;
	}
	/**
	 * ������֤��Ʒ���Լ��� Users ��json
	 * 
	 * @param prodPropertysNode
	 * @param prodId
	 * @return
	 */
	private JSONArray createboUsersJson(BoCreateParam boCreateParam, Node prodPropertysNode, Long prodId,
			BoSeqCalculator boSeqCalculator,String channelId) {
		List propertyNodes = prodPropertysNode.selectNodes("./property");
		String prodSpecId = boCreateParam.getProdSpecId();
		// ����boProdItems
		JSONArray boCustJsArrJs = new JSONArray();
		for (Iterator itr = propertyNodes.iterator(); itr.hasNext();) {
			Node propertyNode = (Node) itr.next();
			String itemSpecIdStr = propertyNode.selectSingleNode("./id").getText();
			String nameStr = propertyNode.selectSingleNode("./name").getText();
			String newValueStr = propertyNode.selectSingleNode("./value").getText();
			String actionTypeStr = propertyNode.selectSingleNode("./actionType").getText();
			String propertyType = propertyNode.selectSingleNode("./@propertyType") != null ? propertyNode
					.selectSingleNode("./@propertyType").getText() : null;
					if (propertyType != null && propertyType.equals("co")) {
						// ���ע���Ƕ������ԾͲ�����
						continue;
					}
					
					if (itemSpecIdStr.equals("201518") ||itemSpecIdStr.equals("800000011")) {
						JSONObject newBoCustJs = new JSONObject();
						newBoCustJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
						newBoCustJs.elementOpt("partyId", newValueStr);
						newBoCustJs.elementOpt("partyProductRelaRoleCd", "4");
						newBoCustJs.elementOpt("state", "ADD");
						newBoCustJs.elementOpt("statusCd", "S");
						boCustJsArrJs.add(newBoCustJs);
				}
		}
		return boCustJsArrJs;
	}

	/**
	 * ����boProdPasswords�ļ���
	 * 
	 * @param newPwd
	 * @param oldPwd
	 * @param prodPwTypeCd
	 * @return
	 */
	private JSONArray createBoProdPasswordsJson(String newPwd, String oldPwd, String prodPwTypeCd,
			BoSeqCalculator boSeqCalculator) {
		// ����boProdPasswords
		JSONArray boProdPasswordArrJs = new JSONArray();
		if (newPwd != null) {
			JSONObject newBoProdPasswordJs = new JSONObject();
			newBoProdPasswordJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProdPasswordJs.elementOpt("prodPwTypeCd", prodPwTypeCd);
			newBoProdPasswordJs.elementOpt("pwd", newPwd);
			newBoProdPasswordJs.elementOpt("state", "ADD");
			newBoProdPasswordJs.elementOpt("statusCd", "S");
			boProdPasswordArrJs.add(newBoProdPasswordJs);
		}
		JSONObject oldBoProdPasswordJs = new JSONObject();
		oldBoProdPasswordJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		oldBoProdPasswordJs.elementOpt("prodPwTypeCd", "1");
		oldBoProdPasswordJs.elementOpt("pwd", "000000");
		oldBoProdPasswordJs.elementOpt("state", "ADD");
		oldBoProdPasswordJs.elementOpt("statusCd", "S");
		boProdPasswordArrJs.add(oldBoProdPasswordJs);
		return boProdPasswordArrJs;
	}

	/**
	 * ����boProd2Tds��json����
	 * 
	 * @param tdsNode
	 * @param prodId
	 * @return
	 */
	private JSONArray createboProd2TdsJson(Node tdsNode, Long prodId, BoSeqCalculator boSeqCalculator) {
		List tdNodes = tdsNode.selectNodes("./td");
		// ����boProd2Tds
		JSONArray boProd2TdArrJs = new JSONArray();
		for (Iterator itr = tdNodes.iterator(); itr.hasNext();) {
			Node tdNode = (Node) itr.next();
			Node terminalCodeNode = tdNode.selectSingleNode("./terminalCode");
			Node terminalDevSpecIdNode = tdNode.selectSingleNode("./terminalDevSpecId");
			Node terminalDevIdNode = tdNode.selectSingleNode("./terminalDevId");
			Node devModelIdNode = tdNode.selectSingleNode("./devModelId");
			Node ownerTypeCdNode = tdNode.selectSingleNode("./ownerTypeCd");
			Node maintainTypeCdNode = tdNode.selectSingleNode("./maintainTypeCd");

			String terminalDevSpecIdStr = (null != terminalDevSpecIdNode ? terminalDevSpecIdNode.getText() : "");
			String newTerminalCodeStr = (null != terminalCodeNode ? terminalCodeNode.getText() : "");
			String newTerminalDevIdStr = (null != terminalDevIdNode ? terminalDevIdNode.getText() : "");
			String newDevModelIdStr = (null != devModelIdNode ? devModelIdNode.getText() : "");
			String newOwnerTypeCdStr = (null != ownerTypeCdNode ? ownerTypeCdNode.getText() : "");
			String newMaintainTypeCdStr = (null != maintainTypeCdNode ? maintainTypeCdNode.getText() : "");
			String devNumId = WSUtil.getXmlNodeText(tdNode, "./devNumId");
			if (StringUtils.isBlank(devNumId)) {
				throw new BmoException(-1, "����Ϣ��ȫdevNumIdֵΪ��");
			}

			JSONObject newBoProd2TdJs = new JSONObject();
			newBoProd2TdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2TdJs.elementOpt("terminalDevSpecId", terminalDevSpecIdStr);
			newBoProd2TdJs.elementOpt("maintainTypeCd", newMaintainTypeCdStr);
			newBoProd2TdJs.elementOpt("ownerTypeCd", newOwnerTypeCdStr);
			newBoProd2TdJs.elementOpt("terminalDevId", newTerminalDevIdStr);
			newBoProd2TdJs.elementOpt("terminalCode", newTerminalCodeStr);
			newBoProd2TdJs.elementOpt("deviceModelId", newDevModelIdStr);
			newBoProd2TdJs.elementOpt("state", "ADD");
			newBoProd2TdJs.elementOpt("statusCd", "S");
			boProd2TdArrJs.add(newBoProd2TdJs);

			if (prodId != null) {

				OfferProd2Td offerProd2Td = intfSMO.findOfferProd2Td(prodId, Long.parseLong(terminalDevSpecIdStr));
				if (offerProd2Td != null) {
					String oldTerminalCodeStr = (null != offerProd2Td.getTerminalCode() ? offerProd2Td
							.getTerminalCode().trim() : "");
					String oldTerminalDevIdStr = (null != offerProd2Td.getTerminalDevId() ? offerProd2Td
							.getTerminalDevId().toString() : "");
					String oldDevModelIdStr = (null != offerProd2Td.getDeviceModelId() ? offerProd2Td
							.getDeviceModelId().toString() : "");
					String oldOwnerTypeCdStr = (null != offerProd2Td.getOwnerTypeCd() ? offerProd2Td.getOwnerTypeCd()
							.toString() : "");
					String oldMaintainTypeCdStr = (null != offerProd2Td.getMaintainTypeCd() ? offerProd2Td
							.getMaintainTypeCd().toString() : "");

					JSONObject oldBoProd2TdJs = new JSONObject();
					oldBoProd2TdJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
					oldBoProd2TdJs.elementOpt("terminalDevSpecId", terminalDevSpecIdStr);
					oldBoProd2TdJs.elementOpt("maintainTypeCd", oldMaintainTypeCdStr);
					oldBoProd2TdJs.elementOpt("ownerTypeCd", oldOwnerTypeCdStr);
					oldBoProd2TdJs.elementOpt("terminalDevId", oldTerminalDevIdStr);
					oldBoProd2TdJs.elementOpt("terminalCode", oldTerminalCodeStr);
					oldBoProd2TdJs.elementOpt("deviceModelId", oldDevModelIdStr);
					oldBoProd2TdJs.elementOpt("state", "DEL");
					oldBoProd2TdJs.elementOpt("statusCd", "S");
					boProd2TdArrJs.add(oldBoProd2TdJs);
				}
			}
		}

		return boProd2TdArrJs;
	}

	private JSONArray createboProd2CouponsJson(Node couponsNode, Long prodId, Long offerId, Long partyId,
			BoSeqCalculator boSeqCalculator) {
		// ����boProd2Coupons
		JSONArray boProd2CouponArrJs = new JSONArray();
		if (couponsNode == null) {
			return boProd2CouponArrJs;
		}
		List couponNodes = couponsNode.selectNodes("./coupon");
		for (Iterator itr = couponNodes.iterator(); itr.hasNext();) {
			Node couponNode = (Node) itr.next();
			Node bcdCodeNode = couponNode.selectSingleNode("./couponcode");
			Node countNode = couponNode.selectSingleNode("./count");
			String bcdCodeStr = (null != bcdCodeNode ? bcdCodeNode.getText() : "");
			String materialIdStr = "";
			String storeIdStr = "";
			String storeNameStr = "";
			String chargeItemCdStr = CrmServiceManagerConstants.CHARGE_ITEM_CD_GJF;// �����ѷ��ñ���
			String couponChargeStr = "";
			String countStr = (null != countNode ? countNode.getText() : "");
			// ���ҹ�������Ϣ
			// �˴���ָ�
			String agentId = "";
			String agentName = "";
			try {
				String materialInfo = soStoreSMO.getMaterialByCode(bcdCodeStr);
				JSONObject couponJson = null;
				logger.debug("��ƷУ��xml��ʽ:{}", materialInfo);
				if (materialInfo == null || materialInfo.equals("-1")) {
					logger.debug("������Ʒ��Ϣʧ�ܣ�bcdcode={}", bcdCodeStr);
					throw new BmoException(-1, "������Ʒ��Ϣʧ��!bcdcode=" + bcdCodeStr);
				}
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON json = xmlSerializer.read(materialInfo);
				couponJson = JSONObject.fromObject(json);
				JSONObject goodsDetail = couponJson.getJSONObject("GoodsDetail");
				agentId = goodsDetail.getString("productorId");
				agentName = goodsDetail.getString("productorName");
				storeIdStr = goodsDetail.getString("storeId");
				materialIdStr = goodsDetail.getString("materialId");
				storeNameStr = goodsDetail.getString("storeName");
				couponChargeStr = goodsDetail.getString("referPrice");
			} catch (Exception e) {
				logger.debug("������Ʒ��Ϣʧ�ܣ�bcdcode={},{}", bcdCodeStr, e);
				throw new BmoException(-1, "������Ʒ��Ϣʧ��!bcdcode=" + bcdCodeStr);
			}
			JSONObject newBoProd2couponJs = new JSONObject();
			newBoProd2couponJs.elementOpt("id", "-1");
			newBoProd2couponJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2couponJs.elementOpt("isTransfer", "Y");
			newBoProd2couponJs.elementOpt("inOutReasonId", "1");
			newBoProd2couponJs.elementOpt("saleId", "1");// ǰ̨
			newBoProd2couponJs.elementOpt("couponId", materialIdStr);
			newBoProd2couponJs.elementOpt("couponinfoStatusCd", "A");// ����
			newBoProd2couponJs.elementOpt("couponUsageTypeCd", "3");// ����
			newBoProd2couponJs.elementOpt("inOutTypeId", "1");
			newBoProd2couponJs.elementOpt("chargeItemCd", chargeItemCdStr);
			newBoProd2couponJs.elementOpt("couponNum", countStr);
			newBoProd2couponJs.elementOpt("storeId", storeIdStr);
			newBoProd2couponJs.elementOpt("storeName", storeNameStr);
			newBoProd2couponJs.elementOpt("agentId", agentId);
			newBoProd2couponJs.elementOpt("agentName", agentName);
			newBoProd2couponJs.elementOpt("apCharge", couponChargeStr);
			newBoProd2couponJs.elementOpt("couponInstanceNumber", bcdCodeStr);
			newBoProd2couponJs.elementOpt("ruleId", null);
			newBoProd2couponJs.elementOpt("description", null);
			newBoProd2couponJs.elementOpt("partyId", partyId);
			newBoProd2couponJs.elementOpt("prodId", prodId);
			newBoProd2couponJs.elementOpt("offerId", offerId);
			newBoProd2couponJs.elementOpt("state", CommonDomain.ADD);
			boProd2CouponArrJs.add(newBoProd2couponJs);

		}
		return boProd2CouponArrJs;
	}

	private JSONArray createbo2Coupon(Node tdsNode, Long prodId, Long offerId, Long partyId,
			BoSeqCalculator boSeqCalculator) {
		// ����boProd2Coupons
		JSONArray boProd2CouponArrJs = new JSONArray();
		List tdNodes = tdsNode.selectNodes("./td");
		for (Iterator itr = tdNodes.iterator(); itr.hasNext();) {
			Node couponNode = (Node) itr.next();
			Node bcdCodeNode = couponNode.selectSingleNode("./terminalCode");
			String bcdCodeStr = (null != bcdCodeNode ? bcdCodeNode.getText() : "");
			String materialIdStr = "";
			String storeIdStr = "";
			String storeNameStr = "";
			String chargeItemCdStr = CrmServiceManagerConstants.CHARGE_ITEM_CD_UIM;// UIM�����ù��ID
			String couponChargeStr = "";
			String countStr = "1";
			// ���ҹ�������Ϣ
			// �˴���ָ�
			String agentId = "";
			String agentName = "";
			String status = "";
			try {
				String materialInfo = soStoreSMO.getMaterialByCode(bcdCodeStr);
				JSONObject couponJson = null;
				logger.debug("��ƷУ��xml��ʽ:{}", materialInfo);
				if (materialInfo == null || materialInfo.equals("-1")) {
					logger.debug("���ҿ���Ϣʧ�ܣ�terminalCode={}", bcdCodeStr);
					throw new BmoException(-1, "���ҿ���Ϣʧ��!terminalCode=" + bcdCodeStr);
				}
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON json = xmlSerializer.read(materialInfo);
				couponJson = JSONObject.fromObject(json);
				JSONObject goodsDetail = couponJson.getJSONObject("GoodsDetail");

				status = goodsDetail.getString("status");
				if (!"A".equals(status)) {
					throw new BmoException(-1, "����������״̬!terminalCode=" + bcdCodeStr);
				}
				agentId = goodsDetail.getString("productorId");
				agentName = goodsDetail.getString("productorName");
				storeIdStr = goodsDetail.getString("storeId");
				materialIdStr = goodsDetail.getString("materialId");
				storeNameStr = goodsDetail.getString("storeName");
				couponChargeStr = goodsDetail.getString("referPrice");

			} catch (Exception e) {
				logger.debug("���ҿ���Ϣʧ�ܣ�terminalCode={},{}", bcdCodeStr, e);
				throw new BmoException(-1, "���ҿ���Ϣʧ��!terminalCode=" + bcdCodeStr);
			}
			JSONObject newBoProd2couponJs = new JSONObject();
			newBoProd2couponJs.elementOpt("id", "-1");
			newBoProd2couponJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			newBoProd2couponJs.elementOpt("isTransfer", "Y");
			newBoProd2couponJs.elementOpt("inOutReasonId", "1");
			newBoProd2couponJs.elementOpt("saleId", "1");// ǰ̨
			newBoProd2couponJs.elementOpt("couponId", materialIdStr);
			newBoProd2couponJs.elementOpt("couponinfoStatusCd", "A");// ����
			newBoProd2couponJs.elementOpt("couponUsageTypeCd", "3");// ����
			newBoProd2couponJs.elementOpt("inOutTypeId", "1");
			newBoProd2couponJs.elementOpt("chargeItemCd", chargeItemCdStr);
			newBoProd2couponJs.elementOpt("couponNum", countStr);
			newBoProd2couponJs.elementOpt("storeId", storeIdStr);
			newBoProd2couponJs.elementOpt("storeName", storeNameStr);
			newBoProd2couponJs.elementOpt("agentId", agentId);
			newBoProd2couponJs.elementOpt("agentName", agentName);
			newBoProd2couponJs.elementOpt("apCharge", couponChargeStr);
			newBoProd2couponJs.elementOpt("couponInstanceNumber", bcdCodeStr);
			newBoProd2couponJs.elementOpt("ruleId", null);
			newBoProd2couponJs.elementOpt("description", null);
			newBoProd2couponJs.elementOpt("partyId", partyId);
			newBoProd2couponJs.elementOpt("prodId", prodId);
			newBoProd2couponJs.elementOpt("offerId", offerId);
			newBoProd2couponJs.elementOpt("state", CommonDomain.ADD);
			boProd2CouponArrJs.add(newBoProd2couponJs);

		}
		return boProd2CouponArrJs;
	}

	/**
	 * ����boProdAns
	 * 
	 * @return
	 */
	private JSONArray createBoProdAns(Node accessNumberNode, Node anIdNode, String prodSpecId, String areaId,
			String partyId, BoSeqCalculator boSeqCalculator) {
		// ����boProdAns
		JSONArray boProdAnArrJs = new JSONArray();
		String accessNumberStr = accessNumberNode.getText();
		String anIdStr = null;
		ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType(Long.parseLong(prodSpecId));
		boolean needVali = false;// �Ƿ���Ҫ��ԴУ��
		// У���������һ�Ǹ��ݲ�Ʒ���������ҵ��Ҫ�����У�飬���ж��Ƿ���Ҫ��ԴУ��
		if (Integer.valueOf(prodSpecId).intValue() == CommonDomain.PROD_SPEC_GR118114) {
			// ����ͨ������Ľ��������Ҫ����У��
			needVali = true;
		} else if (Integer.valueOf(prodSpecId).intValue() == CommonDomain.PROD_SPEC_YXZC_QQ) {
			needVali = true;
		} else if (accessNumberStr == null || accessNumberStr.trim().equals("")) {
			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<root>");
			xml.append("<AccessNumberInfo>");
			xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
			xml.append("<areaId>").append(areaId).append("</areaId>");
			xml.append("</AccessNumberInfo>");
			xml.append("</root>");
			try {
				List list = rscServiceSMO.assignAn(xml.toString());
				if (list != null && list.size() > 0) {
					Map anMap = (Map) list.get(0);
					accessNumberStr = anMap.get("name").toString();
					anIdStr = anMap.get("anId").toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (anIdNode != null) {
				anIdStr = anIdNode.getText();
				if (anIdStr == null && "".equals(anIdStr.trim())) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30008, "������ŵ�anId��ȡʧ��!");
				}
			} else {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30008, "������ŵ�anId��ȡʧ��!");
			}
		}

		// �˴���������ŵ���ԴУ��
		if (needVali) {
			try {
				StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				xml.append("<root>");
				xml.append("<AccessNumberInfo>");
				xml.append("<canChangePwd>").append(prodSpec2AccessNumType.getNeedPw()).append("</canChangePwd>");
				xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
				xml.append("<rscStatusCd>").append(CommonDomain.RSC_STATUS_CD_TEMP_PRE_SAVE).append("</rscStatusCd>");
				xml.append("<areaId>").append(areaId).append("</areaId>");
				xml.append("<name>").append(accessNumberStr).append("</name>");
				xml.append("<password>1</password>");
				xml.append("</AccessNumberInfo>");
				xml.append("</root>");

				List list = rscServiceSMO.addAn(xml.toString());
				if (list == null) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "������ԴУ��ʧ��!");
				}

				Map m = (Map) list.get(0);
				String r = null;
				String c = null;
				String id = null;
				if (m.get("result") == null) {
					if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
						throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "������ԴУ��ʧ��!");
					} else {
						r = "1";
						c = m.get("name").toString();
						id = m.get("anId").toString();
					}
				} else if ("-1".equals(m.get("result").toString())) {
					if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
						throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "������ԴУ��ʧ��!");
					} else {
						r = m.get("result").toString();
						c = m.get("cause").toString();
						id = m.get("anId").toString();
					}
				} else {
					r = m.get("result").toString();
					c = m.get("cause").toString();
					id = m.get("anId").toString();
				}
				anIdStr = id;
			} catch (Exception e) {
				throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "������ԴУ��ʧ��!");
			}
		}

		JSONObject boProdAnJs = new JSONObject();
		boProdAnJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdAnJs.elementOpt("anId", anIdStr);
		boProdAnJs.elementOpt("anTypeCd", prodSpec2AccessNumType.getAnTypeCd());
		boProdAnJs.elementOpt("accessNumber", accessNumberStr);
		boProdAnJs.elementOpt("state", "ADD");
		boProdAnJs.elementOpt("statusCd", "S");
		boProdAnArrJs.add(boProdAnJs);

		return boProdAnArrJs;
	}

	/**
	 * ����boProd2Ans
	 * 
	 * @return
	 */
	private JSONArray createBoProd2Ans(String prodSpecId, String areaId, Node prod2accNbrNode, Node anId2Node,
			Node passwordNode, BoSeqCalculator boSeqCalculator) {
		// ����boProd2Ans
		JSONArray boProd2AnArrJs = new JSONArray();
		String prod2accNbr = prod2accNbrNode.getText();
		String password = "";
		if (passwordNode != null) {
			password = passwordNode.getText();
		}
		String anId = null;
		Integer anTypeCd = 307;
		Integer reasonCd = 2;
		if (prod2accNbr != null && !"".equals(prod2accNbr.trim())) {
			ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType2(Long
					.parseLong(prodSpecId));
			if (anId2Node != null && anId2Node.getText() != null && !"".equals(anId2Node.getText().trim())) {
				anId = anId2Node.getText().trim();
			} else {
				try {
					StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					xml.append("<root>");
					xml.append("<AccessNumberInfo>");
					xml.append("<canChangePwd>Y</canChangePwd>");
					xml.append("<anTypeCd>").append(prodSpec2AccessNumType.getAnTypeCd()).append("</anTypeCd>");
					xml.append("<rscStatusCd>0</rscStatusCd>");
					xml.append("<areaId>").append(areaId).append("</areaId>");
					xml.append("<name>").append(prod2accNbr.trim()).append("</name>");
					xml.append("<password>").append(password).append("</password>");
					xml.append("</AccessNumberInfo>");
					xml.append("</root>");

					List rusultList = rscServiceSMO.addAn(xml.toString());
					Map m = (Map) rusultList.get(0);
					String r = null;
					String c = null;
					String id = null;
					logger.debug("��Դ���ؽ����result={}��anId={},name={},cause={}", new Object[] { m.get("result"),
							m.get("anId"), m.get("name"), m.get("cause") });
					if (m.get("result") == null) {
						if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
							throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "�����������У��ʧ��!");
						} else {
							r = "1";
							c = m.get("name").toString();
							id = m.get("anId").toString();
						}
					} else if ("-1".equals(m.get("result").toString())) {
						if (m.get("anId") == null || m.get("anId").toString().equals("-1")) {
							c = m.get("cause") != null ? m.get("cause").toString() : "";
							throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "�����������У��ʧ��!" + c);
						} else {
							r = m.get("result").toString();
							c = m.get("cause").toString();
							id = m.get("anId").toString();
						}
					} else {
						r = m.get("result").toString();
						c = m.get("cause").toString();
						id = m.get("anId").toString();
					}
					anId = id;

				} catch (Exception e) {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30009, "�����������У��ʧ��!", e);
				}
			}

			JSONObject boProd2AnJs = new JSONObject();
			boProd2AnJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boProd2AnJs.elementOpt("anId", anId);
			boProd2AnJs.elementOpt("anTypeCd", anTypeCd);
			boProd2AnJs.elementOpt("accessNumber", prod2accNbr);
			boProd2AnJs.elementOpt("reasonCd", reasonCd);
			boProd2AnJs.elementOpt("state", "ADD");
			boProd2AnJs.elementOpt("statusCd", "S");
			boProd2AnArrJs.add(boProd2AnJs);
		}

		return boProd2AnArrJs;
	}

	/**
	 * �����ʻ�������ϵ������Ϣ
	 * 
	 * @param acctCdNode
	 * @param bindPayNumberNode
	 * @param bindNumberProdSpec
	 * @param boCreateParam
	 * @return
	 */
	private JSONArray processAccountRela(Node acctCdNode, Node bindPayNumberNode, Node bindNumberProdSpec,
			BoSeqCalculator boSeqCalculator, BoCreateParam boCreateParam) {
		String acctId = null;
		Account account = null;
		if (acctCdNode != null) {
			String acctCdStr = acctCdNode.getText();
			if (boCreateParam.isNeedCreateAcct()) {
				// �ʻ���������,����ȥ���ݿ��в���,ֱ��ʹ��Ԥ���ɵ��ʻ�Id
				acctId = boCreateParam.getAcctId().toString();
			} else {
				account = intfSMO.findAcctByAcctCd(acctCdStr);
				if (account != null) {
					acctId = account.getAcctId().toString();
				} else {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30020, "�������ʻ�����Ϊ" + acctCdStr
							+ "���ʻ������飡");
				}
			}
		} else if (bindPayNumberNode != null && bindNumberProdSpec != null) {
			String bindPayNumberNodeStr = bindPayNumberNode.getText();
			if (bindPayNumberNodeStr != null && !bindPayNumberNodeStr.trim().equals("")) {
				Integer bindNumberProdSpecStr = Integer.valueOf(bindNumberProdSpec.getText());
				account = intfSMO.findAcctByAccNbr(bindPayNumberNodeStr, bindNumberProdSpecStr);
				if (account != null) {
					acctId = account.getAcctId().toString();
				} else {
					throw new BmoException(CrmServiceErrorCode.CRMSERVICE_ERROR_30021, "��Ʒ���[" + bindNumberProdSpecStr
							+ "]����[" + bindPayNumberNodeStr + "]�Ҳ����ʻ�����ȷ������ĸ��Ѻ���Ͳ�Ʒ���");
				}
			}
		} else if (acctCdNode == null && bindPayNumberNode == null && bindNumberProdSpec == null) {
			acctId = boCreateParam.getAcctId().toString();
		}
		JSONArray boAccountRelaArrJs = createBoAccountRelas(acctId, boSeqCalculator, "ADD");
		return boAccountRelaArrJs;
	}

	/**
	 * �����Ʒ������ϵ
	 * 
	 * @param relaAccessNumber
	 * @param relaProdSpecId
	 * @param reasonCd
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processProdRela(String relaAccessNumber, Integer relaProdSpecId, Integer reasonCd, String state,
			BoSeqCalculator boSeqCalculator) {
		OfferProd offerProd = intfSMO.getProdByAccessNumber(relaAccessNumber);
		if (offerProd != null) {
			return processProdRela(offerProd.getProdId(), reasonCd, state, boSeqCalculator);
		}
		return null;
	}

	/**
	 * �����Ʒ������ϵ
	 * 
	 * @param relaProdId
	 * @param reasonCd
	 * @param state
	 * @param boSeqCalculator
	 * @return
	 */
	private JSONObject processProdRela(Long relaProdId, Integer reasonCd, String state, BoSeqCalculator boSeqCalculator) {
		JSONObject boProdRelaJs = new JSONObject();
		boProdRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boProdRelaJs.elementOpt("reasonCd", reasonCd);
		boProdRelaJs.elementOpt("relatedProdId", relaProdId);
		boProdRelaJs.elementOpt("state", state);
		boProdRelaJs.elementOpt("statusCd", "S");
		return boProdRelaJs;
	}

	/**
	 * �����ʻ�������ϵ������Ϣ
	 * 
	 * @param acctId
	 * @return
	 */
	private JSONArray createBoAccountRelas(String acctId, BoSeqCalculator boSeqCalculator, String state) {
		// ����boAccountRelas
		JSONArray boAccountRelaArrJs = new JSONArray();

		JSONObject boAccountRelaJs = new JSONObject();
		boAccountRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boAccountRelaJs.elementOpt("acctId", acctId);
		boAccountRelaJs.elementOpt("chargeItemCd", "0");
		boAccountRelaJs.elementOpt("percent", "100");
		boAccountRelaJs.elementOpt("priority", "1");
		boAccountRelaJs.elementOpt("state", state);
		boAccountRelaJs.elementOpt("statusCd", "S");
		boAccountRelaArrJs.add(boAccountRelaJs);

		return boAccountRelaArrJs;
	}

	/**
	 * ��ʽ�������е�ʱ��
	 * 
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	private void formatOrderDate(Node order) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-mm-dd");
		// �޸Ŀ�ʼ���ڸ�ʽ
		List<Element> startDateElementList = order.selectNodes("//startDt");
		for (Element date : startDateElementList) {
			if (StringUtils.isNotBlank(date.getText())) {
				try {
					date.setText(format2.format(format1.parse(date.getText().trim())));
				} catch (ParseException e) {
					logger.debug("���ڸ�ʽ����dateString={}", date.getText());
					throw new BmoException(-1, "���ڸ�ʽ����dateString={}");
				}
			}
		}

		List<Element> endDtList = order.selectNodes("//endDt");
		for (Element date : endDtList) {
			if (StringUtils.isNotBlank(date.getText())) {
				try {
					date.setText(format2.format(format1.parse(date.getText().trim())));
				} catch (ParseException e) {
					logger.debug("���ڸ�ʽ����dateString={}", date.getText());
					throw new BmoException(-1, "���ڸ�ʽ����dateString={}");
				}
			}
		}

	}

	// �����ʺ� bo_prod_rela
	private JSONArray createBoProdRelas(String bindFixedLine, BoSeqCalculator boSeqCalculator, String bindFixedLineIsNew) {
		// ����boProdRelas
		JSONArray BoProdRelasJs = new JSONArray();
		JSONObject BoProdRelaJs = new JSONObject();
		// ���ݺ�����Ҳ�ƷID
		Map<String, Object> offerProdInfo = intfSMO.queryOfferProdInfoByAccessNumber(bindFixedLine);
		//������Ǿɵĺ���ŷ���  &&"N".equals(bindFixedLineIsNew)
		if (offerProdInfo == null) {
			throw new BmoException(-1, "���������Ч");
		}
		BoProdRelaJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		BoProdRelaJs.elementOpt("relatedProdId", offerProdInfo.get("PRODID"));
		BoProdRelaJs.elementOpt("reasonCd", "6");
		BoProdRelaJs.elementOpt("state", "ADD");
		BoProdRelaJs.elementOpt("statusCd", "S");
		BoProdRelasJs.add(BoProdRelaJs);

		return BoProdRelasJs;
	}

	// �жϲ�Ʒ�Ƿ�Ϊ���в�Ʒ
	private boolean isNewOrder(String prodId) {
		String accessNumber = intfSMO.getAccessNumberByProdId(Long.valueOf(prodId));
		if (StringUtils.isBlank(accessNumber)) {
			return true;
		} else {
			return false;
		}
	}

	// ����boProdTmls ����
	private JSONArray createBoPordTmls(String tmlName, String tmlId, BoSeqCalculator boSeqCalculator) {

		JSONArray boPordTmls = new JSONArray();
		JSONObject boPordTml = new JSONObject();

		boPordTml.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boPordTml.elementOpt("reasonCd", "1");
		boPordTml.elementOpt("tmlName", tmlName);
		boPordTml.elementOpt("tmlId", tmlId);
		boPordTml.elementOpt("state", "ADD");
		boPordTml.elementOpt("statusCd", "S");
		boPordTmls.add(boPordTml);
		return boPordTmls;
	}

	// ��������BO_PROD_ADDRESSʱ ��װ��ַ
	private JSONArray createBoPordAddress(String addrId, String addrStd, String addrDetail,
			BoSeqCalculator boSeqCalculator) {

		JSONArray boPordAddresses = new JSONArray();
		JSONObject boPordAddress = new JSONObject();

		boPordAddress.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boPordAddress.elementOpt("state", "ADD");
		boPordAddress.elementOpt("areaId", "11000");
		boPordAddress.elementOpt("statusCd", "S");
		boPordAddress.elementOpt("addrId", addrId);
		boPordAddress.elementOpt("addrStd", addrStd);
		boPordAddress.elementOpt("addrDetail", addrDetail);
		boPordAddress.elementOpt("neighborNbr", "");
		boPordAddress.elementOpt("reasonCd", "1");
		boPordAddresses.add(boPordAddress);
		return boPordAddresses;
	}

	// ����BO_PROD_ACCESS_ACCOUNT,����˻�����
	private JSONArray createBoProdAccessAccounts(String loginName, String loginPassword, BoSeqCalculator boSeqCalculator) {

		JSONArray boPordAddresses = new JSONArray();
		JSONObject boPordAddress = new JSONObject();

		boPordAddress.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boPordAddress.elementOpt("statusCd", "S");
		boPordAddress.elementOpt("state", "ADD");
		boPordAddress.elementOpt("reasonCd", "2");
		boPordAddress.elementOpt("loginName", loginName);
		boPordAddress.elementOpt("loginPassword", loginPassword);
		boPordAddresses.add(boPordAddress);
		return boPordAddresses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject offerProdItemChangeMsg(Document doc) throws Exception {
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		String staffId = WSUtil.getXmlNodeText(doc, "//request/staffId");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
		Integer prodSpecId = 0;
		Long partyId = 0L;
		Long prodId = 0L;

		// ���ݽ���Ų��Ҳ�Ʒ��Ϣ
		OfferProd prod = intfSMO.getProdByAccessNumber(accessNumber);

		if (prod != null) {
			partyId = prod.getPartyId();
			prodSpecId = prod.getProdSpecId();
			prodId = prod.getProdId();
		} else {
			logger.debug("���ݽ����δ��ѯ����Ʒ��Ϣ" + accessNumber);
			throw new Exception("���ݽ����δ��ѯ����Ʒ��Ϣ" + accessNumber);
		}
		Party party = custFacade.getPartyByAccessNumber(accessNumber);
		if (null == party) {
			logger.debug("���ݽ����δ��ѯ���ͻ���Ϣ:" + accessNumber);
			throw new Exception("���ݽ����δ��ѯ���ͻ���Ϣ:" + accessNumber);
		}

		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();

		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("areaId", areaId);
		orderListInfoatt.elementOpt("areaName", "����");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		//��ȷ��
		//		orderListInfoatt.elementOpt("channelManageCode", "3206000000");

		JSONArray co = new JSONArray();

		JSONObject busiOrderatt = new JSONObject();

		JSONArray b0array = new JSONArray();
		JSONObject b0 = new JSONObject();

		JSONObject busiObjatt = new JSONObject();

		busiObjatt.elementOpt("objId", prodSpecId);
		busiObjatt.elementOpt("name", prodSpecId);
		List<OfferIntf> queryOfferInstByProdId = intfSMO.queryOfferInstByProdId(prod.getProdId());
		if (queryOfferInstByProdId != null && queryOfferInstByProdId.size() > 0)
			busiObjatt.elementOpt("name", queryOfferInstByProdId.get(0).getOfferSpecName());
		busiObjatt.elementOpt("instId", prod.getProdId());
		busiObjatt.elementOpt("accessNumber", accessNumber);
		busiObjatt.elementOpt("isComp", "N");

		JSONObject busiOrderInfoatt = new JSONObject();
		busiOrderInfoatt.elementOpt("seq", -1);
		busiOrderInfoatt.elementOpt("statusCd", "S");

		JSONObject boActionTypeatt = new JSONObject();
		boActionTypeatt.elementOpt("actionClassCd", 4);
		boActionTypeatt.elementOpt("boActionTypeCd", "1179");
		boActionTypeatt.elementOpt("name", "�޸Ĳ�Ʒ����");

		JSONObject boCusts = new JSONObject();
		JSONArray boCustsarr = new JSONArray();
		
		
		//ƴ�Ӳ�Ʒ���Խڵ�
		Map<String,Object> param = new HashMap<String, Object>();
		Node prodItemsNode = doc.selectSingleNode("request/prodItems");
		List<Node> itemNodes = prodItemsNode.selectNodes("./prodItem");
		if(itemNodes == null || itemNodes.size() == 0){
			throw new Exception("����Ľڵ����Դ������飡");
		}
		
		for(Node item : itemNodes){
			param.clear();
			String itemSpecId =  item.selectSingleNode("./itemSpecId").getText();
			String itemValue =  item.selectSingleNode("./itemValue").getText();
			String actionType = item.selectSingleNode("./actionType").getText();
			String old_item_value = null;
			String item_spec_name = null;
			
			
			if(itemSpecId == null || itemValue == null){
				throw new Exception("����Ľڵ�������飡");
			}
			param.put("prodId", prodId);
			param.put("itemSpecId", itemSpecId);
			List<Map<String,Object>> resultMap = intfSMO.getProdItemsByParam(param);
			
			if(null != resultMap && resultMap.size() > 0){
				Map<String, Object> map = resultMap.get(0);
				old_item_value = map.get("ITEM_VALUE")+"";
				item_spec_name = map.get("ITEM_SPEC_NAME")+"";
			}
			
			if("1".equals(actionType)){
				if(itemValue.equals(old_item_value)){
					throw new Exception("�޸ĵ�����ֵ��ԭ������ֵ��ͬ�����飡");
				}
			}
			
			
			JSONObject b1 = new JSONObject();
			//b1.elementOpt("atomActionId", "-1");
			b1.elementOpt("itemSpecId",itemSpecId );
			b1.elementOpt("name", item_spec_name);
			b1.elementOpt("value", itemValue);
			b1.elementOpt("state", "ADD");
			b1.element("statusCd", "S");
			boCustsarr.add(b1);
			
			if(null != item_spec_name){
				JSONObject b2 = new JSONObject();
				//b2.elementOpt("atomActionId", "-2");
				b2.elementOpt("itemSpecId", itemSpecId);
				b2.elementOpt("name", item_spec_name);
				b2.elementOpt("value", old_item_value);
				b2.elementOpt("state", "DEL");
				b2.element("statusCd", "S");
				boCustsarr.add(b2);
			}
			
		}
		
		boCusts.elementOpt("boProdItems", boCustsarr);

		b0.elementOpt("busiOrderInfo", busiOrderInfoatt);
		b0.elementOpt("busiObj", busiObjatt);
		b0.elementOpt("areaId", areaId);
		b0.elementOpt("boActionType", boActionTypeatt);
		b0.elementOpt("data", boCusts);

		b0array.add(b0);

		busiOrderatt.elementOpt("busiOrder", b0array);
		busiOrderatt.elementOpt("colNbr", "-1");
		busiOrderatt.elementOpt("partyId", partyId);
		busiOrderatt.element("name", party.getPartyName());

		co.add(busiOrderatt);

		rootatt.elementOpt("custOrderList", co);

		rootatt.elementOpt("orderListInfo", orderListInfoatt);

		root.elementOpt("orderList", rootatt);
		logger.debug("��Ʒ���Ա��jsonƴ�����...");

		return root;
	}

	@Override
	public JSONObject changeInfoForschool(Document doc) throws Exception {

		//���֤��
		String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
		
		String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
		String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		
		
		String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		//��ȡ��staffId
		
		//��ȡ��partyId
		String partyId = intfSMO.queryPartyIdByprodId(prodId);
		String staffId = intfSMO.queryStaffIdBystaffCode(staffCode);
		//��ȡ�ͻ�����Ϣ
		Map<String, Object> custInfomap  = intfSMO.queryOldcustinfoByPartyId(partyId);
//		custInfomap.get("BEGIN_DT")!=null?custInfomap.get("BEGIN_DT"):""
		
		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();
		JSONArray custOrderListArr = new JSONArray();
		JSONArray busiOrderArr = new JSONArray();
		
		JSONObject custOrderListJs = new JSONObject();
		JSONObject busiOrderInfojs = new JSONObject();
		//У԰���Բ���
		JSONObject busiOrderArrt = new JSONObject();
		
		//seq������
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		Integer seq = boSeqCalculator.getNextSeqInteger();
		
		//ƴװorderListInfo�ڵ�
		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("areaId", "11000");
		//orderListInfoatt.elementOpt("areaName", "����");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		rootatt.elementOpt("orderListInfo", orderListInfoatt);
		

		//ƴװbusiOrderInfo
		busiOrderInfojs.element("areaId", "11000");
		//ƴװboActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "1");
		boActionType.element("boActionTypeCd", "C2");
		boActionType.element("name", "�޸Ŀͻ�");
		busiOrderInfojs.element("boActionType", boActionType);
		//ƴװbusiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("instId", partyId);
		busiObj.element("name", "");
		busiObj.element("objId", "");
		busiOrderInfojs.element("busiObj", busiObj);
		//ƴװbusiOrderInfo
		
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("seq", seq);
		busiOrderInfo.element("statusCd", "S");
		busiOrderInfojs.element("busiOrderInfo", busiOrderInfo);
		
		//ƴװboCustIdentities
		JSONObject boCustIdentities = new JSONObject();
		boCustIdentities.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentities.element("certType", "");
		boCustIdentities.element("identidiesTypeCd", "1");
		boCustIdentities.element("identityNum", cerdValue);
		boCustIdentities.element("ifDefaultId", "1");
		boCustIdentities.element("imgData", "");
		boCustIdentities.element("isDefault", "Y");
		boCustIdentities.element("name", "���֤");
		boCustIdentities.element("partyTypeCd", "1");
		boCustIdentities.element("state", "ADD");
		boCustIdentities.element("statusCd", "P");
		JSONArray boCustIdentitiesAttr = new JSONArray();
		boCustIdentitiesAttr.add(boCustIdentities);
//		busiOrderInfojs.element("data", boCustIdentitiesAttr);
		
		JSONArray boCustProfiles = new JSONArray();
		
		JSONArray boCustInfos = new JSONArray();
		//�ɵĿͻ���Ϣ
		JSONObject oldBoCustInfo = new JSONObject();
		String oldName = custInfomap.get("NAME")!=null?custInfomap.get("NAME").toString():"";
		oldBoCustInfo.element("areaId", "11000");
		oldBoCustInfo.element("areaName", "������");
		oldBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		oldBoCustInfo.element("defaultIdType", "1");
		oldBoCustInfo.element("ifRealName", "1");
		oldBoCustInfo.element("maiAddress", custInfomap.get("MAIL_ADDRESS")!=null?custInfomap.get("MAIL_ADDRESS").toString():"");//�ʼ���ַ
		oldBoCustInfo.element("mailAddressStr", custInfomap.get("ADDRESS_STR")!=null?custInfomap.get("ADDRESS_STR").toString():"");//��ַ
		oldBoCustInfo.element("name", oldName);//����
		oldBoCustInfo.element("postCode", "1");
		oldBoCustInfo.element("simpleSpell", Cn2Spell.getCapSpell(oldName));
		oldBoCustInfo.element("state", "DEL");
		oldBoCustInfo.element("statusCd", "S");
		oldBoCustInfo.element("telNumber", "");//�绰��
		oldBoCustInfo.element("custSubBrand", "");
		oldBoCustInfo.element("partyTypeCd", 1);
		
		JSONObject newBoCustInfo = new JSONObject();
		newBoCustInfo.element("areaId", "11000");
		newBoCustInfo.element("areaName", "������");
		newBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		newBoCustInfo.element("defaultIdType", "1");
		newBoCustInfo.element("ifRealName", "1");
		newBoCustInfo.element("maiAddress", cerdAddr);//�ʼ���ַ
		newBoCustInfo.element("mailAddressStr", cerdAddr);//��ַ
		newBoCustInfo.element("name", custName);//����
		newBoCustInfo.element("postCode", "1");
		newBoCustInfo.element("simpleSpell", Cn2Spell.getCapSpell(custName));
		newBoCustInfo.element("state", "ADD");
		newBoCustInfo.element("statusCd", "S");
		newBoCustInfo.element("telNumber", contactPhone1);//�绰��
		newBoCustInfo.element("custSubBrand", "");//�绰��
		newBoCustInfo.element("partyTypeCd", 1);//�绰��
		
		boCustInfos.add(oldBoCustInfo);
		boCustInfos.add(newBoCustInfo);
//		boCustInfoattr.add(oldBoCustInfo);
//		boCustInfoattr.add(newBoCustInfo);
		JSONObject boCustInfoattr = new JSONObject();
		boCustInfoattr.element("boCustInfos", boCustInfos);
		boCustInfoattr.element("boCustProfiles", boCustProfiles);
		
		boCustInfoattr.element("boCustIdentities", boCustIdentitiesAttr);
		busiOrderInfojs.element("data", boCustInfoattr);
		
		busiOrderArr.add(busiOrderInfojs);
		
		//�޸Ŀͻ��ڵ�
//		if(schoolID != null ){
//			busiOrderArrt = creatbusiOrderArrt(doc ,boSeqCalculator);
//			busiOrderArr.add(busiOrderArrt);
//		}
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		
		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId);//---ϵҪ�޸�
		custOrderListArr.add(custOrderListJs);
		
		rootatt.elementOpt("custOrderList", custOrderListArr);
		
		rootatt.elementOpt("isMutualModify", "INDIV_2_INDIV");
		rootatt.elementOpt("orderListIdFrom", "0");
		root.elementOpt("orderList", rootatt);
		logger.debug("��Ʒ���Ա��jsonƴ�����...");

		return root;
	}

	private JSONObject creatbusiOrderArrt(Document doc ,BoSeqCalculator boSeqCalculator) {
		JSONObject busiOrderInfocp = new JSONObject();
		JSONObject boProdItemsattr = new JSONObject();
		JSONObject prospecInfo= new JSONObject();
		JSONObject prospecInfol = new JSONObject();
		JSONArray custOrderListArr = new JSONArray();
		
//		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		
		//���֤��
		String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
		
		String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
		String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		
		
		String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		//��ȡ��staffId
		
		//��ȡ��partyId
		String partyId = intfSMO.queryPartyIdByprodId(prodId);
		String staffId = intfSMO.queryStaffIdBystaffCode(staffCode);
		String offerSpecId = intfSMO.queryOfferspecidBystaffCode(prodId);
		
		//ƴװbusiOrderInfo
		busiOrderInfocp.element("actionFlag", "2");
		busiOrderInfocp.element("areaId", "11000");
		busiOrderInfocp.element("areaName", "������");
		//ƴװboActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "4");
		boActionType.element("boActionTypeCd", "1179");
		boActionType.element("name", "�޸Ĳ�Ʒ����");
		busiOrderInfocp.element("boActionType", boActionType);
		//ƴװbusiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("accessNumber", accessNumber);
		busiObj.element("groupProdId", "");
		busiObj.element("instId", prodId);//---
		busiObj.element("isComp", "N");
		busiObj.element("name", "CDMA_Ԥ����");
		busiObj.element("objCode", "3G");
		busiObj.element("objId", offerSpecId);
		busiObj.element("partyLoanInfoId", "");
		busiObj.element("prodId", "");
		busiObj.element("staffId", "");
		busiOrderInfocp.element("busiObj", busiObj);
		//ƴװbusiOrderInfo
		int seq = boSeqCalculator.getNextSeqInteger();
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("seq", seq);
		busiOrderInfo.element("statusCd", "S");
		busiOrderInfocp.element("busiOrderInfo", busiOrderInfo);
		
		//
		prospecInfo.element("actionFlag", "2");
		prospecInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		prospecInfo.element("boId", "");
		prospecInfo.element("commitTimeStamp", "9");
		prospecInfo.element("dataSourceTypeCd", "4");
		prospecInfo.element("dataTypeCd", "1");
		prospecInfo.element("itemGroupOrder", "99999999");
		prospecInfo.element("itemOrder", "19");
		prospecInfo.element("itemSpecGroupCd", "99999999");
		prospecInfo.element("itemSpecId", "2015401");
		prospecInfo.element("name", "����-У԰");
		prospecInfo.element("prodSpecItemId", "");
		prospecInfo.element("refreshValueListenerName", "rfefresh_value");
		prospecInfo.element("saved", "true");
		prospecInfo.element("seq", seq);
		prospecInfo.element("state", "DEL");
		prospecInfo.element("statusCd", "S");
		prospecInfo.element("value", "2");
		custOrderListArr.add(prospecInfo);
		//
		prospecInfo.element("actionFlag", "2");
		prospecInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		prospecInfo.element("boId", "");
		prospecInfo.element("commitTimeStamp", "9");
		prospecInfo.element("dataSourceTypeCd", "4");
		prospecInfo.element("dataTypeCd", "1");
		prospecInfo.element("itemGroupOrder", "99999999");
		prospecInfo.element("itemOrder", "19");
		prospecInfo.element("itemSpecGroupCd", "99999999");
		prospecInfo.element("itemSpecId", "2015401");
		prospecInfo.element("name", "����-У԰");
		prospecInfo.element("prodSpecItemId", "");
		prospecInfo.element("refreshValueListenerName", "rfefresh_value");
		prospecInfo.element("saved", "true");
		prospecInfo.element("seq", seq);
		prospecInfo.element("state", "ADD");
		prospecInfo.element("statusCd", "S");
		prospecInfo.element("value", "1");
		custOrderListArr.add(prospecInfo);
		//
		prospecInfol.element("actionFlag", "2");
		prospecInfol.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		prospecInfol.element("boId", "");
		prospecInfol.element("commitTimeStamp", "9");
		prospecInfol.element("dataSourceTypeCd", "12");
		prospecInfol.element("dataTypeCd", "1");
		prospecInfol.element("itemGroupOrder", "99999999");
		prospecInfol.element("itemOrder", "0");
		prospecInfol.element("itemSpecGroupCd", "99999999");
		prospecInfol.element("itemSpecId", "13380379");
		prospecInfol.element("name", "��У��Ϣ");
		prospecInfol.element("prodSpecItemId", "");
		prospecInfol.element("refreshValueListenerName", "rfefresh_value");
		prospecInfol.element("saved", "true");
		prospecInfol.element("seq", seq);
		prospecInfol.element("state", "ADD");
		prospecInfol.element("statusCd", "S");
		prospecInfol.element("value", schoolID);
		custOrderListArr.add(prospecInfol);
		
		
		
		boProdItemsattr.element("boProdItems", custOrderListArr);
		busiOrderInfocp.element("data", boProdItemsattr);
		return busiOrderInfocp;
	}

	@Override
	public JSONObject transferForschool(Document doc) throws Exception {
		
		//���֤��
		String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
		String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
		String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		
		
		String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		//��ȡ��staffId
		
		//��ȡ��partyId
		String partyId = intfSMO.queryPartyIdByCardId(cerdValue);
		String oldPartyId = intfSMO.queryPartyIdByprodId(prodId);
		
		String staffId = intfSMO.queryStaffIdBystaffCode(staffCode);
		String offerSpecId = intfSMO.queryOfferspecidBystaffCode(prodId);
		
		Map<String, Object> oldCustInfomap  = intfSMO.queryOldcustinfoByPartyId(oldPartyId);
		Map<String, Object> custInfomap  = intfSMO.queryOldcustinfoByPartyId(partyId);
		
		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();
		JSONArray custOrderListArr = new JSONArray();
		JSONArray busiOrderArr = new JSONArray();
		
		JSONObject custOrderListJs = new JSONObject();
		JSONObject busiOrderInfojs = new JSONObject();
		//У԰���Բ���
		JSONObject busiOrderArrt = new JSONObject();
		
		//seq������
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		Integer seq = boSeqCalculator.getNextSeqInteger();
		
		//ƴװorderListInfo�ڵ�
		JSONObject orderListInfoatt = new JSONObject();
		
		
		
		orderListInfoatt.elementOpt("areaCode", "8110100");
		orderListInfoatt.elementOpt("areaId", "11000");
		orderListInfoatt.elementOpt("areaName", "����");
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("channelManageCode", "3206000000");
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "1");
		orderListInfoatt.elementOpt("partyId", oldPartyId);
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("statusCd", "S");

		rootatt.elementOpt("orderListInfo", orderListInfoatt);
		

		//ƴװbusiOrderInfo
		busiOrderInfojs.element("areaId", "11000");
		//ƴװboActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "4");
		boActionType.element("boActionTypeCd", "31");
		boActionType.element("name", "�����ͻ�ά��");
		busiOrderInfojs.element("boActionType", boActionType);
		//ƴװbusiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("accessNumber", accessNumber);
		busiObj.element("groupProdId", "");
		busiObj.element("hasDivvies", "N");
		busiObj.element("instId", prodId);
		busiObj.element("isComp", "N");
		busiObj.element("name", "CDMA_Ԥ����");
		busiObj.element("objCode", "3G");
		busiObj.element("objId", offerSpecId);
		busiOrderInfojs.element("busiObj", busiObj);
		
		//ƴװbusiOrderInfo
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("seq", boSeqCalculator.getNextSeqInteger());
		busiOrderInfo.element("statusCd", "S");
		busiOrderInfojs.element("busiOrderInfo", busiOrderInfo);
		
		
		JSONArray boCusts = new JSONArray();
		//�����ɵĿͻ���Ϣ
		JSONObject oldBoCustInfo = new JSONObject();
		oldBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		oldBoCustInfo.element("commitTimeStamp", "12");
		oldBoCustInfo.element("detail", oldCustInfomap.get("NAME")!=null?oldCustInfomap.get("NAME").toString():"");
		oldBoCustInfo.element("partyId", oldPartyId);
		oldBoCustInfo.element("partyProductRelaRoleCd", "0");
		oldBoCustInfo.element("partyTypeCd", "1");
		oldBoCustInfo.element("saved", "true");
		oldBoCustInfo.element("state", "DEL");
		oldBoCustInfo.element("statusCd", "S");
		
		JSONObject newBoCustInfo = new JSONObject();
		newBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		newBoCustInfo.element("commitTimeStamp", "12");
		newBoCustInfo.element("detail", custInfomap.get("NAME")!=null?custInfomap.get("NAME").toString():"");
		newBoCustInfo.element("partyId", partyId);
		newBoCustInfo.element("partyProductRelaRoleCd", "0");
		newBoCustInfo.element("partyTypeCd", "1");
		newBoCustInfo.element("saved", "true");
		newBoCustInfo.element("state", "ADD");
		newBoCustInfo.element("statusCd", "S");
		
		boCusts.add(oldBoCustInfo);
		boCusts.add(newBoCustInfo);
//		boCustInfoattr.add(oldBoCustInfo);
//		boCustInfoattr.add(newBoCustInfo);
		JSONObject boCustInfoattr = new JSONObject();
		boCustInfoattr.element("boCusts", boCusts);
		
		busiOrderInfojs.element("data", boCustInfoattr);
		
		busiOrderArr.add(busiOrderInfojs);
		
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		
		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", oldPartyId);//---ϵҪ�޸�
		custOrderListArr.add(custOrderListJs);
		
		rootatt.elementOpt("custOrderList", custOrderListArr);
		
		root.elementOpt("orderList", rootatt);
		logger.debug("��Ʒ���Ա��jsonƴ�����...");

		return root;
		/**/
	}

	/**
	 * �޸Ĳ�Ʒ����
	 */
	@Override
	public JSONObject changespecInfoForschool(Document doc) {
		//���֤��
		String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
		
		String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
		String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		
		
		String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		//��ȡ��staffId
		
		//��ȡ��partyId
		String partyId = intfSMO.queryPartyIdByprodId(prodId);
		String staffId = intfSMO.queryStaffIdBystaffCode(staffCode);
		//��ȡ�ͻ�����Ϣ
		Map<String, Object> custInfomap  = intfSMO.queryOldcustinfoByPartyId(partyId);
//		custInfomap.get("BEGIN_DT")!=null?custInfomap.get("BEGIN_DT"):""
		
		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();
		JSONArray custOrderListArr = new JSONArray();
		JSONArray busiOrderArr = new JSONArray();
		
		JSONObject custOrderListJs = new JSONObject();
		JSONObject busiOrderInfojs = new JSONObject();
		//У԰���Բ���
		JSONObject busiOrderArrt = new JSONObject();
		
		//seq������
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		Integer seq = boSeqCalculator.getNextSeqInteger();
		
		//ƴװorderListInfo�ڵ�
		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("areaId", "11000");
		//orderListInfoatt.elementOpt("areaName", "����");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		rootatt.elementOpt("orderListInfo", orderListInfoatt);
		

		//ƴװbusiOrderInfo
		busiOrderInfojs.element("areaId", "11000");
		//ƴװboActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "1");
		boActionType.element("boActionTypeCd", "C2");
		boActionType.element("name", "�޸Ŀͻ�");
		busiOrderInfojs.element("boActionType", boActionType);
		//ƴװbusiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("instId", partyId);
		busiObj.element("name", "");
		busiObj.element("objId", "");
		busiOrderInfojs.element("busiObj", busiObj);
		//ƴװbusiOrderInfo
		
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("seq", seq);
		busiOrderInfo.element("statusCd", "S");
		busiOrderInfojs.element("busiOrderInfo", busiOrderInfo);
		
		//ƴװboCustIdentities
		JSONObject boCustIdentities = new JSONObject();
		boCustIdentities.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentities.element("certType", "");
		boCustIdentities.element("identidiesTypeCd", "1");
		boCustIdentities.element("identityNum", cerdValue);
		boCustIdentities.element("ifDefaultId", "1");
		boCustIdentities.element("imgData", "");
		boCustIdentities.element("isDefault", "Y");
		boCustIdentities.element("name", "���֤");
		boCustIdentities.element("partyTypeCd", "1");
		boCustIdentities.element("state", "ADD");
		boCustIdentities.element("statusCd", "P");
		JSONArray boCustIdentitiesAttr = new JSONArray();
		boCustIdentitiesAttr.add(boCustIdentities);
//		busiOrderInfojs.element("data", boCustIdentitiesAttr);
		
		JSONArray boCustProfiles = new JSONArray();
		
		JSONArray boCustInfos = new JSONArray();
		//�ɵĿͻ���Ϣ
		JSONObject oldBoCustInfo = new JSONObject();
		String oldName = custInfomap.get("NAME")!=null?custInfomap.get("NAME").toString():"";
		oldBoCustInfo.element("areaId", "11000");
		oldBoCustInfo.element("areaName", "������");
		oldBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		oldBoCustInfo.element("defaultIdType", "1");
		oldBoCustInfo.element("ifRealName", "1");
		oldBoCustInfo.element("maiAddress", custInfomap.get("MAIL_ADDRESS")!=null?custInfomap.get("MAIL_ADDRESS").toString():"");//�ʼ���ַ
		oldBoCustInfo.element("mailAddressStr", custInfomap.get("ADDRESS_STR")!=null?custInfomap.get("ADDRESS_STR").toString():"");//��ַ
		oldBoCustInfo.element("name", oldName);//����
		oldBoCustInfo.element("postCode", "1");
		oldBoCustInfo.element("simpleSpell", Cn2Spell.getCapSpell(oldName));
		oldBoCustInfo.element("state", "DEL");
		oldBoCustInfo.element("statusCd", "S");
		oldBoCustInfo.element("telNumber", "");//�绰��
		oldBoCustInfo.element("custSubBrand", "");
		oldBoCustInfo.element("partyTypeCd", 1);
		
		JSONObject newBoCustInfo = new JSONObject();
		newBoCustInfo.element("areaId", "11000");
		newBoCustInfo.element("areaName", "������");
		newBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		newBoCustInfo.element("defaultIdType", "1");
		newBoCustInfo.element("ifRealName", "1");
		newBoCustInfo.element("maiAddress", cerdAddr);//�ʼ���ַ
		newBoCustInfo.element("mailAddressStr", cerdAddr);//��ַ
		newBoCustInfo.element("name", custName);//����
		newBoCustInfo.element("postCode", "1");
		newBoCustInfo.element("simpleSpell", Cn2Spell.getCapSpell(custName));
		newBoCustInfo.element("state", "ADD");
		newBoCustInfo.element("statusCd", "S");
		newBoCustInfo.element("telNumber", contactPhone1);//�绰��
		oldBoCustInfo.element("custSubBrand", "");//�绰��
		oldBoCustInfo.element("partyTypeCd", 1);//�绰��
		
		boCustInfos.add(oldBoCustInfo);
		boCustInfos.add(newBoCustInfo);
//		boCustInfoattr.add(oldBoCustInfo);
//		boCustInfoattr.add(newBoCustInfo);
		JSONObject boCustInfoattr = new JSONObject();
		boCustInfoattr.element("boCustInfos", boCustInfos);
		boCustInfoattr.element("boCustProfiles", boCustProfiles);
		
		boCustInfoattr.element("boCustIdentities", boCustIdentitiesAttr);
		busiOrderInfojs.element("data", boCustInfoattr);
		
		//busiOrderArr.add(busiOrderInfojs);
		
		//�޸Ŀͻ��ڵ�
		if(schoolID != null ){
			busiOrderArrt = creatbusiOrderArrt(doc ,boSeqCalculator);
			busiOrderArr.add(busiOrderArrt);
		}
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		
		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId);//---ϵҪ�޸�
		custOrderListArr.add(custOrderListJs);
		
		rootatt.elementOpt("custOrderList", custOrderListArr);
		
		rootatt.elementOpt("isMutualModify", "INDIV_2_INDIV");
		rootatt.elementOpt("orderListIdFrom", "0");
		root.elementOpt("orderList", rootatt);
		logger.debug("��Ʒ���Ա��jsonƴ�����...");

		return root;
	}

	@Override
	public JSONObject unsubscribeService(Document doc) {
		//���֤��
		String cerdValue = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdValue");
		
		String cerdAddr = WSUtil.getXmlNodeText(doc, "//request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(doc, "//request/custInfo/contactPhone1");
		String custName = WSUtil.getXmlNodeText(doc, "//request/custInfo/custName");
		String schoolID = WSUtil.getXmlNodeText(doc, "//request/custInfo/schoolID");
		
		
		String accessNumber = WSUtil.getXmlNodeText(doc, "//request/accessNumber");
		String prodId = WSUtil.getXmlNodeText(doc, "//request/prodId");
		String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
		String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
		//��ȡ��staffId
		
		//��ȡ��partyId
		String partyId = intfSMO.queryPartyIdByprodId(prodId);
		String staffId = intfSMO.queryStaffIdBystaffCode(staffCode);
		//��ȡ�ͻ�����Ϣ
		Map<String, Object> custInfomap  = intfSMO.queryOldcustinfoByPartyId(partyId);
//		custInfomap.get("BEGIN_DT")!=null?custInfomap.get("BEGIN_DT"):""
		
		String offerSpecId = intfSMO.queryOfferspecidBystaffCode(prodId);
		
		JSONObject root = new JSONObject();
		JSONObject rootatt = new JSONObject();
		JSONArray custOrderListArr = new JSONArray();
		JSONArray busiOrderArr = new JSONArray();
		
		JSONObject custOrderListJs = new JSONObject();
		JSONObject busiOrderInfojs = new JSONObject();
		//У԰���Բ���
		JSONObject busiOrderArrt = new JSONObject();
		
		//seq������
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		Integer seq = boSeqCalculator.getNextSeqInteger();
		
		//ƴװorderListInfo�ڵ�
		JSONObject orderListInfoatt = new JSONObject();
		orderListInfoatt.elementOpt("olId", "-1");
		orderListInfoatt.elementOpt("olNbr", "-1");
		orderListInfoatt.elementOpt("olTypeCd", "2");
		orderListInfoatt.elementOpt("staffId", staffId);
		orderListInfoatt.elementOpt("channelId", channelId);
		orderListInfoatt.elementOpt("areaId", "11000");
		//orderListInfoatt.elementOpt("areaName", "����");
		orderListInfoatt.elementOpt("statusCd", "S");
		orderListInfoatt.elementOpt("areaCode", "8110000");
		orderListInfoatt.elementOpt("partyId", partyId);
		rootatt.elementOpt("orderListInfo", orderListInfoatt);
		

		//ƴװbusiOrderInfo
		busiOrderInfojs.element("areaId", "11000");
		//ƴװboActionType
		JSONObject boActionType = new JSONObject();
		boActionType.element("actionClassCd", "4");
		boActionType.element("boActionTypeCd", "7");
		boActionType.element("name", "������Ϣ���");
		busiOrderInfojs.element("boActionType", boActionType);
		//ƴװbusiObj
		JSONObject busiObj = new JSONObject();
		busiObj.element("accessNumber", accessNumber);
		busiObj.element("instId", prodId);
		busiObj.element("isComp", "N");
		busiObj.element("name", "CDMA_Ԥ����");
		busiObj.element("objId", offerSpecId);
		busiObj.element("prodId", prodId);
		busiOrderInfojs.element("busiObj", busiObj);
		//ƴװbusiOrderInfo
		
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.element("extOrderItemGroupId", "");
		busiOrderInfo.element("extOrderItemId", "");
		busiOrderInfo.element("seq", seq);
		busiOrderInfo.element("statusCd", "S");
		busiOrderInfojs.element("busiOrderInfo", busiOrderInfo);
		
		//ƴװboCustIdentities
		JSONObject boCustIdentities = new JSONObject();
		boCustIdentities.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentities.element("certType", "");
		boCustIdentities.element("identidiesTypeCd", "1");
		boCustIdentities.element("identityNum", cerdValue);
		boCustIdentities.element("ifDefaultId", "1");
		boCustIdentities.element("imgData", "");
		boCustIdentities.element("isDefault", "Y");
		boCustIdentities.element("name", "���֤");
		boCustIdentities.element("partyTypeCd", "1");
		boCustIdentities.element("state", "ADD");
		boCustIdentities.element("statusCd", "P");
		JSONArray boCustIdentitiesAttr = new JSONArray();
		boCustIdentitiesAttr.add(boCustIdentities);
//		busiOrderInfojs.element("data", boCustIdentitiesAttr);
		
		JSONArray boCustProfiles = new JSONArray();
		
		JSONArray boServOrders = new JSONArray();
		JSONArray boServs = new JSONArray();
		//�ɵĿͻ���Ϣ
		JSONObject oldBoCustInfo = new JSONObject();
		String oldName = custInfomap.get("NAME")!=null?custInfomap.get("NAME").toString():"";
		oldBoCustInfo.element("boId", "-8");
		oldBoCustInfo.element("compProdId", "");
		oldBoCustInfo.element("containItem", "Y");
		oldBoCustInfo.element("containTime", "Y");
		oldBoCustInfo.element("extProdId", "");
		oldBoCustInfo.element("servId", "101585396382");//����id
		oldBoCustInfo.element("servSpecId", "20130028");//����id
		oldBoCustInfo.element("servSpecName", "�׻����������߿��ͣ������");//��������
		
		
		JSONObject newBoCustInfo = new JSONObject();
		newBoCustInfo.element("appEndDt", "");
		newBoCustInfo.element("appStartDt", "");
		newBoCustInfo.element("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		newBoCustInfo.element("endDt", "");
		newBoCustInfo.element("servId", "");
		newBoCustInfo.element("startDt", "");//�ʼ���ַ
		newBoCustInfo.element("state", "DEL");//��ַ
		newBoCustInfo.element("statusCd", "S");
		
		boServOrders.add(oldBoCustInfo);
		
		boServs.add(newBoCustInfo);

		JSONObject boCustInfoattr = new JSONObject();
		
		boCustInfoattr.element("boServOrders", boServOrders);
		boCustInfoattr.element("boServs", boServs);

		busiOrderInfojs.element("data", boCustInfoattr);
		
		busiOrderArr.add(busiOrderInfojs);
		
		
		custOrderListJs.elementOpt("busiOrder", busiOrderArr);
		
		custOrderListJs.elementOpt("hasPrepared", "1");
		custOrderListJs.elementOpt("servId", "101585396382");//---ϵҪ�޸�
		custOrderListJs.elementOpt("partyId", partyId);//---ϵҪ�޸�
		custOrderListArr.add(custOrderListJs);
		
		rootatt.elementOpt("custOrderList", custOrderListArr);
		
		rootatt.elementOpt("isMutualModify", "INDIV_2_INDIV");
		rootatt.elementOpt("orderListIdFrom", "0");
		root.elementOpt("orderList", rootatt);
		logger.debug("��Ʒ���Ա��jsonƴ�����...");

		return root;
	}

	
}
