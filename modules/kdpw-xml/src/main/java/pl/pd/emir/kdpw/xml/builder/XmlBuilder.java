package pl.pd.emir.kdpw.xml.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import kdpw.xsd.IKDPWDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public final class XmlBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlBuilder.class);

    private static final String XML_ENCODING = "UTF-8";

    private static final Class[] BUILDER_CLASSES = new Class[]{
        kdpw.xsd.trar_ins_001.KDPWDocument.class,
        kdpw.xsd.trar_ins_002.KDPWDocument.class,
        kdpw.xsd.trar_ins_003.KDPWDocument.class,
        kdpw.xsd.trar_ins_004.KDPWDocument.class,
        kdpw.xsd.trar_ins_005.KDPWDocument.class,
        kdpw.xsd.trar_ins_006.KDPWDocument.class,
        kdpw.xsd.trar_ntf_001.KDPWDocument.class,
        kdpw.xsd.trar_rcn_001.KDPWDocument.class,
        kdpw.xsd.trar_rqs_001.KDPWDocument.class,
        kdpw.xsd.trar_sts_001.KDPWDocument.class,
        kdpw.xsd.trar_sts_002.KDPWDocument.class,
        kdpw.xsd.trar_sts_003.KDPWDocument.class
    };

    private static final String VALID_SCHEMA_FILE = "validationSchema.properties";

    private XmlBuilder() {
        super();
    }

    public static String build(final IKDPWDocument document) throws XmlParseException {
        try {
            final Schema schema = loadSchema(getValidationSchemaPath(document));
            final JAXBContext jaxbc = JAXBContext.newInstance(document.getClass());
            final Marshaller marshaller = jaxbc.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            marshaller.setEventHandler(new XmlValidationEventHandler());

            final Writer writer = new StringWriter();

            marshaller.marshal(document, writer);
            return new String(writer.toString().getBytes(XML_ENCODING));
        } catch (JAXBException | UnsupportedEncodingException ex) {
            LOGGER.error("prepareXml marshall error ", ex);
            throw new XmlParseException(ex);
        }
    }

    public static IKDPWDocument readMessage(final String xmlMessage) throws XmlParseException {
        try {
            final XmlBuilderErrorHandler errorHandler = new XmlBuilderErrorHandler();
            final JAXBContext jaxbc = JAXBContext.newInstance(BUILDER_CLASSES);
            final Unmarshaller unmarshaller = jaxbc.createUnmarshaller();
            final IKDPWDocument kdpwDocument = (IKDPWDocument) unmarshaller.unmarshal(new StringReader(xmlMessage));
            final Schema schema = loadSchema(getValidationSchemaPath(kdpwDocument));
            final Validator validator = (Validator) schema.newValidator();
            validator.setErrorHandler(errorHandler);
            validator.validate(new StreamSource(new StringReader(xmlMessage)));
            if (errorHandler.isNotValid() || kdpwDocument == null) {
                throw new XmlParseException("Error on read message!", errorHandler.getException());
            }

            return kdpwDocument;
        } catch (XmlParseException ex) {
            throw ex;
        } catch (IOException | JAXBException | SAXException ex) {
            throw new XmlParseException("Error on read message!", ex);
        }
    }

    protected static Schema loadSchema(final String path) throws XmlParseException {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            return factory.newSchema(loadFileAsResource(path));
        } catch (SAXException ex) {
            throw new XmlParseException("XmlBuilder.loadSchema error: ", ex);
        }

    }

    protected static boolean isNull(final Object value) {
        return value == null;
    }

    protected static String getValidationSchemaPath(final IKDPWDocument kdpwDocument) throws XmlParseException {
        if (isNull(kdpwDocument)) {
            throw new XmlParseException("KDPWDocument is NULL !");
        }
        final String schemaFile = kdpwDocument.getClass().getPackage().getName();
        return getValidationSchemaProperty().getProperty(schemaFile);
    }

    protected static Properties getValidationSchemaProperty() throws XmlParseException {
        final Properties properties = new Properties();
        try (InputStream stream = loadPropertyFile();) {
            properties.load(stream);
        } catch (IOException ex) {
            throw new XmlParseException("Can't find schema property file!", ex);
        }
        return properties;
    }

    protected static InputStream loadPropertyFile() {
        return XmlBuilder.class.getClassLoader().getResourceAsStream(VALID_SCHEMA_FILE);
    }

    protected static URL loadFileAsResource(final String path) {
        return XmlBuilder.class.getClassLoader().getResource(path);
    }
}
