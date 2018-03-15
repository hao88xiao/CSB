<@compress single_line=true>
<response>
<resultCode>${resultCode}</resultCode>
<resultMsg>${resultMsg}</resultMsg>
<olId>${olId!""}</olId>
<olNbr>${olNbr!""}</olNbr>
<boInfos>
<#list boInfos as a>
<boInfo>
<dt>${a.dt!""}</dt>
<objType>${a.objType!""}</objType>
<objProdNum>${a.objProdNum!""}</objProdNum>
<objSpec>${a.objSpec!""}</objSpec>
<boId>${a.boId!""}</boId>
<boActionType>${a.boActionType!""}</boActionType>
<boActionTypeCd>${a.boActionTypeCd!""}</boActionTypeCd>
</boInfo>
</#list>
</boInfos>
<boDetailInfos>
<#list boDetailInfos as b>
<boDetailInfo>
<busiOrderInfo>
<#if  b.busiOrderInfo.busiOrderInfo??>
	<channelName>${b.busiOrderInfo.busiOrderInfo.channelName!""}</channelName>
	<staffId>${b.busiOrderInfo.busiOrderInfo.staffId!""}</staffId>
	<boStatus>${b.busiOrderInfo.busiOrderInfo.boStatus!""}</boStatus>
	<boId>${b.busiOrderInfo.busiOrderInfo.boId!""}</boId>
	<olStatus>${b.busiOrderInfo.busiOrderInfo.olStatus!""}</olStatus>
	<associationName>${b.busiOrderInfo.busiOrderInfo.associationName!""}</associationName>
	<associateId>${b.busiOrderInfo.busiOrderInfo.associateId!""}</associateId>
	<olId>${b.busiOrderInfo.busiOrderInfo.olId!""}</olId>
	<boActionTypeName>${b.busiOrderInfo.busiOrderInfo.boActionTypeName!""}</boActionTypeName>
	<areaName>${b.busiOrderInfo.busiOrderInfo.areaName!""}</areaName>
	<olNbr>${b.busiOrderInfo.busiOrderInfo.olNbr!""}</olNbr>
	<staffName>${b.busiOrderInfo.busiOrderInfo.staffName!""}</staffName>
	<#if b.busiOrderInfo.busiOrderInfo.soDate??>
	<soDate>${b.busiOrderInfo.busiOrderInfo.soDate?datetime}</soDate>
	</#if>
</#if>
</busiOrderInfo>
<BamAcctItemListInfo>
<#if b.acctListInfo??>
<#list b.acctListInfo as being>
	<BamAcctItemInfo>
		<accNbr>${being.accNbr!""}</accNbr>
		<acctItemId>${being.acctItemId!""}</acctItemId>
		<acctItemTypeId>${being.acctItemTypeId!""}</acctItemTypeId>
		<acctItemTypeName>${being.acctItemTypeName!""}</acctItemTypeName>
		<action>${being.action!""}</action>
		<allowStaffId>${being.allowStaffId!""}</allowStaffId>
		<allowStaffName>${being.allowStaffName!""}</allowStaffName>
		<appCharge>${being.appCharge!""}</appCharge>
		<areaId>${being.areaId!""}</areaId>
		<billingCycleId>${being.billingCycleId!""}</billingCycleId> 
		<boActionTypeId>${being.boActionTypeId!""}</boActionTypeId>
		<boActionTypeName>${being.boActionTypeName!""}</boActionTypeName>
		<boId>${being.boId!""}</boId> 
		<cause>${being.cause!""}</cause>
		<charge>${being.charge!""}</charge> 
		<chargeModifyReasonCd>${being.chargeModifyReasonCd!""}</chargeModifyReasonCd> 
		<#if being.createDt?? >
		<createDt>${being.createDt?datetime}</createDt> 
		</#if>
		<custId>${being.custId!""}</custId> 
		<custName>${being.custName!""}</custName> 
		<dataSourse>${being.dataSourse!""}</dataSourse> 
		<dealChannelId>${being.dealChannelId!""}</dealChannelId> 
		<dealStaffId>${being.dealStaffId!""}</dealStaffId> 
		<dealState>${being.dealState!""}</dealState> 
		<infoId>${being.infoId!""}</infoId> 
		<invoiceNum>${being.invoiceNum!""}</invoiceNum> 
		<isRefund>${being.isRefund!""}</isRefund> 
		<itemSourceId>${being.itemSourceId!""}</itemSourceId>
		<objId>${being.objId!""}</objId> 
		<objectName>${being.objectName!""}</objectName> 
		<offerId>${being.offerId!""}</offerId> 
		<offerInstId>${being.offerInstId!""}</offerInstId>
        <olId>${being.olId!""}</olId>
        <otherState>${being.otherState!""}</otherState>
        <payMethod>${being.payMethod!""}</payMethod>
        <payMethodId>${being.payMethodId!""}</payMethodId>
        <payMethodKey>${being.payMethodKey!""}</payMethodKey>
        <payMethodName>${being.payMethodName!""}</payMethodName>
        <payName>${being.payName!""}</payName>
        <productId>${being.productId!""}</productId>
        <readDate>${being.readDate!""}</readDate>
        <relaInfoId>${being.relaInfoId!""}</relaInfoId>
        <servId>${being.servId!""}</servId>
        <soChargeStatusCd>${being.soChargeStatusCd!""}</soChargeStatusCd>
        <state>${being.state!""}</state>
        <#if being.stateDate??>
        <stateDate>${being.stateDate?datetime}</stateDate>
        </#if>
        <statusCd>${being.statusCd!""}</statusCd>
	</BamAcctItemInfo>
