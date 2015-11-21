//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 04:42:13 PM CEST 
//


package kdpw.xsd.trar_ntf_001;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Szczegóły o transakcji
 * 
 * <p>Java class for TradeAdditionalInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeAdditionalInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VenueOfExc" type="{urn:kdpw:xsd:trar.ntf.001.01}Max4Text" minOccurs="0"/&gt;
 *         &lt;element name="Cmprssn" type="{urn:kdpw:xsd:trar.ntf.001.01}Max1Text" minOccurs="0"/&gt;
 *         &lt;element name="Pric" type="{urn:kdpw:xsd:trar.ntf.001.01}PriceChoice" minOccurs="0"/&gt;
 *         &lt;element name="NmnlAmt" type="{urn:kdpw:xsd:trar.ntf.001.01}Max20Dec2Signed" minOccurs="0"/&gt;
 *         &lt;element name="PricMltplr" type="{urn:kdpw:xsd:trar.ntf.001.01}Max10Dec2" minOccurs="0"/&gt;
 *         &lt;element name="Qty" type="{urn:kdpw:xsd:trar.ntf.001.01}Max10Int" minOccurs="0"/&gt;
 *         &lt;element name="UpPmt" type="{urn:kdpw:xsd:trar.ntf.001.01}Max10Dec2Signed" minOccurs="0"/&gt;
 *         &lt;element name="DlvryTp" type="{urn:kdpw:xsd:trar.ntf.001.01}Max1Text" minOccurs="0"/&gt;
 *         &lt;element name="ExecDtTm" type="{urn:kdpw:xsd:trar.ntf.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="FctvDt" type="{urn:kdpw:xsd:trar.ntf.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="MtrtyDt" type="{urn:kdpw:xsd:trar.ntf.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="TrmntnDt" type="{urn:kdpw:xsd:trar.ntf.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="SttlmtDt" type="{urn:kdpw:xsd:trar.ntf.001.01}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="MstrAgrmntTp" type="{urn:kdpw:xsd:trar.ntf.001.01}Max50Text" minOccurs="0"/&gt;
 *         &lt;element name="MstrAgrmntVrsn" type="{urn:kdpw:xsd:trar.ntf.001.01}Max4Int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeAdditionalInformation", propOrder = {
    "venueOfExc",
    "cmprssn",
    "pric",
    "nmnlAmt",
    "pricMltplr",
    "qty",
    "upPmt",
    "dlvryTp",
    "execDtTm",
    "fctvDt",
    "mtrtyDt",
    "trmntnDt",
    "sttlmtDt",
    "mstrAgrmntTp",
    "mstrAgrmntVrsn"
})
public class TradeAdditionalInformation {

    @XmlElement(name = "VenueOfExc")
    protected String venueOfExc;
    @XmlElement(name = "Cmprssn")
    protected String cmprssn;
    @XmlElement(name = "Pric")
    protected PriceChoice pric;
    @XmlElement(name = "NmnlAmt")
    protected BigDecimal nmnlAmt;
    @XmlElement(name = "PricMltplr")
    protected BigDecimal pricMltplr;
    @XmlElement(name = "Qty")
    protected BigInteger qty;
    @XmlElement(name = "UpPmt")
    protected BigDecimal upPmt;
    @XmlElement(name = "DlvryTp")
    protected String dlvryTp;
    @XmlElement(name = "ExecDtTm")
    protected DateAndDateTimeChoice execDtTm;
    @XmlElement(name = "FctvDt")
    protected DateAndDateTimeChoice fctvDt;
    @XmlElement(name = "MtrtyDt")
    protected DateAndDateTimeChoice mtrtyDt;
    @XmlElement(name = "TrmntnDt")
    protected DateAndDateTimeChoice trmntnDt;
    @XmlElement(name = "SttlmtDt")
    protected DateAndDateTimeChoice sttlmtDt;
    @XmlElement(name = "MstrAgrmntTp")
    protected String mstrAgrmntTp;
    @XmlElement(name = "MstrAgrmntVrsn")
    protected BigInteger mstrAgrmntVrsn;

    /**
     * Gets the value of the venueOfExc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVenueOfExc() {
        return venueOfExc;
    }

    /**
     * Sets the value of the venueOfExc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVenueOfExc(String value) {
        this.venueOfExc = value;
    }

    /**
     * Gets the value of the cmprssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmprssn() {
        return cmprssn;
    }

    /**
     * Sets the value of the cmprssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmprssn(String value) {
        this.cmprssn = value;
    }

    /**
     * Gets the value of the pric property.
     * 
     * @return
     *     possible object is
     *     {@link PriceChoice }
     *     
     */
    public PriceChoice getPric() {
        return pric;
    }

    /**
     * Sets the value of the pric property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceChoice }
     *     
     */
    public void setPric(PriceChoice value) {
        this.pric = value;
    }

    /**
     * Gets the value of the nmnlAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNmnlAmt() {
        return nmnlAmt;
    }

    /**
     * Sets the value of the nmnlAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNmnlAmt(BigDecimal value) {
        this.nmnlAmt = value;
    }

    /**
     * Gets the value of the pricMltplr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPricMltplr() {
        return pricMltplr;
    }

    /**
     * Sets the value of the pricMltplr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPricMltplr(BigDecimal value) {
        this.pricMltplr = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQty(BigInteger value) {
        this.qty = value;
    }

    /**
     * Gets the value of the upPmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUpPmt() {
        return upPmt;
    }

    /**
     * Sets the value of the upPmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUpPmt(BigDecimal value) {
        this.upPmt = value;
    }

    /**
     * Gets the value of the dlvryTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDlvryTp() {
        return dlvryTp;
    }

    /**
     * Sets the value of the dlvryTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDlvryTp(String value) {
        this.dlvryTp = value;
    }

    /**
     * Gets the value of the execDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getExecDtTm() {
        return execDtTm;
    }

    /**
     * Sets the value of the execDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setExecDtTm(DateAndDateTimeChoice value) {
        this.execDtTm = value;
    }

    /**
     * Gets the value of the fctvDt property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getFctvDt() {
        return fctvDt;
    }

    /**
     * Sets the value of the fctvDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setFctvDt(DateAndDateTimeChoice value) {
        this.fctvDt = value;
    }

    /**
     * Gets the value of the mtrtyDt property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getMtrtyDt() {
        return mtrtyDt;
    }

    /**
     * Sets the value of the mtrtyDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setMtrtyDt(DateAndDateTimeChoice value) {
        this.mtrtyDt = value;
    }

    /**
     * Gets the value of the trmntnDt property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getTrmntnDt() {
        return trmntnDt;
    }

    /**
     * Sets the value of the trmntnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setTrmntnDt(DateAndDateTimeChoice value) {
        this.trmntnDt = value;
    }

    /**
     * Gets the value of the sttlmtDt property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getSttlmtDt() {
        return sttlmtDt;
    }

    /**
     * Sets the value of the sttlmtDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setSttlmtDt(DateAndDateTimeChoice value) {
        this.sttlmtDt = value;
    }

    /**
     * Gets the value of the mstrAgrmntTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstrAgrmntTp() {
        return mstrAgrmntTp;
    }

    /**
     * Sets the value of the mstrAgrmntTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstrAgrmntTp(String value) {
        this.mstrAgrmntTp = value;
    }

    /**
     * Gets the value of the mstrAgrmntVrsn property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMstrAgrmntVrsn() {
        return mstrAgrmntVrsn;
    }

    /**
     * Sets the value of the mstrAgrmntVrsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMstrAgrmntVrsn(BigInteger value) {
        this.mstrAgrmntVrsn = value;
    }

}
