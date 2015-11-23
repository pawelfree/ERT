package pl.pd.emir.register;

import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author PawelDudek
 */
public class LoggerInjectionSupport {

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }
}
