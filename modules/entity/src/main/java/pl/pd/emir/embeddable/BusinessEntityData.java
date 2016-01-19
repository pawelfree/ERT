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
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CommercialActity;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class BusinessEntityData implements Serializable {

    /*
     *_CTRPTYDTLS_BRKRID_ID, Kod identyfikacyjny brokera, powiązany z transakcją
     */
    @Column(name = "IDENT_CODE", length = 50)
    private String idCode;
    /*
     * _CTRPTYDTLS_BRKRID_TP, Kod identyfikacyjny brokera - typ użytego identyfikatora instytucji, powiązany z transakcją
     */
    @Column(name = "IDENT_TYPE_CODE", length = 4)
    @Enumerated(EnumType.STRING)
    private InstitutionIdType idCodeType;
    /*
     * _CTRPTYDTLS_CLRMMBID_ID, Kod identyfikacyjny członka rozliczającego  powiązany z transakcją
     */
    @Column(name = "MEMBER_ID", length = 50)
    private String memberId;
    /*
     * _CTRPTYDTLS_CLRMMBID_TP, Kod identyfikacyjny członka rozliczającego - typ użytego identyfikatora instytucji, powiązany z transakcją
     */
    @Column(name = "MEMBER_ID_TYPE", length = 4)
    @Enumerated(EnumType.STRING)
    private InstitutionIdType memberIdType;
    /*
     * _CTRPTYDTLS_CLRACCT, Identyfikator konta rozliczającego
     */
    @Column(name = "SETTLING_ACCOUNT", length = 35)
    private String settlingAccout;
    /*
     * _CTRPTYDTLS_BNFCRYID_ID, Kod identyfikacyjny beneficjenta (identyfikator instytucji), powiązany z transakcją
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "BENEFICIARY_CODE", length = 50)
    private String beneficiaryCode;
    /*
     * BCTRPTYDTLS_BNFCRYID_TP, Kod identyfikacyjny beneficjenta - typ użytego identyfikatora instytucji, powiązany z transakcją
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "BENEFICIARY_CODE_TYPE", length = 4)
    @Enumerated(EnumType.STRING)
    private InstitutionIdType beneficiaryCodeType;
    /*
     * _CTRPTYDTLS_TRDGCPCTY, Charakter, w jakim zawarto transakcję
     *  Wskazanie, czy kontrahent dokonujący zgłoszenia zawarł kontrakt na własny rachunek (w imieniu własnym lub w imieniu klienta)
     *  czy też na rachunek i w imieniu klienta.
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "TRANSACTION_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    /*
     * _CTRPTYDTLS_CMMRCLACTVTY, Bezpośredni związek z działalnością gospodarczą lub działalnością w zakresie zarządzania aktywami i pasywami.
     * Wskazanie, czy kontrakt jest obiektywnie i w mierzalny sposób bezpośrednio związany z działalnością gospodarczą kontrahenta
     * dokonującego zgłoszenia lub jego działalnością w zakresie zarządzania aktywami i pasywami
     */
    @Column(name = "COMMERCIAL_ACTITIVY", length = 3)
    @Enumerated(EnumType.STRING)
    private CommercialActity commercialActity;
    /*
     * _CTRPTYDTLS_CLRTRSHLD, Próg wiążący się z obowiązkiem rozliczania
     */
    @Column(name = "SETTLEMENT_THRESHOLD", length = 3)
    @Enumerated(EnumType.STRING)
    private SettlementThreshold settlementThreshold;
    /*
     * _CTRPTYDTLS_COLLPRTFL, Kod portfela zabezpieczeń.
     */
    @Column(name = "COMM_WALLET_CODE", length = 35)
    private String commWalletCode;

    public BusinessEntityData() {
        super();
        initFields();
    }

    public BusinessEntityData(String idCode, InstitutionIdType idCodeType, String memberId, InstitutionIdType memberIdType, String settlingAccout,
            String beneficiaryCode, InstitutionIdType beneficiaryCodeType, TransactionType transactionType, CommercialActity commercialActity,
            SettlementThreshold settlementThreshold, String commWalletCode) {
        super();
        this.idCode = idCode;
        this.idCodeType = idCodeType;
        this.memberId = memberId;
        this.memberIdType = memberIdType;
        this.settlingAccout = settlingAccout;
        this.beneficiaryCode = beneficiaryCode;
        this.beneficiaryCodeType = beneficiaryCodeType;
        this.transactionType = transactionType;
        this.commercialActity = commercialActity;
        this.settlementThreshold = settlementThreshold;
        this.commWalletCode = commWalletCode;
        initFields();
    }

    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    public void setBeneficiaryCode(String value) {
        this.beneficiaryCode = StringUtil.getNullOnEmpty(value);
    }

    public InstitutionIdType getBeneficiaryCodeType() {
        return beneficiaryCodeType;
    }

    public void setBeneficiaryCodeType(InstitutionIdType beneficiaryCodeType) {
        this.beneficiaryCodeType = beneficiaryCodeType;
    }

    public String getCommWalletCode() {
        return commWalletCode;
    }

    public void setCommWalletCode(String value) {
        this.commWalletCode = StringUtil.getNullOnEmpty(value);
    }

    public CommercialActity getCommercialActity() {
        return commercialActity;
    }

    public void setCommercialActity(CommercialActity commercialActity) {
        this.commercialActity = commercialActity;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String value) {
        this.idCode = StringUtil.getNullOnEmpty(value);
    }

    public InstitutionIdType getIdCodeType() {
        return idCodeType;
    }

    public void setIdCodeType(InstitutionIdType idCodeType) {
        this.idCodeType = idCodeType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String value) {
        this.memberId = StringUtil.getNullOnEmpty(value);
    }

    public InstitutionIdType getMemberIdType() {
        return memberIdType;
    }

    public void setMemberIdType(InstitutionIdType memberIdType) {
        this.memberIdType = memberIdType;
    }

    public SettlementThreshold getSettlementThreshold() {
        return settlementThreshold;
    }

    public void setSettlementThreshold(SettlementThreshold settlementThreshold) {
        this.settlementThreshold = settlementThreshold;
    }

    public String getSettlingAccout() {
        return settlingAccout;
    }

    public void setSettlingAccout(String value) {
        this.settlingAccout = StringUtil.getNullOnEmpty(value);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, BusinessEntityData oldEntity, BusinessEntityData newEntity, Boolean isBank) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getIdCode(), isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE : EventLogBuilder.EventDetailsKey.C_ID_CODE);
            checkFieldsEquals(result, null, newEntity.getIdCodeType(), isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_ID_CODE_TYPE);
            checkFieldsEquals(result, null, newEntity.getMemberId(), isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID : EventLogBuilder.EventDetailsKey.C_MEMBER_ID);
            checkFieldsEquals(result, null, newEntity.getMemberIdType(), isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID_TYPE : EventLogBuilder.EventDetailsKey.C_MEMBER_ID_TYPE);
            checkFieldsEquals(result, null, newEntity.getSettlingAccout(), EventLogBuilder.EventDetailsKey.SETTLING_ACCOUT);
            checkFieldsEquals(result, null, newEntity.getBeneficiaryCode(), isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE);
            checkFieldsEquals(result, null, newEntity.getBeneficiaryCodeType(), isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE_TYPE);
            checkFieldsEquals(result, null, newEntity.getTransactionType(), EventLogBuilder.EventDetailsKey.TRANSACTION_TYPE);
            checkFieldsEquals(result, null, newEntity.getCommercialActity(), EventLogBuilder.EventDetailsKey.COMMERCIAL_ACTITY);
            checkFieldsEquals(result, null, newEntity.getSettlementThreshold(), EventLogBuilder.EventDetailsKey.SETTLEMENT_THRESHOLD);
            checkFieldsEquals(result, null, newEntity.getCommWalletCode(), EventLogBuilder.EventDetailsKey.COMM_WALLET_CODE);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getIdCode(), null, isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE : EventLogBuilder.EventDetailsKey.C_ID_CODE);
            checkFieldsEquals(result, oldEntity.getIdCodeType(), null, isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_ID_CODE_TYPE);
            checkFieldsEquals(result, oldEntity.getMemberId(), null, isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID : EventLogBuilder.EventDetailsKey.C_MEMBER_ID);
            checkFieldsEquals(result, oldEntity.getMemberIdType(), null, isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID_TYPE : EventLogBuilder.EventDetailsKey.C_MEMBER_ID_TYPE);
            checkFieldsEquals(result, oldEntity.getSettlingAccout(), null, EventLogBuilder.EventDetailsKey.SETTLING_ACCOUT);
            checkFieldsEquals(result, oldEntity.getBeneficiaryCode(), null, isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE);
            checkFieldsEquals(result, oldEntity.getBeneficiaryCodeType(), null, isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE_TYPE);
            checkFieldsEquals(result, oldEntity.getTransactionType(), null, EventLogBuilder.EventDetailsKey.TRANSACTION_TYPE);
            checkFieldsEquals(result, oldEntity.getCommercialActity(), null, EventLogBuilder.EventDetailsKey.COMMERCIAL_ACTITY);
            checkFieldsEquals(result, oldEntity.getSettlementThreshold(), null, EventLogBuilder.EventDetailsKey.SETTLEMENT_THRESHOLD);
            checkFieldsEquals(result, oldEntity.getCommWalletCode(), null, EventLogBuilder.EventDetailsKey.COMM_WALLET_CODE);
        } else {
            checkFieldsEquals(result, oldEntity.getIdCode(), newEntity.getIdCode(), isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE : EventLogBuilder.EventDetailsKey.C_ID_CODE);
            checkFieldsEquals(result, oldEntity.getIdCodeType(), newEntity.getIdCodeType(), isBank ? EventLogBuilder.EventDetailsKey.B_ID_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_ID_CODE_TYPE);
            checkFieldsEquals(result, oldEntity.getMemberId(), newEntity.getMemberId(), isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID : EventLogBuilder.EventDetailsKey.C_MEMBER_ID);
            checkFieldsEquals(result, oldEntity.getMemberIdType(), newEntity.getMemberIdType(), isBank ? EventLogBuilder.EventDetailsKey.B_MEMBER_ID_TYPE : EventLogBuilder.EventDetailsKey.C_MEMBER_ID_TYPE);
            checkFieldsEquals(result, oldEntity.getSettlingAccout(), newEntity.getSettlingAccout(), EventLogBuilder.EventDetailsKey.SETTLING_ACCOUT);
            checkFieldsEquals(result, oldEntity.getBeneficiaryCode(), newEntity.getBeneficiaryCode(), isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE);
            checkFieldsEquals(result, oldEntity.getBeneficiaryCodeType(), newEntity.getBeneficiaryCodeType(), isBank ? EventLogBuilder.EventDetailsKey.B_BENEFICIARY_CODE_TYPE : EventLogBuilder.EventDetailsKey.C_BENEFICIARY_CODE_TYPE);
            checkFieldsEquals(result, oldEntity.getTransactionType(), newEntity.getTransactionType(), EventLogBuilder.EventDetailsKey.TRANSACTION_TYPE);
            checkFieldsEquals(result, oldEntity.getCommercialActity(), newEntity.getCommercialActity(), EventLogBuilder.EventDetailsKey.COMMERCIAL_ACTITY);
            checkFieldsEquals(result, oldEntity.getSettlementThreshold(), newEntity.getSettlementThreshold(), EventLogBuilder.EventDetailsKey.SETTLEMENT_THRESHOLD);
            checkFieldsEquals(result, oldEntity.getCommWalletCode(), newEntity.getCommWalletCode(), EventLogBuilder.EventDetailsKey.COMM_WALLET_CODE);
        }
    }

    public BusinessEntityData fullClone() {
        return new BusinessEntityData(idCode,
                idCodeType,
                memberId,
                memberIdType,
                settlingAccout,
                beneficiaryCode,
                beneficiaryCodeType,
                transactionType,
                commercialActity,
                settlementThreshold,
                commWalletCode);
    }
}
