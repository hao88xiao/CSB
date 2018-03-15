package com.linkage.bss.crm.intf.common;

import java.io.Serializable;

/**
 * ����Ʒdto���򻯵�����Ʒ��Ϣ
 * 
 * @author ���� ���� Helen
 */
public class OfferIntf implements Serializable {
	/**
	 * ���л�ID
	 */
	private static final long serialVersionUID = 289842197671060683L;

	/**
	 * ����ƷID
	 */
	private Long offerId;

	/**
	 * ����Ʒ���ID
	 */
	private Long offerSpecId;

	/**
	 * ����Ʒ�������
	 */
	private String offerSpecName;

	/**
	 * ����Ʒ���ͣ���Ϊ������Ʒ�͸�������Ʒ
	 */
	private String offerTypeCd;

	/***
	 * �Ʒ�����:�Ƿ��������Ʒ
	 */
	private String billTypeCd;

	/**
	 * ����ƷCD
	 */
	private String statusCd;

	/**
	 * ����Ʒ״̬
	 */
	private String stateName;

	/**
	 * �Ƿ�ͬһ���
	 */
	private String isSameLevel;

	/**
	 * ��ʼʱ��
	 */
	private String startDt;

	/**
	 * ����ʱ��
	 */
	private String endDt;
	/**
	 * �����ͻ�ID
	 */
	private Long partyId;

	private Integer areaId;

	/**
	 * �����ͻ�����
	 */
	private String partyName;

	private Long parentOfferId;

	private String upgradeSpeedInfo;

	private String summary;
	
	/**
	 * ��ϲ�Ʒ��������Ʒ
	 */
	private String prodCompSpecName; 

	public String getProdCompSpecName() {
		return prodCompSpecName;
	}

	public void setProdCompSpecName(String prodCompSpecName) {
		this.prodCompSpecName = prodCompSpecName;
	}
	

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getOfferSpecId() {
		return offerSpecId;
	}

	public void setOfferSpecId(Long offerSpecId) {
		this.offerSpecId = offerSpecId;
	}

	public String getOfferSpecName() {
		return offerSpecName;
	}

	public String getOfferTypeCd() {
		return offerTypeCd;
	}

	public void setOfferTypeCd(String offerTypeCd) {
		this.offerTypeCd = offerTypeCd;
	}

	public String getBillTypeCd() {
		return billTypeCd;
	}

	public void setBillTypeCd(String billTypeCd) {
		this.billTypeCd = billTypeCd;
	}

	public void setOfferSpecName(String offerSpecName) {
		this.offerSpecName = offerSpecName;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getIsSameLevel() {
		return isSameLevel;
	}

	public void setIsSameLevel(String isSameLevel) {
		this.isSameLevel = isSameLevel;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Long getParentOfferId() {
		return parentOfferId;
	}

	public void setParentOfferId(Long parentOfferId) {
		this.parentOfferId = parentOfferId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getUpgradeSpeedInfo() {
		return upgradeSpeedInfo;
	}

	public void setUpgradeSpeedInfo(String upgradeSpeedInfo) {
		this.upgradeSpeedInfo = upgradeSpeedInfo;
	}

}
