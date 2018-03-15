package com.linkage.bss.crm.ws.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;

import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.ws.util.DesensitizationUtil;
import com.linkage.bss.crm.ws.util.WSUtil;

public class DesensitizationFlowFactoryImpl implements
		DesensitizationFlowFactory {

	private IntfSMO intfSMO;
	
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}
	
	/**
	 * MAP����������ʽ
	 *           map             ��Ҫ�������Ľ����
	 * desensitizationResults    �����õ���Ҫ�������ֶ�
	 *     
	 */
	@Override
	public Map<String, Object> generateDesensitization(Map<String, Object> map ,String serviceName) throws Exception {
		List<Map<String, Object>> desensitizationResults = new ArrayList<Map<String, Object>>();
		desensitizationResults = intfSMO.desensitizationService(serviceName);
		for(Map<String, Object> desensitization : desensitizationResults){
			String keyName = desensitization.get("DESENSITIZATION_EN_FIELD").toString();
			String desensiResult ;
			if(map.containsKey(keyName)){
				desensiResult = DesensitizationUtil.getExperssionIsNullSafeValue(map.get(keyName), keyName);
				map.put(keyName, desensiResult);
			}
		}
		
		return map;
	}
	/**
	 * desensitizationSystemCode = 0  : ˵����ƽ̨��û������Ȩ�ޣ�����ʱ��Ӧ�ò�������(ƽ̨Ȩ�޵ȼ�)
	 * (non-Javadoc)
	 * @see com.linkage.bss.crm.ws.order.DesensitizationFlowFactory#generateDesensitizationForXml(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generateDesensitizationForXml(String request,String response ,String serviceName,String desensitizationSystemId) throws Exception {
		String desensiResult = "" ;
		String olIdStr = "0";
		String fileGrade = "0";
		String desensitizationSystemCode = "";
		String desensitizationfileGrade = "";
		Document responseDoc = WSUtil.parseXml(response);
		Document requestDoc = WSUtil.parseXml(request);
		List<Map<String, Object>> desensitizationResults = new ArrayList<Map<String, Object>>();
		desensitizationResults = intfSMO.desensitizationService(serviceName);
		desensitizationSystemCode = intfSMO.desensitizationSystemCode(desensitizationSystemId);
		
		desensitizationfileGrade = WSUtil.getXmlNodeText(requestDoc, "request/desensitizationfileGrade");
		//��Ȩ��¼��־
		if(Integer.parseInt(desensitizationfileGrade) > Integer.parseInt(desensitizationSystemCode)){
			String logId = intfSMO.getIntfCommonSeq();
			Date requestTime = new Date();
			intfSMO.savaDesensitizationLog(logId,desensitizationSystemId,request,requestTime);
		}
		for(Map<String, Object> desensitization : desensitizationResults){
			String keyName = desensitization.get("DESENSITIZATION_EN_FIELD").toString();
			String groupName = desensitization.get("DESENSITIZATION_EN_GROUP").toString();
			fileGrade = desensitization.get("FIELD_GRADE").toString();
			if(requestDoc.selectNodes("request/desensitizationfileGrade").size() != 0){
				olIdStr = WSUtil.getXmlNodeText(requestDoc, "request/desensitizationfileGrade");
			}else{
				olIdStr = "10";
			}
			if(Integer.parseInt(olIdStr) >= Integer.parseInt(fileGrade)){
				continue;
			}
			if(Integer.parseInt(desensitizationSystemCode) >= Integer.parseInt(fileGrade)||Integer.parseInt(desensitizationSystemCode) == 0){
				continue;
			}
			//����accNbr��ȷ������ĺ��룬�������
			Node accNbr = WSUtil.getXmlNode(responseDoc, keyName);
			desensiResult = DesensitizationUtil.getExperssionIsNullSafeValue(accNbr.getText(), groupName);
			accNbr.setText(desensiResult);
		}
		return responseDoc.asXML();
	}
}
