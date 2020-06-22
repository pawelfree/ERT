package pl.pd.emir.resources;

import java.util.Date;
import java.util.Properties;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.PropertyUtils;

/**
 * Klasa generująca szczegóły zdarzeń oraz informacje o modyfikacji rekordów na potrzeby dziennika zdarzeń.
 */
public class EventLogBuilder {

    private static final String PROPERTYFILE = "eventlog-details.properties";

    protected final static Properties PROPERTIES = PropertyUtils.getProperties(EventLogBuilder.class, PROPERTYFILE);

    private StringBuilder builder = new StringBuilder();

    private final String KEY_SEPARATOR = ", ";

    private final String VALUE_SEPARATOR = ": ";

    private final EventDetailsKey initKey;

    public EventLogBuilder() {
        initKey = null;
    }

    public EventLogBuilder(EventDetailsKey key) {
        initKey = key;
        builder.append(PropertyUtils.getString(PROPERTIES, key.msgKey()));
    }

    public void reset() {
        builder = new StringBuilder();
        if (initKey != null) {
            builder.append(PropertyUtils.getString(PROPERTIES, initKey.msgKey()));
        }
    }

    public EventLogBuilder append(EventDetailsKey key, String value) {
        if (builder.length() > 0) {
            builder.append(KEY_SEPARATOR);
        }
        builder.append(PropertyUtils.getString(PROPERTIES, key.msgKey())).append(VALUE_SEPARATOR).append(value);
        return this;
    }

    public EventLogBuilder append(EventDetailsKey key, Date value) {
        return append(key, DateUtils.formatDate(value, DateUtils.DATE_FORMAT));
    }

    public EventLogBuilder append(EventDetailsKey key, boolean value) {
        if (value) {
            return append(key, getBundleAndFormat("boolean.true"));
        } else {
            return append(key, getBundleAndFormat("boolean.false"));
        }
    }

    public EventLogBuilder append(EventDetailsKey key, String value, boolean translate) {
        if (builder.length() > 0) {
            builder.append(KEY_SEPARATOR);
        }
        if (translate) {
            builder.append(PropertyUtils.getString(PROPERTIES, key.msgKey())).append(VALUE_SEPARATOR).append(getBundleAndFormat(value));
            return this;
        } else {
            builder.append(PropertyUtils.getString(PROPERTIES, key.msgKey())).append(VALUE_SEPARATOR).append(value);
            return this;
        }
    }

    public EventLogBuilder appendAndFormat(String key, Object... values) {
        if (builder.length() > 0) {
            builder.append(KEY_SEPARATOR);
        }
        builder.append(getBundleAndFormat(key, values));
        return this;
    }

    public static String[] getChangeLogData(EventDetailsKey fieldNameKey, Object oldValue, Object newValue) {
        String[] changeLogDataArray = new String[3];
        changeLogDataArray[0] = getBundleAndFormat(fieldNameKey.msgKey());
        changeLogDataArray[1] = oldValue == null ? "" : oldValue.toString();
        changeLogDataArray[2] = newValue == null ? "" : newValue.toString();
        return changeLogDataArray;
    }

    public static String[] getChangeLogData(EventDetailsKey fieldNameKey, Date oldValue, Date newValue) {
        String dateFormat = DateUtils.DATE_TIME_FORMAT;
        String[] changeLogDataArray = new String[3];
        if (fieldNameKey.msgKey().compareTo("EventDetailsKey.BO_VERSION_DATE") == 0
                || fieldNameKey.msgKey().compareTo("EventDetailsKey.BO_CAPTURE_DATE") == 0) {
            dateFormat = DateUtils.DATE_FORMAT;
        }
        changeLogDataArray[0] = getBundleAndFormat(fieldNameKey.msgKey());
        changeLogDataArray[1] = DateUtils.formatDate(oldValue, dateFormat);
        changeLogDataArray[2] = DateUtils.formatDate(newValue, dateFormat);
        return changeLogDataArray;
    }

