package pl.pd.emir.entity;

import pl.pd.emir.enums.DataType;

public interface Sendable {

    Long getId();

    Transaction getTransaction();

    String getOriginalId();

    DataType getDataType();

    Client getClient();

}
