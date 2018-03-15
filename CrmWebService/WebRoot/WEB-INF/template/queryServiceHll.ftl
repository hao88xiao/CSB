<@compress single_line=true>
<Query_response>
	<basicInfo>
    	<result>${resultCode}</result>
    	<resultMsg>${resultMsg}</resultMsg>
	</basicInfo>
	<prodRecords>
		<prodRecord>
			
			<#--客户信息子节点-->
			<#if prodRecords.CUST_INFO??>
			<custInfo>
			    <id>${prodRecords.CUST_INFO.party.partyId!""}</id>
			    <name>${prodRecords.CUST_INFO.party.partyName!""}</name>
			    <custunitiveNum>${prodRecords.CUST_INFO.groupCustSeq!""}</custunitiveNum>
			    <custTypeId>${prodRecords.CUST_INFO.party.partyTypeCd!""}</custTypeId>
			    <custTypeName>${prodRecords.CUST_INFO.party.partyTypeName!""}</custTypeName>
			    <industryClassCd>${prodRecords.CUST_INFO.party.industryClassCd!""}</industryClassCd>
			    <#if prodRecords.CUST_INFO.identifyMap??>
			    <indentNbr>${prodRecords.CUST_INFO.identifyMap.IDENTITY_NUM!""}</indentNbr>
			    <indentNbrType>${prodRecords.CUST_INFO.identifyMap.IDENTIDIES_TYPE_CD!""}</indentNbrType>
			    <indentNbrTypeName>${prodRecords.CUST_INFO.identifyMap.NAME!""}</indentNbrTypeName>
			    <#else>
			    <indentNbr></indentNbr>
			    <indentNbrType></indentNbrType>
			    <indentNbrTypeName></indentNbrTypeName>
			    </#if>
			   	<addr>${prodRecords.CUST_INFO.party.addressStr!""}</addr>
			   	<#if prodRecords.CUST_INFO.party.custVipInfo??>
			   		<#list prodRecords.CUST_INFO.party.custVipInfo as vip>
			   			<vipCd>${vip.gradeCode!""}</vipCd>
			   		</#list>
			   	<#else>
			   		<vipCd></vipCd>
			   	</#if>
			    <upId></upId>
			    <gimsi>${prodRecords.CUST_INFO.gimsi!""}</gimsi>
			    <servLevelId></servLevelId>
			    <servEnsureId></servEnsureId>
			    <secuGradeId></secuGradeId>
			    <remarks></remarks>
			    <isvalid>${prodRecords.CUST_INFO.isValid!""}</isvalid>
			</custInfo>
			</#if>
			
			<#--产品信息子节点-->
			<#if prodRecords.PRO_INFO??>
			<productInfo>
			    <accNbr>${prodRecords.PRO_INFO.offerProdDetail.accessNumber!""}</accNbr>
			    <ICCID>${prodRecords.PRO_INFO.ICCID!""}</ICCID>
			    <prodSpecId>${prodRecords.PRO_INFO.offerProdDetail.prodSpecId!""}</prodSpecId>
			    <#if prodRecords.PRO_INFO.ivpnInfo??>
			    	<compProdId>${prodRecords.PRO_INFO.ivpnInfo.COMPPRODID!""}</compProdId>
			    	<shortNum>${prodRecords.PRO_INFO.ivpnInfo.SHORTNUM!""}</shortNum>
			    <#else>
			    	<compProdId></compProdId>
			    	<shortNum></shortNum>
			    </#if>
			    <#if prodRecords.PRO_INFO.qryProMode??>
				    <prodSpecName>${prodRecords.PRO_INFO.qryProMode.prodSpecName!""}</prodSpecName>
				    <areaZoneNumber>010</areaZoneNumber>
				    <offers>${prodRecords.PRO_INFO.qryProMode.offers!""}</offers>
				    <prodKindId>${prodRecords.PRO_INFO.qryProMode.prodKindId!""}</prodKindId>
				    <uimCardNumber>${prodRecords.PRO_INFO.qryProMode.uimCardNumber!""}</uimCardNumber>
				    <IMSI>${prodRecords.PRO_INFO.qryProMode.imsi!""}</IMSI>
				    <LTE_IMSI>${prodRecords.PRO_INFO.qryProMode.lte_imsi!""}</LTE_IMSI>
				    <esn>${prodRecords.PRO_INFO.qryProMode.esn!""}</esn>
				    <productStatusCd>${prodRecords.PRO_INFO.qryProMode.productStatusCd!""}</productStatusCd>
				    <productStatusName>${prodRecords.PRO_INFO.qryProMode.productStatusName!""}</productStatusName>
				    <servCreateDate>${prodRecords.PRO_INFO.qryProMode.servCreateDate!""}</servCreateDate>
				    <exchCode>${prodRecords.PRO_INFO.qryProMode.exchCode!""}</exchCode>
				    <exchName>${prodRecords.PRO_INFO.qryProMode.exchName!""}</exchName>
				    <address>${prodRecords.PRO_INFO.qryProMode.address!""}</address>
				    <netAccount>${prodRecords.PRO_INFO.qryProMode.netAccount!""}</netAccount>
				<#else>
					<prodSpecName></prodSpecName>
					<areaZoneNumber>010</areaZoneNumber>
				    <offers></offers>
				    <prodKindId></prodKindId>
				    <uimCardNumber></uimCardNumber>
				    <IMSI></IMSI>
				    <LTE_IMSI></LTE_IMSI>
				    <esn></esn>
				    <productStatusCd></productStatusCd>
				    <productStatusName></productStatusName>
				    <servCreateDate></servCreateDate>
				    <exchCode></exchCode>
				    <exchName></exchName>
				    <address></address>
				    <netAccount></netAccount>
			    </#if>
			    <#--产品属性-->					
				<prodItems>
					<#if prodRecords.PRO_INFO.offerProdItems??>
						<#list prodRecords.PRO_INFO.offerProdItems.offerProdItems as item>
							<prodItem>
								<id>${item.itemSpec.itemSpecId!""}</id>
								<name>${item.itemSpec.name!""}</name>
								<value>${item.value!""}</value>
							</prodItem>
						</#list>
					</#if>
				</prodItems>
				<#--产品服务-->
				<prodServs>
					<#if prodRecords.PRO_INFO.offerServs??>
						<#list prodRecords.PRO_INFO.offerServs.offerServs as serv>
							<prodServ>
								<#if serv.servSpec??>
									<id>${serv.servSpec.servSpecId!""}</id>
									<name>${serv.servSpec.name!""}</name>
									<#if serv.startDt??>
										<startDate>${serv.startDt?string("yyyyMMdd")}</startDate>
									<#else>
										<startDate></startDate>
									</#if>
									<#if serv.endDt??>
										<endDate>${serv.endDt?string("yyyyMMdd")}</endDate>
									<#else>
										<endDate></endDate>
									</#if>
									<servParams>
										<#if serv.offerServItems??>
											<#list serv.offerServItems as servItem>
											  	<servParam>
													<id>${servItem.itemSpec.itemSpecId!""}</id>
													<name>${servItem.itemSpec.name!""}</name>
													<value>${servItem.itemSpec.value!""}</value>
												</servParam>
											</#list>
										</#if>
									</servParams>
								</#if>
							</prodServ>
						</#list>
					</#if>
				</prodServs>
				<#--组合产品-->					
				<compProds>
					<#if prodRecords.PRO_INFO.offerProdComps??>
						<#list prodRecords.PRO_INFO.offerProdComps.offerProdComps as comp>
							<compProd>
								<compProdAccNbr>${comp.compProd.accessNumber!""}</compProdAccNbr>
								<compProdId>${comp.compProd.prodId!""}</compProdId>
								<#list comp.compProd.offerProdSpecs as offerProdSpec>
									<compProdSpecId>${offerProdSpec.prodSpecId!""}</compProdSpecId>
									<compProdname>${offerProdSpec.prodSpec.name!""}</compProdname>
								</#list>
								<relaRole>${comp.prodCompRelaRoleCd!""}</relaRole>
							</compProd>
						</#list>
					<#else>
						<#if prodRecords.PRO_INFO.offerProdComps_ivpn??>
							<#list prodRecords.PRO_INFO.offerProdComps_ivpn as comp>
								<compProd>
									<compProdAccNbr>${comp.COMPPRODACCNBR!""}</compProdAccNbr>
									<compProdId>${comp.COMPPRODID!""}</compProdId>
									<compProdSpecId>${comp.COMPPRODSPECID!""}</compProdSpecId>
									<compProdname>${comp.COMPPRODNAME!""}</compProdname>
									<shortNum>${comp.SHORTNUM!""}</shortNum>
									<relaRole></relaRole>
								</compProd>
							</#list>
						</#if>
					</#if>
				</compProds>
			</productInfo>
			</#if>
			
			<#--账户信息子节点-->
			<#if prodRecords.ACC_INFO??>
			<#list prodRecords.ACC_INFO.offerProdAccounts.offerProdAccounts as opa>
			<accoutInfo>
				<#if opa.account??>
				<acctId>${opa.account.acctId!""}</acctId>
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
				<#if prodRecords.ACC_INFO.offerProdNumbers??>
					<payAccNbr>${prodRecords.ACC_INFO.offerProdNumbers.accessNumber!""}</payAccNbr>
				</#if>
				<#list prodRecords.ACC_INFO.mailsList as ml>
					<#if ml.accid = opa.acctId>
						<acctMailing>
							<mailingType>${ml.MAILINGTYPE!""}</mailingType>
							<mailAddress>${ml.MAILADDRESS!""}</mailAddress>
							<mailItem>${ml.MAILITEM!""}</mailItem>		
							<mailFormat>${ml.MAILFORMAT!""}</mailFormat>
							<mailPeriod>${ml.MAILPERIOD!""}</mailPeriod>
						</acctMailing>
					</#if>
				</#list>
				</#if>
			</accoutInfo>
			</#list>
			</#if>
			
			<#--销售品信息子节点-->
			<#if prodRecords.SALE_INFO??>
			<offerInfos>
				<#list prodRecords.SALE_INFO.offerList as ofl>
				<offerInfo>
					<id>${ofl.offerSpecId!""}</id>
					<name>${ofl.offerSpecName!""}</name>
					<statusCd>${ofl.statusCd!""}</statusCd>
					<statusName>${ofl.stateName!""}</statusName>
					<effDate>${ofl.startDt!""}</effDate>
					<expDate>${ofl.endDt!""}</expDate>
					<pricePlanTypeCd>${ofl.offerTypeCd!""}</pricePlanTypeCd>
					<#list prodRecords.SALE_INFO.offerParamMapList as offerParamMap>
						<#if offerParamMap.offerId = ofl.offerId >
							<prepayFlag>${offerParamMap.prepayFlag}</prepayFlag>
						</#if>
					</#list>
					<params>
					<#list prodRecords.SALE_INFO.offerParamMapList as offerParamMap>
					<#if offerParamMap.offerId = ofl.offerId >
						<#if offerParamMap.offerServParams ??>
							<#list offerParamMap.offerServParams as OfferServItemDto>
							<param>
					 			<id>${OfferServItemDto.itemSpecId!""}</id>
								<name>${OfferServItemDto.itemSpecName!""}</name>
								<value>${OfferServItemDto.value!""}</value>
								<valuekey></valuekey>
							</param>
							</#list>
						<#elseif offerParamMap.offerParams ??>
							<#list offerParamMap.offerParams as OfferParamDto>
							<#--过滤不用的属性-->
							<#if OfferParamDto.itemSpecId != 5030 && OfferParamDto.itemSpecId != 5031>
							<param>
					 			<id>${OfferParamDto.itemSpecId!""}</id>
								<name>${OfferParamDto.itemSpecName!""}</name>
								<value>${OfferParamDto.value!""}</value>
								<valuekey></valuekey>
							</param>
							</#if>
							</#list>
						</#if>
					</#if>
					</#list>
					</params>
				</offerInfo>
				</#list>
			</offerInfos>
			</#if>
		</prodRecord>
	</prodRecords>
</Query_response>
</@compress>