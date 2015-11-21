package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.ContractType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class ContractData implements Serializable {

    /*
     * CNTRCTTP_TXNM, Stosowana klasyfikacja. Kontrakt identyfikuje się za pomocą identyfikatora produktu.
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Transaction.class),})
    @Column(name = "CONTRACT_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    /*
     * CNTRCTTP_PRDCTID1, Kod identyfikacyjny produktu nr 1.
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Transaction.class),})
    @Column(name = "PROD1_CODE", length = 20)
    private String prod1Code;
    /*
     * CNTRCTTP_PRDCTID2, Kod identyfikacyjny produktu nr 2.
     */
    @Column(name = "PROD2_CODE", length = 20)
    private String prod2Code;
    /*
     * CNTRCTTP_UNDRLYG, Instrument bazowy – identyfikuje się za pomocą niepowtarzalnego identyfikatora.
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Transaction.class),})
    @Column(name = "UNDERL_ID", length = 20)
    private String underlyingId;
    
//TODO co zrobić z tą walidacją     
    @Validators({
        @ValidateCompleteness(subjectClass = Transaction.class)})
    @Column(name = "UNDERL_TP", length = 1)
    private String underlyingTp;

    public ContractData() {
        super();
        initFields();
    }

    public ContractData(ContractType contractType, String prod1Code, String prod2Code, String underlyingId) {
        super();
        this.contractType = contractType;
        this.prod1Code = prod1Code;
        this.prod2Code = prod2Code;
        this.underlyingId = underlyingId;
        initFields();
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public String getProd1Code() {
        return prod1Code;
    }

    public void setProd1Code(String value) {
        this.prod1Code = StringUtil.getNullOnEmpty(value);
    }

    public String getProd2Code() {
        return prod2Code;
    }

    public void setProd2Code(String value) {
        this.prod2Code = StringUtil.getNullOnEmpty(value);
    }

    public String getUnderlyingId() {
        return underlyingId;
    }

    public void setUnderlyingId(String value) {
        this.underlyingId = StringUtil.getNullOnEmpty(value);
    }

    public String getUnderlyingTp() {
        return underlyingTp;
    }
    
    public void setUnderlyingTp(String value) {
        this.underlyingTp = StringUtil.getNullOnEmpty(value);
    }
    
    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, ContractData oldEntity, ContractData newEntity, String changeComment) {
        
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getContractType(), EventLogBuilder.EventDetailsKey.CONTRACT_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getProd1Code(), EventLogBuilder.EventDetailsKey.PROD1_CODE, changeComment);
            checkFieldsEquals(result, null, newEntity.getProd2Code(), EventLogBuilder.EventDetailsKey.PROD2_CODE, changeComment);
            checkFieldsEquals(result, null, newEntity.getUnderlyingId(), EventLogBuilder.EventDetailsKey.UNDERLYING_ID, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getContractType(), null, EventLogBuilder.EventDetailsKey.CONTRACT_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getProd1Code(), null, EventLogBuilder.EventDetailsKey.PROD1_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getProd2Code(), null, EventLogBuilder.EventDetailsKey.PROD2_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getUnderlyingId(), null, EventLogBuilder.EventDetailsKey.UNDERLYING_ID, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getContractType(), newEntity.getContractType(), EventLogBuilder.EventDetailsKey.CONTRACT_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getProd1Code(), newEntity.getProd1Code(), EventLogBuilder.EventDetailsKey.PROD1_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getProd2Code(), newEntity.getProd2Code(), EventLogBuilder.EventDetailsKey.PROD2_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getUnderlyingId(), newEntity.getUnderlyingId(), EventLogBuilder.EventDetailsKey.UNDERLYING_ID, changeComment);
        }
    }

    public ContractData fullClone() {
        return new ContractData(contractType, prod1Code, prod2Code, underlyingId);
    }
}
