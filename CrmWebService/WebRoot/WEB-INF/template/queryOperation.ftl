<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodId>${prodId!""}</prodId>
	<servSpecs>
	<#list servSpecs.orderServSpecs as servSpecDto>
		<servSpec>
			<servSpecId>${servSpecDto.servSpecId!""}</servSpecId>
			<servSpecName>${servSpecDto.servSpecName!""}</servSpecName>
			<ifChoose>Y</ifChoose>
			<groupId>${servSpecDto.groupId!""}</groupId>
			<groupName>${servSpecDto.groupName!""}</groupName>
			<ifDefaultSel>${servSpecDto.ifDefaultSel!""}</ifDefaultSel>
			<ifMustSel>${servSpecDto.ifMustSel!""}</ifMustSel>
			<ifExclusive>${servSpecDto.ifExclusive!""}</ifExclusive>	
			<minsize>${servSpecDto.minsize!""}</minsize>
			<servParams>
			<#if servSpecDto.servParams??>
			<#list servSpecDto.servParams as servParam>
				<servParam>
					<id>${servParam.id!""}</id>
					<name>${servParam.name!""}</name>
					<value>${servParam.value!""}</value>
					<discreateValues>
					<#list servParam.discreateValues as discreateValue>
						<discreateValueInfo>
							<discreateValueName>${discreateValue.discreateValueName!""}</discreateValueName>
							<discreateValue>${discreateValue.discreateValue!""}</discreateValue>
						</discreateValueInfo>
					</#list>
					</discreateValues>
					<ifNullAble>${servParam.ifNullAble!""}</ifNullAble>
				</servParam>
			</#list>
			</#if>
			</servParams>					
		</servSpec>
    </#list>
    <#list servSpecs.optionServSpecs as servSpecDto>
		<servSpec>
			<servSpecId>${servSpecDto.servSpecId!""}</servSpecId>
			<servSpecName>${servSpecDto.servSpecName!""}</servSpecName>
			<ifChoose>N</ifChoose>
			<groupId>${servSpecDto.groupId!""}</groupId>
			<groupName>${servSpecDto.groupName!""}</groupName>
			<ifDefaultSel>${servSpecs.ifDefaultSel!""}</ifDefaultSel>
			<ifMustSel>${servSpecDto.ifMustSel!""}</ifMustSel>
			<ifExclusive>${servSpecDto.ifExclusive!""}</ifExclusive>
			<minsize>${servSpecDto.minsize!""}</minsize>
			<servParams>
			<#if servSpecDto.servParams??>
			<#list servSpecDto.servParams as servParam>
				<servParam>
					<id>${servParam.id!""}</id>
					<name>${servParam.name!""}</name>
					<value>${servParam.value!""}</value>
					<defalut_spec_value>${servParam.defaultSpecValue!""}</defalut_spec_value>
					<discreateValues>
					<#list servParam.discreateValues as discreateValue>
						<discreateValueInfo>
							<discreateValueName>${discreateValue.discreateValueName!""}</discreateValueName>
							<discreateValue>${discreateValue.discreateValue!""}</discreateValue>
						</discreateValueInfo>
					</#list>
					</discreateValues>
					<ifNullAble>${servParam.ifNullAble!""}</ifNullAble>
				</servParam>
			</#list>
			</#if>
			</servParams>			
		</servSpec>
    </#list>
    </servSpecs>
</response>
</@compress>