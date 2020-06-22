//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:17 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to contract and transaction details.
 * 
 * <p>Java class for CommonTradeDataReport17__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonTradeDataReport17__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CtrctData" type="{urn:kdpw:xsd:trar.ins.001.04}ContractType3__1"/&gt;
 *         &lt;element name="TxData" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransaction10__1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonTradeDataReport17__1", propOrder = {
    "ctrctData",
    "txData"
})
public class CommonTradeDataReport171 {

    @XmlElement(name = "CtrctData", required = true)
    protected ContractType31 ctrctData;
    @XmlElement(name = "TxData", required = true)
    protected TradeTransaction101 txData;

    /**
     * Gets the value of the ctrctData property.
     * 
     * @return
     *     possible object is
     *     {@link ContractType31 }
     *     
     */
    public ContractType31 getCtrctData() {
        return ctrctData;
    }

    /**
     * Sets the value of the ctrctData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType31 }
     *     
     */
    public void setCtrctData(ContractType31 value) {
        this.ctrctData = value;
    }

    /**
     * Gets the value of the txData property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTransaction101 }
     *     
     */
    public TradeTransaction101 getTxData() {
        return txData;
    }

    /**
     * Sets the value of the txData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTransaction101 }
     *     
     */
    public void setTxData(TradeTransaction101 value) {
        this.txData = value;
    }

}
