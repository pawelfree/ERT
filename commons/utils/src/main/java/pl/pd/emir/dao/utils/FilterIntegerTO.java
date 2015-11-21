package pl.pd.emir.dao.utils;

public class FilterIntegerTO extends AbstractFilterTO {

    private Object value;

    public static FilterIntegerTO valueOf(String viewId, String field, String comparator, Integer value) {
        FilterIntegerTO newItem = new FilterIntegerTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public Integer getIntValue() {
        Object val = getValue();

        if (val instanceof String) {
            int nVal;

            try {
                nVal = Integer.parseInt((String) val);
            } catch (NumberFormatException exc) {
                // ignore
                nVal = 0;
            }

            return nVal;
        } else if (val instanceof Integer) {
            return (Integer) val;
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

        if ((value instanceof String) || (value instanceof Integer)) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "(FilterIntegerTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";
    }
}
