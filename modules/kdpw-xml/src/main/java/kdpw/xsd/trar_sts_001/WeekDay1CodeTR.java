//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:52 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeekDay1Code_TR.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WeekDay1Code_TR"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="WN"/&gt;
 *     &lt;enumeration value="WD"/&gt;
 *     &lt;enumeration value="WE"/&gt;
 *     &lt;enumeration value="TU"/&gt;
 *     &lt;enumeration value="TH"/&gt;
 *     &lt;enumeration value="SU"/&gt;
 *     &lt;enumeration value="SA"/&gt;
 *     &lt;enumeration value="MO"/&gt;
 *     &lt;enumeration value="FR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "WeekDay1Code_TR")
@XmlEnum
public enum WeekDay1CodeTR {


    /**
     * Weekend.
     * 
     */
    WN,

    /**
     * Weekdays.
     * 
     */
    WD,

    /**
     * Wednesday.
     * 
     */
    WE,

    /**
     * Tuesday.
     * 
     */
    TU,

    /**
     * Thursday.
     * 
     */
    TH,

    /**
     * Sunday. 
     * 
     */
    SU,

    /**
     * Saturday. 
     * 
     */
    SA,

    /**
     * Monday.
     * 
     */
    MO,

    /**
     * Friday.
     * 
     */
    FR;

    public String value() {
        return name();
    }

    public static WeekDay1CodeTR fromValue(String v) {
        return valueOf(v);
    }

}