</#list>
</#if>
 </BamAcctItemListInfo>
 <dealerAidListInfo>
 <#if b.resultBo2Staff??>
<#list b.resultBo2Staff as aA>
	<dealerAidInfo>
		<boId>${aA.boId!""}</boId>
		<#if aA.createDt??>
		<createDt>${aA.createDt?datetime}</createDt>
		</#if>
		<orgId>${aA.orgId!""}</orgId>
		<#if aA.ourOrg??>
		<ourOrg>
			<orgId>${aA.ourOrg.orgId!""}</orgId>
			<compositeName>${aA.ourOrg.compositeName!""}</compositeName>
			<smPartyId>${aA.ourOrg.smPartyId!""}</smPartyId>
		</ourOrg>
		</#if>
		<#if aA.ourStaff??>
		<ourStaff>
			<staffId>${aA.ourStaff.staffId!""}</staffId>
			<ownerDepartment>${aA.ourStaff.ownerDepartment!""}</ownerDepartment>
			<name>${aA.ourStaff.name!""}</name>
			<smPartyId>${aA.ourStaff.smPartyId!""}</smPartyId>
			<#if aA.ourStaff.createDt?? >
			<createDt>${aA.ourStaff.createDt?datetime}</createDt>
			</#if>
			<staffNumber>${aA.ourStaff.staffNumber!""}</staffNumber>
			<bindNumber>${aA.ourStaff.bindnumber!""}</bindNumber>
		</ourStaff>
		</#if>
		<#if aA.party2ProduRole?? >
		<party2ProduRole>
			<description>${aA.party2ProduRole.description!""}</description>
			<name>${aA.party2ProduRole.name!""}</name>
			<partyProductRelaRoleCd>${aA.party2ProduRole.partyProductRelaRoleCd!""}</partyProductRelaRoleCd>
		</party2ProduRole>
		</#if>
		<partyRelaRoleCd>${aA.partyRelaRoleCd!""}</partyRelaRoleCd>
		<staffId>${aA.staffId!""}</staffId>
	</dealerAidInfo>
</#list>
</#if>
</dealerAidListInfo>
<couponListInfo>
<#list b.couponInfo.couponInfo as d >
	<couponInfo>
		<agentName>${d.agentName!""}</agentName>
		<couponInstNumber>${d.couponInstNumber!""}</couponInstNumber>
		<couponName>${d.couponName!""}</couponName>
		<couponNum>${d.couponNum!""}</couponNum>
		<inOutNbr>${d.inOutNbr!""}</inOutNbr>
		<inOutType>${d.inOutType!""}</inOutType>
		<saleName>${d.saleName!""}</saleName>
		<state>${d.state!""}</state>
		<storeName>${d.storeName!""}</storeName>
	</couponInfo>
</#list>
</couponListInfo>
<attachOfferOrderInfo>
<offerInfos>
<#if b.resultAttachOfferOrderInfo.offerInfos??>
<#list b.resultAttachOfferOrderInfo.offerInfos as h >
	<offerInfo>
		<areaName>${h.areaName!""}</areaName>
		<boActionTypeName>${h.boActionTypeName!""}</boActionTypeName>
		<boId>${h.boId!""}</boId>
		<#if h.endDt??>
		<endDt>${h.endDt?date}</endDt>
		</#if>
		<offerId>${h.offerId!""}</offerId>
		<offerNbr>${h.offerNbr!""}</offerNbr>
		<offerSpecName>${h.offerSpecName!""}</offerSpecName>
		<partyName>${h.partyName!""}</partyName>
		<servId>${h.servId!""}</servId>
		<#if h.startDt??>
		<startDt>${h.startDt?date}</startDt>
		</#if>
		<state>${h.state!""}</state>
		<statusCd>${h.statusCd!""}</statusCd>
		<statusName>${h.statusName!""}</statusName>
	</offerInfo>
