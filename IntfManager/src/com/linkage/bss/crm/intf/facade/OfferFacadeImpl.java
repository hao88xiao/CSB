package com.linkage.bss.crm.intf.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.common.IntfDomain;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2AccessNumber;
import com.linkage.bss.crm.model.OfferProd2Addr;
import com.linkage.bss.crm.model.OfferProd2Party;
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
import com.linkage.bss.crm.offer.smo.IOfferSMO;

public class OfferFacadeImpl implements OfferFacade {

	private IOfferSMO offerSMO;

	private IntfSMO intfSMO;

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOfferProdDto> queryAllProdByPartyId(Long partyId, Long prodId) {
		if (partyId == null) {
			throw new IllegalArgumentException("客户ID不能为空");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		param.put("partyId", partyId.toString());
		if (prodId == null) {
			param.put("minRow", 1);
			param.put("maxRow", 6);
		} else if (prodId == 2L) {
			// 政企客户最大查询个数为3个
			param.put("minRow", 1);
			param.put("maxRow", 3);
		} else {
			// 查询子产品信息
			param.put("prodId", prodId);
			result = intfSMO.queryAllChildCompProd(param);
			return result != null ? (List<CommonOfferProdDto>) result.get("offerProdList") : null;
		}
		result = offerSMO.queryAllProdCanFlip(param);
		List<CommonOfferProdDto> prodList = (List<CommonOfferProdDto>) result.get("offerProdList");
		List<CommonOfferProdDto> allProdList = new ArrayList<CommonOfferProdDto>();
		allProdList.addAll(prodList);
		if (CollectionUtils.isNotEmpty(prodList)) { // 获取组合产品的成员产品列表
			String compProdFlag = null;
			for (CommonOfferProdDto commonOfferProdDto : prodList) {
				compProdFlag = intfSMO.ifCompProdByProdSpecId(commonOfferProdDto.getProdSpecId());
				if ("Y".equals(compProdFlag)) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("partyId", partyId);
					params.put("prodId", commonOfferProdDto.getProdId());
					Map<String, Object> childProds = offerSMO.queryAllChildCompProd(params);
					List<CommonOfferProdDto> childProdList = (List<CommonOfferProdDto>) childProds.get("offerProdList");
					if (childProdList.size() >= 10) {
						allProdList.addAll(childProdList.subList(0, 5));
					} else {
						allProdList.addAll(childProdList);
					}
				}
			}
		}
		return allProdList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOfferProdDto> queryCommonOfferProdByAcctCd(String acctCd, String areaId) {
		if (StringUtils.isBlank(acctCd)) {
			throw new IllegalArgumentException("合同号不能为空");
		}

		JSONObject param = new JSONObject();
		param.put("matchType", 3);
		param.put("value", acctCd);
		param.put("areaId", areaId);
		param.put("pageSize", 100);
		param.put("curPage", 1);

		String result = offerSMO.queryCommonOfferProdByConfMap(param);
		if (StringUtils.isNotBlank(result)) {
			Map<String, Object> resultMap = (Map<String, Object>) JsonUtil.getObject(result, Map.class);
			return resultMap != null ? (List<CommonOfferProdDto>) resultMap.get("commonProdList") : null;
		}

		return null;
	}

	@Override
	public OfferProd getAllProdInfoByProdId(Long prodId) {
		return offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_INST_ALL);
	}

