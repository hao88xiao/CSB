<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<#if  bcdCodes??>	
		<bcdCodes> 
	    <#list bcdCodes as bcdCodeEntity>
		    <bcdCode>
				${bcdCodeEntity.bcdCode!""}
			</bcdCode>
		
		</#list>
		</bcdCodes>
		<#else>
		<bcdCodes></bcdCodes>
	</#if>
</response>
</@compress>