/**
 * AccountQueryReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class AccountQueryReturn  implements java.io.Serializable {
    private java.lang.String responseId;

    private java.lang.String responseTime;

    private java.lang.String result;

    private java.lang.String errorCode;

    private java.lang.String errorMessage;

    private int totalBalance;

    private int normalBalance;

    private int specBalance;

    private int charge;

    private int realCharge;

    private int amount;

    private java.lang.String validDate;

    private com.linkage.bss.crm.ws.csb.BalanceInfoType[] balanceDetail;

    private com.linkage.bss.crm.ws.csb.ActiveInfoType[] activeDetail;

    public AccountQueryReturn() {
    }

    public AccountQueryReturn(
           java.lang.String responseId,
           java.lang.String responseTime,
           java.lang.String result,
           java.lang.String errorCode,
           java.lang.String errorMessage,
           int totalBalance,
           int normalBalance,
           int specBalance,
           int charge,
           int realCharge,
           int amount,
           java.lang.String validDate,
           com.linkage.bss.crm.ws.csb.BalanceInfoType[] balanceDetail,
           com.linkage.bss.crm.ws.csb.ActiveInfoType[] activeDetail) {
           this.responseId = responseId;
           this.responseTime = responseTime;
           this.result = result;
           this.errorCode = errorCode;
           this.errorMessage = errorMessage;
           this.totalBalance = totalBalance;
           this.normalBalance = normalBalance;
           this.specBalance = specBalance;
           this.charge = charge;
           this.realCharge = realCharge;
           this.amount = amount;
           this.validDate = validDate;
           this.balanceDetail = balanceDetail;
           this.activeDetail = activeDetail;
    }


    /**
     * Gets the responseId value for this AccountQueryReturn.
     * 
     * @return responseId
     */
    public java.lang.String getResponseId() {
        return responseId;
    }


    /**
     * Sets the responseId value for this AccountQueryReturn.
     * 
     * @param responseId
     */
    public void setResponseId(java.lang.String responseId) {
        this.responseId = responseId;
    }


    /**
     * Gets the responseTime value for this AccountQueryReturn.
     * 
     * @return responseTime
     */
    public java.lang.String getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this AccountQueryReturn.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.lang.String responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this AccountQueryReturn.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this AccountQueryReturn.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the errorCode value for this AccountQueryReturn.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this AccountQueryReturn.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the errorMessage value for this AccountQueryReturn.
     * 
     * @return errorMessage
     */
    public java.lang.String getErrorMessage() {
        return errorMessage;
    }


    /**
     * Sets the errorMessage value for this AccountQueryReturn.
     * 
     * @param errorMessage
     */
    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * Gets the totalBalance value for this AccountQueryReturn.
     * 
     * @return totalBalance
     */
    public int getTotalBalance() {
        return totalBalance;
    }


    /**
     * Sets the totalBalance value for this AccountQueryReturn.
     * 
     * @param totalBalance
     */
    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }


    /**
     * Gets the normalBalance value for this AccountQueryReturn.
     * 
     * @return normalBalance
     */
    public int getNormalBalance() {
        return normalBalance;
    }


    /**
     * Sets the normalBalance value for this AccountQueryReturn.
     * 
     * @param normalBalance
     */
    public void setNormalBalance(int normalBalance) {
        this.normalBalance = normalBalance;
    }


    /**
     * Gets the specBalance value for this AccountQueryReturn.
     * 
     * @return specBalance
     */
    public int getSpecBalance() {
        return specBalance;
    }


    /**
     * Sets the specBalance value for this AccountQueryReturn.
     * 
     * @param specBalance
     */
    public void setSpecBalance(int specBalance) {
        this.specBalance = specBalance;
    }


    /**
     * Gets the charge value for this AccountQueryReturn.
     * 
     * @return charge
     */
    public int getCharge() {
        return charge;
    }


    /**
     * Sets the charge value for this AccountQueryReturn.
     * 
     * @param charge
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }


    /**
     * Gets the realCharge value for this AccountQueryReturn.
     * 
     * @return realCharge
     */
    public int getRealCharge() {
        return realCharge;
    }


    /**
     * Sets the realCharge value for this AccountQueryReturn.
     * 
     * @param realCharge
     */
    public void setRealCharge(int realCharge) {
        this.realCharge = realCharge;
    }


    /**
     * Gets the amount value for this AccountQueryReturn.
     * 
     * @return amount
     */
    public int getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this AccountQueryReturn.
     * 
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }


    /**
     * Gets the validDate value for this AccountQueryReturn.
     * 
     * @return validDate
     */
    public java.lang.String getValidDate() {
        return validDate;
    }


    /**
     * Sets the validDate value for this AccountQueryReturn.
     * 
     * @param validDate
     */
    public void setValidDate(java.lang.String validDate) {
        this.validDate = validDate;
    }


    /**
     * Gets the balanceDetail value for this AccountQueryReturn.
     * 
     * @return balanceDetail
     */
    public com.linkage.bss.crm.ws.csb.BalanceInfoType[] getBalanceDetail() {
        return balanceDetail;
    }


    /**
     * Sets the balanceDetail value for this AccountQueryReturn.
     * 
     * @param balanceDetail
     */
    public void setBalanceDetail(com.linkage.bss.crm.ws.csb.BalanceInfoType[] balanceDetail) {
        this.balanceDetail = balanceDetail;
    }


    /**
     * Gets the activeDetail value for this AccountQueryReturn.
     * 
     * @return activeDetail
     */
    public com.linkage.bss.crm.ws.csb.ActiveInfoType[] getActiveDetail() {
        return activeDetail;
    }


    /**
     * Sets the activeDetail value for this AccountQueryReturn.
     * 
     * @param activeDetail
     */
    public void setActiveDetail(com.linkage.bss.crm.ws.csb.ActiveInfoType[] activeDetail) {
        this.activeDetail = activeDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountQueryReturn)) return false;
        AccountQueryReturn other = (AccountQueryReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseId==null && other.getResponseId()==null) || 
             (this.responseId!=null &&
              this.responseId.equals(other.getResponseId()))) &&
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.errorMessage==null && other.getErrorMessage()==null) || 
             (this.errorMessage!=null &&
              this.errorMessage.equals(other.getErrorMessage()))) &&
            this.totalBalance == other.getTotalBalance() &&
            this.normalBalance == other.getNormalBalance() &&
            this.specBalance == other.getSpecBalance() &&
            this.charge == other.getCharge() &&
            this.realCharge == other.getRealCharge() &&
            this.amount == other.getAmount() &&
            ((this.validDate==null && other.getValidDate()==null) || 
             (this.validDate!=null &&
              this.validDate.equals(other.getValidDate()))) &&
            ((this.balanceDetail==null && other.getBalanceDetail()==null) || 
             (this.balanceDetail!=null &&
              java.util.Arrays.equals(this.balanceDetail, other.getBalanceDetail()))) &&
            ((this.activeDetail==null && other.getActiveDetail()==null) || 
             (this.activeDetail!=null &&
              java.util.Arrays.equals(this.activeDetail, other.getActiveDetail())));
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
        if (getResponseId() != null) {
            _hashCode += getResponseId().hashCode();
        }
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getErrorMessage() != null) {
            _hashCode += getErrorMessage().hashCode();
        }
        _hashCode += getTotalBalance();
        _hashCode += getNormalBalance();
        _hashCode += getSpecBalance();
        _hashCode += getCharge();
        _hashCode += getRealCharge();
        _hashCode += getAmount();
        if (getValidDate() != null) {
            _hashCode += getValidDate().hashCode();
        }
        if (getBalanceDetail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBalanceDetail());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBalanceDetail(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getActiveDetail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getActiveDetail());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getActiveDetail(), i);
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
        new org.apache.axis.description.TypeDesc(AccountQueryReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "AccountQueryReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("normalBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "normalBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("charge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "charge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("realCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "realCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "validDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balanceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "BalanceInfoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://hb.client.csb.crm.linkage.com", "ActiveInfoType"));
        elemField.setMinOccurs(0);
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
