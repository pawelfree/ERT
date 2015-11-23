package pl.pd.emir.reports.model;

import java.util.Date;

public class KDPWResponseWrapper {
    //nagłowek
    private String statusMessageFile;
    private Date datatimeLoading;
    private String login;
    //dane tabelaryczne
    private String idMessage;
    private String idTransaction;
    private String idClients;
    private String statusMessage;
    private String errorCode;
    private String descriptionError;

    public String getStatusMessageFile() {
        return statusMessageFile;
    }

    public void setStatusMessageFile(String statusMessageFile) {
        this.statusMessageFile = statusMessageFile;
    }

    public Date getDatatimeLoading() {
        return datatimeLoading;
    }

    public void setDatatimeLoading(Date datatimeLoading) {
        this.datatimeLoading = datatimeLoading;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdClients() {
        return idClients;
    }

    public void setIdClients(String idClients) {
        this.idClients = idClients;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }
}
