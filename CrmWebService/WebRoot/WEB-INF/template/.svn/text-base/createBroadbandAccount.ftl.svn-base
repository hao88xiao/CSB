<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<accNbrList>
		<#list resultObj as c>
		<accNbr>
			<anTypCd>${c.anTypCd!""}</anTypCd>
			<anId>${c.anId!""}</anId>
			<accessNumber>${c.accessNumber!""}</accessNumber>
			<#if c.password?? >
			<password>${c.password}</password>
			</#if>
		</accNbr>
		</#list>
	</accNbrList>
</response>
</@compress>