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
@XmlRootElement(name = "transactionchanges")
public class TransactionChanges {

        private String transactionId;    
        @XmlElement(name = "change", type = ChangeRegister.class)    
        private List<ChangeRegister> changes;

        public TransactionChanges() {
        }
        
        public TransactionChanges(String transactionId, List<ChangeRegister> changes) {
            this.transactionId = transactionId;
            this.changes = changes;
        }    
        
        public String getTransactionId() {
            return transactionId;
        }
        
        public List<ChangeRegister> getChanges() {
            return changes;
        }
    
}
