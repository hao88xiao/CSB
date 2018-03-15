package com.linkage.bss.crm.intf.facade;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.linkage.bss.BaseTest;
import com.linkage.bss.crm.model.Account;
import com.linkage.bss.crm.model.AccountItemCatg;
import com.linkage.bss.crm.model.AccountItemCatgType;
import com.linkage.bss.crm.model.AccountMailing;
import com.linkage.bss.crm.model.AccountStatus;
import com.linkage.bss.crm.model.Acct2PaymentAcct;
import com.linkage.bss.crm.model.CustVipInfo;
import com.linkage.bss.crm.model.OfferProd;
import com.linkage.bss.crm.model.OfferProd2AccessNumber;
import com.linkage.bss.crm.model.OfferProd2Addr;
import com.linkage.bss.crm.model.OfferProd2Prod;
import com.linkage.bss.crm.model.OfferProd2Td;
import com.linkage.bss.crm.model.OfferProdAccount;
import com.linkage.bss.crm.model.OfferProdFeeType;
import com.linkage.bss.crm.model.OfferProdItem;
import com.linkage.bss.crm.model.OfferProdNumber;
import com.linkage.bss.crm.model.OfferProdSpec;
import com.linkage.bss.crm.model.OfferProdStatus;
import com.linkage.bss.crm.model.OfferServ;
import com.linkage.bss.crm.model.Party;
import com.linkage.bss.crm.model.PartyIdentity;
import com.linkage.bss.crm.model.PartyProfile;
import com.linkage.bss.crm.model.PartySegmentMemberList;
import com.linkage.bss.crm.model.PaymentAccount;
import com.linkage.bss.crm.model.Segment;
import com.linkage.bss.crm.model.SegmentGroup2Segment;
import com.linkage.bss.crm.model.Segment_group;
import com.linkage.bss.crm.offer.dto.AttachOfferDto;
import com.linkage.bss.crm.offer.dto.CommonOfferProdDto;

