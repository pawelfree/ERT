package pl.pd.emir.dao.utils;

public class FilterStringTO extends AbstractFilterTO {

    private Object value;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value) {
        return valueOf(viewId, field, comparator, value, null);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value, String primaryKey) {
        FilterStringTO newItem = new FilterStringTO();
        newItem.setPrimaryKey(primaryKey);
        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = "(FilterString) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
