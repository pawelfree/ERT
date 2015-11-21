package pl.pd.emir.modules.kdpw.adapter.api;

import pl.pd.emir.modules.kdpw.adapter.model.RepositoryResponse;

public interface RepositoryMessageReader {

    RepositoryResponse read(String message);
}
