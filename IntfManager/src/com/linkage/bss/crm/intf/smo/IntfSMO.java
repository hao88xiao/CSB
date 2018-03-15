package com.linkage.bss.crm.intf.smo;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.linkage.bss.crm.cust.dto.ChannelDto;
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
import com.linkage.bss.crm.model.ProdSpec;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.unityPay.IndentItemPay;
import com.linkage.bss.crm.unityPay.IndentItemSync;

public interface IntfSMO {

	/**
	 * 根据接入号码取得产品
	 * 
	 * @param accessNumber
	 * @return
	 */
	public OfferProd getProdByAccessNumber(String accessNumber);

	/**
	 * 初始化参数接口
	 * 
	 * @param id
	 * @return
	 */
	public List<StaticData> initStaticData(String id);
	/**
	 * 初始化参数接口
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryChannelsByMap(String staffId);

	/**
	 * 查询场景模版
	 * 
	 * @return
	 */
	public List<Map<String, Object>> qryOfferModel(Map<String, Object> param);

	/**
	 * 校验用户查询密码
	 * 
	 * @param prodId
	 * @param password
	 * @return -1 标识未设置密码 1 标识是有效的密码 0 标识无效的密码
	 */
	public int isValidProdQryPwd(Long prodId, String password);

	/**
	 * 校验用户业务密码
	 * 
	 * @param prodId
	 * @param password
	 * @return -1 标识未设置密码 1 标识是有效的密码 0 标识无效的密码
	 */
	public int isValidProdBizPwd(Long prodId, String password);

	/**
	 * 校验账户密码
	 * 
	 * @param acctId
	 * @param password
	 * @return -1 标识未设置密码 1 标识是有效的密码 0 标识无效的密码
	 */
	public int isValidAcctPwd(Long acctId, String password);

	/**
	 * 号码归属地查询
	 * 
	 * @param accNbr
	 * @return
	 */
	public Tel2Area queryAccNBRInfo(String accNbr);

	/**
	 * 区号查询
	 * 
	 * @param areaCode
	 * @return
	 */
	public AreaInfo queryAreaInfo(String areaCode, String areaName);

	public List<OperatingOfficeInfo> queryOperatingOfficeInfo(String areaCode, String queryType, String areaName);

	/**
	 * Iccid号反查无线宽带产品号码 单数据卡：用户输入iccid号码，返回产品号 一卡双芯：用户无论输入a卡或b卡的iccid号均返回用户a卡产品号
	 * 
	 * @author zhangying
	 * @param iccid
	 * @return
	 */
	public String queryNbrByIccid(String iccid);

	/**
	 * 亲情号码查询
	 * 
	 * @param accNbrType
	 * 
	 * @param offer_id
	 * @param itemSpecId
	 * @return
	 */
	public Map<String, Object> queryFNSInfo(String accNbr, String accNbrType);

	/**
	 * 电子有价卡批量获取请求
	 * 
	 * @param trade_id
	 * @param sale_time
	 * @param channelId
	 * @param staffCode
	 * @param value_card_type_code
	 * @param value_code
	 * @param apply_num
	 * @param flag
	 * @return
	 */
	public String goodsBatchGet(String trade_id, String sale_time, String channelId, String staffCode,
			String value_card_type_code, String value_code, String apply_num, int flag);

	/**
	 * 取得系统当前时间
	 * 
	 * @return
	 */
	public Date getCurrentTime();

	/**
	 * 销售品产品关联
	 * 
	 * @param objType
	 * @param objId
	 * @return
	 */
	public List<RoleObj> findRoleObjs(Integer objType, Long objId);

	/**
	 * 获取销售品成员角色对象
	 * 
	 * @param offerRoleId
	 * @param objType
	 * @return
	 */
	public RoleObj findRoleObjByOfferRoleIdAndObjType(Long offerRoleId, Integer objType);

	/**
	 * 根据产品ID和销售品规格ID查询订购
	 * 
	 * @param prodId
	 * @param offerSpecId
	 */
	public Offer findOfferByProdIdAndOfferSpecId(Long prodId, Long offerSpecId);

	/**
	 * 成员是产品规格的销售品角色
	 * 
	 * @param offerSpecId
	 * @param prodSpecId
	 * @return
	 */
	public OfferRoles findProdOfferRoles(Long offerSpecId, Long prodSpecId);

	/**
	 * 成员是服务规格的销售品角色
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public List<OfferRoles> findServOfferRoles(Long offerSpecId);

	/**
	 * 获取服务实例信息
	 * 
	 * @param prodId
	 * @param servSpecId
	 * @return
	 */
	public OfferServ findOfferServByProdIdAndServSpecId(Long prodId, Long servSpecId);

	/**
	 * 获取产品属性
	 * 
	 * @param prodId
	 * @param itemSpecId
	 * @return
	 */
	public OfferProdItem findOfferProdItem(Long prodId, Long itemSpecId);

	/**
	 * 获取产品设备信息
	 * 
	 * @param prodId
	 * @param terminalDevSpecId
	 * @return
	 */
	public OfferProd2Td findOfferProd2Td(Long prodId, Long terminalDevSpecId);

	/**
	 * 主接入号码-产品规格对应的接入号码类型
	 * 
	 * @param prodSpecId
	 * @return
	 */
	public ProdSpec2AccessNumType findProdSpec2AccessNumType(Long prodSpecId);

	/**
	 * 非住接入号码-产品规格对应的接入号码类型
	 * 
	 * @param prodSpecId
	 * @return
	 */
	public ProdSpec2AccessNumType findProdSpec2AccessNumType2(Long prodSpecId);

	/**
	 * 根据合同号查询账户信息
	 * 
	 * @param acctCd
	 * @return
	 */
	public Account findAcctByAcctCd(String acctCd);

	/**
	 * 根据接入号码和产品规格查询账户信息
	 * 
	 * @param accessNumber
	 * @param prodSpecId
	 * @return
	 */
	public Account findAcctByAccNbr(String accessNumber, Integer prodSpecId);

	/**
	 * 根据销售品规格ID查找服务规格ID
	 * 
	 * @param id
	 * @return
	 */
	public String findOfferOrService(Long id);

	/**
	 * 根据accId 查找 paymentAcountId
	 * 
	 * @param id
	 * @return
	 */
	public Long selectByAcctId(Long id);

	/**
	 * 根据acctCd 查找acctId
	 * 
	 * @param id
	 * @return
	 */
	public Long selectByAcctCd(String id);

