package pl.pd.emir.admin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import pl.pd.emir.entity.administration.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
//TODO zbedny
public class ExtendedLoggerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedLoggerFacade.class);
    public static final String BASE_JNDI_LOGGER_PATH = "ejblocal:ejb/logger";
    private List<IExtendedLogger> extendedLoggers;

    @PostConstruct
    public void init() {
        extendedLoggers = new ArrayList<>();
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            NamingEnumeration<NameClassPair> loggerTree = ctx.list(BASE_JNDI_LOGGER_PATH);
            if (loggerTree != null) {
                while (loggerTree.hasMore()) {
                    NameClassPair next = loggerTree.next();
                    IExtendedLogger logger = (IExtendedLogger) ctx.lookup("ejblocal:ejb/logger/" + next.getName());
                    extendedLoggers.add(logger);
                }
            }
        } catch (NamingException ex) {
            LOGGER.warn("Blad wyszukiwania dodatkowych loggerow w drzewei JNDI - "
                    + "prawdopodobnie brak dodatkowych implementacji");
        }
    }
    
    public void addEvent(EventLog event) {
        if (extendedLoggers != null) {
            extendedLoggers.stream().forEach((logger) -> {
                logger.addEvent(event);
            });
        }
    }
}
