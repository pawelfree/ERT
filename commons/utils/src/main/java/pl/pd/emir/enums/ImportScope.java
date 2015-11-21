package pl.pd.emir.enums;

public enum ImportScope implements MsgEnum {

    BANK_E,
    CLIENT_E,
    VALUATION_E,
    PROTECTION_E,
    TRANSACTION_E,
    TRANSACTION_CAP_FLOOR,
    TRANSACTION_FRA_DEAL,
    TRANSACTION_FX_DEAL,
    TRANSACTION_IRS_CIRS,
    TRANSACTION_OPTION,
    EMIR_UTI,
    EMIR_DONE;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
