//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:19 PM CEST 
//


package kdpw.xsd.admi_err_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Informacje ogólne
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
 *         &lt;element name="SndrMsgRef" type="{urn:kdpw:xsd:admi.err.001.01}Max16Text"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:kdpw:xsd:admi.err.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
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
    "creDtTm"
})
public class GeneralInformation {

    @XmlElement(name = "SndrMsgRef", required = true)
    protected String sndrMsgRef;
    @XmlElement(name = "CreDtTm")
    protected DateAndDateTimeChoice creDtTm;

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
     * Gets the value of the creDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setCreDtTm(DateAndDateTimeChoice value) {
        this.creDtTm = value;
    }

}
