//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.07 at 01:35:19 AM EDT 
//


package com.osbitools.ws.shared.binding.ds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataSetDescr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataSetDescr">
 *   &lt;complexContent>
 *     &lt;extension base="{}DataSetExt">
 *       &lt;sequence>
 *         &lt;element name="req_params" type="{}RequestParameters" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="group_data" type="{}GroupData"/>
 *           &lt;element name="static_data" type="{}StaticData"/>
 *           &lt;element name="csv_data" type="{}CsvData"/>
 *           &lt;element name="sql_data" type="{}SqlData"/>
 *           &lt;element name="xml_data" type="{}XmlData"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="descr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="ver_max" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ver_min" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSetDescr", propOrder = {
    "reqParams",
    "groupData",
    "staticData",
    "csvData",
    "sqlData",
    "xmlData"
})
public class DataSetDescr
    extends DataSetExt
{

    @XmlElement(name = "req_params")
    protected RequestParameters reqParams;
    @XmlElement(name = "group_data")
    protected GroupData groupData;
    @XmlElement(name = "static_data")
    protected StaticData staticData;
    @XmlElement(name = "csv_data")
    protected CsvData csvData;
    @XmlElement(name = "sql_data")
    protected SqlData sqlData;
    @XmlElement(name = "xml_data")
    protected XmlData xmlData;
    @XmlAttribute(name = "descr")
    protected String descr;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "ver_max", required = true)
    protected int verMax;
    @XmlAttribute(name = "ver_min", required = true)
    protected int verMin;

    /**
     * Gets the value of the reqParams property.
     * 
     * @return
     *     possible object is
     *     {@link RequestParameters }
     *     
     */
    public RequestParameters getReqParams() {
        return reqParams;
    }

    /**
     * Sets the value of the reqParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestParameters }
     *     
     */
    public void setReqParams(RequestParameters value) {
        this.reqParams = value;
    }

    /**
     * Gets the value of the groupData property.
     * 
     * @return
     *     possible object is
     *     {@link GroupData }
     *     
     */
    public GroupData getGroupData() {
        return groupData;
    }

    /**
     * Sets the value of the groupData property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupData }
     *     
     */
    public void setGroupData(GroupData value) {
        this.groupData = value;
    }

    /**
     * Gets the value of the staticData property.
     * 
     * @return
     *     possible object is
     *     {@link StaticData }
     *     
     */
    public StaticData getStaticData() {
        return staticData;
    }

    /**
     * Sets the value of the staticData property.
     * 
     * @param value
     *     allowed object is
     *     {@link StaticData }
     *     
     */
    public void setStaticData(StaticData value) {
        this.staticData = value;
    }

    /**
     * Gets the value of the csvData property.
     * 
     * @return
     *     possible object is
     *     {@link CsvData }
     *     
     */
    public CsvData getCsvData() {
        return csvData;
    }

    /**
     * Sets the value of the csvData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CsvData }
     *     
     */
    public void setCsvData(CsvData value) {
        this.csvData = value;
    }

    /**
     * Gets the value of the sqlData property.
     * 
     * @return
     *     possible object is
     *     {@link SqlData }
     *     
     */
    public SqlData getSqlData() {
        return sqlData;
    }

    /**
     * Sets the value of the sqlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SqlData }
     *     
     */
    public void setSqlData(SqlData value) {
        this.sqlData = value;
    }

    /**
     * Gets the value of the xmlData property.
     * 
     * @return
     *     possible object is
     *     {@link XmlData }
     *     
     */
    public XmlData getXmlData() {
        return xmlData;
    }

    /**
     * Sets the value of the xmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlData }
     *     
     */
    public void setXmlData(XmlData value) {
        this.xmlData = value;
    }

    /**
     * Gets the value of the descr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Sets the value of the descr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescr(String value) {
        this.descr = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the verMax property.
     * 
     */
    public int getVerMax() {
        return verMax;
    }

    /**
     * Sets the value of the verMax property.
     * 
     */
    public void setVerMax(int value) {
        this.verMax = value;
    }

    /**
     * Gets the value of the verMin property.
     * 
     */
    public int getVerMin() {
        return verMin;
    }

    /**
     * Sets the value of the verMin property.
     * 
     */
    public void setVerMin(int value) {
        this.verMin = value;
    }

}