<@compress single_line=true>
<response>
<resultCode>${resultCode}</resultCode>
<resultMsg>${resultMsg}</resultMsg>
<ComponentListInfo>
	<#list resultObj.saComponentListInfo  as c>
	<ComponentInfo>
		<buildingId>${c.buildingId!""}</buildingId>
		<buildingType>${c.buildingType!""}</buildingType>
		<createDt>${c.createDt?datetime!""}</createDt>
		<linkMan>${c.linkMan!""}</linkMan>
		<linkNbr>${c.linkNbr!""}</linkNbr>
		<linkNbr1>${c.linkNbr1!""}</linkNbr1>
		<managerId>${c.managerId!""}</managerId>
		<managerName>${c.managerName!""}</managerName>
		<prodId>${c.prodId!""}</prodId>
		<serviceId>${c.serviceId!""}</serviceId>
		<serviceName>${c.serviceName!""}</serviceName>
		<servicePartyId>${c.servicePartyId!""}</servicePartyId>
		<servicePartyName>${c.servicePartyName!""}</servicePartyName>
		<serviceStateCd>${c.serviceStateCd!""}</serviceStateCd>
		<serviceTypeId>${c.serviceTypeId!""}</serviceTypeId>
		<staffName>${c.staffName!""}</staffName>
		<#if c.version?? >
		<version>${c.version?datetime}</version>
		</#if>
	</ComponentInfo>
	</#list>
</ComponentListInfo>
</response>
</@compress>

