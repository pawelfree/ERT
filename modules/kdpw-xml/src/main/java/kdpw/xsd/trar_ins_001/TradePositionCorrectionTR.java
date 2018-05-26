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
 * Provides details of a correction report on a trade position.
 * 
 * <p>Java class for TradePositionCorrection_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradePositionCorrection_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EligDt" type="{urn:kdpw:xsd:trar.ins.001.03}ISODate"/&gt;
 *         &lt;element name="CtrPtySpcfcData" type="{urn:kdpw:xsd:trar.ins.001.03}CounterpartySpecificData_TR_P_R" maxOccurs="2"/&gt;
 *         &lt;element name="CmonTradData" type="{urn:kdpw:xsd:trar.ins.001.03}CommonTradeDataReport_TR_P_R"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePositionCorrection_TR", propOrder = {
    "eligDt",
    "ctrPtySpcfcData",
    "cmonTradData"
})
public class TradePositionCorrectionTR {

    @XmlElement(name = "EligDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eligDt;
    @XmlElement(name = "CtrPtySpcfcData", required = true)
    protected List<CounterpartySpecificDataTRPR> ctrPtySpcfcData;
    @XmlElement(name = "CmonTradData", required = true)
    protected CommonTradeDataReportTRPR cmonTradData;

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
     * {@link CounterpartySpecificDataTRPR }
     * 
     * 
     */
    public List<CounterpartySpecificDataTRPR> getCtrPtySpcfcData() {
        if (ctrPtySpcfcData == null) {
            ctrPtySpcfcData = new ArrayList<CounterpartySpecificDataTRPR>();
        }
        return this.ctrPtySpcfcData;
    }

    /**
     * Gets the value of the cmonTradData property.
     * 
     * @return
     *     possible object is
     *     {@link CommonTradeDataReportTRPR }
     *     
     */
    public CommonTradeDataReportTRPR getCmonTradData() {
        return cmonTradData;
    }

    /**
     * Sets the value of the cmonTradData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonTradeDataReportTRPR }
     *     
     */
    public void setCmonTradData(CommonTradeDataReportTRPR value) {
        this.cmonTradData = value;
    }

}
