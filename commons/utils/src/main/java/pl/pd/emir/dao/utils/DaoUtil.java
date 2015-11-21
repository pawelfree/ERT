package pl.pd.emir.dao.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.manager.GenericManager;

public class DaoUtil<T> {

    public static final String FIELD_DELIMITER = "\\.";

    public CriteriaQuery createWhere(FilterSortTO filters, CriteriaBuilder criteriaBuilder, Class daoClass, List<Predicate> additional) {
        CriteriaQuery<Class<T>> criteriaQuery = (CriteriaQuery<Class<T>>) criteriaBuilder.createQuery(daoClass);
        Path<T> from = (Path<T>) criteriaQuery.from(daoClass);
        criteriaQuery.where(getPredicateListExt(filters.getFilters(), criteriaBuilder, daoClass, from, additional));
        if (filters.getPrimaryKey() != null) {
            from = from.get(filters.getPrimaryKey());
        }
        if (StringUtil.isNotEmpty(filters.getSortField())) {
            final Path<Object> path = getPath(from, filters.getSortField());
            if (filters.getSortOrder() == FilterSortTO.SortOrder.ASCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.asc(path));
            } else if (filters.getSortOrder() == FilterSortTO.SortOrder.DESCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.desc(path));
            }
        }

        return criteriaQuery;
    }

    public CriteriaQuery createCount(FilterSortTO filters, CriteriaBuilder criteriaBuilder, Class daoClass, List<Predicate> additional) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root from = criteriaQuery.from(daoClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        criteriaQuery.where(getPredicateListExt(filters.getFilters(), criteriaBuilder, daoClass, from, additional));
        return criteriaQuery;
    }

    public Predicate[] getPredicateListExt(List<AbstractFilterTO> filters, CriteriaBuilder builder, Class daoClass,
            Path<T> fromRoot, List<Predicate> additional) {
        List<Predicate> predicateList = new ArrayList<>();
        Path<T> from;

        if (filters != null) {
            for (AbstractFilterTO filter : filters) {
                from = fromRoot;
                if (filter == null) {
                    continue;
                }
                if (filter.getPrimaryKey() != null) {
                    from = builder.createQuery(daoClass).from(daoClass).get(filter.getPrimaryKey());
                }

                if ((filter.getField() != null) && (filter.getValue() != null)) {
                    if (filter instanceof FilterGroupOrTO) {
                        FilterGroupOrTO instance = (FilterGroupOrTO) filter;
                        List<String> values = new ArrayList<>();
                        instance.getGroupFilters().stream().forEach((item) -> {
                            values.add((String) item.getValue());
                        });
                        predicateList.add(setFrom(from, filter.getField()).in(values.toArray(new String[0])));
                    }
                    if (filter instanceof FilterObjectTO) {
                        FilterObjectTO objFilter = (FilterObjectTO) filter;
                        Object val = objFilter.getValue();
                        switch (filter.getComparator()) {
                            case "=":
                                predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                break;
                            case "!=":
                                Predicate notEqual = builder.notEqual(setFrom(from, filter.getField()), val);
                                Predicate aNull = builder.isNull(setFrom(from, filter.getField()));
                                Predicate or = builder.or(notEqual, aNull);
                                predicateList.add(or);
                                break;
                        }
                    }
                    if (filter instanceof FilterIntegerTO) {
                        FilterIntegerTO intFilter = (FilterIntegerTO) filter;
                        Integer val = intFilter.getIntValue();

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(setFrom(from, filter.getField()).as(Integer.class), val));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Integer.class), val));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(setFrom(from, filter.getField()).as(Integer.class), val));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Integer.class), val));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }

                    }
                    if (filter instanceof FilterBigDecimalTO) {
                        FilterBigDecimalTO intFilter = (FilterBigDecimalTO) filter;
                        BigDecimal val = intFilter.getBigDecimalValue();

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(setFrom(from, filter.getField()).as(BigDecimal.class), val));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(BigDecimal.class), val));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(setFrom(from, filter.getField()).as(BigDecimal.class), val));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(BigDecimal.class), val));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }
                    }

                    if (filter instanceof FilterLongTO) {
                        FilterLongTO intFilter = (FilterLongTO) filter;
                        Long val = intFilter.getLongValue();

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(setFrom(from, filter.getField()).as(Long.class), val));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Long.class), val));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(setFrom(from, filter.getField()).as(Long.class), val));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Long.class), val));
                                    break;
                                case "!=":
                                    predicateList.add(builder.notEqual(setFrom(from, filter.getField()).as(Long.class), val));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }
                    }

                    if (filter instanceof FilterDateTO && filter.getValue() != null) {
                        Date val = (Date) filter.getValue();

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(setFrom(from, filter.getField()).as(Date.class), val));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Date.class), val));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(setFrom(from, filter.getField()).as(Date.class), val));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Date.class), val));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }
                    }

                    if (filter instanceof FilterStringTO) {
                        String val = (String) filter.getValue();
                        if (val.trim().isEmpty()) {
                            continue;
                        }

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(setFrom(from, filter.getField()), val));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(setFrom(from, filter.getField()).as(String.class), val));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(String.class), val));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(setFrom(from, filter.getField()).as(String.class), val));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(String.class), val));
                                    break;
                                case "%.":
                                    predicateList.add(builder.like(setFrom(from, filter.getField()).as(String.class), "%" + val));
                                    break;
                                case "%.%":
                                    predicateList.add(builder.like(setFrom(from, filter.getField()).as(String.class), "%" + val + "%"));
                                    break;
                                case ".%":
                                    predicateList.add(builder.like(setFrom(from, filter.getField()).as(String.class), val + "%"));
                                    break;
                                case "like":
                                    predicateList.add(builder.like(setFrom(from, filter.getField()).as(String.class), val));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }
                    }

                    if (filter instanceof FilterDateMonthTO) {
                        Date val = (Date) filter.getValue();
                        Calendar cal = new GregorianCalendar();
                        cal.setTime(val);
                        int yearMonth = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;

                        if (null != filter.getComparator()) {
                            switch (filter.getComparator()) {
                                case "=":
                                    predicateList.add(builder.equal(builder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), builder.literal("YYYYMM")), yearMonth));
                                    break;
                                case ">":
                                    predicateList.add(builder.greaterThan(builder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), builder.literal("YYYYMM")), yearMonth));
                                    break;
                                case ">=":
                                    predicateList.add(builder.greaterThanOrEqualTo(builder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), builder.literal("YYYYMM")), yearMonth));
                                    break;
                                case "<":
                                    predicateList.add(builder.lessThan(builder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), builder.literal("YYYYMM")), yearMonth));
                                    break;
                                case "<=":
                                    predicateList.add(builder.lessThanOrEqualTo(builder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), builder.literal("YYYYMM")), yearMonth));
                                    break;
                                default:
                                    throw new IllegalArgumentException("operator " + filter.getComparator() + " is not supported in this filter");
                            }
                        }
                    }

                    if (filter instanceof FilterLongListTO) {
                        List val = (List) filter.getValue();
                        if (val.isEmpty()) {
                            continue;
                        }
                        if (filter.getComparator().equals("IN")) {
                            predicateList.add(setFrom(from, filter.getField()).in(val.toArray(new Long[0])));
                        }
                    }
                    if (filter instanceof FilterStringListTO) {
                        List val = (List) filter.getValue();
                        if (val.isEmpty()) {
                            continue;
                        }
                        if (filter.getComparator().equals("IN")) {
                            predicateList.add(setFrom(from, filter.getField()).in(val.toArray()));
                        }
                    }

                    if (filter instanceof FilterEnumTO) {
                        List val = (List) filter.getValue();
                        if (val.isEmpty()) {
                            continue;
                        }
                        if (filter.getComparator().equals("IN")) {
                            predicateList.add(setFrom(from, filter.getField()).in(val.toArray(new Enum[val.size()])));
                        }
                    }
                }
                if (filter instanceof FilterNullTO) {
                    switch (filter.getComparator()) {
                        case "IS":
                            predicateList.add(builder.isNull(setFrom(from, filter.getField())));
                            break;
                        case "IS NOT":
                            predicateList.add(builder.isNotNull(setFrom(from, filter.getField())));
                            break;
                    }
                }
            }
        }

        if (CollectionsUtils.isNotEmpty(additional)) {
            predicateList.addAll(additional);
        }

        final Predicate[] predicates = predicateList.toArray(new Predicate[0]);
        return predicates;
    }

    public static String prepareParamDoLike(Object parametr) {
        return "%" + parametr + "%";
    }

    private Path setFrom(Path from, final List<String> path) {
        for (String string : path) {
            CriteriaUtils criteriaUtils = new CriteriaUtils();
            return getPath(from, string);
        }
        return from;
    }

    public static boolean isExistsItemYesterday(GenericManager service, final Long itemId) {
        return isExistsItemYesterday(service, itemId, null);
    }

    public static boolean isExistsItemYesterday(GenericManager service, final Long itemId, final List<AbstractFilterTO> filters) {
        List<AbstractFilterTO> filtersTemp;
        if (filters == null) {
            filtersTemp = new ArrayList<>();
        } else {
            filtersTemp = filters;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        filtersTemp.add(FilterDateTO.valueOf("", "transactionDate", "=", DateUtils.getPreviousWorkingDayWithFreeDays(new Date())));
        filtersTemp.add(FilterLongTO.valueOf("", "id", "!=", itemId));
        return isExistsItem(service, filtersTemp);
    }

    public static boolean isExistsItem(GenericManager service, final List<AbstractFilterTO> filters) {
        AbstractSearchCriteria criteriaTemp = new AbstractSearchCriteria() {
            @Override
            public void addFilters() {
                clearFilters();
                filters.stream().forEach((filtrTmp) -> {
                    getFitrSort().getFilters().add(filtrTmp);
                });
            }
        };
        criteriaTemp.addFilters();
        return service.find(criteriaTemp).getData().size() > 0;
    }

    protected final Path<Object> getPath(final Path<T> from, final String sortField) {
        final String[] fields = sortField.split(FIELD_DELIMITER);
        Path<Object> result = from.get(fields[0]);
        if (fields.length > 1) {
            for (int i = 1; i < fields.length; i++) {
                result = result.get(fields[i]);
            }
        }
        return result;
    }
}
