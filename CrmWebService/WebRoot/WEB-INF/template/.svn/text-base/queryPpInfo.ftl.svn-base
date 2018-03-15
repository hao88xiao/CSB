<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<orderOffers>
	<#if (attachOfferInfo.attachOffersByofferSpec?size>0)> 
		<#list attachOfferInfo.attachOffersByofferSpec as AttachOfferDto>
		<offerSpec>
		    <id>${AttachOfferDto.OFFERSPECID!""}</id>			
		    <name>${AttachOfferDto.OFFERSPECNAME!""}</name>
		</offerSpec>
		</#list>
	<#else>
		<#list attachOfferInfo.attachOffers as AttachOffer>
		<offer>
		    <id>${AttachOffer.offerSpecId!""}</id>			
		    <name>${AttachOffer.offerSpecName!""}</name>
		    <#list AttachOffer.offerMembers as offerMembers>
		    <#if offerMembers.startDt??>
			<startDt>${offerMembers.startDt?datetime}</startDt>
			<#else>
			<startDt></startDt>
			</#if>
		    <#if offerMembers.endDt??>
			<endDt>${offerMembers.endDt?datetime}</endDt>
			<#else>
			<endDt></endDt>
			</#if>
		    </#list>
		    <params>
		    <#--附属销售品属性-->	
			<#list attachOfferInfo.attachOfferParamsList as AOPL>
			<#if AOPL.offerParams??>
				<#if AOPL.offerId = AttachOffer.offerId>
					<#if AOPL.offerSpecFlag = "SERV">
				<#list AOPL.offerServParams as OfferServItemDto>
				<param>
		 			<servId>${OfferServItemDto.servId!""}</servId>
		 			<itemSpecId>${OfferServItemDto.itemSpecId!""}</itemSpecId>
					<itemSpecName>${OfferServItemDto.itemSpecName!""}</itemSpecName>
					<value>${OfferServItemDto.value!""}</value>
				</param>
				</#list>
					<#else>
				<#list AOPL.offerParams as OfferParamDto>
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
			</#if>
			</#list>
			</params>
	    </offer>
		</#list>
	</#if>
	</orderOffers>  
    <optionOfferSpecs>
    <#list attachOfferInfo.attachOfferSpec as AttachOfferSpecDto>
		<label id="${AttachOfferSpecDto.labelId!""}" name="${AttachOfferSpecDto.labelName?replace("\"","\'")!""}">
		<#list AttachOfferSpecDto.offerSpecList as offerSpecList>
			<offerSpec>
				<id>${offerSpecList.offerSpecId!""}</id>			
				<name>${offerSpecList.offerSpecName!""}</name>
			</offerSpec>
		</#list>
		</label>
    </#list>
    </optionOfferSpecs>
</response>
</@compress>