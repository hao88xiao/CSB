package com.linkage.bss.crm.intf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.model.AreaInfo;
import com.linkage.bss.crm.intf.model.BankTableEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntityInputBean;
import com.linkage.bss.crm.intf.model.DiscreateValue;
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
import com.linkage.bss.crm.model.BoServOrder;
import com.linkage.bss.crm.model.BusiOrder;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferSpecParam;
import com.linkage.bss.crm.model.ProdSpec;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offerspec.dto.AttachOfferInfoDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.unityPay.IndentItemPay;

public interface IntfDAO {

	/**
	 * 根据主接入号码查询产品
	 * 
	 * @param accessNumber
	 * @return
	 */
	public OfferProd queryProdByAcessNumber(String accessNumber);

	public List<Map<String, Object>> getStaticSql(List<String> id);

	public List<Map<String, Object>> getSqlInfo(String sql);

	public List<Map<String, Object>> getItemSpec(String itemSpecId);

	/**
	 * 查询场景模版
	 * 
	 * @return
	 */
	public List<Map<String, Object>> qryOfferModel(Map<String, Object> param);

	public String queryProdQryPwdByProdId(Long prodId);

	public String queryProdBizPwdByProdId(Long prodId);

	public String queryAcctPwdByAcctId(Long acctId);

	public Tel2Area queryAccNBRInfo(String accNbr);

	public AreaInfo queryAreaInfoByCode(String areaCode);

	public AreaInfo queryAreaInfoByName(String areaName);

	public List<OperatingOfficeInfo> queryOperatingOfficeInfoAll();

	public List<OperatingOfficeInfo> queryOperatingOfficeInfoByAreaCode(String areaCode);

	public List<OperatingOfficeInfo> queryOperatingOfficeInfoByAreaName(String areaName);

	public List<Map<String, Object>> queryTerminalCode(String iccid);

	public List<Map<String, Object>> ifYKSX(String iccid);

	public List<Map<String, Object>> queryNumOfYKSX(String iccid);

	public List<Map<String, Object>> queryNum(String iccid);

	/**
	 * 电子有价卡批量获取请求 插入接口表
	 * 
	 * @param trade_id
	 * @param sale_time
	 * @param channelId
	 * @param staffCode
	 * @param value_card_type_code
	 * @param value_code
	 * @param apply_num
	 * @param flag
	 */
	public String insertIntoGoodsApplayIntf(String trade_id, String sale_time, String channelId, String staffCode,
			String value_card_type_code, String value_code, String apply_num, int flag);

	public Date queryCurrentTime();

	public List<RoleObj> queryRoleObjs(Integer objType, Long objId);

	public Offer findOffer(Long prodId, Long offerSpecId);

	public OfferRoles findOfferRoles(Long offerSpecId, Integer objType, Long objId, Integer roleId);

	public List<OfferRoles> findOfferRoles(Long offerSpecId, Integer objType, Integer roleId);

	public RoleObj findRoleObjByOfferRoleId(Long offerRoleId, Integer objType);

	public OfferServ findOfferServ(Long prodId, Long servSpecId);

	public OfferProdItem findOfferProdItem(Long prodId, Long itemSpecId);

	public OfferProd2Td findOfferProd2Td(Long prodId, Long terminalDevSpecId);

	public ProdSpec2AccessNumType findProdSpec2AccessNumType(Long prodSpecId, String isPrimary);

	public Account findAcctByAcctCd(String acctCd);

	public Account findAcctByAccNbr(String accessNumber, Integer prodSpecId);

	public String findOfferOrService(Long id);

	public Long selectByAcctId(Long id);

	public List<Map> findServSpecItem(Long id, String flag);

	/**
	 * 查询亲情号码值
	 * 
	 * @param paramMap
	 *            (accNbr and offerSpecId)
	 * @return
	 */
	public List<Map<String, Object>> queryFNSInfo(Map<String, Object> param);

	/**
	 * 判断是否已做话费补贴
	 * 
	 * @param coupon_ins_number
	 * @return status coupon_id
	 * @author ZHANGC
	 */
	public Map isSubsidy(String coupon_ins_number);

	/**
	 * 查询可设置亲情号码数量
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public int queryFNSNum(Long offerSpecId);

	/**
	 * 根据prodSpecId查询prodSpec
	 * 
	 * @param prodSpecId
	 * @return
	 */
	public ProdSpec getProdSpecByProdSpecId(Long prodSpecId);

	public int queryValidateServiceByAccNum(String accessNumber);

	public List<NewValidate> isNewValidateService(String accessNumber);

	public List<YKSXInfo> queryYKSXInfoByAccNbr(String accNbr);

	public String queryYKSXAccNum(Map<String, Object> param1);

	/**
	 * 根据卡号查询imsi信息
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	public List<String> queryImsiInfoByMdn(Long prodId);

	public Date queryDtByProdId(Long prodId);

	public int queryCntByProdId(Map<String, Object> param);

	public int queryCntBySpecId(Map<String, Object> param0);

	public int queryCntByOfferSpecId(Map<String, Object> param2);

	public int queryCntByOfferProdId(Map<String, Object> param3);

	public int queryMoneyByProdId(Long prodId);

	public int queryPaymentByProdId(Long prodId);

	/**
	 * 根据购物车ID查询业务动作信息
	 * 
	 * @param olId
	 * @return
	 * 
	 */
	public List<BusiOrder> getBusiOrdersByOlId(String olId);

	/**
	 * 根据业务动作ID查询服务信息变动时的基本信息
	 * 
	 * @param boId
	 * @return
	 * 
	 */
	public BoServOrder findBoServOrderByBoId(Long boId);

	/**
	 * 根据boId查询出对应产品动作中的prodId
	 * 
	 * @param boId
	 * @return
	 */
	public Long getProdIdByBoId(Long boId);

	/**
	 * 根据产品实例ID查询产品规格信息
	 * 
	 * @param boId
	 * @return
	 * 
	 */
	public OfferProdSpec findProdSpecByProdId(Long prodId);

	public Long selectByAcctCd(String id);

	public List<ProdByCompProdSpec> queryProdByProdSpecByPartyId(Map<String, Object> param);

	public Map<String, Long> selectRoleCdAndOfferRoles(Map<String, Long> param);

	public int compProdRule(Map<String, Object> param);

	public String queryAccNumByTerminalCode(String terminalCode);

	public Map<String, Object> queryPhoneNumberInfoByAnId(Map<String, Object> param);

	public List<Map<String, String>> queryStaffInfoByStaffName(String staffName);

