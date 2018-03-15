package com.linkage.bss.crm.intf.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;

import bss.common.util.LoginedStaffInfo;

import com.linkage.bss.commons.util.BeanUtil;
import com.linkage.bss.crm.dto.OfferDto;
import com.linkage.bss.crm.intf.smo.IntfSMO;
import com.linkage.bss.crm.offer.dto.OfferProdCompSspecDto;
import com.linkage.bss.crm.offer.dto.OfferRolesDto;
import com.linkage.bss.crm.offer.smo.IOfferSMO;
import com.linkage.bss.crm.offerspec.dto.AttachOfferSpecDto;
import com.linkage.bss.crm.offerspec.dto.OfferSpecDto;
import com.linkage.bss.crm.offerspec.smo.IOfferSpecSMO;

public class OfferSpecFacadeImpl implements OfferSpecFacade {

	private IOfferSpecSMO offerSpecSMO;
	private IOfferSMO offerSMO;
	private IntfSMO intfSMO;

	public void setIntfSMO(IntfSMO intfSMO) {
		this.intfSMO = intfSMO;
	}

	public void setOfferSpecSMO(IOfferSpecSMO offerSpecSMO) {
		this.offerSpecSMO = offerSpecSMO;
	}

	public void setOfferSMO(IOfferSMO offerSMO) {
		this.offerSMO = offerSMO;
	}

