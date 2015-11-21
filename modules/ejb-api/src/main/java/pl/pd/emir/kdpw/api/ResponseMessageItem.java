package pl.pd.emir.kdpw.api;

public class ResponseMessageItem {

    String messageStatus;
    String messageId; // Data Submitter Message Id
    String messageAction; // New, Modify, Cancel, Error

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(String messageAction) {
        this.messageAction = messageAction;
    }

}
