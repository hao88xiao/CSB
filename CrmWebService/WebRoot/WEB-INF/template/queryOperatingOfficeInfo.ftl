<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<channels>
	<#list data as OperatingOfficeInfo>
		<channel>
	  		<id>${OperatingOfficeInfo.channelId!""}</id>
	  		<name>${OperatingOfficeInfo.name!""}</name>
	  		<openTime>${OperatingOfficeInfo.openTime!""}</openTime>
	  		<closeTime>${OperatingOfficeInfo.closeTime!""}</closeTime>
	 	</channel>
	</#list>
	</channels>
</response>
</@compress>