package com.linkage.bss.crm.intf.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.cust.common.CustDomain;
import com.linkage.bss.crm.cust.smo.ICustBasicSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.model.PartyIdentity;
import com.linkage.bss.crm.model.PartyProfile;
import com.linkage.bss.crm.model.PartySegmentMemberList;
import com.linkage.bss.crm.so.commit.smo.ISoCommitSMO;

public class CustFacadeImpl implements CustFacade {

	private ISoCommitSMO soCommitSMO;

	private ICustBasicSMO custBasicSMO;

	private ICommonSMO commonSMO;

	public void setSoCommitSMO(ISoCommitSMO soCommitSMO) {
		this.soCommitSMO = soCommitSMO;
	}

	public void setCustBasicSMO(ICustBasicSMO custBasicSMO) {
		this.custBasicSMO = custBasicSMO;
	}

	public void setCommonSMO(ICommonSMO commonSMO) {
		this.commonSMO = commonSMO;
	}

	/**
	 * BSS日志
	 */
	private static final Log LOG = Log.getLog(CustFacadeImpl.class);

	@Override
	public Long saveCustInfo(Party party, String areaId, String channelId, String staffId) {
		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
		boSeqCalculator.reSetCalculator();

		String partyId = commonSMO.generateSeq(Integer.parseInt(areaId), "PARTY", "1");

		// 构造订单购物车
		JSONObject orderListInfo = new JSONObject();
		orderListInfo.put("olId", "-1");
		orderListInfo.put("olNbr", "-1");
		orderListInfo.put("olTypeCd", 1); // TODO 暂时使用营业受理的标志
		orderListInfo.put("staffId", staffId);
		orderListInfo.put("channelId", channelId);
		orderListInfo.put("areaId", areaId);
		orderListInfo.put("statusCd", "S"); // TODO P跟S的区别，P是校验通过的，已入库的，S是校验未通过的
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
		boCustInfo.put("areaId", party.getAreaId().toString());
		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustInfo.put("buiEffTime", "3000-01-01");
		boCustInfo.put("defaultIdType", CustDomain.PARTY_IDENTITY_TYPE);
		boCustInfo.put("mailAddressStr", party.getMailAddressStr());
		boCustInfo.put("name", party.getPartyName());
		boCustInfo.put("partyTypeCd", party.getPartyTypeCd().toString());
		boCustInfo.put("simpleSpell", party.getSimpleSpell());
		boCustInfo.put("state", "ADD");
		boCustInfo.put("statusCd", "S");
		boCustInfo.put("telNumber", party.getLinkPhone());
		boCustInfoArray.add(boCustInfo);

		// 构造客户证件信息
		JSONArray boCustIdentitieArray = new JSONArray();
		// 客户标示信息
		JSONObject boCustIdentitie = new JSONObject();
		boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
		boCustIdentitie.put("identidiesTypeCd", CustDomain.PARTY_IDENTITY_TYPE);
		boCustIdentitie.put("identityNum", custBasicSMO.generatePartyIdentity(Integer.parseInt(areaId)));
		boCustIdentitie.put("state", "ADD");
		boCustIdentitie.put("statusCd", "S");
		boCustIdentitieArray.add(boCustIdentitie);
		// 证件信息
		List<PartyIdentity> identifyList = party.getIdentities();
		if (CollectionUtils.isNotEmpty(identifyList)) {
			for (PartyIdentity identify : identifyList) {
				boCustIdentitie = new JSONObject();
				boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustIdentitie.put("identidiesTypeCd", identify.getIdentidiesTypeCd());
				boCustIdentitie.put("identityNum", custBasicSMO.generatePartyIdentity(Integer.parseInt("45101")));
				boCustIdentitie.put("state", "ADD");
				boCustIdentitie.put("statusCd", "S");
				boCustIdentitieArray.add(boCustIdentitie);
			}
		}

		// 构造客户扩展信息
		JSONArray boCustProfileArray = new JSONArray();
		List<PartyProfile> profileList = party.getProfiles();
		if (CollectionUtils.isNotEmpty(profileList)) {
			for (PartyProfile profile : profileList) {
				JSONObject boCustProfile = new JSONObject();
				boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				// TODO 如果发生改动，怎么同步
				boCustProfile.put("partyProfileCatgCd", profile.getPartyProfileCatgCd());
				boCustProfile.put("profileValue", profile.getProfileValue());
				boCustProfile.put("state", "ADD");
				boCustProfile.put("statusCd", "S");
				boCustProfileArray.add(boCustProfile);
			}
		}

		// 构造客户分段信息
		JSONArray boCustSegmentInfoArray = new JSONArray();
		List<PartySegmentMemberList> ptySegMemberList = party.getPartySegMember();
		if (CollectionUtils.isNotEmpty(ptySegMemberList)) {
			for (PartySegmentMemberList ptySegMember : ptySegMemberList) {
				JSONObject boCustSegmentInfo = new JSONObject();
				boCustSegmentInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				boCustSegmentInfo.put("segmentId", ptySegMember.getSegmentId());
				boCustSegmentInfo.put("state", "ADD");
				boCustSegmentInfo.put("statusCd", "S");
				boCustSegmentInfoArray.add(boCustSegmentInfo);
			}
		}

		// 组建客户数据
		JSONObject data = new JSONObject();
		data.put("boCustInfos", boCustInfoArray);
		data.put("boCustIdentities", boCustIdentitieArray);
		data.put("boCustProfiles", boCustProfileArray);
		data.put("boCustSegmentInfos", boCustSegmentInfoArray);

		// 组建业务动作
		JSONObject busiOrder = new JSONObject();
		busiOrder.put("busiOrderInfo", busiOrderInfo);
		busiOrder.put("busiObj", busiObj);
		busiOrder.put("boActionType", boActionType);
		busiOrder.put("areaId", areaId.toString());
		busiOrder.put("busiComponentInfos", busiComponentInfoArrary);
		busiOrder.put("data", data);
		JSONArray busiOrderArray = new JSONArray();
		busiOrderArray.add(busiOrder);
		custOrderList.put("busiOrder", busiOrderArray);

		// 构造业务动作,构造购物车
		JSONObject orderList = new JSONObject();
		JSONArray custOrderListArray = new JSONArray();
		custOrderListArray.add(custOrderList);
		orderList.put("custOrderList", custOrderListArray);
		orderList.put("orderListInfo", orderListInfo);
		JSONObject json = new JSONObject();
		json.put("orderList", orderList);

		return soCommitSMO.saveCustInfo(json);
	}

