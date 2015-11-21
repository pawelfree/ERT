//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 09:33:42 AM CEST 
//


package kdpw.xsd.trar_ins_002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identyfikacja kontrahenta i produktu
 * 
 * <p>Java class for CounterpartyAndProductIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CounterpartyAndProductIdentification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrPtyInf" type="{urn:kdpw:xsd:trar.ins.002.02}CounterpartyIdentification" minOccurs="0"/&gt;
 *         &lt;element name="PrdctInf" type="{urn:kdpw:xsd:trar.ins.002.02}ProductIdentification"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CounterpartyAndProductIdentification", propOrder = {
    "ctrPtyInf",
    "prdctInf"
})
public class CounterpartyAndProductIdentification {

    @XmlElement(name = "CtrPtyInf")
    protected CounterpartyIdentification ctrPtyInf;
    @XmlElement(name = "PrdctInf", required = true)
    protected ProductIdentification prdctInf;

    /**
     * Gets the value of the ctrPtyInf property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyIdentification }
     *     
     */
    public CounterpartyIdentification getCtrPtyInf() {
        return ctrPtyInf;
    }

    /**
     * Sets the value of the ctrPtyInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyIdentification }
     *     
     */
    public void setCtrPtyInf(CounterpartyIdentification value) {
        this.ctrPtyInf = value;
    }

    /**
     * Gets the value of the prdctInf property.
     * 
     * @return
     *     possible object is
     *     {@link ProductIdentification }
     *     
     */
    public ProductIdentification getPrdctInf() {
        return prdctInf;
    }

    /**
     * Sets the value of the prdctInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductIdentification }
     *     
     */
    public void setPrdctInf(ProductIdentification value) {
        this.prdctInf = value;
    }

}
