package pl.pd.emir.kdpw.service;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.kdpw.api.MessageLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Local(MessageLogManager.class)
public class MessageLogManagerImpl extends AbstractManagerTemplate<MessageLog> implements MessageLogManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogManagerImpl.class);

    public MessageLogManagerImpl() {
        super(MessageLog.class);
    }

    @Override
    public final MessageLog findNewestByMsgType(final MessageType messageType) {
        MessageLog result = null;
        try {
            result = (MessageLog) getEntityManager().createNamedQuery("MessageLog.findNewstByMessageType")
                    .setParameter("messageType", messageType).setMaxResults(1).getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.info("Cannot find message log with type: {}", messageType);
        }
        return result;
    }

    @Override
    public final List<KdpwMsgItem> findTransactionLog(final Long entityId) {
        return getEntityManager().createNamedQuery("KdpwMsgItem.findByTransactionId")
                .setParameter("entityId", entityId)
                .getResultList();
    }

    @Override
    public List<MessageLog> findByStatus(FileStatus fileStatus) {
        return getEntityManager().createNamedQuery("MessageLog.findNewstByMessageType")
                    .setParameter("fileStatus", fileStatus).getResultList();
    }
}
