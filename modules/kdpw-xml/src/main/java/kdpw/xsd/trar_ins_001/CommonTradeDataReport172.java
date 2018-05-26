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
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to contract and transaction details.
 * 
 * <p>Java class for CommonTradeDataReport17__2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonTradeDataReport17__2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrctData" type="{urn:kdpw:xsd:trar.ins.001.03}ContractType3__2"/&gt;
 *         &lt;element name="TxData" type="{urn:kdpw:xsd:trar.ins.001.03}TradeTransaction10__2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonTradeDataReport17__2", propOrder = {
    "ctrctData",
    "txData"
})
public class CommonTradeDataReport172 {

    @XmlElement(name = "CtrctData", required = true)
    protected ContractType32 ctrctData;
    @XmlElement(name = "TxData", required = true)
    protected TradeTransaction102 txData;

    /**
     * Gets the value of the ctrctData property.
     * 
     * @return
     *     possible object is
     *     {@link ContractType32 }
     *     
     */
    public ContractType32 getCtrctData() {
        return ctrctData;
    }

    /**
     * Sets the value of the ctrctData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType32 }
     *     
     */
    public void setCtrctData(ContractType32 value) {
        this.ctrctData = value;
    }

    /**
     * Gets the value of the txData property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTransaction102 }
     *     
     */
    public TradeTransaction102 getTxData() {
        return txData;
    }

    /**
     * Sets the value of the txData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTransaction102 }
     *     
     */
    public void setTxData(TradeTransaction102 value) {
        this.txData = value;
    }

}
