package pl.pd.emir.reports.enums;

public enum ContentTypes {

    CSV("application/csv"), XLS("application/xls"), XLSMany("application/xls"), XML("application/xml");
    private final String type;

    private ContentTypes(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
