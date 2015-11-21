package pl.pd.emir.enums;

public enum ContractType {

    U, I, E, ERR;

    public static ContractType fromString(String value) {
        for (ContractType idType : ContractType.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }

}
