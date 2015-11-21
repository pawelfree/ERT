package pl.pd.emir.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.ImportStatus;

@Entity
@Table(name = "IMPORT_FAIL_LOG")
public class ImportFailLog implements Selectable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "import_fail_seq_gen")
    @SequenceGenerator(name = "import_fail_seq_gen", sequenceName = "SQ_EMIR_IMPORT_FAIL_LOG", allocationSize = 1)
    private Long id;
    /**
     * Kategoria bledu @link ImportStatus.
     */
    @Column(name = "ERROR_CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportStatus errorCategory;
    /**
     * Opis bledu.
     */
    @Column(name = "ERROR_DESCRIPTION", length = 512, nullable = false)
    private String errorDescription;
    /**
     * Numer pola, którego dotyczy błąd.
     */
    @Column(name = "NUMBER_FIELD", length = 128, nullable = true)
    private String numberField;
    @Transient
    private transient boolean selected;

    protected ImportFailLog() {
        super();
    }

    public ImportFailLog(String errorDescription) {
        super();
        this.errorDescription = errorDescription;
    }

    public ImportFailLog(String errorDescription, String numberField) {
        super();
        this.errorDescription = errorDescription;
        this.numberField = numberField;
    }

    public ImportFailLog(ImportStatus errorCategory, String errorDescription) {
        super();
        this.errorCategory = errorCategory;
        this.errorDescription = errorDescription;
    }

    public ImportFailLog(ImportStatus errorCategory, String errorDescription, String numberField) {
        super();
        this.errorCategory = errorCategory;
        this.errorDescription = errorDescription;
        this.numberField = numberField;
    }

    public ImportStatus getErrorCategory() {
        return errorCategory;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setErrorCategory(ImportStatus errorCategory) {
        this.errorCategory = errorCategory;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getNumberField() {
        return numberField;
    }

    public void setNumberField(String numberField) {
        this.numberField = numberField;
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
}
