package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *         table="PROD_2_AN_REASON"
 *     
*/
public class Prod2AnReason implements Serializable {

	private static final long serialVersionUID = -9149723046364628690L;

	private Integer reasonCd;

	private String name;

	private String description;

	public Prod2AnReason() {
	}

	public Prod2AnReason(Integer reasonCd, String name, String description) {
		this.reasonCd = reasonCd;
		this.name = name;
		this.description = description;
	}

	public Integer getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(Integer reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("reasonCd", getReasonCd()).toString();
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Prod2AnReason))
			return false;
		Prod2AnReason castOther = (Prod2AnReason) other;
		return new EqualsBuilder().append(this.getReasonCd(), castOther.getReasonCd()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getReasonCd()).toHashCode();
	}

	public String toXML() {
		StringBuffer xmlString = new StringBuffer();
		xmlString.append("<Prod2AnReason>");

		//------------ process attribute ------------
		xmlString.append("<reasonCd>" + reasonCd + "</reasonCd>");
		xmlString.append("<name>" + name + "</name>");
		xmlString.append("<description>" + description + "</description>");

		xmlString.append("</Prod2AnReason>");
		return xmlString.toString();
	}
}