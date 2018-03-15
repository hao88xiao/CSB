<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<coNbrList>
	<#list coNbrList as CH>
	<coNbr>${CH.coNbr}</coNbr>
	</#list>
	</coNbrList>
</response>
</@compress>