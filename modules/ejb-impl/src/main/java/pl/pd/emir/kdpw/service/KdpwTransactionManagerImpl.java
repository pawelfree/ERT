package pl.pd.emir.kdpw.service;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import pl.pd.emir.admin.MultiNumberGenerator;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.criteria.TransactionToKdpwSC;
import pl.pd.emir.dao.utils.FilterSortTO;
import pl.pd.emir.entity.Sendable;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.entity.administration.Parameter;
import pl.pd.emir.entity.annotations.ProtectionChange;
import pl.pd.emir.entity.annotations.ValuationChange;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.kdpw.api.ErrorItem;
import pl.pd.emir.kdpw.api.KdpwTransactionManager;
import pl.pd.emir.kdpw.api.MessageLogManager;
import pl.pd.emir.kdpw.api.ResultItem;
import pl.pd.emir.kdpw.api.SendingResult;
import pl.pd.emir.kdpw.api.SentItem;
import pl.pd.emir.kdpw.api.TransactionToRepository;
import pl.pd.emir.kdpw.api.UnsentItem;
import pl.pd.emir.kdpw.api.enums.ItemType;
import pl.pd.emir.kdpw.api.enums.SendingError;
import pl.pd.emir.kdpw.api.exception.KdpwServiceException;
import pl.pd.emir.kdpw.service.utils.KdpwUtils;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.manager.GenericManager;
import pl.pd.emir.modules.kdpw.adapter.api.TransactionWriter;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionWriterResult;
import pl.pd.emir.register.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.admin.EventLogManager;
import pl.pd.emir.entity.annotations.TransactionDataChange;
import pl.pd.emir.kdpw.api.TransactionsToKdpwBag;
import pl.pd.emir.kdpw.api.ChangeRegister;
import pl.pd.emir.kdpw.api.ChangesToTransactions;
import pl.pd.emir.kdpw.api.TransactionChanges;

