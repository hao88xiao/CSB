package com.linkage.bss.crm.ws.service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cn.id5.model.qzj.Request;
import cn.id5.model.qzj.Result;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.soservice.crm2building.service.Crm2BuildingService;
import com.linkage.bss.crm.ws.annotation.Node;
import com.linkage.bss.crm.ws.annotation.Required;
import com.linkage.bss.crm.ws.common.ResultCode;
import com.linkage.bss.crm.ws.util.GetRebateAddress;
import com.linkage.bss.crm.ws.util.WSUtil;
import com.linkage.bss.crm.ws.util.gzt.ClientOnJdkHttpConnection;

@WebService
public class CrmService extends AbstractService {

	private static Log logger = Log.getLog(CrmService.class);

	private IntfSMO intfSMO;

	private Crm2BuildingService crm2BuildingService;

	@WebMethod(exclude = true)
	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	@WebMethod(exclude = true)
	public void setCrm2BuildingService(Crm2BuildingService crm2BuildingService) {
		this.crm2BuildingService = crm2BuildingService;
	}

	/**
	 * �й����У�ũ������ǩԼ��Լ�ӿ� <request> <nodeInfo> <partyName></partyName>
	 * <identifyType></identifyType> <identifyNumber></identifyNumber>
	 * <zhjgId></zhjgId> <zhjgName></zhjgName> <freezeNo></freezeNo>
	 * <freezeAcctNo></freezeAcctNo> <freezeSubAcctNo></ freezeSubAcctNo >
	 * <freezeMoney></freezeMoney> <freezeDate></freezeDate>
	 * <unfreezeDate></unfreezeDate> <serialNumber></serialNumber>
	 * <preAccessNumber></preAccessNumber> <systemDate></systemDate> </nodeInfo>
	 * <actionType>1</actionType> <bankCode> bank107</bankCode>
	 * <bankName>ũ������</bankName> </request>
	 * 
	 * @param xmlInfo
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/nodeInfo/partyName", caption = "�ͻ�����"),
			@Node(xpath = "//request/nodeInfo/identifyType", caption = "֤������"),
			@Node(xpath = "//request/nodeInfo/identifyNumber", caption = "֤������"),
			@Node(xpath = "//request/nodeInfo/freezeNo", caption = "������"),
			@Node(xpath = "//request/nodeInfo/freezeAcctNo", caption = "�����ʺ�"),
			@Node(xpath = "//request/actionType", caption = "ҵ����"),
			@Node(xpath = "//request/bankCode", caption = "���б���"),
			@Node(xpath = "//request/bankName", caption = "��������") })
	public String bankFreezeDou(@WebParam(name = "request") String request) {
		String errorMsg = "";
		StringBuffer result = new StringBuffer("");
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			String partyName = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/partyName");
			String identifyType = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/identifyType");
			String identifyNumber = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/identifyNumber");
			String freezeNo = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/freezeNo");
			String systemDate = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/systemDate");
			String bankCode = WSUtil.getXmlNodeText(document,
					"//request/bankCode");
			String bankName = WSUtil.getXmlNodeText(document,
					"//request/bankName");
			String actionType = WSUtil.getXmlNodeText(document,
					"//request/actionType");
			// �Ǳ�ѡ����
			String zhjgId = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/zhjgId");
			String zhjgName = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/zhjgName");
			String freezeAcctNo = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/freezeAcctNo");
			String freezeSubAcctNo = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/freezeSubAcctNo");
			String freezeMoney = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/freezeMoney");
			String freezeDate = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/freezeDate");
			String unfreezeDate = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/unfreezeDate");
			String serialNumber = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/serialNumber");
			String preAccessNumber = WSUtil.getXmlNodeText(document,
					"//request/nodeInfo/preAccessNumber");
			if (!"1".equals(actionType) && !"2".equals(actionType)) {
				errorMsg = "�����ҵ�����ͣ�" + actionType + "���Ϸ���";
			}
			if (!"".equals(errorMsg)) {
				result.append("<response>");
				result.append("<resultCode>5</resultCode>");
				result.append("<resultMsg>").append(errorMsg).append(
						"</resultMsg>");
				result.append("<serialNumber></serialNumber>");
				result.append("</response>");
				return result.toString();
			}
			// ����
			if ("1".equals(actionType)) {
				// ��֤�Ƿ��ж�����Ϣ
				Map<String, Object> checkMap = new HashMap<String, Object>();
				checkMap.put("freezeNo", freezeNo);
				checkMap.put("bankCode", bankCode);
				if (intfSMO.checkBankFreeze(checkMap) > 0) {
					result.append("<response>");
					result.append("<resultCode>1</resultCode>");
					result.append("<resultMsg>�Ѵ���</resultMsg>");
					result.append("<serialNumber></serialNumber>");
					result.append("</response>");
					return result.toString();
				}
				// ����crm.freezerent��sqlƴװ
				StringBuffer sql = new StringBuffer("");
				sql
						.append("insert into crm.freezerent(FREEZERENT_ID,ZHJG_ID,ZHJG_NAME,FREEZE_DATE,FREEZE_NO,FREEZE_MONEY,UNFREEZE_DATE,PARTY_NAME,PARTY_IDENTITYNO,FREEZE_ACCTNO,FREEZE_SUB_ACCTNO,PARTY_IDENTITYTYPE,PRE_ACCESSNUMBER,BANK_TYPE,CREATE_DT)");
				sql.append("values");
				sql.append("(");
				sql.append("crm.SEQ_freezerent.nextval,");
				sql.append(zhjgId == null ? "''" : "'" + zhjgId + "'").append(
						",");
				sql.append(zhjgName == null ? "''" : "'" + zhjgName + "'")
						.append(",");
				sql.append(
						freezeDate == null || "".equals(freezeDate) ? "sysdate"
								: "to_date('" + freezeDate + "', 'yyyyMMdd')")
						.append(",");
				sql.append(freezeNo == null ? "''" : "'" + freezeNo + "'")
						.append(",");
				sql
						.append(
								freezeMoney == null ? "''" : "'" + freezeMoney
										+ "'").append(",");
				sql
						.append(
								unfreezeDate == null || "".equals(unfreezeDate) ? "sysdate"
										: "to_date('" + unfreezeDate
												+ "', 'yyyyMMdd')").append(",");
				sql.append(partyName == null ? "''" : "'" + partyName + "'")
						.append(",");
				sql.append(
						identifyNumber == null ? "''" : "'" + identifyNumber
								+ "'").append(",");
				sql.append(
						freezeAcctNo == null ? "''" : "'" + freezeAcctNo + "'")
						.append(",");
				sql.append(
						freezeSubAcctNo == null ? "''" : "'" + freezeSubAcctNo
								+ "'").append(",");
				sql.append(identifyType == null ? "''" : identifyType).append(
						",");
				sql.append(
						preAccessNumber == null ? "''" : "'" + preAccessNumber
								+ "'").append(",");
				sql.append(bankCode == null ? "''" : "'" + bankCode + "'")
						.append(",");
				sql.append("sysdate)");
				logger.debug("ִ�в����sql���Ϊ��" + sql.toString());
				System.out.println("sql.toString()--:" + sql.toString());
				intfSMO.insertBankFreeze(sql.toString());

				result.append("<response>");
				result.append("<resultCode>0</resultCode>");
				result.append("<resultMsg>�ɹ�</resultMsg>");
				result.append("<serialNumber>").append(serialNumber).append(
						"</serialNumber>");
				result.append("</response>");

				return result.toString();

			} else {// �ⶳ����
				// �ⶳ
				// ���Ȳ�ѯ�ⶳ�Ƿ��м�¼;
				// ��֤�Ƿ��ж�����Ϣ
				Map<String, Object> checkMap = new HashMap<String, Object>();
				checkMap.put("freezeNo", freezeNo);
				checkMap.put("bankCode", bankCode);
				if (intfSMO.checkBankFreeze(checkMap) == 0) {
					result.append("<response>");
					result.append("<resultCode>3</resultCode>");
					result.append("<resultMsg>������</resultMsg>");
					result.append("<serialNumber></serialNumber>");
					result.append("</response>");
					return result.toString();
				}
				// ��ѯ�Ƿ��нⶳ��Ϣ
				String checkSql = "select count(1) from crm.freezerent where freeze_no ='"
						+ freezeNo
						+ "' and BANK_TYPE = '"
						+ bankCode
						+ "' and FREEZE_UNFREEZE_FLAG = 1";
				if (intfSMO.checkBankFreeze(checkSql)) {
					result.append("<response>");
					result.append("<resultCode>4</resultCode>");
					result.append("<resultMsg>�ѽⶳ</resultMsg>");
					result.append("<serialNumber></serialNumber>");
					result.append("</response>");
					return result.toString();
				}

				// ��ѯ�����м�¼���нⶳ
				String updateSql = "update crm.freezerent set FREEZE_UNFREEZE_FLAG='1' , ACTUAL_UNFREEZE_DATE = sysdate where FREEZE_NO ='"
						+ freezeNo + "' and BANK_TYPE = '" + bankCode + "'";
				System.out.println("updateSql:" + updateSql);
				boolean flag = intfSMO.updateBankFreeze(updateSql);
				if (flag == true) {
					result.append("<response>");
					result.append("<resultCode>0</resultCode>");
					result.append("<resultMsg>�ɹ�</resultMsg>");
					result.append("<serialNumber>").append(serialNumber)
							.append("</serialNumber>");
					result.append("</response>");
					return result.toString();
				} else {
					result.append("<response>");
					result.append("<resultCode>5</resultCode>");
					result.append("<resultMsg>���½ⶳ��Ϣʱ��������</resultMsg>");
					result.append("<serialNumber></serialNumber>");
					result.append("</response>");
					return result.toString();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.append("<response>");
			result.append("<resultCode>5</resultCode>");
			result.append("<resultMsg>ϵͳ����</resultMsg>");
			result.append("<serialNumber></serialNumber>");
			result.append("</response>");
			return result.toString();
			// return WSUtil.buildResponse("5", "ϵͳ����");
		}

	}

	/**
	 * ��ȡ¥��ͻ���CRM�ͻ���ϵ��Ϣ
	 * 
	 * @author hell
	 * @param request
	 * 
	            <request> <partyName>zhanguu</partyName>
	 *            <channelId>11040611</channelId> <staffCode>bj1001</staffCode>
	 *            <areaId>10000</areaId> </request>
	 * 
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/partyName", caption = "�ͻ�����"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String getPartyInfoByPartyName(
			@WebParam(name = "request") String request) {
		String partyName = null;
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			partyName = WSUtil.getXmlNodeText(document, "//request/partyName");
			return crm2BuildingService.getPartyInfoByPartyName(partyName);
		} catch (Exception e) {
			logger.error("getPartyInfoByPartyName�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * ��ȡ�ͻ�ҵ����Ϣ
	 * 
	 * @author hell
	 * @param request
	 *            <request> <partyId>103005240275</partyId>
	 *            <channelId>11040611</channelId> <staffCode>bj1001</staffCode>
	 *            <areaId>10000</areaId> </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/partyId", caption = "�ͻ�id"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String getBusiInfoByPartyId(
			@WebParam(name = "request") String request) {
		Long partyId = null;
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			String partyIds = WSUtil.getXmlNodeText(document,
					"//request/partyId");
			partyId = Long.parseLong(partyIds);
			return crm2BuildingService.getBusiInfoByPartyId(partyId);
		} catch (Exception e) {
			logger.error("getBusiInfoByPartyId�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * ��ȡ�ͻ���;������Ϣ
	 * 
	 * @author hell
	 * @param request
	             <request> <partyId>103005240275</partyId>
	            <channelId>11040611</channelId> <staffCode>bj1001</staffCode>
	            <areaId>10000</areaId> </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/partyId", caption = "�ͻ�id"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String getUnfinishedOrderPartyId(
			@WebParam(name = "request") String request) {
		Long partyId = null;
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			String partyIds = WSUtil.getXmlNodeText(document,
					"//request/partyId");
			partyId = Long.parseLong(partyIds);
			return crm2BuildingService.getUnfinishedOrderPartyId(partyId);
		} catch (Exception e) {
			logger.error("getBusiInfoByPartyId�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * ����building_2_discount��������
	 * 
	 * @author hell
	 * @param request
	 *            <request> <BUILDING_ID>43556777</BUILDING_ID>
	 *            <POST_PAID_DISCOUNT>546546</POST_PAID_DISCOUNT>
	 *            <PREPAY_DISCOUNT>546546</PREPAY_DISCOUNT>
	 *            <channelId>11040611</channelId> <staffCode>bj1001</staffCode>
	 *            <areaId>10000</areaId> </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/BUILDING_ID", caption = "¥��id"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String insertBuilding2Discount(
			@WebParam(name = "request") String request) {
		String BUILDING_ID = "";
		String POST_PAID_DISCOUNT = "";
		String PREPAY_DISCOUNT = "";
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			BUILDING_ID = WSUtil.getXmlNodeText(document,
					"//request/BUILDING_ID");
			POST_PAID_DISCOUNT = WSUtil.getXmlNodeText(document,
					"//request/POST_PAID_DISCOUNT");
			PREPAY_DISCOUNT = WSUtil.getXmlNodeText(document,
					"//request/PREPAY_DISCOUNT");
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("BUILDING_ID", BUILDING_ID);
			m1.put("POST_PAID_DISCOUNT", POST_PAID_DISCOUNT);
			m1.put("PREPAY_DISCOUNT", PREPAY_DISCOUNT);
			String result = crm2BuildingService.insertBuilding2Discount(m1);
			if (result != null && result.contains("&lt;"))
				result = result.replace("&lt;", "<");
			return result;
		} catch (Exception e) {
			logger.error("insertBuilding2Discount�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * ɾ������building_2_discount��������
	 * 
	 * @author hell
	 * @param request
	 *            <request> <BUILDING_ID>43556777</BUILDING_ID>
	 *            <channelId>11040611</channelId> <staffCode>bj1001</staffCode>
	 *            <areaId>10000</areaId> </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/BUILDING_ID", caption = "¥��id"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String delBuilding2Discount(
			@WebParam(name = "request") String request) {
		String BUILDING_ID = "";
		try {
			// ��ѡ����
			Document document = WSUtil.parseXml(request);
			BUILDING_ID = WSUtil.getXmlNodeText(document,
					"//request/BUILDING_ID");
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("BUILDING_ID", BUILDING_ID);
			String result = crm2BuildingService.delBuilding2Discount(m1);
			if (result != null && result.contains("&lt;"))
				result = result.replace("&lt;", "<");
			return result;
		} catch (Exception e) {
			logger.error("delBuilding2Discount�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * ��ȡAGENT��Ϣ
	 * 
	 * @author chenlj
	 * @param request
	 *            <request> 
	 *            <channelId>11040611</channelId> 
	 *            <staffCode>bj1001</staffCode>
	 *            <agentName>������</agentName> </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = {
			@Node(xpath = "//request/agentName", caption = "��Ŀ����"),
			@Node(xpath = "//request/channelId", caption = "����id"),
			@Node(xpath = "//request/staffCode", caption = "Ա�����")})
	public String getAgentInfoByName(
			@WebParam(name = "request") String request) {
		Long partyId = null;
		try {
			Document document = WSUtil.parseXml(request);
			// ��ѡ����
			String agentName = WSUtil.getXmlNodeText(document,
					"//request/agentName");
			Map m = new HashMap();
			m.put("agentName", agentName);
			 String result = crm2BuildingService.getAgentInfoByName(m);
			 return result;
		} catch (Exception e) {
			logger.error("getAgentInfoByName�ӿ�:" + request, e);
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * ����Ȩ�ޱ����ѯ�ͻ���Ϣ�ӿ�
	 * @param request
	 <request>
	 	<staffCode>bj1001</staffCode>
	 	<dimensionValue>2600000001</dimensionValue>
	 	<manageCd>BLD003</manageCd>
	 	<channelId>100226398</channelId>
	 </request>
	 * @return
	 */
	@WebMethod
	@Required(nodes = { 
			@Node(xpath = "//request/staffCode", caption = "Ա������"),
			@Node(xpath = "//request/dimensionValue", caption = "ά��ֵ"),
			@Node(xpath = "//request/manageCd", caption = "Ȩ�ޱ���"),
			@Node(xpath = "//request/channelId", caption = "������ҵ�����")})
	public String getPartyIdByManageCd(@WebParam(name = "request") String request) {
		try {
			Document doc = WSUtil.parseXml(request);
			String dimensionValue = WSUtil.getXmlNodeText(doc, "/request/dimensionValue");
			String manageCd = WSUtil.getXmlNodeText(doc, "/request/manageCd");
			Map m = new HashMap();
			m.put("dimensionValue", dimensionValue);
			m.put("manageCd", manageCd);
			String result = crm2BuildingService.getPartyIdByManageCd(m);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse("1", e.getMessage());
		}
	}

