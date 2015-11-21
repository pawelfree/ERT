package pl.pd.emir.admin;

import pl.pd.emir.admin.TransactionTemplateManager;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.TransactionTemplate;
import pl.pd.emir.entity.utils.TransactionTemplateUtils;

@Stateless
@Local(TransactionTemplateManager.class)
public class TransactionTemplateManagerImpl extends AbstractManagerTemplate<TransactionTemplate> implements TransactionTemplateManager {

    public TransactionTemplateManagerImpl() {
        super(TransactionTemplate.class);
    }

    @Override
    public TransactionTemplate edit(TransactionTemplate transactionTemplate) {
        entityManager.persist(transactionTemplate);
        return transactionTemplate;
    }

    @Override
    public Transaction createTransaction() {
        TransactionTemplate template = getById(0L);
        return TransactionTemplateUtils.createTransaction(template);
    }

    @Override
    public Transaction refillTransaction(Transaction transaction) {
        TransactionTemplate template = getById(1L);
        return TransactionTemplateUtils.refillTransaction(transaction, template);
    }

    @Override
    public Transaction refillWithoutOverwriting(Transaction transaction) {
        TransactionTemplate template = getById(1L);
        return TransactionTemplateUtils.refillWithoutOverwriting(transaction, template);
    }

}
