package pl.pd.emir.mapper;

import java.lang.reflect.Field;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelMapperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelMapperUtil.class);

    /**
     * Currently filter String and Boolean. Boolean will check if fieldName Class start with "is"
     *
     * @param obj
     * @param filters
     * @return
     */
    public static Object mappingDataObject(Object obj, Map<String, String> filters) {
        if (obj == null) {
            obj = new Object();
        }
        try {
            for (Map.Entry<String, String> key : filters.entrySet()) {
                String value = key.getValue();
                try {
                    Field f = obj.getClass().getDeclaredField(key.getKey());
                    if (f.getName().equalsIgnoreCase(key.getKey())) {
                        f.setAccessible(true);
                        try {
                            if (key.getKey().startsWith("is")) {
                                if (value.equalsIgnoreCase("Yes")) {
                                    f.set(obj, Boolean.TRUE);
                                } else if (value.equalsIgnoreCase("No")) {
                                    f.set(obj, Boolean.FALSE);
                                }
                            } else {
                                f.set(obj, value);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                } catch (NoSuchFieldException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        } catch (SecurityException e) {
            LOGGER.error(e.getMessage());
        }
        return obj;
    }
}
