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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Unique and unambiguous way to identify a person.
 * 
 * <p>Java class for PersonIdentification10__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonIdentification10__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FrstNm" type="{urn:kdpw:xsd:trar.ins.001.04}ESMA_EeaEuropeanAlphabetMax140_Pattern"/&gt;
 *         &lt;element name="Nm" type="{urn:kdpw:xsd:trar.ins.001.04}ESMA_EeaEuropeanAlphabetMax140_Pattern"/&gt;
 *         &lt;element name="BirthDt" type="{urn:kdpw:xsd:trar.ins.001.04}ISODate"/&gt;
 *         &lt;element name="Othr" type="{urn:kdpw:xsd:trar.ins.001.04}GenericPersonIdentification1__1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonIdentification10__1", propOrder = {
    "frstNm",
    "nm",
    "birthDt",
    "othr"
})
public class PersonIdentification101 {

    @XmlElement(name = "FrstNm", required = true)
    protected String frstNm;
    @XmlElement(name = "Nm", required = true)
    protected String nm;
    @XmlElement(name = "BirthDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    @XmlElement(name = "Othr", required = true)
    protected GenericPersonIdentification11 othr;

    /**
     * Gets the value of the frstNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrstNm() {
        return frstNm;
    }

    /**
     * Sets the value of the frstNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrstNm(String value) {
        this.frstNm = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    /**
     * Gets the value of the othr property.
     * 
     * @return
     *     possible object is
     *     {@link GenericPersonIdentification11 }
     *     
     */
    public GenericPersonIdentification11 getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericPersonIdentification11 }
     *     
     */
    public void setOthr(GenericPersonIdentification11 value) {
        this.othr = value;
    }

}
