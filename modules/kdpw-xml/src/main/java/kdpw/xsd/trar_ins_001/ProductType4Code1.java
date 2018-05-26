//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductType4Code__1.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductType4Code__1"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CR"/&gt;
 *     &lt;enumeration value="CU"/&gt;
 *     &lt;enumeration value="EQ"/&gt;
 *     &lt;enumeration value="IR"/&gt;
 *     &lt;enumeration value="CO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProductType4Code__1")
@XmlEnum
public enum ProductType4Code1 {


    /**
     * Identifies categories of instruments that are credits.
     * 
     */
    CR,

    /**
     * Identifies categories of currency instruments.
     * 
     */
    CU,

    /**
     * Identifies the nature or type of an equity.
     * 
     */
    EQ,

    /**
     * Identifies categories of instruments that are interest rates based.
     * 
     */
    IR,

    /**
     * Identifies categories of instruments that are commodities.
     * 
     */
    CO;

    public String value() {
        return name();
    }

    public static ProductType4Code1 fromValue(String v) {
        return valueOf(v);
    }

}