	@Override
	public List<AttachOfferSpecDto> queryAttachOfferSpecBySpec(Long prodSpecId, Long prodId, String areaId,
			String staffId, String channelId, Long partyId, Long offerSpecId) {
		JSONArray jsonArray = new JSONArray();
		if (offerSpecId == null) {
			jsonArray.element("7");// 服务信息变更场景
		} else {
			jsonArray.element("1");// 新装场景
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaId", areaId);
		param.put("maskByDt", "Y");
		param.put("queryByCondDim", "Y");
		param.put("staffId", Long.valueOf(staffId));
		param.put("channelId", channelId);
		param.put("partyId", partyId);
		param.put("prodSpecId", prodSpecId);
		// 目前没有区分暂不加入
		param.put("filterByArea", "N");
		param.put("filterByBoAction", "Y");
		param.put("boActionTypes", jsonArray);
		Map<String, Object> offerParam = new HashMap<String, Object>();
		offerParam.put("prodId", prodId);
		List<Long> offerSpecIds = new ArrayList<Long>();
		List<Long> offerRoleIds = new ArrayList<Long>();
		if (offerSpecId != null) {
			long start = System.currentTimeMillis();
			List<OfferRolesDto> list = offerSMO.queryOfferRoles(Long.valueOf(offerSpecId));
			System.out.println("queryAttachOfferSpecBySpec.offerSMO.queryOfferRoles(查销售品成员角色信息) 执行时间："
					+ (System.currentTimeMillis() - start));
			for (OfferRolesDto offerRolesDto : list) {
				offerSpecIds.add(offerRolesDto.getOfferSpecId());
				offerRoleIds.add(offerRolesDto.getOfferRoleId());
			}
		} else {
			long start = System.currentTimeMillis();
			List<OfferDto> list = offerSMO.queryOfferFromInstAndOrder(offerParam);
			System.out
					.println("queryAttachOfferSpecBySpec.offerSMO.queryOfferFromInstAndOrder(查询产品被加入的主销售品规格，从实例AND 过程取) 执行时间："
							+ (System.currentTimeMillis() - start));
			for (OfferDto offer : list) {
				if (offer.getOfferSpecId() != null) {
					offerSpecIds.add(offer.getOfferSpecId());
					offerRoleIds.add(offer.getOfferRoleId());
				}
			}
		}
		if (offerSpecIds.size() > 0) {
			param.put("offerSpecIds", offerSpecIds);
		}
		if (offerRoleIds.size() > 0) {
			param.put("offerRoleIds", offerRoleIds);
		}
		long start = System.currentTimeMillis();
		List<OfferProdCompSspecDto> sservGrpList = offerSMO.queryCompProdFromInstAndOrder(offerParam);
		System.out.println("queryAttachOfferSpecBySpec.offerSMO.queryCompProdFromInstAndOrder(查询产品被加入的组合产品) 执行时间："
				+ (System.currentTimeMillis() - start));
		String ivpnFlag = "N", strCompProdId = "", strCompProdSpecId = "";
		Map<String, Object> compProdParam = new HashMap<String, Object>();
		Map<String, Object> pTriffLabelMap = new HashMap<String, Object>();
		start = System.currentTimeMillis();
		List<Long> pTriffLabelList = offerSpecSMO.queryCompProdTriffLabel(pTriffLabelMap);// 获取需要过滤的集团个性化促销标签List
		System.out
				.println("queryAttachOfferSpecBySpec.offerSpecSMO.queryCompProdTriffLabel(获取需要过滤的主产品对应的个性化促销列表) 执行时间："
						+ (System.currentTimeMillis() - start));
		List<Integer> sspecCompGrpIds = new ArrayList<Integer>();
		List<Long> defaultTariffLabelList = new ArrayList<Long>();
		List<Long> compPTriffLableList = new ArrayList<Long>();
		for (OfferProdCompSspecDto sservGrp : sservGrpList) {
			sspecCompGrpIds.add(sservGrp.getSspecCompGrpId());
			// 判断是否是IVPN的成员产品
			// Map<String, Object> compProdListParam = new HashMap<String,
			// Object>();
			start = System.currentTimeMillis();
			List<Long> controlCompProdList = offerSpecSMO.queryCompProdList(param);
			System.out
					.println("queryAttachOfferSpecBySpec.offerSpecSMO.queryCompProdList(查询需要进行附属产品过滤的组合产品ProdId) 执行时间："
							+ (System.currentTimeMillis() - start));
			for (Long temp : controlCompProdList) {
				if (String.valueOf(sservGrp.getCompProdSpecId()).equals(String.valueOf(temp))) {
					ivpnFlag = "Y";
					strCompProdId = String.valueOf(sservGrp.getCompProdId());
					// List<Long> compProdProdId = new ArrayList<Long>();
					compProdParam.put("prodId", strCompProdId);
					start = System.currentTimeMillis();
					List<OfferDto> comProdList = offerSMO.queryOfferFromInstAndOrder(compProdParam);
					System.out
							.println("queryAttachOfferSpecBySpec.offerSMO.queryOfferFromInstAndOrder(查询需要进行附属产品过滤的组合产品ProdId="
									+ strCompProdId + ") 执行时间：" + (System.currentTimeMillis() - start));
					for (OfferDto offer : comProdList) {
						if (offer.getOfferSpecId() != null) {
							// param.put("DefaultTariffLabel", );//获取组合产品对应的销售品
							defaultTariffLabelList.add(offer.getOfferSpecId());
							Map<String, Object> compOfferMap = new HashMap<String, Object>();
							compOfferMap.put("compOfferSpecId", offer.getOfferSpecId());
							List<Long> compOfferPTriffList = offerSpecSMO.queryCompProdTriffLabel(compOfferMap);
							for (Long tmpLong : compOfferPTriffList) {
								// param.put("PTariffLabel",
								// tmpLong);//获取组合产品销售品对应的个性化标签
								compPTriffLableList.add(tmpLong);
							}
						}
					}
					strCompProdSpecId = String.valueOf(sservGrp.getCompProdSpecId());
				}
			}
		}
		param.put("DefaultTariffLabelList", defaultTariffLabelList);// 组合商品对应的主销售品串
		param.put("CompPTriffLableList", compPTriffLableList);// 组合商品对应的小主销售品对应的个性化促销串
		param.put("StriCompProdId", strCompProdId);
		param.put("StrCompProdSpecId", strCompProdSpecId);
		param.put("PTariffLabelList", pTriffLabelList);
		param.put("IvpnFlag", ivpnFlag);
		if (sspecCompGrpIds.size() > 0) {
			param.put("sspecCompGrpIds", sspecCompGrpIds);
		}
		LoginedStaffInfo loginedStaffInfo = new LoginedStaffInfo();
		loginedStaffInfo.setChannelId(Integer.valueOf(channelId));
		loginedStaffInfo.setStaffId(Long.valueOf(staffId));
		param.put("staffInfo", loginedStaffInfo);

		start = System.currentTimeMillis();
		// 营业查询结果A和电子渠道查询结果B,有则进行A与B的交集计算-->A∩B
		List<OfferSpecDto> limitList = intfSMO.queryOfferSpecsByDZQD();
		System.out.println("queryAttachOfferSpecBySpec.intfSMO.queryOfferSpecsByDZQD(查询电子渠道需要的销售品) 执行时间："
				+ (System.currentTimeMillis() - start));
		// LinkedList的增删速度比ArrayList快
		List<AttachOfferSpecDto> blackLabels = new LinkedList<AttachOfferSpecDto>();
		start = System.currentTimeMillis();
		List<AttachOfferSpecDto> attachOfferSpecs = offerSpecSMO.queryAttachOfferSpecBySpec(param);
		System.out
				.println("queryAttachOfferSpecBySpec.offerSpecSMO.queryAttachOfferSpecBySpec(根据产品规格搜索对应的附属销售品，以及销售品标签) 执行时间："
						+ (System.currentTimeMillis() - start));
		for (AttachOfferSpecDto attachOfferSpecDto : attachOfferSpecs) {
			List<OfferSpecDto> srcList = attachOfferSpecDto.getOfferSpecList();
			List<OfferSpecDto> newList = new LinkedList<OfferSpecDto>();
			// srcList:A ,limitList:B,做 A∩B
			Set<OfferSpecDto> cloneSet = new HashSet<OfferSpecDto>((List<OfferSpecDto>) BeanUtil.cloneBean(srcList));
			Set<OfferSpecDto> limitSet = new HashSet<OfferSpecDto>((List<OfferSpecDto>) BeanUtil.cloneBean(limitList));
			cloneSet.retainAll(limitSet);
			// 将set集合转为list
			newList.addAll(cloneSet);
			if (CollectionUtils.isEmpty(newList)) {
				blackLabels.add(attachOfferSpecDto);
			}
			attachOfferSpecDto.setOfferSpecList(newList);
		}
		// 去除空的label标签
		attachOfferSpecs.removeAll(blackLabels);
		return attachOfferSpecs;
	}

}
