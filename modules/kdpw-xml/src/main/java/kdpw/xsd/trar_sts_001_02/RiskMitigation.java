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
 * Ograniczanie ryzyka / zgłaszanie ryzyka
 * 
 * <p>Java class for RiskMitigation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiskMitigation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CnfrmtnDtTm" type="{urn:kdpw:xsd:trar.sts.001.02}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="CnfrmtnTp" type="{urn:kdpw:xsd:trar.sts.001.02}Max1Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiskMitigation", propOrder = {
    "cnfrmtnDtTm",
    "cnfrmtnTp"
})
public class RiskMitigation {

    @XmlElement(name = "CnfrmtnDtTm")
    protected DateAndDateTimeChoice cnfrmtnDtTm;
    @XmlElement(name = "CnfrmtnTp")
    protected String cnfrmtnTp;

    /**
     * Gets the value of the cnfrmtnDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getCnfrmtnDtTm() {
        return cnfrmtnDtTm;
    }

    /**
     * Sets the value of the cnfrmtnDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setCnfrmtnDtTm(DateAndDateTimeChoice value) {
        this.cnfrmtnDtTm = value;
    }

    /**
     * Gets the value of the cnfrmtnTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnfrmtnTp() {
        return cnfrmtnTp;
    }

    /**
     * Sets the value of the cnfrmtnTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnfrmtnTp(String value) {
        this.cnfrmtnTp = value;
    }

}