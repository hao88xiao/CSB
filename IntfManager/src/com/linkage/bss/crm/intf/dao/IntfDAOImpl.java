package com.linkage.bss.crm.intf.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.model.AreaInfo;
import com.linkage.bss.crm.intf.model.BankTableEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntityInputBean;
import com.linkage.bss.crm.intf.model.DiscreateValue;
import com.linkage.bss.crm.intf.model.FingerPhotoCut;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntity;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntityInputBean;
import com.linkage.bss.crm.intf.model.LinkManInfo;
import com.linkage.bss.crm.intf.model.NewValidate;
import com.linkage.bss.crm.intf.model.OfferInfoKF;
import com.linkage.bss.crm.intf.model.OfferParamKF;
import com.linkage.bss.crm.intf.model.OperatingOfficeInfo;
import com.linkage.bss.crm.intf.model.ProdByCompProdSpec;
import com.linkage.bss.crm.intf.model.ProdServRela;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.model.ServActivate;
import com.linkage.bss.crm.intf.model.ServActivatePps;
import com.linkage.bss.crm.intf.model.ServParam;
import com.linkage.bss.crm.intf.model.ServSpecDto;
import com.linkage.bss.crm.intf.model.StaticData;
import com.linkage.bss.crm.intf.model.Tel2Area;
import com.linkage.bss.crm.intf.model.YKSXInfo;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.BoActionType;
import com.linkage.bss.crm.model.BoServOrder;
import com.linkage.bss.crm.model.BusiOrder;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.LogIdentityCheckGzt;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferServItem;
import com.linkage.bss.crm.model.OfferSpecParam;
import com.linkage.bss.crm.model.ProdSpec;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offerspec.common.OfferSpecDomain;
import com.linkage.bss.crm.offerspec.dto.AttachOfferInfoDto;
import com.linkage.bss.crm.offerspec.dto.BusiLimitDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.unityPay.IndentItemPay;
import com.linkage.bss.crm.unityPay.PayInfoType;
import com.linkage.bss.crm.unityPay.PayItemListType;
import com.linkage.bss.crm.unityPay.PayItemType;
import com.linkage.bss.crm.unityPay.PayListType;

public class IntfDAOImpl extends SqlMapClientDaoSupport implements IntfDAO {

	private JdbcTemplate jdbc;

	private ICommonSMO commonSMO;

	private static Log log = Log.getLog(IntfDAOImpl.class);

	public void setCommonSMO(ICommonSMO commonSMO) {
		this.commonSMO = commonSMO;
	}

	@Override
	public OfferProd queryProdByAcessNumber(String accessNumber) {

		OfferProd op = (OfferProd) getSqlMapClientTemplate().queryForObject("IntfDAO.query1ProdByAccessNumber",
				accessNumber);
		if (op != null && op.getProdId() != null) {
			return op;
		} else {
			return (OfferProd) getSqlMapClientTemplate().queryForObject("IntfDAO.query2ProdByAccessNumber",
					accessNumber);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStaticSql(List<String> idList) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getStaticSql", idList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSqlInfo(String sql) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getSqlInfo", sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getItemSpec(String itemSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getItemSpec", itemSpecId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> qryOfferModel(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.qryOfferModel", param);
	}

	@Override
	public String queryProdBizPwdByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryProdBizPwdByProdId", prodId);
	}