	/**
	 * 根据销售品规格查找服务属性ID
	 * 
	 * @param id
	 * @return
	 */
	public List<Map> findServSpecItem(Long id, String flag);

	/**
	 * 是否已做话补补贴--根据代理商传递物品串码，校验是否做补贴
	 * 
	 * @param coupon_ins_number
	 */
	public Map isSubsidy(String coupon_ins_number);

	/**
	 * 根据prodSpecId得到prodSpec
	 * 
	 * @param prodSpecId
	 */
	public ProdSpec getProdSpecByProdSpecId(Long prodSpecId);

	/**
	 * 查询产品业务密码
	 * 
	 * @param prodId
	 * @return
	 */
	public String queryProdBizPwdByProdId(Long prodId);

	/**
	 * 查询产品查询密码
	 * 
	 * @param prodId
	 * @return
	 */
	public String queryProdQryPwdByProdId(Long prodId);

	/**
	 * 新验证接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */

	public Map<String, Object> newValidateService(String accessNumber, String custName, String cardType, String card);

	/**
	 * 一卡双芯查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	public Map<String, String> isYKSXInfo(String accNbr);

	/**
	 * 根据卡号查询imsi信息
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	public List<String> queryImsiInfoByMdn(Long prodId);

	/**
	 * 国际漫游办理查询接口
	 * 
	 * @author 刘志
	 * @param params
	 * @return
	 */
	public Map<String, Object> checkGlobalroam(Long prodId, Long offerId);

	/**
	 * 订单取消
	 * 
	 * @author zhangying
	 * @param coNbr
	 * @param areaId
	 * @param channelId
	 * @param staffCode
	 * @return
	 */
	public String cancelOrder(String olId, String areaId, String channelId, String staffCode);

	/**
	 * ooRoles拼装角色CD和销售品角色ID查询
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Long> selectRoleCdAndOfferRoles(Map<String, Long> param);

	public List<ProdByCompProdSpec> queryProdByCompProdSpecId(Map<String, Object> param);

	public int compProdRule(String accNum, String offerId);

	public String queryAccNumByTerminalCode(String terminalCode);

	/**
	 * 根据号码查找号码信息
	 * 
	 * @author TERFY
	 * @param anId
	 * @return
	 */
	public Map<String, Object> queryPhoneNumberInfoByAnId(Map<String, Object> param);

	/**
	 * 根据员工名称查找员工信息
	 * 
	 * @param staffName
	 * @return
	 */
	public List<Map<String, String>> queryStaffInfoByStaffName(String staffName);

	/**
	 * 根据员工工号查找员工信息
	 * 
	 * @param staffName
	 * @return
	 */
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
	 * @param coNbrList
	 * @param flag
	 * @return
	 */
	public void addReceiptPringLog(List<String> coNbrList, String flag) throws Exception;

	public int judgeCoupon2OfferSpec(String offer_spec_id, String couponId);

	/**
	 * 合同管理客户查询
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, String>> qryPartyInfo(Map<String, Object> param);

	/**
	 * 合同管理客户经理查询
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> qryPartyManager(String identityNum);

	/**
	 * 客户品牌查询
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBrandLevelDetail(Map<String, Object> param);

	/**
	 * 查询客户联系人等信息
	 * 
	 * @param partyId
	 * @return
	 * @author ZHANGC
	 */
	public Map queryCustProfiles(Long partyId);

	/**
	 * 查詢客戶證件信息
	 * 
	 * @param partyId
	 * @return
	 * @author ZHANGC
	 */
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
	 * 代理商资料同步CRM.PARTY
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synAgentToParty(Map<String, String> map);

	/**
	 * 代理商同步税控
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synAgentToTax(Map<String, String> map);

	/**
	 * 代理商同步ABM
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synAgentToABM(Map<String, String> map);

	/**
	 * 网点资料同步CRM.PARTY
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synBranchToParty(Map<String, String> map);

	/**
	 * 网点同步税控
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synBranchToTax(Map<String, String> map);

	/**
	 * 网点商同步ABM
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synBranchToABM(Map<String, String> map);

	/**
	 * 修改同步税控
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void modifySynTax(Map<String, String> map);

	/**
	 * 修改同步ABM
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void modifySynABM(Map<String, String> map);

	/**
	 * 删除同步税控
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void deleteSynTax(Map<String, String> map);

	/**
	 * 删除同步ABM
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void deleteSynABM(Map<String, String> map);

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
	 * 龙厅:代理商插入渠道
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void agentIntoChannel(Map<String, String> map);

	/**
	 * 龙厅:代理商更新渠道
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void agentUpdateChannel(Map<String, String> map);

	/**
	 * 龙厅:网点插入渠道
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void storeIntoChannel(Map<String, String> map);

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
	 * @param channelId
	 * @return
	 * @author CHENJUNJIE
	 */
	public void storeDeleteChannel(String channelId);

	/**
	 * 非龙厅:同步channel表
	 * 
	 * @param map
	 * @return
	 * @author CHENJUNJIE
	 */
	public void synCrmChannel(Map<String, String> map);

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

	/**
	 * 根据订单ID查询订单状态、类型和名称
	 * 
	 * @param param
	 * @return
	 */
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

	/**
	 * 根据客户ID查找客户是否为批开客户的标记值
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> qryIfPkByPartyId(Map<String, String> param);

	/**
	 * 查询产品服务关系（互斥依赖等）
	 * 
	 * @param servSpecId
	 * @return
	 * @author ZHANGC
	 */
	public List<ProdServRela> queryProdServRelas(int servSpecId);

	/**
	 * 查询组合产品角色
	 * 
	 * @param compProdId
	 * @return
	 * @author ZHANGC
	 */
	public OfferProdComp queryOfferProdComp(Long prodCompId);

	/**
	 * 信息同步agent_2_prm表
	 */
	public void updateOrInsertAgent2prm(Map<String, Object> param);

	/**
	 * 信息同步channel表
	 * 
	 * @return
	 */
	public void updateOrInsertChannelForCrm(Map<String, Object> param);

	/**
	 * 信息同步 GIS_PARTY
	 * 
	 * @param param
	 * @return
	 */
	public void updateOrInsertGisParty(Map<String, Object> param);

	/**
	 * 信息同步 CEP_CHANNEL
	 * 
	 * @param param
	 * @return
	 */
	public boolean updateOrInsertCepChannelFromPrmToCrm(Map<String, Object> param);

