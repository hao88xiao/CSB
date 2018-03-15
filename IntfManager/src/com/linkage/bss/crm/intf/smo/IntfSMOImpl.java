package com.linkage.bss.crm.intf.smo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import bss.systemmanager.provide.SmService;

import com.ai.bss.crm.annotation.SwitchDS;
import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.cust.smo.IChannelSMO;
import com.linkage.bss.crm.intf.bmo.IntfBMO;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.model.AreaInfo;
import com.linkage.bss.crm.intf.model.BankTableEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntityInputBean;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntity;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntityInputBean;
import com.linkage.bss.crm.intf.model.LinkManInfo;
import com.linkage.bss.crm.intf.model.OperatingOfficeInfo;
import com.linkage.bss.crm.intf.model.ProdByCompProdSpec;
import com.linkage.bss.crm.intf.model.ProdServRela;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.model.ServActivate;
import com.linkage.bss.crm.intf.model.ServActivatePps;
import com.linkage.bss.crm.intf.model.StaticData;
import com.linkage.bss.crm.intf.model.Tel2Area;
import com.linkage.bss.crm.intf.util.ResultCode;
import com.linkage.bss.crm.intf.util.WSUtil;
import com.linkage.bss.crm.local.bmo.IBillServiceSMO;
import com.linkage.bss.crm.local.bmo.IUnityPaySMO;
import com.linkage.bss.crm.local.bmo.SlcBMO;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferSpecParam;
import com.linkage.bss.crm.model.OrderList;
import com.linkage.bss.crm.model.ProdSpec;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.rsc.smo.RscServiceSMO;
import com.linkage.bss.crm.so.query.smo.ISoQuerySMO;
import com.linkage.bss.crm.so.save.bmo.ISoSaveBMO;
import com.linkage.bss.crm.unityPay.IndentItemPay;
import com.linkage.bss.crm.unityPay.IndentItemSync;
import com.linkage.bss.dbrouter.provider.DsKey;

public class IntfSMOImpl implements IntfSMO {
	private static Log logger = Log.getLog(IntfSMOImpl.class);

	private IntfBMO intfBMO;

	private IUnityPaySMO unityPayClient;

	private String voucherPrintUrl;

	private SmService smService;

	private IChannelSMO channelSMO;

	private SlcBMO slcBMO;

	private RscServiceSMO rscServiceSMO;

	private ISoQuerySMO soQuerySMO;

	private IBillServiceSMO billService;

	private ISoSaveBMO soSaveBMO;

	public void setSoSaveBMO(ISoSaveBMO soSaveBMO) {
		this.soSaveBMO = soSaveBMO;
	}

	public void setBillService(IBillServiceSMO billService) {
		this.billService = billService;
	}

	public void setSoQuerySMO(ISoQuerySMO soQuerySMO) {
		this.soQuerySMO = soQuerySMO;
	}

	public void setRscServiceSMO(RscServiceSMO rscServiceSMO) {
		this.rscServiceSMO = rscServiceSMO;
	}

	public void setSlcBMO(SlcBMO slcBMO) {
		this.slcBMO = slcBMO;
	}

	public void setSmService(SmService smService) {
		this.smService = smService;
	}

	public void setChannelSMO(IChannelSMO channelSMO) {
		this.channelSMO = channelSMO;
	}

	public void setVoucherPrintUrl(String voucherPrintUrl) {
		this.voucherPrintUrl = voucherPrintUrl;
	}

	public void setIntfBMO(IntfBMO intfBMO) {
		this.intfBMO = intfBMO;
	}

	public void setUnityPayClient(IUnityPaySMO unityPayClient) {
		this.unityPayClient = unityPayClient;
	}

