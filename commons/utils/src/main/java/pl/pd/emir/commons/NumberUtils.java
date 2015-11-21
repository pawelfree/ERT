package pl.pd.emir.commons;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public final class NumberUtils {

    private NumberUtils() {
        super();
    }

    /**
     * Konwersja long do int;
     *
     * @param longValue Wartosc do zamiany
     * @return integer
     * @throws IllegalArgumentException Jeżeli wartośc long nie odpiwiada min i max dla integer.
     */
    public static int safeLongToInt(final long longValue) {
        if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(longValue + " cannot be cast to int without changing its value.");
        }
        return (int) longValue;
    }

    /**
     * Konwersja Integer do BigInteger.
     *
     * @param value
     * @return
     */
    public static BigInteger integerToBigInteger(final Integer value) {
        BigInteger result = null;
        if (Objects.nonNull(value)) {
            result = new BigInteger(Integer.toString(value));
        }
        return result;
    }

    /**
     * Konwersja Integer do BigInteger.
     *
     * @param value
     * @return
     */
    public static BigDecimal integerToBigDecimal(final Integer value) {
        BigDecimal result = null;
        if (Objects.nonNull(value)) {
            result = new BigDecimal(Integer.toString(value));
        }
        return result;
    }
}
