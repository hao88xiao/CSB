package com.linkage.bss.crm.intf.facade;

import java.util.List;

import com.linkage.bss.crm.model.Party;

public interface CustFacade {

	/**
	 * 保存客户信息
	 * 
	 * @param party
	 * @return
	 */
	public Long saveCustInfo(Party party, String areaId, String channelId, String staffId);

	/**
	 * 根据主接入号码和非主接入号码查询客户信息
	 * 
	 * @param accessNumber
	 * @param areaId
	 * @return
	 */
	public Party getPartyByAccessNumber(String accessNumber);

	/**
	 * 根据身份证号查询客户信息
	 * 
	 * @param idCard
	 * @param areaId
	 * @return
	 */
	public Party getPartyByIDCard(String cardNumber);

	/**
	 * 根据其他证件号查询客户信息
	 * 
	 * @param idCard
	 * @param areaId
	 * @return
	 */
	public Party getPartyByOtherCard(String cardNumber);

	/**
	 * 根据客户名查询客户信息
	 * 
	 * @param name
	 * @param areaId
	 * @return
	 */
	public Party getPartyByName(String name);

	/**
	 * 根据客户ID查询客户信息
	 * 
	 * @param partyId
	 * @param areaId
	 * @return
	 */
	public Party getPartyById(String partyId);

	/**
	 * 根据账户合同号查询客户信息
	 * 
	 * @param acctCd
	 * @param areaId
	 * @return
	 */
	public Party getPartyByAcctCd(String acctCd);

	/**
	 * 根据设备码（卡号）查询客户信息
	 * 
	 * @param terminalCode
	 * @param areaId
	 * @return
	 */
	public Party getPartyByTerminalCode(String terminalCode);

	/**
	 * 判断是否存在同名客户
	 * 
	 * @param param
	 * @return
	 */
	public boolean isSameCustLocal(String name, String identityType, String identityNum, String partyType,
			String areaId, String partyId);

	/**
	 * 根据ID取得产品信息
	 * 
	 * @param partyId
	 * @return
	 */
	public Party getPartyById(Long partyId);

	/**
	 * 校验客户查询密码
	 * 
	 * @param partyId
	 * @param password
	 * @return -1 标识该客户没有设置密码 1 标识是有效的密码 0 标识无效的密码
	 */
	public int isValidQryPwd(Long partyId, String password);

	/**
	 * 校验客户业务密码
	 * 
	 * @param partyId
	 * @param password
	 * @return -1 标识该客户没有设置密码 1 标识是有效的密码 0 标识无效的密码
	 */
	public int isValidBizPwd(Long partyId, String password);

	/**
	 * 修改客户密码
	 * 
	 * @param partyId
	 * @param partyPassword
	 * @param passwordtimed
	 *            密码失效时间
	 * @return
	 */
	public int changeCustomerPsd(Long partyId, String partyPassword, String passwordtimed);

	/**
	 * 查询客户信息列表
	 * 
	 * @param param
	 * @return
	 */
	public List<Party> getPartyListByName(String name);
	/**
	 * 查询客户信息
	 * 根据身份证查询客户信息
	 * @param param
	 * @return
	 */
	public List<Party> getPartyListByIDCard(String cardNumber);
}
