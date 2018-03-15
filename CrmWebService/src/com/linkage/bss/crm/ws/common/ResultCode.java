package com.linkage.bss.crm.ws.common;

public enum ResultCode {

	//BUSINESSS("B0000", "B��ͷ����ҵ�����"),
	
	CUSTOMER_NOT_EXIST("B0001", "�ͻ�������"),
	CUSTOMER_HAS_EXIST("B0002","����ͬ���û�"),
	CUST_GRADE_INFO_IS_NOT_EXIST("B0009","�ͻ��ȼ���ϢΪ��"),
	IDENTITY_TYPE_IS_NOT_EXIST("B0003","֤������У��ʧ��"),
	REQUEST_PARAME_IS_ERROR("B0004","��δ���"),
	REQUEST_IDENTIFY_TYPE_IS_NOT_EXIST("B0010","��Ч��֤������"),
    PREPARE_ORDER_RESULT_IS_NULL("B0005","��ѯԤ��������Ϣ���Ϊ��"),
    PREPARE_ORDER_TO_RELEASE("B0006","Ԥ������ʧ��"),
    PREPARE_ORDER_TO_COMMIT_FLASE("B0007","Ԥ����ת��ʧ��"),
    RESULT_IS_NULL("B0011","��ѯ���Ϊ��"),
    PROD_NOT_EXIST("B0012", "��Ʒ������"),
    ACCOUNT_NOT_EXIST("B0013", "�˻�������"),
    ACCNBR_TYPE_WRONG("B0014", "����������ʹ���"),
    PARAMETER_ERROR("B0015", "��������"),
	MATERIAL_INST_NOT_EXIST("B0016","��Ʒ�����ڻ�״̬����"),
	CHANGE_PASSWORD_ERROR("B0017","�޸�����ʧ�ܣ�������У��ʧ��"),
	CHANGE_PASSWORD_EXCEPTION("B0018","�޸��û�����ʧ�ܣ�һ��ͨ����ʧ��"),
	CHANGE_PASSWORD_NO_OLD_PSD("B0019","��δ��������"),
	OFFERLIST_NOT_EXIST("B0020", "����Ʒ������"),
	QUERY_FNS_NUM_ERROR("B0020","��ѯ�������ʧ��  ����offer_idδ��ѯ�����Թ��ΪitemSpecId�Ĺ�������Ϣ"),
	//PERMISSION("P0000", "P��ͷ����Ȩ�޴���"),
	INVALID_ACCNBR_TYPE("B0021","����ʶ��Ľ��������"),
	IS_SUBSIDY_ERROR("B0022","�ж��Ƿ��������Ѳ���ʧ�ܣ�����ʵ������δ��ѯ����Ӧ������Ϣ"),
	STAFF_CODE_NOT_EXIST("P0001", "Ա��������"),
	FILTER_TYPE_ERROR("B0023","ɸѡ���Ͳ���"),
	PARAMETER_NOT_EXIST("P0002","����Ϊ��"),
	PARAMETER_NOT_REAL("P0003","������,�������޸�"),
	PRODID_BY_ACCNBR_NOT_EXIST("B0024","ͨ�������prodIdΪ��"),
	PARTY_NOT_EXIST("B0025","�ͻ�������"),
	COMPROD_NOT_EXIST("B0026","������Ĳ�Ʒ������"),
	TERMINALCODE_BY_PRODID_IS_NULL("B0027","����prodId��ѯ��terminalCodeΪ��"),
	IMIINFOWLANCE_IS_NULL("B0028","���ݿ�Ϊ��"),
	IMIINFOVOICE_IS_NULL("B0029","������Ϊ��"),
	IMIINFO_IS_NULL("B0030","UIM����Ϣ��ѯΪ��"),
	OBJINFO_IS_NULL("B0031","���ݽ���Ų�ѯObjInfoDtoΪ��"),
	OFFERSPEC_IS_NULL("B0032","����Ʒ��񲻴���") ,
	GLOBALROAM_NOT_EXIST("B0033","���ݽ���Ų�ѯ��������Ϊ��"),
	OFFER_PROD_BY_PRODID_IS_NULL("B0034","����prodId��ѯ��������ƷΪ��")  ,
	POINT_EXCHANGE_ERROR("B0035","���ֶһ�ʧ��")  ,
	STAFF_LOGIN_NULL("B0036","�����̵�¼�ӿڷ���Ϊ��")  ,
	QUERY_CHANNEL_NULL("B0037","������ѯΪ��")  ,
	PWD_ERROR("B0038","�������")  ,
	STAFF_NOT_EXIST("B0039","��Ա����¼")  ,
	ACCNBR_TYPE_ERROR("B0040","�������ʹ���,����������Ϣ��")  ,
	OFFERLIST_BY_PRODID_NOT_EXIST("B0041","ͨ����Ʒid��ѯ����Ʒ������")  ,
	PARTY_2_PROD_IS_NULL("B0042","�ͻ���δ���ҵ���Ʒ��Ϣ")  ,
	MATERIAL_CODE_NOT_EXIST("B0043","δ��ѯ����ʵ��������Ϣ")  ,
	CALL_METHOD_ERROR("B1000","���õ��������ش�����Ϣ")  ,
	TICKET_IS_NULL("T0001","����ȯ��Ϣ��ѯ���Ϊ��"),
	PASSWORD_INVALID("B0044","������Ч"),
	PASSWORD_UNSET("B0045","δ��������"),
	QUERY_RESULT_IS_NULL("B0046","��ѯ��ϢΪ�գ�����������Ϣ��"),
	NOT_IN_COMPPROD("B0047","������ϲ�Ʒ��"),
	CATEGROYNODEID_ERROR("B0048","����Ŀ¼�ڵ㲻��ȷ!"),
	NOTIMPORTANTPARTY_BY_PARTYID("B0049","False:���ر��ͻ�"),
	UNITYPAY_FAILED("B0050","����ͳһ֧��ʧ��"),
	RESOURSE_STATE_UNVALIBLE("B0051", "����Դ״̬Ϊ������"),
	RESOURSE_INFO_ERROR("B0052", "���롢�����������ʹ���"),
	SR_SALE_CARD_FAILED("B0053","����Ӫ����Դʧ��"),
	GZT_QUERY_FAILED("B0054","����ͨ��ѯʧ�ܣ��������������֤����"),
	GZT_VALIDATION_FAILED("B0055","����ͨУ��ʧ��"),
	RELA_PROD_FAILED("B0056","�ú���ù�������û�ж�Ӧ�Ĳ�Ʒ"),
	REPEAT_CODE_MSG("B0057","�ظ�����"),
	CONTINUE_ORDER_OFFERSPEC_MSG("B0058","�ú���������"),
	HUCHI_OFFER_SPEC_FOR_CHANGE_STARTFASHION("B0059","���ڻ���ҵ����Чʱ�����Ϊ������Ч"),
	GUO_JI_MAN_YOU_BU_YUN_XU_BAN_LI("B0060","���û����ܰ����������"),
	HU_CHI_JIAO_YAN_EXCEPTION("B0061","����У��java�쳣"),
	GETINCOICEID_ERROR("B0062","��ȡ��Ʊ��ʧ��"),
	INSERTPOINT_BSS_ERROR("B0063","���û��ֿۼ�ҵ���쳣"),
	TICKET_OF_CAMP_TYPE_IS_NOT_EXIST("T0002","����ȯԤռ������Ч"),
	RECHARGE_CARD_CHECK_ERROR("B0064","�мۿ������쳣"),
	BUILDING_IS_NOT_EXIST("B0065","δ�ҵ�����������¥����Ϣ"),
	BANK_ACTION_TYPE("B0066","���ᣬ�ⶳ�������ʹ���"),
	BANK_TABLE_CONFIG("B0067","���ᣬ�ⶳ���ݿ�û�н�������"),
	PW_RULEERROR("B0066","�����ʽ����,����Ϊ6λ0-9�����֣�"),
	ORDER_ONWAY("B0068","������;������"),
	BUSINESS_CHECK_ISNOTOK("B0069","��������������Ʒ��"),
	MANYPARTY_BY_ID("B0070","ͬһ֤���Ų��ܴ�������ͻ���"),
	SCORE_CHECK_CRM("B0071","�ͻ�����ҵ��У�鲻ͨ��"),
	//SYSTEM("S0000", "S��ͷ����ϵͳ����"),

