<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<FNSInfo>
		<#if FNSInfo.offerSpecId??>
			<offerSpecId>${FNSInfo.offerSpecId!""}</offerSpecId>		
			<ppcount>${FNSInfo.ppcount!""}</ppcount>
			<FNSList>
			<#if FNSInfo.useFNSInfo??>
			<#list FNSInfo.useFNSInfo as useFNSInfo>
				<FNSDeviceNumber>
					<itemSpecId>${useFNSInfo.ITEM_SPEC_ID!""}</itemSpecId>
					<value>${useFNSInfo.VALUE!""}</value>
				</FNSDeviceNumber>
			</#list>
			</#if>
			<#if FNSInfo.unusedFNSInfo??>
			<#list FNSInfo.unusedFNSInfo as unusedFNSInfo>
				<FNSDeviceNumber>
					<itemSpecId>${unusedFNSInfo.ITEM_SPEC_ID!""}</itemSpecId>
					<value></value>
				</FNSDeviceNumber>
			</#list>
			</#if>
			</FNSList>
			<number>${FNSInfo.number!""}</number>
			<list>${FNSInfo.list!""}</list>
		</#if>
	</FNSInfo>
</response>
</@compress>