package pl.pd.emir.kdpw.api;

import pl.pd.emir.entity.Bank;

public interface BankModificationManager {

    boolean generateMessage(Bank bank);

    boolean bankModificationPossible();
}
