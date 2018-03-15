<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodRecords>
		<#list resultProdInfoMapList as resultProdInfoMap>
		<prodRecord>
			<#if resultProdInfoMap.queryType?contains("1")>
			<custInfo>
				<id>${resultProdInfoMap.party.partyId!""}</id>
				<custNo>${resultProdInfoMap.groupCustSeq!""}</custNo>
				<name>${resultProdInfoMap.party.partyName!""}</name>
				<simpleSpell>${resultProdInfoMap.party.simpleSpell!""}</simpleSpell>
				<custTypeId>${resultProdInfoMap.party.partyTypeCd!""}</custTypeId>
				<custTypeName>${resultProdInfoMap.party.partyTypeName!""}</custTypeName>
				<industryClassCd>${resultProdInfoMap.party.industryClassCd!""}</industryClassCd>
				<areaId>${resultProdInfoMap.party.areaId!""}</areaId>
				<areaName>${resultProdInfoMap.party.areaName!""}</areaName>
				<addr>${resultProdInfoMap.party.mailAddress!""}</addr>
				<cerdAddr>${resultProdInfoMap.party.mailAddressStr!""}</cerdAddr>
				<ifPK>${resultProdInfoMap.party.ifPK!""}</ifPK>
			<indent>
			<#if resultProdInfoMap.identifyMap??>
			<indentType>${resultProdInfoMap.identifyMap.IDENTIDIES_TYPE_CD!""}</indentType>
			<indentTypeName>${resultProdInfoMap.identifyMap.NAME!""}</indentTypeName>
			<indentNum>${resultProdInfoMap.identifyMap.IDENTITY_NUM!""}</indentNum>
			<#else>
			<indentType></indentType>
			<indentTypeName></indentTypeName>
			<indentNum></indentNum>
			</#if>
			</indent>
			<#if resultProdInfoMap.profiles??>
			<contactName>${resultProdInfoMap.profiles.CONTACT_NAME!""}</contactName>
			<contactPhone>${resultProdInfoMap.profiles.TELEPHONE!""}</contactPhone>
			<contactMobile>${resultProdInfoMap.profiles.MOBILE!""}</contactMobile>
			<contactAddress>${resultProdInfoMap.profiles.CONTACT_ADDRESS!""}</contactAddress>
			<emailAddress>${resultProdInfoMap.profiles.EMAIL_ADDRESS!""}</emailAddress>
			<postCode>${resultProdInfoMap.profiles.POST_CODE!""}</postCode>
			<#else>
			<contactName></contactName>
			<contactPhone></contactPhone>
			<contactMobile></contactMobile>
			<contactAddress></contactAddress>
			<emailAddress></emailAddress>
			<postCode></postCode>
			</#if>
			<#if resultProdInfoMap.custLevel??>
			<custLevel>${resultProdInfoMap.custLevel.CUSTLEVEL!""}</custLevel>
			<custLevelId>${resultProdInfoMap.custLevel.CUSTLEVELID!""}</custLevelId>
			<#else>
			<custLevel></custLevel>
			<custLevelId></custLevelId>
			</#if>
			</custInfo>
			</#if>

	
			<#if resultProdInfoMap.queryType?contains("2")>
			<productInfo>
			    <prodId>${resultProdInfoMap.prod.prodId!""}</prodId>
			    <oldCGFlag>${resultProdInfoMap.oldCGFlag!""}</oldCGFlag>
			    <compProdFlag>${resultProdInfoMap.compProdFlag!""}</compProdFlag>
<#--主接入号-->
				<#if  resultProdInfoMap.prod.offerProdNumbers??>		    
			    <#list resultProdInfoMap.prod.offerProdNumbers as offerProdNumbers>
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
				<#if  resultProdInfoMap.prod.offerProdSpecs??>
				<#list resultProdInfoMap.prod.offerProdSpecs as offerProdSpecs>
				<prodSpecId>${offerProdSpecs.prodSpec.prodSpecId!""}</prodSpecId>
				<prodSpecName>${offerProdSpecs.prodSpec.name!""}</prodSpecName>
				</#list>
				<#else>
				<prodSpecId></prodSpecId>
				<prodSpecName></prodSpecName>
				</#if>
<#--付费方式-->					
				<#if  resultProdInfoMap.prod.offerProdFeeTypes??>
				<#list resultProdInfoMap.prod.offerProdFeeTypes as offerProdFeeTypes>
				<feeType>${offerProdFeeTypes.feeTypes.feeType!""}</feeType>
				<feeTypeName>${offerProdFeeTypes.feeTypes.name!""}</feeTypeName>
				</#list>
				<#else>
				<feeType></feeType>
				<feeTypeName></feeTypeName>
				</#if>
