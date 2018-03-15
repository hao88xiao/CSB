<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<orderPaks>
		<#list offerInfo.orderOffers as offerInfoKF>
		<orderPak>
		    <id>${offerInfoKF.offerSpecId!""}</id>			
		    <name>${offerInfoKF.offerSpecName!""}</name>
		    <desc>${offerInfoKF.summary!""}</desc>
		    <#if offerInfoKF.startDt??>
			<startDt>${offerInfoKF.startDt?datetime}</startDt>
			<#else>
			<startDt></startDt>
			</#if>
		    <#if offerInfoKF.endDt??>
			<endDt>${offerInfoKF.endDt?datetime}</endDt>
			<#else>
			<endDt></endDt>
			</#if>
			<effectFlag>${offerInfo.effectFlag!""}</effectFlag>
			<labelId>${offerInfoKF.labelId!""}</labelId>
			<labelName>${offerInfoKF.labelName!""}</labelName>
			<minSelect>${offerInfoKF.minNumber!""}</minSelect>
			<maxSelect>${offerInfoKF.maxNumber!""}</maxSelect>
			<ifMustSel>${offerInfoKF.ifMustSel!""}</ifMustSel>
		    <properties>
		    <#--附属销售品属性-->	
		    <#list offerInfoKF.offerParams as offerParam>
		    	<property>
		 			<id>${offerParam.itemSpecId!""}</id>
		 			<ifNullAble>${offerParam.ifNullAble!""}</ifNullAble>
					<notModify>${offerParam.notModify!""}</notModify>
					<name>${offerParam.itemSpecName!""}</name>
					<dataType>${offerParam.dataTypeCd!""}</dataType>
					<unitCd>${offerParam.unitCd!""}</unitCd>
					<value>${offerParam.value!""}</value>
					<discreateValues>
					<#list offerParam.discreateValues as discreateValue>
						<discreteValue>
							<id>${discreateValue.discreateValue!""}</id>
							<name>${discreateValue.discreateValueName!""}</name>
							<value>${discreateValue.discreateValue!""}</value>
						</discreteValue>
					</#list>
					</discreateValues>
				</property>
			</#list>
			</properties>
	    </orderPak>
		</#list>
	</orderPaks>  
    <optionPaks>
    <#list offerInfo.optionOfferSpecs as offerInfoKF>
		<optionPak>
			<id>${offerInfoKF.offerSpecId!""}</id>			
			<name>${offerInfoKF.offerSpecName!""}</name>
			<desc>${offerInfoKF.summary!""}</desc>
			<effectFlag>${offerInfo.effectFlag!""}</effectFlag>
			<labelId>${offerInfoKF.labelId!""}</labelId>
			<labelName>${offerInfoKF.labelName!""}</labelName>
			<minSelect>${offerInfoKF.minNumber!""}</minSelect>
			<maxSelect>${offerInfoKF.maxNumber!""}</maxSelect>
			<properties>
		    <#--附属销售品属性-->	
		    <#list offerInfoKF.offerParams as offerParam>
		    	<property>
		 			<id>${offerParam.itemSpecId!""}</id>
		 			<ifNullAble>${offerParam.ifNullAble!""}</ifNullAble>
					<notModify>${offerParam.notModify!""}</notModify>
					<name>${offerParam.itemSpecName!""}</name>
					<dataType>${offerParam.dataTypeCd!""}</dataType>
					<unitCd>${offerParam.unitCd!""}</unitCd>
					<value>${offerParam.defaultValue!""}</value>
					<discreteValueList>
					<#list offerParam.discreateValues as discreateValue>
						<discreteValue>
							<id>${discreateValue.discreateValue!""}</id>
							<name>${discreateValue.discreateValueName!""}</name>
							<value>${discreateValue.discreateValue!""}</value>
						</discreteValue>
					</#list>
					</discreteValueList>
				</property>
			</#list>
			</properties>
			<depend>
			<#if offerInfoKF.offerTypeCd=="2">
			<#list offerInfoKF.servSpecs as servSpecDto>
				<servSpec>
					<servSpecId>${servSpecDto.servSpecId!""}</servSpecId>
		 			<servSpecName>${servSpecDto.servSpecName!""}</servSpecName>
					<ifMustSelect>${servSpecDto.ifMustSel!""}</ifMustSelect>
				</servSpec>
			</#list>
			</#if>
			</depend>
		</optionPak>
    </#list>
    </optionPaks>
</response>
</@compress>