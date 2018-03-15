<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<prodList>
	<#list data as prodByCompProdSpec>
		<product>
			<prodId>${prodByCompProdSpec.prodId!""}</prodId>
			<accessNumber>${prodByCompProdSpec.accessNumber!""}</accessNumber>
			<prodSpecId>${prodByCompProdSpec.prodSpecId!""}</prodSpecId>
			<prodSpecName>${prodByCompProdSpec.prodSpecName!""}</prodSpecName>
			<areaId>${prodByCompProdSpec.areaId!""}</areaId>
			<areaName>${prodByCompProdSpec.areaName!""}</areaName>
			<acctCd>${prodByCompProdSpec.acctCd!""}</acctCd>
		</product>
 	</#list>
	</prodList>
</response>
</@compress>