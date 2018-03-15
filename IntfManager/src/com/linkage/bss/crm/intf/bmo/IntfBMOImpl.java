package com.linkage.bss.crm.intf.bmo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanUtils;

import bss.common.util.LoginedStaffInfo;
import bss.common.xml.XMLDom4jUtils;
import bss.systemmanager.sys.PropertiesUtils;

import com.linkage.bss.commons.util.BeanUtil;
import com.linkage.bss.commons.util.DateUtil;
import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.commons.util.ListUtil;
import com.linkage.bss.commons.util.Log;
import com.linkage.bss.crm.commons.CommonDomain;
import com.linkage.bss.crm.cust.common.CustDomain;
import com.linkage.bss.crm.cust.service.IPasswordEncoder;
import com.linkage.bss.crm.intf.common.IntfDomain;
import com.linkage.bss.crm.intf.common.OfferIntf;
import com.linkage.bss.crm.intf.dao.IntfDAO;
import com.linkage.bss.crm.intf.model.AreaInfo;
import com.linkage.bss.crm.intf.model.BankTableEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntity;
import com.linkage.bss.crm.intf.model.BcdCodeEntityInputBean;
import com.linkage.bss.crm.intf.model.DiscreateValue;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntity;
import com.linkage.bss.crm.intf.model.InventoryStatisticsEntityInputBean;
import com.linkage.bss.crm.intf.model.LinkManInfo;
import com.linkage.bss.crm.intf.model.NewValidate;
import com.linkage.bss.crm.intf.model.OfferInfoKF;
import com.linkage.bss.crm.intf.model.OfferParamKF;
import com.linkage.bss.crm.intf.model.OperatingOfficeInfo;
import com.linkage.bss.crm.intf.model.ProdByCompProdSpec;
import com.linkage.bss.crm.intf.model.ProdServRela;
import com.linkage.bss.crm.intf.model.ProdSpec2AccessNumType;
import com.linkage.bss.crm.intf.model.ServActivate;
import com.linkage.bss.crm.intf.model.ServActivatePps;
import com.linkage.bss.crm.intf.model.ServParam;
import com.linkage.bss.crm.intf.model.ServSpecDto;
import com.linkage.bss.crm.intf.model.StaticData;
import com.linkage.bss.crm.intf.model.Tel2Area;
import com.linkage.bss.crm.intf.model.YKSXInfo;
import com.linkage.bss.crm.intf.util.BoSeqCalculator;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.BoServOrder;
import com.linkage.bss.crm.model.BusiOrder;
import com.linkage.bss.crm.model.InstStatus;
import com.linkage.bss.crm.model.Offer;
import com.linkage.bss.crm.model.OfferParam;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdComp;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.OfferRoles;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.OfferSpecParam;
import com.linkage.bss.crm.model.RoleObj;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;
import com.linkage.bss.crm.offer.dto.OfferServItemDto;
import com.linkage.bss.crm.offerspec.common.OfferSpecDomain;
import com.linkage.bss.crm.offerspec.dto.AttachOfferInfoDto;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.so.batch.dao.ISoBatchDAO;
import com.linkage.bss.crm.soservice.syncso.smo.ISoServiceSMO;
import com.linkage.bss.crm.unityPay.IndentItemPay;

public class IntfBMOImpl implements IntfBMO {

	private static Log log = Log.getLog(IntfBMOImpl.class);

	private IntfDAO intfDAO;

	private IPasswordEncoder passwordEncoder;

	private ISoServiceSMO soServiceSMO;

	private ISoBatchDAO soBatchDAO;

	public static void setLog(Log log) {
		IntfBMOImpl.log = log;
	}

	public void setSoBatchDAO(ISoBatchDAO soBatchDAO) {
		this.soBatchDAO = soBatchDAO;
	}

	public void setIntfDAO(IntfDAO intfDAO) {
		this.intfDAO = intfDAO;
	}

	public void setPasswordEncoder(IPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setSoServiceSMO(ISoServiceSMO soServiceSMO) {
		this.soServiceSMO = soServiceSMO;
	}

	@Override
	public OfferProd getProdByAccessNumber(String accessNumber) {
		return intfDAO.queryProdByAcessNumber(accessNumber);
	}

	@Override
	public List<StaticData> initStaticData(String id) {
		String[] temp = id.split(",");
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			idList.add(temp[i]);
		}
		List<Map<String, Object>> sqlList = intfDAO.getStaticSql(idList);
		List<StaticData> staticDataList = new ArrayList<StaticData>();
		if (sqlList != null) {
			for (int i = 0; i < sqlList.size(); i++) {
				Map<String, Object> sqlMap = sqlList.get(i);
				List<Map<String, Object>> resultList = null;
				StaticData staticData = new StaticData();
				if (sqlMap.get("SQL_TYPE").toString().equals(IntfDomain.TYPE_SQL)) {
					resultList = intfDAO.getSqlInfo(sqlMap.get("SQL_INFO").toString());
				} else if (sqlMap.get("SQL_TYPE").equals(IntfDomain.TYPE_ITEM_SPEC)) {
					resultList = intfDAO.getItemSpec(sqlMap.get("ITEM_SPEC_ID").toString());
				}
				staticData.setSqlId(sqlMap.get("SQL_ID").toString());
				staticData.setEle(resultList);
				staticDataList.add(staticData);
			}
		}
		return staticDataList;
	}

	public List<Map<String, Object>> qryOfferModel(Map<String, Object> param) {
		return intfDAO.qryOfferModel(param);
	}

	@Override
	public int isValidProdQryPwd(Long prodId, String password) {
		String dbPwd = intfDAO.queryProdQryPwdByProdId(prodId);
		if (StringUtils.isBlank(dbPwd)) {
			return CustDomain.CUST_NOSETTING_PWD;
		}

		if ("1".equals(CommonDomain.CONST_CUST_PW_MD5)) {
			password = StringUtils.isNotEmpty(password) ? passwordEncoder.encodePassword(password, null) : password;
		}

		if (dbPwd.equals(password)) {
			return CustDomain.CUST_VALID_PWD;
		}

		return CustDomain.CUST_INVALID_PWD;
	}

	@Override
	public int isValidProdBizPwd(Long prodId, String password) {
		String dbPwd = intfDAO.queryProdBizPwdByProdId(prodId);
		if (StringUtils.isBlank(dbPwd)) {
			return CustDomain.CUST_NOSETTING_PWD;
		}

		if ("1".equals(CommonDomain.CONST_CUST_PW_MD5)) {
			password = StringUtils.isNotEmpty(password) ? passwordEncoder.encodePassword(password, null) : password;
		}

		if (dbPwd.equals(password)) {
			return CustDomain.CUST_VALID_PWD;
		}

		return CustDomain.CUST_INVALID_PWD;
	}

	@Override
	public int isValidAcctPwd(Long acctId, String password) {
		String dbPwd = intfDAO.queryAcctPwdByAcctId(acctId);
		if (StringUtils.isBlank(dbPwd)) {
			return CustDomain.CUST_NOSETTING_PWD;
		}

		if ("1".equals(CommonDomain.CONST_CUST_PW_MD5)) {
			password = StringUtils.isNotEmpty(password) ? passwordEncoder.encodePassword(password, null) : password;
		}

		if (dbPwd.equals(password)) {
			return CustDomain.CUST_VALID_PWD;
		}

		return CustDomain.CUST_INVALID_PWD;
	}

	@Override
	public Tel2Area queryAccNBRInfo(String accNbr) {
		return intfDAO.queryAccNBRInfo(accNbr);
	}

	@Override
	public AreaInfo queryAreaInfo(String areaCode, String areaName) {
		AreaInfo areaInfo = null;
		if (!StringUtils.isBlank(areaCode)) {
			areaInfo = intfDAO.queryAreaInfoByCode(areaCode);
		} else {
			areaInfo = intfDAO.queryAreaInfoByName(areaName);
		}
		return areaInfo;
	}

	@Override
	public List<OperatingOfficeInfo> queryOperatingOfficeInfo(String areaCode, String queryType, String areaName) {
		List<OperatingOfficeInfo> operatingOfficeInfos = null;
		if (IntfDomain.OPERATING_ROOM_PROVINCE.equalsIgnoreCase(queryType)) {
			operatingOfficeInfos = intfDAO.queryOperatingOfficeInfoAll();
		} else if ((IntfDomain.OPERATING_ROOM_CITY.equals(queryType) || IntfDomain.OPERATING_ROOM_SOLE
				.equals(queryType))) {
			if (StringUtils.isBlank(areaName)) {
				operatingOfficeInfos = intfDAO.queryOperatingOfficeInfoByAreaCode(areaCode);
			} else {
				operatingOfficeInfos = intfDAO.queryOperatingOfficeInfoByAreaName(areaName);
			}
		}
		return operatingOfficeInfos;
	}

	@Override
	public String queryNbrByIccid(String iccid) {
		List<Map<String, Object>> num = intfDAO.queryTerminalCode(iccid);
		String accNum = null;
		if (!num.get(0).get("COUNT(1)").toString().equals("0")) {
			List<Map<String, Object>> yksxList = intfDAO.ifYKSX(iccid);
			if (!yksxList.get(0).get("COUNT(1)").toString().equals("0")) {
				List<Map<String, Object>> numList = intfDAO.queryNumOfYKSX(iccid);
				accNum = numList.get(0).get("REDU_ACCESS_NUMBER").toString();
			}
			List<Map<String, Object>> numList = intfDAO.queryNum(iccid);
			accNum = numList.get(0).get("REDU_ACCESS_NUMBER").toString();
		}
		return accNum;
	}

	@Override
	public Map<String, Object> queryFNSInfo(String accNbr, String accNbrType) {
		Map<String, Object> fnsInfo = new HashMap<String, Object>();
		// accNbrType=4，accNbr为offerSpecId
		if ("4".equals(accNbrType)) {
			// 查询套餐可以开通亲情号码的个数
			int ppcount = intfDAO.queryFNSNum(Long.valueOf(accNbr));
			List<Map<String, Object>> unusedFNSInfo = intfDAO.queryFNSInfoByOfferSpecId(Long.valueOf(accNbr));
			fnsInfo.put("offerSpecId", accNbr);
			fnsInfo.put("ppcount", ppcount);
			fnsInfo.put("unusedFNSInfo", unusedFNSInfo);
			return fnsInfo;
		} else {
			// 查询已选亲情套餐
			Long offerSpecId = intfDAO.queryOfferSpecIdByAccNbr(accNbr);
			if (offerSpecId != null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("accNbr", accNbr);
				param.put("offerSpecId", offerSpecId);

				// 查询套餐可以开通亲情号码的个数
				int ppcount = intfDAO.queryFNSNum(offerSpecId);

				// 查询亲情号码值
				List<Map<String, Object>> useFNSInfo = intfDAO.queryFNSInfo(param);

				// 查询未开通的paramId
				List<Map<String, Object>> unusedFNSInfo = intfDAO.queryUnusedFNSInfo(param);

				// 还可设置的数量
				int list = ppcount - useFNSInfo.size();

				fnsInfo.put("offerSpecId", offerSpecId);
				fnsInfo.put("ppcount", ppcount);
				fnsInfo.put("useFNSInfo", useFNSInfo);
				fnsInfo.put("unusedFNSInfo", unusedFNSInfo);
				fnsInfo.put("number", useFNSInfo.size());// 已设置数量
				fnsInfo.put("list", list);
			}
		}
		return fnsInfo;
	}

	// 电子有价卡批量获取请求
	public String goodsBatchGet(String trade_id, String sale_time, String channelId, String staffCode,
			String value_card_type_code, String value_code, String apply_num, int flag) {
		// 0是有价卡 1是积分礼品
		if (flag < 1) {
			// 插入接口表GOODS_APPLY_INTF
			return intfDAO.insertIntoGoodsApplayIntf(trade_id, sale_time, channelId, staffCode, value_card_type_code,
					value_code, apply_num, flag);
		} else {
			return "不是有价卡";
		}

	}

	@Override
	public Date getCurrentTime() {
		return intfDAO.queryCurrentTime();
	}

	@Override
	public List<RoleObj> findRoleObjs(Integer objType, Long objId) {
		return intfDAO.queryRoleObjs(objType, objId);
	}

	@Override
	public Account findAcctByAccNbr(String accessNumber, Integer prodSpecId) {
		return intfDAO.findAcctByAccNbr(accessNumber, prodSpecId);
	}

	@Override
	public Account findAcctByAcctCd(String acctCd) {
		return intfDAO.findAcctByAcctCd(acctCd);
	}

	@Override
	public Offer findOfferByProdIdAndOfferSpecId(Long prodId, Long offerSpecId) {
		return intfDAO.findOffer(prodId, offerSpecId);
	}

	@Override
	public OfferProd2Td findOfferProd2Td(Long prodId, Long terminalDevSpecId) {
		return intfDAO.findOfferProd2Td(prodId, terminalDevSpecId);
	}

	@Override
	public OfferProdItem findOfferProdItem(Long prodId, Long itemSpecId) {
		return intfDAO.findOfferProdItem(prodId, itemSpecId);
	}

	@Override
	public OfferServ findOfferServByProdIdAndServSpecId(Long prodId, Long servSpecId) {
		return intfDAO.findOfferServ(prodId, servSpecId);
	}

	@Override
	public OfferRoles findProdOfferRoles(Long offerSpecId, Long prodSpecId) {
		return intfDAO.findOfferRoles(offerSpecId, CommonDomain.OBJ_TYPE_PROD_SPEC, prodSpecId, null);
	}

	@Override
	public ProdSpec2AccessNumType findProdSpec2AccessNumType(Long prodSpecId) {
		return intfDAO.findProdSpec2AccessNumType(prodSpecId, "Y");
	}

	@Override
	public ProdSpec2AccessNumType findProdSpec2AccessNumType2(Long prodSpecId) {
		return intfDAO.findProdSpec2AccessNumType(prodSpecId, "N");
	}

	@Override
	public RoleObj findRoleObjByOfferRoleIdAndObjType(Long offerRoleId, Integer objType) {
		return intfDAO.findRoleObjByOfferRoleId(offerRoleId, objType);
	}

	@Override
	public List<OfferRoles> findServOfferRoles(Long offerSpecId) {
		return intfDAO.findOfferRoles(offerSpecId, CommonDomain.OBJ_TYPE_SERV_SPEC, null);
	}

	@Override
	public String findOfferOrService(Long id) {
		return intfDAO.findOfferOrService(id);
	}

	@Override
	public Long selectByAcctId(Long id) {
		return intfDAO.selectByAcctId(id);
	}

	@Override
	public List<Map> findServSpecItem(Long id, String flag) {
		return intfDAO.findServSpecItem(id, flag);
	}

	@Override
	public Map isSubsidy(String coupon_ins_number) {
		return intfDAO.isSubsidy(coupon_ins_number);
	}

	@Override
	public com.linkage.bss.crm.model.ProdSpec getProdSpecByProdSpecId(Long prodSpecId) {
		return intfDAO.getProdSpecByProdSpecId(prodSpecId);
	}

	@Override
	public String queryProdBizPwdByProdId(Long prodId) {
		return intfDAO.queryProdBizPwdByProdId(prodId);
	}

	@Override
	public String queryProdQryPwdByProdId(Long prodId) {
		return intfDAO.queryProdQryPwdByProdId(prodId);
	}

	@Override
	public Map<String, Object> newValidateService(String accessNumber, String custName, String IDCardType, String IDCard) {

		Map<String, Object> param = new HashMap<String, Object>();
		int count = intfDAO.queryValidateServiceByAccNum(accessNumber);
		if (count == 0) {
			param.put("outId", "1");
			param.put("outMsg", "接入号码不存在!");
		}
		List<NewValidate> newValidates = intfDAO.isNewValidateService(accessNumber);
		if (CollectionUtils.isNotEmpty(newValidates)) {
			for (int i = 0; i < newValidates.size(); i++) {
				String identityNumOut = null;
				String identityNumIn = null;
				if (custName != "-9" && custName.trim() != newValidates.get(i).getName()) {
					param.put("outId", "1");
					param.put("outMsg", "客户名称不正确!");
				}

				if (IDCardType.equalsIgnoreCase((newValidates.get(i).getIdentidiesType() + "").trim())) {
					if ("1".equalsIgnoreCase(IDCardType)) {
						if (IDCard.length() == 15 || IDCard.length() == 18) {
							if (IDCard.length() == 15 && newValidates.get(i).getIdentityNum().length() == 18) {
								identityNumIn = IDCard;
								identityNumOut = newValidates.get(i).getIdentityNum().substring(1, 6)
										+ newValidates.get(i).getIdentityNum().substring(9, 9);
							}
							if (IDCard.length() == 18 && newValidates.get(i).getIdentityNum().length() == 15) {
								identityNumIn = IDCard.substring(1, 6) + IDCard.substring(9, 9);
								identityNumOut = newValidates.get(i).getIdentityNum();
							}
							if (IDCard.length() == newValidates.get(i).getIdentityNum().length()) {
								identityNumIn = IDCard;
								identityNumOut = newValidates.get(i).getIdentityNum();
							}
							if (!identityNumIn.equalsIgnoreCase(identityNumOut)) {

								param.put("outId", "1");
								param.put("outMsg", "证件号码不正确!");
							}
						} else {

							param.put("outId", "3");
							param.put("outMsg", "证件号码不符合15位或18位!");
						}
					} else if (!IDCardType.equalsIgnoreCase(newValidates.get(i).getIdentityNum())) {
						param.put("outId", "1");
						param.put("outMsg", "证件号码不正确!");
					}
				}
				param.put("outId", "0");
				param.put("outMsg", "验证通过！");
			}
		}
		return param;

	}

	@Override
	public Map<String, String> isYKSXInfo(String accNbr) {
		Map<String, String> param = new HashMap<String, String>();
		List<YKSXInfo> yksxinfos = intfDAO.queryYKSXInfoByAccNbr(accNbr);
		if (CollectionUtils.isEmpty(yksxinfos) || yksxinfos.get(0).getProdId() == null) {
			// 不是一卡双芯用户
			param.put("flag", "1");
		} else {
			param.put("flag", "0");
			for (YKSXInfo yksxinfo : yksxinfos) {
				// 同时取出两条，一条是240；一条是239
				if (240 == yksxinfo.getRoleCd()) {
					Map<String, Object> paramVoice = new HashMap<String, Object>();
					paramVoice.put("prodId", yksxinfo.getProdId());
					paramVoice.put("roleCd", yksxinfo.getRoleCd());
					String voiceAccNbr = intfDAO.queryYKSXAccNum(paramVoice);
					param.put("voiceAccNbr", voiceAccNbr);
				} else if (239 == yksxinfo.getRoleCd()) {
					Map<String, Object> paramWlane = new HashMap<String, Object>();
					paramWlane.put("prodId", yksxinfo.getProdId());
					paramWlane.put("roleCd", yksxinfo.getRoleCd());
					String wlaneAccNbr = intfDAO.queryYKSXAccNum(paramWlane);
					param.put("wlaneAccNbr", wlaneAccNbr);
				}
			}
		}
		return param;
	}

	@Override
	public List<String> queryImsiInfoByMdn(Long prodId) {
		return intfDAO.queryImsiInfoByMdn(prodId);
	}

	@Override
	public Map<String, Object> checkGlobalroam(Long prodId, Long offerSpecId) {
		String outCode = "1";
		String outMsg = "不减免押金";
		Map<String, Object> resMap = new HashMap<String, Object>();
		Date createDt = intfDAO.queryDtByProdId(prodId);
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("offerSpecId", offerSpecId);
		param.put("CATEGORY_NODE_ID_EJ", IntfDomain.CheckGlobalroam.CATEGORY_NODE_ID_EJ);
		param.put("CATEGORY_NODE_ID_SWLH", IntfDomain.CheckGlobalroam.CATEGORY_NODE_ID_SWLH);
		param.put("CATEGORY_NODE_ID_HM", IntfDomain.CheckGlobalroam.CATEGORY_NODE_ID_HM);
		int count1 = intfDAO.queryCntBySpecId(param);
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (count1 > 0) {
			outCode = "0";
			outMsg = "押金减免";
		} else if (createDt.after(addDays(date, -30))) {
			// 新入网,小于30天用户

			tempMap.put("prodId", prodId);
			tempMap.put("ORDER_STATUS_DELETED", IntfDomain.CheckGlobalroam.ORDER_STATUS_DELETED);
			tempMap.put("CATEGORY_NODE_ID_DX", IntfDomain.CheckGlobalroam.CATEGORY_NODE_ID_DX);
			int count2 = intfDAO.queryCntByProdId(tempMap);
			if (count2 > 0) {
				outCode = "0";
				outMsg = "押金减免";
			}
		} else {
			tempMap.put("offerSpecId", offerSpecId);
			tempMap.put("CATEGORY_NODE_ID_LX3G", IntfDomain.CheckGlobalroam.CATEGORY_NODE_ID_LX3G);
			int count3 = intfDAO.queryCntByOfferSpecId(tempMap);
			if (count3 > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("prodId", prodId);
				params.put("USER_STATUS_CD_2", IntfDomain.CheckGlobalroam.USER_STATUS_CD_2);
				params.put("USER_STATUS_CD_5", IntfDomain.CheckGlobalroam.USER_STATUS_CD_5);
				params.put("USER_STATUS_CD_6", IntfDomain.CheckGlobalroam.USER_STATUS_CD_6);
				int count4 = intfDAO.queryCntByOfferProdId(params);
				if (count4 <= 0) {
					outCode = "0";
					outMsg = "押金减免";
				}
			}
			// --查询用户付费方式
			int count5 = intfDAO.queryMoneyByProdId(prodId);
			if (count5 > 0) {
				int paymentAccountTypeCd = intfDAO.queryPaymentByProdId(prodId);
				if ((paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_21)
						|| (paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_21)
						|| (paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_22)
						|| (paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_24)
						|| (paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_25)
						|| (paymentAccountTypeCd == IntfDomain.CheckGlobalroam.PAYMENT_ACCOUNT_TYPE_CD_3)) {
					outCode = "0";
					outMsg = "押金减免";
				}
			}
		}
		resMap.put("outCode", outCode);
		resMap.put("outMsg", outMsg);
		return resMap;
	}

	private Date addDays(Date date, int amount) {
		log.debug("***********addDays**************");
		if (date == null)
			throw new IllegalArgumentException("The date must not be null");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(5, amount);
		return c.getTime();
	}

