//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 09:33:47 AM CEST 
//
package kdpw.xsd.trar_sts_002;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import kdpw.xsd.IKDPWDocument;

/**
 * Komunikat systemu KDPW
 *
 * <p>
 * Java class for KDPWDocument complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="KDPWDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="trar.sts.002.02" type="{urn:kdpw:xsd:trar.sts.002.02}trar.sts.002.02" maxOccurs="10000"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Sndr" use="required" type="{urn:kdpw:xsd:trar.sts.002.02}KDPWMemberIdentifier" /&gt;
 *       &lt;attribute name="Rcvr" use="required" type="{urn:kdpw:xsd:trar.sts.002.02}KDPWMemberIdentifier" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlRootElement(name = "KDPWDocument")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KDPWDocument", propOrder = {
    "trarSts00202"
})
public class KDPWDocument implements IKDPWDocument {

    @XmlElement(name = "trar.sts.002.02", required = true)
    protected List<TrarSts00202> trarSts00202;
    @XmlAttribute(name = "Sndr", required = true)
    protected String sndr;
    @XmlAttribute(name = "Rcvr", required = true)
    protected String rcvr;

    /**
     * Gets the value of the trarSts00202 property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the trarSts00202 property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrarSts00202().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TrarSts00202 }
     *
     *
     */
    public List<TrarSts00202> getTrarSts00202() {
        if (trarSts00202 == null) {
            trarSts00202 = new ArrayList<>();
        }
        return this.trarSts00202;
    }

    /**
     * Gets the value of the sndr property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSndr() {
        return sndr;
    }

    /**
     * Sets the value of the sndr property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSndr(String value) {
        this.sndr = value;
    }

    /**
     * Gets the value of the rcvr property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRcvr() {
        return rcvr;
    }

    /**
     * Sets the value of the rcvr property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRcvr(String value) {
        this.rcvr = value;
    }

}
