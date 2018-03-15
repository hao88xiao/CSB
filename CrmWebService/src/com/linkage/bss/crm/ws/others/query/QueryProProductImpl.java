/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.ws.util.WSUtil;

/**
 * @author Administrator
 *
 */
public class QueryProProductImpl implements QueryProductIntf {

	private String request;
	private IntfSMO intfSMO;
	private IOfferSMO offerSMO;

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public QueryProProductImpl(String request, IntfSMO intfSMO, IOfferSMO offerSMO) {
		this.request = request;
		this.intfSMO = intfSMO;
		this.offerSMO = offerSMO;
	}

	public String getProductName() {
		return QueryProductResultUtil.QUERY_PRO_INFO;
	}

	public Map<String, Object> getProductResult() throws Exception {
		long time = System.currentTimeMillis();
		Document doc = null;
		Long prodId = null;
		boolean ifRemoveIneffected = true;
		try {
			long time0 = System.currentTimeMillis();
			doc = WSUtil.parseXml(request);
			//根据accNbr来确定传入的号码，接入号码
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			//输入的产品号码类型：1、接入号码，2、客户ID，3、合同号，13客户标识码。（3、合同暂不考虑）
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");

			if (QueryProductResultUtil.ACCNBR_TYPE_SET.contains(accNbrType)) {
				// 根据接入号查找产品信息
				OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
				if (prod != null) {
					prodId = prod.getProdId();
				} else {
					throw new Exception("根据接入号未查询到产品信息" + accNbr);
				}
			}
			System.out.println("QueryProProductImpl查询产品time0：" + (System.currentTimeMillis() - time0));

			Map<String, Object> map = new HashMap<String, Object>();
			/* 查询产品详细信息 */
			long time1 = System.currentTimeMillis();
			OfferProd offerProdDetail = offerSMO.queryOfferProdInstDetailById(prodId,
					CommonDomain.QRY_OFFER_PROD_DETAIL, new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl查询产品详细信息：" + (System.currentTimeMillis() - time1));
			map.put("offerProdDetail", offerProdDetail);

			//查询是否为本地ivpn产品
			long time2 = System.currentTimeMillis();
			Map<String, Object> ivpns = intfSMO.getIvpnInfos(prodId);
			System.out.println("QueryProProductImpl查询是否为本地ivpn产品：" + (System.currentTimeMillis() - time2));

			map.put("ivpnInfo", ivpns);

			//增加子节点“ICCID”，查询该用户的ICCID,UIM卡ICCID号,itemSpecId = 200221,UIM卡电子校验号 2015-09-08
			long time20 = System.currentTimeMillis();
			Map<String, Object> iccIdMap = intfSMO.getIccIdByProdId(prodId,String.valueOf(200221));
			String iccId="";
			if(iccIdMap!=null){
				iccId=iccIdMap.get("ICCID").toString();
			}
			System.out.println("QueryProProductImpl查询该用户的ICCID并返回：" + (System.currentTimeMillis() - time20));

			map.put("ICCID", iccId);
			
			long time21 = System.currentTimeMillis();
			QueryProductMode qpm = getQryProMode(prodId);
			System.out.println("QueryProProductImpl获得产品包装类：" + (System.currentTimeMillis() - time21));

			map.put("qryProMode", qpm);

			/* 查询产品属性 */
			long time3 = System.currentTimeMillis();
			OfferProd offerProdItem = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_ITEM,
					new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl查询产品属性 ：" + (System.currentTimeMillis() - time3));

			map.put("offerProdItems", offerProdItem);

			/* 查询产品服务(程控服务)集合 */
			long time4 = System.currentTimeMillis();
			OfferProd offerProdServ = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_SERV,
					new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl查询产品服务(程控服务)集合  ：" + (System.currentTimeMillis() - time4));

			map.put("offerServs", offerProdServ);

			//查询是否为本地ivpn壳子
			long time5 = System.currentTimeMillis();
			if (!intfSMO.isLocalIvpn(prodId)) {
				/* 查询组合产品信息 */
				OfferProd offerProdComps = offerSMO.queryOfferProdInstDetailById(prodId,
						CommonDomain.QRY_OFFER_PROD_COMP, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdComps", offerProdComps);
			} else {
				List<Map<String, Object>> cmaps = intfSMO.getCompLocalIvpns(prodId);
				map.put("offerProdComps_ivpn", cmaps);
			}
			System.out.println("QueryProProductImpl查询是否为本地ivpn壳子 ：" + (System.currentTimeMillis() - time5));

			System.out.println("QueryProProductImpl耗时" + (System.currentTimeMillis() - time));

			return map;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 获得产品包装类
	 * @return
	 */
	private QueryProductMode getQryProMode(Long prodId) {
		QueryProductMode m = new QueryProductMode();
		//产品规格名称
		String prodSpecName = intfSMO.getSpecNameByProdId(prodId);
		m.setProdSpecName(prodSpecName);
		//产品订购的主销售品
		String offers = intfSMO.getOffersByProdId(String.valueOf(prodId));
		m.setOffers(offers);
		//UMI卡号
		String uimCardNumber = intfSMO.getUmiCardByProdId(String.valueOf(prodId));
		m.setUimCardNumber(uimCardNumber);
		//产品状态编码，名称和装机时间
		List<Map<String, Object>> stas = intfSMO.getStasByProdId(String.valueOf(prodId));
		if (stas != null && stas.size() > 0) {
			Map<String, Object> map = stas.get(0);
			Object statuscd = map.get("STATUSCD");
			Object createdata = map.get("CREATEDATA");
			String statusName = "";
			if ("12".equals(statuscd))
				statusName = "在用";
			else if ("22".equals(statuscd))
				statusName = "作废";
			if (statuscd != null)
				m.setProductStatusCd(statuscd.toString());
			m.setProductStatusName(statusName);
			if (createdata != null)
				m.setServCreateDate(createdata.toString());
		}
		//局向编码和名称
		List<Map<String, Object>> exchList = intfSMO.getExchsByProdId(prodId);
		if (exchList != null && exchList.size() > 0) {
			Map<String, Object> map = exchList.get(0);
			Object exchCode = map.get("EXCHCODE");
			Object exchName = map.get("EXCHNAME");
			if (exchCode != null)
				m.setExchCode(exchCode.toString());
			if (exchName != null)
				m.setExchName(exchName.toString());
		}

		//查询地址,默认为装机地址，否则为客户地址
		String address = intfSMO.getAddressByProdId(prodId);
		if (StringUtils.isBlank(address)) {
			address = intfSMO.getPartyAddByProdId(prodId);
		}
		m.setAddress(address);
		//宽带帐号
		String netAccount = intfSMO.getNetAccountByProdId(prodId);
		m.setNetAccount(netAccount);

		//C网imsi
		String imsi = intfSMO.getCimsiByProdid(prodId);
		m.setImsi(imsi);

		//LTEimsi
		String lte_imsi = intfSMO.getLteImsiByProdid(prodId);
		m.setLte_imsi(lte_imsi);

		//C网esn
		String esn = intfSMO.getEsnByProdid(prodId);
		m.setEsn(esn);
		return m;
	}

	public int getProductType() {
		return 1;
	}

}
