//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:49 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Provides details of trade transaction.
 * 
 * <p>Java class for TradeTransaction11__4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeTransaction11__4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UnqTradIdr" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAMax52AlphaNumericAdditionalCharacters"/&gt;
 *         &lt;element name="RptTrckgNb" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAMax52AlphaNumeric" minOccurs="0"/&gt;
 *         &lt;element name="CmplxTradId" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAMax35AlphaNumeric" minOccurs="0"/&gt;
 *         &lt;element name="Pric" type="{urn:kdpw:xsd:trar.ins.001.03}SecuritiesTransactionPrice7Choice_TR" minOccurs="0"/&gt;
 *         &lt;element name="NtnlAmt" type="{urn:kdpw:xsd:trar.ins.001.03}Amount20_SimpleType_Negative" minOccurs="0"/&gt;
 *         &lt;element name="PricMltplr" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAImpliedCurrencyAnd20AmountMinExcl0" minOccurs="0"/&gt;
 *         &lt;element name="Qty" type="{urn:kdpw:xsd:trar.ins.001.03}Amount20_SimpleType" minOccurs="0"/&gt;
 *         &lt;element name="UpFrntPmt" type="{urn:kdpw:xsd:trar.ins.001.03}Amount20_SimpleType_Negative" minOccurs="0"/&gt;
 *         &lt;element name="DlvryTp" type="{urn:kdpw:xsd:trar.ins.001.03}PhysicalTransferType4Code" minOccurs="0"/&gt;
 *         &lt;element name="ExctnDtTm" type="{urn:kdpw:xsd:trar.ins.001.03}ISONormalisedDateTime" minOccurs="0"/&gt;
 *         &lt;element name="FctvDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="MtrtyDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="TermntnDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="SttlmDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="MstrAgrmt" type="{urn:kdpw:xsd:trar.ins.001.03}MasterAgreement_TR" minOccurs="0"/&gt;
 *         &lt;element name="TradConf" type="{urn:kdpw:xsd:trar.ins.001.03}TradeConfirmation_TR" minOccurs="0"/&gt;
 *         &lt;element name="TradClr" type="{urn:kdpw:xsd:trar.ins.001.03}TradeClearing_TR_M" minOccurs="0"/&gt;
 *         &lt;element name="IntrstRate" type="{urn:kdpw:xsd:trar.ins.001.03}InterestRateLegs4__1" minOccurs="0"/&gt;
 *         &lt;element name="Ccy" type="{urn:kdpw:xsd:trar.ins.001.03}CurrencyExchange10__1" minOccurs="0"/&gt;
 *         &lt;element name="Cmmdty" type="{urn:kdpw:xsd:trar.ins.001.03}CommodityTrade_M" minOccurs="0"/&gt;
 *         &lt;element name="Optn" type="{urn:kdpw:xsd:trar.ins.001.03}Option_TR_M" minOccurs="0"/&gt;
 *         &lt;element name="Cdt" type="{urn:kdpw:xsd:trar.ins.001.03}CreditDerivative_TR_M" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeTransaction11__4", propOrder = {
    "unqTradIdr",
    "rptTrckgNb",
    "cmplxTradId",
    "pric",
    "ntnlAmt",
    "pricMltplr",
    "qty",
    "upFrntPmt",
    "dlvryTp",
    "exctnDtTm",
    "fctvDt",
    "mtrtyDt",
    "termntnDt",
    "sttlmDt",
    "mstrAgrmt",
    "tradConf",
    "tradClr",
    "intrstRate",
    "ccy",
    "cmmdty",
    "optn",
    "cdt"
})
public class TradeTransaction114 {

