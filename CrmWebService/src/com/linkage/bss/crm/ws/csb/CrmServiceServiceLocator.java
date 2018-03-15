/**
 * CrmServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.bss.crm.ws.csb;

public class CrmServiceServiceLocator extends org.apache.axis.client.Service implements com.linkage.bss.crm.ws.csb.CrmServiceService {

    public CrmServiceServiceLocator() {
    }


    public CrmServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CrmServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CrmService
    private java.lang.String CrmService_address = "http://172.19.17.152:7101/BJCSB/services/CrmService";

    public java.lang.String getCrmServiceAddress() {
        return CrmService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CrmServiceWSDDServiceName = "CrmService";

    public java.lang.String getCrmServiceWSDDServiceName() {
        return CrmServiceWSDDServiceName;
    }

    public void setCrmServiceWSDDServiceName(java.lang.String name) {
        CrmServiceWSDDServiceName = name;
    }

    public com.linkage.bss.crm.ws.csb.WSSPortType getCrmService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CrmService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCrmService(endpoint);
    }

    public com.linkage.bss.crm.ws.csb.WSSPortType getCrmService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.linkage.bss.crm.ws.csb.CrmServiceSoapBindingStub _stub = new com.linkage.bss.crm.ws.csb.CrmServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCrmServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCrmServiceEndpointAddress(java.lang.String address) {
        CrmService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.linkage.bss.crm.ws.csb.WSSPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.linkage.bss.crm.ws.csb.CrmServiceSoapBindingStub _stub = new com.linkage.bss.crm.ws.csb.CrmServiceSoapBindingStub(new java.net.URL(CrmService_address), this);
                _stub.setPortName(getCrmServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CrmService".equals(inputPortName)) {
            return getCrmService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://crm.crmwsi", "CrmServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://crm.crmwsi", "CrmService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CrmService".equals(portName)) {
            setCrmServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
