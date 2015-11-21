package pl.pd.emir.kdpw.xml.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlBuilderErrorHandler implements ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlBuilderErrorHandler.class);

    private boolean isValid = true;

    private Throwable exception = null;

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        LOGGER.info("\nWARRNING: \n", exception);
        this.exception = exception;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        LOGGER.info("\nERROR: \n", exception);
        this.exception = exception;
        this.isValid = false;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        LOGGER.info("\nFATAL ERROR: \n", exception);
        this.exception = exception;
        this.isValid = false;
    }

    public boolean isNotValid() {
        return !this.isValid;
    }

    public Throwable getException() {
        return exception;
    }

}
