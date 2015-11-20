package pl.pd.emir.client.utils;

import com.sun.faces.facelets.impl.DefaultResourceResolver;
import java.net.URL;

public class MultiFilesResourceResolver extends DefaultResourceResolver {

    @Override
    public String toString() {
        return "MultiFilesResourceResolver";
    }

    @Override
    public URL resolveUrl(String resource) {
        URL resourceUrl = super.resolveUrl(resource);
        if (resourceUrl == null) {
            if (resource.startsWith("/")) {
                resource = resource.substring(1);
            }
            resourceUrl = this.getClass().getClassLoader().getResource(resource); //Thread.currentThread().getContextClassLoader().getResource(resource);
            if (resourceUrl == null) {
                resourceUrl = this.getClass().getClassLoader().getResource("META-INF/resources/" + resource);
            }
        }
        return resourceUrl;
    }
}
