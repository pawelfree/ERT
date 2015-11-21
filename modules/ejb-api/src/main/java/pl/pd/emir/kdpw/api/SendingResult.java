package pl.pd.emir.kdpw.api;

import java.util.ArrayList;
import java.util.List;
import pl.pd.emir.commons.CollectionsUtils;

public class SendingResult<E extends TransactionToRepository> {

    private boolean generateError = false;

    private List<TransactionToRepository> listToSend;

    private final List<SentItem> sentList;

    private final List<UnsentItem> unsentList;

    private final List<ErrorItem> noChangesList;

    private int batchNumber;

    public SendingResult() {
        sentList = new ArrayList<>();
        listToSend = new ArrayList<>();
        unsentList = new ArrayList<>();
        noChangesList = new ArrayList<>();
    }

    public final void sendingEror() {
        generateError = true;
    }

    public boolean isGenerateError() {
        return generateError;
    }

    public final boolean noUprocessed() {
        return CollectionsUtils.isEmpty(noChangesList);
    }

    public final boolean anyUnprocessed() {
        return !noUprocessed();
    }

    public final boolean anyUnsent() {
        return CollectionsUtils.isNotEmpty(unsentList);
    }

    public final List<ErrorItem> getUnprocessed() {
        return noChangesList;
    }

    public final List<TransactionToRepository> getListToSend() {
        return listToSend;
    }

    public List<UnsentItem> getUnsentList() {
        return unsentList;
    }

    public final void addItem(ResultItem item) {
        if (null != item.getType()) switch (item.getType()) {
            case TO_SEND:
                listToSend.add((E) item);
                break;
            case UNSENT:
                unsentList.add((UnsentItem) item);
                break;
            case SENT:
                sentList.add((SentItem) item);
                break;
            default:
                noChangesList.add((ErrorItem) item);
                break;
        }
    }

    public final void addItems(List<ResultItem> items) {
        items.stream().forEach((resultItem) -> {
            addItem(resultItem);
        });
    }

    public void setListToSend(List<TransactionToRepository> listToSend) {
        this.listToSend = listToSend;
    }

    public List<SentItem> getSentList() {
        return sentList;
    }

    public void addToSentList(List<SentItem> items) {
        this.sentList.addAll(items);
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

}
