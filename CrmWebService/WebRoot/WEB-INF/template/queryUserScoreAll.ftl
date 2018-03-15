<@compress single_line=true>
<response>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<partyPointInfo>
	<#list partyPointConsume as partyPoint>
		<#if partyPoint.consumeTypeString?contains("����")>
			<PartyPointConsumeCenter>
				 <id>${partyPoint.id!""}</id>
				<consumeType>${partyPoint.consumeType!""}</consumeType>
				<pointUse>${partyPoint.pointUse!""}</pointUse>
				<pointState>${partyPoint.pointState!""}</pointState>
				<stateDate>${partyPoint.stateDate?datetime}</stateDate>
		        <staff_id>${partyPoint.staff_id!""}</staff_id>
				<staffCode>${partyPoint.staffCode!""}</staffCode>
				<olId>${partyPoint.olId!""}</olId>
				<relaId>${partyPoint.relaId!""}</relaId>
				<pptId>${partyPoint.pptId!""}</pptId>
				<transactionId>${partyPoint.transactionId!""}</transactionId>
				<srcOrgId>${partyPoint.srcOrgId!""}</srcOrgId>
				<srcSysId>${partyPoint.srcSysId!""}</srcSysId>
		        <orderNbr>${partyPoint.orderNbr!""}</orderNbr>
				<tally_src>${partyPoint.tally_src!""}</tally_src>
				<tallySrcString>${partyPoint.tallySrcString!""}</tallySrcString>
				<action>${partyPoint.action!""}</action>
				<actionString>${partyPoint.actionString!""}</actionString>
				<svcPlace>${partyPoint.svcPlace!""}</svcPlace>
				<beenRefunded>${partyPoint.beenRefunded!""}</beenRefunded>
		 </PartyPointConsumeCenter>
		 <#else>
		 <PartyPointConsumeCenter/>
			</#if>
        <#if partyPoint.consumeTypeString?contains("ʵ��")>		
         <PartyPointConsumeLocal>
			    <id>${partyPoint.id!""}</id>
				<consumeType>${partyPoint.consumeType!""}</consumeType>
				<consumeTypeString>${partyPoint.consumeTypeString!""}</consumeTypeString>
				<pointUse>${partyPoint.pointUse!""}</pointUse>
				<pointState>${partyPoint.pointState!""}</pointState>
				<pointStateString>${partyPoint.pointStateString!""}</pointStateString>
				<stateDate>${partyPoint.stateDate?datetime}</stateDate>
		        <staff_id>${partyPoint.staff_id!""}</staff_id>
				<staffCode>${partyPoint.staffCode!""}</staffCode>
				<olId>${partyPoint.olId!""}</olId>
				<relaId>${partyPoint.relaId!""}</relaId>
				<partyPointFeeId>${partyPoint.partyPointFeeId!""}</partyPointFeeId>
				<feeState>${partyPoint.feeState!""}</feeState>
				<feeStateString>${partyPoint.feeState!""}</feeStateString>
				<beenRefunded>${partyPoint.beenRefunded!""}</beenRefunded>
		</PartyPointConsumeLocal>
		 <#else>
		 <PartyPointConsumeLocal/>
		</#if>
			 <#if partyPoint.consumeTypeString?contains("����")>		
			<PartyPointConsumeOs>
				<id>${partyPoint.id!""}</id>
				<consumeType>${partyPoint.consumeType!""}</consumeType>
				<consumeTypeString>${partyPoint.consumeTypeString!""}</consumeTypeString>
				<pointUse>${partyPoint.pointUse!""}</pointUse>
				<pointState>${partyPoint.pointState!""}</pointState>
				<pointStateString>${partyPoint.pointStateString!""}</pointStateString>
				<stateDate>${partyPoint.stateDate?datetime}</stateDate>
		        <staff_id>${partyPoint.staff_id!""}</staff_id>
				<staffCode>${partyPoint.staffCode!""}</staffCode>
				<olId>${partyPoint.olId!""}</olId>
				<relaId>${partyPoint.relaId!""}</relaId>
				<beenRefunded>${partyPoint.beenRefunded!""}</beenRefunded>
		</PartyPointConsumeOs>
		 <#else>
		 <PartyPointConsumeOs/>
		</#if>
	
	</#list>
	</partyPointInfo>
</response>
</@compress>