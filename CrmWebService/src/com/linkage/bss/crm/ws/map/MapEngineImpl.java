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
	 * ����ģ��ת������
	 * 
	 * @param templateName
	 * @param model
	 * @return ת����ı���
	 */
	public String transform(String templateName, Map<String, Object> model) {
		try {
			Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName + ".ftl");
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, model).replaceAll("> <", "><");
		} catch (Exception e) {
			WSUtil.logError(templateName, templateName+"ģ������쳣", e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
	}
}
