package com.linkage.bss.crm.ws.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.util.CollectionUtils;
import org.xml.sax.InputSource;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.local.bmo.IRmServiceSMO;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.query.smo.ISoQuerySMO;
import com.linkage.bss.crm.soservice.crmservice.PointService;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.CrmServiceManagerConstants;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.util.WSUtil;

import freemarker.ext.dom.NodeModel;

/**
 * 
 * 业务资源
 * 
 */
@WebService
public class RscService extends AbstractService {

	private static Log logger = Log.getLog(RscService.class);

	private RscServiceSMO rscServiceSMO;

	private IntfSMO intfSMO;

	private IRmServiceSMO rmServiceSMO;

	private ISoQuerySMO soQuerySMO;
	
	private PointService  pointService;

	private boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	@WebMethod(exclude = true)
	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setRmServiceSMO(IRmServiceSMO rmServiceSMO) {
		this.rmServiceSMO = rmServiceSMO;
	}

	@WebMethod(exclude = true)
	public void setSoQuerySMO(ISoQuerySMO soQuerySMO) {
		this.soQuerySMO = soQuerySMO;
	}

	@WebMethod(exclude = true)
	public void setpointService(PointService pointService) {
		this.pointService = pointService;
	}

	/**
	 * 终端、白卡校验
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/anTypeCd", caption = "号码类型"),
			@Node(xpath = "//request/uimNo", caption = "卡号") })
	public String checkUimNo(@WebParam(name = "request") String request) {
		try {
			String terminalInfo = "";
			Document document = WSUtil.parseXml(request);
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String uimNo = WSUtil.getXmlNodeText(document, "//request/uimNo");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String phoneNumberId = WSUtil.getXmlNodeText(document, "//request/phoneNumberId");
			String flag = WSUtil.getXmlNodeText(document, "//request/flag");
			String phoneNumber = WSUtil.getXmlNodeText(document, "//request/phoneNumber");
			String anTypeCd = WSUtil.getXmlNodeText(document, "//request/anTypeCd");
			if (StringUtils.isBlank(phoneNumberId) && StringUtils.isBlank(phoneNumber)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "号码或者号码ID必传其中一个!");
			}
			
			//集团集约卡校验
			/*Map<String, Object> cparam = new HashMap<String, Object>();
			cparam.put("bcdCode", uimNo);
			boolean UIMresult = intfSMO.checkGoupUIM(cparam);
			if(UIMresult){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "不予受理集团集约卡业务");
			}*/
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("phoneNumber", phoneNumber);
			param.put("anId", phoneNumberId);
			// 1.判断号码是否存在
			Map<String, Object> phoneNumberInfo = intfSMO.queryPhoneNumberInfoByAnId(param);
			if (phoneNumberInfo == null) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "号码不存在!");
			}
			if (!"1".equals(flag)) {
				// 2.判断号码是否已经预占
				if (!"14".equals(phoneNumberInfo.get("RSCSTATUSCD").toString())) {
					return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "号码未预占!");
				}
				// 3.判断号码是否有在途单或已归档
				boolean pp = intfSMO.qryAccessNumberIsOk(phoneNumberInfo.get("PHONENUMBER").toString());
				if (pp) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "号码已经开通或者存在在途工单，不能重复开通");
				}
			}
			// add by wanghongli 校验员工渠道和卡号渠道一致
			if ("1".equals(flag)) {
				Map<String, Object> ccParam = new HashMap<String, Object>();
				ccParam.put("channelId", channelId);
				ccParam.put("bcdCode", uimNo);
				boolean cc = intfSMO.checkUIMChannelId(ccParam);
				if (cc) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "办理该业务的员工渠道和卡号渠道不一致,请重新办理！");
				}
			}
			//add by wanghongli 校验卡号的仓库和渠道的仓库一致
			Map<String, Object> ccParam = new HashMap<String, Object>();
			ccParam.put("channelId", channelId);
			ccParam.put("bcdCode", uimNo);

			boolean ss = intfSMO.checkUIMStore(ccParam);
			if (ss) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "办理该业务的渠道仓库和卡号仓库不一致,请重新办理！");
			}

			if (StringUtils.isBlank(phoneNumberId)) {
				phoneNumberId = phoneNumberInfo.get("PHONENUMBERID").toString();
			}
			StringBuffer e = new StringBuffer();
			e.append("<terminalDevice>");
			e.append("<areaId>" + areaId + "</areaId>");
			e.append("<channelId>" + channelId + "</channelId>");
			e.append("<code>" + uimNo + "</code>");
			if ("1".equals(flag)) {
				e.append("<flag>1</flag>");// <!--补换卡时不做号码是否有预配的校验-->
			}
			e.append("<terminalDevSpec>");
			e.append("<id>10302057</id>");
			e.append("</terminalDevSpec>");
			e.append("<accessNumbers> ");
			e.append("<accessNumber>");
			e.append("<anTypeCd>" + anTypeCd + "</anTypeCd>");
			e.append("</accessNumber>");
			e.append("</accessNumbers>");
			e.append("<phoneNumberId>" + phoneNumberId + "</phoneNumberId>");
			e.append("</terminalDevice>");
			logger.debug("资源入参：{}", e.toString());
			terminalInfo = rscServiceSMO.getTerminalDevice(e.toString());
			logger.debug("资源回参：{}", terminalInfo);
			Document resultXml = WSUtil.parseXml(terminalInfo);
			String result = WSUtil.getXmlNodeText(resultXml, "/terminalDevice/accessNumbers/accessNumber/resulte");
			Element root = (Element) resultXml.selectSingleNode("/terminalDevice");
			root.setName("response");
			if (StringUtils.isNotBlank(result)) {
				if ("0".equals(result)) {
					List<Element> accessList = (List<Element>) root.selectNodes("/response/accessNumbers/accessNumber");
					for (Element access : accessList) {
						// IMIS状态是否可用
						if ("509".equals(WSUtil.getXmlNodeText(access, "anTypeCd"))) {
							String anId = WSUtil.getXmlNodeText(access, "anId");
							String rscStatusCd = intfSMO.qryDeviceNumberStatusCd(anId);
							if (!"17".equals(rscStatusCd)) {
								return WSUtil.buildResponse(ResultCode.UNSUCCESS, "卡状态不可用");
							}
						}
					}
					root.addElement("resultCode").setText(ResultCode.SUCCESS.getCode());
					root.addElement("resultMsg").setText(ResultCode.SUCCESS.getDesc());
					return resultXml.asXML().toString();
				} else {
					String msg = WSUtil.getXmlNodeText(resultXml, "//cause");
					root.addElement("resultCode").setText(ResultCode.UNSUCCESS.getCode());
					root.addElement("resultMsg").setText(msg);
					return resultXml.asXML().toString();
				}
			} else {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
		} catch (Exception e) {
			logger.error("checkUimNo终端、白卡校验:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 新建地址接口
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/addrName", caption = "地址名称") })
	public String createUserAddr(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String addrName = WSUtil.getXmlNodeText(document, "//request/addrName");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			Map resultMap = rscServiceSMO.getAddr4Agent(areaId, addrName);
			if (CollectionUtils.isEmpty(resultMap)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "新建地址接口返回信息为空");
			}
			logger.debug("新建地址接口相应结果，result={}", resultMap.toString());
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("result", resultMap);
			return mapEngine.transform("createUserAddr", root);
		} catch (Exception e) {
			logger.error("createUserAddr新建地址接口:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 局向信息查询接口getTmlXml
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required
	public String queryTml(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String deviceCode = WSUtil.getXmlNodeText(document, "//request/deviceCode");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String tmlName = WSUtil.getXmlNodeText(document, "//request/tmlName");
			String tmlManageCd = WSUtil.getXmlNodeText(document, "//request/tmlManageCd");
			String tmlTypeCd = WSUtil.getXmlNodeText(document, "//request/tmlTypeCd");
			Integer tmlTypeCdInt = null;
			if (StringUtils.isNotBlank(tmlTypeCd)) {
				tmlTypeCdInt = Integer.valueOf(tmlTypeCd);
			}
			String resultXml = rscServiceSMO.getTmlXml(deviceCode, tmlName, tmlManageCd, tmlTypeCdInt, Integer
					.parseInt(areaId));
			Document resultParse = WSUtil.parseXml(resultXml);
			List<Element> tmlList = (List<Element>) resultParse.selectNodes("tmls/tml");
			if (tmlList.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			logger.debug("局向信息查询接口相应结果，resultXml={}", resultXml);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultXml", NodeModel.parse(new InputSource(new StringReader(resultXml))));
			return mapEngine.transform("queryTml", root);
		} catch (Exception e) {
			logger.error("queryTml局向信息查询接口getTmlXml:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 选号findPhoneNumber4Agent
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/poolId", caption = "号码池ID"),
			@Node(xpath = "//request/anTypeCd", caption = "号码类型") })
	public String queryPhoneNumberList(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String poolId = WSUtil.getXmlNodeText(document, "//request/poolId");
			String tmlId = WSUtil.getXmlNodeText(document, "//request/tmlId");
			String anTypeCd = WSUtil.getXmlNodeText(document, "//request/anTypeCd");
			String pnLevelId = WSUtil.getXmlNodeText(document, "//request/pnLevelId");
			String numberHead = WSUtil.getXmlNodeText(document, "//request/numberHead");
			String numberTail = WSUtil.getXmlNodeText(document, "//request/numberTail");
			String numberMid = WSUtil.getXmlNodeText(document, "//request/numberMid");
			String numberCnt = WSUtil.getXmlNodeText(document, "//request/numberCnt");
			List<Map<String, String>> resultListMap = rscServiceSMO.findPhoneNumber4Agent(poolId, tmlId, anTypeCd,
					pnLevelId, numberHead, numberTail, numberMid, numberCnt);
			if (CollectionUtils.isEmpty(resultListMap)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			logger.debug("选号接口相应结果，resultListMap={}", resultListMap.toString());
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultListMap", resultListMap);
			return mapEngine.transform("queryPhoneNumberList", root);
		} catch (Exception e) {
			logger.error("queryPhoneNumberList选号:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 号池查询findPnPool4Agent
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/anTypeCd", caption = "号码类型") })
	public String queryPhoneNumberPoolList(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			String channelId = WSUtil.getXmlNodeText(document, "//request/channelId");
			String preflag = WSUtil.getXmlNodeText(document, "//request/preflag");
			String tmlId = WSUtil.getXmlNodeText(document, "//request/tmlId");
			String anTypeCd = WSUtil.getXmlNodeText(document, "//request/anTypeCd");
			List<Map<String, String>> resultListMap = rscServiceSMO.findPnPool4Agent(areaId, prodSpecId, channelId,
					preflag, tmlId, anTypeCd);
			if (CollectionUtils.isEmpty(resultListMap)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			logger.debug("号池查询接口相应结果，resultListMap={}", resultListMap.toString());
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("resultListMap", resultListMap);
			return mapEngine.transform("queryPhoneNumberPoolList", root);
		} catch (Exception e) {
			logger.error("queryPhoneNumberPoolList号池查询findPnPool4Agent:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 查询卡信息
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required
	public String getUimCardInfo(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String cardNo = WSUtil.getXmlNodeText(document, "//request/cardNo");
			String imsi = WSUtil.getXmlNodeText(document, "//request/imsi");
			Map<String, Object> param = new HashMap<String, Object>();
			if (StringUtils.isBlank(cardNo) && StringUtils.isBlank(imsi)) {
				param.put("RESULT", ResultCode.REQUEST_PARAME_IS_ERROR.getCode());
				param.put("CAUSE", ResultCode.REQUEST_PARAME_IS_ERROR.getDesc());
				return mapEngine.transform("getUimCardInfo", param);
			}
			param.put("cardNo", cardNo);
			param.put("imsi", imsi);
			Map resultMap = rscServiceSMO.getUIMCardInfo(param);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultMap", resultMap);
			return mapEngine.transform("getUimCardInfo", root);
		} catch (Exception e) {
			logger.error("getUimCardInfo查询卡信息:" + request, e);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("RESULT", "0");
			param.put("CAUSE", e.getMessage());
			return mapEngine.transform("getUimCardInfo", param);
		}
	}

	/**
	 * 查询卡信息
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String queryCodeByNum(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String num = WSUtil.getXmlNodeText(document, "//request/num");
			Map resultMap = rscServiceSMO.queryCodeByNum(num);
			return mapEngine.transform("queryCodeByNum", resultMap);
		} catch (Exception e) {
			logger.error("queryCodeByNum查询卡信息:" + request, e);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("reCode", "1");
			param.put("deviceCode", e.getMessage());
			return mapEngine.transform("queryCodeByNum", param);
		}
	}

	/**
	 * 宽带账户密码重置
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/prodSpecId", caption = "产品规格"),
			@Node(xpath = "//request/lanAccount", caption = "主接入号") })
	public String broadbandPwdReset(@WebParam(name = "request") String request) {
		long mstart = System.currentTimeMillis();
		try {
			Document document = WSUtil.parseXml(request);
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			String lanAccount = WSUtil.getXmlNodeText(document, "//request/lanAccount");
			long start = System.currentTimeMillis();
			Map<String, String> result = rmServiceSMO.passwordReset(prodSpecId, lanAccount, staffCode, "OTHER");
			if (isPrintLog) {
				System.out.println("broadbandPwdReset.rmServiceSMO.passwordReset(宽带重置密码) 执行时间:"
						+ (System.currentTimeMillis() - start));
			}
			Map<String, Object> root = new HashMap<String, Object>(result);
			root.put("resultCode", 0);
			root.put("resultMsg", "");
			if (isPrintLog) {
				System.out.println("调用营业接口broadbandPwdReset(宽带账户密码重置)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return mapEngine.transform("broadbandPwdReset", root);
		} catch (Exception e) {
			logger.error("broadbandPwdReset宽带账户密码重置:" + request, e);
			if (isPrintLog) {
				System.out.println("调用营业接口broadbandPwdReset(宽带账户密码重置)执行时间：" + (System.currentTimeMillis() - mstart)
						+ "ms");
			}
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 宽带账户注册或者取消
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/intfType", caption = "接口类型"),
			@Node(xpath = "//request/prodSpecId", caption = "产品规格") })
	public String createBroadbandAccount(@WebParam(name = "request") String request) {
		final String LOGINNAMETYPE = "3";
		try {
			Document document = WSUtil.parseXml(request);
			String staffCode = WSUtil.getXmlNodeText(document, "//request/staffCode");
			String prodSpecId = WSUtil.getXmlNodeText(document, "//request/prodSpecId");
			String intfType = WSUtil.getXmlNodeText(document, "//request/intfType");
			String areaId = WSUtil.getXmlNodeText(document, "//request/areaId");
			String pgLanAccount = WSUtil.getXmlNodeText(document, "//request/pgLanAccount");
			String offerSpecId = WSUtil.getXmlNodeText(document, "//request/offerSpecId");
			String anId = WSUtil.getXmlNodeText(document, "//request/anId");
			// String ppStr = WSUtil.getXmlNodeText(document,
			// "//request/ppStr");// 资费计划CD，用于判断预后付费
			String nwkInfoTDStr = WSUtil.getXmlNodeText(document, "//request/nwkInfoTD");
			Integer nwkInfoTD;
			if (StringUtils.isBlank(nwkInfoTDStr)) {
				nwkInfoTD = null;
			} else {
				nwkInfoTD = Integer.valueOf(nwkInfoTDStr);
			}
			String loginName = WSUtil.getXmlNodeText(document, "//request/loginName");
			String speedValue = WSUtil.getXmlNodeText(document, "//request/speedValue");
			// String offeringId = WSUtil.getXmlNodeText(document,
			// "//request/offeringId");
			String custId = WSUtil.getXmlNodeText(document, "//request/custId");
			String connectTypeStr = WSUtil.getXmlNodeText(document, "//request/connectType");
			Long connectType;
			if (StringUtils.isBlank(connectTypeStr)) {
				connectType = null;
			} else {
				connectType = Long.valueOf(connectTypeStr);
			}
			if (WSDomain.BroadBandIntfType.REGISTER.equals(intfType)) {
				if (StringUtils.isBlank(offerSpecId)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "宽带注册时，offerSpecId不能为空");
				}
				if (StringUtils.isBlank(custId)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "宽带注册时，custId不能为空");
				}
				if (StringUtils.isBlank(speedValue)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "宽带注册时，speedValue不能为空");
				}
				try {
					loginName = soQuerySMO.generateUnifiedAccNbr(Integer.parseInt(areaId));
				} catch (Exception e1) {
					// e1.printStackTrace();
					logger.error("宽带自动生成用户名", e1);
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "自动产生loginName异常");
				}
				com.linkage.bss.crm.model.ProdSpec prodSpec = intfSMO.getProdSpecByProdSpecId(Long.valueOf(prodSpecId));
				if (prodSpec == null) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "产品规格编号不存在");
				}
				ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType2(Long
						.valueOf(prodSpecId));
				Integer anTypeCd = prodSpec2AccessNumType.getAnTypeCd();
				if (WSDomain.BroadBandAnfTypeCd.ANTYPECD_307.equals(anTypeCd.toString())
						|| WSDomain.BroadBandAnfTypeCd.ANTYPECD_323.equals(anTypeCd.toString())) {
					logger.debug("prodSpecId={}", prodSpecId);
					logger.debug("offerSpecId={}", offerSpecId);
					logger.debug("nwkInfoTD={}", nwkInfoTD);
					logger.debug("loginName={}", loginName);
					logger.debug("LOGINNAMETYPE={}", LOGINNAMETYPE);
					logger.debug("speedValue={}", speedValue);
					logger.debug("custId={}", custId);
					logger.debug("connectType={}", connectType);
					Map<String, String> resultMap = rmServiceSMO.passWordGenerate(prodSpecId, offerSpecId, nwkInfoTD,
							loginName, LOGINNAMETYPE, speedValue, custId, connectType, staffCode, "OTHER");
					logger.debug("resultMap={}", resultMap);
					if (!"0".equals(resultMap.get("Status"))) {
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "调用宽带平台失败");
					}
					String password = resultMap.get("Password").toString();
					String mainAccNbr = resultMap.get("LanAccount").toString();
					pgLanAccount = mainAccNbr;
					Integer chooseType = prodSpec2AccessNumType.getAnChooseTypeCd();
					String rscStatusCd = "0";
					if ((chooseType != null) && (chooseType.intValue() == 7)) {
						rscStatusCd = "1";
					}
					String boActionTypeCd = "";
					StringBuffer xmlStrBuf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					xmlStrBuf.append("<root>");
					xmlStrBuf.append("<AccessNumberInfo>");
					xmlStrBuf.append("<canChangePwd>Y</canChangePwd>");
					xmlStrBuf.append("<anTypeCd>").append(anTypeCd).append("</anTypeCd>");
					xmlStrBuf.append("<rscStatusCd>").append(rscStatusCd).append("</rscStatusCd>");
					xmlStrBuf.append("<areaId>").append(areaId).append("</areaId>");
					xmlStrBuf.append("<name>").append(pgLanAccount).append("</name>");
					xmlStrBuf.append("<password>").append(password).append("</password>");
					// 增加业务工作类型，主要用于修改账号密码判断。
					xmlStrBuf.append("<boActionTypeCd>").append(boActionTypeCd).append("</boActionTypeCd>");
					xmlStrBuf.append("</AccessNumberInfo>");
					xmlStrBuf.append("</root>");
					List resultList = null;
					try {
						resultList = rscServiceSMO.addAn(xmlStrBuf.toString());
					} catch (Exception e) {
						rmServiceSMO.broadBandCancel(prodSpecId, pgLanAccount, staffCode, "OTHER");
						WSUtil.logError("业务资源接口addAn异常", request, e);
						return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
					}
					String reAnId = "";
					JSONArray rtJson = JSONArray.fromObject(resultList);
					JSONObject rmJO = rtJson.getJSONObject(0);
					if (rmJO != null) {
						reAnId = rmJO.get("anId").toString();
						String result = rmJO.get("result").toString();
						if (!"1".equals(result)) {
							rmServiceSMO.broadBandCancel(prodSpecId, pgLanAccount, staffCode, "OTHER");
							WSUtil.logError("业务资源接口addAn异常", request, new Exception("业务资源接口addAn异常"));
							return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
						}
					}
					Map<String, Object> root = new HashMap<String, Object>();
					List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
					Map<String, Object> mainAcc = new HashMap<String, Object>();
					mainAcc.put("anTypCd", anTypeCd);
					mainAcc.put("anId", reAnId);
					mainAcc.put("accessNumber", mainAccNbr);
					// returnList.add(mainAcc);
					Map<String, Object> notMainAcc = new HashMap<String, Object>();
					notMainAcc.put("anTypCd", anTypeCd);
					notMainAcc.put("anId", reAnId);
					notMainAcc.put("accessNumber", pgLanAccount);
					notMainAcc.put("password", password);
					// returnList.add(notMainAcc);

					String anTypeCdMain = intfSMO.getAnTypeCdByProdSpecId(prodSpecId, "Y");
					String anTypeCdNotMain = intfSMO.getAnTypeCdByProdSpecId(prodSpecId, "N");
					root.put("anTypeCdMain", anTypeCdMain);
					root.put("anTypeCdNotMain", anTypeCdNotMain);

					root.put("resultCode", ResultCode.SUCCESS);
					root.put("resultMsg", ResultCode.SUCCESS.getDesc());
					root.put("anTypeCd", String.valueOf(anTypeCd));
					// root.put("mainAcc", mainAcc);
					// root.put("notMainAcc", notMainAcc);
					root.put("loginName", loginName);
					root.put("password", password);
					root.put("anId", reAnId);
					root.put("lanAccount", mainAccNbr);
					return mapEngine.transform("getCustAdInfo", root);
				} else {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接入类型错误");
				}
			} else if (WSDomain.BroadBandIntfType.CANCEL.equals(intfType)) {
				if (StringUtils.isBlank(anId)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "注销时anId不能为空");
				}
				if (StringUtils.isBlank(pgLanAccount)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "注销时pgLanAccount不能为空");
				}
				Map<String, String> resultMap = rmServiceSMO.broadBandCancel(prodSpecId, pgLanAccount, staffCode,
						"OTHER");
				if (!"0".equals(resultMap.get("Status").toString())) {
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "调用宽带平台失败");
				}
				ProdSpec2AccessNumType prodSpec2AccessNumType = intfSMO.findProdSpec2AccessNumType(Long
						.valueOf(prodSpecId));
				Integer anTypeCd = prodSpec2AccessNumType.getAnTypeCd();
				StringBuffer xmlStrBuf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				xmlStrBuf.append("<root>");
				xmlStrBuf.append("<AccessNumberInfo>");
				xmlStrBuf.append("<anTypeCd>").append(anTypeCd).append("</anTypeCd>");
				xmlStrBuf.append("<rscStatusCd>14</rscStatusCd>");
				xmlStrBuf.append("<anId>").append(anId).append("</anId>");
				xmlStrBuf.append("</AccessNumberInfo>");
				xmlStrBuf.append("</root>");
				List resultList = rscServiceSMO.deleteAn(xmlStrBuf.toString());
				return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
			} else if (WSDomain.BroadBandIntfType.CHANGE.equals(intfType)) {
				if (StringUtils.isBlank(loginName)) {
					return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "修改密码时loginName不能为空");
				}
				Map<String, String> resultMap = rmServiceSMO.passwordReset(prodSpecId, loginName, staffCode, "OTHER");
				if (!"0".equals(resultMap.get("Status").toString())) {
					return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "调用宽带平台失败");
				}
				String returnPassword = resultMap.get("Password").toString();
				return WSUtil
						.buildResponse(ResultCode.SUCCESS, "密码重置成功", "<password>" + returnPassword + "</password>");
			} else {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "接口类型入参错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("createBroadbandAccount宽带账户注册或者取消:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 号码释放与预占
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/flag", caption = "动作类型:flag") })
	public String qryPreHoldNumber(@WebParam(name = "request") String request) {
		return qryPreHoldNumber_common(request);
	}

	/**
	 * 号码释放与预占
	 * 给空中写卡平台提供，不需要校验员工信息
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/flag", caption = "动作类型:flag") })
	public String preAndReleaseNumber(@WebParam(name = "request") String request) {
		return qryPreHoldNumber_common(request);
	}

	private String qryPreHoldNumber_common(String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String anId = WSUtil.getXmlNodeText(document, "//request/anId");// 接入号码ID
			String phoneNumber = WSUtil.getXmlNodeText(document, "//request/phoneNumber");// 接入号码
			String flag = WSUtil.getXmlNodeText(document, "//request/flag");// 动作类型
			if (StringUtils.isBlank(anId) && StringUtils.isBlank(phoneNumber)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST, "号码和号码ID请2选1");
			}
			logger.debug("号码释放与预占，anId={}，flag={}", new Object[] { anId, flag });
			// 根据号码ID查找号码类型编码和状态编码
			Map<String, Object> param = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(anId)) {
				param.put("anId", anId);
			}
			if (StringUtils.isNotBlank(phoneNumber)) {
				param.put("phoneNumber", phoneNumber);
			}
			Map<String, Object> phoneNumInfo = intfSMO.queryPhoneNumberInfoByAnId(param);
			if (phoneNumInfo == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "号码不存在！");
			}
			// 已预约号码预占
			if ("0".equals(flag)
					&& ((phoneNumInfo.get("RSCSTATUSCD").toString())
							.equals(CrmServiceManagerConstants.PHONE_NUMBER_ID_YU_YUE))) {
				intfSMO.updatePhoneNumberStatusCdByYuyue(phoneNumInfo.get("PHONENUMBER").toString());
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			}
			if ("1".equals(flag) || "0".equals(flag)) {
				StringBuffer xml = new StringBuffer();
				xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				xml.append("<root>");
				xml.append("<AccessNumberInfo>");
				xml.append(String.format("<anTypeCd>%s</anTypeCd>", phoneNumInfo.get("ANTYPECD").toString()));// 接入号码类型编码（不可为空）
				xml.append(String.format("<rscStatusCd>%s</rscStatusCd>", flag));// 状态编码，若是正式预占接入号码接口需要传入参数(目标状态　0:临时预占,1:正式预占)
				xml.append(String.format("<anId>%s</anId>", phoneNumInfo.get("PHONENUMBERID")));
				xml.append("</AccessNumberInfo>");
				xml.append("</root>");
				logger.debug("资源接口入参：{}", xml.toString());
				// 预占
				try {
					List result = rscServiceSMO.allocAn(xml.toString());
					Map a = (Map) result.get(0);
					if ((a.get("result").toString()).equals("1")) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, a.get("cause").toString() + "或已被预占");
					}
				} catch (Exception e) {
					WSUtil.logError("qryPreHoldNumber", request, e);
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "出现异常，预占失败！");
				}
			} else if ("2".equals(flag)) {
				if (!"14".equals(phoneNumInfo.get("RSCSTATUSCD").toString())) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "该号码状态不符合释放条件，不能进行释放操作");
				}
				StringBuffer xml = new StringBuffer();
				xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				xml.append("<root>");
				xml.append("<AccessNumberInfo>");
				xml.append(String.format("<anTypeCd>%s</anTypeCd>", phoneNumInfo.get("ANTYPECD").toString()));// 接入号码类型编码（不可为空）
				xml.append(String.format("<rscStatusCd>%s</rscStatusCd>", phoneNumInfo.get("RSCSTATUSCD").toString()));// 状态编码，若是正式预占接入号码接口需要传入参数(目标状态　0:临时预占,1:正式预占)
				xml.append(String.format("<anId>%s</anId>", phoneNumInfo.get("PHONENUMBERID")));
				xml.append("</AccessNumberInfo>");
				xml.append("</root>");
				logger.debug("资源接口入参：{}", xml.toString());
				// 释放
				try {
					List result = rscServiceSMO.releaseAn(xml.toString());
					Map a = (Map) result.get(0);
					if ((a.get("result").toString()).equals("1")) {
						return WSUtil.buildResponse(ResultCode.SUCCESS);
					} else {
						return WSUtil.buildResponse(ResultCode.UNSUCCESS, a.get("cause").toString());
					}
				} catch (Exception e) {
					WSUtil.logError("qryPreHoldNumber", request, e);
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "发生异常，释放失败！");
				}
			} else {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "动作类型无效");
			}
		} catch (Exception e) {
			logger.error("qryPreHoldNumber号码释放与预占:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * PAD/代理商店员查询
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/accNbr", caption = "接入号码（手机号）") })
	public String getClerkId(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(document, "//request/accNbr");
			List<Map<String, Object>> result = intfSMO.getClerkId(accNbr);
			if (CollectionUtils.isEmpty(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "该号码对应的店员不存在！");
			}
			if (result.size() > 1) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "手机号登记店员信息异常！");
			}
			String clerkId = result.get(0).get("CLERKID").toString();
			String clerkName = result.get(0).get("NAME").toString();
			String resultNodeClerkId = "<clerkId>" + clerkId + "</clerkId>";
			String resultNodeClerkName = "<clerkName>" + clerkName + "</clerkName>";
			return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc(), resultNodeClerkId
					+ resultNodeClerkName);
		} catch (Exception e) {
			logger.error("getClerkId PAD/代理商店员查询:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * CRM电子签名日志记录
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/coNbr", caption = "订单流水号"),
			@Node(xpath = "//request/flag", caption = "类型标志 ") })
	// 1、手工打印 2、CA电子签字
	public String addReceiptPringLog(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String coNbr = WSUtil.getXmlNodeText(document, "//request/coNbr");
			String flag = WSUtil.getXmlNodeText(document, "//request/flag");
			List<String> coNbrList = null;
			String[] coNbrArr = coNbr.split(",");
			if (coNbrArr != null && coNbrArr.length > 0) {
				coNbrList = Arrays.asList(coNbrArr);
			}
			if (coNbrList == null || coNbrList.isEmpty()) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "coNbr错误");
			}
			intfSMO.addReceiptPringLog(coNbrList, flag);
			return WSUtil.buildResponse(ResultCode.SUCCESS, ResultCode.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("addReceiptPringLog CRM电子签名日志记录:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 资源释放
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/anId", caption = "订单流水号"),
			@Node(xpath = "//request/anTypeCd", caption = "接入号类型"),
			@Node(xpath = "//request/rscStatusCd", caption = "接入号状态") })
	public String releaseAn(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String anId = WSUtil.getXmlNodeText(document, "//request/anId");
			String anTypeCd = WSUtil.getXmlNodeText(document, "//request/anTypeCd");
			String rscStatusCd = WSUtil.getXmlNodeText(document, "//request/rscStatusCd");
			StringBuffer xml = new StringBuffer();
			xml.append("<root>");
			xml.append("<AccessNumberInfo>");
			xml.append(String.format("<anTypeCd>%s</anTypeCd>", anTypeCd));// 接入号码类型编码（不可为空）
			xml.append(String.format("<rscStatusCd>%s</rscStatusCd>", rscStatusCd));// 状态编码
			xml.append(String.format("<anId>%s</anId>", anId));
			xml.append("</AccessNumberInfo>");
			xml.append("</root>");
			logger.debug("资源接口入参：{}", xml.toString());
			List result = rscServiceSMO.releaseAn(xml.toString());
			Map a = (Map) result.get(0);
			if ((a.get("result").toString()).equals("1")) {
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, a.get("cause").toString());
			}
		} catch (Exception e) {
			logger.error("releaseAn资源释放:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 根据uimNo卡号查询对应号码
	 * 
	 * @author TERFY
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/uimNo", caption = "uim卡号") })
	public String queryMdnByUim(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String uimNo = WSUtil.getXmlNodeText(document, "//request/uimNo");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("uimNo", uimNo);
			Map<String, Object> accessNum = intfSMO.getAccessNumberByUimNo(param);
			if (accessNum == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "未找到对应号码！");
			}
			logger.debug("卡号对应号码查询结果;{}", accessNum);
			param.put("resultCode", ResultCode.SUCCESS.getCode());
			param.put("resultMsg", ResultCode.SUCCESS.getDesc());
			param.put("accessNum", accessNum);
			return mapEngine.transform("queryMdnByUim", param);
		} catch (Exception e) {
			logger.error("queryMdnByUim根据uimNo卡号查询对应号码:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * PAD 获取老卡信息
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String queryUimNum(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String phoneNumber = WSUtil.getXmlNodeText(document, "//request/phoneNumber");
			if (StringUtils.isBlank(phoneNumber)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "号码不能为空");
			}
			Map<String, Object> info = intfSMO.queryUimNum(phoneNumber);
			if (info == null) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "该号码为空号或已销号");
			}
			logger.debug("PAD 获取老卡信息;{}", info.toString());
			String code = intfSMO.getTmlCodeByPhoneNumber(phoneNumber);
			logger.debug("局向信息;{}", code);
			List<Map<String, Object>> list = intfSMO.getPartyIdentityList(info.get("PARTY_ID").toString());
			logger.debug("证件信息;{}", list.toString());
			String type = "";
			String value = "";
			String name = "";
			if (list.size() > 0) {
				type = list.get(0).get("TYPE").toString();
				value = list.get(0).get("VALUE").toString();
				name = list.get(0).get("NAME").toString();
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("resultCode", ResultCode.SUCCESS.getCode());
			param.put("resultMsg", ResultCode.SUCCESS.getDesc());
			param.put("info", info);
			param.put("type", type);
			param.put("value", value);
			param.put("name", name);
			param.put("code", code);
			return mapEngine.transform("queryUimNum", param);
		} catch (Exception e) {
			logger.error("queryUimNum PAD获取老卡信息:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
	@WebMethod
	public String queryVipCardInfo(@WebParam(name = "request") String request) {
		String returnXml="";
		returnXml=pointService.queryPoint(request);
		return returnXml;
	}
	/**
	 * 根据号码及号码类型修改为指定状态
	 * 
	 * @author BaiDong
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	public String updateNumberStatus(@WebParam(name = "request") String request) {
		try {
			Map  result  = rscServiceSMO.updateNumberStatus(request);
			if ("0" != result.get("reCode")) {
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("resultCode", result.get("reCode"));
				if(result.get("reMsg") != null){
					root.put("cause", result.get("reMsg"));
				}else{
					root.put("cause", "未返回错误信息");
				}
				return mapEngine.transform("updateNumberStatus", root);
			}
			logger.debug("根据号码及号码类型修改为指定状态，result={}", result.toString());
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS);
			root.put("cause", "成功");
			return mapEngine.transform("updateNumberStatus", root);
		} catch (Exception e) {
			logger.error("queryPNlevel根据号码及号码类型修改为指定状态:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	
	/**
	 * 提供给营业受理用的
	 * 
	 * @author BaiDong
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	public String queryPNlevel(@WebParam(name = "request") String request) {
		try {
			String result  = rscServiceSMO.queryPNlevel(request);
			if ("".equals(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "根据号码查询号码靓号等级的接口返回信息为空");
			}
			logger.debug("根据号码查询号码靓号等级，result={}", result.toString());
			return result;
		} catch (Exception e) {
			logger.error("queryPNlevel根据号码查询号码靓号等级:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * FTTH正装机同网络部资源系统局向（交换区）信息ID化
	 * 封装业务资源assignAn接口 供手机端掉用
	 * @author BaiDong
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	public String assignAn(@WebParam(name = "request") String request) {
		String accessNumberStr = "";
		String anIdStr = "";
		String resultCode = "";
		String cause = "";
		try {
			StringBuilder sb = new StringBuilder();
			List list = rscServiceSMO.assignAn(request);
			if (list != null && list.size() > 0) {
				Map anMap = (Map) list.get(0);
//				if(anMap.get("result").equals('1')){
				if(true){
					accessNumberStr = anMap.get("name").toString();
//					resultCode = anMap.get("result").toString();
					anIdStr = anMap.get("anId").toString();
//					cause = anMap.get("cause").toString();
						sb.append("<response>");
//						sb.append("<result>").append(resultCode).append("</result>");
						sb.append("<result>0</result>");
//						sb.append("<cause>").append(cause).append("</cause>");
						sb.append("<cause></cause>");
						sb.append("<anId>").append(anIdStr).append("</anId>");
						sb.append("<name>").append(accessNumberStr).append("</name>");
						sb.append("</response>");
						return sb.toString();
					}else{
						accessNumberStr = anMap.get("name").toString();
						resultCode = anMap.get("result").toString();
						anIdStr = anMap.get("anId").toString();
						cause = anMap.get("cause").toString();
						sb.append("<response>");
						sb.append("<result>").append(resultCode).append("</result>");
						sb.append("<cause>").append(cause).append("</cause>");
						sb.append("</response>");
						return sb.toString();
					}
				}else{
					logger.debug("根据号码查询号码靓号等级，result={}", list.toString());
					sb.append("<response>");
					sb.append("<result>-1</result>");
					sb.append("<cause>调用assignAn接口返回null</cause>");
					sb.append("<anId></anId>");
					sb.append("<name></name>");
					sb.append("</response>");
					return sb.toString();
				}
			} catch (Exception e) {
			logger.error("queryPNlevel根据号码查询号码靓号等级:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
}
