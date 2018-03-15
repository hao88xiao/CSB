<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodInfo>
		<partyName>${data.partyName!""}</partyName>
		<mdn>${data.mdn!""}</mdn>
		<mdnimsi>${data.mdnimsi!""}</mdnimsi>
		<mdnuimcode>${data.mdnuimcode!""}</mdnuimcode>
		<#if data.voiceUimImsi??>
		<voiceMdn>${data.voiceMdn!""}</voiceMdn>
		<voiceUimImsi>${data.voiceUimImsi!""}</voiceUimImsi>
		<voiceUimCode>${data.voiceUimCode!""}</voiceUimCode>
		<wlanMdn>${data.wlanMdn!""}</wlanMdn>
		<wlanUimImsi>${data.wlanUimImsi!""}</wlanUimImsi>
		<wlanUimCode>${data.wlanUimCode!""}</wlanUimCode>
		</#if>
	</prodInfo>
</response>
</@compress>