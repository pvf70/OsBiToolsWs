/**
 * IdentityServicesImplPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sun.identity.idsvcs.opensso;

import java.util.Enumeration;

import javax.xml.namespace.QName;

public class IdentityServicesImplPortBindingStub extends org.apache.axis.client.Stub implements com.sun.identity.idsvcs.opensso.IdentityServicesImpl {

	private java.util.Vector<Class<?>> cachedSerClasses = new java.util.Vector<Class<?>>();
    private java.util.Vector<QName> cachedSerQNames = new java.util.Vector<QName>();
    private java.util.Vector<Object> cachedSerFactories = new java.util.Vector<Object>();
    private java.util.Vector<Object> cachedDeserFactories = new java.util.Vector<Object>();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[13];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("log");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "app"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subject"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "logName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "message"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"),
                      "com.sun.identity.idsvcs.opensso.AccessDenied",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("attributes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "attributes"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subject"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "refresh"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "userDetails"));
        oper.setReturnClass(com.sun.identity.idsvcs.opensso.UserDetails.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"),
                      "com.sun.identity.idsvcs.opensso.AccessDenied",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("authenticate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"));
        oper.setReturnClass(com.sun.identity.idsvcs.opensso.Token.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "OrgInactive"),
                      "com.sun.identity.idsvcs.opensso.OrgInactive",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "OrgInactive"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserNotFound"),
                      "com.sun.identity.idsvcs.opensso.UserNotFound",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserNotFound"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserLocked"),
                      "com.sun.identity.idsvcs.opensso.UserLocked",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserLocked"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "MaximumSessionReached"),
                      "com.sun.identity.idsvcs.opensso.MaximumSessionReached",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "MaximumSessionReached"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidCredentials"),
                      "com.sun.identity.idsvcs.opensso.InvalidCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccountExpired"),
                      "com.sun.identity.idsvcs.opensso.AccountExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccountExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserInactive"),
                      "com.sun.identity.idsvcs.opensso.UserInactive",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserInactive"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidPassword"),
                      "com.sun.identity.idsvcs.opensso.InvalidPassword",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidPassword"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("logout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subject"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("authorize");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "action"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subject"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("isTokenValid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidToken"),
                      "com.sun.identity.idsvcs.opensso.InvalidToken",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidToken"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCookieNameForToken");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCookieNamesToForward");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("search");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "filter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "attributes"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attribute"), com.sun.identity.idsvcs.opensso.Attribute[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "admin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identity"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails"), com.sun.identity.idsvcs.opensso.IdentityDetails.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "admin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "DuplicateObject"),
                      "com.sun.identity.idsvcs.opensso.DuplicateObject",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "DuplicateObject"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("read");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "name"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "attributes"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attribute"), com.sun.identity.idsvcs.opensso.Attribute[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "admin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails"));
        oper.setReturnClass(com.sun.identity.idsvcs.opensso.IdentityDetails.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"),
                      "com.sun.identity.idsvcs.opensso.ObjectNotFound",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"),
                      "com.sun.identity.idsvcs.opensso.AccessDenied",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identity"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails"), com.sun.identity.idsvcs.opensso.IdentityDetails.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "admin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"),
                      "com.sun.identity.idsvcs.opensso.ObjectNotFound",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"),
                      "com.sun.identity.idsvcs.opensso.AccessDenied",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identity"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails"), com.sun.identity.idsvcs.opensso.IdentityDetails.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "admin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"), com.sun.identity.idsvcs.opensso.Token.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"),
                      "com.sun.identity.idsvcs.opensso.NeedMoreCredentials",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"),
                      "com.sun.identity.idsvcs.opensso.ObjectNotFound",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"),
                      "com.sun.identity.idsvcs.opensso.GeneralFailure",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"),
                      "com.sun.identity.idsvcs.opensso.TokenExpired",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"),
                      "com.sun.identity.idsvcs.opensso.AccessDenied",
                      new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied"), 
                      true
                     ));
        _operations[12] = oper;

    }

    public IdentityServicesImplPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IdentityServicesImplPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IdentityServicesImplPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class<?> cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class<?> beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class<?> beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccessDenied");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.AccessDenied.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "AccountExpired");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.AccountExpired.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attribute");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.Attribute.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "DuplicateObject");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.DuplicateObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "GeneralFailure");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.GeneralFailure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.IdentityDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidCredentials");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.InvalidCredentials.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidPassword");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.InvalidPassword.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "InvalidToken");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.InvalidToken.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "listWrapper");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "elements");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "MaximumSessionReached");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.MaximumSessionReached.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "NeedMoreCredentials");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.NeedMoreCredentials.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "ObjectNotFound");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.ObjectNotFound.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "OrgInactive");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.OrgInactive.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.Token.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "TokenExpired");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.TokenExpired.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "userDetails");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.UserDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserInactive");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.UserInactive.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserLocked");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.UserLocked.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "UserNotFound");
            cachedSerQNames.add(qName);
            cls = com.sun.identity.idsvcs.opensso.UserNotFound.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            Enumeration<Object> keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class<?> cls = cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class<?> sf = (java.lang.Class<?>)
                                 cachedSerFactories.get(i);
                            java.lang.Class<?> df = (java.lang.Class<?>)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    @Override
    public void log(com.sun.identity.idsvcs.opensso.Token app, com.sun.identity.idsvcs.opensso.Token subject, java.lang.String logName, java.lang.String message) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "log"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {app, subject, logName, message});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccessDenied) {
              throw (com.sun.identity.idsvcs.opensso.AccessDenied) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public com.sun.identity.idsvcs.opensso.UserDetails attributes(java.lang.String[] attributes, com.sun.identity.idsvcs.opensso.Token subject, java.lang.Boolean refresh) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attributesRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {attributes, subject, refresh});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sun.identity.idsvcs.opensso.UserDetails) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sun.identity.idsvcs.opensso.UserDetails) org.apache.axis.utils.JavaUtils.convert(_resp, com.sun.identity.idsvcs.opensso.UserDetails.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccessDenied) {
              throw (com.sun.identity.idsvcs.opensso.AccessDenied) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public com.sun.identity.idsvcs.opensso.Token authenticate(java.lang.String username, java.lang.String password, java.lang.String uri, java.lang.String client) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.OrgInactive, com.sun.identity.idsvcs.opensso.UserNotFound, com.sun.identity.idsvcs.opensso.UserLocked, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.MaximumSessionReached, com.sun.identity.idsvcs.opensso.InvalidCredentials, com.sun.identity.idsvcs.opensso.AccountExpired, com.sun.identity.idsvcs.opensso.UserInactive, com.sun.identity.idsvcs.opensso.InvalidPassword {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "authenticate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {username, password, uri, client});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sun.identity.idsvcs.opensso.Token) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sun.identity.idsvcs.opensso.Token) org.apache.axis.utils.JavaUtils.convert(_resp, com.sun.identity.idsvcs.opensso.Token.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.OrgInactive) {
              throw (com.sun.identity.idsvcs.opensso.OrgInactive) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.UserNotFound) {
              throw (com.sun.identity.idsvcs.opensso.UserNotFound) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.UserLocked) {
              throw (com.sun.identity.idsvcs.opensso.UserLocked) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.MaximumSessionReached) {
              throw (com.sun.identity.idsvcs.opensso.MaximumSessionReached) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.InvalidCredentials) {
              throw (com.sun.identity.idsvcs.opensso.InvalidCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccountExpired) {
              throw (com.sun.identity.idsvcs.opensso.AccountExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.UserInactive) {
              throw (com.sun.identity.idsvcs.opensso.UserInactive) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.InvalidPassword) {
              throw (com.sun.identity.idsvcs.opensso.InvalidPassword) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public void logout(com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "logout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {subject});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public boolean authorize(java.lang.String uri, java.lang.String action, com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "authorize"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {uri, action, subject});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public boolean isTokenValid(com.sun.identity.idsvcs.opensso.Token token) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.InvalidToken {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "isTokenValid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.InvalidToken) {
              throw (com.sun.identity.idsvcs.opensso.InvalidToken) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public java.lang.String getCookieNameForToken() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "getCookieNameForToken"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public java.lang.String[] getCookieNamesToForward() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "getCookieNamesToForward"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public java.lang.String[] search(java.lang.String filter, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "search"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {filter, attributes, admin});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public void create(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.DuplicateObject, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "create"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {identity, admin});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.DuplicateObject) {
              throw (com.sun.identity.idsvcs.opensso.DuplicateObject) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public com.sun.identity.idsvcs.opensso.IdentityDetails read(java.lang.String name, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "read"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {name, attributes, admin});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sun.identity.idsvcs.opensso.IdentityDetails) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sun.identity.idsvcs.opensso.IdentityDetails) org.apache.axis.utils.JavaUtils.convert(_resp, com.sun.identity.idsvcs.opensso.IdentityDetails.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.ObjectNotFound) {
              throw (com.sun.identity.idsvcs.opensso.ObjectNotFound) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccessDenied) {
              throw (com.sun.identity.idsvcs.opensso.AccessDenied) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public void update(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "update"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {identity, admin});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.ObjectNotFound) {
              throw (com.sun.identity.idsvcs.opensso.ObjectNotFound) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccessDenied) {
              throw (com.sun.identity.idsvcs.opensso.AccessDenied) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    @Override
    public void delete(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "delete"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {identity, admin});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.NeedMoreCredentials) {
              throw (com.sun.identity.idsvcs.opensso.NeedMoreCredentials) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.ObjectNotFound) {
              throw (com.sun.identity.idsvcs.opensso.ObjectNotFound) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.GeneralFailure) {
              throw (com.sun.identity.idsvcs.opensso.GeneralFailure) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.TokenExpired) {
              throw (com.sun.identity.idsvcs.opensso.TokenExpired) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.sun.identity.idsvcs.opensso.AccessDenied) {
              throw (com.sun.identity.idsvcs.opensso.AccessDenied) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