	@Override
	public Party getPartyByAccessNumber(String accessNumber) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_ACCESSS_NUMBER, accessNumber, null);
	}

	@Override
	public Party getPartyByIDCard(String cardNumber) {
		Map<String, String> param = buildQueryParam(CustDomain.CUST_QUERY_TYPE_IS_IDENTITY, cardNumber, null);
		param.put(CustDomain.QUERY_TYPE_IS_IDENTITY_CD, "1"); // 身份证
		List<Party> partyList = custBasicSMO.queryCustByConf(param);
		return CollectionUtils.isNotEmpty(partyList) ? partyList.get(0) : null;
	}

	@Override
	public Party getPartyByOtherCard(String cardNumber) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_IDENTITY, cardNumber, null);
	}

	@Override
	public Party getPartyByName(String name) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_PARTY_NAME, name, null);
	}

	@Override
	public Party getPartyById(String partyId) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_PARTY_ID, partyId, null);
	}

	@Override
	public Party getPartyByAcctCd(String acctCd) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_ACCT_CD, acctCd, null);
	}

	@Override
	public Party getPartyByTerminalCode(String terminalCode) {
		return queryParty(CustDomain.CUST_QUERY_TYPE_IS_TERMINAL_CODE, terminalCode, null);
	}

	/**
	 * 查询客户信息
	 * 
	 * @param param
	 * @return
	 */
	private Party queryParty(String queryType, String value, String areaId) {
		long start = System.currentTimeMillis();
		List<Party> partyList = custBasicSMO.queryCustByConf(buildQueryParam(queryType, value, areaId));
		LOG.error("queryParty.custBasicSMO.queryCustByConf(根据复合条件查询客户) 执行时间:" + (System.currentTimeMillis() - start));
		return CollectionUtils.isNotEmpty(partyList) ? partyList.get(0) : null;
	}

	/**
	 * 构建查询参数
	 * 
	 * @param value
	 * @param areaId
	 * @return
	 */
	private Map<String, String> buildQueryParam(String queryType, String value, String areaId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put(CustDomain.CUST_QUERY_TYPE, queryType);
		param.put(CustDomain.CUST_QUERY_VALUE, value);
		if (StringUtils.isNotBlank(areaId)) {
			param.put(CustDomain.QUERY_TYPE_AREA_LIST, areaId);
		}
		return param;
	}

	@Override
	public boolean isSameCustLocal(String name, String identityType, String identityNum, String partyType,
			String areaId, String partyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("identityTypeCd", identityType);
		param.put("identityNum", identityNum);
		param.put("partyTypeCd", partyType);
		param.put("areaId", areaId);
		param.put("partyId", partyId);

		return custBasicSMO.isSameCustLocal(param) > 0;
	}

	@Override
	public Party getPartyById(Long partyId) {
		if (partyId == null) {
			throw new IllegalArgumentException("partyId不能为空");
		}

		JSONObject param = new JSONObject();
		param.put("partyId", partyId.longValue());
		return custBasicSMO.queryCustInfoByPartyId(param);
	}

	@Override
	public int isValidBizPwd(Long partyId, String password) {
		if (partyId == null) {
			throw new IllegalArgumentException("partyId不能为空");
		}

		JSONObject param = new JSONObject();
		param.put("partyId", partyId.longValue());
		param.put("partyPassword", password);
		param.put("pwdType", CustDomain.CUST_BIZ_PASSWORD);
		return custBasicSMO.isValidBizPwd(param);
	}

	@Override
	public int isValidQryPwd(Long partyId, String password) {
		if (partyId == null) {
			throw new IllegalArgumentException("partyId不能为空");
		}

		JSONObject param = new JSONObject();
		param.put("partyId", partyId.longValue());
		param.put("partyPassword", password);
		param.put("pwdType", CustDomain.CUST_QUERY_PASSWORD);
		return custBasicSMO.isValidQryPwd(param);
	}

	@Override
	public int changeCustomerPsd(Long partyId, String partyPassword, String passwordtimed) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("partyId", partyId);
		jsonObj.put("partyPassword", partyPassword);
		jsonObj.put("passwordtimed", passwordtimed);
		long start = System.currentTimeMillis();
		int returnValue = custBasicSMO.saveOrUpdateBizPassword(jsonObj);
		LOG
				.error("changeCustomerPsd.custBasicSMO.saveOrUpdateBizPassword 执行时间:"
						+ (System.currentTimeMillis() - start));
		return returnValue;
	}

	/**
	 * 查询客户信息
	 * 
	 * @param param
	 * @return
	 */
	private List<Party> queryPartyList(String queryType, String value, String areaId) {
		List<Party> partyList = custBasicSMO.queryCustByConf(buildQueryParam(queryType, value, areaId));
		return CollectionUtils.isNotEmpty(partyList) ? partyList : null;
	}

	@Override
	public List<Party> getPartyListByName(String name) {
		return queryPartyList(CustDomain.CUST_QUERY_TYPE_IS_PARTY_NAME, name, null);
	}
	/**
	 * 查询客户信息
	 * 根据身份证查询客户信息
	 * @param param
	 * @return
	 */
	@Override
	public List<Party> getPartyListByIDCard(String cardNumber) {
		Map<String, String> param = buildQueryParam(CustDomain.CUST_QUERY_TYPE_IS_IDENTITY, cardNumber, null);
		param.put(CustDomain.QUERY_TYPE_IS_IDENTITY_CD, "1"); // 身份证
		return custBasicSMO.queryCustByConf(param);
	}
}
