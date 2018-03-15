/**
 * CardManageReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class CardManageReq  implements java.io.Serializable {
    private java.lang.String requestSource;

    private java.lang.String requestUser;

    private java.lang.String requestId;

    private java.lang.String requestTime;

    private int operationCode;

    private java.lang.String startCardNo;

    private java.lang.String endCardNo;

    private int money;

    private java.lang.String delayTime;

    public CardManageReq() {
    }

    public CardManageReq(
           java.lang.String requestSource,
           java.lang.String requestUser,
           java.lang.String requestId,
           java.lang.String requestTime,
           int operationCode,
           java.lang.String startCardNo,
           java.lang.String endCardNo,
           int money,
           java.lang.String delayTime) {
           this.requestSource = requestSource;
           this.requestUser = requestUser;
           this.requestId = requestId;
           this.requestTime = requestTime;
           this.operationCode = operationCode;
           this.startCardNo = startCardNo;
           this.endCardNo = endCardNo;
           this.money = money;
           this.delayTime = delayTime;
    }


    /**
     * Gets the requestSource value for this CardManageReq.
     * 
     * @return requestSource
     */
    public java.lang.String getRequestSource() {
        return requestSource;
    }


    /**
     * Sets the requestSource value for this CardManageReq.
     * 
     * @param requestSource
     */
    public void setRequestSource(java.lang.String requestSource) {
        this.requestSource = requestSource;
    }


    /**
     * Gets the requestUser value for this CardManageReq.
     * 
     * @return requestUser
     */
    public java.lang.String getRequestUser() {
        return requestUser;
    }


    /**
     * Sets the requestUser value for this CardManageReq.
     * 
     * @param requestUser
     */
    public void setRequestUser(java.lang.String requestUser) {
        this.requestUser = requestUser;
    }


    /**
     * Gets the requestId value for this CardManageReq.
     * 
     * @return requestId
     */
    public java.lang.String getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this CardManageReq.
     * 
     * @param requestId
     */
    public void setRequestId(java.lang.String requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the requestTime value for this CardManageReq.
     * 
     * @return requestTime
     */
    public java.lang.String getRequestTime() {
        return requestTime;
    }


    /**
     * Sets the requestTime value for this CardManageReq.
     * 
     * @param requestTime
     */
    public void setRequestTime(java.lang.String requestTime) {
        this.requestTime = requestTime;
    }


    /**
     * Gets the operationCode value for this CardManageReq.
     * 
     * @return operationCode
     */
    public int getOperationCode() {
        return operationCode;
    }


    /**
     * Sets the operationCode value for this CardManageReq.
     * 
     * @param operationCode
     */
    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }


    /**
     * Gets the startCardNo value for this CardManageReq.
     * 
     * @return startCardNo
     */
    public java.lang.String getStartCardNo() {
        return startCardNo;
    }


    /**
     * Sets the startCardNo value for this CardManageReq.
     * 
     * @param startCardNo
     */
    public void setStartCardNo(java.lang.String startCardNo) {
        this.startCardNo = startCardNo;
    }


    /**
     * Gets the endCardNo value for this CardManageReq.
     * 
     * @return endCardNo
     */
    public java.lang.String getEndCardNo() {
        return endCardNo;
    }


    /**
     * Sets the endCardNo value for this CardManageReq.
     * 
     * @param endCardNo
     */
    public void setEndCardNo(java.lang.String endCardNo) {
        this.endCardNo = endCardNo;
    }


    /**
     * Gets the money value for this CardManageReq.
     * 
     * @return money
     */
    public int getMoney() {
        return money;
    }


    /**
     * Sets the money value for this CardManageReq.
     * 
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }


    /**
     * Gets the delayTime value for this CardManageReq.
     * 
     * @return delayTime
     */
    public java.lang.String getDelayTime() {
        return delayTime;
    }


    /**
     * Sets the delayTime value for this CardManageReq.
     * 
     * @param delayTime
     */
    public void setDelayTime(java.lang.String delayTime) {
        this.delayTime = delayTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardManageReq)) return false;
        CardManageReq other = (CardManageReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestSource==null && other.getRequestSource()==null) || 
             (this.requestSource!=null &&
              this.requestSource.equals(other.getRequestSource()))) &&
            ((this.requestUser==null && other.getRequestUser()==null) || 
             (this.requestUser!=null &&
              this.requestUser.equals(other.getRequestUser()))) &&
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.requestTime==null && other.getRequestTime()==null) || 
             (this.requestTime!=null &&
              this.requestTime.equals(other.getRequestTime()))) &&
            this.operationCode == other.getOperationCode() &&
            ((this.startCardNo==null && other.getStartCardNo()==null) || 
             (this.startCardNo!=null &&
              this.startCardNo.equals(other.getStartCardNo()))) &&
            ((this.endCardNo==null && other.getEndCardNo()==null) || 
             (this.endCardNo!=null &&
              this.endCardNo.equals(other.getEndCardNo()))) &&
            this.money == other.getMoney() &&
            ((this.delayTime==null && other.getDelayTime()==null) || 
             (this.delayTime!=null &&
              this.delayTime.equals(other.getDelayTime())));
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
        if (getRequestSource() != null) {
            _hashCode += getRequestSource().hashCode();
        }
        if (getRequestUser() != null) {
            _hashCode += getRequestUser().hashCode();
        }
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getRequestTime() != null) {
            _hashCode += getRequestTime().hashCode();
        }
        _hashCode += getOperationCode();
        if (getStartCardNo() != null) {
            _hashCode += getStartCardNo().hashCode();
        }
        if (getEndCardNo() != null) {
            _hashCode += getEndCardNo().hashCode();
        }
        _hashCode += getMoney();
        if (getDelayTime() != null) {
            _hashCode += getDelayTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardManageReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "CardManageReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestSource");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestUser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startCardNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startCardNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endCardNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endCardNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("money");
        elemField.setXmlName(new javax.xml.namespace.QName("", "money"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delayTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "delayTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
