package pl.pd.emir.modules.kdpw.adapter.api;

import pl.pd.emir.kdpw.api.TransactionToRepository;
import java.util.List;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionWriterResult;

public interface TransactionWriter<E extends TransactionToRepository> {

    TransactionWriterResult write(List<E> list, String institutionId, String institutionIdType);
}
