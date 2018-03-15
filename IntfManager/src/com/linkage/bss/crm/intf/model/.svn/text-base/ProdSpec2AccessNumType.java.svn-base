package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *     table="PROD_SPEC_2_ACCESS_NUM_TYPE" 
 *
 */
public class ProdSpec2AccessNumType implements Serializable {

	private static final long serialVersionUID = -6214321083190448444L;

	private Integer anTypeCd;

	private Integer prodSpecId;

	private Integer reasonCd;

	private String isPrimary;

	private Integer minQty;

	private Integer maxQty;

	private Integer anChooseTypeCd;

	private String needPw;

	private AccessNumberType accessNumberType;

	private AnChooseType anChooseType;

	private Prod2AnReason prod2AnReason;

	public ProdSpec2AccessNumType() {
	}

	public ProdSpec2AccessNumType(Integer anTypeCd, Integer prodSpecId, Integer reasonCd, String isPrimary,
			Integer minQty, Integer maxQty, Integer anChooseTypeCd, String needPw, AccessNumberType accessNumberType,
			AnChooseType anChooseType, Prod2AnReason prod2AnReason) {
		this.anTypeCd = anTypeCd;
		this.prodSpecId = prodSpecId;
		this.reasonCd = reasonCd;
		this.isPrimary = isPrimary;
		this.minQty = minQty;
		this.maxQty = maxQty;
		this.anChooseTypeCd = anChooseTypeCd;
		this.needPw = needPw;
		this.accessNumberType = accessNumberType;
		this.anChooseType = anChooseType;
		this.prod2AnReason = prod2AnReason;
	}

	public Integer getAnTypeCd() {
		return anTypeCd;
	}

	public void setAnTypeCd(Integer anTypeCd) {
		this.anTypeCd = anTypeCd;
	}

	public Integer getProdSpecId() {
		return prodSpecId;
	}

	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}

	public Integer getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(Integer reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Integer getMinQty() {
		return minQty;
	}

	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	public Integer getAnChooseTypeCd() {
		return anChooseTypeCd;
	}

	public void setAnChooseTypeCd(Integer anChooseTypeCd) {
		this.anChooseTypeCd = anChooseTypeCd;
	}

	public String getNeedPw() {
		return needPw;
	}

	public void setNeedPw(String needPw) {
		this.needPw = needPw;
	}

	public AccessNumberType getAccessNumberType() {
		return accessNumberType;
	}

	public void setAccessNumberType(AccessNumberType accessNumberType) {
		this.accessNumberType = accessNumberType;
	}

	public AnChooseType getAnChooseType() {
		return anChooseType;
	}

	public void setAnChooseType(AnChooseType anChooseType) {
		this.anChooseType = anChooseType;
	}

	public Prod2AnReason getProd2AnReason() {
		return prod2AnReason;
	}

	public void setProd2AnReason(Prod2AnReason prod2AnReason) {
		this.prod2AnReason = prod2AnReason;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("prodSpecId", getProdSpecId()).append("reasonCd", getReasonCd())
				.append("anTypeCd", getAnTypeCd()).toString();
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof ProdSpec2AccessNumType))
			return false;
		ProdSpec2AccessNumType castOther = (ProdSpec2AccessNumType) other;
		return new EqualsBuilder().append(this.getProdSpecId(), castOther.getProdSpecId()).append(this.getReasonCd(),
				castOther.getReasonCd()).append(this.getAnTypeCd(), castOther.getAnTypeCd()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getProdSpecId()).append(getReasonCd()).append(getAnTypeCd()).toHashCode();
	}

	public String toXML() {
		StringBuffer xmlString = new StringBuffer();
		xmlString.append("<ProdSpec2AccessNumType>");

		//------------ process attribute ------------
		xmlString.append("<anTypeCd>" + anTypeCd + "</anTypeCd>");
		xmlString.append("<prodSpecId>" + prodSpecId + "</prodSpecId>");
		xmlString.append("<reasonCd>" + reasonCd + "</reasonCd>");
		xmlString.append("<isPrimary>" + isPrimary + "</isPrimary>");
		xmlString.append("<minQty>" + minQty + "</minQty>");
		xmlString.append("<maxQty>" + maxQty + "</maxQty>");
		xmlString.append("<anChooseTypeCd>" + anChooseTypeCd + "</anChooseTypeCd>");
		xmlString.append("<needPw>" + needPw + "</needPw>");

		//------------ process many-to-one --------------
		if (accessNumberType != null) {
			xmlString.append(accessNumberType.toXML());
		}
		if (anChooseType != null) {
			xmlString.append(anChooseType.toXML());
		}
		if (prod2AnReason != null) {
			xmlString.append(prod2AnReason.toXML());
		}

		xmlString.append("</ProdSpec2AccessNumType>");
		return xmlString.toString();
	}
}