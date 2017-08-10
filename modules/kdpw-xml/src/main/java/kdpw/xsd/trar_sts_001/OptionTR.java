//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:52 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Option or swaption related attributes.
 * 
 * <p>Java class for Option_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Option_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OptnTp" type="{urn:kdpw:xsd:trar.sts.001.03}OptionType_TR"/&gt;
 *         &lt;element name="OptnExrcStyle" type="{urn:kdpw:xsd:trar.sts.001.03}OptionStyle_TR"/&gt;
 *         &lt;element name="StrkPric" type="{urn:kdpw:xsd:trar.sts.001.03}SecuritiesTransactionPrice7Choice_TR"/&gt;
 *         &lt;element name="StrkPricNttn" type="{urn:kdpw:xsd:trar.sts.001.03}Max1Text" minOccurs="0"/&gt;
 *         &lt;element name="MtrtyDtOfUndrlyg" type="{urn:kdpw:xsd:trar.sts.001.03}ISODate" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Option_TR", propOrder = {
    "optnTp",
    "optnExrcStyle",
    "strkPric",
    "strkPricNttn",
    "mtrtyDtOfUndrlyg"
})
public class OptionTR {

    @XmlElement(name = "OptnTp", required = true)
    @XmlSchemaType(name = "string")
    protected OptionTypeTR optnTp;
    @XmlElement(name = "OptnExrcStyle", required = true)
    @XmlSchemaType(name = "string")
    protected OptionStyleTR optnExrcStyle;
    @XmlElement(name = "StrkPric", required = true)
    protected SecuritiesTransactionPrice7ChoiceTR strkPric;
    @XmlElement(name = "StrkPricNttn")
    protected String strkPricNttn;
    @XmlElement(name = "MtrtyDtOfUndrlyg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar mtrtyDtOfUndrlyg;

    /**
     * Gets the value of the optnTp property.
     * 
     * @return
     *     possible object is
     *     {@link OptionTypeTR }
     *     
     */
    public OptionTypeTR getOptnTp() {
        return optnTp;
    }

    /**
     * Sets the value of the optnTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionTypeTR }
     *     
     */
    public void setOptnTp(OptionTypeTR value) {
        this.optnTp = value;
    }

    /**
     * Gets the value of the optnExrcStyle property.
     * 
     * @return
     *     possible object is
     *     {@link OptionStyleTR }
     *     
     */
    public OptionStyleTR getOptnExrcStyle() {
        return optnExrcStyle;
    }

    /**
     * Sets the value of the optnExrcStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionStyleTR }
     *     
     */
    public void setOptnExrcStyle(OptionStyleTR value) {
        this.optnExrcStyle = value;
    }

    /**
     * Gets the value of the strkPric property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesTransactionPrice7ChoiceTR }
     *     
     */
    public SecuritiesTransactionPrice7ChoiceTR getStrkPric() {
        return strkPric;
    }

    /**
     * Sets the value of the strkPric property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesTransactionPrice7ChoiceTR }
     *     
     */
    public void setStrkPric(SecuritiesTransactionPrice7ChoiceTR value) {
        this.strkPric = value;
    }

    /**
     * Gets the value of the strkPricNttn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrkPricNttn() {
        return strkPricNttn;
    }

    /**
     * Sets the value of the strkPricNttn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrkPricNttn(String value) {
        this.strkPricNttn = value;
    }

    /**
     * Gets the value of the mtrtyDtOfUndrlyg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMtrtyDtOfUndrlyg() {
        return mtrtyDtOfUndrlyg;
    }

    /**
     * Sets the value of the mtrtyDtOfUndrlyg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMtrtyDtOfUndrlyg(XMLGregorianCalendar value) {
        this.mtrtyDtOfUndrlyg = value;
    }

}
