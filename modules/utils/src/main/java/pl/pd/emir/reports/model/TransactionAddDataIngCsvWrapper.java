package pl.pd.emir.reports.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionAddDataIngCsvWrapper {

    final private List<String> header = new ArrayList<>();

    public TransactionAddDataIngCsvWrapper() {
        addHeader();
    }

    private String contractId;
    private String blockNumber;
    private String dealStatus;
    private String boVersionDate;
    private String validated;
    private String boDealSourceTypeOfEvent;
    private String internalExternal;
    private String boCaptureDate;
    private String eventDealDataFolders;
    private String prodSubtype;
    private String cptyShortName;
    private String thirdCptyId;
    private String nrKkicbs;
    private String dictionaryName;
    private String product;
    private String controlStatus;

    private void addHeader() {
        header.add("CONTRACTID");
        header.add("BLOCKNUMBER");
        header.add("DEALSTATUS");
        header.add("BOVERSIONDATE");
        header.add("VALIDATED");
        header.add("BODEALSOURCETYPEOFEVENT");
        header.add("INTERNALEXTERNAL");
        header.add("BOCAPTUREDATE");
        header.add("EVENTDEALDATAFOLDERS");
        header.add("PRODSUBTYPE");
        header.add("CPTYSHORTNAME");
        header.add("THIRDCPTYID");
        header.add("NRKKICBS");
        header.add("DICTIONARYNAME");
        header.add("PRODUCT");
        header.add("CONTROLSTATUS");
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getBoVersionDate() {
        return boVersionDate;
    }

    public void setBoVersionDate(String boVersionDate) {
        this.boVersionDate = boVersionDate;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public String getBoDealSourceTypeOfEvent() {
        return boDealSourceTypeOfEvent;
    }

    public void setBoDealSourceTypeOfEvent(String boDealSourceTypeOfEvent) {
        this.boDealSourceTypeOfEvent = boDealSourceTypeOfEvent;
    }

    public String getInternalExternal() {
        return internalExternal;
    }

    public void setInternalExternal(String internalExternal) {
        this.internalExternal = internalExternal;
    }

    public String getBoCaptureDate() {
        return boCaptureDate;
    }

    public void setBoCaptureDate(String boCaptureDate) {
        this.boCaptureDate = boCaptureDate;
    }

    public String getEventDealDataFolders() {
        return eventDealDataFolders;
    }

    public void setEventDealDataFolders(String eventDealDataFolders) {
        this.eventDealDataFolders = eventDealDataFolders;
    }

    public String getProdSubtype() {
        return prodSubtype;
    }

    public void setProdSubtype(String prodSubtype) {
        this.prodSubtype = prodSubtype;
    }

    public String getCptyShortName() {
        return cptyShortName;
    }

    public void setCptyShortName(String cptyShortName) {
        this.cptyShortName = cptyShortName;
    }

    public String getThirdCptyId() {
        return thirdCptyId;
    }

    public void setThirdCptyId(String thirdCptyId) {
        this.thirdCptyId = thirdCptyId;
    }

    public String getNrKkicbs() {
        return nrKkicbs;
    }

    public void setNrKkicbs(String nrKkicbs) {
        this.nrKkicbs = nrKkicbs;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public List<String> getHeader() {
        return header;
    }

}
