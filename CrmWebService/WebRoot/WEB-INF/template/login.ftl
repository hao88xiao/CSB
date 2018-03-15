<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<#if resultCode=='0'>
	<staffId>${staffId!""}</staffId>
	<staffNumber>${staffInfo.staffNumber!""}</staffNumber>
	<orgId>${staffInfo.orgId!""}</orgId>
	<orgName>${staffInfo.orgName!""}</orgName>
	<channelList>
	<#list channelDto as channelDto>
		<channel>
			<id>${channelDto.channelId!""}</id>
			<name>${channelDto.name!""}</name>
		</channel>
	</#list>
	</channelList>
	</#if>
</response>
</@compress>