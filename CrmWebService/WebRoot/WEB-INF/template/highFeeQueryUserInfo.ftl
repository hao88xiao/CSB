<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<info>
		<partyid>${prodInfo.partyId!""}</partyid>
		<prodspecid>${prodInfo.prodSpecId!""}</prodspecid>
		<acc_nbr>${prodInfo.accessNumber!""}</acc_nbr>
		<start_dt>${prodInfo.startDt?datetime!""}</start_dt>
		<status>${highFeeInfo.status!""}</status>
		<partyname>${highFeeInfo.partyname!""}</partyname>
		<acctcd>${highFeeInfo.acctcd!""}</acctcd>
<#--客户经理信息-->
		<managerinfo>
			<managercode>${highFeeInfo.managercode!""}</managercode>
			<managername>${highFeeInfo.managername!""}</managername>
			<managertel>${highFeeInfo.managertel!""}</managertel>
		</managerinfo>
<#--发展人信息-->
		<devmaninfo>
			<devmancode>${highFeeInfo.devmancode!""}</devmancode>
			<devmanname>${highFeeInfo.devmanname!""}</devmanname>
			<devmantel>${highFeeInfo.devmantel!""}</devmantel>
			<devdepartid>${highFeeInfo.devdepartid!""}</devdepartid>
			<devdepartname>${highFeeInfo.devdepartname!""}</devdepartname>
		</devmaninfo>
<#--帐户经理信息-->
		<acctinfo>
			<acctmanagercode>${highFeeInfo.acctmanagercode!""}</acctmanagercode>
			<acctmanagername>${highFeeInfo.acctmanagername!""}</acctmanagername>
			<acctmanagertel>${highFeeInfo.acctmanagertel!""}</acctmanagertel>
		</acctinfo>
<#--维系经理信息-->
		<wxinfo>
			<wxName>${highFeeInfo.wxName!""}</wxName>
			<wxTel>${highFeeInfo.wxTel!""}</wxTel>
		</wxinfo>
	</info>
</response>
</@compress>