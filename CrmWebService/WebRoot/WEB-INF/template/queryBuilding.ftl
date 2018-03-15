<@compress single_line=true>
<response>
	<resultCode>${resultCode!""}</resultCode>
	<resultMsg>${resultMsg!""}</resultMsg>
	<buildingList>
	<#list buildingListInfo as a>
		<building>
			<building_id>${a.id!""}</building_id>
			<code>${a.code!""}</code>
			<name>${a.name!""}</name>
			<address>${a.address!""}</address>
			<scale>${a.scale!""}</scale>
			<region_name>${a.region_name!""}</region_name>
			<rank_name>${a.rank!""}</rank_name>
			<type_name>${a.buildingType!""}</type_name>
			<domain_name>${a.domain!""}</domain_name>
			<creator_name>${a.creator!""}</creator_name>
			<status_name>${a.status!""}</status_name>
			<create_date>${a.createDate!""}</create_date>
		</building>
	</#list>
	</buildingList>
</response>
</@compress>