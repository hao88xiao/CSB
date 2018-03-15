<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<currentOSInfos>
	<#list continueOrderOffers.currentPPInfos as currentPPInfo>
		<currentOSInfo>
			<offerSpecId>${currentPPInfo.OFFERSPECID!""}</offerSpecId>
			<offerSpecName>${currentPPInfo.NAME!""}</offerSpecName>
			<startDate>${currentPPInfo.STARTDT!""}</startDate>
			<endDate>${currentPPInfo.ENDDT!""}</endDate>
			<chargeItemCd>${currentPPInfo.ITEMSPECID!""}</chargeItemCd>
			<chargeItemName>${currentPPInfo.ITEMSPECNAME!""}</chargeItemName>
			<chargeTotal>${currentPPInfo.DEFAULTVALUE!""}</chargeTotal>
			<effTime>${currentPPInfo.CHANGEEFFTIME!""}</effTime>
		</currentOSInfo>
	</#list>
	</currentOSInfos>
	<continueOSInfos>
	<#list continueOrderOffers.continuePPInfos as continuePPInfo>
		<OSInfo>
			<offerSpecId>${continuePPInfo.OFFERSPECID!""}</offerSpecId>
			<offerSpecName>${continuePPInfo.NAME!""}</offerSpecName>
			<chargeItemCd>${continuePPInfo.ITEMSPECID!""}</chargeItemCd>
			<chargeItemName>${continuePPInfo.ITEMSPECNAME!""}</chargeItemName>
			<chargeTotal>${continuePPInfo.DEFAULTVALUE!""}</chargeTotal>
			<effTime>${continuePPInfo.CHANGEEFFTIME!""}</effTime>
		</OSInfo>
	</#list>
	</continueOSInfos>
</response>
</@compress>