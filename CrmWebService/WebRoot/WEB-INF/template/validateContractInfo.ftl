<@compress single_line=true>
<response>
<resultCode>${resultCode}</resultCode>
<resultMsg>${resultMsg}</resultMsg>
<zjInfoList>
<#list zjInfoList as A>
<zjInfo>
<offerId>${A.OFFER_ID!""}</offerId>
<offerSpecId>${A.OFFER_SPEC_ID!""}</offerSpecId>
<name>${A.NAME!""}</name>
<createDt>${A.CREATE_DT!""}</createDt>
<startDt>${A.STARTDT!""}</startDt>
<endDt>${A.END_DT!""}<endDt>
</zjInfo>
</#list>
</zjInfoList>
</response>
</@compress>