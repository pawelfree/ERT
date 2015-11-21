package pl.pd.emir.commons;

import javax.ejb.EJB;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.enums.EventType;

public class AbstractLogManagerTemplate<E extends Logable<Long>> extends AbstractManagerTemplate<E> {

    @EJB
    protected transient EventLogManager logManager;

    public AbstractLogManagerTemplate(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public E save(E entity) {
        E encja = entityManager.merge(entity);
        if (!context.getRollbackOnly()) {
            // flush() nie sprawdza czy transackja jest aktywna po tym jak wolene jest context.setRollbackOnly() i kazda operacja na encji powoduje exception
            entityManager.flush();
        }
        return encja;
    }

    @Override
    public void delete(E entity) {
        super.delete(entity);
        if (entity.getDeleteEventType().equals(EventType.TRANSACTION_DELETE)) {
            Transaction trans = (Transaction) entity;
            String description = String.format("%s, wersja:%s, %s",
                    trans.getOriginalId(),
                    trans.getVersion() == null ? "brak" : trans.getVersion(),
                    DateUtils.formatDate(trans.getTransactionDate(), DateUtils.DATE_FORMAT));
            logManager.addEventTransactional(entity.getDeleteEventType(), entity.getId(), description);
            return;
        }
        logManager.addEventTransactional(entity.getDeleteEventType(), entity.getId(), null);
    }
}
