/**
 * InvoiceListType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class InvoiceListType  implements java.io.Serializable {
    private com.linkage.bss.crm.ws.csb.InvoiceItemType[] invoiceItem;

    private com.linkage.bss.crm.ws.csb.InvoiceTxtType[] invoiceTxt;

    public InvoiceListType() {
    }

    public InvoiceListType(
           com.linkage.bss.crm.ws.csb.InvoiceItemType[] invoiceItem,
           com.linkage.bss.crm.ws.csb.InvoiceTxtType[] invoiceTxt) {
           this.invoiceItem = invoiceItem;
           this.invoiceTxt = invoiceTxt;
    }


    /**
     * Gets the invoiceItem value for this InvoiceListType.
     * 
     * @return invoiceItem
     */
    public com.linkage.bss.crm.ws.csb.InvoiceItemType[] getInvoiceItem() {
        return invoiceItem;
    }


    /**
     * Sets the invoiceItem value for this InvoiceListType.
     * 
     * @param invoiceItem
     */
    public void setInvoiceItem(com.linkage.bss.crm.ws.csb.InvoiceItemType[] invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public com.linkage.bss.crm.ws.csb.InvoiceItemType getInvoiceItem(int i) {
        return this.invoiceItem[i];
    }

    public void setInvoiceItem(int i, com.linkage.bss.crm.ws.csb.InvoiceItemType _value) {
        this.invoiceItem[i] = _value;
    }


    /**
     * Gets the invoiceTxt value for this InvoiceListType.
     * 
     * @return invoiceTxt
     */
    public com.linkage.bss.crm.ws.csb.InvoiceTxtType[] getInvoiceTxt() {
        return invoiceTxt;
    }


    /**
     * Sets the invoiceTxt value for this InvoiceListType.
     * 
     * @param invoiceTxt
     */
    public void setInvoiceTxt(com.linkage.bss.crm.ws.csb.InvoiceTxtType[] invoiceTxt) {
        this.invoiceTxt = invoiceTxt;
    }

    public com.linkage.bss.crm.ws.csb.InvoiceTxtType getInvoiceTxt(int i) {
        return this.invoiceTxt[i];
    }

    public void setInvoiceTxt(int i, com.linkage.bss.crm.ws.csb.InvoiceTxtType _value) {
        this.invoiceTxt[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvoiceListType)) return false;
        InvoiceListType other = (InvoiceListType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.invoiceItem==null && other.getInvoiceItem()==null) || 
             (this.invoiceItem!=null &&
              java.util.Arrays.equals(this.invoiceItem, other.getInvoiceItem()))) &&
            ((this.invoiceTxt==null && other.getInvoiceTxt()==null) || 
             (this.invoiceTxt!=null &&
              java.util.Arrays.equals(this.invoiceTxt, other.getInvoiceTxt())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getInvoiceItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInvoiceItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInvoiceItem(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInvoiceTxt() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInvoiceTxt());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInvoiceTxt(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvoiceListType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UnityPay.huawei.com", "InvoiceListType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoiceItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invoiceItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://huawei.com/UnityPay/", "InvoiceItemType"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoiceTxt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invoiceTxt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://huawei.com/UnityPay/", "invoiceTxtType"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
