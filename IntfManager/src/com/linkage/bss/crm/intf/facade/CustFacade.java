package com.linkage.bss.crm.intf.facade;

import java.util.List;

import com.linkage.bss.crm.model.Party;

public interface CustFacade {

	/**
	 * ����ͻ���Ϣ
	 * 
	 * @param party
	 * @return
	 */
	public Long saveCustInfo(Party party, String areaId, String channelId, String staffId);

	/**
	 * �������������ͷ�����������ѯ�ͻ���Ϣ
	 * 
	 * @param accessNumber
	 * @param areaId
	 * @return
	 */
	public Party getPartyByAccessNumber(String accessNumber);

	/**
	 * �������֤�Ų�ѯ�ͻ���Ϣ
	 * 
	 * @param idCard
	 * @param areaId
	 * @return
	 */
	public Party getPartyByIDCard(String cardNumber);

	/**
	 * ��������֤���Ų�ѯ�ͻ���Ϣ
	 * 
	 * @param idCard
	 * @param areaId
	 * @return
	 */
	public Party getPartyByOtherCard(String cardNumber);

	/**
	 * ���ݿͻ�����ѯ�ͻ���Ϣ
	 * 
	 * @param name
	 * @param areaId
	 * @return
	 */
	public Party getPartyByName(String name);

	/**
	 * ���ݿͻ�ID��ѯ�ͻ���Ϣ
	 * 
	 * @param partyId
	 * @param areaId
	 * @return
	 */
	public Party getPartyById(String partyId);

	/**
	 * �����˻���ͬ�Ų�ѯ�ͻ���Ϣ
	 * 
	 * @param acctCd
	 * @param areaId
	 * @return
	 */
	public Party getPartyByAcctCd(String acctCd);

	/**
	 * �����豸�루���ţ���ѯ�ͻ���Ϣ
	 * 
	 * @param terminalCode
	 * @param areaId
	 * @return
	 */
	public Party getPartyByTerminalCode(String terminalCode);

	/**
	 * �ж��Ƿ����ͬ���ͻ�
	 * 
	 * @param param
	 * @return
	 */
	public boolean isSameCustLocal(String name, String identityType, String identityNum, String partyType,
			String areaId, String partyId);

	/**
	 * ����IDȡ�ò�Ʒ��Ϣ
	 * 
	 * @param partyId
	 * @return
	 */
	public Party getPartyById(Long partyId);

	/**
	 * У��ͻ���ѯ����
	 * 
	 * @param partyId
	 * @param password
	 * @return -1 ��ʶ�ÿͻ�û���������� 1 ��ʶ����Ч������ 0 ��ʶ��Ч������
	 */
	public int isValidQryPwd(Long partyId, String password);

	/**
	 * У��ͻ�ҵ������
	 * 
	 * @param partyId
	 * @param password
	 * @return -1 ��ʶ�ÿͻ�û���������� 1 ��ʶ����Ч������ 0 ��ʶ��Ч������
	 */
	public int isValidBizPwd(Long partyId, String password);

	/**
	 * �޸Ŀͻ�����
	 * 
	 * @param partyId
	 * @param partyPassword
	 * @param passwordtimed
	 *            ����ʧЧʱ��
	 * @return
	 */
	public int changeCustomerPsd(Long partyId, String partyPassword, String passwordtimed);

	/**
	 * ��ѯ�ͻ���Ϣ�б�
	 * 
	 * @param param
	 * @return
	 */
	public List<Party> getPartyListByName(String name);
	/**
	 * ��ѯ�ͻ���Ϣ
	 * �������֤��ѯ�ͻ���Ϣ
	 * @param param
	 * @return
	 */
	public List<Party> getPartyListByIDCard(String cardNumber);
}
