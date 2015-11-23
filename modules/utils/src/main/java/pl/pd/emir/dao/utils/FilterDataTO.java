package pl.pd.emir.dao.utils;

import java.util.Date;

public class FilterDataTO extends AbstractFilterTO {

    private Object value;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value) {
        return valueOf(viewId, field, comparator, value, null);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value, String primaryKey) {
        FilterDataTO newItem = new FilterDataTO();
        newItem.setPrimaryKey(primaryKey);
        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);
        return newItem;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String result = "(FilterDateTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
