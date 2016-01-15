package pl.pd.emir.kdpw.xml.parser;

import java.util.Date;
import javax.ejb.Stateless;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.kdpw.xml.builder.XmlBuilder;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;
import pl.pd.emir.modules.kdpw.adapter.model.BankWriterResult;
import kdpw.xsd.trar_ins_005.CounterpartyAddressAndSectorDetails;
import kdpw.xsd.trar_ins_005.DateAndDateTimeChoice;
import kdpw.xsd.trar_ins_005.Domicile;
import kdpw.xsd.trar_ins_005.FunctionOfMessage;
import kdpw.xsd.trar_ins_005.GeneralInformation;
import kdpw.xsd.trar_ins_005.KDPWDocument;
import kdpw.xsd.trar_ins_005.ObjectFactory;
import kdpw.xsd.trar_ins_005.TRInstitutionCode;
import kdpw.xsd.trar_ins_005.TrarIns00502;
import static pl.pd.emir.kdpw.xml.parser.XmlWriterImpl.nullOnEmpty;

@Stateless
public class XmlBankChangeWriter extends XmlWriterImpl {

    private static final String CHANGE_TYPE = "M";

    public BankWriterResult write(final Bank bank) throws XmlParseException {
        LOGGER.info("Starting generate trar.ins.005.01 message");
        final KDPWDocument document = new KDPWDocument();

        document.setSndr(getSenderParameter());
        document.setRcvr(getReceiverParameter());

        final TrarIns00502 trar = new TrarIns00502();
        document.getTrarIns00502().add(trar);

        //1. Informacje ogólne
        trar.setGnlInf(getGeneralInfo(bank));

        //2. Kod identyfikacyjny kontrahenta RT
        trar.setCtrPtyTRId(getTrInstitutionCode(bank));

        //3. Szczegółowe informacje adresowo-branżowe o kontrahencie
        trar.setCtrPtyAdrAndSctr(getCtrPtyAddressAndSector(bank));

        //4. Kontrahent finansowy / niefinansowy
        trar.setFinNonFinInd(bank.getContrPartyType());

        return getResult(document, bank);
    }

    @Override
    protected String getActpTp() {
        return CHANGE_TYPE;
    }

    protected GeneralInformation getGeneralInfo(final Bank bank) {
        // Informacje ogolne
        final GeneralInformation result = new GeneralInformation();

        //1.1 Identyfikator RT wystawcy komunikatu
        result.setTRRprtId(getTrInstitutionCode(bank));

        //1.2 Unikalny identyfikator komunikatu wysyłanego do KDPW_TR
        result.setSndrMsgRef(getSndrMsgRef());

        //1.3 Funkcja komunikatu
        result.setFuncOfMsg(getFuncOfMsg());

        //1.4 Rodzaj zmiany
        result.setActnTp(getActpTp());

        //1.5 Szczegółowe informacje dotyczące zmiany | PUSTE
        result.setActnTpDtls(null);

        //1.6 Data utworzenia komunikatu.
        final Date currentDate = new Date();
        result.setCreDtTm(getCreDtTm(currentDate));

        //1.7 Data obowiązywania zgłoszenia
        result.setEligDt(XmlUtils.formatDate(currentDate, Constants.ISO_DATE));

        //1.9 Poziom szczegółowości odpowiedzi
        result.setDtlLvl(getDtlLvl());

        return result;
    }

    protected TRInstitutionCode getTrInstitutionCode(final Bank bank) {
        final TRInstitutionCode result = new TRInstitutionCode();
        // Podstawowy identyfikator RT
        result.setId(bank.getInstitution().getInstitutionData().getInstitutionId());
        // Dodatkowy identyfikator RT
        result.setTp(XmlUtils.enumName(bank.getInstitution().getInstitutionData().getInstitutionIdType()));
        return result;
    }

    protected static FunctionOfMessage getFuncOfMsg() {
        return FunctionOfMessage.NEWM; // Wartość stała	NEWM
    }

    protected static DateAndDateTimeChoice getCreDtTm(final Date currentDate) {
        final DateAndDateTimeChoice result = new DateAndDateTimeChoice();
        result.setDtTm(XmlUtils.formatDate(currentDate, Constants.ISO_DATE_TIME));
        return result;
    }

    protected CounterpartyAddressAndSectorDetails getCtrPtyAddressAndSector(final Bank bank) {
        // required = false
        CounterpartyAddressAndSectorDetails result = new CounterpartyAddressAndSectorDetails();

        //3.1 Nazwa kontrahenta
        result.setNm(nullOnEmpty(bank.getBankName()));

        //3.2 Siedziba kontrahenta
        result.setDmcl(getDomicile(bank));

        //3.3 Branża, do której należy kontrahent
        result.setCorpSctr(nullOnEmpty(bank.getContrPartyIndustry()));

        return result;

    }

    protected Domicile getDomicile(final Bank bank) {
        // required = true
        Domicile result = new Domicile();

        // Kraj rezydencji - WYMAGANE
        result.setCtry(XmlUtils.enumName(bank.getCountryCode()));

        if (null != bank.getInstitution() && null != bank.getInstitution().getInstitutionAddr()) {
            final InstitutionAddress address = bank.getInstitution().getInstitutionAddr();

            // Kod pocztowy
            result.setPstCd(nullOnEmpty(address.getPostalCode()));

            // Miasto - NIEwymagane
            result.setTwnNm(nullOnEmpty(address.getCity()));

            // Ulica
            result.setStrtNm(nullOnEmpty(address.getStreetName()));

            // Numer budynku
            result.setBldgId(nullOnEmpty(address.getBuildingId()));

            // Numer lokalu
            result.setPrmsId(nullOnEmpty(address.getPremisesId()));

            // Pozostałe dane kontaktowe
            result.setDmclDtls(nullOnEmpty(address.getDetails()));
        }
        return result;
    }

    private BankWriterResult getResult(final KDPWDocument result, final Bank bank) throws XmlParseException {
        final String xmlMsg = XmlBuilder.build(new ObjectFactory().createKDPWDocument(result).getValue());
        return new BankWriterResult(bank.getId(), result.getTrarIns00502().get(0).getGnlInf().getSndrMsgRef(), xmlMsg);
    }
}
