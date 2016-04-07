package pl.pd.emir.kdpw.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PawelDudek
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "change")
public class ChangeRegister {
    private String className;
    private String fieldName;
    private String oldValue;
    private String newValue;
    
    public ChangeRegister() {
    }
    
    public ChangeRegister(String className, String fieldName, String oldValue, String newValue) {
        this.className = className;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getClassName() {
        return className;
    }
  
    public String getFieldName() {
        return fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

}