</#list>
</#if>
</offerInfos>
<ooOwners>
<#if b.resultAttachOfferOrderInfo.ooOwners??>
<#list b.resultAttachOfferOrderInfo.ooOwners as oO>
	<ooOwner>
		<boId>${oO.boId!""}</boId>
		<detail>${oO.detail!""}</detail>
		<partyId>${oO.partyId!""}</partyId>
		<state>${oO.state!""}</state>
	</ooOwner>
</#list>
</#if>
</ooOwners>
<ooParams>
<#if b.resultAttachOfferOrderInfo.ooParams??>
<#list b.resultAttachOfferOrderInfo.ooParams as oP>
	<ooParam>
		<boId>${oP.boId!""}</boId>
		<itemSpecId>${oP.itemSpecId!""}</itemSpecId>
		<name>${oP.name!""}</name>
		<servId>${oP.servId!""}</servId>
		<state>${oP.state!""}</state>
		<value>${oP.value!""}</value>
	</ooParam>
</#list>
</#if>
</ooParams>
<ooRoles>
<#if b.resultAttachOfferOrderInfo.ooRoles??>
<#list b.resultAttachOfferOrderInfo.ooRoles as oR>
	<ooRole>
		<accessNumber>${oR.accessNumber!""}</accessNumber>
		<boId>${oR.boId!""}</boId>
		<custName>${oR.custName!""}</custName>
		<detail>${oR.detail!""}</detail>
		<objInstId>${oR.objInstId!""}</objInstId>
		<objType>${oR.objType!""}</objType>
		<offerRoleId>${oR.offerRoleId!""}</offerRoleId>
		<roleName>${oR.roleName!""}</roleName>
		<state>${oR.state!""}</state>
		<statusCd>${oR.statusCd!""}</statusCd>
	</ooRole>
</#list>
</#if>
</ooRoles>
</attachOfferOrderInfo>
<offerOrderInfo>
<offerInfos>
<#if b.resultOfferOrderInfo.offerInfos??>
<#list b.resultOfferOrderInfo.offerInfos as h >
	<offerInfo>
		<areaName>${h.areaName!""}</areaName>
		<boActionTypeName>${h.boActionTypeName!""}</boActionTypeName>
		<boId>${h.boId!""}</boId>
		<#if h.endDt??>
		<endDt>${h.endDt?date}</endDt>
		</#if>
		<offerId>${h.offerId!""}</offerId>
		<offerNbr>${h.offerNbr!""}</offerNbr>
		<offerSpecName>${h.offerSpecName!""}</offerSpecName>
		<partyName>${h.partyName!""}</partyName>
		<servId>${h.servId!""}</servId>
		<#if h.startDt??>
		<startDt>${h.startDt?date}</startDt>
		</#if>
		<state>${h.state!""}</state>
		<statusCd>${h.statusCd!""}</statusCd>
		<statusName>${h.statusName!""}</statusName>
	</offerInfo>
</#list>
</#if>
</offerInfos>
<ooOwners>
<#if b.resultOfferOrderInfo.ooOwners??>
<#list b.resultOfferOrderInfo.ooOwners as oO>
	<ooOwner>
		<boId>${oO.boId!""}</boId>
		<detail>${oO.detail!""}</detail>
		<partyId>${oO.partyId!""}</partyId>
		<state>${oO.state!""}</state>
	</ooOwner>
</#list>
</#if>
</ooOwners>
<ooParams>
<#if b.resultOfferOrderInfo.ooParams??>
<#list b.resultOfferOrderInfo.ooParams as oP>
	<ooParam>
		<boId>${oP.boId!""}</boId>
		<itemSpecId>${oP.itemSpecId!""}</itemSpecId>
		<name>${oP.name!""}</name>
		<servId>${oP.servId!""}</servId>
		<state>${oP.state!""}</state>
		<value>${oP.value!""}</value>
	</ooParam>
