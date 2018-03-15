<@compress single_line=true>
<response>
	<resultCode>${resultCode!""}</resultCode>
    <resultMsg>${resultMsg!""}</resultMsg>
    <CrmLockInfos>
    <#list lists as c>
		<CrmLockInfo>
			<SID>${c.SID!""}</SID>
			<SERIAL>${c.SERIAL!""}</SERIAL>
			<ORACLE_USERNAME>${c.ORACLE_USERNAME!""}</ORACLE_USERNAME>
			<OS_USER_NAME>${c.OS_USER_NAME!""}</OS_USER_NAME>
			<OBJECT_NAME>${c.OBJECT_NAME!""}</OBJECT_NAME>
			<LOGON_TIME>${c.LOGON_TIME!""}</LOGON_TIME>
			<LOCKED_MODE>${c.LOCKED_MODE!""}</LOCKED_MODE>
		</CrmLockInfo>
	</#list>
	</CrmLockInfos>
</response>
</@compress>