    @XmlElement(name = "UnqTradIdr", required = true)
    protected String unqTradIdr;
    @XmlElement(name = "RptTrckgNb")
    protected String rptTrckgNb;
    @XmlElement(name = "CmplxTradId")
    protected String cmplxTradId;
    @XmlElement(name = "Pric")
    protected SecuritiesTransactionPrice7ChoiceTR pric;
    @XmlElement(name = "NtnlAmt")
    protected BigDecimal ntnlAmt;
    @XmlElement(name = "PricMltplr")
    protected BigDecimal pricMltplr;
    @XmlElement(name = "Qty")
    protected BigDecimal qty;
    @XmlElement(name = "UpFrntPmt")
    protected BigDecimal upFrntPmt;
    @XmlElement(name = "DlvryTp")
    @XmlSchemaType(name = "string")
    protected PhysicalTransferType4Code dlvryTp;
    @XmlElement(name = "ExctnDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exctnDtTm;
    @XmlElement(name = "FctvDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fctvDt;
    @XmlElement(name = "MtrtyDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar mtrtyDt;
    @XmlElement(name = "TermntnDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar termntnDt;
    @XmlElement(name = "SttlmDt")
    @XmlSchemaType(name = "date")
    protected List<XMLGregorianCalendar> sttlmDt;
    @XmlElement(name = "MstrAgrmt")
    protected MasterAgreementTR mstrAgrmt;
    @XmlElement(name = "TradConf")
    protected TradeConfirmationTR tradConf;
    @XmlElement(name = "TradClr")
    protected TradeClearingTRM tradClr;
    @XmlElement(name = "IntrstRate")
    protected InterestRateLegs41 intrstRate;
    @XmlElement(name = "Ccy")
    protected CurrencyExchange101 ccy;
    @XmlElement(name = "Cmmdty")
    protected CommodityTradeM cmmdty;
    @XmlElement(name = "Optn")
    protected OptionTRM optn;
    @XmlElement(name = "Cdt")
    protected CreditDerivativeTRM cdt;

    /**
     * Gets the value of the unqTradIdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnqTradIdr() {
        return unqTradIdr;
    }

    /**
     * Sets the value of the unqTradIdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnqTradIdr(String value) {
        this.unqTradIdr = value;
    }

    /**
     * Gets the value of the rptTrckgNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRptTrckgNb() {
        return rptTrckgNb;
    }

    /**
     * Sets the value of the rptTrckgNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRptTrckgNb(String value) {
        this.rptTrckgNb = value;
    }

    /**
     * Gets the value of the cmplxTradId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmplxTradId() {
        return cmplxTradId;
    }

    /**
     * Sets the value of the cmplxTradId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmplxTradId(String value) {
        this.cmplxTradId = value;
    }

    /**
     * Gets the value of the pric property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesTransactionPrice7ChoiceTR }
     *     
     */
    public SecuritiesTransactionPrice7ChoiceTR getPric() {
        return pric;
    }

    /**
     * Sets the value of the pric property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesTransactionPrice7ChoiceTR }
     *     
     */
    public void setPric(SecuritiesTransactionPrice7ChoiceTR value) {
        this.pric = value;
    }

    /**
     * Gets the value of the ntnlAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNtnlAmt() {
        return ntnlAmt;
    }

    /**
     * Sets the value of the ntnlAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNtnlAmt(BigDecimal value) {
        this.ntnlAmt = value;
    }

    /**
     * Gets the value of the pricMltplr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPricMltplr() {
        return pricMltplr;
    }

    /**
     * Sets the value of the pricMltplr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPricMltplr(BigDecimal value) {
        this.pricMltplr = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQty(BigDecimal value) {
        this.qty = value;
    }

    /**
     * Gets the value of the upFrntPmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUpFrntPmt() {
        return upFrntPmt;
    }

    /**
     * Sets the value of the upFrntPmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUpFrntPmt(BigDecimal value) {
        this.upFrntPmt = value;
    }

    /**
     * Gets the value of the dlvryTp property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalTransferType4Code }
     *     
     */
    public PhysicalTransferType4Code getDlvryTp() {
        return dlvryTp;
    }

    /**
     * Sets the value of the dlvryTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalTransferType4Code }
     *     
     */
    public void setDlvryTp(PhysicalTransferType4Code value) {
        this.dlvryTp = value;
    }

    /**
     * Gets the value of the exctnDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExctnDtTm() {
        return exctnDtTm;
    }

    /**
     * Sets the value of the exctnDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExctnDtTm(XMLGregorianCalendar value) {
        this.exctnDtTm = value;
    }

    /**
     * Gets the value of the fctvDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFctvDt() {
        return fctvDt;
    }

    /**
     * Sets the value of the fctvDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFctvDt(XMLGregorianCalendar value) {
        this.fctvDt = value;
    }

    /**
     * Gets the value of the mtrtyDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMtrtyDt() {
        return mtrtyDt;
    }

    /**
     * Sets the value of the mtrtyDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMtrtyDt(XMLGregorianCalendar value) {
        this.mtrtyDt = value;
    }

    /**
     * Gets the value of the termntnDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTermntnDt() {
        return termntnDt;
    }

    /**
     * Sets the value of the termntnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTermntnDt(XMLGregorianCalendar value) {
        this.termntnDt = value;
    }

    /**
     * Gets the value of the sttlmDt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sttlmDt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSttlmDt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getSttlmDt() {
        if (sttlmDt == null) {
            sttlmDt = new ArrayList<XMLGregorianCalendar>();
        }
        return this.sttlmDt;
    }

    /**
     * Gets the value of the mstrAgrmt property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementTR }
     *     
     */
    public MasterAgreementTR getMstrAgrmt() {
        return mstrAgrmt;
    }

    /**
     * Sets the value of the mstrAgrmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementTR }
     *     
     */
    public void setMstrAgrmt(MasterAgreementTR value) {
        this.mstrAgrmt = value;
    }

    /**
     * Gets the value of the tradConf property.
     * 
     * @return
     *     possible object is
     *     {@link TradeConfirmationTR }
     *     
     */
    public TradeConfirmationTR getTradConf() {
        return tradConf;
    }

    /**
     * Sets the value of the tradConf property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeConfirmationTR }
     *     
     */
    public void setTradConf(TradeConfirmationTR value) {
        this.tradConf = value;
    }

    /**
     * Gets the value of the tradClr property.
     * 
     * @return
     *     possible object is
     *     {@link TradeClearingTRM }
     *     
     */
    public TradeClearingTRM getTradClr() {
        return tradClr;
    }

    /**
     * Sets the value of the tradClr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeClearingTRM }
     *     
     */
    public void setTradClr(TradeClearingTRM value) {
        this.tradClr = value;
    }

    /**
     * Gets the value of the intrstRate property.
     * 
     * @return
     *     possible object is
     *     {@link InterestRateLegs41 }
     *     
     */
    public InterestRateLegs41 getIntrstRate() {
        return intrstRate;
    }

    /**
     * Sets the value of the intrstRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterestRateLegs41 }
     *     
     */
    public void setIntrstRate(InterestRateLegs41 value) {
        this.intrstRate = value;
    }

    /**
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyExchange101 }
     *     
     */
    public CurrencyExchange101 getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyExchange101 }
     *     
     */
    public void setCcy(CurrencyExchange101 value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the cmmdty property.
     * 
     * @return
     *     possible object is
     *     {@link CommodityTradeM }
     *     
     */
    public CommodityTradeM getCmmdty() {
        return cmmdty;
    }

    /**
     * Sets the value of the cmmdty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommodityTradeM }
     *     
     */
    public void setCmmdty(CommodityTradeM value) {
        this.cmmdty = value;
    }

    /**
     * Gets the value of the optn property.
     * 
     * @return
     *     possible object is
     *     {@link OptionTRM }
     *     
     */
    public OptionTRM getOptn() {
        return optn;
    }

    /**
     * Sets the value of the optn property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionTRM }
     *     
     */
    public void setOptn(OptionTRM value) {
        this.optn = value;
    }

    /**
     * Gets the value of the cdt property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDerivativeTRM }
     *     
     */
    public CreditDerivativeTRM getCdt() {
        return cdt;
    }

    /**
     * Sets the value of the cdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDerivativeTRM }
     *     
     */
    public void setCdt(CreditDerivativeTRM value) {
        this.cdt = value;
    }

}
