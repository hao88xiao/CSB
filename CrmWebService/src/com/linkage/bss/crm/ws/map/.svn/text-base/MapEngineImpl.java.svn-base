package com.linkage.bss.crm.ws.map;

import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.util.WSUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MapEngineImpl implements MapEngine {

	private FreeMarkerConfigurer freeMarkerConfigurer;

	private Configuration freeMarkerConfiguration;

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	/**
	 * 根据模板转换报文
	 * 
	 * @param templateName
	 * @param model
	 * @return 转换后的报文
	 */
	public String transform(String templateName, Map<String, Object> model) {
		try {
			Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName + ".ftl");
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, model).replaceAll("> <", "><");
		} catch (Exception e) {
			WSUtil.logError(templateName, templateName+"模板解析异常", e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
}