<#--状态-->					
				<#if  resultProdInfoMap.prod.offerProdStatuses??>
				<#list resultProdInfoMap.prod.offerProdStatuses as offerProdStatuses>
				<productStatusCd>${offerProdStatuses.prodStatusType.prodStatusCd!""}</productStatusCd>
				<productStatusName>${offerProdStatuses.prodStatusType.name!""}</productStatusName>
				</#list>
				<#else>
				<productStatusCd></productStatusCd>
				<productStatusName></productStatusName>
				</#if>
				
<#--地区-->					
				<#if  resultProdInfoMap.prod.area??>
				<areaId>${resultProdInfoMap.prod.area.areaId!""}</areaId>
				<areaName>${resultProdInfoMap.prod.area.name!""}</areaName>
				<#else>
				<areaId></areaId>
				<areaName></areaName>
				</#if>
				
<#--起止时间-->	
				<#if  resultProdInfoMap.prod.startDt??>							
				<startDate>${resultProdInfoMap.prod.startDt?datetime!""}</startDate>
				<endDate>${resultProdInfoMap.prod.endDt?datetime!""}</endDate>
				<#else>
				<startDate></startDate>
				<endDate></endDate>
				</#if>
				
<#--外部产品实例编码？-->		
			
				<extProdInstId>${resultProdInfoMap.prod.extProdInstId!""}</extProdInstId>
			
<#--产品属性-->					
				<prodItems>
					<#if resultProdInfoMap.prod.offerProdItems??>
					<#list resultProdInfoMap.prod.offerProdItems as item>
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
					<#list resultProdInfoMap.prod.offerServs as serv>
					<prodServ>
					<#if serv.servSpec??>
						<id>${serv.servSpec.servSpecId!""}</id>
						<name>${serv.servSpec.name!""}</name>
						<startDate>${serv.servSpec.startDt!""}</startDate>
						<endDate>${serv.servSpec.endDt!""}</endDate>
						<servItems>
							<#list serv.offerServItems as servItem>
						  	<servItem>
								<id>${servItem.itemSpec.itemSpecId!""}</id>
								<name>${servItem.itemSpec.name!""}</name>
								<value>${servItem.itemSpec.value!""}</value>
							</servItem>
							</#list>
						</servItems>
							<#--服务依赖互斥关系-->
						<prodServRelas>
						<#list resultProdInfoMap.prodServRelasMapList as prodServRelasMap>
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
					<#list resultProdInfoMap.prod.offerProdComps as comp>
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
					<#if resultProdInfoMap.prod.offerProd2Prods??>
					<#list resultProdInfoMap.prod.offerProd2Prods as p2p>
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
			<#if resultProdInfoMap.queryType?contains("3")>	
			<#list resultProdInfoMap.prod.offerProdAccounts as opa>
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
				<payAccNbr>${resultProdInfoMap.prod.accessNumber!""}</payAccNbr>
				</#if>
			</accoutInfo>
			</#list>
			</#if>
<#--销售品-->

		<#if resultProdInfoMap.queryType?contains("4")>
		<offerList>
				<#list resultProdInfoMap.offerList as ofl>
				<offer>
					<offerId>${ofl.offerId!""}</offerId>
					<offerSpecId>${ofl.offerSpecId!""}</offerSpecId>
					<offerSpecName>${ofl.offerSpecName!""}</offerSpecName>
					<offerTypeCd>${ofl.offerTypeCd!""}</offerTypeCd>
					<statusCd>${ofl.statusCd!""}</statusCd>
					<statusName>${ofl.stateName!""}</statusName>
					<startDt>${ofl.startDt!""}</startDt>
					<endDt>${ofl.endDt!""}</endDt>
					<#--附属销售品属性-->
					<params>
					<#list resultProdInfoMap.offerParamMapList as offerParamMap>
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
							<param>
					 			<offerParamId>${OfferParamDto.offerParamId!""}</offerParamId>
					 			<itemSpecId>${OfferParamDto.itemSpecId!""}</itemSpecId>
								<itemSpecName>${OfferParamDto.itemSpecName!""}</itemSpecName>
								<value>${OfferParamDto.value!""}</value>
							</param>
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
		</#list>
	</prodRecords>
</response>
</@compress>