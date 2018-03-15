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
			//��Ʒ��������1,2,4,12,��������Ŵ���
			long time1 = System.currentTimeMillis();
			if (QueryProductResultUtil.ACCNBR_TYPE_SET.contains(accNbrType)) {
				party = custFacade.getPartyByAccessNumber(accNbr);
			} else if (QueryProductResultUtil.AccNbrType.ACCOUNT.equals(accNbrType)) {//��ͬ��
				party = custFacade.getPartyByAcctCd(accNbr);
			} else if (QueryProductResultUtil.AccNbrType.PARTY_IDENTITY.equals(accNbrType)) {// �ͻ���ʶ��
				party = custFacade.getPartyByOtherCard(accNbr);
			} else {
				throw new Exception("����Ĳ�Ʒ�������ʹ���");
			}
			System.out.println("QueryCustProductImpl party��ѯ" + (System.currentTimeMillis() - time1));

			if (party == null) {
				throw new Exception("���ݲ�Ʒ��������:" + accNbrType + "�ͺ���:" + accNbr + "δ�鵽�ͻ���Ϣ��");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("party", party);
			//��ѯ��ҵ����cd
			long time2 = System.currentTimeMillis();
			String industryClassCd = intfSMO.getIndustryClasscdByPartyId(party.getPartyId());
			System.out.println("QueryCustProductImpl ��ѯ��ҵ���ͣ�" + (System.currentTimeMillis() - time2));

			if (!StringUtils.isBlank(industryClassCd))
				party.setIndustryClassCd(Integer.valueOf(industryClassCd));
			// ��ѯ�ͻ�֤����Ϣ
			long time3 = System.currentTimeMillis();
			Map identifyMap = intfSMO.queryIdentifyList(party.getPartyId());
			System.out.println("QueryCustProductImpl ��ѯ�ͻ�֤����Ϣ��" + (System.currentTimeMillis() - time3));

			map.put("identifyMap", identifyMap);
			long time4 = System.currentTimeMillis();
			// ��ѯ������Ʒ��һ��Ŀ¼��Ϊ�ͻ�Ʒ��
			Map custBrand = intfSMO.queryCustBrand(party.getPartyId());
			System.out.println("QueryCustProductImpl ��ѯ������Ʒ��һ��Ŀ¼��Ϊ�ͻ�Ʒ�ƣ�" + (System.currentTimeMillis() - time4));

			map.put("custBrand", custBrand);
			long time5 = System.currentTimeMillis();
			// ��ȡ�ͻ���ʶ��
			String groupCustSeq = intfSMO.getGroupSeqByPartyId(String.valueOf(party.getPartyId()));
			System.out.println("QueryCustProductImpl ��ȡ�ͻ���ʶ�룺" + (System.currentTimeMillis() - time5));

			map.put("groupCustSeq", groupCustSeq);
			long time6 = System.currentTimeMillis();
			//�Ƿ���Ч
			String valid = "0";
			String cd = intfSMO.getValidStrByPartyId(party.getPartyId());
			System.out.println("QueryCustProductImpl �Ƿ���Ч��" + (System.currentTimeMillis() - time6));

			if ("1100".equals(cd))
				valid = "1";
			map.put("isValid", valid);
			long time7 = System.currentTimeMillis();
			//��ѯg��imsi
			OfferProd prod = intfSMO.getProdByAccessNumber(accNbr);
			if (prod != null) {
				String imsi = intfSMO.getGimsiByProdid(prod.getProdId());
				map.put("gimsi", imsi);
			}
			System.out.println("QueryCustProductImpl ��ѯg��imsi��" + (System.currentTimeMillis() - time7));

			System.out.println("QueryCustProductImpl��ʱ" + (System.currentTimeMillis() - time));
			return map;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public int getProductType() {
		return 2;
	}

}
