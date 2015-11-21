package pl.pd.emir.modules.kdpw.adapter.model;

public class ResponseItem {

    private String sndrMsgRef;

    private String prvsSndrMsgRef;

    private String statusCode;

    private String reasonCode;

    private String reasonText;

    private String rltdReqRef;

    public String getPrvsSndrMsgRef() {
        return prvsSndrMsgRef;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getReasonText() {
        return reasonText;
    }

    public String getRltdReqRef() {
        return rltdReqRef;
    }

    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

    public void setSndrMsgRef(String sndrMsgRef) {
        this.sndrMsgRef = sndrMsgRef;
    }

    public void setPrvsSndrMsgRef(String prvsSndrMsgRef) {
        this.prvsSndrMsgRef = prvsSndrMsgRef;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public void setRltdReqRef(String rltdReqRef) {
        this.rltdReqRef = rltdReqRef;
    }

}
