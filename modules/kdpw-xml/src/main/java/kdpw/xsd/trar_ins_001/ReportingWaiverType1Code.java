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
 * <p>Java class for ReportingWaiverType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportingWaiverType1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OILQ"/&gt;
 *     &lt;enumeration value="NLIQ"/&gt;
 *     &lt;enumeration value="PRIC"/&gt;
 *     &lt;enumeration value="ILQD"/&gt;
 *     &lt;enumeration value="RFPT"/&gt;
 *     &lt;enumeration value="SIZE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReportingWaiverType1Code")
@XmlEnum
public enum ReportingWaiverType1Code {


    /**
     * Pre-trade waiver was for a negotiated transaction in illiquid financial instruments. Applicable to equity instruments.
     * 
     */
    OILQ,

    /**
     * Pre-trade waiver was for a negotiated transaction in liquid financial instruments. Applicable to equity instruments.
     * 
     */
    NLIQ,

    /**
     * Pre-trade waiver was for a negotiated transaction subject to conditions other than the current market price of that financial instruments. Applicable to equity instruments.
     * 
     */
    PRIC,

    /**
     * Pre-trade waiver was for an illiquid instrument transaction. Applicable to non-equity instruments.
     * 
     */
    ILQD,

    /**
     * Pre-trade waiver was for a reference price transaction. Applicable to equity instruments.
     * 
     */
    RFPT,

    /**
     * Pre-trade waiver was for an above specific size transaction. Applicable to non-equity instruments.
     * 
     */
    SIZE;

    public String value() {
        return name();
    }

    public static ReportingWaiverType1Code fromValue(String v) {
        return valueOf(v);
    }

}
