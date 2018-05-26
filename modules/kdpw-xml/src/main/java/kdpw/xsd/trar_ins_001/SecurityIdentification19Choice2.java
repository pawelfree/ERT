//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.19 at 03:49:39 PM CET 
//


package kdpw.xsd.trar_ins_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Choice between ISIN and an alternative format for the identification of a financial instrument. ISIN is the preferred format.
 * 
 * <p>Java class for SecurityIdentification19Choice__2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityIdentification19Choice__2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="ISIN" type="{urn:kdpw:xsd:trar.ins.001.03}ISINOct2015Identifier"/&gt;
 *           &lt;element name="AltrntvInstrmId" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAMax48AlphaNumericAdditionalCharactersAII"/&gt;
 *           &lt;element name="UnqPdctIdr" type="{urn:kdpw:xsd:trar.ins.001.03}ESMAMax52AlphaNumeric"/&gt;
 *           &lt;element name="BsktCnsttnts" type="{urn:kdpw:xsd:trar.ins.001.03}SecurityIdentification18Choice__1" maxOccurs="unbounded"/&gt;
 *           &lt;element name="Indx" type="{urn:kdpw:xsd:trar.ins.001.03}SecurityIdentification20Choice"/&gt;
 *           &lt;element name="IdNotAvlbl" type="{urn:kdpw:xsd:trar.ins.001.03}IdentificationNotAvailable"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityIdentification19Choice__2", propOrder = {
    "isin",
    "altrntvInstrmId",
    "unqPdctIdr",
    "bsktCnsttnts",
    "indx",
    "idNotAvlbl"
})
public class SecurityIdentification19Choice2 {

    @XmlElement(name = "ISIN")
    protected String isin;
    @XmlElement(name = "AltrntvInstrmId")
    protected String altrntvInstrmId;
    @XmlElement(name = "UnqPdctIdr")
    protected String unqPdctIdr;
    @XmlElement(name = "BsktCnsttnts")
    protected List<SecurityIdentification18Choice1> bsktCnsttnts;
    @XmlElement(name = "Indx")
    protected SecurityIdentification20Choice indx;
    @XmlElement(name = "IdNotAvlbl")
    @XmlSchemaType(name = "string")
    protected IdentificationNotAvailable idNotAvlbl;

    /**
     * Gets the value of the isin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIN() {
        return isin;
    }

    /**
     * Sets the value of the isin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISIN(String value) {
        this.isin = value;
    }

    /**
     * Gets the value of the altrntvInstrmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltrntvInstrmId() {
        return altrntvInstrmId;
    }

    /**
     * Sets the value of the altrntvInstrmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltrntvInstrmId(String value) {
        this.altrntvInstrmId = value;
    }

    /**
     * Gets the value of the unqPdctIdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnqPdctIdr() {
        return unqPdctIdr;
    }

    /**
     * Sets the value of the unqPdctIdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnqPdctIdr(String value) {
        this.unqPdctIdr = value;
    }

    /**
     * Gets the value of the bsktCnsttnts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bsktCnsttnts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBsktCnsttnts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityIdentification18Choice1 }
     * 
     * 
     */
    public List<SecurityIdentification18Choice1> getBsktCnsttnts() {
        if (bsktCnsttnts == null) {
            bsktCnsttnts = new ArrayList<SecurityIdentification18Choice1>();
        }
        return this.bsktCnsttnts;
    }

    /**
     * Gets the value of the indx property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityIdentification20Choice }
     *     
     */
    public SecurityIdentification20Choice getIndx() {
        return indx;
    }

    /**
     * Sets the value of the indx property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityIdentification20Choice }
     *     
     */
    public void setIndx(SecurityIdentification20Choice value) {
        this.indx = value;
    }

    /**
     * Gets the value of the idNotAvlbl property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationNotAvailable }
     *     
     */
    public IdentificationNotAvailable getIdNotAvlbl() {
        return idNotAvlbl;
    }

    /**
     * Sets the value of the idNotAvlbl property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationNotAvailable }
     *     
     */
    public void setIdNotAvlbl(IdentificationNotAvailable value) {
        this.idNotAvlbl = value;
    }

}