	@Override
	public OfferProd getProdByAccessNumber(String accessNumber) {
		if (StringUtils.isBlank(accessNumber)) {
			throw new IllegalArgumentException("accessNumbr can't be null");
		}
		long start = System.currentTimeMillis();
		OfferProd prodByAccessNumber = intfBMO.getProdByAccessNumber(accessNumber);
		logger.error("OfferProd.intfBMO.getProdByAccessNumber 执行时间:" + (System.currentTimeMillis() - start));
		return prodByAccessNumber;
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<StaticData> initStaticData(String id) {
		return intfBMO.initStaticData(id);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> qryOfferModel(Map<String, Object> param) {
		return intfBMO.qryOfferModel(param);
	}

	@Override
	public int isValidProdQryPwd(Long prodId, String password) {
		return intfBMO.isValidProdQryPwd(prodId, password);
	}

	@Override
	public int isValidProdBizPwd(Long prodId, String password) {
		return intfBMO.isValidProdBizPwd(prodId, password);
	}

	@Override
	public int isValidAcctPwd(Long acctId, String password) {
		return intfBMO.isValidAcctPwd(acctId, password);
	}

	@Override
	public Tel2Area queryAccNBRInfo(String accNbr) {
		return intfBMO.queryAccNBRInfo(accNbr);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public AreaInfo queryAreaInfo(String areaCode, String areaName) {
		return intfBMO.queryAreaInfo(areaCode, areaName);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> queryFNSInfo(String accNbr, String accNbrType) {
		return intfBMO.queryFNSInfo(accNbr, accNbrType);
	}

	@Override
	public String goodsBatchGet(String trade_id, String sale_time, String channelId, String staffCode,
			String value_card_type_code, String value_code, String apply_num, int flag) {

		return intfBMO.goodsBatchGet(trade_id, sale_time, channelId, staffCode, value_card_type_code, value_code,
				apply_num, flag);
	}
    @SwitchDS(DsKey.CRM_SLAVE)
	public List<OperatingOfficeInfo> queryOperatingOfficeInfo(String areaCode, String queryType, String areaName) {
		return intfBMO.queryOperatingOfficeInfo(areaCode, queryType, areaName);
	}

	@Override
	public Date getCurrentTime() {
		return intfBMO.getCurrentTime();
	}

	@Override
	public List<RoleObj> findRoleObjs(Integer objType, Long objId) {
		return intfBMO.findRoleObjs(objType, objId);
	}

	@Override
	public Account findAcctByAccNbr(String accessNumber, Integer prodSpecId) {
		return intfBMO.findAcctByAccNbr(accessNumber, prodSpecId);
	}

	@Override
	public Account findAcctByAcctCd(String acctCd) {
		return intfBMO.findAcctByAcctCd(acctCd);
	}

	@Override
	public Offer findOfferByProdIdAndOfferSpecId(Long prodId, Long offerSpecId) {
		return intfBMO.findOfferByProdIdAndOfferSpecId(prodId, offerSpecId);
	}

	@Override
	public OfferProd2Td findOfferProd2Td(Long prodId, Long terminalDevSpecId) {
		return intfBMO.findOfferProd2Td(prodId, terminalDevSpecId);
	}

	@Override
	public OfferProdItem findOfferProdItem(Long prodId, Long itemSpecId) {
		return intfBMO.findOfferProdItem(prodId, itemSpecId);
	}

	@Override
	public OfferServ findOfferServByProdIdAndServSpecId(Long prodId, Long servSpecId) {
		return intfBMO.findOfferServByProdIdAndServSpecId(prodId, servSpecId);
	}

	@Override
	public OfferRoles findProdOfferRoles(Long offerSpecId, Long prodSpecId) {
		return intfBMO.findProdOfferRoles(offerSpecId, prodSpecId);
	}

	@Override
	public ProdSpec2AccessNumType findProdSpec2AccessNumType(Long prodSpecId) {
		return intfBMO.findProdSpec2AccessNumType(prodSpecId);
	}

	@Override
	public ProdSpec2AccessNumType findProdSpec2AccessNumType2(Long prodSpecId) {
		return intfBMO.findProdSpec2AccessNumType2(prodSpecId);
	}

	@Override
	public RoleObj findRoleObjByOfferRoleIdAndObjType(Long offerRoleId, Integer objType) {
		return intfBMO.findRoleObjByOfferRoleIdAndObjType(offerRoleId, objType);
	}

	@Override
	public List<OfferRoles> findServOfferRoles(Long offerSpecId) {
		return intfBMO.findServOfferRoles(offerSpecId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String queryNbrByIccid(String iccid) {
		return intfBMO.queryNbrByIccid(iccid);
	}

	@Override
	public String findOfferOrService(Long id) {
		return intfBMO.findOfferOrService(id);
	}

	@Override
	public Long selectByAcctId(Long id) {
		return intfBMO.selectByAcctId(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> findServSpecItem(Long id, String flag) {
		return intfBMO.findServSpecItem(id, flag);
	}

	@Override
	public Map isSubsidy(String coupon_ins_number) {
		return intfBMO.isSubsidy(coupon_ins_number);
	}

	@Override
	public ProdSpec getProdSpecByProdSpecId(Long prodSpecId) {
		return intfBMO.getProdSpecByProdSpecId(prodSpecId);
	}

	@Override
	public String queryProdBizPwdByProdId(Long prodId) {
		return intfBMO.queryProdBizPwdByProdId(prodId);
	}

	@Override
	public String queryProdQryPwdByProdId(Long prodId) {
		return intfBMO.queryProdQryPwdByProdId(prodId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> newValidateService(String accessNumber, String custName, String cardType, String card) {
		return intfBMO.newValidateService(accessNumber, custName, cardType, card);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, String> isYKSXInfo(String accNbr) {
		return intfBMO.isYKSXInfo(accNbr);
	}

	@Override
	public List<String> queryImsiInfoByMdn(Long prodId) {
		return intfBMO.queryImsiInfoByMdn(prodId);
	}

	@Override
	public Map<String, Object> checkGlobalroam(Long prodId, Long offerSpecId) {
		return intfBMO.checkGlobalroam(prodId, offerSpecId);
	}

	@Override
	public String cancelOrder(String olId, String areaId, String channelId, String staffCode) {
		return intfBMO.cancelOrder(olId, areaId, channelId, staffCode);
	}

	@Override
	public Long selectByAcctCd(String id) {
		return intfBMO.selectByAcctCd(id);
	}

	@Override
	public Map<String, Long> selectRoleCdAndOfferRoles(Map<String, Long> param) {
		return intfBMO.selectRoleCdAndOfferRoles(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<ProdByCompProdSpec> queryProdByCompProdSpecId(Map<String, Object> param) {
		return intfBMO.queryProdByCompProdSpecId(param);
	}

	@Override
	public int compProdRule(String accNum, String offerId) {
		return intfBMO.compProdRule(accNum, offerId);
	}

	@Override
	public String queryAccNumByTerminalCode(String terminalCode) {
		return intfBMO.queryAccNumByTerminalCode(terminalCode);
	}

	@Override
	public Map<String, Object> queryPhoneNumberInfoByAnId(Map<String, Object> param) {
		return intfBMO.queryPhoneNumberInfoByAnId(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, String>> queryStaffInfoByStaffName(String staffName) {
		return intfBMO.queryStaffInfoByStaffName(staffName);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, String>> queryStaffInfoByStaffNumber(String staffNumber) {
		return intfBMO.queryStaffInfoByStaffNumber(staffNumber);
	}

	@Override
	public List<Map<String, Object>> getClerkId(String accNbr) {
		return intfBMO.getClerkId(accNbr);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public void addReceiptPringLog(List<String> coNbrList, String flag) throws Exception {
		try {
			final List<String> paramList = new ArrayList<String>();
			// 先去掉已经存在的记录
			for (int i = 0; i < coNbrList.size(); i++) {
				String coNbr = ((String) coNbrList.get(i)).trim();
				int count = intfBMO.isRPLogExist(coNbr);
				if (count == 0) {
					paramList.add(coNbr);
				}
			}
			for (String coNbr : paramList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("coNbr", coNbr);
				map.put("flag", flag);
				intfBMO.insertReceiptPringLog(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int judgeCoupon2OfferSpec(String offer_spec_id, String couponId) {
		return intfBMO.judgeCoupon2OfferSpec(offer_spec_id, couponId);
	}

	@Override
	public Map<String, Object> getBrandLevelDetail(Map<String, Object> param) {
		return intfBMO.getBrandLevelDetail(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, String>> qryPartyInfo(Map<String, Object> param) {
		return intfBMO.qryPartyInfo(param);
	}

	@Override
	public Map<String, Object> qryPartyManager(String identityNum) {
		return intfBMO.qryPartyManager(identityNum);
	}

	@Override
	public Map queryCustProfiles(Long partyId) {
		return intfBMO.queryCustProfiles(partyId);
	}

	@Override
	public Map queryIdentifyList(Long partyId) {
		return intfBMO.queryIdentifyList(partyId);
	}

	@Override
	public String getChannelId(String parentOrg) {
		return intfBMO.getChannelId(parentOrg);
	}

	@Override
	public long getPartyIdSeq() {
		return intfBMO.getPartyIdSeq();
	}

	@Override
	public void synAgentToParty(Map<String, String> map) {
		map.put("AREA_ID", "11000");
		map.put("PARTY_TYPE_CD", "22");
		map.put("DEFAULT_ID_TYPE", "42");
		map.put("BUSINESS_PASSWORD", "888888");
		map.put("QUERY_PASSWORD", "888888");
		map.put("PARTY_STATUS_CD", "1");
		intfBMO.synAgentToParty(map);
	}

	@Override
	public void synAgentToTax(Map<String, String> map) {
		map.put("SYS_FLAG", "3000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "1");
		map.put("DEAL_FLAG", "0");
		intfBMO.synAgentToTax(map);
	}

	@Override
	public void synAgentToABM(Map<String, String> map) {
		map.put("SYS_FLAG", "7000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "1");
		map.put("DEAL_FLAG", "0");
		intfBMO.synAgentToABM(map);
	}

	@Override
	public void synBranchToParty(Map<String, String> map) {
		map.put("AREA_ID", "11000");
		map.put("PARTY_TYPE_CD", "22");
		map.put("DEFAULT_ID_TYPE", "42");
		map.put("BUSINESS_PASSWORD", "888888");
		map.put("QUERY_PASSWORD", "888888");
		map.put("PARTY_STATUS_CD", "1");
		intfBMO.synBranchToParty(map);
	}

	@Override
	public void synBranchToTax(Map<String, String> map) {
		map.put("SYS_FLAG", "3000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "1");
		map.put("DEAL_FLAG", "0");
		intfBMO.synBranchToTax(map);
	}

	@Override
	public void synBranchToABM(Map<String, String> map) {
		map.put("SYS_FLAG", "7000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "1");
		map.put("DEAL_FLAG", "0");
		intfBMO.synAgentToABM(map);
	}

	@Override
	public void modifySynTax(Map<String, String> map) {
		map.put("SYS_FLAG", "3000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "2");
		map.put("DEAL_FLAG", "0");
		intfBMO.synBranchToTax(map);
	}

	@Override
	public void modifySynABM(Map<String, String> map) {
		map.put("SYS_FLAG", "7000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "2");
		map.put("DEAL_FLAG", "0");
		intfBMO.synAgentToABM(map);
	}

	@Override
	public void deleteSynTax(Map<String, String> map) {
		map.put("SYS_FLAG", "3000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "4");
		map.put("DEAL_FLAG", "0");
		intfBMO.synBranchToTax(map);
	}

	@Override
	public void deleteSynABM(Map<String, String> map) {
		map.put("SYS_FLAG", "7000");
		map.put("PARTY_TYPE", "2");
		map.put("ACTION_TYPE", "4");
		map.put("DEAL_FLAG", "0");
		intfBMO.synAgentToABM(map);
	}

	@Override
	public void deleteChannel(String partyId) {
		intfBMO.deleteChannel(partyId);
	}

	@Override
	public long getChannelIdSeq() {
		return intfBMO.getChannelIdSeq();
	}

	@Override
	public void agentIntoChannel(Map<String, String> map) {
		map.put("AREA_ID", "11000");
		map.put("CHANNEL_SPEC_ID", "2");
		map.put("CAPACITY", "1000");
		map.put("CHANNEL_STATUS_CD", "1");
		intfBMO.agentIntoChannel(map);
	}

	@Override
	public void agentUpdateChannel(Map<String, String> map) {
		intfBMO.agentUpdateChannel(map);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> qryOrderSimpleInfoByOlId(Map<String, Object> param) {
		return intfBMO.qryOrderSimpleInfoByOlId(param);
	}

	@Override
	public Map queryCustBrand(Long partyId) {

		return intfBMO.queryCustBrand(partyId);
	}

	@Override
	public Map queryCustLevel(Long partyId) {
		return intfBMO.queryCustLevel(partyId);
	}

	@Override
	public Map queryCustSegment(Long partyId) {
		return intfBMO.queryCustSegment(partyId);
	}

	@Override
	public void storeIntoChannel(Map<String, String> map) {
		map.put("AREA_ID", "11000");
		map.put("CHANNEL_SPEC_ID", "20");
		map.put("CAPACITY", "1000");
		map.put("CHANNEL_STATUS_CD", "1");
		intfBMO.storeIntoChannel(map);
	}

	@Override
	public void storeUpdateChannel(Map<String, String> map) {
		intfBMO.storeUpdateChannel(map);
	}

	@Override
	public void storeDeleteChannel(String channelId) {
		intfBMO.storeDeleteChannel(channelId);
	}

	@Override
	public void synCrmChannel(Map<String, String> map) {
		map.put("AREA_ID", "11000");
		map.put("AREA_ID", "1000");
		map.put("CHANNEL_STATUS_CD", "1");
		intfBMO.synCrmChannel(map);
	}

	@Override
	public void insertCrmGisParty(Map<String, String> map) {
		intfBMO.insertCrmGisParty(map);
	}

	@Override
	public void updateCrmGisParty(Map<String, String> map) {
		intfBMO.updateCrmGisParty(map);
	}

	@Override
	public void delUpdateCrmGisParty(Map<String, String> map) {
		intfBMO.delUpdateCrmGisParty(map);
	}

	@Override
	public InstStatus queryInstStatusByCd(String statusCd) {
		return intfBMO.queryInstStatusByCd(statusCd);
	}

	@Override
	public Map<String, Object> qryIfPkByPartyId(Map<String, String> param) {
		return intfBMO.qryIfPkByPartyId(param);
	}

	@Override
	public List<ProdServRela> queryProdServRelas(int servSpecId) {
		return intfBMO.queryProdServRelas(servSpecId);
	}

	@Override
	public OfferProdComp queryOfferProdComp(Long prodCompId) {
		return intfBMO.queryOfferProdComp(prodCompId);
	}

	@Override
	public void updateOrInsertAgent2prm(Map<String, Object> param) {
		intfBMO.updateOrInsertAgent2prm(param);
	}

	@Override
	public void updateOrInsertChannelForCrm(Map<String, Object> param) {
		intfBMO.updateOrInsertChannelForCrm(param);
	}

	@Override
	public List<Map<String, Object>> queryAttachOfferByProd(Long offerSpecId, Long prodSpecId) {
		return intfBMO.queryAttachOfferByProd(offerSpecId, prodSpecId);
	}

	@Override
	public Map computeChargeInfo(Long ol_id) {
		return intfBMO.computeChargeInfo(ol_id);
	}

	@Override
	public List queryChargeInfo(Long ol_id) {
		return intfBMO.queryChargeInfo(ol_id);
	}

	@Override
	public Map<String, Object> getChannelIdByPartyId(Map<String, Object> param) {
		return intfBMO.getChannelIdByPartyId(param);
	}

	@Override
	public List<String> queryOfferSpecAttr(Long offerSpecId) {
		return intfBMO.queryOfferSpecAttr(offerSpecId);
	}

	@Override
	public String queryOfferSpecValueParam(Long offerSpecId) {
		return intfBMO.queryOfferSpecValueParam(offerSpecId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> getAccessNumberByUimNo(Map<String, Object> param) {
		return intfBMO.getAccessNumberByUimNo(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> getUserZJInfoByAccessNumber(Map<String, Object> param) {
		return intfBMO.getUserZJInfoByAccessNumber(param);
	}

	@Override
	public void updateOrInsertGisParty(Map<String, Object> param) {
		intfBMO.updateOrInsertGisParty(param);
	}

	@Override
	public Map<String, Object> getHighFeeInfo(Map<String, Object> param) {
		return intfBMO.getHighFeeInfo(param);
	}

	@Override
	public boolean updateOrInsertCepChannelFromPrmToCrm(Map<String, Object> param) {
		return intfBMO.updateOrInsertCepChannelFromPrmToCrm(param);
	}

	@Override
	public Map<String, Object> selectPartyInfoFromSmParty(Map<String, Object> param) {
		return intfBMO.selectPartyInfoFromSmParty(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> checkDeviceIdInLogin(Map<String, Object> param) {
		return intfBMO.checkDeviceIdInLogin(param);
	}

	@Override
	public boolean updatePadPwdInLogin(Map<String, Object> param) {
		return intfBMO.updatePadPwdInLogin(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectChannelInfoByPartyId(Map<String, Object> param) {
		return intfBMO.checkSnPwd4SelectChannelInfoByPartyId(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectStaffInfoByStaffNumber(Map<String, Object> param) {
		return intfBMO.checkSnPwd4SelectStaffInfoByStaffNumber(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwdInLogin(Map<String, Object> param) {
		return intfBMO.checkSnPwdInLogin(param);
	}

	@Override
	public List<Map<String, Object>> getStaffCodeAndStaffName(Map<String, Object> param) {
		return intfBMO.getStaffCodeAndStaffName(param);
	}

	@Override
	public boolean insertPadPwdLog(Map<String, Object> param) {
		return intfBMO.insertPadPwdLog(param);
	}

	@Override
	public boolean insertSmsWaitSendCrmSomeInfo(Map<String, Object> param) {
		return intfBMO.insertSmsWaitSendCrmSomeInfo(param);
	}

	@Override
	public List<Map<String, Object>> transmitRandom4SelectStaffInfoByDeviceId(Map<String, Object> param) {
		return intfBMO.transmitRandom4SelectStaffInfoByDeviceId(param);
	}

	@Override
	public String oldCGFlag(String materialId) {
		return intfBMO.oldCGFlag(materialId);
	}

	@Override
	public OfferSpecParam queryOfferSpecParam(String offerSpecParamId) {
		return intfBMO.queryOfferSpecParam(offerSpecParamId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> queryMainOfferSpecNameAndIdByOlId(Map<String, Object> param) {
		return intfBMO.queryMainOfferSpecNameAndIdByOlId(param);
	}

	@Override
	public int queryIfPk(Long partyId) {
		return intfBMO.queryIfPk(partyId);
	}

	@Override
	public String getOlNbrByOlId(long olId) {
		return intfBMO.getOlNbrByOlId(olId);
	}

	@Override
	public String getPartyIdByGroupSeq(String groupSeq) {
		return intfBMO.getPartyIdByGroupSeq(groupSeq);
	}

	@Override
	public String getGroupSeqByPartyId(String partyId) {
		return intfBMO.getGroupSeqByPartyId(partyId);
	}

	@Override
	public String getAnTypeCdByProdSpecId(String prodSpecId, String isPrimary) {
		return intfBMO.getAnTypeCdByProdSpecId(prodSpecId, isPrimary);
	}

	@Override
	public void insertBServActivate(ServActivate servActivate) {
		intfBMO.insertBServActivate(servActivate);
	}

	@Override
	public void insertBServActivatPps(ServActivatePps servActivatePps) {
		intfBMO.insertBServActivatPps(servActivatePps);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getPayIndentIdByBcdCode(String bcdCode) {
		return intfBMO.getPayIndentIdByBcdCode(bcdCode);
	}

	@Override
	public void savePayIndentId(Map<String, String> map) {
		intfBMO.savePayIndentId(map);
	}

	@Override
	public List<Long> queryCategoryNodeId(Long offerSpecId) {
		return intfBMO.queryCategoryNodeId(offerSpecId);
	}

	@Override
	public Long queryIfRootNode(Long categoryNodeId) {
		return intfBMO.queryIfRootNode(categoryNodeId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> queryUimNum(String phoneNumber) {
		return intfBMO.queryUimNum(phoneNumber);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> getPartyIdentityList(String partyId) {
		return intfBMO.getPartyIdentityList(partyId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getTmlCodeByPhoneNumber(String phoneNumber) {
		return intfBMO.getTmlCodeByPhoneNumber(phoneNumber);
	}

	@Override
	public Map<String, Object> getPartyPW(String partyId) {
		return intfBMO.getPartyPW(partyId);
	}

	@Override
	public String indentItemPayIntf(IndentItemPay indentItemPay2) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = unityPayClient.indentItemPay(indentItemPay2);
			String result = map.get("result").toString();
			if ("0".equals(result)) {
//				intfBMO.indentItemPayIntf(indentItemPay2);
//				return "<response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>";
				StringBuffer rt = new StringBuffer();
				rt.append("<response><resultCode>0</resultCode><resultMsg>成功</resultMsg>");
				rt.append("<PAY_INDENT_ID>").append(indentItemPay2.getPayIdentId()).append("</PAY_INDENT_ID>");
				rt.append("</response>");
				
				intfBMO.indentItemPayIntf(indentItemPay2);
//				return "<response><resultCode>0</resultCode><resultMsg>成功</resultMsg></response>";
				return rt.toString();
			} else {
				return "<response><resultCode>1</resultCode><resultMsg>费用同步失败</resultMsg></response>";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getPartyNumberCount(String accessNumber) {
		return intfBMO.getPartyNumberCount(accessNumber);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public int ifImportantPartyByPartyId(Long partyId) {
		return intfBMO.ifImportantPartyByPartyId(partyId);
	}

	@Override
	public String getPayItemId() {
		return intfBMO.getPayItemId();
	}

	@Override
	public String getItemTypeByBcdCode(String bcdCode) {
		return intfBMO.getItemTypeByBcdCode(bcdCode);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<String> queryCardinfoByAcct(String accessNumber) {
		return intfBMO.queryCardinfoByAcct(accessNumber);
	}

	@Override
	public String getSecondAccNbrByProdId(String prodId) {
		return intfBMO.getSecondAccNbrByProdId(prodId);
	}

	@Override
	public String getOfferOrProdSpecIdByBoId(String boId) {
		return intfBMO.getOfferOrProdSpecIdByBoId(boId);
	}

	@Override
	public String getNewOlIdByOlIdForPayIndentId(String olId) {
		return intfBMO.getNewOlIdByOlIdForPayIndentId(olId);
	}

	@Override
	public String getInterfaceIdBySystemId(String systemId) {
		return intfBMO.getInterfaceIdBySystemId(systemId);
	}

	@Override
	public List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Map<String, Object> param) {
		return intfBMO.queryAttachOfferSpecBySpec(param);
	}

	@Override
	public void insertTableInfoPayInfoListForOrderSubmit(Map<String, Object> payInfoList) {
		intfBMO.insertTableInfoPayInfoListForOrderSubmit(payInfoList);
	}

	@Override
	public int getRenewOfferSpecAttr(Long offerSpecId) {
		return intfBMO.getRenewOfferSpecAttr(offerSpecId);
	}

	@Override
	public String getAccessNumberByProdId(Long prodId) {
		return intfBMO.getAccessNumberByProdId(prodId);
	}

	@Override
	public String getOfferSpecSummary(Long offerSpecId) {
		return intfBMO.getOfferSpecSummary(offerSpecId);
	}

	@Override
	public int checkProductNumByPartyId(Long partyId) {
		return intfBMO.checkProductNumByPartyId(partyId);
	}

	@Override
	public Long getPartyIdByIdentityNum(String identifyValue) {
		return intfBMO.getPartyIdByIdentityNum(identifyValue);
	}

	@Override
	public void updateOrInsertAmaLinkman(List<LinkManInfo> linkmanList) {
		intfBMO.updateOrInsertAmaLinkman(linkmanList);
	}

	@Override
	public List<OfferServItemDto> queryOfferServNotInvalid(Long offerId) {
		return intfBMO.queryOfferServNotInvalid(offerId);
	}

	@Override
	public void updateOrInsertAbm2crmProvince(Map<String, Object> param) {
		intfBMO.updateOrInsertAbm2crmProvince(param);

	}

	/**
	 * 打印回执单
	 * 
	 * @param olId
	 * @param runFlag
	 * @return
	 */
	public String getPageInfo(String olId, String runFlag, String ifAgreementStr) {
		URL url;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line;
		String result = "";
		StringBuffer sb = new StringBuffer("");
		String msgUrl = String.format("%s%s&runFlag=%s&printType=1&ifAgreementStr=%s", voucherPrintUrl, olId, runFlag,
				ifAgreementStr);
		System.out.println("=++++++++++++++++++++++++++++"+msgUrl);
		try {
			long start = System.currentTimeMillis();
			url = new URL(msgUrl);
			is = url.openStream();
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			while (null != (line = br.readLine())) {
				//				result += new String(line.getBytes());
				sb.append(new String(line.getBytes()));
			}
			result = sb.toString();
			sb.setLength(0);
			logger.error("当前线程(id:" + Thread.currentThread().getId() + " name:" + Thread.currentThread().getName()
					+ ")getPageInfo.调用crm回执打印执行时间:" + (System.currentTimeMillis() - start));
		} catch (MalformedURLException e) {
			result = e.getMessage();
		} catch (IOException e) {
			result = e.getMessage();
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}

			} catch (IOException e) {
				result = e.getMessage();
			}
		}
		return result;

	}

	@Override
	public Map<String, Object> queryOfferProdInfoByAccessNumber(String phoneNumber) {
		return intfBMO.queryOfferProdInfoByAccessNumber(phoneNumber);
	}

	@Override
	public Long getProdStatusByProdId(String prodId) {
		return intfBMO.getProdStatusByProdId(prodId);
	}

	public Map<String, Object> getServSpecs(Map<String, Object> params) {
		return intfBMO.getServSpecs(params);
	}

	@Override
	public Map<String, Object> isHaveInOffer(Map<String, Object> req) {
		return intfBMO.isHaveInOffer(req);
	}

	@Override
	public String getOfferSpecNameByOfferSpecId(String offerSpecId) {
		return intfBMO.getOfferSpecNameByOfferSpecId(offerSpecId);
	}

	@Override
	public Map<String, Object> qryPhoneNumberInfoByAccessNumber(String accessNumber) {
		return intfBMO.qryPhoneNumberInfoByAccessNumber(accessNumber);
	}

	@Override
	public Long getPartyIdByAccNbr(String accNbr) {
		return intfBMO.getPartyIdByAccNbr(accNbr);
	}

	@Override
	public Long getProdidByAccNbr(String accNbr) {
		return intfBMO.getProdidByAccNbr(accNbr);
	}

	@Override
	public Long getGxProdItemIdByProdid(Long prodid) {
		return intfBMO.getGxProdItemIdByProdid(prodid);
	}

	@Override
	public Map<String, Object> findOfferProdComp(Map<String, Object> param) {
		return intfBMO.findOfferProdComp(param);
	}

	@Override
	public String checkOrderRouls(Element order) {
		return intfBMO.checkOrderRouls(order);
	}

	@Override
	public void channelInfoGoDown(Map<String, Object> map) {
		intfBMO.channelInfoGoDown(map);
	}

	@Override
	public boolean yesOrNoAliveInOfferSpecRoles(Map<String, Object> map) {
		return intfBMO.yesOrNoAliveInOfferSpecRoles(map);
	}

	@Override
	public List<OfferIntf> queryOfferInstByProdId(Long prodId) {
		return intfBMO.queryOfferInstByProdId(prodId);
	}

	@Override
	public Map<String, Object> queryOfferInfoByProdId(Long prodId, Integer prodSpecId) {
		return intfBMO.queryOfferInfoByProdId(prodId, prodSpecId);
	}

	@Override
	public String queryOfferSpecParamIdByItemSpecId(Map<String, Object> map) {
		return intfBMO.queryOfferSpecParamIdByItemSpecId(map);
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId) {
		return intfBMO.queryAttachOfferByProd(prodId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> queryContinueOrderPPInfoByAccNbr(String accNbr) {
		return intfBMO.queryContinueOrderPPInfoByAccNbr(accNbr);
	}

	@Override
	public Map<String, Object> getAuditingTicketInfoByOlId(String olId) {
		return intfBMO.getAuditingTicketInfoByOlId(olId);
	}

	public List<OfferParam> queryOfferParamByOfferId(Long offerId) {
		return intfBMO.queryOfferParamByOfferId(offerId);
	}

	public Map<String, Object> queryAllChildCompProd(Map<String, Object> param) {
		return intfBMO.queryAllChildCompProd(param);
	}

	@Override
	public String isPKagent(String payIndentId) {
		return intfBMO.isPKagent(payIndentId);
	}

	@Override
	public String getIntfCommonSeq() {
		return intfBMO.getIntfCommonSeq();
	}

	@Override
	public void saveRequestInfo(String id, String plat, String method, String request, Date requestTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("plat", plat);
		map.put("method", method);
		map.put("request", request);
		map.put("requestTime", requestTime);
		intfBMO.saveRequestInfo(map);
	}

	@Override
	public void saveResponseInfo(String id, String plat, String method, String request, Date requestTime,
			String response, Date responseTime, String type, String resultCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("response", response);
		map.put("type", type);
		map.put("resultCode", resultCode);
		map.put("plat", plat);
		map.put("method", method);
		map.put("request", request);
		map.put("requestTime", requestTime);
		map.put("responseTime", responseTime);
		intfBMO.saveResponseInfo(map);
	}

	@Override
	public boolean yesOrNoNeedAddCoupon(Map<String, Object> param) {
		return intfBMO.yesOrNoNeedAddCoupon(param);
	}

	@Override
	public String ifCompProdByProdSpecId(Integer prodSpecId) {
		return intfBMO.ifCompProdByProdSpecId(prodSpecId);
	}

	@Override
	public Integer ifRightPartyGradeByCustTypeAndPartyGrade(Map<String, Object> params) {
		return intfBMO.ifRightPartyGradeByCustTypeAndPartyGrade(params);
	}

	public String syncDate4Prm2Crm(String request) throws Exception {

		String storeName = "";
		String location = "";
		String agentType = "";
		String agentTypeState = "0";
		String parentChannelId = "";// 代理商父级渠道ID
		String partyId = "";
		String channelId = "";
		String seq = String.format("%1$06d", slcBMO.getBankEntityIntfSeq());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String requestTime = format.format(new Date());
		String transactionId = requestTime + "04" + seq;
		logger.debug("渠道信息下发主键ID：{}", transactionId);
		// try {

		Document doc = WSUtil.parseXml(request);
		String mailAddressId = WSUtil.getXmlNodeText(doc, "//request/mailAddressId");
		
		//orgId是sm.party的partyId
		String orgId = WSUtil.getXmlNodeText(doc, "//request/orgId");
		String agentCode = WSUtil.getXmlNodeText(doc, "//request/agentCode");// --渠道ID
		String agentName = WSUtil.getXmlNodeText(doc, "//request/agentName");// --渠道名称
		String linkMobilePhone = WSUtil.getXmlNodeText(doc, "//request/linkMobilePhone");// --联系人移动电话
		String agentAddr = WSUtil.getXmlNodeText(doc, "//request/agentAddr");// --代理商地址
		String parentMailAddressId = WSUtil.getXmlNodeText(doc, "//request/parentMailAddressId");// --父级代理商ID
		// ---
		String aFlag = WSUtil.getXmlNodeText(doc, "//request/aFlag");// --龙厅标志(1：是；0：否)
		String agentFlag = WSUtil.getXmlNodeText(doc, "//request/agentFlag");// --代理商or网点标志
		// (0：代理商;1：网点)
		String opType = WSUtil.getXmlNodeText(doc, "//request/opType");// --数据库操作标志1增2修4删
		String parentAgentFlag = WSUtil.getXmlNodeText(doc, "//request/parentAgentFlag");// --父级渠道到标记(-1：代理商0：网点)
		String orgTypeCd = WSUtil.getXmlNodeText(doc, "//request/orgTypeCd");
		String agentTypeFlag = WSUtil.getXmlNodeText(doc, "//request/agentTypeFlag");// 代理类型(0:固网1:移动)
		String parentOrgId = WSUtil.getXmlNodeText(doc, "//request/parentOrg");// 父级组织ID
		String channelManager = WSUtil.getXmlNodeText(doc, "//request/channelManager");//代理商对应的渠道经理
		String channelSpecId = WSUtil.getXmlNodeText(doc, "//request/channelSpecId");
		String areaId = WSUtil.getXmlNodeText(doc, "//request/areaId");
		String mailAddressStr = agentCode;
		
		if ("1".equals(opType)) {
			//mailAddressId在esb侧不作为主键传过来，sm.party表没有传这个属性，通过partyId判断
			if (intfBMO.checkIsExistsMailAddressId(orgId)) {
			    //if (intfBMO.checkIsExistsMailAddressId(mailAddressId)) {
				return WSUtil.buildResponse(ResultCode.UNSUCCESS, "该代理商信息已经存在,请勿重复添加！");
			}
		}
		//修改的同步信息不处理
//		if ("2".equals(opType)) {
//			return WSUtil.buildResponse(ResultCode.SUCCESS, "不允许修改代理商信息！");
//		}
		if("2".equals(opType)){
			parentOrgId = "";
		}
		//认领资料同步接口。要求根据代理商编码，更改渠道经理
		if ("5".equals(opType)) {
			
			//add by jxx 20180201
			String agentId = WSUtil.getXmlNodeText(doc, "//request/agentId");
			
			// 修改agent_2_prm 信息日后进一步完善
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("opType", opType);
//			param.put("agentCode", agentCode);
			
			// modify by jxx 20180201 change param.put("mailxxxx",mailxxx) to param.put("mailxxx",agentId)
			param.put("mailAddressId", agentId);
			
			param.put("staffCode", channelManager);
			updateOrInsertAgent2prm(param);
			return WSUtil.buildResponse(ResultCode.SUCCESS, "认领资料同步接口成功！");
		}
		// 1.身份核实
		Map<String, Object> pp = new HashMap<String, Object>();
		pp.put("mailAddressId", parentMailAddressId);
		Map<String, Object> paramSm = selectPartyInfoFromSmParty(pp);
		if (paramSm != null) {
			parentOrgId = paramSm.get("PARTYID").toString();
		}
		//agentFlag 0代表 代理商 1代表网点
		if ("1".equals(agentFlag)) {
			storeName = WSUtil.getXmlNodeText(doc, "//request/storeName");
			location = WSUtil.getXmlNodeText(doc, "//request/location");
		} else if ("0".equals(agentFlag)) {
			agentType = WSUtil.getXmlNodeText(doc, "//request/agentType");
			agentTypeState = "3";
		}
		parentChannelId = getChannelId(parentOrgId);// 获取父级渠道ID

		if ("2".equals(opType) || "4".equals(opType)) {
			Map<String, Object> a = new HashMap<String, Object>();
			//mailAddressId在esb侧不作为主键传过来，sm.party表没有传这个属性，通过partyId判断
			a.put("mailAddressId", mailAddressId);//--改造
			//a.put("partyId", orgId);//  渠道掉头专用字段///----一般情况别上线看着点
			
			//a.put("orgId", orgId);
			Map<String, Object> smPartyInfoMap = selectPartyInfoFromSmParty(a);
			// --查出mailAddressId      smPartyInfoMap  1.有 抛提示 2。没有值 根据partyid(orgId)  更新mailAddressId / agentCode 
			if (smPartyInfoMap == null) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "PRM同步CRM网点，组织在SM里找不到异常!");
			}
			partyId = smPartyInfoMap.get("PARTYID").toString(); // partyId = orgId
			if ("1".equals(agentFlag) && "1".equals(agentTypeFlag)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("partyId", partyId);
				Map<String, Object> partyInfoMap = getChannelIdByPartyId(param);
				if (partyInfoMap == null) {
					return WSUtil.buildResponse(ResultCode.RESULT_IS_NULL, "系统没有对应的渠道信息，请确认！");
				}
				channelId = partyInfoMap.get("CHANNELID").toString();
			}
		}
		// 针对网点
		if (parentMailAddressId != null && "0".equals(parentAgentFlag)) {
			Map<String, Object> a = new HashMap<String, Object>();
			a.put("mailAddressId", parentMailAddressId);
			Map<String, Object> smPartyInfoMap = selectPartyInfoFromSmParty(a);
			if (smPartyInfoMap == null) {
				return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "PRM同步CRM网点，上级组织在SM里找不到异常!");
			}
			if ("1".equals(agentTypeFlag)) {
				parentChannelId = getChannelId(smPartyInfoMap.get("PARTYID").toString());
				agentTypeState = "3";
			}
		}
		if (StringUtils.isBlank(parentChannelId)) {
			parentChannelId = "";
		}
		//add by wanghongli 20130322 移动代理商直销的情况下取 parentOrg的值 agentFlag 0 代理商 agentTypeFlag 1 移动
		if ("0".equals(agentFlag) && "1".equals(agentTypeFlag)) {
			agentType = WSUtil.getXmlNodeText(doc, "//request/agentType");
			if ("3".equals(agentType)) { //3 代表直销
				parentChannelId = getChannelId(WSUtil.getXmlNodeText(doc, "//request/parentOrg"));
				parentOrgId = WSUtil.getXmlNodeText(doc, "//request/parentOrg");
			}
		}

		// @see
		// bss.systemmanager.provide.SmService#operationOrg(java.lang.String)
		//
		// [{action:'动作:1添加2修改3删除',sysId:1营业厅2CRM3代理商等
		//
		// org:{id:'新增时可空',name:'机构名称',areaId:'归属区域ID',parentId:'父组织ID',typeCd:'类型',mailAddressId:'通信地址编码',
		// address:'地址描述',linknbr:'联系电话'}
		// 返回
		// {code:'0成功,其他异常',msg:'提示信息',info:'系统信息',id:''}

		JSONArray orgArrayList = new JSONArray();
		JSONObject orgJson = new JSONObject();
		
		if ("4".equals(opType)) {
			orgJson.element("action", "3");
		} else {
			orgJson.element("action", opType);
		}
		if (agentFlag.equals("0")) {
			orgJson.element("sysId", 3);
		} else if (agentFlag.equals("1")) {
			orgJson.element("sysId", 1);
		} else {
			return WSUtil.buildResponse(ResultCode.REQUEST_PARAME_IS_ERROR, "agentFlag类型无效！");
		}
		
		String parentOrgIdmsg =intfBMO.getParentOrgId(mailAddressId);
		
		if(!parentOrgIdmsg.equals("")){
			parentOrgId = parentOrgIdmsg;
		}
		
		JSONObject info = new JSONObject();
		info.element("id", partyId);
		info.element("name", agentName);
		info.element("areaId", 11000);
		info.element("parentId", parentOrgId);
		info.element("typeCd", orgTypeCd);
		info.element("mailAddressId", mailAddressId);
		info.element("address", agentAddr);
		info.element("linknbr", linkMobilePhone);
		//"2".equals(opType) 2的时候增加节点
		//info.element("mailAddress", mailAddressStr);
		if("2".equals(opType)){
			info.element("mailAddress", mailAddressStr);
		}
		orgJson.element("org", info);
		orgArrayList.add(orgJson);

		String orgOpTypeResult = smService.operationOrg(orgArrayList.toString());
		JSONObject jsonOrg = JSONObject.fromObject(orgOpTypeResult);
		if (!"0".equals(jsonOrg.getString("code"))) {
			return WSUtil.buildResponse(jsonOrg.getString("code"), jsonOrg.getString("info"));
		}
		// 代理商联系人信息操作
		if ("0".equals(agentFlag)) {
			List<org.dom4j.Node> linkManList = WSUtil.getXmlNodeList(doc, "//request/linkManList/linkMan");
			List<LinkManInfo> LinkManInfoList = new ArrayList<LinkManInfo>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for (int i = 0; linkManList.size() > i; i++) {
				LinkManInfo linkman = new LinkManInfo();
				linkman.setAgentKind(Integer.valueOf(WSUtil.getXmlNodeText(linkManList.get(i), "./agentKind")));
				String businessType = WSUtil.getXmlNodeText(linkManList.get(i), "./buniessType");
				if (StringUtils.isNotBlank(businessType)) {
					linkman.setBuniessType(Integer.valueOf(businessType));
				}
				linkman.setCardNum(WSUtil.getXmlNodeText(linkManList.get(i), "./cardNum"));
				linkman.setCardType(WSUtil.getXmlNodeText(linkManList.get(i), "./cardType"));
				linkman.setLinkAddress(WSUtil.getXmlNodeText(linkManList.get(i), "./linkAddress"));
				String linkBirthday = WSUtil.getXmlNodeText(linkManList.get(i), "./linkBirthday");
				if (StringUtils.isNotBlank(linkBirthday)) {
					linkman.setLinkBirthday(sdf.parse(linkBirthday));
				}
				linkman.setLinkEmail(WSUtil.getXmlNodeText(linkManList.get(i), "./linkEmail"));
				linkman.setLinkManName(WSUtil.getXmlNodeText(linkManList.get(i), "./linkManName"));
				linkman.setLinkPhone(WSUtil.getXmlNodeText(linkManList.get(i), "./linkPhone"));
				linkman.setLinkPost(WSUtil.getXmlNodeText(linkManList.get(i), "./linkPost"));
				linkman.setLinkSex(WSUtil.getXmlNodeText(linkManList.get(i), "./linkSex"));
				linkman.setOfficialPhone(WSUtil.getXmlNodeText(linkManList.get(i), "./officialPhone"));
				linkman.setState(WSUtil.getXmlNodeText(linkManList.get(i), "./state"));
				linkman.setSupType(WSUtil.getXmlNodeText(linkManList.get(i), "./supType"));
				linkman.setTelecomOfficial(WSUtil.getXmlNodeText(linkManList.get(i), "./telecomOfficial"));
				linkman.setAgentId(Integer.valueOf(mailAddressId));
				linkman.setOpType(opType);
				LinkManInfoList.add(linkman);
			}
			if (LinkManInfoList.size() > 0) {
				updateOrInsertAmaLinkman(LinkManInfoList);
			}
		}
		// 2.数据操作ADD动作
		if ("1".equals(opType)) {
			partyId = jsonOrg.getString("id");
			// 系管已经同步crm_app.party
		} else if ("2".equals(opType)) {
			if ("1".equals(agentTypeFlag) && "0".equals(aFlag)) {
				// --代理商更新渠道
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", opType);
				param.put("agentName", agentName);
				param.put("storeName", agentName);
				param.put("partyId", partyId);
				updateOrInsertChannelForCrm(param);
				param.put("partyId", partyId);
				param.put("channelSpecId", channelSpecId);
				param.put("id", transactionId);
				param.put("channelId", channelId);
				param.put("parentChannelId", parentChannelId);
				param.put("channelSpecName", "代理商");
				channelInfoGoDown(param);
			}
		} else if ("4".equals(opType)) {
			if ("1".equals(agentTypeFlag) && "0".equals(aFlag)) {
				// 代理商更新渠道
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("opType", opType);
				param.put("partyId", partyId);
				updateOrInsertChannelForCrm(param);
				param.put("partyId", partyId);
				param.put("channelSpecId", channelSpecId);
				param.put("id", transactionId);
				param.put("channelId", channelId);
				param.put("parentChannelId", parentChannelId);
				param.put("agentName", "(PRM删除)" + agentName);
				param.put("channelSpecName", "代理商");
				channelInfoGoDown(param);
			}
		}
		// 3.针对是否龙厅处理
		if ("1".equals(aFlag)) {
			if ("0".equals(agentFlag)) {
				// 获取各节点值
				if ("1".equals(opType)) {
					channelId = String.valueOf(getChannelIdSeq());
					Map<String, Object> channelMap = new HashMap<String, Object>();
					channelMap.put("channelSpecId", channelSpecId);
					channelMap.put("channelIdSeq", channelId);
					channelMap.put("agentName", agentName);
					channelMap.put("storeName", agentName);
					channelMap.put("partyId", partyId);
					channelMap.put("parentChannelId", parentChannelId);
					channelMap.put("opType", opType);
					updateOrInsertChannelForCrm(channelMap);
					channelMap.put("id", transactionId);
					channelMap.put("channelId", channelId);
					channelMap.put("parentChannelId", parentChannelId);
					channelMap.put("channelSpecName", "代理商");
					channelInfoGoDown(channelMap);
				} else if ("2".equals(opType)) {
					Map<String, Object> channelMap = new HashMap<String, Object>();
					channelMap.put("opType", opType);
					channelMap.put("agentName", agentName);
					channelMap.put("storeName", agentName);
					channelMap.put("partyId", partyId);
					updateOrInsertChannelForCrm(channelMap);
					channelMap.put("channelSpecId", channelSpecId);
					channelMap.put("id", transactionId);
					channelMap.put("channelId", channelId);
					channelMap.put("parentChannelId", parentChannelId);
					channelMap.put("channelSpecName", "代理商");
					channelInfoGoDown(channelMap);
				} else if ("4".equals(opType)) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("opType", opType);
					param.put("partyId", partyId);
					updateOrInsertChannelForCrm(param);
					param.put("channelSpecId", channelSpecId);
					param.put("id", transactionId);
					param.put("parentChannelId", parentChannelId);
					param.put("channelId", channelId);
					param.put("agentName", "(PRM删除)" + agentName);
					param.put("channelSpecName", "代理商");
					channelInfoGoDown(param);
				}
			} else if ("1".equals(agentFlag)) {
				if ("1".equals(opType)) {
					// 网点信息增加渠道信息
					channelId = String.valueOf(getChannelIdSeq());
					Map<String, Object> channelMap = new HashMap<String, Object>();
					channelMap.put("opType", opType);
					channelMap.put("channelSpecId", channelSpecId);
					channelMap.put("channelIdSeq", channelId);
					channelMap.put("agentName", agentName);
					channelMap.put("storeName", storeName);
					channelMap.put("partyId", partyId);
					channelMap.put("parentChannelId", parentChannelId);
					updateOrInsertChannelForCrm(channelMap);
					channelMap.put("parentChannelId", parentChannelId);
					channelMap.put("id", transactionId);
					channelMap.put("channelId", channelId);
					channelMap.put("channelSpecName", "专营厅");
					channelInfoGoDown(channelMap);
				} else if ("2".equals(opType)) {
					// 网点更新渠道名称
					Map<String, Object> channelMap = new HashMap<String, Object>();
					channelMap.put("opType", opType + "wd");
					channelMap.put("storeName", storeName);
					channelMap.put("channelId", channelId);
					//更新channelMap.put("parentChannelId", parentChannelId);
					
					updateOrInsertChannelForCrm(channelMap);
					channelMap.put("partyId", partyId);
					channelMap.put("channelSpecId", channelSpecId);
					channelMap.put("id", transactionId);
					channelMap.put("parentChannelId", parentChannelId);
					channelMap.put("agentName", agentName);
					channelMap.put("channelSpecName", "营业厅");
					channelInfoGoDown(channelMap);
				} else if ("4".equals(opType)) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("opType", opType + "wd");
					param.put("channelId", channelId);
					updateOrInsertChannelForCrm(param);
					param.put("partyId", partyId);
					param.put("channelSpecId", channelSpecId);
					param.put("id", transactionId);
					param.put("parentChannelId", parentChannelId);
					param.put("agentName", "(PRM删除)" + agentName);
					param.put("channelSpecName", "营业厅");
					channelInfoGoDown(param);
				}
			}
			// 同步 cep_channel表
			Map<String, Object> cepChannelInfo = new HashMap<String, Object>();
			cepChannelInfo.put("opType", opType);
			cepChannelInfo.put("channelId", channelId);
			// 1代理商 2 网厅
			if ("0".equals(agentFlag)) {
				cepChannelInfo.put("channelType", "1");
			} else {
				cepChannelInfo.put("channelType", "2");
			}
			cepChannelInfo.put("agentCode", agentCode);
			updateOrInsertCepChannelFromPrmToCrm(cepChannelInfo);
		} else if ("0".equals(aFlag)) {
			if ("1".equals(agentTypeFlag) && "1".equals(opType)) {
				// 非龙厅且移动合作厅且新增时同步channnel表
				long channelIdSeq = getChannelIdSeq();
				channelId = channelIdSeq + "";
				Map<String, Object> channelMap = new HashMap<String, Object>();
				channelMap.put("channelSpecId", channelSpecId);
				channelMap.put("opType", opType);
				channelMap.put("channelIdSeq", channelIdSeq);
				if (agentFlag.equals("0")) {
					channelMap.put("agentName", agentName);
				} else if (agentFlag.equals("1")) {
					channelMap.put("agentName", storeName);
				} else {
					channelMap.put("agentName", null);
				}
				channelMap.put("partyId", partyId);
				if ("3".equals(agentTypeState)) {
					channelMap.put("parentChannelId", parentChannelId);
				} else {
					channelMap.put("parentChannelId", "11040032");
				}
				channelMap.put("storeName", storeName);
				updateOrInsertChannelForCrm(channelMap);
				channelMap.put("id", transactionId);
				channelMap.put("channelId", channelIdSeq);
				channelMap.put("agentName", agentName);
				channelMap.put("channelSpecName", "营业厅");
				channelInfoGoDown(channelMap);

			}
		}
		// 4.移动网点处理
		if ("1".equals(agentTypeFlag) && "1".equals(agentFlag)) {
			Document noLongTing = WSUtil.parseXml(request);
			String direction = WSUtil.getXmlNodeText(noLongTing, "//request/direction");
			String district = WSUtil.getXmlNodeText(noLongTing, "//request/district");
			String openDate = WSUtil.getXmlNodeText(noLongTing, "//request/openDate");
			String storeLevel = WSUtil.getXmlNodeText(noLongTing, "//request/storeLevel");
			String pointType = WSUtil.getXmlNodeText(noLongTing, "//request/pointType");
			String managerId = WSUtil.getXmlNodeText(noLongTing, "//request/managerId");
			String longitude = WSUtil.getXmlNodeText(noLongTing, "//request/longitude");
			String latitude = WSUtil.getXmlNodeText(noLongTing, "//request/latitude");
			String storePhone = WSUtil.getXmlNodeText(noLongTing, "//request/storePhone");
			if ("1".equals(opType)) {
				Map<String, Object> insertGisPartyInfo = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(parentMailAddressId)) {
					Map<String, Object> a = new HashMap<String, Object>();
					a.put("mailAddressId", parentMailAddressId);
					Map<String, Object> smPartyInfoMap = selectPartyInfoFromSmParty(a);
					if (smPartyInfoMap.size() > 0) {
						insertGisPartyInfo.put("agentName", smPartyInfoMap.get("NAME").toString());
					}
				}
				insertGisPartyInfo.put("opType", opType);
				insertGisPartyInfo.put("partyId", partyId);
				insertGisPartyInfo.put("storeName", storeName);
				insertGisPartyInfo.put("addressStr", location);
				insertGisPartyInfo.put("district", district);
				insertGisPartyInfo.put("openDate", openDate);
				insertGisPartyInfo.put("storeLevel", storeLevel);
				insertGisPartyInfo.put("storePhone", storePhone);
				insertGisPartyInfo.put("pointType", pointType);
				insertGisPartyInfo.put("direction", direction);
				insertGisPartyInfo.put("managerId", managerId);
				if (longitude.length() > 10) {
					longitude = longitude.substring(0, 10);
				}
				if (latitude.length() > 10) {
					latitude = latitude.substring(0, 10);
				}
				insertGisPartyInfo.put("longitude", longitude);
				insertGisPartyInfo.put("latitude", latitude);
				updateOrInsertGisParty(insertGisPartyInfo);
			}
			if ("2".equals(opType)) {
				/**
				 * insertGisPartyInfo.put("opType", opType);
				insertGisPartyInfo.put("partyId", partyId);
				insertGisPartyInfo.put("storeName", storeName);
				insertGisPartyInfo.put("addressStr", location);
				insertGisPartyInfo.put("district", district);
				insertGisPartyInfo.put("openDate", openDate);
				insertGisPartyInfo.put("storeLevel", storeLevel);
				insertGisPartyInfo.put("storePhone", storePhone);
				insertGisPartyInfo.put("pointType", pointType);
				insertGisPartyInfo.put("direction", direction);
				insertGisPartyInfo.put("managerId", managerId);
				将这些属性都要更新
				 */
				Map<String, Object> updateGisPartyInfo = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(parentMailAddressId)) {
					Map<String, Object> a = new HashMap<String, Object>();
					a.put("mailAddressId", parentMailAddressId);
					Map<String, Object> smPartyInfoMap = selectPartyInfoFromSmParty(a);
					if (smPartyInfoMap.size() > 0) {
						updateGisPartyInfo.put("agentName", smPartyInfoMap.get("NAME").toString());
					}
				}
				//modify by wanghongli 20130329 防止长度不够报错
				if (longitude.length() > 10) {
					longitude = longitude.substring(0, 10);
				}
				if (latitude.length() > 10) {
					latitude = latitude.substring(0, 10);
				}
				updateGisPartyInfo.put("opType", opType);
				updateGisPartyInfo.put("partyId", partyId);
				updateGisPartyInfo.put("storeName", storeName);
				updateGisPartyInfo.put("addressStr", location);
				updateGisPartyInfo.put("district", district);
				updateGisPartyInfo.put("openDate", openDate);
				updateGisPartyInfo.put("storeLevel", storeLevel);
				updateGisPartyInfo.put("storePhone", storePhone);
				updateGisPartyInfo.put("pointType", pointType);
				updateGisPartyInfo.put("direction", direction);
				updateGisPartyInfo.put("managerId", managerId);
				//updateGisPartyInfo.put("longitude", longitude.substring(0, 10));
				//updateGisPartyInfo.put("latitude", latitude.substring(0, 10));
				updateGisPartyInfo.put("longitude", longitude);
				updateGisPartyInfo.put("latitude", latitude);
				updateOrInsertGisParty(updateGisPartyInfo);
			}
			if ("4".equals(opType)) {
				Map<String, Object> deleteGisPartyInfo = new HashMap<String, Object>();
				deleteGisPartyInfo.put("opType", opType);
				deleteGisPartyInfo.put("partyId", partyId);
				deleteGisPartyInfo.put("storeName", storeName);
				updateOrInsertGisParty(deleteGisPartyInfo);
			}
		}
		// 同步agent_2_prm 信息日后进一步完善
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("opType", opType);
		param.put("agentId", mailAddressId);
		param.put("agentName", agentName);
		//param.put("agentCode", agentCode);
		param.put("agentType", agentType);
		param.put("addressId", agentAddr);
		param.put("agentAddr", agentAddr);
		param.put("staffCode", channelManager);
		// 1代理商 2 网厅
		if ("0".equals(agentFlag)) {
			param.put("channelType", "1");
		} else {
			param.put("channelType", "2");
		}
		updateOrInsertAgent2prm(param);

		//同步渠道视图
		//如果是代理商
	/*	String dlsResult = "";
		if ("0".equals(agentFlag)) {
			Map<String, Object> insertDls = new HashMap<String, Object>();
			if("".equals(channelId)&&"2".equals(opType)){
				channelId = String.valueOf(getChannelIdSeq());
			}
			insertDls.put("i_channel_id", new Integer(channelId));
			insertDls.put("i_manage_code", partyId);
			insertDls.put("i_area_id", new Integer(areaId));
			insertDls.put("i_channel_spec_id", new Integer(channelSpecId));
			insertDls.put("i_channel_name", agentName);
			insertDls.put("i_agentaddr", agentAddr);
			insertDls.put("i_linkphone", "");
			insertDls.put("i_linkmanname", "");
			insertDls.put("i_linkemail", "");
			insertDls.put("i_parentorg", parentOrgId);
			insertDls.put("i_staff_id", new Integer("-10020"));
			insertDls.put("i_parentmailaddressid", new Integer(parentMailAddressId));
			insertDls.put("opType", new Integer(opType));
			insertDls.put("o_flag", "1");
			insertDls.put("o_msg", "1");
			dlsResult = intfBMO.insertPrm2CmsDls(insertDls);
		} else if ("1".equals(agentFlag)) {
			Map<String, Object> insertWt = new HashMap<String, Object>();
			if("".equals(channelId)&&"2".equals(opType)){
				channelId = String.valueOf(getChannelIdSeq());
			}
			insertWt.put("i_channel_id", new Integer(channelId));
			insertWt.put("i_manage_code", partyId);
			insertWt.put("i_area_id", new Integer(areaId));
			insertWt.put("i_channel_spec_id", new Integer(channelSpecId));
			insertWt.put("i_channel_name", agentName);
			insertWt.put("i_parentorg", new Long(parentOrgId));
			insertWt.put("i_staff_id", new Integer("-10020"));
			insertWt.put("opType", new Integer(opType));
			insertWt.put("o_flag", "1");
			insertWt.put("o_msg", "1");
			dlsResult = intfBMO.insertPrm2CmsWt(insertWt);
		}
		if (!dlsResult.startsWith("0")) {
			return WSUtil.buildValidateResponse(ResultCode.UNSUCCESS, "-1", "同步渠道视图失败：" + dlsResult);
		}*/
		partyId="<partyId>"+partyId+"</partyId>"+"<channel_id>"+channelId+"</channel_id>";
		return WSUtil.buildResponse(ResultCode.SUCCESS, "成功", partyId);

	}

	public String queryOlIdByOlNbr(String olNbr) {
		return intfBMO.queryOlIdByOlNbr(olNbr);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getPartyNameByPartyId(Long partyId) {
		return intfBMO.getPartyNameByPartyId(partyId);
	}

	@Override
	public String getPartyIdByTerminalCode(String terminalCode) {
		return intfBMO.getPartyIdByTerminalCode(terminalCode);
	}

	@Override
	public List<Map<String, Object>> getOfferListByProdId(String prodId) {
		return intfBMO.getOfferListByProdId(prodId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Long getPartyIdByIdentifyNum(String accNbr) {
		return intfBMO.getPartyIdByIdentifyNum(accNbr);
	}

	@Override
	public Long getPartyIdByProdId(Long prodId) {
		return intfBMO.getPartyIdByProdId(prodId);
	}

	@Override
	public Map consoleUimK(String devNumId, String flag) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<root>");
		xml.append("<AccessNumberInfo>");
		xml.append(String.format("<anTypeCd>%s</anTypeCd>", "509"));// 接入号码类型编码（不可为空）
		xml.append(String.format("<rscStatusCd>%s</rscStatusCd>", flag));// 状态编码，若是正式预占接入号码接口需要传入参数(目标状态　0:临时预占,1:正式预占)
		xml.append(String.format("<anId>%s</anId>", devNumId));
		xml.append("</AccessNumberInfo>");
		xml.append("</root>");
		logger.debug("资源接口入参：{}", xml.toString());
		List result = new ArrayList();
		Map resultmap = new HashMap();
		// 预占
		if ("0".equals(flag)) {
			try {
				result = rscServiceSMO.allocAn(xml.toString());

			} catch (Exception e) {
				resultmap.put("result", "-1");
				resultmap.put("cause", e.getMessage());
				return resultmap;
			}
		} else if ("14".equals(flag)) {
			try {
				result = rscServiceSMO.releaseAn(xml.toString());
			} catch (Exception e) {
				resultmap.put("result", "-1");
				resultmap.put("cause", e.getMessage());
				return resultmap;
			}
		}
		resultmap = (Map) result.get(0);
		/*if ((a.get("result").toString()).equals("1")) {
			return true;
		} else {
			return false;
		}*/
		return resultmap;
	}

	@Override
	public boolean checkRelaSub(Map<String, String> param) {
		return intfBMO.checkRelaSub(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String changeRequestByCheckRelaSub(Element order) throws Exception {
		try {
			List offerIdList = null;
			Map<String, String> param = new HashMap<String, String>();
			Element offerSpecs = (Element) order.selectSingleNode("./offerSpecs");
			String prodId = WSUtil.getXmlNodeText(order, "./prodId");
			String accessNumber = WSUtil.getXmlNodeText(order, "./accessNumber");
			String systemId = WSUtil.getXmlNodeText(order, "./systemId");
			// 根据产品ID查找旗下所有关联的销售品ID
			if (StringUtils.isNotBlank(prodId)) {
				// 客服如果订购国际漫游||国际长途需要判断客户是否可以订购
				if ("6090010042".equals(systemId)) {
					if (offerSpecs != null) {
						List<Element> offerSpec = offerSpecs.selectNodes("./offerSpec");
						for (int i = 0; offerSpec.size() > i; i++) {
							String newOfferSpecId = offerSpec.get(i).selectSingleNode("./id").getText();
							String newActionType = offerSpec.get(i).selectSingleNode("./actionType").getText();
							String newName = WSUtil.getXmlNodeText(offerSpec.get(i), "./name");
							if (("992018226".equals(newOfferSpecId) || "992018127".equals(newOfferSpecId))
									&& "0".equals(newActionType)) {
								Map<String, Object> pp = new HashMap<String, Object>();
								pp.put("v_prodId", prodId);
								String v_fee = soQuerySMO.queryUserAccount(accessNumber);
								pp.put("v_fee", v_fee);
								pp.put("v_code", "1");
								gjmyYesOrNoRule(pp);
								if ("0".equals(pp.get("v_code").toString())) {
									return newOfferSpecId + newName;
								}

							}
						}

					}
				}
				offerIdList = intfBMO.queryOfferSpecIdByProdId(prodId);
			}
			if (offerSpecs != null && offerIdList != null) {
				List<Element> offerSpec = offerSpecs.selectNodes("./offerSpec");
				ArrayList<String> cancelSpecId = new ArrayList<String>();
				for (int i = 0; offerSpec.size() > i; i++) {
					String newOfferSpecId = offerSpec.get(i).selectSingleNode("./id").getText();
					String newActionTypeCd = offerSpec.get(i).selectSingleNode("./actionType").getText();
					if ("1".equals(newActionTypeCd)) {
						cancelSpecId.add(newOfferSpecId);
					}
				}
				for (int i = 0; offerSpec.size() > i; i++) {
					String newOfferSpecId = offerSpec.get(i).selectSingleNode("./id").getText();
					String newActionTypeCd = offerSpec.get(i).selectSingleNode("./actionType").getText();
					String newStartFashion = WSUtil.getXmlNodeText(offerSpec.get(i), "./startFashion");

					if ("0".equals(newActionTypeCd) && !"6090010042".equals(systemId)) {
						// 循环进行已有的销售品和新订购销售品进行比对  客服自己判断互斥 不进入此方法
						for (int j = 0; offerIdList.size() > j; j++) {
							// modify by wanghongli 新订购的销售品已经存在不进入互斥判断
							if (!newOfferSpecId.equals(offerIdList.get(j).toString())
									&& !cancelSpecId.contains(offerIdList.get(j).toString())) {
								param.put("newOfferSpecId", newOfferSpecId);
								param.put("oldOfferSpecId", offerIdList.get(j).toString());
								if (checkRelaSub(param)) {
									// 服务可以立即订购 资费不能立即生效
									String serviceId = findOfferOrService(Long.valueOf(newOfferSpecId));
									if ("0".equals(newStartFashion) && StringUtils.isBlank(serviceId)) {
										return "-1";
									}
									// 如果互斥 将已有互斥产品退订
									Element oldOfferSpecElement = (Element) offerSpec.get(i).clone();
									oldOfferSpecElement.selectSingleNode("./id").setText(offerIdList.get(j).toString());
									oldOfferSpecElement.selectSingleNode("./actionType").setText("1");
									if (oldOfferSpecElement.selectSingleNode("./endFashion") != null) {
										oldOfferSpecElement.selectSingleNode("endFashion").setText(newStartFashion);
									} else {
										oldOfferSpecElement.addElement("endFashion").setText(newStartFashion);
									}
									Element oldProperties = (Element) oldOfferSpecElement
											.selectSingleNode("./properties");
									if (oldProperties != null) {
										oldOfferSpecElement.remove(oldProperties);
									}
									Element oldEndDt = (Element) oldOfferSpecElement.selectSingleNode("./endDt");
									if (oldEndDt != null) {
										oldOfferSpecElement.remove(oldEndDt);
									}
									offerSpecs.add(oldOfferSpecElement);
								}
							}
						}
					}
				}
			}
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "-2";
		}
	}

	@Override
	public String checkContinueOrderPPInfo(String accNbr, String offerSpecId, String actionType) {
		return intfBMO.checkContinueOrderPPInfo(accNbr, offerSpecId, actionType);
	}

	@Override
	public List<OfferSpecDto> queryOfferSpecsByDZQD() {
		return intfBMO.queryOfferSpecsByDZQD();
	}

	@Override
	public boolean queryProdSpec2BoActionTypeCdBYprodAndAction(Map<String, Object> param) {
		return intfBMO.queryProdSpec2BoActionTypeCdBYprodAndAction(param);
	}

	@Override
	public int queryIfWLANByOfferSpecId(Long offerSpecId) {
		return intfBMO.queryIfWLANByOfferSpecId(offerSpecId);
	}

	@Override
	public Map<String, Object> selectStaffInofFromPadPwdStaff(String staffNumber) {
		return intfBMO.selectStaffInofFromPadPwdStaff(staffNumber);
	}

	@Override
	public void updatePhoneNumberStatusCdByYuyue(String phoneNumber) {
		intfBMO.updatePhoneNumberStatusCdByYuyue(phoneNumber);

	}

	@Override
	public String gjmyYesOrNoRule(Map<String, Object> param) {
		return intfBMO.gjmyYesOrNoRule(param);

	}

	@Override
	public Map<String, Object> queryAccountInfo(String prodId) {
		return intfBMO.queryAccountInfo(prodId);
	}

	public Map<String, String> qryAccount(String accessNumber) {
		Map<String, String> param = new HashMap<String, String>();
		// 调用接口固定参数
		long end = System.currentTimeMillis();
		String requestId = DateUtil.longToDateString(end, "yyyyMMddHHmmssS");
		String requestTime = DateUtil.longToDateString(end, "yyyyMMddHHmmss");
		String objType = "1";
		String obj = accessNumber;
		String queryFlag = "1";
		String detailFlag = "0";
		String interfaceId = "CRM";
		long start = System.currentTimeMillis();
		param = billService.accountQuery(requestId, requestTime, objType, obj, queryFlag, detailFlag, interfaceId);
		logger.error("billService.accountQuery 执行时间:" + (System.currentTimeMillis() - start));
		return param;
	}

	@Override
	public String getPostCodeByPartyId(Long partyId) {
		return intfBMO.getPostCodeByPartyId(partyId);
	}

	public String qryDeviceNumberStatusCd(String anId) {
		return intfBMO.qryDeviceNumberStatusCd(anId);
	}

	@Override
	public Map<String, Object> queryAuditingTicketBusiInfo(Map<String, Object> param) {
		return intfBMO.queryAuditingTicketBusiInfo(param);
	}

	@Override
	public String queryOlIdByBoId(Map<String, Object> param) {
		return intfBMO.queryOlIdByBoId(param);
	}

	@Override
	public String queryCycleByPayId(String payIndentId) {
		return intfBMO.queryCycleByPayId(payIndentId);
	}

	@Override
	public String getChargeByTicketCd(String auditTicketCd) {
		return intfBMO.getChargeByTicketCd(auditTicketCd);
	}

	@Override
	public boolean qryAccessNumberIsOk(String accessNumber) {
		return intfBMO.qryAccessNumberIsOk(accessNumber);
	}

	@Override
	public Map queryCodeByNum(String num) throws Exception {
		return rscServiceSMO.queryCodeByNum(num);
	}

	public Map<String, Object> queryDefaultValueByMainOfferSpecId(String offerSpecId) {
		return intfBMO.queryDefaultValueByMainOfferSpecId(offerSpecId);
	}

	@Override
	public String getPayIndentIdByOlId(String olId) {
		return intfBMO.getPayIndentIdByOlId(olId);
	}

	/**
	 * 作废购物车和业务动作
	 * 
	 * @param olId
	 */
	public void cancelOrderInfo(long olId) {
		// 作废购物车
		OrderList orderList = new OrderList();
		orderList.setOlId(olId);
		orderList.setStatusCd(CommonDomain.ORDER_STATUS_PREPARE_REMOVE);
		orderList.setStatusDt(new Date());
		soSaveBMO.updateOrderListByPrimaryKeyDynamic(orderList);
		// 作废业务动作
		soSaveBMO.updateBusiOrderStatusCdByOrderListId(CommonDomain.ORDER_STATUS_DELETED, olId);
		logger.debug("释放购物车成功");
	}

	@Override
	public Map<String, Object> indentItemSync(IndentItemSync indentItemSync) {
		return unityPayClient.indentItemSync(indentItemSync);
	}

	@Override
	public Map<String, Object> indentItemPay(IndentItemPay indentItemPay) {
		return unityPayClient.indentItemPay(indentItemPay);
	}

	@Override
	public String getProdStatusNameByCd(Long prodStatusCd) {
		return intfBMO.getProdStatusNameByCd(prodStatusCd);
	}

	@Override
	public String getChannelIdByTicketCd(String ticketCd) {
		return intfBMO.getChannelIdByTicketCd(ticketCd);
	}

	@Override
	public void saveTicketCd2terminal(Map<String, Object> map) {
		intfBMO.saveTicketCd2terminal(map);
	}

	@Override
	public String getTicketIdByCd(String autitTicketCd) {
		return intfBMO.getTicketIdByCd(autitTicketCd);
	}

	/**
	 * 统计库存数量
	 */
	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<InventoryStatisticsEntity> getInventoryStatistics(InventoryStatisticsEntityInputBean parameterObject) {
		return intfBMO.getInventoryStatistics(parameterObject);
	}

	@Override
	public List<Map<String, Object>> getUnCheckedCardInfo() {
		return intfBMO.getUnCheckedCardInfo();
	}

	@Override
	public void updaCradCheckResult(Map<String, Object> map) {
		intfBMO.updaCradCheckResult(map);
	}

	@Override
	public void updaCradCheckStatus(Map<String, Object> map) {
		intfBMO.updaCradCheckStatus(map);
	}

	@Override
	public String ifPayIndentIdExists(Map<String, Object> map) {
		return intfBMO.ifPayIndentIdExists(map);
	}

	@Override
	public boolean checkUIMChannelId(Map<String, Object> map) {
		return intfBMO.checkUIMChannelId(map);
	}

	@Override
	public boolean checkGoupUIM(Map<String, Object> map) {
		return intfBMO.checkGoupUIM(map);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<BcdCodeEntity> getBcdCode(BcdCodeEntityInputBean parameterObject) {
		return intfBMO.getBcdCode(parameterObject);
	}

	@Override
	public String getOfferProdCompMainProd(Long compProdId) {
		return intfBMO.getOfferProdCompMainProd(compProdId);
	}

	@Override
	public void deleteCrmRequest(String id) {
		intfBMO.deleteCrmRequest(id);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getChargeByPayId(String payIndentId) {
		return intfBMO.getChargeByPayId(payIndentId);
	}

	@Override
	public boolean checkUIMStore(Map<String, Object> map) {
		return intfBMO.checkUIMStore(map);
	}

	@Override
	public void saveSRInOut(Map<String, Object> map) {
		intfBMO.saveSRInOut(map);
	}

	@Override
	public String getOfferTypeCdByOfferSpecId(String offerSpecId) {
		return intfBMO.getOfferTypeCdByOfferSpecId(offerSpecId);
	}

	@Override
	public String getFaceValueByTicketCd(String auditTicketCd) {
		return intfBMO.getFaceValueByTicketCd(auditTicketCd);
	}

	@Override
	public List<BankTableEntity> getBankTable(String bankCode) {
		return intfBMO.getBankTable(bankCode);
	}

	@Override
	public void insertBankFreeze(String sql) {
		intfBMO.insertBankFreeze(sql);
	}

	@Override
	public boolean checkBankFreeze(String sql) {
		return intfBMO.checkBankFreeze(sql);
	}

	@Override
	public boolean updateBankFreeze(String sql) {
		return intfBMO.updateBankFreeze(sql);
	}

	@Override
	public List queryChargeInfoBySpec(String offerSpecId) {
		return intfBMO.queryChargeInfoBySpec(offerSpecId);
	}

	@Override
	public String getRequestInfo(String olId) {
		return intfBMO.getRequestInfo(olId);
	}

	@Override
	public void saveRequestTime(String logId, String methodName, String accessNumber, String identityNum,
			Date requestTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", logId);
		map.put("method_name", methodName);
		map.put("accessNumber", accessNumber);
		map.put("identityNum", identityNum);
		map.put("requestTime", requestTime);
		intfBMO.saveRequestTime(map);

	}

	@Override
	public void updateRequestTime(String logId, Date time1, Date time2, Date time3, Date time4) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", logId);
		map.put("time1", time1);
		map.put("time2", time2);
		map.put("time3", time3);
		map.put("time4", time4);
		intfBMO.updateRequestTime(map);
	}

	@Override
	public Long getOfferItemByAccNum(String accessNumber) {
		return intfBMO.getOfferItemByAccNum(accessNumber);
	}

	@Override
	public String getFlag(String keyflag) {
		return intfBMO.getFlag(keyflag);
	}

	@Override
	public String getIntfTimeCommonSeq() {
		return intfBMO.getIntfTimeCommonSeq();
	}

	@Override
	public String getPartyIdByCard(Integer certType, String cerdValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("certType", certType);
		map.put("certValue", cerdValue);
		return intfBMO.getPartyIdByCard(map);
	}

	/**
	 * 根据prodid查询是否停机服务
	 */
	@Override
	public List<Map<String, Object>> getAserByProd(Long prodidByPartyId) {
		return intfBMO.getAserByProd(prodidByPartyId);
	}

	@Override
	public String getOfferSpecidByProdId(Long prodidByPartyId) {
		return intfBMO.getOfferSpecidByProdId(prodidByPartyId);
	}

	@Override
	public String getSpecNameByProdId(Long prodId) {
		return intfBMO.getSpecNameByProdId(prodId);
	}

	@Override
	public String getOffersByProdId(String valueOf) {
		return intfBMO.getOffersByProdId(valueOf);
	}

	@Override
	public String getUmiCardByProdId(String valueOf) {
		return intfBMO.getUmiCardByProdId(valueOf);
	}

	@Override
	public List<Map<String, Object>> getStasByProdId(String valueOf) {
		return intfBMO.getStasByProdId(valueOf);
	}

	@Override
	public List<Map<String, Object>> getExchsByProdId(Long prodId) {
		return intfBMO.getExchsByProdId(prodId);
	}

	@Override
	public String getAddressByProdId(Long prodId) {
		return intfBMO.getAddressByProdId(prodId);
	}

	@Override
	public String getNetAccountByProdId(Long prodId) {
		return intfBMO.getNetAccountByProdId(prodId);
	}

	@Override
	public String getValidStrByPartyId(Long partyid) {
		return intfBMO.getValidStrByPartyId(partyid);
	}

	@Override
	public Long getPrepayFlagBySpecid(Long offerSpecId) {
		return intfBMO.getPrepayFlagBySpecid(offerSpecId);
	}

	@Override
	public List<Map<String, Object>> getAccMailMap(Long acctId) {
		return intfBMO.getAccMailMap(acctId);
	}

	@Override
	public String getGimsiByProdid(Long prodId) {
		return intfBMO.getGimsiByProdid(prodId);
	}

	@Override
	public String getIndustryClasscdByPartyId(Long partyId) {
		return intfBMO.getIndustryClasscdByPartyId(partyId);
	}

	@Override
	public String getPartyAddByProdId(Long prodId) {
		return intfBMO.getPartyAddByProdId(prodId);
	}

	@Override
	public String getCimsiByProdid(Long prodId) {
		return intfBMO.getCimsiByProdid(prodId);
	}

	@Override
	public String getEsnByProdid(Long prodId) {
		return intfBMO.getEsnByProdid(prodId);
	}

	@Override
	public String getCtfRuleIdByOCId(Long offerSpecId, Long couponId) {
		return intfBMO.getCtfRuleIdByOCId(offerSpecId, couponId);
	}

	@Override
	public boolean qryAccessNumberIsZt(Map<String, Object> map) {
		return intfBMO.qryAccessNumberIsZt(map);
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProdForPad(Long prodId) {
		return intfBMO.queryAttachOfferByProdForPad(prodId);
	}
	@Override
	public List<AttachOfferDto> queryAttachOfferInfo(Long prodId) {
		return intfBMO.queryAttachOfferInfo(prodId);
	}

	@Override
	public List queryFreeOfBank(String freezeNo, String bankCode, String bankName, String serialNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freezeNo", freezeNo);
		map.put("bankCode", bankCode);
		map.put("bankName", bankName);
		map.put("serialNumber", serialNumber);
		return intfBMO.queryFreeOfBank(map);
	}

	@Override
	public List getSaComponentInfo(String serviceId) {
		return intfBMO.getSaComponentInfo(serviceId);
	}

	@Override
	public int getIfpkByProd(String prodId) {
		return intfBMO.getIfpkByProd(prodId);
	}

	@Override
	public String insertPrm2CmsDls(Map<String, Object> param) {
		return intfBMO.insertPrm2CmsDls(param);
	}

	@Override
	public String insertPrm2CmsWt(Map<String, Object> param) {
		return intfBMO.insertPrm2CmsWt(param);
	}

	@Override
	public Boolean checkIfIdentityNum(String identityNumValue) {
		return intfBMO.checkIfIdentityNum(identityNumValue);
	}

	@Override
	public int queryCountProd(String identityNumValue) {
		return intfBMO.queryCountProd(identityNumValue);
	}

	@Override
	public boolean checkIsExistsParty(String custId) {
		return intfBMO.checkIsExistsParty(custId);
	}

	@Override
	public List<Map<String, Object>> queryTaxPayer(String custId) {
		return intfBMO.queryTaxPayer(custId);
	}

	@Override
	public Long getStaffIdByDbid(String dbid) {

		return intfBMO.getStaffIdByDbid(dbid);
	}

	@Override
	public Long getChannelIdByStaffId(Long staffId) {
		return intfBMO.getChannelIdByStaffId(staffId);
	}

	@Override
	public Long getStaffIdByAgentNum(String staffNumber) {

		return intfBMO.getStaffIdByAgentNum(staffNumber);
	}

	@Override
	public String findStaffNumByStaffId(Long staffid) {
		return intfBMO.findStaffNumByStaffId(staffid);
	}

	@Override
	public boolean getInfoByProId(Map map) {
		return intfBMO.getInfoByProId(map);
	}

	@Override
	public List<Map> instDateListBySt() {

		return intfBMO.instDateListBySt();
	}

	@Override
	public List<Map> Intf2BillingMsgListBySt(int i, Integer inProcessNumber) {

		return intfBMO.Intf2BillingMsgListBySt(i, inProcessNumber);
	}

	@Override
	public boolean processMsg(Map map) {

		return intfBMO.processMsg(map);
	}

	@Override
	public Long getChannelIdByStaffCode(String staffCode) {

		return intfBMO.getChannelIdByStaffCode(staffCode);
	}

	@Override
	public int qryUsefulOfferNumByAccnum(String accNbr) {

		return intfBMO.qryUsefulOfferNumByAccnum(accNbr);
	}

	@Override
	public boolean isLocalIvpn(Long prodId) {

		return intfBMO.isLocalIvpn(prodId);
	}

	@Override
	public List<Map<String, Object>> getCompLocalIvpns(Long prodId) {

		return intfBMO.getCompLocalIvpns(prodId);
	}

	@Override
	public Map<String, Object> getIvpnInfos(Long prodId) {

		return intfBMO.getIvpnInfos(prodId);
	}
	@Override
	public Map<String, Object> getIccIdByProdId(Long prodId,String itemSpecId) {
		
		return intfBMO.getIccIdByProdId(prodId,itemSpecId);
	}
	
	@Override
	public boolean getInfoByCRM(Map map) {
		// TODO Auto-generated method stub
		return intfBMO.getInfoByCRM(map);
	}

	@Override
	public List<Map> csipPCRFListByst(int i) {
		// TODO Auto-generated method stub
		return intfBMO.csipPCRFListByst(i);
	}

	@Override
	public Long getDevNumIdByAccNum(String accNum) {
		return intfBMO.getDevNumIdByAccNum(accNum);
	}

	@Override
	public Long getValidateYHParams(Map<String, Object> paraMap) {
		return intfBMO.getValidateYHParams(paraMap);
	}

	@Override
	public Long getValidateYHOffer(Map<String, Object> offerMap) {
		return intfBMO.getValidateYHOffer(offerMap);
	}

	@Override
	public Map<String, Object> queryOrderList(String orderId) {
		return intfBMO.queryOrderList(orderId);
	}

	@Override
	public String getLteImsiByProdid(Long prodId) {

		return intfBMO.getLteImsiByProdid(prodId);
	}

	@Override
	public void saveJSONObject(String jsonObjectStr) {

		intfBMO.saveJSONObject(jsonObjectStr);
	}

	@Override
	public Map<String, Object> getProdSmsByProdId(String prodid) {
		return intfBMO.getProdSmsByProdId(prodid);
	}
	
	@Override
	public Map<String, Object> getProdInfoByAccNbr(String accNbr) {
		return intfBMO.getProdInfoByAccNbr(accNbr);
	}
	
	@Override
	public Map<String, Object> getCouponInfoByTerminalCode(String terminalCode) {
		return intfBMO.getCouponInfoByTerminalCode(terminalCode);
	}
	@Override
	public Map<String, Object> getgetprod2TdIdDelCodeCode(String terminalCode) {
		return intfBMO.getgetprod2TdIdDelCodeCode(terminalCode);
	}
	
	@Override
	public Map<String, Object> getBasicCouponInfoByTerminalCode(String terminalCode) {
		return intfBMO.getBasicCouponInfoByTerminalCode(terminalCode);
	}
	
	@Override
	public Map<String, Object> queryTerminalCodeByProdId(String prodId) {
		return intfBMO.queryTerminalCodeByProdId(prodId);
	}
	
	@Override
	public Map<String, Object> getDevInfoByCode(String devCode) {
		return intfBMO.getDevInfoByCode(devCode);
	}

	@Override
	public Map<String, Object> getStaffIdByStaffCode(String staffCdoe) {
		return intfBMO.getStaffIdByStaffCode(staffCdoe);
	}
	
	@Override
	public void updateJSONObject(String resultStr) {
		intfBMO.updateJSONObject(resultStr);

	}

	@Override
	public String querySpeedValue(String prodSpecId) {
		return intfBMO.querySpeedValue(prodSpecId);
	}

	@Override
	public boolean isUimBak(String prodid) {

		return intfBMO.isUimBak(prodid);
	}

	@Override
	public boolean getIsOrderOnWay(Map<String, Object> map) {

		return intfBMO.getIsOrderOnWay(map);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String findTelphoneByCardno(String terminalCode) {

		return intfBMO.findTelphoneByCardno(terminalCode);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String findTelphoneByDiscard(String terminalCode) {

		return intfBMO.findTelphoneByDiscard(terminalCode);
	}

	@Override
	public boolean isExistCardByProdId(Map<String, Object> map) {
		return intfBMO.isExistCardByProdId(map);
	}

	@Override
	public Map<String, Object> getImsiInfoByBillingNo(String billingNo) {

		return intfBMO.getImsiInfoByBillingNo(billingNo);
	}

	@Override
	public List<Map<String, Object>> getBillingCardRelation(String billingNo) {
		return intfBMO.getBillingCardRelation(billingNo);
	}
	
	@Override
	public List<Map<String, Object>> getDevNumIdByDevCode(String devCode) {
		return intfBMO.getDevNumIdByDevCode(devCode);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> queryTableSpace() {

		return intfBMO.queryTableSpace();
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> queryUseSpaceNotSysLob(String tableSpaceName) {
		return intfBMO.queryUseSpaceNotSysLob(tableSpaceName);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> queryUseSpaceSysLob(String tableSpaceName) {

		return intfBMO.queryUseSpaceSysLob(tableSpaceName);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> queryCrmLockInfo() {

		return intfBMO.queryCrmLockInfo();
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Long queryDBSessionInfo() {

		return intfBMO.queryDBSessionInfo();
	}

	@Override
	public boolean saveBenzBusiOrder(Map<String, Object> result) {

		return intfBMO.saveBenzBusiOrder(result);
	}

	@Override
	public void saveBenzBusiOrderSub(Map<String, Object> resultSub) {
		intfBMO.saveBenzBusiOrderSub(resultSub);
	}

	@Override
	public boolean isBenzOfferServUser(String accNbr) {
		return intfBMO.isBenzOfferServUser(accNbr);
	}

	@Override
	public List<Map<String, Object>> getProdItemsByParam(Map<String, Object> param) {

		return intfBMO.getProdItemsByParam(param);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map<String, Object>> getCustClassInfoByCustId(String custId) {

		return intfBMO.getCustClassInfoByCustId(custId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getCustIdByAccNum(String accessNumber) {

		return intfBMO.getCustIdByAccNum(accessNumber);
	}

	@Override
	public boolean qryProdOrderIsZtByOrderTypes(Map<String, Object> map) {

		return intfBMO.qryProdOrderIsZtByOrderTypes(map);
	}

	@Override
	public Map<String, Object> queryOfferProdStatus(String accessNumber) {

		return intfBMO.queryOfferProdStatus(accessNumber);
	}

	@Override
	public boolean isFtWifiSystem(String accessNumber) {
		return intfBMO.isFtWifiSystem(accessNumber);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public int checkHykdOrder(Map<String, Object> param) {

		return intfBMO.checkHykdOrder(param);
	}

	@Override
	public Map<String, String> checkCustName(String phone_number, String cust_name) {
		return intfBMO.checkCustName(phone_number, cust_name);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> getPartyTypeCdByProdId(Long prodidByAccNbr) {

		return intfBMO.getPartyTypeCdByProdId(prodidByAccNbr);
	}

	@Override
	public boolean checkOfferSpecBsns(String id) {

		return intfBMO.checkOfferSpecBsns(id);
	}

	@Override
	public long checkBankFreeze(Map<String, Object> checkMap) {

		return intfBMO.checkBankFreeze(checkMap);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public Map<String, Object> qryOrderListByOlId(String olId) {

		return intfBMO.qryOrderListByOlId(olId);
	}

	@Override
	public String getNbrTypeByAccNbr(String accNbr) {

		return intfBMO.getNbrTypeByAccNbr(accNbr);
	}

	@Override
	public List<Map<String, Object>> qryEncryptStrByParam(Map<String, String> param) {

		return intfBMO.qryEncryptStrByParam(param);
	}

	@Override
	public Map<String, Object> queryOrgByStaffNumber(String staffNumber) {

		return intfBMO.queryOrgByStaffNumber(staffNumber);
	}

	@Override
	public Map<String, Object> findOrgByStaffId(Map<String, Object> map) {

		return intfBMO.findOrgByStaffId(map);
	}

	@Override
	public List<Long> queryStaffByProdId(Long prodIdLong) {

		return intfBMO.queryStaffByProdId(prodIdLong);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getIDCardEncryptionVector(String mac) {

		return intfBMO.getIDCardEncryptionVector(mac);
	}

	@Override
	public List<Map<String, Object>> getGhAddressTemp() {

		return intfBMO.getGhAddressTemp();
	}

	@Override
	public void insertGhAddressUnit(Map<String, Object> param) {
		intfBMO.insertGhAddressUnit(param);

	}

	@Override
	public long queryBussinessOrder(Map<String, Object> mk) {

		return intfBMO.queryBussinessOrder(mk);
	}

	@Override
	public long querySeqBussinessOrder() {

		return intfBMO.querySeqBussinessOrder();
	}

	@Override
	public void saveOrUpdateBussinessOrderCheck(Map<String, Object> mk, String str) {
		intfBMO.saveOrUpdateBussinessOrderCheck(mk, str);
	}

	@Override
	public int isAccNumRealNameparty(Long prodId) {

		return intfBMO.isAccNumRealNameparty(prodId);
	}

	@Override
	public String getIntfReqCtrlValue(String string) {

		return intfBMO.getIntfReqCtrlValue(string);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public  Map<String, Object>  checkParByIdCust(Map<String, Object> m) {

		return intfBMO.checkParByIdCust(m);
	}

	@Override
	public Map<String, Object> queryOfferProdItemsByProdId(Long prodId) {

		return intfBMO.queryOfferProdItemsByProdId(prodId);
	}

	@Override
	public boolean isManyPartyByIDNum(Map<String, Object> m) {

		return intfBMO.isManyPartyByIDNum(m);
	}

	@Override
	public boolean insertSms(Map<String, Object> map) {

		return intfBMO.insertSms(map);
	}

	@Override
	public List<Map<String, Object>> qryBoInfos(Map<String, Object> mv) {
		
		return intfBMO.qryBoInfos(mv);
	}

	@Override
	public Long qryChargesByOlid(String ol_id) {
		
		return intfBMO.qryChargesByOlid(ol_id);
	}

	@Override
	public String queryOfferAddressDesc(Long prod_id) {
		
		return intfBMO.queryOfferAddressDesc(prod_id);
	}

	@Override
	public String getTmDescription(Long prodId) {
		
		return intfBMO.getTmDescription(prodId);
	}

	@Override
	public String getOfferProdTmlName(Long prodId) {
		
		return intfBMO.getOfferProdTmlName(prodId);
	}

	@Override
	public String getPartyManagerName(Long prodId) {
		
		return intfBMO.getPartyManagerName(prodId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public int getOfferSpecAction2Count(String offerSpecId) {
		
		return intfBMO.getOfferSpecAction2Count(offerSpecId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getCommunityPolicy(String buildingId) {
		
		return intfBMO.getCommunityPolicy(buildingId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public List<Map> getOfferMemberInfo(String prodId) {

		return intfBMO.getOfferMemberInfo(prodId);
	}

	@Override
	@SwitchDS(DsKey.CRM_SLAVE)
	public String getComponentBuildingId(String serviceId) {

		return intfBMO.getComponentBuildingId(serviceId);
	}

	@Override
	public String getOrganizStaffOrgId(Long prodId) {
	
		return intfBMO.getOrganizStaffOrgId(prodId);
	}
	@Override
	public String getDevelopmentDepartment(String accessNumber) {
	
		return intfBMO.getDevelopmentDepartment(accessNumber);
	}

	@Override
	public String getChannelNbrByChannelID(String channelId) {
		
		return intfBMO.getChannelNbrByChannelID(channelId);
	}

	@Override
	public int getCmsStaffCodeByStaffCode(String staffCode) {

		return intfBMO.getCmsStaffCodeByStaffCode(staffCode);
	}

	@Override
	public String getOfferProdReduOwnerIdByAccNbr(String accNbr) {
		
		 return intfBMO.getOfferProdReduOwnerIdByAccNbr(accNbr);
	}

	@Override
	public Map<String, Object> getSaopRuleIntfLogByTransactionID(String transactionID) {
		
		return intfBMO.getSaopRuleIntfLogByTransactionID(transactionID);
	}
	@Override
	public List<Map<String, Object>> validatePhoneCanChangeBand(String accNbr) {
		
		return intfBMO.validatePhoneCanChangeBand(accNbr);
	}
	@Override
	public List<Map<String, Object>> queryMainOfferPackageRelation(String offerSpecId) {
		
		return intfBMO.queryMainOfferPackageRelation(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryAccNbrInfoList(String accNbr) {
		
		return intfBMO.queryAccNbrInfoList(accNbr);
	}
	@Override
	public Map<String, Object> queryAccessTypeByAccessNumber(String accNbr,String itemSpecIds) {
		
		return intfBMO.queryAccessTypeByAccessNumber(accNbr,itemSpecIds);
	}
	@Override
	public List<Map<String, Object>> getProdCompList(String accNbr) {
		
		return intfBMO.getProdCompList(accNbr);
	}
	@Override
	public Map<String, Object> queryCompAccNbrByCompProdId(String compProdId) {
		
		return intfBMO.queryCompAccNbrByCompProdId(compProdId);
	}
	@Override
	public Map<String, Object> querySpeedInfoByOfferSpecId(String offerSpecId) {
		
		return intfBMO.querySpeedInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryChangeOffersByOfferSpecId(String offerSpecId) {
		
		return intfBMO.queryChangeOffersByOfferSpecId(offerSpecId);
	}
	@Override
	public boolean judgeMainOfferByOfferSpecId(String offerSpecId) {
		
		return intfBMO.judgeMainOfferByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryCompInfoListByOfferSpecId(String offerSpecId) {
		
		return intfBMO.queryCompInfoListByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryCompMainOfferByProdSpecId(Map<String,Object> paraMap) {
		
		return intfBMO.queryCompMainOfferByProdSpecId(paraMap);
	}
	@Override
	public Map<String,Object> getProdInfoByAccessNuber(String olId){
		
		return intfBMO.getProdInfoByAccessNuber(olId);
	}
	@Override
	public Map<String,Object> getServInfoByOfferSpecId(String offerSpecId){
		
		return intfBMO.getServInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String,Object>> getComBasicInfoByOfferSpecId(String offerSpecId){
		
		return intfBMO.getComBasicInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public String queryStaffIdByStaffCode(String staffCode){
		
		return intfBMO.queryStaffIdByStaffCode(staffCode);
	}
	public String sYncStaff2Crm(String request) throws Exception {
		StringBuffer retStr = new StringBuffer();
		Document doc = WSUtil.parseXml(request);
		String transactionID = WSUtil.getXmlNodeText(doc, "//request/TransactionID");
		String reqTime = WSUtil.getXmlNodeText(doc, "//request/info/ReqTime");
		String SALES_CODE = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/SALES_CODE");
		String nbr = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/STAFF_CODE");
		String orgId = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/ORG_ID");
		String name = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/STAFF_NAME");
		String idtype = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/CERT_TYPE");
		String idnbr = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/CERT_NUMBER");
		String linknbr = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/MOBILE_PHONE");
		String E_MAIL = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/E_MAIL");
		String COMMON_REGION_ID = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/COMMON_REGION_ID");
		String STAFF_DESC = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/STAFF_DESC");
		String statusCd = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/STATUS_CD");
		String CREATE_DATE = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/CREATE_DATE");
		String action = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/ACTION");
		String partyId = WSUtil.getXmlNodeText(doc, "//request/info/STAFF/PARTY_ID");
		JSONArray partyArray = new JSONArray();
		JSONObject partyInfo = new JSONObject();
		if(action!=null && action.equals("ADD")){
			action = "1";
		}else if(action!=null && action.equals("MOD")){
			action = "2";
		}else if(action!=null && action.equals("DEL")){
			action = "3";
		}
		//根据staff_code查询partyId，即staffId
//		String staffId = intfBMO.queryStaffIdByStaffCode(nbr);
//		if(!"1".equals(action)){
//			partyId = staffId;
//		}
		Map<String,Object>  map = new HashMap();
		map.put("idtype", idtype);
		partyInfo.element("action", action);
		partyInfo.element("sysId", "6090010002");
		JSONObject partyInfoDetail = new JSONObject();
		partyInfoDetail.element("areaId", "11000");
		partyInfoDetail.element("statusCd", statusCd);
		partyInfoDetail.element("id", partyId);
		partyInfoDetail.element("idnbr", idnbr);
		partyInfoDetail.element("idtype", idtype);
		partyInfoDetail.element("linknbr", linknbr);
		partyInfoDetail.element("name", name);
		partyInfoDetail.element("nbr", nbr);
		partyInfoDetail.element("orgId", orgId);
//		partyInfoDetail.element("orgId", "104002892960");
		partyInfoDetail.element("pwd", "123456");
		partyInfoDetail.element("typeCd", "1");
//		partyInfoDetail.element("staffSegmentType", "8");
//		partyInfoDetail.element("staffSegmentValue", "1234567");
		partyInfo.element("party", partyInfoDetail);
		
		partyArray.add(partyInfo);
		System.out.println("系管入参报文：>>>>>>>>>>>>>>>" + partyArray.toString());
		//员工信息同步系管
		String orgOpTypeResult = smService.operationStaff(partyArray.toString());
		JSONObject jsonOrg = JSONObject.fromObject(orgOpTypeResult);
		System.out.println("系管返回json报文：>>>>>>>>>>>>>>>" + jsonOrg);
		
		
//		if (!"0".equals(jsonOrg.getString("code"))) {
//			return WSUtil.buildResponse(jsonOrg.getString("code"), jsonOrg.getString("info"));
//		}
		Date rspTime = new Date();
		retStr.append("<response>");
		retStr.append("<TransactionID>").append(transactionID).append("</TransactionID>");
		retStr.append("<RspTime>").append(rspTime).append("</RspTime>");
		retStr.append("<RspCode>").append(jsonOrg.getString("code")).append("</RspCode>");
		if(jsonOrg.getString("code").equals("0")){
			retStr.append("<PARTY_ID>").append(jsonOrg.getString("id")).append("</PARTY_ID>");
			retStr.append("<RspDesc>").append(jsonOrg.getString("msg")).append("</RspDesc>");
		}else{
			retStr.append("<RspDesc>").append(jsonOrg.getString("msg")+jsonOrg.getString("info")).append("</RspDesc>");
		}
		retStr.append("</response>");
		return String.valueOf(retStr);
	}

	@Override
	public Map<String, Object> getMethForFtth(String prodId) {
		return intfBMO.getMethForFtth(prodId);
	}

	@Override
	public String getProValue(String prodId) {
		return intfBMO.getProValue(prodId);
	}

	@Override
	public Map<String, Object> getaddresForFtth(String prodId) {
		return intfBMO.getaddresForFtth(prodId);
	}

	@Override
	public Map<String, Object> getTmlForFtth(String prodId) {
		return intfBMO.getTmlForFtth(prodId);
	}

	@Override
	public boolean checkSchool(String channelId) {
		return intfBMO.checkSchool(channelId);
	}

	@Override
	public List<Map<String,Object>> queryExchangeByName(String name) {
		return intfBMO.queryExchangeByName(name);
	}

	@Override
	public List<Map<String, Object>> getchannelByCode(String code) {
		return intfBMO.getchannelByCode(code);
	}

	@Override
	public List<Map<String, Object>> queryDeptInfo(String name,String level) {
		return intfBMO.queryDeptInfo(name,level);
	}

	@Override
	public List<Map<String, Object>> getOlidByg(String olId) {
		return intfBMO.getOlidByg(olId);
	}

	@Override
	public List<Map<String, Object>> queryChannelsByMap(String staffId) {
		return intfBMO.queryChannelsByMap(staffId);
	}

	@Override
	public List<Map<String, Object>> checkCinfoByb(String code) {
		return intfBMO.checkCinfoByb(code);
	}

	@Override
	public Map<String, Object> queryByPartyId(String partyId) {
		return intfBMO.queryByPartyId(partyId);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsList(String time,String staffId) {
		return intfBMO.queryIndicatorsList(time , staffId);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsListMouth(String startTime,
			String endTime,String staffId) {
		return intfBMO.queryIndicatorsListMouth(startTime,endTime,staffId);
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsList(
			String statisticalTime, String startRown ,String endRown,
			String staffId,String accessNumber) {
		return intfBMO.queryDetailedIndicatorsList(statisticalTime, startRown, endRown,
				staffId, accessNumber);
	}

	@Override
	public Map<String, Object> queryIndicatorsNumber(String statisticalTime,
			String staffId,String accessNumber) {
		return intfBMO.queryIndicatorsNumber(statisticalTime,staffId,accessNumber);
	}

	@Override
	public Map<String, Object> queryEmailByPartyId(String partyId) {
		return intfBMO.queryEmailByPartyId(partyId);
	}

	@Override
	public List<Map<String, Object>> getValueByolId(String olId) {
		return intfBMO.getValueByolId(olId);
	}

	@Override
	public List<Map<String, Object>> getOlnbrByg(String olId) {
		return intfBMO.getOlnbrByg(olId);
	}

	@Override
	public String getNumByStatus(String specId) {
		return intfBMO.getNumByStatus(specId);
	}
	@Override
	public Map<String, Object> queryIndicators1(String statisticalTime,
			String staffId) {
		
		return intfBMO.queryIndicators1(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators2(String statisticalTime,
			String staffId) {
		
		return intfBMO.queryIndicators2(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators3(String statisticalTime,
			String staffId) {
		
		return intfBMO.queryIndicators3(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators4(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators4(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators9(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators9(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators5(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators5(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators6(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators6(statisticalTime,
				staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicators7(String statisticalTime,
			String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicators7(statisticalTime,
				staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicators8(String statisticalTime,
			String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicators8(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators10(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators10(statisticalTime,
				staffId);
	}

	@Override
	public Map<String, Object> queryIndicators11(String statisticalTime,
			String staffId) {
		return intfBMO.queryIndicators11(statisticalTime,
				staffId);
	}
	@Override
	public Map<String, Object> queryIndicatorsMouth1(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth1(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth2(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth2(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth3(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth3(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth10(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth10(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth11(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth11(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth12(String statisticalTime) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth12(statisticalTime);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth4(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth4(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth5(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth5(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth6(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth6(startTime,endTime,staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth7(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth7(startTime,endTime,staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth8(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth8(startTime,endTime,staffId);
	}



	@Override
	public Map<String, Object> queryIndicatorsMouth9(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfBMO.queryIndicatorsMouth9(startTime,endTime,staffId);
	}

	@Override
	public Map<String, Object> getAddressPreemptedSeq() {
		return intfBMO.getAddressPreemptedSeq( );
	}

	@Override
	public Map<String, Object> showSchoolName(String bcdCode) {
		return intfBMO.showSchoolName(bcdCode);
	}

	@Override
	public List<Map<String, Object>> qryDeptById(String dept_id) {
		// TODO Auto-generated method stub
		return intfBMO.qryDeptById(dept_id);
	}

	@Override
	public List<Map<String, Object>> campusCustomerCampusMark(String subjectId,
			String subjectNameId) {
		// TODO Auto-generated method stub
		return intfBMO.campusCustomerCampusMark(subjectId,subjectNameId);
	}

	@Override
	public List<Map<String, Object>> queryCampusCustomerInformation(String num) {
		// TODO Auto-generated method stub
		return intfBMO.queryCampusCustomerInformation(num);
	}

	@Override
	public void saveSendRecord(Map<String, Object> saveMap) {
		intfBMO.saveSendRecord(saveMap);
		
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsListMouth(
			String startTime, String endTime, String startRown ,String endRown,
			String staffId,String accessNumber) {
		// TODO Auto-generated method stub
		return intfBMO.queryDetailedIndicatorsListMouth( startTime,  endTime, startRown,  endRown,
				 staffId, accessNumber);
	}

	@Override
	public Map<String, Object> qryKeyByFlag() {
		// TODO Auto-generated method stub
		return intfBMO.qryKeyByFlag();
	}

	@Override
	public Map<String, Object> queryIndicatorsNumberMouth(String startTime,
			String endTime, String staffId, String accessNumber) {
		return intfBMO.queryIndicatorsNumberMouth(startTime,endTime,staffId,accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryRewardInfoById(String staffId,
			String stardTime, String endTime,String startNum,String endNum) {
		return intfBMO.queryRewardInfoById(staffId,stardTime,endTime,startNum,endNum);
	}

	@Override
	public Map<String, Object> queryRewardSum(String staffId, String stardTime,
			String endTime) {
		return intfBMO.queryRewardSum(staffId,stardTime,endTime);
	}

	@Override
	public Map<String, Object> queryRewardInfo(String rewardId) {
		return intfBMO.queryRewardInfo(rewardId);
	}

	@Override
	public List<Map<String, Object>> querypartyListBypartyList(String idCard) {
		return intfBMO.querypartyListBypartyList(idCard);
	}

	@Override
	public List<Map<String, Object>> queryProListByProId(String prodId) {
		return intfBMO.queryProListByProId(prodId);
	}

	@Override
	public Map<String, Object> queryOlidByProId(String prodId) {
		return intfBMO.queryOlidByProId(prodId);
	}

	@Override
	public Map<String, Object> queryUimCodeByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return intfBMO.queryUimCodeByAccessNumber(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOlIdByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return intfBMO.queryOlIdByAccessNumber(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOrganizationforScrm(
			String staffNumber, String staffName, String organizationId,
			String ruleNumber) {
		return intfBMO.queryOrganizationforScrm(staffNumber, staffName, organizationId,
				ruleNumber);
	}

	@Override
	public Map<String, Object> queryOlNbrByOlId(String id) {
		// TODO Auto-generated method stub
		return intfBMO.queryOlNbrByOlId(id);
	}

	@Override
	public List<Map<String, Object>> queryRewardSource(String olNbr) {
		// TODO Auto-generated method stub
		return intfBMO.queryRewardSource(olNbr);
	}

	@Override
	public void insertReward(Map<String, String> para) {
		// TODO Auto-generated method stub
		intfBMO.insertReward(para);
		
	}

	@Override
	public String queryRewardId() {
		// TODO Auto-generated method stub
		return intfBMO.queryRewardId();
	}

	@Override
	public Map<String, Object> getFingerInfo(String olId) {
		return intfBMO.getFingerInfo(olId);
	}

	@Override
	public void insertFingerInfo(Map<String, Object> fingerInfo) {
		intfBMO.insertFingerInfo(fingerInfo);		
	}

	@Override
	public String queryPayIdentityInfo(String PayIdentityNum , String lsNumber) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = unityPayClient.indentItemQry(PayIdentityNum, lsNumber);
			String result = map.get("result").toString();
			if ("0".equals(result)) {
				StringBuffer rt = new StringBuffer();
				rt.append("<response><resultCode>0</resultCode><resultMsg>成功</resultMsg>");
				rt.append("<responseId>").append(map.get("responseId").toString()).append("</responseId>");
				rt.append("<responseTime>").append(map.get("responseTime").toString()).append("</responseTime>");
				rt.append("<staffId>").append(map.get("staffId").toString()).append("</staffId>");
				rt.append("<indentCharge>").append(map.get("indentCharge").toString()).append("</indentCharge>");
				rt.append("<charge>").append(map.get("charge").toString()).append("</charge>");
				rt.append("<payIndentId>").append(map.get("indentItemPay").toString()).append("</payIndentId>");
				rt.append("<channelId>").append(map.get("channelId1").toString()).append("</channelId>");
				rt.append("</response>");
				return rt.toString();
			} else {
				return "<response><resultCode>1</resultCode><resultMsg>费用查询失败</resultMsg></response>";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryStoreByStaffId(String staffId) {
		return intfBMO.queryStoreByStaffId(staffId);
	}

	@Override
	public List<Map<String, Object>> queryStatusByInstCode(String mktResInstCode) {
		return intfBMO.queryStatusByInstCode(mktResInstCode);
	}

	@Override
	public Map<String, Object> getStaffIdBystaffNumber(String staffNumber) {
		return intfBMO.getStaffIdBystaffNumber(staffNumber);
	}

	@Override
	public List<Map<String, Object>> getEdidInfo() {
		return intfBMO.getEdidInfo();
	}

	@Override
	public void edidType(String partyId) {
		intfBMO.edidType(partyId);		
	}

	@Override
	public Map<String, Object> queryIdentityInfoByOlnbr(String olNbr) {
		return intfBMO.queryIdentityInfoByOlnbr(olNbr);
	}

	@Override
	public Map<String, Object> queryProdInfoByAccs(String accessNumber) {
		return intfBMO.queryProdInfoByAccs(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryDepinfoForScrm(String partyId) {
		return intfBMO.queryDepinfoForScrm(partyId);
	}

	@Override
	public List<Map<String, Object>> queryUserdByAccess(String accessNuber) {
		return intfBMO.queryUserdByAccess(accessNuber);
	}

	@Override
	public Map<String, Object> queryExtCustOrder(String queryExtCustOrder) {
		return intfBMO.queryExtCustOrder(queryExtCustOrder);
	}

	@Override
	public String[] findPartyByIdentityPic(String name, String identityNum) {
		return intfBMO.findPartyByIdentityPic(name,identityNum);
	}

	@Override
	public List<Map<String, Object>> queryPhoneNoByAliUid(String aliUid) {
		return intfBMO.queryPhoneNoByAliUid(aliUid);
	}

	@Override
	public void upStarMember(Map<String, Object> parameter) {
		intfBMO.upStarMember(parameter);
	}

	@Override
	public Map<String, Object> queryCaflagByOlnbr(String olNbr) {
		return intfBMO.queryCaflagByOlnbr(olNbr);
	}

	@Override
	public String queryBlocOlidToProOlid(String olId) {
		return intfBMO.queryBlocOlidToProOlid(olId);
	}

	@Override
	public void insertPartyFlagInfo(Map<String, Object> autonymFlag) {
		intfBMO.insertPartyFlagInfo(autonymFlag);
	}

	@Override
	public String queryBlocOlidToProOlNbr(String olId) {
		return intfBMO.queryBlocOlidToProOlNbr(olId);
	}

	@Override
	public String queryBlocOlidToBlocOlNbr(String olNbr) {
		return intfBMO.queryBlocOlidToBlocOlNbr(olNbr);
	}

	@Override
	public void InsertOutskirts(Map<String, Object> parameter) {
		intfBMO.InsertOutskirts(parameter);
	}

	@Override
	public Boolean queryOlidIfHave(String olId) {
		return intfBMO.queryOlidIfHave(olId);
	}

	@Override
	public void updataOutskirts(Map<String, Object> parameter) {
		intfBMO.updataOutskirts(parameter);
	}

	@Override
	public String queryPartyIdByprodId(String prodId) {
		return intfBMO.queryPartyIdByprodId(prodId);
	}

	@Override
	public String queryStaffIdBystaffCode(String staffCode) {
		return intfBMO.queryStaffIdBystaffCode(staffCode);
	}

	@Override
	public String queryOfferspecidBystaffCode(String prodId) {
		return intfBMO.queryOfferspecidBystaffCode(prodId);
	}

	@Override
	public Map<String, Object> queryOldcustinfoByPartyId(String partyId) {
		return intfBMO.queryOldcustinfoByPartyId(partyId);
	}

	@Override
	public String queryPartyIdByCardId(String cerdValue) {
		return intfBMO.queryPartyIdByCardId(cerdValue);
	}

	@Override
	public Boolean getSchoolRole(String staffId) {
		return intfBMO.getSchoolRole(staffId);
	}

	@Override
	public String queryGroupNumberByGroupId(String olId) {
		return intfBMO.queryGroupNumberByGroupId(olId);
	}

	@Override
	public String queryProvenceIdByGroupNum(String olId) {
		return intfBMO.queryProvenceIdByGroupNum(olId);
	}

	@Override
	public Map<String, Object> queryBureauDirectionByPhoneNum(String phoneNumber) {
		return intfBMO.queryBureauDirectionByPhoneNum(phoneNumber);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByIdent(String custInfo) {
		return intfBMO.queryRealNameFlagByIdent(custInfo);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByParttId(String custInfo) {
		return intfBMO.queryRealNameFlagByParttId(custInfo);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByPhoneAccssnumber(
			String custInfo) {
		return intfBMO.queryRealNameFlagByPhoneAccssnumber(custInfo);
	}

	@Override
	public Map<String, Object> queryPictureByolId(String olId) {
		return intfBMO.queryPictureByolId(olId);
	}

	@Override
	public Map<String, Object> querygztPictureInfoByolId(String olId) {
		return intfBMO.querygztPictureInfoByolId(olId);
	}

	@Override
	public String queryAccIdbyAccCd(String accId) {
		return intfBMO.queryAccIdbyAccCd(accId);
	}

	@Override
	public List<Map<String, Object>> desensitizationService(String object) {
		return intfBMO.desensitizationService(object);
	}

	@Override
	public String desensitizationSystemCode(String desensitizationSystemId) {
		return intfBMO.desensitizationSystemCode(desensitizationSystemId);
	}

	@Override
	public void savaDesensitizationLog(String logId,
			String desensitizationSystemId, String request, Date requestTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", logId);
		map.put("system", desensitizationSystemId);
		map.put("request", request);
		map.put("visitDt", requestTime);
		intfBMO.savaDesensitizationLog(map);
		
	}
	
	@Override
	public Map<String, Object> queryNewPartyPhotoByOlId(String olId) {
		return intfBMO.queryNewPartyPhotoByOlId(olId);
	}
	
	public List<Object> queryOfferSpecInfoList(Map<String, Object> param) {		
		return intfBMO.queryOfferSpecInfoList(param);
	}
	
	public List<Map<String,Object>> queryChannelInfoByIdentityNum(String identityNum) {		
		return intfBMO.queryChannelInfoByIdentityNum(identityNum);
	}
	
	@Override
	public Map<String, Object> getStartLevelByPartyAccNbr(String accNbr) {		
		return intfBMO.getStartLevelByPartyAccNbr(accNbr);
	}
}
