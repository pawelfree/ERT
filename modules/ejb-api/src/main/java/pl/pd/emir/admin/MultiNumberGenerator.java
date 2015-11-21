package pl.pd.emir.admin;

import java.util.Date;
import java.util.List;
import pl.pd.emir.enums.MultiGeneratorKey;

public interface MultiNumberGenerator {

    Long getNewNumber(Date forDate, MultiGeneratorKey key);

    long tryGetNewNumber(Date forDate, MultiGeneratorKey key, long size);

    List<Long> getNumbers(Date forDate, MultiGeneratorKey key, long size);
}
