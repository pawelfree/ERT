package pl.pd.emir.kdpw.service.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import pl.pd.emir.admin.MultiNumberGenerator;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.PropertyUtils;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Sendable;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageDirection;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.MultiGeneratorKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KdpwUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(KdpwUtils.class);

    private static final String PROPERTY_FILE = "kdpw-details.properties";

    private static final Properties PROPERTIES = PropertyUtils.getProperties(KdpwUtils.class, PROPERTY_FILE);

    public static Date getEligDate(final String dateAsString) {
        Date result = null;
        try {
            result = DateUtils.getDateFromString(dateAsString, Constants.ISO_DATE);
        } catch (ParseException ex) {
            LOGGER.error("Cannot parse value: " + dateAsString + " to date (" + Constants.ISO_DATE + ")");
        }
        return result;
    }

    /**
     * Zwraca numer wygenerowanego pliku do KDPW w fomracie yyMMdd + kolejny numer 6-cyfrowy w dniu.
     *
     * @param numberGenerator Manager numerów
     * @return Unikalny numer komunikatu
     */
    public static String getMsgLogNumber(final MultiNumberGenerator numberGenerator) {
        final Date date = new Date();
        final Long newNumber = numberGenerator.getNewNumber(date, MultiGeneratorKey.KDPW_FILE_NUMBER);
        return String.format("%s%s", DateUtils.formatDate(date, "yyMMdd"), newNumber);
    }

    public static boolean isNew(final Sendable transaction) {
        return DataType.NEW.equals(transaction.getDataType());
    }

    public static boolean isCompleted(final Sendable transaction) {
        return DataType.COMPLETED.equals(transaction.getDataType());
    }

    public static boolean isOngoing(final Sendable transaction) {
        return DataType.ONGOING.equals(transaction.getDataType());
    }

    public static boolean isCancelled(final Sendable transaction) {
        return DataType.CANCELLED.equals(transaction.getDataType());
    }

    public static boolean notEquals(final Object object1, final Object object2) {
        boolean result = true;
        if (Objects.isNull(object1)) {
            result = object2 != null;
        } else if (Objects.nonNull(object2)) {
            result = !object1.equals(object2);
        }
        return result;
    }

    public static <T extends Object> boolean isNotEqual(final T object1, final T object2, final Class<? extends Annotation> annotation) {
        if (Objects.isNull(object1)) {
            return Objects.nonNull(object2);
        } else if (Objects.isNull(object2)) {
            return false;
        }

        Class clazz = object1.getClass();
        while (clazz != Object.class) {
            final Field[] allFields = clazz.getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                if (Objects.nonNull(field.getAnnotation(annotation))) {
                    try {
                        final boolean notEquals = notEquals(field.get(object1), field.get(object2));
                        if (notEquals) {
                            LOGGER.debug("NOT equals field: {}", field.getName());
                            LOGGER.debug("VALUES: base: " + field.get(object1) + ", new: " + field.get(object2));
                            return notEquals;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException ex) {
                        LOGGER.error("Error: ", ex);
                    }
                } else if (isEmbedableObject(field)) {
                    try {
                        boolean notEquals = isNotEqual(field.get(object1), field.get(object2), annotation);
                        if (notEquals) {
                            LOGGER.debug("NOT equals field: {}", field.getName());
                            LOGGER.debug("VALUES: base: " + field.get(object1) + ", new: " + field.get(object2));
                            return notEquals;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException ex) {
                        LOGGER.error("Error: ", ex);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    protected static boolean isEmbedableObject(Field field) {
        return field.getType().getName().startsWith("pl.pd.") && !field.getType().isEnum();
    }

    public static boolean isReported(final Client client) {
        return client != null && client.getReported();
    }

    public static boolean isSameDateOrLater(final Date lastModifyDate, final Date transactionDate) {
        if (lastModifyDate == null || transactionDate == null) {
            return false;
        }
        final Date modifyDate = DateUtils.getDayBegin(lastModifyDate);
        final Date transDate = DateUtils.getDayBegin(transactionDate);
        return DateUtils.isSameDay(modifyDate, transDate) || modifyDate.after(transDate);
    }

    public static boolean allWithResponse(final List<KdpwMsgItem> list) {
        return list.stream().allMatch((item) -> (item.hasResponse()));
    }

    //TODO zalozone ze jesli nie zaakceptowane to odrzucone
    public static boolean allRejected(final List<KdpwMsgItem> list) {
        return list.stream().noneMatch((item) -> (item.isAccepted()));
    }

    public static boolean allAccepted(final List<KdpwMsgItem> list) {
        return list.stream().allMatch((item) -> (item.isAccepted()));
    }

    public static boolean allDisconnected(final List<KdpwMsgItem> list) {
        return list.stream().noneMatch((item) -> (item.isConnected()));
    }

    public static String getAllStatuses(final List<KdpwMsgItem> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= list.size(); i++) {
            KdpwMsgItem item = list.get(i - 1);
            String itemStatus = item != null && item.getStatus() != null ? item.getStatus().name() : "null";
            sb.append("item").append(i).append(" - ").append(itemStatus);
            if (i < list.size()) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static List<KdpwMsgItem> getRejected(final List<KdpwMsgItem> nonProcessed) {
        final List<KdpwMsgItem> result = new ArrayList<>();
        nonProcessed.stream().filter((item) -> (item.isRejected())).forEach((item) -> {
            result.add(item);
        });
        return result;
    }

    public static String getMsgs(final String key) {
        return PropertyUtils.getString(PROPERTIES, key);
    }

    public static String getVersionMsg(Transaction transaction) {
        return "wersja: " + transaction.getExtractVersion(); // TODO getMsgs
    }

    public static KdpwMsgItem findLastSendKdpwItem(Transaction transaction) {
        KdpwMsgItem result = null;
        Date tempDate = null;
        LOGGER.debug("+++++++++++++++++++++++++ relateditems size: {}", transaction.getRelatedItems().size());
        for (KdpwMsgItem item : transaction.getRelatedItems()) {
            if (tempDate == null || (tempDate.before(item.getMessageLog().getMessageTime())
                    && item.getMessageLog().getDirection().equals(MessageDirection.OUTPUT))) {
                tempDate = item.getMessageLog().getMessageTime();
                if (item.getRequestDetails().getTransMsgType().hasData()) {
                    result = item;
                }
            }
        }
        return result;
    }
}
