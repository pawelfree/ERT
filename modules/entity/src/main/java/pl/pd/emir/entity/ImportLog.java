package pl.pd.emir.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ImportStatus;

@Entity
@Table(name = "IMPORT_LOG")
@NamedQueries({
    @NamedQuery(name = "ImportLog.getFailLogList",
            query = "SELECT i.failLogList FROM ImportLog i WHERE i.id = :id")
})

public class ImportLog implements Selectable<Long> {

    private static final long serialVersionUID = 1853949994868735041L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "import_log_seq_gen")
    @SequenceGenerator(name = "import_log_seq_gen", sequenceName = "SQ_EMIR_IMPORT_LOG", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMPORT_DATE", nullable = false)
    private Date importDate;

    @Column(name = "IMPORT_USER")
    private String importUser;

    @Column(name = "IMPORT_SCOPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportScope importScope;
    /**
     * Data danych – pole/kolumna DATA_TR z ekstraktów: VALUATION_E, PROTECTION_E, TRANSACTION_E, SET_VALUATION_E,
     * SET_PROTECTION_E, TERMINATION_E. Dla pozostałych ekstraktów pole będzie niewypełnione
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;
    /**
     * Status importu @link ImportStatus.
     */
    @Column(name = "IMPORT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportStatus importStatus;
    /**
     * Liczba rekordow zaimportowanych z wszystkich ekstraktow
     */
    @Column(name = "RECORDS_COUNT", nullable = false)
    private Integer recordsCount;
    /**
     * Nazwa wczytanego pliku.
     */
    @Column(name = "FILE_NAME", length = 512, nullable = true)
    private String fileName;
    /**
     * Lista błędów.
     */
    @JoinTable(name = "IMPORT_FAIL_LOG_ASSOC")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ImportFailLog> failLogList;

    /**
     * Flaga informująca zaznaczeniu rekordu na widoku.
     */
    @Transient
    private transient boolean selected;
    /**
     * Flaga określająca czy w ramach życia obiektu ImportLog dodano do niego informacje o rekordzie z błędami.
     */
    @Transient
    private boolean wasErrors;
    /**
     * Flaga określająca czy w ramach życia obiektu ImportLog dodano do niego informacje o rekordzie z ostrzeżeniami.
     */
    @Transient
    private boolean wasWarnings;
    /**
     * Flaga określająca czy w ramach życia obiektu ImportLog dodano do niego informacje o prawidłowym rekordzie.
     */
    @Transient
    private boolean wasCorrect;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getImportUser() {
        return importUser;
    }

    public void setImportUser(String importUser) {
        this.importUser = importUser;
    }

    public ImportScope getImportScope() {
        return importScope;
    }

    public void setImportScope(ImportScope importScope) {
        this.importScope = importScope;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ImportStatus importStatus) {
        this.importStatus = importStatus;
    }

    public List<ImportFailLog> getFailLogList() {
        return failLogList;
    }

    public void setFailLogList(List<ImportFailLog> failLogList) {
        this.failLogList = failLogList;
    }

    public Integer getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        this.recordsCount = recordsCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ImportLog() {
        super();
        wasErrors = false;
        wasWarnings = false;
        wasCorrect = false;
    }

    public ImportLog(Date importDate, String importUser, ImportScope importScope, Date transactionDate,
            ImportStatus importStatus, List<ImportFailLog> failLogList) {
        super();
        this.importDate = importDate;
        this.importUser = importUser;
        this.importScope = importScope;
        this.transactionDate = transactionDate;
        this.importStatus = importStatus;
        this.recordsCount = 0;

        if (failLogList == null) {
            this.failLogList = new ArrayList<>();
        } else {
            this.failLogList = failLogList;
        }

        wasErrors = false;
        wasWarnings = false;
        wasCorrect = false;
    }

    @Override
    public void initFields() {
        //EMPTY
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void processFailLogs(List<ImportFailLog> importErrors, List<ImportFailLog> importWarnings) {
        if (!importErrors.isEmpty()) {
            wasErrors = true;
        }
        if (!importWarnings.isEmpty()) {
            wasWarnings = true;
        }
        if (importErrors.isEmpty() && importWarnings.isEmpty()) {
            wasCorrect = true;
        }

        failLogList.addAll(importErrors);
        failLogList.addAll(importWarnings);

        //TODO to jest niezbyt dobre miejsce na to
        recordsCount++;
    }

    public void determineStatus() {
        if ((wasErrors && !wasCorrect && !wasWarnings) || (!wasErrors && !wasCorrect && !wasWarnings)) {
            //w przypadku gdy wszystkie rekordy są błędne bądź nie ma żadnych rekordów status całości to ERROR
            importStatus = ImportStatus.ERROR;
        } else if (wasWarnings) {
            //w przypadku gdy wystąpiły jakieś błędy lub ostrzeżenia status całości to WARNING
            importStatus = ImportStatus.WARRNING;
        } else {
            //w przypadu gdy wszystkie rekordy są prawidłowe status całości to OK
            importStatus = ImportStatus.OK;
        }
    }

}
