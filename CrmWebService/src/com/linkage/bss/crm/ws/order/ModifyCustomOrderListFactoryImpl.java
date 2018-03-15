package com.linkage.bss.crm.ws.order;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.Cn2Spell;
import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.ws.others.ShowCsLogFactory;
import com.linkage.bss.crm.ws.util.WSUtil;

public class ModifyCustomOrderListFactoryImpl implements ModifyCustomOrderListFactory {

	private static Log logger = Log.getLog(ModifyCustomOrderListFactoryImpl.class);
	private CustFacade custFacade;
	private IntfSMO intfSMO;
	private boolean isPrintLog = ShowCsLogFactory.isShowCsLog();

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	@Override
	public JSONObject generateOrderList(Document request) {
		logger.debug("��ʼ����json");
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator();
		JSONObject data = new JSONObject();
		String areaId = WSUtil.getXmlNodeText(request, "/request/areaId");
		String partyId = WSUtil.getXmlNodeText(request, "/request/custInfo/partyId");
		String custName = WSUtil.getXmlNodeText(request, "/request/custInfo/custName");
		String custType = WSUtil.getXmlNodeText(request, "/request/custInfo/custType");
		String cerdAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone1");
		String custLevel = WSUtil.getXmlNodeText(request, "/request/custInfo/custLevel");
		String custBusinessPwd = WSUtil.getXmlNodeText(request, "/request/custInfo/custBusinessPwd");
		String custQueryPwd = WSUtil.getXmlNodeText(request, "/request/custInfo/custQueryPwd");
		String postCode = WSUtil.getXmlNodeText(request, "/request/custInfo/postCode");

		// ���충�����ﳵ
		JSONObject orderListInfo = new JSONObject();
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", 1); // order_list_type 2��ʾ�������� 1?
		orderListInfo.put("staffId", WSUtil.getXmlNodeText(request, "/request/staffId")); // Ӫҵ�����Ա����
		// ��staffCodeȡ��
		orderListInfo.put("channelId", WSUtil.getXmlNodeText(request, "/request/channelId"));
		orderListInfo.put("areaId", Integer.valueOf(areaId));
		orderListInfo.put("areaCode", WSUtil.getXmlNodeText(request, "/request/areaCode"));
		orderListInfo.put("statusCd", "P");
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
		busiObj.put("instId", partyId);//		

		// ҵ��������
		JSONObject boActionType = new JSONObject();
		boActionType.put("actionClassCd", "1");
		boActionType.put("boActionTypeCd", "C2");
		boActionType.put("name", "�޸Ŀͻ�");

		// �������
		JSONArray busiComponentInfoArrary = new JSONArray();
		JSONObject busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustBasicInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boCustIdentityInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustExtensionInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boCustSegmentInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boPartyRoleInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);

