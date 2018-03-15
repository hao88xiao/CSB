<@compress single_line=true>
<response>
<resultCode>${resultCode!""}</resultCode>
<resultMsg>${resultMsg!""}</resultMsg>
<password>${password!""}</password>
<#if olNbr??>
<olNbr>${olNbr!""}</olNbr>
</#if>
<#if olId??>
<olId>${olId!""}</olId>
</#if>
<#if payIndentId??>
<payIndentId>${payIndentId!""}</payIndentId>
</#if>
<#if pageInfo??>
<pageInfo><![CDATA[${pageInfo!""}]]></pageInfo>
</#if>
</response>
</@compress>