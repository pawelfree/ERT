package pl.pd.emir.dao.utils;

public class FilterNullTO extends AbstractFilterTO {

    public static FilterNullTO valueOf(String viewId, String field,
            String comparator) {
        FilterNullTO newItem = new FilterNullTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);

        return newItem;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
        //nie dotyczy
    }

    @Override
    public String toString() {
        String result = "(FilterNullTO) " + getField() + " ( "
                + getComparator() + ")";
        return result;
    }
}
