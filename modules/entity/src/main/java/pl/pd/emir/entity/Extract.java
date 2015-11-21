package pl.pd.emir.entity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Extract {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IMPORT_LOG_ID", nullable = true)
    private ImportLog importLog;

    public abstract Integer getExtractVersion();

    public abstract String getExtractName();

    public abstract String getFileName();

    public ImportLog getImportLog() {
        return importLog;
    }

    public void setImportLog(ImportLog importLog) {
        this.importLog = importLog;
    }

}
