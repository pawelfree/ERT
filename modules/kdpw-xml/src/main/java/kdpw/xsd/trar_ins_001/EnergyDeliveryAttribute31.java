//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:49 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Information related to energy derivatives attributes.
 * 
 * <p>Java class for EnergyDeliveryAttribute3__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnergyDeliveryAttribute3__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DlvryIntrvl" type="{urn:kdpw:xsd:trar.ins.001.03}ESMANormalisedTime" minOccurs="0"/&gt;
 *         &lt;element name="DlvryPrd" type="{urn:kdpw:xsd:trar.ins.001.03}Period10__1" minOccurs="0"/&gt;
 *         &lt;element name="Drtn" type="{urn:kdpw:xsd:trar.ins.001.03}DurationType1Code_TR" minOccurs="0"/&gt;
 *         &lt;element name="WkDay" type="{urn:kdpw:xsd:trar.ins.001.03}WeekDay1Code_TR" minOccurs="0"/&gt;
 *         &lt;element name="DlvryCpcty" type="{urn:kdpw:xsd:trar.ins.001.03}Amount20_SimpleType_Negative" minOccurs="0"/&gt;
 *         &lt;element name="QtyUnit" type="{urn:kdpw:xsd:trar.ins.001.03}EnergyQuantityUnit_TR" minOccurs="0"/&gt;
 *         &lt;element name="PricTmIntrvlQty" type="{urn:kdpw:xsd:trar.ins.001.03}Amount20_SimpleType_Negative" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnergyDeliveryAttribute3__1", propOrder = {
    "dlvryIntrvl",
    "dlvryPrd",
    "drtn",
    "wkDay",
    "dlvryCpcty",
    "qtyUnit",
    "pricTmIntrvlQty"
})
public class EnergyDeliveryAttribute31 {

    @XmlElement(name = "DlvryIntrvl")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar dlvryIntrvl;
    @XmlElement(name = "DlvryPrd")
    protected Period101 dlvryPrd;
    @XmlElement(name = "Drtn")
    @XmlSchemaType(name = "string")
    protected DurationType1CodeTR drtn;
    @XmlElement(name = "WkDay")
    @XmlSchemaType(name = "string")
    protected WeekDay1CodeTR wkDay;
    @XmlElement(name = "DlvryCpcty")
    protected BigDecimal dlvryCpcty;
    @XmlElement(name = "QtyUnit")
    @XmlSchemaType(name = "string")
    protected EnergyQuantityUnitTR qtyUnit;
    @XmlElement(name = "PricTmIntrvlQty")
    protected BigDecimal pricTmIntrvlQty;

    /**
     * Gets the value of the dlvryIntrvl property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDlvryIntrvl() {
        return dlvryIntrvl;
    }

    /**
     * Sets the value of the dlvryIntrvl property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDlvryIntrvl(XMLGregorianCalendar value) {
        this.dlvryIntrvl = value;
    }

    /**
     * Gets the value of the dlvryPrd property.
     * 
     * @return
     *     possible object is
     *     {@link Period101 }
     *     
     */
    public Period101 getDlvryPrd() {
        return dlvryPrd;
    }

    /**
     * Sets the value of the dlvryPrd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period101 }
     *     
     */
    public void setDlvryPrd(Period101 value) {
        this.dlvryPrd = value;
    }

    /**
     * Gets the value of the drtn property.
     * 
     * @return
     *     possible object is
     *     {@link DurationType1CodeTR }
     *     
     */
    public DurationType1CodeTR getDrtn() {
        return drtn;
    }

    /**
     * Sets the value of the drtn property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurationType1CodeTR }
     *     
     */
    public void setDrtn(DurationType1CodeTR value) {
        this.drtn = value;
    }

    /**
     * Gets the value of the wkDay property.
     * 
     * @return
     *     possible object is
     *     {@link WeekDay1CodeTR }
     *     
     */
    public WeekDay1CodeTR getWkDay() {
        return wkDay;
    }

    /**
     * Sets the value of the wkDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDay1CodeTR }
     *     
     */
    public void setWkDay(WeekDay1CodeTR value) {
        this.wkDay = value;
    }

    /**
     * Gets the value of the dlvryCpcty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDlvryCpcty() {
        return dlvryCpcty;
    }

    /**
     * Sets the value of the dlvryCpcty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDlvryCpcty(BigDecimal value) {
        this.dlvryCpcty = value;
    }

    /**
     * Gets the value of the qtyUnit property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyQuantityUnitTR }
     *     
     */
    public EnergyQuantityUnitTR getQtyUnit() {
        return qtyUnit;
    }

    /**
     * Sets the value of the qtyUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyQuantityUnitTR }
     *     
     */
    public void setQtyUnit(EnergyQuantityUnitTR value) {
        this.qtyUnit = value;
    }

    /**
     * Gets the value of the pricTmIntrvlQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPricTmIntrvlQty() {
        return pricTmIntrvlQty;
    }

    /**
     * Sets the value of the pricTmIntrvlQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPricTmIntrvlQty(BigDecimal value) {
        this.pricTmIntrvlQty = value;
    }

}
