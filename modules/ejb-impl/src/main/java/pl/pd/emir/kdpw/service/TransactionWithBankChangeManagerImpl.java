package pl.pd.emir.kdpw.service;

import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.register.TransactionWithBankChangeManager;

@Stateless
@Local(TransactionWithBankChangeManager.class)
public class TransactionWithBankChangeManagerImpl extends AbstractManagerTemplate<Transaction> implements TransactionWithBankChangeManager {

    public TransactionWithBankChangeManagerImpl() {
        super(Transaction.class);
    }

    @Override
    public final Date getMaxDate() {
        return getDateFromQuery("Transaction.getValidMaxDate");
    }

    @Override
    public final Date getMinDate() {
        return getDateFromQuery("Transaction.getValidMinDate");
    }

    protected final Date getDateFromQuery(final String namedQuery) {
        Date result;
        try {
            result = (Date) getEntityManager().createNamedQuery(namedQuery).getSingleResult();
        } catch (NoResultException nre) {
            result = null;
        }
        return result;
    }
}
