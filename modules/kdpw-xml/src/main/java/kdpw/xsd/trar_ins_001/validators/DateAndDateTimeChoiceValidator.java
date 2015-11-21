package kdpw.xsd.trar_ins_001.validators;

import kdpw.xsd.trar_ins_001.DateAndDateTimeChoice;

public class DateAndDateTimeChoiceValidator extends AbstractValidator<DateAndDateTimeChoice> {

    @Override
    public boolean isEmpty(DateAndDateTimeChoice object) {
        return object == null
                || (null == object.getDt() && null == object.getDtTm());
    }

}
