package pl.pd.emir.dao.utils;

public class FilterLongTO extends AbstractFilterTO {

    private Object value;

    public static FilterLongTO valueOf(String viewId, String field, String comparator, Long value) {
        FilterLongTO newItem = new FilterLongTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public Long getLongValue() {
        Object val = getValue();

        if (val instanceof String) {
            long nVal;

            try {
                nVal = Long.parseLong((String) val);
            } catch (NumberFormatException exc) {
                // ignore
                nVal = 0;
            }

            return nVal;
        } else if (val instanceof Long) {
            return (Long) val;
        }

        return null;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }

        if ((value instanceof String) || (value instanceof Long)) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        String result = "(FilterIntegerTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
