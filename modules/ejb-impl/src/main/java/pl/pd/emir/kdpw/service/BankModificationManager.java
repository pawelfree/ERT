package pl.pd.emir.kdpw.service;

import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.pd.emir.admin.MultiNumberGenerator;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageStatus;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.kdpw.api.MessageLogManager;
import pl.pd.emir.kdpw.api.exception.KdpwServiceException;
import pl.pd.emir.kdpw.service.interfaces.KdpwMsgItemManager;
import pl.pd.emir.kdpw.service.utils.KdpwUtils;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.kdpw.xml.parser.XmlBankChangeWriter;
import pl.pd.emir.modules.kdpw.adapter.model.BankWriterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.admin.EventLogManager;

@Stateless
public class BankModificationManager  {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankModificationManager.class);

    @EJB
    protected transient MessageLogManager logManager;

    @EJB
    protected transient MultiNumberGenerator numberGenerator;

    @EJB
    protected transient EventLogManager eventLogManager;

    @EJB
    protected transient KdpwMsgItemManager msgItemManager;

    @EJB
    protected transient UserManager userManager;

    @EJB
    protected transient XmlBankChangeWriter bankChangeWriter;

    protected XmlBankChangeWriter getWriter() {
        return bankChangeWriter;
    }

    public boolean generateMessage(final Bank bank) {
        try {
            return saveMessageLog(getWriter().write(bank));
        } catch (XmlParseException ex) {
            LOGGER.error("XML writing error: " + ex);
            throw new KdpwServiceException(ex);
        }
    }

    protected boolean saveMessageLog(final BankWriterResult writerResult) {
        final MessageLog msgLog = MessageLog.Builder.getOutput(KdpwUtils.getMsgLogNumber(numberGenerator),
                MessageType.BANK,
                userManager.getCurrentUserLogin(),
                writerResult.getMessage(),
                null);
        boolean result = true;
        try {
            final MessageLog saved = logManager.save(msgLog);
            logEvent(msgLog, MessageType.BANK);
            final KdpwMsgItem request = KdpwMsgItem.getRequest(writerResult.getExtractId(),
                    writerResult.getMsgId());
            request.assignToLog(saved);
            msgItemManager.save(request);
        } catch (RuntimeException re) {
            LOGGER.error("Message log save error: " + re);
            result = false;
        }
        return result;
    }

    public boolean bankModificationPossible() {
        final EventLog event = eventLogManager.getNewesttByEventType(null, EventType.BANK_MODIFICATION);
        final MessageLog msgLog = logManager.findNewestByMsgType(MessageType.BANK);
        return (event != null && (msgLog == null || event.getDate().after(msgLog.getMessageTime())))
                || (Objects.nonNull(msgLog)
                && CollectionsUtils.isNotEmpty(msgLog.getContentItems())
                && MessageStatus.REJECTED.equals(msgLog.getContentItems().get(0).getStatus()));
    }

    protected void logEvent(final MessageLog messageLog, final MessageType messageType) {
        final StringBuilder importDetails = new StringBuilder();
        importDetails.append(KdpwUtils.getMsgs("kdpw.event.message.generate.fileName")).append(" ");
        importDetails.append(messageLog.getFileId()).append("; ");

        eventLogManager.addEventTransactional(EventType.KDPW_INSTITUTION_CHANGE_MESSAGE_CREATE,
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
}
