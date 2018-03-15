/**
 * PrepayCanBackReturnType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class PrepayCanBackReturnType  implements java.io.Serializable {
    private java.lang.String resultcode;

    private java.lang.String backflag;

    public PrepayCanBackReturnType() {
    }

    public PrepayCanBackReturnType(
           java.lang.String resultcode,
           java.lang.String backflag) {
           this.resultcode = resultcode;
           this.backflag = backflag;
    }


    /**
     * Gets the resultcode value for this PrepayCanBackReturnType.
     * 
     * @return resultcode
     */
    public java.lang.String getResultcode() {
        return resultcode;
    }


    /**
     * Sets the resultcode value for this PrepayCanBackReturnType.
     * 
     * @param resultcode
     */
    public void setResultcode(java.lang.String resultcode) {
        this.resultcode = resultcode;
    }


    /**
     * Gets the backflag value for this PrepayCanBackReturnType.
     * 
     * @return backflag
     */
    public java.lang.String getBackflag() {
        return backflag;
    }


    /**
     * Sets the backflag value for this PrepayCanBackReturnType.
     * 
     * @param backflag
     */
    public void setBackflag(java.lang.String backflag) {
        this.backflag = backflag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrepayCanBackReturnType)) return false;
        PrepayCanBackReturnType other = (PrepayCanBackReturnType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultcode==null && other.getResultcode()==null) || 
             (this.resultcode!=null &&
              this.resultcode.equals(other.getResultcode()))) &&
            ((this.backflag==null && other.getBackflag()==null) || 
             (this.backflag!=null &&
              this.backflag.equals(other.getBackflag())));
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
        if (getResultcode() != null) {
            _hashCode += getResultcode().hashCode();
        }
        if (getBackflag() != null) {
            _hashCode += getBackflag().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrepayCanBackReturnType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "PrepayCanBackReturnType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("backflag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "backflag"));
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
