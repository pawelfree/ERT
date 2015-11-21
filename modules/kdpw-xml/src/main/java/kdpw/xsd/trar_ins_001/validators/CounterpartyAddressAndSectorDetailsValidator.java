package kdpw.xsd.trar_ins_001.validators;

import pl.pd.emir.commons.StringUtil;
import kdpw.xsd.trar_ins_001.CounterpartyAddressAndSectorDetails;

public class CounterpartyAddressAndSectorDetailsValidator extends AbstractValidator<CounterpartyAddressAndSectorDetails> {

    @Override
    public boolean isEmpty(CounterpartyAddressAndSectorDetails object) {
        return null == object
                || (StringUtil.isEmpty((object.getCorpSctr() == null ? "" : object.getCorpSctr().getValue()))
                && new DomicileValidator().isEmpty(object.getDmcl())
                && StringUtil.isEmpty((object.getNm() == null ? "" : object.getNm().getValue())));
    }

    public CounterpartyAddressAndSectorDetails nullWhenRequiredNotCompleted(final CounterpartyAddressAndSectorDetails value) {
        if (value != null
                && StringUtil.isNotEmpty((value.getNm() == null ? "" : value.getNm().getValue()))
                && new DomicileValidator().hasAllRequired(value.getDmcl())) {
            return value;
        }
        return null;
    }
}
