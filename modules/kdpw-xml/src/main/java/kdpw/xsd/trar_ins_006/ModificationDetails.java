//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:51 PM CEST 
//


package kdpw.xsd.trar_ins_006;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Modification details
 * 
 * <p>Java class for ModificationDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModificationDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EligDt" type="{urn:kdpw:xsd:trar.ins.006.02}ISODate"/&gt;
 *         &lt;element name="ClntId" type="{urn:kdpw:xsd:trar.ins.006.02}Max20Text"/&gt;
 *         &lt;element name="LEI" type="{urn:kdpw:xsd:trar.ins.006.02}LEIIdentifier"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificationDetails", propOrder = {
    "eligDt",
    "clntId",
    "lei"
})
public class ModificationDetails {

    @XmlElement(name = "EligDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eligDt;
    @XmlElement(name = "ClntId", required = true)
    protected String clntId;
    @XmlElement(name = "LEI", required = true)
    protected String lei;

    /**
     * Gets the value of the eligDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEligDt() {
        return eligDt;
    }

    /**
     * Sets the value of the eligDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEligDt(XMLGregorianCalendar value) {
        this.eligDt = value;
    }

    /**
     * Gets the value of the clntId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClntId() {
        return clntId;
    }

    /**
     * Sets the value of the clntId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClntId(String value) {
        this.clntId = value;
    }

    /**
     * Gets the value of the lei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLEI() {
        return lei;
    }

    /**
     * Sets the value of the lei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLEI(String value) {
        this.lei = value;
    }

}
