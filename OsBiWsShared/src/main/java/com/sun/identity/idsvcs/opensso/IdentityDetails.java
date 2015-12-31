/**
 * IdentityDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sun.identity.idsvcs.opensso;

public class IdentityDetails  implements java.io.Serializable {

	// Default serial version uid
	private static final long serialVersionUID = 1L;

	private java.lang.String name;

    private java.lang.String type;

    private java.lang.String realm;

    private java.lang.String[] roleList;

    private java.lang.String[] groupList;

    private java.lang.String[] memberList;

    private com.sun.identity.idsvcs.opensso.Attribute[] attributes;

    public IdentityDetails() {
    }

    public IdentityDetails(
           java.lang.String name,
           java.lang.String type,
           java.lang.String realm,
           java.lang.String[] roleList,
           java.lang.String[] groupList,
           java.lang.String[] memberList,
           com.sun.identity.idsvcs.opensso.Attribute[] attributes) {
           this.name = name;
           this.type = type;
           this.realm = realm;
           this.roleList = roleList;
           this.groupList = groupList;
           this.memberList = memberList;
           this.attributes = attributes;
    }


    /**
     * Gets the name value for this IdentityDetails.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this IdentityDetails.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the type value for this IdentityDetails.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this IdentityDetails.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the realm value for this IdentityDetails.
     * 
     * @return realm
     */
    public java.lang.String getRealm() {
        return realm;
    }


    /**
     * Sets the realm value for this IdentityDetails.
     * 
     * @param realm
     */
    public void setRealm(java.lang.String realm) {
        this.realm = realm;
    }


    /**
     * Gets the roleList value for this IdentityDetails.
     * 
     * @return roleList
     */
    public java.lang.String[] getRoleList() {
        return roleList;
    }


    /**
     * Sets the roleList value for this IdentityDetails.
     * 
     * @param roleList
     */
    public void setRoleList(java.lang.String[] roleList) {
        this.roleList = roleList;
    }


    /**
     * Gets the groupList value for this IdentityDetails.
     * 
     * @return groupList
     */
    public java.lang.String[] getGroupList() {
        return groupList;
    }


    /**
     * Sets the groupList value for this IdentityDetails.
     * 
     * @param groupList
     */
    public void setGroupList(java.lang.String[] groupList) {
        this.groupList = groupList;
    }


    /**
     * Gets the memberList value for this IdentityDetails.
     * 
     * @return memberList
     */
    public java.lang.String[] getMemberList() {
        return memberList;
    }


    /**
     * Sets the memberList value for this IdentityDetails.
     * 
     * @param memberList
     */
    public void setMemberList(java.lang.String[] memberList) {
        this.memberList = memberList;
    }


    /**
     * Gets the attributes value for this IdentityDetails.
     * 
     * @return attributes
     */
    public com.sun.identity.idsvcs.opensso.Attribute[] getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this IdentityDetails.
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

    private java.lang.Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentityDetails)) return false;
        IdentityDetails other = (IdentityDetails) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.realm==null && other.getRealm()==null) || 
             (this.realm!=null &&
              this.realm.equals(other.getRealm()))) &&
            ((this.roleList==null && other.getRoleList()==null) || 
             (this.roleList!=null &&
              java.util.Arrays.equals(this.roleList, other.getRoleList()))) &&
            ((this.groupList==null && other.getGroupList()==null) || 
             (this.groupList!=null &&
              java.util.Arrays.equals(this.groupList, other.getGroupList()))) &&
            ((this.memberList==null && other.getMemberList()==null) || 
             (this.memberList!=null &&
              java.util.Arrays.equals(this.memberList, other.getMemberList()))) &&
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              java.util.Arrays.equals(this.attributes, other.getAttributes())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getRealm() != null) {
            _hashCode += getRealm().hashCode();
        }
        if (getRoleList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoleList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoleList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMemberList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMemberList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMemberList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentityDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "identityDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("realm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "realm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roleList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "elements"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "elements"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "memberList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "elements"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://opensso.idsvcs.identity.sun.com/", "attribute"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
