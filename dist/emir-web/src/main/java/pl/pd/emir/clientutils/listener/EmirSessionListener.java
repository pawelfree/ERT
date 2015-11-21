package pl.pd.emir.clientutils.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class EmirSessionListener implements HttpSessionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmirSessionListener.class);
    
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        LOGGER.info("------------------- SESSION Created------------------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        LOGGER.info("------------------- SESSION DESTROYED------------------");
    }
}
