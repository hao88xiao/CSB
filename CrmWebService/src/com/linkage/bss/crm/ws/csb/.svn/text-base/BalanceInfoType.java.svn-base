/**
 * BalanceInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class BalanceInfoType  implements java.io.Serializable {
    private java.lang.String balanceName;

    private int groupBalance;

    private int balance;

    private com.linkage.bss.crm.ws.csb.BalanceItemType[] balanceItem;

    public BalanceInfoType() {
    }

    public BalanceInfoType(
           java.lang.String balanceName,
           int groupBalance,
           int balance,
           com.linkage.bss.crm.ws.csb.BalanceItemType[] balanceItem) {
           this.balanceName = balanceName;
           this.groupBalance = groupBalance;
           this.balance = balance;
           this.balanceItem = balanceItem;
    }


    /**
     * Gets the balanceName value for this BalanceInfoType.
     * 
     * @return balanceName
     */
    public java.lang.String getBalanceName() {
        return balanceName;
    }


    /**
     * Sets the balanceName value for this BalanceInfoType.
     * 
     * @param balanceName
     */
    public void setBalanceName(java.lang.String balanceName) {
        this.balanceName = balanceName;
    }


    /**
     * Gets the groupBalance value for this BalanceInfoType.
     * 
     * @return groupBalance
     */
    public int getGroupBalance() {
        return groupBalance;
    }


    /**
     * Sets the groupBalance value for this BalanceInfoType.
     * 
     * @param groupBalance
     */
    public void setGroupBalance(int groupBalance) {
        this.groupBalance = groupBalance;
    }


    /**
     * Gets the balance value for this BalanceInfoType.
     * 
     * @return balance
     */
    public int getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this BalanceInfoType.
     * 
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }


    /**
     * Gets the balanceItem value for this BalanceInfoType.
     * 
     * @return balanceItem
     */
    public com.linkage.bss.crm.ws.csb.BalanceItemType[] getBalanceItem() {
        return balanceItem;
    }


    /**
     * Sets the balanceItem value for this BalanceInfoType.
     * 
     * @param balanceItem
     */
    public void setBalanceItem(com.linkage.bss.crm.ws.csb.BalanceItemType[] balanceItem) {
        this.balanceItem = balanceItem;
    }

    public com.linkage.bss.crm.ws.csb.BalanceItemType getBalanceItem(int i) {
        return this.balanceItem[i];
    }

    public void setBalanceItem(int i, com.linkage.bss.crm.ws.csb.BalanceItemType _value) {
        this.balanceItem[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BalanceInfoType)) return false;
        BalanceInfoType other = (BalanceInfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.balanceName==null && other.getBalanceName()==null) || 
             (this.balanceName!=null &&
              this.balanceName.equals(other.getBalanceName()))) &&
            this.groupBalance == other.getGroupBalance() &&
            this.balance == other.getBalance() &&
            ((this.balanceItem==null && other.getBalanceItem()==null) || 
             (this.balanceItem!=null &&
              java.util.Arrays.equals(this.balanceItem, other.getBalanceItem())));
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
        if (getBalanceName() != null) {
            _hashCode += getBalanceName().hashCode();
        }
        _hashCode += getGroupBalance();
        _hashCode += getBalance();
        if (getBalanceItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBalanceItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBalanceItem(), i);
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
        new org.apache.axis.description.TypeDesc(BalanceInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "BalanceInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ocs", "BalanceItemType"));
        elemField.setMinOccurs(0);
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
