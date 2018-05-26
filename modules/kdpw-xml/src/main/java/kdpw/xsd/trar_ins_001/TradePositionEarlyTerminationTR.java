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
import javax.xml.bind.annotation.XmlType;


/**
 * Provides details of an early termination report on a trade position.
 * 
 * <p>Java class for TradePositionEarlyTermination_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradePositionEarlyTermination_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrPtySpcfcData" type="{urn:kdpw:xsd:trar.ins.001.03}CounterpartySpecificData_TR_Z" maxOccurs="2"/&gt;
 *         &lt;element name="CmonTradData" type="{urn:kdpw:xsd:trar.ins.001.03}CommonTradeDataReport16__1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePositionEarlyTermination_TR", propOrder = {
    "ctrPtySpcfcData",
    "cmonTradData"
})
public class TradePositionEarlyTerminationTR {

    @XmlElement(name = "CtrPtySpcfcData", required = true)
    protected List<CounterpartySpecificDataTRZ> ctrPtySpcfcData;
    @XmlElement(name = "CmonTradData", required = true)
    protected CommonTradeDataReport161 cmonTradData;

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
     * {@link CounterpartySpecificDataTRZ }
     * 
     * 
     */
    public List<CounterpartySpecificDataTRZ> getCtrPtySpcfcData() {
        if (ctrPtySpcfcData == null) {
            ctrPtySpcfcData = new ArrayList<CounterpartySpecificDataTRZ>();
        }
        return this.ctrPtySpcfcData;
    }

    /**
     * Gets the value of the cmonTradData property.
     * 
     * @return
     *     possible object is
     *     {@link CommonTradeDataReport161 }
     *     
     */
    public CommonTradeDataReport161 getCmonTradData() {
        return cmonTradData;
    }

    /**
     * Sets the value of the cmonTradData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonTradeDataReport161 }
     *     
     */
    public void setCmonTradData(CommonTradeDataReport161 value) {
        this.cmonTradData = value;
    }

}
