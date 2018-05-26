//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:41 PM CET 
//


package kdpw.xsd.trar_sts_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnergyQuantityUnit_TR.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnergyQuantityUnit_TR"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Therm/d"/&gt;
 *     &lt;enumeration value="MWh/h"/&gt;
 *     &lt;enumeration value="MWh/d"/&gt;
 *     &lt;enumeration value="MW"/&gt;
 *     &lt;enumeration value="MTherm/d"/&gt;
 *     &lt;enumeration value="cm/d"/&gt;
 *     &lt;enumeration value="mcm/d"/&gt;
 *     &lt;enumeration value="KWh/h"/&gt;
 *     &lt;enumeration value="KWh/d"/&gt;
 *     &lt;enumeration value="KW"/&gt;
 *     &lt;enumeration value="KTherm/d"/&gt;
 *     &lt;enumeration value="GWh/h"/&gt;
 *     &lt;enumeration value="GWh/d"/&gt;
 *     &lt;enumeration value="GW"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EnergyQuantityUnit_TR")
@XmlEnum
public enum EnergyQuantityUnitTR {


    /**
     * Therm per day.
     * 
     */
    @XmlEnumValue("Therm/d")
    THERM_D("Therm/d"),

    /**
     * Mega Watt hour per hour.
     * 
     */
    @XmlEnumValue("MWh/h")
    M_WH_H("MWh/h"),

    /**
     * Mega Watt hour per day.
     * 
     */
    @XmlEnumValue("MWh/d")
    M_WH_D("MWh/d"),

    /**
     * Mega Watt.
     * 
     */
    MW("MW"),

    /**
     * MTherm per day.
     * 
     */
    @XmlEnumValue("MTherm/d")
    M_THERM_D("MTherm/d"),

    /**
     * Cm per day.
     * 
     */
    @XmlEnumValue("cm/d")
    CM_D("cm/d"),

    /**
     * Mcm per day.
     * 
     * 
     */
    @XmlEnumValue("mcm/d")
    MCM_D("mcm/d"),

    /**
     * Kilo Watt hour per hour.
     * 
     */
    @XmlEnumValue("KWh/h")
    K_WH_H("KWh/h"),

    /**
     * Kilo Watt hour per day.
     * 
     */
    @XmlEnumValue("KWh/d")
    K_WH_D("KWh/d"),

    /**
     * Kilo Watt.
     * 
     */
    KW("KW"),

    /**
     * KTherm per day.
     * 
     */
    @XmlEnumValue("KTherm/d")
    K_THERM_D("KTherm/d"),

    /**
     * Giga Watt hour per hour.
     * 
     */
    @XmlEnumValue("GWh/h")
    G_WH_H("GWh/h"),

    /**
     * Giga Watt hour per day.
     * 
     */
    @XmlEnumValue("GWh/d")
    G_WH_D("GWh/d"),

    /**
     * Giga Watt.
     * 
     */
    GW("GW");
    private final String value;

    EnergyQuantityUnitTR(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnergyQuantityUnitTR fromValue(String v) {
        for (EnergyQuantityUnitTR c: EnergyQuantityUnitTR.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
