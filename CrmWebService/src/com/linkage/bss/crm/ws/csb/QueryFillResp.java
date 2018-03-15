/**
 * QueryFillResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class QueryFillResp  implements java.io.Serializable {
    private com.linkage.bss.crm.ws.csb.QueryRechargeFill[] QRechInfoList;

    private int responseAmount;

    private java.lang.String responseId;

    private java.lang.String responseTime;

    private int result;

    public QueryFillResp() {
    }

    public QueryFillResp(
           com.linkage.bss.crm.ws.csb.QueryRechargeFill[] QRechInfoList,
           int responseAmount,
           java.lang.String responseId,
           java.lang.String responseTime,
           int result) {
           this.QRechInfoList = QRechInfoList;
           this.responseAmount = responseAmount;
           this.responseId = responseId;
           this.responseTime = responseTime;
           this.result = result;
    }


    /**
     * Gets the QRechInfoList value for this QueryFillResp.
     * 
     * @return QRechInfoList
     */
    public com.linkage.bss.crm.ws.csb.QueryRechargeFill[] getQRechInfoList() {
        return QRechInfoList;
    }


    /**
     * Sets the QRechInfoList value for this QueryFillResp.
     * 
     * @param QRechInfoList
     */
    public void setQRechInfoList(com.linkage.bss.crm.ws.csb.QueryRechargeFill[] QRechInfoList) {
        this.QRechInfoList = QRechInfoList;
    }


    /**
     * Gets the responseAmount value for this QueryFillResp.
     * 
     * @return responseAmount
     */
    public int getResponseAmount() {
        return responseAmount;
    }


    /**
     * Sets the responseAmount value for this QueryFillResp.
     * 
     * @param responseAmount
     */
    public void setResponseAmount(int responseAmount) {
        this.responseAmount = responseAmount;
    }


    /**
     * Gets the responseId value for this QueryFillResp.
     * 
     * @return responseId
     */
    public java.lang.String getResponseId() {
        return responseId;
    }


    /**
     * Sets the responseId value for this QueryFillResp.
     * 
     * @param responseId
     */
    public void setResponseId(java.lang.String responseId) {
        this.responseId = responseId;
    }


    /**
     * Gets the responseTime value for this QueryFillResp.
     * 
     * @return responseTime
     */
    public java.lang.String getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this QueryFillResp.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.lang.String responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this QueryFillResp.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryFillResp.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFillResp)) return false;
        QueryFillResp other = (QueryFillResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.QRechInfoList==null && other.getQRechInfoList()==null) || 
             (this.QRechInfoList!=null &&
              java.util.Arrays.equals(this.QRechInfoList, other.getQRechInfoList()))) &&
            this.responseAmount == other.getResponseAmount() &&
            ((this.responseId==null && other.getResponseId()==null) || 
             (this.responseId!=null &&
              this.responseId.equals(other.getResponseId()))) &&
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            this.result == other.getResult();
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
        if (getQRechInfoList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQRechInfoList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQRechInfoList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getResponseAmount();
        if (getResponseId() != null) {
            _hashCode += getResponseId().hashCode();
        }
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryFillResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vc.client.csb.crm.linkage.com", "QueryFillResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("QRechInfoList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QRechInfoList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vc.client.csb.crm.linkage.com", "QueryRechargeFill"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
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
