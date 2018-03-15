package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *         table="AN_CHOOSE_TYPE"
 *     
*/
public class AnChooseType implements Serializable {

	private static final long serialVersionUID = -6155864660088444772L;

	private Integer anChooseTypeCd;

	private String name;

	private String description;

	public AnChooseType() {
	}

	public AnChooseType(Integer anChooseTypeCd, String name, String description) {
		this.anChooseTypeCd = anChooseTypeCd;
		this.name = name;
		this.description = description;
	}

	public Integer getAnChooseTypeCd() {
		return anChooseTypeCd;
	}

	public void setAnChooseTypeCd(Integer anChooseTypeCd) {
		this.anChooseTypeCd = anChooseTypeCd;
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
		return new ToStringBuilder(this).append("anChooseTypeCd", getAnChooseTypeCd()).toString();
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof AnChooseType))
			return false;
		AnChooseType castOther = (AnChooseType) other;
		return new EqualsBuilder().append(this.getAnChooseTypeCd(), castOther.getAnChooseTypeCd()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAnChooseTypeCd()).toHashCode();
	}

	public String toXML() {
		StringBuffer xmlString = new StringBuffer();
		xmlString.append("<AnChooseType>");

		//------------ process attribute ------------
		xmlString.append("<anChooseTypeCd>" + anChooseTypeCd + "</anChooseTypeCd>");
		xmlString.append("<name>" + name + "</name>");
		xmlString.append("<description>" + description + "</description>");

		xmlString.append("</AnChooseType>");
		return xmlString.toString();
	}
}