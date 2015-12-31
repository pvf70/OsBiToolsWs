//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.21 at 10:01:17 PM EDT 
//


package com.osbitools.ws.shared.binding.wp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.osbitools.ws.shared.binding.wp package. 
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

    private final static QName _Wp_QNAME = new QName("", "wp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.osbitools.ws.shared.binding.wp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WebPage }
     * 
     */
    public WebPage createWebPage() {
        return new WebPage();
    }

    /**
     * Create an instance of {@link WebWidgetContainer }
     * 
     */
    public WebWidgetContainer createWebWidgetContainer() {
        return new WebWidgetContainer();
    }

    /**
     * Create an instance of {@link WebWidgetProperty }
     * 
     */
    public WebWidgetProperty createWebWidgetProperty() {
        return new WebWidgetProperty();
    }

    /**
     * Create an instance of {@link ComponentPanel }
     * 
     */
    public ComponentPanel createComponentPanel() {
        return new ComponentPanel();
    }

    /**
     * Create an instance of {@link ComponentPanels }
     * 
     */
    public ComponentPanels createComponentPanels() {
        return new ComponentPanels();
    }

    /**
     * Create an instance of {@link WebWidget }
     * 
     */
    public WebWidget createWebWidget() {
        return new WebWidget();
    }

    /**
     * Create an instance of {@link WebWidgetChart }
     * 
     */
    public WebWidgetChart createWebWidgetChart() {
        return new WebWidgetChart();
    }

    /**
     * Create an instance of {@link WebWidgetProperties }
     * 
     */
    public WebWidgetProperties createWebWidgetProperties() {
        return new WebWidgetProperties();
    }

    /**
     * Create an instance of {@link WebWidgetPropertiesGroups }
     * 
     */
    public WebWidgetPropertiesGroups createWebWidgetPropertiesGroups() {
        return new WebWidgetPropertiesGroups();
    }

    /**
     * Create an instance of {@link WebWidgetControl }
     * 
     */
    public WebWidgetControl createWebWidgetControl() {
        return new WebWidgetControl();
    }

    /**
     * Create an instance of {@link WebWidgetPropertiesGroup }
     * 
     */
    public WebWidgetPropertiesGroup createWebWidgetPropertiesGroup() {
        return new WebWidgetPropertiesGroup();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebPage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "wp")
    public JAXBElement<WebPage> createWp(WebPage value) {
        return new JAXBElement<WebPage>(_Wp_QNAME, WebPage.class, null, value);
    }

}
