package pl.pd.emir.kdpw.xml.parser;

import pl.pd.emir.entity.Bank;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.modules.kdpw.adapter.model.BankWriterResult;

public interface XmlBankChangeWriter {

    BankWriterResult write(Bank bank) throws XmlParseException;
}
