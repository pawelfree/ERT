package pl.pd.emir.commons;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class NumberFormatUtils {

    public static int NUMBER_OF_FRACTION_DIGITS = 2;
    private static final String FORMAT = "###,##0.00";

    private static NumberFormat getNumberFormat() {
        final DecimalFormatSymbols dcf = new DecimalFormatSymbols();
        dcf.setGroupingSeparator(' ');
        dcf.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat(FORMAT, dcf);
        numberFormat.setMaximumFractionDigits(NUMBER_OF_FRACTION_DIGITS);
        numberFormat.setMinimumFractionDigits(NUMBER_OF_FRACTION_DIGITS);
        return numberFormat;
    }

    public static String format(final BigDecimal number) {
        return getNumberFormat().format(number);
    }

    public static String formatInteger(final Integer number) {
        DecimalFormatSymbols dcf = new DecimalFormatSymbols();
        dcf.setGroupingSeparator(' ');
        dcf.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat("###,##0", dcf);
        return numberFormat.format(number);
    }

    public static String formatFixedLength(final long number, final int length) {
        String result = "" + number;
        while (result.length() < length) {
            result = "0" + result;
        }
        return result;
    }

    public static String formatFixedLength(final String number, final int length) {
        String result = null == number ? "" : number;
        while (result.length() < length) {
            result = "0" + result;
        }
        return result;
    }
}
