/**
 * BalanceTransferType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class BalanceTransferType  implements java.io.Serializable {
    private long servId;

    private java.lang.String acctNbr97;

    private int amount;

    private long manageCode;

    private java.lang.String interfaceId;

    public BalanceTransferType() {
    }

    public BalanceTransferType(
           long servId,
           java.lang.String acctNbr97,
           int amount,
           long manageCode,
           java.lang.String interfaceId) {
           this.servId = servId;
           this.acctNbr97 = acctNbr97;
           this.amount = amount;
           this.manageCode = manageCode;
           this.interfaceId = interfaceId;
    }


    /**
     * Gets the servId value for this BalanceTransferType.
     * 
     * @return servId
     */
    public long getServId() {
        return servId;
    }


    /**
     * Sets the servId value for this BalanceTransferType.
     * 
     * @param servId
     */
    public void setServId(long servId) {
        this.servId = servId;
    }


    /**
     * Gets the acctNbr97 value for this BalanceTransferType.
     * 
     * @return acctNbr97
     */
    public java.lang.String getAcctNbr97() {
        return acctNbr97;
    }


    /**
     * Sets the acctNbr97 value for this BalanceTransferType.
     * 
     * @param acctNbr97
     */
    public void setAcctNbr97(java.lang.String acctNbr97) {
        this.acctNbr97 = acctNbr97;
    }


    /**
     * Gets the amount value for this BalanceTransferType.
     * 
     * @return amount
     */
    public int getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this BalanceTransferType.
     * 
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }


    /**
     * Gets the manageCode value for this BalanceTransferType.
     * 
     * @return manageCode
     */
    public long getManageCode() {
        return manageCode;
    }


    /**
     * Sets the manageCode value for this BalanceTransferType.
     * 
     * @param manageCode
     */
    public void setManageCode(long manageCode) {
        this.manageCode = manageCode;
    }


    /**
     * Gets the interfaceId value for this BalanceTransferType.
     * 
     * @return interfaceId
     */
    public java.lang.String getInterfaceId() {
        return interfaceId;
    }


    /**
     * Sets the interfaceId value for this BalanceTransferType.
     * 
     * @param interfaceId
     */
    public void setInterfaceId(java.lang.String interfaceId) {
        this.interfaceId = interfaceId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BalanceTransferType)) return false;
        BalanceTransferType other = (BalanceTransferType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.servId == other.getServId() &&
            ((this.acctNbr97==null && other.getAcctNbr97()==null) || 
             (this.acctNbr97!=null &&
              this.acctNbr97.equals(other.getAcctNbr97()))) &&
            this.amount == other.getAmount() &&
            this.manageCode == other.getManageCode() &&
            ((this.interfaceId==null && other.getInterfaceId()==null) || 
             (this.interfaceId!=null &&
              this.interfaceId.equals(other.getInterfaceId())));
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
        _hashCode += new Long(getServId()).hashCode();
        if (getAcctNbr97() != null) {
            _hashCode += getAcctNbr97().hashCode();
        }
        _hashCode += getAmount();
        _hashCode += new Long(getManageCode()).hashCode();
        if (getInterfaceId() != null) {
            _hashCode += getInterfaceId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BalanceTransferType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "BalanceTransferType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctNbr97");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctNbr97"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "manageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interfaceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interfaceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
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
