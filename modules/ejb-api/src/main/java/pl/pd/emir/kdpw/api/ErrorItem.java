package pl.pd.emir.kdpw.api;

import pl.pd.emir.kdpw.api.enums.ItemType;
import pl.pd.emir.kdpw.api.enums.SendingError;

public class ErrorItem extends ResultItem {

    private final SendingError errorReason;

    private final String errorDetails;

    public ErrorItem(String orignalId, SendingError errorReason, String errorDetails) {
        super(orignalId);
        this.errorReason = errorReason;
        this.errorDetails = errorDetails;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public SendingError getErrorReason() {
        return errorReason;
    }

    @Override
    public ItemType getType() {
        return ItemType.UNPROCESSED;
    }

}
