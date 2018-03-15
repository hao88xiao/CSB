<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<custList>
	<#list partyInfoList as data>
		<partyInfo>
			<partyId>${data.basic.partyId!""}</partyId>
			<partyName>${data.basic.partyName!""}</partyName>
			<partyTypeCd>${data.basic.partyTypeCd!""}</partyTypeCd>
			<partyTypeName>${data.basic.partyTypeName!""}</partyTypeName>
			<address>${data.basic.mailAddress!""}</address>			
			<certAddress>${data.basic.mailAddressStr!""}</certAddress>
			<areaName>${data.basic.areaName!""}</areaName>
			<#if data.identify??>
			<identifyType>${data.identify.IDENTIDIES_TYPE_CD!""}</identifyType>
			<identityTypeName>${data.identify.NAME!""}</identityTypeName>
			<identityNum>${data.identify.IDENTITY_NUM!""}</identityNum>
			<#else>
			<identifyType></identifyType>
			<identityTypeName></identityTypeName>
			<identityNum></identityNum>
			</#if>
			<#if data.profiles??>
			<contactName>${data.profiles.CONTACT_NAME!""}</contactName>
			<contactAddress>${data.profiles.CONTACT_ADDRESS!""}</contactAddress>
			<contactPhone>${data.profiles.TELEPHONE!""}</contactPhone>
			<contactMobile>${data.profiles.MOBILE!""}</contactMobile>
			<emailAddress>${data.profiles.EMAIL_ADDRESS!""}</emailAddress>
			<#else>
			<contactName></contactName>
			<contactPhone></contactPhone>
			<contactMobile></contactMobile>
			<emailAddress></emailAddress>
			</#if>
			<postCode>${data.postCode!""}</postCode>
			<#--客户经理信息-->
			<managerinfo>
			<#if data.highFeeInfo??>
				<managercode>${data.highFeeInfo.managercode!""}</managercode>
				<managername>${data.highFeeInfo.managername!""}</managername>
				<managertel>${data.highFeeInfo.managertel!""}</managertel>
			<#else>
				<managercode></managercode>
				<managername></managername>
				<managertel></managertel>
			</#if>
			</managerinfo>
			<custGrade>${data.grade!""}</custGrade>
		</partyInfo>
	</#list>
	</custList>
</response>
</@compress>