package com.linkage.bss.crm.intf.model;

import java.io.Serializable;
import java.util.List;

public class ServSpecDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long servSpecId;
	private Long servSpecIdReal;
	private String servSpecName;
	private Long groupId;
	private String groupName;
	private String ifDefaultSel;
	private String ifMustSel;
	private String ifExclusive;
	private int minsize;
	private List<ServParam> servParams;

	public Long getServSpecIdReal() {
		return servSpecIdReal;
	}

	public void setServSpecIdReal(Long servSpecIdReal) {
		this.servSpecIdReal = servSpecIdReal;
	}

	public Long getServSpecId() {
		return servSpecId;
	}

	public void setServSpecId(Long servSpecId) {
		this.servSpecId = servSpecId;
	}

	public String getServSpecName() {
		return servSpecName;
	}

	public void setServSpecName(String servSpecName) {
		this.servSpecName = servSpecName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIfDefaultSel() {
		return ifDefaultSel;
	}

	public void setIfDefaultSel(String ifDefaultSel) {
		this.ifDefaultSel = ifDefaultSel;
	}

	public String getIfMustSel() {
		return ifMustSel;
	}

	public void setIfMustSel(String ifMustSel) {
		this.ifMustSel = ifMustSel;
	}

	public String getIfExclusive() {
		return ifExclusive;
	}

	public void setIfExclusive(String ifExclusive) {
		this.ifExclusive = ifExclusive;
	}

	public int getMinsize() {
		return minsize;
	}

	public void setMinsize(int minsize) {
		this.minsize = minsize;
	}

	public List<ServParam> getServParams() {
		return servParams;
	}

	public void setServParams(List<ServParam> servParams) {
		this.servParams = servParams;
	}

}
