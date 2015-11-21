//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.02 at 10:13:41 AM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Szczegółowe informacje   kontrahenta
 * 
 * <p>Java class for CounterpartyDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CounterpartyDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BrkrId" type="{urn:kdpw:xsd:trar.ins.001.02}InstitutionCodeOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="ClrMmbId" type="{urn:kdpw:xsd:trar.ins.001.02}InstitutionCodeOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="ClrAcct" type="{urn:kdpw:xsd:trar.ins.001.02}Max35TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="BnfcryId" type="{urn:kdpw:xsd:trar.ins.001.02}InstitutionCode" minOccurs="0"/&gt;
 *         &lt;element name="TrdgCpcty" type="{urn:kdpw:xsd:trar.ins.001.02}Max1Text" minOccurs="0"/&gt;
 *         &lt;element name="FinNonFinInd" type="{urn:kdpw:xsd:trar.ins.001.02}Max1Text" minOccurs="0"/&gt;
 *         &lt;element name="CmmrclActvty" type="{urn:kdpw:xsd:trar.ins.001.02}Max1TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="ClrTrshld" type="{urn:kdpw:xsd:trar.ins.001.02}Max1TextOrDelete" minOccurs="0"/&gt;
 *         &lt;element name="CollPrtfl" type="{urn:kdpw:xsd:trar.ins.001.02}Max35TextOrDelete" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CounterpartyDetails", propOrder = {
    "brkrId",
    "clrMmbId",
    "clrAcct",
    "bnfcryId",
    "trdgCpcty",
    "finNonFinInd",
    "cmmrclActvty",
    "clrTrshld",
    "collPrtfl"
})
public class CounterpartyDetails {

    @XmlElement(name = "BrkrId")
    protected InstitutionCodeOrDelete brkrId;
    @XmlElement(name = "ClrMmbId")
    protected InstitutionCodeOrDelete clrMmbId;
    @XmlElement(name = "ClrAcct")
    protected Max35TextOrDelete clrAcct;
    @XmlElement(name = "BnfcryId")
    protected InstitutionCode bnfcryId;
    @XmlElement(name = "TrdgCpcty")
    protected String trdgCpcty;
    @XmlElement(name = "FinNonFinInd")
    protected String finNonFinInd;
    @XmlElement(name = "CmmrclActvty")
    protected Max1TextOrDelete cmmrclActvty;
    @XmlElement(name = "ClrTrshld")
    protected Max1TextOrDelete clrTrshld;
    @XmlElement(name = "CollPrtfl")
    protected Max35TextOrDelete collPrtfl;

    /**
     * Gets the value of the brkrId property.
     * 
     * @return
     *     possible object is
     *     {@link InstitutionCodeOrDelete }
     *     
     */
    public InstitutionCodeOrDelete getBrkrId() {
        return brkrId;
    }

    /**
     * Sets the value of the brkrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitutionCodeOrDelete }
     *     
     */
    public void setBrkrId(InstitutionCodeOrDelete value) {
        this.brkrId = value;
    }

    /**
     * Gets the value of the clrMmbId property.
     * 
     * @return
     *     possible object is
     *     {@link InstitutionCodeOrDelete }
     *     
     */
    public InstitutionCodeOrDelete getClrMmbId() {
        return clrMmbId;
    }

    /**
     * Sets the value of the clrMmbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitutionCodeOrDelete }
     *     
     */
    public void setClrMmbId(InstitutionCodeOrDelete value) {
        this.clrMmbId = value;
    }

    /**
     * Gets the value of the clrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link Max35TextOrDelete }
     *     
     */
    public Max35TextOrDelete getClrAcct() {
        return clrAcct;
    }

    /**
     * Sets the value of the clrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max35TextOrDelete }
     *     
     */
    public void setClrAcct(Max35TextOrDelete value) {
        this.clrAcct = value;
    }

    /**
     * Gets the value of the bnfcryId property.
     * 
     * @return
     *     possible object is
     *     {@link InstitutionCode }
     *     
     */
    public InstitutionCode getBnfcryId() {
        return bnfcryId;
    }

    /**
     * Sets the value of the bnfcryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitutionCode }
     *     
     */
    public void setBnfcryId(InstitutionCode value) {
        this.bnfcryId = value;
    }

    /**
     * Gets the value of the trdgCpcty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrdgCpcty() {
        return trdgCpcty;
    }

    /**
     * Sets the value of the trdgCpcty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrdgCpcty(String value) {
        this.trdgCpcty = value;
    }

    /**
     * Gets the value of the finNonFinInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinNonFinInd() {
        return finNonFinInd;
    }

    /**
     * Sets the value of the finNonFinInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinNonFinInd(String value) {
        this.finNonFinInd = value;
    }

    /**
     * Gets the value of the cmmrclActvty property.
     * 
     * @return
     *     possible object is
     *     {@link Max1TextOrDelete }
     *     
     */
    public Max1TextOrDelete getCmmrclActvty() {
        return cmmrclActvty;
    }

    /**
     * Sets the value of the cmmrclActvty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max1TextOrDelete }
     *     
     */
    public void setCmmrclActvty(Max1TextOrDelete value) {
        this.cmmrclActvty = value;
    }

    /**
     * Gets the value of the clrTrshld property.
     * 
     * @return
     *     possible object is
     *     {@link Max1TextOrDelete }
     *     
     */
    public Max1TextOrDelete getClrTrshld() {
        return clrTrshld;
    }

    /**
     * Sets the value of the clrTrshld property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max1TextOrDelete }
     *     
     */
    public void setClrTrshld(Max1TextOrDelete value) {
        this.clrTrshld = value;
    }

    /**
     * Gets the value of the collPrtfl property.
     * 
     * @return
     *     possible object is
     *     {@link Max35TextOrDelete }
     *     
     */
    public Max35TextOrDelete getCollPrtfl() {
        return collPrtfl;
    }

    /**
     * Sets the value of the collPrtfl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max35TextOrDelete }
     *     
     */
    public void setCollPrtfl(Max35TextOrDelete value) {
        this.collPrtfl = value;
    }

}