	@Override
	public OfferProd getProdDetailByProdId(Long prodId) {
		return offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_DETAIL);
	}

	@Override
	public OfferProdNumber getOfferProdNumberByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_AN);
		if (offerProd == null) {
			return null;
		}

		List<OfferProdNumber> offerProdNumbers = offerProd.getOfferProdNumbers();
		return CollectionUtils.isNotEmpty(offerProdNumbers) ? offerProdNumbers.get(0) : null;
	}

	@Override
	public OfferProd2AccessNumber getProd2AccessNumberByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_2_AN);
		if (offerProd == null) {
			return null;
		}

		List<OfferProd2AccessNumber> offerProd2AccessNumbers = offerProd.getOfferProd2AccessNumbers();
		return CollectionUtils.isNotEmpty(offerProd2AccessNumbers) ? offerProd2AccessNumbers.get(0) : null;
	}

	@Override
	public List<OfferProd2Prod> getProd2ProdByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_2_PROD);
		return offerProd != null ? offerProd.getOfferProd2Prods() : null;
	}

	@Override
	public List<OfferProd2Td> getProd2TDByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_2_TD);
		return offerProd != null ? offerProd.getOfferProd2Tds() : null;
	}

	@Override
	public List<OfferProdFeeType> getProdFeeTypeByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_FEE_TYPE);
		return offerProd != null ? offerProd.getOfferProdFeeTypes() : null;
	}

	@Override
	public List<OfferProdItem> getProdItemByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_ITEM);
		return offerProd != null ? offerProd.getOfferProdItems() : null;
	}

	@Override
	public OfferProdSpec getProdSpecByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_SPEC);
		if (offerProd == null) {
			return null;
		}

		List<OfferProdSpec> offerProdSpecs = offerProd.getOfferProdSpecs();
		return CollectionUtils.isNotEmpty(offerProdSpecs) ? offerProdSpecs.get(0) : null;
	}

	@Override
	public List<OfferServ> getProdServByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_SERV);
		return offerProd != null ? offerProd.getOfferServs() : null;
	}

	@Override
	public List<OfferServ> getProdServWithItemByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_SERV
				+ CommonDomain.QRY_OFFER_PROD_SERV_ITEM);
		return offerProd != null ? offerProd.getOfferServs() : null;
	}

	@Override
	public OfferProdStatus getProdStatusByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_STATUS);

		List<OfferProdStatus> offerProdStatuses = offerProd.getOfferProdStatuses();
		return CollectionUtils.isNotEmpty(offerProdStatuses) ? offerProdStatuses.get(0) : null;
	}

	@Override
	public OfferProd2Addr getProd2AddrByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_2_ADDR);
		if (offerProd == null) {
			return null;
		}

		List<OfferProd2Addr> offerProd2Addrs = offerProd.getOfferProd2Addrs();
		return CollectionUtils.isNotEmpty(offerProd2Addrs) ? offerProd2Addrs.get(0) : null;
	}

	@Override
	public Party getPartyByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_2_PARTY);
		if (offerProd == null) {
			return null;
		}

		List<OfferProd2Party> offerProd2Parties = offerProd.getOfferProd2Parties();
		for (OfferProd2Party a : offerProd2Parties) {
			if (a.getPartyProductRelaRoleCd() == IntfDomain.PARTY_PRODUCT_OWNER) {
				return a.getParty();
			}
		}
		return null;
	}

	@Override
	public OfferProdAccount getProd2AccountByProdId(Long prodId) {
		OfferProd offerProd = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_ACCOUNT);
		if (offerProd == null) {
			return null;
		}

		List<OfferProdAccount> offerProdAccounts = offerProd.getOfferProdAccounts();
		return CollectionUtils.isNotEmpty(offerProdAccounts) ? offerProdAccounts.get(0) : null;
	}

	@Override
	public Account getAccountByProdId(Long prodId) {
		OfferProdAccount offerProdAccount = getProd2AccountByProdId(prodId);
		return offerProdAccount != null ? offerProdAccount.getAccount() : null;
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodId", prodId);
		return offerSMO.queryAttachOfferByProd(param);
	}

	@Override
	public List<MorphDynaBean> queryProdByCompProdSpecId(String partyId, String offerId, Long offerSpecId,
			String roleCD, String objId, String objType) {

		// String objType = "2";
		// String objId = "379" ;

		JSONObject param = new JSONObject();
		JSONArray objList = new JSONArray();
		JSONObject js = new JSONObject();
		JSONArray objIds = new JSONArray();
		objIds.add(0, objId);
		js.elementOpt("objType", objType);
		js.elementOpt("objIds", objIds);

		objList.add(js);
		param.put("jugleUpdate", "N");
		param.put("offerSpecId", offerSpecId);
		param.put("offerId", offerId);
		param.put("partyId", partyId);
		param.elementOpt("objList", objList);

		JSONObject jss = offerSMO.queryExistObjInst(JSONObject.fromObject(param));
		String result = JsonUtil.getJsonString(jss);
		if (StringUtils.isNotBlank(result)) {
		}
		return null;
	}

}
