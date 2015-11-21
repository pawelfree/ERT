package pl.pd.webresources;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author PawelDudek
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return getRestResourceClasses();
    }

    private Set<Class<?>> getRestResourceClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ClientWebResource.class);
        try {
            Class<?> jacksonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return resources;
    }

}
