package com.linkage.bss.crm.intf.model;

import java.util.Date;

public class LinkManInfo {
	/** ��ϵ������ */
	private int agentKind;
	/** ��Ӫ��ʽ */
	private int buniessType;
	/** ���Ÿ����� */
	private String telecomOfficial;
	/** ���Ÿ����˵绰 */
	private String officialPhone;
	/** ��������ϵ�� */
	private String linkManName;
	/** ��������ϵ�˵绰 */
	private String linkPhone;
	/** ��������ϵ���Ա� */
	private String linkSex;
	/** ��������ϵ�˳������� */
	private Date linkBirthday;
	/** ��������ϵ��סַ */
	private String linkAddress;
	/** ��������ϵ���ʼ� */
	private String linkEmail;
	/** 4:ҵ����ϵ�� 5:������ϵ�� 6:�ͷ���ϵ�� 7:���� */
	private String supType;
	/** ״̬ */
	private String state;
	/** ֤������ */
	private String cardType;
	/** ֤������ */
	private String cardNum;
	/** �ʱ� */
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
