package pl.pd.emir.commons.commonutils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);
    private static final char[] specialChars = {'\'', '!', '@', '#', '$', '%', '^', '&', '*', '/', '(', ')', '<', '>', '-', '_', '+', '=', ':', ';', '?', '"', '.'};
    private static final char[] polishChars = {'\u0105', '\u015b', '\u0142', '\u0119', '\u00f3', '\u017c', '\u017A', '\u0107', '\u0144',
        '\u0104', '\u015a', '\u0141', '\u0118', '\u00d3', '\u017b', '\u0179', '\u0106', '\u0143'};
    private static final String KEY = "5fcb9962cb7482e77b380161f9e4041f";

    public static String getSha1EncodedPassword(String clearTextPassword) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(clearTextPassword.getBytes("UTF-8"));
            result = Base64.encodeToString(hashBytes, false);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        }
        if (result != null && !result.isEmpty()) {
            result = result.replaceAll("\\r", "");
            result = result.replaceAll("\\n", "");
        }
        return "{SHA-1}" + result;
    }

    public static String getRandomPassword(final int length) {
        try {

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            char lowerChars[] = getCharactersTab(TabType.lowerChars).toCharArray();
            char upperChars[] = getCharactersTab(TabType.upperChars).toCharArray();
            char numbers[] = getCharactersTab(TabType.numbers).toCharArray();
            int tabCount = TabType.values().length - 1;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int tabNumber = random.nextInt(tabCount);
                int pos;
                switch (tabNumber) {
                    case 0:
                        pos = random.nextInt(lowerChars.length);
                        sb.append(lowerChars[pos]);
                        break;
                    case 1:
                        pos = random.nextInt(upperChars.length);
                        sb.append(upperChars[pos]);
                        break;
                    case 2:
                        pos = random.nextInt(numbers.length);
                        sb.append(numbers[pos]);
                        break;
                    case 3:
                        pos = random.nextInt(specialChars.length);
                        sb.append(specialChars[pos]);
                        break;
                    case 4:
                        pos = random.nextInt(polishChars.length);
                        sb.append(polishChars[pos]);
                        break;
                }
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    private enum TabType {

        lowerChars(0), upperChars(1), numbers(2), specialChars(3), polishChars(4);
        private final int nr;

        private TabType(int nr) {
            this.nr = nr;
        }

        public int getNr() {
            return nr;
        }
    }

    private static String getCharactersTab(TabType type) {
        String str;
        switch (type) {
            case lowerChars:
                str = getCharactersTab(97, 122);
                break;
            case upperChars:
                str = getCharactersTab(65, 90);
                break;
            case numbers:
                str = getCharactersTab(48, 57);
                break;
            case specialChars:
                str = getCharactersTab(32, 47) + getCharactersTab(58, 64) + getCharactersTab(91, 96);
                break;
            default:
                str = "";
        }
        return str;
    }

    private static String getCharactersTab(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int a = 0, i = start; i <= end; i++, a++) {
            sb.append((char) i);
        }
        return sb.toString();
    }

    public static boolean verifyPassword(String passwd) {
        boolean correct = false;
        boolean passwdLongEnough = false;
        boolean passwdNotWhitespace = true;
        boolean passwdLowerCase = false;
        boolean passwdUpperCase = false;
        boolean passwdDigit = false;
        boolean passwdHasPolishChar = false;

        if ((passwd.length() > 7) && (passwd.length() < 20)) {
            passwdLongEnough = true;
        }

        for (int i = 0; i < passwd.length(); i++) {
            Character c = passwd.charAt(i);

            if (Character.isDigit(c) || isSpecialChar(c)) {
                passwdDigit = true;
            }
            if (Character.isWhitespace(c)) {
                passwdNotWhitespace = false;
            }
            if (Character.isLowerCase(c)) {
                passwdLowerCase = true;
            }
            if (Character.isUpperCase(c)) {
                passwdUpperCase = true;
            }
            if (isPolishChar(c)) {
                passwdHasPolishChar = true;
            }
        }

        if (passwdLongEnough && passwdLowerCase && passwdUpperCase && passwdDigit && passwdNotWhitespace && !passwdHasPolishChar) {
            correct = true;
        }

        return correct;
    }

    private static boolean isSpecialChar(Character c) {
        for (int i = 0; i < specialChars.length; i++) {
            if (c == specialChars[i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPolishChar(Character c) {
        for (int i = 0; i < polishChars.length; i++) {
            if (c == polishChars[i]) {
                return true;
            }
        }
        return false;
    }

    public static String encrypt(String data) throws Exception {
        byte[] rawKey = asBytes(KEY);
        SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        return asHex(encrypted);
    }

    public static String decryptData(String data) throws Exception {
        byte[] rawKey = asBytes(KEY);
        SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(asBytes(data));
        return new String(original);
    }

    public static String generateAESKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey skey = kgen.generateKey();
        byte key[] = skey.getEncoded();
        return asHex(key);
    }

    private static String asHex(byte[] buf) {
        StringBuilder strbuf = new StringBuilder(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private static byte[] asBytes(String strHex) {
        char[] hex = strHex.toCharArray();
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127) {
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }
}
