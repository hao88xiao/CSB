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
//	// 业务动作信息详情
//	@Test
//	public void testQueryBusiOrderInfoByBoId() {
//		Long boId = Long.valueOf("510032733844");
//		String user = "so";
//		String busiOrderInfo = soDetailSMO.queryBusiOrderInfoByBoId(boId, user);
//		System.out.printf(busiOrderInfo);
//		// {"channelName":"福州研发中心","staffId":1001,"boStatus":"保存未通过校验","boId":510032733844,"olStatus":"预受理待转正","associationName":"黑龙江","associateId":1001,"olId":510000252213,"boActionTypeName":"订购","areaName":"CRM研发部","olNbr":"510000252860","staffName":"黑龙江","soDate":"2012-07-07 14:55:45"}
//	}
//
//	// 根据BOID或OLID获取费用信息
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
//		// {"acctInfos":[{"accNbr":"13329413052","acctItemId":51012847,"acctItemTypeId":10013,"acctItemTypeName":"CDMA入网卡费","action":"ADD","allowStaffId":null,"allowStaffName":"","appCharge":1000,"areaId":45101,"billingCycleId":0,"boActionTypeId":"1","boActionTypeName":"新装","boId":510012641144,"cause":"","charge":10,"chargeModifyReasonCd":null,"createDt":"2012-03-08 10:57:45","custId":513005061453,"custName":"test","dataSourse":"C","dealChannelId":null,"dealStaffId":null,"dealState":"N","infoId":51012846,"invoiceNum":"","isRefund":"","itemSourceId":11,"objId":379,"objectName":"","offerId":"0","offerInstId":null,"olId":510000229803,"otherState":"","payMethod":null,"payMethodId":1,"payMethodKey":"","payMethodName":"","payName":"现金支付","productId":"379","readDate":null,"relaInfoId":null,"servId":513030032471,"soChargeStatusCd":null,"state":"未收费","stateDate":"2012-03-08 10:57:45","statusCd":""},{"accNbr":"13329413052","acctItemId":51012849,"acctItemTypeId":10013,"acctItemTypeName":"CDMA入网卡费","action":"ADD","allowStaffId":null,"allowStaffName":"","appCharge":5000,"areaId":45101,"billingCycleId":0,"boActionTypeId":"1","boActionTypeName":"新装","boId":510012641144,"cause":"","charge":50,"chargeModifyReasonCd":null,"createDt":"2012-03-08 10:57:45","custId":513005061453,"custName":"test","dataSourse":"C","dealChannelId":null,"dealStaffId":null,"dealState":"N","infoId":51012848,"invoiceNum":"","isRefund":"","itemSourceId":11,"objId":379,"objectName":"","offerId":"0","offerInstId":null,"olId":510000229803,"otherState":"","payMethod":null,"payMethodId":1,"payMethodKey":"","payMethodName":"","payName":"现金支付","productId":"379","readDate":null,"relaInfoId":null,"servId":513030032471,"soChargeStatusCd":null,"state":"未收费","stateDate":"2012-03-08 10:57:45","statusCd":""}]}
//	}
//
//	/*// 管理属性详情
//	@Test
//	public void testBusiOrderAttrInfosDetails() {
//		Long boId = Long.valueOf("510032733844");
//		Long prodId=Long.valueOf("510032733844");
//		String user = "so";
//		String busiOrderAttrInfo = soDetailSMO.queryBusiOrderAttrById(boId,prodId user);
//		System.out.print(busiOrderAttrInfo);
//		// [{"itemSpecName":"联系人","value":"test"},{"itemSpecName":"联系电话","value":"adsa"},{"itemSpecName":"是否异常处理订单","value":"Y"}]
//	}*/
//
//	// 客户信息
//	@Test
//	public void testBoCustOrderInfoDetails() {
//		Long boId = Long.valueOf("510032721606");
//		String user = "so";
//		String custOrderInfo = JsonUtil.getJsonString(soDetailSMO.queryCustOrderByBoId(boId, user));
//		System.out.print(custOrderInfo);
//		// {"boCustIdentityDto":[{"identifyNumber":"123457","identifyTypeName":"军官证","state":"ADD"},{"identifyNumber":"3451146168790000","identifyTypeName":"客户标识码","state":"ADD"}],"boCustInfoDto":[{"addressStr":"","area":"CRM研发部","businessPwd":"","industryClass":"","mailAddressStr":"湖南湘潭","name":"CS003","partyTypeName":"个人","professionName":"","queryPwd":"","simpleSpell":"CS003","state":"ADD"}],"boCustProfileDto":[{"partyProfileCatgCd":"40115","partyProfileCatgName":"客户经理","prodfileValue":"[300000321]张玲玲","state":"ADD"},{"partyProfileCatgCd":"2","partyProfileCatgName":"备用联系电话","prodfileValue":"13322222222","state":"ADD"},{"partyProfileCatgCd":"3","partyProfileCatgName":"第三联系电话","prodfileValue":"13333333333","state":"ADD"},{"partyProfileCatgCd":"40074","partyProfileCatgName":"邮政编码","prodfileValue":"3332122","state":"ADD"},{"partyProfileCatgCd":"900002","partyProfileCatgName":"收件地址","prodfileValue":"北京朝阳区","state":"ADD"},{"partyProfileCatgCd":"40067","partyProfileCatgName":"国籍","prodfileValue":"中华人民共和国","state":"ADD"},{"partyProfileCatgCd":"40068","partyProfileCatgName":"民族","prodfileValue":"汉族","state":"ADD"}],"boCustSegmentDto":[{"segmentName":"空客户群","state":"ADD"}]}
//	}
//
//	// http://crm.bjtest.ctbss.net:7020/BizHall/xrainbow/services/bss.bizHall.offerFacade.querySpComponentListInfo
//	// 产品信息
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
//		// {"boProdOrderInfo":{"boProdOrderInfo":{"boAccountRelas":[{"acctCd":"510006605660","acctId":"513007238939","payMethodName":"","percent":"100","priority":"1","state":"ADD"}],"boCusts":[{"detail":"meco","partyId":"513005061494","partyProductRelaRoleCd":"0","partyProductRelaRoleName":"产权客户","state":"ADD"}],"boProd2Ans":[{"accessNumber":"045153932154","reasonCd":"2","reasonName":"宽带接入帐号","state":"ADD"}],"boProd2Tds":[],"boProdAddresses":[{"addrDetail":"哈尔滨市平江区大井巷123号","addrId":"122776087028","neighborNbr":"","reasonCd":"1","reasonName":"装机地址","state":"ADD"}],"boProdAns":[{"accessNumber":"11980850","prodId":"513030046859","state":"ADD"}],"boProdCompOrders":[],"boProdFeeTypes":[{"detail":"仅后付","feeType":"1","state":"ADD"}],"boProdItems":[{"itemSpecId":"740000003","name":"产品速率","state":"ADD","value":"10M(家客)"},{"itemSpecId":"499000103","name":"物理接入方式","state":"ADD","value":"DSL"},{"itemSpecId":"400007512","name":"预约服务时间","state":"ADD","value":"2012-04-17"},{"itemSpecId":"400007549","name":"用户联系人","state":"ADD","value":"111"},{"itemSpecId":"400007011","name":"互联网行业类型","state":"ADD","value":"其他"},{"itemSpecId":"400007586","name":"用户联系电话","state":"ADD","value":"111"},{"itemSpecId":"400000360","name":"用户装机地址","state":"ADD","value":"哈尔滨市平江区大井巷123号"},{"itemSpecId":"300204","name":"催缴号码","state":"ADD","value":"110"},{"itemSpecId":"400003104","name":"漫游属性","state":"ADD","value":"禁止漫游"},{"itemSpecId":"499000105","name":"是否送终端","state":"ADD","value":"是"},{"itemSpecId":"400007454","name":"渠道性质","state":"ADD","value":"营业厅（自办）"},{"itemSpecId":"400007453","name":"交费方式","state":"ADD","value":"营业厅交费"},{"itemSpecId":"750000001","name":"用户证件类型","state":"ADD","value":"身份证"},{"itemSpecId":"750000005","name":"接入方式","state":"ADD","value":"全部"},{"itemSpecId":"400006910","name":"受理次数","state":"ADD","value":"初次受理"},{"itemSpecId":"400080840","name":"套餐到期提醒","state":"ADD","value":"套餐到期不提醒"},{"itemSpecId":"300203","name":"催缴方式","state":"ADD","value":"语音催缴"}],"boProdRelas":[{"reasonCd":"6","reasonName":"ADSL电话线路共享","relatedProdId":"513030046860","state":"ADD"}],"boProdSpecs":[{"detail":"带宽","prodSpecId":"280000002","state":"ADD"}],"boProdStatus":[{"prodStatusCd":"1","prodStatusName":"在用","state":"ADD"}],"boProdTml":[{"atomActionId":510027154218,"boId":510032738390,"reasonCd":1,"state":"ADD","tmlId":"510010","tmlName":"哈尔滨市交换局67C"},{"atomActionId":510027154224,"boId":510032738390,"reasonCd":1,"state":"DEL","tmlId":"510010","tmlName":"哈尔滨市交换局67C"},{"atomActionId":510027154225,"boId":510032738390,"reasonCd":1,"state":"ADD","tmlId":"51464637","tmlName":"通河交换局30"}],"boServOrders":[{"boId":510012640477,"boServItems":[{"detail":"","itemSpecId":"230504405","name":"国内国际长途","state":"DEL","value":"开通国际长途"}],"servId":511202151346,"servSpecId":"119","servSpecName":"国内长途通话","state":"ADD"}]}}
//	}
//
//	// 销售品信息
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
//		// {"boOfferOrderInfo":{"offerInfos":[{"areaName":"CRM研发部","boActionTypeName":"订购","boId":"510032740749","endDt":"3000-01-01","offerId":"515000762795","offerNbr":"C510121763359","offerSpecName":"[30909327]车机终端定位月功能费50元/月","partyName":"zhu0315","servId":"","startDt":"2012-07-16","state":"ADD","statusCd":"12","statusName":"已生效"}],"ooOwners":[{"boId":"510032740749","detail":"zhu0315","partyId":"513005062414","state":"ADD"}],"ooParams":[{"boId":"510032741832","itemSpecId":"31532","name":"(活动划拨属性_59000分)","servId":"","state":"ADD","value":"59000"}],"ooRoles":[{"accessNumber":"13349316292","boId":"510032740749","custName":"产权客户","detail":"CDMA","objInstId":"513030037418","objType":"产品规格","offerRoleId":"30909327","roleName":"车机终端定位月功能费50元/月","state":"ADD","statusCd":"已生效"}],"ooTimes":[]}}
//
//	}
//
//	// 附属销售品信息
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
//		// {"boAttachOfferOrderInfo":{"offerInfos":[{"areaName":"CRM研发部","boActionTypeName":"订购","boId":"510032740749","endDt":"3000-01-01","offerId":"515000762795","offerNbr":"C510121763359","offerSpecName":"[30909327]车机终端定位月功能费50元/月","partyName":"zhu0315","servId":"","startDt":"2012-07-16","state":"ADD","statusCd":"12","statusName":"已生效"}],"ooOwners":[{"boId":"510032733850","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733844","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733845","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733851","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733849","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733848","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733847","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733843","detail":"test","partyId":"513005063694","state":"ADD"},{"boId":"510032733846","detail":"test","partyId":"513005063694","state":"ADD"}],"ooParams":[{"boId":"510032741832","itemSpecId":"31532","name":"(活动划拨属性_59000分)","servId":"","state":"ADD","value":"59000"}],"ooRoles":[{"accessNumber":"13349316292","boId":"510032740749","custName":"产权客户","detail":"CDMA","objInstId":"513030037418","objType":"产品规格","offerRoleId":"30909327","roleName":"车机终端定位月功能费50元/月","state":"ADD","statusCd":"已生效"}],"ooTimes":[]}}
//
//	}
//
//	// 物品信息
//	@Test
//	public void testQueryCouponInfoByBoId() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510012694812");
//		String user = "so";
//		String couponInfo = soDetailSMO.queryCouponInfoByBoId(boId, user);
//		details.elementOpt("couponInfo", couponInfo);
//		String result = JsonUtil.getJsonString(details);
//		System.out.printf(result);
//		// {"couponInfo":[{"agentName":"uim卡_供应商","couponInstNumber":"8986031080451031922","couponName":"3G卡-128K卡","couponNum":"1","inOutNbr":"-1","inOutType":"出物品","saleName":"前台","state":"N","storeName":"*电信延寿_仓库"}]}
//	}
//
//	// 业务动作发展人信息详情
//	@Test
//	public void testBoDealerAidDetails() {
//		JSONObject details = new JSONObject();
//		Long boId = Long.valueOf("510012646537");
//		String user = "so";
//		String dealerAidInfos = JsonUtil.getJsonString(soQuerySMO.queryBo2StaffById(boId, user));
//		details.elementOpt("dealerAidInfos", dealerAidInfos);
//		String result = JsonUtil.getJsonString(details);
//		System.out.print(result);
//		// {"dealerAidInfos":[{"boId":510012646537,"createDt":"2012-03-13 00:00:00","orgId":null,"ourOrg":null,"ourStaff":{"bindnumber":"","createDt":"2011-03-13 11:30:44","name":"亚联测试工号","ownerDepartment":110000000,"smPartyId":1001,"staffId":1001,"staffNumber":"AL1001"},"party2ProduRole":{"description":"","name":"揽收人","partyProductRelaRoleCd":32},"partyRelaRoleCd":32,"staffId":1001}]}
//	}

}
