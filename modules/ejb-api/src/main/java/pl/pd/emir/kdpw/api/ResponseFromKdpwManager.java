package pl.pd.emir.kdpw.api;

import java.util.Date;
import pl.pd.emir.kdpw.api.to.ImportResult;

public interface ResponseFromKdpwManager {

    boolean processMessages(Date date, String userLogin, ImportResult results);

}
