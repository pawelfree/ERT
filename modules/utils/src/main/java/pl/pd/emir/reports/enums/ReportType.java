package pl.pd.emir.reports.enums;

import java.util.Arrays;
import java.util.List;

public enum ReportType {

    USER_LIST("user_list", Arrays.asList(ReportFormat.XLS)),
    IMPORT_LIST_REPORT_PART2("import_list_report_part2", Arrays.asList(ReportFormat.XLS)),
    IMPORT_LIST_REPORT("import_list_report", Arrays.asList(ReportFormat.XLS)),
    KDPW_RESPONSE_REPORT("kdpw_response_report", Arrays.asList(ReportFormat.XLS)),
    KDPW_REQUEST_REPORT("kdpw_request_report", Arrays.asList(ReportFormat.XLS)),
    SETS_PROTECTION_TABLE("sets_protection", Arrays.asList(ReportFormat.XLS)),
    SETS_VALUATION_TABLE("sets_valuation", Arrays.asList(ReportFormat.XLS)),
    KDPWLIST_TABLE("kdpwList", Arrays.asList(ReportFormat.XLS)),
    CONTRACTORS_TABLE("contractors", Arrays.asList(ReportFormat.XLS)),
    THRESHOLD_REPORT("threshold_report", Arrays.asList(ReportFormat.XLS)),
    OVERFLOW_REPORT("overflow_report", Arrays.asList(ReportFormat.XLS)),
    EVENT_LOG_TABLE("event_log", Arrays.asList(ReportFormat.XLS)),
    IMPORT_LIST("import_list", Arrays.asList(ReportFormat.XLS)),
    TRANSACTION_TABLE("transaction_table", Arrays.asList(ReportFormat.XLS)),
    REGISTRATION_TRANSACTION_TABLE("registration_transaction", Arrays.asList(ReportFormat.XLS)),
    TEST("test", Arrays.asList(ReportFormat.XLS));
    private final String templateName;
    private final List<ReportFormat> supportedFormats;

    private ReportType(final String templateName, final List<ReportFormat> supportedFormats) {
        this.templateName = templateName;
        this.supportedFormats = supportedFormats;
    }

    public String getTemplateName() {
        return templateName;
    }

    public List<ReportFormat> getSupportedFormats() {
        return supportedFormats;
    }

    public String getKey() {
        return this.getClass().getSimpleName() + "." + this.name();
    }
}
