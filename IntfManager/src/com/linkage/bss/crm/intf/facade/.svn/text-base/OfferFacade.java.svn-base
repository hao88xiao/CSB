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
	 * 根据客户ID查询产品信息
	 * 
	 * @param partyId
	 * @return
	 */
	public List<CommonOfferProdDto> queryAllProdByPartyId(Long partyId,Long prodId);

	/**
	 * 根据合同号查询产品信息
	 * 
	 * @param acctCd
	 * @return
	 */
	public List<CommonOfferProdDto> queryCommonOfferProdByAcctCd(String acctCd, String areaId);

	/**
	 * 查询所有产品信息
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd getAllProdInfoByProdId(Long prodId);

	/**
	 * 查询产品详情
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd getProdDetailByProdId(Long prodId);

	/**
	 * 查询产品主接入号
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdNumber getOfferProdNumberByProdId(Long prodId);

	/**
	 * 查询产品非主接入号
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd2AccessNumber getProd2AccessNumberByProdId(Long prodId);

	/**
	 * 查询产品关联关系
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProd2Prod> getProd2ProdByProdId(Long prodId);

	/**
	 * 查询产品终端
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProd2Td> getProd2TDByProdId(Long prodId);

	/**
	 * 查询产品付费方式
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProdFeeType> getProdFeeTypeByProdId(Long prodId);

	/**
	 * 查询产品属性
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferProdItem> getProdItemByProdId(Long prodId);

	/**
	 * 查询产品规格
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdSpec getProdSpecByProdId(Long prodId);

	/**
	 * 查询产品服务信息
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferServ> getProdServByProdId(Long prodId);

	/**
	 * 查询产品服务信息，包括服务属性
	 * 
	 * @param prodId
	 * @return
	 */
	public List<OfferServ> getProdServWithItemByProdId(Long prodId);

	/**
	 * 查询产品状态
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdStatus getProdStatusByProdId(Long prodId);

	/**
	 * 查询产品地址
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProd2Addr getProd2AddrByProdId(Long prodId);

	/**
	 * 查询客户信息
	 * 
	 * @param prodId
	 * @return
	 */
	public Party getPartyByProdId(Long prodId);

	/**
	 * 查询产品帐户信息
	 * 
	 * @param prodId
	 * @return
	 */
	public OfferProdAccount getProd2AccountByProdId(Long prodId);

	/**
	 * 根据ID查询账户信息
	 * 
	 * @param prodId
	 * @return
	 */
	public Account getAccountByProdId(Long prodId);

	/**
	 * 根据产品实例查找已订购的附属销售品、以及附属销售品构成信息 同时返回是否需要选择参数，是否需要选择构成的标识信息
	 * 
	 * @param prodId
	 * @return
	 */
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId);

	public List<MorphDynaBean> queryProdByCompProdSpecId(String partyId, String offerId, Long offerSpecId,
			String roleCD, String objId, String objType);
}
