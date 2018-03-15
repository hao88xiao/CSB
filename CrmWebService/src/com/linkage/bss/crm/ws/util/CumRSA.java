package com.linkage.bss.crm.ws.util;

public class CumRSA {

	private String rsaModulu;

	private String priP;

	private String priQ;

	private String priDp;

	private String priDq;

	private String priQinv;

	private String priExponet;

	public String getRsaModulu() {
		return rsaModulu;
	}

	public void setRsaModulu(String rsaModulu) {
		this.rsaModulu = rsaModulu == null ? null : rsaModulu.trim();
	}

	public String getPriP() {
		return priP;
	}

	public void setPriP(String priP) {
		this.priP = priP == null ? null : priP.trim();
	}

	public String getPriQ() {
		return priQ;
	}

	public void setPriQ(String priQ) {
		this.priQ = priQ == null ? null : priQ.trim();
	}

	public String getPriDp() {
		return priDp;
	}

	public void setPriDp(String priDp) {
		this.priDp = priDp == null ? null : priDp.trim();
	}

	public String getPriDq() {
		return priDq;
	}

	public void setPriDq(String priDq) {
		this.priDq = priDq == null ? null : priDq.trim();
	}

	public String getPriQinv() {
		return priQinv;
	}

	public void setPriQinv(String priQinv) {
		this.priQinv = priQinv == null ? null : priQinv.trim();
	}

	public String getPriExponet() {
		return priExponet;
	}

	public void setPriExponet(String priExponet) {
		this.priExponet = priExponet == null ? null : priExponet.trim();
	}

}