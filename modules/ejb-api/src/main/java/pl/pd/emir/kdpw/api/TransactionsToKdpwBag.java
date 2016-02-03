package pl.pd.emir.kdpw.api;

import java.util.List;

/**
 *
 * @author PawelDudek
 */
public class TransactionsToKdpwBag {

    private List<ResultItem> items;
    private long newCounter;
    private long valCounter;
    private long modCounter;

    public TransactionsToKdpwBag(List<ResultItem> list) {
        items = list;
    }

    public List<ResultItem> getItems() {
        return items;
    }

    public void setItems(List<ResultItem> items) {
        this.items = items;
    }

    public long getNewCounter() {
        return newCounter;
    }

    public void setNewCounter(long newCounter) {
        this.newCounter = newCounter;
    }

    public long getValCounter() {
        return valCounter;
    }

    public void setValCounter(long valCounter) {
        this.valCounter = valCounter;
    }

    public long getModCounter() {
        return modCounter;
    }

    public void setModCounter(long modCounter) {
        this.modCounter = modCounter;
    }
    
    public String getInfo() {
        return "N:".concat(String.valueOf(newCounter))
                .concat(":V:").concat(String.valueOf(valCounter))
                .concat(":M:").concat(String.valueOf(modCounter))
                .concat(":T:").concat(String.valueOf(newCounter + modCounter + valCounter));
    }
}
