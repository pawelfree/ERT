package pl.pd.emir.clientutils;

public enum MessageDialogType {
    
    INFO,
    WARNING,
    ERROR,
    CONFIRM,
    ;

    public String getMsgKey() {
        return String.format("commons.%s.%s", this.getClass().getSimpleName(), this.name());
    }
    
}
