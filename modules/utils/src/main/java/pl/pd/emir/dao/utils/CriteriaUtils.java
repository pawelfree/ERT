package pl.pd.emir.dao.utils;

import java.lang.reflect.Field;
import java.text.Annotation;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.LoggerFactory;

public class CriteriaUtils {

    public static final String DELIMITER = "\\.";

    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected Path<?> addRequiredJoins(String stringPath, Root root, boolean distinct) {

        String[] pathValues = stringPath.split(DELIMITER);
        int propertyIndex = pathValues.length - 1;
        Join lastJoinRoot = null;
        Path lastPathRoot = null;
        LOGGER.info("PATH: {}", stringPath);

        if (propertyIndex > 0) {
            try {
                for (int i = 0; i < propertyIndex; i++) {
                    LOGGER.info("processing {} path element: {}", i, pathValues[i]);
                    if (i == 0) {
                        Class rootClazz = root.getModel().getJavaType();
                        Class fieldClazz = getClassByFieldName(pathValues[0], rootClazz); //rootClazz.getDeclaredField(pathValues[0]).getType(); //
                        LOGGER.info("root class: {}, field class: {}", rootClazz, fieldClazz);
                        Annotation annotationEmbedded = (Annotation) getFieldByName(rootClazz, pathValues[0]).getAnnotation(Embedded.class);

                        Annotation annotationManyToMany = (Annotation) getFieldByName(rootClazz, pathValues[0]).getAnnotation(ManyToMany.class);
                        Annotation annotationOneToMany = (Annotation) getFieldByName(rootClazz, pathValues[0]).getAnnotation(OneToMany.class);
                        LOGGER.info("annotationEmbedded: {}, annotationManyToMany: {}, annotationOneToMany: {}", new Object[]{annotationEmbedded, annotationManyToMany, annotationOneToMany});

                        if (annotationEmbedded != null) {
                            lastPathRoot = root.get(pathValues[i]);
                            LOGGER.info("it is an embeded field setting lastPathRoot to: {}", lastPathRoot);
                        } else {
                            LOGGER.info("it is not an embeded field");
                            if (!isAlreadyJoined(root, fieldClazz, rootClazz)) {
                                lastJoinRoot = root.join(pathValues[0], JoinType.LEFT);
                                root.getJoins().add(lastJoinRoot);
                                LOGGER.info("didn't had that join adding an setting lastJoinRoot to: {}", lastJoinRoot);
                            } else {
                                lastJoinRoot = getRootJoin(root, fieldClazz, rootClazz);
                                LOGGER.info("already had that join adding an setting lastJoinRoot to: {}", lastJoinRoot);
                            }
                        }
                    } else {
                        Class parentClazz = lastJoinRoot.getJavaType();
                        Class attributeClazz = getClassByFieldName(pathValues[i], parentClazz);
                        Annotation annotationEmbedded = (Annotation) getFieldByName(parentClazz, pathValues[i]).getAnnotation(Embedded.class);

                        if (annotationEmbedded != null) {
                            lastPathRoot = lastJoinRoot.get(pathValues[i]);
                        } else if (!isAlreadyJoined(root, attributeClazz, parentClazz)) {
                            lastJoinRoot = lastJoinRoot.join(pathValues[i], JoinType.LEFT);
                            root.getJoins().add(lastJoinRoot);
                        } else {
                            lastJoinRoot = getRootJoin(root, attributeClazz, parentClazz);
                        }
                    }
                }

                Path<Object> path = (lastPathRoot == null ? lastJoinRoot.get(pathValues[propertyIndex]) : lastPathRoot.get(pathValues[propertyIndex]));
                LOGGER.debug("Setting path to: {}", path);
                return path;
            } catch (NoSuchFieldException | SecurityException ex) {
                LOGGER.error(" addRequiredJoins error", ex);
            }
        }
        return null;
    }

    protected static String extractFieldName(String stringPath) {
        String[] pathValues = stringPath.split("\\.");
        return pathValues[pathValues.length - 1];
    }

    private boolean isAlreadyJoined(Root root, Class attribute, Class parent) {
        return ((Set<Join>) root.getJoins())
                .stream()
                .anyMatch((j) -> (j.getAttribute().getJavaType().equals(attribute) && j.getParent().getJavaType().equals(parent)));

    }

    private Join getRootJoin(Root root, Class attribute, Class parent) {
        for (Join j : (Set<Join>) root.getJoins()) {
            if (j.getAttribute().getJavaType().equals(attribute)
                    && j.getParent().getJavaType().equals(parent)) {
                return j;
            }
        }
        return null;
    }

    @Deprecated
    private Class getClassByFieldName(String fieldName, Class clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals(fieldName)) {
                return f.getType(); //getClass();
            }
        }
        return null;
    }

    private Field getFieldByName(Class rootClazz, String fieldName) throws NoSuchFieldException, SecurityException {
        try {
            return rootClazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            if (!rootClazz.getSuperclass().equals(Object.class)) {
                return getFieldByName(rootClazz.getSuperclass(), fieldName);
            } else {
                LOGGER.info("getFieldByName missing field: " + fieldName);
                throw ex;
            }
        } catch (SecurityException ex) {
            LOGGER.info("getFieldByName security error: " + fieldName);
            throw ex;
        }
    }

    public static void addWhereClause(Predicate predicate, CriteriaQuery criteriaQuery) {
        if (predicate != null) {
            Predicate current = criteriaQuery.getRestriction();
            if (current == null) {
                current = predicate; // workaround toplinkowego problemu z opakowywaniem wyniku getRestriction w isTrue
            }
            criteriaQuery.where(current, predicate);
        }
    }
}