	/**
	 * 信息同步 查找组织信息
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectPartyInfoFromSmParty(Map<String, Object> param);

	/**
	 * 根据partyId查找channelId
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getChannelIdByPartyId(Map<String, Object> param);

	/**
	 * 算费
	 * 
	 * @param ol_id
	 * @return
	 * @author ZHANGC
	 */
	public Map computeChargeInfo(Long ol_id);

	/**
	 * 算费之后查询需要展示的信息
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

	/**
	 * 查规格的 费用参数itemSpecId为（20447,20458,20434,20426,20427, 23981,23990,23982,
	 * 23991,23983, 23992,23984,23993,20457, 20439,20584,20438,20585,
	 * 20435,20448,20465,20445,20432,20446,20455,20466,20582,20433,20456,20583）的
	 * 
	 * @param offerSpecId
	 * @return
	 * @author ZHANGC
	 */
	public String queryOfferSpecValueParam(Long offerSpecId);

	/**
	 * 根据uimNo 查询对应号码mdn
	 * 
	 * @author TERFY
	 * @param param
	 * @return
	 */
	public Map<String, Object> getAccessNumberByUimNo(Map<String, Object> param);

	/**
	 * 通过销售品规格和产品规格查询销售品连带的附属销售品
	 */
	public List<Map<String, Object>> queryAttachOfferByProd(Long offerSpecId, Long prodSpecId);

	/**
	 * 根据已有号码查询是否定否租机销售品
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getUserZJInfoByAccessNumber(Map<String, Object> param);

	/**
	 * 设备有效性验证
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> checkDeviceIdInLogin(Map<String, Object> param);

	/**
	 * 随机数入表
	 * 
	 * @param param
	 * @return
	 */
	public boolean updatePadPwdInLogin(Map<String, Object> param);

	/**
	 * 随机数校验
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> checkSnPwdInLogin(Map<String, Object> param);

	public boolean insertPadPwdLog(Map<String, Object> param);

	public List<Map<String, Object>> checkSnPwd4SelectStaffInfoByStaffNumber(Map<String, Object> param);

	public List<Map<String, Object>> checkSnPwd4SelectChannelInfoByPartyId(Map<String, Object> param);

	/**
	 * 设备同步随机数
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> transmitRandom4SelectStaffInfoByDeviceId(Map<String, Object> param);

	public boolean insertSmsWaitSendCrmSomeInfo(Map<String, Object> param);

	public List<Map<String, Object>> getStaffCodeAndStaffName(Map<String, Object> param);

	/**
	 * 返回高额系统信息
	 */
	public Map<String, Object> getHighFeeInfo(Map<String, Object> param);

	/**
	 * 新旧双模卡判断
	 * 
	 * @param materialId
	 * @return
	 * @author ZHANGC
	 */
	public String oldCGFlag(String materialId);

	/**
	 * 查询OfferSpecParam信息
	 */
	public OfferSpecParam queryOfferSpecParam(String offerSpecParamId);

	/**
	 * 根据olId查找对应主销售品规格和名称
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryMainOfferSpecNameAndIdByOlId(Map<String, Object> param);

	/*
	 * 判断是否批开客户
	 */
	public int queryIfPk(Long partyId);

	/** 根据订单id获取购物车流水号 */
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

	/**
	 * 首话单/充值激活接口数据插入
	 * 
	 * @param servActivate
	 */
	public void insertBServActivate(ServActivate servActivate);

	/**
	 * 133充值变套餐接口数据插入
	 * 
	 * @param servActivatePps
	 */
	public void insertBServActivatPps(ServActivatePps servActivatePps);

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
	 * 根据offerSpecId获取销售品目录节点
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public List<Long> queryCategoryNodeId(Long offerSpecId);

	public Long queryIfRootNode(Long categoryNodeId);

	/**
	 * pad获取老uim信息
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public Map<String, Object> queryUimNum(String phoneNumber);

	public List<Map<String, Object>> getPartyIdentityList(String partyId);

	public String getTmlCodeByPhoneNumber(String phoneNumber);

	/**
	 * 封装3.4 已收订单费用传送接口
	 * 
	 * @param xml
	 * @return
	 */
	public String indentItemPayIntf(IndentItemPay indentItemPay);

	/**
	 * 查询用户下已办理的号码数量
	 * 
	 * @param accessNumber
	 * @return
	 */
	public int getPartyNumberCount(String accessNumber);

	/**
	 * 获取客户密码
	 * 
	 * @param partyId
	 * @return
	 */
	public Map<String, Object> getPartyPW(String partyId);

	/**
	 * 判断是否重保客户
	 * 
	 * @param partyId
	 * @return
	 */
	public int ifImportantPartyByPartyId(Long partyId);

	/**
	 * 得到费用明细的ID
	 * 
	 * @return
	 */
	public String getPayItemId();

	/**
	 * 根据资源的实例编码查询费用项
	 * 
	 * @param bcdCode
	 * @return
	 */
	public String getItemTypeByBcdCode(String bcdCode);

	/**
	 * 根据接入号码查询证件号码
	 * 
	 * @param accessNumber
	 * @return
	 */
	public List<String> queryCardinfoByAcct(String accessNumber);

	/**
	 * 根据prodId查询非主接入号
	 * 
	 * @param prodId
	 * @return
	 */
	public String getSecondAccNbrByProdId(String prodId);

	/** 根据boId获取销售品或者产品规格ID */
	public String getOfferOrProdSpecIdByBoId(String boId);

	/** 业务返销根据老olId获取新olId */
	public String getNewOlIdByOlIdForPayIndentId(String olId);

	/** 根据平台编码获取对应统一支付的平台编码 */
	public String getInterfaceIdBySystemId(String systemId);

