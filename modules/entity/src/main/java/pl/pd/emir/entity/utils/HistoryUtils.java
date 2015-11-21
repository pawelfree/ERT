package pl.pd.emir.entity.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import pl.pd.emir.commons.PropertyUtils;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.resources.EventLogBuilder;

public class HistoryUtils {

    private static final String PROPERTYFILE = "history-details.properties";

    protected final static Properties PROPERTIES = PropertyUtils.getProperties(HistoryUtils.class, PROPERTYFILE);

    public static void checkFieldsEquals(List<ChangeLog> result, Object oldField, Object newField, EventLogBuilder.EventDetailsKey nameField, String changeComment) {
        if (EventLogBuilder.fieldsNotEquals(oldField, newField)) {
            if (oldField instanceof Date || newField instanceof Date) {
                result.add(new ChangeLog(EventLogBuilder.getChangeLogData(nameField, (Date) oldField, (Date) newField, changeComment)));
            } else if (oldField instanceof BigDecimal || newField instanceof BigDecimal) {
                BigDecimal bd1 = (BigDecimal) oldField;
                BigDecimal bd2 = (BigDecimal) newField;
                if (bd1 == null || bd2 == null) {
                    result.add(new ChangeLog(EventLogBuilder.getChangeLogData(nameField, oldField, newField, changeComment)));

                } else if (bd1.compareTo(bd2) != 0) {
                    result.add(new ChangeLog(EventLogBuilder.getChangeLogData(nameField, oldField, newField, changeComment)));
                }
            } else {
                result.add(new ChangeLog(EventLogBuilder.getChangeLogData(nameField, oldField, newField, changeComment)));
            }
        }
    }

    public static void checkFieldsEqualsMsg(List<ChangeLog> result, Object oldField, Object newField, EventLogBuilder.EventDetailsKey nameField, String changeComment) {
        if (EventLogBuilder.fieldsNotEquals(oldField, newField)) {
            result.add(new ChangeLog(EventLogBuilder.getChangeLogData(nameField,
                    PropertyUtils.getString(PROPERTIES, (String) oldField),
                    PropertyUtils.getString(PROPERTIES, (String) newField), changeComment)));
        }
    }

}
