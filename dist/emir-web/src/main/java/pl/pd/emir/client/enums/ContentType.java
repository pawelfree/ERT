package pl.pd.emir.client.enums;

public enum ContentType {

    TEXT_PLAIN("text/plain");

    private final String value;

    private ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
