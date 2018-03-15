<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodRecords>
		<prodRecord>
			<#if queryType?contains("1")>
			<custInfo>
				<id>${party.partyId!""}</id>
				<custNo>${groupCustSeq!""}</custNo>
				<name>${party.partyName!""}</name>
				<simpleSpell>${party.simpleSpell!""}</simpleSpell>
				<custTypeId>${party.partyTypeCd!""}</custTypeId>
				<custTypeName>${party.partyTypeName!""}</custTypeName>
				<industryClassCd>${party.industryClassCd!""}</industryClassCd>
				<areaId>${party.areaId!""}</areaId>
				<areaName>${party.areaName!""}</areaName>
				<addr>${party.mailAddress!""}</addr>
				<cerdAddr>${party.mailAddressStr!""}</cerdAddr>
				<ifPK>${party.ifPK!""}</ifPK>
			<indent>
			<#if identifyMap??>
			<indentType>${identifyMap.IDENTIDIES_TYPE_CD!""}</indentType>
			<indentTypeName>${identifyMap.NAME!""}</indentTypeName>
			<indentNum>${identifyMap.IDENTITY_NUM!""}</indentNum>
			<#else>
			<indentType></indentType>
			<indentTypeName></indentTypeName>
			<indentNum></indentNum>
			</#if>
			</indent>
			<#if profiles??>
			<contactName>${profiles.CONTACT_NAME!""}</contactName>
			<contactPhone>${profiles.TELEPHONE!""}</contactPhone>
			<contactMobile>${profiles.MOBILE!""}</contactMobile>
			<contactAddress>${profiles.CONTACT_ADDRESS!""}</contactAddress>
			<emailAddress>${profiles.EMAIL_ADDRESS!""}</emailAddress>
			<postCode>${profiles.POST_CODE!""}</postCode>
			<#else>
			<contactName></contactName>
			<contactPhone></contactPhone>
			<contactMobile></contactMobile>
			<contactAddress></contactAddress>
			<emailAddress></emailAddress>
			<postCode></postCode>
			</#if>
			<#if custLevel??>
			<custLevel>${custLevel.CUSTLEVEL!""}</custLevel>
			<custLevelId>${custLevel.CUSTLEVELID!""}</custLevelId>
			<#else>
			<custLevel></custLevel>
			<custLevelId></custLevelId>
			</#if>
			<#if custBrand??>
			<custLevel>${custBrand.NAME!""}</custLevel>
			<custLevelId>${custBrand.CATEGORY_NODE_ID!""}</custLevelId>
			<#else>
			<custLevel></custLevel>
			<custLevelId></custLevelId>
			</#if>
			</custInfo>
			</#if>
			
			
			
			<#if queryType?contains("2")>
			<productInfo>
			    <prodId>${prodId!""}</prodId>
			    <oldCGFlag>${oldCGFlag!""}</oldCGFlag>
			    <compProdFlag>${compProdFlag!""}</compProdFlag>
			    <WLANFlag>${WLANFlag!""}</WLANFlag>
<#--主接入号-->
				<#if offerProdNumbers??>		    
			    <#list offerProdNumbers.offerProdNumbers as offerProdNumbers>
				<accNbr>${offerProdNumbers.accessNumber!""}</accNbr>
				<accNbrType>${offerProdNumbers.accessNumberType.anTypeCd!""}</accNbrType>
				<accNbrTypeName>${offerProdNumbers.accessNumberType.name!""}</accNbrTypeName>
				</#list>
				<#else>
				<accNbr></accNbr>
				<accNbrType></accNbrType>
				<accNbrTypeName></accNbrTypeName>
				</#if>

<#--产品规格-->				
				<#if offerProdSpecs??>
				<#list offerProdSpecs.offerProdSpecs as offerProdSpecs>
				<prodSpecId>${offerProdSpecs.prodSpec.prodSpecId!""}</prodSpecId>
				<prodSpecName>${offerProdSpecs.prodSpec.name!""}</prodSpecName>
				</#list>
				<#else>
				<prodSpecId></prodSpecId>
				<prodSpecName></prodSpecName>
				</#if>