    /**
     * Pobranie informacji o modyfikacji pola przechowującego wartość boolowską.
     *
     * @param fieldNameKey klucz do nazwy pola
     * @param oldValue poprzednia wartość
     * @param newValue aktualna wartość
     * @return informacje o modyfikacji pola
     */
    public static String[] getChangeLogData(EventDetailsKey fieldNameKey, boolean oldValue, boolean newValue) {
        String[] changeLogDataArray = new String[3];
        changeLogDataArray[0] = getBundleAndFormat(fieldNameKey.msgKey());
        changeLogDataArray[1] = oldValue ? getBundleAndFormat("boolean.true") : getBundleAndFormat("boolean.false");
        changeLogDataArray[2] = newValue ? getBundleAndFormat("boolean.true") : getBundleAndFormat("boolean.false");
        return changeLogDataArray;
    }

    /**
     * Pobranie ciągu tekstowego na podstawie klucza i sformatownie go z użyciem podanych wartości.
     *
     * @param key klucz do ciągu tekstowego
     * @param values wartości
     * @return
     */
    public static String getBundleAndFormat(String key, Object... values) {
        return PropertyUtils.getString(PROPERTIES, key, values);
    }

    /**
     * Sprawdzenie czy wskazane obiekty są różne.
     *
     * @param a obiekt pierwszy
     * @param b obiekt drugi
     * @return true jesli obiekty są różne
     */
    public static boolean fieldsNotEquals(Object a, Object b) {
        if (isEmpty(a) && isEmpty(b)) {
            return false;
        }
        if ((!isEmpty(a) && isEmpty(b)) || (isEmpty(a) && !isEmpty(b))) {
            return true;
        }
        return !a.equals(b);
    }

    /**
     * Sprawdzenie czy obiekt jest pusty. Jeżeli ciąg tekstowy uzyskany z przekazanego obiektu nie zawiera nic prócz
     * białych znaków to sam obiekt traktowany jest jako pusty.
     *
     * @param a obiekt
     * @return true jeśli obiekt jest pusty
     */
    public static boolean isEmpty(Object a) {
        return a == null || a.toString().trim().length() == 0;
    }

    /**
     * Pobranie aktualnie przechowywanego ciągu tekstowego.
     *
     * @return aktualny ciąg tekstowy
     */
    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * Klucze nazw pól mogących ulec modyfikacji oraz pozostałych ciągów tekstowych wykożystywanych przy budowie
     * szczegółów zdarzń.
     */
    public static enum EventDetailsKey {
        // ==================== Rejestr ========================