	SYSTEM_ERROR("S0001", "ϵͳ����"),
	ZJ_INFO_IS_NOT_NULL("Z0001","�Ѷ��������δ���ڣ�"),
	ZJ_INFO_IS_NOTBLZJ("Z0002","���ײͲ��ܰ����ҵ��"),
	ZJ_INFO_IS_NOT_NULL_NOTBLZJ("Z0001|Z0002","�Ѷ��������δ���ڲ������ײͲ��ܰ����ҵ��"),
	UNSUCCESS("1","ʧ��"),
	SUCCESS("0", "�ɹ�"),
	
	//���弯�Žӿ�Э��淶����
	createCRAccountREP_SUC("000000","�ɹ�"),
	createCRAccountREP_A("000001","�������Ѿ��ɹ������첽��ʽ��"),
	createCRAccountREP_B("200001","����ı�ѡ����Ϊ��"),
	createCRAccountREP_C("200002","������ʽ����"),
	createCRAccountREP_D("200006","�豸�����"),
	createCRAccountREP_E("200007","�������кŲ��Գ�"),
	createCRAccountREP_F("200008","�����������"),
	createCRAccountREP_G("100002","ϵͳæ"),
	createCRAccountREP_H("301000","�û��ѿ�ͨ����");

	
	private String code;

	private String desc;

	private ResultCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static void main(String args[]) {
		System.out.println(ResultCode.SUCCESS);
		System.out.println(ResultCode.SUCCESS.getCode());
		System.out.println(ResultCode.SUCCESS.getDesc());
		System.out.println(ResultCode.SUCCESS.getDeclaringClass());
		System.out.println(ResultCode.SUCCESS.name());
		System.out.println(ResultCode.SUCCESS.ordinal());
	}
}
