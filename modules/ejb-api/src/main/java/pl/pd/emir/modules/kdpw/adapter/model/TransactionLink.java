package pl.pd.emir.modules.kdpw.adapter.model;

import java.util.Date;

public class TransactionLink {

    private String tradeIdId;

    private String ctrPtyTRId;

    private Date eligDate;

    public String getTradeIdId() {
        return tradeIdId;
    }

    public void setTradeIdId(String tradeIdId) {
        this.tradeIdId = tradeIdId;
    }

    public String getCtrPtyTRId() {
        return ctrPtyTRId;
    }

    public void setCtrPtyTRId(String ctrPtyTRId) {
        this.ctrPtyTRId = ctrPtyTRId;
    }

    public Date getEligDate() {
        return eligDate;
    }

    public void setEligDate(Date eligDate) {
        this.eligDate = eligDate;
    }

}
