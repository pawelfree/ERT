//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.14 at 01:33:12 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Data set related to MIFIR regulation requirements.
 * 
 * <p>Java class for MIFIRTransactionData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MIFIRTransactionData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TradgCpcty" type="{urn:kdpw:xsd:trar.ins.001.03}RegulatoryTradingCapacity1Code"/&gt;
 *         &lt;element name="DerivNtnlChng" type="{urn:kdpw:xsd:trar.ins.001.03}VariationType1Code" minOccurs="0"/&gt;
 *         &lt;element name="CtryOfBrnch" type="{urn:kdpw:xsd:trar.ins.001.03}CountryCode" minOccurs="0"/&gt;
 *         &lt;element name="UpFrntPmtCcy" type="{urn:kdpw:xsd:trar.ins.001.03}ActiveCurrencyCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MIFIRTransactionData", propOrder = {
    "tradgCpcty",
    "derivNtnlChng",
    "ctryOfBrnch",
    "upFrntPmtCcy"
})
public class MIFIRTransactionData {

    @XmlElement(name = "TradgCpcty", required = true)
    @XmlSchemaType(name = "string")
    protected RegulatoryTradingCapacity1Code tradgCpcty;
    @XmlElement(name = "DerivNtnlChng")
    @XmlSchemaType(name = "string")
    protected VariationType1Code derivNtnlChng;
    @XmlElement(name = "CtryOfBrnch")
    protected String ctryOfBrnch;
    @XmlElement(name = "UpFrntPmtCcy")
    protected String upFrntPmtCcy;

    /**
     * Gets the value of the tradgCpcty property.
     * 
     * @return
     *     possible object is
     *     {@link RegulatoryTradingCapacity1Code }
     *     
     */
    public RegulatoryTradingCapacity1Code getTradgCpcty() {
        return tradgCpcty;
    }

    /**
     * Sets the value of the tradgCpcty property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegulatoryTradingCapacity1Code }
     *     
     */
    public void setTradgCpcty(RegulatoryTradingCapacity1Code value) {
        this.tradgCpcty = value;
    }

    /**
     * Gets the value of the derivNtnlChng property.
     * 
     * @return
     *     possible object is
     *     {@link VariationType1Code }
     *     
     */
    public VariationType1Code getDerivNtnlChng() {
        return derivNtnlChng;
    }

    /**
     * Sets the value of the derivNtnlChng property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariationType1Code }
     *     
     */
    public void setDerivNtnlChng(VariationType1Code value) {
        this.derivNtnlChng = value;
    }

    /**
     * Gets the value of the ctryOfBrnch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtryOfBrnch() {
        return ctryOfBrnch;
    }

    /**
     * Sets the value of the ctryOfBrnch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtryOfBrnch(String value) {
        this.ctryOfBrnch = value;
    }

    /**
     * Gets the value of the upFrntPmtCcy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpFrntPmtCcy() {
        return upFrntPmtCcy;
    }

    /**
     * Sets the value of the upFrntPmtCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpFrntPmtCcy(String value) {
        this.upFrntPmtCcy = value;
    }

}