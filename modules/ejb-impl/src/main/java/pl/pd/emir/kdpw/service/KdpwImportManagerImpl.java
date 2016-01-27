package pl.pd.emir.kdpw.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.kdpw.api.KdpwImportManager;
import pl.pd.emir.kdpw.api.MessageLogManager;
import pl.pd.emir.kdpw.api.to.ImportResult;
import pl.pd.emir.kdpw.service.utils.KdpwUtils;
import pl.pd.emir.modules.kdpw.adapter.model.RepositoryResponse;
import pl.pd.emir.modules.kdpw.adapter.model.ResponseItem;
import pl.pd.emir.register.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.admin.EventLogManager;
import pl.pd.emir.kdpw.xml.parser.XmlReader;

@Stateless
@Local(KdpwImportManager.class)
public class KdpwImportManagerImpl implements KdpwImportManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KdpwImportManagerImpl.class);

    @EJB
    private transient KdpwImportManager thisManager;

    @EJB
    private transient KdpwMsgItemManager msgItemManager;

    @EJB
    private transient EventLogManager eventLogManager;

    @EJB
    private transient TransactionManager transManager;

    @EJB
    private transient UserManager userManager;

    @EJB
    private transient MessageLogManager messageLogManager;

    @EJB
    private transient XmlReader messageReader;

    protected XmlReader getMessageReader() {
        return messageReader;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public final void importFile(final InputStream stream, final String fileName) {

        LOGGER.info("Import file: " + fileName);
        final String userLogin = userManager.getCurrentUserLogin();
        try {
            thisManager.processFile(fileName, StringUtil.readStream(stream), userLogin);
        } catch (IOException e) {
            LOGGER.info("Unpack error: " + e);
            final ImportResult result = new ImportResult(fileName, userLogin);
            result.responseError();
            thisManager.logError(result);
        }
    }

    /**
     * Przetworzenie pliku z komunikatem zwrotnym dla komunikatu z danymi transakcji.
     *
     */
    protected final void processTransactions(final List<ResponseItem> list, final ImportResult result) {
        LOGGER.info("Processing {} transactions", list.size());
        for (ResponseItem responseItem : list) {
            LOGGER.info("Processing msg with msgId = {}", responseItem.getSndrMsgRef());
            if (responseItem.getSndrMsgRef().startsWith(Constants.TR_C)) {
                throw new NotImplementedException("TR_C not implemented");
            } else {
                KdpwMsgItem requestItem = null;
                if (StringUtil.isNotEmpty(responseItem.getPrvsSndrMsgRef())) {
                    requestItem = msgItemManager.findKdpwMsgItem(responseItem.getPrvsSndrMsgRef());
                }

                // NIE znaleziono komunikatu lub komunikat odpowiedzi na transakcje nie dotyczy komunikatu z transakcja
                if (null == requestItem || !MessageType.TRANSACTION.equals(requestItem.getMessageLog().getMessageType())) {
                    LOGGER.info("Nie można powiązać komunikatu");
                    final KdpwMsgItem response = KdpwMsgItem.getResponse(responseItem.getSndrMsgRef(),
                            null,
                            responseItem.getStatusCode(),
                            responseItem.getReasonCode(),
                            responseItem.getReasonText(),
                            responseItem.getPrvsSndrMsgRef(),
                            null,
                            null,
                            null,
                            null);
                    // 1 - zapis odpowiedzi
                    result.addItem(msgItemManager.save(response));
                } else {
                    LOGGER.info("Można powiązać komunikat");
                    final KdpwMsgItem response = KdpwMsgItem.getResponse(responseItem.getSndrMsgRef(),
                            requestItem.getExtractId(),
                            responseItem.getStatusCode(),
                            responseItem.getReasonCode(),
                            responseItem.getReasonText(),
                            responseItem.getPrvsSndrMsgRef(),
                            requestItem.getMessageLog().getId(),
                            requestItem.getTransactionId(),
                            requestItem.getClientId(),
                            requestItem.getClientId2());

                    requestItem.getMessageLog().responseFounded();
                    messageLogManager.save(requestItem.getMessageLog());

                    requestItem.addResponse(response); // 2 - powiazanie miedzy itemami
                    LOGGER.info("Response status : " + requestItem.getStatus().getMsgKey());
                    processTransaction(requestItem, response); // 3 - powiazanie z transakcja
                    result.addItem(response);
                }
            }
        }
        saveMessageLog(result, MessageType.TRANSACTION_RESPONSE);
    }

    private void processTransaction(final KdpwMsgItem requestItem, final KdpwMsgItem responseItem) {
        Transaction transaction = transManager.getById(requestItem.getExtractId());
        if (TransactionMsgType.E.equals(requestItem.getRequestDetails().getTransMsgType())) {
            // Komunikat  dotyczy ANULOWANIA
            processKdpwCancel(transaction, requestItem, responseItem);
        } else if (!TransactionMsgType.O.equals(requestItem.getRequestDetails().getTransMsgType())
                && ProcessingStatus.SENT.equals(transaction.getProcessingStatus())) {
            // Status transakcji powiazanej z komunikatem jest = WYSŁANA
            LOGGER.info("Processing sent transaction");
            processKdpwResponse(transaction, requestItem, responseItem);
        } else {
            LOGGER.info("Komunikat dotyczący transakcji nie został obsłużony !!!");
            LOGGER.info("RequestItem - id : {}", requestItem.getId());
            LOGGER.info("RequestItem - transaction id : {}", requestItem.getTransactionId());
            LOGGER.info("RequestItem - extract id : {}", requestItem.getExtractId());
            LOGGER.info("Original transaction : " + transaction.getOriginalId() + ", transaction id : " + transaction.getId());
            //TODO
            /**
             * czy na pewno zostawiać pusty czas może to dotyczyć np komunikatu wyceny za BTMU i za klienta przychodzący
             * komunikat za BTMU zmienił status oryginalnego komunikatu/trnasakcji a przychodzący komunikat za klienta
             * nie może już zmienić statusu tej transakcji bo nie jest ona w statusie SENT
             *
             */

//            addProcessTime(new ArrayList<KdpwMsgItem>(), requestItem);
        }
        //  // 7.3.4.4.8. Komunikat odpowiedzi dotyczy zmiany danych instytucji - nie zmieniaj statusu tranakcji
    }

    protected void processKdpwResponse(final Transaction transaction, final KdpwMsgItem requestItem, final KdpwMsgItem responseItem) {
        //Znajdz wszystkie wysłane i nie przetworzone zwrotnie komunikaty - processing time is null
        final List<KdpwMsgItem> nonProcessed = msgItemManager.findNonProcessed(transaction.getId(), MessageType.TRANSACTION);
        LOGGER.info("processKdpwResponse | responseItem.isAccepted: {}", responseItem.isAccepted());
        LOGGER.info("non processed size : {}", nonProcessed.size());
        LOGGER.info("all with response : {}", KdpwUtils.allWithResponse(nonProcessed));
        if (CollectionsUtils.isEmpty(nonProcessed)) {
            LOGGER.info("Brak nieprzetworzonych komunikatow");
            //Nie ma innych komunikatów zapytania powiazanych z ta transakcja NIEOBSLUZONYCH
            if (responseItem.isAccepted()) {
                //status transakcji = PRZYJETA
                transaction.confirm();
            } else {
                //status transakcji = ODRZUCONA
                transaction.reject();
            }
            addProcessTime(nonProcessed, requestItem);
        } else if (KdpwUtils.allWithResponse(nonProcessed)) {
            LOGGER.info("Przynajmniej jeden nieprzetworzony komunikat, ale każdy z odpowiedzią");
            if (KdpwUtils.allAccepted(nonProcessed)) {
                // wszystkie w statusie PRZYJETA
                if (responseItem.isAccepted()) {
                    //status transakcji = PRZYJETA
                    transaction.confirm();
                } else {
                    //status transakcji = ODRZUCONA CZESCIOWO
                    transaction.partlyReject();
                }
                addProcessTime(nonProcessed, requestItem);
            } else if (KdpwUtils.allRejected(nonProcessed)) {
                // wszystkie odrzucone
                if (responseItem.isRejected()) {
                    transaction.reject();
                } else {
                    //status transakcji = ODRZUCONA CZESCIOWO
                    transaction.partlyReject();
                }
            } else {
                // przynajmniej jeden ODRZUCONY
                // status = ODRZUCONA CZESCIOWO
                transaction.partlyReject();
            }
            addProcessTime(nonProcessed, requestItem);
        } else //else // brak zmiany statusu tranakcji
        {
            LOGGER.info("Jest wiele wygenerowanych komunikatow dla transakcji. Przetworzę wszystkie.");
            LOGGER.info("komunikaty bez odpowiedzi : " + nonProcessed.size());

            nonProcessed.stream().forEach((item) -> {
                LOGGER.info("KDPW_MSG_ITEM id  " + item.getId() + " status : " + item.getStatus());
            });

            addProcessTime(nonProcessed, requestItem);

        }
        LOGGER.info("processKdpwResponse | transaction.status = {}", transaction.getProcessingStatus());
    }

    private void processKdpwCancel(final Transaction transaction, final KdpwMsgItem requestItem, final KdpwMsgItem responseItem) {
        LOGGER.info("-- processKdpwCancel --");
        if (requestItem.getRequestDetails().getCancelMutation()) { // anulowanie MUTACJI
            // status tranakcji powiazanej = WYSŁANE ANULOWANIE
            if (ProcessingStatus.CANCELLATION_SENT.equals(transaction.getProcessingStatus())) {
                final List<KdpwMsgItem> nonProcessed = msgItemManager.findNonProcessed(transaction.getId(), MessageType.TRANSACTION);
                LOGGER.info("Nonprocessed transactions: {}", nonProcessed.size());
                if (CollectionsUtils.isEmpty(nonProcessed)) {
                    LOGGER.info("Nonprocessed not found.");
                    // nie ma innych komunikatów nieobsłużonych - tylko jeden komunikat anulowania
                    if (responseItem.isAccepted()) {
                        // Transakcja ANULOWANA jeśli komunikat jest PRZYJETY
                        LOGGER.info("transaction.cancel");
                        transaction.cancel();
                    } else {
                        // Transakcja PRZYJETA, jeśli komunikat jest ODRZUCONY
                        LOGGER.info("transaction.confirm");
                        transaction.confirm();
                    }
                    // powiazanie z komunikatem dla transaction --> jest juz przy wysyłce
                } else if (KdpwUtils.allWithResponse(nonProcessed)) { // sa nieobsłużone komunikaty
                    LOGGER.info("All nonprocesed with response");
                    if (KdpwUtils.allAccepted(nonProcessed) && responseItem.isAccepted()) {
                        // wszystkie komunikaty = PRZYJETY -> status = ANULOWANY
                        transaction.cancel();
                        LOGGER.info("transaction.cancel");
                        // wprowadz date i czas zmiany statusu tranakcji
                        addProcessTime(nonProcessed, requestItem);
                    } else if (KdpwUtils.allRejected(nonProcessed) && responseItem.isRejected()) {
                        // wszystkie komunikaty = ODRZUCONY -> status = PRZYJETY
                        transaction.confirm();
                        LOGGER.info("transaction.confirm");
                        // wprowadz date i czas zmiany statusu tranakcji
                        addProcessTime(nonProcessed, requestItem);
                    } else {
                        // przynajmniej jeden odrzucony -> status = ANULOWANY CZESCIOWO
                        LOGGER.info("transaction.partlyCanceled");
                        transaction.partlyCanceled();
                        // wprowadz date i czas zmiany statusu tranakcji
                        // nie oznaczaj jako przetworzone komunikatów o statusie przyjety
                        addProcessTime(KdpwUtils.getRejected(nonProcessed), requestItem);
                    }
                    // powiazanie z komunikatem dla transaction --> jest juz przy wysyłce
                }
                // else: dal dowlonego komunikatu nie ma odpowiedzi status przetworzenia: BEZ ZMIAN
            }
        } else { // anulowanie CAŁEJ TRANSAKCJI
            if (responseItem.isAccepted()) {
                final List<Transaction> transactions = transManager.getByOriginalId(transaction.getOriginalId());
                transactions.stream().forEach((trans) -> {
                    // status transakcji i mutacji o danym OriginalId = ANULOWANA
                    trans.cancel();
                });
            } else {
                // status transakcji = PRZYJETA (zmiana ze statusu: wysłana)
                transaction.confirm();
                // powiazanie z komunikatem dla transaction --> jest juz przy wysyłce
            }
            requestItem.addProcessingTime(new Date());
        }
    }

    protected final MessageLog saveMessageLog(final ImportResult importResult, final MessageType messageType) {
        final MessageLog messageLog = messageLogManager.save(MessageLog.Builder.getInput(messageType,
                getFileStatus(importResult),
                importResult.getUserName(),
                importResult.getFileAsString(),
                importResult.getFileName()));
        LOGGER.info("Message log saved. Id = {}", messageLog.getId());
        LOGGER.info("Assign items to log: {}", importResult.getItems().size());
        importResult.getItems().stream().forEach((kdpwMsgItem) -> {
            kdpwMsgItem.assignToLog(messageLog);
        });

        logEvent(importResult, messageLog, messageType);
        return messageLog;
    }

    private void addProcessTime(final List<KdpwMsgItem> nonProcessed, final KdpwMsgItem requestItem) {
        final Date proessTime = new Date();
        nonProcessed.stream().forEach((item) -> {
            item.addProcessingTime(proessTime);
        });
        requestItem.addProcessingTime(proessTime);
    }

    private FileStatus getFileStatus(final ImportResult importResult) {
        FileStatus result = null;
        final List<KdpwMsgItem> items = importResult.getItems();
        if (KdpwUtils.allAccepted(items)) {
            result = FileStatus.F_ACCEPTED;
        } else if (KdpwUtils.allRejected(items)) {
            result = FileStatus.E_REJECTED;
        } else {
            LOGGER.warn("Unexpected message item status: {}", KdpwUtils.getAllStatuses(items));
            //status CZĘŚCIOWO_PRZYJĘTY został usnięty
            //result = FileStatus.C_PARTLY_ACCEPTED;
        }
        if (KdpwUtils.allDisconnected(items)) {
            result = FileStatus.D_DISCONNECTED;
        }
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void logError(final ImportResult result) {
        final MessageLog messageLog = messageLogManager.save(MessageLog.Builder.getInput(null,
                result.getFileStatus(),
                result.getUserName(),
                result.getFileAsString(),
                result.getFileName()));

        logEvent(result, messageLog, null);
    }

    protected void logEvent(final ImportResult importResult, final MessageLog messageLog, final MessageType messageType) {
        final StringBuilder importDetails = new StringBuilder();
        importDetails.append(KdpwUtils.getMsgs("kdpw.event.message.import.fileName")).append(" ");
        importDetails.append(importResult.getFileName()).append("; ");
        importDetails.append(KdpwUtils.getMsgs("kdpw.event.message.import.fileStatus")).append(" ");
        importDetails.append(KdpwUtils.getMsgs(messageLog.getFileStatus().getMsgKey())).append(";");

        eventLogManager.addEventTransactional(EventType.KDPW_MESSAGE_IMPORT,
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

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void processFile(String fileName, String fileAsString, String userLogin) {
        //TODO PAWEL constructor
        final ImportResult result = new ImportResult(fileName, userLogin);
        result.setFileAsString(fileAsString);

        RepositoryResponse response = getMessageReader().read(fileAsString);

        if (response.isTransaction()) {
            LOGGER.info("Processing transactions from file : " + fileName);
            processTransactions(response.getList(), result);
        } else {
            throw new IllegalArgumentException("Invalid message document type ! (maybe dataset)");
        }
    }
}
