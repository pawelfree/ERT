package pl.pd.emir.dao.utils;

public class FilterObjectTO extends AbstractFilterTO {

    private Object value;

    public static FilterObjectTO valueOf(String viewId, String field,
            String comparator, Object value) {
        FilterObjectTO newItem = new FilterObjectTO();

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
    Enum enum1;

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }
        this.value = value;

    }

    @Override
    public String toString() {
        String result = "(FilterObjectTO) " + getField() + " ( "
                + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
