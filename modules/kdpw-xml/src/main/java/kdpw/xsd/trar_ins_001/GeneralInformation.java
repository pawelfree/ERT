//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.29 at 02:25:35 PM CEST 
//


package kdpw.xsd.trar_ins_001;

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
 *         &lt;element name="RptgNtty" type="{urn:kdpw:xsd:trar.ins.001.04}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="SndrMsgRef" type="{urn:kdpw:xsd:trar.ins.001.04}Max16Text"/&gt;
 *         &lt;element name="RepTmStmp" type="{urn:kdpw:xsd:trar.ins.001.04}ISONormalisedDateTime"/&gt;
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
    "rptgNtty",
    "sndrMsgRef",
    "repTmStmp"
})
public class GeneralInformation {

    @XmlElement(name = "RptgNtty")
    protected String rptgNtty;
    @XmlElement(name = "SndrMsgRef", required = true)
    protected String sndrMsgRef;
    @XmlElement(name = "RepTmStmp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar repTmStmp;

    /**
     * Gets the value of the rptgNtty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRptgNtty() {
        return rptgNtty;
    }

    /**
     * Sets the value of the rptgNtty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRptgNtty(String value) {
        this.rptgNtty = value;
    }

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

}
