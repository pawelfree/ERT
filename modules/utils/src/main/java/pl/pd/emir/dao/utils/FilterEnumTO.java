package pl.pd.emir.dao.utils;

import java.util.List;

public class FilterEnumTO extends AbstractFilterTO {

    private Object value;

    public static FilterEnumTO valueOf(String viewId, String field, String comparator, List values) {
        FilterEnumTO enumTO = new FilterEnumTO();
        enumTO.setViewId(viewId);
        enumTO.setField(field);
        enumTO.setValue(values);
        enumTO.setComparator(comparator);
        return enumTO;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }

        if ((value instanceof List)) {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return value;
    }
}
