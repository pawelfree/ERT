//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.29 at 02:25:35 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to contract attributes.
 * 
 * <p>Java class for ContractDetails_TR_N complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractDetails_TR_N"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PdctClssfctn" type="{urn:kdpw:xsd:trar.ins.001.04}ProductClassification1Choice"/&gt;
 *         &lt;element name="PdctId" type="{urn:kdpw:xsd:trar.ins.001.04}SecurityIdentification18Choice__1" minOccurs="0"/&gt;
 *         &lt;element name="UndrlygInstrm" type="{urn:kdpw:xsd:trar.ins.001.04}SecurityIdentification19Choice__2" minOccurs="0"/&gt;
 *         &lt;element name="TechUndrlyg" type="{urn:kdpw:xsd:trar.ins.001.04}Max50Text" minOccurs="0"/&gt;
 *         &lt;element name="NtnlCcyFrstLeg" type="{urn:kdpw:xsd:trar.ins.001.04}ActiveCurrencyCode"/&gt;
 *         &lt;element name="NtnlCcyScndLeg" type="{urn:kdpw:xsd:trar.ins.001.04}ActiveCurrencyCode" minOccurs="0"/&gt;
 *         &lt;element name="DlvrblCcy" type="{urn:kdpw:xsd:trar.ins.001.04}ActiveCurrencyCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractDetails_TR_N", propOrder = {
    "pdctClssfctn",
    "pdctId",
    "undrlygInstrm",
    "techUndrlyg",
    "ntnlCcyFrstLeg",
    "ntnlCcyScndLeg",
    "dlvrblCcy"
})
public class ContractDetailsTRN {

    @XmlElement(name = "PdctClssfctn", required = true)
    protected ProductClassification1Choice pdctClssfctn;
    @XmlElement(name = "PdctId")
    protected SecurityIdentification18Choice1 pdctId;
    @XmlElement(name = "UndrlygInstrm")
    protected SecurityIdentification19Choice2 undrlygInstrm;
    @XmlElement(name = "TechUndrlyg")
    protected String techUndrlyg;
    @XmlElement(name = "NtnlCcyFrstLeg", required = true)
    protected String ntnlCcyFrstLeg;
    @XmlElement(name = "NtnlCcyScndLeg")
    protected String ntnlCcyScndLeg;
    @XmlElement(name = "DlvrblCcy")
    protected String dlvrblCcy;

    /**
     * Gets the value of the pdctClssfctn property.
     * 
     * @return
     *     possible object is
     *     {@link ProductClassification1Choice }
     *     
     */
    public ProductClassification1Choice getPdctClssfctn() {
        return pdctClssfctn;
    }

    /**
     * Sets the value of the pdctClssfctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductClassification1Choice }
     *     
     */
    public void setPdctClssfctn(ProductClassification1Choice value) {
        this.pdctClssfctn = value;
    }

    /**
     * Gets the value of the pdctId property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityIdentification18Choice1 }
     *     
     */
    public SecurityIdentification18Choice1 getPdctId() {
        return pdctId;
    }

    /**
     * Sets the value of the pdctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityIdentification18Choice1 }
     *     
     */
    public void setPdctId(SecurityIdentification18Choice1 value) {
        this.pdctId = value;
    }

    /**
     * Gets the value of the undrlygInstrm property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityIdentification19Choice2 }
     *     
     */
    public SecurityIdentification19Choice2 getUndrlygInstrm() {
        return undrlygInstrm;
    }

    /**
     * Sets the value of the undrlygInstrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityIdentification19Choice2 }
     *     
     */
    public void setUndrlygInstrm(SecurityIdentification19Choice2 value) {
        this.undrlygInstrm = value;
    }

    /**
     * Gets the value of the techUndrlyg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechUndrlyg() {
        return techUndrlyg;
    }

    /**
     * Sets the value of the techUndrlyg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechUndrlyg(String value) {
        this.techUndrlyg = value;
    }

    /**
     * Gets the value of the ntnlCcyFrstLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtnlCcyFrstLeg() {
        return ntnlCcyFrstLeg;
    }

    /**
     * Sets the value of the ntnlCcyFrstLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtnlCcyFrstLeg(String value) {
        this.ntnlCcyFrstLeg = value;
    }

    /**
     * Gets the value of the ntnlCcyScndLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtnlCcyScndLeg() {
        return ntnlCcyScndLeg;
    }

    /**
     * Sets the value of the ntnlCcyScndLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtnlCcyScndLeg(String value) {
        this.ntnlCcyScndLeg = value;
    }

    /**
     * Gets the value of the dlvrblCcy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDlvrblCcy() {
        return dlvrblCcy;
    }

    /**
     * Sets the value of the dlvrblCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDlvrblCcy(String value) {
        this.dlvrblCcy = value;
    }

}
