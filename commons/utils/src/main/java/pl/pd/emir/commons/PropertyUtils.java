package pl.pd.emir.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    public static Properties getProperties(Class clazz, String propertyFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = clazz.getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException ex) {
            LOGGER.error("Cannot load property file: {}", propertyFile);
            LOGGER.error("Details: " + ex);
        }
        return properties;
    }

    public static String getString(Properties properties, String key) {
        String property = properties.getProperty(key);
        if (null == property) {
            return String.format("??? %s ???", key);
        } else {
            return property;
        }

    }

    public static String getString(Properties properties, String key, Object... params) {
        if (properties.containsKey(key)) {
            String string = properties.getProperty(key);
            return String.format(string, params);
        } else {
            return String.format("??? %s ???", key);
        }
    }
}
