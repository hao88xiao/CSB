<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<#if  statistics??>	
		<statistics> 
	    <#list statistics as inventoryStatisticsEntity>
		    <item>
				<storeId>${inventoryStatisticsEntity.storeId!""}</storeId>
				<materialId>${inventoryStatisticsEntity.materialId!""}</materialId>
				<count>${inventoryStatisticsEntity.count!""}</count>
			</item>
		
		</#list>
		</statistics>
		<#else>
		<statistics></statistics>
	</#if>
</response>
</@compress>