package pl.pd.emir.register.bean;

import java.io.Serializable;
import javax.persistence.Column;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.enums.FormType;

public abstract class AbstractFormBean<T extends Identifiable> implements Serializable {

    private static final long serialVersionUID = 42L;
    protected T entity;
    private transient final Class entityClass;

    public AbstractFormBean(Class entityClass) {
        this.entityClass = entityClass;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
    protected FormType formType;

    /**
     * @param formType the formType to set
     */
    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public void changeContext(FormType newTyp) {
        setFormType(newTyp);
    }

    /**
     * @return the formType
     */
    public FormType getFormType() {
        return formType;
    }

    protected abstract String getAction();

    protected abstract void initEntity(Long id);

    protected abstract void initEntityFields();

    public abstract boolean isEditable();

    protected void initEntity() {
        initEntity(null);
    }

    public String init(FormType type, long itemID) {
        initEntity(itemID);
        return init(type);
    }

    public String init(FormType type) {
        formType = type;
        if (entity == null) {
            initEntity();
            if (type == FormType.New) {
                initEntityFields();
            }
        } else {
            if (type == FormType.New) {
                initEntity();
                initEntityFields();
            }
        }

        if (entity == null) {
            throw new IllegalArgumentException("entity is not initialized");
        }
        return getAction();
    }

    public String init() {
        initEntity();
        return init(FormType.New);
    }

    protected int getSizeValue(Class entityType, String fieldName) {
        int val = Integer.MAX_VALUE;
        try {
            Column annotation = entityType.getDeclaredField(fieldName).getAnnotation(Column.class);
            if (annotation != null) {
                val = annotation.length();
            }
        } catch (NoSuchFieldException e) {
            val = Integer.MAX_VALUE;
        }
        return val;
    }

    protected int getSizeValue(String fieldName) {
        return getSizeValue(entityClass, fieldName);
    }
}
