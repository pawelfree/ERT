//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:52 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to counterparty identification.
 * 
 * <p>Java class for Counterparty_TR_N complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Counterparty_TR_N"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RptgCtrPtyId" type="{urn:kdpw:xsd:trar.sts.001.03}LEIIdentifier"/&gt;
 *         &lt;element name="CtrPtySd" type="{urn:kdpw:xsd:trar.sts.001.03}OptionParty1Code"/&gt;
 *         &lt;element name="Sctr" type="{urn:kdpw:xsd:trar.sts.001.03}Max53Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Ntr" type="{urn:kdpw:xsd:trar.sts.001.03}CounterpartyTradeNature_TR"/&gt;
 *         &lt;element name="Brkr" type="{urn:kdpw:xsd:trar.sts.001.03}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="ClrMmb" type="{urn:kdpw:xsd:trar.sts.001.03}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="Bnfcry" type="{urn:kdpw:xsd:trar.sts.001.03}OrganisationIdentification3Choice__1"/&gt;
 *         &lt;element name="TradgCpcty" type="{urn:kdpw:xsd:trar.sts.001.03}TradingCapacity7Code"/&gt;
 *         &lt;element name="CmmrclActvty" type="{urn:kdpw:xsd:trar.sts.001.03}YesNoIndicator" minOccurs="0"/&gt;
 *         &lt;element name="ClrTrshld" type="{urn:kdpw:xsd:trar.sts.001.03}YesNoIndicator" minOccurs="0"/&gt;
 *         &lt;element name="OthrCtrPty" type="{urn:kdpw:xsd:trar.sts.001.03}CounterpartyOther_TR_N"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Counterparty_TR_N", propOrder = {
    "rptgCtrPtyId",
    "ctrPtySd",
    "sctr",
    "ntr",
    "brkr",
    "clrMmb",
    "bnfcry",
    "tradgCpcty",
    "cmmrclActvty",
    "clrTrshld",
    "othrCtrPty"
})
public class CounterpartyTRN {

    @XmlElement(name = "RptgCtrPtyId", required = true)
    protected String rptgCtrPtyId;
    @XmlElement(name = "CtrPtySd", required = true)
    @XmlSchemaType(name = "string")
    protected OptionParty1Code ctrPtySd;
    @XmlElement(name = "Sctr")
    protected List<String> sctr;
    @XmlElement(name = "Ntr", required = true)
    @XmlSchemaType(name = "string")
    protected CounterpartyTradeNatureTR ntr;
    @XmlElement(name = "Brkr")
    protected String brkr;
    @XmlElement(name = "ClrMmb")
    protected String clrMmb;
    @XmlElement(name = "Bnfcry", required = true)
    protected OrganisationIdentification3Choice1 bnfcry;
    @XmlElement(name = "TradgCpcty", required = true)
    @XmlSchemaType(name = "string")
    protected TradingCapacity7Code tradgCpcty;
    @XmlElement(name = "CmmrclActvty")
    protected Boolean cmmrclActvty;
    @XmlElement(name = "ClrTrshld")
    protected Boolean clrTrshld;
    @XmlElement(name = "OthrCtrPty", required = true)
    protected CounterpartyOtherTRN othrCtrPty;

    /**
     * Gets the value of the rptgCtrPtyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRptgCtrPtyId() {
        return rptgCtrPtyId;
    }

    /**
     * Sets the value of the rptgCtrPtyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRptgCtrPtyId(String value) {
        this.rptgCtrPtyId = value;
    }

    /**
     * Gets the value of the ctrPtySd property.
     * 
     * @return
     *     possible object is
     *     {@link OptionParty1Code }
     *     
     */
    public OptionParty1Code getCtrPtySd() {
        return ctrPtySd;
    }

    /**
     * Sets the value of the ctrPtySd property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionParty1Code }
     *     
     */
    public void setCtrPtySd(OptionParty1Code value) {
        this.ctrPtySd = value;
    }

    /**
     * Gets the value of the sctr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sctr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSctr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSctr() {
        if (sctr == null) {
            sctr = new ArrayList<String>();
        }
        return this.sctr;
    }

    /**
     * Gets the value of the ntr property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyTradeNatureTR }
     *     
     */
    public CounterpartyTradeNatureTR getNtr() {
        return ntr;
    }

    /**
     * Sets the value of the ntr property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyTradeNatureTR }
     *     
     */
    public void setNtr(CounterpartyTradeNatureTR value) {
        this.ntr = value;
    }

    /**
     * Gets the value of the brkr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrkr() {
        return brkr;
    }

    /**
     * Sets the value of the brkr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrkr(String value) {
        this.brkr = value;
    }

    /**
     * Gets the value of the clrMmb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClrMmb() {
        return clrMmb;
    }

    /**
     * Sets the value of the clrMmb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClrMmb(String value) {
        this.clrMmb = value;
    }

    /**
     * Gets the value of the bnfcry property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification3Choice1 }
     *     
     */
    public OrganisationIdentification3Choice1 getBnfcry() {
        return bnfcry;
    }

    /**
     * Sets the value of the bnfcry property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification3Choice1 }
     *     
     */
    public void setBnfcry(OrganisationIdentification3Choice1 value) {
        this.bnfcry = value;
    }

    /**
     * Gets the value of the tradgCpcty property.
     * 
     * @return
     *     possible object is
     *     {@link TradingCapacity7Code }
     *     
     */
    public TradingCapacity7Code getTradgCpcty() {
        return tradgCpcty;
    }

    /**
     * Sets the value of the tradgCpcty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradingCapacity7Code }
     *     
     */
    public void setTradgCpcty(TradingCapacity7Code value) {
        this.tradgCpcty = value;
    }

    /**
     * Gets the value of the cmmrclActvty property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCmmrclActvty() {
        return cmmrclActvty;
    }

    /**
     * Sets the value of the cmmrclActvty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCmmrclActvty(Boolean value) {
        this.cmmrclActvty = value;
    }

    /**
     * Gets the value of the clrTrshld property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClrTrshld() {
        return clrTrshld;
    }

    /**
     * Sets the value of the clrTrshld property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClrTrshld(Boolean value) {
        this.clrTrshld = value;
    }

    /**
     * Gets the value of the othrCtrPty property.
     * 
     * @return
     *     possible object is
     *     {@link CounterpartyOtherTRN }
     *     
     */
    public CounterpartyOtherTRN getOthrCtrPty() {
        return othrCtrPty;
    }

    /**
     * Sets the value of the othrCtrPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CounterpartyOtherTRN }
     *     
     */
    public void setOthrCtrPty(CounterpartyOtherTRN value) {
        this.othrCtrPty = value;
    }

}
