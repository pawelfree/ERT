package pl.pd.emir.dao.criteria;

import java.util.List;

public class PagedList<T> {

    /**
     * Lista obieków TO zwrócona przez DAO. Typ obiektu w liscie powinien być zadeklarowany przy użyciu java generics.
     */
    private java.util.List<T> data;
    /**
     * Ilość rekordów z wynikami dla podanych kryteriów wyszukiwania. Jeśli w Kryteriach ustawiona zostałą parametr
     * isCount na false to ilość stron powinna zostać wstawiona taka sama jak w requeście bez zliczania wyników.
     */
    private int recordCounter;
    /**
     * Numer strony z wynikami dla której poszło zapytanie do bazy danych.
     */
    private int pageNumber;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRecordCounter() {
        return recordCounter;
    }

    public void setRecordCounter(int recordCounter) {
        this.recordCounter = recordCounter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
