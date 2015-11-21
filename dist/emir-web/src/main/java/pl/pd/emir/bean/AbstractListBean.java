package pl.pd.emir.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.manager.GenericManager;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.SortOrder;

public abstract class AbstractListBean<T extends Selectable<Long>, M extends GenericManager<T>, S extends AbstractSearchCriteria> extends AbstractLazyDataModel<T, S, M> implements Serializable {

    protected boolean selectAll;

    protected boolean selectAllCheckBoxValue;

    protected T selected;

    protected Long selectedId;

    private transient final List<Long> selectedIds = new ArrayList<>();

    private transient final List<Long> deselectedIds = new ArrayList<>();

    private DataTable table;

    public AbstractListBean(Class<S> criteriaClazz) {
        super(criteriaClazz);
    }

    public abstract void initSearchCriteria();

    public abstract void initSearchCriteriaAfterClean();

    public abstract String getAction();

    public void cleanSearchCriteria() {
        createNewSearchCriteria();
        initSearchCriteriaAfterClean();
    }

    public String init() {
        return init(getAction());
    }

    public String init(String action) {
        createNewSearchCriteria();
        initSearchCriteria();
        return action;
    }

    @Override
    public List<T> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, Object> filters) {
        final List<T> results = super.load(first, pageSize, sortField, sortOrder, filters);
        markSelected(results);
        return results;
    }

    protected void markSelected(List<T> list) {
        LOGGER.debug("load.markSelected: {}", list.size());
        LOGGER.debug("selectAll = {}", selectAll);
        LOGGER.debug("selected: {}", CollectionsUtils.print(selectedIds));
        LOGGER.debug("deselected: {}", CollectionsUtils.print(deselectedIds));
        if (selectAll) {
            list.stream().forEach((data) -> {
                data.setSelected(!deselectedIds.contains(data.getId()));
                LOGGER.debug("Id = " + data.getId() + " selected: " + data.isSelected());
            });
        } else {
            list.stream().forEach((data) -> {
                data.setSelected(selectedIds.contains(data.getId()));
                LOGGER.debug("Id = " + data.getId() + " selected: " + data.isSelected());
            });
        }
        LOGGER.debug("selected: {}", CollectionsUtils.print(selectedIds));
        LOGGER.debug("deselected: {}", CollectionsUtils.print(deselectedIds));
    }

    public void selectAllListener() {
        selectAll = selectAllCheckBoxValue;
        selectedIds.clear();
        deselectedIds.clear();
        LOGGER.debug("+++ selectAllListener +++");
        LOGGER.debug("selectAll = selectAllCheckBoxValue = {}", selectAll);
    }

    public void selectRowListener(Long value) {
        LOGGER.debug("+++ selectRowListener +++");
        LOGGER.debug("Value: {}", value);
        if (selectAll) {
            LOGGER.debug("selectAll = true");
            if (deselectedIds.contains(value)) {
                LOGGER.debug("remove from deselected");
                deselectedIds.remove(value);
            } else {
                selectAllCheckBoxValue = false;
                LOGGER.debug("add to deselected");
                deselectedIds.add(value);
            }
        } else {
            LOGGER.debug("selectAll = false");
            if (selectedIds.contains(value)) {
                LOGGER.debug("remove from selectedIds");
                selectedIds.remove(value);
            } else {
                LOGGER.debug("add to selectedIds");
                selectedIds.add(value);
            }
        }
        LOGGER.debug("selectAll = {}", selectAll);
        LOGGER.debug("selectAllCheckBoxValue = {}", selectAllCheckBoxValue);
        LOGGER.debug("SELECTED: {}", StringUtil.printCollection(selectedIds));
        LOGGER.debug("DESELECTED: {}", StringUtil.printCollection(deselectedIds));
    }

    public List<T> findAllSelected() {
        LOGGER.debug("--- findAllSelected ---");
        final List<T> resultList = new ArrayList<>();
        if (selectAll) {
            LOGGER.debug("Deselected: {}", CollectionsUtils.print(deselectedIds));
            resultList.addAll(getService().findWithoutDeselected(criteria, deselectedIds));
        } else {
            LOGGER.debug("Selected: {}", CollectionsUtils.print(selectedIds));
            resultList.addAll(getService().findByIds(criteria, selectedIds));
        }
        LOGGER.debug("+++ getAllSelected: {}", resultList.size());
        return resultList;
    }

    public final boolean anySelected() {
        //return anySelectedItems() || (isSelectAll() && getRecordCounter() != deselectedIds.size());
        return getSelectedCount() > 0;
    }

    public final boolean noResults() {
        return getRecordCounter() == 0;
    }

    public void setFirstPage() {
        selectAllCheckBoxValue = false;
        selectAll = false;
        selectedIds.clear();
        deselectedIds.clear();
        if (Objects.nonNull(table)) {
            table.setFirst(0);
        }
    }

    protected boolean anySelectedItems() {
        return !selectedIds.isEmpty();
    }

    protected boolean anyDeselectedItems() {
        return !deselectedIds.isEmpty();
    }

    protected final void resetSelected() {
        selectAllCheckBoxValue = false;
        selectAll = false;
        selectedIds.clear();
        deselectedIds.clear();
    }

    public DataTable getTable() {
        return table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean value) {
        this.selectAll = value;
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T value) {
        this.selected = value;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long value) {
        this.selectedId = value;
    }

    public void setSelectAllCheckBoxValue(boolean value) {
        this.selectAllCheckBoxValue = value;
    }

    public boolean isSelectAllCheckBoxValue() {
        return selectAllCheckBoxValue;
    }

    public final int getSelectedCount() {
        int result = 0;
        if (selectAll) {
            result += getRecordCounter() - deselectedIds.size();
            LOGGER.debug("getSelectedCount (selectAll) = {} ", result);
        } else {
            result += selectedIds.size();
            LOGGER.debug("getSelectedCount = {} ", result);
        }
        return result;
    }

    protected List<Long> getSelectedIds() {
        return Collections.unmodifiableList(selectedIds);
    }

    protected List<Long> getDeselectedIds() {
        return Collections.unmodifiableList(deselectedIds);
    }

}
