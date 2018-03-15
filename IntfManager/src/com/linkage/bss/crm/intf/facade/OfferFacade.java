package com.linkage.bss.crm.intf.facade;

import java.util.List;
import net.sf.ezmorph.bean.MorphDynaBean;

import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2AccessNumber;
import com.linkage.bss.crm.model.OfferProd2Addr;
import com.linkage.bss.crm.model.OfferProd2Prod;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdAccount;
import com.linkage.bss.crm.model.OfferProdFeeType;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdNumber;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.OfferProdStatus;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;

public interface OfferFacade {

	/**
	 * ���ݿͻ�ID��ѯ��Ʒ��Ϣ
	 * 
	 * @param partyId
	 * @return
	 */
	public List<CommonOfferProdDto> queryAllProdByPartyId(Long partyId,Long prodId);

	/**
	 * ���ݺ�ͬ�Ų�ѯ��Ʒ��Ϣ
	 * 
	 * @param acctCd
	 * @return
	 */
	public List<CommonOfferProdDto> queryCommonOfferProdByAcctCd(String acctCd, String areaId);

	/**
	 * ��ѯ���в�Ʒ��Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd getAllProdInfoByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ����
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd getProdDetailByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ�������
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdNumber getOfferProdNumberByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ���������
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd2AccessNumber getProd2AccessNumberByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ������ϵ
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProd2Prod> getProd2ProdByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ�ն�
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProd2Td> getProd2TDByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ���ѷ�ʽ
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProdFeeType> getProdFeeTypeByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ����
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProdItem> getProdItemByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ���
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdSpec getProdSpecByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ������Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferServ> getProdServByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ������Ϣ��������������
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferServ> getProdServWithItemByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ״̬
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdStatus getProdStatusByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ��ַ
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd2Addr getProd2AddrByProdId(Long prodId);

	/**
	 * ��ѯ�ͻ���Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public Party getPartyByProdId(Long prodId);

	/**
	 * ��ѯ��Ʒ�ʻ���Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdAccount getProd2AccountByProdId(Long prodId);

	/**
	 * ����ID��ѯ�˻���Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public Account getAccountByProdId(Long prodId);

	/**
	 * ���ݲ�Ʒʵ�������Ѷ����ĸ�������Ʒ���Լ���������Ʒ������Ϣ ͬʱ�����Ƿ���Ҫѡ��������Ƿ���Ҫѡ�񹹳ɵı�ʶ��Ϣ
	 * 
	 * @param prodId
	 * @return
	 */
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId);

	public List<MorphDynaBean> queryProdByCompProdSpecId(String partyId, String offerId, Long offerSpecId,
			String roleCD, String objId, String objType);
}
