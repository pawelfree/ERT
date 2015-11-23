package pl.pd.emir.enums;

public enum IntergropuTrans {

    Y,
    N,
    ERR;
    //DOTO
    //Wartości do uzupełnienia

    public static IntergropuTrans fromString(String value) {
        for (IntergropuTrans currCode : IntergropuTrans.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }
}
