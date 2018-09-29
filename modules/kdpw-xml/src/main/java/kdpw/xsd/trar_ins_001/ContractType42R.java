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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to contract attributes.
 * 
 * <p>Java class for ContractType4__2_R complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractType4__2_R"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrctTp" type="{urn:kdpw:xsd:trar.ins.001.04}FinancialInstrumentContractType2Code"/&gt;
 *         &lt;element name="AsstClss" type="{urn:kdpw:xsd:trar.ins.001.04}ProductType4Code__1"/&gt;
 *         &lt;element name="CtrctDtls" type="{urn:kdpw:xsd:trar.ins.001.04}ContractDetails_TR_R"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractType4__2_R", propOrder = {
    "ctrctTp",
    "asstClss",
    "ctrctDtls"
})
public class ContractType42R {

    @XmlElement(name = "CtrctTp", required = true)
    @XmlSchemaType(name = "string")
    protected FinancialInstrumentContractType2Code ctrctTp;
    @XmlElement(name = "AsstClss", required = true)
    @XmlSchemaType(name = "string")
    protected ProductType4Code1 asstClss;
    @XmlElement(name = "CtrctDtls", required = true)
    protected ContractDetailsTRR ctrctDtls;

    /**
     * Gets the value of the ctrctTp property.
     * 
     * @return
     *     possible object is
     *     {@link FinancialInstrumentContractType2Code }
     *     
     */
    public FinancialInstrumentContractType2Code getCtrctTp() {
        return ctrctTp;
    }

    /**
     * Sets the value of the ctrctTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialInstrumentContractType2Code }
     *     
     */
    public void setCtrctTp(FinancialInstrumentContractType2Code value) {
        this.ctrctTp = value;
    }

    /**
     * Gets the value of the asstClss property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType4Code1 }
     *     
     */
    public ProductType4Code1 getAsstClss() {
        return asstClss;
    }

    /**
     * Sets the value of the asstClss property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType4Code1 }
     *     
     */
    public void setAsstClss(ProductType4Code1 value) {
        this.asstClss = value;
    }

    /**
     * Gets the value of the ctrctDtls property.
     * 
     * @return
     *     possible object is
     *     {@link ContractDetailsTRR }
     *     
     */
    public ContractDetailsTRR getCtrctDtls() {
        return ctrctDtls;
    }

    /**
     * Sets the value of the ctrctDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractDetailsTRR }
     *     
     */
    public void setCtrctDtls(ContractDetailsTRR value) {
        this.ctrctDtls = value;
    }

}
