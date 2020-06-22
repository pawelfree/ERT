//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:18 PM CEST 
//


package kdpw.xsd.trar_rcn_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * General information
 * 
 * <p>Java class for GeneralInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneralInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SndrMsgRef" type="{urn:kdpw:xsd:trar.rcn.001.03}Max16Text"/&gt;
 *         &lt;element name="RepTmStmp" type="{urn:kdpw:xsd:trar.rcn.001.03}ISODateTime"/&gt;
 *         &lt;element name="ParDt" type="{urn:kdpw:xsd:trar.rcn.001.03}ISODate"/&gt;
 *         &lt;element name="CompDt" type="{urn:kdpw:xsd:trar.rcn.001.03}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="EligDt" type="{urn:kdpw:xsd:trar.rcn.001.03}ISODate"/&gt;
 *         &lt;element name="Lnk" type="{urn:kdpw:xsd:trar.rcn.001.03}Linkages"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneralInformation", propOrder = {
    "sndrMsgRef",
    "repTmStmp",
    "parDt",
    "compDt",
    "eligDt",
    "lnk"
})
public class GeneralInformation {

    @XmlElement(name = "SndrMsgRef", required = true)
    protected String sndrMsgRef;
    @XmlElement(name = "RepTmStmp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar repTmStmp;
    @XmlElement(name = "ParDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar parDt;
    @XmlElement(name = "CompDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar compDt;
    @XmlElement(name = "EligDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eligDt;
    @XmlElement(name = "Lnk", required = true)
    protected Linkages lnk;

    /**
     * Gets the value of the sndrMsgRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

    /**
     * Sets the value of the sndrMsgRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSndrMsgRef(String value) {
        this.sndrMsgRef = value;
    }

    /**
     * Gets the value of the repTmStmp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRepTmStmp() {
        return repTmStmp;
    }

    /**
     * Sets the value of the repTmStmp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRepTmStmp(XMLGregorianCalendar value) {
        this.repTmStmp = value;
    }

    /**
     * Gets the value of the parDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getParDt() {
        return parDt;
    }

    /**
     * Sets the value of the parDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setParDt(XMLGregorianCalendar value) {
        this.parDt = value;
    }

    /**
     * Gets the value of the compDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompDt() {
        return compDt;
    }

    /**
     * Sets the value of the compDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompDt(XMLGregorianCalendar value) {
        this.compDt = value;
    }

    /**
     * Gets the value of the eligDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEligDt() {
        return eligDt;
    }

    /**
     * Sets the value of the eligDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEligDt(XMLGregorianCalendar value) {
        this.eligDt = value;
    }

    /**
     * Gets the value of the lnk property.
     * 
     * @return
     *     possible object is
     *     {@link Linkages }
     *     
     */
    public Linkages getLnk() {
        return lnk;
    }

    /**
     * Sets the value of the lnk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Linkages }
     *     
     */
    public void setLnk(Linkages value) {
        this.lnk = value;
    }

}
