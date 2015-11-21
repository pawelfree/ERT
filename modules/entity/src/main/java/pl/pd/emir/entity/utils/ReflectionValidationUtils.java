package pl.pd.emir.entity.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionValidationUtils.class);

    public static boolean isComplete(Object extract) {
        Validator validator = new Validator();
        boolean entityComplete = validator.isEntityComplete(extract, false, extract.getClass());
        boolean groupComplete = validator.isOrGroupComplete();
        return entityComplete && groupComplete;
    }

    public static boolean isComplete(Object extract, boolean valuationReporting) {
        Validator validator = new Validator();
        boolean entityComplete = validator.isEntityComplete(extract, valuationReporting, extract.getClass());
        boolean orGroupComplete = validator.isOrGroupComplete();
        boolean andGroupComplete = validator.isAndGroupComplete();
        return entityComplete && orGroupComplete;
    }

    private static class Validator {

        private final Map<String, List<Object>> orGroups = new HashMap<>();
        private final Map<String, List<Object>> andGroups = new HashMap<>();

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

                if (vcAnnotation != null && vcAnnotation.subjectClass().equals(extractClazz)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        boolean falseIfNull = vcAnnotation.checkValuationReporting() ? valuationReporting : true;
                        if (!vcAnnotation.orGroup().isEmpty()) {
                            addGroup(orGroups, vcAnnotation.orGroup(), value);
                            continue;
                        }
                        if (!vcAnnotation.andGroup().isEmpty()) {
                            addGroup(andGroups, vcAnnotation.orGroup(), value);
                            continue;
                        }
                        if (vcAnnotation.entry() && value != null) {
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

        public boolean isOrGroupComplete() {
            for (Map.Entry<String, List<Object>> entry : orGroups.entrySet()) {
                String key = entry.getKey();
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

        public boolean isAndGroupComplete() {
            for (Map.Entry<String, List<Object>> entry : andGroups.entrySet()) {
                List<Object> values = entry.getValue();
                boolean validateResult = true;
                for (Object value : values) {
                    LOGGER.info("isGroupAndComplete: " + value + " : " + Objects.nonNull(value));
                    validateResult = validateResult && Objects.nonNull(value);
                }
                if (validateResult) {
                    return false;
                }
            }
            return true;
        }
    }
}
