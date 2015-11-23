package pl.pd.emir.enums;

public enum YesNo implements MsgEnum {

    Y,
    N;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }

    public Boolean getLogical() {
        return (Boolean) Y.equals(this);
    }
}
