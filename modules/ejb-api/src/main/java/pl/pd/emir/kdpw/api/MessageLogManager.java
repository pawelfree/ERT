package pl.pd.emir.kdpw.api;

import java.util.List;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.manager.GenericManager;

public interface MessageLogManager extends GenericManager<MessageLog> {

    MessageLog findNewestByMsgType(MessageType messageType);

    List<KdpwMsgItem> findTransactionLog(Long entityId);

    List<MessageLog> findByStatus(FileStatus fileStatus);

}