	/**
	 * ����buildingId��ѯ�����пͻ���Ϣ
	 * @param request
	 * @return
	 */
	@WebMethod
	public String queryPartyInfoByBuildingId(@WebParam(name = "request") String request) {
		try {
			Document document = WSUtil.parseXml(request);
			String buildingId = WSUtil.getXmlNodeText(document, "request/buildingId");
			String curPage = WSUtil.getXmlNodeText(document, "request/curPage");
			String pageSize = WSUtil.getXmlNodeText(document, "request/pageSize");
			String partyName = WSUtil.getXmlNodeText(document, "request/partyName");
			String addressDesc = WSUtil.getXmlNodeText(document, "request/addressDesc");
			
			if(buildingId.equals("")||buildingId==null||curPage.equals("")||curPage==null||
					pageSize.equals("")||pageSize==null){
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "buildingId,curPage,pageSize�п�ֵ!");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("buildingId", Long.parseLong(buildingId));
			map.put("curPage",  Integer.parseInt(curPage));
			map.put("pageSize", Integer.parseInt(pageSize));
			map.put("partyName", partyName);
			map.put("addressDesc", addressDesc);
			String result = crm2BuildingService.queryPartyInfoByBuildingId(map);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
	}
	
	/**
     * CRM��APPʵ���Ʒ��۸�����
     * @param request
     * @return
     * @throws RemoteException
     */
    public String queryCert(@WebParam(name = "request") String request) throws RemoteException{
    	CloseableHttpClient httpclient = null;
    	try {		
    		GetRebateAddress ga = new GetRebateAddress();
    		
		    String appId = ga.getAddress("APP_ID");				//��ʹ��crmϵͳ��Ӧ��Ӧ��Id,1001����������
		    String appSecret = ga.getAddress("APP_SECRET"); 	// appId��Ӧ��ǩ����Կ
		    String secretKey = ga.getAddress("SECRET_KEY");		//appId��Ӧ��3des������Կ
			//String serverIp = ga.getAddress("SERVER_IP");		//���ɽڵ㹫��IP,�������ã�����������ʹ����ƽ̨���صĵ�������IP
			String nonce = ga.getAddress("NONCE");				// ����ַ���,���ù���������
	
			Document document = WSUtil.parseXml(request);
			String decodeId = WSUtil.getXmlNodeText(document, "request/decodeId");
			String serverIp = WSUtil.getXmlNodeText(document, "request/serverIP");
			String result = "";
	        String timestamp = String.valueOf(System.currentTimeMillis());
	        String query = "{\"decodeId\":\"" + decodeId + "\"}";
			
			httpclient = HttpClients.createDefault();
			
			StringBuffer sbData = new StringBuffer();
	        sbData.append(appId).append(appSecret).append(query).append(nonce).append(timestamp);
	        String signature = DigestUtils.shaHex(sbData.toString());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("appId", appId));
            params.add(new BasicNameValuePair("timestamp", timestamp));
            params.add(new BasicNameValuePair("nonce", nonce));
            params.add(new BasicNameValuePair("query", query));
            params.add(new BasicNameValuePair("signature", signature));
            
            String url = "http://"+serverIp + "/api/v1/queryCert?" + URLEncodedUtils.format(params, Consts.UTF_8);
            HttpGet httpGetRequest = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGetRequest);

            //��ȡ����
            String resStr = EntityUtils.toString(response.getEntity());
            //���֤����
            resStr = resStr.replaceAll("\n","");
            result = decode(resStr,secretKey);
            StringBuffer sb = new StringBuffer();
			sb.append("<response>");
			if(result != null){
				sb.append("<resultCode>").append("0").append("</resultCode>");
				sb.append("<resultMsg>").append(result).append("</resultMsg>");
			}else{
				sb.append("<resultCode>").append("1").append("</resultCode>");
				sb.append("<resultMsg>").append("���ؽ��Ϊ��:"+result).append("</resultMsg>");
			}
			sb.append("</response>");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		} finally {
            try {
            	httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, "queryCer�ر�����==="+e.toString());
            }            
        }
    }
    
    private String decode(String data,String secretKey) throws JSONException{
    	String certInfo=null;
    	
        Map<String, Object> resultMap=jsonToMap(data);
        String resultContent = resultMap.get("resultContent") == null ? null : resultMap.get("resultContent").toString();
        
        if(resultContent == null){
        	certInfo = resultMap.toString();
        	return certInfo;
        }
        JSONObject resultJSon = new JSONObject(resultContent);
        String certificate=resultJSon.getString("certificate");
        certificate = certificate.replaceAll("\n","");
        
    try {
    	certInfo = com.linkage.bss.crm.ws.util.Des33.decode1(certificate);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return certInfo;
    } 
    
    /**
     * Json����Map 
     * @param jsonString
     * @return
     * @throws JSONException
     */
    private Map<String, Object> jsonToMap(String jsonString) throws JSONException {
        //JSONObject������"{"��ͷ  
        JSONObject jsonObject = new JSONObject(jsonString);  
        Map<String, Object> resultMap = new HashMap<String, Object>();  
        Iterator<String> iter = jsonObject.keys();  
        String key=null;  
        Object value=null;  
        while (iter.hasNext()) {  
            key=iter.next();  
            value=jsonObject.get(key);  
            resultMap.put(key, value);  
        }  
        return resultMap;  
    }  

    /**
	 * ����buildingId��ѯ�����пͻ���Ϣ
	 * @param request
	 * @return
	 */
	@WebMethod
	public String getCheckResult(@WebParam(name = "request") String request) {
		
		Result result = null;
		StringBuffer sb = new StringBuffer();
		final com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			
			Document document = WSUtil.parseXml(request);
			Date requestTime =  new Date();
			String logId = intfSMO.getIntfCommonSeq();
			String infoXML = document.selectSingleNode("//request").asXML();
			intfSMO.saveRequestInfo(logId, "getCheckResult", "getCheckResultXML", infoXML, requestTime);

			String serialNo = WSUtil.getXmlNodeText(document, "request/serialNo");
			String msgname = WSUtil.getXmlNodeText(document, "request/msgname");
			String msgid = WSUtil.getXmlNodeText(document, "request/msgid");
			
			//��ʼ��һ��
			ClientOnJdkHttpConnection client = new ClientOnJdkHttpConnection();
			client.init();
			
			try {
				Request requestle = new Request.Builder()
						.gmsfhm(msgid)
						.xm(msgname)
						.cym("")
						.csrq("") 
						.build();
				result = client.invoke(requestle);
				JSONObject jsonObject = new JSONObject(gson.toJson(result));
				String jsonString = jsonObject.toString();
				//
				intfSMO.saveResponseInfo(logId, "CrmJson", "getCheckResultXML", infoXML, requestTime, jsonString, new Date(),
						"1", "0");
				
				JSONObject outputResultJson = new JSONObject();
				JSONObject inputResultJson = new JSONObject();
				JSONObject gmjsonObject = new JSONObject();
				JSONObject xmjsonObject = new JSONObject();
				inputResultJson = (JSONObject) jsonObject.get("input");
				outputResultJson = (JSONObject) jsonObject.get("output");
				String gmsfhm = (String) inputResultJson.get("gmsfhm");
				String xm = (String) inputResultJson.get("xm");
				String xp = (String) outputResultJson.get("xp");
				String  resultCode = (String) jsonObject.get("resultCode");
				sb.append("<CheckResult><ResultCode>").append(resultCode);
				sb.append("</ResultCode><Photo>").append(xp).append("</Photo>");
				sb.append("<Gender></Gender><Birthday></Birthday><Nationality></Nationality>");
				sb.append("<Address></Address><SignUnit/><Degree/><Career/><AddressCode/>");
				sb.append("<AddressCount/><Ssssxq></Ssssxq><Oid></Oid></CheckResult>");
			} catch (Exception e) {
				e.printStackTrace();
				return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return WSUtil.buildResponse(ResultCode.SYSTEM_ERROR, e.toString());
		}
	}
    
}
