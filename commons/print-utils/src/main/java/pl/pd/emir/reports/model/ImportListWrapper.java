package pl.pd.emir.reports.model;

import java.util.Date;

public class ImportListWrapper {

    private Date importDateSupply;
    private String importUser;
    private String importScopeMsgKey;
    private Date importDate;
    private String importStatusMsgKey;

    public Date getImportDateSupply() {
        return importDateSupply;
    }

    public void setImportDateSupply(Date importDateSupply) {
        this.importDateSupply = importDateSupply;
    }

    public String getImportUser() {
        return importUser;
    }

    public void setImportUser(String importUser) {
        this.importUser = importUser;
    }

    public String getImportScopeMsgKey() {
        return importScopeMsgKey;
    }

    public void setImportScopeMsgKey(String importScopeMsgKey) {
        this.importScopeMsgKey = importScopeMsgKey;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getImportStatusMsgKey() {
        return importStatusMsgKey;
    }

    public void setImportStatusMsgKey(String importStatusMsgKey) {
        this.importStatusMsgKey = importStatusMsgKey;
    }
}
