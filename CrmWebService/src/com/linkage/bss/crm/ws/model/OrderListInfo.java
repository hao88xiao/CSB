package com.linkage.bss.crm.ws.model;

import com.linkage.bss.crm.so.query.dto.SaleOrderInfoDto;

public class OrderListInfo {

	private SaleOrderInfoDto saleOrderInfoDto;// Ӫҵ���ض�����Ϣ
	private String statusCd;// ����״̬CD
	private String statusName;// ����״̬����
	private String olTypeCd;// ��������CD
	private String olTypeName;// ������������
	private String accessNumber;// �������
	private String payIndentId;// ֧����ˮ��
	private String partyName;//�ͻ�����

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPayIndentId() {
		return payIndentId;
	}

	public void setPayIndentId(String payIndentId) {
		this.payIndentId = payIndentId;
	}

	public String getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

	public SaleOrderInfoDto getSaleOrderInfoDto() {
		return saleOrderInfoDto;
	}

	public void setSaleOrderInfoDto(SaleOrderInfoDto saleOrderInfoDto) {
		this.saleOrderInfoDto = saleOrderInfoDto;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getOlTypeCd() {
		return olTypeCd;
	}

	public void setOlTypeCd(String olTypeCd) {
		this.olTypeCd = olTypeCd;
	}

	public String getOlTypeName() {
		return olTypeName;
	}

	public void setOlTypeName(String olTypeName) {
		this.olTypeName = olTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
