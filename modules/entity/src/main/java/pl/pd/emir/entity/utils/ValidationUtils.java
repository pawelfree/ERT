package pl.pd.emir.entity.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Embedded;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);

    /**
     * Sprawdzenie kompletności ekstraktu - tylko bank i.
     *
     * @param extract encja odzwierciedlająca ekstrakt
     * @param valuationReporting flaga określająca czy walidować kompletność zabezpieczenia i wyceny
     * @return true w sytuacji gdy ekstrakt jest kompletny, w przeciwnym przypadku false
     */
    public static boolean isComplete(Object extract, boolean valuationReporting) {
        Validator validator = new Validator();
        boolean entityComplete = validator.isEntityComplete(extract, valuationReporting, extract.getClass());
//        boolean groupComplete = validator.isGroupComplete();
        boolean groupComplete = true;
        return entityComplete && groupComplete;
    }

    /**
     * Klasa walidatora kompletności encji
     */
    private static class Validator {

        private final Map<String, List<Object>> groups = new HashMap<>();

        private boolean isEntityComplete(Object entity, boolean valuationReporting, Class extractClazz) {
            return isEntityComplete(entity.getClass(), entity, valuationReporting, extractClazz);
        }

        private boolean isEntityComplete(Class clazz, Object entity, boolean valuationReporting, Class extractClazz) {
            for (Field field : clazz.getDeclaredFields()) {
                ValidateCompleteness vcAnnotation = field.getAnnotation(ValidateCompleteness.class);
                if (vcAnnotation == null) {
                    Validators vAnnotation = field.getAnnotation(Validators.class);
                    if (vAnnotation != null) {
                        ValidateCompleteness[] validators = vAnnotation.value();
                        for (ValidateCompleteness validator : validators) {
                            if (validator.subjectClass().equals(extractClazz)) {
                                vcAnnotation = validator;
                                break;
                            }
                        }
                    }
                }
                Embedded eAnnotation = field.getAnnotation(Embedded.class);
                if (vcAnnotation != null && vcAnnotation.subjectClass().equals(extractClazz)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        boolean falseIfNull = vcAnnotation.checkValuationReporting() ? valuationReporting : true;
                        if (!vcAnnotation.orGroup().isEmpty()) {
                            addGroup(groups, vcAnnotation.orGroup(), value);
                            continue;
                        }
                        if (eAnnotation != null) {
                            boolean complete = isEntityComplete(value, valuationReporting, extractClazz);
                            if (!complete) {
                                return false;
                            }
                        }
                        boolean validateResult = isFiledComplete(value, falseIfNull);
                        if (!validateResult) {
                            return false;
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LOGGER.error("Can't validate completeness of field: ", ex);
                    }
                }
            }
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
                boolean complete = isEntityComplete(clazz.getSuperclass(), entity, valuationReporting, extractClazz);
                if (!complete) {
                    return false;
                }
            }
            return true;
        }

        private void addGroup(Map<String, List<Object>> groups, String key, Object value) {
            List<Object> values = groups.get(key);
            if (values == null) {
                values = new ArrayList<>();
                groups.put(key, values);
            }
            values.add(value);
        }

        private boolean isFiledComplete(Object value, boolean falseIfNull) {
            if (value == null) {
                if (falseIfNull) {
                    return false;
                }
            } else if (value.getClass().equals(String.class)) {
                if (((String) value).isEmpty()) {
                    return false;
                }
            } else if (value instanceof Enum) {
                if (((Enum) value).name().toLowerCase().equals("err")) {
                    return false;
                }
            }
            return true;
        }

        public boolean isGroupComplete() {
            for (Map.Entry<String, List<Object>> entry : groups.entrySet()) {
                List<Object> values = entry.getValue();
                boolean groupValidateResult = false;
                for (Object value : values) {
                    boolean validateResult = isFiledComplete(value, true);
                    if (validateResult) {
                        groupValidateResult = true;
                    }
                }
                if (!groupValidateResult) {
                    return false;
                }
            }
            return true;
        }
    }
}
