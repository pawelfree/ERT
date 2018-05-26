//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:41 PM CET 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Information related to contract valuation.
 * 
 * <p>Java class for ContractValuationData_TR_N complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractValuationData_TR_N"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrctVal" type="{urn:kdpw:xsd:trar.sts.001.03}ActiveCurrencyAnd20AmountN"/&gt;
 *         &lt;element name="TmStmp" type="{urn:kdpw:xsd:trar.sts.001.03}ISONormalisedDateTime"/&gt;
 *         &lt;element name="Tp" type="{urn:kdpw:xsd:trar.sts.001.03}ValuationType1Code"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractValuationData_TR_N", propOrder = {
    "ctrctVal",
    "tmStmp",
    "tp"
})
public class ContractValuationDataTRN {

    @XmlElement(name = "CtrctVal", required = true)
    protected ActiveCurrencyAnd20AmountN ctrctVal;
    @XmlElement(name = "TmStmp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tmStmp;
    @XmlElement(name = "Tp", required = true)
    @XmlSchemaType(name = "string")
    protected ValuationType1Code tp;

    /**
     * Gets the value of the ctrctVal property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAnd20AmountN }
     *     
     */
    public ActiveCurrencyAnd20AmountN getCtrctVal() {
        return ctrctVal;
    }

    /**
     * Sets the value of the ctrctVal property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAnd20AmountN }
     *     
     */
    public void setCtrctVal(ActiveCurrencyAnd20AmountN value) {
        this.ctrctVal = value;
    }

    /**
     * Gets the value of the tmStmp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTmStmp() {
        return tmStmp;
    }

    /**
     * Sets the value of the tmStmp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTmStmp(XMLGregorianCalendar value) {
        this.tmStmp = value;
    }

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link ValuationType1Code }
     *     
     */
    public ValuationType1Code getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValuationType1Code }
     *     
     */
    public void setTp(ValuationType1Code value) {
        this.tp = value;
    }

}
