package com.linkage.bss.crm.intf.util;

/**
 * 受理一点通主键序列号生成工具类
 * @author 景博文
 * @author 唐敏军
 *
 */
public class BoSeqCalculator {
	
	private int seq = 0;
	private int atomActionId = 0;
	private int prodCompId = 0;
	private int servId = 0;//服务实例ID
	private int prodId = 0;//产品实例ID
	private int offerId = 0;//销售品实例ID
	private int offerParamId = 0;//销售品参数实例ID
	private int acctId = 0;//帐户ID
	private int acctCd = 0;//帐户编码
	private int paymentAccountId = 0;//外部支付帐户Id
	
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
	 * 重置产品实例序列号
	 */
	public void reSetProdId() {
		prodId = 0;
	}

	/**
	 * 重置服务实例序列号
	 */
	public void reSetServId() {
		servId = 0;
	}

	/**
	 * 重置销售品实例序列号
	 */
	public void reSetofferId() {
		offerId = 0;
	}

	/**
	 * 重置销售品参数实例序列号
	 */
	public void reSetOfferParamId() {
		offerParamId = 0;
	}
	
	/**
	 * 获取当前产品实例序列号
	 * @return
	 */
	public int getCurProdId() {
		return prodId;
	}

	/**
	 * 获取当前销售品实例序列号
	 * @return
	 */
	public int getCurOfferId() {
		return offerId;
	}

	/**
	 * 获取当前销售品参数实例序列号
	 * @return
	 */
	public int getCurOfferParamId() {
		return offerParamId;
	}
	
	/**
	 * 获取产品实例下一个序列号
	 * @return
	 */
	public int getNextProdId() {
		prodId = prodId - 1;
		return prodId;
	}

	/**
	 * 获取销售品实例下一个序列号
	 * @return
	 */
	public int getNextOfferId() {
		offerId = offerId - 1;
		return offerId;
	}

	/**
	 * 获取销售品参数实例的下一个序列号
	 * @return
	 */
	public int getNextOfferParamId() {
		offerParamId = offerParamId - 1;
		return offerParamId;
	}

	/**
	 * 获取产品实例下一个序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextProdIdInteger() {
		return Integer.valueOf(getNextProdId());
	}

	/**
	 * 获取销售品实例下一个序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextOfferIdInteger() {
		return Integer.valueOf(getNextOfferId());
	}

	/**
	 * 获取销售品参数实例下一个序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextOfferParamIdInteger() {
		return Integer.valueOf(getNextOfferParamId());
	}

	/**
	 * 获取产品实例下一个序列号的String值
	 * @return
	 */
	public String getNextProdIdString() {
		return String.valueOf(getNextProdId());
	}

	/**
	 * 获取销售品实例下一个序列号的String值
	 * @return
	 */
	public String getNextOfferIdString() {
		return String.valueOf(getNextOfferId());
	}

	/**
	 * 获取销售品参数实例下一个序列号的String值
	 * @return
	 */
	public String getNextOfferParamIdString() {
		return String.valueOf(getNextOfferParamId());
	}
	
	
	/**
	 * 重置帐户ID
	 */
	public void reSetAcctId() {
		acctId = 0;
	}

	/**
	 * 重置帐户编码
	 */
	public void reSetAcctCd() {
		acctCd = 0;
	}
	
	/**
	 * 重置外部支付帐户ID
	 */
	public void reSetPaymentAccountId() {
		paymentAccountId = 0;
	}
	
	/**
	 * 获取当前帐户序列号
	 * @return
	 */
	public int getCurAcctId() {
		return acctId;
	}

	/**
	 * 获取当前帐户编码序列号
	 * @return
	 */
	public int getCurAcctCd() {
		return acctCd;
	}
	
	/**
	 * 获取当前外部支付帐户序列号
	 * @return
	 */
	public int getCurPaymentAccountId() {
		return paymentAccountId;
	}
	
	/**
	 * 获取下一个帐户序列号
	 * @return
	 */
	public int getNextAcctId() {
		acctId = acctId - 1;
		return acctId;
	}

	/**
	 * 获取下一个帐户编码序列号
	 * @return
	 */
	public int getNextAcctCd() {
		acctCd = acctCd - 1;
		return acctCd;
	}
	
	/**
	 * 获取下一个外部支付帐户序列号
	 * @return
	 */
	public int getNextPaymentAccountId() {
		paymentAccountId = paymentAccountId - 1;
		return paymentAccountId;
	}
	
	/**
	 * 获取下一个帐户序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextAcctIdInteger() {
		return Integer.valueOf(getNextAcctId());
	}

	/**
	 * 获取下一个帐户序列号的String值
	 * @return
	 */
	public String getNextAcctIdString() {
		return String.valueOf(getNextAcctId());
	}
		
	/**
	 * 获取下一个帐户编码序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextAcctCdInteger() {
		return Integer.valueOf(getNextAcctCd());
	}

	/**
	 * 获取下一个帐户编码序列号的String值
	 * @return
	 */
	public String getNextAcctCdString() {
		return String.valueOf(getNextAcctCd());
	}
	
	/**
	 * 获取下一个帐户序列号的Integer包装对象
	 * @return
	 */
	public Integer getNextPaymentAccountIdInteger() {
		return Integer.valueOf(getNextPaymentAccountId());
	}

	/**
	 * 获取下一个帐户序列号的String值
	 * @return
	 */
	public String getNextPaymentAccountIdString() {
		return String.valueOf(getNextPaymentAccountId());
	}
}