	public List<Map<String, String>> queryStaffInfoByStaffNumber(String staffNumber);

	/**
	 * 根据手机号码查询员工
	 * 
	 * @param accNbr
	 * @return
	 */
	public List<Map<String, Object>> getClerkId(String accNbr);

	/**
	 * CRM电子签名日志记录
	 * 
	 * @param map
	 * @param flag
	 * @return
	 */
	public void insertReceiptPringLog(Map<String, String> map);

	/**
	 * 查询该条日志记录是否存在
	 * 
	 * @param map
	 * @param coNbr
	 * @return
	 */
	public int isRPLogExist(String coNbr);

	public int judgeCoupon2OfferSpec(String offer_spec_id, String couponId);

	public List<Map<String, String>> qryPartyInfo(Map<String, Object> param);

	public Map<String, Object> qryPartyManager(String identityNum);

	public Map<String, Object> getBrandLevelDetail(Map<String, Object> param);

	public Map queryCustProfiles(Long partyId);

	public Map queryIdentifyList(Long partyId);

	/**
	 * 查询渠道Id
	 * 
	 * @param parentOrg
	 * @return
	 * @author CHENJUNJIE
	 */
	public String getChannelId(String parentOrg);

	/**
	 * 得到PartyIdSeq序列的值
	 * 
	 * @param
	 * @return
	 * @author CHENJUNJIE
	 */
	public long getPartyIdSeq();

	/**
	 * 插入CRM.PARTY
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void insertCrmParty(Map<String, String> map);

	/**
	 * 插入intf.intf_party
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void insertIntfParty(Map<String, String> map);

	/**
	 * 代理商/网点更新删除渠道
	 * 
	 * @param name
	 * @return
	 * @author CHENJUNJIE
	 */
	public void deleteChannel(String partyId);

	/**
	 * 得到crm.seq_channel序列的值
	 * 
	 * @param
	 * @return
	 * @author CHENJUNJIE
	 */
	public long getChannelIdSeq();

	/**
	 * 插入crm.channel
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void insertCrmChannel(Map<String, String> map);

	/**
	 * 更新crm.channel
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void updateCrmChannel(Map<String, String> map);

	/**
	 * 龙厅:网点更新渠道
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void storeUpdateChannel(Map<String, String> map);

	/**
	 * 龙厅:网点更新删除渠道
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void storeDeleteChannel(String channelId);

	/**
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void insertCrmGisParty(Map<String, String> map);

	/**
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void updateCrmGisParty(Map<String, String> map);

	/**
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void delUpdateCrmGisParty(Map<String, String> map);

	public List<Map<String, Object>> qryOrderSimpleInfoByOlId(Map<String, Object> param);

	/**
	 * 查詢客戶类型
	 * 
	 * @param partyId
	 * @return
	 * @author ZHANGC
	 */
	public Map queryCustSegment(Long partyId);

	/**
	 * 查詢客戶品牌
	 * 
	 * @param partyId
	 * @return
	 * @author ZHANGC
	 */
	public Map queryCustBrand(Long partyId);

	/**
	 * 查詢客戶等级
	 * 
	 * @param partyId
	 * @return
	 * @author ZHANGC
	 */
	public Map queryCustLevel(Long partyId);

	/**
	 * 根据状态编码 查询状态
	 * 
	 * @param statusCd
	 * @return
	 * @author ZHANGC
	 */
	public InstStatus queryInstStatusByCd(String statusCd);

	public Map<String, Object> qryIfPkByPartyId(Map<String, String> param);

	public List<ProdServRela> queryProdServRelas(int servSpecId);

	public OfferProdComp queryOfferProdComp(Long compProdId);

	public void updateOrInsertAgent2prm(Map<String, Object> param);

	public void updateOrInsertChannelForCrm(Map<String, Object> param);

	/**
	 * 算费后查询需要展示的信息
	 * 
	 * @param ol_id
	 * @return
	 * @author ZHANGC
	 */
	public List queryChargeInfo(Long ol_id);

	/**
	 * 查询销售品的规格属性
	 * 
	 * @param offerSpecId
	 * @return
	 * @author ZHANGC
	 */
	public List<String> queryOfferSpecAttr(Long offerSpecId);

	public String queryOfferSpecValueParam(Long offerSpecId);

	public Map<String, Object> getChannelIdByPartyId(Map<String, Object> param);

	public Map<String, Object> getAccessNumberByUimNo(Map<String, Object> param);

	/**
	 * 通过销售品规格和产品规格查询销售品连带的附属销售品
	 */
	public List<Map<String, Object>> queryAttachOfferByProd(Map<String, Object> param);

	public List<Map<String, Object>> getUserZJInfoByAccessNumber(Map<String, Object> param);

	public void updateOrInsertGisParty(Map<String, Object> param);

	public List<Map<String, Object>> getStatusList(Long prod_id);

	public List<Map<String, Object>> getAcctList(Long prod_id);

	public List<Map<String, Object>> getManagerList(Long prod_id);

	public List<Map<String, Object>> getManagerToList(Long owner_id);

	public List<Map<String, Object>> getDevList(Long prod_id);

	public List<Map<String, Object>> getDevNameList(Long party_id);

	public List<Map<String, Object>> getDevTelList(Long party_id);

	public List<Map<String, Object>> getDevOrgIdList(Long party_id);

	public List<Map<String, Object>> getAcctItemList(Long acct_id);

	public List<Map<String, Object>> getTelList(Long acct_id);

	public List<Map<String, Object>> getWxList(Long prod_id);

	public boolean updateOrInsertCepChannelFromPrmToCrm(Map<String, Object> param);

	public Map<String, Object> selectPartyInfoFromSmParty(Map<String, Object> param);

	public List<Map<String, Object>> checkDeviceIdInLogin(Map<String, Object> param);

	public boolean updatePadPwdInLogin(Map<String, Object> param);

	public List<Map<String, Object>> checkSnPwd4SelectChannelInfoByPartyId(Map<String, Object> param);

	public List<Map<String, Object>> checkSnPwd4SelectStaffInfoByStaffNumber(Map<String, Object> param);

	public List<Map<String, Object>> checkSnPwdInLogin(Map<String, Object> param);

	public List<Map<String, Object>> getStaffCodeAndStaffName(Map<String, Object> param);

	public boolean insertPadPwdLog(Map<String, Object> param);

	public boolean insertSmsWaitSendCrmSomeInfo(Map<String, Object> param);

	public List<Map<String, Object>> transmitRandom4SelectStaffInfoByDeviceId(Map<String, Object> param);

	public String oldCGFlag(String materialId);

