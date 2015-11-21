package pl.pd.emir.dao.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilterTO implements Serializable {

    private String viewId;
    private List<String> field;
    private String comparator;
    private String primaryKey;

    public AbstractFilterTO() {
        field = new ArrayList<>();
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setField(String field) {
        this.field.add(field);
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    public abstract Object getValue();

    public abstract void setValue(Object value);

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    @Override
    public String toString() {
        return "FilterTO{"
                + "viewId='" + viewId + '\''
                + ", field='" + field + '\''
                + ", comparator='" + comparator + '\''
                + '}';
    }
}
