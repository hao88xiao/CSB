package com.linkage.bss.crm.intf.common;

import java.io.Serializable;

/**
 * 销售品dto，简化的销售品信息
 * 
 * @author 吴向东 更改 Helen
 */
public class OfferIntf implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 289842197671060683L;

	/**
	 * 销售品ID
	 */
	private Long offerId;

	/**
	 * 销售品规格ID
	 */
	private Long offerSpecId;

	/**
	 * 销售品规格名称
	 */
	private String offerSpecName;

	/**
	 * 销售品类型：分为主销售品和附属销售品
	 */
	private String offerTypeCd;

	/***
	 * 计费类型:是否基础销售品
	 */
	private String billTypeCd;

	/**
	 * 销售品CD
	 */
	private String statusCd;

	/**
	 * 销售品状态
	 */
	private String stateName;

	/**
	 * 是否同一层次
	 */
	private String isSameLevel;

	/**
	 * 开始时间
	 */
	private String startDt;

	/**
	 * 结束时间
	 */
	private String endDt;
	/**
	 * 所属客户ID
	 */
	private Long partyId;

	private Integer areaId;

	/**
	 * 所属客户名称
	 */
	private String partyName;

	private Long parentOfferId;

	private String upgradeSpeedInfo;

	private String summary;
	
	/**
	 * 组合产品的主销售品
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