</#list>
</#if>
</ooParams>
<ooRoles>
<#if b.resultAttachOfferOrderInfo.ooRoles??>
<#list b.resultAttachOfferOrderInfo.ooRoles as oR>
	<ooRole>
		<accessNumber>${oR.accessNumber!""}</accessNumber>
		<boId>${oR.boId!""}</boId>
		<custName>${oR.custName!""}</custName>
		<detail>${oR.detail!""}</detail>
		<objInstId>${oR.objInstId!""}</objInstId>
		<objType>${oR.objType!""}</objType>
		<offerRoleId>${oR.offerRoleId!""}</offerRoleId>
		<roleName>${oR.roleName!""}</roleName>
		<state>${oR.state!""}</state>
		<statusCd>${oR.statusCd!""}</statusCd>
	</ooRole>
</#list>
</#if>
</ooRoles>
</offerOrderInfo>
<prodOrderInfo>
<boProdFeeTypes>
<#if b.resultProdOrderInfo.boProdFeeTypes??>
<#list b.resultProdOrderInfo.boProdFeeTypes as bO>
	<boProdFeeType>
		<detail>${bO.detail!""}</detail>
		<feeType>${bO.feeType!""}</feeType>
		<state>${bO.state!""}</state>
	</boProdFeeType>
</#list>
</#if>
</boProdFeeTypes>
<boProdItems>
<#if b.resultProdOrderInfo.boProdItems??>
<#list b.resultProdOrderInfo.boProdItems as bO>
	<boProdItem>
		<itemSpecId>${bO.itemSpecId!""}</itemSpecId>
		<name>${bO.name!""}</name>
		<state>${bO.state!""}</state>
		<value>${bO.value!""}</value>
	</boProdItem>
</#list>
</#if>
</boProdItems>
<boCusts>
<#if b.resultProdOrderInfo.boCusts??>
<#list b.resultProdOrderInfo.boCusts as bO>
	<boCust>
		<partyId>${bO.partyId!""}</partyId>
	</boCust>
</#list>
</#if>
</boCusts>
<boProd2Ans>
<#if b.resultProdOrderInfo.boProd2Ans??>
<#list b.resultProdOrderInfo.boProd2Ans as bO>
	<boProd2An>
		<accessNumber>${bO.accessNumber!""}</accessNumber>
	</boProd2An>
</#list>
</#if>
</boProd2Ans>
<boProdAddresses>
<#if b.resultProdOrderInfo.boProdAddresses??>
<#list b.resultProdOrderInfo.boProdAddresses as bO>
	<boProdAddresse>
		<addrDetail>${bO.addrDetail!""}</addrDetail>
		<addrId>${bO.addrId!""}</addrId>
	</boProdAddresse>
</#list>
</#if>
</boProdAddresses>
<boProdAns>
<#if b.resultProdOrderInfo.boProdAns??>
<#list b.resultProdOrderInfo.boProdAns as bO>
	<boProdAn>
		<prodId>${bO.prodId!""}</prodId>
		<accessNumber>${bO.accessNumber!""}</accessNumber>
	</boProdAn>
</#list>
</#if>
</boProdAns>
</prodOrderInfo>
<custInfo>
<#if b.resultCustInfo.boCustIdentityDto??>
<#list b.resultCustInfo.boCustIdentityDto as bO>
<boCustIdentityDto>
	<identifyNumber>${bO.identifyNumber!""}</identifyNumber>
	<identifyTypeName>${bO.identifyTypeName!""}</identifyTypeName>
</boCustIdentityDto>
</#list>
</#if>
<#if b.resultCustInfo.boCustProfileDto??>
<#list b.resultCustInfo.boCustProfileDto as bO>
<boCustProfileDto>
	<partyProfileCatgCd>${bO.partyProfileCatgCd!""}</partyProfileCatgCd>
	<partyProfileCatgName>${bO.partyProfileCatgName!""}</partyProfileCatgName>
	<prodfileValue>${bO.prodfileValue!""}</prodfileValue>
</boCustProfileDto>
</#list>
</#if>
<#if b.resultCustInfo.boCustSegmentDto??>
<#list b.resultCustInfo.boCustSegmentDto as bO>
<boCustSegmentDto>
	<segmentName>${bO.segmentName!""}</segmentName>
</boCustSegmentDto>
</#list>
</#if>
</custInfo>
<busiOrderAttrInfo>
<#if  b.busiOrderAttrInfo.busiOrderAttrInfo??>
<#list b.busiOrderAttrInfo.busiOrderAttrInfo as bO>
<busiOrderAttrInfo>
	<itemSpecName>${bO.itemSpecName!""}</itemSpecName>
	<value>${bO.value!""}</value>
</busiOrderAttrInfo>
</#list>
</#if>
</busiOrderAttrInfo>
</boDetailInfo>
</#list>
</boDetailInfos>
</response>
</@compress>