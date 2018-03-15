package com.linkage.bss.crm.ws.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;

/**
 * ��������
 * 
 * @author BaiDong
 * 
 */
public class DesensitizationUtil {
	
	List<String> DesensitizationList = new ArrayList<String>();
	
	/**
	 * 
	 * @param request ���
	 * @param line		
	 * @param SecurityLevel ��ȫ����
	 * @return
	 */
	public static String distributionInfo(String request ,String line,String SecurityLevel){
		Document document;
		try {
			document = WSUtil.parseXml(request);
	
		String inSecurityLevel = WSUtil.getXmlNodeText(document, "//request/inSecurityLevel");//����еİ�ȫ����
		
		//���ȸ���ƽ̨�����ѯƽ̨�İ�ȫ����
		//--Map<String,Object> SecurityLevel = new HashMap<String,Object>();
		SecurityLevel = "";
		//�õ������еİ�ȫ�����ֶ� �����ݿ��ѯ�����ֶαȽϣ�����ԽС����Խ�ߣ�
		
		if(Integer.parseInt(SecurityLevel) <Integer.parseInt(inSecurityLevel)){
			
			//--�� ���ݿ��ѯ�����ֶ�ֵ ���ڴ�����ֶ��״�"������İ�ȫ����������ȷ�Ϻ�������"��
			return "������İ�ȫ����������ȷ�Ϻ�������";
		}
		
		List<Object> list = new ArrayList<Object>();
		for (int j = 0; j < list.size(); j++) {
			String DesensitizationName = (String) list.get(j);
			getExperssionIsNullSafeValue(line, DesensitizationName);
		}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * �����ַ�����
	 */
	public static String getExperssionIsNullSafeValue(Object o, String name) {
		String returnString = "";
		name = name.trim();
		try {
			if (name.equals("accessNumber") || name.equals("compAccessNumber")) {
				String accessNumber = (String) o;
				if (null != accessNumber && !"".equals(accessNumber)) {
					String regExp1 = "^[1]([3-9]{1}[0-9]{1})[0-9]{8}$"; // C��
					String regExp2 = "^[A-Z][0-9]*$"; // ����˺�
					String regExp3 = "^[0-9]*$"; // ����
					if (judgeValue(regExp1, accessNumber)) {
						// returnString =
						// accessNumber.substring(0,3)+"*****"+accessNumber.substring(8);
						returnString = accessNumber.substring(0, accessNumber
								.length() - 4)
								+ "****";
					} else if (judgeValue(regExp2, accessNumber)) {
						returnString = accessNumber.substring(0, 1) + "******";
					} else if (judgeValue(regExp3, accessNumber)) {
						// returnString = accessNumber.substring(0,3)+"******";
						returnString = accessNumber.substring(0, accessNumber
								.length() - 4)
								+ "****";
					} else if (accessNumber.contains("*****")) {
						returnString = accessNumber;
					} else {
						returnString = "******";
					}
				}
			} else if (name.equals("INDNTNUM")) {
				
				String identityNum = (String) o;
				if (null != identityNum && !"".equals(identityNum)) {
					String regExp1 = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])"; // ���֤

					if (judgeValue(regExp1, identityNum)) {
						returnString = "******" + identityNum.substring(6, 14)
								+ getStarString(identityNum.length() - 14);
					} else if (identityNum.contains("*")) {
						returnString = identityNum;
					} else {
						returnString = identityNum.substring(0, 1)
								+ getStarString(identityNum.length() - 1);
					}
				}
			} else if (name.equals("profileValue")) {
				String accessNumber = (String) o;
				if (null != accessNumber && !"".equals(accessNumber)) {
					String regExp1 = "^[1]([3-9]{1}[0-9]{1})[0-9]{8}$"; // C��
					String regExp2 = "^[A-Z][0-9]*$"; // ����˺�
					String regExp3 = "^[0-9]*$"; // ����
					if (judgeValue(regExp1, accessNumber)) {
						// returnString =
						// accessNumber.substring(0,3)+"*****"+accessNumber.substring(8);
						returnString = accessNumber.substring(0, accessNumber
								.length() - 4)
								+ "****";
					} else if (judgeValue(regExp2, accessNumber)
							&& accessNumber.length() > 8) {
						returnString = accessNumber.substring(0, 1) + "******";
					} else if (judgeValue(regExp3, accessNumber)
							&& accessNumber.length() > 8) {
						// returnString = accessNumber.substring(0,3)+"******";
						returnString = accessNumber.substring(0, accessNumber
								.length() - 4)
								+ "****";
					} else if (accessNumber.contains("*")) {
						returnString = accessNumber;
					} else {
						returnString = accessNumber.substring(0, 1)
								+ getStarString(accessNumber.length() - 1);
					}
				}
			}else if(name.equals("NAME")){
				returnString = o.toString().substring(0, 1)
						+ getStarString(name.length() -2);
			}
		} catch (Exception e) {
			// LOG.error("������������"+e.getMessage());
			returnString = "***";
		}

		return returnString;
	}

	/**
	 * ���ɡ��ĸ���
	 */
	
	/**
	 * �ж��Ƿ��������ڱ��ʽ
	 */
	public static boolean judgeValue(String regExp, String s) {
		try {
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(s);
			return m.find();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ȡ������*��
	 * 
	 * @param i
	 * @return
	 */
	public static String getStarString(int i) {
		String s = "*";
		for (int j = 1; j < i; j++) {
			s += "*";
		}
		if (s.length() > 6) {
			s = "******";
		}
		return s;
	}
	/**
	 * ͨ����������
	 * @param args
	 */
	
	public static void main(String[] args)
	{
		System.out.println("$$$$$$$:"+getExperssionIsNullSafeValue("18001359861","accessNumber"));
		
//		String i = "+!+++";
//		String s = "-!*1-!*3";
//		System.out.print(s.indexOf("!*"));
		
		
		//System.out.println("++++++++++++"+judgeValue("+!*1-!*3","���ſƼ��й����޹�˾"));
		//System.out.println("++++++++++++"+getSafeValue("+!*1-!*3","���ſƼ��й����޹�˾"));
		//System.out.println("++++++++++++"+getSafeValue("+!*1","���ſƼ��й����޹�˾"));
		//System.out.println("++++++++++++"+getSafeValue("-!*1","���ſƼ��й����޹�˾"));
		//System.out.println("++++++++++++"+getSafeValue("+*1-*3","���ſƼ�"));
		//System.out.println("++++++++++++"+getSafeValue("-*1","���ſƼ��й����޹�˾"));
		//System.out.println("++++++++++++"+getSafeValue("+*1","���ſƼ��й����޹�˾"));
//		System.out.println("&&&&:"+8/2);
//		String regExp = "+1-1";
//		Integer start = Integer.parseInt(regExp.substring(regExp.indexOf("+")+1,regExp.indexOf("-")));
//		Integer end = Integer.parseInt(regExp.substring(regExp.indexOf("-")+1));
//		System.out.println("start:"+getStarString(3));
		
		//System.out.println("************************"+getFindCount("+","*"));
	}
}
