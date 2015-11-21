package pl.pd.emir.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {

    public static boolean validREGON(String regon) {
        return (validREGON7(regon) || validREGON9(regon) || validREGON14(regon));
    }

    public static boolean validNIP(String nip) {
        if (nip.length() == 13) {
            nip = nip.replaceAll("-", "");
        }
        nip = nip.trim();

        if (nip.length() != 10) {
            return false;
        }
        final List<Integer> weights = new ArrayList<>();
        weights.add(6);
        weights.add(5);
        weights.add(7);
        weights.add(2);
        weights.add(3);
        weights.add(4);
        weights.add(5);
        weights.add(6);
        weights.add(7);

        String[] aNip = nip.split("");

        //obejscie problemu pustego pierwszego elementu po nip.split (zależy od wersji javy?)
        if (aNip[0].isEmpty()) {
            aNip = Arrays.copyOfRange(aNip, 1, aNip.length);
        }

        int i = 0;
        try {
            int sum = 0;
            for (Integer weight : weights) {
                sum += Integer.parseInt(aNip[i]) * weight;
                i++;
            }
            sum = sum % 11;
            return sum == Integer.parseInt(aNip[i]);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Walidator numeru PESEL.
     *
     * @param pesel numer PESEL
     * @return true jeżeli numer PESEL jest poprawny, w przeciwnym wypadku false
     */
    public static boolean validPESEL(String pesel) {
        if (pesel == null || pesel.length() != 11) {
            return false;
        }
        try {
            final List<Integer> weights = new ArrayList<>();
            weights.add(1);
            weights.add(3);
            weights.add(7);
            weights.add(9);
            weights.add(1);
            weights.add(3);
            weights.add(7);
            weights.add(9);
            weights.add(1);
            weights.add(3);
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Integer.parseInt(pesel.substring(i, i + 1)) * weights.get(i);
            }
            int control = Integer.parseInt(pesel.substring(10, 11));
            sum %= 10;
            sum = 10 - sum;
            sum %= 10;
            return (sum == control);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validDO(String id) {
            final Map<Character, Integer> valueMap = createValueMap();
            final List<Integer> weights = new ArrayList<>();
            weights.add(7);
            weights.add(3);
            weights.add(1);
            weights.add(0);
            weights.add(7);
            weights.add(3);
            weights.add(1);
            weights.add(7);
            weights.add(3);

            int sum = 0;
            for (int i = 0; i < id.length(); i++) {
                if (i == 3) {
                    continue;
                }
                char c = id.charAt(i);
                Integer ii = valueMap.get(c);
                sum += weights.get(i) * ii;
            }
            sum %= 10;
            return sum == Character.getNumericValue(id.charAt(3)) - Character.getNumericValue((char) '0');
    }

    public static boolean validPassport(String id) {
            final Map<Character, Integer> valueMap = createValueMap();
            final List<Integer> weights = new ArrayList<>();
            weights.add(7);
            weights.add(3);
            weights.add(9);
            weights.add(1);
            weights.add(7);
            weights.add(3);
            weights.add(1);
            weights.add(7);
            weights.add(3);

            int sum = 0;

            for (int i = 0; i < id.length(); i++) {
                if (i == 2) {
                    continue;
                }
                char c = id.charAt(i);
                Integer ii = valueMap.get(c);
                sum += weights.get(i) * ii;
            }
            sum %= 10;
            return sum == Character.getNumericValue(id.charAt(2)) - Character.getNumericValue((char) '0');
    }

    private static Map<Character, Integer> createValueMap() {
        Map<Character, Integer> valueMap = new HashMap<>();
        for (Character c = 'A'; c <= 'Z'; c++) {
            valueMap.put(c, 10 + (Character.getNumericValue(c) - Character.getNumericValue((char) 'A')));
        }
        for (Character c = '0'; c <= '9'; c++) {
            valueMap.put(c, (Character.getNumericValue(c - Character.getNumericValue((char) '0'))));
        }
        return valueMap;
    }

    private static boolean validREGON7(String regon) {
        if (regon == null || regon.length() != 7) {
            return false;
        }
        try {
            final List<Integer> weights = new ArrayList<>();
            weights.add(2);
            weights.add(3);
            weights.add(4);
            weights.add(5);
            weights.add(6);
            weights.add(7);

            int sum = 0;
            for (int i = 0; i < 6; i++) {
                sum += Integer.parseInt(regon.substring(i, i + 1)) * weights.get(i);
            }
            sum %= 11;
            sum %= 10;
            return sum == Integer.parseInt(regon.substring(6, 7));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validREGON9(String regon) {
        if (regon == null || regon.length() != 9) {
            return false;
        }
        try {
            final List<Integer> weights = new ArrayList<>();
            weights.add(8);
            weights.add(9);
            weights.add(2);
            weights.add(3);
            weights.add(4);
            weights.add(5);
            weights.add(6);
            weights.add(7);

            int sum = 0;
            for (int i = 0; i < 8; i++) {
                sum += Integer.parseInt(regon.substring(i, i + 1)) * weights.get(i);
            }
            sum %= 11;
            sum %= 10;
            return sum == Integer.parseInt(regon.substring(8, 9));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validREGON14(String regon) {
        if (regon == null || regon.length() != 14) {
            return false;
        }
        try {
            final List<Integer> weights = new ArrayList<>();
            weights.add(2);
            weights.add(4);
            weights.add(8);
            weights.add(5);
            weights.add(0);
            weights.add(9);
            weights.add(7);
            weights.add(3);
            weights.add(6);
            weights.add(1);
            weights.add(2);
            weights.add(4);
            weights.add(8);
            int sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Integer.parseInt(regon.substring(i, i + 1)) * weights.get(i);
            }
            sum %= 11;
            sum %= 10;
            return sum == Integer.parseInt(regon.substring(13, 14));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validBIC(String bic) {
        boolean result = false;
        if (bic.length() == 8) {
            if (bic.matches("([A-Z]{4}[A-Z]{2}[A-Z2-9][O])")) {
                result = true;
            }
        }
        if (bic.length() == 11) {
            if (bic.matches("([A-Z]{4}[A-Z]{2}[A-Z2-9][O]([A-Z0-9]{3}))")) {
                result = true;
            }
        }
        return result;
    }
}
