<@compress single_line=true>
<resultinfo>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
    <#list listTaxPayer as l>
    <#if l.PARTY_PROFILE_CATG_CD==999999971>
	<taxpayerFlag>${l.PROFILE_VALUE!""}</taxpayerFlag>
	</#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999972>
    <taxpayerName>${l.PROFILE_VALUE!""}</taxpayerName>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999973>
    <taxpayerIden>${l.PROFILE_VALUE!""}</taxpayerIden>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999974>
    <taxpayerAdder>${l.PROFILE_VALUE!""}</taxpayerAdder>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999975>
    <taxpayerPho>${l.PROFILE_VALUE!""}</taxpayerPho>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999976>
    <taxpayerBank>${l.PROFILE_VALUE!""}</taxpayerBank>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999977>
    <taxpayerAcc>${l.PROFILE_VALUE!""}</taxpayerAcc>
    </#if>
	 <#if l.PARTY_PROFILE_CATG_CD==999999978>
    <taxpayerArea>${l.PROFILE_VALUE!""}</taxpayerArea>
    </#if>
	</#list>
</resultinfo>
</@compress>