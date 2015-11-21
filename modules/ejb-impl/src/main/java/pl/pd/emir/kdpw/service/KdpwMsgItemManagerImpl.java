package pl.pd.emir.kdpw.service;

import java.util.Arrays;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.kdpw.service.interfaces.KdpwMsgItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Local(KdpwMsgItemManager.class)
public class KdpwMsgItemManagerImpl extends AbstractManagerTemplate<KdpwMsgItem> implements KdpwMsgItemManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KdpwMsgItemManagerImpl.class);

    public KdpwMsgItemManagerImpl() {
        super(KdpwMsgItem.class);
    }

    @Override
    public List<KdpwMsgItem> findNonProcessed(final Long extractId, final MessageType... types) {
        return getEntityManager().createNamedQuery("KdpwMsgItem.findNonProcessed")
                .setParameter("extractId", extractId)
                .setParameter("msgTypes", Arrays.asList(types))
                .getResultList();
    }

    @Override
    public final KdpwMsgItem findKdpwMsgItem(final String rltdRef) {
        KdpwMsgItem result = null;
        try {
            result = (KdpwMsgItem) getEntityManager().createNamedQuery("KdpwMsgItem.findBySndrMsgRef")
                    .setParameter("sndrMsgRef", rltdRef).getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.error("Cannot find KdpwMsgItem with messageId = {}", rltdRef);
        } catch (NonUniqueResultException nure) {
            LOGGER.error("Cannot find unique KdpwMsgItem with messageId = {}", rltdRef);
        }
        return result;
    }

    @Override
    public final List<KdpwMsgItem> getNewest(final Long extractId, final MessageType... types) {
        return getEntityManager().createNamedQuery("KdpwMsgItem.getNewest")
                .setParameter("extractId", extractId)
                .setParameter("msgTypes", Arrays.asList(types))
                .getResultList();
    }

    @Override
    public final List<KdpwMsgItem> getNewestRejected(final Long extractId, final MessageType... types) {
        return getEntityManager().createNamedQuery("KdpwMsgItem.getNewestRejectedForCancellation")
                .setParameter("extractId", extractId)
                .setParameter("msgTypes", Arrays.asList(types))
                .getResultList();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public KdpwMsgItem save(KdpwMsgItem item) {
        getEntityManager().persist(item);
        return item;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public KdpwMsgItem update(KdpwMsgItem item) {
        return super.save(item);
    }
}
