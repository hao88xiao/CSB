package com.linkage.bss.crm.so.detail.smo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.BaseTest;
import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.crm.charge.smo.IChargeManagerSMO;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.model.BamAcctItemInfo;
import com.linkage.bss.crm.so.query.smo.ISoQuerySMO;

public class SoDetailSMOTest extends BaseTest {
//	@Autowired
//	@Qualifier("soManager.soDetailSMO")
//	private ISoDetailSMO soDetailSMO;
//
//	@Autowired
//	@Qualifier("charge.chargeManagerSMO")
//	private IChargeManagerSMO chargeManagerSMO;
//
//	@Autowired
//	@Qualifier("soManager.soQuerySMO")
//	private ISoQuerySMO soQuerySMO;
//
//	// ҵ������Ϣ����
//	@Test
//	public void testQueryBusiOrderInfoByBoId() {
//		Long boId = Long.valueOf("510032733844");
//		String user = "so";
//		String busiOrderInfo = soDetailSMO.queryBusiOrderInfoByBoId(boId, user);
//		System.out.printf(busiOrderInfo);
//		// {"channelName":"�����з�����","staffId":1001,"boStatus":"����δͨ��У��","boId":510032733844,"olStatus":"Ԥ�����ת��","associationName":"������","associateId":1001,"olId":510000252213,"boActionTypeName":"����","areaName":"CRM�з���","olNbr":"510000252860","staffName":"������","soDate":"2012-07-07 14:55:45"}
//	}
//
//	// ����BOID��OLID��ȡ������Ϣ
//	@Test
//	public void testGetBamAcctInfos() {
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("boId", "");
//		param.put("olId", "510000229803");
//		List<BamAcctItemInfo> list = chargeManagerSMO.getBamAcctInfos(param);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("acctInfos", list);
//		String reJsonString = JsonUtil.getJsonString(resultMap);
//		System.out.print(reJsonString);
//		// {"acctInfos":[{"accNbr":"13329413052","acctItemId":51012847,"acctItemTypeId":10013,"acctItemTypeName":"CDMA��������","action":"ADD","allowStaffId":null,"allowStaffName":"","appCharge":1000,"areaId":45101,"billingCycleId":0,"boActionTypeId":"1","boActionTypeName":"��װ","boId":510012641144,"cause":"","charge":10,"chargeModifyReasonCd":null,"createDt":"2012-03-08 10:57:45","custId":513005061453,"custName":"test","dataSourse":"C","dealChannelId":null,"dealStaffId":null,"dealState":"N","infoId":51012846,"invoiceNum":"","isRefund":"","itemSourceId":11,"objId":379,"objectName":"","offerId":"0","offerInstId":null,"olId":510000229803,"otherState":"","payMethod":null,"payMethodId":1,"payMethodKey":"","payMethodName":"","payName":"�ֽ�֧��","productId":"379","readDate":null,"relaInfoId":null,"servId":513030032471,"soChargeStatusCd":null,"state":"δ�շ�","stateDate":"2012-03-08 10:57:45","statusCd":""},{"accNbr":"13329413052","acctItemId":51012849,"acctItemTypeId":10013,"acctItemTypeName":"CDMA��������","action":"ADD","allowStaffId":null,"allowStaffName":"","appCharge":5000,"areaId":45101,"billingCycleId":0,"boActionTypeId":"1","boActionTypeName":"��װ","boId":510012641144,"cause":"","charge":50,"chargeModifyReasonCd":null,"createDt":"2012-03-08 10:57:45","custId":513005061453,"custName":"test","dataSourse":"C","dealChannelId":null,"dealStaffId":null,"dealState":"N","infoId":51012848,"invoiceNum":"","isRefund":"","itemSourceId":11,"objId":379,"objectName":"","offerId":"0","offerInstId":null,"olId":510000229803,"otherState":"","payMethod":null,"payMethodId":1,"payMethodKey":"","payMethodName":"","payName":"�ֽ�֧��","productId":"379","readDate":null,"relaInfoId":null,"servId":513030032471,"soChargeStatusCd":null,"state":"δ�շ�","stateDate":"2012-03-08 10:57:45","statusCd":""}]}
//	}
//
//	/*// ������������
//	@Test
//	public void testBusiOrderAttrInfosDetails() {
//		Long boId = Long.valueOf("510032733844");
//		Long prodId=Long.valueOf("510032733844");
//		String user = "so";
//		String busiOrderAttrInfo = soDetailSMO.queryBusiOrderAttrById(boId,prodId user);
//		System.out.print(busiOrderAttrInfo);
//		// [{"itemSpecName":"��ϵ��","value":"test"},{"itemSpecName":"��ϵ�绰","value":"adsa"},{"itemSpecName":"�Ƿ��쳣������","value":"Y"}]
//	}*/
//
//	// �ͻ���Ϣ
//	@Test
//	public void testBoCustOrderInfoDetails() {
//		Long boId = Long.valueOf("510032721606");
//		String user = "so";
//		String custOrderInfo = JsonUtil.getJsonString(soDetailSMO.queryCustOrderByBoId(boId, user));
//		System.out.print(custOrderInfo);
//		// {"boCustIdentityDto":[{"identifyNumber":"123457","identifyTypeName":"����֤","state":"ADD"},{"identifyNumber":"3451146168790000","identifyTypeName":"�ͻ���ʶ��","state":"ADD"}],"boCustInfoDto":[{"addressStr":"","area":"CRM�з���","businessPwd":"","industryClass":"","mailAddressStr":"������̶","name":"CS003","partyTypeName":"����","professionName":"","queryPwd":"","simpleSpell":"CS003","state":"ADD"}],"boCustProfileDto":[{"partyProfileCatgCd":"40115","partyProfileCatgName":"�ͻ�����","prodfileValue":"[300000321]������","state":"ADD"},{"partyProfileCatgCd":"2","partyProfileCatgName":"������ϵ�绰","prodfileValue":"13322222222","state":"ADD"},{"partyProfileCatgCd":"3","partyProfileCatgName":"������ϵ�绰","prodfileValue":"13333333333","state":"ADD"},{"partyProfileCatgCd":"40074","partyProfileCatgName":"��������","prodfileValue":"3332122","state":"ADD"},{"partyProfileCatgCd":"900002","partyProfileCatgName":"�ռ���ַ","prodfileValue":"����������","state":"ADD"},{"partyProfileCatgCd":"40067","partyProfileCatgName":"����","prodfileValue":"�л����񹲺͹�","state":"ADD"},{"partyProfileCatgCd":"40068","partyProfileCatgName":"����","prodfileValue":"����","state":"ADD"}],"boCustSegmentDto":[{"segmentName":"�տͻ�Ⱥ","state":"ADD"}]}
//	}
//
//	// http://crm.bjtest.ctbss.net:7020/BizHall/xrainbow/services/bss.bizHall.offerFacade.querySpComponentListInfo
//	// ��Ʒ��Ϣ
//	@Test
//	public void testBoProdOrderInfoDetails() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510032738181");
//		String user = "so";
//		String boProdOrderInfo = JsonUtil.getJsonString(soDetailSMO.queryProdOrderByBoId(boId, CommonDomain.QRY_BO_ALL,
//				user));
//		details.elementOpt("boProdOrderInfo", boProdOrderInfo);
//		String result = JsonUtil.getJsonString(details);
//		System.out.printf(result);
//		// {"boProdOrderInfo":{"boProdOrderInfo":{"boAccountRelas":[{"acctCd":"510006605660","acctId":"513007238939","payMethodName":"","percent":"100","priority":"1","state":"ADD"}],"boCusts":[{"detail":"meco","partyId":"513005061494","partyProductRelaRoleCd":"0","partyProductRelaRoleName":"��Ȩ�ͻ�","state":"ADD"}],"boProd2Ans":[{"accessNumber":"045153932154","reasonCd":"2","reasonName":"��������ʺ�","state":"ADD"}],"boProd2Tds":[],"boProdAddresses":[{"addrDetail":"��������ƽ��������123��","addrId":"122776087028","neighborNbr":"","reasonCd":"1","reasonName":"װ����ַ","state":"ADD"}],"boProdAns":[{"accessNumber":"11980850","prodId":"513030046859","state":"ADD"}],"boProdCompOrders":[],"boProdFeeTypes":[{"detail":"����","feeType":"1","state":"ADD"}],"boProdItems":[{"itemSpecId":"740000003","name":"��Ʒ����","state":"ADD","value":"10M(�ҿ�)"},{"itemSpecId":"499000103","name":"������뷽ʽ","state":"ADD","value":"DSL"},{"itemSpecId":"400007512","name":"ԤԼ����ʱ��","state":"ADD","value":"2012-04-17"},{"itemSpecId":"400007549","name":"�û���ϵ��","state":"ADD","value":"111"},{"itemSpecId":"400007011","name":"��������ҵ����","state":"ADD","value":"����"},{"itemSpecId":"400007586","name":"�û���ϵ�绰","state":"ADD","value":"111"},{"itemSpecId":"400000360","name":"�û�װ����ַ","state":"ADD","value":"��������ƽ��������123��"},{"itemSpecId":"300204","name":"�߽ɺ���","state":"ADD","value":"110"},{"itemSpecId":"400003104","name":"��������","state":"ADD","value":"��ֹ����"},{"itemSpecId":"499000105","name":"�Ƿ����ն�","state":"ADD","value":"��"},{"itemSpecId":"400007454","name":"��������","state":"ADD","value":"Ӫҵ�����԰죩"},{"itemSpecId":"400007453","name":"���ѷ�ʽ","state":"ADD","value":"Ӫҵ������"},{"itemSpecId":"750000001","name":"�û�֤������","state":"ADD","value":"���֤"},{"itemSpecId":"750000005","name":"���뷽ʽ","state":"ADD","value":"ȫ��"},{"itemSpecId":"400006910","name":"�������","state":"ADD","value":"��������"},{"itemSpecId":"400080840","name":"�ײ͵�������","state":"ADD","value":"�ײ͵��ڲ�����"},{"itemSpecId":"300203","name":"�߽ɷ�ʽ","state":"ADD","value":"�����߽�"}],"boProdRelas":[{"reasonCd":"6","reasonName":"ADSL�绰��·����","relatedProdId":"513030046860","state":"ADD"}],"boProdSpecs":[{"detail":"����","prodSpecId":"280000002","state":"ADD"}],"boProdStatus":[{"prodStatusCd":"1","prodStatusName":"����","state":"ADD"}],"boProdTml":[{"atomActionId":510027154218,"boId":510032738390,"reasonCd":1,"state":"ADD","tmlId":"510010","tmlName":"�������н�����67C"},{"atomActionId":510027154224,"boId":510032738390,"reasonCd":1,"state":"DEL","tmlId":"510010","tmlName":"�������н�����67C"},{"atomActionId":510027154225,"boId":510032738390,"reasonCd":1,"state":"ADD","tmlId":"51464637","tmlName":"ͨ�ӽ�����30"}],"boServOrders":[{"boId":510012640477,"boServItems":[{"detail":"","itemSpecId":"230504405","name":"���ڹ��ʳ�;","state":"DEL","value":"��ͨ���ʳ�;"}],"servId":511202151346,"servSpecId":"119","servSpecName":"���ڳ�;ͨ��","state":"ADD"}]}}
//	}
//
//	// ����Ʒ��Ϣ
//	@Test
//	public void testBoOfferOrderInfoDetails() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510012646709");
//		String user = "so";
//		String boOfferOrderInfo = JsonUtil.getJsonString(soDetailSMO.queryOfferOrderByBoId(boId,
//				CommonDomain.QRY_BO_ALL, user));
//		details.elementOpt("boOfferOrderInfo", boOfferOrderInfo);
//		String result = JsonUtil.getJsonString(details);
//		System.out.printf(result);
//		// {"boOfferOrderInfo":{"offerInfos":[{"areaName":"CRM�з���","boActionTypeName":"����","boId":"510032740749","endDt":"3000-01-01","offerId":"515000762795","offerNbr":"C510121763359","offerSpecName":"[30909327]�����ն˶�λ�¹��ܷ�50Ԫ/��","partyName":"zhu0315","servId":"","startDt":"2012-07-16","state":"ADD","statusCd":"12","statusName":"����Ч"}],"ooOwners":[{"boId":"510032740749","detail":"zhu0315","partyId":"513005062414","state":"ADD"}],"ooParams":[{"boId":"510032741832","itemSpecId":"31532","name":"(���������_59000��)","servId":"","state":"ADD","value":"59000"}],"ooRoles":[{"accessNumber":"13349316292","boId":"510032740749","custName":"��Ȩ�ͻ�","detail":"CDMA","objInstId":"513030037418","objType":"��Ʒ���","offerRoleId":"30909327","roleName":"�����ն˶�λ�¹��ܷ�50Ԫ/��","state":"ADD","statusCd":"����Ч"}],"ooTimes":[]}}
//
//	}
//
//	// ��������Ʒ��Ϣ
//	@Test
//	public void testQueryAttachOfferOrderInfoDetails() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510032740749");
//		String user = "so";
//		String boAttachOfferOrderInfo = JsonUtil.getJsonString(soDetailSMO.queryAttachOfferOrderByBoId(boId,
//				CommonDomain.QRY_BO_ALL, user));
//		details.elementOpt("boAttachOfferOrderInfo", boAttachOfferOrderInfo);
//		String result = JsonUtil.getJsonString(details);
//		System.out.printf(result);
//		// {"boAttachOfferOrderInfo":{"offerInfos":[{"areaName":"CRM�з���","boActionTypeName":"����","boId":"510032740749","endDt":"3000-01-01","offerId":"515000762795","offerNbr":"C510121763359","offerSpecName":"[30909327]�����ն˶�λ�¹��ܷ�50Ԫ/��","partyName":"zhu0315","servId":"","startDt":"2012-07-16","state":"ADD","statusCd":"12","statusName":"����Ч"}],"ooOwners":[{"boId":"510032733850","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733844","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733845","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733851","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733849","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733848","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733847","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733843","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733846","detail":"test","partyId":"513005063694","state":"ADD"}],"ooParams":[{"boId":"510032741832","itemSpecId":"31532","name":"(���������_59000��)","servId":"","state":"ADD","value":"59000"}],"ooRoles":[{"accessNumber":"13349316292","boId":"510032740749","custName":"��Ȩ�ͻ�","detail":"CDMA","objInstId":"513030037418","objType":"��Ʒ���","offerRoleId":"30909327","roleName":"�����ն˶�λ�¹��ܷ�50Ԫ/��","state":"ADD","statusCd":"����Ч"}],"ooTimes":[]}}
//
//	}
//
//	// ��Ʒ��Ϣ
//	@Test
//	public void testQueryCouponInfoByBoId() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510012694812");
//		String user = "so";
//		String couponInfo = soDetailSMO.queryCouponInfoByBoId(boId, user);
//		details.elementOpt("couponInfo", couponInfo);
//		String result = JsonUtil.getJsonString(details);
//		System.out.printf(result);
//		// {"couponInfo":[{"agentName":"uim��_��Ӧ��","couponInstNumber":"8986031080451031922","couponName":"3G��-128K��","couponNum":"1","inOutNbr":"-1","inOutType":"����Ʒ","saleName":"ǰ̨","state":"N","storeName":"*��������_�ֿ�"}]}
//	}
//
//	// ҵ������չ����Ϣ����
//	@Test
//	public void testBoDealerAidDetails() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510012646537");
//		String user = "so";
//		String dealerAidInfos = JsonUtil.getJsonString(soQuerySMO.queryBo2StaffById(boId, user));
//		details.elementOpt("dealerAidInfos", dealerAidInfos);
//		String result = JsonUtil.getJsonString(details);
//		System.out.print(result);
//		// {"dealerAidInfos":[{"boId":510012646537,"createDt":"2012-03-13 00:00:00","orgId":null,"ourOrg":null,"ourStaff":{"bindnumber":"","createDt":"2011-03-13 11:30:44","name":"�������Թ���","ownerDepartment":110000000,"smPartyId":1001,"staffId":1001,"staffNumber":"AL1001"},"party2ProduRole":{"description":"","name":"������","partyProductRelaRoleCd":32},"partyRelaRoleCd":32,"staffId":1001}]}
//	}

}
