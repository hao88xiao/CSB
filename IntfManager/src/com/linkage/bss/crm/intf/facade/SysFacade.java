package com.linkage.bss.crm.intf.facade;

public interface SysFacade {
	/**
	 * ����staffCodeȡ��staffId
	 * 
	 * @param staffCode
	 * @return
	 */
	public String findStaffIdByStaffCode(String staffNumber);

	/**
	 * �ж����Ա���Ƿ�߱��������Ȩ��
	 * 
	 * @param staffId
	 * @param operManageCd
	 * @return
	 */
	public boolean hasOperationSpec(Long staffId, String operManageCd);
}
