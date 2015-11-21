package pl.pd.emir.admin;

import pl.pd.emir.admin.BankManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.commons.EventLogManager;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.enums.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Local(BankManager.class)
public class BankManagerImpl extends AbstractManagerTemplate<Bank> implements BankManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankManagerImpl.class);

    @EJB
    private EventLogManager eventLogFacade;

    public BankManagerImpl() {
        super(Bank.class);
    }

    @Override
    public Bank getActive() {
        Bank result = null;
        Query query = entityManager.createNamedQuery("Bank.getActive");
        try {
            result = (Bank) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Institution does not exist.");
        }
        return result;
    }

    @Override
    public Bank edit(Bank bank) {
        //pobranie aktualnie obowiązującej instytucji
        Bank activeBank = getActive();

        if (activeBank != null) {
            //ustawienie aktualnie obowiązującej instytucji jako archiwalnej
            activeBank.setActive(false);
            //utrwalenie zmian
            entityManager.merge(activeBank);
        }

        //ustawienie nowej instytucji jako obowiązującej
        bank.setActive(true);
        bank.setModificationDate(new Date());

        //utrwalenie nowej instytucji
        entityManager.persist(bank);

        //dodanie zdarzeń opisujących modyfikacje instytucji do dziennika
        if (activeBank != null) {
            logModificationEvents(activeBank.getChangeLogs(bank));
        }

        //zwrócenie nowej instytucji
        return getActive();
    }

    @Override
    public Bank getFirst() {
        Bank result = null;
        Query query = entityManager.createNamedQuery("Bank.getAll");
        query.setMaxResults(1);
        try {
            result = (Bank) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Institution does not exist.");
        }
        return result;
    }

    @Override
    public Date getValuationReportingDate() {
        Bank bank = getActive();
        if (bank != null) {
            return bank.getValuationReportingDate();
        } else {
            return null;
        }
    }

    public void logModificationEvents(List<ChangeLog> logs) {
        if (logs != null && !logs.isEmpty()) {
            Bank first = getFirst();
            if (first != null) {
                eventLogFacade.addEventTransactional(EventType.BANK_MODIFICATION, first.getId(), null);
                logs.stream().forEach((log) -> {
                    eventLogFacade.addEventTransactional(EventType.BANK_MODIFICATION_CHANGELOG, first.getId(), null, log);
                });
            }
        }
    }

    @Override
    public List<Bank> saveBankList(List<Bank> bankList) {
        LOGGER.debug("saveBankList...listSize = {} ", bankList.size());
        List<Bank> listBank = new ArrayList<>();
        Bank bankEntity;
        for (Bank bank : bankList) {
            boolean bankNotExist = StringUtil.isEmpty(bank.getBankNr()) ? true : entityManager.createNamedQuery("Bank.getByBankNr").setParameter("bankNr", bank.getBankNr()).getResultList().isEmpty();
            if (bankNotExist && getActive() == null) {
                bank.setModificationDate(new Date());
                bank.setActive(true);
                bankEntity = this.save(bank);
            } else {
                bankEntity = this.edit(bank);
            }
            listBank.add(bankEntity);
        }
        LOGGER.debug("saveBankList...resultList = {} ", listBank.size());
        return listBank;
    }

    /**
     * Metoda szuka Banku, który był aktywny w podanej dacie.
     *
     * @param activeDate - sprawdzana data aktywności
     * @return
     */
    @Override
    public Bank getHistoryActiveBankByDate(Date activeDate) {
        Query query = entityManager.createNamedQuery("Bank.getHistryBankByDate").setParameter("activeDate", DateUtils.getDayEnd(activeDate));
        Bank bank = null;
        try {
            bank = (Bank) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("No active bank on date: {}", activeDate, e.getStackTrace());
        } catch (NonUniqueResultException e) {
            LOGGER.error("Non Unique active bank on date: {}", activeDate, e.getStackTrace());
        }
        return bank;
    }
}
