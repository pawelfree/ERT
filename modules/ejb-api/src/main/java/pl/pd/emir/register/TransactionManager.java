package pl.pd.emir.register;

import java.util.Date;
import java.util.List;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.manager.GenericManagerControlDate;

public interface TransactionManager extends GenericManagerControlDate<Transaction> {

    Transaction findNewestConfirmed(final String sourceTransId, final Long currentTransId);

    Boolean isExistsItemYesterday(Long itemId, String innerId);

    Boolean isExistsItem(Long itemId, String innerId, Date transactionDate, Integer extractVersion);

    boolean isYoungerMutation(String innerId, Date transactionDate, Integer extractVersion);

    Long countDistinctTransactionForDay(Long idClient, String instrumentType, Date day);

    List<Object[]> sumAmountTransactionForDay(Long idClient, String instrumentType, Date day);

    Transaction save(Transaction entity, boolean mutation, boolean isValuation);

    List<Transaction> saveAll(List<Transaction> transactionList);

    List<Transaction> getByDateAndOriginalId(String originalId, Date transactionDate);

    List<Transaction> getByDate(Date transactionDate, Integer startIndex, Integer resultSize);

    List<Transaction> getByOriginalId(String originalId);

    List<Transaction> getTransactionByFilenameAndDate(String filename, Date date);

    Transaction findOtherProcessingNew(Long currentId, String sourceId, Date maxDate);

    Transaction getSentByOriginalIdAndDate(String originalId, Date transactionDate);

    Transaction getByOriginalIdMaxVersion(final String originalId);

    Transaction getBySourceNrRefMaxVersion(final String sourceNrRef);

    List<Transaction> getUniquenessIdOriginal(String originalId);

    List<Object[]> isPossibilityDeleteTransaction(String originalId);

    Transaction findForVersion(String originalId, Date transactionDate, DataType dataType, ProcessingStatus... statuses);

    Transaction findOtherVersion(Long currentId, String originalId, Date transactionDate, DataType dataType, ProcessingStatus... statuses);

    Transaction findNewestOtherVersion(Long currentId, String sourceTransId, Date maxDate, ProcessingStatus... statuses);

    Transaction update(Transaction transaction);

    /**
     * Update obiektu tranakcji - TYLKO merge.
     *
     * @param transaction Obiekt transakcji.
     * @return Zaktualizowany, podłaczony obiekt transakcji.
     */
    Transaction updateOnlyMerge(Transaction transaction);

    Integer getNewestVersionTransaction(String originalId, Date transactionDate);

    boolean isMutation(Transaction transaction);

    boolean isNewerVersion(Transaction transaction);

    Transaction saveTransaction(final Transaction entity, boolean mutation, boolean isValuation);

    List<Transaction> getTransactionByImportLog(final ImportLog idImportLog);

    Long getCountTransactionByClient(Client client);

    Transaction getNewestVersion(Date transactionDate, String originalId);

    List<Transaction> findProcessedByKDPW(String tradIdId, Date eligDate);

    boolean checkForStatusTr(Transaction transaction, Date reportingDate);

    public Transaction findNewestTransaction(String tradIdId, Date eligDate, Date eligDate1);

    public Long getTransactionsCountForADay(Date transactionDate);

    public Long getNewTransactionsCountForADay(Date transactionDate);

    public Long getMaturedTransactionsCountForADay(Date transactionDate);

    public Long getKdpwReportsCountForADay(Date transactionDate);
}
