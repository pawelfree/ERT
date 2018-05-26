//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Data set related to MIFIR regulation requirements.
 * 
 * <p>Java class for MIFIRReportingData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MIFIRReportingData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrPtySd" type="{urn:kdpw:xsd:trar.ins.001.03}OptionParty1Code" minOccurs="0"/&gt;
 *         &lt;element name="InvstmtPtyInd" type="{urn:kdpw:xsd:trar.ins.001.03}YesNoIndicator"/&gt;
 *         &lt;element name="BuyrAddtlDtls" type="{urn:kdpw:xsd:trar.ins.001.03}PartyIdentification_EMIR" minOccurs="0"/&gt;
 *         &lt;element name="SellrAddtlDtls" type="{urn:kdpw:xsd:trar.ins.001.03}PartyIdentification_EMIR" minOccurs="0"/&gt;
 *         &lt;element name="OrdrTrnsmssn" type="{urn:kdpw:xsd:trar.ins.001.03}SecuritiesTransactionTransmission2"/&gt;
 *         &lt;element name="Tx" type="{urn:kdpw:xsd:trar.ins.001.03}MIFIRTransactionData"/&gt;
 *         &lt;element name="FinInstrm" type="{urn:kdpw:xsd:trar.ins.001.03}MIFIRInstrumentData" minOccurs="0"/&gt;
 *         &lt;element name="InvstmtDcsnPrsn" type="{urn:kdpw:xsd:trar.ins.001.03}InvestmentParty1Choice__1" minOccurs="0"/&gt;
 *         &lt;element name="ExctgPrsn" type="{urn:kdpw:xsd:trar.ins.001.03}ExecutingParty1Choice__1" minOccurs="0"/&gt;
 *         &lt;element name="AddtlAttrbts" type="{urn:kdpw:xsd:trar.ins.001.03}SecuritiesTransactionIndicator2__1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MIFIRReportingData", propOrder = {
    "ctrPtySd",
    "invstmtPtyInd",
    "buyrAddtlDtls",
    "sellrAddtlDtls",
    "ordrTrnsmssn",
    "tx",
    "finInstrm",
    "invstmtDcsnPrsn",
    "exctgPrsn",
    "addtlAttrbts"
})
public class MIFIRReportingData {

    @XmlElement(name = "CtrPtySd")
    @XmlSchemaType(name = "string")
    protected OptionParty1Code ctrPtySd;
    @XmlElement(name = "InvstmtPtyInd")
    protected boolean invstmtPtyInd;
    @XmlElement(name = "BuyrAddtlDtls")
    protected PartyIdentificationEMIR buyrAddtlDtls;
    @XmlElement(name = "SellrAddtlDtls")
    protected PartyIdentificationEMIR sellrAddtlDtls;
    @XmlElement(name = "OrdrTrnsmssn", required = true)
    protected SecuritiesTransactionTransmission2 ordrTrnsmssn;
    @XmlElement(name = "Tx", required = true)
    protected MIFIRTransactionData tx;
    @XmlElement(name = "FinInstrm")
    protected MIFIRInstrumentData finInstrm;
    @XmlElement(name = "InvstmtDcsnPrsn")
    protected InvestmentParty1Choice1 invstmtDcsnPrsn;
    @XmlElement(name = "ExctgPrsn")
    protected ExecutingParty1Choice1 exctgPrsn;
    @XmlElement(name = "AddtlAttrbts")
    protected SecuritiesTransactionIndicator21 addtlAttrbts;

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
     * Gets the value of the invstmtPtyInd property.
     * 
     */
    public boolean isInvstmtPtyInd() {
        return invstmtPtyInd;
    }

    /**
     * Sets the value of the invstmtPtyInd property.
     * 
     */
    public void setInvstmtPtyInd(boolean value) {
        this.invstmtPtyInd = value;
    }

    /**
     * Gets the value of the buyrAddtlDtls property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentificationEMIR }
     *     
     */
    public PartyIdentificationEMIR getBuyrAddtlDtls() {
        return buyrAddtlDtls;
    }

    /**
     * Sets the value of the buyrAddtlDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentificationEMIR }
     *     
     */
    public void setBuyrAddtlDtls(PartyIdentificationEMIR value) {
        this.buyrAddtlDtls = value;
    }

    /**
     * Gets the value of the sellrAddtlDtls property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentificationEMIR }
     *     
     */
    public PartyIdentificationEMIR getSellrAddtlDtls() {
        return sellrAddtlDtls;
    }

    /**
     * Sets the value of the sellrAddtlDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentificationEMIR }
     *     
     */
    public void setSellrAddtlDtls(PartyIdentificationEMIR value) {
        this.sellrAddtlDtls = value;
    }

    /**
     * Gets the value of the ordrTrnsmssn property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesTransactionTransmission2 }
     *     
     */
    public SecuritiesTransactionTransmission2 getOrdrTrnsmssn() {
        return ordrTrnsmssn;
    }

    /**
     * Sets the value of the ordrTrnsmssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesTransactionTransmission2 }
     *     
     */
    public void setOrdrTrnsmssn(SecuritiesTransactionTransmission2 value) {
        this.ordrTrnsmssn = value;
    }

    /**
     * Gets the value of the tx property.
     * 
     * @return
     *     possible object is
     *     {@link MIFIRTransactionData }
     *     
     */
    public MIFIRTransactionData getTx() {
        return tx;
    }

    /**
     * Sets the value of the tx property.
     * 
     * @param value
     *     allowed object is
     *     {@link MIFIRTransactionData }
     *     
     */
    public void setTx(MIFIRTransactionData value) {
        this.tx = value;
    }

    /**
     * Gets the value of the finInstrm property.
     * 
     * @return
     *     possible object is
     *     {@link MIFIRInstrumentData }
     *     
     */
    public MIFIRInstrumentData getFinInstrm() {
        return finInstrm;
    }

    /**
     * Sets the value of the finInstrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link MIFIRInstrumentData }
     *     
     */
    public void setFinInstrm(MIFIRInstrumentData value) {
        this.finInstrm = value;
    }

    /**
     * Gets the value of the invstmtDcsnPrsn property.
     * 
     * @return
     *     possible object is
     *     {@link InvestmentParty1Choice1 }
     *     
     */
    public InvestmentParty1Choice1 getInvstmtDcsnPrsn() {
        return invstmtDcsnPrsn;
    }

    /**
     * Sets the value of the invstmtDcsnPrsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestmentParty1Choice1 }
     *     
     */
    public void setInvstmtDcsnPrsn(InvestmentParty1Choice1 value) {
        this.invstmtDcsnPrsn = value;
    }

    /**
     * Gets the value of the exctgPrsn property.
     * 
     * @return
     *     possible object is
     *     {@link ExecutingParty1Choice1 }
     *     
     */
    public ExecutingParty1Choice1 getExctgPrsn() {
        return exctgPrsn;
    }

    /**
     * Sets the value of the exctgPrsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutingParty1Choice1 }
     *     
     */
    public void setExctgPrsn(ExecutingParty1Choice1 value) {
        this.exctgPrsn = value;
    }

    /**
     * Gets the value of the addtlAttrbts property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesTransactionIndicator21 }
     *     
     */
    public SecuritiesTransactionIndicator21 getAddtlAttrbts() {
        return addtlAttrbts;
    }

    /**
     * Sets the value of the addtlAttrbts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesTransactionIndicator21 }
     *     
     */
    public void setAddtlAttrbts(SecuritiesTransactionIndicator21 value) {
        this.addtlAttrbts = value;
    }

}
