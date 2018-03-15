package com.linkage.bss.crm.ws.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WSDomain {

	/** ֤������ */
	public interface IdentifyType {
		/** ȱʡ���� */
		static final String QS_TYPE = "0";
		/** ���֤ */
		static final String ID_CARD = "1";
		/** ʿ��֤ */
		static final String SG_CARD = "2";
		/** ˰��֤ */
		static final String SW_CARD = "3";
		/** ��������� */
		static final String DLS_CARD = "4";
		/** ����ʶ���� */
		static final String DXSBM_NUM = "5";
		/** ��ʻ֤ */
		static final String DRIVING_LICENSE = "6";
		/** ���� */
		static final String PASSPORT = "9";
		/** �籣�� */
		static final String SOCIAL_SECURITY_CARD = "10";
		/** ��֯�������� */
		static final String TEACHER_CERTIFICATE = "11";
		/** ���ڱ�/��ס֤ */
		static final String ACCOUNT_BOOK = "12";
		/** �ͻ���ʶ�� */
		static final String PARTY_IDENTITY = "13";
		/** ���ſͻ���ʶ�� */
		static final String JT_FLAG = "18";
		/** ����֤ */
		static final String POLICE_CERTIFICATE = "22";
		/** ����֤ */
		static final String NCO_CARD = "37";
		/** �۰�̨ͨ��֤ */
		static final String GAT_CARD = "38";
		/** ѧ��֤ */
		static final String STUDENT_ID_CARD = "39";
		/** �ͻ��� */
		static final String CUSTOMER_CARD = "102";
		/** �����˻� */
		static final String BANK_ACCOUNT = "104";
		/** ����-���� */
		static final String PAIVATE_ORTHER = "47";
	}

	/** �ͻ���ѯ���� */
	public interface CustQueryType {
		/** ������ѯ */
		static final String QUERY_BY_NAME = "1";
		/** ����Ų�ѯ */
		static final String QUERY_BY_ACCNBR = "2";
		/** ֤����ѯ */
		static final String QUERY_BY_IDENTIFY = "3";
		/** �ͻ�ID��ѯ */
		static final String QUERY_BY_PARTY_ID = "4";
	}

	/** У��ͨ�� ������0��ʾ */
	public static final String CHECK_RESULT_YES = "0";

	/** У��δͨ�� ������1��ʾ */
	public static final String CHECK_RESULT_NO = "1";

	/** �ֻ�������״̬��ѯ����������Ƴ��� */
	public static final String QUERY_ROWNUM_BCNT = "1";

	public static final String QUERY_ROWNUM_ECNT = "2";

	/** �����item_spec_id */
	public static final String ADSL_ITEM_SPEC_ID = "740000003";

	/** Ԥ���������� 5 */
	public static final String PREPARE_ORDER_OL_TYPE = "5";

	// ��ѯ����
	public static final String VALIDATE_TYPE = "9";

	/** Ԥ�����ѯ�û� */
	public static final String USER_NAME = "so";

	/** ֤�����ͼ��� */
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

	/** �ظ�����code���� */
	public static final Set<String> REPEAT_CODES = new HashSet<String>();
	static {
		REPEAT_CODES.add("CRMBJ0062");
		REPEAT_CODES.add("CRMBJ0063");
		REPEAT_CODES.add("CRMBJ0066");
	}

	/** �ͻ���ѯ���ͼ��� */
	public static final Set<String> CUST_QUERY_TYPE_SET = new HashSet<String>();
	static {
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_NAME);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_ACCNBR);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_IDENTIFY);
		CUST_QUERY_TYPE_SET.add(CustQueryType.QUERY_BY_PARTY_ID);
	}

	/** ����������� */
	public interface AccNbrType {
		/** �绰С��ͨ */
		// static final String TELEPHONE = "1";
		/** ��� */
		// static final String BRANDBAND = "2";
		/** ������� */
		static final String ACCESS_NUMBER = "1";
		/** �ͻ�ID */
		static final String PARTY_ID = "2";
		/** ��ͬ�� */
		static final String ACCOUNT = "3";
		/** �ƶ����� */
		// static final String CDMA = "4";
		/** C+W���� */
		// static final String CW = "5";
		/** �ͻ���ʶ�� */
		static final String PARTY_IDENTITY = "13";
	}

	/** �ͻ���ѯ���ͼ��� */
	public static final Set<String> ACCNBR_TYPE_SET = new HashSet<String>();
	static {
		// ACCNBR_TYPE_SET.add(AccNbrType.TELEPHONE);
		// ACCNBR_TYPE_SET.add(AccNbrType.BRANDBAND);
		// ACCNBR_TYPE_SET.add(AccNbrType.CDMA);
		// ACCNBR_TYPE_SET.add(AccNbrType.CW);
		ACCNBR_TYPE_SET.add(AccNbrType.ACCESS_NUMBER);
	}

	/** ��ѯģʽ */
	public interface QueryMode {
		/** ��ѯ����Ʒ */
		static final String PRODUCT = "1";
		/** ��ѯ�ͻ������еĲ�Ʒ */
		static final String PARTY = "2";
		/** ��ѯ��ͬ�Ŷ�Ӧ�����в�Ʒ */
		static final String ACCT = "3";
	}

	public interface PasswordType {
		/** ��Ʒ��ѯ���� */
		static final String PROD_QUERY = "1";
		/** ��Ʒҵ������ */
		static final String PROD_BUSINESS = "2";
		/** �ͻ���ѯ���� */
		static final String CUSTOMER_QUERY = "3";
		/** �ͻ�ҵ������ */
		static final String CUSTOMER_BUSINESS = "4";
		/** ��ͬ������ */
		static final String ACCOUNT = "5";
	}

	public interface InOutType {
		/** ��������� ���� */
		static final String OUT = "CK";
		/** ��������� ��� */
		static final String IN = "RK";
	}

	public interface CheckPasswordResult {
		/** ������Ч */
		static final String VALID = "1";
		/** ������Ч */
		static final String INVALID = "0";
		/** δ�������� */
		static final String NONE = "-1";
	}

	// ��Ʒ���
	public interface ProdSpecId {
		// cdma
		static final String CDMA = "379";
	}

	// ��������
	public interface OrderTypeId {
		// �޸�����
		static final String CHANGE_PASSWORD = "18";
		static final String RESET_PASSWORD = "201407";
	}

	/** ����˻�ע�����ȡ���ӿ����� */
	public interface BroadBandIntfType {
		/** ע�� */
		static final String REGISTER = "1";
		/** ���� */
		static final String CHANGE = "2";
		/** ȡ�� */
		static final String CANCEL = "3";
	}

	/** ����������� */
	public interface BroadBandAnfTypeCd {
		/** 307 */
		static final String ANTYPECD_307 = "307";
		/** 323 */
		static final String ANTYPECD_323 = "323";
	}

	/** �������� */
	public interface CompletedType {
		/** �ѿ��� */
		static final String COMPLETED = "1";
		/** δ���� */
		static final String UNCOMPLETED = "2";
	}

	// ɸѡ�����������
	public interface ServSpecIdOfFilterNumber {
		/** �����ű���ɸѡ */
		static final String AreaCode = "992018236";
		/** �����뱻��ɸѡ */
		static final String PassWord = "992018237";
		/** ��ʱ��α���ɸѡ */
		static final String Time = "992018238";
		/** ��������뱻��ɸѡ */
		static final String InNumber = "992018239";
		/** �����������ɸѡ */
		static final String OutNumber = "992018240";
	}

	// �Ƿ��������Ѳ��� ������
	public interface IsSubsidy {
		// ��������
		static final String YES = "1";
		// δ������
		static final String NO = "0";
	}

	public static final String AUDIT_STATUS_CD_USED = "6";

	/** �����������ķ��������� */
	public static final Set<String> BLACK_METHOD = new HashSet<String>();
	static {
		// ��ӷ�����
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
		/** ��Ʊ��ص�4���ӿ� */
		BLACK_METHOD.add("indentInvoiceNumQryIntf");
		BLACK_METHOD.add("indentInvoiceNumModIntf");
		BLACK_METHOD.add("indentInvoicePrintIntf");
		BLACK_METHOD.add("indentInvoiceRepPrintIntf");
		/** �ṩ���Ʒѵ�2������ӿ� */
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
		//�ṩ����дƽ̨
		BLACK_METHOD.add("queryPhoneNumberList");
		//���л���У��
		BLACK_METHOD.add("validateYH");
		//ͳһ֧��δ�շѶ�����ѯ�ӿ�
		BLACK_METHOD.add("queryOrderList");
		//������ϸ��ѯ
		BLACK_METHOD.add("queryUserScoreAll");
		//�µķ����ӿ�
		BLACK_METHOD.add("cancelOrderListNew");
		//�ͻ�������ϢУ��
		BLACK_METHOD.add("checkCustName");
		//��ѯ������ʷ��Ϣ
		BLACK_METHOD.add("queryOrderListInfo");

	}

	/** �ն�ʵ��״̬ */
	public interface MATERIALSTATUS {
		/** ���� */
		static final String AVAILABLE = "A";

	}

	/** ���ܰ�����忪�����Ĳ�Ʒ״̬ */
	public static final Set<String> BLACK_PROD_STATUS = new HashSet<String>();
	static {
		BLACK_PROD_STATUS.add("3");
		BLACK_PROD_STATUS.add("9");
		BLACK_PROD_STATUS.add("10");
		BLACK_PROD_STATUS.add("18");
		BLACK_PROD_STATUS.add("16");
	}

	// esb��ַ add by CHENJUNJIE 2012/11/08
	public static final String ESB_ADDRESS = "http://172.19.17.184:7102/ACCESS/services/CrmWebService?wsdl";

	public static final Integer STATUS_CD_PHONE = 6;

	/** �ͷ�2.0����ID */
	public static final Set<String> KF_CHANNEL = new HashSet<String>();
	static {
		KF_CHANNEL.add("11040754");
		KF_CHANNEL.add("11040755");
		KF_CHANNEL.add("11040756");
	}

	/** �������item_spec_id���� */
	public static final Set<String> BROADBAND_SPEED_TYPE_SET = new HashSet<String>();
	static {
		BROADBAND_SPEED_TYPE_SET.add("12");
		BROADBAND_SPEED_TYPE_SET.add("13");
	}

	/** �ͻ����ͱ���ת�� */
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

	//�������ʽ����ID
	public static final String BUSI_ORDER_ATTR_PROD_STATUS = "700007002";

	/** ��ѯ��ӿ� */
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

	/** ������ӿ� */
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
	/** ����exchangeForProvince ������ */
	public interface ExchangeForProvinceCode {
		static final String EXCHANGE_OK = "0000";
		static final String EXCHANGE_ERROR = "1";
		static final String PREHOLDING_STATUS = "1001";
		static final String PREHOLDING_TYPE = "1108";
	}
}
