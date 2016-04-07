package pl.pd.emir.kdpw.api;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PawelDudek
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "changestotransactions")
public class ChangesToTransactions {

    @XmlElement(name = "transactionchanges", type = TransactionChanges.class)
    private List<TransactionChanges> changes;
    
    public ChangesToTransactions() {
        
    }
    
    public ChangesToTransactions(List<TransactionChanges> changes) {
        this.changes = changes;
    }

    public List<TransactionChanges> getTransactionChangeses() {
        return changes;
    }
}
