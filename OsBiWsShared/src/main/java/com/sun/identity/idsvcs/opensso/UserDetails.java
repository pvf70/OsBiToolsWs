/**
 * UserDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sun.identity.idsvcs.opensso;

public class UserDetails  implements java.io.Serializable {

	// Default serial version uid
	private static final long serialVersionUID = 1L;

	private com.sun.identity.idsvcs.opensso.Attribute[] attributes;

    private java.lang.String[] roles;

    private com.sun.identity.idsvcs.opensso.Token token;

    public UserDetails() {
    }

    public UserDetails(
           com.sun.identity.idsvcs.opensso.Attribute[] attributes,
           java.lang.String[] roles,
           com.sun.identity.idsvcs.opensso.Token token) {
           this.attributes = attributes;
           this.roles = roles;
           this.token = token;
    }


    /**
     * Gets the attributes value for this UserDetails.
     * 
     * @return attributes
     */
    public com.sun.identity.idsvcs.opensso.Attribute[] getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this UserDetails.
     * 
     * @param attributes
     */
    public void setAttributes(com.sun.identity.idsvcs.opensso.Attribute[] attributes) {
        this.attributes = attributes;
    }

    public com.sun.identity.idsvcs.opensso.Attribute getAttributes(int i) {
        return this.attributes[i];
    }

    public void setAttributes(int i, com.sun.identity.idsvcs.opensso.Attribute _value) {
        this.attributes[i] = _value;
    }


    /**
     * Gets the roles value for this UserDetails.
     * 
     * @return roles
     */
    public java.lang.String[] getRoles() {
        return roles;
    }


    /**
     * Sets the roles value for this UserDetails.
     * 
     * @param roles
     */
    public void setRoles(java.lang.String[] roles) {
        this.roles = roles;
    }

    public java.lang.String getRoles(int i) {
        return this.roles[i];
    }

    public void setRoles(int i, java.lang.String _value) {
        this.roles[i] = _value;
    }


    /**
     * Gets the token value for this UserDetails.
     * 
     * @return token
     */
    public com.sun.identity.idsvcs.opensso.Token getToken() {
        return token;
    }


    /**
     * Sets the token value for this UserDetails.
     * 
     * @param token
     */
    public void setToken(com.sun.identity.idsvcs.opensso.Token token) {
        this.token = token;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserDetails)) return false;
        UserDetails other = (UserDetails) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              java.util.Arrays.equals(this.attributes, other.getAttributes()))) &&
            ((this.roles==null && other.getRoles()==null) || 
             (this.roles!=null &&
              java.util.Arrays.equals(this.roles, other.getRoles()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAttributes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttributes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttributes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRoles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "userDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attribute"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("", "token"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "token"));
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
           java.lang.Class<?> _javaType,  
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
           java.lang.Class<?> _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
