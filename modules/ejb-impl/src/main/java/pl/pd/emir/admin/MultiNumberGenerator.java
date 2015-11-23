package pl.pd.emir.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.entity.MultiNumber;
import pl.pd.emir.enums.MultiGeneratorKey;
import pl.pd.emir.exceptions.MultiNumberGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class MultiNumberGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiNumberGenerator.class);

    public static final int MAX_GENERATE_NEW_NUMBER_TRIES = 10;

    @PersistenceContext(unitName = "emir2")
    protected EntityManager entityManager;

    @Resource
    protected SessionContext context;

    public MultiNumberGenerator() {
        super();
    }

    public Long getNewNumber(final Date forDate, final MultiGeneratorKey key) {
        boolean newValueGenerated = false;
        long number = -1;
        for (int i = 0; !newValueGenerated; i++) {
            try {
                number = context.getBusinessObject(MultiNumberGenerator.class).tryGetNewNumber(forDate, key, 1);
                newValueGenerated = true;
            } catch (IllegalStateException ex) {
                LOGGER.debug("Trying get new number failed", ex);
                newValueGenerated = false;
            }
            if (i > MAX_GENERATE_NEW_NUMBER_TRIES) {
                LOGGER.debug("Max generate new number tries reached");
                throw new MultiNumberGenerationException();
            }
        }
        return number;
    }

    private MultiNumber createMultiNumber(final Date forDate, final MultiGeneratorKey key) {
        final MultiNumber transactionNumber = new MultiNumber(key, forDate, key.getStartNumber(), 0L);
        entityManager.persist(transactionNumber);
        return transactionNumber;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long tryGetNewNumber(final Date forDate, final MultiGeneratorKey key, long size) {
        final List<MultiNumber> list = entityManager.createNamedQuery("MultiNumber.findNumberForDate")
                .setParameter("numberType", key)
                .setParameter("forDate", forDate).getResultList();
        MultiNumber number = null;
        if (CollectionsUtils.isEmpty(list)) {
            number = createMultiNumber(forDate, key);
        } else if (list.size() == 1) {
            number = list.get(0);
        } else {
            throw new IllegalStateException("Expected only 1 MultiNumber entity and found " + list.size() + " for date " + forDate + " and type " + key);
        }

        return number.increase(size);
    }

    public List<Long> getNumbers(Date forDate, MultiGeneratorKey key, long size) {
        boolean newValueGenerated = false;
        final List<Long> result = new ArrayList<>();
        long number = -1;
        for (int i = 0; !newValueGenerated; i++) {
            try {
                number = context.getBusinessObject(MultiNumberGenerator.class).tryGetNewNumber(forDate, key, size);
                newValueGenerated = true;
            } catch (IllegalStateException ex) {
                LOGGER.debug("Trying get new number failed", ex);
                newValueGenerated = false;
            }
            if (i > MAX_GENERATE_NEW_NUMBER_TRIES) {
                LOGGER.debug("Max generate new number tries reached");
                throw new MultiNumberGenerationException();
            }

            long start = number - size + 1;
            for (long j = start; j <= number; j++) {
                result.add(j);
            }
        }
        return result;
    }
}
