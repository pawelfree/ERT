package pl.pd.emir.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipleFilesResourceBundleControl extends ResourceBundle.Control implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleFilesResourceBundleControl.class);

    private final String bundleExtension;

    public MultipleFilesResourceBundleControl(String bundleExtension) {
        super();
        this.bundleExtension = bundleExtension;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, bundleExtension);

        ClassLoader localLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> it = localLoader.getResources(resourceName);

        ResourceBundle bundle = null;

        List<URL> list = Collections.list(it);

        Collections.sort(list, new ResourceBundleURLComparator(localLoader.getResource(resourceName)));

        byte[] newLine = System.getProperty("line.separator").getBytes("UTF-8");
        URL url;
        URLConnection connection;
        List<InputStream> inputStream = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            url = list.get(i);
            LOGGER.info("****************************** Get content from file : {}", url);
            connection = url.openConnection();
            if (connection != null) {
                connection.setUseCaches(false);
                if (i > 0) {
                    //dodanie nowej lini po kadym pliku
                    inputStream.add(new ByteArrayInputStream(newLine));
                }
                inputStream.add(connection.getInputStream());
            }
        }
        if (0 < inputStream.size()) {
            InputStream stream = null;
            try {
                stream = new SequenceInputStream(Collections.enumeration(inputStream));
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                if (null != stream) {
                    stream.close();
                }
            }
        }
        return bundle;
    }
}