<#--付费方式-->					
				<#if offerProdFeeTypes??>
				<#list offerProdFeeTypes.offerProdFeeTypes as offerProdFeeTypes>
				<feeType>${offerProdFeeTypes.feeTypes.feeType!""}</feeType>
				<feeTypeName>${offerProdFeeTypes.feeTypes.name!""}</feeTypeName>
				</#list>
				<#else>
				<feeType></feeType>
				<feeTypeName></feeTypeName>
				</#if>
<#--状态-->					
				<#if offerProdStatuses??>
				<#list offerProdStatuses.offerProdStatuses as offerProdStatuses>
				<productStatusCd>${offerProdStatuses.prodStatusType.prodStatusCd!""}</productStatusCd>
				<productStatusName>${offerProdStatuses.prodStatusType.name!""}</productStatusName>
				</#list>
				<#else>
				<productStatusCd></productStatusCd>
				<productStatusName></productStatusName>
				</#if>
				
<#--地区-->					
				<#if  offerProdNumbers.area??>
				<areaId>${offerProdNumbers.area.areaId!""}</areaId>
				<areaName>${offerProdNumbers.area.name!""}</areaName>
				<#else>
				<areaId></areaId>
				<areaName></areaName>
				</#if>
				
<#--起止时间-->									
				<#if offerProdNumbers.startDt??>
				<startDt>${offerProdNumbers.startDt?datetime}</startDt>
				<#else>
				<startDt></startDt>
				</#if>
				<#if offerProdNumbers.endDt??>
				<endDt>${offerProdNumbers.endDt?datetime}</endDt>
				<#else>
				<endDt></endDt>
				</#if>
				
<#--外部产品实例编码？-->		
			
				<extProdInstId>${offerProdNumbers.extProdInstId!""}</extProdInstId>
			
<#--产品属性-->					
				<prodItems>
					<#if offerProdItems??>
					<#list offerProdItems.offerProdItems as item>
					<prodItem>
						<id>${item.itemSpec.itemSpecId!""}</id>
						<name>${item.itemSpec.name!""}</name>
						<value>${item.value!""}</value>
					</prodItem>
					</#list>
					</#if>
				</prodItems>
<#--产品对应服务-->					
				<prodServs>
					<#list offerServs.offerServs as serv>
					<prodServ>
					<#if serv.servSpec??>
						<id>${serv.servSpec.servSpecId!""}</id>
						<name>${serv.servSpec.name!""}</name>
						
						<#if serv.startDt??>
						<startDt>${serv.startDt?datetime}</startDt>
						<#else>
						<startDt></startDt>
						</#if>
						<#if serv.endDt??>
						<endDt>${serv.endDt?datetime}</endDt>
						<#else>
						<endDt></endDt>
						</#if>
						
						
						<servItems>
							<#if serv.offerServItems??>
							<#list serv.offerServItems as servItem>
						  	<servItem>
								<id>${servItem.itemSpec.itemSpecId!""}</id>
								<name>${servItem.itemSpec.name!""}</name>
								<value>${servItem.itemSpec.value!""}</value>
							</servItem>
							</#list>
							</#if>
						</servItems>
<#--服务依赖互斥关系-->
						<prodServRelas>
						<#list prodServRelasMapList as prodServRelasMap>
						<#if prodServRelasMap.servSpecId = serv.servSpec.servSpecId>
						<#list prodServRelasMap.prodServRelas as prodServRela>
						<prodServRela>
						<servSpecId>${prodServRela.servSpec2Id!""}</servSpecId>
						<servSpecName>${prodServRela.servSpec2IdName!""}</servSpecName>
						<relaTypeCd>${prodServRela.servRelaReasonCd!""}</relaTypeCd>
						<relaTypeName>${prodServRela.servRelaTypeName!""}</relaTypeName>
						</prodServRela>
						</#list>
						</#if>
						</#list>
						</prodServRelas>
					</#if>
					</prodServ>
					</#list>
				</prodServs>
<#--组合产品-->
				<compProds>
					<#list offerProdComps.offerProdComps as comp>
					<compProd>
						<compProdAccNbr>${comp.compProd.accessNumber!""}</compProdAccNbr>
						<compProdId>${comp.compProd.prodId!""}</compProdId>
						<#list comp.compProd.offerProdSpecs as offerProdSpec>
						<compProdSpecId>${offerProdSpec.prodSpecId!""}</compProdSpecId>
						<compProdname>${offerProdSpec.prodSpec.name!""}</compProdname>
						</#list>
						<prodCompRelaRoleCd>${comp.prodCompRelaRoleCd!""}</prodCompRelaRoleCd>
					</compProd>
					</#list>
				</compProds>
				
