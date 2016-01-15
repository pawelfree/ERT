package pl.pd.emir.enums;

public enum ParameterKey implements MsgEnum {

    IMPORT_INPUT_URI,
    IMPORT_INPUT_MASK_BANK,
    IMPORT_INPUT_MASK_CLIENT,
    IMPORT_INPUT_MASK_VALUATION,
    IMPORT_INPUT_MASK_PROTECTION,
    IMPORT_INPUT_MASK_TRANSACTION,
    IMPORT_INPUT_CASE_SENSITIVE,
    AUTH_CONFIG_PATH,
    KDPW_RECEIVER,
    KDPW_BATCH_SIZE,
    ENABLE_BACKLOADING,
    SHOW_EXTRACT_VERSION,
    KDPW_SENDER,

    /**
     * Wielkość paczki transakcji wczytywanych podczas importu dla banku i.
     */
    IMPORT_TRANSACTION_BATCH_SIZE,
    /**
     * Ręczna edycja kodu beneficjenta w sekcji kontrachenta podczas ręcznego tworzenia transakcji
     */
    DISABLE_MANUAL_TRANSACTION_BENEFICIARY_CODE_EDITING;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
