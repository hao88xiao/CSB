package com.linkage.bss.crm.ws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import bss.systemmanager.provide.SmService;

import com.linkage.bss.commons.spring.SpringBeanInvoker;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.cust.dto.ChannelDto;
import com.linkage.bss.crm.cust.smo.IChannelSMO;
import com.linkage.bss.crm.intf.model.StaticData;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * 
 * 代理商
 * 
 */
@WebService
public class AgentService extends AbstractService {

	private static Log logger = Log.getLog(AgentService.class);

	private SmService smService;

	private IntfSMO intfSMO;

	private IChannelSMO channelSMO;

	@WebMethod(exclude = true)
	public void setSmService(SmService smService) {
		this.smService = smService;
	}

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setChannelSMO(IChannelSMO channelSMO) {
		this.channelSMO = channelSMO;
	}

	@WebMethod
	public String test() {
		return "";
	}

	/**
	 * 代理商信息查询接口 调用系统管理的代理商查询接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/checkStaffCd", caption = "员工ID") })
	public String queryAgentInfo(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			HashMap result = new HashMap();
			String checkStaffCd = WSUtil.getXmlNodeText(doc, "//request/checkStaffCd");
			String staffInfo = smService.findStaffInfoByStaffNumber(checkStaffCd);
			if (staffInfo != null) {
				Document document = WSUtil.parseXml(staffInfo);
				String staffId = WSUtil.getXmlNodeText(document, "//staffId");
				result = smService.findStaffNumberByStaffId(Long.valueOf(staffId));
			}

			if (result.size() == 0) {
				logger.error("代理商信息查询接口：查询结果为空！");
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> queryAgentInfoMap = new HashMap<String, Object>();
			queryAgentInfoMap.put("resultCode", ResultCode.SUCCESS);
			queryAgentInfoMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			queryAgentInfoMap.put("data", result);
			return mapEngine.transform("queryAgentInfo", queryAgentInfoMap);

		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 登录校验 调用系统管理接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/staffNumber", caption = "员工工号"),
			@Node(xpath = "//request/passwd", caption = "密码") })
	public String login(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String staffNumber = WSUtil.getXmlNodeText(doc, "//request/staffNumber");
			//空中写卡校验入参中添加了airKard节点
			//需要代理商工号到crm员工工号的转换 add by helinglong
			String airKard = WSUtil.getXmlNodeText(doc, "//request/airKard");
			if ("1".equals(airKard)) {
				Long staffid = intfSMO.getStaffIdByAgentNum(staffNumber);
				String errorMsg = "";
				if (staffid == null) {
					errorMsg = "根据代理商工号未查询到staff_id！代理商工号：" + staffNumber;
					logger.debug(errorMsg);
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, errorMsg);
				}
				staffNumber = intfSMO.findStaffNumByStaffId(staffid);
			}
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
         //增加平台编码限制 只对pad限制是否捆绑安全钥匙
			if("6090010023".equals(systemId)){
			Map<String, Object> padPwdStaffInfo = intfSMO.selectStaffInofFromPadPwdStaff(staffNumber);
			if (padPwdStaffInfo != null) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "请使用与该工号绑定的安全钥匙登录！");
			}
			}
			String passWord = WSUtil.getXmlNodeText(doc, "//request/passwd");
			if (StringUtils.isEmpty(staffNumber) || StringUtils.isEmpty(passWord)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST, "入参不能为空");
			}
			HashMap allStaffInfo = new HashMap();
			Map<String, Object> loginMap = new HashMap<String, Object>();
			List<ChannelDto> channelDto = new ArrayList<ChannelDto>();
			// 取出staffId
			String staffId = "";
			String staffInfo = smService.findStaffInfoByStaffNumber(staffNumber);
			if (staffInfo != null) {
				Document staffInfoDoc = WSUtil.parseXml(staffInfo);
				staffId = WSUtil.getXmlNodeText(staffInfoDoc, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
				allStaffInfo = smService.findStaffNumberByStaffId(Long.valueOf(staffId));
			}
			String result = smService.verifyLoginStaff(staffNumber, passWord);
			if (StringUtils.isBlank(result)) {
				return WSUtil.buildResponse(ResultCode.STAFF_LOGIN_NULL);
			}
			String reStr = result.substring(0, 1);
			if (reStr.equals("0") || reStr.equals("1") || reStr.equals("2") || reStr.equals("3")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("partyId", staffId);
				params.put("partyChannelRoleCd", "1");
				params.put("channelStatusCd", "1");
				channelDto = channelSMO.queryChannelsByMap(params);
				loginMap.put("resultCode", ResultCode.SUCCESS);
				loginMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
				loginMap.put("staffId", staffId);
				loginMap.put("staffInfo", allStaffInfo);
				loginMap.put("channelDto", channelDto);
				return mapEngine.transform("login", loginMap);
			} else {
				if (result.equals("-1")) {
					loginMap.put("resultCode", ResultCode.PWD_ERROR);
					loginMap.put("resultMsg", ResultCode.PWD_ERROR.getDesc());
				} else if (result.equals("-3")) {
					loginMap.put("resultCode", ResultCode.STAFF_NOT_EXIST);
					loginMap.put("resultMsg", ResultCode.STAFF_NOT_EXIST.getDesc());
				} else {
					loginMap.put("resultCode", ResultCode.CALL_METHOD_ERROR);
					loginMap.put("resultMsg", ResultCode.CALL_METHOD_ERROR.getDesc());
				}
				return mapEngine.transform("login", loginMap);
			}
		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 登录校验 调用系统管理接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/staffNumber", caption = "员工工号"),
			@Node(xpath = "//request/passwd", caption = "密码") })
	public String loginln(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String staffNumber = WSUtil.getXmlNodeText(doc, "//request/staffNumber");
			//空中写卡校验入参中添加了airKard节点
			//需要代理商工号到crm员工工号的转换 add by helinglong
			String airKard = WSUtil.getXmlNodeText(doc, "//request/airKard");
			if ("1".equals(airKard)) {
				Long staffid = intfSMO.getStaffIdByAgentNum(staffNumber);
				String errorMsg = "";
				if (staffid == null) {
					errorMsg = "根据代理商工号未查询到staff_id！代理商工号：" + staffNumber;
					logger.debug(errorMsg);
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, errorMsg);
				}
				staffNumber = intfSMO.findStaffNumByStaffId(staffid);
			}
			String systemId = WSUtil.getXmlNodeText(doc, "//request/systemId");
         //增加平台编码限制 只对pad限制是否捆绑安全钥匙
			if("6090010023".equals(systemId)){
			Map<String, Object> padPwdStaffInfo = intfSMO.selectStaffInofFromPadPwdStaff(staffNumber);
			if (padPwdStaffInfo != null) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "请使用与该工号绑定的安全钥匙登录！");
			}
			}
			String passWord = WSUtil.getXmlNodeText(doc, "//request/passwd");
			if (StringUtils.isEmpty(staffNumber) || StringUtils.isEmpty(passWord)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST, "入参不能为空");
			}
			HashMap allStaffInfo = new HashMap();
			Map<String, Object> loginMap = new HashMap<String, Object>();
			List<ChannelDto> channelDto = new ArrayList<ChannelDto>();
			// 取出staffId
			String staffId = "";
			String staffInfo = smService.findStaffInfoByStaffNumber(staffNumber);
			if (staffInfo != null) {
				Document staffInfoDoc = WSUtil.parseXml(staffInfo);
				staffId = WSUtil.getXmlNodeText(staffInfoDoc, "//StaffInfos/StaffInfo/staffNumberInfo/staffId");
				
				allStaffInfo = smService.findStaffNumberByStaffId(Long.valueOf(staffId));
			}
			String result = smService.verifyLoginStaff(staffNumber, passWord);
			if (StringUtils.isBlank(result)) {
				return WSUtil.buildResponse(ResultCode.STAFF_LOGIN_NULL);
			}
			String reStr = result.substring(0, 1);
			if (reStr.equals("0") || reStr.equals("1") || reStr.equals("2") || reStr.equals("3")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("partyId", staffId);
				params.put("partyChannelRoleCd", "1");
				params.put("channelStatusCd", "1");
//				channelDto = channelSMO.queryChannelsByMap(params);
				/**
				 * 60
				 */
				List<Map<String, Object>> resList = intfSMO.queryChannelsByMap(staffId);
				
