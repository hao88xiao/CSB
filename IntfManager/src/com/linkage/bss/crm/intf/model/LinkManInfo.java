package com.linkage.bss.crm.intf.model;

import java.util.Date;

public class LinkManInfo {
	/** 联系人类型 */
	private int agentKind;
	/** 经营方式 */
	private int buniessType;
	/** 电信负责人 */
	private String telecomOfficial;
	/** 电信负责人电话 */
	private String officialPhone;
	/** 代理商联系人 */
	private String linkManName;
	/** 代理商联系人电话 */
	private String linkPhone;
	/** 代理商联系人性别 */
	private String linkSex;
	/** 代理商联系人出生日期 */
	private Date linkBirthday;
	/** 代理商联系人住址 */
	private String linkAddress;
	/** 代理商联系人邮件 */
	private String linkEmail;
	/** 4:业务联系人 5:技术联系人 6:客服联系人 7:法人 */
	private String supType;
	/** 状态 */
	private String state;
	/** 证件类型 */
	private String cardType;
	/** 证件号码 */
	private String cardNum;
	/** 邮编 */
	private String linkPost;
	/**agentId*/
	private int agentId;
	/**opType*/
	private String opType;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getAgentKind() {
		return agentKind;
	}

	public void setAgentKind(int agentKind) {
		this.agentKind = agentKind;
	}

	public int getBuniessType() {
		return buniessType;
	}

	public void setBuniessType(int buniessType) {
		this.buniessType = buniessType;
	}

	public String getTelecomOfficial() {
		return telecomOfficial;
	}

	public void setTelecomOfficial(String telecomOfficial) {
		this.telecomOfficial = telecomOfficial;
	}

	public String getOfficialPhone() {
		return officialPhone;
	}

	public void setOfficialPhone(String officialPhone) {
		this.officialPhone = officialPhone;
	}

	public String getLinkManName() {
		return linkManName;
	}

	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkSex() {
		return linkSex;
	}

	public void setLinkSex(String linkSex) {
		this.linkSex = linkSex;
	}

	public Date getLinkBirthday() {
		return linkBirthday;
	}

	public void setLinkBirthday(Date linkBirthday) {
		this.linkBirthday = linkBirthday;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getSupType() {
		return supType;
	}

	public void setSupType(String supType) {
		this.supType = supType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getLinkPost() {
		return linkPost;
	}

	public void setLinkPost(String linkPost) {
		this.linkPost = linkPost;
	}
}
