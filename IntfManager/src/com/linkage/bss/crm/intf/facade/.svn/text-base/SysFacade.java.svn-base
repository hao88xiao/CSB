package com.linkage.bss.crm.intf.facade;

public interface SysFacade {
	/**
	 * 根据staffCode取得staffId
	 * 
	 * @param staffCode
	 * @return
	 */
	public String findStaffIdByStaffCode(String staffNumber);

	/**
	 * 判断这个员工是否具备这个操作权限
	 * 
	 * @param staffId
	 * @param operManageCd
	 * @return
	 */
	public boolean hasOperationSpec(Long staffId, String operManageCd);
}
