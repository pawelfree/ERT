//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.10 at 07:51:49 PM CEST 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DurationType1Code_TR.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DurationType1Code_TR"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Y"/&gt;
 *     &lt;enumeration value="W"/&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="Q"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *     &lt;enumeration value="N"/&gt;
 *     &lt;enumeration value="H"/&gt;
 *     &lt;enumeration value="D"/&gt;
 *     &lt;enumeration value="O"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DurationType1Code_TR")
@XmlEnum
public enum DurationType1CodeTR {


    /**
     * Duration is a year.
     * 
     */
    Y,

    /**
     * Event takes place every week
     * 
     */
    W,

    /**
     * Event takes place every six months or two times a year.
     * 
     */
    S,

    /**
     * Event takes place every three months or four times a year.
     * 
     */
    Q,

    /**
     * Event takes place every month or once a month.
     * 
     */
    M,

    /**
     * Minute
     * 
     */
    N,

    /**
     * Hour
     * 
     */
    H,

    /**
     * Duration is a day.
     * 
     */
    D,

    /**
     * Other
     * 
     */
    O;

    public String value() {
        return name();
    }

    public static DurationType1CodeTR fromValue(String v) {
        return valueOf(v);
    }

}
