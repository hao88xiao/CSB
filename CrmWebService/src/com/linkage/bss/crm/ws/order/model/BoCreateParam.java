package com.linkage.bss.crm.ws.order.model;

import java.util.List;

import org.dom4j.Element;

public class BoCreateParam {
	
	String prodSpecId;  //产品规格id
	Long psOfferSpecId; //产品对应的销售品规格id
	Long prodId;
	Long partyId;
	String linkMan;
	String linkNbr;
	String installDate;
	String areaId;
	String curDate;
	String roleboActionType;
	String RelaRoleCd;
	String RoleState;
	String feeType;
	Boolean isCompOffer = false;//是否是套餐销售品
	List<Element> coItemList = null;//订单属性
	Boolean needDealCompOrder = true;//订单类型1233、1234这类的订单类型crm不需要处理组合关系的变动，服务开通需要用到此时就用到该成员变量
	boolean isNew = false;//是否是新装
	String assistMan;//协销人
	boolean isNeedCreateAcct = false; //是否需要新增帐户
	Long acctId ;//帐户Id
	String acctCd ;//帐户编码
	Long paymentAccountId;//外部支付帐户Id
	Long servId;//服务ID
	
	public Long getServId() {
		return servId;
	}

	public void setServId(Long servId) {
		this.servId = servId;
	}

	public String getProdSpecId() {
		return prodSpecId;
	}

	public void setProdSpecId(String prodSpecId) {
		this.prodSpecId = prodSpecId;
	}

	public Long getPsOfferSpecId() {
		return psOfferSpecId;
	}

	public void setPsOfferSpecId(Long psOfferSpecId) {
		this.psOfferSpecId = psOfferSpecId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getRelaRoleCd() {
		return RelaRoleCd;
	}

	public void setRelaRoleCd(String relaRoleCd) {
		RelaRoleCd = relaRoleCd;
	}

	public String getRoleboActionType() {
		return roleboActionType;
	}

	public void setRoleboActionType(String roleboActionType) {
		this.roleboActionType = roleboActionType;
	}

	public String getRoleState() {
		return RoleState;
	}

	public void setRoleState(String roleState) {
		RoleState = roleState;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkNbr() {
		return linkNbr;
	}

	public void setLinkNbr(String linkNbr) {
		this.linkNbr = linkNbr;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Boolean getIsCompOffer() {
		return isCompOffer;
	}

	public void setIsCompOffer(Boolean isCompOffer) {
		this.isCompOffer = isCompOffer;
	}

	public List<Element> getCoItemList() {
		return coItemList;
	}

	public void setCoItemList(List<Element> coItemList) {
		this.coItemList = coItemList;
	}

	public Boolean getNeedDealCompOrder() {
		return needDealCompOrder;
	}

	public void setNeedDealCompOrder(Boolean needDealCompOrder) {
		this.needDealCompOrder = needDealCompOrder;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getAssistMan() {
		return assistMan;
	}

	public void setAssistMan(String assistMan) {
		this.assistMan = assistMan;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getAcctCd() {
		return acctCd;
	}

	public void setAcctCd(String acctCd) {
		this.acctCd = acctCd;
	}

	public Long getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(Long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	public boolean isNeedCreateAcct() {
		return isNeedCreateAcct;
	}

	public void setNeedCreateAcct(boolean isNeedCreateAcct) {
		this.isNeedCreateAcct = isNeedCreateAcct;
	}
	
}
