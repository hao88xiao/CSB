<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<offers>
	<#list offers as offers>
	<categoryNode id = "${offers.nodeId!""}">
		<#list offers.offerList as OfferSpecShopDto>
		<offer>
	  		<id>${OfferSpecShopDto.offerSpecId!""}</id>
	  		<name>${OfferSpecShopDto.offerSpecName!""}</name>
	  		<summary>${OfferSpecShopDto.summary!""}</summary>
	 	</offer>
		</#list>
	</categoryNode>		
	</#list>
	</offers>
</response>
</@compress>