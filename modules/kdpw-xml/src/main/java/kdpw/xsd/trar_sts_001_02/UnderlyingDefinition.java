//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.30 at 11:11:04 AM GMT 
//


package kdpw.xsd.trar_sts_001_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Instrument bazowy
 * 
 * <p>Java class for UnderlyingDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnderlyingDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UndrlygId" type="{urn:kdpw:xsd:trar.sts.001.02}Max20Text"/&gt;
 *         &lt;element name="UndrlygTp" type="{urn:kdpw:xsd:trar.sts.001.02}Max1Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnderlyingDefinition", propOrder = {
    "undrlygId",
    "undrlygTp"
})
public class UnderlyingDefinition {

    @XmlElement(name = "UndrlygId", required = true)
    protected String undrlygId;
    @XmlElement(name = "UndrlygTp", required = true)
    protected String undrlygTp;

    /**
     * Gets the value of the undrlygId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndrlygId() {
        return undrlygId;
    }

    /**
     * Sets the value of the undrlygId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndrlygId(String value) {
        this.undrlygId = value;
    }

    /**
     * Gets the value of the undrlygTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndrlygTp() {
        return undrlygTp;
    }

    /**
     * Sets the value of the undrlygTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndrlygTp(String value) {
        this.undrlygTp = value;
    }

}