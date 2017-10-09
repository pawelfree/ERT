package pl.pd.emir.enums;

public enum ParameterKey implements MsgEnum {

    IMPORT_INPUT_URI,
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
     * Ręczna edycja kodu beneficjenta w sekcji kontrachenta podczas ręcznego tworzenia transakcji
     */
    DISABLE_MANUAL_TRANSACTION_BENEFICIARY_CODE_EDITING,
    
    INSTITUTION_ID,
    
    INSTITUTION_ID_TYPE,
    CUSTOMERS_TO_SKIP_DURING_IMPORT;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
