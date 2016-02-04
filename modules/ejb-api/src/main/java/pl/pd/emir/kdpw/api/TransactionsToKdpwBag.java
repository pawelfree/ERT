package pl.pd.emir.kdpw.api;

import java.util.List;

/**
 *
 * @author PawelDudek
 */
public class TransactionsToKdpwBag {

    private List<ResultItem> items;
    private long newCounter = 0;
    private long valCounter = 0;
    private long valCCounter = 0;
    private long modCounter = 0;
    private long modCCounter = 0;

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

    public long getModCCounter() {
        return modCCounter;
    }

    public void setModCCounter(long modCCounter) {
        this.modCCounter = modCCounter;
    }
    
    public long getValCCounter() {
        return valCCounter;
    }

    public void setValCCounter(long valCCounter) {
        this.valCCounter = valCCounter;
    }
    
    public String getInfo() {
        return "N:".concat(String.valueOf(newCounter))
                .concat(":V:").concat(String.valueOf(valCounter))
                .concat(":VC:").concat(String.valueOf(valCCounter))
                .concat(":M:").concat(String.valueOf(modCounter))
                .concat(":MC:").concat(String.valueOf(modCCounter))
                .concat(":T:").concat(String.valueOf(newCounter + modCounter + modCCounter + valCounter + valCCounter));
    }
}
