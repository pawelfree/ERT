package pl.pd.emir.kdpw.service;

import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.register.TransactionToSendManager;

@Stateless
@Local(TransactionToSendManager.class)
public class TransactionToSendManagerImpl extends AbstractManagerTemplate<Transaction> implements TransactionToSendManager {

    protected TransactionToSendManagerImpl() {
        super(Transaction.class);
    }

    @Override
    public final Date getMaxDate() {
        return getDateFromQuery("Transaction.getMaxDateToSend");
    }

    @Override
    public final Date getMinDate() {
        return getDateFromQuery("Transaction.getMinDateToSend");
    }

    protected final Date getDateFromQuery(final String namedQuery) {
        Date result;
        try {
            final Query query = getEntityManager().createNamedQuery(namedQuery);
            result = (Date) query.getSingleResult();
        } catch (NoResultException nre) {
            result = null;
        }
        return result;
    }
}
