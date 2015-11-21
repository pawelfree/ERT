package pl.pd.emir.dao.utils;

import java.math.BigDecimal;

public class FilterBigDecimalTO extends AbstractFilterTO {

    private Object value;

    public static FilterBigDecimalTO valueOf(String viewId, String field, String comparator, BigDecimal value) {
        FilterBigDecimalTO newItem = new FilterBigDecimalTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public BigDecimal getBigDecimalValue() {
        Object val = getValue();

        if (val instanceof String) {
            try {
                return new BigDecimal((String) val);
            } catch (NumberFormatException exc) {
                // ignore
                return BigDecimal.ZERO;
            }
        } else if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }

        return null;
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
        String result = "(FilterBigDecimalTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
