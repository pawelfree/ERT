package pl.pd.emir.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class CollectionsUtils {

    private CollectionsUtils() {
        super();
    }

    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String print(Collection collection) {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (collection != null && !collection.isEmpty()) {
            for (Iterator it = collection.iterator(); it.hasNext();) {
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

    public static List<String> stringToListCollection(String orderIssuers) {
        return stringToListCollection(orderIssuers, "[\\s,]");
    }

    public static List<String> stringToListCollection(String orderIssuers, String delimeterPatern) {
        List<String> result = new ArrayList<>();
        String[] values = orderIssuers.trim().split(delimeterPatern);
        for (String s : values) {
            if (!StringUtil.isEmpty(s)) {
                result.add(s.trim());
            }
        }
        return result;
    }
}