public class OfferFacadeTest extends BaseTest {

//	@Autowired
//	@Qualifier("intfManager.offerFacade")
//	private OfferFacade offerFacade;
//
//	@Test
//	public void testQueryAllProdByPartyId() {
//		List<CommonOfferProdDto> prodList = offerFacade.queryAllProdByPartyId(513005063694L,null);
//		for (CommonOfferProdDto prod : prodList) {
//			System.out.println(prod.getProdId());
//		}
//	}
//
//	@Test
//	public void testGetAllProdInfoByProdId() {
//
//	}
//
//	@Test
//	public void testGetProdDetailByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 4510519603L;
//		OfferProd offerProd = offerFacade.getProdDetailByProdId(prodId);
//		Assert.notNull(offerProd);
//		System.out.println("offerProd.prodId=" + offerProd.getProdId());
//		System.out.println("offerProd.atomActionId=" + offerProd.getAcctId());
//		System.out.println("offerProd.areaId=" + offerProd.getAreaId());
//		System.out.println("offerProd.beginDt=" + offerProd.getBeginDt().toString());
//		System.out.println("offerProd.startDt=" + offerProd.getStartDt().toString());
//		System.out.println("offerProd.endDt=" + offerProd.getEndDt().toString());
//		System.out.println("offerProd.statusCd=" + offerProd.getStatusCd());
//		System.out.println("offerProd.statusDt=" + offerProd.getStatusDt().toString());
//		System.out.println("offerProd.createDt=" + offerProd.getCreateDt().toString());
//		System.out.println("offerProd.version=" + offerProd.getVersion());
//
//		if (offerProd.getArea() != null) {
//			System.out.println("offerProd.area.id=" + offerProd.getArea().getAreaId());
//			System.out.println("offerProd.area.name=" + offerProd.getArea().getName());
//		} else {
//			System.out.println("offerProd.area=¿Õ");
//		}
//
//		if (offerProd.getInstStatus() != null) {
//			System.out.println("offerProd.instStatus.statusCd=" + offerProd.getInstStatus().getStatusCd());
//			System.out.println("offerProd.instStatus.name=" + offerProd.getInstStatus().getName());
//		} else {
//			System.out.println("offerProd.instStatus=¿Õ");
//		}
//
//		System.out.println("offerProd.accessNumber=" + offerProd.getAccessNumber());
//		System.out.println("offerProd.acctId=" + offerProd.getAcctId());
//		System.out.println("offerProd.prodSpecId=" + offerProd.getProdSpecId());
//		System.out.println("offerProd.partyId=" + offerProd.getPartyId());
//		System.out.println("offerProd.zoneNumber=" + offerProd.getZoneNumber());
//		System.out.println("offerProd.extProdInstId=" + offerProd.getExtProdInstId());
//		System.out.println("offerProd.extSystem=" + offerProd.getExtSystem());
//
//		System.out.println("offerProd.offerProd2Parties = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProd2Parties()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdSpecs = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdSpecs()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdStatuses = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdStatuses()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdFeeTypes = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdFeeTypes()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProd2Prods = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProd2Prods()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProd2Addrs = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProd2Addrs()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerPord2Tml = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferPord2Tml()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdItems = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdItems()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdNumbers = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdNumbers()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProd2AccessNumbers = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProd2AccessNumbers()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProd2Tds = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProd2Tds()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerServs = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferServs()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdAccounts = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdAccounts()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdComps = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdComps()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerCoupons = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferCoupons()) ? "¿Õ" : "·Ç¿Õ"));
//		System.out.println("offerProd.offerProdAgents = "
//				+ (CollectionUtils.isEmpty(offerProd.getOfferProdAgents()) ? "¿Õ" : "·Ç¿Õ"));
//
//		System.out.println("getProdDetailByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetOfferProdNumberByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 4510519603L;
//		OfferProdNumber offerProdNumber = offerFacade.getOfferProdNumberByProdId(prodId);
//		Assert.notNull(offerProdNumber);
//		dumpOfferProdNumber(offerProdNumber);
//
//		System.out.println("getOfferProdNumberByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2AccessNumberByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033704L;
//		OfferProd2AccessNumber offerProd2AccessNumber = offerFacade.getProd2AccessNumberByProdId(prodId);
//		Assert.notNull(offerProd2AccessNumber);
//		System.out.println("offerProd2AccessNumber.atomActionId=" + offerProd2AccessNumber.getAtomActionId());
//		System.out.println("offerProd2AccessNumber.prodId=" + offerProd2AccessNumber.getProdId());
//		System.out.println("offerProd2AccessNumber.reasonCd=" + offerProd2AccessNumber.getReasonCd());
//		System.out.println("offerProd2AccessNumber.anId=" + offerProd2AccessNumber.getAnId());
//		System.out.println("offerProd2AccessNumber.anTypeCd=" + offerProd2AccessNumber.getAnTypeCd());
//		System.out.println("offerProd2AccessNumber.accessNumber=" + offerProd2AccessNumber.getAccessNumber());
//		System.out.println("offerProd2AccessNumber.startDt=" + offerProd2AccessNumber.getStartDt());
//		System.out.println("offerProd2AccessNumber.endDt=" + offerProd2AccessNumber.getEndDt());
//		System.out.println("offerProd2AccessNumber.statusCd=" + offerProd2AccessNumber.getStatusCd());
//		System.out.println("offerProd2AccessNumber.statusDt=" + offerProd2AccessNumber.getStatusDt());
//		System.out.println("offerProd2AccessNumber.createDt=" + offerProd2AccessNumber.getCreateDt());
//		System.out.println("offerProd2AccessNumber.version=" + offerProd2AccessNumber.getVersion());
//		System.out.println("offerProd2AccessNumber.reasonName=" + offerProd2AccessNumber.getReasonName());
//
//		if (offerProd2AccessNumber.getAccessNumberType() != null) {
//			System.out.println("offerProd2AccessNumber.accessNumberType.anTypeCd="
//					+ offerProd2AccessNumber.getAccessNumberType().getAnTypeCd());
//			System.out.println("offerProd2AccessNumber.accessNumberType.name"
//					+ offerProd2AccessNumber.getAccessNumberType().getName());
//		} else {
//			System.out.println("offerProd2AccessNumber.accessNumberType=¿Õ");
//		}
//
//		if (offerProd2AccessNumber.getInstStatus() != null) {
//			System.out.println("offerProd2AccessNumber.instStatus.statusCd="
//					+ offerProd2AccessNumber.getInstStatus().getStatusCd());
//			System.out.println("offerProd2AccessNumber.instStatus.name="
//					+ offerProd2AccessNumber.getInstStatus().getName());
//		} else {
//			System.out.println("offerProd2AccessNumber.instStatus=¿Õ");
//		}
//
//		if (offerProd2AccessNumber.getProd2AnReason() != null) {
//			System.out.println("offerProd2AccessNumber.prod2AnReason.statusCd="
//					+ offerProd2AccessNumber.getProd2AnReason().getReasonCd());
//			System.out.println("offerProd2AccessNumber.prod2AnReason.name="
//					+ offerProd2AccessNumber.getProd2AnReason().getName());
//		} else {
//			System.out.println("offerProd2AccessNumber.prod2AnReason=¿Õ");
//		}
//
//		System.out.println("getProd2AccessNumberByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2ProdByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long relatesProdId = 513030047461L;
//		// Long relatedProdId = 513030047462L;
//		List<OfferProd2Prod> offerProd2Prods = offerFacade.getProd2ProdByProdId(relatesProdId);
//		// List<OfferProd2Prod> offerProd2Prods =
//		// offerFacade.getProd2ProdByProdId(relatedProdId);
//		Assert.notEmpty(offerProd2Prods);
//
//		for (OfferProd2Prod offerProd2Prod : offerProd2Prods) {
//			System.out.println("offerProd2Prod.atomActionId=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.relatesProdId=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.reasonCd=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.relatedProdId=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.startDt=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.endDt=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.statusCd=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.statusDt=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.createDt=" + offerProd2Prod.getAtomActionId());
//			System.out.println("offerProd2Prod.version=" + offerProd2Prod.getAtomActionId());
//
//			if (offerProd2Prod.getInstStatus() != null) {
//				System.out
//						.println("offerProd2Prod.instStatus.statusCd=" + offerProd2Prod.getInstStatus().getStatusCd());
//				System.out.println("offerProd2Prod.instStatus.name=" + offerProd2Prod.getInstStatus().getName());
//			} else {
//				System.out.println("offerProd2Prod.instStatus=¿Õ");
//			}
//
//			if (offerProd2Prod.getProdRelaReason() != null) {
//				System.out.println("offerProd2Prod.prodRelaReason.statusCd="
//						+ offerProd2Prod.getProdRelaReason().getReasonCd());
//				System.out
//						.println("offerProd2Prod.prodRelaReason.name=" + offerProd2Prod.getProdRelaReason().getName());
//			} else {
//				System.out.println("offerProd2Prod.prodRelaReason=¿Õ");
//			}
//
//			if (offerProd2Prod.getProdRelaReason() != null) {
//				System.out.println("offerProd2Prod.prodRelaReason.statusCd="
//						+ offerProd2Prod.getProdRelaReason().getReasonCd());
//				System.out
//						.println("offerProd2Prod.prodRelaReason.name=" + offerProd2Prod.getProdRelaReason().getName());
//			} else {
//				System.out.println("offerProd2Prod.prodRelaReason=¿Õ");
//			}
//
//			System.out.println("ÒÔÏÂÊÇÖ÷½ÓÈëºÅÂëµÄÐÅÏ¢");
//			if (offerProd2Prod.getOfferProdNumber() != null) {
//				dumpOfferProdNumber(offerProd2Prod.getOfferProdNumber());
//			} else {
//				System.out.println("offerProd2Prod.offerProdNumber=¿Õ");
//			}
//		}
//		System.out.println("getProd2ProdByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2TDByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		List<OfferProd2Td> offerProd2Tds = offerFacade.getProd2TDByProdId(prodId);
//		Assert.notEmpty(offerProd2Tds);
//		for (OfferProd2Td offerProd2Td : offerProd2Tds) {
//			System.out.println("offerProd2Td.atomActionId=" + offerProd2Td.getAtomActionId());
//			System.out.println("offerProd2Td.prod2TdId=" + offerProd2Td.getProd2TdId());
//			System.out.println("offerProd2Td.prodId=" + offerProd2Td.getProdId());
//			System.out.println("offerProd2Td.terminalDevSpecId=" + offerProd2Td.getTerminalDevSpecId());
//			System.out.println("offerProd2Td.terminalDevId=" + offerProd2Td.getTerminalDevId());
//			System.out.println("offerProd2Td.terminalCode=" + offerProd2Td.getTerminalCode());
//			System.out.println("offerProd2Td.deviceModelId=" + offerProd2Td.getDeviceModelId());
//			System.out.println("offerProd2Td.deviceModelName=" + offerProd2Td.getDeviceModelName());
//			System.out.println("offerProd2Td.ownerTypeCd=" + offerProd2Td.getOwnerTypeCd());
//			System.out.println("offerProd2Td.maintainTypeCd=" + offerProd2Td.getMaintainTypeCd());
//			System.out.println("offerProd2Td.startDt=" + offerProd2Td.getStartDt());
//			System.out.println("offerProd2Td.endDt=" + offerProd2Td.getEndDt());
//			System.out.println("offerProd2Td.statusCd=" + offerProd2Td.getStatusCd());
//			System.out.println("offerProd2Td.statusDt=" + offerProd2Td.getStartDt());
//
//			System.out.println("offerProd2Td.createDt=" + offerProd2Td.getCreateDt());
//			System.out.println("offerProd2Td.version=" + offerProd2Td.getVersion());
//
//			if (offerProd2Td.getInstStatus() != null) {
//				System.out.println("offerProd2Td.instStatus.statusCd=" + offerProd2Td.getInstStatus().getStatusCd());
//				System.out.println("offerProd2Td.instStatus.name=" + offerProd2Td.getInstStatus().getName());
//			} else {
//				System.out.println("offerProd2Td.instStatus=¿Õ");
//			}
//
//			if (offerProd2Td.getOwnerType() != null) {
//				System.out
//						.println("offerProd2Td.ownerType.ownerTypeCd=" + offerProd2Td.getOwnerType().getOwnerTypeCd());
//				System.out.println("offerProd2Td.ownerType.name=" + offerProd2Td.getOwnerType().getName());
//			} else {
//				System.out.println("offerProd2Td.ownerType=¿Õ");
//			}
//
//			if (offerProd2Td.getTerminalDevSpec() != null) {
//				System.out.println("offerProd2Td.terminalDevSpec.terminalDevSpecId="
//						+ offerProd2Td.getTerminalDevSpec().getTerminalDevSpecId());
//				if (offerProd2Td.getTerminalDevSpec().getResourceSpec() != null) {
//					System.out.println("offerProd2Td.terminalDevSpec.resourceSpec.rscSpecIdId="
//							+ offerProd2Td.getTerminalDevSpec().getResourceSpec().getRscSpecId());
//					System.out.println("offerProd2Td.terminalDevSpec.resourceSpec.name="
//							+ offerProd2Td.getTerminalDevSpec().getResourceSpec().getName());
//				} else {
//					System.out.println("offerProd2Td.terminalDevSpec.resourceSpec=¿Õ");
//				}
//			} else {
//				System.out.println("offerProd2Td.terminalDevSpec=¿Õ");
//			}
//		}
//
//		System.out.println("getProd2TDByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdFeeTypeByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		List<OfferProdFeeType> offerProdFeeTypes = offerFacade.getProdFeeTypeByProdId(prodId);
//		Assert.notEmpty(offerProdFeeTypes);
//		for (OfferProdFeeType offerProdFeeType : offerProdFeeTypes) {
//			System.out.println("offerProdFeeType.atomActionId=" + offerProdFeeType.getAtomActionId());
//			System.out.println("offerProdFeeType.prodId=" + offerProdFeeType.getProdId());
//			System.out.println("offerProdFeeType.feeType=" + offerProdFeeType.getFeeType());
//			System.out.println("offerProdFeeType.startDt=" + offerProdFeeType.getStartDt());
//			System.out.println("offerProdFeeType.endDt=" + offerProdFeeType.getEndDt());
//			System.out.println("offerProdFeeType.statusCd=" + offerProdFeeType.getStatusCd());
//			System.out.println("offerProdFeeType.statusDt=" + offerProdFeeType.getStatusDt());
//			System.out.println("offerProdFeeType.createDt=" + offerProdFeeType.getCreateDt());
//			System.out.println("offerProdFeeType.version=" + offerProdFeeType.getVersion());
//
//			if (offerProdFeeType.getInstStatus() != null) {
//				System.out.println("offerProdFeeType.instStatus.statusCd="
//						+ offerProdFeeType.getInstStatus().getStatusCd());
//				System.out.println("offerProdFeeType.instStatus.name=" + offerProdFeeType.getInstStatus().getName());
//			} else {
//				System.out.println("offerProdFeeType.instStatus=¿Õ");
//			}
//
//			if (offerProdFeeType.getFeeTypes() != null) {
//				System.out.println("offerProdFeeType.feeTypes.feeType=" + offerProdFeeType.getFeeTypes().getFeeType());
//				System.out.println("offerProdFeeType.feeTypes.name=" + offerProdFeeType.getFeeTypes().getName());
//			} else {
//				System.out.println("offerProdFeeType.feeTypes=¿Õ");
//			}
//		}
//
//		System.out.println("getProdFeeTypeByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdItemByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		List<OfferProdItem> offerProdItems = offerFacade.getProdItemByProdId(prodId);
//		Assert.notEmpty(offerProdItems);
//		for (OfferProdItem offerProdItem : offerProdItems) {
//			System.out.println("---------------------------" + offerProdItem.getItemSpec().getName()
//					+ "---------------------------");
//
//			System.out.println("offerProdItem.atomActionId=" + offerProdItem.getAtomActionId());
//			System.out.println("offerProdItem.prodId=" + offerProdItem.getProdId());
//			System.out.println("offerProdItem.itemSpecId=" + offerProdItem.getItemSpecId());
//			System.out.println("offerProdItem.value=" + offerProdItem.getValue());
//			System.out.println("offerProdItem.startDt=" + offerProdItem.getStartDt());
//			System.out.println("offerProdItem.endDt=" + offerProdItem.getEndDt());
//			System.out.println("offerProdItem.statusCd=" + offerProdItem.getStatusCd());
//			System.out.println("offerProdItem.statusDt=" + offerProdItem.getStartDt());
//			System.out.println("offerProdItem.createDt=" + offerProdItem.getCreateDt());
//			System.out.println("offerProdItem.version=" + offerProdItem.getVersion());
//
//			if (offerProdItem.getItemSpec() != null) {
//				System.out.println("offerProdItem.itemSpec.itemSpecId=" + offerProdItem.getItemSpec().getItemSpecId());
//				System.out.println("offerProdItem.itemSpec.name=" + offerProdItem.getItemSpec().getName());
//				System.out
//						.println("offerProdItem.itemSpec.description=" + offerProdItem.getItemSpec().getDescription());
//				System.out.println("offerProdItem.itemSpec.itemSpecTypeCd="
//						+ offerProdItem.getItemSpec().getItemSpecTypeCd());
//			} else {
//				System.out.println("offerProdItem.itemSpec=¿Õ");
//			}
//
//			if (offerProdItem.getInstStatus() != null) {
//				System.out.println("offerProdItem.instStatus.statusCd=" + offerProdItem.getInstStatus().getStatusCd());
//				System.out.println("offerProdItem.instStatus.name=" + offerProdItem.getInstStatus().getName());
//			} else {
//				System.out.println("offerProdFeeType.instStatus=¿Õ");
//			}
//		}
//
//		System.out.println("getProdFeeTypeByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdSpecByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		OfferProdSpec offerProdSpec = offerFacade.getProdSpecByProdId(prodId);
//		Assert.notNull(offerProdSpec);
//		System.out.println("offerProdSpec.atomActionId=" + offerProdSpec.getAtomActionId());
//		System.out.println("offerProdSpec.prodId=" + offerProdSpec.getProdId());
//		System.out.println("offerProdSpec.prodSpecId=" + offerProdSpec.getProdSpecId());
//		System.out.println("offerProdSpec.startDt=" + offerProdSpec.getStartDt());
//		System.out.println("offerProdSpec.endDt=" + offerProdSpec.getEndDt());
//		System.out.println("offerProdSpec.statusCd=" + offerProdSpec.getStatusCd());
//		System.out.println("offerProdSpec.statusDt=" + offerProdSpec.getStatusDt());
//		System.out.println("offerProdSpec.createDt=" + offerProdSpec.getCreateDt());
//		System.out.println("offerProdSpec.version=" + offerProdSpec.getVersion());
//
//		if (offerProdSpec.getInstStatus() != null) {
//			System.out.println("offerProdSpec.instStatus.statusCd=" + offerProdSpec.getInstStatus().getStatusCd());
//			System.out.println("offerProdSpec.instStatus.name=" + offerProdSpec.getInstStatus().getName());
//		} else {
//			System.out.println("offerProdSpec.instStatus=¿Õ");
//		}
//
//		if (offerProdSpec.getProdSpec() != null) {
//			System.out.println("offerProdSpec.prodSpec.prodSpecId=" + offerProdSpec.getProdSpec().getProdSpecId());
//			System.out.println("offerProdSpec.prodSpec.name=" + offerProdSpec.getProdSpec().getName());
//		} else {
//			System.out.println("offerProdSpec.prodSpec=¿Õ");
//		}
//
//		System.out.println("getProdSpecByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdServByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		List<OfferServ> offerProdSpec = offerFacade.getProdServByProdId(prodId);
//		Assert.notEmpty(offerProdSpec);
//		for (OfferServ offerServ : offerProdSpec) {
//			System.out.println("-----------------------" + offerServ.getServSpec().getName()
//					+ "-----------------------");
//
//			System.out.println("offerServ.servId=" + offerServ.getServId());
//			System.out.println("offerServ.atomActionId=" + offerServ.getAtomActionId());
//			System.out.println("offerServ.servSpecId=" + offerServ.getServSpecId());
//			System.out.println("offerServ.prodId=" + offerServ.getProd());
//			System.out.println("offerServ.compProdId=" + offerServ.getCompProdId());
//			System.out.println("offerServ.beiginDt=" + offerServ.getBeginDt());
//			System.out.println("offerServ.startDt=" + offerServ.getStartDt());
//			System.out.println("offerServ.endDt=" + offerServ.getEndDt());
//			System.out.println("offerServ.statusCd=" + offerServ.getStatusCd());
//			System.out.println("offerServ.createDt=" + offerServ.getCreateDt());
//			System.out.println("offerServ.version=" + offerServ.getVersion());
//			System.out.println("offerServ.extServInstId=" + offerServ.getExtServInstId());
//			System.out.println("offerServ.extSystem=" + offerServ.getExtSystem());
//
//			if (offerServ.getServSpec() != null) {
//				System.out.println("offerServ.servSpec.servSpecId=" + offerServ.getServSpec().getServSpecId());
//				System.out.println("offerServ.servSpec.name=" + offerServ.getServSpec().getName());
//			} else {
//				System.out.println("offerServ.servSpec=¿Õ");
//			}
//
//			if (offerServ.getInstStatus() != null) {
//				System.out.println("offerServ.instStatus.statusCd=" + offerServ.getInstStatus().getStatusCd());
//				System.out.println("offerServ.instStatus.name=" + offerServ.getInstStatus().getName());
//			} else {
//				System.out.println("offerServ.instStatus=¿Õ");
//			}
//
//			Assert.isTrue(CollectionUtils.isEmpty(offerServ.getOfferServItems()));
//		}
//
//		System.out.println("getProdServByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdServWithItemByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		List<OfferServ> offerProdSpec = offerFacade.getProdServWithItemByProdId(prodId);
//		Assert.notEmpty(offerProdSpec);
//		for (OfferServ offerServ : offerProdSpec) {
//			System.out.println("-----------------------" + offerServ.getServSpec().getName()
//					+ "-----------------------");
//
//			System.out.println("offerServ.servId=" + offerServ.getServId());
//			System.out.println("offerServ.atomActionId=" + offerServ.getAtomActionId());
//			System.out.println("offerServ.servSpecId=" + offerServ.getServSpecId());
//			System.out.println("offerServ.prodId=" + offerServ.getProd());
//			System.out.println("offerServ.compProdId=" + offerServ.getCompProdId());
//			System.out.println("offerServ.beiginDt=" + offerServ.getBeginDt());
//			System.out.println("offerServ.startDt=" + offerServ.getStartDt());
//			System.out.println("offerServ.endDt=" + offerServ.getEndDt());
//			System.out.println("offerServ.statusCd=" + offerServ.getStatusCd());
//			System.out.println("offerServ.createDt=" + offerServ.getCreateDt());
//			System.out.println("offerServ.version=" + offerServ.getVersion());
//			System.out.println("offerServ.extServInstId=" + offerServ.getExtServInstId());
//			System.out.println("offerServ.extSystem=" + offerServ.getExtSystem());
//
//			if (offerServ.getServSpec() != null) {
//				System.out.println("offerServ.servSpec.servSpecId=" + offerServ.getServSpec().getServSpecId());
//				System.out.println("offerServ.servSpec.name=" + offerServ.getServSpec().getName());
//			} else {
//				System.out.println("offerServ.servSpec=¿Õ");
//			}
//
//			if (offerServ.getInstStatus() != null) {
//				System.out.println("offerServ.instStatus.statusCd=" + offerServ.getInstStatus().getStatusCd());
//				System.out.println("offerServ.instStatus.name=" + offerServ.getInstStatus().getName());
//			} else {
//				System.out.println("offerServ.instStatus=¿Õ");
//			}
//
//			Assert.isTrue(CollectionUtils.isEmpty(offerServ.getOfferServItems()));
//		}
//
//		System.out.println("getProdServWithItemByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProdStatusByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033498L;
//		OfferProdStatus offerProdStatus = offerFacade.getProdStatusByProdId(prodId);
//		Assert.notNull(offerProdStatus);
//		System.out.println("offerProdStatus.atomActionId=" + offerProdStatus.getAtomActionId());
//		System.out.println("offerProdStatus.prodId=" + offerProdStatus.getProdId());
//		System.out.println("offerProdStatus.prodStatusCd=" + offerProdStatus.getProdStatusCd());
//		System.out.println("offerProdStatus.startDt=" + offerProdStatus.getStartDt());
//		System.out.println("offerProdStatus.endDt=" + offerProdStatus.getEndDt());
//		System.out.println("offerProdStatus.statusCd=" + offerProdStatus.getStatusCd());
//		System.out.println("offerProdStatus.statusDt=" + offerProdStatus.getStartDt());
//		System.out.println("offerProdStatus.createDt=" + offerProdStatus.getCreateDt());
//		System.out.println("offerProdStatus.version=" + offerProdStatus.getVersion());
//
//		if (offerProdStatus.getInstStatus() != null) {
//			System.out.println("offerProdStatus.instStatus.statusCd=" + offerProdStatus.getInstStatus().getStatusCd());
//			System.out.println("offerProdStatus.instStatus.name=" + offerProdStatus.getInstStatus().getName());
//		} else {
//			System.out.println("offerProdStatus.instStatus=¿Õ");
//		}
//
//		if (offerProdStatus.getProdStatusType() != null) {
//			System.out.println("offerProdStatus.prodStatusType.prodStatusCd="
//					+ offerProdStatus.getProdStatusType().getProdStatusCd());
//			System.out.println("offerProdStatus.prodStatusType.name=" + offerProdStatus.getProdStatusType().getName());
//		} else {
//			System.out.println("offerProdStatus.prodStatusType=¿Õ");
//		}
//
//		System.out.println("getProdStatusByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2AddrByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030048941L;
//		OfferProd2Addr offerProd2Addr = offerFacade.getProd2AddrByProdId(prodId);
//		Assert.notNull(offerProd2Addr);
//		System.out.println("offerProd2Addr.atomActionId=" + offerProd2Addr.getAtomActionId());
//		System.out.println("offerProd2Addr.prodId=" + offerProd2Addr.getProdId());
//		System.out.println("offerProd2Addr.reasonCd=" + offerProd2Addr.getReasonCd());
//		System.out.println("offerProd2Addr.areaId=" + offerProd2Addr.getAreaId());
//		System.out.println("offerProd2Addr.addressId=" + offerProd2Addr.getAddressId());
//		System.out.println("offerProd2Addr.addressDesc=" + offerProd2Addr.getAddressDesc());
//		System.out.println("offerProd2Addr.startDt=" + offerProd2Addr.getStartDt());
//		System.out.println("offerProd2Addr.endDt=" + offerProd2Addr.getEndDt());
//		System.out.println("offerProd2Addr.statusCd=" + offerProd2Addr.getStatusCd());
//		System.out.println("offerProd2Addr.statusDt=" + offerProd2Addr.getStartDt());
//		System.out.println("offerProd2Addr.createDt=" + offerProd2Addr.getCreateDt());
//		System.out.println("offerProd2Addr.version=" + offerProd2Addr.getVersion());
//
//		if (offerProd2Addr.getInstStatus() != null) {
//			System.out.println("offerProd2Addr.instStatus.statusCd=" + offerProd2Addr.getInstStatus().getStatusCd());
//			System.out.println("offerProd2Addr.instStatus.name=" + offerProd2Addr.getInstStatus().getName());
//		} else {
//			System.out.println("offerProd2Addr.instStatus=¿Õ");
//		}
//
//		if (offerProd2Addr.getProd2LocationReason() != null) {
//			System.out.println("offerProd2Addr.prod2LocationReason.statusCd="
//					+ offerProd2Addr.getProd2LocationReason().getReasonCd());
//			System.out.println("offerProd2Addr.prod2LocationReason.name="
//					+ offerProd2Addr.getProd2LocationReason().getName());
//		}
//
//		System.out.println("getProd2AddrByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2PartyByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033518L;
//		Party party = offerFacade.getPartyByProdId(prodId);
//		Assert.notNull(party);
//		dumpParty(party);
//		System.out.println("getPartyByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	@Test
//	public void testGetProd2AccountByProdId() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033518L;
//		OfferProdAccount offerProdAccount = offerFacade.getProd2AccountByProdId(prodId);
//		Assert.notNull(offerProdAccount);
//		System.out.println("offerProdAccount.atomActionId=" + offerProdAccount.getAtomActionId());
//		System.out.println("offerProdAccount.prodId=" + offerProdAccount.getProdId());
//		System.out.println("offerProdAccount.chargeItemCd=" + offerProdAccount.getChargeItemCd());
//		System.out.println("offerProdAccount.acctId=" + offerProdAccount.getAcctId());
//		System.out.println("offerProdAccount.acctCd=" + offerProdAccount.getAcctCd());
//		System.out.println("offerProdAccount.priority=" + offerProdAccount.getPriority());
//		System.out.println("offerProdAccount.percent=" + offerProdAccount.getPercent());
//		System.out.println("offerProdAccount.mailingBill=" + offerProdAccount.getMailingBill());
//		System.out.println("offerProdAccount.mailingDetail=" + offerProdAccount.getMailingDetail());
//		System.out.println("offerProdAccount.startDt=" + offerProdAccount.getStartDt());
//		System.out.println("offerProdAccount.endDt=" + offerProdAccount.getEndDt());
//		System.out.println("offerProdAccount.statusCd=" + offerProdAccount.getStatusCd());
//		System.out.println("offerProdAccount.statusDt=" + offerProdAccount.getStartDt());
//		System.out.println("offerProdAccount.createDt=" + offerProdAccount.getCreateDt());
//		System.out.println("offerProdAccount.version=" + offerProdAccount.getVersion());
//		System.out.println("offerProdAccount.acctRelaTypeCd=" + offerProdAccount.getAcctRelaTypeCd());
//		System.out.println("offerProdAccount.conChargeItemCd=" + offerProdAccount.getConChargeItemCd());
//		System.out.println("offerProdAccount.assignTypeCd=" + offerProdAccount.getAssignTypeCd());
//		System.out.println("offerProdAccount.acctProdId=" + offerProdAccount.getAcctProdId());
//
//		if (offerProdAccount.getAccount() != null) {
//			dumpAccount(offerProdAccount.getAccount());
//		} else {
//			System.out.println("offerProdAccount.account=¿Õ");
//		}
//
//		if (offerProdAccount.getChargeItem() != null) {
//			System.out.println("offerProdAccount.chargeItem.chargetItemCd="
//					+ offerProdAccount.getChargeItem().getChargeItemCd());
//			System.out.println("offerProdAccount.chargeItem.name=" + offerProdAccount.getChargeItem().getName());
//		} else {
//			System.out.println("offerProdAccount.chargeItem=¿Õ");
//		}
//
//		if (offerProdAccount.getAcctRelaType() != null) {
//			System.out.println("offerProdAccount.acctRelaType.acctRelaTypeCd="
//					+ offerProdAccount.getAcctRelaType().getAcctRelaTypeCd());
//			System.out.println("offerProdAccount.acctRelaType.name=" + offerProdAccount.getAcctRelaType().getName());
//		} else {
//			System.out.println("offerProdAccount.acctRelaTypes=¿Õ");
//		}
//
//		if (offerProdAccount.getChargeItem() != null) {
//			System.out.println("offerProdAccount.chargeItem.chargeItemCd="
//					+ offerProdAccount.getChargeItem().getChargeItemCd());
//			System.out.println("offerProdAccount.chargeItem.name=" + offerProdAccount.getChargeItem().getName());
//		} else {
//			System.out.println("offerProdAccount.chargeItem=¿Õ");
//		}
//
//		if (offerProdAccount.getAssignType() != null) {
//			System.out.println("offerProdAccount.assignType.assignTypeCd"
//					+ offerProdAccount.getAssignType().getAssignTypeCd());
//			System.out.println("offerProdAccount.assignType.name" + offerProdAccount.getAssignType().getName());
//		} else {
//			System.out.println("offerProdAccount.assignType=¿Õ");
//		}
//
//		if (offerProdAccount.getOfferProd() != null) {
//			System.out.println("offerProdAccount.offerProd!=¿Õ");
//		} else {
//			System.out.println("offerProdAccount.offerProd=¿Õ");
//		}
//
//		System.out.println("getProd2AccountByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
//
//	private void dumpOfferProdNumber(OfferProdNumber offerProdNumber) {
//		System.out.println("offerProdNumber.atomActionId=" + offerProdNumber.getAtomActionId());
//		System.out.println("offerProdNumber.prodId=" + offerProdNumber.getProdId());
//		System.out.println("offerProdNumber.anId=" + offerProdNumber.getAnId());
//		System.out.println("offerProdNumber.anTypeCd=" + offerProdNumber.getAnTypeCd());
//		System.out.println("offerProdNumber.accessNumber=" + offerProdNumber.getAccessNumber());
//		System.out.println("offerProdNumber.startDt=" + offerProdNumber.getStartDt());
//		System.out.println("offerProdNumber.endDt=" + offerProdNumber.getEndDt());
//		System.out.println("offerProdNumber.statusCd=" + offerProdNumber.getStatusCd());
//		System.out.println("offerProdNumber.statusDt=" + offerProdNumber.getStatusCd());
//		System.out.println("offerProdNumber.createDt=" + offerProdNumber.getCreateDt());
//		System.out.println("offerProdNumber.version=" + offerProdNumber.getVersion());
//
//		if (offerProdNumber.getAccessNumberType() != null) {
//			System.out.println("offerProdNumber.accessNumberType.anTypeCd="
//					+ offerProdNumber.getAccessNumberType().getAnTypeCd());
//			System.out.println("offerProdNumber.accessNumberType.name"
//					+ offerProdNumber.getAccessNumberType().getName());
//		} else {
//			System.out.println("offerProdNumber.accessNumberType=¿Õ");
//		}
//
//		if (offerProdNumber.getInstStatus() != null) {
//			System.out.println("offerProdNumber.instStatus.statusCd=" + offerProdNumber.getInstStatus().getStatusCd());
//			System.out.println("offerProdNumber.instStatus.name=" + offerProdNumber.getInstStatus().getName());
//		} else {
//			System.out.println("offerProdNumber.instStatus=¿Õ");
//		}
//	}
//
//	private void dumpAccount(Account account) {
//		System.out.println("account.acctId=" + account.getAcctId());
//		System.out.println("account.acctCd=" + account.getAcctCd());
//		System.out.println("account.areaId=" + account.getAreaId());
//		System.out.println("account.partyId=" + account.getPartyId());
//		System.out.println("account.partyName=" + account.getPartyName());
//		System.out.println("account.acctStatusCd=" + account.getAcctStatusCd());
//		System.out.println("account.acctStatusReasonCd=" + account.getAcctStatusReasonCd());
//		System.out.println("account.creditClassCd=" + account.getCreditClassCd());
//		System.out.println("account.limitQty=" + account.getLimitQty());
//		System.out.println("account.prodId=" + account.getProdId());
//		System.out.println("account.acctTypeCd=" + account.getAcctTypeCd());
//		System.out.println("account.startDt=" + account.getStartDt());
//		System.out.println("account.version=" + account.getVersion());
//		System.out.println("account.acctName=" + account.getAcctName());
//		System.out.println("account.businessPassword=" + account.getBusinessPassword());
//
//		if (account.getParty() != null) {
//			dumpParty(account.getParty());
//		} else {
//			System.out.println("account.party=¿Õ");
//		}
//
//		if (account.getAcctStatusType() != null) {
//			System.out.println("account.acctStatusType.acctStatusCd=" + account.getAcctStatusType().getAcctStatusCd());
//			System.out.println("account.acctStatusType.name=" + account.getAcctStatusType().getName());
//		} else {
//			System.out.println("account.acctStatusType=¿Õ");
//		}
//
//		if (account.getCreditClass() != null) {
//			System.out.println("account.creditClass.acctStatusCd=" + account.getCreditClass().getCreditClassCd());
//			System.out.println("account.creditClass.name=" + account.getCreditClass().getName());
//		} else {
//			System.out.println("account.creditClass=¿Õ");
//		}
//
//		if (account.getArea() != null) {
//			System.out.println("account.area.areaId=" + account.getArea().getAreaId());
//			System.out.println("account.area.name=" + account.getArea().getName());
//		} else {
//			System.out.println("account.area=¿Õ");
//		}
//
//		if (account.getOfferProd() != null) {
//			System.out.println("account.offerProd!=¿Õ");
//		} else {
//			System.out.println("account.offerProd=¿Õ");
//		}
//
//		if (account.getAccountStatusReason() != null) {
//			System.out.println("account.accountStatusReason.acctStatusReasonCd="
//					+ account.getAccountStatusReason().getAcctStatusReasonCd());
//			System.out.println("account.accountStatusReason.name=" + account.getAccountStatusReason().getName());
//		} else {
//			System.out.println("account.accountStatusReason=¿Õ");
//		}
//
//		if (account.getAcctType() != null) {
//			System.out.println("account.acctType.acctTypeCd=" + account.getAcctType().getAcctTypeCd());
//			System.out.println("account.acctType.name=" + account.getAcctType().getName());
//		} else {
//			System.out.println("account.acctType=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(account.getAccountMailing())) {
//			for (AccountMailing accountMailing : account.getAccountMailing()) {
//				System.out.println("account.accouontMailling.acctId=" + accountMailing.getAcctId());
//				System.out.println("account.accouontMailling.mailingType=" + accountMailing.getMailingType());
//				System.out.println("account.accouontMailling.param1=" + accountMailing.getParam1());
//				System.out.println("account.accouontMailling.param2=" + accountMailing.getParam2());
//				System.out.println("account.accouontMailling.param3=" + accountMailing.getParam3());
//				System.out.println("account.accouontMailling.param7=" + accountMailing.getParam7());
//			}
//		} else {
//			System.out.println("account.accountMailing=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(account.getAccountStatus())) {
//			for (AccountStatus accountStatus : account.getAccountStatus()) {
//				System.out.println("account.accountStatus.acctId=" + accountStatus.getAcctId());
//				System.out.println("account.accountStatus.acctStatusTypeCd=" + accountStatus.getAcctStatusTypeCd());
//				System.out.println("account.accountStatus.acctStatusReasonCd=" + accountStatus.getAcctStatusReasonCd());
//				System.out.println("account.accountStatus.startDt=" + accountStatus.getStartDt());
//				System.out.println("account.accountStatus.endDt=" + accountStatus.getEndDt());
//			}
//		} else {
//			System.out.println("account.accountStatus=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(account.getAcct2PaymentAcct())) {
//			for (Acct2PaymentAcct acct2PaymentAcct : account.getAcct2PaymentAcct()) {
//				System.out.println("account.acct2PaymentAcct.paymentAccountId="
//						+ acct2PaymentAcct.getPaymentAccountId());
//				Assert.notNull(acct2PaymentAcct.getPaymentAccount());
//				dumpPaymentAccount(acct2PaymentAcct.getPaymentAccount());
//			}
//		} else {
//			System.out.println("account.acct2PaymentAcct=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(account.getAccountItemCatgType())) {
//			for (AccountItemCatgType accountItemCataType : account.getAccountItemCatgType()) {
//				System.out.println("account.accountItemCataType.accountItemCatgTypeCd="
//						+ accountItemCataType.getAccountItemCatgTypeCd());
//				System.out.println("account.accountItemCataType.name" + accountItemCataType.getName());
//				List<AccountItemCatg> accountItemCatgs = accountItemCataType.getAccountItemCatg();
//				Assert.notEmpty(accountItemCatgs);
//				for (AccountItemCatg accountItemCatg : accountItemCatgs) {
//					System.out.println("account.accountItemCataType.accountItemCatg.accountItemCatgTypeCd="
//							+ accountItemCatg.getAccountItemCatgTypeCd());
//					System.out.println("account.accountItemCataType.accountItemCatg.itemSpecId="
//							+ accountItemCatg.getItemSpecId());
//					System.out.println("account.accountItemCataType.accountItemCatg.itemSpec.name="
//							+ accountItemCatg.getItemSpec().getName());
//					System.out.println("account.accountItemCataType.accountItemCatg.value="
//							+ accountItemCatg.getValue());
//				}
//			}
//		} else {
//			System.out.println("account.accountItemCatgType=¿Õ");
//		}
//	}
//
//	private void dumpParty(Party party) {
//		System.out.println("party.addressId=" + party.getAddressId());
//		System.out.println("party.addressStr=" + party.getAddressStr());
//		System.out.println("party.areaId=" + party.getAreaId());
//		System.out.println("party.areaName=" + party.getAreaName());
//		System.out.println("party.businessPassword=" + party.getBusinessPassword());
//		System.out.println("party.createDt=" + party.getCreateDt());
//		System.out.println("party.creator=" + party.getCreator());
//		System.out.println("party.defaultIdType=" + party.getDefaultIdType());
//		System.out.println("party.identityTypeName=" + party.getIdentityTypeName());
//		System.out.println("party.industryClassCd=" + party.getIndustryClassCd());
//		System.out.println("party.linkPhone=" + party.getLinkPhone());
//		System.out.println("party.mailAddressId=" + party.getMailAddressId());
//		System.out.println("party.mailAddressStr=" + party.getMailAddressStr());
//		System.out.println("party.partyId=" + party.getPartyId());
//		System.out.println("party.partyName=" + party.getPartyName());
//		System.out.println("party.acctCd=" + party.getAcctCd());
//		System.out.println("party.industryClassCdGroup=" + party.getIndustryClassCdGroup());
//		System.out.println("party.partyStatusCd=" + party.getPartyStatusCd());
//		System.out.println("party.partyTypeCd=" + party.getPartyTypeCd());
//		System.out.println("party.partyTypeName=" + party.getPartyTypeName());
//		System.out.println("party.partyVersion=" + party.getPartyVersion());
//		System.out.println("party.queryPassword=" + party.getQueryPassword());
//		System.out.println("party.simpleSpell=" + party.getSimpleSpell());
//		System.out.println("party.areaCode=" + party.getAreaCode());
//		System.out.println("party.partyCode=" + party.getPartyCode());
//		System.out.println("party.version=" + party.getVersion());
//		System.out.println("party.ifPK=" + party.getIfPK());
//
//		if (party.getArea() != null) {
//			System.out.println("party.area.areaId=" + party.getArea().getAreaId());
//			System.out.println("party.area.name=" + party.getArea().getName());
//		} else {
//			System.out.println("party.area=¿Õ");
//		}
//
//		if (party.getIndividual() != null) {
//			System.out.println("party.individual.individualId=" + party.getIndividual().getIndividualId());
//			System.out.println("party.individual.profession.professionCd="
//					+ party.getIndividual().getProfession().getProfessionCd());
//			System.out.println("party.individual.profession.name=" + party.getIndividual().getProfession().getName());
//		} else {
//			System.out.println("party.individual=¿Õ");
//		}
//
//		if (party.getIndustryClass() != null) {
//			System.out.println("party.industryClass.industryClassCd=" + party.getIndustryClass().getIndustryClassCd());
//			System.out.println("party.industryClass.industryName=" + party.getIndustryClass().getIndustryName());
//		} else {
//			System.out.println("party.industryClass=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getIdentities())) {
//			for (PartyIdentity partyIdentity : party.getIdentities()) {
//				System.out.println("party.identities.identifyType.identidiesTypeCd="
//						+ partyIdentity.getIdentifyType().getIdentidiesTypeCd());
//				System.out.println("party.identities.identifyType.name=" + partyIdentity.getIdentifyType().getName());
//				System.out.println("party.identities.identityNum=" + partyIdentity.getIdentityNum());
//			}
//		} else {
//			System.out.println("party.identities=¿Õ");
//		}
//
//		if (party.getPartyIdentityPic() != null) {
//			System.out.println("party.partyIdentityPic.identidiesPicType="
//					+ party.getPartyIdentityPic().getIdentidiesPicType());
//			System.out.println("party.partyIdentityPic.identidiesPicType="
//					+ party.getPartyIdentityPic().getIdentidiesPic());
//		} else {
//			System.out.println("party.partyIdentityPic=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getPartySegMember())) {
//			for (PartySegmentMemberList psml : party.getPartySegMember()) {
//				System.out.println("party.partySegMember.segmentId=" + psml.getSegmentId());
//				System.out.println("party.partySegMember.segmentName=" + psml.getSegmentName());
//				List<Segment> listSegment = psml.getListSegment();
//				if (CollectionUtils.isNotEmpty(listSegment)) {
//					for (Segment segment : psml.getListSegment()) {
//						System.out.println("party.partySegMember.segment.listSegment.segmentId="
//								+ segment.getSegmentId());
//						System.out.println("party.partySegMember.segment.listSegment.segmentTypeCd="
//								+ segment.getSegmentTypeCd());
//						System.out.println("party.partySegMember.segment.listSegment.name=" + segment.getName());
//					}
//				} else {
//					System.out.println("party.partySegMember.listSegment=¿Õ");
//				}
//			}
//		} else {
//			System.out.println("party.PartySegmentMemberList=¿Õ");
//		}
//
//		if (party.getPartyStatus() != null) {
//			System.out.println("party.partyStatus.name=" + party.getPartyStatus().getName());
//			System.out.println("party.partyStatus.partyStatusCde=" + party.getPartyStatus().getPartyStatusCd());
//		} else {
//			System.out.println("party.partyStatus=¿Õ");
//		}
//
//		if (party.getPartyType() != null) {
//			System.out.println("party.partyType.partyTypeCd=" + party.getPartyType().getPartyTypeCd());
//			System.out.println("party.partyType.name=" + party.getPartyType().getName());
//		} else {
//			System.out.println("party.partyType=¿Õ");
//		}
//
//		if (party.getPasswordTime() != null) {
//			System.out.println("party.passwordTime.pwType=" + party.getPasswordTime().getPwType());
//			System.out.println("party.passwordTime.timed=" + party.getPasswordTime().getTimed());
//		} else {
//			System.out.println("party.passwordTime=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getProfiles())) {
//			for (PartyProfile partyProfile : party.getProfiles()) {
//				System.out.println("party.profiles.partyProfileCatgCd=" + partyProfile.getPartyProfileCatgCd());
//				System.out.println("party.profiles.partyProfileCatgName=" + partyProfile.getPartyProfileCatgName());
//				System.out.println("party.profiles.profileValue=" + partyProfile.getProfileValue());
//			}
//		} else {
//			System.out.println("party.profiles=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getSeggrp2Segments())) {
//			for (SegmentGroup2Segment segmentGroup2Segment : party.getSeggrp2Segments()) {
//				System.out.println("party.seggrp2Segments.segmentId=" + segmentGroup2Segment.getSegmentId());
//				System.out.println("party.seggrp2Segments.segmentGroupId=" + segmentGroup2Segment.getSegmentGroupId());
//			}
//		} else {
//			System.out.println("party.seggrp2Segments=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getSegGrps())) {
//			for (Segment_group segmentGroup : party.getSegGrps()) {
//				System.out.println("party.segGrps.segmentGroupId=" + segmentGroup.getSegmentGroupId());
//				System.out.println("party.segGrps.name=" + segmentGroup.getName());
//			}
//		} else {
//			System.out.println("party.segGrps=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getSegments())) {
//			for (Segment segment : party.getSegments()) {
//				System.out.println("party.segments.segmentId=" + segment.getSegmentId());
//				System.out.println("party.segments.name=" + segment.getName());
//			}
//		} else {
//			System.out.println("party.segments=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getStrategySegMember())) {
//			for (PartySegmentMemberList partySegmentMemberList : party.getStrategySegMember()) {
//				System.out.println("party.strategySegMember.segmentId=" + partySegmentMemberList.getSegmentId());
//				System.out.println("party.strategySegMember.segmentName=" + partySegmentMemberList.getSegmentName());
//
//				if (CollectionUtils.isNotEmpty(partySegmentMemberList.getListSegment())) {
//					for (Segment segment : partySegmentMemberList.getListSegment()) {
//						System.out.println("party.strategySegMember.listSegment.segmentId=" + segment.getSegmentId());
//						System.out.println("party.strategySegMember.listSegment.name=" + segment.getName());
//					}
//				}
//			}
//		} else {
//			System.out.println("party.strategySegMember=¿Õ");
//		}
//
//		if (CollectionUtils.isNotEmpty(party.getCustVipInfo())) {
//			for (CustVipInfo custVipInfo : party.getCustVipInfo()) {
//				System.out.println("party.custVipInfo.vipCustId=" + custVipInfo.getVipCustId());
//				System.out.println("party.custVipInfo.partyId=" + custVipInfo.getPartyId());
//			}
//		} else {
//			System.out.println("party.custVipInfo=¿Õ");
//		}
//	}
//
//	private void dumpPaymentAccount(PaymentAccount paymentAccount) {
//		System.out.println("paymentAccount.paymentAccountId=" + paymentAccount.getPaymentAccountId());
//		System.out.println("paymentAccount.paymentAccountTypeCd=" + paymentAccount.getPaymentAccountTypeCd());
//		System.out.println("paymentAccount.paymentAccountTypeName=" + paymentAccount.getPaymentAccountTypeName());
//		System.out.println("paymentAccount.paymentAccountStatusCd=" + paymentAccount.getPaymentAccountStatusCd());
//		System.out.println("paymentAccount.areaId=" + paymentAccount.getAreaId());
//		System.out.println("paymentAccount.limitQty=" + paymentAccount.getLimitQty());
//		System.out.println("paymentAccount.bankId=" + paymentAccount.getBankId());
//		System.out.println("paymentAccount.bankAcctCd=" + paymentAccount.getBankAcctCd());
//		System.out.println("paymentAccount.startDt=" + paymentAccount.getStartDt());
//		System.out.println("paymentAccount.endDt=" + paymentAccount.getEndDt());
//		System.out.println("paymentAccount.version=" + paymentAccount.getVersion());
//		System.out.println("paymentAccount.paymentMan=" + paymentAccount.getPaymentMan());
//		System.out.println("paymentAccount.contractNum=" + paymentAccount.getContractNum());
//		System.out.println("paymentAccount.addrDesc=" + paymentAccount.getAddrDesc());
//		System.out.println("paymentAccount.zipCode=" + paymentAccount.getZipCode());
//
//		if (paymentAccount.getBank() != null) {
//			System.out.println("paymentAccount.bank.bankId=" + paymentAccount.getBank().getBankId());
//			System.out.println("paymentAccount.bank.name=" + paymentAccount.getBank().getName());
//			System.out.println("paymentAccount.bank.simpleSpell=" + paymentAccount.getBank().getSimpleSpell());
//		} else {
//			System.out.println("paymentAccount.bank=¿Õ");
//		}
//	}
//
//	@Test
//	public void testQueryAttachOfferByProd() {
//		long startTime = System.currentTimeMillis();
//
//		Long prodId = 513030033518L;
//		List<AttachOfferDto> result = offerFacade.queryAttachOfferByProd(prodId);
//		for (AttachOfferDto map : result) {
//			System.out.println(map);
//		}
//		System.out.println("getPartyByProdId ×ÜºÄÊ±" + (System.currentTimeMillis() - startTime));
//	}
}