	public OfferSpecParam queryOfferSpecParam(String offerSpecParamId);

	public Map<String, Object> queryMainOfferSpecNameAndIdByOlId(Map<String, Object> param);

	public int queryIfPk(Long partyId);

	public Long queryOfferSpecIdByAccNbr(String accNbr);

	public List<Map<String, Object>> queryUnusedFNSInfo(Map<String, Object> param);

	public String getOlNbrByOlId(long olId);

	/**
	 * 根据 客户编号 查询 客户Id
	 * 
	 * @param groupSeq
	 * @return
	 */
	public String getPartyIdByGroupSeq(String groupSeq);

	/**
	 * 根据 客户Id 查询 客户编号
	 * 
	 * @param PartyId
	 * @return
	 */
	public String getGroupSeqByPartyId(String partyId);

	/**
	 * 根据ProdSpecId查询anTypeCd
	 * 
	 * @param prodSpecId
	 * @param isPrimary
	 * @return
	 */
	public String getAnTypeCdByProdSpecId(String prodSpecId, String isPrimary);

	public void insertBServActivate(ServActivate bServActivate);

	public void insertBServActivatPps(ServActivatePps bServActivatePps);

	/**
	 * 根据bcdCode查找PayIndentId
	 * 
	 * @param bcdCode
	 * @return
	 */
	public String getPayIndentIdByBcdCode(String bcdCode);

	/**
	 * 把PayIndentId和bcdCode的对应关系存到表unityPay_sr
	 * 
	 * @param map
	 * @return
	 */
	public void savePayIndentId(Map<String, String> map);

	/**
	 * 通过prodId查询是否加入组合产品
	 * 
	 * @param prodId
	 */
	public int queryIfJoinCompProdByProdId(Long prodId);

	public List<Long> queryCategoryNodeId(Long offerSpecId);

	public Long queryIfRootNode(Long categoryNodeId);

	public Map<String, Object> queryUimNum(String phoneNumber);

	public List<Map<String, Object>> getPartyIdentityList(String partyId);

	public String getTmlCodeByPhoneNumber(String phoneNumber);

	public Map<String, Object> getPartyPW(String partyId);

	public void indentItemPayIntf(IndentItemPay indentItemPay);

	public int getPartyNumberCount(String accessNumber);

	public int ifImportantPartyByPartyId(Long partyId);

	public String getPayItemId();

	public String getItemTypeByBcdCode(String bcdCode);

	public List<String> queryCardinfoByAcct(String accessNumber);

	/**
	 * 根据prodId查询非主接入号
	 * 
	 * @param prodId
	 * @return
	 */
	public String getSecondAccNbrByProdId(String prodId);

	public String getOfferOrProdSpecIdByBoId(String boId);

	public String getNewOlIdByOlIdForPayIndentId(String olId);

	public String getInterfaceIdBySystemId(String systemId);

	public List<AttachOfferInfoDto> queryAttachOfferSpecBySpec(Map<String, Object> param);

	/**
	 * 根据员工业务权限，查询业务权限信息
	 * 
	 * @param <Type>
	 * 
	 * @param statementName
	 *            所要查询业务对象所对应的IBATIS查询配置
	 * @param param
	 *            查询条件
	 * @return map 返回对象结果集
	 */
	<Type> List<Type> querySubBusiObjectLimit(String statementName, Map<String, Object> param);

	/**
	 * 根据员工业务权限，查询业务权限信息
	 * 
	 * @param <Type>
	 * 
	 * @param statementName
	 *            所要查询业务对象所对应的IBATIS查询配置
	 * @param param
	 *            查询条件
	 * @return map 返回结果集，键-值：onlyHandle：只能操作,canHandle:能操作,canNotHandle:不能操作
	 */
	<Type> Map<String, List<Type>> queryBusiObjectLimit(String statementName, Map<String, Object> param);

	/**
	 * 根据渠道ID获取渠道规格ID
	 * 
	 * @param channelId
	 * @return
	 */
	String queryChannelSpecByChannelId(Integer channelId);

	public void insertTableInfoPayInfoListForOrderSubmit(Map<String, Object> payInfoList);

	public int getRenewOfferSpecAttr(Long offerSpecId);

	public String getAccessNumberByProdId(Long prodId);

	public String getOfferSpecSummary(Long offerSpecId);

	public List<Long> getPartyIdByIdentityNum(String identifyValue);

	public void updateOrInsertAmaLinkman(List<LinkManInfo> linkmanList);

	public int isYKSXByPartyId(Long partyId);

	public int isIndividualCustByPartyId(Long partyId);

	public int getRunOrderByPartyId(Long partyId);

	public int getOwnCDMANumByPartyId(Long partyId);

	public List<OfferServItemDto> queryOfferServNotInvalid(Long offerId);

	public void updateOrInsertAbm2crmProvince(Map<String, Object> param);

	public Map<String, Object> queryOfferProdInfoByAccessNumber(String phoneNumber);

	/**
	 * 根据prodId取得prodStatus
	 * 
	 * @param prodId
	 * @return
	 */
	public Long getProdStatusByProdId(String prodId);

	public List<ServSpecDto> queryOrderServSpecs(Map<String, Object> params);

	public List<ServSpecDto> queryOptionServSpecs(Map<String, Object> params);

	public Map<String, Object> isHaveInOffer(Map<String, Object> req);

	public List<ServParam> queryServItemsByServSpecIdAndProdId(Map<String, Object> temp);

	public List<ServParam> queryItemSpecsByItemSpecIdAndServSpecId(Map<String, Object> temp);

	public List<DiscreateValue> queryDiscreateValuesByItemSpecId(Long id);

	public List<Long> querySservItemsByServSpecId(Long servSpecId);

	/**
	 * 根据offerSpecId查询offerSpecName
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public String getOfferSpecNameByOfferSpecId(String offerSpecId);

	public Map<String, Object> qryPhoneNumberInfoByAccessNumber(String accessNumber);

	/**
	 * 根据主接入号查询partyId
	 * 
	 * @param accNbr
	 * @return
	 */
	public Long getPartyIdByAccNbr(String accNbr);

	public Map<String, Object> findOfferProdComp(Map<String, Object> param);

	public String checkOrderRouls(Element order);

	public void channelInfoGoDown(Map<String, Object> map);

	public boolean yesOrNoAliveInOfferSpecRoles(Map<String, Object> map);

	public List<OfferIntf> queryOfferInstByProdId(Long prodId);

	public List<OfferInfoKF> queryOrderOffersByProdId(Long prodId);

