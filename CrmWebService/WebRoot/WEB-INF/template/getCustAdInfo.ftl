<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<accNbrList>
		<accNbr>
			<anTypCd>${anTypeCdMain!""}</anTypCd>
			<anId>${anId!""}</anId>
			<accessNumber>${lanAccount!""}</accessNumber>
		</accNbr>
		<accNbr>
			<anTypCd>${anTypeCdNotMain!""}</anTypCd>
			<anId>${anId!""}</anId>
			<accessNumber>${loginName!""}</accessNumber>
			<password>${password!""}</password>
		</accNbr>
	</accNbrList>
</response>
</@compress>
