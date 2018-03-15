package com.linkage.bss.crm.intf.model;

import java.io.Serializable;

public class FingerPhotoCutDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long olId;

	private Long boId;

	private String photoCut;

	public Long getOlId() {
		return olId;
	}

	public void setOlId(Long olId) {
		this.olId = olId;
	}

	public Long getBoId() {
		return boId;
	}

	public void setBoId(Long boId) {
		this.boId = boId;
	}

	public String getPhotoCut() {
		return photoCut;
	}

	public void setPhotoCut(String photoCut) {
		this.photoCut = photoCut;
	}

	
}
