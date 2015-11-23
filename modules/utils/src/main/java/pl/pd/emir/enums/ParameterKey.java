package pl.pd.emir.enums;

public enum ParameterKey implements MsgEnum {

    IMPORT_INPUT_URI,
    EXPORT_CSV_URI,
    IMPORT_INPUT_MASK_BANK,
    IMPORT_INPUT_MASK_CLIENT,
    IMPORT_INPUT_MASK_VALUATION,
    IMPORT_INPUT_MASK_PROTECTION,
    IMPORT_INPUT_MASK_TRANSACTION,
    IMPORT_INPUT_CASE_SENSITIVE,
    ADAPTER_KDPW_OUTPUT_URI,
    ADAPTER_KDPW_INPUT_URI,
    AUTH_CONFIG_PATH,
    KDPW_RECEIVER,
    KDPW_BATCH_SIZE,
    ENABLE_BACKLOADING,
    SHOW_EXTRACT_VERSION,
    EMIR_DONE_MASK,
    EMIR_UTI_MASK,
    BANK_ADRESS,
    BANK_DIR_IN,
    BANK_DIR_ARCH,
    BANK_LOGIN,
    BANK_MOVE_FILE,
    BANK_PASSWORD,
    BANK_FILE_DATA,
    BANK_RAPORT_ACK,
    BANK_RAPORT_NACK,
    BANK_FILE_COMMENTS,
    BANK_DATA_CHANGE_PASSWORD,
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
