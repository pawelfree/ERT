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
 * Set of elements used to identify a person or an organisation.
 * 
 * <p>Java class for PartyIdentification_EMIR_D complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyIdentification_EMIR_D"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:kdpw:xsd:trar.ins.001.04}PersonOrOrganisation1Choice_EMIR" minOccurs="0"/&gt;
 *         &lt;element name="CtryOfBrnch" type="{urn:kdpw:xsd:trar.ins.001.04}CountryCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification_EMIR_D", propOrder = {
    "id",
    "ctryOfBrnch"
})
public class PartyIdentificationEMIRD {

    @XmlElement(name = "Id")
    protected PersonOrOrganisation1ChoiceEMIR id;
    @XmlElement(name = "CtryOfBrnch")
    protected String ctryOfBrnch;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link PersonOrOrganisation1ChoiceEMIR }
     *     
     */
    public PersonOrOrganisation1ChoiceEMIR getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonOrOrganisation1ChoiceEMIR }
     *     
     */
    public void setId(PersonOrOrganisation1ChoiceEMIR value) {
        this.id = value;
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

}
