<@compress single_line=true>
<response>
	<resultCode>${resultCode!""}</resultCode>
    <resultMsg>${resultMsg!""}</resultMsg>
    <tableSpaceInfos>
    <#list lists as c>
		<tableSpaceInfo>
			<SEGMENT_NAME>${c.SEGMENT_NAME!""}</SEGMENT_NAME>
			<#if c.TABLESPACE_NAME??>
			<TABLESPACE_NAME>${c.TABLESPACE_NAME!""}</TABLESPACE_NAME>
			</#if>
			<#if c.TABLE_NAME??>
			<TABLE_NAME>${c.TABLE_NAME!""}</TABLE_NAME>
			</#if>
			<USERD>${c.USERD!""}</USERD>
		</tableSpaceInfo>
	</#list>
	</tableSpaceInfos>
</response>
</@compress>