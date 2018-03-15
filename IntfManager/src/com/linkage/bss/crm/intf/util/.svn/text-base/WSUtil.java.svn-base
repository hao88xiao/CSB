package com.linkage.bss.crm.intf.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.CollectionUtils;

import com.linkage.bss.commons.util.Log;


public class WSUtil {

	private static Log logger = Log.getLog(WSUtil.class);

	/**
	 * 为接口生成简单的回参
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(String resultCode, String resultMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode, resultMsg);
	}

	/**
	 * 为接口生成简单的回参
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(ResultCode resultCode, String resultMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode.getCode().toString(), resultMsg);
	}

	/**
	 * 为接口生成简单的回参
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(ResultCode resultCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode.getCode(), resultCode.getDesc());
	}

	/**
	 * 
	 * @param resultCode
	 * @return
	 * @author ZHANGC
	 */
	public static String buildValidateResponse(ResultCode resultCode, String nodeName, String validateCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("<" + nodeName + ">");
		sb.append(validateCode);
		sb.append("</" + nodeName + ">");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode.getCode(), resultCode.getDesc());
	}

	/**
	 * 为接口生成简单的回参
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(ResultCode resultCode, String resultMsg, String resultInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append(resultInfo);
		sb.append("</response>");
		return String.format(sb.toString(), resultCode.getCode(), resultMsg);
	}

	/**
	 * 解析XML
	 * 
	 * @param request
	 * @return
	 * @throws DocumentException
	 * @throws DocumentException
	 */
	public static Document parseXml(String xml) throws DocumentException {
		return DocumentHelper.parseText(xml);
	}

	/**
	 * 取得xpath对应的节点
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static Node getXmlNode(Document doc, String xpath) {
		if (doc == null || StringUtils.isBlank(xpath)) {
			throw new IllegalArgumentException();
		}

		return doc.selectSingleNode(xpath);
	}

	/**
	 * 取得xpath对应的节点
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static Node getXmlNode(Node node, String xpath) {
		if (node == null || StringUtils.isBlank(xpath)) {
			throw new IllegalArgumentException();
		}

		return node.selectSingleNode(xpath);
	}

	/**
	 * 取得xpath对应的节点列表
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getXmlNodeList(Document doc, String xpath) {
		if (doc == null || StringUtils.isBlank(xpath)) {
			throw new IllegalArgumentException();
		}

		return doc.selectNodes(xpath);
	}

	/**
	 * 取得xpath对应的节点值
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static String getXmlNodeText(Document doc, String xpath) {
		Node node = getXmlNode(doc, xpath);
		return node != null ? node.getText() : null;
	}

	/**
	 * 取得xpath对应的节点值
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static String getXmlNodeText(Node node, String xpath) {
		Node subNode = getXmlNode(node, xpath);
		return subNode != null ? subNode.getText() : null;
	}

	/**
	 * 判断是否空节点
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static boolean isEmptyNode(Document doc, String xpath) {
		Node node = getXmlNode(doc, xpath);
		if (node == null) {
			return true;
		}

		if (Node.ELEMENT_NODE == node.getNodeType()) {
			Element element = (Element) node;
			if (!CollectionUtils.isEmpty(element.elements())) {
				return false;
			} else if (StringUtils.isNotBlank(element.getText())) {
				return false;
			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * JSONObject转Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * catch异常公用方法
	 */
	public static void logError(String methodName, String request, Exception e) {
		logger.error(String.format("系统错误，%s=%s", methodName, request), e);
	}

	public static String getSysDt(String style) {
		Date d = new Date();
		// String str = d.toString();
		SimpleDateFormat sdf = new SimpleDateFormat(style);// 其中yyyy-MM-dd是你要表示的格式
		// 可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
		String str = sdf.format(d);
		return str;
	}

}
