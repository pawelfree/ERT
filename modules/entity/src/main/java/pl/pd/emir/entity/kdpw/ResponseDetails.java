package pl.pd.emir.entity.kdpw;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ResponseDetails implements Serializable {

    @Column(name = "MSG_STATUS")
    private String msgStatus;

    @Column(name = "MSG_STATUS_CODE")
    private String statusCode;

    @Column(name = "MSG_STATUS_DESC")
    private String statusDesc;

    @Column(name = "RLTD_REQ_REF", length = 16)
    private String rltdRef;

    @Column(name = "RELATED_MSG_LOG_ID")
    private Long refMsgLogId;

    public String getMsgStatus() {
        return msgStatus;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public String getRltdRef() {
        return rltdRef;
    }

    public Long getRefMsgLogId() {
        return refMsgLogId;
    }

    protected ResponseDetails() {
        super();
    }

    protected ResponseDetails(final String msgStatus, final String code, final String description,
            final String rltdRef, final Long refMsgLogId) {
        this.msgStatus = msgStatus;
        this.statusCode = code;
        this.statusDesc = description;
        this.rltdRef = rltdRef;
        this.refMsgLogId = refMsgLogId;
    }
}
