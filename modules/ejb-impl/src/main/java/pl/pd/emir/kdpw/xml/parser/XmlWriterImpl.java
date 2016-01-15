package pl.pd.emir.kdpw.xml.parser;

import java.text.ParseException;
import java.util.Date;
import javax.ejb.EJB;
import pl.pd.emir.admin.MultiNumberGenerator;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.enums.MultiGeneratorKey;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;
import org.slf4j.LoggerFactory;

public abstract class XmlWriterImpl {

    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @EJB
    protected ParameterManager parameterManager;

    @EJB
    protected MultiNumberGenerator numberGenerator;

    protected String getSenderParameter() {
        return parameterManager.getValue(ParameterKey.KDPW_SENDER);
    }

    protected String getReceiverParameter() {
        // pobierana z PARAMETRY_E.RECEIVER_ID
        return parameterManager.getValue(ParameterKey.KDPW_RECEIVER);
    }

    /**
     *
     * @param institution
     * @return
     */
    protected String getBankOrClientRprtId(final Institution institution) {
        final StringBuilder result = new StringBuilder();
        if (null != institution && null != institution.getInstitutionData()
                && allNotEmpty(institution.getInstitutionData().getInstitutionId())) {
            result.append(institution.getInstitutionData().getInstitutionId());
        }
        return nullOnEmpty(result);
    }

    /**
     * BANK_E.RPRTID_TP
     *
     * @param institution
     * @return
     */
    protected String getBankOrClientRprtIdType(final Institution institution) {
        final StringBuilder result = new StringBuilder();
        if (null != institution
                && null != institution.getInstitutionData()
                && null != institution.getInstitutionData().getInstitutionIdType()) {
            result.append(XmlUtils.enumName(institution.getInstitutionData().getInstitutionIdType()));
        }
        return nullOnEmpty(result);
    }

    /**
     * Pobranie identifikatora komunikatu.
     *
     * @return Unikalny identyfilator komunikatu
     *
     * N M E C Z V O
     * M M M M M M M
     */
    protected String getSndrMsgRef() {
        final Date date = new Date();
        final Long newNumber = numberGenerator.getNewNumber(date, MultiGeneratorKey.KDPW_MSG_NUMBER);
        return String.format("%s%s", DateUtils.formatDate(date, "yyMMdd"), newNumber);
    }

    // V – aktualizacja wyceny kontraktu.
    protected abstract String getActpTp();

    //result.setActnTpDtls(null);
    //Dowolny tekst - opcjonalnie. TODO czy cos wstawiamy
    protected static String getDtlLvl() {
        // Pole uzupełniane przy tworzeniu komunikatu
        // Proszę aby zawsze była tam wstawiana wartość S
        return "S";
    }

    protected static boolean allNotEmpty(final Object... args) {
        return XmlUtils.allNotEmpty(args);
    }

    protected static boolean notAllEmpty(final Object... args) {
        return XmlUtils.notAllEmpty(args);
    }

    protected static String nullOnEmpty(final StringBuilder result) {
        return result.length() > 0 ? result.toString() : null;
    }

    protected static String nullOnEmpty(final String result) {
        return StringUtil.isEmpty(result) ? null : result;
    }

    protected Date getEligDate(final String dateAsString) {
        Date result = null;
        try {
            result = DateUtils.getDateFromString(dateAsString, Constants.ISO_DATE);
        } catch (ParseException ex) {
            LOGGER.error("Cannot parse value: " + dateAsString + " to date (" + Constants.ISO_DATE + ")");
        }
        return result;
    }
}
