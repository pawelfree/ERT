package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.TransactionDataChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
@Customizer(ContractDataDetailed.class)
public class ContractDataDetailed implements Serializable, DescriptorCustomizer {

    @ValidateCompleteness(subjectClass = Transaction.class)
    @Embedded
    private ContractData contractData;
    /*
     * CNTRCTTP_ISRCTRY, Kod kraju emitenta instrumentu bazowego
     */
    @Column(name = "UNDERL_COUNTRY_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    @TransactionDataChange
    private CountryCode underlCountryCode;
    /*
     * CNTRCTTP_NTNLCCY1, Waluta nominalna 1
     */
    @Column(name = "UNDERL_CURRENCY_1_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyCode underlCurrency1Code;
    /*
     * CNTRCTTP_NTNLCCY2, Waluta nominalna 2
     */
    @Column(name = "UNDERL_CURRENCY_2_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyCode underlCurrency2Code;
    /*
     * CNTRCTTP_DLVRBLCCY, Waluta dostawy
     */
    @Column(name = "DELIV_CURRENCY_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyCode delivCurrencyCode;

    public ContractDataDetailed() {
        super();
        initFields();
    }

    public ContractDataDetailed(ContractData contractData, CountryCode underlCountryCode, CurrencyCode underlCurrency1Code, CurrencyCode underlCurrency2Code, CurrencyCode delivCurrencyCode) {
        super();
        this.contractData = contractData;
        this.underlCountryCode = underlCountryCode;
        this.underlCurrency1Code = underlCurrency1Code;
        this.underlCurrency2Code = underlCurrency2Code;
        this.delivCurrencyCode = delivCurrencyCode;
        initFields();
    }

    public ContractData getContractData() {
        return contractData;
    }

    public void setContractData(ContractData contractData) {
        this.contractData = contractData;
    }

    public CurrencyCode getDelivCurrencyCode() {
        return delivCurrencyCode;
    }

    public void setDelivCurrencyCode(CurrencyCode delivCurrencyCode) {
        this.delivCurrencyCode = delivCurrencyCode;
    }

    public CountryCode getUnderlCountryCode() {
        return underlCountryCode;
    }

    public void setUnderlCountryCode(CountryCode underlCountryCode) {
        this.underlCountryCode = underlCountryCode;
    }

    public CurrencyCode getUnderlCurrency1Code() {
        return underlCurrency1Code;
    }

    public void setUnderlCurrency1Code(CurrencyCode underlCurrency1Code) {
        this.underlCurrency1Code = underlCurrency1Code;
    }

    public CurrencyCode getUnderlCurrency2Code() {
        return underlCurrency2Code;
    }

    public void setUnderlCurrency2Code(CurrencyCode underlCurrency2Code) {
        this.underlCurrency2Code = underlCurrency2Code;
    }

    public final void initFields() {
        if (this.contractData == null) {
            this.contractData = new ContractData();
        }
    }

    public static void checkEntity(List<ChangeLog> result, ContractDataDetailed oldEntity, ContractDataDetailed newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }

        if (oldEntity == null && newEntity != null) {
            ContractData.checkEntity(result, null, newEntity.getContractData());
            checkFieldsEquals(result, null, newEntity.getUnderlCountryCode(), EventLogBuilder.EventDetailsKey.UNDERL_COUNTRY_CODE);
            checkFieldsEquals(result, null, newEntity.getUnderlCurrency1Code(), EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY1_CODE);
            checkFieldsEquals(result, null, newEntity.getUnderlCurrency2Code(), EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY2_CODE);
            checkFieldsEquals(result, null, newEntity.getDelivCurrencyCode(), EventLogBuilder.EventDetailsKey.DELIV_CURRENCY_CODE);
        } else if (oldEntity != null && newEntity == null) {
            ContractData.checkEntity(result, oldEntity.getContractData(), null);
            checkFieldsEquals(result, oldEntity.getUnderlCountryCode(), null, EventLogBuilder.EventDetailsKey.UNDERL_COUNTRY_CODE);
            checkFieldsEquals(result, oldEntity.getUnderlCurrency1Code(), null, EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY1_CODE);
            checkFieldsEquals(result, oldEntity.getUnderlCurrency2Code(), null, EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY2_CODE);
            checkFieldsEquals(result, oldEntity.getDelivCurrencyCode(), null, EventLogBuilder.EventDetailsKey.DELIV_CURRENCY_CODE);
        } else if (oldEntity != null && newEntity != null) {
            ContractData.checkEntity(result, oldEntity.getContractData(), newEntity.getContractData());
            checkFieldsEquals(result, oldEntity.getUnderlCountryCode(), newEntity.getUnderlCountryCode(), EventLogBuilder.EventDetailsKey.UNDERL_COUNTRY_CODE);
            checkFieldsEquals(result, oldEntity.getUnderlCurrency1Code(), newEntity.getUnderlCurrency1Code(), EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY1_CODE);
            checkFieldsEquals(result, oldEntity.getUnderlCurrency2Code(), newEntity.getUnderlCurrency2Code(), EventLogBuilder.EventDetailsKey.UNDERL_CURRENCY2_CODE);
            checkFieldsEquals(result, oldEntity.getDelivCurrencyCode(), newEntity.getDelivCurrencyCode(), EventLogBuilder.EventDetailsKey.DELIV_CURRENCY_CODE);
        }
    }

    public ContractDataDetailed fullClone() {
        return new ContractDataDetailed(contractData.fullClone(), underlCountryCode, underlCurrency1Code, underlCurrency2Code, delivCurrencyCode);
    }

    @Override
    public void customize(ClassDescriptor descriptor) {
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("contractData")).setIsNullAllowed(false);
    }
}
