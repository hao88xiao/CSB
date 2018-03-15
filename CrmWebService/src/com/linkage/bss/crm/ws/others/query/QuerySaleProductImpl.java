/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;

import com.linkage.bss.crm.dto.OfferDto;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.offer.dto.OfferParamDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * @author Administrator
 *
 */
public class QuerySaleProductImpl implements QueryProductIntf {

	private String request;
	private IntfSMO intfSMO;
	private IOfferSMO offerSMO;

	public QuerySaleProductImpl(String request, IntfSMO intfSMO, IOfferSMO offerSMO) {
		this.request = request;
		this.intfSMO = intfSMO;
		this.offerSMO = offerSMO;
	}

	public String getProductName() {
		return QueryProductResultUtil.QUERY_SALE_INFO;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getProductResult() throws Exception {
		long time = System.currentTimeMillis();
		Document doc;
		List offerParamMapList = new ArrayList();
		try {
			doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			// 根据接入号查找产品信息
			OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
			if (prod == null) {
				throw new Exception("根据接入号未查到产品信息！");
			}
			Long prodId = prod.getProdId();
			Map<String, Object> map = new HashMap<String, Object>();

			/* 查询销售品信息 */
			long time1 = System.currentTimeMillis();
			List<OfferDto> offerList = offerSMO.queryOfferByProdId(prodId, true);
			System.out.println("QuerySaleProductImpl 查询销售品信息:" + (System.currentTimeMillis() - time1));
			long time2 = System.currentTimeMillis();
			for (OfferDto offer : offerList) {

				// 查询所得的offer中只有StatusCd没有名称,在此处查询加入状态名称
				InstStatus instStatus = intfSMO.queryInstStatusByCd(offer.getStatusCd());
				offer.setStateName(instStatus.getName());
				// 获取服务参数
				List<OfferServItemDto> offerServParams = intfSMO.queryOfferServNotInvalid(offer.getOfferId());
				// 获取销售品参数
				List<OfferParamDto> offerParams = offerSMO.queryOfferParamByIdNotInvalid(offer.getOfferId());
				Map offerParamMap = new HashMap();
				if (CollectionUtils.isNotEmpty(offerServParams)) {
					offerParamMap.put("offerServParams", offerServParams);
				} else {
					offerParamMap.put("offerParams", offerParams);
				}
				//获取预付费标志
				String f = "N";
				Long prepayFlag = intfSMO.getPrepayFlagBySpecid(offer.getOfferSpecId());
				if (prepayFlag == 379)
					f = "Y";
				offerParamMap.put("prepayFlag", f);
				offerParamMap.put("offerId", offer.getOfferId());

				offerParamMapList.add(offerParamMap);
			}
			System.out.println("QuerySaleProductImpl 遍历销售品信息:" + (System.currentTimeMillis() - time2));

			map.put("offerList", offerList);
			map.put("offerParamMapList", offerParamMapList);
			System.out.println("QuerySaleProductImpl耗时" + (System.currentTimeMillis() - time));

			return map;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public int getProductType() {
		return 3;
	}

}
