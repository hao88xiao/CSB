package com.linkage.bss.crm.ws.order;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.Cn2Spell;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.cust.common.CustDomain;
import com.linkage.bss.crm.cust.smo.ICustBasicSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.ws.util.WSUtil;

public class CreateCustOrderListFactoryImpl implements CreateCustOrderListFactory {

	private static Log logger = Log.getLog(CreateCustOrderListFactoryImpl.class);

	private ICustBasicSMO custBasicSMO;

	private ICommonSMO commonSMO;

	public void setCustBasicSMO(ICustBasicSMO custBasicSMO) {
		this.custBasicSMO = custBasicSMO;
	}

	public void setCommonSMO(ICommonSMO commonSMO) {
		this.commonSMO = commonSMO;
	}

	@Override
	public JSONObject generateOrderList(Document request) {
		logger.debug("��ʼ����json");
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator();

		String areaId = WSUtil.getXmlNodeText(request, "/request/areaId");
		String partyId = commonSMO.generateSeq(Integer.valueOf(areaId), "PARTY", "1");
		String custName = WSUtil.getXmlNodeText(request, "/request/custInfo/custName");
		String areaCode = WSUtil.getXmlNodeText(request, "/request/custInfo/areaCode");

		// ���충�����ﳵ
		JSONObject orderListInfo = new JSONObject();
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", 2); // order_list_type 2��ʾ�������� 1?--���������޸�Ϊ2
		orderListInfo.put("staffId", WSUtil.getXmlNodeText(request, "/request/staffId")); // Ӫҵ�����Ա����,��staffCodeȡ��
		orderListInfo.put("channelId", WSUtil.getXmlNodeText(request, "/request/channelId"));
		orderListInfo.put("areaId", Integer.valueOf(areaId));
		orderListInfo.put("areaCode", areaCode == null ? areaCode : "010");
		orderListInfo.put("statusCd", "P"); // TODO P��S������
		orderListInfo.put("partyId", partyId);

		// �ͻ�����
		JSONObject custOrderList = new JSONObject();
		custOrderList.put("colNbr", "-1");
		custOrderList.put("partyId", partyId);

		// ҵ������Ϣ
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.put("seq", "-1");
		busiOrderInfo.put("statusCd", "S");

		// ҵ�����
		JSONObject busiObj = new JSONObject();
		busiObj.put("objId", "");
		busiObj.put("name", "");

		// ҵ��������
		JSONObject boActionType = new JSONObject();
		boActionType.put("actionClassCd", "1");
		boActionType.put("boActionTypeCd", "C1");
		boActionType.put("name", "�����ͻ�");

		// �������
		JSONArray busiComponentInfoArrary = new JSONArray();
		JSONObject busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustBasicInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustIdentityInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustExtensionInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustSegmentInfos");
		busiComponentInfoArrary.add(busiComponentInfo);

		// ����ͻ�������Ϣ
		JSONArray boCustInfoArray = new JSONArray();
		JSONObject boCustInfo = new JSONObject();
		boCustInfo.put("addressId", null);
		boCustInfo.put("addressStr", "");
		boCustInfo.put("areaId", areaId);
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("buiPwdTip", "");
		boCustInfo.put("custBrand", "");
		boCustInfo.put("custLevel", null);
		boCustInfo.put("custSegment", "");
		boCustInfo.put("custStrategy", "");
		boCustInfo.put("custSubBrand", "");
		boCustInfo.put("defaultIdType", WSUtil.getXmlNodeText(request, "//custInfo/cerdType"));
		boCustInfo.put("departmentId", null);
		boCustInfo.put("departmentName", "");
		boCustInfo.put("industryClassCd", null);
		boCustInfo.put("industryClassStr", "");
		boCustInfo.put("maiAddress", WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr"));
		boCustInfo.put("mailAddressId", null);
		boCustInfo.put("mailAddressStr", WSUtil.getXmlNodeText(request, "/request/custInfo/cerdAddr"));
		boCustInfo.put("name", custName);
		boCustInfo.put("partyGrade", WSUtil.getXmlNodeText(request, "/request/custInfo/partyGrade"));
		boCustInfo.put("partyTypeCd", WSUtil.getXmlNodeText(request, "/request/custInfo/custType"));
		boCustInfo.put("professionCd", null);
		boCustInfo.put("postCode", WSUtil.getXmlNodeText(request, "/request/custInfo/postCode"));
		boCustInfo.put("professionStr", "");
		boCustInfo.put("qryEffTime", null);
		boCustInfo.put("qryPwdTip", "");
		boCustInfo.put("queryPassword", WSUtil.getXmlNodeText(request, "/request/custInfo/queryPassword"));
		boCustInfo.put("businessPassword", WSUtil.getXmlNodeText(request, "/request/custInfo/businessPassword"));
		boCustInfo.put("segmentId", "");
		boCustInfo.put("simpleSpell", Cn2Spell.getCapSpell(custName));
		boCustInfo.put("state", "ADD");
		boCustInfo.put("statusCd", "P");
		boCustInfo.put("telNumber", WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone1"));
		boCustInfoArray.add(boCustInfo);

		// ����ͻ�֤����Ϣ
		JSONArray boCustIdentitieArray = new JSONArray();
		JSONObject boCustIdentitie = new JSONObject();
		String identityNum = custBasicSMO.generatePartyIdentity(Integer.parseInt(areaId));
		boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentitie.put("identidiesTypeCd", WSUtil.getXmlNodeText(request, "/request/custInfo/cerdType"));
		boCustIdentitie.put("identityNum", WSUtil.getXmlNodeText(request, "/request/custInfo/cerdValue"));
		boCustIdentitie.put("state", "ADD");
		boCustIdentitie.put("statusCd", "P");
		boCustIdentitieArray.add(boCustIdentitie);
		boCustIdentitie = new JSONObject();
		boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentitie.put("identidiesTypeCd", CustDomain.PARTY_IDENTITY_TYPE);
		boCustIdentitie.put("identityNum", identityNum);
		boCustIdentitie.put("state", "ADD");
		boCustIdentitie.put("statusCd", "P");
		boCustIdentitieArray.add(boCustIdentitie);

		// ����ͻ���չ��Ϣ
		JSONArray boCustProfileArray = new JSONArray();
		JSONObject boCustProfile = null;

		String emailAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/emailAddr");
		if (StringUtils.isNotBlank(emailAddr)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "4"); // Email��ַ
			boCustProfile.put("profileValue", WSUtil.getXmlNodeText(request, "/request/custInfo/emailAddr"));
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}
		//
		//		String custAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr");
		//		if (StringUtils.isNotBlank(custAddr)) {
		//			boCustProfile = new JSONObject();
		//			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		//			boCustProfile.put("partyProfileCatgCd", "900002"); // �ռ���ַ
		//			boCustProfile.put("profileValue", WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr"));
		//			boCustProfile.put("state", "ADD");
		//			boCustProfile.put("statusCd", "P");
		//			boCustProfileArray.add(boCustProfile);
		//		}

		String linkMan = WSUtil.getXmlNodeText(request, "/request/custInfo/linkMan");
		if (StringUtils.isNotBlank(linkMan)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "10022"); // ��ϵ��
			boCustProfile.put("profileValue", linkMan);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}
		
		String realNameGrade = WSUtil.getXmlNodeText(request, "/request/custInfo/realNameGrade");
		if (StringUtils.isNotBlank(realNameGrade)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201538"); //ʵ���Ƶȼ�
			boCustProfile.put("profileValue", realNameGrade);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}else{
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201538"); // ʵ���Ƶȼ� Ĭ��
			boCustProfile.put("profileValue", "0");
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}

		String contactPhone2 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone2");
		if (StringUtils.isNotBlank(contactPhone2)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "44"); // ��ϵ�绰��������
			boCustProfile.put("profileValue", contactPhone2);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}

