//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.14 at 01:33:12 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related specifically to credit derivatives attributes.
 * 
 * <p>Java class for CreditDerivative_TR_M complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditDerivative_TR_M"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Snrty" type="{urn:kdpw:xsd:trar.ins.001.03}DebtInstrumentSeniorityType2Code" minOccurs="0"/&gt;
 *         &lt;element name="RefPty" type="{urn:kdpw:xsd:trar.ins.001.03}ReferenceParty" minOccurs="0"/&gt;
 *         &lt;element name="PmtFrqcy" type="{urn:kdpw:xsd:trar.ins.001.03}Frequency8Code" minOccurs="0"/&gt;
 *         &lt;element name="ClctnBsis" type="{urn:kdpw:xsd:trar.ins.001.03}ESMADayCount" minOccurs="0"/&gt;
 *         &lt;element name="Srs" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAPositiveInteger5" minOccurs="0"/&gt;
 *         &lt;element name="Vrsn" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAPositiveInteger5" minOccurs="0"/&gt;
 *         &lt;element name="IndxFctr" type="{urn:kdpw:xsd:trar.ins.001.03}PercentageRate" minOccurs="0"/&gt;
 *         &lt;element name="Trch" type="{urn:kdpw:xsd:trar.ins.001.03}TrancheIndicator" minOccurs="0"/&gt;
 *         &lt;element name="AttchmntPt" type="{urn:kdpw:xsd:trar.ins.001.03}PercentageRate" minOccurs="0"/&gt;
 *         &lt;element name="DtchmntPt" type="{urn:kdpw:xsd:trar.ins.001.03}PercentageRate" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditDerivative_TR_M", propOrder = {
    "snrty",
    "refPty",
    "pmtFrqcy",
    "clctnBsis",
    "srs",
    "vrsn",
    "indxFctr",
    "trch",
    "attchmntPt",
    "dtchmntPt"
})
public class CreditDerivativeTRM {

    @XmlElement(name = "Snrty")
    @XmlSchemaType(name = "string")
    protected DebtInstrumentSeniorityType2Code snrty;
    @XmlElement(name = "RefPty")
    protected ReferenceParty refPty;
    @XmlElement(name = "PmtFrqcy")
    @XmlSchemaType(name = "string")
    protected Frequency8Code pmtFrqcy;
    @XmlElement(name = "ClctnBsis")
    protected String clctnBsis;
    @XmlElement(name = "Srs")
    protected BigDecimal srs;
    @XmlElement(name = "Vrsn")
    protected BigDecimal vrsn;
    @XmlElement(name = "IndxFctr")
    protected BigDecimal indxFctr;
    @XmlElement(name = "Trch")
    @XmlSchemaType(name = "string")
    protected TrancheIndicator trch;
    @XmlElement(name = "AttchmntPt")
    protected BigDecimal attchmntPt;
    @XmlElement(name = "DtchmntPt")
    protected BigDecimal dtchmntPt;

    /**
     * Gets the value of the snrty property.
     * 
     * @return
     *     possible object is
     *     {@link DebtInstrumentSeniorityType2Code }
     *     
     */
    public DebtInstrumentSeniorityType2Code getSnrty() {
        return snrty;
    }

    /**
     * Sets the value of the snrty property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebtInstrumentSeniorityType2Code }
     *     
     */
    public void setSnrty(DebtInstrumentSeniorityType2Code value) {
        this.snrty = value;
    }

    /**
     * Gets the value of the refPty property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceParty }
     *     
     */
    public ReferenceParty getRefPty() {
        return refPty;
    }

    /**
     * Sets the value of the refPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceParty }
     *     
     */
    public void setRefPty(ReferenceParty value) {
        this.refPty = value;
    }

    /**
     * Gets the value of the pmtFrqcy property.
     * 
     * @return
     *     possible object is
     *     {@link Frequency8Code }
     *     
     */
    public Frequency8Code getPmtFrqcy() {
        return pmtFrqcy;
    }

    /**
     * Sets the value of the pmtFrqcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Frequency8Code }
     *     
     */
    public void setPmtFrqcy(Frequency8Code value) {
        this.pmtFrqcy = value;
    }

    /**
     * Gets the value of the clctnBsis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClctnBsis() {
        return clctnBsis;
    }

    /**
     * Sets the value of the clctnBsis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClctnBsis(String value) {
        this.clctnBsis = value;
    }

    /**
     * Gets the value of the srs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSrs() {
        return srs;
    }

    /**
     * Sets the value of the srs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSrs(BigDecimal value) {
        this.srs = value;
    }

    /**
     * Gets the value of the vrsn property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrsn() {
        return vrsn;
    }

    /**
     * Sets the value of the vrsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrsn(BigDecimal value) {
        this.vrsn = value;
    }

    /**
     * Gets the value of the indxFctr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIndxFctr() {
        return indxFctr;
    }

    /**
     * Sets the value of the indxFctr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIndxFctr(BigDecimal value) {
        this.indxFctr = value;
    }

    /**
     * Gets the value of the trch property.
     * 
     * @return
     *     possible object is
     *     {@link TrancheIndicator }
     *     
     */
    public TrancheIndicator getTrch() {
        return trch;
    }

    /**
     * Sets the value of the trch property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrancheIndicator }
     *     
     */
    public void setTrch(TrancheIndicator value) {
        this.trch = value;
    }

    /**
     * Gets the value of the attchmntPt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAttchmntPt() {
        return attchmntPt;
    }

    /**
     * Sets the value of the attchmntPt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAttchmntPt(BigDecimal value) {
        this.attchmntPt = value;
    }

    /**
     * Gets the value of the dtchmntPt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDtchmntPt() {
        return dtchmntPt;
    }

    /**
     * Sets the value of the dtchmntPt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDtchmntPt(BigDecimal value) {
        this.dtchmntPt = value;
    }

}
