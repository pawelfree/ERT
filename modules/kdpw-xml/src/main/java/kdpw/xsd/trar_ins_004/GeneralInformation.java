//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 09:33:43 AM CEST 
//


package kdpw.xsd.trar_ins_004;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Informacje ogólne
 * 
 * <p>Java class for GeneralInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneralInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TRRprtId" type="{urn:kdpw:xsd:trar.ins.004.02}TRInstitutionCode"/&gt;
 *         &lt;element name="SndrMsgRef" type="{urn:kdpw:xsd:trar.ins.004.02}Max16Text"/&gt;
 *         &lt;element name="FuncOfMsg" type="{urn:kdpw:xsd:trar.ins.004.02}FunctionOfMessage"/&gt;
 *         &lt;element name="ActnTp" type="{urn:kdpw:xsd:trar.ins.004.02}Max1Text"/&gt;
 *         &lt;element name="ActnTpDtls" type="{urn:kdpw:xsd:trar.ins.004.02}Max50Text" minOccurs="0"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:kdpw:xsd:trar.ins.004.02}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="EligDt" type="{urn:kdpw:xsd:trar.ins.004.02}ISODate"/&gt;
 *         &lt;element name="DtlLvl" type="{urn:kdpw:xsd:trar.ins.004.02}Max1Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneralInformation", propOrder = {
    "trRprtId",
    "sndrMsgRef",
    "funcOfMsg",
    "actnTp",
    "actnTpDtls",
    "creDtTm",
    "eligDt",
    "dtlLvl"
})
public class GeneralInformation {

    @XmlElement(name = "TRRprtId", required = true)
    protected TRInstitutionCode trRprtId;
    @XmlElement(name = "SndrMsgRef", required = true)
    protected String sndrMsgRef;
    @XmlElement(name = "FuncOfMsg", required = true)
    @XmlSchemaType(name = "string")
    protected FunctionOfMessage funcOfMsg;
    @XmlElement(name = "ActnTp", required = true)
    protected String actnTp;
    @XmlElement(name = "ActnTpDtls")
    protected String actnTpDtls;
    @XmlElement(name = "CreDtTm")
    protected DateAndDateTimeChoice creDtTm;
    @XmlElement(name = "EligDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eligDt;
    @XmlElement(name = "DtlLvl", required = true)
    protected String dtlLvl;

    /**
     * Gets the value of the trRprtId property.
     * 
     * @return
     *     possible object is
     *     {@link TRInstitutionCode }
     *     
     */
    public TRInstitutionCode getTRRprtId() {
        return trRprtId;
    }

    /**
     * Sets the value of the trRprtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRInstitutionCode }
     *     
     */
    public void setTRRprtId(TRInstitutionCode value) {
        this.trRprtId = value;
    }

    /**
     * Gets the value of the sndrMsgRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

    /**
     * Sets the value of the sndrMsgRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSndrMsgRef(String value) {
        this.sndrMsgRef = value;
    }

    /**
     * Gets the value of the funcOfMsg property.
     * 
     * @return
     *     possible object is
     *     {@link FunctionOfMessage }
     *     
     */
    public FunctionOfMessage getFuncOfMsg() {
        return funcOfMsg;
    }

    /**
     * Sets the value of the funcOfMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionOfMessage }
     *     
     */
    public void setFuncOfMsg(FunctionOfMessage value) {
        this.funcOfMsg = value;
    }

    /**
     * Gets the value of the actnTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActnTp() {
        return actnTp;
    }

    /**
     * Sets the value of the actnTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActnTp(String value) {
        this.actnTp = value;
    }

    /**
     * Gets the value of the actnTpDtls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActnTpDtls() {
        return actnTpDtls;
    }

    /**
     * Sets the value of the actnTpDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActnTpDtls(String value) {
        this.actnTpDtls = value;
    }

    /**
     * Gets the value of the creDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setCreDtTm(DateAndDateTimeChoice value) {
        this.creDtTm = value;
    }

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
     * Gets the value of the dtlLvl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtlLvl() {
        return dtlLvl;
    }

    /**
     * Sets the value of the dtlLvl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtlLvl(String value) {
        this.dtlLvl = value;
    }

}
