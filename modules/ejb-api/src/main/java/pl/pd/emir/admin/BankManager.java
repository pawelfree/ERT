package pl.pd.emir.admin;

import java.util.Date;
import java.util.List;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.manager.GenericManager;

public interface BankManager extends GenericManager<Bank> {

    Bank edit(Bank bank);

    Bank getActive();

    Bank getFirst();

    Date getValuationReportingDate();

    List<Bank> saveBankList(List<Bank> bankList);

    Bank getHistoryActiveBankByDate(Date activeDate);
}
