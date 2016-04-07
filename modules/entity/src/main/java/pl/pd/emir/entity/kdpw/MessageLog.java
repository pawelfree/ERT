package pl.pd.emir.entity.kdpw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.EventType;

@Entity
@Table(name = "MESSAGE_LOG")
@NamedQueries({
    @NamedQuery(name = "MessageLog.markAsReferenced",
            query = "UPDATE MessageLog t SET t.hasResponse = TRUE WHERE t.id IN :ids"),
    @NamedQuery(name = "MessageLog.findNewstByMessageType",
            query = "SELECT m from MessageLog m left join fetch m.contentItems"
            + " WHERE m.messageType = :messageType ORDER BY m.messageTime DESC"),
    @NamedQuery(name = "MessageLog.findByFileStatus",
            query = "SELECT m from MessageLog m WHERE m.fileStatus = :fileStatus ORDER BY m.messageTime DESC")
})
public class MessageLog implements Logable<Long>, Selectable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "msg_log_seq_gen")
    @SequenceGenerator(name = "msg_log_seq_gen", sequenceName = "SQ_EMIR_MESSAGE_LOG", allocationSize = 1)
    private Long id;

    /**
     * Data i czas rejestracji komunikatu.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MESSGE_TIME", nullable = true)
    private Date messageTime;

    /**
     * Typ komunikatu.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "MESSGE_TYPE")
    private MessageType messageType;

    /**
     * Użytkownik, który wygenerował/wczytał komunikat.
     */
    @Column(name = "USER_LOGIN")
    private String userLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION")
    private MessageDirection direction;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "TRANSPORT_FORM")
    private String transportForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "FILE_STATUS")
    private FileStatus fileStatus;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "messageLog", fetch = FetchType.LAZY) //, orphanRemoval = true)
    private final List<KdpwMsgItem> contentItems = new ArrayList<>();

    @Column(name = "HAS_RESPONSE")
    private Boolean hasResponse = Boolean.FALSE;

    @Column(name = "FILE_ID")
    private String fileId;

    @Column(name = "BATCH_NUMBER")
    private Integer batchNumber;

    @Column(name = "INFO")
    private String info;
    
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "CHANGE_LOG")
    @Lob
    private String changeLog;

    @Transient
    private transient boolean selected;

    protected MessageLog() {
        super();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public void initFields() {
    }

    @Override
    public EventType getDeleteEventType() {
        return null;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.KDPW_MESSAGE_INSERT;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.KDPW_MESSAGE_INSERT;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void responseFounded() {
        this.hasResponse = Boolean.TRUE;
    }
    
    public String getChangeLog() {
        return changeLog;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isInfo() {
        return StringUtil.isNotEmpty(this.info);
    }

    //TODO do it better
    public String newMsg() {
        //only new messages
        return isInfo() ? info.substring(info.indexOf("N:") + 2, info.indexOf(":V:")) : "0";
    }

    public String newMsgRepo() {
        return newMsg();
    }

    public String valMsg() {
        //only valuations for both parties
        long val = isInfo() ? Long.valueOf(info.substring(info.indexOf(":V:") + 3, info.indexOf(":VC:"))) : 0;
        long valc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":VC:") + 4, info.indexOf(":M:"))) : 0;
        return String.valueOf(val + valc);
    }

    public String valMsgRepo() {
        //only valuations for both parties
        long val = isInfo() ? Long.valueOf(info.substring(info.indexOf(":V:") + 3, info.indexOf(":VC:"))) : 0;
        long valc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":VC:") + 4, info.indexOf(":M:"))) : 0;
        return String.valueOf(val + valc * 2);
    }

    public String modMsg() {
        //only modifications for both parties
        long mod = isInfo() ? Long.valueOf(info.substring(info.indexOf(":M:") + 3, info.indexOf(":MC:"))) : 0;
        long modc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":MC:") + 4, info.indexOf(":T:"))) : 0;
        return String.valueOf(mod + modc);
    }

    public String modMsgRepo() {
        //only modifications for both parties
        long mod = isInfo() ? Long.valueOf(info.substring(info.indexOf(":M:") + 3, info.indexOf(":MC:"))) : 0;
        long modc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":MC:") + 4, info.indexOf(":T:"))) : 0;
        return String.valueOf(mod + modc * 2);
    }

    public String totalMsg() {
        return isInfo() ? info.substring(info.indexOf(":T:") + 3) : "0";
    }

    public String totalMsgRepo() {
        long modc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":MC:") + 4, info.indexOf(":T:"))) : 0;
        long valc = isInfo() ? Long.valueOf(info.substring(info.indexOf(":VC:") + 4, info.indexOf(":M:"))) : 0;
        long tot  = isInfo() ? Long.valueOf(info.substring(info.indexOf(":T:") + 3)) : 0;
        return String.valueOf(tot + modc + valc);
    }

    public static class Builder {

        private Builder() {
            super();
        }

        public static MessageLog getOutput(final String fileIden, final MessageType messageType,
                final String userLogin, final String transportForm,
                final Integer batchNumber,
                final String info,
                final String changeLog
            ) {
            final MessageLog result = getOutput(fileIden, messageType, userLogin, transportForm, batchNumber);
            result.info = info;
            result.changeLog = changeLog;
            return result;
        }

        public static MessageLog getOutput(final String fileIden, final MessageType messageType,
                final String userLogin, final String transportForm,
                final Integer batchNumber) {
            final MessageLog result = new MessageLog();
            result.direction = MessageDirection.OUTPUT;
            result.fileId = fileIden;
            result.hasResponse = Boolean.FALSE;
            result.fileStatus = FileStatus.G_GENERATED;
            result.messageTime = new Date();
            result.messageType = messageType;
            result.transportForm = transportForm;
            result.userLogin = userLogin;
            result.batchNumber = batchNumber;
            return result;
        }

        public static MessageLog getInput(final MessageType messageType, final FileStatus fileStatus,
                final String userLogin, final String transportForm,
                final String fileName) {
            final MessageLog result = new MessageLog();
            result.direction = MessageDirection.INPUT;
            result.hasResponse = Boolean.FALSE;
            result.fileStatus = fileStatus;
            result.messageTime = new Date();
            result.messageType = messageType;
            result.transportForm = transportForm;
            result.userLogin = userLogin;
            result.fileId = fileName;
            result.info = "";
            result.changeLog = "";
            return result;
        }
    }

    // gettters
    public Date getMessageTime() {
        return messageTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public final String getMessageName() {
        final StringBuilder result = new StringBuilder();
        if (Objects.nonNull(messageType)) {
            result.append(messageType.getMsgName());
        }
        if (null != batchNumber && batchNumber > 0) {
            result.append("-").append(batchNumber.toString());
        }
        return result.toString();
    }

    public String getUserLogin() {
        return userLogin;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public String getTransportForm() {
        return transportForm;
    }

    public FileStatus getFileStatus() {
        return fileStatus;
    }

    public Boolean getHasResponse() {
        return hasResponse;
    }

    public String getFileId() {
        return fileId;
    }

    public List<KdpwMsgItem> getContentItems() {
        return contentItems;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    //setters
    public void setFileStatus(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }

}