        /**
         * Typ danych
         */
        DATA_TYPE,
        /**
         * Status przetworzenia
         */
        PROCESSING_STATUS,
        /**
         * Status poprawności
         */
        VALIDATION_STATUS,
        /**
         * Data
         */
        TRANSACTION_DATE,
        /**
         * Data zasilenia
         */
        DATE_SUPPLY,
        // ==================== Transakcje ====================
        // ======== Transakcje: Podstawowe dane transakcji ============
        /**
         * Wewnętrzny identyfikator transakcji
         */
        ORIGINAL_ID,
        /**
         * Identyfikator transakcji
         */
        SOURCE_TRANS_ID,
        /**
         * Poprzedni identyfikator transakcji
         */
        PREVIOUS_SOURCE_TRANS_ID,
        /**
         * Numer referencyjny transakcji
         */
        SOURCE_TRANS_REF_NR,
        /**
         * Identyfikator kontrahenta
         */
        ORIGINAL_CLIENT_ID,
        /**
         * Identyfikator 2 kontrahenta np. BTMU
         */
        ORIGINAL_CLIENT_ID2,        
        /**
         * Strona transakcji
         */
        TRANSACTION_PARTY,
        /**
         * Transakcja potwierdzona
         */
        CONFIRMED,
        // ================== Transakcje: Wycena =====================
        // zdefiniowane w zesatawach
        /*
         AMOUNT,
         CURRENCY_CODE,
         VALUATION_DATE,
         VALUATION_TYPE,*/
        /**
         * Wycena kontrahenta
         */
        AMOUNT_CLIENT,
        // ================= Transakcje: Zabezpieczenia ==============
        /**
         * Dokonano zabezpieczenia
         */
        PROTECTION,
        /**
         * Dokonano zabezpieczenia na poziomie portfela
         */
        WALLET_PROTECTION,
        /**
         * Kod portfela
         */
        WALLET_ID,
        // zdefiniowane w zesatawach
        /*PROTECTION_AMOUNT,
         PROTECTION_CURRENCYCODE,*/
        // ====================== Transakcje: Dane ogólne ====================
        /**
         * Kontrahent 2 - Dane brokera - Kod identyfikacyjny
         */
        B_ID_CODE,
        /**
         * Kontrahent 2 - Dane brokera - Typ identyfikacyjny
         */
        B_ID_CODE_TYPE,
        /**
         * Klient - Dane brokera - Kod identyfikacyjny
         */
        C_ID_CODE,
        /**
         * Klient - Dane brokera - Typ identyfikacyjny
         */
        C_ID_CODE_TYPE,
        /**
         * Kontrahent 2 - Dane instytucji raportującej - Kod identyfikacyjny
         */
        B_BENEFICIARY_CODE,
        /**
         * Kontrahent 2 - Dane instytucji raportującej - Typ identyfikacyjny
         */
        B_BENEFICIARY_CODE_TYPE,
        /**
         * Klient - Dane instytucji raportującej - Kod identyfikacyjny
         */
        C_BENEFICIARY_CODE,
        /**
         * Klient - Dane instytucji raportującej - Typ identyfikacyjny
         */
        C_BENEFICIARY_CODE_TYPE,
        // zdefiniowane w zesatawach
        /*
         MEMBER_ID,
         MEMBER_ID_TYPE,*/
        /**
         * Identyfikator konta rozliczającego
         */
        SETTLING_ACCOUT,
        /**
         * Charakter, w jakim zawarto transakcję
         */
        TRANSACTION_TYPE,
        /**
         * Bezpośredni związek z działalnością gospodarczą
         */
        COMMERCIAL_ACTITY,
        /**
         * Próg związany z obowiązkiem rozliczania
         */
        SETTLEMENT_THRESHOLD,
        /**
         * Kod portfela zabezpieczeń
         */
        COMM_WALLET_CODE,
        //=========== Transakcje: Typ kontraktu ========================
        // zdefiniowane w zesatawach
        /*
         CONTRACT_TYPE,
         PROD1_CODE,
         PROD2_CODE,
         UNDERLYING_ID,*/
        /**
         * Kod kraju emitenta instrumentu bazowego
         */
        UNDERL_COUNTRY_CODE,
        /**
         * Waluta nominalna 1
         */
        UNDERL_CURRENCY1_CODE,
        /**
         * Waluta nominalna 2
         */
        UNDERL_CURRENCY2_CODE,
        /**
         * Waluta dostawy
         */
        DELIV_CURRENCY_CODE,
        /**
         * Miejsce realiazacji
         */
        REALIZATION_VENUE,
        /**
         * Kompresja
         */
        COMPRESSION,
        /**
         * Cena
         */
        UNIT_PRICE,
        /**
         * Stawka procentowa
         */
        UNIT_PRICE_RATE,
        /**
         * Kwota nominalna
         */
        NOMINAL_AMOUNT,
        /**
         * Mnożnik ceny
         */
        PRICE_MULTIPLIER,
        /**
         * Liczba kontraktów w zgłoszeniu
         */
        CONTRACT_COUNT,
        /**
         * Płatność z góry
         */
        IN_ADVANCE_AMOUNT,
        /**
         * Typ dostawy
         */
        DELIV_TYPE,
        /**
         * Data i czas realizacji transakcji
         */
        EXECUTION_DATE,
        /**
         * Data wejścia w życie obowiążków wynikających z kontraktu
         */
        EFFECTIVE_DATE,
        /**
         * Termin zapadalności
         */
        MATURITY_DATE,
        /**
         * Data rozwiązania zgłaszanego kontraktu
         */
        TERMINATION_DATE,
        /**
         * Data rozrachunku instrumentu bazowego
         */
        SETTLEMENT_DATE,
        /**
         * Data rozrachunku instrumentu bazowego. Dluga noga SWAP
         */
        SETTLEMENT_DATE_2,
        /**
         * Rodzaj umowy ramowej
         */
        FRAMEWORK_AGGR_TYPE,
        /**
         * Wersja umowy ramowej
         */
        FRAMEWORK_AGGR_VER,
        /**
         * Znacznik czasu potwierdzenia
         */
        CONFIRMATION_DATE,
        /**
         * Sposób dokonania potwierdzenia
         */
        CONFIRMATION_TYPE,
        /**
         * Obowiązek rozliczenia
         */
        SETTLEMENT_OBLIG,
        /**
         * Rozliczono
         */
        SETTLED,
        /**
         * Znacznik czasu rozliczenia
         */
        CLEARING_DATE,
        /**
         * CCP
         */
        CCP_CODE,
        /**
         * Transakcja wewnątrzgrupowa
         */
        INTERGROPU_TRANS,
        // =========== Transakcje: Instrumenty pochodne ======================
        /**
         * Stała stopa procentowa - "Noga 1"
         */
        FIXED_RATE_LEG1,
        /**
         * Stała stopa procentowa - "Noga 2"
         */
        FIXED_RATE_LEG2,
        /**
         * Długość okresu stosowania stałej stopy procentowej
         */
        FIXED_RATE_DAY_COUNT,
        /**
         * Częstotliwość płatności - noga o stałym oprocentowaniu
         */
        FIXED_PAYMENT_FREQ,
        /**
         * Częstotliwość płatności - noga o zmiennym oprocentowaniu
         */
        FLOAT_PAYMENT_FREQ,
        /**
         * Częstotliwość płatności na nowo zmiennej stopy procentowej
         */
        NEW_PAYMENT_FREQ,
        /**
         * Zmienna stopa procentowa części ("noga") 1
         */
        FLOAT_RATE_LEG1,
        /**
         * Zmienna stopa procentowa części ("noga") 2
         */
        FLOAT_RATE_LEG2,
        /**
         * Inna waluta transakcji
         */
        CURRENCY_TRADE_CODE,
        /**
         * Kurs walutowy 1
         */
        CURR_TRADE_EXCHR_RATE,
        /**
         * Terminowy kurs walutowy
         */
        CURR_TRADE_FRWD_RATE,
        /**
         * Podstawa kursu walutowego
         */
        CURR_TRADE_BASIS,

