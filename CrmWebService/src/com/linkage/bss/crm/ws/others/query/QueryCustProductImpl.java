/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * @author Administrator
 *
 */
public class QueryCustProductImpl implements QueryProductIntf {

	private String request;
	private CustFacade custFacade;
	private IntfSMO intfSMO;

	public QueryCustProductImpl(String request, IntfSMO intfSMO, CustFacade custFacade) {
		this.request = request;
		this.intfSMO = intfSMO;
		this.custFacade = custFacade;
	}

	public String getProductName() {
		return QueryProductResultUtil.QUERY_CUST_INFO;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getProductResult() throws Exception {
		long time = System.currentTimeMillis();
		Document doc;
		Party party = null;
		try {
			doc = WSUtil.parseXml(request);
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");
			//产品号码类型1,2,4,12,都按接入号处理
			long time1 = System.currentTimeMillis();
			if (QueryProductResultUtil.ACCNBR_TYPE_SET.contains(accNbrType)) {
				party = custFacade.getPartyByAccessNumber(accNbr);
			} else if (QueryProductResultUtil.AccNbrType.ACCOUNT.equals(accNbrType)) {//合同号
				party = custFacade.getPartyByAcctCd(accNbr);
			} else if (QueryProductResultUtil.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {// 客户标识码
				party = custFacade.getPartyByOtherCard(accNbr);
			} else {
				throw new Exception("输入的产品号码类型错误！");
			}
			System.out.println("QueryCustProductImpl party查询" + (System.currentTimeMillis() - time1));

			if (party == null) {
				throw new Exception("根据产品号码类型:" + accNbrType + "和号码:" + accNbr + "未查到客户信息！");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("party", party);
			//查询行业类型cd
			long time2 = System.currentTimeMillis();
			String industryClassCd = intfSMO.getIndustryClasscdByPartyId(party.getPartyId());
			System.out.println("QueryCustProductImpl 查询行业类型：" + (System.currentTimeMillis() - time2));

			if (!StringUtils.isBlank(industryClassCd))
				party.setIndustryClassCd(Integer.valueOf(industryClassCd));
			// 查询客户证件信息
			long time3 = System.currentTimeMillis();
			Map identifyMap = intfSMO.queryIdentifyList(party.getPartyId());
			System.out.println("QueryCustProductImpl 查询客户证件信息：" + (System.currentTimeMillis() - time3));

			map.put("identifyMap", identifyMap);
			long time4 = System.currentTimeMillis();
			// 查询主销售品的一级目录作为客户品牌
			Map custBrand = intfSMO.queryCustBrand(party.getPartyId());
			System.out.println("QueryCustProductImpl 查询主销售品的一级目录作为客户品牌：" + (System.currentTimeMillis() - time4));

			map.put("custBrand", custBrand);
			long time5 = System.currentTimeMillis();
			// 获取客户标识码
			String groupCustSeq = intfSMO.getGroupSeqByPartyId(String.valueOf(party.getPartyId()));
			System.out.println("QueryCustProductImpl 获取客户标识码：" + (System.currentTimeMillis() - time5));

			map.put("groupCustSeq", groupCustSeq);
			long time6 = System.currentTimeMillis();
			//是否有效
			String valid = "0";
			String cd = intfSMO.getValidStrByPartyId(party.getPartyId());
			System.out.println("QueryCustProductImpl 是否有效：" + (System.currentTimeMillis() - time6));

			if ("1100".equals(cd))
				valid = "1";
			map.put("isValid", valid);
			long time7 = System.currentTimeMillis();
			//查询g网imsi
			OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
			if (prod != null) {
				String imsi = intfSMO.getGimsiByProdid(prod.getProdId());
				map.put("gimsi", imsi);
			}
			System.out.println("QueryCustProductImpl 查询g网imsi：" + (System.currentTimeMillis() - time7));

			System.out.println("QueryCustProductImpl耗时" + (System.currentTimeMillis() - time));
			return map;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public int getProductType() {
		return 2;
	}

}
