<@compress single_line=true>
<response>
<resultCode>${resultCode}</resultCode>
<resultMsg>${resultMsg}</resultMsg>
<phoneNumberList>
<#list resultListMap as c>
<phoneNumberInfo>
<phoneNumberId>${c.PHONE_NUMBER_ID!""}</phoneNumberId>
<phoneNumber>${c.PHONE_NUMBER!""}</phoneNumber>
<anTypeCd>${c.AN_TYPE_CD!""}</anTypeCd>
<pnLevelId>${c.PN_LEVEL_ID!""}</pnLevelId>
<pnLevelName>${c.PN_LEVEL_NAME!""}</pnLevelName>
<pnLevelDesc>${c.PN_LEVEL_DESC!""}</pnLevelDesc>
<minCharge>${c.MIN_CHARGE!""}</minCharge>
<preCharge>${c.PRE_CHARGE!""}</preCharge>
<reuseFlag>${c.REUSE_FLAG!""}</reuseFlag>
<cardCode>${c.CARD_CODE!""}</cardCode>
<imsi>${c.IMSI!""}</imsi>
<description>${c.DESCRIPTION!""}</description>
</phoneNumberInfo>
</#list>
</phoneNumberList>
</response>
</@compress>