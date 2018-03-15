package com.linkage.bss.crm.ws.interceptor;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.linkage.bss.crm.intf.facade.SysFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.common.WSDomain;
import com.linkage.bss.crm.ws.util.WSUtil;

@Aspect
@Component
public class InvokeInterceptor {

	@Autowired
	@Qualifier("intfManager.sysFacade")
	private SysFacade sysFacade;
	
	@Autowired
	@Qualifier("intfManager.intfSMO")
	private IntfSMO intfSMO;
	
	private static final String PLAT = "CrmWebService";

	@Around("execution(public !void com.linkage.bss.crm.ws.service.*.*(..)) && @annotation(required)")
	public Object around(ProceedingJoinPoint pjp, Required required) throws Throwable {
		
		String resultCode = "-2";
		String type = "-1";
		if (WSDomain.QUERY_INTERFACE.contains(pjp.getSignature().getName())) {
			type = "0";
		} else if(WSDomain.DEAL_INTERFACE.contains(pjp.getSignature().getName())) {
			type = "1";
		}

		String staffInfo = null;
		Object[] args = pjp.getArgs();
		if (args == null || args.length == 0 || StringUtils.isBlank((String) args[0])) {
			return WSUtil.buildResponse(ResultCode.PARAMETER_NOT_EXIST,"入参为空");
		}

		String request = (String) args[0];
		String logId = intfSMO.getIntfCommonSeq();
		Date requestTime = new Date();
		intfSMO.saveRequestInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime);
		
		Document requestDoc = null;
		try {
			requestDoc = WSUtil.parseXml(request);
		} catch (DocumentException e) {
			Date responseTime = new Date();
			intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参XML解析异常"), responseTime, type, resultCode);
			return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "入参XML解析异常");
		}
		if (!WSDomain.BLACK_METHOD.contains(pjp.getSignature().getName())) {

			String channelId = WSUtil.getXmlNodeText(requestDoc, "/request/channelId");
			String staffCode = WSUtil.getXmlNodeText(requestDoc, "/request/staffCode");

			if (StringUtils.isBlank(channelId)) {
				Date responseTime = new Date();
				intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理渠道为空，请重新输入"), responseTime, type, resultCode);
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理渠道为空，请重新输入");
			}
//			
			if (!channelId.matches("\\-?[0-9]*")) {
				Date responseTime = new Date();
				intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理渠道为空，请重新输入"), responseTime, type, resultCode);
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理渠道错误，请重新输入");
			}

			if (StringUtils.isBlank(staffCode)) {
				Date responseTime = new Date();
				intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理员工工号为空，请重新输入"), responseTime, type, resultCode);
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "受理员工工号为空，请重新输入");
			} else {
				staffInfo = sysFacade.findStaffIdByStaffCode(staffCode.toUpperCase());
			}

			if (staffInfo == null) {
				Date responseTime = new Date();
				intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "员工信息查询结果为空"), responseTime, type, resultCode);
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "员工信息查询结果为空");
			}
		}

		if (required != null && required.nodes() != null) {
			for (Node node : required.nodes()) {
				if (WSUtil.isEmptyNode(requestDoc, node.xpath())) {
					Date responseTime = new Date();
					intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, node.caption() + "为空，请重新输入"), responseTime, type, resultCode);
					return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, node.caption() + "为空，请重新输入");
				}
			}
		}
		Element rootElement = requestDoc.getRootElement();
		rootElement.addElement("areaCode").addText("010");
		if (WSUtil.getXmlNode(requestDoc, "/request/areaId") == null) {
			rootElement.addElement("areaId").addText("11000");
		} else {
			WSUtil.getXmlNode(requestDoc, "/request/areaId").setText("11000");
		}
		if (staffInfo != null) {
			Document doc = WSUtil.parseXml(staffInfo);
			String staffId = WSUtil.getXmlNodeText(doc, "//staffId");
			rootElement.addElement("staffId").addText(staffId);
		}
		Object result = pjp.proceed(new String[] { requestDoc.asXML() });

		String responseString = "";
		if (result!=null) {
			responseString = result.toString();
		}
		
		
		if (responseString.contains("<resultCode>")&&responseString.contains("</resultCode>")) {
			resultCode = responseString.substring(responseString.indexOf("<resultCode>")+12,responseString.indexOf("</resultCode>"));
		} else {
			resultCode = "-1";
		}
		Date responseTime = new Date();
		if (("0".equals(type))&&("0".equals(resultCode))) {
			intfSMO.deleteCrmRequest(logId);
		} else {
			intfSMO.saveResponseInfo(logId, PLAT, pjp.getSignature().getName(), request, requestTime, responseString, responseTime, type, resultCode);
		}
		return result;
	}
}