		long start = System.currentTimeMillis();
		// ����ͻ�������Ϣ
		Party party = custFacade.getPartyById(partyId);
		System.out.println("generateOrderList.custFacade.getPartyById(���ݿͻ�ID��ѯ�ͻ���Ϣ) ִ��ʱ��:"
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		String oldPostCode = intfSMO.getPostCodeByPartyId(Long.valueOf(partyId)); // ��ѯ��������
		if (isPrintLog) {
			System.out.println("generateOrderList.intfSMO.getPostCodeByPartyId(���ݿͻ�Id��ѯ��������) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}
		start = System.currentTimeMillis();
		Map<String, Object> pwInfo = intfSMO.getPartyPW(partyId);
		if (isPrintLog) {
			System.out.println("generateOrderList.intfSMO.getPartyPW(��ȡ�ͻ�����) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}

		if (party == null) {
			return null;
		}
		JSONArray boCustInfoArray = new JSONArray();
		JSONObject boCustInfo = new JSONObject();
		boCustInfo.put("addressId", party.getAddressId());
		boCustInfo.put("addressStr", "");
		boCustInfo.put("areaId", areaId);
		boCustInfo.put("areaName", party.getAreaName());//
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("buiPwdTip", "");
		boCustInfo.put("custBrand", "");
		if (StringUtils.isNotBlank(custLevel)) {
			boCustInfo.put("custLevel", custLevel);
		} else {
			boCustInfo.put("custLevel", StringUtils.isNotBlank(party.getCust_level_name()) ? party.getCust_level_name()
					: "");
		}
		boCustInfo.put("custSegment", "");
		boCustInfo.put("custStrategy", "");
		boCustInfo.put("custSubBrand", "");
		boCustInfo.put("defaultIdType", party.getDefaultIdType() != null ? party.getDefaultIdType() : "");
		boCustInfo.put("departmentId", null);
		boCustInfo.put("departmentName", "");
		boCustInfo.put("industryClassCd", null);
		boCustInfo.put("industryClassStr", "");
		boCustInfo.put("mailAddressId", null);
		if (StringUtils.isNotBlank(cerdAddr)) {
			boCustInfo.put("mailAddressStr", cerdAddr);
		} else {
			boCustInfo.put("mailAddressStr", StringUtils.isNotBlank(party.getMailAddressStr()) ? party
					.getMailAddressStr() : "");
		}
		boCustInfo.put("name", custName);
		if (StringUtils.isNotBlank(custType)) {
			boCustInfo.put("partyTypeCd", custType);
		} else {
			boCustInfo.put("partyTypeCd", party.getPartyTypeCd() != null ? party.getPartyTypeCd() : "");
		}
		boCustInfo.put("professionCd", "");
		if (StringUtils.isNotBlank(postCode)) {
			boCustInfo.put("postCode", postCode);
		} else {
			boCustInfo.put("postCode", oldPostCode);
		}
		boCustInfo.put("professionStr", "");
		boCustInfo.put("qryEffTime", null);
		boCustInfo.put("qryPwdTip", "");
		if (StringUtils.isNotBlank(custQueryPwd)) {
			boCustInfo.put("queryPassword", custQueryPwd);
		} else {
			boCustInfo.put("queryPassword", StringUtils.isNotBlank((String) pwInfo.get("QUERY_PASSWORD")) ? pwInfo
					.get("QUERY_PASSWORD") : "");
		}
		if (StringUtils.isNotBlank(custBusinessPwd)) {
			boCustInfo.put("businessPassword", custBusinessPwd);
		} else {
			boCustInfo.put("businessPassword",
					StringUtils.isNotBlank((String) pwInfo.get("BUSINESS_PASSWORD")) ? pwInfo.get("BUSINESS_PASSWORD")
							: "");
		}
		boCustInfo.put("segmentId", "");
		boCustInfo.put("simpleSpell", Cn2Spell.getCapSpell(custName));
		boCustInfo.put("state", "ADD");
		boCustInfo.put("statusCd", "P");
		if (StringUtils.isNotBlank(contactPhone1)) {
			boCustInfo.put("telNumber", contactPhone1);
		} else {
			boCustInfo.put("telNumber", StringUtils.isNotBlank(party.getLinkPhone()) ? party.getLinkPhone() : "");
		}
		boCustInfoArray.add(boCustInfo);
		boCustInfo = new JSONObject();
		boCustInfo.put("addressId", party.getAddressId());
		boCustInfo.put("addressStr", party.getAddressStr());
		boCustInfo.put("areaId", party.getAreaId());
		boCustInfo.put("areaName", party.getAreaName());//
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("buiPwdTip", "");
		boCustInfo.put("custBrand", "");
		boCustInfo.put("custLevel", null);
		boCustInfo.put("custSegment", "");
		boCustInfo.put("custStrategy", "");
		boCustInfo.put("custSubBrand", "");
		boCustInfo.put("defaultIdType", party.getDefaultIdType());
		boCustInfo.put("departmentId", null);
		boCustInfo.put("departmentName", "");
		boCustInfo.put("industryClassCd", null);
		boCustInfo.put("industryClassStr", "");
		boCustInfo.put("mailAddressId", party.getMailAddressId() != null ? party.getMailAddressId() : "");
		boCustInfo.put("mailAddressStr", party.getMailAddressStr() != null ? party.getMailAddressStr() : "");
		boCustInfo.put("name", party.getPartyName());
		boCustInfo.put("partyTypeCd", party.getPartyTypeCd());
		boCustInfo.put("professionCd", "");
		boCustInfo.put("postCode", oldPostCode != null ? oldPostCode : "");
		boCustInfo.put("professionStr", "");
		boCustInfo.put("qryEffTime", null);
		boCustInfo.put("qryPwdTip", "");
		boCustInfo.put("queryPassword", party.getQueryPassword() != null ? party.getQueryPassword() : "");
		boCustInfo.put("businessPassword", party.getBusinessPassword() != null ? party.getBusinessPassword() : "");
		boCustInfo.put("segmentId", "");
		boCustInfo.put("simpleSpell", party.getSimpleSpell());
		boCustInfo.put("state", "DEL");
		boCustInfo.put("statusCd", "P");
		boCustInfo.put("telNumber", party.getLinkPhone() != null ? party.getLinkPhone() : "");
		boCustInfoArray.add(boCustInfo);

		// ����ͻ�֤����Ϣ
		Map<String, String> param = new HashMap<String, String>();
		param.put("partyId", partyId);
		start = System.currentTimeMillis();
		Map<String, Object> ifPkMap = intfSMO.qryIfPkByPartyId(param);
		if (isPrintLog) {
			System.out.println("getBrandLevelDetail.intfSMO.qryIfPkByPartyId(���ݿͻ�ID���ҿͻ��Ƿ�Ϊ�����ͻ��ı��ֵ) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}
		if (ifPkMap.get("IFPK") != null && ifPkMap.get("IFPK").equals("1")) {
			// ����ͻ�֤����Ϣ
			String cerdType = WSUtil.getXmlNodeText(request, "/request/custInfo/cerdType");
			String cerdValue = "";
			JSONArray boCustIdentitieArray = new JSONArray();
			JSONObject boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustIdentitie.put("identidiesTypeCd", cerdType);
			boCustIdentitie.put("identityNum", WSUtil.getXmlNodeText(request, "/request/custInfo/cerdValue"));
			boCustIdentitie.put("state", "ADD");
			boCustIdentitie.put("statusCd", "P");
			boCustIdentitieArray.add(boCustIdentitie);
			boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			// ���ݿͻ�ID��ѯ�ͻ�֤����Ϣ by C
			Map identifyMap = intfSMO.queryIdentifyList(Long.valueOf(partyId));
			if (identifyMap.get("IDENTIDIES_TYPE_CD").equals(cerdType)) {
				cerdValue = identifyMap.get("IDENTITY_NUM").toString();
			}
			boCustIdentitie.put("identidiesTypeCd", cerdType);
			boCustIdentitie.put("identityNum", cerdValue);
			boCustIdentitie.put("state", "DEL");
			boCustIdentitie.put("statusCd", "P");
			boCustIdentitieArray.add(boCustIdentitie);
			data.put("boCustIdentities", boCustIdentitieArray);
		} else {
			// �ͻ�֤�������޸�
			JSONArray boCustIdentitieArray = new JSONArray();
			JSONObject boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustIdentitie.put("identidiesTypeCd", "1");
			boCustIdentitie.put("identityNum", "1");
			boCustIdentitie.put("state", "KIP");// ��������
			boCustIdentitie.put("statusCd", "P");
			boCustIdentitieArray.add(boCustIdentitie);
			data.put("boCustIdentities", boCustIdentitieArray);
		}

		// ����ͻ���չ��Ϣ
		JSONArray boCustProfileArray = new JSONArray();
		// ��ѯ�ͻ���չ��Ϣ BY C
		String emailAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/emailAddr");
		String custAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr");
		String linkMan = WSUtil.getXmlNodeText(request, "/request/custInfo/linkMan");
		String creditsAccNbr = WSUtil.getXmlNodeText(request, "/request/custInfo/creditsAccNbr");
		String contactPhone2 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone2");
		
		//��������Ϣ
		String zrrXm = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrXm");
		String zrrZjlx = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrZjlx");
		String zrrZjhm = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrZjhm");
		String zrrLxr = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrLxr");
		
		String oldZrrXm = null;
		String oldZrrZjlx = null;
		String oldZrrZjhm = null;
		String oldZrrLxr = null;
		
		String oldEmailAddr = null;
		String oldCustAddr = null;
		String oldLinkMan = null;
		String oldCreditsAccNbr = null;
		String oldContactPhone2 = null;
		
		

		Map custProfiles = intfSMO.queryCustProfiles(Long.valueOf(partyId));
		if (custProfiles != null) {
			oldEmailAddr = (String) custProfiles.get("EMAIL_ADDRESS");
			oldCustAddr = (String) custProfiles.get("CONTACT_ADDRESS");
			oldLinkMan = (String) custProfiles.get("CONTACT_NAME");
			oldCreditsAccNbr = (String) custProfiles.get("CREDITS_ACCNBR");
			oldContactPhone2 = (String) custProfiles.get("MOBILE");
			oldZrrXm = (String) custProfiles.get("ZRRXM");
			oldZrrZjlx = (String) custProfiles.get("ZRRZJLX");
			oldZrrZjhm = (String) custProfiles.get("ZRRZJHM");
			oldZrrLxr = (String) custProfiles.get("ZRRLXR");
		}
		//����������
		if (StringUtils.isNotBlank(zrrXm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201519");
			boCustProfile.put("profileValue", zrrXm);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrXm)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201519");
				boCustProfile.put("profileValue", oldZrrXm);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		//������֤������
		if (StringUtils.isNotBlank(zrrXm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201520");
			boCustProfile.put("profileValue", zrrZjlx);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrZjlx)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201520");
				boCustProfile.put("profileValue", oldZrrZjlx);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		//������֤������
		if (StringUtils.isNotBlank(zrrZjhm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201521");
			boCustProfile.put("profileValue", zrrZjhm);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrZjhm)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201521");
				boCustProfile.put("profileValue", oldZrrZjlx);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		//��������ϵ��
		if (StringUtils.isNotBlank(zrrLxr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201522");
			boCustProfile.put("profileValue", zrrLxr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrLxr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201522");
				boCustProfile.put("profileValue", oldZrrLxr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		// email��ַ
		if (StringUtils.isNotBlank(emailAddr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "333333");
			boCustProfile.put("profileValue", emailAddr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldEmailAddr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "333333");
				boCustProfile.put("profileValue", oldEmailAddr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// �ռ���ַ
		if (StringUtils.isNotBlank(custAddr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "900002");
			boCustProfile.put("profileValue", custAddr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldCustAddr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "900002");
				boCustProfile.put("profileValue", oldCustAddr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// ��ϵ��
		if (StringUtils.isNotBlank(linkMan)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "10022");
			boCustProfile.put("profileValue", linkMan);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldLinkMan)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "10022");
				boCustProfile.put("profileValue", oldLinkMan);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// ���������û�����
		if (StringUtils.isNotBlank(creditsAccNbr)) {
			JSONObject boCustProfile = new JSONObject();
			// ���Ϊ��-1����ʱ��Լ��Ϊȡ������
			if (!"-1".equals(creditsAccNbr)) {
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "310000015");
				boCustProfile.put("profileValue", creditsAccNbr);
				boCustProfile.put("state", "ADD");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
			if (StringUtils.isNotBlank(oldCreditsAccNbr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "310000015");
				boCustProfile.put("profileValue", oldCreditsAccNbr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}

		// ��ϵ�绰��������
		if (StringUtils.isNotBlank(contactPhone2)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "44");
			boCustProfile.put("profileValue", contactPhone2);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldContactPhone2)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "44");
				boCustProfile.put("profileValue", oldContactPhone2);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}

		// ����ͻ��ֶ���Ϣ
		// JSONArray boCustSegmentInfoArray = new JSONArray();
		// JSONObject boCustSegmentInfo = new JSONObject();
		// boCustSegmentInfo.put("atomActionId",
		// boSeqCalculator.getNextAtomActionIdInteger());
		// boCustSegmentInfo.put("segmentId", "405");
		// boCustSegmentInfo.put("state", "ADD");
		// boCustSegmentInfo.put("statusCd", "P");
		// boCustSegmentInfoArray.add(boCustSegmentInfo);

		// �齨�ͻ�����
		data.put("boCustInfos", boCustInfoArray);
		data.put("boCustProfiles", boCustProfileArray);
		// data.put("boCustSegmentInfos", boCustSegmentInfoArray);

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
		orderList.put("isMutualModify", "INDIV_2_INDIV");
		orderList.put("custOrderList", custOrderListArray);
		orderList.put("orderListInfo", orderListInfo);
		orderList.put("orderListIdFrom", "0");
		JSONObject json = new JSONObject();
		json.put("orderList", orderList);

		return json;
	}

	@Override
	public JSONObject generateOrderList2(Document request) {

		logger.debug("��ʼ����json");
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator();
		JSONObject data = new JSONObject();
		String areaId = WSUtil.getXmlNodeText(request, "/request/areaId");
		String partyId = WSUtil.getXmlNodeText(request, "/request/custInfo/partyId");
		String custName = WSUtil.getXmlNodeText(request, "/request/custInfo/custName");
		String custType = WSUtil.getXmlNodeText(request, "/request/custInfo/custType");
		String cerdAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/cerdAddr");
		String contactPhone1 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone1");
		String custLevel = WSUtil.getXmlNodeText(request, "/request/custInfo/custLevel");
		String custBusinessPwd = WSUtil.getXmlNodeText(request, "/request/custInfo/custBusinessPwd");
		String custQueryPwd = WSUtil.getXmlNodeText(request, "/request/custInfo/custQueryPwd");
		String postCode = WSUtil.getXmlNodeText(request, "/request/custInfo/postCode");
		String ctype = WSUtil.getXmlNodeText(request, "/request/custInfo/cerdType");
		
		
		

		// ���충�����ﳵ
		JSONObject orderListInfo = new JSONObject();
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", 2); // order_list_type 2��ʾ�������� 1?
		orderListInfo.put("staffId", WSUtil.getXmlNodeText(request, "/request/staffId")); // Ӫҵ�����Ա����
		// ��staffCodeȡ��
		orderListInfo.put("channelId", WSUtil.getXmlNodeText(request, "/request/channelId"));
		orderListInfo.put("areaId", Integer.valueOf(areaId));
		orderListInfo.put("areaCode", WSUtil.getXmlNodeText(request, "/request/areaCode"));
		orderListInfo.put("statusCd", "P");
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
		busiObj.put("instId", partyId);//		

		// ҵ��������
		JSONObject boActionType = new JSONObject();
		boActionType.put("actionClassCd", "1");
		boActionType.put("boActionTypeCd", "C2");
		boActionType.put("name", "�޸Ŀͻ�");

		// �������
		JSONArray busiComponentInfoArrary = new JSONArray();
		JSONObject busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustBasicInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boCustIdentityInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		busiComponentInfo = new JSONObject();
		busiComponentInfo.put("behaviorFlag", "001111");
		busiComponentInfo.put("busiComponentCode", "boCustExtensionInfos");
		busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boCustSegmentInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);
		// busiComponentInfo = new JSONObject();
		// busiComponentInfo.put("behaviorFlag", "001111");
		// busiComponentInfo.put("busiComponentCode", "boPartyRoleInfos");
		// busiComponentInfoArrary.add(busiComponentInfo);

		long start = System.currentTimeMillis();
		// ����ͻ�������Ϣ
		Party party = custFacade.getPartyById(partyId);
		System.out.println("generateOrderList.custFacade.getPartyById(���ݿͻ�ID��ѯ�ͻ���Ϣ) ִ��ʱ��:"
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		String oldPostCode = intfSMO.getPostCodeByPartyId(Long.valueOf(partyId)); // ��ѯ��������
		if (isPrintLog) {
			System.out.println("generateOrderList.intfSMO.getPostCodeByPartyId(���ݿͻ�Id��ѯ��������) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}
		start = System.currentTimeMillis();
		Map<String, Object> pwInfo = intfSMO.getPartyPW(partyId);
		if (isPrintLog) {
			System.out.println("generateOrderList.intfSMO.getPartyPW(��ȡ�ͻ�����) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}

		if (party == null) {
			return null;
		}
		JSONArray boCustInfoArray = new JSONArray();
		JSONObject boCustInfo = new JSONObject();
		boCustInfo.put("addressId", party.getAddressId());
		boCustInfo.put("addressStr", "");
		boCustInfo.put("areaId", areaId);
		boCustInfo.put("areaName", party.getAreaName());//
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("buiPwdTip", "");
		boCustInfo.put("custBrand", "");
		if (StringUtils.isNotBlank(custLevel)) {
			boCustInfo.put("custLevel", custLevel);
		} else {
			boCustInfo.put("custLevel", StringUtils.isNotBlank(party.getCust_level_name()) ? party.getCust_level_name()
					: "");
		}
		boCustInfo.put("custSegment", "");
		boCustInfo.put("custStrategy", "");
		boCustInfo.put("custSubBrand", "");
		boCustInfo.put("defaultIdType", ctype != null ? Integer.valueOf(ctype) : "");
		boCustInfo.put("departmentId", null);
		boCustInfo.put("departmentName", "");
		boCustInfo.put("industryClassCd", null);
		boCustInfo.put("industryClassStr", "");
		boCustInfo.put("mailAddressId", null);
		if (StringUtils.isNotBlank(cerdAddr)) {
			boCustInfo.put("mailAddressStr", cerdAddr);
		} else {
			boCustInfo.put("mailAddressStr", StringUtils.isNotBlank(party.getMailAddressStr()) ? party
					.getMailAddressStr() : "");
		}
		boCustInfo.put("name", custName);
		if (StringUtils.isNotBlank(custType)) {
			boCustInfo.put("partyTypeCd", custType);
		} else {
			boCustInfo.put("partyTypeCd", party.getPartyTypeCd() != null ? party.getPartyTypeCd() : "");
		}
		boCustInfo.put("professionCd", "");
		if (StringUtils.isNotBlank(postCode)) {
			boCustInfo.put("postCode", postCode);
		} else {
			boCustInfo.put("postCode", oldPostCode);
		}
		boCustInfo.put("professionStr", "");
		boCustInfo.put("qryEffTime", null);
		boCustInfo.put("qryPwdTip", "");
		if (StringUtils.isNotBlank(custQueryPwd)) {
			boCustInfo.put("queryPassword", custQueryPwd);
		} else {
			boCustInfo.put("queryPassword", StringUtils.isNotBlank((String) pwInfo.get("QUERY_PASSWORD")) ? pwInfo
					.get("QUERY_PASSWORD") : "");
		}
		if (StringUtils.isNotBlank(custBusinessPwd)) {
			boCustInfo.put("businessPassword", custBusinessPwd);
		} else {
			boCustInfo.put("businessPassword",
					StringUtils.isNotBlank((String) pwInfo.get("BUSINESS_PASSWORD")) ? pwInfo.get("BUSINESS_PASSWORD")
							: "");
		}
		boCustInfo.put("segmentId", "");
		boCustInfo.put("simpleSpell", Cn2Spell.getCapSpell(custName));
		boCustInfo.put("state", "ADD");
		boCustInfo.put("statusCd", "P");
		boCustInfo.put("ifRealName", "1");
		if (StringUtils.isNotBlank(contactPhone1)) {
			boCustInfo.put("telNumber", contactPhone1);
		} else {
			boCustInfo.put("telNumber", StringUtils.isNotBlank(party.getLinkPhone()) ? party.getLinkPhone() : "");
		}

		boCustInfoArray.add(boCustInfo);
		boCustInfo = new JSONObject();
		boCustInfo.put("addressId", party.getAddressId());
		boCustInfo.put("addressStr", party.getAddressStr());
		boCustInfo.put("areaId", party.getAreaId());
		boCustInfo.put("areaName", party.getAreaName());//
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("buiPwdTip", "");
		boCustInfo.put("custBrand", "");
		boCustInfo.put("custLevel", null);
		boCustInfo.put("custSegment", "");
		boCustInfo.put("custStrategy", "");
		boCustInfo.put("custSubBrand", "");
		boCustInfo.put("defaultIdType", party.getDefaultIdType());
		boCustInfo.put("departmentId", null);
		boCustInfo.put("departmentName", "");
		boCustInfo.put("industryClassCd", null);
		boCustInfo.put("industryClassStr", "");
		boCustInfo.put("mailAddressId", party.getMailAddressId() != null ? party.getMailAddressId() : "");
		boCustInfo.put("mailAddressStr", party.getMailAddressStr() != null ? party.getMailAddressStr() : "");
		boCustInfo.put("name", party.getPartyName());
		boCustInfo.put("partyTypeCd", party.getPartyTypeCd());
		boCustInfo.put("professionCd", "");
		boCustInfo.put("postCode", oldPostCode != null ? oldPostCode : "");
		boCustInfo.put("professionStr", "");
		boCustInfo.put("qryEffTime", null);
		boCustInfo.put("qryPwdTip", "");
		boCustInfo.put("queryPassword", party.getQueryPassword() != null ? party.getQueryPassword() : "");
		boCustInfo.put("businessPassword", party.getBusinessPassword() != null ? party.getBusinessPassword() : "");
		boCustInfo.put("segmentId", "");
		boCustInfo.put("simpleSpell", party.getSimpleSpell());
		boCustInfo.put("state", "DEL");
		boCustInfo.put("statusCd", "P");
		boCustInfo.put("telNumber", party.getLinkPhone() != null ? party.getLinkPhone() : "");
		boCustInfoArray.add(boCustInfo);

		// ����ͻ�֤����Ϣ
		Map<String, String> param = new HashMap<String, String>();
		param.put("partyId", partyId);
		start = System.currentTimeMillis();
		Map<String, Object> ifPkMap = intfSMO.qryIfPkByPartyId(param);
		if (isPrintLog) {
			System.out.println("getBrandLevelDetail.intfSMO.qryIfPkByPartyId(���ݿͻ�ID���ҿͻ��Ƿ�Ϊ�����ͻ��ı��ֵ) ִ��ʱ��:"
					+ (System.currentTimeMillis() - start));
		}

		if (ifPkMap.get("IFPK") != null && ifPkMap.get("IFPK").toString().equals("1")) {
			// ����ͻ�֤����Ϣ
			String cerdType = WSUtil.getXmlNodeText(request, "/request/custInfo/cerdType");
			String cerdValue = "";
			JSONArray boCustIdentitieArray = new JSONArray();
			JSONObject boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustIdentitie.put("identidiesTypeCd", cerdType);
			boCustIdentitie.put("identityNum", WSUtil.getXmlNodeText(request, "/request/custInfo/cerdValue"));
			boCustIdentitie.put("state", "ADD");
			boCustIdentitie.put("statusCd", "P");
			boCustIdentitieArray.add(boCustIdentitie);
			boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			// ���ݿͻ�ID��ѯ�ͻ�֤����Ϣ by C
			Map identifyMap = intfSMO.queryIdentifyList(Long.valueOf(partyId));
			if (identifyMap != null && cerdType.equals(identifyMap.get("IDENTIDIES_TYPE_CD"))) {
				cerdValue = identifyMap.get("IDENTITY_NUM").toString();
				boCustIdentitie.put("identidiesTypeCd", cerdType);
				boCustIdentitie.put("identityNum", cerdValue);
				boCustIdentitie.put("state", "DEL");
				boCustIdentitie.put("statusCd", "P");
				boCustIdentitieArray.add(boCustIdentitie);
			}
			data.put("boCustIdentities", boCustIdentitieArray);
		} else {
			// �ͻ�֤�������޸�
			JSONArray boCustIdentitieArray = new JSONArray();
			JSONObject boCustIdentitie = new JSONObject();
			boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustIdentitie.put("identidiesTypeCd", "1");
			boCustIdentitie.put("identityNum", "1");
			boCustIdentitie.put("state", "KIP");// ��������
			boCustIdentitie.put("statusCd", "P");
			boCustIdentitieArray.add(boCustIdentitie);
			data.put("boCustIdentities", boCustIdentitieArray);
		}

		// ����ͻ���չ��Ϣ
		JSONArray boCustProfileArray = new JSONArray();
		// ��ѯ�ͻ���չ��Ϣ BY C
		String emailAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/emailAddr");
		String custAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr");
		String linkMan = WSUtil.getXmlNodeText(request, "/request/custInfo/linkMan");
		String creditsAccNbr = WSUtil.getXmlNodeText(request, "/request/custInfo/creditsAccNbr");
		String contactPhone2 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone2");
		
		//��������Ϣ
		String zrrXm = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrXm");
		String zrrZjlx = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrZjlx");
		String zrrZjhm = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrZjhm");
		String zrrLxr = WSUtil.getXmlNodeText(request, "/request/custInfo/zrrLxr");
		
		String oldZrrXm = null;
		String oldZrrZjlx = null;
		String oldZrrZjhm = null;
		String oldZrrLxr = null;
		
		String oldEmailAddr = null;
		String oldCustAddr = null;
		String oldLinkMan = null;
		String oldCreditsAccNbr = null;
		String oldContactPhone2 = null;
		
		

		Map custProfiles = intfSMO.queryCustProfiles(Long.valueOf(partyId));
		if (custProfiles != null) {
			oldEmailAddr = (String) custProfiles.get("EMAIL_ADDRESS");
			oldCustAddr = (String) custProfiles.get("CONTACT_ADDRESS");
			oldLinkMan = (String) custProfiles.get("CONTACT_NAME");
			oldCreditsAccNbr = (String) custProfiles.get("CREDITS_ACCNBR");
			oldContactPhone2 = (String) custProfiles.get("MOBILE");
			oldZrrXm = (String) custProfiles.get("ZRRXM");
			oldZrrZjlx = (String) custProfiles.get("ZRRZJLX");
			oldZrrZjhm = (String) custProfiles.get("ZRRZJHM");
			oldZrrLxr = (String) custProfiles.get("ZRRLXR");
		}
		//����������
		if (StringUtils.isNotBlank(zrrXm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201519");
			boCustProfile.put("profileValue", zrrXm);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrXm)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201519");
				boCustProfile.put("profileValue", oldZrrXm);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		//������֤������
		if (StringUtils.isNotBlank(zrrXm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201520");
			boCustProfile.put("profileValue", zrrZjlx);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrZjlx)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201520");
				boCustProfile.put("profileValue", oldZrrZjlx);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		//������֤������
		if (StringUtils.isNotBlank(zrrZjhm)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201521");
			boCustProfile.put("profileValue", zrrZjhm);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrZjhm)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201521");
				boCustProfile.put("profileValue", oldZrrZjlx);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		//��������ϵ��
		if (StringUtils.isNotBlank(zrrLxr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201522");
			boCustProfile.put("profileValue", zrrLxr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldZrrLxr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "201522");
				boCustProfile.put("profileValue", oldZrrLxr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		
		// email��ַ
		if (StringUtils.isNotBlank(emailAddr) && !"null".equals(emailAddr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "333333");
			boCustProfile.put("profileValue", emailAddr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldEmailAddr) && !"null".equals(oldEmailAddr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "333333");
				boCustProfile.put("profileValue", oldEmailAddr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// �ռ���ַ
		if (StringUtils.isNotBlank(custAddr) && !"null".equals(custAddr)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "900002");
			boCustProfile.put("profileValue", custAddr);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldCustAddr) && !"null".equals(oldCustAddr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "900002");
				boCustProfile.put("profileValue", oldCustAddr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// ��ϵ��
		if (StringUtils.isNotBlank(linkMan) && !"null".equals(linkMan)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "10022");
			boCustProfile.put("profileValue", linkMan);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldLinkMan) && !"null".equals(oldLinkMan)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "10022");
				boCustProfile.put("profileValue", oldLinkMan);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}
		// ���������û�����
		if (StringUtils.isNotBlank(creditsAccNbr)) {
			JSONObject boCustProfile = new JSONObject();
			// ���Ϊ��-1����ʱ��Լ��Ϊȡ������
			if (!"-1".equals(creditsAccNbr)) {
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "310000015");
				boCustProfile.put("profileValue", creditsAccNbr);
				boCustProfile.put("state", "ADD");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
			if (StringUtils.isNotBlank(oldCreditsAccNbr)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "310000015");
				boCustProfile.put("profileValue", oldCreditsAccNbr);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}

		// ��ϵ�绰��������
		if (StringUtils.isNotBlank(contactPhone2) && !"null".equals(contactPhone2)) {
			JSONObject boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "44");
			boCustProfile.put("profileValue", contactPhone2);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfile.put("profileName", "");
			boCustProfileArray.add(boCustProfile);
			if (StringUtils.isNotBlank(oldContactPhone2) && !"null".equals(oldContactPhone2)) {
				boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustProfile.put("partyProfileCatgCd", "44");
				boCustProfile.put("profileValue", oldContactPhone2);
				boCustProfile.put("state", "DEL");
				boCustProfile.put("statusCd", "P");
				boCustProfile.put("profileName", "");
				boCustProfileArray.add(boCustProfile);
			}
		}

		// ����ͻ��ֶ���Ϣ
		// JSONArray boCustSegmentInfoArray = new JSONArray();
		// JSONObject boCustSegmentInfo = new JSONObject();
		// boCustSegmentInfo.put("atomActionId",
		// boSeqCalculator.getNextAtomActionIdInteger());
		// boCustSegmentInfo.put("segmentId", "405");
		// boCustSegmentInfo.put("state", "ADD");
		// boCustSegmentInfo.put("statusCd", "P");
		// boCustSegmentInfoArray.add(boCustSegmentInfo);

		// �齨�ͻ�����
		data.put("boCustInfos", boCustInfoArray);
		data.put("boCustProfiles", boCustProfileArray);
		// data.put("boCustSegmentInfos", boCustSegmentInfoArray);

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
		orderList.put("isMutualModify", "INDIV_2_INDIV");
		orderList.put("custOrderList", custOrderListArray);
		orderList.put("orderListInfo", orderListInfo);
		orderList.put("orderListIdFrom", "0");
		JSONObject json = new JSONObject();
		json.put("orderList", orderList);

		return json;

	}
}
