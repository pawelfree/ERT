package pl.pd.emir.enums;

public enum Compression {

    Y,
    N,
    ERR;

    public static Compression fromString(String value) {
        for (Compression idType : Compression.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }
}
