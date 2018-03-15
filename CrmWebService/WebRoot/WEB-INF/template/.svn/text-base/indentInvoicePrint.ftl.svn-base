<@compress single_line=true>
<response>
<resultCode>${result!""}</resultCode>
<responseId>${responseId!""}</responseId>
<responseTime>${responseTime!""}</responseTime>
<payIndentId>${payIndentId!""}</payIndentId>
<invoiceList>
<#list invoiceLists as a>
<#list a.invoiceTxt as b>
<invoiceTxt>
<itemCode>${b.itemCode!""}</itemCode>
<value>${b.value!""}</value>
</invoiceTxt>
</#list>
<#list a.invoiceItem as c>
<invoiceItem>
<item>${c.item!""}</item>
<value>${c.value!""}</value>
</invoiceItem>
</#list>
</#list>
</invoiceList>
</response>
</@compress>