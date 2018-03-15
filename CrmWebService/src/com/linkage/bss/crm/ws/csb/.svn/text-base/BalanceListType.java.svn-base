/**
 * BalanceListType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class BalanceListType  implements java.io.Serializable {
    private int balanceTypeId;

    private int balanceFlag;

    private long balance;

    private long usedBalance;

    public BalanceListType() {
    }

    public BalanceListType(
           int balanceTypeId,
           int balanceFlag,
           long balance,
           long usedBalance) {
           this.balanceTypeId = balanceTypeId;
           this.balanceFlag = balanceFlag;
           this.balance = balance;
           this.usedBalance = usedBalance;
    }


    /**
     * Gets the balanceTypeId value for this BalanceListType.
     * 
     * @return balanceTypeId
     */
    public int getBalanceTypeId() {
        return balanceTypeId;
    }


    /**
     * Sets the balanceTypeId value for this BalanceListType.
     * 
     * @param balanceTypeId
     */
    public void setBalanceTypeId(int balanceTypeId) {
        this.balanceTypeId = balanceTypeId;
    }


    /**
     * Gets the balanceFlag value for this BalanceListType.
     * 
     * @return balanceFlag
     */
    public int getBalanceFlag() {
        return balanceFlag;
    }


    /**
     * Sets the balanceFlag value for this BalanceListType.
     * 
     * @param balanceFlag
     */
    public void setBalanceFlag(int balanceFlag) {
        this.balanceFlag = balanceFlag;
    }


    /**
     * Gets the balance value for this BalanceListType.
     * 
     * @return balance
     */
    public long getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this BalanceListType.
     * 
     * @param balance
     */
    public void setBalance(long balance) {
        this.balance = balance;
    }


    /**
     * Gets the usedBalance value for this BalanceListType.
     * 
     * @return usedBalance
     */
    public long getUsedBalance() {
        return usedBalance;
    }


    /**
     * Sets the usedBalance value for this BalanceListType.
     * 
     * @param usedBalance
     */
    public void setUsedBalance(long usedBalance) {
        this.usedBalance = usedBalance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BalanceListType)) return false;
        BalanceListType other = (BalanceListType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.balanceTypeId == other.getBalanceTypeId() &&
            this.balanceFlag == other.getBalanceFlag() &&
            this.balance == other.getBalance() &&
            this.usedBalance == other.getUsedBalance();
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
        _hashCode += getBalanceTypeId();
        _hashCode += getBalanceFlag();
        _hashCode += new Long(getBalance()).hashCode();
        _hashCode += new Long(getUsedBalance()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BalanceListType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UnityPay.huawei.com", "BalanceListType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usedBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usedBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
