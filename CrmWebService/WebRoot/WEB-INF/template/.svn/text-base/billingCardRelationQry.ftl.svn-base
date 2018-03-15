<@compress single_line=true>
<response>
	<resultCode>${resultCode!""}</resultCode>
    <resultMsg>${resultMsg!""}</resultMsg>
    <IMSI_LTE>${IMSI_LTE!""}</IMSI_LTE>
    <IMSI_CDMA>${IMSI_CDMA!""}</IMSI_CDMA>
    <IMSI_CTOG>${IMSI_CTOG!""}</IMSI_CTOG>
    <MSISDN>${MSISDN!""}</MSISDN>
    <#list cardRelation as c>
		<RELA_Info>
			<MAIN_MSISDN>${c.MAIN_MSISDN!""}</MAIN_MSISDN>
			<SUB_MSISDN>${c.SUB_MSISDN!""}</SUB_MSISDN>
			<RELA_Type>1</RELA_Type>
		</RELA_Info>
	</#list>
</response>
</@compress>