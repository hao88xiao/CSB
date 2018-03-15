package com.linkage.bss.crm.intf.util;

/**
 * ����һ��ͨ�������к����ɹ�����
 * @author ������
 * @author ������
 *
 */
public class BoSeqCalculator {
	
	private int seq = 0;
	private int atomActionId = 0;
	private int prodCompId = 0;
	private int servId = 0;//����ʵ��ID
	private int prodId = 0;//��Ʒʵ��ID
	private int offerId = 0;//����Ʒʵ��ID
	private int offerParamId = 0;//����Ʒ����ʵ��ID
	private int acctId = 0;//�ʻ�ID
	private int acctCd = 0;//�ʻ�����
	private int paymentAccountId = 0;//�ⲿ֧���ʻ�Id
	
	public void reSetSeq() {
		seq = 0;
	}
	
	public void reSetAtomActionId() {
		atomActionId = 0;
	}
	
	public void reSetCalculator() {
		seq = 0;
		atomActionId = 0;
		prodCompId = 0;
		servId = 0;
		prodId = 0;
		offerId = 0;
		offerParamId = 0;
	}
	
	public int getCurSeq() {
		return seq;
	}
	
	public int getNextSeq() {
		seq = seq - 1;
		return seq;
	}
	
	public Integer getNextSeqInteger() {
		return Integer.valueOf(getNextSeq());
	}

	public String getNextSeqString() {
		return String.valueOf(getNextSeq());
	}
	
	
	public int getCurAtomActionId() {
		return atomActionId;
	}
	
	public int getNextAtomActionId() {
		atomActionId = atomActionId - 1;
		return atomActionId;
	}
	
	public Integer getNextAtomActionIdInteger() {
		return Integer.valueOf(getNextAtomActionId());
	}

	public String getNextAtomActionIdString() {
		return String.valueOf(getNextAtomActionId());
	}
	
	public int getCurProdCompId() {
		return prodCompId;
	}
	
	public int getNextProdCompId() {
		prodCompId = prodCompId - 1;
		return prodCompId;
	}
	
	public Integer getNextProdCompIdInteger() {
		return Integer.valueOf(getNextProdCompId());
	}

	public String getNextProdCompIdString() {
		return String.valueOf(getNextProdCompId());
	}
	
	public int getCurServId() {
		return servId;
	}

	public int getNextServId() {
		servId = servId - 1;
		return servId;
	}

	public Integer getNextServIdInteger() {
		return Integer.valueOf(getNextServId());
	}

	public String getNextServIdString() {
		return String.valueOf(getNextServId());
	}
	
	/**
	 * ���ò�Ʒʵ�����к�
	 */
	public void reSetProdId() {
		prodId = 0;
	}

	/**
	 * ���÷���ʵ�����к�
	 */
	public void reSetServId() {
		servId = 0;
	}

	/**
	 * ��������Ʒʵ�����к�
	 */
	public void reSetofferId() {
		offerId = 0;
	}

	/**
	 * ��������Ʒ����ʵ�����к�
	 */
	public void reSetOfferParamId() {
		offerParamId = 0;
	}
	
	/**
	 * ��ȡ��ǰ��Ʒʵ�����к�
	 * @return
	 */
	public int getCurProdId() {
		return prodId;
	}

	/**
	 * ��ȡ��ǰ����Ʒʵ�����к�
	 * @return
	 */
	public int getCurOfferId() {
		return offerId;
	}

	/**
	 * ��ȡ��ǰ����Ʒ����ʵ�����к�
	 * @return
	 */
	public int getCurOfferParamId() {
		return offerParamId;
	}
	
	/**
	 * ��ȡ��Ʒʵ����һ�����к�
	 * @return
	 */
	public int getNextProdId() {
		prodId = prodId - 1;
		return prodId;
	}

	/**
	 * ��ȡ����Ʒʵ����һ�����к�
	 * @return
	 */
	public int getNextOfferId() {
		offerId = offerId - 1;
		return offerId;
	}

