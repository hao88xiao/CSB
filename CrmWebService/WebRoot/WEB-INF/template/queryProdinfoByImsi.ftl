<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodinfo>
		<isFlag>${data.isFlag}</isFlag>
		<partyName>${data.partyName!""}</partyName>
		<#if data.mdn??>
		<mdn>${data.mdn!""}</mdn>
		<mdnimsi>${data.mdnimsi!""}</mdnimsi>
		<mdnuimcode>${data.mdnuimcode!""}</mdnuimcode>
		</#if>
		<#if data.voiceMdn??>
		<voiceMdn>${data.voiceMdn!""}</voiceMdn>
		<voiceUimImsi>${data.voiceUimImsi!""}</voiceUimImsi>
		<voiceUimCode>${data.voiceUimCode!""}</voiceUimCode>
		<wlanMdn>${data.wlanMdn!""}</wlanMdn>
		<wlanUimImsi>${data.wlanUimImsi!""}</wlanUimImsi>
		<wlanUimCode>${data.wlanUimCode!""}</wlanUimCode>
		</#if>
		<productTypeCd>${""}</productTypeCd>
	</prodinfo>
</response>
</@compress>