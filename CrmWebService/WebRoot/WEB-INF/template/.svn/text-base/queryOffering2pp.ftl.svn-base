<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	
	<compProds>
	<#list compProds as name>
		<compProdName>
			${name!""}
		</compProdName>
	</#list>
	</compProds>
	<orderOffer>
		<#list orderOfferList as OfferIntf>
		<offer>
			<offerId>${OfferIntf.offerId!""}</offerId>
			<offerSpecId>${OfferIntf.offerSpecId!""}</offerSpecId>
			<offerSpecName>${OfferIntf.offerSpecName!""}</offerSpecName>
			<#if OfferIntf.startDt??>
			<startDt>${OfferIntf.startDt?datetime}</startDt>
			<#else>
			<startDt></startDt>
			</#if>
		    <#if OfferIntf.endDt??>
			<endDt>${OfferIntf.endDt?datetime}</endDt>
			<#else>
			<endDt></endDt>
			</#if>
			<#--销售品属性-->
			<params>
			<#list offerParamsList as OPL>
			<#if OPL.offerParams ??>
				<#if OPL.offerId = OfferIntf.offerId>
				<#list OPL.offerParams as OfferParamDto>
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
			<summary>${OfferIntf.summary!""}</summary>
		</offer>
		</#list>
	</orderOffer>
	<saleOffer>
		<#list saleOfferList as offers>
		<categoryNode id = "${offers.nodeId!""}">
			<#list offers.offerList as OfferSpecShopDto>
			<offer>
		  		<id>${OfferSpecShopDto.offerSpecId!""}</id>
		  		<name>${OfferSpecShopDto.offerSpecName!""}</name>
		 	</offer>
			</#list>
		</categoryNode>		
		</#list>		
	</saleOffer>
</response>
</@compress>