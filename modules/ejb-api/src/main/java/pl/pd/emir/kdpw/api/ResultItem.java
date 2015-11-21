package pl.pd.emir.kdpw.api;

import pl.pd.emir.kdpw.api.enums.ItemType;

public abstract class ResultItem {

    protected final String orignalId;

    public ResultItem(String orignalId) {
        this.orignalId = orignalId;
    }

    public String getOrignalId() {
        return orignalId;
    }

    public abstract ItemType getType();
}
