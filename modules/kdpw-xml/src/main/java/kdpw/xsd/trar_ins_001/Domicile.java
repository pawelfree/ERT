//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.02 at 10:13:41 AM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Siedziba kontrahenta
 * 
 * <p>Java class for Domicile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Domicile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ctry" type="{urn:kdpw:xsd:trar.ins.001.02}CountryCodeOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="PstCd" type="{urn:kdpw:xsd:trar.ins.001.02}Max40TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="TwnNm" type="{urn:kdpw:xsd:trar.ins.001.02}Max60TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="StrtNm" type="{urn:kdpw:xsd:trar.ins.001.02}Max150TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="BldgId" type="{urn:kdpw:xsd:trar.ins.001.02}Max20TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="PrmsId" type="{urn:kdpw:xsd:trar.ins.001.02}Max20TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="DmclDtls" type="{urn:kdpw:xsd:trar.ins.001.02}Max208TextOrDelete" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="DeltnInd" type="{urn:kdpw:xsd:trar.ins.001.02}YesNoIndicator" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Domicile", propOrder = {
    "ctry",
    "pstCd",
    "twnNm",
    "strtNm",
    "bldgId",
    "prmsId",
    "dmclDtls"
})
public class Domicile {

    @XmlElement(name = "Ctry")
    protected CountryCodeOrDelete ctry;
    @XmlElement(name = "PstCd")
    protected Max40TextOrDelete pstCd;
    @XmlElement(name = "TwnNm")
    protected Max60TextOrDelete twnNm;
    @XmlElement(name = "StrtNm")
    protected Max150TextOrDelete strtNm;
    @XmlElement(name = "BldgId")
    protected Max20TextOrDelete bldgId;
    @XmlElement(name = "PrmsId")
    protected Max20TextOrDelete prmsId;
    @XmlElement(name = "DmclDtls")
    protected Max208TextOrDelete dmclDtls;
    @XmlAttribute(name = "DeltnInd")
    protected YesNoIndicator deltnInd;

    /**
     * Gets the value of the ctry property.
     * 
     * @return
     *     possible object is
     *     {@link CountryCodeOrDelete }
     *     
     */
    public CountryCodeOrDelete getCtry() {
        return ctry;
    }

    /**
     * Sets the value of the ctry property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryCodeOrDelete }
     *     
     */
    public void setCtry(CountryCodeOrDelete value) {
        this.ctry = value;
    }

    /**
     * Gets the value of the pstCd property.
     * 
     * @return
     *     possible object is
     *     {@link Max40TextOrDelete }
     *     
     */
    public Max40TextOrDelete getPstCd() {
        return pstCd;
    }

    /**
     * Sets the value of the pstCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max40TextOrDelete }
     *     
     */
    public void setPstCd(Max40TextOrDelete value) {
        this.pstCd = value;
    }

    /**
     * Gets the value of the twnNm property.
     * 
     * @return
     *     possible object is
     *     {@link Max60TextOrDelete }
     *     
     */
    public Max60TextOrDelete getTwnNm() {
        return twnNm;
    }

    /**
     * Sets the value of the twnNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max60TextOrDelete }
     *     
     */
    public void setTwnNm(Max60TextOrDelete value) {
        this.twnNm = value;
    }

    /**
     * Gets the value of the strtNm property.
     * 
     * @return
     *     possible object is
     *     {@link Max150TextOrDelete }
     *     
     */
    public Max150TextOrDelete getStrtNm() {
        return strtNm;
    }

    /**
     * Sets the value of the strtNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max150TextOrDelete }
     *     
     */
    public void setStrtNm(Max150TextOrDelete value) {
        this.strtNm = value;
    }

    /**
     * Gets the value of the bldgId property.
     * 
     * @return
     *     possible object is
     *     {@link Max20TextOrDelete }
     *     
     */
    public Max20TextOrDelete getBldgId() {
        return bldgId;
    }

    /**
     * Sets the value of the bldgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max20TextOrDelete }
     *     
     */
    public void setBldgId(Max20TextOrDelete value) {
        this.bldgId = value;
    }

    /**
     * Gets the value of the prmsId property.
     * 
     * @return
     *     possible object is
     *     {@link Max20TextOrDelete }
     *     
     */
    public Max20TextOrDelete getPrmsId() {
        return prmsId;
    }

    /**
     * Sets the value of the prmsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max20TextOrDelete }
     *     
     */
    public void setPrmsId(Max20TextOrDelete value) {
        this.prmsId = value;
    }

    /**
     * Gets the value of the dmclDtls property.
     * 
     * @return
     *     possible object is
     *     {@link Max208TextOrDelete }
     *     
     */
    public Max208TextOrDelete getDmclDtls() {
        return dmclDtls;
    }

    /**
     * Sets the value of the dmclDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max208TextOrDelete }
     *     
     */
    public void setDmclDtls(Max208TextOrDelete value) {
        this.dmclDtls = value;
    }

    /**
     * Gets the value of the deltnInd property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoIndicator }
     *     
     */
    public YesNoIndicator getDeltnInd() {
        return deltnInd;
    }

    /**
     * Sets the value of the deltnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoIndicator }
     *     
     */
    public void setDeltnInd(YesNoIndicator value) {
        this.deltnInd = value;
    }

}