	/**
	 * ��ȡ����Ʒ����ʵ������һ�����к�
	 * @return
	 */
	public int getNextOfferParamId() {
		offerParamId = offerParamId - 1;
		return offerParamId;
	}

	/**
	 * ��ȡ��Ʒʵ����һ�����кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextProdIdInteger() {
		return Integer.valueOf(getNextProdId());
	}

	/**
	 * ��ȡ����Ʒʵ����һ�����кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextOfferIdInteger() {
		return Integer.valueOf(getNextOfferId());
	}

	/**
	 * ��ȡ����Ʒ����ʵ����һ�����кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextOfferParamIdInteger() {
		return Integer.valueOf(getNextOfferParamId());
	}

	/**
	 * ��ȡ��Ʒʵ����һ�����кŵ�Stringֵ
	 * @return
	 */
	public String getNextProdIdString() {
		return String.valueOf(getNextProdId());
	}

	/**
	 * ��ȡ����Ʒʵ����һ�����кŵ�Stringֵ
	 * @return
	 */
	public String getNextOfferIdString() {
		return String.valueOf(getNextOfferId());
	}

	/**
	 * ��ȡ����Ʒ����ʵ����һ�����кŵ�Stringֵ
	 * @return
	 */
	public String getNextOfferParamIdString() {
		return String.valueOf(getNextOfferParamId());
	}
	
	
	/**
	 * �����ʻ�ID
	 */
	public void reSetAcctId() {
		acctId = 0;
	}

	/**
	 * �����ʻ�����
	 */
	public void reSetAcctCd() {
		acctCd = 0;
	}
	
	/**
	 * �����ⲿ֧���ʻ�ID
	 */
	public void reSetPaymentAccountId() {
		paymentAccountId = 0;
	}
	
	/**
	 * ��ȡ��ǰ�ʻ����к�
	 * @return
	 */
	public int getCurAcctId() {
		return acctId;
	}

	/**
	 * ��ȡ��ǰ�ʻ��������к�
	 * @return
	 */
	public int getCurAcctCd() {
		return acctCd;
	}
	
	/**
	 * ��ȡ��ǰ�ⲿ֧���ʻ����к�
	 * @return
	 */
	public int getCurPaymentAccountId() {
		return paymentAccountId;
	}
	
	/**
	 * ��ȡ��һ���ʻ����к�
	 * @return
	 */
	public int getNextAcctId() {
		acctId = acctId - 1;
		return acctId;
	}

	/**
	 * ��ȡ��һ���ʻ��������к�
	 * @return
	 */
	public int getNextAcctCd() {
		acctCd = acctCd - 1;
		return acctCd;
	}
	
	/**
	 * ��ȡ��һ���ⲿ֧���ʻ����к�
	 * @return
	 */
	public int getNextPaymentAccountId() {
		paymentAccountId = paymentAccountId - 1;
		return paymentAccountId;
	}
	
	/**
	 * ��ȡ��һ���ʻ����кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextAcctIdInteger() {
		return Integer.valueOf(getNextAcctId());
	}

	/**
	 * ��ȡ��һ���ʻ����кŵ�Stringֵ
	 * @return
	 */
	public String getNextAcctIdString() {
		return String.valueOf(getNextAcctId());
	}
		
	/**
	 * ��ȡ��һ���ʻ��������кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextAcctCdInteger() {
		return Integer.valueOf(getNextAcctCd());
	}

	/**
	 * ��ȡ��һ���ʻ��������кŵ�Stringֵ
	 * @return
	 */
	public String getNextAcctCdString() {
		return String.valueOf(getNextAcctCd());
	}
	
	/**
	 * ��ȡ��һ���ʻ����кŵ�Integer��װ����
	 * @return
	 */
	public Integer getNextPaymentAccountIdInteger() {
		return Integer.valueOf(getNextPaymentAccountId());
	}

	/**
	 * ��ȡ��һ���ʻ����кŵ�Stringֵ
	 * @return
	 */
	public String getNextPaymentAccountIdString() {
		return String.valueOf(getNextPaymentAccountId());
	}
}
