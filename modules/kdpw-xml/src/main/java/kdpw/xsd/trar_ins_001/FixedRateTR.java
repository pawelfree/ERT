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


/**
 * Fixed rate related information.
 * 
 * <p>Java class for FixedRate_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FixedRate_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Rate" type="{urn:kdpw:xsd:trar.ins.001.03}PercentageRate"/&gt;
 *         &lt;element name="DayCnt" type="{urn:kdpw:xsd:trar.ins.001.03}ESMADayCount"/&gt;
 *         &lt;element name="PmtFrqcyTmPrd" type="{urn:kdpw:xsd:trar.ins.001.03}RateBasis1Code_TR" minOccurs="0"/&gt;
 *         &lt;element name="PmtFrqcyMltplr" type="{urn:kdpw:xsd:trar.ins.001.03}Max3Number" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FixedRate_TR", propOrder = {
    "rate",
    "dayCnt",
    "pmtFrqcyTmPrd",
    "pmtFrqcyMltplr"
})
public class FixedRateTR {

    @XmlElement(name = "Rate", required = true)
    protected BigDecimal rate;
    @XmlElement(name = "DayCnt", required = true)
    protected String dayCnt;
    @XmlElement(name = "PmtFrqcyTmPrd")
    @XmlSchemaType(name = "string")
    protected RateBasis1CodeTR pmtFrqcyTmPrd;
    @XmlElement(name = "PmtFrqcyMltplr")
    protected BigDecimal pmtFrqcyMltplr;

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

    /**
     * Gets the value of the dayCnt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayCnt() {
        return dayCnt;
    }

    /**
     * Sets the value of the dayCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayCnt(String value) {
        this.dayCnt = value;
    }

    /**
     * Gets the value of the pmtFrqcyTmPrd property.
     * 
     * @return
     *     possible object is
     *     {@link RateBasis1CodeTR }
     *     
     */
    public RateBasis1CodeTR getPmtFrqcyTmPrd() {
        return pmtFrqcyTmPrd;
    }

    /**
     * Sets the value of the pmtFrqcyTmPrd property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateBasis1CodeTR }
     *     
     */
    public void setPmtFrqcyTmPrd(RateBasis1CodeTR value) {
        this.pmtFrqcyTmPrd = value;
    }

    /**
     * Gets the value of the pmtFrqcyMltplr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPmtFrqcyMltplr() {
        return pmtFrqcyMltplr;
    }

    /**
     * Sets the value of the pmtFrqcyMltplr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPmtFrqcyMltplr(BigDecimal value) {
        this.pmtFrqcyMltplr = value;
    }

}
