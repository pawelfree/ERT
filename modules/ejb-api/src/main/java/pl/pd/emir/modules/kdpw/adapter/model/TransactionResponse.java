package pl.pd.emir.modules.kdpw.adapter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransactionResponse extends ResponseItem {

    private final List<TransactionLink> links = new ArrayList<>();

    private Date terminationDate;

    public List<TransactionLink> getLinks() {
        return Collections.unmodifiableList(links);
    }

    public void addLink(TransactionLink link) {
        links.add(link);
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

}
