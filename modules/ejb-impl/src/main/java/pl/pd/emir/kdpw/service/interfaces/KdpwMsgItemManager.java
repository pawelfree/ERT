package pl.pd.emir.kdpw.service.interfaces;

import java.util.List;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.manager.GenericManager;

public interface KdpwMsgItemManager extends GenericManager<KdpwMsgItem> {

    List<KdpwMsgItem> findNonProcessed(Long extractId, MessageType... types);

    KdpwMsgItem findKdpwMsgItem(String rltdRef);

    List<KdpwMsgItem> getNewest(Long extractId, MessageType... types);

    List<KdpwMsgItem> getNewestRejected(Long extractId, MessageType... types);

    KdpwMsgItem update(KdpwMsgItem item);

    @Override
    KdpwMsgItem save(KdpwMsgItem item);
}
