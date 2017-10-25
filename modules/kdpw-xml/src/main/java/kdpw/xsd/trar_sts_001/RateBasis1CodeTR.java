//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.24 at 06:51:43 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RateBasis1Code_TR.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RateBasis1Code_TR"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="D"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *     &lt;enumeration value="W"/&gt;
 *     &lt;enumeration value="Y"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RateBasis1Code_TR")
@XmlEnum
public enum RateBasis1CodeTR {


    /**
     * Rate is reported in days.
     * 
     */
    D,

    /**
     * Rate is reported in months.
     * 
     */
    M,

    /**
     * Rate is reported in weeks.
     * 
     */
    W,

    /**
     * Rate is reported in years.
     * 
     */
    Y;

    public String value() {
        return name();
    }

    public static RateBasis1CodeTR fromValue(String v) {
        return valueOf(v);
    }

}