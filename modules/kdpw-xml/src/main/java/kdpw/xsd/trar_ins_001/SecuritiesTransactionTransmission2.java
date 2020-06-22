//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:17 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the securities order transmission attributes.
 * 
 * 
 * <p>Java class for SecuritiesTransactionTransmission2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesTransactionTransmission2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TrnsmssnInd" type="{urn:kdpw:xsd:trar.ins.001.04}YesNoIndicator"/&gt;
 *         &lt;element name="TrnsmttgBuyr" type="{urn:kdpw:xsd:trar.ins.001.04}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="TrnsmttgSellr" type="{urn:kdpw:xsd:trar.ins.001.04}LEIIdentifier" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesTransactionTransmission2", propOrder = {
    "trnsmssnInd",
    "trnsmttgBuyr",
    "trnsmttgSellr"
})
public class SecuritiesTransactionTransmission2 {

    @XmlElement(name = "TrnsmssnInd")
    protected boolean trnsmssnInd;
    @XmlElement(name = "TrnsmttgBuyr")
    protected String trnsmttgBuyr;
    @XmlElement(name = "TrnsmttgSellr")
    protected String trnsmttgSellr;

    /**
     * Gets the value of the trnsmssnInd property.
     * 
     */
    public boolean isTrnsmssnInd() {
        return trnsmssnInd;
    }

    /**
     * Sets the value of the trnsmssnInd property.
     * 
     */
    public void setTrnsmssnInd(boolean value) {
        this.trnsmssnInd = value;
    }

    /**
     * Gets the value of the trnsmttgBuyr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrnsmttgBuyr() {
        return trnsmttgBuyr;
    }

    /**
     * Sets the value of the trnsmttgBuyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrnsmttgBuyr(String value) {
        this.trnsmttgBuyr = value;
    }

    /**
     * Gets the value of the trnsmttgSellr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrnsmttgSellr() {
        return trnsmttgSellr;
    }

    /**
     * Sets the value of the trnsmttgSellr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrnsmttgSellr(String value) {
        this.trnsmttgSellr = value;
    }

}
