//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:17 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegulatoryTradingCapacity1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RegulatoryTradingCapacity1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MTCH"/&gt;
 *     &lt;enumeration value="DEAL"/&gt;
 *     &lt;enumeration value="AOTC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RegulatoryTradingCapacity1Code")
@XmlEnum
public enum RegulatoryTradingCapacity1Code {


    /**
     * Transaction was carried out as a matched principal trading under Article 4(38) of Directive 2014/65/EU.
     * 
     */
    MTCH,

    /**
     * Transaction was carried out as a deal under own account under Article 4(6) of Directive 2014/65/EU.
     * 
     */
    DEAL,

    /**
     * Transaction was carried out as an agent.
     * 
     */
    AOTC;

    public String value() {
        return name();
    }

    public static RegulatoryTradingCapacity1Code fromValue(String v) {
        return valueOf(v);
    }

}
