package com.linkage.bss.crm.intf.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OfferInfoKF implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long offerSpecId;
	private String offerSpecName;
	private String summary;
	private Long labelId;
	private String labelName;
	private Integer minNumber;
	private Integer maxNumber;
	private Date startDt;
	private Date endDt;
	private String offerTypeCd;
	private List<OfferParamKF> offerParams;
	private List<ServSpecDto> servSpecs;
	private String ifMustSel;

	public List<ServSpecDto> getServSpecs() {
		return servSpecs;
	}

	public void setServSpecs(List<ServSpecDto> servSpecs) {
		this.servSpecs = servSpecs;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	public Integer getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Integer minNumber) {
		this.minNumber = minNumber;
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

	public void setOfferSpecName(String offerSpecName) {
		this.offerSpecName = offerSpecName;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getOfferTypeCd() {
		return offerTypeCd;
	}

	public void setOfferTypeCd(String offerTypeCd) {
		this.offerTypeCd = offerTypeCd;
	}

	public List<OfferParamKF> getOfferParams() {
		return offerParams;
	}

	public void setOfferParams(List<OfferParamKF> offerParams) {
		this.offerParams = offerParams;
	}

	public String getIfMustSel() {
		return ifMustSel;
	}

	public void setIfMustSel(String ifMustSel) {
		this.ifMustSel = ifMustSel;
	}

	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (!(that instanceof OfferInfoKF)) {
			return false;
		}
		OfferInfoKF other = (OfferInfoKF) that;

		return getOfferSpecId() == null ? other.getOfferSpecId() == null : getOfferSpecId().equals(
				other.getOfferSpecId());
	}

	public int hashCode() {
		int hash = 23;
		if (getOfferSpecId() != null) {
			hash *= getOfferSpecId().hashCode();
		}
		return hash;
	}
}
