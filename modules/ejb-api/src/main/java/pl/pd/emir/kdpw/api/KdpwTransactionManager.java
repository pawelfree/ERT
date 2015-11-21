package pl.pd.emir.kdpw.api;

import java.util.List;
import pl.pd.emir.criteria.TransactionToKdpwSC;

public interface KdpwTransactionManager {
    
    Long getKdpwReportsCount(TransactionToKdpwSC criteria);

    SendingResult generateRegistration(TransactionToKdpwSC criteria);

    SendingResult generateMsg(List<ResultItem> resultItems, int batchNumber, String userLogin);
}
