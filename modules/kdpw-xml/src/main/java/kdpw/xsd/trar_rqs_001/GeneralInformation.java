//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 09:33:46 AM CEST 
//


package kdpw.xsd.trar_rqs_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="TRRprtId" type="{urn:kdpw:xsd:trar.rqs.001.02}TRInstitutionCode"/&gt;
 *         &lt;element name="SndrMsgRef" type="{urn:kdpw:xsd:trar.rqs.001.02}Max16Text"/&gt;
 *         &lt;element name="FuncOfMsg" type="{urn:kdpw:xsd:trar.rqs.001.02}FunctionOfMessage"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:kdpw:xsd:trar.rqs.001.02}DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="ReqTp" type="{urn:kdpw:xsd:trar.rqs.001.02}Max1Text"/&gt;
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
    "creDtTm",
    "reqTp"
})
public class GeneralInformation {

    @XmlElement(name = "TRRprtId", required = true)
    protected TRInstitutionCode trRprtId;
    @XmlElement(name = "SndrMsgRef", required = true)
    protected String sndrMsgRef;
    @XmlElement(name = "FuncOfMsg", required = true)
    @XmlSchemaType(name = "string")
    protected FunctionOfMessage funcOfMsg;
    @XmlElement(name = "CreDtTm")
    protected DateAndDateTimeChoice creDtTm;
    @XmlElement(name = "ReqTp", required = true)
    protected String reqTp;

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
     * Gets the value of the reqTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqTp() {
        return reqTp;
    }

    /**
     * Sets the value of the reqTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqTp(String value) {
        this.reqTp = value;
    }

}
