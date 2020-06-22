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
 * Provides details on the reported trade transactions.
 * 
 * <p>Java class for TradeTransactionReportChoice_TR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeTransactionReportChoice_TR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="New" type="{urn:kdpw:xsd:trar.ins.001.04}TradeNewTransaction_TR"/&gt;
 *           &lt;element name="Mod" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionModification_TR"/&gt;
 *           &lt;element name="Crrctn" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionCorrection_TR"/&gt;
 *           &lt;element name="EarlyTermntn" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionEarlyTermination_TR"/&gt;
 *           &lt;element name="PosCmpnt" type="{urn:kdpw:xsd:trar.ins.001.04}TradePositionComponent_TR"/&gt;
 *           &lt;element name="ValtnUpd" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionValuationUpdate_TR"/&gt;
 *           &lt;element name="Cmprssn" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionCompression_TR"/&gt;
 *           &lt;element name="Err" type="{urn:kdpw:xsd:trar.ins.001.04}TradeTransactionError_TR"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeTransactionReportChoice_TR", propOrder = {
    "_new",
    "mod",
    "crrctn",
    "valtnUpd"
})
public class TradeTransactionReportChoiceTR {

    @XmlElement(name = "New")
    protected TradeNewTransactionTR _new;
    @XmlElement(name = "Mod")
    protected TradeTransactionModificationTR mod;
    @XmlElement(name = "Crrctn")
    protected TradeTransactionCorrectionTR crrctn;
    @XmlElement(name = "ValtnUpd")
    protected TradeTransactionValuationUpdateTR valtnUpd;

    /**
     * Gets the value of the new property.
     * 
     * @return
     *     possible object is
     *     {@link TradeNewTransactionTR }
     *     
     */
    public TradeNewTransactionTR getNew() {
        return _new;
    }

    /**
     * Sets the value of the new property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeNewTransactionTR }
     *     
     */
    public void setNew(TradeNewTransactionTR value) {
        this._new = value;
    }

    /**
     * Gets the value of the mod property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTransactionModificationTR }
     *     
     */
    public TradeTransactionModificationTR getMod() {
        return mod;
    }

    /**
     * Sets the value of the mod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTransactionModificationTR }
     *     
     */
    public void setMod(TradeTransactionModificationTR value) {
        this.mod = value;
    }

    /**
     * Gets the value of the crrctn property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTransactionCorrectionTR }
     *     
     */
    public TradeTransactionCorrectionTR getCrrctn() {
        return crrctn;
    }

    /**
     * Sets the value of the crrctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTransactionCorrectionTR }
     *     
     */
    public void setCrrctn(TradeTransactionCorrectionTR value) {
        this.crrctn = value;
    }

    /**
     * Gets the value of the valtnUpd property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTransactionValuationUpdateTR }
     *     
     */
    public TradeTransactionValuationUpdateTR getValtnUpd() {
        return valtnUpd;
    }

    /**
     * Sets the value of the valtnUpd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTransactionValuationUpdateTR }
     *     
     */
    public void setValtnUpd(TradeTransactionValuationUpdateTR value) {
        this.valtnUpd = value;
    }

}
