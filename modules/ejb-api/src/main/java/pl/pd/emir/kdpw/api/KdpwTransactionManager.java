package pl.pd.emir.kdpw.api;

import java.util.List;
import pl.pd.emir.criteria.TransactionToKdpwSC;

public interface KdpwTransactionManager {
    
    Long getKdpwReportsCount(TransactionToKdpwSC criteria);

    SendingResult generateRegistration(TransactionToKdpwSC criteria);

    SendingResult generateMsg(TransactionsToKdpwBag transactionsBag, int batchNumber, String userLogin);
}
