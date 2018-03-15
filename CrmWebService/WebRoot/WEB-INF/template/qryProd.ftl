<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<productlist>
	<#list data as CommonOfferProdDto>
		<product>
			<prodId>${CommonOfferProdDto.prodId!""}</prodId>
			<prodSpecId>${CommonOfferProdDto.prodSpecId!""}</prodSpecId>
			<prodSpecName>${CommonOfferProdDto.prodSpecName!""}</prodSpecName>
			<accessNumber>${CommonOfferProdDto.accessNumber!""}</accessNumber>
			<compFlag>${CommonOfferProdDto.compFlag!""}</compFlag>
		</product>
	</#list>
	</productlist>
</response>
</@compress>