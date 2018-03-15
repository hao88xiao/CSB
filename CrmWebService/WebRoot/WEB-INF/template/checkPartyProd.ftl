<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<number>${data!""}</number>
	<#if party.partyTypeCd==1>
	<partyType>个人客户</partyType>
	<#else>
	<partyType>非个人客户</partyType>
	</#if>	
</response>
</@compress>