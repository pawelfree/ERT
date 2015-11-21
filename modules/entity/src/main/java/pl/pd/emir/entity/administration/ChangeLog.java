package pl.pd.emir.entity.administration;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChangeLog implements Serializable {

    @Column(name = "FIELD_NAME", length = 256)
    private String fieldName;

    @Column(name = "OLD_VALUE", length = 512)
    private String oldValue;

    @Column(name = "NEW_VALUE", length = 512)
    private String newValue;

    @Column(name = "CHANGE_COMMENT", length = 512)
    private String changeComment;

    public ChangeLog() {
        super();
    }

    public ChangeLog createNew(String fieldName, String oldValue, String newValue) {
        ChangeLog item = new ChangeLog();
        item.fieldName = fieldName;
        item.oldValue = oldValue;
        item.newValue = newValue;
        return item;
    }

    public ChangeLog(String[] modificationData) {
        this.fieldName = modificationData[0];
        this.oldValue = modificationData[1];
        this.newValue = modificationData[2];
        this.changeComment = modificationData.length == 4 ? modificationData[3] : null;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getChangeComment() {
        return changeComment;
    }

    public void setChangeComment(String changeComment) {
        this.changeComment = changeComment;
    }
}
