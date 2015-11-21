package pl.pd.emir.enums;

public enum ValidationStatus implements MsgEnum {

    VALID,
    INCOMPLETE,
    INVALID;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }

    public static ValidationStatus fromString(String value) {
        for (ValidationStatus status : ValidationStatus.values()) {
            if (status.name().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
