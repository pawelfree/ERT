//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.29 at 02:25:37 PM CEST 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommodityDetails.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CommodityDetails"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="GO"/&gt;
 *     &lt;enumeration value="DA"/&gt;
 *     &lt;enumeration value="LI"/&gt;
 *     &lt;enumeration value="FO"/&gt;
 *     &lt;enumeration value="SO"/&gt;
 *     &lt;enumeration value="SF"/&gt;
 *     &lt;enumeration value="OT"/&gt;
 *     &lt;enumeration value="OI"/&gt;
 *     &lt;enumeration value="NG"/&gt;
 *     &lt;enumeration value="CO"/&gt;
 *     &lt;enumeration value="EL"/&gt;
 *     &lt;enumeration value="IE"/&gt;
 *     &lt;enumeration value="DR"/&gt;
 *     &lt;enumeration value="WT"/&gt;
 *     &lt;enumeration value="PR"/&gt;
 *     &lt;enumeration value="NP"/&gt;
 *     &lt;enumeration value="WE"/&gt;
 *     &lt;enumeration value="EM"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CommodityDetails")
@XmlEnum
public enum CommodityDetails {


    /**
     * GO
     * 
     */
    GO,

    /**
     * DA
     * 
     */
    DA,

    /**
     * LI
     * 
     */
    LI,

    /**
     * FO
     * 
     */
    FO,

    /**
     * SO
     * 
     */
    SO,

    /**
     * SF
     * 
     */
    SF,

    /**
     * OT
     * 
     */
    OT,

    /**
     * OI
     * 
     */
    OI,

    /**
     * NG
     * 
     */
    NG,

    /**
     * CO
     * 
     */
    CO,

    /**
     * EL
     * 
     */
    EL,

    /**
     * IE
     * 
     */
    IE,

    /**
     * DR
     * 
     */
    DR,

    /**
     * WT
     * 
     */
    WT,

    /**
     * PR
     * 
     */
    PR,

    /**
     * NP
     * 
     */
    NP,

    /**
     * WE
     * 
     */
    WE,

    /**
     * EM
     * 
     */
    EM;

    public String value() {
        return name();
    }

    public static CommodityDetails fromValue(String v) {
        return valueOf(v);
    }

}
