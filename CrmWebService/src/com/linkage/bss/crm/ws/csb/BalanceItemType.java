/**
 * BalanceItemType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class BalanceItemType  implements java.io.Serializable {
    private java.lang.String itemName;

    private int itemTotalBalance;

    private int itemBalance;

    private java.lang.String useAccNbr;

    private java.lang.String useLimitItem;

    private int attr;

    private int balanceAttrId;

    public BalanceItemType() {
    }

    public BalanceItemType(
           java.lang.String itemName,
           int itemTotalBalance,
           int itemBalance,
           java.lang.String useAccNbr,
           java.lang.String useLimitItem,
           int attr,
           int balanceAttrId) {
           this.itemName = itemName;
           this.itemTotalBalance = itemTotalBalance;
           this.itemBalance = itemBalance;
           this.useAccNbr = useAccNbr;
           this.useLimitItem = useLimitItem;
           this.attr = attr;
           this.balanceAttrId = balanceAttrId;
    }


    /**
     * Gets the itemName value for this BalanceItemType.
     * 
     * @return itemName
     */
    public java.lang.String getItemName() {
        return itemName;
    }


    /**
     * Sets the itemName value for this BalanceItemType.
     * 
     * @param itemName
     */
    public void setItemName(java.lang.String itemName) {
        this.itemName = itemName;
    }


    /**
     * Gets the itemTotalBalance value for this BalanceItemType.
     * 
     * @return itemTotalBalance
     */
    public int getItemTotalBalance() {
        return itemTotalBalance;
    }


    /**
     * Sets the itemTotalBalance value for this BalanceItemType.
     * 
     * @param itemTotalBalance
     */
    public void setItemTotalBalance(int itemTotalBalance) {
        this.itemTotalBalance = itemTotalBalance;
    }


    /**
     * Gets the itemBalance value for this BalanceItemType.
     * 
     * @return itemBalance
     */
    public int getItemBalance() {
        return itemBalance;
    }


    /**
     * Sets the itemBalance value for this BalanceItemType.
     * 
     * @param itemBalance
     */
    public void setItemBalance(int itemBalance) {
        this.itemBalance = itemBalance;
    }


    /**
     * Gets the useAccNbr value for this BalanceItemType.
     * 
     * @return useAccNbr
     */
    public java.lang.String getUseAccNbr() {
        return useAccNbr;
    }


    /**
     * Sets the useAccNbr value for this BalanceItemType.
     * 
     * @param useAccNbr
     */
    public void setUseAccNbr(java.lang.String useAccNbr) {
        this.useAccNbr = useAccNbr;
    }


    /**
     * Gets the useLimitItem value for this BalanceItemType.
     * 
     * @return useLimitItem
     */
    public java.lang.String getUseLimitItem() {
        return useLimitItem;
    }


    /**
     * Sets the useLimitItem value for this BalanceItemType.
     * 
     * @param useLimitItem
     */
    public void setUseLimitItem(java.lang.String useLimitItem) {
        this.useLimitItem = useLimitItem;
    }


    /**
     * Gets the attr value for this BalanceItemType.
     * 
     * @return attr
     */
    public int getAttr() {
        return attr;
    }


    /**
     * Sets the attr value for this BalanceItemType.
     * 
     * @param attr
     */
    public void setAttr(int attr) {
        this.attr = attr;
    }


    /**
     * Gets the balanceAttrId value for this BalanceItemType.
     * 
     * @return balanceAttrId
     */
    public int getBalanceAttrId() {
        return balanceAttrId;
    }


    /**
     * Sets the balanceAttrId value for this BalanceItemType.
     * 
     * @param balanceAttrId
     */
    public void setBalanceAttrId(int balanceAttrId) {
        this.balanceAttrId = balanceAttrId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BalanceItemType)) return false;
        BalanceItemType other = (BalanceItemType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.itemName==null && other.getItemName()==null) || 
             (this.itemName!=null &&
              this.itemName.equals(other.getItemName()))) &&
            this.itemTotalBalance == other.getItemTotalBalance() &&
            this.itemBalance == other.getItemBalance() &&
            ((this.useAccNbr==null && other.getUseAccNbr()==null) || 
             (this.useAccNbr!=null &&
              this.useAccNbr.equals(other.getUseAccNbr()))) &&
            ((this.useLimitItem==null && other.getUseLimitItem()==null) || 
             (this.useLimitItem!=null &&
              this.useLimitItem.equals(other.getUseLimitItem()))) &&
            this.attr == other.getAttr() &&
            this.balanceAttrId == other.getBalanceAttrId();
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
        if (getItemName() != null) {
            _hashCode += getItemName().hashCode();
        }
        _hashCode += getItemTotalBalance();
        _hashCode += getItemBalance();
        if (getUseAccNbr() != null) {
            _hashCode += getUseAccNbr().hashCode();
        }
        if (getUseLimitItem() != null) {
            _hashCode += getUseLimitItem().hashCode();
        }
        _hashCode += getAttr();
        _hashCode += getBalanceAttrId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BalanceItemType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ocs", "BalanceItemType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemTotalBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemTotalBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useAccNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useAccNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useLimitItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useLimitItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceAttrId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceAttrId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