        /**
         * Status transakcji
         */
        ORIGINAL_STATUS,
        /**
         * Kod identyfikacyjny
         */
        MEMBER_ID,
        /**
         * Typ identyfikacyjny
         */
        MEMBER_ID_TYPE,
        /**
         * Kontrahent 2 - Dane instytucji rozliczającej - Kod identyfikacyjny
         */
        B_MEMBER_ID,
        /**
         * Kontrahent 2 - Dane instytucji rozliczającej - Typ identyfikacyjny
         */
        B_MEMBER_ID_TYPE,
        /**
         * Klient - Dane instytucji rozliczającej - Kod identyfikacyjny
         */
        C_MEMBER_ID,
        /**
         * Klient - Dane instytucji rozliczającej - Typ identyfikacyjny
         */
        C_MEMBER_ID_TYPE,
        /**
         * Stosowana klasyfikacja
         */
        CONTRACT_TYPE,
        /**
         * Kod identyfikacyjny produktu 1
         */
        PROD1_CODE,
        /**
         * Kod identyfikacyjny produktu 2
         */
        PROD2_CODE,
        /**
         * Instrument bazowy
         */
        UNDERLYING_ID,
        /**
         * Wartość wyceny
         */
        AMOUNT,
        /**
         * Waluta wyceny
         */
        CURRENCY_CODE,
        /**
         * Data i godzina wyceny
         */
        VALUATION_DATE,
        /**
         * Rodzaj wyceny
         */
        VALUATION_TYPE,
        /**
         * Wartość wyceny dla drugiej strony transakcji
         */
        CLIENT_AMOUNT,
        /**
         * Waluta zabezpieczenia
         */
        PROTECTION_CURRENCY_CODE,
        /**
         * Wartość zabezpieczenia
         */
        PROTECTION_AMOUNT,
        /**
         * Kod portfela analitycznego
         */
        ANALYT_WALLET_CODE,
        /**
         * Kod portfela syntetycznego
         */
        SYNT_WALLET_CODE,
        // ==================== Kontrahenci ====================
        /**
         * Id kontrahenta
         */
        CLIENT_ID,
        /**
         * Nazwa klienta
         */
        CLIENT_NAME,
        /**
         * Raportowany - czy kontrahent 2 (BTMU) przekazuje informacje w imieniu kontrahenta
         */
        REPORTED,
        /**
         * EOG
         */
        EOG,
        /**
         * NATURAL_PERSON
         */
        NATURAL_PERSON,
        /**
         * Identyfikator ESMA.
         */
        SENDER_ID,
        /**
         * Identyfikator KDPW_TR.
         */
        SENDER_ID_KDPW,
        /**
         * Kod kraju.
         */
        COUNTRY_CODE,
        /**
         * Branża.
         */
        CONTR_PARTY_INDUSTRY,
        /**
         * Kontrahent.
         */
        CONTR_PARTY_TYPE,
        /**
         * Data raportowania wyceny.
         */
        VALUATION_RAPORTING_DATE,
        /**
         * Nr NIP.
         */
        SUBJECT_NIP,
        /**
         * Nr REGON.
         */
        SUBJECT_REGON,
        /**
         * Identyfikator.
         */
        INSTITUTION_ID,
        /**
         * Typ identyfikatora.
         */
        INSTITUTION_ID_TYPE,
        /**
         * Kod pocztowy.
         */
        POSTAL_CODE,
        /**
         * Miasto.
         */
        CITY,
        /**
         * Ulica.
         */
        STREET_NAME,
        /**
         * Nr budynku.
         */
        BUILDING_ID,
        /**
         * Nr lokalu.
         */
        PREMISES_ID,
        /**
         * Inne.
         */
        DETAILS,
        // ==================== Raporty ====================
        /**
         * Nazwa ogólna raportu
         */
        RAPORT,
        /**
         * Nazwa parametr
         */
        PARAMETER,
        /**
         * Nazwa wygenerowanie raportu liczby transakcji
         */
        REPORT_NAME_OVERFLOW,
        /**
         * Nazwa wygenerowanego raportu sumy transakcji
         */
        REPORT_NAME_THRESHOLD,
        /**
         * Raport - data od
         */
        REPORT_DATE_FROM,
        /**
         * Raport - data do
         */
        REPORT_DATE_TO,
        /**
         * Raport - typ instrumentu
         */
        TYPE_INSTRUMENT,
        /**
         * Raport - klient
         */
        CLIENT,
        // ==================== Import ekstraktu ====================
        /**
         * Ekstrakt
         */
        EXTRACT,
        /**
         * Ekstrakt - status
         */
        EXTRACT_STATUS,
        // ==================== TransactionAdditionalData ====================
        /**
         * Id kontraktu.
         */
        CONTRACT_ID,
        /**
         * BlockNumber.
         */
        BLOCK_NUMBER,
        /**
         * DealStatus.
         */
        DEAL_STATUS,
        /**
         * BOVersionDate.
         */
        BO_VERSION_DATE,
        /**
         * Validated.
         */
        VALIDATED,
        /**
         * BODealSourceTypeOfEvent.
         */
        BO_DEAL_SOURCE_TYPE_EVENT,
        /**
         * InternalExternal.
         */
        INTERNAL_EXTERNAL,
        /**
         * BOCaptureDate.
         */
        BO_CAPTURE_DATE,
        /**
         * EventDealDataFoldersSN.
         */
        EVENT_DEAL_DATA_FOLDERS,
        /**
         * SubType.
         */
        PROD_SUBTYPE,
        /**
         * CptyShortName.
         */
        CPTY_SHORT_NAME,
        /**
         * ThirdCptyID.
         */
        THIRD_CPTY_ID,
        /**
         * NrKKICBS.
         */
        NR_KKICBS,
        /**
         * Słownik.
         */
        DICTIONARY_NAME,
        /**
         * Produkt.
         */
        PRODUCT,
        REPORT_STATUS,
        CONTROL_STATUS,
        CONTRACTTYPE,
        CONTRACTTYPE1,
        CONTRACTTYPE2,
        INADVANCECURRENCY,
        ADDITIONALUNITPRICECURRENCY,
        // ==================== Użytkownik ====================
        INITLMRGNPSTD,
        INITLMRGNRCVD,
        VARTNMRGNPSTD,
        VARTNMRGNRCVD,
        XCSSCOLLPSTD,
        XCSSCOLLRCVD,
        /**
         * Login
         */
        LOGIN,
        /**
         * Role
         */
        ROLES,;

        public String msgKey() {
            return getClass().getSimpleName() + "." + name();
        }
    }
}
