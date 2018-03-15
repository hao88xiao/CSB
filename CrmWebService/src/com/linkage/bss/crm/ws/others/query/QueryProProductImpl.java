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
			//����accNbr��ȷ������ĺ��룬�������
			String accNbr = WSUtil.getXmlNodeText(doc, "//request/accNbr");
			//����Ĳ�Ʒ�������ͣ�1��������룬2���ͻ�ID��3����ͬ�ţ�13�ͻ���ʶ�롣��3����ͬ�ݲ����ǣ�
			String accNbrType = WSUtil.getXmlNodeText(doc, "//request/accNbrType");

			if (QueryProductResultUtil.ACCNBR_TYPE_SET.contains(accNbrType)) {
				// ���ݽ���Ų��Ҳ�Ʒ��Ϣ
				OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
				if (prod != null) {
					prodId = prod.getProdId();
				} else {
					throw new Exception("���ݽ����δ��ѯ����Ʒ��Ϣ" + accNbr);
				}
			}
			System.out.println("QueryProProductImpl��ѯ��Ʒtime0��" + (System.currentTimeMillis() - time0));

			Map<String, Object> map = new HashMap<String, Object>();
			/* ��ѯ��Ʒ��ϸ��Ϣ */
			long time1 = System.currentTimeMillis();
			OfferProd offerProdDetail = offerSMO.queryOfferProdInstDetailById(prodId,
					CommonDomain.QRY_OFFER_PROD_DETAIL, new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl��ѯ��Ʒ��ϸ��Ϣ��" + (System.currentTimeMillis() - time1));
			map.put("offerProdDetail", offerProdDetail);

			//��ѯ�Ƿ�Ϊ����ivpn��Ʒ
			long time2 = System.currentTimeMillis();
			Map<String, Object> ivpns = intfSMO.getIvpnInfos(prodId);
			System.out.println("QueryProProductImpl��ѯ�Ƿ�Ϊ����ivpn��Ʒ��" + (System.currentTimeMillis() - time2));

			map.put("ivpnInfo", ivpns);

			//�����ӽڵ㡰ICCID������ѯ���û���ICCID,UIM��ICCID��,itemSpecId = 200221,UIM������У��� 2015-09-08
			long time20 = System.currentTimeMillis();
			Map<String, Object> iccIdMap = intfSMO.getIccIdByProdId(prodId,String.valueOf(200221));
			String iccId="";
			if(iccIdMap!=null){
				iccId=iccIdMap.get("ICCID").toString();
			}
			System.out.println("QueryProProductImpl��ѯ���û���ICCID�����أ�" + (System.currentTimeMillis() - time20));

			map.put("ICCID", iccId);
			
			long time21 = System.currentTimeMillis();
			QueryProductMode qpm = getQryProMode(prodId);
			System.out.println("QueryProProductImpl��ò�Ʒ��װ�ࣺ" + (System.currentTimeMillis() - time21));

			map.put("qryProMode", qpm);

			/* ��ѯ��Ʒ���� */
			long time3 = System.currentTimeMillis();
			OfferProd offerProdItem = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_ITEM,
					new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl��ѯ��Ʒ���� ��" + (System.currentTimeMillis() - time3));

			map.put("offerProdItems", offerProdItem);

			/* ��ѯ��Ʒ����(�̿ط���)���� */
			long time4 = System.currentTimeMillis();
			OfferProd offerProdServ = offerSMO.queryOfferProdInstDetailById(prodId, CommonDomain.QRY_OFFER_PROD_SERV,
					new Boolean(ifRemoveIneffected).booleanValue());
			System.out.println("QueryProProductImpl��ѯ��Ʒ����(�̿ط���)����  ��" + (System.currentTimeMillis() - time4));

			map.put("offerServs", offerProdServ);

			//��ѯ�Ƿ�Ϊ����ivpn����
			long time5 = System.currentTimeMillis();
			if (!intfSMO.isLocalIvpn(prodId)) {
				/* ��ѯ��ϲ�Ʒ��Ϣ */
				OfferProd offerProdComps = offerSMO.queryOfferProdInstDetailById(prodId,
						CommonDomain.QRY_OFFER_PROD_COMP, new Boolean(ifRemoveIneffected).booleanValue());
				map.put("offerProdComps", offerProdComps);
			} else {
				List<Map<String, Object>> cmaps = intfSMO.getCompLocalIvpns(prodId);
				map.put("offerProdComps_ivpn", cmaps);
			}
			System.out.println("QueryProProductImpl��ѯ�Ƿ�Ϊ����ivpn���� ��" + (System.currentTimeMillis() - time5));

			System.out.println("QueryProProductImpl��ʱ" + (System.currentTimeMillis() - time));

			return map;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * ��ò�Ʒ��װ��
	 * @return
	 */
	private QueryProductMode getQryProMode(Long prodId) {
		QueryProductMode m = new QueryProductMode();
		//��Ʒ�������
		String prodSpecName = intfSMO.getSpecNameByProdId(prodId);
		m.setProdSpecName(prodSpecName);
		//��Ʒ������������Ʒ
		String offers = intfSMO.getOffersByProdId(String.valueOf(prodId));
		m.setOffers(offers);
		//UMI����
		String uimCardNumber = intfSMO.getUmiCardByProdId(String.valueOf(prodId));
		m.setUimCardNumber(uimCardNumber);
		//��Ʒ״̬���룬���ƺ�װ��ʱ��
		List<Map<String, Object>> stas = intfSMO.getStasByProdId(String.valueOf(prodId));
		if (stas != null && stas.size() > 0) {
			Map<String, Object> map = stas.get(0);
			Object statuscd = map.get("STATUSCD");
			Object createdata = map.get("CREATEDATA");
			String statusName = "";
			if ("12".equals(statuscd))
				statusName = "����";
			else if ("22".equals(statuscd))
				statusName = "����";
			if (statuscd != null)
				m.setProductStatusCd(statuscd.toString());
			m.setProductStatusName(statusName);
			if (createdata != null)
				m.setServCreateDate(createdata.toString());
		}
		//������������
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

		//��ѯ��ַ,Ĭ��Ϊװ����ַ������Ϊ�ͻ���ַ
		String address = intfSMO.getAddressByProdId(prodId);
		if (StringUtils.isBlank(address)) {
			address = intfSMO.getPartyAddByProdId(prodId);
		}
		m.setAddress(address);
		//����ʺ�
		String netAccount = intfSMO.getNetAccountByProdId(prodId);
		m.setNetAccount(netAccount);

		//C��imsi
		String imsi = intfSMO.getCimsiByProdid(prodId);
		m.setImsi(imsi);

		//LTEimsi
		String lte_imsi = intfSMO.getLteImsiByProdid(prodId);
		m.setLte_imsi(lte_imsi);

		//C��esn
		String esn = intfSMO.getEsnByProdid(prodId);
		m.setEsn(esn);
		return m;
	}

	public int getProductType() {
		return 1;
	}

}