		//		String postCode = WSUtil.getXmlNodeText(request, "/request/custInfo/postCode");
		//		if (StringUtils.isNotBlank(postCode)) {
		//			boCustProfile = new JSONObject();
		//			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		//			boCustProfile.put("partyProfileCatgCd", "40074"); // ��������
		//			boCustProfile.put("profileValue", postCode);
		//			boCustProfile.put("state", "ADD");
		//			boCustProfile.put("statusCd", "P");
		//			boCustProfileArray.add(boCustProfile);
		//		}

		// ����ͻ��ֶ���Ϣ
		JSONArray boCustSegmentInfoArray = new JSONArray();
		JSONObject boCustSegmentInfo = new JSONObject();
		boCustSegmentInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustSegmentInfo.put("segmentId", "405");
		boCustSegmentInfo.put("state", "ADD");
		boCustSegmentInfo.put("statusCd", "P");
		boCustSegmentInfoArray.add(boCustSegmentInfo);

		// �齨�ͻ�����
		JSONObject data = new JSONObject();
		data.put("boCustInfos", boCustInfoArray);
		data.put("boCustIdentities", boCustIdentitieArray);
		data.put("boCustProfiles", boCustProfileArray);
		data.put("boCustSegmentInfos", boCustSegmentInfoArray);
		data.put("staff_id", WSUtil.getXmlNodeText(request, "/request/staffId"));
		// �齨ҵ����
		JSONObject busiOrder = new JSONObject();
		busiOrder.put("busiOrderInfo", busiOrderInfo);
		busiOrder.put("busiObj", busiObj);
		busiOrder.put("boActionType", boActionType);
		busiOrder.put("areaId", areaId);
		busiOrder.put("busiComponentInfos", busiComponentInfoArrary);
		busiOrder.put("data", data);
		JSONArray busiOrderArray = new JSONArray();
		busiOrderArray.add(busiOrder);
		custOrderList.put("busiOrder", busiOrderArray);

		// ����ҵ����,���칺�ﳵ
		JSONObject orderList = new JSONObject();
		JSONArray custOrderListArray = new JSONArray();
		custOrderListArray.add(custOrderList);
		orderList.put("isMutualModify", "");
		orderList.put("custOrderList", custOrderListArray);
		orderList.put("orderListInfo", orderListInfo);
		orderList.put("orderListIdFrom", "0");
		JSONObject json = new JSONObject();
		json.put("orderList", orderList);

		return json;
	}
}
