package pl.pd.emir.bean;

import java.util.List;
import java.util.Map;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.criteria.PagedList;
import pl.pd.emir.dao.utils.FilterSortTO;
import pl.pd.emir.manager.GenericManager;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.LoggerFactory;

public abstract class AbstractLazyDataModel<T extends Selectable<Long>, S extends AbstractSearchCriteria, M extends GenericManager<T>> extends LazyDataModel<T> {

    private static final long serialVersionUID = -27891531159872027L;

    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final Class<S> criteriaClazz;

    protected S criteria;

    private String sortField;

    private SortOrder sortOrder = SortOrder.UNSORTED;

    protected int recordCounter = 0;

    public AbstractLazyDataModel(Class<S> criteriaClazz) {
        super();
        this.criteriaClazz = criteriaClazz;
    }

    protected void createNewSearchCriteria() {
        try {
            criteria = criteriaClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.error(String.format("Can't create instance of %s: ", criteriaClazz.getSimpleName()), ex);
        }
    }

    @Override
    public T getRowData(String rowKey) {
        return getService().getById(Long.valueOf(rowKey));
    }

    @Override
    public Object getRowKey(T entity) {
        return entity.getId();
    }

    @Override
    public List<T> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, Object> filters) {
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        criteria.getFitrSort().setSortField(getSortField());

        criteria.addFilters();

        switch (getSortOrder()) {
            case ASCENDING:
                criteria.getFitrSort().setSortOrder(FilterSortTO.SortOrder.ASCENDING);
                break;
            case DESCENDING:
                criteria.getFitrSort().setSortOrder(FilterSortTO.SortOrder.DESCENDING);
                break;
            default:
                criteria.getFitrSort().setSortOrder(FilterSortTO.SortOrder.UNSORTED);
                break;
        }

        PagedList<T> result = getService().find(criteria);
        LOGGER.info("Row count: {}", result.getRecordCounter());
        recordCounter = result.getRecordCounter();
        setRowCount(recordCounter);
        return result.getData();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    public abstract M getService();

    public S getCriteria() {
        return criteria;
    }

    public void setCriteria(S criteria) {
        this.criteria = criteria;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String newSortField) {
        if (newSortField != null && !newSortField.equals(this.sortField)) {
            sortOrder = SortOrder.UNSORTED;
        }
        if (null != sortOrder) {
            switch (sortOrder) {
                case UNSORTED:
                    sortOrder = SortOrder.ASCENDING;
                    break;
                case ASCENDING:
                    sortOrder = SortOrder.DESCENDING;
                    break;
                case DESCENDING:
                    sortOrder = SortOrder.UNSORTED;
                    break;
                default:
                    sortOrder = SortOrder.UNSORTED;
            }
        }
        this.sortField = newSortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public final int getRecordCounter() {
        return recordCounter;
    }
}
