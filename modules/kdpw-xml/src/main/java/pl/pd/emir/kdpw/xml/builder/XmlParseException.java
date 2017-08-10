package pl.pd.emir.kdpw.xml.builder;

public class XmlParseException extends Exception {

    public XmlParseException() {
        super();
    }

    public XmlParseException(final String message) {
        super(message);
    }

    public XmlParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public XmlParseException(final Throwable cause) {
        super(cause);
    }
}
