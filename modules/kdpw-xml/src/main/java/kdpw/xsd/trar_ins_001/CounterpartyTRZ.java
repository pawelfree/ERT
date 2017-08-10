//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:49 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to counterparty identification.
 * 
 * <p>Java class for Counterparty_TR_Z complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Counterparty_TR_Z"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RptgCtrPtyId" type="{urn:kdpw:xsd:trar.ins.001.03}LEIIdentifier"/&gt;
 *         &lt;element name="OthrCtrPty" type="{urn:kdpw:xsd:trar.ins.001.03}CounterpartyOther_TR"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Counterparty_TR_Z", propOrder = {
    "rptgCtrPtyId",
    "othrCtrPty"
})
public class CounterpartyTRZ {

    @XmlElement(name = "RptgCtrPtyId", required = true)
    protected String rptgCtrPtyId;
    @XmlElement(name = "OthrCtrPty", required = true)
    protected CounterpartyOtherTR othrCtrPty;

    /**
     * Gets the value of the rptgCtrPtyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRptgCtrPtyId() {
        return rptgCtrPtyId;
    }

    /**
     * Sets the value of the rptgCtrPtyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRptgCtrPtyId(String value) {
        this.rptgCtrPtyId = value;
    }

    /**
     * Gets the value of the othrCtrPty property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyOtherTR }
     *     
     */
    public CounterpartyOtherTR getOthrCtrPty() {
        return othrCtrPty;
    }

    /**
     * Sets the value of the othrCtrPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyOtherTR }
     *     
     */
    public void setOthrCtrPty(CounterpartyOtherTR value) {
        this.othrCtrPty = value;
    }

}