	public List<OfferInfoKF> queryOptionOfferSpecsByProdId(Map<String, Object> params);

	public List<OfferParamKF> queryOfferParamsByOfferSpecId(Long offerSpecId);

	public List<OfferParamKF> queryOfferParamsByProdIdAndOfferSpecId(Map<String, Object> params);

	public List<ServSpecDto> queryServOfferRelaByOfferSpecId(Long offerSpecId);

	public String queryOfferSpecParamIdByItemSpecId(Map<String, Object> map);

	public Integer queryProductSpec2CategroyNodeByProdSpecId(Integer prodSpecId);

	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId);

	public Map<String, Object> getAuditingTicketInfoByOlId(String olId);

	public Integer queryCountPS2CNByAccNbr(String accNbr);

	public Integer queryWlaneLogByAccNbr(String accNbr);

	public Integer queryCountContinueOrderOfferByAccNbr(String accNbr);

	public List<Map<String, Object>> queryCurrentPPInfosByAccNbr(String accNbr);

	public List<Map<String, Object>> queryContinuePPInfosByAccNbr(String accNbr);

	public List<Map<String, Object>> queryCurrentPPInfosFixedByAccNbr(String accNbr);

	public List<Map<String, Object>> queryContinuePPInfosFixedByAccNbr(String accNbr);

	public List<OfferParam> queryOfferParamByOfferId(Long offerId);

	public List<CommonOfferProdDto> queryAllChildCompProd(Map<String, Object> param);

	/**
	 * 根据 payIndentId 来判断是否代理商批开
	 * 
	 * @param payIndentId
	 * @return
	 */
	public String isPKagent(String payIndentId);

	/**
	 * 取得SEQ_INTF_COMMON的序列值
	 * 
	 * @return
	 */
	public String getIntfCommonSeq();

	/**
	 * 把调用CrmWebService的请求信息存到表中
	 */
	public void saveRequestInfo(Map<String, Object> map);

	/**
	 * 把调用CrmWebService的返回信息存到表中
	 */
	public void saveResponseInfo(Map<String, Object> map);

	public boolean yesOrNoNeedAddCoupon(Map<String, Object> param);

	public String ifCompProdByProdSpecId(Integer prodSpecId);

	public List<Map<String, Object>> queryFNSInfoByOfferSpecId(Long offerSpecId);

	public Integer ifRightPartyGradeByCustTypeAndPartyGrade(Map<String, Object> params);

	public String queryOlIdByOlNbr(String olNbr);

	/**
	 * 根据partyId查询partyName
	 */
	public String getPartyNameByPartyId(Long partyId);

	/**
	 * 根据terminalCode查询partyId
	 */
	public String getPartyIdByTerminalCode(String terminalCode);

	/**
	 * 根据prodId查询OfferList
	 * 
	 * @param prodId
	 * @return
	 */
	public List<Map<String, Object>> getOfferListByProdId(String prodId);

	public Long getPartyIdByIdentifyNum(String accNbr);

	/**
	 * 根据prodId查询partyId
	 * 
	 * @param prodId
	 * @return
	 */
	public Long getPartyIdByProdId(Long prodId);

	public boolean checkRelaSub(Map<String, String> param);

	public List queryOfferSpecIdByProdId(String prodId);

	public int checkContinueOrderPPInfo(Map<String, Object> param);

	public List<OfferSpecDto> queryOfferSpecsByDZQD();

	public boolean queryProdSpec2BoActionTypeCdBYprodAndAction(Map<String, Object> param);

	public int queryIfWLANByOfferSpecId(Long offerSpecId);

	public Map<String, Object> selectStaffInofFromPadPwdStaff(String staffNumber);

	public void updatePhoneNumberStatusCdByYuyue(String phoneNumber);

	public String gjmyYesOrNoRule(Map<String, Object> param);

	public Map<String, Object> queryAccountInfo(String prodId);

	public Long queryCurrentOfferSpecIdKFByProdId(Long prodId);

	public String getPostCodeByPartyId(Long partyId);

	public String qryDeviceNumberStatusCd(String anId);

	/**
	 * 根据抵扣券号查找bo_id
	 * 
	 * @return
	 */
	public Map<String, Object> queryAuditingTicketBusiInfo(Map<String, Object> param);

	/**
	 * 根据bo_id查找ol_id
	 * 
	 * @return
	 */
	public String queryOlIdByBoId(Map<String, Object> param);

	/**
	 * 根据payIndentId查询cycleId（只针对营业的表，不包括资源等）
	 * 
	 * @param payIndentId
	 * @return
	 */
	public String queryCycleByPayId(String payIndentId);

	/**
	 * 根据auditTicketCd得到券的价值属性
	 * 
	 * @param auditTicketCd
	 * @return
	 */
	public String getChargeByTicketCd(String auditTicketCd);

	public boolean qryAccessNumberIsOk(String accessNumber);

	public Map<String, Object> queryDefaultValueByMainOfferSpecId(String offerSpecId);

	/**
	 * 通过OlId查询得到PayIndentId
	 * 
	 * @param olId
	 * @return
	 */
	public String getPayIndentIdByOlId(String olId);

	public boolean checkIsExistsMailAddressId(String mailAddressId);

	/**
	 * 通过prodStatusCd得到相应的产品状态名称
	 * @param prodStatusCd
	 * @return
	 */
	public String getProdStatusNameByCd(Long prodStatusCd);

	/**
	 * 通过抵扣券编码的到发放该抵扣券的channel
	 * @param ticketCd
	 * @return
	 */
	public String getChannelIdByTicketCd(String ticketCd);

	/**
	 * 存储抵扣券兑换时传给统一支付的数据
	 * @param map
	 */
	public void saveTicketCd2terminal(Map<String, Object> map);

	/**
	 * 通过抵扣券编码查询抵扣券Id
	 * @param autitTicketCd
	 * @return
	 */
	public String getTicketIdByCd(String autitTicketCd);

	public List<InventoryStatisticsEntity> getInventoryStatistics(InventoryStatisticsEntityInputBean parameterObject);

	/**
	 * 得到还没有对过账的有价卡
	 * @return
	 */
	public List<Map<String, Object>> getUnCheckedCardInfo();

	/**
	 * 更新有价卡对账状态
	 */
	public void updaCradCheckStatus(Map<String, Object> map);

	/**
	 * 更新有价卡对账补发的结果
	 */
	public void updaCradCheckResult(Map<String, Object> map);

	/**
	 * 有价卡对账 查询是否这笔费用已经传给统一支付
	 * @return
	 */
	public String ifPayIndentIdExists(Map<String, Object> map);

	/**
	 * 判断UIM卡的渠道
	 * @param map
	 * @return
	 */
	public boolean checkUIMChannelId(Map<String, Object> map);

	/**
	 * 判断是不是集团集约卡
	 * @param map
	 * @return
	 */
	public boolean checkGoupUIM(Map<String, Object> map);

	/**
	 * 获取 bcd 编码
	 * @return
	 */
	public List<BcdCodeEntity> getBcdCode(BcdCodeEntityInputBean parameterObject);

	/**
	 * 获取组合产品的主销售品
	 * @return
	 */
	public String getOfferProdCompMainProd(Long compProdId);

	/**
	 * 删除crmwebservice的日志表intf_request_log中的信息
	 */
	public void deleteCrmRequest(String id);

	/**
	 * 根据payindentId得到实际支付的值
	 * @param payIndentId
	 * @return
	 */
	public String getChargeByPayId(String payIndentId);

	/**
	 * 判断UIM卡和渠道的仓库一致
	 * @param map
	 * @return
	 */

	public boolean checkUIMStore(Map<String, Object> map);

	/**
	 * 记录调用营销资源出入库接口的参数
	 * @param map
	 */
	public void saveSRInOut(Map<String, Object> map);

	/**
	 * 根据规格Id查询规格类型
	 * @return
	 */
	public String getOfferTypeCdByOfferSpecId(String offerSpecId);

	/**
	 * 根据 auditTicketCd 查询 抵扣券的面值
	 * @param auditTicketCd
	 * @return
	 */
	public String getFaceValueByTicketCd(String auditTicketCd);

	/**
	 * 根据银行编码查询需要插入的日志表名称
	 * @param bankCode
	 * @return
	 */
	public List<BankTableEntity> getBankTable(String bankCode);

	/**
	 * 插入冻结数据
	 * @param bankCode
	 * @return
	 */
	public void insertBankFreeze(String sql);

	/**
	 * 判断冻结信息是否存在
	 * @param map
	 * @return
	 */
	public boolean checkBankFreeze(String sql);

	/**
	 * 银行解冻
	 * @param no
	 * @return
	 */
	public boolean updateBankFreeze(String sql);

	/**
	 * 根据 offerSpecId查相关费用项
	 * @param offerSpecId
	 * @return
	 */
	public List queryChargeInfoBySpec(String offerSpecId);

	/**
	 * 查询订单提交json报文
	 * @param olId
	 */
	public String getRequestInfo(String olId);

	/**
	 * 根据partyId查询prodid
	 * @param partyId
	 * @return
	 */
	Long getProdidByAccNbr(String accNbr);

	Long getGxProdItemIdByProdid(Long prodid);

	/**
	* 保存时间日志
	* @param map
	*/
	public void saveRequestTime(Map<String, Object> map);

	/**
	 * 修改时间日志
	 * @param map
	 */
	public void updateRequestTime(Map<String, Object> map);

	public Long getOfferItemByAccNum(String accessNumber);

	/**
	 * 获得标示
	 * @param keyflag
	 * @return
	 */
	public String getFlag(String keyflag);

	public String getPartyIdByCard(Map<String, Object> map);

	public List<Map<String, Object>> getAserByProd(Long prodidByPartyId);

	public String getOfferSpecidByProdId(Long prodidByPartyId);

	public String getSpecNameByProdId(Long prodId);

	public String getOffersByProdId(String valueOf);

	public String getUmiCardByProdId(String valueOf);

	public List<Map<String, Object>> getStasByProdId(String valueOf);

	public List<Map<String, Object>> getExchsByProdId(Long prodId);

	public String getAddressByProdId(Long prodId);

	public String getNetAccountByProdId(Long prodId);

	public String getValidStrByPartyId(Long partyid);

	public Long getPrepayFlagBySpecid(Long offerSpecId);

	public List<Map<String, Object>> getAccMailMap(Long acctId);

	/**
	* 实名制客户资料传存时间 获取序列
	* @return
	*/
	public String getIntfTimeCommonSeq();

	public String getGimsiByProdid(Long prodId);

	public String getIndustryClasscdByPartyId(Long partyId);

	public String getPartyAddByProdId(Long prodId);

	public String getCimsiByProdid(Long prodId);

	public String getEsnByProdid(Long prodId);

	public String getCtfRuleIdByOCId(Long offerSpecId, Long couponId);

	public boolean qryAccessNumberIsZt(Map<String, Object> map);

	public List<AttachOfferDto> queryAttachOfferByProdForPad(Long prodId);

	public List queryFreeOfBank(Map<String, Object> map);

	public List getSaComponentInfo(String serviceId);

	/**保存 拼装成的XML报文 huzx20131012
	 * 并修改状态为 非  T 临时状态
	 * 
	 * */
	public void saveXMLInfo(Map<String, Object> map);

	/**通用查询方法 huzx20131016
	 * */
	public List<Map<String, Object>> qrySqlById(String Sql, Map<String, Object> map);

	public Map<String, Object> qrySqlToMapById(String Sql, Map<String, Object> map);

	/**清空存放PROID等信息的表 huzx20131016
	 * */
	public void delSoInstData(String proId);

	/**清空存放报文信息的表 huzx20131016
	 * */
	public void delIntfToBillingMsg(String proId);

	/**转存已经处理完成的客户信息到历史表 huzx20131016
	 * */

	public void saveSoInstDataToHis(String proId);

	/**转存已经处理完成的客户信息报文到历史表 huzx20131016
	 * */
	public void saveIntfToBillingMsgToHis(String proId);

	/**修改存放初始客户信息的soInstData表的状态 huzx20131016
	 *  W 待处理,N 正在处理，C 处理完成，F 处理出错
	 * */
	public void updateSoInstDataSt(String proid, String state);

	/**修改存放报文数据的intfToBillingMsg表的状态 huzx20131016
	 *  T 临时状态，  W 待处理,N 正在处理，C 处理完成，F 发送计费处理出错
	 * */
	public void updateIntfToBillingMsgSt(String proid, String state);

	/**
	 * 监听方法，取得监听数据
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public List<Map> instDateListBySt();

	public List<Map> Intf2BillingMsgListBySt(Map<String, Object> param);

	public String insertPrm2CmsWt(Map<String, Object> param);

	public String insertPrm2CmsDls(Map<String, Object> param);

	public Boolean checkIfIdentityNum(String identityNumValue);

	public int queryCountProd(String identityNumValue);

	public int getIfpkByProd(String prodId);

	public boolean checkIsExistsParty(String custId);

	public List<Map<String, Object>> queryTaxPayer(String custId);

	public Long getStaffIdByDbid(String dbid);

	public Long getChannelIdByStaffId(Long staffId);

	public Long getStaffIdByAgentNum(String staffNumber);

	public String findStaffNumByStaffId(Long staffid);

	public Long getChannelIdByStaffCode(String staffCode);

	public int qryUsefulOfferNumByAccnum(String accNbr);

	public boolean isLocalIvpn(Long prodId);

	public List<Map<String, Object>> getCompLocalIvpns(Long prodId);

	public Map<String, Object> getIvpnInfos(Long prodId);
	public Map<String, Object> getIccIdByProdId(Long prodId,String itemSpecId);

	/**修改接口表的csip_c2u_events_esb_pcrf表的状态 huzx20131225
	 *  W 待处理,R正在处理，WS待发送，S正在发送
	 * */
	public void updateC2uPCRFSt(String acchiveId, String state);

	/**清空存放PROID等信息的表 huzx20131016
	 * */
	public void delC2uPCRFData(String acchiveId);

	/**转存已经处理完成的客户信息到历史表 huzx20131016
	 * */

	public void saveC2uPCRFToHis(List<Map<String, Object>> AcchiveList, String acchiveId);

	/**转存已经处理完成的客户信息到历史表 huzx20131016
	 * */
	public void saveC2uPCRF2ESBERRToHis(List<Map<String, Object>> AcchiveList, String acchiveId);

	/**转存已经处理完成的客户信息到历史表 huzx20131016
	 * */
	public void saveC2uPCRFNO4GToHis(List<Map<String, Object>> AcchiveList, String acchiveId);

	public List<Map> csipPCRFListByst(Map<String, Object> param);

	/**查询主副卡关系 huzx20131231
	 * */
	public List<Map<String, Object>> getProdCompRelaRoleCd(String boId);

	/**查询主卡电话号码 huzx20131231
	 * */
	public String getAccNumByCompProdId(String compProdId);

	/**取得PCRF流水Seq huzx20140122
	 * */
	public String getPCRFTransactionId();

	public Map<String, Object> queryOrderList(String orderId);

	public Long getDevNumIdByAccNum(String accNum);

	public Long getValidateYHParams(Map<String, Object> paraMap);

	public Long getValidateYHOffer(Map<String, Object> offerMap);

	public String getLteImsiByProdid(Long prodId);

	public void saveJSONObject(String jsonObjectStr);

	public void updateJSONObject(String resultStr);

	public String querySpeedValue(Long prodSpecId);

	public Map<String, Object> getProdSmsByProdId(String prodid);
	/**
     * 根据接入号获取产品信息
     * @param accNbr
     * @since 2015-09-26
     */
	public Map<String, Object> getProdInfoByAccNbr(String accNbr);
	
	/**
     * 根据UIM卡号获取物品相关信息
     * @param accNbr
     * @since 2015-09-29
     */
	public Map<String, Object> getCouponInfoByTerminalCode(String terminalCode);
	
	/**
     * 根据UIM卡号获取物品相关信息
     * @param accNbr
     * @since 2015-09-29
     */
	public Map<String, Object> getBasicCouponInfoByTerminalCode(String terminalCode);
	
	/**
     * 根据产品ID获取terminalCode
     * @param accNbr
     * @since 2015-09-29
     */
	public Map<String, Object> queryTerminalCodeByProdId(String prodId);
	
	/**
     * 根据staffCode获取staffId
     * @param accNbr
     * @since 2015-09-28
     */
	public Map<String, Object> getStaffIdByStaffCode(String staffCode);
	
	/**
     * 根据接入号获取产品信息
     * @param accNbr
     * @since 2015-09-26
     */
	public Map<String, Object> getDevInfoByCode(String devCode);

	public boolean isUimBak(String prodid);

	public boolean getIsOrderOnWay(Map<String, Object> map);

	public String findTelphoneByCardno(String terminalCode);

	public String findTelphoneByDiscard(String terminalCode);

	public boolean isExistCardByProdId(Map<String, Object> map);

	public Map<String, Object> getImsiInfoByBillingNo(String billingNo);

	public List<Map<String, Object>> getBillingCardRelation(String billingNo);
	
	/**
	 * 根据设备号查询终端信息
	 * @param devCode
	 * @return
	 */
	public List<Map<String, Object>> getDevNumIdByDevCode(String devCode);
	

	/**
	 * 根据用户id查询必选的销售品
	 * @param prodId
	 */
	public List<Long> queryMustSelOfferByProd(Long prodId);

	public List<Map<String, Object>> queryTableSpace();

	public List<Map<String, Object>> queryUseSpaceNotSysLob(String tableSpaceName);

	public List<Map<String, Object>> queryUseSpaceSysLob(String tableSpaceName);

	public List<Map<String, Object>> queryCrmLockInfo();

	public Long queryDBSessionInfo();

	public boolean saveBenzBusiOrder(Map<String, Object> result);

	public void saveBenzBusiOrderSub(Map<String, Object> resultSub);

	public boolean isBenzOfferServUser(String accNbr);

	public List<Map<String, Object>> getProdItemsByParam(Map<String, Object> param);

	public List<Map<String, Object>> getCustClassInfoByCustId(String custId);

	public String getCustIdByAccNum(String accessNumber);

	public boolean qryProdOrderIsZtByOrderTypes(Map<String, Object> map);

	public Map<String, Object> queryOfferProdStatus(String accessNumber);

	public boolean isFtWifiSystem(String accessNumber);

	public int checkHykdOrder(Map<String, Object> param);

	public Map<String, String> checkCustName(String phone_number, String cust_name);

	public Map<String, Object> getPartyTypeCdByProdId(Long prodidByAccNbr);

	public boolean checkOfferSpecBsns(String id);

	public long checkBankFreeze(Map<String, Object> checkMap);

	public Map<String, Object> qryOrderListByOlId(String olId);

	public String getNbrTypeByAccNbr(String accNbr);

	public List<Map<String, Object>> qryEncryptStrByParam(Map<String, String> param);

	public Map<String, Object> queryOrgByStaffNumber(String staffNumber);

	public Map<String, Object> findOrgByStaffId(Map<String, Object> map);

	public List<Long> queryStaffByProdId(Long prodIdLong);

	public String getIDCardEncryptionVector(String mac);

	public List<Map<String, Object>> getGhAddressTemp();

	public void insertGhAddressUnit(Map<String, Object> param);

	public long queryBussinessOrder(Map<String, Object> mk);

	public long querySeqBussinessOrder();

	public void saveOrUpdateBussinessOrderCheck(Map<String, Object> mk, String str);

	public int isAccNumRealNameparty(Long prodId);

	public String getIntfReqCtrlValue(String string);

	public Map<String, Object> checkParByIdCust(Map<String, Object> m);

	public Map<String, Object> queryOfferProdItemsByProdId(Long prodId);

	public boolean isManyPartyByIDNum(Map<String, Object> m);

	public boolean insertSms(Map<String, Object> map);

	public List<Map<String, Object>> qryBoInfos(Map<String, Object> mv);

	public Long qryChargesByOlid(String ol_id);

	public String queryOfferAddressDesc(Long prodId);

	public String getTmDescription(Long prodId);

	public String getOfferProdTmlName(Long prodId);

	public String getPartyManagerName(Long prodId);
	/**
     * 查询 资费需要关联楼宇折扣的配置表
     * @param offerSpecId
     * @return count 
     */
	public int getOfferSpecAction2Count(String offerSpecId);
   /**
    * 小区政策查询接口(测试)
    * @param buildingId
    * @return
    */
	public String getCommunityPolicy(String buildingId);
	/**
     * 查询续费的开始时间和截止时间
     * @param prodId
     * @param offerSpecId 
     * @return
     */
    public List<Map> getOfferMemberInfo(String prodId);
    /**
	 * 根据serviceid查询BUILDING_ID
	 * @param buildingId
	 * @return
	 */
	public String getComponentBuildingId(String serviceId);
	/**
     * 发展归属部门 
     * @param prodId
     * @return 
     */
	public String getOrganizStaffOrgId(Long prodId);
	/**
     * 业务发展人所属部门 
     * @param prodId
     * @return 
     */
	public String getDevelopmentDepartment(String accessNumber);

	public String getChannelNbrByChannelID(String channelId);

	public int getCmsStaffCodeByStaffCode(String staffCode);

	public String getOfferProdReduOwnerIdByAccNbr(String accNbr);

	public Map<String, Object> getSaopRuleIntfLogByTransactionID(String transactionID);
	/**
	 * 校验号码是否可光改接口
	 * @param accNbr
	 * @return 
	 */
	public List<Map<String, Object>> validatePhoneCanChangeBand(String accNbr);
	/**
	 * 根据主销售品查询打包关系
	 * @param accNbr
	 * @return 
	 */
	public List<Map<String, Object>> queryMainOfferPackageRelation(String offerSpecId);
	/**
	 * 查询产品规格，B号的主销售品、B 号的附属销售品订购
	 * @param accNbr
	 * @return 
	 */
	public List<Map<String, Object>> queryAccNbrInfoList(String accNbr);
	/**
	 * 根据接入号码查询：接入方式
	 * @param accNbr
	 * @return 
	 */
	public Map<String, Object> queryAccessTypeByAccessNumber(String accNbr,String itemSpecIds);
	/**
	 * 查询是否存在组合标识、组合号码
	 * @param accNbr
	 * @return 
	 */
	public List<Map<String, Object>> getProdCompList(String accNbr);
	/**
	 * 根据compProdId查询查询B号的组合号码
	 * @param compProdId
	 * @return 
	 */
	public Map<String, Object> queryCompAccNbrByCompProdId(String compProdId);
	/**
	 * 根据当前销售品获得速率属性及值
	 * @param compProdId
	 * @return 
	 */
	public Map<String, Object> querySpeedInfoByOfferSpecId(String offerSpecId);
	/**
	 * 根据当前销售品查询可换档的主销售品
	 * @param compProdId
	 * @return 
	 */
	public List<Map<String, Object>> queryChangeOffersByOfferSpecId(String offerSpecId);
	/**
	 * 判断当前销售品是否为主销售品
	 * @param offerSpecId
	 * @return 
	 */
	public boolean judgeMainOfferByOfferSpecId(String offerSpecId);
	/**
	 * 判断当前主销售品对应的产品是否为组合产品,获取组合产品信息
	 * @param offerSpecId
	 * @return 
	 */
	public List<Map<String, Object>> queryCompInfoListByOfferSpecId(String offerSpecId);
	/**
	 * 根据产品ID获取组合产品下所有的主销售品
	 * @param offerSpecId
	 * @return 
	 */
	public List<Map<String, Object>> queryCompMainOfferByProdSpecId(Map<String,Object> paraMap);
	/**
	 * 全业务流程:根据olId查询接入号和prodId
	 * @param olId
	 * @return 
	 */
	public Map<String,Object> getProdInfoByAccessNuber(String olId);
	/**
	 * 根据offerSpecId 查询服务Id
	 * @param olId
	 * @return 
	 */
	public Map<String,Object> getServInfoByOfferSpecId(String offerSpecId);
	/**
	 * 小区折扣查询:手机端返回折扣编号和折扣名称
	 * @param olId
	 * @return 
	 */
	public List<Map<String,Object>> getComBasicInfoByOfferSpecId(String offerSpecId);
	/**
	 * 根据staffCode 查询staffId
	 * @param staffCode
	 * @return 
	 */
	public String queryStaffIdByStaffCode(String staffCode);
	/**
	 * 根据产品号码查询附属销售品，状态为22
	 * @param staffCode
	 * @return 
	 */
	public List<AttachOfferDto> queryAttachOfferInfo(Long prodId);

	/**
	 * 为FTTH 取接入方式
	 * @param prodId
	 * @return
	 */
	public Map<String, Object> getMethForFtth(String prodId);

	public String getProValue(String prodId);

	public Map<String, Object> getaddresForFtth(String prodId);

	public Map<String, Object> getTmlForFtth(String prodId);

	public Map<String, Object> getgetprod2TdIdDelCodeCode(String terminalCode);

	public boolean checkSchool(String channelId);

	public List<Map<String, Object>> queryExchangeByName(String name);

	public List<Map<String, Object>> getchannelByCode(String code);

	public List<Map<String, Object>> queryDeptInfo(String name,String level);

	public List<Map<String, Object>> getOlidByg(String olId);
	public List<Map<String, Object>> getValueByolId(String olId);

	public List<Map<String, Object>> queryChannelsByMap(String staffId);

	public List<Map<String, Object>> checkCinfoByb(String code);

	public Map<String, Object> queryByPartyId(String partyId);

	public List<Map<String, Object>> queryIndicatorsList(String time ,String staffId);

	public List<Map<String, Object>> queryIndicatorsListMouth(String startTime,
			String endTime ,String staffId);

	public List<Map<String, Object>> queryDetailedIndicatorsList(
			String statisticalTime, String startRown, String endRown,
			String staffId,String accessNumber);

	public Map<String, Object> queryIndicatorsNumber(String statisticalTime,
			String staffId,String accessNumber);

	public Map<String, Object> queryEmailByPartyId(String partyId);

	public List<Map<String, Object>> getOlnbrByg(String olId);

	public String getNumByStatus(String specId);
	
	public Map<String, Object> queryIndicatorsMouth1(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth2(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth3(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth10(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth11(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth12(String statisticalTime);

	public Map<String, Object> queryIndicatorsMouth4(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth5(String startTime,
			String endTime, String staffId);

	public Map<String, Object> queryIndicatorsMouth6(String startTime,
			String endTime, String staffId);
	
	public Map<String, Object> queryIndicatorsMouth7(String startTime,
			String endTime, String staffId);
	
	public Map<String, Object> queryIndicatorsMouth8(String startTime,
			String endTime, String staffId);


	public Map<String, Object> queryIndicatorsMouth9(String startTime,
			String endTime, String staffId);
	
	public Map<String, Object> queryIndicators1(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators2(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators3(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators4(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators9(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators5(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators6(String statisticalTime,
			String staffId);
	
	public Map<String, Object> queryIndicators7(String statisticalTime,
			String staffId);
	
	public Map<String, Object> queryIndicators8(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators10(String statisticalTime,
			String staffId);

	public Map<String, Object> queryIndicators11(String statisticalTime,
			String staffId);

	public Map<String, Object> getAddressPreemptedSeq();

	public Map<String, Object> showSchoolName(String bcdCode);

	public List<Map<String, Object>> qryDeptById(String dept_id);

	public List<Map<String, Object>> campusCustomerCampusMark(String subjectId,
			String subjectNameId);

	public List<Map<String, Object>> queryCampusCustomerInformation(String num);

	public void saveSendRecord(Map<String, Object> saveMap);

	public List<Map<String, Object>> queryDetailedIndicatorsListMouth(
			String startTime, String endTime, String startRown , String endRown,
			String staffId,String accessNumber);

	public Map<String, Object> qryKeyByFlag();

	public Map<String, Object> queryIndicatorsNumberMouth(String startTime,
			String endTime, String staffId, String accessNumber);

	public List<Map<String, Object>> queryRewardInfoById(String staffId,
			String stardTime, String endTime,String startNum,String endNum);

	public Map<String, Object> queryRewardSum(String staffId, String stardTime,
			String endTime);

	public Map<String, Object> queryRewardInfo(String rewardId);

	public List<Map<String, Object>> querypartyListBypartyList(String idCard);

	public List<Map<String, Object>> queryProListByProId(String prodId);

	public Map<String, Object> queryOlidByProId(String prodId);

	public Map<String, Object> queryUimCodeByAccessNumber(String accessNumber);

	public List<Map<String, Object>> queryOlIdByAccessNumber(String accessNumber);

	public List<Map<String, Object>> queryOrganizationforScrm(
			String staffNumber, String staffName, String organizationId,
			String ruleNumber);

	public Map<String, Object> queryOlNbrByOlId(String id);

	public List<Map<String, Object>> queryRewardSource(String olNbr);

	public void insertReward(Map<String, String> para);

	public String queryRewardId();

	public Map<String, Object> getFingerInfo(String olId);

	public void insertFingerInfo(Map<String, Object> fingerInfo);

	public List<Map<String, Object>> queryStoreByStaffId(String staffId);

	public List<Map<String, Object>> queryStatusByInstCode(String mktResInstCode);

	public Map<String, Object> getStaffIdBystaffNumber(String staffNumber);

	public List<Map<String, Object>> getEdidInfo();

	public void edidType(String partyId);

	public Map<String, Object> queryIdentityInfoByOlnbr(String olNbr);

	public Map<String, Object> queryProdInfoByAccs(String accessNumber);

	public List<Map<String, Object>> queryDepinfoForScrm(String partyId);

	public List<Map<String, Object>> queryUserdByAccess(String accessNuber);

	public Map<String, Object> queryExtCustOrder(String queryExtCustOrder);

	public String[] findPartyByIdentityPic(String name, String identityNum);

	public List<Map<String, Object>> queryPhoneNoByAliUid(String aliUid);

	public String getParentOrgId(String mailAddressId);

	public void upStarMember(Map<String, Object> parameter);

	public Map<String, Object> queryCaflagByOlnbr(String olNbr);

	public String queryBlocOlidToProOlid(String olId);

	public void insertPartyFlagInfo(Map<String, Object> autonymFlag);

	public String queryBlocOlidToProOlNbr(String olId);

	public String queryBlocOlidToBlocOlNbr(String olNbr);

	public void InsertOutskirts(Map<String, Object> parameter);

	public Map<String, Object> queryOlidIfHave(String olId);

	public void updataOutskirts(Map<String, Object> parameter);

	public Map<String, Object> queryPartyIdByprodId(String prodId);

	public Map<String, Object> queryStaffIdBystaffCode(String staffCode);

	public Map<String, Object> queryOfferspecidBystaffCode(String prodId);

	public Map<String, Object> queryOldcustinfoByPartyId(String partyId);

	public Map<String, Object> queryPartyIdByCardId(String cerdValue);

	public Map<String, Object> getSchoolRole(String staffId);

	public Map<String, Object> queryGroupNumberByGroupId(String olId);

	public List<Map<String, Object>> queryProvenceIdByGroupNum(String olId);

	public Map<String, Object> queryBureauDirectionByPhoneNum(String phoneNumber);

	public List<Map<String, Object>> queryRealNameFlagByIdent(String custInfo);

	public List<Map<String, Object>> queryRealNameFlagByParttId(String custInfo);

	public List<Map<String, Object>> queryRealNameFlagByPhoneAccssnumber(
			String custInfo);

	public Map<String, Object> queryPictureByolId(String olId);

	public Map<String, Object> querygztPictureInfoByolId(String olId);

	public String queryAccIdbyAccCd(String accId);

	public List<Map<String, Object>> desensitizationService(String object);

	public String desensitizationSystemCode(String desensitizationSystemId);

	public void savaDesensitizationLog(Map<String, Object> map);

	public Map<String, Object> queryNewPartyPhotoByOlId(String olId);
	
	public List<Object> queryOfferSpecInfoList(Map<String, Object> param);
	
	public Map<String, Object> getStartLevelByPartyAccNbr(String accNbr);

	public List<Map<String,Object>> queryChannelInfoByIdentityNum(String identityNum);
}
