<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
<listChargeInfo>
<#list listInfo as CC >
<chargeInfo>
<offerName>${CC.OFFER_NAME!""}</offerName>
<charge>${CC.CHARGE!""}</charge>
<acctItemTypeName>${CC.ACCT_ITEM_TYPE_NAME!""}</acctItemTypeName>
<acctItemTypeId>${CC.ACCT_ITEM_TYPE_ID!""}</acctItemTypeId>
<appCharge>${CC.APP_CHARGE!""}</appCharge>
<specId>${CC.SPEC_ID!""}</specId>
</chargeInfo>
</#list>
</listChargeInfo>
</response>
</@compress>