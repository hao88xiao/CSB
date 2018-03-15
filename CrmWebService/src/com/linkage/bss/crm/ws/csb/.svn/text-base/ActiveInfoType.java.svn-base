/**
 * ActiveInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class ActiveInfoType  implements java.io.Serializable {
    private int groupBalance;

    private int activeBalance;

    private com.linkage.bss.crm.ws.csb.ActiveItemType[] activeItem;

    public ActiveInfoType() {
    }

    public ActiveInfoType(
           int groupBalance,
           int activeBalance,
           com.linkage.bss.crm.ws.csb.ActiveItemType[] activeItem) {
           this.groupBalance = groupBalance;
           this.activeBalance = activeBalance;
           this.activeItem = activeItem;
    }


    /**
     * Gets the groupBalance value for this ActiveInfoType.
     * 
     * @return groupBalance
     */
    public int getGroupBalance() {
        return groupBalance;
    }


    /**
     * Sets the groupBalance value for this ActiveInfoType.
     * 
     * @param groupBalance
     */
    public void setGroupBalance(int groupBalance) {
        this.groupBalance = groupBalance;
    }


    /**
     * Gets the activeBalance value for this ActiveInfoType.
     * 
     * @return activeBalance
     */
    public int getActiveBalance() {
        return activeBalance;
    }


    /**
     * Sets the activeBalance value for this ActiveInfoType.
     * 
     * @param activeBalance
     */
    public void setActiveBalance(int activeBalance) {
        this.activeBalance = activeBalance;
    }


    /**
     * Gets the activeItem value for this ActiveInfoType.
     * 
     * @return activeItem
     */
    public com.linkage.bss.crm.ws.csb.ActiveItemType[] getActiveItem() {
        return activeItem;
    }


    /**
     * Sets the activeItem value for this ActiveInfoType.
     * 
     * @param activeItem
     */
    public void setActiveItem(com.linkage.bss.crm.ws.csb.ActiveItemType[] activeItem) {
        this.activeItem = activeItem;
    }

    public com.linkage.bss.crm.ws.csb.ActiveItemType getActiveItem(int i) {
        return this.activeItem[i];
    }

    public void setActiveItem(int i, com.linkage.bss.crm.ws.csb.ActiveItemType _value) {
        this.activeItem[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActiveInfoType)) return false;
        ActiveInfoType other = (ActiveInfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.groupBalance == other.getGroupBalance() &&
            this.activeBalance == other.getActiveBalance() &&
            ((this.activeItem==null && other.getActiveItem()==null) || 
             (this.activeItem!=null &&
              java.util.Arrays.equals(this.activeItem, other.getActiveItem())));
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
        _hashCode += getGroupBalance();
        _hashCode += getActiveBalance();
        if (getActiveItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getActiveItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getActiveItem(), i);
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
        new org.apache.axis.description.TypeDesc(ActiveInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "ActiveInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ocs", "ActiveItemType"));
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