	@Override
	public String cancelOrder(String olId, String areaId, String channelId, String staffId) {
		log.debug("-----cancelOrder start");
		int result = 0;
		String resCode = "";
		String resultMsgs = "";

		// 查找需要撤单的bo_id
		log.debug("-----查找需要撤单的业务动作, ol_id = " + olId);
		List<BusiOrder> boList = getActiveBusiOrders(olId);
		if (boList == null || boList.isEmpty()) {
			return "<response>" + "<result>1</result>" + "<resultCode></resultCode>"
					+ "<resultMsg>未找到需要撤单的订单!</resultMsg>" + "</response>";
		}
		log.debug("-----查找需要撤单的业务动作成功，boList={}", boList);

		// 根据传入的参数生成购物车和业务对象
		JSONObject jsObj = createCancelOrderList(boList, areaId, channelId, staffId);
		log.debug("---------根据传入的参数生成撤单购物车和业务对象完成jsObj= {}", jsObj);

		// 生成购物车
		String returnStr = soServiceSMO.soAutoService(jsObj);
		log.debug("生成撤单物车返回值:{}", returnStr);

		// 对于收取的一次性费用需要发营收接口需要调用计费方法，这个功能稍后与计费谈论接口

		JSONObject returnJsObj = JSONObject.fromObject(returnStr);
		String resultCode = returnJsObj.getString("resultCode");
		String resultMsg = returnJsObj.getString("resultMsg");

		if ("0".equals(resultCode)) {
			result = 0;
			resCode = IntfDomain.CRM_BUSS + IntfDomain.SUCC;
			JSONArray boIdArray = returnJsObj.getJSONArray("boIds");
			String boIds = getboIds(boIdArray);
			resultMsgs = boIds;
		} else {
			result = 1;
			resCode = IntfDomain.BUSSSYSE0002;
			resultMsgs = "生成订单失败:" + resultMsg;
		}
		log.debug("-------撤单购物车受理完成" + resultMsg);

		return "<response>" + "<result>" + result + "</result>" + "<resultCode>" + resCode + "</resultCode>"
				+ "<resultMsg>" + resultMsgs + "</resultMsg>" + "</response>";
	}

	/**
	 * 根据购物车的olId，查询返回可以撤单的产品及服务动作的业务动作id
	 * 
	 * @param olId
	 * @return
	 * 
	 */
	private List<BusiOrder> getActiveBusiOrders(String olId) {
		log.debug("getActiveBusiOrders start, olId={}", olId);
		List<BusiOrder> boList = new ArrayList<BusiOrder>();
		List<BusiOrder> list = intfDAO.getBusiOrdersByOlId(olId);
		if (list != null && list.size() > 0) {
			for (BusiOrder busiOrder : list) {
				String statusCd = busiOrder.getStatusCd();
				log.debug("statusCd={}", statusCd);
				// 获得非竣工、非作废状态的业务动作ID
				if (!statusCd.equals(CommonDomain.ORDER_STATUS_DELETED)
						&& !statusCd.equals(CommonDomain.ORDER_STATUS_COMPLETED)) {
					int boActionClassCd = busiOrder.getBoActionType().getActionClassCd();
					log.debug("boActionClassCd={}", boActionClassCd);
					// 获得业务动作类型种类是产品及服务动作的业务动作ID
					if (boActionClassCd == CommonDomain.ACTION_CLASS_PROD_ACTION) {
						boList.add(busiOrder);
					}
				}
			}
		}
		log.debug("-----boList.size()={}", boList.size());
		return boList;
	}

	/**
	 * 创建撤单购物车对象
	 * 
	 * @param boList
	 * @param areaCode
	 * @param channelId
	 * @param staffCode
	 * @return
	 */
	private JSONObject createCancelOrderList(List<BusiOrder> boList, String areaId, String channelId, String staffId) {
		// 输入参数的转化和整理
		Long partyId = getBoPartyId(boList);
		log.debug("创建撤单购物车对象入参：boList={}，areaCode={}，channelId={}，staffCode={}", boList, areaId, channelId, staffId);

		// 根据入参构造购物车的json对象
		JSONObject rootObj = new JSONObject();
		JSONObject orderListObj = new JSONObject();
		JSONObject orderListInfoJs = new JSONObject();
		orderListInfoJs.elementOpt("olId", "-1");
		orderListInfoJs.elementOpt("olNbr", "-1");
		orderListInfoJs.elementOpt("olTypeCd", "2");
		orderListInfoJs.elementOpt("staffId", Long.valueOf(staffId));
		orderListInfoJs.elementOpt("channelId", channelId);
		orderListInfoJs.elementOpt("areaId", areaId);
		orderListInfoJs.elementOpt("statusCd", "S");
		orderListInfoJs.elementOpt("partyId", partyId.toString());
		orderListObj.elementOpt("orderListInfo", orderListInfoJs);
		log.debug("---------orderListInfo 完成！");

		JSONArray custOrderListArr = new JSONArray();
		JSONObject custOrderListJs = new JSONObject();
		custOrderListJs.elementOpt("colNbr", "-1");
		custOrderListJs.elementOpt("partyId", partyId.toString());

		if (boList != null && boList.size() > 0) {
			JSONArray busiOrderArr = new JSONArray();
			BoSeqCalculator boSeqCalculator = new BoSeqCalculator();
			for (BusiOrder busiOrder : boList) {
				JSONObject busiOrderJs = new JSONObject();
				JSONObject busiOrderInfoJs = new JSONObject();
				busiOrderInfoJs.elementOpt("seq", boSeqCalculator.getNextSeqInteger());
				busiOrderInfoJs.elementOpt("staffId", Long.valueOf(staffId));
				busiOrderInfoJs.elementOpt("statusCd", "S");
				busiOrderJs.elementOpt("busiOrderInfo", busiOrderInfoJs);
				log.debug("---------busiOrderInfo 完成！");

				busiOrderJs.elementOpt("areaId", areaId);
				busiOrderJs.elementOpt("linkFlag", "Y");

				Long instId = null;
				Integer objId = null;
				String boActionTypeCd = busiOrder.getBoActionTypeCd();
				log.debug("boActionTypeCd={}", boActionTypeCd);
				if (boActionTypeCd != null
						&& boActionTypeCd.trim().equals(CommonDomain.BO_ACTION_TYPE_PROD_SERV_MODIFIED)) {
					BoServOrder boServOrder = intfDAO.findBoServOrderByBoId(busiOrder.getBoId());
					if (boServOrder != null) {
						instId = boServOrder.getServId();
						objId = boServOrder.getServSpecId();
					}
				} else {
					log.debug("BoId={}", busiOrder.getBoId());
					Long prodId = intfDAO.getProdIdByBoId(busiOrder.getBoId());
					log.debug("prodId={}", prodId);
					if (prodId != null) {
						OfferProdSpec offerProdSpec = intfDAO.findProdSpecByProdId(prodId);
						if (offerProdSpec != null) {
							instId = prodId;
							objId = offerProdSpec.getProdSpecId();
						}
					}
				}
				log.debug("instId={},objId={}", instId, objId);
				// 动作种类设置
				JSONObject busiObjJs = new JSONObject();
				busiObjJs.elementOpt("objId", objId);
				busiObjJs.elementOpt("instId", instId);
				busiObjJs.elementOpt("name", "");
				busiOrderJs.elementOpt("busiObj", busiObjJs);
				log.debug("---------busiObj 完成！");

				// 业务动作设置
				JSONObject boActionTypeJs = new JSONObject();
				boActionTypeJs.elementOpt("actionClassCd", "5");
				boActionTypeJs.elementOpt("boActionTypeCd", "3000");
				boActionTypeJs.elementOpt("name", "撤单");
				busiOrderJs.elementOpt("boActionType", boActionTypeJs);
				log.debug("---------boActionType 完成！");

				JSONObject dataJs = new JSONObject();
				dataJs.elementOpt("atomActionId", boSeqCalculator.getNextAtomActionIdInteger());
				dataJs.elementOpt("state", "ADD");
				dataJs.elementOpt("statusCd", "S");
				busiOrderJs.elementOpt("dataJs", dataJs);
				log.debug("---------boRelas 完成！");

				// 业务动作关系设置
				JSONArray boRelasArr = new JSONArray();
				JSONObject boRelasJs = new JSONObject();
				boRelasJs.elementOpt("relaSeq", "");
				boRelasJs.elementOpt("relaTypeCd", "101");
				boRelasJs.elementOpt("relaBoId", busiOrder.getBoId());
				log.debug("relaBoId={}", busiOrder.getBoId());
				boRelasArr.add(boRelasJs);
				busiOrderJs.elementOpt("boRelas", boRelasArr);
				log.debug("---------boRelas 完成！");
				busiOrderArr.add(busiOrderJs);
			}
			if (!busiOrderArr.isEmpty()) {
				custOrderListJs.elementOpt("busiOrder", busiOrderArr);
				log.debug("---------busiOrder 完成！");
			}
		}
		custOrderListArr.add(custOrderListJs);
		orderListObj.elementOpt("custOrderList", custOrderListArr);
		log.debug("---------custOrderList 完成！");

		rootObj.elementOpt("orderList", orderListObj);
		log.debug("---------orderList 完成！");
		return rootObj;
	}

	/**
	 * 处理购物车订单号BOID
	 * 
	 * @param boIdArray
	 * @return
	 */
	private String getboIds(JSONArray boIdArray) {
		StringBuffer boIds = new StringBuffer("");
		if (!boIdArray.isEmpty()) {
			for (Iterator iter = boIdArray.iterator(); iter.hasNext();) {
				JSONObject boIdJson = (JSONObject) iter.next();
				for (Object value : boIdJson.values()) {
					boIds.append(",");
					boIds.append(value);
				}
			}
		}
		return boIds.toString().replaceFirst(",", "");
	}

	/**
	 * 获得当前业务动作所属客户Id
	 * 
	 * @param boList
	 * @return
	 */
	private Long getBoPartyId(List<BusiOrder> boList) {
		Long partyId = null;
		if (boList != null && boList.size() > 0) {
			for (BusiOrder busiOrder : boList) {
				partyId = busiOrder.getPartyId();
				if (partyId != null) {
					break;
				}
			}
		}
		return partyId;
	}

	@Override
	public Long selectByAcctCd(String id) {
		return intfDAO.selectByAcctCd(id);
	}

	@Override
	public List<ProdByCompProdSpec> queryProdByCompProdSpecId(Map<String, Object> param) {
		List<ProdByCompProdSpec> prodByCompProdSpecs = intfDAO.queryProdByProdSpecByPartyId(param);
		for (int i = prodByCompProdSpecs.size() - 1; i >= 0; i--) {
			int j = intfDAO.queryIfJoinCompProdByProdId(prodByCompProdSpecs.get(i).getProdId());
			if (j != 0) {
				prodByCompProdSpecs.remove(i);
			}
		}
		return prodByCompProdSpecs;
	}

	@Override
	public Map<String, Long> selectRoleCdAndOfferRoles(Map<String, Long> param) {
		return intfDAO.selectRoleCdAndOfferRoles(param);
	}

	@Override
	public int compProdRule(String accNum, String offerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accNum", accNum);
		param.put("offerId", offerId);
		return intfDAO.compProdRule(param);
	}

	@Override
	public String queryAccNumByTerminalCode(String terminalCode) {
		return intfDAO.queryAccNumByTerminalCode(terminalCode);
	}

	@Override
	public Map<String, Object> queryPhoneNumberInfoByAnId(Map<String, Object> param) {
		return intfDAO.queryPhoneNumberInfoByAnId(param);
	}

	@Override
	public List<Map<String, String>> queryStaffInfoByStaffName(String staffName) {
		return intfDAO.queryStaffInfoByStaffName(staffName);
	}

	@Override
	public List<Map<String, String>> queryStaffInfoByStaffNumber(String staffNumber) {
		return intfDAO.queryStaffInfoByStaffNumber(staffNumber);
	}

	@Override
	public List<Map<String, Object>> getClerkId(String accNbr) {
		return intfDAO.getClerkId(accNbr);
	}

	@Override
	public void insertReceiptPringLog(Map<String, String> map) {
		intfDAO.insertReceiptPringLog(map);
	}

	@Override
	public int isRPLogExist(String coNbr) {
		return intfDAO.isRPLogExist(coNbr);
	}

	@Override
	public int judgeCoupon2OfferSpec(String offer_spec_id, String couponId) {
		return intfDAO.judgeCoupon2OfferSpec(offer_spec_id, couponId);
	}

	@Override
	public Map<String, Object> getBrandLevelDetail(Map<String, Object> param) {
		return intfDAO.getBrandLevelDetail(param);
	}

	@Override
	public List<Map<String, String>> qryPartyInfo(Map<String, Object> param) {
		return intfDAO.qryPartyInfo(param);
	}

	@Override
	public Map<String, Object> qryPartyManager(String identityNum) {
		return intfDAO.qryPartyManager(identityNum);
	}

	@Override
	public Map queryCustProfiles(Long partyId) {
		return intfDAO.queryCustProfiles(partyId);
	}

	@Override
	public Map queryIdentifyList(Long partyId) {
		return intfDAO.queryIdentifyList(partyId);
	}

	@Override
	public String getChannelId(String parentOrg) {
		return intfDAO.getChannelId(parentOrg);
	}

	@Override
	public long getPartyIdSeq() {
		return intfDAO.getPartyIdSeq();
	}

	@Override
	public void synAgentToParty(Map<String, String> map) {
		intfDAO.insertCrmParty(map);
	}

