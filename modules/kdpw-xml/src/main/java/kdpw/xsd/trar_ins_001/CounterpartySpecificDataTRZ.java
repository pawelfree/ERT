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
 * Data related specifically to counterparty.
 * 
 * <p>Java class for CounterpartySpecificData_TR_Z complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CounterpartySpecificData_TR_Z"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrPty" type="{urn:kdpw:xsd:trar.ins.001.04}Counterparty_TR_V"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CounterpartySpecificData_TR_Z", propOrder = {
    "ctrPty"
})
public class CounterpartySpecificDataTRZ {

    @XmlElement(name = "CtrPty", required = true)
    protected CounterpartyTRV ctrPty;

    /**
     * Gets the value of the ctrPty property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyTRV }
     *     
     */
    public CounterpartyTRV getCtrPty() {
        return ctrPty;
    }

    /**
     * Sets the value of the ctrPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyTRV }
     *     
     */
    public void setCtrPty(CounterpartyTRV value) {
        this.ctrPty = value;
    }

}
