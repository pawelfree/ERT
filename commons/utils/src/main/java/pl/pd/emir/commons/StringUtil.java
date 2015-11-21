package pl.pd.emir.commons;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public final class StringUtil {

    private StringUtil() {
        super();
    }

    public static String getValue(Object obj, final int length) {
        if (null != obj && !("null".equalsIgnoreCase(obj.toString()))) {
            if (length < obj.toString().length()) {
                return obj.toString().substring(0, length);
            } else {
                return obj.toString();
            }
        }
        return "";
    }

    public static StringBuilder setPipe(int part, StringBuilder _sb) {
        int i = 0, j;
        while (true) {
            j = (((i + 1) * part) + (i++));
            if (j > _sb.length()) {
                break;
            }
            _sb.insert(j, "|");
        }
        return _sb;
    }

    public static String stripWhitespaces(final String value) {
        return value.replaceAll("\\s+", "");
    }

    /**
     * sprawdza czy string jest null, pusty lub składa się z samych pustych znaków
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean isWhiteSpacesOnly(String string) {
        CharSequence chars = string;
        for (int i = 0; i < chars.length(); i++) {
            if (Character.isWhitespace(chars.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(final String string) {
        return isEmpty(string) || isWhiteSpacesOnly(string);
    }

    public static boolean isNotEmpty(final String value) {
        return !isEmpty(value);
    }

    public static String printCollection(final Collection collection) {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (collection != null && !collection.isEmpty()) {
            for (final Iterator it = collection.iterator(); it.hasNext();) {
                builder.append(it.next());
                builder.append(", ");
            }
        }
        if (builder.length() > 1) {
            builder.replace(builder.length() - 2, builder.length(), "]");
        } else {
            builder.append("]");
        }
        return builder.toString();
    }

    public static InputStream toInputStream(final String value) {
        InputStream result;
        if (isEmpty(value)) {
            result = new ByteArrayInputStream("".getBytes());
        } else {
            result = new ByteArrayInputStream(value.getBytes());
        }
        return result;
    }

    public static String shorten(String string, int otputLength) {
        if (string != null && string.length() > otputLength) {
            return string.substring(0, otputLength - 1);
        } else {
            return string;
        }
    }

    public static String readStream(final InputStream inputStream) throws IOException {
        final InputStreamReader inputReader = new InputStreamReader(inputStream);
        final StringBuilder builder = new StringBuilder();
        final BufferedReader bReader = new BufferedReader(inputReader);
        String read = bReader.readLine();
        while (read != null) {
            builder.append(read).append("\r\n");
            read = bReader.readLine();
        }
        return builder.toString();
    }

    public static String generateCollection(final List<?> list) {
        final StringBuilder result = new StringBuilder();
        if (list == null || list.isEmpty()) {
            result.append("()");
        } else {
            result.append("( ");
            for (final Iterator it = list.iterator(); it.hasNext();) {
                final Object object = it.next();
                if (object.getClass().isEnum()) {
                    result.append(object.getClass().getCanonicalName()).append(".");
                }
                result.append(object.toString());
                if (it.hasNext()) {
                    result.append(" , ");
                }
            }
            result.append(" )");
        }
        return result.toString();
    }

    public static String getMessage(final ResourceBundle bundle, final String key, final Object... params) {
        String result;
        boolean error = false;
        if (bundle != null && bundle.containsKey(key)) {
            result = bundle.getString(key);
        } else {
            error = true;
            final StringBuilder builder = new StringBuilder();
            builder.append(key);
            for (Object param : params) {
                builder.append(" ");
                builder.append(param);
            }
            result = "###" + builder.toString() + "###";
        }

        if (!error && params != null && params.length > 0 && params[0] != null) {
            result = MessageFormat.format(result, params);
        }
        return result;
    }

    public static String getNullOnEmpty(final String value) {
        if (isEmpty(value)) {
            return null;
        }
        return value;
    }
}
