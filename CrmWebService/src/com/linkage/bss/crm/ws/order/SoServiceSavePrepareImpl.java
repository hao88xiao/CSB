package com.linkage.bss.crm.ws.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.commons.client.ICommonClient;
import com.linkage.bss.crm.intf.util.WSUtil;
import com.linkage.bss.crm.model.OrderList;
import com.linkage.bss.crm.model.PreOrder;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.so.prepare.smo.ISoPrepareSMO;
import com.linkage.bss.crm.so.save.bmo.ISoSaveBMO;
import com.linkage.bss.crm.so.save.smo.ISoSaveSMO;
import com.linkage.bss.crm.soservice.syncso.bmo.ISoServiceBMO;

public class SoServiceSavePrepareImpl implements ISoServiceSavePrepare {
	private static Log log = Log.getLog(SoServiceSavePrepareImpl.class);

	private ISoServiceBMO soServiceBMO;

	// 受理准备服务
	private ISoPrepareSMO soPrepareSMO;
	// 业务动作单保存服务
	private ISoSaveSMO soSaveSMO;
	// 批量受理服务

	private IOfferSMO offerSMO;

	private ISoSaveBMO soSaveBMO;

	private ICommonClient commonClient;

	public void setSoSaveBMO(ISoSaveBMO soSaveBMO) {
		this.soSaveBMO = soSaveBMO;
	}

	public void setCommonClient(ICommonClient commonClient) {
		this.commonClient = commonClient;
	}

	public ISoServiceBMO getSoServiceBMO() {
		return soServiceBMO;
	}

	public void setSoServiceBMO(ISoServiceBMO soServiceBMO) {
		this.soServiceBMO = soServiceBMO;
	}

	public IOfferSMO getOfferSMO() {
		return offerSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	public void setSoPrepareSMO(ISoPrepareSMO soPrepareSMO) {
		this.soPrepareSMO = soPrepareSMO;
	}

	public void setSoSaveSMO(ISoSaveSMO soSaveSMO) {
		this.soSaveSMO = soSaveSMO;
	}

	@Override
	public String savePrepare(JSONObject jsObj) {
		Long olId = Long.valueOf(-1);
		try {
			log.debug("自动接口入参:{}", jsObj.toString());
			// 1. 初始化保存购物车
			JSONObject idJSON = soSaveSMO.saveOrderList(jsObj);
			log.debug("初始化购物车返回值:{}", idJSON.toString());

			jsObj.put("idJSON", idJSON);
			// 1.1 更新购物车ID
			olId = idJSON.getJSONObject("ORDER_LIST-OL_ID").getLong("-1");
			jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").element("olId", olId);
			String olTypeCd = jsObj.getJSONObject("orderList").getJSONObject("orderListInfo").getString("olTypeCd");
			log.debug("更新购物车ID:{},购物车类型:{}", olId, olTypeCd);

			// 2.预受理购物车提交
			log.debug("预受理购物车提交。。。");
			// 定义返回标识，默认=1（成功）
			String res = "1";
			JSONObject jsOrderList = null;
			JSONObject jsOrderListInfo = null;
			// 2.1 获取olId
			if (jsObj.containsKey("orderList")) {
				jsOrderList = jsObj.getJSONObject("orderList");
				if (jsOrderList != null && jsOrderList.containsKey("orderListInfo")) {
					jsOrderListInfo = jsOrderList.getJSONObject("orderListInfo");
				}
				if (jsOrderListInfo != null && jsOrderListInfo.containsKey("olId")) {
					olId = jsOrderListInfo.getLong("olId");
				}
			}
			if (olId == null) {
				return "0";
			}
			// 2.2 保存业务动作
			JSONObject idObj = soSaveSMO.saveOrUpdateBusiOrders(jsObj);
			if (idObj != null) {
				// 2.2.1 新增预受理表
				PreOrder preOrder = new PreOrder();
				preOrder.setSoId(olId);
				preOrder.setPreOrderType(Integer.valueOf(2));
				preOrder.setEndDate(commonClient.generateSysdate());
				soSaveBMO.savePreOrderDynamic(preOrder);
				// 2.2.2 修改购物车类型与状态
				OrderList oList = new OrderList();
				oList.setOlId(olId);
				oList.setOlTypeCd(olTypeCd);
				oList.setStatusCd(CommonDomain.ORDER_STATUS_PREPARE_WAIT);
				soSaveBMO.updateOrderListByPrimaryKeyDynamic(oList);
				// 预受理失效时间
				Map<String, Object> vMap = new HashMap<String, Object>();
				String timeSet = commonClient.querySysConfigByItemSpecId(Integer.valueOf("9999110"));
				vMap.put("olId", olId);
				vMap.put("itemSpecId", "9999110");
				Date aa = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				long myTime = (aa.getTime() / 1000) + 60 * 60 * 24 * (Integer.valueOf(timeSet));
				aa.setTime(myTime * 1000);
				vMap.put("value", format.format(aa));
				soSaveSMO.addOrderListAttr(vMap);
				res = "1";
			} else {
				res = "0";
			}
			log.debug("预受理购物车提交结果 ：{}", res);
			if (res.equals("0")) {
				// 失败后释放资源和作废购物车
				soPrepareSMO.releaseCartByOlId(olId, CommonDomain.CONST_OD_LOCK_STAFF_ID);
			}
			return res;
		} catch (Exception e) {
			log.error("预受理订单提交服务异常:{}", e);
			soPrepareSMO.releaseCartByOlId(olId, CommonDomain.CONST_OD_LOCK_STAFF_ID);
			return "0";
		}
	}
}