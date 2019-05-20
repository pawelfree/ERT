package pl.pd.emir.imports;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ImportFaillogUtils {

    private static final String PROPERTYFILE = "import-faillog-details.properties";

    protected final static Properties PROPERTIES = new Properties();

    private static final Logger LOG = Logger.getLogger(ImportFaillogUtils.class.getName());

    static {
        try (InputStream inputStream = ImportFaillogUtils.class.getClassLoader().getResourceAsStream(PROPERTYFILE)) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            LOG.info("Cannot load property file: " + PROPERTYFILE);
        }
    }

    public static String getString(ImportFaillogKey key) {
        String property = PROPERTIES.getProperty(key.getMsgKey());
        if (null == property) {
            return String.format("??? %s ???", key.getMsgKey());
        } else {
            return property;
        }
    }

    public static String getString(ImportFaillogKey key, Object... params) {
        if (PROPERTIES.containsKey(key.getMsgKey())) {
            String string = PROPERTIES.getProperty(key.getMsgKey());
            return String.format(string, params);
        } else {
            return String.format("??? %s ???", key.getMsgKey());
        }
    }

    /**
     * Klucze ostrzeżeń i błędów.
     */
    public enum ImportFaillogKey {

        /**
         * (w linii %s).
         */
        LINE_INDICATOR,
        /**
         * Błąd odczytu pliku.
         */
        IO_EXCEPION,
        /**
         * W lokalizacji źródłowej nie ma ekstraktu odpowiadającego podanym parametrom.
         */
        FILE_DOESNT_EXIST,
        /**
         * Ekstrakt źródłowy nie zawiera żadnych danych.
         */
        EMPTY_FILE,
        /**
         * Nieprawidłowa struktura pliku - niepoprawna liczba kolumn dla wiersza: %s.
         */
        INVALID_COLUMN_COUNT_ERROR,
        /**
         * Brak obowiązkowej wartości pola ID_KLIENTA dla linii: %s.
         */
        EMPTY_CLIENT_ID,
        /**
         * Brak obowiązkowej wartości pola ID_TR dla linii: %s.
         */
        EMPTY_TRANSACTION_ID,
        /**
         * Brak obowiązkowej wartości pola ID_TR dla linii: %s.
         */
        EMPTY_VALUATION_ID,
        /**
         * Brak obowiązkowej wartości pola ID_TR dla linii: %s.
         */
        EMPTY_PROTECTION_ID,
        /**
         * Transakcja o identyfikatorze %s i dacie %s nie istnieje w rejestrze.
         */
        TRANSACTION_DOES_NOT_EXIST,
        /**
         * Informacje o zabezpieczeniu dla transakcji %s na datę danych %s zostały już przekazane do KDPW. W celu
         * ponownego importu danych zabezpieczenia wymagane jest utworzenie mutacji transakcji na ten sam dzień roboczy.
         */
        TRANSACTION_ALREADY_HAS_PROTECTION,
        /**
         * Informacje o wycenie dla transakcji %s na datę danych %s zostały już przekazane do KDPW. W celu ponownego
         * importu danych wyceny wymagane jest utworzenie mutacji transakcji na ten sam dzień roboczy.
         */
        TRANSACTION_ALREADY_HAS_VALUATION,
        /**
         * Data transakcji %s niezgodna z datą wczytywanego ekstraktu.
         */
        INCOMPATIBILE_TRANSACTION_DATE,
        /**
         * Klient o id %s dla transakcji %s nie widnieje w bazie danych.
         */
        CLIENT_DOES_NOT_EXIST,
                /**
         * Klient 2 o id %s dla transakcji %s nie widnieje w bazie danych.
         */
        CLIENT2_DOES_NOT_EXIST;


        public String getMsgKey() {
            return String.format("%s.%s", this.getClass().getSimpleName(), this.name());
        }

    }

}
