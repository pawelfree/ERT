package pl.pd.emir.enums;

public enum ContrPartyIndustry implements MsgEnum {

    A,
    C,
    F,
    I,
    L,
    O,
    R,
    U;

    public static ContrPartyIndustry fromString(String value) {
        for (ContrPartyIndustry contrPartyIndustry : ContrPartyIndustry.values()) {
            if (contrPartyIndustry.toString().equals(value)) {
                return contrPartyIndustry;
            }
        }
        return null;
    }

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
