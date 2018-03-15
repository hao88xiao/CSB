package com.linkage.bss.crm.ws.service;

import java.text.ParseException;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.intf.model.ServActivate;
import com.linkage.bss.crm.intf.model.ServActivatePps;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.util.WSUtil;

@WebService
public class HBService extends AbstractService {

	private static Log logger = Log.getLog(HBService.class);

	private IntfSMO intfSMO;

	private static final int BILL_MODE_PRE = 1;

	private static final int BILL_MODE_POST = 0;

	private static final String ACTIVATE_TYPE_2HA = "2HA";

	private static final String ACTIVATE_TYPE_2HO = "2HO";

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod
	@Required
	public String activateUser(@WebParam(name = "request") String request) {
		if (StringUtils.isBlank(request)) {
			return WSUtil.buildResponse("1", "入参不能为空");
		}

		try {
			Document doc = WSUtil.parseXml(request);
			String strSequence = WSUtil.getXmlNodeText(doc, "/request/sequence");
			String strServId = WSUtil.getXmlNodeText(doc, "/request/servId");
			String accNbr = WSUtil.getXmlNodeText(doc, "/request/accNbr");
			String strBillingMode = WSUtil.getXmlNodeText(doc, "/request/billingMode");
			String activateType = WSUtil.getXmlNodeText(doc, "/request/activateType");
			String strActivateDate = WSUtil.getXmlNodeText(doc, "/request/activateDate");

			if (StringUtils.isBlank(strSequence)) {
				return WSUtil.buildResponse("1", "流水号不能为空");
			}
			if (StringUtils.isBlank(strServId)) {
				return WSUtil.buildResponse("1", "用户编码不能为空");
			}
			if (StringUtils.isBlank(accNbr)) {
				return WSUtil.buildResponse("1", "主业务号码不能为空");
			}
			if (StringUtils.isBlank(strBillingMode)) {
				return WSUtil.buildResponse("1", "付费模式不能为空");
			}
			if (StringUtils.isBlank(activateType)) {
				return WSUtil.buildResponse("1", "激活类型不能为空");
			}
			if (StringUtils.isBlank(strActivateDate)) {
				return WSUtil.buildResponse("1", "激活时间不能为空");
			}

			long sequence = NumberUtils.toLong(strSequence, 0);
			if (sequence <= 0) {
				return WSUtil.buildResponse("1", "流水号格式错误");
			}

			long servId = NumberUtils.toLong(strServId, 0);
			if (servId <= 0) {
				return WSUtil.buildResponse("1", "用户编码格式错误");
			}

			int billingMode = NumberUtils.toInt(strBillingMode, -1);
			if (BILL_MODE_PRE != billingMode && BILL_MODE_POST != billingMode) {
				return WSUtil.buildResponse("1", "付费模式错误");
			}

			if (!ACTIVATE_TYPE_2HA.equals(activateType) && !ACTIVATE_TYPE_2HO.equals(activateType)) {
				return WSUtil.buildResponse("1", "激活类型错误");
			}

			Date activateDate = DateUtil.getDateFromString(strActivateDate, "yyyyMMddHHmmss");

			ServActivate servActivate = new ServActivate();
			servActivate.setSequence(sequence);
			servActivate.setServId(servId);
			servActivate.setAccNbr(accNbr);
			servActivate.setBillingMode(billingMode);
			servActivate.setActivateType(activateType);
			servActivate.setActivateDate(activateDate);
			intfSMO.insertBServActivate(servActivate);

			return WSUtil.buildResponse("0", "处理成功");
		} catch (DocumentException e) {
			logger.error("入参XML解析失败", e);
			return WSUtil.buildResponse("1", "入参XML解析失败");
		} catch (ParseException e) {
			logger.error("入参时间格式错误", e);
			return WSUtil.buildResponse("1", "入参时间格式错误");
		} catch (Exception e) {
			logger.error("服务异常", e);
			return WSUtil.buildResponse("1", "服务异常");
		}
	}

	@WebMethod
	@Required
	public String rechargeChangePriceplan(@WebParam(name = "request") String request) {
		if (StringUtils.isBlank(request)) {
			return WSUtil.buildResponse("1", "入参不能为空");
		}

		try {
			Document doc = WSUtil.parseXml(request);
			String strServId = WSUtil.getXmlNodeText(doc, "/request/servId");
			String pricePlanCd = WSUtil.getXmlNodeText(doc, "/request/pricePlanCd"); // manageCode
			String strStartDate = WSUtil.getXmlNodeText(doc, "/request/startDate");
			String strEndDate = WSUtil.getXmlNodeText(doc, "/request/endDate");

			if (StringUtils.isBlank(strServId)) {
				return WSUtil.buildResponse("1", "用户编号不能为空");
			}
			if (StringUtils.isBlank(pricePlanCd)) {
				return WSUtil.buildResponse("1", "资费标识不能为空");
			}
			if (StringUtils.isBlank(strStartDate)) {
				return WSUtil.buildResponse("1", "资费生效时间不能为空");
			}
			if (StringUtils.isBlank(strEndDate)) {
				return WSUtil.buildResponse("1", "资费失效时间不能为空");
			}

			long servId = NumberUtils.toLong(strServId, 0);
			if (servId <= 0) {
				return WSUtil.buildResponse("1", "用户编码格式错误");
			}
			
			Date startDate = DateUtil.getDateFromString(strStartDate, "yyyyMMddHHmmss");
			Date endDate = DateUtil.getDateFromString(strEndDate, "yyyyMMddHHmmss");

			ServActivatePps servActivatePps = new ServActivatePps();
			servActivatePps.setServId(servId);
			servActivatePps.setManageCode(pricePlanCd);
			servActivatePps.setStartDate(startDate);
			servActivatePps.setEndDate(endDate);
			intfSMO.insertBServActivatPps(servActivatePps);

			return WSUtil.buildResponse("0", "处理成功");
		} catch (DocumentException e) {
			logger.error("入参XML解析失败", e);
			return WSUtil.buildResponse("1", "入参XML解析失败");
		} catch (ParseException e) {
			logger.error("入参时间格式错误", e);
			return WSUtil.buildResponse("1", "入参时间格式错误");
		} catch (Exception e) {
			logger.error("服务异常", e);
			return WSUtil.buildResponse("1", "服务异常");
		}
	}
}
