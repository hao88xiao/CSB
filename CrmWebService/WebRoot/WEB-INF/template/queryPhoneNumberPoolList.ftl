<@compress single_line=true>
<response>
<resultCode>${resultCode}</resultCode>
<resultMsg>${resultMsg}</resultMsg>
<poolInfos>
<#list resultListMap as c>
	<poolInfo>
		<poolId>${c.POOL_ID!""}</poolId>
		<name>${c.NAME!""}</name>
		<description>${c.DESCRIPTION!""}</description>
	</poolInfo>
</#list>
</poolInfos>
</response>
</@compress>