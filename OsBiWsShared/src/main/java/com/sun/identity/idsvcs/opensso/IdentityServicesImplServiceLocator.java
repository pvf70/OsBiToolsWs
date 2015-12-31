/**
 * IdentityServicesImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sun.identity.idsvcs.opensso;

import java.util.HashSet;

import javax.xml.namespace.QName;

public class IdentityServicesImplServiceLocator extends org.apache.axis.client.Service implements com.sun.identity.idsvcs.opensso.IdentityServicesImplService {

	// Default serial version uid
	private static final long serialVersionUID = 1L;

	public IdentityServicesImplServiceLocator() {
    }


    public IdentityServicesImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IdentityServicesImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IdentityServicesImplPort
    private java.lang.String IdentityServicesImplPort_address = "http://localhost:8080/openam/identityservices/IdentityServices";

    @Override
    public java.lang.String getIdentityServicesImplPortAddress() {
        return IdentityServicesImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IdentityServicesImplPortWSDDServiceName = "IdentityServicesImplPort";

    public java.lang.String getIdentityServicesImplPortWSDDServiceName() {
        return IdentityServicesImplPortWSDDServiceName;
    }

    public void setIdentityServicesImplPortWSDDServiceName(java.lang.String name) {
        IdentityServicesImplPortWSDDServiceName = name;
    }

    @Override
    public com.sun.identity.idsvcs.opensso.IdentityServicesImpl getIdentityServicesImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IdentityServicesImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIdentityServicesImplPort(endpoint);
    }

    @Override
    public com.sun.identity.idsvcs.opensso.IdentityServicesImpl getIdentityServicesImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.sun.identity.idsvcs.opensso.IdentityServicesImplPortBindingStub _stub = new com.sun.identity.idsvcs.opensso.IdentityServicesImplPortBindingStub(portAddress, this);
            _stub.setPortName(getIdentityServicesImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIdentityServicesImplPortEndpointAddress(java.lang.String address) {
        IdentityServicesImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sun.identity.idsvcs.opensso.IdentityServicesImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.sun.identity.idsvcs.opensso.IdentityServicesImplPortBindingStub _stub = new com.sun.identity.idsvcs.opensso.IdentityServicesImplPortBindingStub(new java.net.URL(IdentityServicesImplPort_address), this);
                _stub.setPortName(getIdentityServicesImplPortWSDDServiceName());
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
    @Override
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IdentityServicesImplPort".equals(inputPortName)) {
            return getIdentityServicesImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "IdentityServicesImplService");
    }

    private java.util.HashSet<QName> ports = null;

    @Override
    public java.util.Iterator<QName> getPorts() {
        if (ports == null) {
            ports = new HashSet<QName>();
            ports.add(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "IdentityServicesImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IdentityServicesImplPort".equals(portName)) {
            setIdentityServicesImplPortEndpointAddress(address);
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
