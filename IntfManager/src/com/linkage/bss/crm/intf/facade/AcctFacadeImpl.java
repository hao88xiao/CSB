package com.linkage.bss.crm.intf.facade;

import com.linkage.bss.crm.acct.smo.IAcctSMO;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.model.Account;

public class AcctFacadeImpl implements AcctFacade {

	private IAcctSMO acctSMO;

	@Override
	public Account getAccountById(Long acctId) {
		return acctSMO.queryAcctById(acctId, CommonDomain.QRY_ACCOUNT_BASIC);
	}

	public void setAcctSMO(IAcctSMO acctSMO) {
		this.acctSMO = acctSMO;
	}
}