<#--关联产品-->	
				<prod2prods>
					<#if offerProd2Prods??>
					<#list offerProd2Prods.offerProd2Prods as p2p>
					<prod2prod>
						<relaprodAccNbr>${p2p.offerProdNumber.accessNumber!""}</relaprodAccNbr>
						<relatedProdId>${p2p.relatedProdId!""}</relatedProdId>
						<relaReasonCd>${p2p.prodRelaReason.reasonCd!""}</relaReasonCd>
						<relaReasonName>${p2p.prodRelaReason.name!""}</relaReasonName>
					</prod2prod>
					</#list>
					</#if>
				</prod2prods>
			</productInfo>
			</#if>
		
<#--账户信息-->				
			<#if queryType?contains("3")>	
			<#list offerProdAccounts.offerProdAccounts as opa>
			<accoutInfo>
				<#if opa.account??>
				<acctNbr>${opa.account.acctCd!""}</acctNbr>
				<payMethodCd>${opa.chargeItem.payMethod.payMethodCd!""}</payMethodCd>
				<#list opa.account.acct2PaymentAcct as A2PA>
				<#if opa.acctId = A2PA.acctId>
				<#if A2PA.paymentAccount??>
				<payType>${A2PA.paymentAccount.paymentAccountTypeCd!""}</payType>
				<#else>
				<payType></payType>
				</#if>
				<#if A2PA.paymentAccount.bank??>
				<bankId>${A2PA.paymentAccount.bank.bankId!""}</bankId>
				<bankName>${A2PA.paymentAccount.bank.name!""}</bankName>
				<#else>
				<bankId></bankId>
				<bankName></bankName>
				</#if>
				<bankAcctCd>${A2PA.paymentAccount.bankAcctCd!""}</bankAcctCd>
				</#if>
				</#list>
				<#if offerProdNumbers??>
				<payAccNbr>${offerProdNumbers.accessNumber!""}</payAccNbr>
				</#if>
				</#if>
			</accoutInfo>
			</#list>
			</#if>

<#--销售品-->
			<#if queryType?contains("4")>
			<offerList>
				<#list offerList as ofl>
				<offer>
					<offerId>${ofl.offerId!""}</offerId>
					<offerSpecId>${ofl.offerSpecId!""}</offerSpecId>
					<offerSpecName>${ofl.offerSpecName!""}</offerSpecName>
					<offerTypeCd>${ofl.offerTypeCd!""}</offerTypeCd>
					<statusCd>${ofl.statusCd!""}</statusCd>
					<statusName>${ofl.stateName!""}</statusName>
					<startDt>${ofl.startDt!""}</startDt>
					<endDt>${ofl.endDt!""}</endDt>
					<#--销售品或服务属性-->
					<params>
					<#list offerParamMapList as offerParamMap>
					<#if offerParamMap.offerId = ofl.offerId >
						<#if offerParamMap.offerServParams ??>
							<#list offerParamMap.offerServParams as OfferServItemDto>
							<param>
					 			<servId>${OfferServItemDto.servId!""}</servId>
					 			<itemSpecId>${OfferServItemDto.itemSpecId!""}</itemSpecId>
								<itemSpecName>${OfferServItemDto.itemSpecName!""}</itemSpecName>
								<value>${OfferServItemDto.value!""}</value>
							</param>
							</#list>
						<#elseif offerParamMap.offerParams ??>
							<#list offerParamMap.offerParams as OfferParamDto>
							<#--过滤不用的属性-->
							<#if OfferParamDto.itemSpecId != 5030 && OfferParamDto.itemSpecId != 5031>
							<param>
					 			<offerParamId>${OfferParamDto.offerParamId!""}</offerParamId>
					 			<itemSpecId>${OfferParamDto.itemSpecId!""}</itemSpecId>
								<itemSpecName>${OfferParamDto.itemSpecName!""}</itemSpecName>
								<value>${OfferParamDto.value!""}</value>
							</param>
							</#if>
							</#list>
						</#if>
					</#if>
					</#list>
					</params>
				</offer>
				</#list>
			</offerList>
			</#if>
		</prodRecord>
	</prodRecords>
</response>
</@compress>