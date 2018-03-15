package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *         table="ACCESS_NUMBER_TYPE"
 *     
*/
public class AccessNumberType implements Serializable {

	private static final long serialVersionUID = 2037456435489626152L;

	private Integer anTypeCd;

	private Integer anTypeGroupCd;

	private String tableName;

	private String name;

	private String description;

	private String managedCode;

	private Integer anLength;

	public AccessNumberType() {
	}

	public AccessNumberType(Integer anTypeCd, Integer anTypeGroupCd, String tableName, String name, String description,
			String managedCode, Integer anLength) {
		this.anTypeCd = anTypeCd;
		this.anTypeGroupCd = anTypeGroupCd;
		this.tableName = tableName;
		this.name = name;
		this.description = description;
		this.managedCode = managedCode;
		this.anLength = anLength;
	}

	public Integer getAnTypeCd() {
		return anTypeCd;
	}

	public void setAnTypeCd(Integer anTypeCd) {
		this.anTypeCd = anTypeCd;
	}

	public Integer getAnTypeGroupCd() {
		return anTypeGroupCd;
	}

	public void setAnTypeGroupCd(Integer anTypeGroupCd) {
		this.anTypeGroupCd = anTypeGroupCd;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getManagedCode() {
		return managedCode;
	}

	public void setManagedCode(String managedCode) {
		this.managedCode = managedCode;
	}

	public Integer getAnLength() {
		return anLength;
	}

	public void setAnLength(Integer anLength) {
		this.anLength = anLength;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("anTypeCd", getAnTypeCd()).toString();
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof AccessNumberType))
			return false;
		AccessNumberType castOther = (AccessNumberType) other;
		return new EqualsBuilder().append(this.getAnTypeCd(), castOther.getAnTypeCd()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAnTypeCd()).toHashCode();
	}

	public String toXML() {
		StringBuffer xmlString = new StringBuffer();
		xmlString.append("<AccessNumberType>");

		//------------ process attribute ------------
		xmlString.append("<anTypeCd>" + anTypeCd + "</anTypeCd>");
		xmlString.append("<anTypeGroupCd>" + anTypeGroupCd + "</anTypeGroupCd>");
		xmlString.append("<tableName>" + tableName + "</tableName>");
		xmlString.append("<name>" + name + "</name>");
		xmlString.append("<description>" + description + "</description>");
		xmlString.append("<managedCode>" + managedCode + "</managedCode>");
		xmlString.append("<anLength>" + anLength + "</anLength>");

		xmlString.append("</AccessNumberType>");
		return xmlString.toString();
	}}