//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.05 at 10:55:54 PM CEST 
//


package kdpw.xsd.trar_sts_003;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OptionParty1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OptionParty1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="B"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OptionParty1Code")
@XmlEnum
public enum OptionParty1Code {


    /**
     * Seller in a trade.
     * 
     */
    S,

    /**
     * Buyer in a trade.
     * 
     */
    B;

    public String value() {
        return name();
    }

    public static OptionParty1Code fromValue(String v) {
        return valueOf(v);
    }

}
