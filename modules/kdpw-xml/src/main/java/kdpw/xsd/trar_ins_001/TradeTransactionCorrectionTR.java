//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Provides details of a correction report on a trade transaction.
 * 
 * <p>Java class for TradeTransactionCorrection_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeTransactionCorrection_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EligDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate"/&gt;
 *         &lt;element name="RglntInd" type="{urn:kdpw:xsd:trar.ins.001.03}RegulationIndicator"/&gt;
 *         &lt;element name="CtrPtySpcfcData" type="{urn:kdpw:xsd:trar.ins.001.03}CounterpartySpecificData_TR_R" maxOccurs="2"/&gt;
 *         &lt;element name="CmonTradData" type="{urn:kdpw:xsd:trar.ins.001.03}CommonTradeDataReport_TR_R"/&gt;
 *         &lt;element name="MIFIRrptgData" type="{urn:kdpw:xsd:trar.ins.001.03}MIFIRReportingData" maxOccurs="2" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeTransactionCorrection_TR", propOrder = {
    "eligDt",
    "rglntInd",
    "ctrPtySpcfcData",
    "cmonTradData",
    "mifiRrptgData"
})
public class TradeTransactionCorrectionTR {

    @XmlElement(name = "EligDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eligDt;
    @XmlElement(name = "RglntInd", required = true)
    @XmlSchemaType(name = "string")
    protected RegulationIndicator rglntInd;
    @XmlElement(name = "CtrPtySpcfcData", required = true)
    protected List<CounterpartySpecificDataTRR> ctrPtySpcfcData;
    @XmlElement(name = "CmonTradData", required = true)
    protected CommonTradeDataReportTRR cmonTradData;
    @XmlElement(name = "MIFIRrptgData")
    protected List<MIFIRReportingData> mifiRrptgData;

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
     * Gets the value of the rglntInd property.
     * 
     * @return
     *     possible object is
     *     {@link RegulationIndicator }
     *     
     */
    public RegulationIndicator getRglntInd() {
        return rglntInd;
    }

    /**
     * Sets the value of the rglntInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegulationIndicator }
     *     
     */
    public void setRglntInd(RegulationIndicator value) {
        this.rglntInd = value;
    }

    /**
     * Gets the value of the ctrPtySpcfcData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ctrPtySpcfcData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCtrPtySpcfcData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CounterpartySpecificDataTRR }
     * 
     * 
     */
    public List<CounterpartySpecificDataTRR> getCtrPtySpcfcData() {
        if (ctrPtySpcfcData == null) {
            ctrPtySpcfcData = new ArrayList<CounterpartySpecificDataTRR>();
        }
        return this.ctrPtySpcfcData;
    }

    /**
     * Gets the value of the cmonTradData property.
     * 
     * @return
     *     possible object is
     *     {@link CommonTradeDataReportTRR }
     *     
     */
    public CommonTradeDataReportTRR getCmonTradData() {
        return cmonTradData;
    }

    /**
     * Sets the value of the cmonTradData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonTradeDataReportTRR }
     *     
     */
    public void setCmonTradData(CommonTradeDataReportTRR value) {
        this.cmonTradData = value;
    }

    /**
     * Gets the value of the mifiRrptgData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mifiRrptgData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMIFIRrptgData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MIFIRReportingData }
     * 
     * 
     */
    public List<MIFIRReportingData> getMIFIRrptgData() {
        if (mifiRrptgData == null) {
            mifiRrptgData = new ArrayList<MIFIRReportingData>();
        }
        return this.mifiRrptgData;
    }

}