@Stateless
@Local(KdpwTransactionManager.class)
public class KdpwTransactionManagerImpl implements KdpwTransactionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KdpwTransactionManagerImpl.class);

    @EJB
    private transient TransactionWriter transactionWriter;

    @EJB
    private transient MessageLogManager logManager;

    @EJB
    private transient MultiNumberGenerator numberGenerator;

    @EJB
    private transient EventLogManager eventLogManager;

    @EJB
    private transient KdpwMsgItemManager kdpwItemManager;

    @EJB
    private transient ParameterManager parameterManager;

    @EJB
    private transient UserManager userManager;

    @EJB
    private transient KdpwTransactionManager thisManager;

    @EJB
    protected transient TransactionManager transactionManager;

    @EJB
    private transient TransactionToCancelManager cancelManager;

    protected final GenericManager getRegisterDateManager() {
        return transactionManager;
    }

    @Override
    public Long getKdpwReportsCount(final TransactionToKdpwSC criteria) {
        return transactionManager.getKdpwReportsCountForADay(criteria.getTransactionDate());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public SendingResult generateRegistration(final TransactionToKdpwSC criteria) {
        final GenericManager manager = getRegisterDateManager();
        final String userLogin = userManager.getCurrentUserLogin();
        final SendingResult mainResult = new SendingResult();
        final int batchSize = getBatchSize();
        criteria.getFitrSort().setSortField("id");
        criteria.getFitrSort().setSortOrder(FilterSortTO.SortOrder.ASCENDING);
        int batchNumber = 0;

        final StringBuilder errorLog = new StringBuilder();
        boolean error = false;
        long maxId = 0;
        for (int i = 1; i <= 10000; i++) {
            criteria.setFromId(maxId);
            criteria.addFilters();
            final List<Sendable> list = getListToSend(manager, criteria, 0, batchSize);

            if (CollectionsUtils.isEmpty(list)) {
                break;
            } else {
                maxId = list.get(list.size() - 1).getId();
                LOGGER.info("NEW MAX id = " + maxId);
            }
            TransactionsToKdpwBag transactionsBag = getRegistrationListByType(list);
            try {
                SendingResult tmpResult = thisManager.generateMsg(transactionsBag, batchNumber, userLogin);
                batchNumber = tmpResult.getBatchNumber();
                mainResult.addItems(tmpResult.getSentList());
                mainResult.addItems(tmpResult.getUnsentList());
                mainResult.addItems(tmpResult.getUnprocessed());
            } catch (Exception e) {
                LOGGER.error("Generate msg error: " + e);
                errorLog.append("Bład: ").append(e);
                error = true;
            }
        }

        if (error && batchNumber == 0) {
            throw new RuntimeException("Bład generowania komunikatu: " + errorLog.toString());
        }
        return mainResult;
    }

    private List<Sendable> getListToSend(GenericManager manager, TransactionToKdpwSC criteria, int start, int max) {
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);
        if (CollectionsUtils.isNotEmpty(criteria.getSelectedIds())) {
            return manager.find(criteria).getData();
        } else if (CollectionsUtils.isNotEmpty(criteria.getDeselectedIds())) {
            return manager.findWithoutDeselected(criteria, criteria.getDeselectedIds());
        } else {
            return manager.find(criteria).getData();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public SendingResult generateMsg(TransactionsToKdpwBag transactionsBag, final int batchNumber, final String userLogin) {
        logCurrentTime("Start generate MSG");
        SendingResult tmpResult = new SendingResult();
        // zmiana statusu na: NIEWYSŁANA
        int unsentCounter = 0;
        int unprocessCounter = 0;
        final List<TransactionToRepository> toSendList = new ArrayList<>();
        for (ResultItem resultItem : transactionsBag.getItems()) {
            if (null != resultItem.getType()) { 
                switch (resultItem.getType()) {
                    case UNSENT:
                        unsentCounter += 1;
                        UnsentItem unsentItem = (UnsentItem) resultItem;
                        if (unsentItem.getTransaction() != null) {
                            unsentItem.getTransaction().unsent();
                            transactionManager.updateOnlyMerge(unsentItem.getTransaction());
                            tmpResult.addItem(unsentItem); // dodaj niewysłane
                        }   break;
                    case TO_SEND:
                        toSendList.add((TransactionToRepository) resultItem);
                        break;
                    case UNPROCESSED:
                        unprocessCounter += 1;
                        tmpResult.addItem(resultItem); // dodaj nieprocesowane
                        break;
                    default:
                        break;
                }
            }
        }
        LOGGER.info("ToSend transactions list =  {}", toSendList.size());
        LOGGER.info("Unsent transactions list =  {}", unsentCounter);
        LOGGER.info("Unprocess transactions list =  {}", unprocessCounter);

        if (CollectionsUtils.isNotEmpty(toSendList)) {
            try {
                generateFile(toSendList, userLogin, batchNumber, transactionsBag.getInfo(), transactionsBag.getTransactionChanges());
                toSendList.stream().forEach((ttr) -> {
                    tmpResult.addItem(new SentItem(ttr.getRegistable().getOriginalId())); // dodaj wysłane
                });
                tmpResult.setBatchNumber(batchNumber + 1);
            } catch (XmlParseException | UnsupportedEncodingException ex) {
                LOGGER.error("XML writing error: " + ex);
                throw new KdpwServiceException(ex);
            }
        }
        return tmpResult;
    }

    private String getTransactionChangesXmlString(List<TransactionChanges> changes) {
        String xmlString = "";
        try {
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(ChangesToTransactions.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new ChangesToTransactions(changes), sw);
            xmlString = sw.toString();
        } catch (JAXBException ex) {
            LOGGER.error("Convert changes to xml failed.",ex);
        }
        return xmlString;
    }

    protected void generateFile(final List<TransactionToRepository> messages, final String userLogin,
            final Integer batchNumber, final String info, final List<TransactionChanges> changes) throws XmlParseException, UnsupportedEncodingException {

        logCurrentTime(String.format("START generate file for %s messages.", messages.size()));

        TransactionWriterResult result = transactionWriter.write(messages, getReportingInstitutionId());
        logCurrentTime("End of writing file");
        String message = result.getMessage();

        final MessageLog messageLog = logManager.save(MessageLog.Builder.getOutput(KdpwUtils.getMsgLogNumber(numberGenerator),
                MessageType.TRANSACTION,
                userLogin,
                new String(message.getBytes(), "UTF-8"),
                batchNumber,
                info,
                new String(getTransactionChangesXmlString(changes).getBytes(),"UTF-8")));

        final List<TransactionToRepository> transactions = result.getTransactions();
        logEvent(messageLog, getEventType(transactions), transactions.size(), MessageType.TRANSACTION);
        final Map<Long, ArrayList<TransactionToRepository>> transactionMap = getTransactionMap(transactions);

        transactionMap.keySet().stream().map((key) -> {
            final Sendable registable = transactionMap.get(key).get(0).getRegistable();
            final Transaction transaction = registable.getTransaction();
            transactionMap.get(key).stream().forEach((item) -> {
                final KdpwMsgItem request = KdpwMsgItem.getRequest(transaction.getId(),
                        item.getSndrMsgRef(),
                        item.getMsgType(),
                        item.isCancelMutation(),
                        item.getRegistable().getOriginalId(),
                        item.getRegistable().getClient().getOriginalId(),
                        item.getRegistable().getClient2().getOriginalId());
                LOGGER.debug("KDPW request: transId: " + request.getExtractId() + ", msgId: " + request.getSndrMsgRef());

                request.assignToLog(messageLog);
                final KdpwMsgItem itemSaved = kdpwItemManager.save(request);

                transaction.addKdpwItem(itemSaved);
                if (TransactionMsgType.O.equals(item.getMsgType())) {
                    //nie rób nic
                } else if (TransactionMsgType.E.equals(item.getMsgType())) {
                    transaction.cancellationSent();
                    if (!item.isCancelMutation()) {
                        final List<Transaction> transactionsById = transactionManager.getByOriginalId(transaction.getOriginalId());
                        for (Transaction transactionById : transactionsById) {
                            if (!transaction.getId().equals(transactionById.getId())) {
                                transactionById.cancellationSent();
                                transactionById.addKdpwItem(itemSaved);
                                transactionManager.updateOnlyMerge(transactionById);
                            }
                        }
                    }
                } else {
                    transaction.sent();
                }
            });
            return transaction;
        }).forEach((transaction) -> {
            transactionManager.updateOnlyMerge(transaction);
        });
        logCurrentTime("End of generating file");
    }

    private class OngoingResults {

        private List<ResultItem> results;
        private TransactionChanges changes;

        public OngoingResults(List<ResultItem> results, TransactionChanges changes) {
            this.results = results;
            this.changes = changes;
        }
    }

    /**
     * Definiuje możliwość i typ komunikatu do wysyłki dla transakcji.
     *
     * @param list
     * @return
     */
    protected final TransactionsToKdpwBag getRegistrationListByType(final List<Sendable> list) {
        final List<ResultItem> result = new ArrayList<>();
        final List<TransactionChanges> changeList = new ArrayList<>();
        LOGGER.info("START | getRegistrationType (" + list.size() + ") on: " + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMAT));
        list.stream().filter((sendable) -> !(!checkClientCompleteness(sendable, result))).forEach((sendable) -> {
            if (KdpwUtils.isNew(sendable)) { // NOWA
                result.add(getTypeForNew(sendable));
            } else if (KdpwUtils.isCompleted(sendable)) { // ZAKONCZONA
                result.add(getTypeForCompleted(sendable));
            } else if (KdpwUtils.isOngoing(sendable)) { // TRWAJACA
                OngoingResults results = getTypeForOngoing(sendable);
                result.addAll(results.results);
                changeList.add(results.changes);
            } else if (KdpwUtils.isCancelled(sendable)) {
                result.add(getTypeForCancelled(sendable));
            } else {
                result.add(new ErrorItem(sendable.getOriginalId(), SendingError.NO_TYPE, ""));
            }
        });
        LOGGER.info("END | getRegistrationType on: " + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMAT));
        return countKdpwMessageTypes(result, changeList);
    }

    private TransactionsToKdpwBag countKdpwMessageTypes(List<ResultItem> list, List<TransactionChanges> changes) {
        List<ResultItem> tmp = list.stream().filter(item -> item.getType() == ItemType.TO_SEND && item.getClass().equals(TransactionToRepository.class)).collect(Collectors.toList());

        TransactionsToKdpwBag result = new TransactionsToKdpwBag(list, changes);
        long newCounter = tmp.stream().filter(item -> ((TransactionToRepository) item).getMsgType() == TransactionMsgType.N).count();
        long valCounter = tmp.stream().filter(item -> ((TransactionToRepository) item).getMsgType() == TransactionMsgType.V).count();
        long valCCounter = tmp.stream().filter(item -> ((TransactionToRepository) item).getMsgType() == TransactionMsgType.VR).count();
        long modCounter = tmp.stream().filter(item -> ((TransactionToRepository) item).getMsgType() == TransactionMsgType.M).count();
        long modCCounter = tmp.stream().filter(item -> ((TransactionToRepository) item).getMsgType() == TransactionMsgType.MC).count();

        result.setNewCounter(newCounter);
        result.setModCounter(modCounter);
        result.setModCCounter(modCCounter);
        result.setValCounter(valCounter);
        result.setValCCounter(valCCounter);
        return result;
    }

    protected final ResultItem getTypeForNew(final Sendable registable) {
        // sprawdza, że nie ma żadnej innej wersji tej transakcji dla której Typ danych = NOWA
        // i status przetworzenia jest WYSŁANA lub PRZYJĘTA
        Transaction transaction = registable.getTransaction();
        final Transaction oldTrans = transactionManager.findOtherVersion(transaction.getId(),
                transaction.getOriginalId(),
                transaction.getTransactionDate(),
                DataType.NEW,
                ProcessingStatus.SENT, ProcessingStatus.CONFIRMED);
        if (null != oldTrans) {
            return new ErrorItem(transaction.getOriginalId(),
                    SendingError.EXISTS_DATA_NEW_SENT_OR_CONFIRMED,
                    KdpwUtils.getVersionMsg(oldTrans));
        } else if (hasValuationAndProtection(transaction)) {
            return new TransactionToRepository(registable, TransactionMsgType.N);
        } else {
            // status przetworzenia bieżącej transakcji pozostaje bez zmian
            return new ErrorItem(transaction.getOriginalId(), SendingError.EMPTY_VALUATION_OR_PROTECTION_INFO, "");
        }
    }

    protected final ResultItem getTypeForCompleted(final Sendable registable) {
        // sprawdza, że nie ma żadnej wersji innej tej transakcji dla której Typ danych = ZAKOŃCZONA
        // i status przetworzenia jest WYSŁANA lub PRZYJĘTA
        final Transaction transaction = registable.getTransaction();

        if (isKdpwProcessed(transaction)) {
            return new UnsentItem(transaction.getOriginalId(), SendingError.KDPW_PROCESSED, "", transaction);
        }

        final Transaction oldTrans = transactionManager.findOtherVersion(transaction.getId(),
                transaction.getOriginalId(),
                transaction.getTransactionDate(),
                DataType.COMPLETED,
                ProcessingStatus.SENT, ProcessingStatus.CONFIRMED);
        if (null != oldTrans) {
            // tus przetworzenia bieżącej transakcji pozostaje bez zmian
            return new ErrorItem(transaction.getOriginalId(),
                    SendingError.EXISTS_DATA_COMPLETED_SENT_OR_CONFIRMED,
                    KdpwUtils.getVersionMsg(oldTrans));
        }
        return new TransactionToRepository(registable, TransactionMsgType.C);
    }

    private OngoingResults getTypeForOngoing(final Sendable registable) {
        final List<ResultItem> result = new ArrayList<>();
        // sprawdza, że nie ma transakcji o tym samym ID (pole TRADID_ID),
        // o dacie nie późniejszej niż data przetwarzanej transakcji ze statusem przetwarzania NOWA
        List<ChangeRegister> l1 = new ArrayList<>();
        List<ChangeRegister> l2;

        final Transaction transaction = registable.getTransaction();
        final Transaction oldTrans = transactionManager.findOtherProcessingNew(transaction.getId(),
                transaction.getTransactionDetails().getSourceTransId(),
                transaction.getTransactionDate());
        LOGGER.debug("getTypeForOngoing: {}", printTransaction(transaction));
        if (Objects.nonNull(oldTrans)) {
            result.add(new ErrorItem(transaction.getOriginalId(), SendingError.EXISTS_OTHER_UNSENT, getIdenAndVersionAndDate(oldTrans)));
        } else {
            final Transaction kdpwTrans = transactionManager.findNewestOtherVersion(transaction.getId(),
                    transaction.getTransactionDetails().getSourceTransId(),
                    transaction.getTransactionDate(),
                    ProcessingStatus.CONFIRMED, ProcessingStatus.SENT);
            if (Objects.nonNull(kdpwTrans)) {
                boolean anyChanges = false;
                if (hasValuationAndProtection(transaction)) {
                    l1 = valuationOrProtectionChanges(kdpwTrans, transaction);
                    if (!l1.isEmpty()) {
                        if (KdpwUtils.isReported(transaction.getClient())) {
                            result.add(new TransactionToRepository(registable, TransactionMsgType.VR));
                        } else {
                            result.add(new TransactionToRepository(registable, TransactionMsgType.V));
                        }
                        anyChanges = true;
                    }
                } else {
                    result.add(new ErrorItem(transaction.getOriginalId(),
                            SendingError.EMPTY_VALUATION_OR_PROTECTION_INFO,
                            ""));
                }

                l2 = transactionChanges(kdpwTrans, transaction);
                l1.addAll(l2);
                if (!l2.isEmpty()) {
                    if (transaction.getClient().getReported()) {
                        result.add(new TransactionToRepository(registable, TransactionMsgType.MC));
                    } else {
                        result.add(new TransactionToRepository(registable, TransactionMsgType.M));
                    }
                    anyChanges = true;
                }
                if (!anyChanges) {
                    result.add(new UnsentItem(transaction.getOriginalId(), SendingError.ONGOING_NO_CHANGES, "", transaction));
                }
            } else {
                LOGGER.debug("Cannot find other version of transaction in KDPW.");
                // 7.2.9.4.4. Odrzucenie z wysyłki transakcji z typem danych TRWAJĄCA ze względu na brak przyjętych
                result.add(new ErrorItem(transaction.getOriginalId(), SendingError.PREVIOUS_KDPW_VERSION_NOT_FOUND, ""));
            }
        }
        return new OngoingResults(result, new TransactionChanges(transaction.getOriginalId(), l1));
    }

    protected final ResultItem getTypeForCancelled(final Sendable registable) {
        //sprawdza, że nie ma żadnej wersji innej tej transakcji dla której Typ danych = ANULOWANA
        // i status przetworzenia jest WYSŁANA lub PRZYJĘTA
        Transaction transaction = registable.getTransaction();
        final Transaction oldTrans = transactionManager.findOtherVersion(transaction.getId(),
                transaction.getOriginalId(),
                transaction.getTransactionDate(),
                DataType.CANCELLED,
                ProcessingStatus.SENT,
                ProcessingStatus.CONFIRMED);
        if (Objects.isNull(oldTrans)) {
            return new TransactionToRepository(registable, TransactionMsgType.E);
        } else {
            // istnieje inna ANULOWANA o statusie WYSŁANA lub PRZYJĘTA
            return new UnsentItem(transaction.getOriginalId(), SendingError.CANCELLED_P_SENT_CONFIRMED, "", transaction);
        }
    }

    protected final int getBatchSize() {
        final Parameter batchParameter = parameterManager.get(ParameterKey.KDPW_BATCH_SIZE);
        if (Objects.nonNull(batchParameter)) {
            return Integer.parseInt(batchParameter.getValue());
        }
        throw new IllegalStateException("Cannot find parameter: KDPW_BATCH_SIZE");
    }

    private boolean hasValuationAndProtection(final Transaction transaction) {
        return Objects.nonNull(transaction.getValuation()) && !transaction.getValuation().isEmpty()
                && Objects.nonNull(transaction.getProtection()) && !transaction.getProtection().isEmpty();
    }

    private String getIdenAndVersionAndDate(Transaction transaction) {
        if (transaction.getExtractVersion() == null) {
            return "identyfikator: " + transaction.getTransactionDetails().getSourceTransId() + ", wersja: brak, data: " + DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT);
        }
        return "identyfikator: " + transaction.getTransactionDetails().getSourceTransId() + ", wersja: " + transaction.getExtractVersion() + ", data: " + DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT);
    }

    private String printTransaction(Transaction trans) {
        return String.format("[id = %s, originalId = %s, transactionDate = %s]",
                trans.getId(),
                trans.getOriginalId(),
                DateUtils.formatDate(trans.getTransactionDate(), DateUtils.DATE_FORMAT));
    }

    private Map<Long, ArrayList<TransactionToRepository>> getTransactionMap(final List<TransactionToRepository> messages) {
        final Map<Long, ArrayList<TransactionToRepository>> result = new HashMap<>();
        messages.stream().forEach((item) -> {
            if (result.containsKey(item.getRegistable().getId())) {
                result.get(item.getRegistable().getId()).add(item);
            } else {
                final ArrayList<TransactionToRepository> list = new ArrayList<>();
                list.add(item);
                result.put(item.getRegistable().getId(), list);
            }
        });
        LOGGER.debug("MAP size (list of transactions): {}", result.size());
        return result;
    }

    protected void logEvent(final MessageLog messageLog, final EventType eventType, final long size, final MessageType messageType) {
        final StringBuilder importDetails = new StringBuilder();
        importDetails.append(KdpwUtils.getMsgs("kdpw.event.message.generate.fileName")).append(" ");
        importDetails.append(messageLog.getFileId()).append("; ");
        importDetails.append(KdpwUtils.getMsgs("kdpw.event.message.generate.total")).append(" ");
        importDetails.append(size).append(";");

        eventLogManager.addEventTransactional(eventType,
                messageLog.getId(),
                importDetails.toString());
        final StringBuilder insertDetails = new StringBuilder();
        insertDetails.append(KdpwUtils.getMsgs("kdpw.event.message.insert")).append(" ");
        if (Objects.nonNull(messageType)) {
            insertDetails.append(KdpwUtils.getMsgs(messageType.getMsgKey())).append(";");
        } else {
            insertDetails.append(KdpwUtils.getMsgs("kdpw.event.message.unrecognized")).append(";");
        }
        eventLogManager.addEventTransactional(EventType.KDPW_MESSAGE_INSERT,
                messageLog.getId(),
                insertDetails.toString());
    }

    private EventType getEventType(final List<TransactionToRepository> messages) {
        final TransactionMsgType msgType = messages.get(0).getMsgType();
        if (TransactionMsgType.C.equals(msgType)) {
            return EventType.KDPW_TRANSACTION_CANCELLATION_MESSAGE_CREATE;
        } else {
            return EventType.KDPW_TRANSACTION_MESSAGE_CREATE;
        }
    }

    private boolean isKdpwProcessed(Transaction transaction) {
        Date terminationDate = transaction.getTransactionDetails().getTerminationDate();
        Date maturityDate = transaction.getTransactionDetails().getMaturityDate();
        if (terminationDate == null || maturityDate == null) {
            return false;
        }
        Calendar termCal = Calendar.getInstance();
        termCal.setTime(terminationDate);

        Calendar matuCal = Calendar.getInstance();
        matuCal.setTime(maturityDate);

        return termCal.getTimeInMillis() >= matuCal.getTimeInMillis();
    }

    private String getCurrentTime() {
        return DateUtils.formatDate(new Date(), DateUtils.DATE_FULL_TIME_FORMAT);
    }

    private void logCurrentTime(final String header) {
        LOGGER.info(String.format("%s | %s", header, getCurrentTime()));
    }

    /**
     * Sprawdzenie kompletności danych klienta.
     *
     * @param transaction wysyłana transakcja
     * @param resultList lista rezultatów wysyłki
     * @return true w sytuacji gdy klient jest kompletny, w przeciwnym przypadku false
     */
    private boolean checkClientCompleteness(final Sendable transaction, final List<ResultItem> resultList) {
        if (transaction.getClient() == null
                || !ValidationStatus.VALID.equals(transaction.getClient().getValidationStatus())) {
            resultList.add(new ErrorItem(transaction.getOriginalId(), SendingError.NO_CLIENT, ""));
            return false;
        }
        return true;
    }

    protected final List<ChangeRegister> valuationOrProtectionChanges(final Transaction oldTrans, final Transaction newTrans) {
        List<ChangeRegister> changes = new ArrayList<>();

        if (Objects.nonNull(oldTrans)) {
            changes.addAll(KdpwUtils.getChanges(oldTrans.getProtection(), newTrans.getProtection(), ProtectionChange.class));
            changes.addAll(KdpwUtils.getChanges(oldTrans.getValuation(), newTrans.getValuation(), ValuationChange.class));
        }

        return changes;
    }

    protected final List<ChangeRegister> transactionChanges(final Transaction oldTrans, final Transaction transaction) {
        List<ChangeRegister> changes = new ArrayList<>();

        if (Objects.nonNull(oldTrans)) {
            changes.addAll(KdpwUtils.getChanges(oldTrans, transaction, TransactionDataChange.class));
        }

        return changes;
    }

    protected final Date getLastModifyDate(final Long clientId) {
        final EventLog eventLog = eventLogManager.getNewesttByEventType(clientId, EventType.CLIENT_MODIFICATION);
        if (Objects.isNull(eventLog)) {
            return null;
        }
        return eventLog.getDate();
    }

    private String getReportingInstitutionId() {
        return parameterManager.getValue(ParameterKey.INSTITUTION_ID);
    }

}
