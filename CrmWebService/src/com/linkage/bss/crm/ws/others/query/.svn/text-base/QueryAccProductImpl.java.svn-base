/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProdAccount;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * @author Administrator
 *
 */
public class QueryAccProductImpl implements QueryProductIntf {

	private String request;
	private IntfSMO intfSMO;
	private IOfferSMO offerSMO;

	public QueryAccProductImpl(String request, IntfSMO intfSMO, IOfferSMO offerSMO) {
		this.request = request;
		this.intfSMO = intfSMO;
		this.offerSMO = offerSMO;
	}

	public String getProductName() {
		return QueryProductResultUtil.QUERY_ACC_INFO;
	}

	public Map<String, Object> getProductResult() throws Exception {
		long time = System.currentTimeMillis();
		Document doc;
		boolean ifRemoveIneffected = true;
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
			/* 查询账户信息 */
			long time1 = System.currentTimeMillis();
			OfferProd offerProdAccounts = offerSMO.queryOfferProdInstDetailById(prodId,
					CommonDomain.QRY_OFFER_PROD_ACCOUNT, new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryAccProductImpl查询账户信息:" + (System.currentTimeMillis() - time1));
			map.put("offerProdAccounts", offerProdAccounts);
			/*账户投递方式*/
			List<Map<String, Object>> mailsList = new ArrayList<Map<String, Object>>();
			if (offerProdAccounts != null) {
				List<OfferProdAccount> accounts = offerProdAccounts.getOfferProdAccounts();
				if (accounts != null && accounts.size() > 0) {
					for (OfferProdAccount op : accounts) {
						List<Map<String, Object>> accMap = intfSMO.getAccMailMap(op.getAcctId());
						if (accMap != null && accMap.size() > 0) {
							accMap.get(0).put("accid", op.getAcctId());
							mailsList.add(accMap.get(0));
						}
					}
				}
			}
			map.put("mailsList", mailsList);
			/* 地区+起止时间 */
			long time2 = System.currentTimeMillis();
			OfferProd offerProdNumbers = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_AN,
					new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryAccProductImpl地区+起止时间 :" + (System.currentTimeMillis() - time2));

			map.put("offerProdNumbers", offerProdNumbers);
			System.out.println("QueryAccProductImpl耗时" + (System.currentTimeMillis() - time));

			return map;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public int getProductType() {
		return 4;
	}

}
