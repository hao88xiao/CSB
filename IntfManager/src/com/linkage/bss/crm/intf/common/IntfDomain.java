package com.linkage.bss.crm.intf.common;

public class IntfDomain {

	public interface CustQueryType {

		static final String QUERY_BY_NAME = "1";

		static final String QUERY_BY_ACCNBR = "2";

		static final String QUERY_BY_IDENTIFY = "3";
	}

	/** ��ѯȫʡӪҵ�� */
	public static final String OPERATING_ROOM_PROVINCE = "1";
	/** ��ѯ���� */
	public static final String OPERATING_ROOM_CITY = "2";
	/** ��ѯ����ĳ��Ӫҵ�� */
	public static final String OPERATING_ROOM_SOLE = "3";

	public static final String TYPE_SQL = "1";

	public static final String SUCC = "0000SUCC";// ������߲�ѯ�ɹ�
	public static final String SYSE0001 = "SYSE0001";// Ŀǰ�ǲ�ѯ��Ϣ�ӿ���Ϣ��ѯ�����쳣
	public static final String SYSE0002 = "SYSE0002";// Ŀǰ�ǲ�ѯ��Ϣ�ӿڷ������ݿ��쳣

	// ����һ��ͨ�ӿ�businessService
	public static final String CRM_BUSS = "CRM-BUSS";
	public static final String BUSSSYSE0001 = "CRM-BUSSSYSE0001";// �������������ʧ��
	public static final String BUSSSYSE0002 = "CRM-BUSSSYSE0002";// ���ɶ���ʧ��
	public static final String BUSSSYSE0004 = "CRM-BUSSSYSE0004";// �������ݿ��쳣

	/**
	 * ���ݹ��ﳵID��ѯҵ����ID�쳣
	 */
	public final static int CRMSERVICE_ERROR_30015 = 30015;

	public static final String TYPE_ITEM_SPEC = "2";

	public interface CheckGlobalroam {

		// e��Ŀ¼
		static final int CATEGORY_NODE_ID_EJ = -1300001;
		// �����캽Ŀ¼
		static final int CATEGORY_NODE_ID_SWLH = -1300002;
		// ����3GĿ¼
		static final int CATEGORY_NODE_ID_HM = -1300003;
		// ��ݮĿ¼
		static final int CATEGORY_NODE_ID_LX3G = -1300004;
		/** --��������200����ƷĿ¼ */
		static final int CATEGORY_NODE_ID_DX = -1300005;
		static final String ORDER_STATUS_DELETED = "D";

		// �û���ͣ
		static final int USER_STATUS_CD_2 = 2;
		// Ƿͣ(����)
		static final int USER_STATUS_CD_6 = 6;
		// Ƿͣ(˫��)
		static final int USER_STATUS_CD_5 = 5;
		// �ֶ���������(����)
		static final int PAYMENT_ACCOUNT_TYPE_CD_21 = 21;
		// �Զ���������(����)
		static final int PAYMENT_ACCOUNT_TYPE_CD_22 = 22;
		// �ֶ���������(����)
		static final int PAYMENT_ACCOUNT_TYPE_CD_24 = 24;
		// �����Զ�����(��λ)
		static final int PAYMENT_ACCOUNT_TYPE_CD_25 = 25;
		// ��������
		static final int PAYMENT_ACCOUNT_TYPE_CD_3 = 3;

	}
	
	/**
	 * ��Ȩ�ͻ���PartyProductRelaRoleCd
	 */
	public final static int PARTY_PRODUCT_OWNER = 0;
}
