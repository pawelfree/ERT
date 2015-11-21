package pl.pd.emir.criteria;

import java.util.Date;
import java.util.Objects;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterLongTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.MessageType;

public class MessageLogSC extends AbstractSearchCriteria {

    private static final long serialVersionUID = -2393137018658954191L;

    private Date messageTimeFrom;

    private Date messageTimeTo;

    private String userLogin;

    private MessageType messageType;

    private FileStatus status;

    private String fileId;

    private Long relatedToOutputId;

    private Long relatedToInputId;

    @Override
    public void clear() {
        super.clear();
        userLogin = null;
        messageType = null;
        status = null;
        messageTimeFrom = null;
        messageTimeTo = null;
        fileId = null;
    }

    @Override
    public final void addFilters() {
        clearFilters();
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "userLogin", "like", userLogin));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "fileId", "like", fileId));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "messageType", "=", messageType));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "fileStatus", "=", status));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "messageTime", ">=", messageTimeFrom));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "messageTime", "<=", messageTimeTo));

        if (Objects.nonNull(relatedToOutputId)) {
            getFitrSort().getFilters().add(FilterLongTO.valueOf("", "contentItems.responseDetails.refMsgLogId", "=", relatedToOutputId));
        }
    }

    public final Date getMessageTimeFrom() {
        return messageTimeFrom;
    }

    public final void setMessageTimeFrom(final Date value) {
        this.messageTimeFrom = value;
    }

    public final Date getMessageTimeTo() {
        return messageTimeTo;
    }

    public final void setMessageTimeTo(final Date value) {
        this.messageTimeTo = value;
    }

    public final String getUserLogin() {
        return userLogin;
    }

    public final void setUserLogin(final String value) {
        this.userLogin = value;
    }

    public final MessageType getMessageType() {
        return messageType;
    }

    public final void setMessageType(final MessageType value) {
        this.messageType = value;
    }

    public final FileStatus getStatus() {
        return status;
    }

    public final void setStatus(final FileStatus value) {
        this.status = value;
    }

    public final String getFileId() {
        return fileId;
    }

    public final void setFileId(final String value) {
        this.fileId = value;
    }

    public final Long getRelatedToInputId() {
        return relatedToInputId;
    }

    public final void setRelatedToInputId(final Long value) {
        this.relatedToInputId = value;
    }

    public final Long getRelatedToOutputId() {
        return relatedToOutputId;
    }

    public final void setRelatedToOutputId(final Long value) {
        this.relatedToOutputId = value;
    }

    @Override
    public String toString() {
        return "MessageLogSC{" + "messageTimeFrom=" + messageTimeFrom + ", messageTimeTo=" + messageTimeTo
                + ", userLogin=" + userLogin + ", messageType=" + messageType + ", status=" + status + ", fileId="
                + fileId + ", relatedToOutputId=" + relatedToOutputId + ", relatedToInputId=" + relatedToInputId + '}';
    }

    public final MessageLogSC cloneWithoutRelated() {
        final MessageLogSC result = new MessageLogSC();
        result.setDistinct(isDistinct());
        result.setFileId(fileId);

        result.setMessageTimeFrom(messageTimeFrom);
        result.setMessageTimeTo(messageTimeTo);
        result.setMessageType(messageType);
        result.setStatus(status);
        result.setUserLogin(userLogin);
        return result;
    }
}
