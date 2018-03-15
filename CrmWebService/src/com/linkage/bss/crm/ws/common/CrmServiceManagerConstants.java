package com.linkage.bss.crm.ws.common;

public class CrmServiceManagerConstants {
	// SEGMENT
	public static final Integer SEGMENT_VIP_CUST = Integer.valueOf(10001);// ��ͻ�
	public static final Integer SEGMENT_BUSINESS_CUST = Integer.valueOf(10002);// ��ҵ�ͻ�
	public static final Integer SEGMENT_PUBLIC_CUST = Integer.valueOf(10003);// ���ڿͻ�
	public static final Integer SEGMENT_FLOW_CUST = Integer.valueOf(10004);// �����ͻ�
	public static final Integer SEGMENT_PUBLIC_PHONE_CUST = Integer.valueOf(10005);// �����ͻ�
	// ����Ĭ������
	public static final String PASSWORD_DEFAULT_DATE = "3000-01-01";
	// ISMPͬ���ӿڷ��ر���
	public static final int RC_SUCCESS = 0;// �����ɹ�
	public static final int RC_USER_NBR_ERROR = 5104;// �û��������ͷǷ�
	public static final int RC_OTHER_FAULT = 7215;// ����ԭ��ʧ��
	public static final int RC_USER_NOT_EXIST = 7008;// �û�������
	// ��������
	public static final Integer PW_TYPE_BIZ_PASSWORD = 4; // �ͻ�ҵ������
	// ITEM_SPEC
	public static final String ITEM_SPEC_TML = "30079";// ���������Ź���������
	public static final String ITEM_SPEC_INSTALL_TIME = "303378";// ���Ű�װʱ��
	// �ۺ�ҵ�����ƽ̨��������
	public static final String ISMP_CHANNEL_ID = "-12000";// �ۺ�ҵ�����ƽ̨����
	public static final String ISMP_STAFF_ID = "-10000";// �ۺ�ҵ�����ƽ̨Ա�����
	public static final int MSISDN_PROD_SPEC_ID = 379;// �ƶ�ҵ��
	public static final int PHS_PROD_SPEC_ID = 1;// С��ͨ
	public static final int PSTN_PROD_SPEC_ID = 2;// ��ͨ�绰
	// ͬ��������ϵʱ��ı�ʶ
	public static final int OPFLAG_ORDER = 0;// ����
	public static final int OPFLAG_UNORDER = 3;// ȡ������
	public static final int OPFLAG_SUSPEND = 1;// ��ͣ

	// ͳһ�˺����ɽӿڳ���
	public static final String QUERY_UNIFIED_ACCNBR_FLAG = "1";
	public static final String GENERATE_UNIFIED_ACCNBR_FLAG = "2";

	// �ͻ����������
	public static final int PARTY_PROFILE_CATG_CD_LINKNUM = 1;// �ͻ���ϵ�绰
	public static final int PARTY_PROFILE_CATG_CD_MAILNUM = 40074;// �ͻ���������
	public static final int PARTY_PROFILE_CATG_CD_EMAIL = 4;// �ͻ�Emai

	// ����ʵʱ����
	// Ա������������
	public static final long I_STAFF_PHS = -10005l;// �����Զ�����Ա��
	public static final int I_CHANNEL_PHS_SMS = -10005;// ������������
	public static final int I_CHANNEL_PHS_SMS_NJ = 250119155; // �Ͼ���ֵҵ�񲿶�������
	// ����������
	public static final int SSPEC_PHONE_LD = 9; // ���ڳ�Ȩ
	public static final int SSPEC_PHONE_CALLER_DISP = 20; // ������ʾ
	public static final int SSPEC_PHONE_PHS_MSG = 35; // PHS����Ϣ
	public static final int SSPEC_SERV_179 = 179; // ��������Ԫ�Ƚ����⣬���������������phs������ͨ��ݿ�
	public static final int SSPEC_COLORFUL_RING = 41; // �߲�����
	public static final int SSPEC_ZJCL = 235; // ���в���
	public static final int SSPEC_PHONE_REDIRECT = 13; // ����������ת��
	public static final int SSPEC_PHS_HDJC = 182; // ����Ĵ�
	public static final int SSPEC_PHS_SWCL = 183; // �������
	public static final int SSPEC_PHS_NA_REDIRECT = 39; // ���ɼ�ǰת
	public static final int SSPEC_PHS_CMODE = 34; // PHSĴָ��Ϣ
	public static final int SSPEC_PHS_HFYCX = 249; // PHS�����ײ�ѯ
	public static final int SSPEC_PHS_TCYCX = 362; // PHS�ײ��ײ�ѯ
	public static final int SSPEC_PHS_MY = 157; // PHSְ����(����)
	public static final int SSPEC_PHS_XLTMS = 37; // С��ͨ���� - BSS��PHS����ä������37��
	public static final int SSPEC_PHS_HXZ = 272; // ��ϻ�� - BSS��272��������
	public static final int SSPEC_PHONE_GHMS = 252; // �������� - BSS���̻����飨252��
	public static final int SSPEC_PHS_ICARD = 179; // PHS��ͨ��ݿ�
	// ������������
	public static final String ZONE_NUMBER_NJ = "025"; // �Ͼ�

	// ��Ʒ״̬
	public static final int PROD_STATUS_ACTIVE = 1; // ����
	public static final int PROD_STATUS_PAUSE = 2; // ��ͣ
	public static final int PROD_STATUS_USER_STOP = 8; // ��ʧ
	public static final int PROD_STATUS_FROZEN = 16; // ����
	public static final int PROD_STATUS_DIS = 13; // ���

	// ����һ��ͨ�ӿ�orderFlag�ڵ㳣��
	public static final String WAITACTIVATION_ORDER = "1"; // ��Ҫ�ȴ�����Ķ���

	// ��������ӿڳ���
	public static final String ACTIVEORDER_FLAG = "1"; // ����
	public static final String CANCELORDER_FLAG = "2"; // ����

	// �����ύ�漰��������
	public static final String CHARGE_ITEM_CD_GJF = "621";
	public static final String CHARGE_ITEM_CD_UIM = "90013";
	
	//����ԤԼ״̬
	public static final String PHONE_NUMBER_ID_YU_YUE = "54";

}
