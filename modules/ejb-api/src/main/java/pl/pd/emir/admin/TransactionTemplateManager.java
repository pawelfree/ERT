package pl.pd.emir.admin;

import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.TransactionTemplate;
import pl.pd.emir.manager.GenericManager;

public interface TransactionTemplateManager extends GenericManager<TransactionTemplate> {

    TransactionTemplate edit(TransactionTemplate transactionTemplate);

    Transaction createTransaction();

    Transaction refillTransaction(Transaction transaction);

    Transaction refillWithoutOverwriting(Transaction transaction);
}
