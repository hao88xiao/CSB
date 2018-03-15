package com.linkage.bss.crm.intf.model;

import java.util.Date;

public class FingerPhotoCut {
    private Long olId;

    private Long boId;

    private String boActionTypeId;

    private Long prodId;

    private Long staffId;

    private Long partyId;

    private Date creatDt;

    private String extFields1;

    private String extFields2;

    private String extFields3;

    private String auditResult;

    private Long auditStaffId;

    private Date auditDt;
    
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

    public String getBoActionTypeId() {
        return boActionTypeId;
    }

    public void setBoActionTypeId(String boActionTypeId) {
        this.boActionTypeId = boActionTypeId == null ? null : boActionTypeId.trim();
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Date getCreatDt() {
        return creatDt;
    }

    public void setCreatDt(Date creatDt) {
        this.creatDt = creatDt;
    }

    public String getExtFields1() {
        return extFields1;
    }

    public void setExtFields1(String extFields1) {
        this.extFields1 = extFields1 == null ? null : extFields1.trim();
    }

    public String getExtFields2() {
        return extFields2;
    }

    public void setExtFields2(String extFields2) {
        this.extFields2 = extFields2 == null ? null : extFields2.trim();
    }

    public String getExtFields3() {
        return extFields3;
    }

    public void setExtFields3(String extFields3) {
        this.extFields3 = extFields3 == null ? null : extFields3.trim();
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult == null ? null : auditResult.trim();
    }

    public Long getAuditStaffId() {
        return auditStaffId;
    }

    public void setAuditStaffId(Long auditStaffId) {
        this.auditStaffId = auditStaffId;
    }

    public Date getAuditDt() {
        return auditDt;
    }

    public void setAuditDt(Date auditDt) {
        this.auditDt = auditDt;
    }

    public String getPhotoCut() {
        return photoCut;
    }

    public void setPhotoCut(String photoCut) {
        this.photoCut = photoCut == null ? null : photoCut.trim();
    }
    
}