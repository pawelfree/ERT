package pl.pd.emir.imports;

/**
 *
 * @author PawelDudek
 */
public class ImportOverview {

    private int itemsInValuation = 0;
    private int itemsInProtection = 0;
    private int itemsInTransaction = 0;

    private Long newTransactions = 0L;
    private Long existingTransactions = 0L;
    private Long expiredTransactions = 0L;

    public Long getOngoingTransactions() {
        return existingTransactions - newTransactions - expiredTransactions;
    }
    
    /**
     * @return the itemsInValuation
     */
    public int getItemsInValuation() {
        return itemsInValuation;
    }

    /**
     * @param itemsInValuation the itemsInValuation to set
     */
    public void setItemsInValuation(int itemsInValuation) {
        this.itemsInValuation = itemsInValuation;
    }

    /**
     * @return the itemsInProtection
     */
    public int getItemsInProtection() {
        return itemsInProtection;
    }

    /**
     * @param itemsInProtection the itemsInProtection to set
     */
    public void setItemsInProtection(int itemsInProtection) {
        this.itemsInProtection = itemsInProtection;
    }

    /**
     * @return the itemsInTransaction
     */
    public int getItemsInTransaction() {
        return itemsInTransaction;
    }

    /**
     * @param itemsInTransaction the itemsInTransaction to set
     */
    public void setItemsInTransaction(int itemsInTransaction) {
        this.itemsInTransaction = itemsInTransaction;
    }

    /**
     * @return the newTransactions
     */
    public Long getNewTransactions() {
        return newTransactions;
    }

    /**
     * @param newTransactions the newTransactions to set
     */
    public void setNewTransactions(Long newTransactions) {
        this.newTransactions = newTransactions;
    }

    /**
     * @return the existingTransactions
     */
    public Long getExistingTransactions() {
        return existingTransactions;
    }

    /**
     * @param existingTransactions the existingTransactions to set
     */
    public void setExistingTransactions(Long existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

    /**
     * @return the expiredTransactions
     */
    public Long getExpiredTransactions() {
        return expiredTransactions;
    }

    /**
     * @param expiredTransactions the expiredTransactions to set
     */
    public void setExpiredTransactions(Long expiredTransactions) {
        this.expiredTransactions = expiredTransactions;
    }
}
