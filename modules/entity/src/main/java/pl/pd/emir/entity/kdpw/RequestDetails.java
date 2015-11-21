package pl.pd.emir.entity.kdpw;

import java.io.Serializable;
import javax.persistence.Embeddable;
import pl.pd.emir.enums.TransactionMsgType;

@Embeddable
public class RequestDetails implements Serializable {

    private TransactionMsgType transMsgType;

    private Boolean cancelMutation;

    protected RequestDetails() {
        super();
    }

    protected RequestDetails(final TransactionMsgType transMsgType, final Boolean cancelMutation) {
        this.transMsgType = transMsgType;
        this.cancelMutation = cancelMutation;
    }

    public TransactionMsgType getTransMsgType() {
        return transMsgType;
    }

    public Boolean getCancelMutation() {
        return cancelMutation;
    }
}
