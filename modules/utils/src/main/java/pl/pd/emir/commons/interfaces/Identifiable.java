package pl.pd.emir.commons.interfaces;

import java.io.Serializable;

public interface Identifiable<PK extends Long> extends Serializable, Initializable {

    PK getId();

    void setId(PK id);

}