	@Override
	public String queryProdQryPwdByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryProdQryPwdByProdId", prodId);
	}

	@Override
	public String queryAcctPwdByAcctId(Long acctId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAcctPwdByProdId", acctId);
	}

	@Override
	public Tel2Area queryAccNBRInfo(String accNbr) {
		return (Tel2Area) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAccNBRInfo", accNbr);

	}

	@Override
	public AreaInfo queryAreaInfoByCode(String areaCode) {
		return (AreaInfo) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAreaInfoByCode", areaCode);
	}

	@Override
	public AreaInfo queryAreaInfoByName(String areaName) {
		return (AreaInfo) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAreaInfoByName", areaName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingOfficeInfo> queryOperatingOfficeInfoAll() {
		return (List<OperatingOfficeInfo>) getSqlMapClientTemplate()
				.queryForList("IntfDAO.queryOperatingOfficeInfoAll");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingOfficeInfo> queryOperatingOfficeInfoByAreaCode(String areaCode) {
		return (List<OperatingOfficeInfo>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.queryOperatingOfficeInfoByAreaCode", areaCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingOfficeInfo> queryOperatingOfficeInfoByAreaName(String areaName) {
		return (List<OperatingOfficeInfo>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.queryOperatingOfficeInfoByAreaName", areaName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTerminalCode(String iccid) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryTerminalCode", iccid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> ifYKSX(String iccid) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.ifYKSX", iccid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryNumOfYKSX(String iccid) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryNumOfYKSX", iccid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryNum(String iccid) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryNum", iccid);
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public String insertIntoGoodsApplayIntf(String trade_id, String sale_time, String channelId, String staffCode,
			String value_card_type_code, String value_code, String apply_num, int flag) {
		String returnMag = "1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trade_id", trade_id);
		paramMap.put("sale_time", sale_time);
		paramMap.put("channelId", channelId);
		paramMap.put("value_card_type_code", value_card_type_code);
		paramMap.put("value_code", value_code);
		paramMap.put("apply_num", apply_num);
		paramMap.put("staffCode", staffCode);
		paramMap.put("flag", flag);
		try {
			getSqlMapClientTemplate().insert("IntfDAO.insertIntoGoodsApplayIntf", paramMap);
		} catch (DataAccessException e) {
			returnMag = "插入接口表异常";
			e.printStackTrace();
		}
		return returnMag;
	}

	@Override
	public Date queryCurrentTime() {
		return (Date) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCurrentTime");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleObj> queryRoleObjs(Integer objType, Long objId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("objType", objType);
		paramMap.put("objId", objId);
		return (List<RoleObj>) getSqlMapClientTemplate().queryForList("IntfDAO.queryRoleObjs", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Offer findOffer(Long prodId, Long offerSpecId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prodId", prodId);
		paramMap.put("offerSpecId", offerSpecId);
		List<Offer> list = getSqlMapClientTemplate().queryForList("IntfDAO.findOfferByProdIdAndOfferSpecId", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferRoles> findOfferRoles(Long offerSpecId, Integer objType, Integer roleId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offerSpecId", offerSpecId);
		paramMap.put("objType", objType);
		paramMap.put("roleId", roleId);
		return getSqlMapClientTemplate().queryForList("IntfDAO.findOfferRolesByObjType", paramMap);
	}

	@SuppressWarnings("unchecked")
	public OfferRoles findOfferRoles(Long offerSpecId, Integer objType, Long objId, Integer roleId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offerSpecId", offerSpecId);
		paramMap.put("objType", objType);
		paramMap.put("objId", objId);
		paramMap.put("roleId", roleId);
		List<OfferRoles> list = getSqlMapClientTemplate().queryForList("IntfDAO.findOfferRoles", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoleObj findRoleObjByOfferRoleId(Long offerRoleId, Integer objType) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offerRoleId", offerRoleId);
		paramMap.put("objType", objType);
		List<RoleObj> list = getSqlMapClientTemplate().queryForList("IntfDAO.findRoleObj", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferServ findOfferServ(Long prodId, Long servSpecId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		OfferServ offerServ = new OfferServ();
		paramMap.put("prodId", prodId);
		paramMap.put("servSpecId", servSpecId);
		List<OfferServ> offerServs = getSqlMapClientTemplate().queryForList("IntfDAO.findOfferServ", paramMap);
		if (CollectionUtils.isNotEmpty(offerServs)) {
			offerServ = offerServs.get(0);
			List<OfferServItem> offerServParams = getSqlMapClientTemplate().queryForList(
					"IntfDAO.findOfferServItemByServId", offerServ.getServId());
			offerServ.setOfferServItems(offerServParams);
		}

		return offerServ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferProdItem findOfferProdItem(Long prodId, Long itemSpecId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prodId", prodId);
		paramMap.put("itemSpecId", itemSpecId);
		List<OfferProdItem> list = getSqlMapClientTemplate().queryForList("IntfDAO.findOfferProdItem", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferProd2Td findOfferProd2Td(Long prodId, Long terminalDevSpecId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prodId", prodId);
		paramMap.put("terminalDevSpecId", terminalDevSpecId);
		List<OfferProd2Td> list = getSqlMapClientTemplate().queryForList("IntfDAO.findOfferProd2Td", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProdSpec2AccessNumType findProdSpec2AccessNumType(Long prodSpecId, String isPrimary) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prodSpecId", prodSpecId);
		paramMap.put("isPrimary", isPrimary);
		List<ProdSpec2AccessNumType> list = this.getSqlMapClientTemplate().queryForList(
				"IntfDAO.findProdSpec2AccessNumType", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account findAcctByAcctCd(String acctCd) {
		List<Account> list = getSqlMapClientTemplate().queryForList("IntfDAO.findAcctByAcctCd", acctCd);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account findAcctByAccNbr(String accessNumber, Integer prodSpecId) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accessNumber", accessNumber);
		paramMap.put("prodSpecId", prodSpecId);
		List<Account> list = getSqlMapClientTemplate().queryForList("IntfDAO.findAcctByAccNbrAndProdSpec", paramMap);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public String findOfferOrService(Long id) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.findServSpecId", id);
	}

	@Override
	public Long selectByAcctId(Long id) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.selectByAcctId", id);
	}

	@Override
	public List<Map> findServSpecItem(Long id, String flag) {
		List<Map> param = new ArrayList<Map>();
		if ("servicePak".equals(flag)) {
			param = getSqlMapClientTemplate().queryForList("IntfDAO.findServSpecItem", id);
		} else if ("pricePlanPak".equals(flag)) {
			param = getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferSpecItemId", id);
		}
		return param;
	}

	@Override
	public Map isSubsidy(String coupon_ins_number) {
		Map returnMap = (Map) getSqlMapClientTemplate().queryForObject("IntfDAO.isSubsidy", coupon_ins_number);
		return returnMap;
	}

	@Override
	public Long queryOfferSpecIdByAccNbr(String accNbr) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferSpecIdByAccNbr", accNbr);
	}

	@Override
	public int queryFNSNum(Long offerSpecId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryFNSNum", offerSpecId);
	}

	@Override
	public List<Map<String, Object>> queryFNSInfo(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryFNSInfo", param);
	}

	@Override
	public List<Map<String, Object>> queryUnusedFNSInfo(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryUnusedFNSInfo", param);
	}

	@Override
	public ProdSpec getProdSpecByProdSpecId(Long prodSpecId) {
		return (ProdSpec) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdSpecByProdSpecId", prodSpecId);
	}

	@Override
	public int queryValidateServiceByAccNum(String accessNumber) {

		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryValidateServiceByAccNum", accessNumber);
	}

	@Override
	public List<NewValidate> isNewValidateService(String accessNumber) {
		return (List<NewValidate>) getSqlMapClientTemplate().queryForList("IntfDAO.isNewValidateService", accessNumber);
	}

	@Override
	public List<YKSXInfo> queryYKSXInfoByAccNbr(String accNbr) {
		return (List<YKSXInfo>) getSqlMapClientTemplate().queryForList("IntfDAO.queryYKSXInfoByAccNbr", accNbr);
	}

	@Override
	public String queryYKSXAccNum(Map<String, Object> param) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryYKSXAccNum", param);
	}

	@Override
	public List<String> queryImsiInfoByMdn(Long prodId) {
		return (List<String>) getSqlMapClientTemplate().queryForList("IntfDAO.queryImsiInfoByMdn", prodId);
	}

	@Override
	public Date queryDtByProdId(Long prodId) {
		return (Date) getSqlMapClientTemplate().queryForObject("IntfDAO.queryDtByProdId", prodId);
	}

	@Override
	public int queryCntBySpecId(Map<String, Object> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCntBySpecId", param);
	}

	@Override
	public int queryCntByProdId(Map<String, Object> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCntByProdId", param);
	}

	@Override
	public int queryCntByOfferSpecId(Map<String, Object> param2) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCntByOfferSpecId", param2);
	}

	@Override
	public int queryCntByOfferProdId(Map<String, Object> param3) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCntByOfferProdId", param3);
	}

	@Override
	public int queryMoneyByProdId(Long prodId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryMoneyByProdId", prodId);
	}

	@Override
	public int queryPaymentByProdId(Long prodId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryPaymentByProdId", prodId);
	}

	/*
	 * 根据购物车ID查询业务动作信息
	 */
	public List<BusiOrder> getBusiOrdersByOlId(String olId) {
		List<BusiOrder> list = getSqlMapClientTemplate().queryForList("IntfDAO.getBusiOrdersByOlId", new Long(olId));
		if (list != null && list.size() > 0) {
			for (BusiOrder busiOrder : list) {
				List<BoActionType> boActionTypeList = getSqlMapClientTemplate().queryForList(
						"IntfDAO.findBoActionTypeByPK", busiOrder.getBoActionTypeCd());
				if (boActionTypeList != null && boActionTypeList.size() > 0) {
					busiOrder.setBoActionType(boActionTypeList.get(0));
				}
			}
		}
		log.debug("-----list.size()={}", list.size());
		return list;
	}

	/*
	 * 根据业务动作ID查询服务信息变动时的基本信息
	 */
	public BoServOrder findBoServOrderByBoId(Long boId) {
		List<BoServOrder> list = getSqlMapClientTemplate().queryForList("IntfDAO.findBoServOrderByBoId", boId);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/*
	 * 根据产品实例ID查询产品规格信息
	 */
	public Long getProdIdByBoId(Long boId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdIdByBoId", boId);
	}

	/*
	 * 根据产品实例ID查询产品规格信息
	 */
	public OfferProdSpec findProdSpecByProdId(Long prodId) {
		// 获得在用的产品实例的产品规格
		List<OfferProdSpec> list = getSqlMapClientTemplate().queryForList("IntfDAO.findProdSpecByProdId1", prodId);
		// 如果没有在用的产品实例，则查询待失效状态的产品实例的产品规格
		if (list == null || list.isEmpty()) {
			list = getSqlMapClientTemplate().queryForList("IntfDAO.findProdSpecByProdId2", prodId);
		}

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long selectByAcctCd(String id) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.selectByAcctCd", id);
	}

	@Override
	public Map<String, Long> selectRoleCdAndOfferRoles(Map<String, Long> param) {
		return (Map<String, Long>) getSqlMapClientTemplate().queryForObject("IntfDAO.selectRoleCdAndOfferRoles", param);

	}

	@Override
	public List<ProdByCompProdSpec> queryProdByProdSpecByPartyId(Map<String, Object> param) {
		return (List<ProdByCompProdSpec>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.queryProdByProdSpecByPartyId", param);
	}

	@Override
	public int queryIfJoinCompProdByProdId(Long prodId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIfJoinCompProdByProdId", prodId);
	}

	@Override
	public int compProdRule(Map<String, Object> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.compProdRule", param);
	}

	@Override
	public String queryAccNumByTerminalCode(String terminalCode) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAccNumByTerminalCode", terminalCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPhoneNumberInfoByAnId(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryPhoneNumberInfoByAnId",
				param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> queryStaffInfoByStaffName(String staffName) {
		return (List<Map<String, String>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryStaffInfoByStaffName",
				staffName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> queryStaffInfoByStaffNumber(String staffNumber) {
		return (List<Map<String, String>>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.queryStaffInfoByStaffNumber", staffNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getClerkId(String accNbr) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.getClerkId", accNbr);
	}

	@Override
	public void insertReceiptPringLog(Map<String, String> map) {
		getSqlMapClientTemplate().insert("IntfDAO.insertReceiptPringLog", map);
	}

	@Override
	public int isRPLogExist(String coNbr) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.isRPLogExist", coNbr);
	}

	@Override
	public int judgeCoupon2OfferSpec(String offer_spec_id, String couponId) {
		Map paramMap = new HashMap();
		paramMap.put("offer_spec_id", offer_spec_id);
		paramMap.put("couponId", couponId);
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.judgeCoupon2OfferSpec", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> qryPartyInfo(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.qryPartyInfo", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> qryPartyManager(String identityNum) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.qryPartyManager", identityNum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBrandLevelDetail(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getBrandLevelDetail", param);
	}

	@Override
	public Map queryCustProfiles(Long partyId) {
		return (Map) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCustProfiles", partyId);
	}

	@Override
	public Map queryIdentifyList(Long partyId) {
		List<Map> identifyList = getSqlMapClientTemplate().queryForList("IntfDAO.queryIdentifyList", partyId);
		if (CollectionUtils.isNotEmpty(identifyList)) {
			if (!"13".equals(identifyList.get(0).get("IDENTIDIES_TYPE_CD"))) {
				return identifyList.get(0);
			}
		}
		return null;
	}

	@Override
	public String getChannelId(String parentOrg) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelId", parentOrg);
	}

	@Override
	public long getPartyIdSeq() {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdSeq");
	}

	@Override
	public void insertCrmParty(Map<String, String> map) {
		getSqlMapClientTemplate().insert("IntfDAO.insertCtgInstParty", map);
		getSqlMapClientTemplate().insert("IntfDAO.insertCtgInstCust", map);
	}

	@Override
	public void insertIntfParty(Map<String, String> map) {
		getSqlMapClientTemplate().insert("IntfDAO.insertIntfParty", map);
	}

	@Override
	public void deleteChannel(String partyId) {
		getSqlMapClientTemplate().update("IntfDAO.deleteChannel", partyId);
	}

	@Override
	public long getChannelIdSeq() {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelIdSeq");
	}

	@Override
	public void insertCrmChannel(Map<String, String> map) {
		getSqlMapClientTemplate().insert("IntfDAO.insertCrmChannel", map);
	}

	@Override
	public void updateCrmChannel(Map<String, String> map) {
		getSqlMapClientTemplate().update("IntfDAO.updateCrmChannel", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> qryOrderSimpleInfoByOlId(Map<String, Object> param) {
		// 结果集
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if ("Y".equals(param.get("transfer"))) {
			resultList = getSqlMapClientTemplate().queryForList("IntfDAO.queryHisOrderListInfoByParam", param);
			for (int i = 0; i < resultList.size(); i++) {
				String olIdStr = resultList.get(i).get("OL_ID").toString();
				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("olIdStr", olIdStr);
				String accessNumber = (String) getSqlMapClientTemplate().queryForObject(
						"IntfDAO.queryAccessNumberByOlId", pp);
				resultList.get(i).put("ACCESS_NUMBER", accessNumber);
				if (param.get("sysFlag") != null) {
					String payIndentId = param.get("sysFlag").toString() + olIdStr;
					String payIndentIdStr = (String) getSqlMapClientTemplate().queryForObject(
							"IntfDAO.queryPayIndentIdByOlIdAndFlag", payIndentId);
					resultList.get(i).put("PAY_INDENT_ID", payIndentIdStr);
				}
			}
		} else {
			resultList = getSqlMapClientTemplate().queryForList("IntfDAO.queryOrderListInfoByParam", param);
			for (int i = 0; i < resultList.size(); i++) {
				String olIdStr = resultList.get(i).get("OL_ID").toString();
				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("olIdStr", olIdStr);
				String accessNumber = (String) getSqlMapClientTemplate().queryForObject(
						"IntfDAO.queryAccessNumberByOlId", pp);
				resultList.get(i).put("ACCESS_NUMBER", accessNumber);
				if (param.get("sysFlag") != null) {
					String payIndentId = param.get("sysFlag").toString() + olIdStr;
					String payIndentIdStr = (String) getSqlMapClientTemplate().queryForObject(
							"IntfDAO.queryPayIndentIdByOlIdAndFlag", payIndentId);
					resultList.get(i).put("PAY_INDENT_ID", payIndentIdStr);
				}
				String charge = (String) getSqlMapClientTemplate().queryForObject(
						"IntfDAO.querychargeByOlId", olIdStr);
				resultList.get(i).put("charge1", charge);
			}
		}
		return resultList;
	}

	@Override
	public Map queryCustBrand(Long partyId) {
		return (Map) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCustBrand", partyId);
	}

	@Override
	public Map queryCustLevel(Long partyId) {
		return (Map) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCustLevel", partyId);
	}

	@Override
	public Map queryCustSegment(Long partyId) {
		return (Map) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCustSegment", partyId);
	}

	@Override
	public void storeUpdateChannel(Map<String, String> map) {
		getSqlMapClientTemplate().update("IntfDAO.storeUpdateChannel", map);
	}

	@Override
	public void storeDeleteChannel(String channelId) {
		getSqlMapClientTemplate().update("IntfDAO.storeDeleteChannel", channelId);
	}

	@Override
	public void insertCrmGisParty(Map<String, String> map) {
		getSqlMapClientTemplate().update("IntfDAO.insertCrmGisParty", map);
	}

	@Override
	public void updateCrmGisParty(Map<String, String> map) {
		getSqlMapClientTemplate().update("IntfDAO.updateCrmGisParty", map);
	}

	@Override
	public void delUpdateCrmGisParty(Map<String, String> map) {
		getSqlMapClientTemplate().update("IntfDAO.delUpdateCrmGisParty", map);
	}

	@Override
	public InstStatus queryInstStatusByCd(String statusCd) {
		InstStatus param = new InstStatus();
		param.setStatusCd(statusCd);
		return (InstStatus) getSqlMapClientTemplate().queryForObject("IntfDAO.queryInstStatusByCd", param);
	}

	@Override
	public Map<String, Object> qryIfPkByPartyId(Map<String, String> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.qryIfPkByPartyId", param);
	}

	@Override
	public List<ProdServRela> queryProdServRelas(int servSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryProdServRelas", servSpecId);
	}

	@Override
	public OfferProdComp queryOfferProdComp(Long prodCompId) {
		return (OfferProdComp) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferProdComp", prodCompId);
	}

	@Override
	public void updateOrInsertAgent2prm(Map<String, Object> param) {
		String opType = param.get("opType").toString();
		if (opType.equals("1")) {
			
		List<Map<String, Object>> ls = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.selectAgent2prm",
						param);
			
		if(ls.size() > 0){
			getSqlMapClientTemplate().update("IntfDAO.updateAgent2prm", param);
		}else{
			getSqlMapClientTemplate().insert("IntfDAO.addAgent2prm", param);
		}
		} else if (opType.equals("2")) { 

			List<Map<String, Object>> ls = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.selectAgent2prm",
							param);
				
			if(ls.size() > 0){
				getSqlMapClientTemplate().update("IntfDAO.updateAgent2prm", param);
			}else{
				getSqlMapClientTemplate().insert("IntfDAO.addAgent2prm", param);
			}
			
		} else if (opType.equals("4")) {
			getSqlMapClientTemplate().delete("IntfDAO.deleteAgent2prm", param);
		} else if (opType.equals("5")) {
			getSqlMapClientTemplate().delete("IntfDAO.updateAgent2prmManager", param);
		}
	}

	@Override
	public void updateOrInsertChannelForCrm(Map<String, Object> param) {
		String opType = param.get("opType").toString();
		if (opType.equals("1")) {
			getSqlMapClientTemplate().insert("IntfDAO.addChannelForCrm", param);
		} else if (opType.equals("2")) {
			getSqlMapClientTemplate().update("IntfDAO.updateChannelForCrm", param);
		} else if (opType.equals("4")) {
			getSqlMapClientTemplate().delete("IntfDAO.deleteChannelForCrm", param);
		} else if (opType.equals("2wd")) {
			getSqlMapClientTemplate().update("IntfDAO.updateWDChannelForCrm", param);
		} else if (opType.equals("4wd")) {
			getSqlMapClientTemplate().delete("IntfDAO.deleteWDChannelForCrm", param);
		}
	}

	@Override
	public List<Map<String, Object>> queryAttachOfferByProd(Map<String, Object> param) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryAttachOfferByProd",
				param);
	}

	@Override
	public List queryChargeInfo(Long ol_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryChargeInfo", ol_id);

	}

	@Override
	public List<String> queryOfferSpecAttr(Long offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferSpecAttr", offerSpecId);
	}

	@Override
	public String queryOfferSpecValueParam(Long offerSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferSpecValueParam", offerSpecId);
	}

	@Override
	public Map<String, Object> getChannelIdByPartyId(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelIdByPartyId", param);
	}

	@Override
	public Map<String, Object> getAccessNumberByUimNo(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getAccessNumberByUimNo", param);
	}

	@Override
	public List<Map<String, Object>> getUserZJInfoByAccessNumber(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getUserZJInfoByAccessNumber", param);
	}

	@Override
	public void updateOrInsertGisParty(Map<String, Object> param) {
		String opType = param.get("opType").toString();
		if (opType.equals("1")) {
			getSqlMapClientTemplate().insert("IntfDAO.insertAgentOrAgentPointInfo", param);
		} else if (opType.equals("2")) {
			getSqlMapClientTemplate().update("IntfDAO.updateAgentOrAgentPointInfoByPartyId", param);
		} else if (opType.equals("4")) {
			getSqlMapClientTemplate().update("IntfDAO.updateAgentOrAgentPointNameByPartyId", param);
		}
	}

	@Override
	public List<Map<String, Object>> getStatusList(Long prod_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getStatusList", prod_id);
	}

	@Override
	public List<Map<String, Object>> getAcctList(Long prod_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getAcctList", prod_id);
	}

	@Override
	public List<Map<String, Object>> getManagerList(Long prod_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getManagerList", prod_id);
	}

	@Override
	public List<Map<String, Object>> getManagerToList(Long owner_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getManagerToList", owner_id);
	}

	@Override
	public List<Map<String, Object>> getDevList(Long prod_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getDevList", prod_id);
	}

	@Override
	public List<Map<String, Object>> getDevNameList(Long party_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getDevNameList", party_id);
	}

	@Override
	public List<Map<String, Object>> getDevTelList(Long party_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getDevTelList", party_id);
	}

	@Override
	public List<Map<String, Object>> getDevOrgIdList(Long party_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getDevOrgIdList", party_id);
	}

	@Override
	public List<Map<String, Object>> getAcctItemList(Long acct_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getAcctItemList", acct_id);
	}

	@Override
	public List<Map<String, Object>> getTelList(Long acct_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getTelList", acct_id);
	}

	@Override
	public List<Map<String, Object>> getWxList(Long prod_id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getWxList", prod_id);
	}

	@Override
	public boolean updateOrInsertCepChannelFromPrmToCrm(Map<String, Object> param) {
		try {
			String opType = param.get("opType").toString();
			if (opType.equals("1")) {
				getSqlMapClientTemplate().insert("IntfDAO.insertCepChannelFromPrmToCrm", param);
			} else if (opType.equals("2")) {
				getSqlMapClientTemplate().update("IntfDAO.updateCepChannelFromPrmToCrm", param);
			} else if (opType.equals("4")) {
				getSqlMapClientTemplate().update("IntfDAO.deleteCepChannelFromPrmToCrm", param);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Map<String, Object> selectPartyInfoFromSmParty(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.selectPartyInfoFromSmParty",
				param);
	}

	@Override
	public List<Map<String, Object>> checkDeviceIdInLogin(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.checkDeviceIdInLogin", param);
	}

	@Override
	public boolean updatePadPwdInLogin(Map<String, Object> param) {
		try {
			getSqlMapClientTemplate().update("IntfDAO.updatePadPwdInLogin", param);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectChannelInfoByPartyId(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.checkSnPwd4SelectChannelInfoByPartyId", param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectStaffInfoByStaffNumber(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.checkSnPwd4SelectStaffInfoByStaffNumber", param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwdInLogin(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.checkSnPwdInLogin", param);
	}

	@Override
	public List<Map<String, Object>> getStaffCodeAndStaffName(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getStaffCodeAndStaffName", param);
	}

	@Override
	public boolean insertPadPwdLog(Map<String, Object> param) {
		try {
			getSqlMapClientTemplate().insert("IntfDAO.insertPadPwdLog", param);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean insertSmsWaitSendCrmSomeInfo(Map<String, Object> param) {
		try {
			getSqlMapClientTemplate().insert("IntfDAO.insertSmsWaitSendCrmSomeInfo", param);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> transmitRandom4SelectStaffInfoByDeviceId(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.transmitRandom4SelectStaffInfoByDeviceId", param);
	}

	@Override
	public String oldCGFlag(String materialId) {
		String flag = "1";
		String num = (String) getSqlMapClientTemplate().queryForObject("IntfDAO.oldCGFlag", materialId);
		if (num != null) {
			// 新双模卡
			flag = "0";
		}
		return flag;
	}

	@Override
	public OfferSpecParam queryOfferSpecParam(String offerSpecParamId) {

		return (OfferSpecParam) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferSpecParam",
				offerSpecParamId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryMainOfferSpecNameAndIdByOlId(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.queryMainOfferSpecNameAndIdByOlId", param);
	}

	@Override
	public int queryIfPk(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIfPk", partyId);
	}

	@Override
	public String getOlNbrByOlId(long olId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOlNbrByOlId", olId);
	}

	@Override
	public String getPartyIdByGroupSeq(String groupSeq) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByGroupSeq", groupSeq);
	}

	@Override
	public String getGroupSeqByPartyId(String partyId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getGroupSeqByPartyId", partyId);
	}

	@Override
	public String getAnTypeCdByProdSpecId(String prodSpecId, String isPrimary) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("prodSpecId", prodSpecId);
		map.put("isPrimary", isPrimary);
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getAnTypeCdByProdSpecId", map);
	}

	@Override
	public void insertBServActivate(ServActivate servActivate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("servId", servActivate.getServId());
		map.put("activateType", servActivate.getActivateType());
		map.put("activateDate", servActivate.getActivateDate());
		map.put("accNbr", servActivate.getAccNbr());
		map.put("sequence", servActivate.getSequence());
		map.put("billingMode", servActivate.getBillingMode());

		getSqlMapClientTemplate().insert("IntfDAO.insertServActivate", map);
	}

	@Override
	public void insertBServActivatPps(ServActivatePps servActivatePps) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("servId", servActivatePps.getServId());
		map.put("manageCode", servActivatePps.getManageCode());
		map.put("startDate", servActivatePps.getStartDate());
		map.put("endDate", servActivatePps.getEndDate());

		getSqlMapClientTemplate().insert("IntfDAO.insertServActivatePps", map);
	}

	@Override
	public String getPayIndentIdByBcdCode(String bcdCode) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPayIndentIdByBcdCode", bcdCode);
	}

	@Override
	public void savePayIndentId(Map<String, String> map) {
		getSqlMapClientTemplate().insert("IntfDAO.savePayIndentId", map);
	}

	@Override
	public List<Long> queryCategoryNodeId(Long offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCategoryNodeId", offerSpecId);
	}

	@Override
	public Long queryIfRootNode(Long categoryNodeId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIfRootNode", categoryNodeId);
	}

	@Override
	public Map<String, Object> queryUimNum(String phoneNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryUimNum", phoneNumber);
	}

	@Override
	public List<Map<String, Object>> getPartyIdentityList(String partyId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getPartyIdentityList", partyId);
	}

	@Override
	public String getTmlCodeByPhoneNumber(String phoneNumber) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getTmlCodeByPhoneNumber", phoneNumber);
	}

	@Override
	public Map<String, Object> getPartyPW(String partyId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyPW", partyId);
	}

	@Override
	public void indentItemPayIntf(IndentItemPay indentItemPay) {
		String requestId = indentItemPay.getRequestId();
		String indentItemPayId = commonSMO.generateSeq(11000, "ORDER_LIST", "2");
		Map<String, Object> indentMap = new HashMap<String, Object>();
		indentMap.put("id", indentItemPayId);
		indentMap.put("requestId", requestId);
		indentMap.put("requestTime", indentItemPay.getRequestTime());
		indentMap.put("platId", indentItemPay.getPlatId());
		indentMap.put("channelId", indentItemPay.getChannelId());
		indentMap.put("cycleId", indentItemPay.getCycleId());
		indentMap.put("payIndentId", indentItemPay.getPayIdentId());
		indentMap.put("custType", indentItemPay.getCustType());
		indentMap.put("custName", indentItemPay.getCustName());
		indentMap.put("custId", indentItemPay.getCustId());
		indentMap.put("staffId", indentItemPay.getStaffId());
		indentMap.put("interfaceSerial", indentItemPay.getInterfaceSerial());
		getSqlMapClientTemplate().insert("IntfDAO.saveIndentPay", indentMap);
		PayItemListType payItemListType = indentItemPay.getItemList();
		List<PayItemType> itemList = payItemListType.getItem();
		for (PayItemType item : itemList) {
			String itemListId = commonSMO.generateSeq(11000, "ORDER_LIST", "2");
			Map<String, Object> itemListMap = new HashMap<String, Object>();
			itemListMap.put("id", itemListId);
			itemListMap.put("requestId", requestId);
			itemListMap.put("refundPayIndentId", item.getRefundPayIdentId());
			itemListMap.put("custIndentNbr", item.getCustIndentNbr());
			itemListMap.put("itemId", item.getItemId());
			itemListMap.put("itemFlag", item.getItemFlag());
			itemListMap.put("itemTypeId", item.getItemTypeId());
			itemListMap.put("chargeStd", item.getChargeStd());
			itemListMap.put("charge", item.getCharge());
			itemListMap.put("commissionType", item.getCommissionType());
			itemListMap.put("commission", item.getCommission());
			itemListMap.put("productOfferId", item.getProductOfferId());
			itemListMap.put("productOfferInstanceId", item.getProductOfferInstanceId());
			itemListMap.put("count", item.getCount());
			itemListMap.put("servId", item.getServId());
			itemListMap.put("acctId", item.getAcctId());
			itemListMap.put("accNbr", item.getAccNbr());
			itemListMap.put("billingMode", item.getBillingMode());
			itemListMap.put("productSpecId", item.getProductSpecId());
			itemListMap.put("productSpecName", item.getProductSpecName());
			getSqlMapClientTemplate().insert("IntfDAO.saveIndentItemList", itemListMap);
		}
		PayListType payListType = indentItemPay.getPayList();
		List<PayInfoType> payList = payListType.getPayInfo();
		for (PayInfoType payInfoType : payList) {
			String payListId = commonSMO.generateSeq(11000, "ORDER_LIST", "2");
			Map<String, Object> payListMap = new HashMap<String, Object>();
			payListMap.put("id", payListId);
			payListMap.put("requestId", requestId);
			payListMap.put("method", payInfoType.getMethod());
			payListMap.put("amount", payInfoType.getAmount());
			payListMap.put("appendInfo", payInfoType.getAppendInfo());
			getSqlMapClientTemplate().insert("IntfDAO.saveIndentPayList", payListMap);
		}
	}

	@Override
	public int getPartyNumberCount(String accessNumber) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyNumberCount", accessNumber);

	}

	@Override
	public int ifImportantPartyByPartyId(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.ifImportantPartyByPartyId", partyId);
	}

	@Override
	public String getPayItemId() {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPayItemId");
	}

	@Override
	public String getItemTypeByBcdCode(String bcdCode) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getItemTypeByBcdCode", bcdCode);
	}

	@Override
	public List<String> queryCardinfoByAcct(String accessNumber) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCardinfoByAcct", accessNumber);
	}

	@Override
	public String getSecondAccNbrByProdId(String prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getSecondAccNbrByProdId", prodId);
	}

	@Override
	public String getOfferOrProdSpecIdByBoId(String boId) {
		String specId = null;
		specId = (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdSpecIdByBoId", boId);
		if (StringUtils.isBlank(specId)) {
			specId = (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferSpecIdByBoId", boId);
		}
		return specId;
	}

	@Override
	public String getNewOlIdByOlIdForPayIndentId(String olId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getNewOlIdByOlIdForPayIndentId", olId);
	}

	@Override
	public String getInterfaceIdBySystemId(String systemId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getInterfaceIdBySystemId", systemId);
	}

	@Override
	public List<AttachOfferInfoDto> queryAttachOfferSpecBySpec(Map<String, Object> param) {
		List<AttachOfferInfoDto> list = new ArrayList<AttachOfferInfoDto>();
		log.debug("获取附属销售品入参param:{}", JsonUtil.getJsonString(param));
		Long startTime = System.currentTimeMillis();
		list = this.getSqlMapClientTemplate().queryForList("IntfDAO.queryAttachOfferSpecBySpec", param);
		Long costTime = System.currentTimeMillis() - startTime;
		log.debug("获取附属销售品耗时：{} ms, 共获得附属销售品标签个数为: {}", costTime, list.size());
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <Type> Map<String, List<Type>> queryBusiObjectLimit(String statementName, Map<String, Object> param) {

		Map<String, List<Type>> result = new HashMap<String, List<Type>>();
		// 能操作
		param.put("limitRoleCd", CommonDomain.LIMIT_ROLE_CAN_HANDLE);
		if (log.isDebugEnabled()) {
			log.debug("查询业务权限能当前能操作前：{}", JsonUtil.getJsonString(param));
		}
		List<BusiLimitDto> listCan = getSqlMapClientTemplate().queryForList(statementName, param);
		if (log.isDebugEnabled()) {
			log.debug("查询业务权限能当前能操作后：{}", JsonUtil.getJsonString(param));
		}
		// 不能操作
		param.put("limitRoleCd", CommonDomain.LIMIT_ROLE_CAN_NOT_HANDLE);
		if (log.isDebugEnabled()) {
			log.debug("查询业务权限不能当前能操作前：{}", JsonUtil.getJsonString(param));
		}
		List<BusiLimitDto> listNotCan = getSqlMapClientTemplate().queryForList(statementName, param);
		if (log.isDebugEnabled()) {
			log.debug("查询业务权限不能当前能操作后：{}", JsonUtil.getJsonString(param));
		}
		if (log.isDebugEnabled()) {
			log.debug("业务对象优先级计算前：{}", JsonUtil.getJsonString(param));
		}
		List<Type> canList = new ArrayList<Type>();
		List<Type> NotCanList = new ArrayList<Type>();
		Set<Type> canSets = new HashSet<Type>();
		Set<Type> NotCanSets = new HashSet<Type>();
		Set<Type> canSetStaffs = new HashSet<Type>();
		Set<Type> canSetChannels = new HashSet<Type>();
		Set<Type> canSetChannelSpecs = new HashSet<Type>();
		Set<Type> notCanSetStaffs = new HashSet<Type>();
		Set<Type> notCanSetChannels = new HashSet<Type>();
		Set<Type> notCanSetChannelSpecs = new HashSet<Type>();

		// 能操作对象集合
		for (BusiLimitDto busiLimitDto : listCan) {
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.STAFF_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				canSetStaffs = new HashSet<Type>(list);
			}
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.CHANNEL_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				canSetChannels = new HashSet<Type>(list);
			}
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.CHANNEL_SPEC_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				canSetChannelSpecs = new HashSet<Type>(list);
			}
		}
		// 不能操作对象集合
		for (BusiLimitDto busiLimitDto : listNotCan) {
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.STAFF_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				notCanSetStaffs = new HashSet<Type>(list);
			}
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.CHANNEL_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				notCanSetChannels = new HashSet<Type>(list);
			}
			if (busiLimitDto.getAuthObjType().equals(OfferSpecDomain.CHANNEL_SPEC_AUTH_OBJ_TYPE)) {
				List<Type> list = busiLimitDto.getAuthObjLists();
				notCanSetChannelSpecs = new HashSet<Type>(list);
			}
		}

		// 1.员工能操作的对象集合减员工不能操作的对象集合 员工能操作的对象集合
		canSetStaffs.removeAll(notCanSetStaffs);
		// 2.渠道能操作的对象集合减渠道不能操作的对象集合再减员工不能操作的对象集合 渠道能操作的对象集合
		canSetChannels.removeAll(notCanSetChannels);
		canSetChannels.removeAll(notCanSetStaffs);
		// 3.渠道规格能操作的对象集合减渠道规格不能操作的对象集合，减渠道不能操作的对象集合，减员工不能操作的对象集合 渠道规格能操作的对象集合
		canSetChannelSpecs.removeAll(notCanSetChannelSpecs);
		canSetChannelSpecs.removeAll(notCanSetChannels);
		canSetChannelSpecs.removeAll(notCanSetStaffs);
		// 收集所有能的集合
		canSets.addAll(canSetStaffs);
		canSets.addAll(canSetChannels);
		canSets.addAll(canSetChannelSpecs);
		canList.addAll(canSets);
		// 收集所有不能的集合
		NotCanSets.addAll(notCanSetStaffs);
		NotCanSets.addAll(notCanSetChannels);
		NotCanSets.addAll(notCanSetChannelSpecs);
		NotCanList.addAll(NotCanSets);
		result.put(CommonDomain.CAN_HANDLE, canList);
		result.put(CommonDomain.CAN_NOT_HANDLE, NotCanList);
		if (log.isDebugEnabled()) {
			log.debug("业务对象优先级计算后：{}", JsonUtil.getJsonString(param));
		}
		return result;
	}

	@Override
	public <Type> List<Type> querySubBusiObjectLimit(String statementName, Map<String, Object> param) {
		List<Type> result = getSqlMapClientTemplate().queryForList(statementName, param);
		return result;
	}

	@Override
	public String queryChannelSpecByChannelId(Integer channelId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryChannelSpecByChannelId", channelId);
	}

	@Override
	public void insertTableInfoPayInfoListForOrderSubmit(Map<String, Object> payInfoList) {
		getSqlMapClientTemplate().insert("IntfDAO.insertTableInfoPayInfoListForOrderSubmit", payInfoList);
	}

	@Override
	public int getRenewOfferSpecAttr(Long offerSpecId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getRenewOfferSpecAttr", offerSpecId);
	}

	@Override
	public String getAccessNumberByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getAccessNumberByProdId", prodId);
	}

	@Override
	public String getOfferSpecSummary(Long offerSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferSpecSummary", offerSpecId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getPartyIdByIdentityNum(String identifyValue) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getPartyIdByIdentityNum", identifyValue);
	}

	@Override
	public void updateOrInsertAmaLinkman(List<LinkManInfo> linkmanList) {
		Integer agentId = linkmanList.get(0).getAgentId();
		if ("2".equals(linkmanList.get(0).getOpType())) {
			getSqlMapClientTemplate().delete("IntfDAO.deleteAmaLinkman", agentId);
		}
		for (int i = 0; linkmanList.size() > i; i++) {
			String opType = linkmanList.get(i).getOpType();
			if ("1".equals(opType) || "2".equals(opType)) {
				getSqlMapClientTemplate().insert("IntfDAO.insertAmaLinkman", linkmanList.get(i));
			}
			if ("4".equals(opType)) {
				getSqlMapClientTemplate().delete("IntfDAO.deleteAmaLinkman", agentId);
			}
		}

	}

	@Override
	public int getOwnCDMANumByPartyId(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getOwnCDMANumByPartyId", partyId);
	}

	@Override
	public int getRunOrderByPartyId(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getRunOrderByPartyId", partyId);
	}

	@Override
	public int isIndividualCustByPartyId(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.isIndividualCustByPartyId", partyId);
	}

	@Override
	public int isYKSXByPartyId(Long partyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.isYKSXByPartyId", partyId);
	}

	@Override
	public List<OfferServItemDto> queryOfferServNotInvalid(Long offerId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferServNotInvalid", offerId);
	}

	@Override
	public void updateOrInsertAbm2crmProvince(Map<String, Object> param) {
		String opType = param.get("opType").toString();
		if ("insert".equals(opType)) {
			getSqlMapClientTemplate().update("IntfDAO.insertAbm2crmProvince", param);
		} else if ("update".equals(opType)) {
			getSqlMapClientTemplate().insert("IntfDAO.updateAbm2crmProvince", param);
		}
	}

	@Override
	public Map<String, Object> queryOfferProdInfoByAccessNumber(String phoneNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.queryOfferProdInfoByAccessNumber", phoneNumber);
	}

	@Override
	public Long getProdStatusByProdId(String prodId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdStatusByProdId", prodId);
	}

	@Override
	public List<ServSpecDto> queryOptionServSpecs(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOptionServSpecs", params);
	}

	@Override
	public List<ServSpecDto> queryOrderServSpecs(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOrderServSpecs", params);
	}

	@Override
	public Map<String, Object> isHaveInOffer(Map<String, Object> req) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.isHaveInOffer", req);
	}

	@Override
	public List<DiscreateValue> queryDiscreateValuesByItemSpecId(Long id) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryDiscreateValuesByItemSpecId", id);
	}

	@Override
	public List<ServParam> queryItemSpecsByItemSpecIdAndServSpecId(Map<String, Object> temp) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryItemSpecsByItemSpecIdAndServSpecId", temp);
	}

	@Override
	public List<ServParam> queryServItemsByServSpecIdAndProdId(Map<String, Object> temp) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryServItemsByServSpecIdAndProdId", temp);
	}

	@Override
	public List<Long> querySservItemsByServSpecId(Long servSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.querySservItemsByServSpecId", servSpecId);
	}

	@Override
	public String getOfferSpecNameByOfferSpecId(String offerSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferSpecNameByOfferSpecId", offerSpecId);
	}

	@Override
	public Map<String, Object> qryPhoneNumberInfoByAccessNumber(String accessNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.qryPhoneNumberInfoByAccessNumber", accessNumber);
	}

	@Override
	public Long getPartyIdByAccNbr(String accNbr) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByAccNbr", accNbr);
	}

	@Override
	public Map<String, Object> findOfferProdComp(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.findOfferProdComp", param);
	}

	/**
	 * 根据TYPE字段选择SQL
	 */
	@Override
	public String checkOrderRouls(Element order) {
		// TODO:
		order.selectSingleNode("/request/order/prodId");
		List<Element> offerSpecs = order.selectNodes("/request/order/offerSpecs");
		for (int i = 0; offerSpecs.size() > i; i++) {

		}
		return "";
	}

	@Override
	public void channelInfoGoDown(Map<String, Object> map) {
		// TODO:增 、删、修 渠道信息插入表
		String type = map.get("opType").toString();
		if ("1".equals(type)) {
			map.put("actionFlag", "CI");
		} else {
			map.put("actionFlag", "CU");
		}
		getSqlMapClientTemplate().insert("IntfDAO.channelInfoGoDown", map);
	}

	@Override
	public boolean yesOrNoAliveInOfferSpecRoles(Map<String, Object> map) {
		Map<String, Object> param = (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.yesOrNoAliveInOfferSpecRoles", map);
		if (param != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<OfferIntf> queryOfferInstByProdId(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferInstByProdId", prodId);
	}

	@Override
	public List<OfferParamKF> queryOfferParamsByOfferSpecId(Long offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferParamsByOfferSpecId", offerSpecId);
	}

	@Override
	public List<OfferInfoKF> queryOptionOfferSpecsByProdId(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOptionOfferSpecsByProdId", params);
	}

	@Override
	public List<OfferInfoKF> queryOrderOffersByProdId(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOrderOffersByProdId", prodId);
	}

	@Override
	public List<OfferParamKF> queryOfferParamsByProdIdAndOfferSpecId(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferParamsByProdIdAndOfferSpecId", params);
	}

	@Override
	public List<ServSpecDto> queryServOfferRelaByOfferSpecId(Long offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryServOfferRelaByOfferSpecId", offerSpecId);
	}

	@Override
	public String queryOfferSpecParamIdByItemSpecId(Map<String, Object> map) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferSpecParamIdByItemSpecId", map);
	}

	@Override
	public Integer queryProductSpec2CategroyNodeByProdSpecId(Integer prodSpecId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryProductSpec2CategroyNodeByProdSpecId",
				prodSpecId);
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryAttachOfferByProdId", prodId);
	}

	@Override
	public Map<String, Object> getAuditingTicketInfoByOlId(String olId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getAuditingTicketInfoByOlId",
				olId);
	}

	@Override
	public Integer queryCountPS2CNByAccNbr(String accNbr) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCountPS2CNByAccNbr", accNbr);
	}

	@Override
	public List<Map<String, Object>> queryContinuePPInfosFixedByAccNbr(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryContinuePPInfosFixedByAccNbr", accNbr);
	}

	@Override
	public List<Map<String, Object>> queryCurrentPPInfosFixedByAccNbr(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCurrentPPInfosFixedByAccNbr", accNbr);
	}

	@Override
	public Integer queryWlaneLogByAccNbr(String accNbr) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryWlaneLogByAccNbr", accNbr);
	}

	@Override
	public Integer queryCountContinueOrderOfferByAccNbr(String accNbr) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCountContinueOrderOfferByAccNbr",
				accNbr);
	}

	@Override
	public List<Map<String, Object>> queryContinuePPInfosByAccNbr(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryContinuePPInfosByAccNbr", accNbr);
	}

	@Override
	public List<Map<String, Object>> queryCurrentPPInfosByAccNbr(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCurrentPPInfosByAccNbr", accNbr);
	}

	@Override
	public List<OfferParam> queryOfferParamByOfferId(Long offerId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferParamByOfferId", offerId);
	}

	@SuppressWarnings("unchecked")
	public List<CommonOfferProdDto> queryAllChildCompProd(Map<String, Object> param) {
		return this.getSqlMapClientTemplate().queryForList("IntfDAO.queryAllChildCompProd", param);
	}

	@Override
	public String isPKagent(String payIndentId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.isPKagent", payIndentId);
	}

	@Override
	public String getIntfCommonSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getIntfCommonSeq");
	}

	@Override
	public void saveRequestInfo(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.saveRequestInfo", map);
	}

	@Override
	public void saveResponseInfo(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.saveCRMResponseInfo", map);
		getSqlMapClientTemplate().delete("IntfDAO.deleteRequestInfo", map);
		//		getSqlMapClientTemplate().update("IntfDAO.saveResponseInfo", map);
	}

	@Override
	public boolean yesOrNoNeedAddCoupon(Map<String, Object> param) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.yesOrNoNeedAddCoupon", param);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String ifCompProdByProdSpecId(Integer prodSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.ifCompProdByProdSpecId", prodSpecId);
	}

	@Override
	public List<Map<String, Object>> queryFNSInfoByOfferSpecId(Long offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryFNSInfoByOfferSpecId", offerSpecId);
	}

	@Override
	public Integer ifRightPartyGradeByCustTypeAndPartyGrade(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.ifRightPartyGradeByCustTypeAndPartyGrade",
				params);
	}

	@Override
	public String queryOlIdByOlNbr(String olNbr) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOlIdByOlNbr", olNbr);
	}

	@Override
	public String getPartyNameByPartyId(Long partyId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyNameByPartyId", partyId);
	}

	@Override
	public String getPartyIdByTerminalCode(String terminalCode) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByTerminalCode", terminalCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getOfferListByProdId(String prodId) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.getOfferListByProdId",
				prodId);
	}

	@Override
	public Long getPartyIdByIdentifyNum(String accNbr) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByIdentifyNum", accNbr);
	}

	@Override
	public Long getPartyIdByProdId(Long prodId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByProdId", prodId);
	}

	@Override
	public Long getProdidByAccNbr(String accNbr) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdidByAccNbr", accNbr);
	}

	@Override
	public Long getGxProdItemIdByProdid(Long prodid) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getGxProdItemIdByProdid", prodid);
	}

	@Override
	public boolean checkRelaSub(Map<String, String> param) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkRelaSub", param);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List queryOfferSpecIdByProdId(String prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferSpecIdByProdId", prodId);
	}

	@Override
	public int checkContinueOrderPPInfo(Map<String, Object> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkContinueOrderPPInfo", param);
	}

	@Override
	public List<OfferSpecDto> queryOfferSpecsByDZQD() {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferSpecsByDZQD");
	}

	@Override
	public boolean queryProdSpec2BoActionTypeCdBYprodAndAction(Map<String, Object> param) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.queryProdSpec2BoActionTypeCdBYprodAndAction", param);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int queryIfWLANByOfferSpecId(Long offerSpecId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIfWLANByOfferSpecId", offerSpecId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectStaffInofFromPadPwdStaff(String staffNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.selectStaffInofFromPadPwdStaff",
				staffNumber);
	}

	@Override
	public void updatePhoneNumberStatusCdByYuyue(String phoneNumber) {
		getSqlMapClientTemplate().update("IntfDAO.updatePhoneNumberStatusCdByYuyue", phoneNumber);
	}

	@Override
	public String gjmyYesOrNoRule(Map<String, Object> param) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.gjmyYesOrNoRules", param);
	}

	@Override
	public Map<String, Object> queryAccountInfo(String prodId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAccountInfo", prodId);
	}

	@Override
	public Long queryCurrentOfferSpecIdKFByProdId(Long prodId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCurrentOfferSpecIdKFByProdId", prodId);
	}

	@Override
	public String getPostCodeByPartyId(Long partyId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPostCodeByPartyId", partyId);
	}

	@Override
	public String qryDeviceNumberStatusCd(String anId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.qryDeviceNumberStatusCd", anId);
	}

	@Override
	public Map<String, Object> queryAuditingTicketBusiInfo(Map<String, Object> param) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAuditingTicketBusiInfo",
				param);
	}

	@Override
	public String queryOlIdByBoId(Map<String, Object> param) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOlIdByBoId", param);
	}

	@Override
	public String queryCycleByPayId(String payIndentId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCycleByPayId", payIndentId);
	}

	@Override
	public String getChargeByTicketCd(String auditTicketCd) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getChargeByTicketCd", auditTicketCd);
	}

	@Override
	public boolean qryAccessNumberIsOk(String accessNumber) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.qryAccessNumberIsOk", accessNumber);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> queryDefaultValueByMainOfferSpecId(String offerSpecId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"IntfDAO.queryDefaultValueByMainOfferSpecId", offerSpecId);
	}

	@Override
	public String getPayIndentIdByOlId(String olId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPayIndentIdByOlId", olId);
	}

	@Override
	public boolean checkIsExistsMailAddressId(String orgId) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkIsExistsMailAddressId",
				orgId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getProdStatusNameByCd(Long prodStatusCd) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdStatusNameByCd", prodStatusCd);
	}

	@Override
	public String getChannelIdByTicketCd(String ticketCd) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelIdByTicketCd", ticketCd);
	}

	@Override
	public void saveTicketCd2terminal(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.saveTicketCd2terminal", map);
	}

	@Override
	public String getTicketIdByCd(String autitTicketCd) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getTicketIdByCd", autitTicketCd);
	}

	@Override
	public List<InventoryStatisticsEntity> getInventoryStatistics(InventoryStatisticsEntityInputBean parameterObject) {
		return (List<InventoryStatisticsEntity>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.getInventoryStatistics", parameterObject);
	}

	@Override
	public List<Map<String, Object>> getUnCheckedCardInfo() {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getUnCheckedCardInfo");
	}

	@Override
	public void updaCradCheckResult(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.updaCradCheckResult", map);
	}

	@Override
	public void updaCradCheckStatus(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.updaCradCheckStatus", map);
	}

	@Override
	public String ifPayIndentIdExists(Map<String, Object> map) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.ifPayIndentIdExists", map);
	}

	@Override
	public boolean checkUIMChannelId(Map<String, Object> map) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkUIMChannelId", map);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean checkGoupUIM(Map<String, Object> map) {
		String count = (String) getSqlMapClientTemplate().queryForObject("IntfDAO.checkGoupUIM", map);
		if (Integer.parseInt(count) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<BcdCodeEntity> getBcdCode(BcdCodeEntityInputBean parameterObject) {
		return (List<BcdCodeEntity>) getSqlMapClientTemplate().queryForList("IntfDAO.getBcdCode", parameterObject);
	}

	@Override
	public String getOfferProdCompMainProd(Long compProdId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferProdCompMainProd", compProdId);
	}

	@Override
	public void deleteCrmRequest(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		getSqlMapClientTemplate().delete("IntfDAO.deleteRequestInfo", map);
	}

	@Override
	public String getChargeByPayId(String payIndentId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getChargeByPayId", payIndentId);
	}

	@Override
	public boolean checkUIMStore(Map<String, Object> map) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkUIMStore", map);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void saveSRInOut(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.saveSRInOut", map);
	}

	@Override
	public String getOfferTypeCdByOfferSpecId(String offerSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferTypeCdByOfferSpecId", offerSpecId);
	}

	@Override
	public String getFaceValueByTicketCd(String auditTicketCd) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getFaceValueByTicketCd", auditTicketCd);
	}

	@Override
	public List<BankTableEntity> getBankTable(String bankCode) {
		return (List<BankTableEntity>) getSqlMapClientTemplate().queryForList("IntfDAO.getBankTable", bankCode);
	}

	@Override
	public void insertBankFreeze(String bankSql) {
		getSqlMapClientTemplate().insert("IntfDAO.insertBankFreeze", bankSql);
	}

	@Override
	public boolean checkBankFreeze(String bankSql) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkBankFreeze", bankSql);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateBankFreeze(String bankSql) {

		int count = (Integer) getSqlMapClientTemplate().update("IntfDAO.updateBankFreeze", bankSql);
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List queryChargeInfoBySpec(String offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryChargeInfoBySpec", offerSpecId);
	}

	@Override
	public String getRequestInfo(String olId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getRequestInfo", olId);
	}

	@Override
	public void saveRequestTime(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.saveRequestTime", map);

	}

	@Override
	public void updateRequestTime(Map<String, Object> map) {
		getSqlMapClientTemplate().update("IntfDAO.updateRequestTime", map);

	}

	@Override
	public Long getOfferItemByAccNum(String accessNumber) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferItemByAccNum", accessNumber);
	}

	@Override
	public String getFlag(String keyflag) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getFlag", keyflag);
	}

	@Override
	public String getPartyIdByCard(Map<String, Object> map) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyIdByCard", map);
	}

	@Override
	public List<Map<String, Object>> getAserByProd(Long prodidByPartyId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getAserByProd", prodidByPartyId);
	}

	@Override
	public String getOfferSpecidByProdId(Long prodidByPartyId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferSpecidByProdId", prodidByPartyId);
	}

	@Override
	public String getSpecNameByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getSpecNameByProdId", String.valueOf(prodId));
	}

	@Override
	public String getOffersByProdId(String valueOf) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOffersByProdId", valueOf);
	}

	@Override
	public String getUmiCardByProdId(String valueOf) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getUmiCardByProdId", valueOf);
	}

	@Override
	public List<Map<String, Object>> getStasByProdId(String valueOf) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getStasByProdId", valueOf);
	}

	@Override
	public List<Map<String, Object>> getExchsByProdId(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getExchsByProdId", prodId);
	}

	@Override
	public String getAddressByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getAddressByProdId", prodId);
	}

	@Override
	public String getNetAccountByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getNetAccountByProdId", prodId);
	}

	@Override
	public String getValidStrByPartyId(Long partyid) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getValidStrByPartyId", partyid);
	}

	@Override
	public Long getPrepayFlagBySpecid(Long offerSpecId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getPrepayFlagBySpecid", offerSpecId);
	}

	@Override
	public List<Map<String, Object>> getAccMailMap(Long acctId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getAccMailMap", acctId);
	}

	@Override
	public String getIntfTimeCommonSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getIntfTimeCommonSeq");
	}

	@Override
	public String getGimsiByProdid(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getGimsiByProdid", prodId);
	}

	@Override
	public String getIndustryClasscdByPartyId(Long partyId) {
		Object queryForObject = getSqlMapClientTemplate()
				.queryForObject("IntfDAO.getIndustryClasscdByPartyId", partyId);
		if (queryForObject == null || "".equals(queryForObject))
			return "";
		return queryForObject.toString();
	}

	@Override
	public String getPartyAddByProdId(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyAddByProdId", prodId);
	}

	@Override
	public String getCimsiByProdid(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getCimsiByProdid", prodId);
	}

	@Override
	public String getEsnByProdid(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getEsnByProdid", prodId);
	}

	@Override
	public String getCtfRuleIdByOCId(Long offerSpecId, Long couponId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offerSpecId", offerSpecId);
		map.put("couponId", couponId);
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getCtfRuleIdByOCId", map);
	}

	@Override
	public boolean qryAccessNumberIsZt(Map<String, Object> map) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.qryAccessNumberIsZt", map);
		//		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.qryAccessNumberIsZt", accessNumber);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProdForPad(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryAttachOfferByProdIdForPad", prodId);
	}
	@Override
	public List<AttachOfferDto> queryAttachOfferInfo(Long prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryAttachOfferInfo", prodId);
	}

	@Override
	public List queryFreeOfBank(Map<String, Object> map) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryFreeOfBank", map);
	}

	@Override
	public List getSaComponentInfo(String serviceId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getSaComponentInfo", serviceId);
	}

	///////////////////////////////////////////////
	//建立一个对象实例进行保存
	@Override
	public void saveXMLInfo(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.insertIntfToBillingMsg", map);

	}

	//删除原表中的数据
	@Override
	public void delIntfToBillingMsg(String proId) {
		getSqlMapClientTemplate().delete("IntfDAO.delIntfToBillingMsg", proId);

	}

	@Override
	public void delSoInstData(String proId) {
		getSqlMapClientTemplate().delete("IntfDAO.delSoInstData", proId);

	}

	//通用查询方法
	@Override
	public List<Map<String, Object>> qrySqlById(String Sql, Map<String, Object> map) {

		return getSqlMapClientTemplate().queryForList(Sql, map);
	}

	@Override
	public Map<String, Object> qrySqlToMapById(String Sql, Map<String, Object> map) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(Sql, map);
	}

	//转存数据到历史表
	@Override
	public void saveIntfToBillingMsgToHis(String proId) {
		getSqlMapClientTemplate().insert("IntfDAO.saveIntfToBillingMsgToHis", proId);

	}

	@Override
	public void saveSoInstDataToHis(String proId) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("IntfDAO.saveSoInstDataToHis", proId);

	}

	//修改原表中的状态
	@Override
	public void updateIntfToBillingMsgSt(String proid, String state) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prod_id", proid);
		paramMap.put("status", state);//每次修改在SQL中修改时间
		getSqlMapClientTemplate().update("IntfDAO.updateIntfToBillingMsgSt", paramMap);

	}

	@Override
	public void updateSoInstDataSt(String proid, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prod_id", proid);
		paramMap.put("status", state);//每次修改在SQL中修改时间
		getSqlMapClientTemplate().update("IntfDAO.updateSoInstDataSt", paramMap);

	}

	@Override
	public List<Map> instDateListBySt() {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getInstDateListBySt");
	}

	@Override
	public List<Map> Intf2BillingMsgListBySt(Map<String, Object> param) {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getIntf2BillingMsgListBySt", param);
	}

	@Override
	public String insertPrm2CmsWt(Map<String, Object> param) {
		getSqlMapClientTemplate().queryForObject("IntfDAO.p_sync_prm_to_cms_w", param);
		return (String) param.get("o_flag") + "msg=" + param.get("o_msg");
	}

	@Override
	public String insertPrm2CmsDls(Map<String, Object> param) {
		getSqlMapClientTemplate().queryForObject("IntfDAO.p_sync_prm_to_cms_d", param);
		return (String) param.get("o_flag") + "msg=" + param.get("o_msg");

	}

	@Override
	public Boolean checkIfIdentityNum(String identityNumValue) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkIfIdentityNum", identityNumValue);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int queryCountProd(String identityNumValue) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCountProd", identityNumValue);
		return count;
	}

	@Override
	public int getIfpkByProd(String prodId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getIfpkByProd", prodId);
	}

	@Override
	public boolean checkIsExistsParty(String custId) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.checkIsExistsParty", custId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> queryTaxPayer(String custId) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryTaxPayer", custId);
	}

	@Override
	public Long getStaffIdByDbid(String dbid) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getStaffIdByDbid", dbid);
	}

	@Override
	public Long getChannelIdByStaffId(Long staffId) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelIdByStaffId", staffId);
	}

	@Override
	public Long getStaffIdByAgentNum(String staffNumber) {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getStaffIdByAgentNum", staffNumber);
	}

	@Override
	public String findStaffNumByStaffId(Long staffid) {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.findStaffNumByStaffId", staffid);
	}

	@Override
	public Long getChannelIdByStaffCode(String staffCode) {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelIdByStaffCode", staffCode);
	}

	@Override
	public int qryUsefulOfferNumByAccnum(String accNbr) {

		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.qryUsefulOfferNumByAccnum", accNbr);

	}

	@Override
	public boolean isLocalIvpn(Long prodId) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.isLocalIvpn", prodId);
		return count == 1;
	}

	@Override
	public List<Map<String, Object>> getCompLocalIvpns(Long prodId) {

		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.getCompLocalIvpns", prodId);
	}

	@Override
	public Map<String, Object> getIvpnInfos(Long prodId) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getIvpnInfos", prodId);
	}
	@Override
	public Map<String, Object> getIccIdByProdId(Long prodId,String itemSpecId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prodId", prodId);
		paramMap.put("itemSpecId", itemSpecId);//每次修改在SQL中修改时间
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getIccIdByProdId", paramMap);
	}

	@Override
	public void updateC2uPCRFSt(String acchiveId, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("acchiveId", acchiveId);
		paramMap.put("status", state);//每次修改在SQL中修改时间
		getSqlMapClientTemplate().update("IntfDAO.updateC2uPCRFSt", paramMap);

	}

	@Override
	public void delC2uPCRFData(String acchiveId) {
		getSqlMapClientTemplate().delete("IntfDAO.delC2uPCRFData", acchiveId);

	}

	@Override
	public void saveC2uPCRFToHis(List<Map<String, Object>> AcchiveList, String acchiveId) {

		for (int i = 0; i < AcchiveList.size(); i++) {
			AcchiveList.get(i).put("acchiveIdOld", acchiveId);
			getSqlMapClientTemplate().insert("IntfDAO.saveC2uPCRFToHis", AcchiveList.get(i));
		}

	}

	@Override
	public List<Map> csipPCRFListByst(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getCsipPCRFListByst", param);
	}

	@Override
	public List<Map<String, Object>> getProdCompRelaRoleCd(String boId) {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getProdCompRelaRoleCd", boId);

	}

	@Override
	public Long getDevNumIdByAccNum(String accNum) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getDevNumIdByAccNum", accNum);
	}

	@Override
	public void saveC2uPCRF2ESBERRToHis(List<Map<String, Object>> AcchiveList, String acchiveId) {
		for (int i = 0; i < AcchiveList.size(); i++) {
			AcchiveList.get(i).put("acchiveIdOld", acchiveId);
			getSqlMapClientTemplate().insert("IntfDAO.saveC2uPCRF2ESBERRToHis", AcchiveList.get(i));
		}

	}

	@Override
	public void saveC2uPCRFNO4GToHis(List<Map<String, Object>> AcchiveList, String acchiveId) {
		for (int i = 0; i < AcchiveList.size(); i++) {
			AcchiveList.get(i).put("acchiveIdOld", acchiveId);
			getSqlMapClientTemplate().insert("IntfDAO.saveC2uPCRFNO4GToHis", AcchiveList.get(i));
		}

	}

	@Override
	public String getAccNumByCompProdId(String compProdId) {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getAccNumByCompProdId", compProdId);

	}

	@Override
	public String getPCRFTransactionId() {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPCRFTransactionId");
	}

	@Override
	public Map<String, Object> queryOrderList(String orderId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOrderList",
				Long.parseLong(orderId));
		if (count > 0) {
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "可以作废");
		} else {
			resultMap.put("resultCode", "1");
			resultMap.put("resultMsg", "不可以作废");
		}
		return resultMap;
	}

	@Override
	public Long getValidateYHParams(Map<String, Object> paraMap) {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getValidateYHParams", paraMap);
	}

	@Override
	public Long getValidateYHOffer(Map<String, Object> offerMap) {
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.getValidateYHOffer", offerMap);
	}

	@Override
	public String getLteImsiByProdid(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getLteImsiByProdid", prodId);
	}

	@Override
	public void saveJSONObject(String jsonObjectStr) {
		getSqlMapClientTemplate().insert("IntfDAO.saveJSONObject", jsonObjectStr);
	}

	@Override
	public void updateJSONObject(String resultStr) {
		getSqlMapClientTemplate().insert("IntfDAO.updateJSONObject", resultStr);
	}

	@Override
	public String querySpeedValue(Long prodSpecId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.querySpeedValue", prodSpecId);
	}

	@Override
	public Map<String, Object> getProdSmsByProdId(String prodid) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdSmsByProdId", prodid);
	}
	
	@Override
	public Map<String, Object> getProdInfoByAccNbr(String accNbr) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdInfoByAccNbr", accNbr);
	}
	
	@Override
	public Map<String, Object> getCouponInfoByTerminalCode(String terminalCode) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getCouponInfoByTerminalCode", terminalCode);
	}
	
	@Override
	public Map<String, Object> getBasicCouponInfoByTerminalCode(String terminalCode) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getBasicCouponInfoByTerminalCode", terminalCode);
	}
	
	@Override
	public Map<String, Object> queryTerminalCodeByProdId(String prodId) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryTerminalCodeByProdId", prodId);
	}
	
	@Override
	public Map<String, Object> getDevInfoByCode(String devCode) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getDevInfoByCode", devCode);
	}
	
	@Override
	public Map<String, Object> getStaffIdByStaffCode(String staffCode) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getStaffIdByStaffCode", staffCode);
	}

	@Override
	public boolean isUimBak(String prodid) {
		long num = (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.isUimBak", prodid);
		return num > 0;
	}

	@Override
	public boolean getIsOrderOnWay(Map<String, Object> map) {
		List<String> queryForObject = (List<String>) getSqlMapClientTemplate().queryForList("IntfDAO.getIsOrderOnWay",
				map);

		return (queryForObject != null && queryForObject.size() > 0);
	}

	@Override
	public String findTelphoneByCardno(String terminalCode) {
		Object telphone = getSqlMapClientTemplate().queryForObject("IntfDAO.findTelphoneByCardno", terminalCode);
		if (telphone == null)
			telphone = "";
		return telphone + "";
	}

	@Override
	public String findTelphoneByDiscard(String terminalCode) {
		Object telphone = getSqlMapClientTemplate().queryForObject("IntfDAO.findTelphoneByDiscard", terminalCode);
		if (telphone == null)
			telphone = "";
		return telphone + "";
	}

	@Override
	public boolean isExistCardByProdId(Map<String, Object> map) {
		Long size = (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.isExistCardByProdId", map);
		return size > 0;
	}

	@Override
	public Map<String, Object> getImsiInfoByBillingNo(String billingNo) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getImsiInfoByBillingNo",
				billingNo);
	}

	@Override
	public List<Map<String, Object>> getBillingCardRelation(String billingNo) {
		List<Map<String, Object>> queryForList = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.getBillingCardRelation", billingNo);
		String mainCard = "";
		List<String> subCard = new ArrayList<String>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (queryForList != null && queryForList.size() > 0) {
			for (Map<String, Object> m : queryForList) {
				Object cd = m.get("CD");
				Object acc = m.get("ACCNUMBER");
				if ("243".equals(cd + "")) {
					mainCard = acc + "";
				} else if ("244".equals(cd + "")) {
					subCard.add(acc + "");
				}
			}
		}
		for (String s : subCard) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("MAIN_MSISDN", mainCard);
			m.put("SUB_MSISDN", s);
			result.add(m);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getDevNumIdByDevCode(String devCode) {
		List<Map<String, Object>> queryForList = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(
				"IntfDAO.getDevNumIdByDevCode", devCode);
		return queryForList;
	}

	@Override
	public List<Long> queryMustSelOfferByProd(Long prodId) {
		List<Long> list = (List<Long>) getSqlMapClientTemplate()
				.queryForList("IntfDAO.queryMustSelOfferByProd", prodId);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryTableSpace() {

		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryTableSpace");
	}

	@Override
	public List<Map<String, Object>> queryUseSpaceNotSysLob(String tableSpaceName) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryUseSpaceNotSysLob",
				tableSpaceName);
	}

	@Override
	public List<Map<String, Object>> queryUseSpaceSysLob(String tableSpaceName) {

		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryUseSpaceSysLob",
				tableSpaceName);
	}

	@Override
	public List<Map<String, Object>> queryCrmLockInfo() {
		Map<String, Object> m = new HashMap<String, Object>();
		//		 SELECT SESS.SID,
		//	       SESS.SERIAL# SERIAL,
		//	       LO.ORACLE_USERNAME,
		//	       LO.OS_USER_NAME,
		//	       AO.OBJECT_NAME,
		//	       logon_time,
		//	       LO.LOCKED_MODE
		//	  FROM V$$LOCKED_OBJECT LO, DBA_OBJECTS AO, V$SESSION SESS
		m.put("tableName1", "V$LOCKED_OBJECT");
		m.put("tableName2", "V$SESSION");
		m.put("param", "SESS.SERIAL#");
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryCrmLockInfo", m);
	}

	@Override
	public Long queryDBSessionInfo() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tableName", "v$session");
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.queryDBSessionInfo", m);
		if (size == null || !(size instanceof Long))
			size = "0";
		return (Long) size;
	}

	@Override
	public boolean saveBenzBusiOrder(Map<String, Object> result) {
		Object obj = getSqlMapClientTemplate().insert("IntfDAO.saveBenzBusiOrder", result);
		return !"-1".equals(obj);
	}

	@Override
	public void saveBenzBusiOrderSub(Map<String, Object> resultSub) {
		getSqlMapClientTemplate().insert("IntfDAO.saveBenzBusiOrderSub", resultSub);
	}

	@Override
	public boolean isBenzOfferServUser(String accNbr) {
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.isBenzOfferServUser", accNbr);
		if (size != null && (size instanceof Long))
			return ((Long) size) > 0;
		return false;
	}

	@Override
	public List<Map<String, Object>> getProdItemsByParam(Map<String, Object> param) {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getProdItemsByParam", param);
	}

	@Override
	public List<Map<String, Object>> getCustClassInfoByCustId(String custId) {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getCustClassInfoByCustId", Double.parseDouble(custId));
	}

	@Override
	public String getCustIdByAccNum(String accessNumber) {

		return String.valueOf(getSqlMapClientTemplate().queryForObject("IntfDAO.getCustIdByAccNum", accessNumber));
	}

	@Override
	public boolean qryProdOrderIsZtByOrderTypes(Map<String, Object> map) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.qryProdOrderIsZtByOrderTypes", map);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> queryOfferProdStatus(String accessNumber) {
		List<Map<String, Object>> queryForList = getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferProdStatus",
				accessNumber);
		if (queryForList != null && queryForList.size() > 0)
			return queryForList.get(0);
		return null;
	}

	@Override
	public boolean isFtWifiSystem(String accessNumber) {
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.isFtWifiSystem", accessNumber);
		if (size != null && (size instanceof Long))
			return ((Long) size) > 0;
		return false;
	}

	@Override
	public int checkHykdOrder(Map<String, Object> param) {
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.checkHykdOrder", param);
		if (size != null && (size instanceof Long))
			return Integer.valueOf(size.toString());
		return 0;
	}

	@Override
	public Map<String, String> checkCustName(String phone_number, String cust_name) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("phone_number", phone_number);
		param.put("cust_name", cust_name);
		Map<String, String> result = new HashMap<String, String>();
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.checkCustName", param);
		if (size != null && (size instanceof Long)) {
			long s = Long.valueOf(size.toString());
			if (s > 0) {
				result.put("0", "校验匹配");
				return result;
			}
		}
		result.put("1", "校验不匹配");
		return result;
	}

	@Override
	public Map<String, Object> getPartyTypeCdByProdId(Long prodidByAccNbr) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyTypeCdByProdId",
				prodidByAccNbr);

	}

	@Override
	public boolean checkOfferSpecBsns(String id) {
		Object size = getSqlMapClientTemplate().queryForObject("IntfDAO.checkOfferSpecBsns", id);
		if (size != null && (size instanceof Long)) {
			long s = Long.valueOf(size.toString());
			if (s > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long checkBankFreeze(Map<String, Object> checkMap) {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.checkBankFreeze2", checkMap);
	}

	@Override
	public Map<String, Object> qryOrderListByOlId(String olId) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.qryOrderListByOlId", olId);
	}

	@Override
	public String getNbrTypeByAccNbr(String accNbr) {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getNbrTypeByAccNbr", accNbr);
	}

	@Override
	public List<Map<String, Object>> qryEncryptStrByParam(Map<String, String> param) {

		return getSqlMapClientTemplate().queryForList("IntfDAO.qryEncryptStrByParam", param);
	}

	@Override
	public Map<String, Object> queryOrgByStaffNumber(String staffNumber) {

		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOrgByStaffNumber",
				staffNumber);
	}

	@Override
	public Map<String, Object> findOrgByStaffId(Map<String, Object> map) {

		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("IntfDAO.findOrgByStaffId", map);
	}

	@Override
	public List<Long> queryStaffByProdId(Long prodIdLong) {
		return (List<Long>) getSqlMapClientTemplate().queryForList("IntfDAO.queryStaffByProdId", prodIdLong);
	}

	@Override
	public String getIDCardEncryptionVector(String mac) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getIDCardEncryptionVector", mac);
	}

	@Override
	public List<Map<String, Object>> getGhAddressTemp() {

		return getSqlMapClientTemplate().queryForList("IntfDAO.getGhAddressTemp");
	}

	@Override
	public void insertGhAddressUnit(Map<String, Object> param) {
		getSqlMapClientTemplate().insert("IntfDAO.insertGhAddressUnit", param);
	}

	@Override
	public long queryBussinessOrder(Map<String, Object> mk) {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.queryBussinessOrder", mk);
	}

	@Override
	public long querySeqBussinessOrder() {

		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.querySeqBussinessOrder");
	}

	@Override
	public void saveOrUpdateBussinessOrderCheck(Map<String, Object> mk, String str) {
		if ("save".equals(str)) {
			getSqlMapClientTemplate().insert("IntfDAO.saveBussinessOrderCheck", mk);
		} else {
			getSqlMapClientTemplate().update("IntfDAO.updateBussinessOrderCheck", mk);
		}

	}

	@Override
	public int isAccNumRealNameparty(Long prodId) {

		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.isAccNumRealNameparty", prodId);
	}

	@Override
	public String getIntfReqCtrlValue(String string) {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getIntfReqCtrlValue", string);
	}

	@Override
	public Map<String, Object>  checkParByIdCust(Map<String, Object> m) {
		Map<String, Object>  queryForObject =( Map<String, Object> )getSqlMapClientTemplate().queryForObject("IntfDAO.checkParByIdCust", m);
		if (queryForObject != null)
			return ( Map<String, Object> ) getSqlMapClientTemplate().queryForObject("IntfDAO.checkParByIdCust", m);
		return null;
	}

	@Override
	public Map<String, Object> queryOfferProdItemsByProdId(Long prodId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferProdItemsByProdId",
				prodId);

	}

	@Override
	public boolean isManyPartyByIDNum(Map<String, Object> m) {
		Long partys = (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.isManyPartyByIDNum", m);

		return null != partys && partys > 0;
	}

	@Override
	public boolean insertSms(Map<String, Object> map) {
		try {
			getSqlMapClientTemplate().insert("IntfDAO.insertSms", map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> qryBoInfos(Map<String, Object> mv) {
		List<Map<String, Object>> boActionTypeSum = null;
		boActionTypeSum = getSqlMapClientTemplate().queryForList("IntfDAO.queryBoActionTypeSum", mv);
		if(boActionTypeSum.size() > 0){
			return getSqlMapClientTemplate().queryForList("IntfDAO.qryBoInfosAdd", mv);
		}
		return getSqlMapClientTemplate().queryForList("IntfDAO.qryBoInfos", mv);
	}

	@Override
	public Long qryChargesByOlid(String ol_id) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("IntfDAO.qryChargesByOlid", ol_id);
	}

	@Override
	public String queryOfferAddressDesc(Long prodId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferAddressDesc", prodId);
	}

	@Override
	public String getTmDescription(Long prodId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getTmDescription", prodId);
	}

	@Override
	public String getOfferProdTmlName(Long prodId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferProdTmlName", prodId);

	}

	@Override
	public String getPartyManagerName(Long prodId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getPartyManagerName", prodId);
	}

	@Override
	public int getOfferSpecAction2Count(String offerSpecId) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferSpecAction2Count", offerSpecId);
	}

	@Override
	public String getCommunityPolicy(String buildingId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getCommunityPolicy", buildingId);
	}

	@Override
	public List<Map> getOfferMemberInfo(String prodId) {
		return  getSqlMapClientTemplate().queryForList("IntfDAO.getOfferMemberInfo", prodId);
		
	}

	@Override
	public String getComponentBuildingId(String serviceId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getComponentBuildingId", serviceId);
	}

	@Override
	public String getOrganizStaffOrgId(Long prodId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOrganizStaffOrgId", prodId);
	}
	
	@Override
	public String getDevelopmentDepartment(String accessNumber){
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getDevelopmentDepartment", accessNumber);
	}

	@Override
	public String getChannelNbrByChannelID(String channelId) {
		
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getChannelNbrByChannelID", channelId);
	}

	@Override
	public int getCmsStaffCodeByStaffCode(String staffCode) {
		
		return Integer.valueOf((String)getSqlMapClientTemplate().queryForObject("IntfDAO.getCmsStaffCodeByStaffCode", staffCode));
	}

	@Override
	public String getOfferProdReduOwnerIdByAccNbr(String accNbr) {

		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getOfferProdReduOwnerIdByAccNbr", accNbr);
	}

	@Override
	public Map<String, Object> getSaopRuleIntfLogByTransactionID(String transactionID) {
		
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getSaopRuleIntfLogByTransactionID",
				transactionID);

	}
	@Override
	public List<Map<String, Object>> validatePhoneCanChangeBand(String accNbr) {
		List<Map<String, Object>> bandList;
		bandList= getSqlMapClientTemplate().queryForList("IntfDAO.validatePhoneCanChangeBand", accNbr);
		if(bandList==null || bandList.size()<=0){
			bandList = getSqlMapClientTemplate().queryForList("IntfDAO.validatePhoneCanChangeBandFTTH", accNbr);
		}
		return bandList;
	}
	@Override
	public List<Map<String, Object>> queryMainOfferPackageRelation(String offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryMainOfferPackageRelation", offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryAccNbrInfoList(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryAccNbrInfoList", accNbr);
	}
	@Override
	public Map<String, Object> queryAccessTypeByAccessNumber(String accNbr,String itemSpecIds) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("accNbr", accNbr);
		paraMap.put("itemSpecIds", itemSpecIds);
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAccessTypeByAccessNumber",paraMap);

	}
	@Override
	public List<Map<String, Object>> getProdCompList(String accNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getProdCompList", accNbr);
	}
	@Override
	public Map<String, Object> queryCompAccNbrByCompProdId(String compProdId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCompAccNbrByCompProdId",compProdId);
	}
	@Override
	public Map<String, Object> querySpeedInfoByOfferSpecId(String offerSpecId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.querySpeedInfoByOfferSpecId",offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryChangeOffersByOfferSpecId(String offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryChangeOffersByOfferSpecId", offerSpecId);
	}
	@Override
	public boolean judgeMainOfferByOfferSpecId(String offerSpecId) {
		boolean flag = false;
		Map<String, Object> offerMap =  (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.judgeMainOfferByOfferSpecId",offerSpecId);
	    if(offerMap!=null && !offerMap.equals("")){
	    	flag = true;
	    }
	    return flag;
	}
	@Override
	public List<Map<String, Object>> queryCompInfoListByOfferSpecId(String offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCompInfoListByOfferSpecId", offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryCompMainOfferByProdSpecId(Map<String,Object> paraMap) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCompMainOfferByProdSpecId", paraMap);
	}
	@Override
	public Map<String, Object> getProdInfoByAccessNuber(String olId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getProdInfoByAccessNuber",olId);

	}
	@Override
	public Map<String, Object> getServInfoByOfferSpecId(String offerSpecId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getServInfoByOfferSpecId",offerSpecId);
		
	}
	@Override
	public List<Map<String,Object>> getComBasicInfoByOfferSpecId(String offerSpecId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getComBasicInfoByOfferSpecId", offerSpecId);
	}
	@Override
	public String queryStaffIdByStaffCode(String staffCode){
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryStaffIdByStaffCode", staffCode);
	}

	@Override
	public Map<String, Object> getMethForFtth(String prodId) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.getMethForFtth", prodId);
	}

	@Override
	public String getProValue(String prodId) {
		return (String)getSqlMapClientTemplate().queryForObject("IntfDAO.getProValue", prodId);
	}

	@Override
	public Map<String, Object> getaddresForFtth(String prodId) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.getaddresForFtth", prodId);
	}

	@Override
	public Map<String, Object> getTmlForFtth(String prodId) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.getTmlForFtth", prodId);
	}

	@Override
	public Map<String, Object> getgetprod2TdIdDelCodeCode(String terminalCode) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.getgetprod2TdIdDelCodeCode", terminalCode);
	}

	@Override
	public boolean checkSchool(String channelId) {
		boolean flag = false;
		Map<String, Object> resultMap =  (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.checkIsSchool",channelId);
	    if(resultMap !=null && !resultMap.equals("")){
	    	flag = true;
	    }
	    return flag;
	}

	@Override
	public List<Map<String, Object>> queryExchangeByName(String name) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryExchangeByName", name);
	}

	@Override
	public List<Map<String, Object>> getchannelByCode(String code) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getchannelByCode", code);
	}

	@Override
	public List<Map<String, Object>> queryDeptInfo(String name,String level) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("level", level);
		paraMap.put("name", name);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryDeptInfo", paraMap);
	}

	@Override
	public List<Map<String, Object>> getOlidByg(String olId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getOlidByg", olId);
	}
	@Override
	public List<Map<String, Object>> getValueByolId(String olId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getValueByolId", olId);
	}

	@Override
	public List<Map<String, Object>> queryChannelsByMap(String staffId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryChannelsByMap", staffId);
	}

	@Override
	public List<Map<String, Object>> checkCinfoByb(String code) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.checkCinfoByb", code);
	}

	@Override
	public Map<String, Object> queryByPartyId(String partyId) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.queryByPartyId", partyId);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsList(String time , String staffId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("time", time);
		paraMap.put("staffId", staffId);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryIndicatorsList", paraMap);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsListMouth(String startTime,
			String endTime , String staffId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryIndicatorsListMouth", paraMap);
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsList(
			String statisticalTime,  String startRown, String endRown,  
			String staffId,String accessNumber) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("startRown", startRown);
		paraMap.put("endRown", endRown);
		paraMap.put("staffId", staffId);
		paraMap.put("accessNumber", accessNumber);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryDetailedIndicatorsList", paraMap);
	}

	@Override
	public Map<String, Object> queryIndicatorsNumber(String statisticalTime,
			String staffId,String accessNumber) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		paraMap.put("accessNumber", accessNumber);
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsNumber", paraMap);
	}

	@Override
	public Map<String, Object> queryEmailByPartyId(String partyId) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("IntfDAO.queryEmailByPartyId", partyId);
	}

	@Override
	public List<Map<String, Object>> getOlnbrByg(String olId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getOlnbrByg", olId);
	}

	@Override
	public String getNumByStatus(String specId) {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.getNumByStatus", specId);
	}
	
	@Override
	public Map<String, Object> queryIndicators1(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators1",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators2(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result =(Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators2",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators3(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators3",paraMap);
		
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators4(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators4",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators9(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result =  (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators9",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators5(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators5",paraMap);
		if(result.get("TOTAL")==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators6(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators6",paraMap);
		if(result.get("TOTAL")==null){
			return para;
		}
		return result;
	}
	
	@Override
	public Map<String, Object> queryIndicators7(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result =  (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators7",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}
	
	@Override
	public Map<String, Object> queryIndicators8(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("STATISTICS_NUM", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result =  (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators8",paraMap);
		if(result==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators10(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators10",paraMap);
		if(result.get("TOTAL")==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicators11(String statisticalTime,
			String staffId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("statisticalTime", statisticalTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicators11",paraMap);
		if(result.get("TOTAL")==null){
			return para;
		}
		return result;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth1(String startTime,
			String endTime, String staffId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth1",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth2(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth2",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth3(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth3",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth10(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth10",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth11(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth11",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth12(String statisticalTime) {
		
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth12",statisticalTime);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth4(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth4",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth5(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth5",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth6(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth6",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth7(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth7",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth8(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth8",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth9(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("TOTAL", "0");
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsMouth9",paraMap);
		return result.get("TOTAL")!=null? result : para;
	}

	@Override
	public Map<String, Object> getAddressPreemptedSeq() {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getAddressPreemptedSeq");
	}

	@Override
	public Map<String, Object> showSchoolName(String bcdCode) {
//		Map<String,String> paraMap = new HashMap<String,String>();
//		paraMap.put("bcdCode", bcdCode);
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.showSchoolName",bcdCode);
	}

	@Override
	public List<Map<String, Object>> qryDeptById(String dept_id) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("IntfDAO.qryDeptById",dept_id);
	}

	@Override
	public List<Map<String, Object>> campusCustomerCampusMark(String subjectId,
			String subjectNameId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("subjectId", subjectId);
		paraMap.put("subjectNameId", subjectNameId);
		return getSqlMapClientTemplate().queryForList("IntfDAO.campusCustomerCampusMark",paraMap);
	}

	@Override
	public List<Map<String, Object>> queryCampusCustomerInformation(String num) {
		String len = num.length() + "";
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("len", len);
		paraMap.put("num", num);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryCampusCustomerInformation",paraMap);
	}

	@Override
	public void saveSendRecord(Map<String, Object> saveMap) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("IntfDAO.saveSendRecord",saveMap);
		
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsListMouth(
			String startTime, String endTime, String startRown, String endRown,
			String staffId,String accessNumber) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("startTime", startTime);
		paraMap.put("startRown", startRown);
		paraMap.put("endRown", endRown);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		paraMap.put("accessNumber", accessNumber);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryDetailedIndicatorsListMouth",paraMap);
	}

	@Override
	public Map<String, Object> qryKeyByFlag() {
		// TODO Auto-generated method stub
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.qryKeyByFlag");
	}

	@Override
	public Map<String, Object> queryIndicatorsNumberMouth(String startTime,
			String endTime, String staffId, String accessNumber) {
		// TODO Auto-generated method stub
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("staffId", staffId);
		paraMap.put("accessNumber", accessNumber);
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIndicatorsNumberMouth",paraMap);
	}

	@Override
	public List<Map<String, Object>> queryRewardInfoById(String staffId,
			String stardTime, String endTime,String startNum,String endNum) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("staffId", staffId);
		paraMap.put("stardTime", stardTime);
		paraMap.put("endTime", endTime);
		paraMap.put("startNum", startNum);
		paraMap.put("endNum", endNum);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryRewardInfoById",paraMap);
	}

	@Override
	public Map<String, Object> queryRewardSum(String staffId, String stardTime,
			String endTime) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("staffId", staffId);
		paraMap.put("stardTime", stardTime);
		paraMap.put("endTime", endTime);
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryRewardSum",paraMap);
	}

	@Override
	public Map<String, Object> queryRewardInfo(String rewardId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("rewardId", rewardId);
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryRewardInfo",paraMap);
	}

	@Override
	public List<Map<String, Object>> querypartyListBypartyList(String idCard) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.querypartyListBypartyList",idCard);
	}

	@Override
	public List<Map<String, Object>> queryProListByProId(String prodId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryProListByProId",prodId);
	}

	@Override
	public Map<String, Object> queryOlidByProId(String prodId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOlidByProId",prodId);
	}

	@Override
	public Map<String, Object> queryUimCodeByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryUimCodeByAccessNumber",accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOlIdByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOlIdByAccessNumber",accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOrganizationforScrm(
			String staffNumber, String staffName, String organizationId,
			String ruleNumber) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("staffNumber", staffNumber);
		paraMap.put("staffName", staffName);
		paraMap.put("organizationId", organizationId);
		paraMap.put("ruleNumber", ruleNumber);
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryOrganizationforScrm",paraMap);
	}

	@Override
	public Map<String, Object> queryOlNbrByOlId(String id) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOlNbrByOlId",id);
	}

	@Override
	public List<Map<String, Object>> queryRewardSource(String olNbr) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryRewardSource",olNbr);
	}

	@Override
	public void insertReward(Map<String, String> para) {
		getSqlMapClientTemplate().insert("IntfDAO.insertReward", para);
	}

	@Override
	public String queryRewardId() {
		return (String) getSqlMapClientTemplate().queryForObject("IntfDAO.queryRewardId");
	}

	@Override
	public Map<String, Object> getFingerInfo(String olId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getFingerInfo",olId);
	}

	@Override
	public void insertFingerInfo(Map<String, Object> fingerInfo) {
		getSqlMapClientTemplate().insert("IntfDAO.insertFingerInfo", fingerInfo);
	}

	@Override
	public List<Map<String, Object>> queryStoreByStaffId(String staffId) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryStoreByStaffId",staffId);
	}

	@Override
	public List<Map<String, Object>> queryStatusByInstCode(String mktResInstCode) {
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryStatusByInstCode",mktResInstCode);
	}

	@Override
	public Map<String, Object> getStaffIdBystaffNumber(String staffNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getStaffIdBystaffNumber",staffNumber);
	}

	@Override
	public List<Map<String, Object>> getEdidInfo() {
		return getSqlMapClientTemplate().queryForList("IntfDAO.getEdidInfo");
//		return null;
	}

	@Override
	public void edidType(String partyId) {
		getSqlMapClientTemplate().update("IntfDAO.edidType", partyId);
	}

	@Override
	public Map<String, Object> queryIdentityInfoByOlnbr(String olNbr) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryIdentityInfoByOlnbr",olNbr);
	}

	@Override
	public Map<String, Object> queryProdInfoByAccs(String accessNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryProdInfoByAccs",accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryDepinfoForScrm(String partyId) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryDepinfoForScrm", partyId);
	}

	@Override
	public List<Map<String, Object>> queryUserdByAccess(String accessNuber) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryUserdByAccess", accessNuber);
	}

	@Override
	public Map<String, Object> queryExtCustOrder(String queryExtCustOrder) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryExtCustOrder",queryExtCustOrder);
	}

	@Override
	public String[] findPartyByIdentityPic(String name, String identityNum) {
		
		LogIdentityCheckGzt logIdentityCheck = new LogIdentityCheckGzt();
		logIdentityCheck.setPartyName(name);
		logIdentityCheck.setIdentityNumber(identityNum);
		String strGztXml = null;
		String partyName = null;
		 List<LogIdentityCheckGzt> list= getSqlMapClientTemplate().queryForList("IntfDAO.selectByNameAndPic", logIdentityCheck);
		 if (list != null && list.size() >= 1) {
			 strGztXml = list.get(0).getReturnMsg().trim();
			 partyName = list.get(0).getPartyName();
		 }
		 String[] resultArray = {strGztXml,partyName};
		return  resultArray;
	}

	@Override
	public List<Map<String, Object>> queryPhoneNoByAliUid(String aliUid) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryPhoneNoByAliUid", aliUid);
	}

	@Override
	public String getParentOrgId(String mailAddressId) {
		Map<String, Object> map =(Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getParentOrgId",mailAddressId);
		if(map != null){
			return map.get("PARENT_ORG_ID").toString();
		}else{
			return "";
		}
	}

	@Override
	public void upStarMember(Map<String, Object> parameter) {
		getSqlMapClientTemplate().update("IntfDAO.upStarMember", parameter);
	}

	@Override
	public Map<String, Object> queryCaflagByOlnbr(String olNbr) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryCaflagByOlnbr",olNbr);
	}

	@Override
	public String queryBlocOlidToProOlid(String olId) {
		Map<String, Object> proOlId = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryBlocOlidToProOlid",olId);
		if(proOlId == null){
			return "";
		}else{
			return  proOlId.get("OL_ID").toString();
		}
	}

	@Override
	public void insertPartyFlagInfo(Map<String, Object> autonymFlag) {
		getSqlMapClientTemplate().insert("IntfDAO.insertPartyFlagInfo", autonymFlag);
	}

	@Override
	public String queryBlocOlidToProOlNbr(String olId) {
		Map<String, Object> proOlId = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryBlocOlidToProOlNbr",olId);
		if(proOlId ==null){
			return "";
		}else{
			return (String) proOlId.get("OL_ID").toString();
		}
	}

	@Override
	public String queryBlocOlidToBlocOlNbr(String olNbr) {
		Map<String, Object> proOlId = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryBlocOlidToBlocOlNbr",olNbr);
		if(proOlId ==null){
			return "";
		}else{
			return (String) proOlId.get("OL_ID").toString();
		}
	}

	@Override
	public void InsertOutskirts(Map<String, Object> parameter) {
		getSqlMapClientTemplate().insert("IntfDAO.InsertOutskirts", parameter);
	}

	@Override
	public Map<String, Object> queryOlidIfHave(String olId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOlidIfHave",olId);
	}

	@Override
	public void updataOutskirts(Map<String, Object> parameter) {
		getSqlMapClientTemplate().update("IntfDAO.updataOutskirts", parameter);
	}

	@Override
	public Map<String, Object> queryPartyIdByprodId(String prodId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryPartyIdByprodId",prodId);
	}

	@Override
	public Map<String, Object> queryStaffIdBystaffCode(String staffCode) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryStaffIdBystaffCode",staffCode);
	}

	@Override
	public Map<String, Object> queryOfferspecidBystaffCode(String prodId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOfferspecidBystaffCode",prodId);
	}

	@Override
	public Map<String, Object> queryOldcustinfoByPartyId(String partyId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryOldcustinfoByPartyId",partyId);
	}

	@Override
	public Map<String, Object> queryPartyIdByCardId(String cerdValue) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryPartyIdByCardId",cerdValue);
	}

	@Override
	public Map<String, Object> getSchoolRole(String staffId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.getSchoolRole",staffId);
	}

	@Override
	public Map<String, Object> queryGroupNumberByGroupId(String olId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryGroupNumberByGroupId",olId);
	}

	@Override
	public List<Map<String, Object>> queryProvenceIdByGroupNum(String olId) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryProvenceIdByGroupNum",olId);
	}

	@Override
	public Map<String, Object> queryBureauDirectionByPhoneNum(String phoneNumber) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryBureauDirectionByPhoneNum",phoneNumber);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByIdent(String custInfo) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryRealNameFlagByIdent", custInfo);

	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByParttId(String custInfo) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryRealNameFlagByParttId", custInfo);

	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByPhoneAccssnumber(
			String custInfo) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.queryRealNameFlagByPhoneAccssnumber", custInfo);
	}

	@Override
	public Map<String, Object> queryPictureByolId(String olId) {
		String ptctureStr = null;
		String partyIdStr = null;
		FingerPhotoCut fingerPhotoCut = new FingerPhotoCut();
		Map<String, Object> pictureMap = new HashMap<String, Object>();
		List<FingerPhotoCut> list = getSqlMapClientTemplate().queryForList("IntfDAO.queryPictureByolId", olId);
		//return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryPictureByolId", olId);
		if (list != null && list.size() >= 1) {
			 ptctureStr = list.get(0).getPhotoCut().trim();
			 pictureMap.put("PHOTOCUT", ptctureStr);
			 partyIdStr = list.get(0).getPartyId().toString();
			 pictureMap.put("PARTYID", partyIdStr);
		 }
		return pictureMap;
	}

	@Override
	public Map<String, Object> querygztPictureInfoByolId(String olId) {
		String ptctureStr = null;
		Map<String, Object> pictureMap = new HashMap<String, Object>();
		LogIdentityCheckGzt logIdentityCheck = new LogIdentityCheckGzt();
		List<LogIdentityCheckGzt> list= getSqlMapClientTemplate().queryForList("IntfDAO.querygztPictureInfoByolId", olId);
		if (list != null && list.size() >= 1) {
			 ptctureStr = list.get(0).getReturnMsg().trim();
			 pictureMap.put("GZTMSG", ptctureStr);
		 }
		return pictureMap;
	}

	@Override
	public String queryAccIdbyAccCd(String accCd) {
		Map<String, Object> result = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("IntfDAO.queryAccIdbyAccCd",accCd);
		String accId = "";
		if(result != null){
			accId = result.get("ACCT_ID").toString();
		}
		return accId;
	}

	@Override
	public List<Map<String, Object>> desensitizationService(String serviceName) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.desensitizationService", serviceName);
	}

	@Override
	public String desensitizationSystemCode(String desensitizationSystemId) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("IntfDAO.desensitizationSystemCode", desensitizationSystemId);
		String desensitizationSystemCode = "";
		if(list.size()>0){
			desensitizationSystemCode = (String) list.get(0).get("SYSTEM_GRADE");
		}else{
			desensitizationSystemCode = "0";
		}
		return desensitizationSystemCode;
	}

	@Override
	public void savaDesensitizationLog(Map<String, Object> map) {
		getSqlMapClientTemplate().insert("IntfDAO.savaDesensitizationLog", map);
	}
	
	@Override
	public Map<String, Object> queryNewPartyPhotoByOlId(String olId) {
		String ptctureStr = null;
		Map<String, Object> pictureMap = new HashMap<String, Object>();
		LogIdentityCheckGzt logIdentityCheck = new LogIdentityCheckGzt();
		List<LogIdentityCheckGzt> list= getSqlMapClientTemplate().queryForList("IntfDAO.queryNewPartyPhotoByOlId", olId);
		if (list != null && list.size() >= 1) {
			 ptctureStr = list.get(0).getReturnMsg().trim();
			 pictureMap.put("RETURN_MSG", ptctureStr);
		 }
		return pictureMap;
	}
	
	public List<Object> queryOfferSpecInfoList(Map<String, Object> param) {		
		return this.getSqlMapClientTemplate().queryForList("IntfDAO.queryOfferSpecInfoList", param);
	}
	
	public List<Map<String,Object>> queryChannelInfoByIdentityNum(String identityNum) {		
		return getSqlMapClientTemplate().queryForList("IntfDAO.queryChannelInfoByIdentityNum", identityNum);
	}
	
	@Override
	public Map<String, Object> getStartLevelByPartyAccNbr(String accNbr) {
		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("IntfDAO.getStartLevelByPartyAccNbr", accNbr);	
	}
}
