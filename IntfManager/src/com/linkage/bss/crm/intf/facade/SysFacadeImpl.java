package com.linkage.bss.crm.intf.facade;

import bss.common.BssException;
import bss.systemmanager.provide.SmService;

public class SysFacadeImpl implements SysFacade {
	private SmService smService;

	@Override
	public String findStaffIdByStaffCode(String staffNumber) {
		try {
			
			return smService.findStaffInfoByStaffNumber(staffNumber);
			
		} catch (BssException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean hasOperationSpec(Long staffId, String operManageCd) {
		try {
			return smService.hasOperationSpec(staffId, operManageCd);
		} catch (BssException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void setSmService(SmService smService) {
		this.smService = smService;
	}
}
