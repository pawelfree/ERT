package pl.pd.emir.kdpw.xml.builder;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlValidationEventHandler implements ValidationEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidationEventHandler.class);

    private static final String NEW_LINE = "\r\n";

    @Override
    public final boolean handleEvent(final ValidationEvent event) {
        final StringBuilder message = new StringBuilder();
        message.append(NEW_LINE).append("EVENT");
        message.append(NEW_LINE).append("SEVERITY:  ");
        message.append(event.getSeverity());
        message.append(NEW_LINE).append("MESSAGE:  ");
        message.append(event.getMessage());
        message.append(NEW_LINE).append("LINKED EXCEPTION:  ");
        message.append(event.getLinkedException());
        message.append(NEW_LINE).append("LOCATOR");
        message.append(NEW_LINE).append("    LINE NUMBER:  ");
        if (event.getLocator() != null) {
            message.append(event.getLocator().getLineNumber());
            message.append(NEW_LINE).append("    COLUMN NUMBER:  ");
            message.append(event.getLocator().getColumnNumber());
            message.append(NEW_LINE).append("    OFFSET:  ");
            message.append(event.getLocator().getOffset());
            message.append(NEW_LINE).append("    OBJECT:  ");
            message.append(event.getLocator().getObject());
            message.append(NEW_LINE).append("    NODE:  ");
            message.append(event.getLocator().getNode());
            message.append(NEW_LINE).append("    URL:  ");
            message.append(event.getLocator().getURL());
        }
        LOGGER.error(message.toString());
        return false;
    }
}
