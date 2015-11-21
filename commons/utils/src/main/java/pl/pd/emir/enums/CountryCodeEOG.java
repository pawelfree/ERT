package pl.pd.emir.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CountryCodeEOG {

    AT, BE, BG, CY, CZ, DK, EE, FI, FR, GR, HS, NL, IE, IS, LI, LT, LU, LV, MT, DE, NO, PL, PT, RO, SK, SI, SE, HU, GB, IT;

    public static CountryCodeEOG fromString(String value) {
        for (CountryCodeEOG countryCode : CountryCodeEOG.values()) {
            if (countryCode.toString().equals(value)) {
                return countryCode;
            }
        }
        return null;
    }

    public static boolean isFromString(String value) {
        for (CountryCodeEOG countryCode : CountryCodeEOG.values()) {
            if (countryCode.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static List<CountryCodeEOG> getFilterList() {
        final ArrayList<CountryCodeEOG> result = new ArrayList<>();
        result.addAll(Arrays.asList(CountryCodeEOG.values()));
        return result;
    }
}
