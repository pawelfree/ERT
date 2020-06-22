//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:18 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Information related to the clearing of the contract
 * 
 * <p>Java class for TradeClearing_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeClearing_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClrOblgtn" type="{urn:kdpw:xsd:trar.sts.001.04}ClearingObligationCode" minOccurs="0"/&gt;
 *         &lt;element name="Clrd" type="{urn:kdpw:xsd:trar.sts.001.04}YesNoIndicator"/&gt;
 *         &lt;element name="ClrDtTm" type="{urn:kdpw:xsd:trar.sts.001.04}ISONormalisedDateTime" minOccurs="0"/&gt;
 *         &lt;element name="CCP" type="{urn:kdpw:xsd:trar.sts.001.04}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="IntraGrp" type="{urn:kdpw:xsd:trar.sts.001.04}YesNoIndicator" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeClearing_TR", propOrder = {
    "clrOblgtn",
    "clrd",
    "clrDtTm",
    "ccp",
    "intraGrp"
})
public class TradeClearingTR {

    @XmlElement(name = "ClrOblgtn")
    @XmlSchemaType(name = "string")
    protected ClearingObligationCode clrOblgtn;
    @XmlElement(name = "Clrd")
    protected boolean clrd;
    @XmlElement(name = "ClrDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar clrDtTm;
    @XmlElement(name = "CCP")
    protected String ccp;
    @XmlElement(name = "IntraGrp")
    protected Boolean intraGrp;

    /**
     * Gets the value of the clrOblgtn property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingObligationCode }
     *     
     */
    public ClearingObligationCode getClrOblgtn() {
        return clrOblgtn;
    }

    /**
     * Sets the value of the clrOblgtn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingObligationCode }
     *     
     */
    public void setClrOblgtn(ClearingObligationCode value) {
        this.clrOblgtn = value;
    }

    /**
     * Gets the value of the clrd property.
     * 
     */
    public boolean isClrd() {
        return clrd;
    }

    /**
     * Sets the value of the clrd property.
     * 
     */
    public void setClrd(boolean value) {
        this.clrd = value;
    }

    /**
     * Gets the value of the clrDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClrDtTm() {
        return clrDtTm;
    }

    /**
     * Sets the value of the clrDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClrDtTm(XMLGregorianCalendar value) {
        this.clrDtTm = value;
    }

    /**
     * Gets the value of the ccp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCP() {
        return ccp;
    }

    /**
     * Sets the value of the ccp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCP(String value) {
        this.ccp = value;
    }

    /**
     * Gets the value of the intraGrp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIntraGrp() {
        return intraGrp;
    }

    /**
     * Sets the value of the intraGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIntraGrp(Boolean value) {
        this.intraGrp = value;
    }

}
