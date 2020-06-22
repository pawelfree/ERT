//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 04:37:18 PM CEST 
//


package kdpw.xsd.trar_rqs_001;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the kdpw.xsd.trar_rqs_001 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _KDPWDocument_QNAME = new QName("urn:kdpw:xsd:trar.rqs.001.02", "KDPWDocument");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: kdpw.xsd.trar_rqs_001
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KDPWDocument }
     * 
     */
    public KDPWDocument createKDPWDocument() {
        return new KDPWDocument();
    }

    /**
     * Create an instance of {@link DateAndDateTimeChoice }
     * 
     */
    public DateAndDateTimeChoice createDateAndDateTimeChoice() {
        return new DateAndDateTimeChoice();
    }

    /**
     * Create an instance of {@link FilterInformation }
     * 
     */
    public FilterInformation createFilterInformation() {
        return new FilterInformation();
    }

    /**
     * Create an instance of {@link GeneralInformation }
     * 
     */
    public GeneralInformation createGeneralInformation() {
        return new GeneralInformation();
    }

    /**
     * Create an instance of {@link InstitutionCode }
     * 
     */
    public InstitutionCode createInstitutionCode() {
        return new InstitutionCode();
    }

    /**
     * Create an instance of {@link Period }
     * 
     */
    public Period createPeriod() {
        return new Period();
    }

    /**
     * Create an instance of {@link TradeIdentification }
     * 
     */
    public TradeIdentification createTradeIdentification() {
        return new TradeIdentification();
    }

    /**
     * Create an instance of {@link TradeListIdentification }
     * 
     */
    public TradeListIdentification createTradeListIdentification() {
        return new TradeListIdentification();
    }

    /**
     * Create an instance of {@link TRInstitutionCode }
     * 
     */
    public TRInstitutionCode createTRInstitutionCode() {
        return new TRInstitutionCode();
    }

    /**
     * Create an instance of {@link TRInstitutionCode2 }
     * 
     */
    public TRInstitutionCode2 createTRInstitutionCode2() {
        return new TRInstitutionCode2();
    }

    /**
     * Create an instance of {@link TrarRqs00102 }
     * 
     */
    public TrarRqs00102 createTrarRqs00102() {
        return new TrarRqs00102();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KDPWDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:kdpw:xsd:trar.rqs.001.02", name = "KDPWDocument")
    public JAXBElement<KDPWDocument> createKDPWDocument(KDPWDocument value) {
        return new JAXBElement<KDPWDocument>(_KDPWDocument_QNAME, KDPWDocument.class, null, value);
    }

}
