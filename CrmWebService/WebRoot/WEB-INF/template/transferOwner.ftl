<@compress single_line=true>
<response>
<resultCode>${resultCode!""}</resultCode>
<resultMsg>${resultMsg!""}</resultMsg>
<olNbr>${olNbr!""}</olNbr>
<olId>${olId!""}</olId>
<resultCode2>${resultCode2!""}</resultCode2>
<resultMsg2>${resultMsg2!""}</resultMsg2>
<#if pageInfo??>
<pageInfo><![CDATA[${pageInfo!""}]]></pageInfo>
</#if>
</response>
</@compress>