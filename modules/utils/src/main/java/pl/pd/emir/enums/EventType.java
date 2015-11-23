package pl.pd.emir.enums;

public enum EventType implements MsgEnum {

    USER_REGISTRATION(EventLogType.INFO, 4001),
    USER_MODIFICATION(EventLogType.INFO, 4002),
    USER_PERMISSIONS_MODIFICATION(EventLogType.INFO, 4003),
    USER_ACTIVITY_MODIFICATION(EventLogType.INFO, 4004),
    USER_DELETE(EventLogType.INFO, 4005),
    KDPW_MESSAGE_INSERT(EventLogType.INFO, 4006),
    KDPW_MESSAGE_IMPORT(EventLogType.INFO, 4007),
    KDPW_TRANSACTION_MESSAGE_CREATE(EventLogType.INFO, 4008),
    KDPW_TRANSACTION_CANCELLATION_MESSAGE_CREATE(EventLogType.INFO, 4009),
    KDPW_INSTITUTION_CHANGE_MESSAGE_CREATE(EventLogType.INFO, 4010),
    KDPW_GROUP_MESSAGE_CREATE(EventLogType.INFO, 4011),
    KDPW_XML_DOWNLOAD(EventLogType.INFO, 4012),
    EXTRACT_IMPORT(EventLogType.INFO, 4013),
    REPORT_CREATE(EventLogType.INFO, 4014),
    REPORT_EXPORT(EventLogType.INFO, 4015),
    BANK_MODIFICATION(EventLogType.INSTITUTION, 4016),
    BANK_MODIFICATION_CHANGELOG(EventLogType.INSTITUTION, 4017),
    TRANSACTION_INSERT(EventLogType.TRANSACTION, 4018),
    TRANSACTION_MODIFICATION(EventLogType.TRANSACTION, 4019),
    TRANSACTION_MODIFICATION_CHANGELOG(EventLogType.TRANSACTION, 4020),
    TRANSACTION_DELETE(EventLogType.TRANSACTION, 4021),
    TRANSACTION_ADD_MUTATION(EventLogType.TRANSACTION, 4022),
    PROTECTION_INSERT(EventLogType.PROTECTION_GROUP, 4023),
    PROTECTION_DELETE(EventLogType.PROTECTION_GROUP, 4024),
    PROTECTION_MODIFICATION(EventLogType.PROTECTION_GROUP, 4025),
    VALUATION_INSERT(EventLogType.VALUATION_GROUP, 4026),
    VALUATION_MODIFICATION(EventLogType.VALUATION_GROUP, 4027),
    VALUATION_DELETE(EventLogType.VALUATION_GROUP, 4028),
    CLIENT_INSERT(EventLogType.CONTRACTOR, 4041),
    CLIENT_MODIFICATION(EventLogType.CONTRACTOR, 4042),
    CLIENT_MODIFICATION_CHANGELOG(EventLogType.CONTRACTOR, 4043),
    CLIENT_DELETE(EventLogType.CONTRACTOR, 4044),
    REPORT_OVERFLOW_ADD(EventLogType.INFO, 4045),
    REPORT_THRESHOLD_ADD(EventLogType.INFO, 4046),
    TRANSACTION_ADD_VALUATION(EventLogType.TRANSACTION, 4047);
    ;

    private EventLogType logType;
    private Integer mzdzCode;

    private EventType(EventLogType logType) {
        this.logType = logType;
    }

    private EventType(EventLogType logType, Integer mzdzCode) {
        this.logType = logType;
        this.mzdzCode = mzdzCode;
    }

    public EventLogType getLogType() {
        return logType;
    }

    public void setLogType(EventLogType logType) {
        this.logType = logType;
    }

    public Integer getMzdzCode() {
        return mzdzCode;
    }

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
