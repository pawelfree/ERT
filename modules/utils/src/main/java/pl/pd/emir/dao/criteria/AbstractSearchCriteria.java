package pl.pd.emir.dao.criteria;

import java.io.Serializable;
import pl.pd.emir.dao.utils.FilterSortTO;

public abstract class AbstractSearchCriteria implements Serializable {

    protected FilterSortTO fitrSort = new FilterSortTO();

    protected int firstResult;

    protected int maxResults;

    private boolean distinct;

    private String hintName;

    private String hintValue;

    public abstract void addFilters();

    public int getFirstResult() {
        return firstResult;
    }

    public AbstractSearchCriteria() {
        super();
        distinct = false;
//        setFirstResult(0);
//        setFitrSort(null);
//        setMaxResults(-1);
    }

    public final void setFirstResult(final int value) {
        this.firstResult = value;
    }

    public final int getMaxResults() {
        return maxResults;
    }

    public final void setMaxResults(final int value) {
        this.maxResults = value;
    }

    public FilterSortTO getFitrSort() {
        return fitrSort;
    }

    public void setFitrSort(FilterSortTO fitrSort) {
        this.fitrSort = fitrSort;
    }

    public void clear() {
        setFirstResult(0);
        setFitrSort(new FilterSortTO());
        setMaxResults(-1);
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    protected void clearFilters() {
        this.fitrSort.clearFilters();
    }

    public String getHintName() {
        return hintName;
    }

    public void setHintName(String hintName) {
        this.hintName = hintName;
    }

    public String getHintValue() {
        return hintValue;
    }

    public void setHintValue(String hintValue) {
        this.hintValue = hintValue;
    }
}
