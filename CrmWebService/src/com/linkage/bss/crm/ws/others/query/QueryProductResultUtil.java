/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * @author Administrator
 * 查询结果工具类
 */
public class QueryProductResultUtil {

	/**
	 * 查询账务信息
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getAccResult(String request) {
		return null;
	}

	/**
	 * 查询客户信息
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getCustResult(String request) {
		return null;
	}

	/**
	 * 查询产品信息
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getProResult(String request) {
		return null;
	}

	/**
	 * 查询销售品信息
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getSaleResult(String request) {
		return null;
	}

	/**
	 * 校验入参是否通过
	 * @param request
	 * @return 0 通过 其它不通过
	 */
	public static String verifyParams(String request) {
		//获取数据
		Document doc;
		System.out.println("orderService开始校验入参。。。。");
		try {
			doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");
			String areaCode = WSUtil.getXmlNodeText(doc, "//request/areaCode");
			String channelId = WSUtil.getXmlNodeText(doc, "//request/channelId");
			String staffCode = WSUtil.getXmlNodeText(doc, "//request/staffCode");
			String queryType = WSUtil.getXmlNodeText(doc, "//request/queryType");
			String queryMode = WSUtil.getXmlNodeText(doc, "//request/queryMode");
			if (StringUtils.isBlank(accNbr) || StringUtils.isBlank(accNbrType) || StringUtils.isBlank(areaCode)
					|| StringUtils.isBlank(channelId) || StringUtils.isBlank(staffCode)
					|| StringUtils.isBlank(queryType) || StringUtils.isBlank(queryMode)) {
				return "入参错误，请检查";
			}
			String[] accTypeArray = { "1", "2", "3", "4", "12", "13" };
			boolean acctypeFlag = false;
			for (String i : accTypeArray) {
				if (accNbrType.equals(i))
					acctypeFlag = true;
			}
			if (!acctypeFlag) {
				return "输入的产品号码类型错误，请检查！";
			}

			Set<String> queryT = new HashSet<String>();
			queryT.add("1");
			queryT.add("2");
			queryT.add("3");
			queryT.add("4");
			String[] q = queryType.split(",");
			for (String s : q) {
				queryT.add(s);
			}
			if (queryT.size() != 4) {
				return "输入查询类型错误，请检查！";
			}

			if ("3".equals(accNbrType) && !"3".equals(queryType)) {
				return "输入产品号码类型和查询类型不匹配，请检查！";
			}

			if ("13".equals(accNbrType) && !"1".equals(queryType)) {
				return "输入产品号码类型和查询类型不匹配，请检查！";
			}
			return "0";
		} catch (DocumentException e) {
			return e.getMessage();
		}

	}

	/**
	 * 入参转化为XML格式
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
	public static String formatToXml(String accNbr, int accNbrType, String areaCode, String channelId,
			String staffCode, String queryType, int queryMode) {

		accNbr = (accNbr == null ? "" : accNbr);
		String at = (accNbrType == 0 ? "" : String.valueOf(accNbrType));
		areaCode = (areaCode == null ? "" : areaCode);
		channelId = (channelId == null ? "" : channelId);
		staffCode = (staffCode == null ? "" : staffCode);
		queryType = (queryType == null ? "" : queryType);
		String qm = (queryMode == 0 ? "" : String.valueOf(queryMode));

		StringBuffer sb = new StringBuffer("");
		sb.append("<request>");
		sb.append("<accNbr>").append(accNbr).append("</accNbr>");
		sb.append("<accNbrType>").append(at).append("</accNbrType>");
		sb.append("<areaCode>").append(areaCode).append("</areaCode>");
		sb.append("<channelId>").append(channelId).append("</channelId>");
		sb.append("<staffCode>").append(staffCode).append("</staffCode>");
		sb.append("<queryType>").append(queryType).append("</queryType>");
		sb.append("<queryMode>").append(qm).append("</queryMode>");
		sb.append("</request>");
		return sb.toString();
	}

	/**
	 * 构造简单的回参
	 * @param result
	 * @param resultMsg
	 * @return
	 */
	public static String builtResponse(String result, String resultMsg) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<Query_response>");
		sb.append("<basicInfo>");
		sb.append("<result>").append(result).append("</result>");
		sb.append("<resultMsg>").append(resultMsg).append("</resultMsg>");
		sb.append("</basicInfo>");
		sb.append("</Query_response>");
		return sb.toString();
	}

	public static String builtResultResp(String result) {
		return null;
	}

	/** 客户查询类型集合 */
	public static final Set<String> ACCNBR_TYPE_SET = new HashSet<String>();
	static {
		ACCNBR_TYPE_SET.add("1");
		ACCNBR_TYPE_SET.add("2");
		ACCNBR_TYPE_SET.add("4");
		ACCNBR_TYPE_SET.add("12");
	}

	/** 接入号码类型 */
	public interface AccNbrType {
		/** 合同号 */
		static final String ACCOUNT = "3";
		/** 客户标识码 */
		static final String PARTY_IDENTITY = "13";
	}

	/** 客户查询类型 */
	public static final String QUERY_CUST_INFO = "CUST_INFO";
	public static final String QUERY_PRO_INFO = "PRO_INFO";
	public static final String QUERY_ACC_INFO = "ACC_INFO";
	public static final String QUERY_SALE_INFO = "SALE_INFO";

}
