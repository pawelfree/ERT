package pl.pd.emir.enums;

public enum InstitutionIdType {

    LEIC, //kod LEI
    PLEI, //identyfikator tymczasowy
    BICC, //kod BIC
    OTHR, //inny identyfikator
    ERR;  //bledny

    public static InstitutionIdType fromString(String value) {
        for (InstitutionIdType idType : InstitutionIdType.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }

}
