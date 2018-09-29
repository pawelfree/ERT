//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.29 at 02:25:37 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Details of collateral agreement between counterparties.
 * 
 * <p>Java class for TradeCollateralReport_TR_N complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeCollateralReport_TR_N"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Collstn" type="{urn:kdpw:xsd:trar.sts.001.03}CollateralisationType1Code"/&gt;
 *         &lt;element name="PrtflColl" type="{urn:kdpw:xsd:trar.sts.001.03}YesNoIndicator"/&gt;
 *         &lt;element name="Prtfl" type="{urn:kdpw:xsd:trar.sts.001.03}KDPWMax52AlphaNumericAdditionalCharacters" minOccurs="0"/&gt;
 *         &lt;element name="InitlMrgnPstd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *         &lt;element name="VartnMrgnPstd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *         &lt;element name="InitlMrgnRcvd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *         &lt;element name="VartnMrgnRcvd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *         &lt;element name="XcssCollPstd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *         &lt;element name="XcssCollRcvd" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20Amount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeCollateralReport_TR_N", propOrder = {
    "collstn",
    "prtflColl",
    "prtfl",
    "initlMrgnPstd",
    "vartnMrgnPstd",
    "initlMrgnRcvd",
    "vartnMrgnRcvd",
    "xcssCollPstd",
    "xcssCollRcvd"
})
public class TradeCollateralReportTRN {

    @XmlElement(name = "Collstn", required = true)
    @XmlSchemaType(name = "string")
    protected CollateralisationType1Code collstn;
    @XmlElement(name = "PrtflColl")
    protected boolean prtflColl;
    @XmlElement(name = "Prtfl")
    protected String prtfl;
    @XmlElement(name = "InitlMrgnPstd")
    protected ActiveCurrencyAnd20Amount initlMrgnPstd;
    @XmlElement(name = "VartnMrgnPstd")
    protected ActiveCurrencyAnd20Amount vartnMrgnPstd;
    @XmlElement(name = "InitlMrgnRcvd")
    protected ActiveCurrencyAnd20Amount initlMrgnRcvd;
    @XmlElement(name = "VartnMrgnRcvd")
    protected ActiveCurrencyAnd20Amount vartnMrgnRcvd;
    @XmlElement(name = "XcssCollPstd")
    protected ActiveCurrencyAnd20Amount xcssCollPstd;
    @XmlElement(name = "XcssCollRcvd")
    protected ActiveCurrencyAnd20Amount xcssCollRcvd;

    /**
     * Gets the value of the collstn property.
     * 
     * @return
     *     possible object is
     *     {@link CollateralisationType1Code }
     *     
     */
    public CollateralisationType1Code getCollstn() {
        return collstn;
    }

    /**
     * Sets the value of the collstn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollateralisationType1Code }
     *     
     */
    public void setCollstn(CollateralisationType1Code value) {
        this.collstn = value;
    }

    /**
     * Gets the value of the prtflColl property.
     * 
     */
    public boolean isPrtflColl() {
        return prtflColl;
    }

    /**
     * Sets the value of the prtflColl property.
     * 
     */
    public void setPrtflColl(boolean value) {
        this.prtflColl = value;
    }

    /**
     * Gets the value of the prtfl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtfl() {
        return prtfl;
    }

    /**
     * Sets the value of the prtfl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtfl(String value) {
        this.prtfl = value;
    }

    /**
     * Gets the value of the initlMrgnPstd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getInitlMrgnPstd() {
        return initlMrgnPstd;
    }

    /**
     * Sets the value of the initlMrgnPstd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setInitlMrgnPstd(ActiveCurrencyAnd20Amount value) {
        this.initlMrgnPstd = value;
    }

    /**
     * Gets the value of the vartnMrgnPstd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getVartnMrgnPstd() {
        return vartnMrgnPstd;
    }

    /**
     * Sets the value of the vartnMrgnPstd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setVartnMrgnPstd(ActiveCurrencyAnd20Amount value) {
        this.vartnMrgnPstd = value;
    }

    /**
     * Gets the value of the initlMrgnRcvd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getInitlMrgnRcvd() {
        return initlMrgnRcvd;
    }

    /**
     * Sets the value of the initlMrgnRcvd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setInitlMrgnRcvd(ActiveCurrencyAnd20Amount value) {
        this.initlMrgnRcvd = value;
    }

    /**
     * Gets the value of the vartnMrgnRcvd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getVartnMrgnRcvd() {
        return vartnMrgnRcvd;
    }

    /**
     * Sets the value of the vartnMrgnRcvd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setVartnMrgnRcvd(ActiveCurrencyAnd20Amount value) {
        this.vartnMrgnRcvd = value;
    }

    /**
     * Gets the value of the xcssCollPstd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getXcssCollPstd() {
        return xcssCollPstd;
    }

    /**
     * Sets the value of the xcssCollPstd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setXcssCollPstd(ActiveCurrencyAnd20Amount value) {
        this.xcssCollPstd = value;
    }

    /**
     * Gets the value of the xcssCollRcvd property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public ActiveCurrencyAnd20Amount getXcssCollRcvd() {
        return xcssCollRcvd;
    }

    /**
     * Sets the value of the xcssCollRcvd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20Amount }
     *     
     */
    public void setXcssCollRcvd(ActiveCurrencyAnd20Amount value) {
        this.xcssCollRcvd = value;
    }

}
