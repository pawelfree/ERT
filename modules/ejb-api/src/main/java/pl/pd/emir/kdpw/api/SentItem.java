package pl.pd.emir.kdpw.api;

import pl.pd.emir.kdpw.api.enums.ItemType;

public class SentItem extends ResultItem {

    public SentItem(String orignalId) {
        super(orignalId);
    }

    @Override
    public ItemType getType() {
        return ItemType.SENT;
    }

}