				StringBuilder res = new StringBuilder();
				res.append("<response>");
				res.append("<resultCode>").append(ResultCode.SUCCESS.getCode()).append("</resultCode>");
				res.append("<resultMsg>").append(ResultCode.SUCCESS.getDesc()).append("</resultMsg>");
				res.append("<staffId>").append(staffId).append("</staffId>");
				res.append("<staffNumber>").append(allStaffInfo.get("staffNumber")!=null?allStaffInfo.get("staffNumber"):"").append("</staffNumber>");
				res.append("<orgId>").append(allStaffInfo.get("orgId")!=null?allStaffInfo.get("orgId"):"").append("</orgId>");
				res.append("<orgName>").append(allStaffInfo.get("orgName")!=null?allStaffInfo.get("orgName"):"").append("</orgName>");
				res.append("<channelList>");
				for(Map<String, Object> channeler:resList){
					res.append("<channel>");
					res.append("<id>").append(channeler.get("channelId")).append("</id>");
					res.append("<name>").append(channeler.get("name")).append("</name>");
					res.append("</channel>");
				}
				res.append("</channelList>");
				res.append("</response>");
				return res.toString();
			} else {
				if (result.equals("-1")) {
					loginMap.put("resultCode", ResultCode.PWD_ERROR);
					loginMap.put("resultMsg", ResultCode.PWD_ERROR.getDesc());
				} else if (result.equals("-3")) {
					loginMap.put("resultCode", ResultCode.STAFF_NOT_EXIST);
					loginMap.put("resultMsg", ResultCode.STAFF_NOT_EXIST.getDesc());
				} else {
					loginMap.put("resultCode", ResultCode.CALL_METHOD_ERROR);
					loginMap.put("resultMsg", ResultCode.CALL_METHOD_ERROR.getDesc());
				}
				return mapEngine.transform("login", loginMap);
			}
		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}
	/**
	 * 初始化参数接口
	 * 
	 * @author zhangying
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/id", caption = "参数id") })
	public String initStaticData(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String id = WSUtil.getXmlNodeText(doc, "//request/id");
			List<StaticData> result = intfSMO.initStaticData(id);
			if (CollectionUtils.isEmpty(result)) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL);
			}
			Map<String, Object> initStaticDataMap = new HashMap<String, Object>();
			initStaticDataMap.put("resultCode", ResultCode.SUCCESS);
			initStaticDataMap.put("resultMsg", ResultCode.SUCCESS.getDesc());
			initStaticDataMap.put("data", result);
			return mapEngine.transform("initStaticData", initStaticDataMap);

		} catch (Exception e) {
			logger.error(String.format("系统错误，request=%s", request), e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}

	}

	/**
	 * 数据同步
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = {@Node(xpath = "//request/agentCode", caption = "代理CODE"),
			@Node(xpath = "//request/opType", caption = "操作类型") })
	public String syncDate4Prm2Crm(@WebParam(name = "request") String request) {
		try {
			return intfSMO.syncDate4Prm2Crm(request);

		} catch (Exception e) {
			WSUtil.logError("syncDate4Prm2Crm", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());

		}
	}

	/**
	 * 登录设备有效性校验
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String checkDeviceId(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String deviceId = WSUtil.getXmlNodeText(doc, "//request/deviceId");
			if (StringUtils.isBlank(deviceId)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "设备号不能为空！");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("serialNo", deviceId);
			List<Map<String, Object>> resultState = intfSMO.checkDeviceIdInLogin(param);
			if (resultState.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "系统无该设备号或设备绑定的工号已失效");
			}
			if ("2".equals(resultState.get(0).get("STATE").toString())) {
				// 生成6位随机密码
				Random r = new Random();
				String result = "";
				for (int i = 0; i < 6; i++) {
					result += r.nextInt(10);
				}
				logger.debug("登录生成的随机密码为：{}", result);
				// 保存随机密码
				param.put("loginRandom", result);
				boolean buer = intfSMO.updatePadPwdInLogin(param);
				if (!buer) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "保存随机密码失败");
				}
				return WSUtil.buildValidateResponse(ResultCode.SUCCESS, "random", result);

			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "该设备号状态不是绑定状态，校验失败");
			}
		} catch (Exception e) {
			WSUtil.logError("checkDeviceId", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());

		}
	}

	/**
	 * 随机数校验
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String checkSnPwd(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String deviceId = WSUtil.getXmlNodeText(doc, "//request/deviceId");
			String value = WSUtil.getXmlNodeText(doc, "//request/value");
			// 注释：执行1登录、2密码重置、3挂失、4解挂业务操作
			String operType = WSUtil.getXmlNodeText(doc, "//request/operType");
			if (StringUtils.isBlank(operType) || StringUtils.isBlank(value) || StringUtils.isBlank(deviceId)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参不能为空！");
			}
			if (!"1".equals(operType) && !"2".equals(operType) && !"3".equals(operType) && !"4".equals(operType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "请求报文operType节点值非法");
			}
			// 获取真实设备号
			String password = "";// 加密计算后的结果
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("serialNo", deviceId);
			param.put("operType", operType);
			List<Map<String, Object>> resultCkeckSnPwdInLogin = intfSMO.checkSnPwdInLogin(param);
			if (resultCkeckSnPwdInLogin.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "设备号错误");
			}
			Map<String, Object> map = (Map<String, Object>) resultCkeckSnPwdInLogin.get(0);
			String realNo = map.get("REAL_NO").toString();
			String loginRandom = map.get("LOGIN_RANDOM").toString();
			String serialNo = map.get("SERIAL_NO").toString();
			String staffNumber = map.get("STAFF_NUMBER").toString();
			String str = "";
			if ("3".equals(operType)) {
				str = deviceId + loginRandom;

			} else if ("1".equals(operType)) {
				str = realNo + loginRandom;
			} else {
				str = loginRandom;
			}
			{
				int b = 378551;
				int a = 63689;
				int hash = 0;

				for (int i = 0; i < str.length(); i++) {
					hash = hash * a + str.charAt(i);
					a = a * b;
				}

				password = (hash & 0x7FFFFFFF) + "a";
			}
			// 密码比较
			if (!password.equals(value)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "密码校验失败");
			}
			Map<String, Object> staffInfoMap = new HashMap<String, Object>();
			List<Map<String, Object>> channelInfo = new ArrayList<Map<String, Object>>();
			if ("3".equals(operType)) {// 挂失
				param.put("state", "3");
				param.put("serialNo", serialNo);
				intfSMO.updatePadPwdInLogin(param);
				intfSMO.insertPadPwdLog(param);
			} else if ("4".equals(operType)) {// 解挂
				param.put("state", "2");
				param.put("serialNo", serialNo);
				intfSMO.updatePadPwdInLogin(param);
				intfSMO.insertPadPwdLog(param);
			}
			// 登录校验返回工号等信息
			if ("1".equals(operType)) {
				param.put("staffNumber", staffNumber);
				List<Map<String, Object>> staffInfo = intfSMO.checkSnPwd4SelectStaffInfoByStaffNumber(param);
				staffInfoMap = (Map<String, Object>) staffInfo.get(0);
				param.put("partyId", staffInfoMap.get("STAFF_ID"));
				channelInfo = intfSMO.checkSnPwd4SelectChannelInfoByPartyId(param);
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("staffInfoMap", staffInfoMap);
			root.put("channelInfo", channelInfo);
			return mapEngine.transform("checkSnPwd", root);
		} catch (Exception e) {
			WSUtil.logError("checkSnPwd", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * 设备同步随机数
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String transmitRandom(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String deviceId = WSUtil.getXmlNodeText(doc, "//request/deviceId");
			String random = WSUtil.getXmlNodeText(doc, "//request/random");
			if (StringUtils.isBlank(random) || StringUtils.isBlank(deviceId)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参不能为空！");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("serialNo", deviceId);
			param.put("resetRandom", random);
			boolean bueriUpdate = intfSMO.updatePadPwdInLogin(param);
			if (!bueriUpdate) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "保存随机密码失败");
			}
			List<Map<String, Object>> resultMap = intfSMO.transmitRandom4SelectStaffInfoByDeviceId(param);
			if (resultMap.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "员工信息查询结果为空！");
			}
			logger.debug("员工信息查询结果：{}", resultMap.toString());
			Map<String, Object> map = (Map<String, Object>) resultMap.get(0);
			String staffNumber = map.get("STAFF_NUMBER").toString();
			String accessNumber = map.get("ACCESS_NUMBER").toString();
			param.put("staffNumber", staffNumber);
			param.put("accessNumber", accessNumber);
			param.put("state", "2");
			param.put("operType", "2");
			param.put("random", random);
			intfSMO.insertPadPwdLog(param);
			intfSMO.insertSmsWaitSendCrmSomeInfo(param);
			return WSUtil.buildResponse(ResultCode.SUCCESS);
		} catch (Exception e) {
			WSUtil.logError("transmitRandom", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());

		}
	}

	/**
	 * 设备有效性校验（挂失、解挂用）
	 * 
	 * @author TERFY
	 * @param request
	 * @return
	 */
	@WebMethod
	public String checkDeviceIdNew(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String staffNumber = WSUtil.getXmlNodeText(doc, "//request/staffNumber");
			String operType = WSUtil.getXmlNodeText(doc, "//request/operType");
			if (StringUtils.isBlank(operType) || StringUtils.isBlank(staffNumber)) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参不能为空！");
			}
			// 3挂失、4解挂
			if (!"3".equals(operType) && !"4".equals(operType)) {
				return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "请求报文operType节点值非法");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("serialNo", staffNumber);
			param.put("operType", operType);
			List<Map<String, Object>> infoList = intfSMO.checkSnPwdInLogin(param);
			if (infoList.size() == 0) {
				return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "系统无该工号绑定的设备信息，校验失败:系统无该设备号");
			}
			logger.debug("设备信息;{}", infoList.toString());
			Map<String, Object> map = (Map<String, Object>) infoList.get(0);
			String serialNo = map.get("SERIAL_NO").toString();
			String accessNumber = map.get("ACCESS_NUMBER").toString();
			String newStaffNumber = map.get("STAFF_NUMBER").toString();
			String state = map.get("STATE").toString();

			if ("3".equals(operType)) {
				if (!"2".equals(state)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "该工号绑定的设备号不是绑定状态,或者设备已经挂失/作废，无法挂失");
				}
			} else if ("4".equals(operType)) {
				if (!"3".equals(state)) {
					return WSUtil.buildResponse(ResultCode.UNSUCCESS, "该工号绑定的设备号不是挂失状态，无法解挂");
				}
			}
			// 生成6位随机密码
			Random r = new Random();
			String result = "";
			for (int i = 0; i < 6; i++) {
				result += r.nextInt(10);
			}
			logger.debug("登录生成的随机密码为：{}", result);
			param.put("loginRandom", result);
			param.put("serialNo", serialNo);
			intfSMO.updatePadPwdInLogin(param);
			param.put("random", result);
			param.put("accessNumber", accessNumber);
			intfSMO.insertSmsWaitSendCrmSomeInfo(param);
			param.put("staffNumber", newStaffNumber);
			List<Map<String, Object>> staffInfoList = intfSMO.getStaffCodeAndStaffName(param);
			String name = "";
			Map<String, Object> staffInfoMap = new HashMap<String, Object>();
			if (staffInfoList.size() > 0) {
				staffInfoMap = (Map<String, Object>) staffInfoList.get(0);
				name = staffInfoMap.get("NAME").toString();
				logger.debug("员工信息查询结果;{}", staffInfoList.toString());
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("resultCode", ResultCode.SUCCESS.getCode());
			root.put("resultMsg", ResultCode.SUCCESS.getDesc());
			root.put("staffName", name);
			root.put("accessNumber", accessNumber);
			root.put("staffNumber", staffNumber);
			return mapEngine.transform("checkDeviceIdNew", root);
		} catch (Exception e) {
			WSUtil.logError("checkDeviceIdNew", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());

		}
	}

	/**
	 * 修改员工密码
	 * 
	 * @author CHENJUNJIE
	 * @param request
	 * @return
	 */
	@WebMethod
	@Required(nodes = { @Node(xpath = "//request/staffNumber", caption = "被修改的员工号"),
			@Node(xpath = "//request/oldPassword", caption = "旧密码"),
			@Node(xpath = "//request/newPassword", caption = "新密码") })
	public String changeStaffPassword(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String staffNumber = WSUtil.getXmlNodeText(doc, "//request/staffNumber");
			String oldPassword = WSUtil.getXmlNodeText(doc, "//request/oldPassword");
			String newPassword = WSUtil.getXmlNodeText(doc, "//request/newPassword");
			int result = smService.changePassword(staffNumber, oldPassword, newPassword);
			logger.debug("调用系管changePassword返回的信息：", result);
			if (result == 0) {
				return WSUtil.buildResponse(ResultCode.SUCCESS);
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS);
			}
		} catch (Exception e) {
			WSUtil.logError("changeStaffPassword", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 代理商查询
	 * @param request
	 * @return
	 */
	public String qryAgent(@WebParam(name = "request") String request){
		try {
			com.ai.bss.crm.soquery.so.smo.ICrm2BuildingSMO smo = (com.ai.bss.crm.soquery.so.smo.ICrm2BuildingSMO)SpringBeanInvoker.getBean("soQueryManager.crm2BuildingSMO");
			String result = smo.qryAgent(request);
		
			if (result != null) {
				return result;
			} else {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS);
			}
		} catch (Exception e) {
			WSUtil.logError("qryAgent", request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
}