	@Override
	public void synAgentToTax(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void synAgentToABM(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void synBranchToParty(Map<String, String> map) {
		intfDAO.insertCrmParty(map);
	}

	@Override
	public void synBranchToTax(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void synBranchToABM(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void modifySynTax(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void modifySynABM(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void deleteSynTax(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void deleteSynABM(Map<String, String> map) {
		intfDAO.insertIntfParty(map);
	}

	@Override
	public void deleteChannel(String partyId) {
		intfDAO.deleteChannel(partyId);
	}

	@Override
	public long getChannelIdSeq() {
		return intfDAO.getChannelIdSeq();
	}

	@Override
	public void agentIntoChannel(Map<String, String> map) {
		intfDAO.insertCrmChannel(map);
	}

	@Override
	public void agentUpdateChannel(Map<String, String> map) {
		intfDAO.updateCrmChannel(map);
	}

	@Override
	public List<Map<String, Object>> qryOrderSimpleInfoByOlId(Map<String, Object> param) {
		return intfDAO.qryOrderSimpleInfoByOlId(param);
	}

	@Override
	public Map queryCustBrand(Long partyId) {

		return intfDAO.queryCustBrand(partyId);
	}

	@Override
	public Map queryCustLevel(Long partyId) {
		return intfDAO.queryCustLevel(partyId);
	}

	@Override
	public Map queryCustSegment(Long partyId) {
		return intfDAO.queryCustSegment(partyId);
	}

	@Override
	public void storeIntoChannel(Map<String, String> map) {
		intfDAO.insertCrmChannel(map);
	}

	@Override
	public void storeUpdateChannel(Map<String, String> map) {
		intfDAO.updateCrmChannel(map);
	}

	@Override
	public void storeDeleteChannel(String channelId) {
		intfDAO.storeDeleteChannel(channelId);
	}

	@Override
	public void synCrmChannel(Map<String, String> map) {
		intfDAO.insertCrmChannel(map);
	}

	@Override
	public void insertCrmGisParty(Map<String, String> map) {
		intfDAO.insertCrmGisParty(map);
	}

	@Override
	public void updateCrmGisParty(Map<String, String> map) {
		intfDAO.updateCrmGisParty(map);
	}

	@Override
	public void delUpdateCrmGisParty(Map<String, String> map) {
		intfDAO.delUpdateCrmGisParty(map);
	}

	@Override
	public InstStatus queryInstStatusByCd(String statusCd) {
		return intfDAO.queryInstStatusByCd(statusCd);
	}

	@Override
	public Map<String, Object> qryIfPkByPartyId(Map<String, String> param) {
		return intfDAO.qryIfPkByPartyId(param);
	}

	@Override
	public List<ProdServRela> queryProdServRelas(int servSpecId) {
		return intfDAO.queryProdServRelas(servSpecId);
	}

	@Override
	public OfferProdComp queryOfferProdComp(Long prodCompId) {
		return intfDAO.queryOfferProdComp(prodCompId);
	}

	@Override
	public void updateOrInsertAgent2prm(Map<String, Object> param) {
		intfDAO.updateOrInsertAgent2prm(param);
	}

	@Override
	public void updateOrInsertChannelForCrm(Map<String, Object> param) {
		intfDAO.updateOrInsertChannelForCrm(param);
	}

	@Override
	public List<Map<String, Object>> queryAttachOfferByProd(Long offerSpecId, Long prodSpecId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("offerSpecId", offerSpecId);
		param.put("prodSpecId", prodSpecId);
		return intfDAO.queryAttachOfferByProd(param);
	}

	@Override
	public Map computeChargeInfo(Long ol_id) {
		// 调用营业的dao层方法
		return soBatchDAO.computeChargeInfoByOlId(ol_id);
	}

	@Override
	public List queryChargeInfo(Long ol_id) {
		return intfDAO.queryChargeInfo(ol_id);
	}

	@Override
	public List<String> queryOfferSpecAttr(Long offerSpecId) {
		return intfDAO.queryOfferSpecAttr(offerSpecId);
	}

	@Override
	public String queryOfferSpecValueParam(Long offerSpecId) {
		return intfDAO.queryOfferSpecValueParam(offerSpecId);
	}

	@Override
	public Map<String, Object> getChannelIdByPartyId(Map<String, Object> param) {
		return intfDAO.getChannelIdByPartyId(param);
	}

	@Override
	public Map<String, Object> getAccessNumberByUimNo(Map<String, Object> param) {
		return intfDAO.getAccessNumberByUimNo(param);
	}

	@Override
	public List<Map<String, Object>> getUserZJInfoByAccessNumber(Map<String, Object> param) {
		return intfDAO.getUserZJInfoByAccessNumber(param);
	}

	@Override
	public void updateOrInsertGisParty(Map<String, Object> param) {
		intfDAO.updateOrInsertGisParty(param);
	}

	@Override
	public Map<String, Object> getHighFeeInfo(Map<String, Object> param) {
		Long prod_id = (Long) param.get("prod_id");
		Long owner_id = (Long) param.get("owner_id");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 用户状态
		List<Map<String, Object>> statusList = intfDAO.getStatusList(prod_id);
		String status = "";
		String prod_status_cd = "";
		if (statusList.size() == 1) {
			status = "在用";
		} else {
			for (int i = 0; i < statusList.size(); i++) {
				Map<String, Object> s = (Map<String, Object>) statusList.get(i);
				prod_status_cd = s.get("PROD_STATUS_CD").toString();
				if ("1".equals(prod_status_cd)) {
					continue;
				}
				status += s.get("NAME").toString() + ",";
			}
			if (StringUtils.isNotEmpty(status)) {
				status = status.substring(0, status.length() - 1);
			}
		}
		resMap.put("status", status);
		// 客户名称
		List<Map<String, Object>> partyList = intfDAO.getDevNameList(owner_id);
		map = partyList.get(0);
		String name = map.get("NAME").toString();
		resMap.put("partyname", name);
		// 97合同账号
		List<Map<String, Object>> acctList = intfDAO.getAcctList(prod_id);
		String acct_id = "";
		if (acctList != null && acctList.size() > 0) {
			map = (Map<String, Object>) acctList.get(0);
			String acct_cd = map.get("ACCT_CD").toString();
			acct_id = map.get("ACCT_ID").toString();
			resMap.put("acctcd", acct_cd);
		} else {
			resMap.put("acctcd", "");
		}
		// 客户经理信息
		List<Map<String, Object>> managerList = intfDAO.getManagerList(prod_id);
		String abc = "PARTY_ID";
		// 用户对应的客户经理为空时取客户对应的客户经理
		if (managerList != null && managerList.size() == 0) {
			managerList = intfDAO.getManagerToList(owner_id);
			abc = "STAFF_ID";
		}
		if (managerList != null && managerList.size() > 0) {
			for (int i = 0; i < managerList.size(); i++) {
				map = (Map<String, Object>) managerList.get(i);
				String staff_number = map.get("STAFF_NUMBER").toString();
				String party_id = map.get(abc).toString();
				resMap.put("managercode", staff_number);
				// 名称
				List<Map<String, Object>> managerNameList = intfDAO.getDevNameList(Long.valueOf(party_id));
				if (managerNameList != null && managerNameList.size() > 0) {
					map = (Map<String, Object>) managerNameList.get(0);
					String manager_name = map.get("NAME").toString();
					resMap.put("managername", manager_name);
				} else {
					resMap.put("managername", "");
				}
				// 联系电话
				List<Map<String, Object>> managerTelList = intfDAO.getDevTelList(Long.valueOf(party_id));
				if (managerTelList != null && managerTelList.size() > 0) {
					map = (Map<String, Object>) managerTelList.get(0);
					String manager_tel = map.get("PROFILE_VALUE").toString();
					resMap.put("managertel", manager_tel);
				} else {
					resMap.put("managertel", "");
				}
			}
		}
		// 发展人信息
		List<Map<String, Object>> devList = intfDAO.getDevList(prod_id);
		if (devList != null && devList.size() > 0) {
			for (int i = 0; i < devList.size(); i++) {
				map = (Map<String, Object>) devList.get(i);
				String staff_number = map.get("STAFF_NUMBER").toString();
				String party_id = map.get("PARTY_ID").toString();
				resMap.put("devmancode", staff_number);
				// 名称
				List<Map<String, Object>> devNameList = intfDAO.getDevNameList(Long.valueOf(party_id));
				if (devNameList != null && devNameList.size() > 0) {
					map = (Map<String, Object>) devNameList.get(0);
					name = map.get("NAME").toString();
					resMap.put("devmanname", name);
				} else {
					resMap.put("devmanname", "");
				}
				// 联系电话
				List<Map<String, Object>> devTelList = intfDAO.getDevTelList(Long.valueOf(party_id));
				if (devTelList != null && devTelList.size() > 0) {
					map = (Map<String, Object>) devTelList.get(0);
					String value = map.get("PROFILE_VALUE").toString();
					resMap.put("devmantel", value);
				} else {
					resMap.put("devmantel", "");
				}
				// 部门id、名称
				List<Map<String, Object>> devOrgIdList = intfDAO.getDevOrgIdList(Long.valueOf(party_id));
				String org_id = "";
				if (devOrgIdList != null && devOrgIdList.size() > 0) {
					map = (Map<String, Object>) devOrgIdList.get(0);
					org_id = map.get("ORG_ID").toString();
					resMap.put("devdepartid", org_id);
				} else {
					resMap.put("devdepartid", "");
				}
				List<Map<String, Object>> devOrgNameList = intfDAO.getDevNameList(Long.valueOf(party_id));
				if (devOrgNameList != null && devOrgNameList.size() > 0) {
					map = (Map<String, Object>) devOrgNameList.get(0);
					name = map.get("NAME").toString();
					resMap.put("devdepartname", name);
				} else {
					resMap.put("devdepartname", "");
				}
			}
		}
		// 帐户经理信息
		if (StringUtils.isNotEmpty(acct_id)) {
			acctList = intfDAO.getAcctItemList(Long.valueOf(acct_id));
			if (acctList != null && acctList.size() > 0) {
				map = (Map<String, Object>) acctList.get(0);
				String value = map.get("VALUE").toString();// 员工工号：C00960,员工姓名：丛丹,部门：大北窑营业厅,上级部门：营业中心
				String[] tmp = value.split(",");
				String staff_code = "";
				String staff_name = "";
				for (int i = 0; i < tmp.length; i++) {
					if (tmp[i].indexOf("员工工号：") >= 0) {
						staff_code = tmp[i].substring(tmp[i].indexOf("：") + 1);
					}
					if (tmp[i].indexOf("员工姓名：") >= 0) {
						staff_name = tmp[i].substring(tmp[i].indexOf("：") + 1);
					}
				}
				resMap.put("acctmanagercode", staff_code);
				resMap.put("acctmanagername", staff_name);
				// 联系电话
				List<Map<String, Object>> telList = intfDAO.getTelList(Long.valueOf(acct_id));
				String tel_num = "";
				if (telList != null && telList.size() > 0) {
					for (int i = 0; i < telList.size(); i++) {
						map = (Map<String, Object>) telList.get(i);
						if (i == 0) {
							tel_num = map.get("VALUE").toString();
						} else {
							tel_num += "," + map.get("VALUE").toString();
						}
					}
					resMap.put("acctmanagertel", tel_num);
				}
			}
		}
		// 维系经理
		List<Map<String, Object>> wxList = intfDAO.getWxList(prod_id);
		String itemSpecId = "", wxName = "", wxTel = "";
		if (wxList != null && wxList.size() > 0) {
			for (int i = 0; i < wxList.size(); i++) {
				map = (Map<String, Object>) wxList.get(i);
				itemSpecId = map.get("ITEM_SPEC_ID").toString();
				if ("1000003".equals(itemSpecId)) {
					wxName = map.get("VALUE").toString();
					resMap.put("wxName", wxName);
				} else if ("1000004".equals(itemSpecId)) {
					wxTel = map.get("VALUE").toString();
					resMap.put("wxTel", wxTel);
				}
			}
		}
		return resMap;
	}

	@Override
	public boolean updateOrInsertCepChannelFromPrmToCrm(Map<String, Object> param) {
		return intfDAO.updateOrInsertCepChannelFromPrmToCrm(param);
	}

	@Override
	public Map<String, Object> selectPartyInfoFromSmParty(Map<String, Object> param) {
		return intfDAO.selectPartyInfoFromSmParty(param);
	}

	@Override
	public List<Map<String, Object>> checkDeviceIdInLogin(Map<String, Object> param) {
		return intfDAO.checkDeviceIdInLogin(param);
	}

	@Override
	public boolean updatePadPwdInLogin(Map<String, Object> param) {
		return intfDAO.updatePadPwdInLogin(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectChannelInfoByPartyId(Map<String, Object> param) {
		return intfDAO.checkSnPwd4SelectChannelInfoByPartyId(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwd4SelectStaffInfoByStaffNumber(Map<String, Object> param) {
		return intfDAO.checkSnPwd4SelectStaffInfoByStaffNumber(param);
	}

	@Override
	public List<Map<String, Object>> checkSnPwdInLogin(Map<String, Object> param) {
		return intfDAO.checkSnPwdInLogin(param);
	}

	@Override
	public List<Map<String, Object>> getStaffCodeAndStaffName(Map<String, Object> param) {
		return intfDAO.getStaffCodeAndStaffName(param);
	}

	@Override
	public boolean insertPadPwdLog(Map<String, Object> param) {
		return intfDAO.insertPadPwdLog(param);
	}

	@Override
	public boolean insertSmsWaitSendCrmSomeInfo(Map<String, Object> param) {
		return intfDAO.insertSmsWaitSendCrmSomeInfo(param);
	}

	@Override
	public List<Map<String, Object>> transmitRandom4SelectStaffInfoByDeviceId(Map<String, Object> param) {
		return intfDAO.transmitRandom4SelectStaffInfoByDeviceId(param);
	}

	@Override
	public String oldCGFlag(String materialId) {
		return intfDAO.oldCGFlag(materialId);
	}

	@Override
	public OfferSpecParam queryOfferSpecParam(String offerSpecParamId) {
		return intfDAO.queryOfferSpecParam(offerSpecParamId);
	}

	@Override
	public Map<String, Object> queryMainOfferSpecNameAndIdByOlId(Map<String, Object> param) {
		return intfDAO.queryMainOfferSpecNameAndIdByOlId(param);
	}

	@Override
	public int queryIfPk(Long partyId) {
		return intfDAO.queryIfPk(partyId);
	}

	@Override
	public String getOlNbrByOlId(long olId) {
		return intfDAO.getOlNbrByOlId(olId);
	}

	@Override
	public String getPartyIdByGroupSeq(String groupSeq) {
		return intfDAO.getPartyIdByGroupSeq(groupSeq);
	}

	@Override
	public String getGroupSeqByPartyId(String partyId) {
		return intfDAO.getGroupSeqByPartyId(partyId);
	}

	@Override
	public String getAnTypeCdByProdSpecId(String prodSpecId, String isPrimary) {
		return intfDAO.getAnTypeCdByProdSpecId(prodSpecId, isPrimary);
	}

	@Override
	public void insertBServActivate(ServActivate servActivate) {
		intfDAO.insertBServActivate(servActivate);

	}

	@Override
	public void insertBServActivatPps(ServActivatePps servActivatePps) {
		intfDAO.insertBServActivatPps(servActivatePps);
	}

	@Override
	public String getPayIndentIdByBcdCode(String bcdCode) {
		return intfDAO.getPayIndentIdByBcdCode(bcdCode);
	}

	@Override
	public void savePayIndentId(Map<String, String> map) {
		intfDAO.savePayIndentId(map);
	}

	@Override
	public List<Long> queryCategoryNodeId(Long offerSpecId) {
		return intfDAO.queryCategoryNodeId(offerSpecId);
	}

	@Override
	public Long queryIfRootNode(Long categoryNodeId) {
		return intfDAO.queryIfRootNode(categoryNodeId);
	}

	@Override
	public Map<String, Object> queryUimNum(String phoneNumber) {
		return intfDAO.queryUimNum(phoneNumber);
	}

	@Override
	public List<Map<String, Object>> getPartyIdentityList(String partyId) {
		return intfDAO.getPartyIdentityList(partyId);
	}

	@Override
	public String getTmlCodeByPhoneNumber(String phoneNumber) {
		return intfDAO.getTmlCodeByPhoneNumber(phoneNumber);
	}

	@Override
	public Map<String, Object> getPartyPW(String partyId) {
		return intfDAO.getPartyPW(partyId);
	}

	@Override
	public void indentItemPayIntf(IndentItemPay indentItemPay) {
		intfDAO.indentItemPayIntf(indentItemPay);
	}

	@Override
	public int getPartyNumberCount(String accessNumber) {
		return intfDAO.getPartyNumberCount(accessNumber);
	}

	@Override
	public int ifImportantPartyByPartyId(Long partyId) {
		return intfDAO.ifImportantPartyByPartyId(partyId);
	}

	@Override
	public String getPayItemId() {
		return intfDAO.getPayItemId();
	}

	@Override
	public String getItemTypeByBcdCode(String bcdCode) {
		return intfDAO.getItemTypeByBcdCode(bcdCode);
	}

	@Override
	public List<String> queryCardinfoByAcct(String accessNumber) {
		return intfDAO.queryCardinfoByAcct(accessNumber);
	}

	@Override
	public String getSecondAccNbrByProdId(String prodId) {
		return intfDAO.getSecondAccNbrByProdId(prodId);
	}

	@Override
	public String getOfferOrProdSpecIdByBoId(String boId) {
		return intfDAO.getOfferOrProdSpecIdByBoId(boId);
	}

	@Override
	public String getNewOlIdByOlIdForPayIndentId(String olId) {
		return intfDAO.getNewOlIdByOlIdForPayIndentId(olId);
	}

	@Override
	public String getInterfaceIdBySystemId(String systemId) {
		return intfDAO.getInterfaceIdBySystemId(systemId);
	}

	@Override
	public List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Map<String, Object> param) {
		param.put("objTypeId", OfferSpecDomain.SUB_OFFER_SPEC_OBJ_TYPE);
		param.put("boActionTypeCd", CommonDomain.BO_ACTION_TYPE_OFFER_BUY);// 业务动作
		Long startTime = System.currentTimeMillis();
		// 根据当前员工的业务权限对附属销售品进行处理
		setBusiObjectLimitCond(param, (LoginedStaffInfo) param.get("staffInfo"));
		// 角色类型-服务
		param.put("objTypeCd4", CommonDomain.OBJ_TYPE_SERV_SPEC);
		// 附属销售品
		param.put("offerTypeCd2", CommonDomain.OFFER_TYPE_CD_INT2);
		// 在用状态销售品
		param.put("statusCd2", CommonDomain.SPEC_STATUS_ACTIVE);
		param.put("osAttrClass", CommonDomain.ITEM_SPEC_OFFER_SPEC_ATTR_CLASS);
		List<AttachOfferInfoDto> list = intfDAO.queryAttachOfferSpecBySpec(param);
		List<AttachOfferInfoDto> limitList = intfDAO.querySubBusiObjectLimit(
				"IntfDAO.queryLimitAttachOfferSpecInfoBySpec", param);
		// if(!ListUtil.isListEmpty(list)){
		Map<String, List<AttachOfferInfoDto>> offerSpecsLimit = intfDAO.queryBusiObjectLimit(
				"IntfDAO.queryAttachOfferSpecLimit", param);
		List<AttachOfferInfoDto> oriList = new ArrayList<AttachOfferInfoDto>();
		if (!ListUtil.isListEmpty(list)) {
			oriList = (List<AttachOfferInfoDto>) BeanUtil.cloneBean(list);
		}
		list = dealSubBusiObjectLimit(list, limitList);
		list = dealBusiObjectLimit(oriList, list, offerSpecsLimit);
		// }
		Long costTime = System.currentTimeMillis() - startTime;
		log.debug("获取附属销售品耗时：{} ms, 附属销售品标签个数为: {}", costTime, list.size());
		return buildAttachOfferSpec(list);
	}

	/**
	 * 将附属销售品转换成一级标签目录形式的集合
	 * 
	 * @param attachOfferList
	 * @return
	 */
	private List<AttachOfferSpecDto> buildAttachOfferSpec(List<AttachOfferInfoDto> attachOfferList) {
		// LinkedList的增删速度比ArrayList快
		List<AttachOfferSpecDto> resultList = new LinkedList<AttachOfferSpecDto>();
		if (ListUtil.isListEmpty(attachOfferList)) {
			return resultList;
		}
		// 移除重复的附属销售品
		ListUtil.removeDuplicateAndSortByDesc(attachOfferList, new String[] { "labelId", "seqId" });
		Map<Integer, AttachOfferSpecDto> labelCache = new HashMap<Integer, AttachOfferSpecDto>();
		for (AttachOfferInfoDto attachOfferInfoDto : attachOfferList) {
			// 新增附属销售品对象
			OfferSpecDto newOfferSpec = new OfferSpecDto();
			BeanUtils.copyProperties(attachOfferInfoDto, newOfferSpec);
			Integer labelId = attachOfferInfoDto.getLabelId();

			if (!labelCache.containsKey(labelId)) {
				// 1.标签如果未添加过,则新增标签
				// 新增标签对象
				AttachOfferSpecDto newLabelResult = new AttachOfferSpecDto();
				newLabelResult.setLabelId(attachOfferInfoDto.getLabelId());
				newLabelResult.setLabelName(attachOfferInfoDto.getLabelName());
				// add by zhangzc@20121030 租机类标签未归类显示，直接与普通附属标签显示在附属订购界面
				newLabelResult.setLabelType(attachOfferInfoDto.getLabelType());
				// end by zhangzc@20121030
				List<OfferSpecDto> offerSpecList = new LinkedList<OfferSpecDto>();
				// 添加附属销售品对象
				offerSpecList.add(newOfferSpec);
				newLabelResult.setOfferSpecList(offerSpecList);
				resultList.add(newLabelResult);

				// 缓存标签对象
				labelCache.put(labelId, newLabelResult);
			} else {
				// 3.添加到已有的标签中
				labelCache.get(labelId).getOfferSpecList().add(newOfferSpec);
			}
		}
		return resultList;
	}

	/**
	 * 设置业务权限查询通用条件信息
	 * 
	 * @param param
	 * @param staffInfo
	 */
	private void setBusiObjectLimitCond(Map<String, Object> param, LoginedStaffInfo staffInfo) {
		Integer channelId = staffInfo.getChannelId();
		String channelSpecId = staffInfo.getChannelSpecId();
		if (channelSpecId == null) {
			// 获取渠道规格id
			channelSpecId = intfDAO.queryChannelSpecByChannelId(channelId);
		}
		param.put("staffAuthType", OfferSpecDomain.STAFF_AUTH_OBJ_TYPE);
		param.put("staffId", staffInfo.getStaffId());
		param.put("channelSpecAuthType", OfferSpecDomain.CHANNEL_SPEC_AUTH_OBJ_TYPE);
		param.put("channelSpecId", channelSpecId);
		param.put("channelAuthType", OfferSpecDomain.CHANNEL_AUTH_OBJ_TYPE);
		param.put("channelId", channelId);
		if (CommonDomain.CONST_CUR_PROVINCE.equals(CommonDomain.CONST_PROVINCE_TYPE.NEI_MENG_GU.getValue())) {
			// 内蒙业务权限是授权在系统管理角色上,此处增加角色授权对象的入参
			param.put("sysRoleAuthType", OfferSpecDomain.SYS_ROLE_AUTH_OBJ_TYPE);
		}
	}

	/**
	 * 处理业务对象的业务权限控制<BR>
	 * 源集合记为A,业务权限配置只能操作的集合记为B<BR>
	 * 业务权限配置不能操作的集合记为C,业务权限配置能操作的集合记为D<BR>
	 * (只能操作)B不为空:则返回A与B的交集-->A∩B<BR>
	 * (不能操作)C不为空:计算集合A与C的差集-->A = A-C<BR>
	 * 北京版本才会用到：(能操作)D不为空:计算集合A与C的并集-->A = A∪D<BR>
	 * 
	 * @param <T>
	 *            泛型 List<T> oriList 原始集合,
	 * @param srcList
	 *            源集合
	 * @param busiObjectLimit
	 *            业务权限集合
	 * @return List
	 */
	private <T> List<T> dealBusiObjectLimit(List<T> oriList, List<T> srcList, Map<String, List<T>> busiObjectLimit) {
		List<T> resultList = new ArrayList<T>();
		// if (ListUtil.isListEmpty(srcList)) {
		// return resultList;
		// }
		// 1.不存在业务权限配置,则返回源集合
		if (ListUtil.isMapEmpty(busiObjectLimit)) {
			return srcList;
		}
		// 利用Set的元素唯一性(依赖元素的hashcode和equals方法),进行集合运算
		Set<T> cloneSet = new HashSet<T>((List<T>) BeanUtil.cloneBean(srcList));
		if (log.isDebugEnabled()) {
			log.debug("业务对象的业务权限控制前：{}", JsonUtil.getJsonString(cloneSet));
		}

		// 2.判断是否存在只能操作的业务对象集合，有则进行A与B的交集计算-->A∩B
		List<T> busiObjects = busiObjectLimit.get(CommonDomain.ONLY_CAN_HANDLE);
		if (!ListUtil.isListEmpty(busiObjects)) {
			Set<T> limitSet = new HashSet<T>(busiObjects);
			cloneSet.retainAll(limitSet);
		} else {
			// 3.判断是否存在不能操作的业务对象集合，有则进行A与C的相减计算-->A-C
			busiObjects = busiObjectLimit.get(CommonDomain.CAN_NOT_HANDLE);
			if (!ListUtil.isListEmpty(busiObjects)) {
				Set<T> limitSet = new HashSet<T>(busiObjects);
				cloneSet.removeAll(limitSet);
			}
			// 北京版本会配置能操作的业务对象，需要将其添加到A集合中
			// 4.判断是否存在能操作的业务对象集合，有则进行A与D的并集计算-->A∪D
			busiObjects = busiObjectLimit.get(CommonDomain.CAN_HANDLE);
			if (!ListUtil.isListEmpty(busiObjects)) {
				// 增加统一口径处理去除配置以外多余能操作对象
				busiObjects = dealUniteBusiObjectLimit(oriList, busiObjects);
				Set<T> limitSet = new HashSet<T>(busiObjects);
				cloneSet.addAll(limitSet);
			}
		}
		// 5.将set集合转为list
		resultList.addAll(cloneSet);
		if (log.isDebugEnabled()) {
			log.debug("业务对象的业务权限控制后：{}", JsonUtil.getJsonString(resultList));
		}
		return resultList;
	}

	/**
	 * 处理数据统一口径问题 源集合记为A,业务权限配置能操作的集合记为B,业务权限操作的对象集合必需在源集合<BR>
	 * 
	 */
	private <T> List<T> dealUniteBusiObjectLimit(List<T> srcList, List<T> busiObjectLimit) {
		List<T> resultList = new ArrayList<T>();
		// 源数据集合
		Set<T> cloneSet = new HashSet<T>((List<T>) BeanUtil.cloneBean(srcList));
		// 业务对象集合
		Set<T> busiSet = new HashSet<T>((List<T>) BeanUtil.cloneBean(busiObjectLimit));
		// 有则进行A与B的交集计算-->A∩B
		busiSet.retainAll(cloneSet);
		resultList.addAll(busiSet);
		return resultList;
	}

	/**
	 * 处理业务对象的业务权限控制<BR>
	 * 源集合记为A<BR>
	 * (授权管控集合)C不为空:计算集合A与C的差集-->A = A-C<BR>
	 * 
	 * @param <T>
	 *            泛型
	 * @param srcList
	 *            源集合
	 * @param busiObjectLimit
	 *            业务权限集合
	 * @return List
	 */
	private <T> List<T> dealSubBusiObjectLimit(List<T> srcList, List<T> busiObjectLimit) {

		List<T> resultList = new ArrayList<T>();
		// 1.不存在业务权限配置,则返回源集合
		if (ListUtil.isListEmpty(busiObjectLimit)) {
			return srcList;
		}
		// 没有可以共公源数据
		if (ListUtil.isListEmpty(srcList)) {
			return srcList;
		}
		// 利用Set的元素唯一性(依赖元素的hashcode和equals方法),进行集合运算
		Set<T> cloneSet = new HashSet<T>((List<T>) BeanUtil.cloneBean(srcList));
		if (log.isDebugEnabled()) {
			log.debug("源数据去除受权数据前：{}", JsonUtil.getJsonString(cloneSet));
		}
		Set<T> busiObjectSet = new HashSet<T>((List<T>) BeanUtil.cloneBean(busiObjectLimit));
		cloneSet.removeAll(busiObjectSet);
		// 5.将set集合转为list
		resultList.addAll(cloneSet);
		if (log.isDebugEnabled()) {
			log.debug("源数据去除受权数据后：{}", JsonUtil.getJsonString(resultList));
		}
		return resultList;
	}

	@Override
	public void insertTableInfoPayInfoListForOrderSubmit(Map<String, Object> payInfoList) {
		intfDAO.insertTableInfoPayInfoListForOrderSubmit(payInfoList);
	}

	@Override
	public int getRenewOfferSpecAttr(Long offerSpecId) {
		return intfDAO.getRenewOfferSpecAttr(offerSpecId);
	}

	@Override
	public String getAccessNumberByProdId(Long prodId) {
		return intfDAO.getAccessNumberByProdId(prodId);
	}

	@Override
	public String getOfferSpecSummary(Long offerSpecId) {
		return intfDAO.getOfferSpecSummary(offerSpecId);
	}

	@Override
	public int checkProductNumByPartyId(Long partyId) {
		// 判断是否一卡双芯客户
		int cnt1 = intfDAO.isYKSXByPartyId(partyId);
		// 判断是否个人客户
		int partyPresonNum = intfDAO.isIndividualCustByPartyId(partyId);
		// 计算当前客户拥有的用户数量
		int cnt3 = intfDAO.getOwnCDMANumByPartyId(partyId);
		int cnt = cnt3 - cnt1;
		return cnt;
	}

	@Override
	public Long getPartyIdByIdentityNum(String identifyValue) {
		List<Long> partyIdList = intfDAO.getPartyIdByIdentityNum(identifyValue);
		return CollectionUtils.isNotEmpty(partyIdList) ? partyIdList.get(0) : null;
	}

	@Override
	public void updateOrInsertAmaLinkman(List<LinkManInfo> linkmanList) {
		intfDAO.updateOrInsertAmaLinkman(linkmanList);
	}

	@Override
	public List<OfferServItemDto> queryOfferServNotInvalid(Long offerId) {
		return intfDAO.queryOfferServNotInvalid(offerId);
	}

	@Override
	public void updateOrInsertAbm2crmProvince(Map<String, Object> param) {
		intfDAO.updateOrInsertAbm2crmProvince(param);

	}

	@Override
	public Map<String, Object> queryOfferProdInfoByAccessNumber(String phoneNumber) {
		return intfDAO.queryOfferProdInfoByAccessNumber(phoneNumber);
	}

	@Override
	public Long getProdStatusByProdId(String prodId) {
		return intfDAO.getProdStatusByProdId(prodId);
	}

	@Override
	public Map<String, Object> getServSpecs(Map<String, Object> params) {
		Map<String, Object> servSpecs = new HashMap<String, Object>();
		// 查询该产品已有的服务
		List<ServSpecDto> orderServSpecs = intfDAO.queryOrderServSpecs(params);
		Long prodid = Long.valueOf(params.get("prodId") + "");
		List<Long> queryMustSelOfferByProd = intfDAO.queryMustSelOfferByProd(prodid);
		if (CollectionUtils.isNotEmpty(orderServSpecs)) {
			for (ServSpecDto servSpec : orderServSpecs) {
				if (queryMustSelOfferByProd.contains(servSpec.getServSpecId())) {
					servSpec.setIfMustSel("Y");
				} else {
					servSpec.setIfMustSel("N");
				}
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("prodId", params.get("prodId"));
				temp.put("servSpecId", servSpec.getServSpecIdReal());
				// 获取itemSpecId和value值
				List<ServParam> servItems = intfDAO.queryServItemsByServSpecIdAndProdId(temp);
				for (ServParam servParam : servItems) {
					temp.put("itemSpecId", servParam.getId());
					// 为了获取servParam的其他值需要继续查询
					List<ServParam> servParams = intfDAO.queryItemSpecsByItemSpecIdAndServSpecId(temp);
					for (ServParam servParamNew : servParams) {
						// 由于新的查询覆盖了老的servParam，需要重新设置servParam的value值
						servParamNew.setValue(servParam.getValue());
						// 获取discreateValues
						List<DiscreateValue> discreateValues = intfDAO.queryDiscreateValuesByItemSpecId(servParamNew
								.getId());
						servParamNew.setDiscreateValues(discreateValues);
					}
					servSpec.setServParams(servParams);
				}
			}
		}
		servSpecs.put("orderServSpecs", orderServSpecs);
		// 查询该产品未有的服务
		List<ServSpecDto> optionServSpecs = intfDAO.queryOptionServSpecs(params);
		if (CollectionUtils.isNotEmpty(optionServSpecs)) {
			for (ServSpecDto servSpec : optionServSpecs) {
				List<Long> itemSpecIds = intfDAO.querySservItemsByServSpecId(servSpec.getServSpecIdReal());
				for (Long itemSpecId : itemSpecIds) {
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("servSpecId", servSpec.getServSpecIdReal());
					temp.put("itemSpecId", itemSpecId);
					// 获取servParams
					List<ServParam> servParams = intfDAO.queryItemSpecsByItemSpecIdAndServSpecId(temp);
					for (ServParam servParam : servParams) {
						// 获取discreateValues
						List<DiscreateValue> discreateValues = intfDAO.queryDiscreateValuesByItemSpecId(servParam
								.getId());
						servParam.setDiscreateValues(discreateValues);
					}
					servSpec.setServParams(servParams);
				}
			}
		}
		servSpecs.put("optionServSpecs", optionServSpecs);
		return servSpecs;
	}

	@Override
	public Map<String, Object> isHaveInOffer(Map<String, Object> req) {
		return intfDAO.isHaveInOffer(req);
	}

	@Override
	public String getOfferSpecNameByOfferSpecId(String offerSpecId) {
		return intfDAO.getOfferSpecNameByOfferSpecId(offerSpecId);
	}

	@Override
	public Map<String, Object> qryPhoneNumberInfoByAccessNumber(String accessNumber) {
		return intfDAO.qryPhoneNumberInfoByAccessNumber(accessNumber);
	}

	@Override
	public Long getPartyIdByAccNbr(String accNbr) {
		return intfDAO.getPartyIdByAccNbr(accNbr);
	}

	@Override
	public Map<String, Object> findOfferProdComp(Map<String, Object> param) {
		return intfDAO.findOfferProdComp(param);
	}

	@Override
	public String checkOrderRouls(Element order) {
		return intfDAO.checkOrderRouls(order);
	}

	@Override
	public void channelInfoGoDown(Map<String, Object> map) {
		intfDAO.channelInfoGoDown(map);
	}

	@Override
	public boolean yesOrNoAliveInOfferSpecRoles(Map<String, Object> map) {
		return intfDAO.yesOrNoAliveInOfferSpecRoles(map);
	}

	@Override
	public List<OfferIntf> queryOfferInstByProdId(Long prodId) {
		return intfDAO.queryOfferInstByProdId(prodId);
	}

	@Override
	public Map<String, Object> queryOfferInfoByProdId(Long prodId, Integer prodSpecId) {
		Map<String, Object> offerInfos = new HashMap<String, Object>();
		// 判断是否固网资费
		int effectFlag = 1;
		Integer cnt = intfDAO.queryProductSpec2CategroyNodeByProdSpecId(prodSpecId);
		if (cnt > 0) {
			effectFlag = 0;// 固网资费立即生失效
		}
		offerInfos.put("effectFlag", effectFlag);
		// 查询该产品在用销售品信息
		List<OfferInfoKF> orderOffers = intfDAO.queryOrderOffersByProdId(prodId);
		//查询主套餐下必选销售品
		List<Long> queryMustSelOfferByProd = intfDAO.queryMustSelOfferByProd(prodId);

		if (CollectionUtils.isNotEmpty(orderOffers)) {
			for (OfferInfoKF offerInfo : orderOffers) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("prodId", prodId);
				params.put("offerSpecId", offerInfo.getOfferSpecId());
				if (queryMustSelOfferByProd != null && queryMustSelOfferByProd.size() > 0) {
					if (queryMustSelOfferByProd.contains(offerInfo.getOfferSpecId())) {
						offerInfo.setIfMustSel("Y");
					} else
						offerInfo.setIfMustSel("N");
				}
				// 获取offerParams
				List<OfferParamKF> offerParams = intfDAO.queryOfferParamsByProdIdAndOfferSpecId(params);
				for (OfferParamKF offerParam : offerParams) {
					// 对特殊属性值进行处理
					if (offerParam.getItemSpecId() == 23990) {
						String value = offerParam.getValue();
						if (StringUtils.isNotBlank(value)) {
							Integer newValue = Integer.parseInt(value) / 100;
							offerParam.setValue(newValue.toString());
						}
					}
					// 获取discreateValues
					List<DiscreateValue> discreateValues = intfDAO.queryDiscreateValuesByItemSpecId(offerParam
							.getItemSpecId());
					offerParam.setDiscreateValues(discreateValues);
				}
				offerInfo.setOfferParams(offerParams);
			}
		}
		offerInfos.put("orderOffers", orderOffers);
		// 查询该产品可选销售品信息
		List<OfferInfoKF> optionOfferSpecs = new ArrayList<OfferInfoKF>();
		Long offerSpecId = intfDAO.queryCurrentOfferSpecIdKFByProdId(prodId);
		if (offerSpecId != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("prodId", prodId);
			params.put("offerSpecId", offerSpecId);
			optionOfferSpecs = intfDAO.queryOptionOfferSpecsByProdId(params);
			System.out.println(optionOfferSpecs.toString());
		}
		if (CollectionUtils.isNotEmpty(optionOfferSpecs)) {
			for (OfferInfoKF offerInfo : optionOfferSpecs) {
				// 获取offerParams
				List<OfferParamKF> offerParams = intfDAO.queryOfferParamsByOfferSpecId(offerInfo.getOfferSpecId());
				for (OfferParamKF offerParam : offerParams) {
					// 对特殊属性值进行处理
					if (offerParam.getItemSpecId() == 23990) {
						String value = offerParam.getDefaultValue();
						if (StringUtils.isNotBlank(value)) {
							Integer newValue = Integer.parseInt(value) / 100;
							offerParam.setDefaultValue(newValue.toString());
						}
					}
					// 获取discreateValues
					List<DiscreateValue> discreateValues = intfDAO.queryDiscreateValuesByItemSpecId(offerParam
							.getItemSpecId());
					offerParam.setDiscreateValues(discreateValues);
				}
				offerInfo.setOfferParams(offerParams);
				// 获取资费与服务的依赖关系
				List<ServSpecDto> servSpecs = intfDAO.queryServOfferRelaByOfferSpecId(offerInfo.getOfferSpecId());
				offerInfo.setServSpecs(servSpecs);
			}
		}
		offerInfos.put("optionOfferSpecs", optionOfferSpecs);
		return offerInfos;
	}

	@Override
	public String queryOfferSpecParamIdByItemSpecId(Map<String, Object> map) {
		return intfDAO.queryOfferSpecParamIdByItemSpecId(map);
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProd(Long prodId) {
		return intfDAO.queryAttachOfferByProd(prodId);
	}

	@Override
	public Map<String, Object> queryContinueOrderPPInfoByAccNbr(String accNbr) {
		Map<String, Object> continueOrderOffers = new HashMap<String, Object>();
		Integer cnt = intfDAO.queryCountPS2CNByAccNbr(accNbr);
		if (cnt > 0) {// 固网续费查询
			// 查询已有资费信息
			List<Map<String, Object>> currentPPInfos = intfDAO.queryCurrentPPInfosFixedByAccNbr(accNbr);
			// 查询可转资费信息
			List<Map<String, Object>> continuePPInfos = intfDAO.queryContinuePPInfosFixedByAccNbr(accNbr);
			continueOrderOffers.put("currentPPInfos", currentPPInfos);
			continueOrderOffers.put("continuePPInfos", continuePPInfos);
		} else {// 非固网用户续费查询
			// Integer cnt1 = intfDAO.queryWlaneLogByAccNbr(accNbr);
			// if (cnt1 > 0) {
			// continueOrderOffers.put("1", "已续费");
			// return continueOrderOffers;
			// }
			// 有属性为预存金额的资费 数量
			Integer cnt2 = intfDAO.queryCountContinueOrderOfferByAccNbr(accNbr);
			if (cnt2 > 0) {
				// 查询已有资费信息
				List<Map<String, Object>> currentPPInfos = intfDAO.queryCurrentPPInfosByAccNbr(accNbr);
				for (Map<String, Object> map : currentPPInfos) {
					if ("11".equals(map.get("STATUSCD"))) {
						continueOrderOffers.put("1", "已续费");
						break;
					}
				}
				// 查询可转资费信息
				List<Map<String, Object>> continuePPInfos = intfDAO.queryContinuePPInfosByAccNbr(accNbr);
				continueOrderOffers.put("currentPPInfos", currentPPInfos);
				continueOrderOffers.put("continuePPInfos", continuePPInfos);
			}
		}
		return continueOrderOffers;
	}

	@Override
	public Map<String, Object> getAuditingTicketInfoByOlId(String olId) {
		return intfDAO.getAuditingTicketInfoByOlId(olId);
	}

	@Override
	public List<OfferParam> queryOfferParamByOfferId(Long offerId) {
		return intfDAO.queryOfferParamByOfferId(offerId);
	}

	/*
	 * 根据客户id和prod_id查询子产品
	 */
	public Map<String, Object> queryAllChildCompProd(Map<String, Object> param) {

		List<CommonOfferProdDto> compProds = intfDAO.queryAllChildCompProd(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("offerProdList", compProds);
		return result;
	}

	@Override
	public String isPKagent(String payIndentId) {
		return intfDAO.isPKagent(payIndentId);
	}

	@Override
	public String getIntfCommonSeq() {
		return intfDAO.getIntfCommonSeq();
	}

	@Override
	public void saveRequestInfo(Map<String, Object> map) {
		intfDAO.saveRequestInfo(map);
	}

	@Override
	public void saveResponseInfo(Map<String, Object> map) {
		intfDAO.saveResponseInfo(map);
	}

	@Override
	public boolean yesOrNoNeedAddCoupon(Map<String, Object> param) {
		return intfDAO.yesOrNoNeedAddCoupon(param);
	}

	@Override
	public String ifCompProdByProdSpecId(Integer prodSpecId) {
		return intfDAO.ifCompProdByProdSpecId(prodSpecId);
	}

	@Override
	public Integer ifRightPartyGradeByCustTypeAndPartyGrade(Map<String, Object> params) {
		return intfDAO.ifRightPartyGradeByCustTypeAndPartyGrade(params);
	}

	@Override
	public String queryOlIdByOlNbr(String olNbr) {
		return intfDAO.queryOlIdByOlNbr(olNbr);
	}

	@Override
	public String getPartyNameByPartyId(Long partyId) {
		return intfDAO.getPartyNameByPartyId(partyId);
	}

	@Override
	public String getPartyIdByTerminalCode(String terminalCode) {
		return intfDAO.getPartyIdByTerminalCode(terminalCode);
	}

	@Override
	public List<Map<String, Object>> getOfferListByProdId(String prodId) {
		return intfDAO.getOfferListByProdId(prodId);
	}

	@Override
	public Long getPartyIdByIdentifyNum(String accNbr) {
		return intfDAO.getPartyIdByIdentifyNum(accNbr);
	}

	@Override
	public Long getPartyIdByProdId(Long prodId) {
		return intfDAO.getPartyIdByProdId(prodId);
	}

	@Override
	public Long getProdidByAccNbr(String accNbr) {
		return intfDAO.getProdidByAccNbr(accNbr);
	}

	@Override
	public Long getGxProdItemIdByProdid(Long prodid) {
		return intfDAO.getGxProdItemIdByProdid(prodid);
	}

	@Override
	public boolean checkRelaSub(Map<String, String> param) {
		return intfDAO.checkRelaSub(param);
	}

	@Override
	public List queryOfferSpecIdByProdId(String prodId) {
		return intfDAO.queryOfferSpecIdByProdId(prodId);
	}

	@Override
	public String checkContinueOrderPPInfo(String accNbr, String offerSpecId, String actionType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accNbr", accNbr);
		param.put("offerSpecId", offerSpecId);
		int cnt = intfDAO.checkContinueOrderPPInfo(param);
		String actionTypeNum = null;
		if (cnt == 0) {
			actionTypeNum = "0";
		} else {
			actionTypeNum = actionType;
		}
		return actionTypeNum;
	}

	@Override
	public List<OfferSpecDto> queryOfferSpecsByDZQD() {
		return intfDAO.queryOfferSpecsByDZQD();
	}

	@Override
	public boolean queryProdSpec2BoActionTypeCdBYprodAndAction(Map<String, Object> param) {
		return intfDAO.queryProdSpec2BoActionTypeCdBYprodAndAction(param);
	}

	@Override
	public int queryIfWLANByOfferSpecId(Long offerSpecId) {
		return intfDAO.queryIfWLANByOfferSpecId(offerSpecId);
	}

	@Override
	public Map<String, Object> selectStaffInofFromPadPwdStaff(String staffNumber) {
		return intfDAO.selectStaffInofFromPadPwdStaff(staffNumber);
	}

	@Override
	public void updatePhoneNumberStatusCdByYuyue(String phoneNumber) {
		intfDAO.updatePhoneNumberStatusCdByYuyue(phoneNumber);
	}

	@Override
	public String gjmyYesOrNoRule(Map<String, Object> param) {
		return intfDAO.gjmyYesOrNoRule(param);
	}

	@Override
	public Map<String, Object> queryAccountInfo(String prodId) {
		return intfDAO.queryAccountInfo(prodId);
	}

	@Override
	public String getPostCodeByPartyId(Long partyId) {
		return intfDAO.getPostCodeByPartyId(partyId);
	}

	@Override
	public String qryDeviceNumberStatusCd(String anId) {
		return intfDAO.qryDeviceNumberStatusCd(anId);
	}

	@Override
	public Map<String, Object> queryAuditingTicketBusiInfo(Map<String, Object> param) {
		return intfDAO.queryAuditingTicketBusiInfo(param);
	}

	@Override
	public String queryOlIdByBoId(Map<String, Object> param) {
		return intfDAO.queryOlIdByBoId(param);
	}

	@Override
	public String queryCycleByPayId(String payIndentId) {
		return intfDAO.queryCycleByPayId(payIndentId);
	}

	@Override
	public String getChargeByTicketCd(String auditTicketCd) {
		return intfDAO.getChargeByTicketCd(auditTicketCd);
	}

	@Override
	public boolean qryAccessNumberIsOk(String accessNumber) {
		return intfDAO.qryAccessNumberIsOk(accessNumber);
	}

	@Override
	public Map<String, Object> queryDefaultValueByMainOfferSpecId(String offerSpecId) {
		return intfDAO.queryDefaultValueByMainOfferSpecId(offerSpecId);
	}

	@Override
	public String getPayIndentIdByOlId(String olId) {
		return intfDAO.getPayIndentIdByOlId(olId);
	}

	@Override
	public boolean checkIsExistsMailAddressId(String mailAddressId) {
		return intfDAO.checkIsExistsMailAddressId(mailAddressId);
	}

	@Override
	public String getProdStatusNameByCd(Long prodStatusCd) {
		return intfDAO.getProdStatusNameByCd(prodStatusCd);
	}

	@Override
	public String getChannelIdByTicketCd(String ticketCd) {
		return intfDAO.getChannelIdByTicketCd(ticketCd);
	}

	@Override
	public void saveTicketCd2terminal(Map<String, Object> map) {
		intfDAO.saveTicketCd2terminal(map);
	}

	@Override
	public String getTicketIdByCd(String autitTicketCd) {
		return intfDAO.getTicketIdByCd(autitTicketCd);
	}

	@Override
	public List<InventoryStatisticsEntity> getInventoryStatistics(InventoryStatisticsEntityInputBean parameterObject) {
		return intfDAO.getInventoryStatistics(parameterObject);
	}

	@Override
	public List<Map<String, Object>> getUnCheckedCardInfo() {
		return intfDAO.getUnCheckedCardInfo();
	}

	@Override
	public void updaCradCheckResult(Map<String, Object> map) {
		intfDAO.updaCradCheckResult(map);
	}

	@Override
	public void updaCradCheckStatus(Map<String, Object> map) {
		intfDAO.updaCradCheckStatus(map);
	}

	@Override
	public String ifPayIndentIdExists(Map<String, Object> map) {
		return intfDAO.ifPayIndentIdExists(map);
	}

	@Override
	public boolean checkUIMChannelId(Map<String, Object> map) {
		return intfDAO.checkUIMChannelId(map);
	}

	@Override
	public boolean checkGoupUIM(Map<String, Object> map) {
		return intfDAO.checkGoupUIM(map);
	}

	@Override
	public List<BcdCodeEntity> getBcdCode(BcdCodeEntityInputBean parameterObject) {
		return intfDAO.getBcdCode(parameterObject);
	}

	@Override
	public String getOfferProdCompMainProd(Long compProdId) {
		return intfDAO.getOfferProdCompMainProd(compProdId);
	}

	@Override
	public void deleteCrmRequest(String id) {
		intfDAO.deleteCrmRequest(id);
	}

	@Override
	public String getChargeByPayId(String payIndentId) {
		return intfDAO.getChargeByPayId(payIndentId);
	}

	@Override
	public boolean checkUIMStore(Map<String, Object> map) {
		return intfDAO.checkUIMStore(map);
	}

	@Override
	public void saveSRInOut(Map<String, Object> map) {
		intfDAO.saveSRInOut(map);
	}

	@Override
	public String getOfferTypeCdByOfferSpecId(String offerSpecId) {
		return intfDAO.getOfferTypeCdByOfferSpecId(offerSpecId);
	}

	@Override
	public String getFaceValueByTicketCd(String auditTicketCd) {
		return intfDAO.getFaceValueByTicketCd(auditTicketCd);
	}

	@Override
	public List<BankTableEntity> getBankTable(String bankCode) {
		return intfDAO.getBankTable(bankCode);
	}

	@Override
	public void insertBankFreeze(String sql) {
		intfDAO.insertBankFreeze(sql);
	}

	@Override
	public boolean checkBankFreeze(String sql) {
		return intfDAO.checkBankFreeze(sql);
	}

	@Override
	public boolean updateBankFreeze(String sql) {
		return intfDAO.updateBankFreeze(sql);
	}

	@Override
	public List queryChargeInfoBySpec(String offerSpecId) {
		return intfDAO.queryChargeInfoBySpec(offerSpecId);
	}

	@Override
	public String getRequestInfo(String olId) {
		return intfDAO.getRequestInfo(olId);
	}

	@Override
	public void saveRequestTime(Map<String, Object> map) {
		intfDAO.saveRequestTime(map);
	}

	@Override
	public void updateRequestTime(Map<String, Object> map) {
		intfDAO.updateRequestTime(map);

	}

	@Override
	public Long getOfferItemByAccNum(String accessNumber) {
		return intfDAO.getOfferItemByAccNum(accessNumber);
	}

	@Override
	public String getFlag(String keyflag) {
		return intfDAO.getFlag(keyflag);
	}

	@Override
	public String getIntfTimeCommonSeq() {
		return intfDAO.getIntfTimeCommonSeq();

	}

	@Override
	public String getPartyIdByCard(Map<String, Object> map) {
		return intfDAO.getPartyIdByCard(map);
	}

	@Override
	public List<Map<String, Object>> getAserByProd(Long prodidByPartyId) {
		return intfDAO.getAserByProd(prodidByPartyId);
	}

	@Override
	public String getOfferSpecidByProdId(Long prodidByPartyId) {
		return intfDAO.getOfferSpecidByProdId(prodidByPartyId);
	}

	@Override
	public String getSpecNameByProdId(Long prodId) {
		return intfDAO.getSpecNameByProdId(prodId);
	}

	@Override
	public String getOffersByProdId(String valueOf) {
		return intfDAO.getOffersByProdId(valueOf);
	}

	@Override
	public String getUmiCardByProdId(String valueOf) {
		return intfDAO.getUmiCardByProdId(valueOf);
	}

	@Override
	public List<Map<String, Object>> getStasByProdId(String valueOf) {
		return intfDAO.getStasByProdId(valueOf);
	}

	@Override
	public List<Map<String, Object>> getExchsByProdId(Long prodId) {
		return intfDAO.getExchsByProdId(prodId);
	}

	@Override
	public String getAddressByProdId(Long prodId) {
		return intfDAO.getAddressByProdId(prodId);
	}

	@Override
	public String getNetAccountByProdId(Long prodId) {
		return intfDAO.getNetAccountByProdId(prodId);
	}

	@Override
	public String getValidStrByPartyId(Long partyid) {
		return intfDAO.getValidStrByPartyId(partyid);
	}

	@Override
	public Long getPrepayFlagBySpecid(Long offerSpecId) {
		return intfDAO.getPrepayFlagBySpecid(offerSpecId);
	}

	@Override
	public List<Map<String, Object>> getAccMailMap(Long acctId) {
		return intfDAO.getAccMailMap(acctId);
	}

	@Override
	public String getGimsiByProdid(Long prodId) {
		return intfDAO.getGimsiByProdid(prodId);
	}

	@Override
	public String getIndustryClasscdByPartyId(Long partyId) {
		return intfDAO.getIndustryClasscdByPartyId(partyId);
	}

	@Override
	public String getPartyAddByProdId(Long prodId) {
		return intfDAO.getPartyAddByProdId(prodId);
	}

	@Override
	public String getCimsiByProdid(Long prodId) {
		return intfDAO.getCimsiByProdid(prodId);
	}

	@Override
	public String getEsnByProdid(Long prodId) {
		return intfDAO.getEsnByProdid(prodId);
	}

	@Override
	public String getCtfRuleIdByOCId(Long offerSpecId, Long couponId) {
		return intfDAO.getCtfRuleIdByOCId(offerSpecId, couponId);
	}

	@Override
	public boolean qryAccessNumberIsZt(Map<String, Object> map) {
		return intfDAO.qryAccessNumberIsZt(map);
	}

	@Override
	public List<AttachOfferDto> queryAttachOfferByProdForPad(Long prodId) {
		return intfDAO.queryAttachOfferByProdForPad(prodId);
	}
	@Override
	public List<AttachOfferDto> queryAttachOfferInfo(Long prodId) {
		return intfDAO.queryAttachOfferInfo(prodId);
	}

	@Override
	public List queryFreeOfBank(Map<String, Object> map) {
		return intfDAO.queryFreeOfBank(map);
	}

	@Override
	public List getSaComponentInfo(String serviceId) {
		return intfDAO.getSaComponentInfo(serviceId);
	}

	/**
	 * 根据PROID取得客户全部信息 huzx20131012
	 * 业务逻辑写在这一层
	 * */
	int rec_num = 0;

	@Override
	public boolean getInfoByProId(Map<String, Object> map) {

		String proId = map.get("PROD_ID").toString();
		String custId = map.get("CUST_ID").toString();
		String accId = map.get("ACCT_ID").toString();
		String subCommandId = map.get("PROD_SPEC_ID").toString();
		String paymentMode = map.get("PAYMENT_TYPE").toString();

		try {

			log.debug("-----查询实例数据开始 start---prod_id:" + proId);
			//首先根据取得的proid,custid,accid,查询出报文各个段的数据，使用通用查询方法查询
			//取得serv字段数据
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prod_id", proId);//用户ID
			param.put("cust_id", custId);//客户ID
			param.put("acct_id", accId);//账户ID
			param.put("PAYMENT_MODE", paymentMode);//支付方式
			param.put("Sub_CommandId", subCommandId);

			intfDAO.updateSoInstDataSt(proId, "N");//修改信息表状态为N正在处理

			StringBuffer infoXML = new StringBuffer();
			//换方法拼报文
			Document document = DocumentHelper.createDocument();
			Element object = document.addElement("object");
			head(object, param);

			Element bodyObject = object.addElement("body_object");
			bodyObject.addAttribute("CommandId", "1");
			//用户信息CommandId=1
			rec_num = 0;
			getServXML(bodyObject, param);
			getServAcctXML(bodyObject, param);
			getServAttrXML(bodyObject, param);
			getServPartyXML(bodyObject, param);
			//取得每一个销售品的ID
			List<Map<String, Object>> offerIdNum = (List<Map<String, Object>>) intfDAO.qrySqlById(
					"IntfDAO.getOfferIdNum", param);
			if (offerIdNum != null && offerIdNum.size() > 0) {
				for (int i = 0; i < offerIdNum.size(); i++) {
					String offerId = convertDBObj2Str(offerIdNum.get(i).get("PRODUCT_OFFER_INSTANCE_ID"));
					param.put("offerId", offerId);
					Element bodyObjectS1 = object.addElement("body_object");
					bodyObjectS1.addAttribute("CommandId", "S1");
					//用户信息CommandId=s1
					rec_num = 0;
					getServPricePlanXML(bodyObjectS1, param);
					getServPricePlanObjectXML(bodyObjectS1, param);
					getServPricePlanParamXML(bodyObjectS1, param);

				}
			}

			//用户信息CommandId=7
			getServProductXML(object, param);

			//用户信息CommandId=24
			getServCompXML(object, param);

			String xmlInfo2 = XmlToString(document);

			rec_num = 0; //把计数变量置为0

			System.out.println(xmlInfo2);

			log.debug("-----查询实例数据拼装报文成功-------");

			Map<String, Object> xmlInfo = new HashMap<String, Object>();
			xmlInfo.put("prod_id", proId);
			xmlInfo.put("infoXML", xmlInfo2);
			xmlInfo.put("status", "T");
			intfDAO.saveXMLInfo(xmlInfo);//把拼装的报文存如报文表并把状态改为待处理
			intfDAO.updateSoInstDataSt(proId, "C");//修改信息表状态为C处理完成
			log.debug("-----保存报文成功-------prod_id:" + proId);
			intfDAO.saveSoInstDataToHis(proId);//转存数据到历史表
			intfDAO.delSoInstData(proId);//清空原数据

			rec_num = 0; //把计数变量置为0
			log.debug("-----转存数据到历史表并清空原数据成功-------prod_id:" + proId);

			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("-----查询实例数据拼装报文失败-------");
			intfDAO.updateSoInstDataSt(proId, "F");//修改信息表状态为F处理出错
			e.printStackTrace();
			return false;

		}
	}

	public Element head(Element object, Map<String, Object> param) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String TimeStamp = sdf.format(d);
		String transactionId = sdf2.format(d);
		Map<String, Object> archiveGroupID = intfDAO.qrySqlToMapById("IntfDAO.getArchiveGroupID", param);

		Element head = object.addElement("head_object");
		head.addElement("head").addAttribute("head_name", "Order_type");
		head.addElement("head").addAttribute("head_name", "OriginSystemId").setText("1001");
		head.addElement("head").addAttribute("head_name", "DestSystemId").setText("1002");
		head.addElement("head").addAttribute("head_name", "TransactionId").setText(transactionId);
		head.addElement("head").addAttribute("head_name", "TimeStamp").setText(TimeStamp);
		head.addElement("head").addAttribute("head_name", "ArchiveGroupID").setText(
				convertDBObj2Str(archiveGroupID.get("ARCHIVE_GROUP_ID")));
		head.addElement("head").addAttribute("head_name", "Version").setText("1");
		head.addElement("head").addAttribute("head_name", "OrderNumber").setText("1");
		head.addElement("head").addAttribute("head_name", "PreOrderNumber").setText("0");
		head.addElement("head").addAttribute("head_name", "PaymentMode").setText(
				convertDBObj2Str(param.get("PAYMENT_MODE")));
		head.addElement("head").addAttribute("head_name", "CustID").setText(convertDBObj2Str(param.get("cust_id")));
		head.addElement("head").addAttribute("head_name", "Priority").setText("50");
		head.addElement("head").addAttribute("head_name", "OlNbr").setText("600031896782");
		head.addElement("head").addAttribute("head_name", "OfferParam").setText("0");
		log.debug("-----拼装head报文成功-------");
		return object;
	}

	public Element getServXML(Element object, Map<String, Object> param) {

		//取得serv字段数据

		Map<String, Object> servInfoAAP = intfDAO.qrySqlToMapById("IntfDAO.getServInfoAAP", param);
		Map<String, Object> servInfoC = intfDAO.qrySqlToMapById("IntfDAO.getServInfoC", param);
		if (servInfoC == null || servInfoC.isEmpty()) {
			servInfoC = new HashMap<String, Object>();
			servInfoC.put("CUST_NO", "");
		}

		Map<String, Object> servInfoCC = intfDAO.qrySqlToMapById("IntfDAO.getServInfoCC", param);
		Map<String, Object> servInfoPC = intfDAO.qrySqlToMapById("IntfDAO.getServInfoPC", param);
		param.put("an_type_cd", "509");
		Map<String, Object> servInfoCIMSI = intfDAO.qrySqlToMapById("IntfDAO.getServInfoIMSI", param);

		//param.remove("an_type_cd");
		param.put("an_type_cd", "510");
		Map<String, Object> servInfoGIMSI = intfDAO.qrySqlToMapById("IntfDAO.getServInfoIMSI", param);

		Map<String, Object> servInfoName = intfDAO.qrySqlToMapById("IntfDAO.getServInfoName", param);

		Map<String, Object> servInfoServState = intfDAO.qrySqlToMapById("IntfDAO.getServInfoServState", param);

		Map<String, Object> servInfoEffdate = intfDAO.qrySqlToMapById("IntfDAO.getServInfoEffdate", param);

		//Map<String, Object> servInfoApplydate = intfDAO.qrySqlToMapById("getServInfoApplydate", param);

		//将取出来的数据拼接成报文
		rec_num += 1;
		Element subCommandid = object.addElement("sub_commandid").addAttribute("Sub_CommandId", "378");

		Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
		bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV");
		bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
		bodyAttr.addElement("attr").addAttribute("attr_name", "AREA_ID").setText(
				convertDBObj2Str(servInfoAAP.get("AREA_ID")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_ID")
				.setText(convertDBObj2Str(param.get("prod_id")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "CUST_ID")
				.setText(convertDBObj2Str(param.get("cust_id")));
		if (servInfoC == null || servInfoC.isEmpty()) {
			bodyAttr.addElement("attr").addAttribute("attr_name", "CUST_NO");
		} else {
			bodyAttr.addElement("attr").addAttribute("attr_name", "CUST_NO").setText(
					convertDBObj2Str(servInfoC.get("CUST_NO")));
		}

		bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_ID").setText(
				convertDBObj2Str(servInfoAAP.get("PRODUCT_ID")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_CODE").setText(
				convertDBObj2Str(servInfoPC.get("PRODUCT_CODE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "COMP_PROD").setText(
				convertDBObj2Str(servInfoPC.get("COMP_PROD")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "USER_KIND_ID");
		bodyAttr.addElement("attr").addAttribute("attr_name", "COUNTRY_FLAG");
		bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
				convertDBObj2Str(servInfoEffdate.get("EFF_DATE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "APPLY_DATE").setText(
				convertDBObj2Str(servInfoEffdate.get("APPLY_DATE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "RENT_DATE");
		bodyAttr.addElement("attr").addAttribute("attr_name", "ACC_NBR").setText(
				convertDBObj2Str(servInfoAAP.get("ACC_NBR")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "ACC_NBR2");
		bodyAttr.addElement("attr").addAttribute("attr_name", "PHYSICS_NBR");
		bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_STATE").setText(
				convertDBObj2Str(servInfoServState.get("SERV_STATE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "CYCLE_TYPE_ID").setText("400");
		if (servInfoCIMSI == null || servInfoCIMSI.isEmpty()) {
			bodyAttr.addElement("attr").addAttribute("attr_name", "C_IMSI");
		} else {
			bodyAttr.addElement("attr").addAttribute("attr_name", "C_IMSI").setText(
					convertDBObj2Str(servInfoCIMSI.get("IMSI")));
		}
		if (servInfoGIMSI == null || servInfoGIMSI.isEmpty()) {
			bodyAttr.addElement("attr").addAttribute("attr_name", "G_IMSI");
		} else {
			bodyAttr.addElement("attr").addAttribute("attr_name", "G_IMSI").setText(
					convertDBObj2Str(servInfoGIMSI.get("IMSI")));
		}
		bodyAttr.addElement("attr").addAttribute("attr_name", "PAYMENT_MODE").setText(
				convertDBObj2Str(param.get("PAYMENT_MODE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "BRAND_ID");
		bodyAttr.addElement("attr").addAttribute("attr_name", "USER_NAME").setText(
				convertDBObj2Str(servInfoName.get("USER_NAME")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "PASSWORD");
		bodyAttr.addElement("attr").addAttribute("attr_name", "PASSWORD_MD5");
		bodyAttr.addElement("attr").addAttribute("attr_name", "OLD_PASSWORD_MD5");
		bodyAttr.addElement("attr").addAttribute("attr_name", "CERTIFICATE_TYPE").setText(
				convertDBObj2Str(servInfoCC.get("CERTIFICATE_TYPE")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "CERTIFICATE_NO").setText(
				convertDBObj2Str(servInfoCC.get("CERTIFICATE_NO")));
		bodyAttr.addElement("attr").addAttribute("attr_name", "OLD_PROD_NBR");
		bodyAttr.addElement("attr").addAttribute("attr_name", "OL_TYPE_CD");
		bodyAttr.addElement("attr").addAttribute("attr_name", "UAM_PRODUCT_ID");
		bodyAttr.addElement("attr").addAttribute("attr_name", "UAM_ACC_NBR");
		bodyAttr.addElement("attr").addAttribute("attr_name", "VC_ACC_NBR");
		bodyAttr.addElement("attr").addAttribute("attr_name", "VC_SERV_STATE");
		bodyAttr.addElement("attr").addAttribute("attr_name", "UDB_PRODUCT_ID");
		log.debug("-----拼装serv报文成功-------");
		return object;
	}

	public Element getServAcctXML(Element object, Map<String, Object> param) {

		//Map<String, Object> servAcctInfo = intfDAO.qrySqlToMapById("getServAcctInfo", param);

		Map<String, Object> servInfoEffdate = intfDAO.qrySqlToMapById("IntfDAO.getServInfoEffdate", param);
		if (servInfoEffdate == null || servInfoEffdate.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_ACCT");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_ID").setText(
					convertDBObj2Str(param.get("prod_id")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "ACCT_ID").setText(
					convertDBObj2Str(param.get("acct_id")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "STATE_DATE").setText(
					convertDBObj2Str(servInfoEffdate.get("EFF_DATE")));
			log.debug("-----拼装serv	aAcct报文成功-------");
			return object;
		}
	}

	public Element getServAttrXML(Element object, Map<String, Object> param) {

		//取得SERV_ATTR字段数据 多段数据
		List<Map<String, Object>> servAttrInfo = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServAttrInfo", param);
		//将取出来的数据拼接成报文

		if (servAttrInfo != null && servAttrInfo.size() > 0) {

			for (int i = 0; i < servAttrInfo.size(); i++) {
				rec_num += 1;
				Map<String, Object> servAttrMap = servAttrInfo.get(i);
				Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_ATTR");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_ID").setText(
						convertDBObj2Str(param.get("prod_id")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_ID").setText(
						convertDBObj2Str(servAttrMap.get("ATTR_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_CODE").setText(
						convertDBObj2Str(servAttrMap.get("ATTR_CODE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_VALUE").setText(
						convertDBObj2Str(servAttrMap.get("ATTR_VALUE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
						convertDBObj2Str(servAttrMap.get("EFF_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
						convertDBObj2Str(servAttrMap.get("EXP_DATE")));

			}
			log.debug("-----拼装servAttr报文成功-------");
			return object;
		} else {
			return object;
		}

	}

	//SERV_PARTY
	public Element getServPartyXML(Element object, Map<String, Object> param) {
		Map<String, Object> servPartyInfo = intfDAO.qrySqlToMapById("IntfDAO.getServPartyInfo", param);
		if (servPartyInfo == null || servPartyInfo.isEmpty()) {
			return object;
		} else {
			//将取出来的数据拼接成报文
			rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_PARTY");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_ID").setText(
					convertDBObj2Str(param.get("prod_id")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "ROLE_TYPE").setText(
					convertDBObj2Str(servPartyInfo.get("ROLE_TYPE")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "STAFF_ID").setText(
					convertDBObj2Str(servPartyInfo.get("STAFF_ID")));
			log.debug("-----拼装servParty报文成功-------");
			return object;
		}
	}

	//SERV_PRODUCT可能多条
	public Element getServProductXML(Element object, Map<String, Object> param) {
		//取得SERV_PRODUCT字段数据 多段数据
		List<Map<String, Object>> servProductInfo = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServProductInfo", param);

		//将取出来的数据拼接成报文

		if (servProductInfo != null && servProductInfo.size() > 0) {

			for (int i = 0; i < servProductInfo.size(); i++) {
				Map<String, Object> servProductMap = servProductInfo.get(i);

				Element bodyObject = object.addElement("body_object");
				bodyObject.addAttribute("CommandId", "7");
				Element bodyAttr = bodyObject.addElement("body_attr").addAttribute("rec_num", "1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_PRODUCT");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_ID").setText(
						convertDBObj2Str(param.get("prod_id")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "SERV_PRODUCT_ID").setText(
						convertDBObj2Str(servProductMap.get("SERV_PRODUCT_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_CODE").setText(
						convertDBObj2Str(servProductMap.get("PRODUCT_CODE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_ID").setText(
						convertDBObj2Str(servProductMap.get("PRODUCT_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
						convertDBObj2Str(servProductMap.get("EFF_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
						convertDBObj2Str(servProductMap.get("EXP_DATE")));

			}
			log.debug("-----拼装servProduct报文成功-------");
			return object;
		} else {

			return object;
		}

	}

	//SERV_PRICE_PLAN 可能多条

	public Element getServPricePlanXML(Element object, Map<String, Object> param) {

		List<Map<String, Object>> servPricePlanInfo = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServPricePlanInfo", param);

		//将取出来的数据拼接成报文

		if (servPricePlanInfo != null && servPricePlanInfo.size() > 0) {

			for (int i = 0; i < servPricePlanInfo.size(); i++) {
				Map<String, Object> servPricePlanMap = servPricePlanInfo.get(i);
				rec_num += 1;
				Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_PRICE_PLAN");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_OFFER_INSTANCE_ID").setText(
						convertDBObj2Str(servPricePlanMap.get("PRODUCT_OFFER_INSTANCE_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "CUST_ID").setText(
						convertDBObj2Str(param.get("cust_id")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "MANAGE_CODE").setText(
						convertDBObj2Str(servPricePlanMap.get("MANAGE_CODE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OFFER_SPEC_ID").setText(
						convertDBObj2Str(servPricePlanMap.get("OFFER_SPEC_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
						convertDBObj2Str(servPricePlanMap.get("EFF_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
						convertDBObj2Str(servPricePlanMap.get("EXP_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_ID").setText(
						convertDBObj2Str(servPricePlanMap.get("OBJECT_ID")));

			}
			log.debug("-----拼装SERV_PRICE_PLAN报文成功-------");
			return object;
		} else {
			return object;

		}

	}

	//SERV_PRICE_PLAN_OBJECT 可能多条
	public Element getServPricePlanObjectXML(Element object, Map<String, Object> param) {

		List<Map<String, Object>> servPricePlanObjectInfo = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServPricePlanObjectInfo", param);
		//将取出来的数据拼接成报文

		if (servPricePlanObjectInfo != null && servPricePlanObjectInfo.size() > 0) {

			for (int i = 0; i < servPricePlanObjectInfo.size(); i++) {
				Map<String, Object> servPricePlanObjectMap = servPricePlanObjectInfo.get(i);
				rec_num += 1;
				Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_PRICE_PLAN_OBJECT");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_OFFER_INSTANCE_ID").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("PRODUCT_OFFER_INSTANCE_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "INSTANCE_RELATION_ID").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("INSTANCE_RELATION_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_ID").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("OBJECT_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("EFF_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("EXP_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OFFER_SPEC_ID").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("OFFER_SPEC_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "MANAGE_CODE").setText(
						convertDBObj2Str(servPricePlanObjectMap.get("MANAGE_CODE")));
			}
			log.debug("-----拼装SERV_PRICE_PLAN_OBJECT报文成功-------");
			return object;
		} else {

			return object;
		}

	}

	//SERV_PRICE_PLAN_PARAM 可能多条
	public Element getServPricePlanParamXML(Element object, Map<String, Object> param) {

		List<Map<String, Object>> servPricePlanParamInfo = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServPricePlanParamInfo", param);

		//将取出来的数据拼接成报文

		if (servPricePlanParamInfo != null && servPricePlanParamInfo.size() > 0) {

			for (int i = 0; i < servPricePlanParamInfo.size(); i++) {
				Map<String, Object> servPricePlanParamMap = servPricePlanParamInfo.get(i);
				rec_num += 1;

				Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", rec_num + "");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_PRICE_PLAN_PARAM");
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
				bodyAttr.addElement("attr").addAttribute("attr_name", "PRODUCT_OFFER_INSTANCE_ID").setText(
						convertDBObj2Str(servPricePlanParamMap.get("PRODUCT_OFFER_INSTANCE_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OFFER_PARAM_ID").setText(
						convertDBObj2Str(servPricePlanParamMap.get("OFFER_PARAM_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_CODE").setText(
						convertDBObj2Str(servPricePlanParamMap.get("ATTR_CODE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_ID").setText(
						convertDBObj2Str(servPricePlanParamMap.get("ATTR_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "ATTR_VALUE").setText(
						convertDBObj2Str(servPricePlanParamMap.get("ATTR_VALUE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
						convertDBObj2Str(servPricePlanParamMap.get("EFF_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
						convertDBObj2Str(servPricePlanParamMap.get("EXP_DATE")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_ID").setText(
						convertDBObj2Str(servPricePlanParamMap.get("OBJECT_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "OFFER_SPEC_ID").setText(
						convertDBObj2Str(servPricePlanParamMap.get("OFFER_SPEC_ID")));
				bodyAttr.addElement("attr").addAttribute("attr_name", "MANAGE_CODE").setText(
						convertDBObj2Str(servPricePlanParamMap.get("MANAGE_CODE")));

			}
			log.debug("-----拼装SERV_PRICE_PLAN_PARAM报文成功-------");
			return object;
		} else {

			return object;
		}
	}

	//SERV_COMP可能没有

	public Element getServCompXML(Element object, Map<String, Object> param) {

		Map<String, Object> servCompInfo = intfDAO.qrySqlToMapById("IntfDAO.getServCompInfo", param);
		if (servCompInfo == null || servCompInfo.isEmpty()) {

			return object;
		} else {

			//将取出来的数据拼接成报文
			Element bodyObject = object.addElement("body_object");
			bodyObject.addAttribute("CommandId", "24");

			Element bodyAttr = bodyObject.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_NAME").setText("SERV_COMP");
			bodyAttr.addElement("attr").addAttribute("attr_name", "OBJECT_OPER").setText("1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "COMP_SERV_ID").setText(
					convertDBObj2Str(servCompInfo.get("COMP_SERV_ID")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "SUB_SERV_ID").setText(
					convertDBObj2Str(param.get("SUB_SERV_ID")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "ROLE_ID").setText(
					convertDBObj2Str(servCompInfo.get("ROLE_ID")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "EFF_DATE").setText(
					convertDBObj2Str(servCompInfo.get("EFF_DATE")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "EXP_DATE").setText(
					convertDBObj2Str(servCompInfo.get("EXP_DATE")));
			log.debug("-----拼装SERV_COMP报文成功-------");
			return object;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Map> instDateListBySt() {

		return intfDAO.instDateListBySt();
	}

	@Override
	public List<Map> Intf2BillingMsgListBySt(int threadIndex, Integer inProcessNumber) {
		int processNumber = inProcessNumber.intValue();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("threadIndex", threadIndex);
		map.put("inProcessNumber", processNumber);

		List<Map> MsgList = intfDAO.Intf2BillingMsgListBySt(map);
		//		try {
		//
		//			if (MsgList != null && !MsgList.isEmpty() && MsgList.size() > 0) {
		//				for (int i = 0; i < MsgList.size(); i++) {
		//					String prodId = MsgList.get(i).get("PROD_ID").toString();
		//					intfDAO.updateIntfToBillingMsgSt(prodId, "N");
		//				}
		//
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		return MsgList;

	}

	@Override
	public boolean processMsg(Map map) {

		String prodId = map.get("PROD_ID").toString();

		//if(prodId!=null&&!"".equals(prodId)){}

		Clob msg = (Clob) map.get("MSG_TEXT");
		log.debug("-----发送实例数据报文到计费开始 start---prod_id:" + prodId);
		try {
			String xmlInfo = ClobToString(msg);
			intfDAO.updateIntfToBillingMsgSt(prodId, "N");

			Document document = DocumentHelper.parseText(xmlInfo);
			String xml = document.asXML();
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + document.asXML());
			sendMsg(xml);

			intfDAO.updateIntfToBillingMsgSt(prodId, "C");
			log.debug("-----发送实例数据报文到计费成功 success---prod_id:" + prodId);
			log.debug("-----保存实例数据报文到历史表---prod_id:" + prodId);
			intfDAO.saveIntfToBillingMsgToHis(prodId);//转存数据
			intfDAO.delIntfToBillingMsg(prodId);//删除原数据
			return true;
		} catch (Exception e) {
			intfDAO.updateIntfToBillingMsgSt(prodId, "F");

			e.printStackTrace();
			log.debug("-----发送实例数据报文到计费失败 failed---prod_id:" + prodId);
			return false;
		}

	}

	public void sendMsg(String msg) throws Exception {
		//http://172.19.17.184:7102/ACCESS/services/CrmWebService?wsdl
		//esbWsdlLocation
		String esbWsdlLocation = PropertiesUtils.getPropertiesValue("ESB_WEBSERVICE_URL");
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(esbWsdlLocation));
		call.setOperationName(new QName("http://crm.crmwsi", "crmXmlService"));
		call.addParameter("request", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(esbWsdlLocation);
		String result = (String) call.invoke(new Object[] { msg });
		Document document = null;
		document = XMLDom4jUtils.fromXML(result, null);
		Element root = document.getRootElement();
		Element element = root.element("TcpCont");
		Element response = element.element("Response");
		int resutCode = Integer.parseInt(response.elementText("RspType"));
		String errCode = response.elementText("RspCode");
		String desc = response.elementText("RspDesc");
		System.out.println(desc + "==========================");
		System.out.println(errCode + "+++++++++++++++++++++++++++++");
		//	xml = msg;
		if (resutCode != 0) {
			throw new Exception("[" + errCode + "]" + desc);
		}

	}

	//oracle.sql.Clob类型转换成String类型

	public String ClobToString(Clob clob) throws SQLException, IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	public String XmlToString(Document document) throws Exception {
		String xmlInfo = "";

		XMLWriter writer = new XMLWriter();
		StringWriter StringXml = new StringWriter();
		//writer.write(document);
		OutputFormat format = OutputFormat.createPrettyPrint();
		writer = new XMLWriter(StringXml, format);
		writer.write(document);
		writer.close();
		StringBuffer xml = StringXml.getBuffer();
		xmlInfo = xml.toString();
		StringXml.close();

		return xmlInfo;

	}

	public String convertDBObj2Str(Object obj) {
		String retStr = "";
		if (obj == null) {
			return "";
		} else if (obj instanceof Date) {
			retStr = DateUtil.getDefaultFormateTimeString((Date) obj);
		} else if (obj instanceof BigDecimal) {
			retStr = String.valueOf((BigDecimal) obj);
		} else if (obj instanceof Long) {
			retStr = String.valueOf((Long) obj);
		} else if (obj instanceof Integer) {
			retStr = String.valueOf((Integer) obj);
		} else if (obj instanceof Short) {
			retStr = String.valueOf((Short) obj);
		} else if (obj instanceof String) {
			retStr = (String) obj;
		}
		return retStr;
	}

	@Override
	public Boolean checkIfIdentityNum(String identityNumValue) {
		return intfDAO.checkIfIdentityNum(identityNumValue);
	}

	@Override
	public int queryCountProd(String identityNumValue) {
		return intfDAO.queryCountProd(identityNumValue);
	}

	@Override
	public int getIfpkByProd(String prodId) {
		return intfDAO.getIfpkByProd(prodId);
	}

	@Override
	public boolean checkIsExistsParty(String custId) {
		return intfDAO.checkIsExistsParty(custId);
	}

	@Override
	public List<Map<String, Object>> queryTaxPayer(String custId) {
		return intfDAO.queryTaxPayer(custId);
	}

	@Override
	public Long getStaffIdByDbid(String dbid) {

		return intfDAO.getStaffIdByDbid(dbid);
	}

	@Override
	public Long getChannelIdByStaffId(Long staffId) {

		return intfDAO.getChannelIdByStaffId(staffId);
	}

	@Override
	public Long getStaffIdByAgentNum(String staffNumber) {
		return intfDAO.getStaffIdByAgentNum(staffNumber);
	}

	@Override
	public String findStaffNumByStaffId(Long staffid) {
		return intfDAO.findStaffNumByStaffId(staffid);
	}

	@Override
	public String insertPrm2CmsDls(Map<String, Object> param) {
		return intfDAO.insertPrm2CmsDls(param);
	}

	@Override
	public String insertPrm2CmsWt(Map<String, Object> param) {
		return intfDAO.insertPrm2CmsWt(param);
	}

	@Override
	public Long getChannelIdByStaffCode(String staffCode) {

		return intfDAO.getChannelIdByStaffCode(staffCode);
	}

	@Override
	public int qryUsefulOfferNumByAccnum(String accNbr) {
		return intfDAO.qryUsefulOfferNumByAccnum(accNbr);
	}

	@Override
	public boolean isLocalIvpn(Long prodId) {
		return intfDAO.isLocalIvpn(prodId);
	}

	@Override
	public List<Map<String, Object>> getCompLocalIvpns(Long prodId) {

		return intfDAO.getCompLocalIvpns(prodId);
	}

	@Override
	public Map<String, Object> getIvpnInfos(Long prodId) {

		return intfDAO.getIvpnInfos(prodId);
	}
	@Override
	public Map<String, Object> getIccIdByProdId(Long prodId,String itemSpecId) {
		
		return intfDAO.getIccIdByProdId(prodId,itemSpecId);
	}
	/**
	 * 送PCRF数据提取
	 * @param valueOf
	 * @param cerdValue
	 * @return
	 */
	//	String errCode = "";//esb返回值
	//	String desc = "";//esb返回值
	//	String resultCode = "";
	//	String resultMsg = "";
	//String TransactionId = "";
	//String xml = "";
	@Override
	public boolean getInfoByCRM(Map<String, Object> map) {

		String acchiveId = convertDBObj2Str(map.get("OBJECT_KEY"));//归档组号
		String prodId = convertDBObj2Str(map.get("PROD_ID"));//PROD_ID
		String priority = convertDBObj2Str(map.get("EVENT_PRIORITY"));//优先级
		String custID = convertDBObj2Str(map.get("PARTY_ID"));//客户ID
		String paymentMode = convertDBObj2Str(map.get("FEE_TYPE"));//支付方式
		String olNbr = convertDBObj2Str(map.get("OL_NBR"));//购物车号
		String offerParam = convertDBObj2Str(map.get("OFFER_PARAM"));//销售品参数

		boolean mainFlag = true;
		//		errCode = "";//esb返回值
		//		desc = "";//esb返回值
		//		resultCode = "";
		//		resultMsg = "";
		//TransactionId = "";
		//xml = "";
		char a = 'a';

		int num = 0;//判断每一个归档号包含的4G产品的个数

		intfDAO.updateC2uPCRFSt(acchiveId, "R");//修改信息表状态为R正在处理
		//取得用户电话号码
		Long prodId2 = Long.parseLong(prodId);
		String accNbr = intfDAO.getAccessNumberByProdId(prodId2);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("acchiveId", acchiveId);
		param.put("priority", priority);
		param.put("custID", custID);
		param.put("paymentMode", paymentMode);
		param.put("olNbr", olNbr);
		param.put("offerParam", offerParam);
		param.put("accNbr", "86" + accNbr);

		param.put("usrState", "65");//限速要求
		param.put("usrStation", "1");//用户身份，如主用户、从用户
		param.put("usrMasterIdentifier", "");//主用户标识。当usrStation设置为2（从用户）时，需要设置此参数。
		param.put("usrBillCycleDate", "1");//结账日

		//记录一个归档组号内每一个流水号的历史信息
		List<Map<String, Object>> AcchiveList = new ArrayList<Map<String, Object>>();
		//记录一个归档组号内每一个流水号的发送ESB返回错误000的历史信息
		List<Map<String, Object>> Acchive2ESBERRList = new ArrayList<Map<String, Object>>();
		//记录一个归档组号内没有4G产品的的历史信息
		List<Map<String, Object>> AcchiveNO4GList = new ArrayList<Map<String, Object>>();

		//取得接口表中需要送PCRF的产品的serv_spec_id
		List<Map<String, Object>> servSpecIdForPCRF = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getServSpecIdForPCRF", param);

		//取得产品中是否包含主副卡变更信息
		List<Map<String, Object>> principalAndSupplementInfoForPCRF = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.getPSInfoForPCRF", param);

		param.put("flag", "0");
		//取得对照表PCRF主产品
		List<Map<String, Object>> PCRFServSpecIdForMain = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.PCRFServSpecId", param);
		param.put("flag", "1");
		//取得对照表PCRF附属产品
		List<Map<String, Object>> PCRFServSpecId = (List<Map<String, Object>>) intfDAO.qrySqlById(
				"IntfDAO.PCRFServSpecId", param);
		try {
			if ((servSpecIdForPCRF != null && servSpecIdForPCRF.size() > 0)
					|| (principalAndSupplementInfoForPCRF != null && principalAndSupplementInfoForPCRF.size() > 0)) {
				//try {
				//判断主产品
				if (servSpecIdForPCRF != null && servSpecIdForPCRF.size() > 0) {
					for (int i = 0; i < servSpecIdForPCRF.size(); i++) {
						if ("7".equals(convertDBObj2Str(servSpecIdForPCRF.get(i).get("BO_ACTION_TYPE_CD")))) {

							for (int j = 0; j < PCRFServSpecIdForMain.size(); j++) {
								//如果包含主产品送新装或者拆机
								if (convertDBObj2Str(PCRFServSpecIdForMain.get(j).get("SERV_SPEC_ID")) == convertDBObj2Str(servSpecIdForPCRF
										.get(i).get("SERV_SPEC_ID"))
										|| convertDBObj2Str(PCRFServSpecIdForMain.get(j).get("SERV_SPEC_ID")).equals(
												convertDBObj2Str(servSpecIdForPCRF.get(i).get("SERV_SPEC_ID")))) {

									//判断是这个主产品是新装还是拆机
									if ("ADD".equals(convertDBObj2Str(servSpecIdForPCRF.get(i).get("STATE")))) {

										//拼装新装报文
										num = num + 1;
										param.put("acchiveId", acchiveId + (char) (a + num - 1));
										param.put("PreOrderNumber", "0");
										/////////////////////////////
										Date d = new Date();
										SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
										String transId = intfDAO.getPCRFTransactionId();
										String TransactionIdD1 = sdf2.format(d) + transId;
										param.put("TransactionId", TransactionIdD1);
										///////////////////////////////////////////////
										Document document = DocumentHelper.createDocument();
										Element object = document.addElement("object");
										PCRFhead(object, param);

										Element bodyObject = object.addElement("body_object");
										bodyObject.addAttribute("CommandId", "D1");
										PCRFD1info(bodyObject, param);//新增报文拼装
										String xmlD1 = XmlToString(document);
										//测试用
										//System.out.println("拼装新装报文");
										//System.out.println(xml);
										//
										//int resutCode = sendPCRFMsg(xmlD1);

										Map<String, Object> esbDesc = sendPCRFMsg(xmlD1);
										String resutCode = convertDBObj2Str(esbDesc.get("resutCode"));//esb返回
										String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
										String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

										//int resutCode = 0;
										//									AcchiveList.add(getHisParamMap(acchiveId));
										if (Integer.parseInt(resutCode) != 0) {
											String resultCode = "1";
											String resultMsg = "调用ESB接口返回错误";
											Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param
													.get("acchiveId")), TransactionIdD1, xmlD1, resultCode, resultMsg,
													errCode, desc));
											continue;

										} else {
											//mainFlag = true;
											String resultCode = "0";
											String resultMsg = "成功";
											AcchiveList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")),
													TransactionIdD1, xmlD1, resultCode, resultMsg, errCode, desc));
										}

									}
									if ("DEL".equals(convertDBObj2Str(servSpecIdForPCRF.get(i).get("STATE")))) {
										num = num + 1;

										//判断附属产品
										boolean fsFlag = true;
										if (mainFlag) {
											for (int g = 0; g < servSpecIdForPCRF.size(); g++) {
												for (int h = 0; h < PCRFServSpecId.size(); h++) {
													//如果包含附属产品送订购或者退订
													if (convertDBObj2Str(PCRFServSpecId.get(h).get("SERV_SPEC_ID")) == convertDBObj2Str(servSpecIdForPCRF
															.get(g).get("SERV_SPEC_ID"))
															|| convertDBObj2Str(
																	PCRFServSpecId.get(h).get("SERV_SPEC_ID")).equals(
																	convertDBObj2Str(servSpecIdForPCRF.get(g).get(
																			"SERV_SPEC_ID")))) {

														if ("DEL".equals(convertDBObj2Str(servSpecIdForPCRF.get(g).get(
																"STATE")))) {
															//拼装退订报文
															boolean flagTD = true;
															if (num == 1) {
																param.put("PreOrderNumber", "0");
																param
																		.put("acchiveId", acchiveId
																				+ (char) (a + num - 1));
																num = num + 1;
																flagTD = false;
															}
															if (num >= 2 && flagTD) {
																String preOrderNumber = param.get("acchiveId")
																		.toString();//先取出来旧的归档组号再生成新的归档组
																param
																		.put("acchiveId", acchiveId
																				+ (char) (a + num - 1));
																num = num + 1;

																param.put("PreOrderNumber", preOrderNumber);

															}
															param.put("PCRF_SERV_ID", convertDBObj2Str(PCRFServSpecId
																	.get(h).get("PCRF_SERV_ID")));
															//Document document = DocumentHelper.createDocument();
															/////////////////////////////
															Date d = new Date();
															SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
															String transId = intfDAO.getPCRFTransactionId();
															String TransactionIdTD = sdf2.format(d) + transId;
															param.put("TransactionId", TransactionIdTD);
															///////////////////////////////////////////////
															Document document = DocumentHelper.createDocument();

															Element object = document.addElement("object");
															PCRFhead(object, param);

															Element bodyObject = object.addElement("body_object");
															bodyObject.addAttribute("CommandId", "TD");
															PCRFTDinfo(bodyObject, param);//退订报文拼装
															String xmlTD = XmlToString(document);
															//测试用
															//System.out.println("拼装退订报文");
															//System.out.println(xml);
															//

															Map<String, Object> esbDesc = sendPCRFMsg(xmlTD);
															String resutCode = convertDBObj2Str(esbDesc
																	.get("resutCode"));//esb返回
															String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
															String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

															//int resutCode = 0;
															//									AcchiveList.add(getHisParamMap(acchiveId));
															if (Integer.parseInt(resutCode) != 0) {
																String resultCode = "1";
																String resultMsg = "调用ESB接口返回错误";
																Acchive2ESBERRList.add(getHisParamMap(
																		convertDBObj2Str(param.get("acchiveId")),
																		TransactionIdTD, xmlTD, resultCode, resultMsg,
																		errCode, desc));
																continue;

															} else {
																//mainFlag = true;
																String resultCode = "0";
																String resultMsg = "成功";
																AcchiveList.add(getHisParamMap(convertDBObj2Str(param
																		.get("acchiveId")), TransactionIdTD, xmlTD,
																		resultCode, resultMsg, errCode, desc));
															}
														}

													}

												}
											}

										}
										if (fsFlag) {
											//Thread.sleep(10000);

											//拼装拆机报文
											boolean flagD2 = true;
											if (num == 1) {
												param.put("PreOrderNumber", "0");
												param.put("acchiveId", acchiveId + (char) (a + num - 1));
												num = num + 1;
												flagD2 = false;
											}
											if (num >= 2 && flagD2) {
												String preOrderNumber = param.get("acchiveId").toString();//先取出来旧的归档组号再生成新的归档组
												param.put("acchiveId", acchiveId + (char) (a + num - 1));
												num = num + 1;

												param.put("PreOrderNumber", preOrderNumber);

											}
											/////////////////////////////////////
											Date d = new Date();
											SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
											String transId = intfDAO.getPCRFTransactionId();
											String TransactionIdD2 = sdf2.format(d) + transId;
											param.put("TransactionId", TransactionIdD2);
											////////////////////////////////////////////////
											Document document = DocumentHelper.createDocument();

											Element object = document.addElement("object");
											PCRFhead(object, param);

											Element bodyObject = object.addElement("body_object");
											bodyObject.addAttribute("CommandId", "D2");
											PCRFD2info(bodyObject, param);//拆机报文拼装
											String xmlD2 = XmlToString(document);

											//测试用
											//System.out.println("拼装拆机报文");
											//System.out.println(xml);
											//

											Map<String, Object> esbDesc = sendPCRFMsg(xmlD2);
											String resutCode = convertDBObj2Str(esbDesc.get("resutCode"));//esb返回
											String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
											String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

											//int resutCode = 0;
											//									AcchiveList.add(getHisParamMap(acchiveId));
											if (Integer.parseInt(resutCode) != 0) {
												String resultCode = "1";
												String resultMsg = "调用ESB接口返回错误";
												Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param
														.get("acchiveId")), TransactionIdD2, xmlD2, resultCode,
														resultMsg, errCode, desc));
												continue;

											} else {
												//mainFlag = true;
												String resultCode = "0";
												String resultMsg = "成功";
												AcchiveList.add(getHisParamMap(
														convertDBObj2Str(param.get("acchiveId")), TransactionIdD2,
														xmlD2, resultCode, resultMsg, errCode, desc));
											}

										}

									}

								}

							}
						}
					}
				}
				if (principalAndSupplementInfoForPCRF != null && principalAndSupplementInfoForPCRF.size() > 0) {
					for (int i = 0; i < principalAndSupplementInfoForPCRF.size(); i++) {
						if (!"7".equals(convertDBObj2Str(principalAndSupplementInfoForPCRF.get(i).get(
								"BO_ACTION_TYPE_CD")))) {

							for (int j = 0; j < PCRFServSpecIdForMain.size(); j++) {

								//else if (!"7".equals(convertDBObj2Str(servSpecIdForPCRF.get(i).get("BO_ACTION_TYPE_CD")))) {
								//如果场景不为7，再根据PRODid查询实例数据是否订购了PCRF主服务
								//String temp=prodId;
								//boolean upFlag = false;
								Map<String, Object> prodIdMap = new HashMap<String, Object>();
								prodIdMap.put("prodId", prodId);
								List<Map<String, Object>> pcrfServInfo = (List<Map<String, Object>>) intfDAO
										.qrySqlById("IntfDAO.getPcrfServInfo", prodIdMap);
								//判断卡是否订购了PCRF主服务
								if (pcrfServInfo.size() > 0) {
									for (int k = 0; k < pcrfServInfo.size(); k++) {
										for (int f = 0; f < PCRFServSpecIdForMain.size(); f++) {
											if (convertDBObj2Str(PCRFServSpecIdForMain.get(f).get("SERV_SPEC_ID")) == convertDBObj2Str(pcrfServInfo
													.get(k).get("SERV_SPEC_ID"))
													|| convertDBObj2Str(
															PCRFServSpecIdForMain.get(f).get("SERV_SPEC_ID")).equals(
															convertDBObj2Str(pcrfServInfo.get(k).get("SERV_SPEC_ID")))) {

												if ("3047".equals(convertDBObj2Str(principalAndSupplementInfoForPCRF
														.get(i).get("BO_ACTION_TYPE_CD")))
														|| "3057"
																.equals(convertDBObj2Str(principalAndSupplementInfoForPCRF
																		.get(i).get("BO_ACTION_TYPE_CD")))) {
													num = num + 1;
													String boId = convertDBObj2Str(principalAndSupplementInfoForPCRF
															.get(i).get("BO_ID"));
													List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
													temp = intfDAO.getProdCompRelaRoleCd(boId);
													String roleCD = "";
													String comProdId = "";
													if (temp.size() > 0) {
														for (int z = 0; z < temp.size(); z++) {
															roleCD = convertDBObj2Str(temp.get(z).get("ROLE_CD"));//取得主副卡标识
															comProdId = convertDBObj2Str(temp.get(z)
																	.get("COMP_PROD_ID"));//取得组合产品号				
														}
														//判断主副卡关系
														if ("244".equals(roleCD) || "244" == roleCD) {//如果是副卡
															String accNbrZ = intfDAO.getAccNumByCompProdId(comProdId);
															param.put("usrStation", "2");
															param.put("usrMasterIdentifier", accNbrZ);

															//拼装修改报文，把主卡修改成副卡
															//if (num > 0) {
															//String preOrderNumber = param.get("acchiveId").toString();//先取出来旧的归档组号再生成新的归档组
															param.put("acchiveId", acchiveId + (char) (a + num - 1));
															//num = num + 1;

															param.put("PreOrderNumber", "0");

															///}
															//num = num + 1;
															/////////////////////////////////////
															Date d = new Date();
															SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
															String transId = intfDAO.getPCRFTransactionId();
															String TransactionIdD3 = sdf2.format(d) + transId;
															param.put("TransactionId", TransactionIdD3);
															////////////////////////////////////////////////
															Document document = DocumentHelper.createDocument();
															Element object = document.addElement("object");
															PCRFhead(object, param);

															Element bodyObject = object.addElement("body_object");
															bodyObject.addAttribute("CommandId", "D3");
															PCRFD3info(bodyObject, param);//新增报文拼装
															String xmlD3 = XmlToString(document);
															//测试用
															System.out.println("拼装修改报文");
															System.out.println(xmlD3);
															//
															Map<String, Object> esbDesc = sendPCRFMsg(xmlD3);
															String resutCode = convertDBObj2Str(esbDesc
																	.get("resutCode"));//esb返回
															String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
															String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

															//int resutCode = 0;
															//									AcchiveList.add(getHisParamMap(acchiveId));
															if (Integer.parseInt(resutCode) != 0) {
																String resultCode = "1";
																String resultMsg = "调用ESB接口返回错误";
																Acchive2ESBERRList.add(getHisParamMap(
																		convertDBObj2Str(param.get("acchiveId")),
																		TransactionIdD3, xmlD3, resultCode, resultMsg,
																		errCode, desc));
																continue;

															} else {
																//mainFlag = true;
																String resultCode = "0";
																String resultMsg = "成功";
																AcchiveList.add(getHisParamMap(convertDBObj2Str(param
																		.get("acchiveId")), TransactionIdD3, xmlD3,
																		resultCode, resultMsg, errCode, desc));
															}
														}

													}
												}
												if ("3048".equals(convertDBObj2Str(principalAndSupplementInfoForPCRF
														.get(i).get("BO_ACTION_TYPE_CD")))
														|| "3058"
																.equals(convertDBObj2Str(principalAndSupplementInfoForPCRF
																		.get(i).get("BO_ACTION_TYPE_CD")))) {
													num = num + 1;
													//拼装修改报文，把副卡修改成主卡
													//if (num > 0) {
													//String preOrderNumber = param.get("acchiveId").toString();//先取出来旧的归档组号再生成新的归档组
													param.put("acchiveId", acchiveId + (char) (a + num - 1));
													//num = num + 1;

													param.put("PreOrderNumber", "0");

													//}
													//num = num + 1;
													/////////////////////////////////////
													param.put("usrStation", "1");//把副卡标识改为主卡
													param.put("usrMasterIdentifier", "");//把主卡电话号码改为空

													Date d = new Date();
													SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
													String transId = intfDAO.getPCRFTransactionId();
													String TransactionIdD3 = sdf2.format(d) + transId;
													param.put("TransactionId", TransactionIdD3);
													////////////////////////////////////////////////
													Document document = DocumentHelper.createDocument();
													Element object = document.addElement("object");
													PCRFhead(object, param);

													Element bodyObject = object.addElement("body_object");
													bodyObject.addAttribute("CommandId", "D3");
													PCRFD3info(bodyObject, param);//修改报文拼装
													String xmlD3 = XmlToString(document);
													//测试用
													System.out.println("拼装修改报文");
													System.out.println(xmlD3);
													//
													Map<String, Object> esbDesc = sendPCRFMsg(xmlD3);
													String resutCode = convertDBObj2Str(esbDesc.get("resutCode"));//esb返回
													String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
													String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

													//int resutCode = 0;
													//									AcchiveList.add(getHisParamMap(acchiveId));
													if (Integer.parseInt(resutCode) != 0) {
														String resultCode = "1";
														String resultMsg = "调用ESB接口返回错误";
														Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param
																.get("acchiveId")), TransactionIdD3, xmlD3, resultCode,
																resultMsg, errCode, desc));
														continue;

													} else {
														//mainFlag = true;
														String resultCode = "0";
														String resultMsg = "成功";
														AcchiveList.add(getHisParamMap(convertDBObj2Str(param
																.get("acchiveId")), TransactionIdD3, xmlD3, resultCode,
																resultMsg, errCode, desc));
													}

												}
											}
										}
									}

								} else {
									Date d = new Date();
									SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
									String transId = intfDAO.getPCRFTransactionId();
									String TransactionIdNO = sdf2.format(d) + transId;
									String resultCode = "2";
									String resultMsg = "没有订购4G主产品直接返回";
									//pcrfDAO.updateC2uPCRFSt(acchiveId, "C");//修改信息表状态为C处理完成
									AcchiveNO4GList.add(getHisParamMap(acchiveId, TransactionIdNO, "空", resultCode,
											resultMsg, "", ""));
									return true;

								}

							}
						}

					}
				}

				//判断附属产品

				if (mainFlag) {
					for (int g = 0; g < servSpecIdForPCRF.size(); g++) {
						for (int h = 0; h < PCRFServSpecId.size(); h++) {
							//如果包含附属产品送订购或者退订
							if (convertDBObj2Str(PCRFServSpecId.get(h).get("SERV_SPEC_ID")) == convertDBObj2Str(servSpecIdForPCRF
									.get(g).get("SERV_SPEC_ID"))
									|| convertDBObj2Str(PCRFServSpecId.get(h).get("SERV_SPEC_ID")).equals(
											convertDBObj2Str(servSpecIdForPCRF.get(g).get("SERV_SPEC_ID")))) {

								if ("ADD".equals(convertDBObj2Str(servSpecIdForPCRF.get(g).get("STATE")))) {
									//拼装订购报文报文
									boolean flag = true;
									num = num + 1;
									if (num == 1) {
										param.put("PreOrderNumber", "0");//没有新装直接订购父归档组号为0
										param.put("acchiveId", acchiveId + (char) (a + num - 1));
										num = num + 1;
										flag = false;
									}

									if (num >= 2 && flag) {
										//String preOrderNumber = param.get("acchiveId").toString();
										param.put("acchiveId", acchiveId + (char) (a + num - 1));
										param.put("PreOrderNumber", acchiveId + "a");
										num = num + 1;
									}

									param.put("PCRF_SERV_ID", convertDBObj2Str(PCRFServSpecId.get(h)
											.get("PCRF_SERV_ID")));
									param.put("STATE_DATE",
											convertDBObj2Str(servSpecIdForPCRF.get(g).get("STATE_DATE")));
									param.put("END_DATE", convertDBObj2Str(servSpecIdForPCRF.get(g).get("END_DATE")));
									/////////////////////////////////////
									Date d = new Date();
									SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
									String transId = intfDAO.getPCRFTransactionId();
									String TransactionIdDG = sdf2.format(d) + transId;
									param.put("TransactionId", TransactionIdDG);
									////////////////////////////////////////////////

									Document document = DocumentHelper.createDocument();

									Element object = document.addElement("object");
									PCRFhead(object, param);

									Element bodyObject = object.addElement("body_object");
									bodyObject.addAttribute("CommandId", "DG");
									PCRFDGinfo(bodyObject, param);//订购报文拼装
									String xmlDG = XmlToString(document);

									//测试用
									//System.out.println("拼装订购报文报文");
									//System.out.println(xml);
									//
									Map<String, Object> esbDesc = sendPCRFMsg(xmlDG);
									String resutCode = convertDBObj2Str(esbDesc.get("resutCode"));//esb返回
									String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
									String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

									//int resutCode = 0;
									//									AcchiveList.add(getHisParamMap(acchiveId));
									if (Integer.parseInt(resutCode) != 0) {
										String resultCode = "1";
										String resultMsg = "调用ESB接口返回错误";
										Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")),
												TransactionIdDG, xmlDG, resultCode, resultMsg, errCode, desc));
										continue;

									} else {
										//mainFlag = true;
										String resultCode = "0";
										String resultMsg = "成功";
										AcchiveList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")),
												TransactionIdDG, xmlDG, resultCode, resultMsg, errCode, desc));
									}
								}
								if ("DEL".equals(convertDBObj2Str(servSpecIdForPCRF.get(g).get("STATE")))) {
									//拼装退订报文
									num = num + 1;
									param.put("PreOrderNumber", "0");//没有新装直接订购父归档组号为0
									if (num > 0) {
										param.put("acchiveId", acchiveId + (char) (a + num - 1));
										//param.put("PreOrderNumber", acchiveId);//有新装订购父归档组号为新装归档组号

									}

									param.put("PCRF_SERV_ID", convertDBObj2Str(PCRFServSpecId.get(h)
											.get("PCRF_SERV_ID")));
									/////////////////////////////////////
									Date d = new Date();
									SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
									String transId = intfDAO.getPCRFTransactionId();
									String TransactionIdTD2 = sdf2.format(d) + transId;
									param.put("TransactionId", TransactionIdTD2);
									////////////////////////////////////////////////
									Document document = DocumentHelper.createDocument();

									Element object = document.addElement("object");
									PCRFhead(object, param);

									Element bodyObject = object.addElement("body_object");
									bodyObject.addAttribute("CommandId", "TD");
									PCRFTDinfo(bodyObject, param);//退订报文拼装
									String xmlTD2 = XmlToString(document);
									//测试用
									System.out.println("拼装退订报文");
									System.out.println(xmlTD2);
									//

									Map<String, Object> esbDesc = sendPCRFMsg(xmlTD2);
									String resutCode = convertDBObj2Str(esbDesc.get("resutCode"));//esb返回
									String errCode = convertDBObj2Str(esbDesc.get("errCode"));//esb返回code
									String desc = convertDBObj2Str(esbDesc.get("desc"));//esb返回描述

									//int resutCode = 0;
									//									AcchiveList.add(getHisParamMap(acchiveId));
									if (Integer.parseInt(resutCode) != 0) {
										String resultCode = "1";
										String resultMsg = "调用ESB接口返回错误";
										Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")),
												TransactionIdTD2, xmlTD2, resultCode, resultMsg, errCode, desc));
										continue;

									} else {
										//mainFlag = true;
										String resultCode = "0";
										String resultMsg = "成功";
										AcchiveList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")),
												TransactionIdTD2, xmlTD2, resultCode, resultMsg, errCode, desc));
									}
								}

							}

						}
					}

				}
				if (num == 0) {
					//没有合适的4G产品返回false
					//流水ID
					synchronized (this) {
						Date d = new Date();
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
						String transId = intfDAO.getPCRFTransactionId();
						String TransactionIdNO = sdf2.format(d) + transId;
						String resultCode = "2";
						String resultMsg = "没有订购4G产品直接返回";
						//pcrfDAO.updateC2uPCRFSt(acchiveId, "C");//修改信息表状态为C处理完成
						AcchiveNO4GList.add(getHisParamMap(acchiveId, TransactionIdNO, "空", resultCode, resultMsg, "",
								""));
					}
				}

				//全部跑完，返回BOOLEAN
				intfDAO.updateC2uPCRFSt(acchiveId, "C");//修改信息表状态为C处理完成
				return true;

			} else {
				//没有合适的4G产品返回false
				synchronized (this) {
					Date d = new Date();
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
					String transId = intfDAO.getPCRFTransactionId();
					String TransactionIdNO2 = sdf2.format(d) + transId;
					String resultCode = "2";
					String resultMsg = "没有订购4G产品直接返回";
					intfDAO.updateC2uPCRFSt(acchiveId, "C");//修改信息表状态为C处理完成
					AcchiveNO4GList
							.add(getHisParamMap(acchiveId, TransactionIdNO2, "空", resultCode, resultMsg, "", ""));
					return true;
				}
			}

		} catch (NumberFormatException e) {
			Date d = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
			String transId = intfDAO.getPCRFTransactionId();
			String TransactionIdERR = sdf2.format(d) + transId;
			intfDAO.updateC2uPCRFSt(acchiveId, "F");//修改信息表状态为F处理失败
			Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")), TransactionIdERR, "空", "4",
					"类型转换错误", "", ""));
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			Date d = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHH");
			String transId = intfDAO.getPCRFTransactionId();
			String TransactionIdERR = sdf2.format(d) + transId;
			intfDAO.updateC2uPCRFSt(acchiveId, "F");//修改信息表状态为F处理失败
			Acchive2ESBERRList.add(getHisParamMap(convertDBObj2Str(param.get("acchiveId")), TransactionIdERR, "空", "5",
					"抛出异常", "", ""));
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (AcchiveList.size() > 0) {
					intfDAO.saveC2uPCRFToHis(AcchiveList, acchiveId);//正确成功数据转存数据到历史表
				}
				if (Acchive2ESBERRList.size() > 0) {
					intfDAO.saveC2uPCRF2ESBERRToHis(Acchive2ESBERRList, acchiveId);//发送ESB返回0的转存数据到历史表
				}
				if (AcchiveNO4GList.size() > 0) {
					intfDAO.saveC2uPCRFNO4GToHis(AcchiveNO4GList, acchiveId);//没有订购4G产品的转存数据到历史表
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			intfDAO.delC2uPCRFData(acchiveId);//清空原数据
		}

	}

	@Override
	public List<Map> csipPCRFListByst(int i) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("threadIndex", i);
		List<Map> temp = intfDAO.csipPCRFListByst(map);
		return temp;
	}

	public Map<String, Object> sendPCRFMsg(String msg) throws Exception {
		//http://172.19.17.184:7102/ACCESS/services/CrmWebService?wsdl
		//esbWsdlLocation
		//String esbWsdlLocation = PropertiesUtils.getPropertiesValue("ESB_WEBSERVICE_URL");
		String esbWsdlLocation = "http://172.19.17.184:7102/ACCESS/services/CrmWebService?wsdl";
		log.debug("-----取得esb地址：" + esbWsdlLocation);
		Service service = new Service();
		Call call = (Call) service.createCall();
		//		resultCode = "3";
		//		resultMsg = esbWsdlLocation + "未取得ESB地址";
		call.setTargetEndpointAddress(new java.net.URL(esbWsdlLocation));
		call.setOperationName(new QName("http://crm.crmwsi", "crmXmlService"));
		call.addParameter("request", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(esbWsdlLocation);
		String result = (String) call.invoke(new Object[] { msg });
		Document document = null;
		document = XMLDom4jUtils.fromXML(result, null);
		Element root = document.getRootElement();
		Element element = root.element("TcpCont");
		Element response = element.element("Response");
		int resutCode = Integer.parseInt(response.elementText("RspType"));
		String errCode = response.elementText("RspCode");
		String desc = response.elementText("RspDesc");
		System.out.println(desc + "==========================");
		System.out.println(errCode + "+++++++++++++++++++++++++++++");
		//		resultCode = "0";
		//		resultMsg = "成功";
		log.debug("-----ESB返回" + errCode + "--------" + desc);
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("errCode", errCode);
		temp.put("desc", desc);
		temp.put("resutCode", resutCode);
		//xml = msg;
		return temp;
		//		if (resutCode != 0) {
		//			resultCode = "1";
		//			resultMsg = "调用ESB接口返回错误";
		//			throw new Exception("[" + errCode + "]" + desc);
		//		}

	}

	public Map<String, Object> getHisParamMap(String acchiveId, String TransactionIdNew, String xml, String resultCode,
			String resultMsg, String errCode, String desc) {
		Map<String, Object> hisParam = new HashMap<String, Object>();
		hisParam.put("acchiveId", acchiveId);
		hisParam.put("errCode", errCode);
		hisParam.put("desc", desc);
		hisParam.put("TransactionId", TransactionIdNew);
		hisParam.put("xml", xml);
		hisParam.put("resultCode", resultCode);
		hisParam.put("resultMsg", resultMsg);
		return hisParam;
	}

	public Element PCRFhead(Element object, Map<String, Object> param) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String TimeStamp = sdf.format(d);
		//TransactionId = sdf2.format(d);

		//		param.put("acchiveId", acchiveId);
		//		param.put("priority", priority);
		//		param.put("custID", custID);
		//		param.put("paymentMode", PaymentMode);
		//		param.put("olNbr", olNbr);
		//		param.put("offerParam", offerParam);
		//			param.put("accNbr", accNbr);

		Element head = object.addElement("head_object");
		head.addElement("head").addAttribute("head_name", "Order_type");
		head.addElement("head").addAttribute("head_name", "OriginSystemId").setText("1001");
		head.addElement("head").addAttribute("head_name", "DestSystemId").setText("1002");
		head.addElement("head").addAttribute("head_name", "TransactionId").setText(
				convertDBObj2Str(param.get("TransactionId")));
		head.addElement("head").addAttribute("head_name", "TimeStamp").setText(TimeStamp);
		head.addElement("head").addAttribute("head_name", "ArchiveGroupID").setText(
				convertDBObj2Str(param.get("acchiveId")));
		head.addElement("head").addAttribute("head_name", "Version").setText("1");
		head.addElement("head").addAttribute("head_name", "OrderNumber").setText("0");
		head.addElement("head").addAttribute("head_name", "PreOrderNumber").setText(
				convertDBObj2Str(param.get("PreOrderNumber")));
		head.addElement("head").addAttribute("head_name", "PaymentMode").setText(
				convertDBObj2Str(param.get("paymentMode")));
		head.addElement("head").addAttribute("head_name", "CustID").setText(convertDBObj2Str(param.get("custID")));
		head.addElement("head").addAttribute("head_name", "Priority").setText(convertDBObj2Str(param.get("priority")));
		head.addElement("head").addAttribute("head_name", "OlNbr").setText(convertDBObj2Str(param.get("olNbr")));
		head.addElement("head").addAttribute("head_name", "OfferParam").setText(
				convertDBObj2Str(param.get("offerParam")));
		log.debug("-----拼装head报文成功-------");
		return object;
	}

	public Element PCRFD1info(Element object, Map<String, Object> param) {
		if (param == null || param.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			//rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "oldusrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrMSISDN").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrState").setText(
					convertDBObj2Str(param.get("usrState")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrPaidType").setText(
					convertDBObj2Str(param.get("paymentMode")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrStation").setText(
					convertDBObj2Str(param.get("usrStation")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrMasterIdentifier");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrBillCycleDate").setText(
					convertDBObj2Str(param.get("usrBillCycleDate")));

			log.debug("-----拼装PCRF新装报文成功-------");
			return object;
		}

	}

	public Element PCRFD3info(Element object, Map<String, Object> param) {
		if (param == null || param.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			//rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "oldusrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrMSISDN").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrState").setText(
					convertDBObj2Str(param.get("usrState")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrPaidType").setText(
					convertDBObj2Str(param.get("paymentMode")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrStation").setText(
					convertDBObj2Str(param.get("usrStation")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrMasterIdentifier");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrBillCycleDate").setText(
					convertDBObj2Str(param.get("usrBillCycleDate")));

			log.debug("-----拼装PCRF修改报文成功-------");
			return object;
		}

	}

	public Element PCRFD2info(Element object, Map<String, Object> param) {

		if (param == null || param.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			//rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));

			log.debug("-----拼装PCRF拆机报文成功-------");
			return object;
		}
	}

	public Element PCRFDGinfo(Element object, Map<String, Object> param) {

		if (param == null || param.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			//rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "srvName").setText(
					convertDBObj2Str(param.get("PCRF_SERV_ID")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "srvStartDateTime").setText(
					convertDBObj2Str(param.get("STATE_DATE")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "srvEndDateTime").setText(
					convertDBObj2Str(param.get("END_DATE")));

			log.debug("-----拼装PCRF订购报文成功-------");
			return object;
		}
	}

	public Element PCRFTDinfo(Element object, Map<String, Object> param) {

		if (param == null || param.isEmpty()) {
			//将取出来的数据拼接成报文
			return object;
		} else {
			//rec_num += 1;
			Element bodyAttr = object.addElement("body_attr").addAttribute("rec_num", "1");
			bodyAttr.addElement("attr").addAttribute("attr_name", "usrIdentifier").setText(
					convertDBObj2Str(param.get("accNbr")));
			bodyAttr.addElement("attr").addAttribute("attr_name", "srvName").setText(
					convertDBObj2Str(param.get("PCRF_SERV_ID")));

			log.debug("-----拼装PCRF退订报文成功-------");
			return object;
		}
	}

	@Override
	public Map<String, Object> queryOrderList(String orderId) {
		return intfDAO.queryOrderList(orderId);
	}

	@Override
	public Long getDevNumIdByAccNum(String accNum) {
		return intfDAO.getDevNumIdByAccNum(accNum);
	}

	@Override
	public Long getValidateYHParams(Map<String, Object> paraMap) {
		return intfDAO.getValidateYHParams(paraMap);
	}

	@Override
	public Long getValidateYHOffer(Map<String, Object> offerMap) {
		return intfDAO.getValidateYHOffer(offerMap);
	}

	@Override
	public String getLteImsiByProdid(Long prodId) {
		return intfDAO.getLteImsiByProdid(prodId);
	}

	@Override
	public void saveJSONObject(String jsonObjectStr) {
		intfDAO.saveJSONObject(jsonObjectStr);

	}

	@Override
	public void updateJSONObject(String resultStr) {
		intfDAO.updateJSONObject(resultStr);
	}

	@Override
	public String querySpeedValue(String prodSpecId) {
		return intfDAO.querySpeedValue(Long.valueOf(prodSpecId));
	}

	@Override
	public Map<String, Object> getProdSmsByProdId(String prodid) {
		return intfDAO.getProdSmsByProdId(prodid);
	}
	
	@Override
	public Map<String, Object> getProdInfoByAccNbr(String accNbr) {
		return intfDAO.getProdInfoByAccNbr(accNbr);
	}
	
	@Override
	public Map<String, Object> getCouponInfoByTerminalCode(String terminalCode) {
		return intfDAO.getCouponInfoByTerminalCode(terminalCode);
	}
	
	@Override
	public Map<String, Object> getBasicCouponInfoByTerminalCode(String terminalCode) {
		return intfDAO.getBasicCouponInfoByTerminalCode(terminalCode);
	}
	
	@Override
	public Map<String, Object> queryTerminalCodeByProdId(String prodId) {
		return intfDAO.queryTerminalCodeByProdId(prodId);
	}
	
	@Override
	public Map<String, Object> getDevInfoByCode(String devCode) {
		return intfDAO.getDevInfoByCode(devCode);
	}
	
	@Override
	public Map<String, Object> getStaffIdByStaffCode(String staffCode) {
		return intfDAO.getStaffIdByStaffCode(staffCode);
	}

	@Override
	public boolean isUimBak(String prodid) {

		return intfDAO.isUimBak(prodid);
	}

	@Override
	public boolean getIsOrderOnWay(Map<String, Object> map) {

		return intfDAO.getIsOrderOnWay(map);
	}

	@Override
	public String findTelphoneByCardno(String terminalCode) {

		return intfDAO.findTelphoneByCardno(terminalCode);
	}

	@Override
	public String findTelphoneByDiscard(String terminalCode) {

		return intfDAO.findTelphoneByDiscard(terminalCode);
	}

	@Override
	public boolean isExistCardByProdId(Map<String, Object> map) {

		return intfDAO.isExistCardByProdId(map);
	}

	@Override
	public Map<String, Object> getImsiInfoByBillingNo(String billingNo) {
		return intfDAO.getImsiInfoByBillingNo(billingNo);
	}

	@Override
	public List<Map<String, Object>> getBillingCardRelation(String billingNo) {
		return intfDAO.getBillingCardRelation(billingNo);
	}
	
	@Override
	public List<Map<String, Object>> getDevNumIdByDevCode(String devCode) {
		return intfDAO.getDevNumIdByDevCode(devCode);
	}
	
	@Override
	public List<Map<String, Object>> queryTableSpace() {

		return intfDAO.queryTableSpace();
	}

	@Override
	public List<Map<String, Object>> queryUseSpaceNotSysLob(String tableSpaceName) {
		return intfDAO.queryUseSpaceNotSysLob(tableSpaceName);
	}

	@Override
	public List<Map<String, Object>> queryUseSpaceSysLob(String tableSpaceName) {
		return intfDAO.queryUseSpaceSysLob(tableSpaceName);
	}

	@Override
	public List<Map<String, Object>> queryCrmLockInfo() {
		return intfDAO.queryCrmLockInfo();
	}

	@Override
	public Long queryDBSessionInfo() {
		return intfDAO.queryDBSessionInfo();
	}

	@Override
	public boolean saveBenzBusiOrder(Map<String, Object> result) {

		return intfDAO.saveBenzBusiOrder(result);
	}

	@Override
	public void saveBenzBusiOrderSub(Map<String, Object> resultSub) {
		intfDAO.saveBenzBusiOrderSub(resultSub);
	}

	@Override
	public boolean isBenzOfferServUser(String accNbr) {
		return intfDAO.isBenzOfferServUser(accNbr);
	}

	@Override
	public List<Map<String, Object>> getProdItemsByParam(Map<String, Object> param) {

		return intfDAO.getProdItemsByParam(param);
	}

	@Override
	public List<Map<String, Object>> getCustClassInfoByCustId(String custId) {

		return intfDAO.getCustClassInfoByCustId(custId);
	}

	@Override
	public String getCustIdByAccNum(String accessNumber) {

		return intfDAO.getCustIdByAccNum(accessNumber);
	}

	@Override
	public boolean qryProdOrderIsZtByOrderTypes(Map<String, Object> map) {

		return intfDAO.qryProdOrderIsZtByOrderTypes(map);
	}

	@Override
	public Map<String, Object> queryOfferProdStatus(String accessNumber) {

		return intfDAO.queryOfferProdStatus(accessNumber);
	}

	@Override
	public boolean isFtWifiSystem(String accessNumber) {
		return intfDAO.isFtWifiSystem(accessNumber);
	}

	@Override
	public int checkHykdOrder(Map<String, Object> param) {

		return intfDAO.checkHykdOrder(param);
	}

	@Override
	public Map<String, String> checkCustName(String phone_number, String cust_name) {

		return intfDAO.checkCustName(phone_number, cust_name);
	}

	@Override
	public Map<String, Object> getPartyTypeCdByProdId(Long prodidByAccNbr) {

		return intfDAO.getPartyTypeCdByProdId(prodidByAccNbr);
	}

	@Override
	public boolean checkOfferSpecBsns(String id) {

		return intfDAO.checkOfferSpecBsns(id);
	}

	@Override
	public long checkBankFreeze(Map<String, Object> checkMap) {

		return intfDAO.checkBankFreeze(checkMap);
	}

	@Override
	public Map<String, Object> qryOrderListByOlId(String olId) {

		return intfDAO.qryOrderListByOlId(olId);
	}

	@Override
	public String getNbrTypeByAccNbr(String accNbr) {

		return intfDAO.getNbrTypeByAccNbr(accNbr);
	}

	@Override
	public List<Map<String, Object>> qryEncryptStrByParam(Map<String, String> param) {

		return intfDAO.qryEncryptStrByParam(param);
	}

	@Override
	public Map<String, Object> queryOrgByStaffNumber(String staffNumber) {

		return intfDAO.queryOrgByStaffNumber(staffNumber);
	}

	@Override
	public Map<String, Object> findOrgByStaffId(Map<String, Object> map) {

		return intfDAO.findOrgByStaffId(map);
	}

	@Override
	public List<Long> queryStaffByProdId(Long prodIdLong) {

		return intfDAO.queryStaffByProdId(prodIdLong);
	}

	@Override
	public String getIDCardEncryptionVector(String mac) {

		return intfDAO.getIDCardEncryptionVector(mac);
	}

	@Override
	public List<Map<String, Object>> getGhAddressTemp() {

		return intfDAO.getGhAddressTemp();
	}

	@Override
	public void insertGhAddressUnit(Map<String, Object> param) {

		intfDAO.insertGhAddressUnit(param);
	}

	@Override
	public long queryBussinessOrder(Map<String, Object> mk) {

		return intfDAO.queryBussinessOrder(mk);
	}

	@Override
	public long querySeqBussinessOrder() {

		return intfDAO.querySeqBussinessOrder();
	}

	@Override
	public void saveOrUpdateBussinessOrderCheck(Map<String, Object> mk, String str) {
		intfDAO.saveOrUpdateBussinessOrderCheck(mk, str);
	}

	@Override
	public int isAccNumRealNameparty(Long prodId) {

		return intfDAO.isAccNumRealNameparty(prodId);
	}

	@Override
	public String getIntfReqCtrlValue(String string) {

		return intfDAO.getIntfReqCtrlValue(string);
	}

	@Override
	public  Map<String, Object>  checkParByIdCust(Map<String, Object> m) {

		return intfDAO.checkParByIdCust(m);
	}

	@Override
	public Map<String, Object> queryOfferProdItemsByProdId(Long prodId) {

		return intfDAO.queryOfferProdItemsByProdId(prodId);
	}

	@Override
	public boolean isManyPartyByIDNum(Map<String, Object> m) {

		return intfDAO.isManyPartyByIDNum(m);
	}

	@Override
	public boolean insertSms(Map<String, Object> map) {

		return intfDAO.insertSms(map);
	}

	@Override
	public List<Map<String, Object>> qryBoInfos(Map<String, Object> mv) {
		return intfDAO.qryBoInfos(mv);
	}

	@Override
	public Long qryChargesByOlid(String ol_id) {
		
		return intfDAO.qryChargesByOlid(ol_id);
	}

	@Override
	public String queryOfferAddressDesc(Long prod_id) {

		return intfDAO.queryOfferAddressDesc(prod_id);
	}

	@Override
	public String getTmDescription(Long prodId) {

		return intfDAO.getTmDescription(prodId);
	}

	@Override
	public String getOfferProdTmlName(Long prodId) {
		
		return intfDAO.getOfferProdTmlName(prodId);
	}

	@Override
	public String getPartyManagerName(Long prodId) {
		
		return intfDAO.getPartyManagerName(prodId);
	}

	@Override
	public int getOfferSpecAction2Count(String offerSpecId) {
		
		return intfDAO.getOfferSpecAction2Count(offerSpecId);
	}

	@Override
	public String getCommunityPolicy(String buildingId) {
		
		return intfDAO.getCommunityPolicy(buildingId);
	}

	@Override
	public List<Map> getOfferMemberInfo(String prodId) {

		return intfDAO.getOfferMemberInfo(prodId);
	}

	@Override
	public String getComponentBuildingId(String serviceId) {

		return intfDAO.getComponentBuildingId(serviceId);
	}

	@Override
	public String getOrganizStaffOrgId(Long prodId) {
		
		return intfDAO.getOrganizStaffOrgId(prodId);
	}
	@Override
	public String getDevelopmentDepartment(String accessNumber) {
		
		return intfDAO.getDevelopmentDepartment(accessNumber);
	}

	@Override
	public String getChannelNbrByChannelID(String channelId) {
		
		return intfDAO.getChannelNbrByChannelID(channelId);
	}

	@Override
	public int getCmsStaffCodeByStaffCode(String staffCode) {
		
		return intfDAO.getCmsStaffCodeByStaffCode(staffCode);
	}

	@Override
	public String getOfferProdReduOwnerIdByAccNbr(String accNbr) {

		return intfDAO.getOfferProdReduOwnerIdByAccNbr(accNbr);
	}

	@Override
	public Map<String, Object> getSaopRuleIntfLogByTransactionID(String transactionID) {
		
		return intfDAO.getSaopRuleIntfLogByTransactionID(transactionID);
	}
	@Override
	public List<Map<String, Object>> validatePhoneCanChangeBand(String accNbr) {
		
		return intfDAO.validatePhoneCanChangeBand(accNbr);
	}
	@Override
	public List<Map<String, Object>> queryMainOfferPackageRelation(String offerSpecId) {
		
		return intfDAO.queryMainOfferPackageRelation(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryAccNbrInfoList(String accNbr) {
		
		return intfDAO.queryAccNbrInfoList(accNbr);
	}
	@Override
	public Map<String, Object> queryAccessTypeByAccessNumber(String accNbr,String itemSpecIds){
		
		return intfDAO.queryAccessTypeByAccessNumber(accNbr,itemSpecIds);
	}
	@Override
	public List<Map<String, Object>> getProdCompList(String accNbr) {
		
		return intfDAO.getProdCompList(accNbr);
	}
	@Override
	public Map<String, Object> queryCompAccNbrByCompProdId(String compProdId) {
		
		return intfDAO.queryCompAccNbrByCompProdId(compProdId);
	}
	@Override
	public Map<String, Object> querySpeedInfoByOfferSpecId(String offerSpecId) {
		
		return intfDAO.querySpeedInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryChangeOffersByOfferSpecId(String offerSpecId) {
		
		return intfDAO.queryChangeOffersByOfferSpecId(offerSpecId);
	}
	@Override
	public boolean judgeMainOfferByOfferSpecId(String offerSpecId) {
		
		return intfDAO.judgeMainOfferByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryCompInfoListByOfferSpecId(String offerSpecId) {
		
		return intfDAO.queryCompInfoListByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String, Object>> queryCompMainOfferByProdSpecId(Map<String,Object> paraMap) {
		
		return intfDAO.queryCompMainOfferByProdSpecId(paraMap);
	}
	@Override
	public Map<String, Object> getProdInfoByAccessNuber(String olId) {
		
		return intfDAO.getProdInfoByAccessNuber(olId);
	}
	@Override
	public Map<String, Object> getServInfoByOfferSpecId(String offerSpecId) {
		
		return intfDAO.getServInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public List<Map<String,Object>> getComBasicInfoByOfferSpecId(String offerSpecId) {
		
		return intfDAO.getComBasicInfoByOfferSpecId(offerSpecId);
	}
	@Override
	public String queryStaffIdByStaffCode(String staffCode){
		
		return intfDAO.queryStaffIdByStaffCode(staffCode);
	}

	@Override
	public Map<String, Object> getMethForFtth(String prodId) {
		return intfDAO.getMethForFtth(prodId);
	}

	@Override
	public String getProValue(String prodId) {
		return intfDAO.getProValue(prodId);
	}

	@Override
	public Map<String, Object> getaddresForFtth(String prodId) {
		return intfDAO.getaddresForFtth(prodId);
	}

	@Override
	public Map<String, Object> getTmlForFtth(String prodId) {
		return intfDAO.getTmlForFtth(prodId);
	}

	@Override
	public Map<String, Object> getgetprod2TdIdDelCodeCode(String terminalCode) {
		return intfDAO.getgetprod2TdIdDelCodeCode(terminalCode);
	}

	@Override
	public boolean checkSchool(String channelId) {
		return intfDAO.checkSchool(channelId);
	}

	@Override
	public List<Map<String, Object>> queryExchangeByName(String name) {
		return intfDAO.queryExchangeByName(name);
	}

	@Override
	public List<Map<String, Object>> getchannelByCode(String code) {
		return intfDAO.getchannelByCode(code);
	}

	@Override
	public List<Map<String, Object>> queryDeptInfo(String name,String level) {
		return intfDAO.queryDeptInfo(name,level);
	}

	@Override
	public List<Map<String, Object>> getOlidByg(String olId) {
		return intfDAO.getOlidByg(olId);
	}

	@Override
	public List<Map<String, Object>> queryChannelsByMap(String staffId) {
		return intfDAO.queryChannelsByMap(staffId);
	}

	@Override
	public List<Map<String, Object>> checkCinfoByb(String code) {
		return intfDAO.checkCinfoByb(code);
	}

	@Override
	public Map<String, Object> queryByPartyId(String partyId) {
		return intfDAO.queryByPartyId(partyId);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsList(String time , String staffId) {
		return intfDAO.queryIndicatorsList(time , staffId);
	}

	@Override
	public List<Map<String, Object>> queryIndicatorsListMouth(String startTime,
			String endTime,String staffId) {
		return intfDAO.queryIndicatorsListMouth(startTime,endTime,staffId);
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsList(
			String statisticalTime, String startRown ,String endRown, String staffId,String accessNumber
			) {
		return intfDAO.queryDetailedIndicatorsList(
			statisticalTime, startRown,endRown, staffId, accessNumber
				);
	}

	@Override
	public Map<String, Object> queryIndicatorsNumber(String statisticalTime,
			String staffId,String accessNumber) {
		return intfDAO.queryIndicatorsNumber(statisticalTime,
				staffId,accessNumber);
	}

	@Override
	public Map<String, Object> queryEmailByPartyId(String partyId) {
		return intfDAO.queryEmailByPartyId(partyId);
	}

	@Override
	public List<Map<String, Object>> getValueByolId(String olId) {
		return intfDAO.getValueByolId(olId);
	}

	@Override
	public List<Map<String, Object>> getOlnbrByg(String olId) {
		return intfDAO.getOlnbrByg(olId);
	}

	@Override
	public String getNumByStatus(String specId) {
		return intfDAO.getNumByStatus(specId);
	}
	
	@Override
	public Map<String, Object> queryIndicators1(String statisticalTime,
			String staffId) {
		
		return intfDAO.queryIndicators1(statisticalTime, staffId
				);
	}

	@Override
	public Map<String, Object> queryIndicators2(String statisticalTime,
			String staffId) {
		
		return intfDAO.queryIndicators2(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators3(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators3(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators4(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators4(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators9(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators9(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators5(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators5(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators6(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators6(statisticalTime, staffId
		);
	}
	
	@Override
	public Map<String, Object> queryIndicators7(String statisticalTime,
			String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicators7(statisticalTime, staffId
		);
	}
	
	@Override
	public Map<String, Object> queryIndicators8(String statisticalTime,
			String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicators8(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators10(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators10(statisticalTime, staffId
		);
	}

	@Override
	public Map<String, Object> queryIndicators11(String statisticalTime,
			String staffId) {
		return intfDAO.queryIndicators11(statisticalTime, staffId
		);
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth1(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth1(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth2(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth2(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth3(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth3(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth10(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth10(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth11(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth11(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth12(String statisticalTime) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth12(statisticalTime);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth4(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth4(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth5(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth5(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth6(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth6(startTime,
				endTime, staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth7(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth7(startTime,
				endTime, staffId);
	}
	
	@Override
	public Map<String, Object> queryIndicatorsMouth8(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth8(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> queryIndicatorsMouth9(String startTime,
			String endTime, String staffId) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsMouth9(startTime,
				endTime, staffId);
	}

	@Override
	public Map<String, Object> getAddressPreemptedSeq() {
		return intfDAO.getAddressPreemptedSeq();
	}

	@Override
	public Map<String, Object> showSchoolName(String bcdCode) {
		return intfDAO.showSchoolName(bcdCode);
	}

	@Override
	public List<Map<String, Object>> qryDeptById(String dept_id) {
		// TODO Auto-generated method stub
		return intfDAO.qryDeptById(dept_id);
	}

	@Override
	public List<Map<String, Object>> campusCustomerCampusMark(String subjectId,
			String subjectNameId) {
		// TODO Auto-generated method stub
		return intfDAO.campusCustomerCampusMark(subjectId,subjectNameId);
	}

	@Override
	public List<Map<String, Object>> queryCampusCustomerInformation(String num) {
		// TODO Auto-generated method stub
		return intfDAO.queryCampusCustomerInformation(num);
	}

	@Override
	public void saveSendRecord(Map<String, Object> saveMap) {
		// TODO Auto-generated method stub
		intfDAO.saveSendRecord(saveMap);
		
	}

	@Override
	public List<Map<String, Object>> queryDetailedIndicatorsListMouth(
			String startTime, String endTime, String startRown ,String endRown,
			String staffId,String accessNumber) {
		// TODO Auto-generated method stub
		return intfDAO.queryDetailedIndicatorsListMouth( startTime,  endTime,  startRown, endRown,
				 staffId, accessNumber);
	}

	@Override
	public Map<String, Object> qryKeyByFlag() {
		// TODO Auto-generated method stub
		return intfDAO.qryKeyByFlag();
	}

	@Override
	public Map<String, Object> queryIndicatorsNumberMouth(String startTime,
			String endTime, String staffId, String accessNumber) {
		// TODO Auto-generated method stub
		return intfDAO.queryIndicatorsNumberMouth(startTime,endTime,staffId,accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryRewardInfoById(String staffId,
			String stardTime, String endTime,String startNum,String endNum) {
		return intfDAO.queryRewardInfoById(staffId,stardTime,endTime,startNum,endNum);
	}

	@Override
	public Map<String, Object> queryRewardSum(String staffId, String stardTime,
			String endTime) {
		return intfDAO.queryRewardSum(staffId,stardTime,endTime);
	}

	@Override
	public Map<String, Object> queryRewardInfo(String rewardId) {
		return intfDAO.queryRewardInfo(rewardId);
	}

	@Override
	public List<Map<String, Object>> querypartyListBypartyList(String idCard) {
		return intfDAO.querypartyListBypartyList(idCard);
	}

	@Override
	public List<Map<String, Object>> queryProListByProId(String prodId) {
		return intfDAO.queryProListByProId(prodId);
	}

	@Override
	public Map<String, Object> queryOlidByProId(String prodId) {
		return intfDAO.queryOlidByProId(prodId);
	}

	@Override
	public Map<String, Object> queryUimCodeByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return intfDAO.queryUimCodeByAccessNumber(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOlIdByAccessNumber(String accessNumber) {
		// TODO Auto-generated method stub
		return intfDAO.queryOlIdByAccessNumber(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryOrganizationforScrm(
			String staffNumber, String staffName, String organizationId,
			String ruleNumber) {
		return intfDAO.queryOrganizationforScrm(staffNumber, staffName, organizationId,
				ruleNumber);
	}

	@Override
	public Map<String, Object> queryOlNbrByOlId(String id) {
		// TODO Auto-generated method stub
		return intfDAO.queryOlNbrByOlId(id);
	}

	@Override
	public List<Map<String, Object>> queryRewardSource(String olNbr) {
		// TODO Auto-generated method stub
		return intfDAO.queryRewardSource(olNbr);
	}

	@Override
	public void insertReward(Map<String, String> para) {
		// TODO Auto-generated method stub
		intfDAO.insertReward(para);
	}

	@Override
	public String queryRewardId() {
		return intfDAO.queryRewardId();
	}

	@Override
	public Map<String, Object> getFingerInfo(String olId) {
		return intfDAO.getFingerInfo(olId);
	}

	@Override
	public void insertFingerInfo(Map<String, Object> fingerInfo) {
		intfDAO.insertFingerInfo(fingerInfo);		
	}

	@Override
	public List<Map<String, Object>> queryStoreByStaffId(String staffId) {
		return intfDAO.queryStoreByStaffId(staffId);
	}

	@Override
	public List<Map<String, Object>> queryStatusByInstCode(String mktResInstCode) {
		return intfDAO.queryStatusByInstCode(mktResInstCode);
	}

	@Override
	public Map<String, Object> getStaffIdBystaffNumber(String staffNumber) {
		return intfDAO.getStaffIdBystaffNumber(staffNumber);
	}

	@Override
	public List<Map<String, Object>> getEdidInfo() {
		return intfDAO.getEdidInfo();
	}

	@Override
	public void edidType(String partyId) {
		intfDAO.edidType(partyId);
	}

	@Override
	public Map<String, Object> queryIdentityInfoByOlnbr(String olNbr) {
		return intfDAO.queryIdentityInfoByOlnbr(olNbr);
	}

	@Override
	public Map<String, Object> queryProdInfoByAccs(String accessNumber) {
		return intfDAO.queryProdInfoByAccs(accessNumber);
	}

	@Override
	public List<Map<String, Object>> queryDepinfoForScrm(String partyId) {
		return intfDAO.queryDepinfoForScrm(partyId);
	}

	@Override
	public List<Map<String, Object>> queryUserdByAccess(String accessNuber) {
		return intfDAO.queryUserdByAccess(accessNuber);
	}

	@Override
	public Map<String, Object> queryExtCustOrder(String queryExtCustOrder) {
		return intfDAO.queryExtCustOrder(queryExtCustOrder);
	}

	@Override
	public String[] findPartyByIdentityPic(String name, String identityNum) {
		return intfDAO.findPartyByIdentityPic(name,identityNum);
	}

	@Override
	public List<Map<String, Object>> queryPhoneNoByAliUid(String aliUid) {
		return intfDAO.queryPhoneNoByAliUid(aliUid);
	}

	@Override
	public String getParentOrgId(String mailAddressId) {
		return intfDAO.getParentOrgId(mailAddressId);
	}

	@Override
	public void upStarMember(Map<String, Object> parameter) {
		intfDAO.upStarMember(parameter);
		
	}

	@Override
	public Map<String, Object> queryCaflagByOlnbr(String olNbr) {
		return intfDAO.queryCaflagByOlnbr(olNbr);
	}

	@Override
	public String queryBlocOlidToProOlid(String olId) {
		return intfDAO.queryBlocOlidToProOlid(olId);
	}

	@Override
	public void insertPartyFlagInfo(Map<String, Object> autonymFlag) {
		intfDAO.insertPartyFlagInfo(autonymFlag);
	}

	@Override
	public String queryBlocOlidToProOlNbr(String olId) {
		return intfDAO.queryBlocOlidToProOlNbr(olId);
	}

	@Override
	public String queryBlocOlidToBlocOlNbr(String olNbr) {
		return intfDAO.queryBlocOlidToBlocOlNbr(olNbr);
	}

	@Override
	public void InsertOutskirts(Map<String, Object> parameter) {
		intfDAO.InsertOutskirts(parameter);
	}

	@Override
	public Boolean queryOlidIfHave(String olId) {
		Map<String, Object> result = intfDAO.queryOlidIfHave(olId);
		if(result == null){
			return false;
		}
		return true;
	}

	@Override
	public void updataOutskirts(Map<String, Object> parameter) {
		intfDAO.updataOutskirts(parameter);
	}

	@Override
	public String queryPartyIdByprodId(String prodId) {
		Map<String, Object> result = intfDAO.queryPartyIdByprodId(prodId);
		if(result == null){
			return "";
		}
		return result.get("REDU_OWNER_ID").toString();
	}

	@Override
	public String queryStaffIdBystaffCode(String staffCode) {
		Map<String, Object> result = intfDAO.queryStaffIdBystaffCode(staffCode);
		if(result == null){
			return "";
		}
		return result.get("STAFF_ID").toString();
	}

	@Override
	public String queryOfferspecidBystaffCode(String prodId) {
		Map<String, Object> result = intfDAO.queryOfferspecidBystaffCode(prodId);
		if(result == null){
			return "";
		}
		return result.get("PRODSPECID").toString();
	}

	@Override
	public Map<String, Object> queryOldcustinfoByPartyId(String partyId) {
		return intfDAO.queryOldcustinfoByPartyId(partyId);
	}

	@Override
	public String queryPartyIdByCardId(String cerdValue) {
		Map<String, Object> result = intfDAO.queryPartyIdByCardId(cerdValue);
		if(result == null){
			return "";
		}
		return result.get("PARTY_ID").toString();
	}

	@Override
	public Boolean getSchoolRole(String staffId) {
		Map<String, Object> result = intfDAO.getSchoolRole(staffId);
		if(result == null){
			return true;
		}
		return false;
	}

	@Override
	public String queryGroupNumberByGroupId(String olId) {
		Map<String, Object> result = intfDAO.queryGroupNumberByGroupId(olId);
		if(result == null){
			return "";
		}
		return result.get("VALUE").toString();
	}

	@Override
	public String queryProvenceIdByGroupNum(String olId) {
		List<Map<String, Object>> result ;
		result = intfDAO.queryProvenceIdByGroupNum(olId);
		if(result == null){
			return "";
		}
		if(result.size() == 0){
			return "";
		}
		return result.get(0).get("OL_ID").toString();
	}

	@Override
	public Map<String, Object> queryBureauDirectionByPhoneNum(String phoneNumber) {
		return intfDAO.queryBureauDirectionByPhoneNum(phoneNumber);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByIdent(String custInfo) {
		return intfDAO.queryRealNameFlagByIdent(custInfo);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByParttId(String custInfo) {
		return intfDAO.queryRealNameFlagByParttId(custInfo);
	}

	@Override
	public List<Map<String, Object>> queryRealNameFlagByPhoneAccssnumber(
			String custInfo) {
		return intfDAO.queryRealNameFlagByPhoneAccssnumber(custInfo);
	}

	@Override
	public Map<String, Object> queryPictureByolId(String olId) {
		return intfDAO.queryPictureByolId(olId);
	}

	@Override
	public Map<String, Object> querygztPictureInfoByolId(String olId) {
		return intfDAO.querygztPictureInfoByolId(olId);
	}

	@Override
	public String queryAccIdbyAccCd(String accId) {
		return intfDAO.queryAccIdbyAccCd(accId);
	}

	@Override
	public List<Map<String, Object>> desensitizationService(String object) {
		return intfDAO.desensitizationService(object);
	}

	@Override
	public String desensitizationSystemCode(String desensitizationSystemId) {
		return intfDAO.desensitizationSystemCode(desensitizationSystemId);
	}

	@Override
	public void savaDesensitizationLog(Map<String, Object> map) {
		intfDAO.savaDesensitizationLog(map);
	}
	
	@Override
	public Map<String, Object> queryNewPartyPhotoByOlId(String olId) {
		return intfDAO.queryNewPartyPhotoByOlId(olId);
	}
	
	public List<Object> queryOfferSpecInfoList(Map<String, Object> param) {		
		return intfDAO.queryOfferSpecInfoList(param);
	}
	
	public List<Map<String,Object>> queryChannelInfoByIdentityNum(String identityNum) {		
		return intfDAO.queryChannelInfoByIdentityNum(identityNum);
	}
	
	@Override
	public Map<String, Object> getStartLevelByPartyAccNbr(String accNbr) {		
		return intfDAO.getStartLevelByPartyAccNbr(accNbr);
	}
}
