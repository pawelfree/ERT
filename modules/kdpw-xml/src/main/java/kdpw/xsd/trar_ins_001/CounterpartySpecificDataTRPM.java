//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Data related specifically to counterparty.
 * 
 * <p>Java class for CounterpartySpecificData_TR_P_M complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CounterpartySpecificData_TR_P_M"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrPty" type="{urn:kdpw:xsd:trar.ins.001.03}Counterparty_TR_P_M"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CounterpartySpecificData_TR_P_M", propOrder = {
    "ctrPty"
})
public class CounterpartySpecificDataTRPM {

    @XmlElement(name = "CtrPty", required = true)
    protected CounterpartyTRPM ctrPty;

    /**
     * Gets the value of the ctrPty property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyTRPM }
     *     
     */
    public CounterpartyTRPM getCtrPty() {
        return ctrPty;
    }

    /**
     * Sets the value of the ctrPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyTRPM }
     *     
     */
    public void setCtrPty(CounterpartyTRPM value) {
        this.ctrPty = value;
    }

}