	/**
	 * 获取可选的附属销售品
	 * 
	 * @param param
	 * @return
	 */
	public List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Map<String, Object> param);

	public void insertTableInfoPayInfoListForOrderSubmit(Map<String, Object> payInfoList);

	/**
	 * 判断是否有续费标志
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public int getRenewOfferSpecAttr(Long offerSpecId);

	/** 根据产品ID获取主接入号 */
	public String getAccessNumberByProdId(Long prodId);

	/**
	 * 获取销售品的描述summary
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public String getOfferSpecSummary(Long offerSpecId);

	/**
	 * 查询客户下C网产品的数量（含在途单）
	 * 
	 * @param partyId
	 * @return
	 */
	public int checkProductNumByPartyId(Long partyId);

	/**
	 * 根据证件号码得到客户id
	 * 
	 * @param identifyValue
	 * @return
	 */
	public Long getPartyIdByIdentityNum(String identifyValue);

	public void updateOrInsertAmaLinkman(List<LinkManInfo> linkmanList);

	/**
	 * 根据销售品实例ID查找非失效状态的服务实例信息
	 * 
	 * @param offerId
	 * @return
	 */
	public List<OfferServItemDto> queryOfferServNotInvalid(Long offerId);

	public void updateOrInsertAbm2crmProvince(Map<String, Object> param);

	public String getPageInfo(String olId, String runFlag, String ifAgreement);

	public Map<String, Object> queryOfferProdInfoByAccessNumber(String phoneNumber);

	/**
	 * 根据prodId取得prodStatus
	 * 
	 * @param prodId
	 * @return
	 */
	public Long getProdStatusByProdId(String prodId);

	/**
	 * 获取产品的服务规格信息
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getServSpecs(Map<String, Object> params);

	/**
	 * 根据销售品规格ID查找客户是否已经订购该产品
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public Map<String, Object> isHaveInOffer(Map<String, Object> req);

	/**
	 * 根据号码查询号码信息
	 * 
	 * @param accessNumber
	 * @return
	 */
	public Map<String, Object> qryPhoneNumberInfoByAccessNumber(String accessNumber);

	/**
	 * 根据offerSpecId查询offerSpecName
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public String getOfferSpecNameByOfferSpecId(String offerSpecId);

	/**
	 * 根据主接入号查询partyId
	 * 
	 * @param accNbr
	 * @return
	 */
	public Long getPartyIdByAccNbr(String accNbr);

	/**
	 * 根据产品ID和产品组合ID查找它们之间的关系信息
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> findOfferProdComp(Map<String, Object> param);

	/**
	 * 在途单校验
	 * 
	 * @param json
	 * @return
	 */
	public String checkOrderRouls(Element order);

	/**
	 * 渠道信息下发
	 * 
	 * @param map
	 * @return
	 */
	public void channelInfoGoDown(Map<String, Object> map);

	/**
	 * 判断所订购的附属销售品是否在主套餐下
	 * 
	 * @param map
	 * @return
	 */
	public boolean yesOrNoAliveInOfferSpecRoles(Map<String, Object> map);

	/**
	 * 根据产品id查询销售品信息
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferIntf> queryOfferInstByProdId(Long prodId);

	/**
	 * 根据产品id查询销售品信息（客服2.0渠道）
	 * 
	 * @param prodId
	 * @param prodSpecId
	 * @return
	 */
	public Map<String, Object> queryOfferInfoByProdId(Long prodId, Integer prodSpecId);

	/**
	 * 根据itemSpecId反查offerSpecParamId
	 * 
	 * @param itemSpecId
	 * @return
	 */
	public String queryOfferSpecParamIdByItemSpecId(Map<String, Object> map);

	/**
	 * 根据产品号码查询附属销售品（已订购）
	 * 
	 * @param prodId
	 * @return
	 */
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId);

	/**
	 * 根据接入号码查询宽带类可续费信息接口
	 * 
	 * @param accNbr
	 * @return
	 */
	public Map<String, Object> queryContinueOrderPPInfoByAccNbr(String accNbr);

	/**
	 * 根据订单号查询抵用券ID和密码
	 * 
	 * @param olId
	 * @return
	 */
	public Map<String, Object> getAuditingTicketInfoByOlId(String olId);

	/**
	 * 根据销售品实例ID查找有效的销售品参数
	 * 
	 * @param offerId
	 * @return
	 */
	public List<OfferParam> queryOfferParamByOfferId(Long offerId);

	/**
	 * 根据客户ID和产品ID查子产品的信息
	 * 
	 * @param param
	 *            [partyId:客户ID,accessNumber:产品号码]
	 * @author 马腾宇 at 2011-08-30
	 * @return
	 */
	public Map<String, Object> queryAllChildCompProd(Map<String, Object> param);

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
	public void saveRequestInfo(String id, String plat, String method, String request, Date requestTime);

	/**
	 * 查询订单提交json报文
	 * @param olId
	 */
	public String getRequestInfo(String olId);

	/**
	 * 把调用CrmWebService的返回信息存到表中
	 */
	public void saveResponseInfo(String id, String plat, String method, String request, Date requestTime,
			String response, Date responseTime, String type, String resultCode);

	/**
	 * 判断销售品是否需要可以和物品关联
	 * 
	 * @return
	 */
	public boolean yesOrNoNeedAddCoupon(Map<String, Object> param);

	/**
	 * 根据产品规格查询是否为组合产品
	 * 
	 * @param prodSpecId
	 * @return
	 */
	public String ifCompProdByProdSpecId(Integer prodSpecId);

	/**
	 * 判断客户等级和客户类型是否匹配
	 * 
	 * @param custType
	 * @param partyGrade
	 * @return
	 */
	public Integer ifRightPartyGradeByCustTypeAndPartyGrade(Map<String, Object> params);

	/**
	 * PRM组织信息同步
	 * 
	 * @param request
	 * @return
	 */
	public String syncDate4Prm2Crm(String request) throws Exception;

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

	/**
	 * 根据identifyNum查询partyId
	 * 
	 * @param accNbr
	 * @return
	 */
	public Long getPartyIdByIdentifyNum(String accNbr);

	/**
	 * 根据prodId查询partyId
	 * 
	 * @param prodId
	 * @return
	 */
	public Long getPartyIdByProdId(Long prodId);

	/**
	 * UIMK预占与释放
	 * 
	 * @param devNumId
	 * @return
	 */
	public Map consoleUimK(String devNumId, String flag);

	/**
	 * 判断2个销售品是否互斥
	 * 
	 * @param param
	 * @return
	 */
	public boolean checkRelaSub(Map<String, String> param);

	/**
	 * 根据互斥给入参添加已有互斥销售品的退订
	 * 
	 * @param request
	 * @return
	 */
	public String changeRequestByCheckRelaSub(Element order) throws Exception;

	/**
	 * 校验宽带续费时动作类型
	 * 
	 * @param accNbr
	 * @param offerSpecId
	 * @param actionType
	 * @return
	 */
	public String checkContinueOrderPPInfo(String accNbr, String offerSpecId, String actionType);

	/**
	 * 查询电子渠道需要的销售品
	 * 
	 * @return
	 */
	public List<OfferSpecDto> queryOfferSpecsByDZQD();

	public boolean queryProdSpec2BoActionTypeCdBYprodAndAction(Map<String, Object> param);

	/**
	 * 根据销售品id判断是否无线宽带号码
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public int queryIfWLANByOfferSpecId(Long offerSpecId);

	/**
	 * 查询员工是否配有安全密钥
	 * 
	 * @param staffNumber
	 * @return
	 */
	public Map<String, Object> selectStaffInofFromPadPwdStaff(String staffNumber);

	/**
	 * 预约号码直接预占
	 * 
	 * @param phoneNumber
	 */
	public void updatePhoneNumberStatusCdByYuyue(String phoneNumber);

	/**
	 * 判断用户是否可以订购国际漫游
	 * 
	 * @param param
	 */
	public String gjmyYesOrNoRule(Map<String, Object> param);

	public Map<String, Object> queryAccountInfo(String prodId);

	/**
	 * 余额查询接口
	 * 
	 * @param accessNumber
	 * @return
	 */
	public Map<String, String> qryAccount(String accessNumber);

	public String qryDeviceNumberStatusCd(String anId);

	/**
	 * 根据客户Id查询邮政编码
	 * 
	 * @param partyId
	 * @return
	 */
	public String getPostCodeByPartyId(Long partyId);

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

	/**
	 * 直接调用业务资源的接口
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public Map queryCodeByNum(String num) throws Exception;

	/**
	 * 根据主销售品规格id查找默认速率
	 * 
	 * @param offerSpecId
	 * @return
	 */
	public Map<String, Object> queryDefaultValueByMainOfferSpecId(String offerSpecId);

	/**
	 * 通过OlId查询得到PayIndentId
	 * 
	 * @param olId
	 * @return
	 */
	public String getPayIndentIdByOlId(String olId);

	/**
	 * 作废购物车和业务动作
	 * 
	 * @param olId
	 */
	public void cancelOrderInfo(long olId) throws Exception;

	/**
	 * 调用统一支付订单费用同步接口，用于重发
	 * @param indentItemSync
	 * @return
	 */
	public Map<String, Object> indentItemSync(IndentItemSync indentItemSync);

	/**
	 * 调用统一支付已收费用同步接口，用于重发
	 * @param indentItemPay
	 * @return
	 */
	public Map<String, Object> indentItemPay(IndentItemPay indentItemPay);

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

	/**
	 * 查找统计
	 * @return
	 */
	public List<InventoryStatisticsEntity> getInventoryStatistics(InventoryStatisticsEntityInputBean parameterObject);
	/**
	 * 3G/4G判断
	 * @return
	 */
	public List<Map<String, Object>> getOlidByg(String olId);
	/**
	 * value
	 * @return
	 */
	public List<Map<String, Object>> getValueByolId(String olId);

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
	  * @param ccParam
	  * @return
	  */
	public boolean checkUIMChannelId(Map<String, Object> map);

	/**
	 * 判断是不是集团集约卡
	 * @param cParam
	 * @return
	 */
	public boolean checkGoupUIM(Map<String, Object> map);

	/**
	 * 获取bcd 编码
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
	* @param ccParam
	* @return
	*/
	public boolean checkUIMStore(Map<String, Object> ccParam);

	/**
	 * 记录调用营销资源出入库接口的参数
	 * @param map
	 */
	public void saveSRInOut(Map<String, Object> map);

	/**
	 * 根据规格Id查询规格类型
	 */
	public String getOfferTypeCdByOfferSpecId(String offerSpecId);

	/**
	 * 根据 auditTicketCd 查询 抵扣券的面值
	 * @param auditTicketCd
	 * @return
	 */
	public String getFaceValueByTicketCd(String auditTicketCd);

	/**
	 * 根据 offerSpecId查相关费用项
	 * @param offerSpecId
	 * @return
	 */
	public List queryChargeInfoBySpec(String offerSpecId);

	/**
	 * 根据银行编码查询需要插入的日志表名称
	 * @param bankCode
	 * @return
	 */
	public List<BankTableEntity> getBankTable(String bankCode);

	/**
	 * 插入冻结数据
	 * @param sql
	 * @return
	 */
	public void insertBankFreeze(String sql);

	/**
	 * 判断冻结信息是否存在
	 * @param no
	 * @param freezeNo 
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
	 * 根据partyId查询prodid
	 * @param partyId
	 * @return
	 */
	Long getProdidByAccNbr(String accNbr);

	Long getGxProdItemIdByProdid(Long prodid);

	public void saveRequestTime(String logId, String methodName, String accessNumber, String identityNum,
			Date requestTime);

	/**
	 * 更新日志时间记录
	 * @param method_name
	 * @param accessNumber
	 * @param identityNum
	 * @param identityNum2 
	 * @param time1
	 * @param time2
	 * @param time3
	 * @param time4
	 */
	public void updateRequestTime(String logId, Date time1, Date time2, Date time3, Date time4);

	/**
	 * 获得标示
	 * @param flagstr
	 * @return
	 */
	public String getFlag(String keyflag);

	/**
	 * 实名制客户资料传存时间 获取序列
	 * @return
	 */
	public String getIntfTimeCommonSeq();

	/**
	 * 根据接入号查询校园标签产品属性
	 * @param accessNumber
	 * @return
	 */
	public Long getOfferItemByAccNum(String accessNumber);

	/**
	 * 
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public String getPartyIdByCard(Integer valueOf, String cerdValue);

	public List<Map<String, Object>> getAserByProd(Long prodidByPartyId);

	public String getOfferSpecidByProdId(Long prodidByPartyId);

	public String getSpecNameByProdId(Long prodId);

	public String getOffersByProdId(String valueOf);

	public String getUmiCardByProdId(String valueOf);

	public List<Map<String, Object>> getStasByProdId(String valueOf);

	public List<Map<String, Object>> getExchsByProdId(Long prodId);

	public String getAddressByProdId(Long prodId);

	public String getNetAccountByProdId(Long prodId);

	public String getValidStrByPartyId(Long long1);

	public Long getPrepayFlagBySpecid(Long offerSpecId);

	public List<Map<String, Object>> getAccMailMap(Long acctId);

	public String getGimsiByProdid(Long prodId);

	public String getIndustryClasscdByPartyId(Long partyId);

	public String getPartyAddByProdId(Long prodId);

	public String getCimsiByProdid(Long prodId);

	public String getEsnByProdid(Long prodId);

	public String getCtfRuleIdByOCId(Long offerSpecId, Long couponId);

	public boolean qryAccessNumberIsZt(Map<String, Object> map);

	public List<AttachOfferDto> queryAttachOfferByProdForPad(Long prodId);

	public List queryFreeOfBank(String freezeNo, String bankCode, String bankName, String serialNumber);

	public List getSaComponentInfo(String serviceId);

	public String insertPrm2CmsWt(Map<String, Object> param);

	public String insertPrm2CmsDls(Map<String, Object> param);

	/**根据PROID取得客户全部信息拼装成报文 huzx20131012
	 * 监听调用此方法
	 * */
	public boolean getInfoByProId(Map map);

	/**根据根据前台数据取得用户数据归档组号，并根据场景拼成报文 huzx20131224
	 * 
	 * */
	public boolean getInfoByCRM(Map map);

	/**
	 * 监听方法，取得监听数据
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public List<Map> instDateListBySt();

	/**
	 * 监听方法，取得监听数据
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public List<Map> csipPCRFListByst(int i);

	/**
	 * 监听方法，取得XML信息表监听数据
	 * @param inProcessNumber 
	 * @param i 
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public List<Map> Intf2BillingMsgListBySt(int i, Integer inProcessNumber);

	/**
	 * 将待处理的数据发送到计费系统
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	public boolean processMsg(Map map);

	public Boolean checkIfIdentityNum(String identityNumValue);

	public int queryCountProd(String identityNumValue);

	public int getIfpkByProd(String prodId);

	public boolean checkIsExistsParty(String value);

	public List<Map<String, Object>> queryTaxPayer(String value);

	public Long getStaffIdByDbid(String dbid);

	public Long getChannelIdByStaffId(Long staffId);

	public Long getStaffIdByAgentNum(String staffNumber);

	public String findStaffNumByStaffId(Long staffid);

	public Long getChannelIdByStaffCode(String staffCode);

	//List<Map> Intf2BillingMsgListBySt();
	public int qryUsefulOfferNumByAccnum(String accNbr);

	public boolean isLocalIvpn(Long prodId);

	public List<Map<String, Object>> getCompLocalIvpns(Long prodId);

	public Map<String, Object> getIvpnInfos(Long prodId);
	
	public Map<String, Object> getIccIdByProdId(Long prodId,String itemSpecId);

	public Map<String, Object> queryOrderList(String orderId);

	public Long getDevNumIdByAccNum(String accNum);

	public Long getValidateYHParams(Map<String, Object> paraMap);

	public Long getValidateYHOffer(Map<String, Object> offerMap);

	public String getLteImsiByProdid(Long prodId);

	public void saveJSONObject(String jsonObjectStr);

	public void updateJSONObject(String resultStr);

	public String querySpeedValue(String prodSpecId);

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
	 * 根据UIM卡号获取prod2TdIdDelCode
	 * @param accNbr
	 * @since 
	 */
	public Map<String, Object> getgetprod2TdIdDelCodeCode(String terminalCode);
	
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
     * 根据终端设备号查询终端信息
     * @param accNbr
     * @since 2015-09-26
     */
	public Map<String, Object> getDevInfoByCode(String devCode);

	public boolean isUimBak(String prodid);

	/**
	 * 补换卡是否存在在途单
	 * @param map
	 * @return
	 */
	public boolean getIsOrderOnWay(Map<String, Object> map);

	/**
	 * 通过uim卡号查询对应手机号码
	 * @param terminalCode
	 * @return
	 */
	public String findTelphoneByCardno(String terminalCode);

	/**
	 * 通过作废的UIM卡号查询对应的补卡手机号码
	 * @param terminalCode
	 * @return
	 */
	public String findTelphoneByDiscard(String terminalCode);

	/**
	 * 补换卡业务，在offer_prod_2_td表中判断用户是否存在相同的卡号
	 * @param map
	 * @return
	 */
	public boolean isExistCardByProdId(Map<String, Object> map);

	/**
	 * 根据手机号码，查询imsi信息
	 * @param billingNo
	 * @return
	 */
	public Map<String, Object> getImsiInfoByBillingNo(String billingNo);

	/**
	 * 根据接入号查询主副卡关系
	 * @param billingNo
	 * @return
	 */
	public List<Map<String, Object>> getBillingCardRelation(String billingNo);
	
	/**
	 * 根据设备号查询终端信息
	 * @param devCode
	 * @return
	 */
	public List<Map<String, Object>> getDevNumIdByDevCode(String devCode);

	/**
	 * 查询表空间信息
	 * @return
	 */
	public List<Map<String, Object>> queryTableSpace();

	/**
	 * 表中对应非SYS_LOB字段进行核查表占用的空间大小
	 * @param tableSpaceName 表空间名称
	 * @return
	 */
	public List<Map<String, Object>> queryUseSpaceNotSysLob(String tableSpaceName);

	/**
	 * 核查表空间中对应的SYS_LOB字段对应的实体表信息
	 * @param tableSpaceName 表空间名称
	 * @return
	 */
	public List<Map<String, Object>> queryUseSpaceSysLob(String tableSpaceName);

	/**
	 * CRM库锁表信息
	 * @return
	 */
	public List<Map<String, Object>> queryCrmLockInfo();

	/**
	 * 数据连接session的使用情况
	 * @return
	 */
	public Long queryDBSessionInfo();

	/**
	 * 保存奔驰休斯主表数据
	 * @param result
	 * @return
	 */
	public boolean saveBenzBusiOrder(Map<String, Object> result);

	/**
	 * 保存奔驰休斯从表数据
	 * @param resultSub
	 */
	public void saveBenzBusiOrderSub(Map<String, Object> resultSub);

	/**
	 * 判断是否为奔驰休斯用户
	 * @param accNbr
	 * @return
	 */
	public boolean isBenzOfferServUser(String accNbr);

	/**
	 * 根据用户id，产品属性规格id获取产品属性信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getProdItemsByParam(Map<String, Object> param);

	/**
	 * 根据custId查询s_crm_cust_class表信息
	 * @param custId
	 * @return
	 */
	public List<Map<String, Object>> getCustClassInfoByCustId(String custId);

	/**
	 *  根据用户号码获取custId
	 * @param accessNumber
	 * @return
	 */
	public String getCustIdByAccNum(String accessNumber);

	/**
	 * 根据业务类型查询是否存在在途单
	 * @param map
	 * @return
	 */
	public boolean qryProdOrderIsZtByOrderTypes(Map<String, Object> map);

	/**
	 * 查询用户状态
	 * @param accessNumber
	 */
	public Map<String, Object> queryOfferProdStatus(String accessNumber);

	/**
	 *  判断是否丰田wifi系统用户
	 * @param accessNumber
	 * @return
	 */
	public boolean isFtWifiSystem(String accessNumber);

	/**
	 * 校验华翼宽带订购资费参数
	 * @param param
	 * @return
	 */
	public int checkHykdOrder(Map<String, Object> param);

	/**
	 * 客户姓名信息校验
	 * @param phone_number
	 * @param cust_name
	 * @return
	 */
	public Map<String, String> checkCustName(String phone_number, String cust_name);

	/**
	 * 获取客户类型
	 * @param prodidByAccNbr
	 * @return
	 */
	public Map<String, Object> getPartyTypeCdByProdId(Long prodidByAccNbr);

	/**
	 * 判断集团积分商城是否可订购的销售品
	 * @param id
	 * @return
	 */
	public boolean checkOfferSpecBsns(String id);

	/**
	 * 验证是否有冻结信息（中行，农商行）
	 * @param checkMap
	 * @return
	 */
	public long checkBankFreeze(Map<String, Object> checkMap);

	/**
	 * 根据订单id查找订单信息
	 * @param olId
	 * @return
	 */
	public Map<String, Object> qryOrderListByOlId(String olId);

	/**
	 * 根据接入号码查找映射给uam的号码类型
	 * @param accNbr 
	 * @return
	 */
	public String getNbrTypeByAccNbr(String accNbr);

	/**
	 * 根据平台编码查询加密向量（为空的话查询全部）
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> qryEncryptStrByParam(Map<String, String> param);

	/**
	 * 根据工号查询组织和员工ID
	 * @param staffNumber
	 * @return
	 */
	public Map<String, Object> queryOrgByStaffNumber(String staffNumber);

	/**
	 * 根据PROD_ID查询用户对应的业务发展人STAFF_ID  
	 * @param prodIdLong
	 * @return
	 */
	public List<Long> queryStaffByProdId(Long prodIdLong);

	/**
	 * 获取相对应的组织
	 * @param map
	 * @return
	 */
	public Map<String, Object> findOrgByStaffId(Map<String, Object> map);

	/**
	 * 根据mac值查找证件信息加密向量
	 * @param mac
	 * @return
	 */
	public String getIDCardEncryptionVector(String mac);

	public List<Map<String, Object>> getGhAddressTemp();

	public void insertGhAddressUnit(Map<String, Object> param);

	/**
	 * 根据主键和业务类型查询是否有订购记录
	 * @param mk
	 * @return
	 */
	public long queryBussinessOrder(Map<String, Object> mk);

	public long querySeqBussinessOrder();

	public void saveOrUpdateBussinessOrderCheck(Map<String, Object> mk, String string);

	public int isAccNumRealNameparty(Long prodId);

	public String getIntfReqCtrlValue(String string);

	public Map<String, Object> checkParByIdCust(Map<String, Object> m);

	public Map<String, Object> queryOfferProdItemsByProdId(Long prodId);

	/**
	 * 一个证件号码是否有多个客户id
	 * @param m
	 * @return
	 */
	public boolean isManyPartyByIDNum(Map<String, Object> m);

	public boolean insertSms(Map<String, Object> map);

	public List<Map<String, Object>> qryBoInfos(Map<String, Object> mv);

	public Long qryChargesByOlid(String ol_id);

	public String queryOfferAddressDesc(Long prod_id);

	public String getTmDescription(Long prodId);

	public String getPartyManagerName(Long prodId);

	public String getOfferProdTmlName(Long prodId);
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
	 /**
     * 查询受理渠道 
     * @param prodId
     * @return 
     */
	public String getChannelNbrByChannelID(String channelId);
	/**
     * 查询员工工号
     * @param prodId
     * @return 
     */
	public int getCmsStaffCodeByStaffCode(String staffCode);

	public String getOfferProdReduOwnerIdByAccNbr(String accNbr);
	/**
     * 查询saop_rule_intf_log 信息
     * @param transactionID
     * @return 
     */
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
	 * @param offerSpecId
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
	 * 判断当前主销售品对应的产品是否为组合产品 offerSpecId = 10001782,获取组合产品信息
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
	
	public String sYncStaff2Crm(String request) throws Exception;
	
	/**
	 * 根据产品号码查询附属销售品，状态为22
	 * @param staffCode
	 * @return 
	 */
	public List<AttachOfferDto> queryAttachOfferInfo(Long prodId);
	
	/**
	 * 为FTTH 取接入方式
	 * @param staffCode
	 * @return 
	 */
	public Map<String, Object> getMethForFtth(String prodId);
	/**
	 * 为FTTH 查询value
	 * @param staffCode
	 * @return 
	 */
	public String getProValue(String prodId);
	/**
	 * 为FTTH 查询地址
	 * @param staffCode
	 * @return 
	 */
	public Map<String, Object> getaddresForFtth(String prodId);
	/**
	 * 为FTTH tml查询地理接口
	 * @param staffCode
	 * @return 
	 */
	/**
	 * 主销售品根据B号查C号
	 * @param request
	 */
	public List<Map<String,Object>> checkCinfoByb(String code);
	
	public Map<String, Object> getTmlForFtth(String prodId);
	
	/**
	 * 判断在3G4G下单归档时判断开户订购新产品实例是否受理渠道具有校园属性。
	 * @param id
	 * @return
	 */
	public boolean checkSchool(String id);

	/**
	 * 交换区查询接口
	 * @param request
	 */
	public List<Map<String,Object>> queryExchangeByName(String name);
	/**
	 * 查询对应的渠道和费用项
	 * @param request
	 */
	public List<Map<String,Object>> getchannelByCode(String name);
	public List<Map<String,Object>> queryDeptInfo(String name,String level);
	public Map<String,Object> queryByPartyId(String partyId);
	public Map<String,Object> queryEmailByPartyId(String partyId);
	
	/**
	 * 交换区查询接口
	 * @param request
	 */
	public List<Map<String,Object>> queryIndicatorsList(String time,String staffId);
	/**
	 * 按月查询交换区查询接口
	 * @param request
	 */
	public List<Map<String,Object>> queryIndicatorsListMouth(String startTime,String endTime,String staffId);

	/**
	 * 按日查询明细指标接口
	 * @param startTime
	 * @param endTime
	 * @param startRown
	 * @param endRown
	 * @param staffId
	 * @param indicatorsCode
	 * @return
	 */
	public List<Map<String, Object>> queryDetailedIndicatorsList(
			String statisticalTime,
			String startRown,
			 String endRown,
			String staffId,
			String accessNumber);
