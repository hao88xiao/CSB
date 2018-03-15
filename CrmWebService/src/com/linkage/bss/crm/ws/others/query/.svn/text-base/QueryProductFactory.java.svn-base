/**
 * 
 */
package com.linkage.bss.crm.ws.others.query;

import com.linkage.bss.crm.intf.facade.CustFacade;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.offer.smo.IOfferSMO;

/**
 * @author Administrator
 */
public class QueryProductFactory {

	private int code;
	private String request;
	private IntfSMO intfSMO;
	private IOfferSMO offerSMO;
	private CustFacade custFacade;

	public QueryProductFactory(int code, String request, IntfSMO intfSMO, IOfferSMO offerSMO, CustFacade custFacade) {
		this.code = code;
		this.request = request;
		this.intfSMO = intfSMO;
		this.offerSMO = offerSMO;
		this.custFacade = custFacade;
	}

	public QueryProductIntf getQueryPro() {
		switch (code) {
		case 1:
			return new QueryCustProductImpl(request, intfSMO, custFacade);
		case 2:
			return new QueryProProductImpl(request, intfSMO, offerSMO);
		case 3:
			return new QueryAccProductImpl(request, intfSMO, offerSMO);
		case 4:
			return new QuerySaleProductImpl(request, intfSMO, offerSMO);
		default:
			return null;
		}
	}
}
