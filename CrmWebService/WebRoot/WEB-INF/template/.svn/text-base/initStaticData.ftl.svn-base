<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<datas>
	<#list data as StaticData>
		<data>
			<sqlId>${StaticData.sqlId!""}</sqlId>
			<#list StaticData.ele as ele>
			<ele>
				<key>${ele.C1!""}</key>
				<value>${ele.C2!""}</value>
			</ele>
			</#list>
		</data>
	</#list>
	</datas>
</response>
</@compress>