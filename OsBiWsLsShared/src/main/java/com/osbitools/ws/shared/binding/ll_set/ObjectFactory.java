//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.06 at 01:29:02 PM EDT 
//


package com.osbitools.ws.shared.binding.ll_set;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.osbitools.ws.shared.binding.ll_set package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LlSet_QNAME = new QName("", "ll_set");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.osbitools.ws.shared.binding.ll_set
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LangLabelsSet }
     * 
     */
    public LangLabelsSet createLangLabelsSet() {
        return new LangLabelsSet();
    }

    /**
     * Create an instance of {@link LangLabel }
     * 
     */
    public LangLabel createLangLabel() {
        return new LangLabel();
    }

    /**
     * Create an instance of {@link LangLabelDef }
     * 
     */
    public LangLabelDef createLangLabelDef() {
        return new LangLabelDef();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LangLabelsSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ll_set")
    public JAXBElement<LangLabelsSet> createLlSet(LangLabelsSet value) {
        return new JAXBElement<LangLabelsSet>(_LlSet_QNAME, LangLabelsSet.class, null, value);
    }

}
