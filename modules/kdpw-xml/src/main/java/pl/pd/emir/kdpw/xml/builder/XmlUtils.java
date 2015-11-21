package pl.pd.emir.kdpw.xml.builder;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.StringUtil;

public class XmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

    public static String getIsoDate(final Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE);
        return formatter.format(date);
    }

    public static String getIsoDateTime(final Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE_TIME);
        return formatter.format(date);
    }

    public static boolean allEmpty(final Object... values) {
        boolean result = true;
        for (Object object : values) {
            if (object != null) {
                if (object instanceof String) {
                    result = StringUtil.isEmpty((String) object);
                } else {
                    result = false;
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public static boolean notAllEmpty(final Object... values) {
        return !allEmpty(values);
    }

    public static boolean allNotEmpty(final Object... values) {
        boolean result = true;
        for (Object object : values) {
            if (object == null) {
                result = false;
            } else {
                if (object instanceof String) {
                    result = StringUtil.isNotEmpty((String) object);
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public static String enumName(final Enum value) {
        String result = null;
        if (value != null) {
            return value.name();
        }
        return result;
    }

    public static String booleanAsString(final boolean value) {
        return value ? "1" : "0";
    }

    public static String booleanAsYorN(final Boolean value) {
        if (value == null) {
            return null;
        }
        if (value) {
            return "Y";
        }
        return "N";
    }

    public static XMLGregorianCalendar formatDate(final Date date, final String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String stringDate = sdf.format(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(stringDate);
        } catch (DatatypeConfigurationException ex) {
            LOGGER.error("FormatDateException: " + ex);
            return null;
        }
    }

    public static Date getDate(final XMLGregorianCalendar calendar) {
        if (calendar != null) {
            return calendar.toGregorianCalendar().getTime();
        }
        return null;
    }
}
