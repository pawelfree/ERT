//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Time span defined by a start date and time, and an end date and time.
 * 
 * <p>Java class for Period10__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Period10__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FrDtTm" type="{urn:kdpw:xsd:trar.ins.001.03}ISONormalisedDateTime"/&gt;
 *         &lt;element name="ToDtTm" type="{urn:kdpw:xsd:trar.ins.001.03}ISONormalisedDateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Period10__1", propOrder = {
    "frDtTm",
    "toDtTm"
})
public class Period101 {

    @XmlElement(name = "FrDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar frDtTm;
    @XmlElement(name = "ToDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toDtTm;

    /**
     * Gets the value of the frDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFrDtTm() {
        return frDtTm;
    }

    /**
     * Sets the value of the frDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFrDtTm(XMLGregorianCalendar value) {
        this.frDtTm = value;
    }

    /**
     * Gets the value of the toDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getToDtTm() {
        return toDtTm;
    }

    /**
     * Sets the value of the toDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setToDtTm(XMLGregorianCalendar value) {
        this.toDtTm = value;
    }

}
