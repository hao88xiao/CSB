package com.linkage.bss.crm.so.commit.smo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.BaseTest;
import com.linkage.bss.crm.commons.smo.ICommonSMO;
import com.linkage.bss.crm.cust.common.CustDomain;
import com.linkage.bss.crm.cust.smo.ICustBasicSMO;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;

public class SoCommitSMOTest extends BaseTest {

//	@Autowired
//	@Qualifier("soManager.soCommitSMO")
//	private ISoCommitSMO soCommitSMO;
//	
//	@Autowired
//	@Qualifier("custManager.custBasicSMO")
//	private ICustBasicSMO custBasicSMO;
//	
//	@Autowired
//	@Qualifier("crmCommons.commonSMO")
//	private ICommonSMO commonSMO;
//
//	@Test
//	public void testSaveCustInfo() {
//		BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
//		boSeqCalculator.reSetCalculator();
//		
//		Integer areaId = 45101;
//		String partyId = commonSMO.generateSeq(areaId,"PARTY","1");
//		
//		//构造订单购物车
//		JSONObject orderListInfo = new JSONObject();
//		orderListInfo.put("olId", "-1");
//		orderListInfo.put("olNbr", "-1");
//		orderListInfo.put("olTypeCd", 2); //order_list_type 2标示电子渠道
//		orderListInfo.put("staffId", "1001"); //营业受理的员工号 从staffCode取得
//		orderListInfo.put("channelId", "51000000"); 
//		orderListInfo.put("areaId", areaId.toString());
//		orderListInfo.put("statusCd", "P"); //TODO P跟S的区别
//		orderListInfo.put("partyId", partyId);
//		
//		//客户受理单
//		JSONObject custOrderList = new JSONObject();
//		custOrderList.put("colNbr", "-1");
//		custOrderList.put("partyId", partyId);
//		
//		//业务动作信息
//		JSONObject busiOrderInfo = new JSONObject();
//		busiOrderInfo.put("seq", "-1");
//		busiOrderInfo.put("statusCd", "S");
//		
//		//业务对象
//		JSONObject busiObj = new JSONObject();
//		busiObj.put("objId", "");
//		busiObj.put("name", "");
//		
//		//业务动作类型
//		JSONObject boActionType = new JSONObject();
//		boActionType.put("actionClassCd", "1");
//		boActionType.put("boActionTypeCd", "C1");
//		boActionType.put("name", "新增客户");
//		
//		//组件数据
//		JSONArray busiComponentInfoArrary = new JSONArray();
//		JSONObject busiComponentInfo = new JSONObject();
//		busiComponentInfo.put("behaviorFlag", "001111");
//		busiComponentInfo.put("busiComponentCode", "boCustBasicInfos");
//		busiComponentInfoArrary.add(busiComponentInfo);
//		busiComponentInfo = new JSONObject();
//		busiComponentInfo.put("behaviorFlag", "001111");
//		busiComponentInfo.put("busiComponentCode", "boCustIdentityInfos");
//		busiComponentInfoArrary.add(busiComponentInfo);
//		busiComponentInfo = new JSONObject();
//		busiComponentInfo.put("behaviorFlag", "001111");
//		busiComponentInfo.put("busiComponentCode", "boCustExtensionInfos");
//		busiComponentInfoArrary.add(busiComponentInfo);
//		busiComponentInfo = new JSONObject();
//		busiComponentInfo.put("behaviorFlag", "001111");
//		busiComponentInfo.put("busiComponentCode", "boCustSegmentInfos");
//		busiComponentInfoArrary.add(busiComponentInfo);
//
//		//构造客户基本信息
//		JSONArray boCustInfoArray = new JSONArray();
//		JSONObject boCustInfo = new JSONObject();
//		boCustInfo.put("areaId", areaId.toString());
//		boCustInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger()); //TODO 如何生成
//		boCustInfo.put("buiEffTime", "3000-01-01");
//		boCustInfo.put("defaultIdType", 2); //TODO 要用常量
//		boCustInfo.put("mailAddressStr", "证件地址");
//		boCustInfo.put("name", "张飞二");
//		boCustInfo.put("partyTypeCd", "1");
//		boCustInfo.put("simpleSpell", "张飞二");
//		boCustInfo.put("state", "ADD");
//		boCustInfo.put("statusCd", "P");
//		boCustInfo.put("telNumber", "13111111111");
//		boCustInfoArray.add(boCustInfo);
//		
//		//构造客户证件信息
//		JSONArray boCustIdentitieArray = new JSONArray();
//		JSONObject boCustIdentitie = new JSONObject();
//		boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustIdentitie.put("identidiesTypeCd", 2);
//		boCustIdentitie.put("identityNum", "888888");
//		boCustIdentitie.put("state", "ADD");
//		boCustIdentitie.put("statusCd", "P");
//		boCustIdentitieArray.add(boCustIdentitie);
//		boCustIdentitie = new JSONObject();
//		boCustIdentitie.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustIdentitie.put("identidiesTypeCd", CustDomain.PARTY_IDENTITY_TYPE);
//		boCustIdentitie.put("identityNum", custBasicSMO.generatePartyIdentity(Integer.parseInt("45101")));
//		boCustIdentitie.put("state", "ADD");
//		boCustIdentitie.put("statusCd", "P");
//		boCustIdentitieArray.add(boCustIdentitie);
//		
//		//构造客户扩展信息
//		JSONArray boCustProfileArray = new JSONArray();
//		JSONObject boCustProfile = new JSONObject();
//		boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustProfile.put("partyProfileCatgCd", "40074"); //TODO 如果发生改动，怎么同步
//		boCustProfile.put("profileValue", "100086");
//		boCustProfile.put("state", "ADD");
//		boCustProfile.put("statusCd", "P");
//		boCustProfileArray.add(boCustProfile);
//		boCustProfile = new JSONObject();
//		boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustProfile.put("partyProfileCatgCd", "900002"); //TODO 如果发生改动，怎么同步
//		boCustProfile.put("profileValue", "北京大郊亭桥"); 
//		boCustProfile.put("state", "ADD");
//		boCustProfile.put("statusCd", "P");
//		boCustProfileArray.add(boCustProfile);
//		boCustProfile = new JSONObject();
//		boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustProfile.put("partyProfileCatgCd", "40067"); //TODO
//		boCustProfile.put("profileValue", "1142");
//		boCustProfile.put("state", "ADD");
//		boCustProfile.put("statusCd", "P");
//		boCustProfileArray.add(boCustProfile);
//		boCustProfile = new JSONObject();
//		boCustProfile.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustProfile.put("partyProfileCatgCd", "40068"); //TODO
//		boCustProfile.put("profileValue", "1142");
//		boCustProfile.put("state", "ADD");
//		boCustProfile.put("statusCd", "P");
//		boCustProfileArray.add(boCustProfile);
//		
//		//构造客户分段信息
//		JSONArray boCustSegmentInfoArray = new JSONArray();
//		JSONObject boCustSegmentInfo = new JSONObject();
//		boCustSegmentInfo.put("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
//		boCustSegmentInfo.put("segmentId", "405");
//		boCustSegmentInfo.put("state", "ADD");
//		boCustSegmentInfo.put("statusCd", "P");
//		JSONArray partySegmentArray = new JSONArray();
//		JSONObject partySegment = new JSONObject();
//		partySegment.put("partyTypeCd", 1);
//		partySegment.put("segmentIds", JSONArray.fromObject("[405]"));
//		partySegmentArray.add(partySegment);
//		partySegment = new JSONObject();
//		partySegment.put("partyTypeCd", 2);
//		partySegment.put("segmentIds", JSONArray.fromObject("[80004]"));
//		partySegmentArray.add(partySegment);
//		partySegment = new JSONObject();
//		partySegment.put("partyTypeCd", 10);
//		partySegment.put("segmentIds", JSONArray.fromObject("[405]"));
//		partySegmentArray.add(partySegment);
//		boCustSegmentInfo.put("partySegments", partySegmentArray);
//		
//		//组建客户数据
//		JSONObject data = new  JSONObject();
//		data.put("boCustInfos", boCustInfoArray);
//		data.put("boCustIdentities", boCustIdentitieArray);
//		data.put("boCustProfiles", boCustProfileArray);
//		data.put("boCustSegmentInfos", boCustSegmentInfoArray);
//		
//		//组建业务动作
//		JSONObject busiOrder = new JSONObject();
//		busiOrder.put("busiOrderInfo", busiOrderInfo);
//		busiOrder.put("busiObj", busiObj);
//		busiOrder.put("boActionType", boActionType);
//		busiOrder.put("areaId", areaId.toString());
//		busiOrder.put("busiComponentInfos", busiComponentInfoArrary);
//		busiOrder.put("data", data);
//		JSONArray busiOrderArray = new JSONArray();
//		busiOrderArray.add(busiOrder);
//		custOrderList.put("busiOrder", busiOrderArray);
//		
//		//构造业务动作,构造购物车
//		JSONObject orderList = new JSONObject();
//		JSONArray custOrderListArray = new JSONArray();
//		custOrderListArray.add(custOrderList);
//		orderList.put("custOrderList", custOrderListArray);
//		orderList.put("orderListInfo", orderListInfo);
//		JSONObject json = new JSONObject();
//		json.put("orderList", orderList);
//		
//		Long result = soCommitSMO.saveCustInfo(json);
//		System.out.println("result=" + result);
//	}

}
