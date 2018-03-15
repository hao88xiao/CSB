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
		logger.debug("开始构造json");
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator();

		String areaId = WSUtil.getXmlNodeText(request, "/request/areaId");
		String partyId = commonSMO.generateSeq(Integer.valueOf(areaId), "PARTY", "1");
		String custName = WSUtil.getXmlNodeText(request, "/request/custInfo/custName");
		String areaCode = WSUtil.getXmlNodeText(request, "/request/custInfo/areaCode");

		// 构造订单购物车
		JSONObject orderListInfo = new JSONObject();
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", 2); // order_list_type 2标示电子渠道 1?--经过讨论修改为2
		orderListInfo.put("staffId", WSUtil.getXmlNodeText(request, "/request/staffId")); // 营业受理的员工号,从staffCode取得
		orderListInfo.put("channelId", WSUtil.getXmlNodeText(request, "/request/channelId"));
		orderListInfo.put("areaId", Integer.valueOf(areaId));
		orderListInfo.put("areaCode", areaCode == null ? areaCode : "010");
		orderListInfo.put("statusCd", "P"); // TODO P跟S的区别
		orderListInfo.put("partyId", partyId);

		// 客户受理单
		JSONObject custOrderList = new JSONObject();
		custOrderList.put("colNbr", "-1");
		custOrderList.put("partyId", partyId);

		// 业务动作信息
		JSONObject busiOrderInfo = new JSONObject();
		busiOrderInfo.put("seq", "-1");
		busiOrderInfo.put("statusCd", "S");

		// 业务对象
		JSONObject busiObj = new JSONObject();
		busiObj.put("objId", "");
		busiObj.put("name", "");

		// 业务动作类型
		JSONObject boActionType = new JSONObject();
		boActionType.put("actionClassCd", "1");
		boActionType.put("boActionTypeCd", "C1");
		boActionType.put("name", "新增客户");

		// 组件数据
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

		// 构造客户基本信息
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

		// 构造客户证件信息
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

		// 构造客户扩展信息
		JSONArray boCustProfileArray = new JSONArray();
		JSONObject boCustProfile = null;

		String emailAddr = WSUtil.getXmlNodeText(request, "/request/custInfo/emailAddr");
		if (StringUtils.isNotBlank(emailAddr)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "4"); // Email地址
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
		//			boCustProfile.put("partyProfileCatgCd", "900002"); // 收件地址
		//			boCustProfile.put("profileValue", WSUtil.getXmlNodeText(request, "/request/custInfo/custAddr"));
		//			boCustProfile.put("state", "ADD");
		//			boCustProfile.put("statusCd", "P");
		//			boCustProfileArray.add(boCustProfile);
		//		}

		String linkMan = WSUtil.getXmlNodeText(request, "/request/custInfo/linkMan");
		if (StringUtils.isNotBlank(linkMan)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "10022"); // 联系人
			boCustProfile.put("profileValue", linkMan);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}
		
		String realNameGrade = WSUtil.getXmlNodeText(request, "/request/custInfo/realNameGrade");
		if (StringUtils.isNotBlank(realNameGrade)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201538"); //实名制等级
			boCustProfile.put("profileValue", realNameGrade);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}else{
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "201538"); // 实名制等级 默认
			boCustProfile.put("profileValue", "0");
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}

		String contactPhone2 = WSUtil.getXmlNodeText(request, "/request/custInfo/contactPhone2");
		if (StringUtils.isNotBlank(contactPhone2)) {
			boCustProfile = new JSONObject();
			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
			boCustProfile.put("partyProfileCatgCd", "44"); // 联系电话（其他）
			boCustProfile.put("profileValue", contactPhone2);
			boCustProfile.put("state", "ADD");
			boCustProfile.put("statusCd", "P");
			boCustProfileArray.add(boCustProfile);
		}

		//		String postCode = WSUtil.getXmlNodeText(request, "/request/custInfo/postCode");
		//		if (StringUtils.isNotBlank(postCode)) {
		//			boCustProfile = new JSONObject();
		//			boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		//			boCustProfile.put("partyProfileCatgCd", "40074"); // 邮政编码
		//			boCustProfile.put("profileValue", postCode);
		//			boCustProfile.put("state", "ADD");
		//			boCustProfile.put("statusCd", "P");
		//			boCustProfileArray.add(boCustProfile);
		//		}

		// 构造客户分段信息
		JSONArray boCustSegmentInfoArray = new JSONArray();
		JSONObject boCustSegmentInfo = new JSONObject();
		boCustSegmentInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustSegmentInfo.put("segmentId", "405");
		boCustSegmentInfo.put("state", "ADD");
		boCustSegmentInfo.put("statusCd", "P");
		boCustSegmentInfoArray.add(boCustSegmentInfo);

		// 组建客户数据
		JSONObject data = new JSONObject();
		data.put("boCustInfos", boCustInfoArray);
		data.put("boCustIdentities", boCustIdentitieArray);
		data.put("boCustProfiles", boCustProfileArray);
		data.put("boCustSegmentInfos", boCustSegmentInfoArray);
		data.put("staff_id", WSUtil.getXmlNodeText(request, "/request/staffId"));
		// 组建业务动作
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

		// 构造业务动作,构造购物车
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
