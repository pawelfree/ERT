package pl.pd.emir.modules.kdpw.adapter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositoryResponse {

    public enum ResponseType {

        INVALID,
        TRANSACTION,
        DATASET,
        RECONCILIATION,

    }

    private final ResponseType responseType;

    public RepositoryResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    private final List<ResponseItem> list = new ArrayList<>();

    public void addToList(ResponseItem response) {
        list.add(response);
    }

    public List<ResponseItem> getList() {
        return Collections.unmodifiableList(list);
    }

    public boolean isTransaction() {
        return ResponseType.TRANSACTION.equals(responseType);
    }

    public boolean isDataSet() {
        return ResponseType.DATASET.equals(responseType);
    }

    public boolean isReconciliation() {
        return ResponseType.RECONCILIATION.equals(responseType);
    }

}
