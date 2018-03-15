package com.linkage.bss.crm.ws.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WSDomain {

	/** 证件类型 */
	public interface IdentifyType {
		/** 缺省类型 */
		static final String QS_TYPE = "0";
		/** 身份证 */
		static final String ID_CARD = "1";
		/** 士官证 */
		static final String SG_CARD = "2";
		/** 税务证 */
		static final String SW_CARD = "3";
		/** 代理商身份 */
		static final String DLS_CARD = "4";
		/** 电信识别码 */
		static final String DXSBM_NUM = "5";
		/** 驾驶证 */
		static final String DRIVING_LICENSE = "6";
		/** 护照 */
		static final String PASSPORT = "9";
		/** 社保卡 */
		static final String SOCIAL_SECURITY_CARD = "10";
		/** 组织机构代码 */
		static final String TEACHER_CERTIFICATE = "11";
		/** 户口本/居住证 */
		static final String ACCOUNT_BOOK = "12";
		/** 客户标识码 */
		static final String PARTY_IDENTITY = "13";
		/** 集团客户标识码 */
		static final String JT_FLAG = "18";
		/** 警官证 */
		static final String POLICE_CERTIFICATE = "22";
		/** 军官证 */
		static final String NCO_CARD = "37";
		/** 港澳台通行证 */
		static final String GAT_CARD = "38";
		/** 学生证 */
		static final String STUDENT_ID_CARD = "39";
		/** 客户卡 */
		static final String CUSTOMER_CARD = "102";
		/** 银行账户 */
		static final String BANK_ACCOUNT = "104";
		/** 个人-其他 */
		static final String PAIVATE_ORTHER = "47";
	}

	/** 客户查询类型 */
	public interface CustQueryType {
		/** 姓名查询 */
		static final String QUERY_BY_NAME = "1";
		/** 接入号查询 */
		static final String QUERY_BY_ACCNBR = "2";
		/** 证件查询 */
		static final String QUERY_BY_IDENTIFY = "3";
		/** 客户ID查询 */
		static final String QUERY_BY_PARTY_ID = "4";
	}

	/** 校验通过 常量用0表示 */
	public static final String CHECK_RESULT_YES = "0";

	/** 校验未通过 常量用1表示 */
	public static final String CHECK_RESULT_NO = "1";

	/** 手机卡激活状态查询结果个数限制常量 */
	public static final String QUERY_ROWNUM_BCNT = "1";

	public static final String QUERY_ROWNUM_ECNT = "2";

	/** 宽带的item_spec_id */
	public static final String ADSL_ITEM_SPEC_ID = "740000003";

	/** 预受理订单类型 5 */
	public static final String PREPARE_ORDER_OL_TYPE = "5";

	// 查询类型
	public static final String VALIDATE_TYPE = "9";

	/** 预受理查询用户 */
	public static final String USER_NAME = "so";

	/** 证件类型集合 */
	public static final Set<String> IDENTIFY_TYPE_SET = new HashSet<String>();
	static {
		IDENTIFY_TYPE_SET.add(IdentifyType.ID_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.NCO_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.PASSPORT);
		IDENTIFY_TYPE_SET.add(IdentifyType.DRIVING_LICENSE);
		IDENTIFY_TYPE_SET.add(IdentifyType.STUDENT_ID_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.TEACHER_CERTIFICATE);
		IDENTIFY_TYPE_SET.add(IdentifyType.ACCOUNT_BOOK);
		IDENTIFY_TYPE_SET.add(IdentifyType.POLICE_CERTIFICATE);
		IDENTIFY_TYPE_SET.add(IdentifyType.SOCIAL_SECURITY_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.PARTY_IDENTITY);
		IDENTIFY_TYPE_SET.add(IdentifyType.CUSTOMER_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.BANK_ACCOUNT);
		IDENTIFY_TYPE_SET.add(IdentifyType.SG_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.SW_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.DLS_CARD);
		IDENTIFY_TYPE_SET.add(IdentifyType.DXSBM_NUM);
		IDENTIFY_TYPE_SET.add(IdentifyType.JT_FLAG);
		IDENTIFY_TYPE_SET.add(IdentifyType.QS_TYPE);
		IDENTIFY_TYPE_SET.add(IdentifyType.PAIVATE_ORTHER);
		IDENTIFY_TYPE_SET.add(IdentifyType.GAT_CARD);

	}

	/** 重复订购code集合 */
	public static final Set<String> REPEAT_CODES = new HashSet<String>();
	static {
		REPEAT_CODES.add("CRMBJ0062");
		REPEAT_CODES.add("CRMBJ0063");
		REPEAT_CODES.add("CRMBJ0066");
	}

	/** 客户查询类型集合 */
	public static final Set<String> CUST_QUERY_TYPE_SET = new HashSet<String>();
	static {
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_NAME);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_ACCNBR);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_IDENTIFY);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_PARTY_ID);
	}

	/** 接入号码类型 */
	public interface AccNbrType {
		/** 电话小灵通 */
		// static final String TELEPHONE = "1";
		/** 宽带 */
		// static final String BRANDBAND = "2";
		/** 接入号码 */
		static final String ACCESS_NUMBER = "1";
		/** 客户ID */
		static final String PARTY_ID = "2";
		/** 合同号 */
		static final String ACCOUNT = "3";
		/** 移动号码 */
		// static final String CDMA = "4";
		/** C+W号码 */
		// static final String CW = "5";
		/** 客户标识码 */
		static final String PARTY_IDENTITY = "13";
	}

	/** 客户查询类型集合 */
	public static final Set<String> ACCNBR_TYPE_SET = new HashSet<String>();
	static {
		// ACCNBR_TYPE_SET.add(AccNbrType.TELEPHONE);
		// ACCNBR_TYPE_SET.add(AccNbrType.BRANDBAND);
		// ACCNBR_TYPE_SET.add(AccNbrType.CDMA);
		// ACCNBR_TYPE_SET.add(AccNbrType.CW);
		ACCNBR_TYPE_SET.add(AccNbrType.ACCESS_NUMBER);
	}

	/** 查询模式 */
	public interface QueryMode {
		/** 查询本产品 */
		static final String PRODUCT = "1";
		/** 查询客户下所有的产品 */
		static final String PARTY = "2";
		/** 查询合同号对应的所有产品 */
		static final String ACCT = "3";
	}

	public interface PasswordType {
		/** 产品查询密码 */
		static final String PROD_QUERY = "1";
		/** 产品业务密码 */
		static final String PROD_BUSINESS = "2";
		/** 客户查询密码 */
		static final String CUSTOMER_QUERY = "3";
		/** 客户业务密码 */
		static final String CUSTOMER_BUSINESS = "4";
		/** 合同号密码 */
		static final String ACCOUNT = "5";
	}

	public interface InOutType {
		/** 出入库类型 出库 */
		static final String OUT = "CK";
		/** 出入库类型 入库 */
		static final String IN = "RK";
	}

	public interface CheckPasswordResult {
		/** 密码有效 */
		static final String VALID = "1";
		/** 密码无效 */
		static final String INVALID = "0";
		/** 未设置密码 */
		static final String NONE = "-1";
	}

	// 产品规格
	public interface ProdSpecId {
		// cdma
		static final String CDMA = "379";
	}

	// 订单类型
	public interface OrderTypeId {
		// 修改密码
		static final String CHANGE_PASSWORD = "18";
		static final String RESET_PASSWORD = "201407";
	}

	/** 宽带账户注册或者取消接口类型 */
	public interface BroadBandIntfType {
		/** 注册 */
		static final String REGISTER = "1";
		/** 重置 */
		static final String CHANGE = "2";
		/** 取消 */
		static final String CANCEL = "3";
	}

	/** 宽带接入类型 */
	public interface BroadBandAnfTypeCd {
		/** 307 */
		static final String ANTYPECD_307 = "307";
		/** 323 */
		static final String ANTYPECD_323 = "323";
	}

	/** 竣工类型 */
	public interface CompletedType {
		/** 已竣工 */
		static final String COMPLETED = "1";
		/** 未竣工 */
		static final String UNCOMPLETED = "2";
	}

	// 筛选号码服务类型
	public interface ServSpecIdOfFilterNumber {
		/** 按区号被叫筛选 */
		static final String AreaCode = "992018236";
		/** 按密码被叫筛选 */
		static final String PassWord = "992018237";
		/** 按时间段被叫筛选 */
		static final String Time = "992018238";
		/** 按呼入号码被叫筛选 */
		static final String InNumber = "992018239";
		/** 按呼出号码叫筛选 */
		static final String OutNumber = "992018240";
	}

	// 是否已做话费补贴 返回码
	public interface IsSubsidy {
		// 已做补贴
		static final String YES = "1";
		// 未作补贴
		static final String NO = "0";
	}

	public static final String AUDIT_STATUS_CD_USED = "6";

	/** 跳过拦截器的方法名集合 */
	public static final Set<String> BLACK_METHOD = new HashSet<String>();
	static {
		// 添加方法名
		BLACK_METHOD.add("loginln");
		BLACK_METHOD.add("login");
		BLACK_METHOD.add("getUimCardInfo");
		BLACK_METHOD.add("indentPayNotice");
		//BLACK_METHOD.add("queryOrderListInfo");
		BLACK_METHOD.add("ifImportantPartyByPartyId");
		BLACK_METHOD.add("QueryCardinfoByAcct");
		BLACK_METHOD.add("checkResultIn");
		BLACK_METHOD.add("syncDate4Prm2Crm");
		BLACK_METHOD.add("highFeeQueryUserInfo");
		/** 发票相关的4个接口 */
		BLACK_METHOD.add("indentInvoiceNumQryIntf");
		BLACK_METHOD.add("indentInvoiceNumModIntf");
		BLACK_METHOD.add("indentInvoicePrintIntf");
		BLACK_METHOD.add("indentInvoiceRepPrintIntf");
		/** 提供给计费的2个反向接口 */
		BLACK_METHOD.add("activateUser");
		BLACK_METHOD.add("rechargeChangePriceplan");
		BLACK_METHOD.add("bankFreeze");
		BLACK_METHOD.add("bankFreezeDou");
		BLACK_METHOD.add("queryInvoiceReprintTemp");
		BLACK_METHOD.add("uploadRealNameCust");
		BLACK_METHOD.add("uploadRealNameCust");
		BLACK_METHOD.add("updateAndDenyLoan");
		BLACK_METHOD.add("queryFreezeOfBank");
		BLACK_METHOD.add("qryProdCount");
		BLACK_METHOD.add("addAnonymousParty");
		BLACK_METHOD.add("orderSubmit_kx");
		//提供给空写平台
		BLACK_METHOD.add("queryPhoneNumberList");
		//银行基金校验
		BLACK_METHOD.add("validateYH");
		//统一支付未收费订单查询接口
		BLACK_METHOD.add("queryOrderList");
		//积分明细查询
		BLACK_METHOD.add("queryUserScoreAll");
		//新的返销接口
		BLACK_METHOD.add("cancelOrderListNew");
		//客户姓名信息校验
		BLACK_METHOD.add("checkCustName");
		//查询订单历史信息
		BLACK_METHOD.add("queryOrderListInfo");

	}

	/** 终端实例状态 */
	public interface MATERIALSTATUS {
		/** 可用 */
		static final String AVAILABLE = "A";

	}

	/** 不能办理彩铃开销户的产品状态 */
	public static final Set<String> BLACK_PROD_STATUS = new HashSet<String>();
	static {
		BLACK_PROD_STATUS.add("3");
		BLACK_PROD_STATUS.add("9");
		BLACK_PROD_STATUS.add("10");
		BLACK_PROD_STATUS.add("18");
		BLACK_PROD_STATUS.add("16");
	}

	// esb地址 add by CHENJUNJIE 2012/11/08
	public static final String ESB_ADDRESS = "http://172.19.17.184:7102/ACCESS/services/CrmWebService?wsdl";

	public static final Integer STATUS_CD_PHONE = 6;

	/** 客服2.0渠道ID */
	public static final Set<String> KF_CHANNEL = new HashSet<String>();
	static {
		KF_CHANNEL.add("11040754");
		KF_CHANNEL.add("11040755");
		KF_CHANNEL.add("11040756");
	}

	/** 宽带速率item_spec_id集合 */
	public static final Set<String> BROADBAND_SPEED_TYPE_SET = new HashSet<String>();
	static {
		BROADBAND_SPEED_TYPE_SET.add("12");
		BROADBAND_SPEED_TYPE_SET.add("13");
	}

	/** 客户类型编码转换 */
	public static final Map<String, String> PARTY_LEVEL = new HashMap<String, String>();
	static {
		PARTY_LEVEL.put("1", "20014");
		PARTY_LEVEL.put("2", "20001");
		PARTY_LEVEL.put("3", "20018");
	}

	public static final String CARD_DIAMOND = "1000";
	public static final String CARD_GOLD = "1100";
	public static final String CARD_SILVER = "1200";
	public static final String CARD_COMMON = "1300";

	//订单激活方式属性ID
	public static final String BUSI_ORDER_ATTR_PROD_STATUS = "700007002";

	/** 查询类接口 */
	public static final Set<String> QUERY_INTERFACE = new HashSet<String>();
	static {
		QUERY_INTERFACE.add("queryAgentInfo");
		QUERY_INTERFACE.add("login");
		QUERY_INTERFACE.add("checkDeviceId");
		QUERY_INTERFACE.add("checkSnPwd");
		QUERY_INTERFACE.add("checkDeviceIdNew");
		QUERY_INTERFACE.add("blackUserCheck");
		QUERY_INTERFACE.add("qryUserVipType");
		QUERY_INTERFACE.add("qryCust");
		QUERY_INTERFACE.add("checkPassword");
		QUERY_INTERFACE.add("checkCampusUser");
		QUERY_INTERFACE.add("getCustAdInfo");
		QUERY_INTERFACE.add("queryManager");
		QUERY_INTERFACE.add("qryCreditrating");
		QUERY_INTERFACE.add("selectPartyInfo");
		QUERY_INTERFACE.add("selectPartyManager");
		QUERY_INTERFACE.add("getBrandLevelDetail");
		QUERY_INTERFACE.add("checkResultIn");
		QUERY_INTERFACE.add("getUserZJInfoByAccessNumber");
		QUERY_INTERFACE.add("validateContractInfo");
		QUERY_INTERFACE.add("queryBuilding");
		QUERY_INTERFACE.add("queryBuildingForCrm");
		QUERY_INTERFACE.add("queryOrderListInfo");
		QUERY_INTERFACE.add("getPreInterimBycond");
		QUERY_INTERFACE.add("queryPreOrderDetail");
		QUERY_INTERFACE.add("queryService");
		QUERY_INTERFACE.add("queryOperation");
		QUERY_INTERFACE.add("queryOffering2pp");
		QUERY_INTERFACE.add("qryAcctInfo");
		QUERY_INTERFACE.add("checkPartyProd");
		QUERY_INTERFACE.add("qryProd");
		QUERY_INTERFACE.add("queryCouponInfoByPriceplan");
		QUERY_INTERFACE.add("querySerivceAcct");
		QUERY_INTERFACE.add("qryOfferModel");
		QUERY_INTERFACE.add("queryAccNBRInfo");
		QUERY_INTERFACE.add("queryAreaInfo");
		QUERY_INTERFACE.add("queryOperatingOfficeInfo");
		QUERY_INTERFACE.add("queryFNSInfo");
		QUERY_INTERFACE.add("isSubsidy");
		QUERY_INTERFACE.add("checkBindAccessNumber");
		QUERY_INTERFACE.add("queryPpInfo");
		QUERY_INTERFACE.add("newValidateService");
		QUERY_INTERFACE.add("isYKSXInfo");
		QUERY_INTERFACE.add("checkGlobalroam");
		QUERY_INTERFACE.add("filterNumber");
		QUERY_INTERFACE.add("queryContinueOrderPPInfo");
		QUERY_INTERFACE.add("queryProdByCompProdSpecId");
		QUERY_INTERFACE.add("compProdRule");
		QUERY_INTERFACE.add("queryNbrByIccid");
		QUERY_INTERFACE.add("queryImsiInfoByMdn");
		QUERY_INTERFACE.add("queryProdinfoByImsi");
		QUERY_INTERFACE.add("validateCustOrder");
		QUERY_INTERFACE.add("queryOptionalOfferList");
		QUERY_INTERFACE.add("highFeeQueryUserInfo");
		QUERY_INTERFACE.add("ifImportantPartyByPartyId");
		QUERY_INTERFACE.add("QueryCardinfoByAcct");
		QUERY_INTERFACE.add("checkUimNo");
		QUERY_INTERFACE.add("queryTml");
		QUERY_INTERFACE.add("queryPhoneNumberList");
		QUERY_INTERFACE.add("queryPhoneNumberPoolList");
		QUERY_INTERFACE.add("getUimCardInfo");
		QUERY_INTERFACE.add("queryCodeByNum");
		QUERY_INTERFACE.add("getClerkId");
		QUERY_INTERFACE.add("queryMdnByUim");
		QUERY_INTERFACE.add("queryUimNum");
		QUERY_INTERFACE.add("checkTicket");
		QUERY_INTERFACE.add("checkExchangeTicket");
		QUERY_INTERFACE.add("checkGiftCert");
		QUERY_INTERFACE.add("indentInvoiceNumQryIntf");
		QUERY_INTERFACE.add("queryInvoiceReprint");
		QUERY_INTERFACE.add("checkSaleResourceByCode");
		QUERY_INTERFACE.add("uploadRealNameCust");
	}

	/** 受理类接口 */
	public static final Set<String> DEAL_INTERFACE = new HashSet<String>();
	static {
		DEAL_INTERFACE.add("syncDate4Prm2Crm");
		DEAL_INTERFACE.add("transmitRandom");
		DEAL_INTERFACE.add("changeStaffPassword");
		DEAL_INTERFACE.add("createCust");
		DEAL_INTERFACE.add("changePassword");
		DEAL_INTERFACE.add("resetPassword");
		DEAL_INTERFACE.add("modifyCustom");
		DEAL_INTERFACE.add("indentPayNotice");
		DEAL_INTERFACE.add("activateUser");
		DEAL_INTERFACE.add("rechargeChangePriceplan");
		DEAL_INTERFACE.add("releaseCartByOlIdForPrepare ");
		DEAL_INTERFACE.add("commitPreOrderInfo");
		DEAL_INTERFACE.add("addSerivceAcct");
		DEAL_INTERFACE.add("businessService");
		DEAL_INTERFACE.add("orderSubmit");
		DEAL_INTERFACE.add("savePrepare");
		DEAL_INTERFACE.add("cancelOrder");
		DEAL_INTERFACE.add("newAcct");
		DEAL_INTERFACE.add("confirmContinueOrderPPInfo");
		DEAL_INTERFACE.add("cancelOrderList");
		DEAL_INTERFACE.add("CreateCRAccountREP");
		DEAL_INTERFACE.add("DelCRAccountREP");
		DEAL_INTERFACE.add("reprintReceipt");
		DEAL_INTERFACE.add("createUserAddr");
		DEAL_INTERFACE.add("broadbandPwdReset");
		DEAL_INTERFACE.add("createBroadbandAccount");
		DEAL_INTERFACE.add("qryPreHoldNumber");
		DEAL_INTERFACE.add("addReceiptPringLog");
		DEAL_INTERFACE.add("releaseAn");
		DEAL_INTERFACE.add("goodsBatchGet");
		DEAL_INTERFACE.add("checkSequence");
		DEAL_INTERFACE.add("doStoreInOutCrm");
		DEAL_INTERFACE.add("confirmSequence");
		DEAL_INTERFACE.add("confirmCertGift");
		DEAL_INTERFACE.add("insertPoint");
		DEAL_INTERFACE.add("updateAndConfirmPoint");
		DEAL_INTERFACE.add("insertPointRefund");
		DEAL_INTERFACE.add("indentInvoiceNumModIntf");
		DEAL_INTERFACE.add("indentInvoicePrintIntf");
		DEAL_INTERFACE.add("indentInvoiceRepPrintIntf");
		DEAL_INTERFACE.add("confirmPrint");
		DEAL_INTERFACE.add("confirmPrintSelf");
	}
	/** 集团exchangeForProvince 返回码 */
	public interface ExchangeForProvinceCode {
		static final String EXCHANGE_OK = "0000";
		static final String EXCHANGE_ERROR = "1";
		static final String PREHOLDING_STATUS = "1001";
		static final String PREHOLDING_TYPE = "1108";
	}
}
