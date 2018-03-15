package com.linkage.bss.crm.ws.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.CollectionUtils;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.ws.common.ResultCode;

public class WSUtil {

	private static Log logger = Log.getLog(WSUtil.class);

	/**
	 * Ϊ�ӿ����ɼ򵥵Ļز�
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
	 * Ϊ�ӿ����ɼ򵥵Ļز�
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(String resultCode, String resultMsg, String resultCode2, String resultMsg2) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("<resultCode2>%s</resultCode2>");
		sb.append("<resultMsg2>%s</resultMsg2>");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode, resultMsg, resultCode2, resultMsg2);
	}

	/**
	 * Ϊ�ӿ����ɼ򵥵Ļز�
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
	 * Ϊ�ӿ����ɼ򵥵Ļز�
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
	 * @author BaiDong
	 * @param resultCode
	 * @param nodeName
	 * @param validateCode
	 * @param nodeName1
	 * @param validateCode1
	 * @return
	 */
	public static String buildValidateResponse(ResultCode resultCode, String nodeName, String validateCode,
			String nodeName1, String validateCode1) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>%s</resultCode>");
		sb.append("<resultMsg>%s</resultMsg>");
		sb.append("<" + nodeName + ">");
		sb.append(validateCode);
		sb.append("</" + nodeName + ">");
		sb.append("<" + nodeName1 + ">");
		sb.append(validateCode1);
		sb.append("</" + nodeName1 + ">");
		sb.append("</response>");
		return String.format(sb.toString(), resultCode.getCode(), resultCode.getDesc());
	}

	/**
	 * �ɹ���������ɸ��ӻز�(��Ҫ����˳��)
	 * @author BaiDong
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String buildResponse(List<String> response) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>0</resultCode>");
		sb.append("<resultMsg>�ɹ�</resultMsg>");
		for(int i = 0;i<response.size();i++ ){
			sb.append("<" + response.get(i) + ">");
			sb.append(response.get(i+1));
			sb.append("</" + response.get(i) + ">");
			i++;
		}
		sb.append("</response>");
		return sb.toString();
	}
	/**
	public static String buildResponse(String... response) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append("<resultCode>0</resultCode>");
		sb.append("<resultMsg>�ɹ�</resultMsg>");
		for(int i = 0;i<response.length;i++ ){
			sb.append("<" + response[i] + ">");
			sb.append(response[i+1]);
			sb.append("</" + response[i] + ">");
			i++;
		}
		sb.append("</response>");
		return sb.toString();
	}**/
	
	public static void main(String[] args) {
		
		ArrayList<String> responseList = new ArrayList<String>();
		
		responseList.add("qw");
		responseList.add("1");
		responseList.add("gfd");
		responseList.add("2");
		responseList.add("re");
		responseList.add("3");
		//String sb = buildResponse(responseList);
		//System.out.println(sb);
	}
	/**
	 * Ϊ�ӿ����ɼ򵥵Ļز�
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
	 * ����XML
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
	 * ȡ��xpath��Ӧ�Ľڵ�
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
	 * ȡ��xpath��Ӧ�Ľڵ�
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
	 * ȡ��xpath��Ӧ�Ľڵ��б�
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
	 * ȡ��xpath��Ӧ�Ľڵ�ֵ
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
	 * ȡ��xpath��Ӧ�Ľڵ�ֵ
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
	 * �ж��Ƿ�սڵ�
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
	 * JSONObjectתMap
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
	 * catch�쳣���÷���
	 */
	public static void logError(String methodName, String request, Exception e) {
		logger.error(String.format("ϵͳ����%s=%s", methodName, request), e);
	}

	public static String getSysDt(String style) {
		Date d = new Date();
		// String str = d.toString();
		SimpleDateFormat sdf = new SimpleDateFormat(style);// ����yyyy-MM-dd����Ҫ��ʾ�ĸ�ʽ
		// ����������ϣ����޸����ʹ��򣻾����ʾΪ��MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
		String str = sdf.format(d);
		return str;
	}
	
	/**
	 * xml->json ����***************************
	 */
	 /**
     * ��xml�ַ���<STRONG>ת��</STRONG>ΪJSON�ַ���
     * 
     * @param xmlString
     *            xml�ַ���
     * @return JSON<STRONG>����</STRONG>
     */
    public static String xml2json(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }


    /**
     * JSON(����)�ַ���<STRONG>ת��</STRONG>��XML�ַ���
     * 
     * @param jsonString
     * @return
     */
    public static String json2xml(String jsonString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
        // return xmlSerializer.write(JSONArray.fromObject(jsonString));//���ַ�ʽֻ֧��JSON����
    }
    /**
     * �ز�����nullת��String�ġ���
     */
    public static String dealReturnParam(Map<String,Object> map,String str){
    	if(map.get(str) != null){
    		return map.get(str).toString();
    	}else{
    		return "";
    	}
    }
    /**
     * �ж��ַ�����
     * @param str �ַ��ܳ���
     * @param num  �Ƿ񳬹�num���ַ�
     * @return
     */
    public static Boolean checkUnicode(String str,int num){
		String len="";
		int j=0;
		if(str.length()>10)
			System.out.println("���ַ�������10");
		char[] c=str.toCharArray();
		for(int i=0;i<c.length;i++){
			len=Integer.toBinaryString(c[i]);
			if(len.length()>8)
				j++;
		}
		if(j < num){
			System.out.println(true);
			return true;
		}
		System.out.println(false);
		return false;
	}
}
