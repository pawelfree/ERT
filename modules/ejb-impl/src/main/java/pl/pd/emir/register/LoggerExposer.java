package pl.pd.emir.register;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author PawelDudek
 */
@Dependent
public class LoggerExposer {

    @Produces
    public Logger expose(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}
