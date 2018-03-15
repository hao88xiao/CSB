<@compress single_line=true>
<response>
	<resultCode>${resultCode!""}</resultCode>
    <resultMsg>${resultMsg!""}</resultMsg>
    <tableSpaceInfos>
    <#list lists as c>
		<tableSpaceInfo>
			<TABLESPACE>${c.TABLESPACE!""}</TABLESPACE>
			<MEGS_ALLOC>${c.MEGS_ALLOC!""}</MEGS_ALLOC>
			<PCT>${c.PCT!""}</PCT>
		</tableSpaceInfo>
	</#list>
	</tableSpaceInfos>
</response>
</@compress>