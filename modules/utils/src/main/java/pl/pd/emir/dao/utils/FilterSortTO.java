package pl.pd.emir.dao.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterSortTO implements Serializable {

    public static enum SortOrder {

        ASCENDING("ASC"), DESCENDING("DESC"), UNSORTED("");
        private final String valueSql;

        private SortOrder(String valueSql) {
            this.valueSql = valueSql;

        }

        public String getValueSql() {
            return valueSql;
        }
    }
    private String primaryKey;
    private List<AbstractFilterTO> filters;
    private String sortField;
    private SortOrder sortOrder = SortOrder.UNSORTED;

    public List<AbstractFilterTO> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        return filters;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void clearFilters() {
        if (filters != null) {
            filters.clear();
        }
    }
}