/**
 * 明细总数
 * @param statisticalTime
 * @param staffId
 * @param accessNumber
 * @return
 */
	public Map<String, Object> queryIndicatorsNumber(String statisticalTime,
			String staffId,String accessNumber);

	public List<Map<String, Object>> getOlnbrByg(String olId);

	public String getNumByStatus(String specId);
	
	public Map<String, Object> queryIndicators1(String statisticalTime, String staffId
	);

	public Map<String, Object> queryIndicators2(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators3(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators4(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators9(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators5(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators6(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators7(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators8(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators10(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicators11(String statisticalTime, String staffId);
	
	public Map<String, Object> queryIndicatorsMouth1(String startTime, String endTime,
		String staffId);
	
	public Map<String, Object> queryIndicatorsMouth2(String startTime, String endTime,
		String staffId);
	
	public Map<String, Object> queryIndicatorsMouth3(String startTime, String endTime,
		String staffId);
	
	public Map<String, Object> queryIndicatorsMouth4(String startTime, String endTime,
		String staffId);
	public Map<String, Object> queryIndicatorsMouth5(String startTime, String endTime,
		String staffId);
	public Map<String, Object> queryIndicatorsMouth6(String startTime, String endTime,
		String staffId);
	
	public Map<String, Object> queryIndicatorsMouth7(String startTime, String endTime,
			String staffId);
	
	public Map<String, Object> queryIndicatorsMouth8(String startTime, String endTime,
			String staffId);

	
	public Map<String, Object> queryIndicatorsMouth9(String startTime, String endTime,
		String staffId);
	public Map<String, Object> queryIndicatorsMouth10(String startTime, String endTime,
		String staffId);
	public Map<String, Object> queryIndicatorsMouth11(String startTime, String endTime,
		String staffId);
	public Map<String, Object> queryIndicatorsMouth12(String statisticalTime);
	
	public Map<String, Object> getAddressPreemptedSeq();
	public Map<String, Object> showSchoolName(String bcdCode);

	public List<Map<String, Object>> qryDeptById(String dept_id);

	public List<Map<String, Object>> campusCustomerCampusMark(String subjectId,
			String subjectNameId);

	public List<Map<String, Object>> queryCampusCustomerInformation(String num);

	public void saveSendRecord(Map<String, Object> saveMap);
	/**
	 * 按月查询明细指标接口
	 * @param startTime
	 * @param endTime
	 * @param endRown
	 * @param staffId
	 * @return
	 */
	public List<Map<String, Object>> queryDetailedIndicatorsListMouth(
			String startTime, String endTime, String startRown, String endRown,
			String staffId,String accessNumber);

	public Map<String, Object> qryKeyByFlag();

	public Map<String, Object> queryIndicatorsNumberMouth(String startTime,
			String endTime, String staffId, String accessNumber);


	public Map<String, Object> queryRewardSum(String staffId, String stardTime,
			String endTime);

	public List<Map<String, Object>> queryRewardInfoById(String staffId,
			String stardTime, String endTime, String startNum, String endNum);

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

	public String queryPayIdentityInfo(String xmlNodeText,String lsNumber);


	public List<Map<String, Object>> queryStoreByStaffId(String staffId);

	public List<Map<String, Object>> queryStatusByInstCode(String mktResInstCode);

	public Map<String, Object> getStaffIdBystaffNumber(String staffNumber);

	public List<Map<String, Object>> getEdidInfo();

	public void edidType(String partyId);

	public Map<String, Object> queryIdentityInfoByOlnbr(String olNbr);

	public Map<String, Object> queryProdInfoByAccs(String accessNumber);
	
	public Map<String, Object> queryExtCustOrder(String queryExtCustOrder);

	public List<Map<String, Object>> queryDepinfoForScrm(String partyId);

	public List<Map<String, Object>> queryUserdByAccess(String accessNuber);

	public String[] findPartyByIdentityPic(String name, String id);

	public List<Map<String, Object>> queryPhoneNoByAliUid(String aliUid);

	public void upStarMember(Map<String, Object> parameter);

	public Map<String, Object> queryCaflagByOlnbr(String olNbr);

	public String queryBlocOlidToProOlid(String olId);

	public void insertPartyFlagInfo(Map<String, Object> autonymFlag);

	public String queryBlocOlidToProOlNbr(String olId);

	public String queryBlocOlidToBlocOlNbr(String olNbr);

	public void InsertOutskirts(Map<String, Object> parameter);

	public Boolean queryOlidIfHave(String olId);

	public void updataOutskirts(Map<String, Object> parameter);

	public String queryPartyIdByprodId(String prodId);

	public String queryStaffIdBystaffCode(String staffCode);

	public String queryOfferspecidBystaffCode(String prodId);

	public Map<String, Object> queryOldcustinfoByPartyId(String partyId);

	public String queryPartyIdByCardId(String cerdValue);

	public Boolean getSchoolRole(String staffId);

	public String queryGroupNumberByGroupId(String olId);

	public String queryProvenceIdByGroupNum(String olId);

	public Map<String, Object> queryBureauDirectionByPhoneNum(String phoneNumber);

	public List<Map<String, Object>> queryRealNameFlagByPhoneAccssnumber(
			String custInfo);

	public List<Map<String, Object>> queryRealNameFlagByIdent(String custInfo);

	public List<Map<String, Object>> queryRealNameFlagByParttId(String custInfo);

	public Map<String, Object> queryPictureByolId(String olId);

	public Map<String, Object> querygztPictureInfoByolId(String olId);

	public String queryAccIdbyAccCd(String accId);

	public List<Map<String, Object>> desensitizationService(String object);

	public String desensitizationSystemCode(String desensitizationSystemId);

	public void savaDesensitizationLog(String logId,
			String desensitizationSystemId, String request, Date requestTime);
	
	public Map<String,Object> queryNewPartyPhotoByOlId(String olId);
	
	/**
	 * 将PPM库里的
	 * 销售品id 销售品名称 销售品描述 
	 * 提供WebService接口能力给到知识库
	 * @param request
	 * @return
	 * @throws RemoteException
	 */
	public List<Object> queryOfferSpecInfoList(Map<String, Object> param);
	
	/**
	 * 根据 用户accNbr 获得 用户星级
	 * @param accNbr
	 * @return
	 */
	public Map<String,Object> getStartLevelByPartyAccNbr(String accNbr);

	public List<Map<String,Object>> queryChannelInfoByIdentityNum(String identityNum);
}
