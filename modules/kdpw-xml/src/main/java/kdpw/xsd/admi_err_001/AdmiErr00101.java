//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.02 at 09:48:26 AM CEST 
//


package kdpw.xsd.admi_err_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Błąd formalny instrukcji
 * 
 * <p>Java class for admi.err.001.01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="admi.err.001.01"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GnlInf" type="{urn:kdpw:xsd:admi.err.001.01}GeneralInformation"/&gt;
 *         &lt;element name="FileInf" type="{urn:kdpw:xsd:admi.err.001.01}FileInformation"/&gt;
 *         &lt;element name="ErrDtls" type="{urn:kdpw:xsd:admi.err.001.01}ErrorDetails"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "admi.err.001.01", propOrder = {
    "gnlInf",
    "fileInf",
    "errDtls"
})
public class AdmiErr00101 {

    @XmlElement(name = "GnlInf", required = true)
    protected GeneralInformation gnlInf;
    @XmlElement(name = "FileInf", required = true)
    protected FileInformation fileInf;
    @XmlElement(name = "ErrDtls", required = true)
    protected ErrorDetails errDtls;

    /**
     * Gets the value of the gnlInf property.
     * 
     * @return
     *     possible object is
     *     {@link GeneralInformation }
     *     
     */
    public GeneralInformation getGnlInf() {
        return gnlInf;
    }

    /**
     * Sets the value of the gnlInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralInformation }
     *     
     */
    public void setGnlInf(GeneralInformation value) {
        this.gnlInf = value;
    }

    /**
     * Gets the value of the fileInf property.
     * 
     * @return
     *     possible object is
     *     {@link FileInformation }
     *     
     */
    public FileInformation getFileInf() {
        return fileInf;
    }

    /**
     * Sets the value of the fileInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileInformation }
     *     
     */
    public void setFileInf(FileInformation value) {
        this.fileInf = value;
    }

    /**
     * Gets the value of the errDtls property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetails }
     *     
     */
    public ErrorDetails getErrDtls() {
        return errDtls;
    }

    /**
     * Sets the value of the errDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetails }
     *     
     */
    public void setErrDtls(ErrorDetails value) {
        this.errDtls = value;
    }

}
