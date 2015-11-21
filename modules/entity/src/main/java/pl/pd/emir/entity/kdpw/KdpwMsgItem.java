package pl.pd.emir.entity.kdpw;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.enums.TransactionMsgType;

@Entity
@Table(name = "KDPW_MSG_ITEM")
@NamedQueries({
    @NamedQuery(name = "KdpwMsgItem.findBySndrMsgRef",
            query = "SELECT t FROM KdpwMsgItem t WHERE t.sndrMsgRef = :sndrMsgRef"),
    @NamedQuery(name = "KdpwMsgItem.findByTransactionId",
            query = "SELECT t FROM KdpwMsgItem t WHERE t.extractId = :entityId"
            + " AND (t.messageLog.messageType = pl.pd.emir.entity.kdpw.MessageType.TRANSACTION"
            + " OR t.messageLog.messageType = pl.pd.emir.entity.kdpw.MessageType.TRANSACTION_RESPONSE)"
            + " ORDER BY t.messageLog.messageTime ASC"),
    @NamedQuery(name = "KdpwMsgItem.findNonProcessed",
            query = "SELECT t FROM KdpwMsgItem t WHERE t.extractId = :extractId"
            + " AND t.messageLog.messageType IN :msgTypes"
            + " AND t.processTime IS NULL"),
    @NamedQuery(name = "KdpwMsgItem.getNewest",
            query = "SELECT t FROM KdpwMsgItem t WHERE t.extractId = :extractId"
            + " AND t.messageLog.messageType IN :msgTypes"
            + " AND t.processTime = (SELECT MAX(p.processTime) FROM KdpwMsgItem p WHERE p.extractId = :extractId)"),
    @NamedQuery(name = "KdpwMsgItem.getNewestRejectedForCancellation",
            query = "SELECT t FROM KdpwMsgItem t WHERE "
            + " t.extractId = :extractId"
            + " AND t.messageLog.messageType IN :msgTypes"
            + " AND t.status = pl.pd.emir.entity.kdpw.MessageStatus.REJECTED"
            + " AND t.requestDetails.transMsgType = pl.pd.emir.enums.TransactionMsgType.E"
            + " AND t.processTime = (SELECT MAX(p.processTime) FROM KdpwMsgItem p WHERE p.extractId = :extractId)")
})
public class KdpwMsgItem implements Identifiable<Long> {

    private static final long serialVersionUID = -814195162141416045L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "kdpw_msg_item_seq_gen")
    @SequenceGenerator(name = "kdpw_msg_item_seq_gen", sequenceName = "SQ_EMIR_KDPW_MSG_ITEM", allocationSize = 100)
    private Long id;

    /**
     * Wartość pola: SndrMsgRef. Id komunikatu.
     */
    @Column(name = "SNDR_MSG_REF", length = 16)
    private String sndrMsgRef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MESSAGE_LOG_ID")
    private MessageLog messageLog;

    @Column(name = "EXTRACT_ID")
    private Long extractId;

    @Embedded
    private RequestDetails requestDetails;

    @Embedded
    private ResponseDetails responseDetails;

    /**
     * Data i czas określający kiedy komunikat miał wpływ na status transakcji.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROCESS_TIME")
    private Date processTime;

    /**
     * Komunikat z odopowiedzia - TYLKO dla ZAPYTAŃ.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private KdpwMsgItem response;

    @Enumerated(EnumType.STRING)
    @Column(name = "ITEM_STATUS")
    private MessageStatus status;

    @Column(name = "TRANSACTION_IDEN")
    private String transactionId;

    @Column(name = "CLIENT_IDEN")
    private String clientId;

    @ManyToOne
    @JoinColumn(name = "BANK_ID", nullable = true)
    private Bank bank;

    protected KdpwMsgItem() {
        super();
    }

    @Override
    public void initFields() {
        // nothing
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

    public MessageLog getMessageLog() {
        return messageLog;
    }

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public ResponseDetails getResponseDetails() {
        return responseDetails;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public boolean hasResponse() {
        return response != null;
    }

    public void addResponse(final KdpwMsgItem response) {
        this.response = response;
        if (MessageStatus.ACCEPTED.equals(response.getStatus())) {
            this.status = MessageStatus.ACCEPTED;
        } else {
            this.status = MessageStatus.REJECTED;
        }
    }

    public static KdpwMsgItem getResponse(final String msgId, final Long extractId,
            final String status,
            final String statusCode, final String stausDesc,
            final String rltdRef,
            final Long relatedMsgLogId,
            final String transactionId, final String clientId) {
        final KdpwMsgItem result = new KdpwMsgItem();
        result.responseDetails = new ResponseDetails(status, statusCode, stausDesc, rltdRef, relatedMsgLogId);
        result.sndrMsgRef = msgId;
        result.extractId = extractId;
        if (Constants.isStatusOk(status)) {
            result.status = MessageStatus.ACCEPTED;
        } else {
            result.status = MessageStatus.REJECTED;
        }
        result.transactionId = transactionId;
        result.clientId = clientId;
        return result;
    }

    public static KdpwMsgItem getRequest(final Long extractId, final String msgId) {
        final KdpwMsgItem result = new KdpwMsgItem();
        result.requestDetails = new RequestDetails();
        result.sndrMsgRef = msgId;
        result.extractId = extractId;
        result.status = MessageStatus.GENERATED;
        return result;
    }

    public static KdpwMsgItem getRequest(final Long extractId, final String msgId,
            final TransactionMsgType msgType,
            final Boolean cancelMutation,
            final String transactionId, final String clientId) {
        final KdpwMsgItem result = new KdpwMsgItem();
        result.requestDetails = new RequestDetails(msgType, cancelMutation);
        result.sndrMsgRef = msgId;
        result.extractId = extractId;
        result.status = MessageStatus.GENERATED;
        result.transactionId = transactionId;
        result.clientId = clientId;
        return result;
    }

    public boolean isAccepted() {
        return MessageStatus.ACCEPTED.equals(status);
    }

    public boolean isRejected() {
        return MessageStatus.REJECTED.equals(status);
    }

    public Long getExtractId() {
        return extractId;
    }

    public void addProcessingTime(final Date date) {
        this.processTime = date;
        if (Objects.nonNull(response)) {
            response.addProcessingTime(date);
        }
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void assignToLog(final MessageLog value) {
        this.messageLog = value;
    }

    public boolean isConnected() {
        return Objects.nonNull(extractId);
    }

    public String getClientId() {
        return clientId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

}
