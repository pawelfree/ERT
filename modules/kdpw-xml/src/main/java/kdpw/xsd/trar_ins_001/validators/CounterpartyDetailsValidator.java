package kdpw.xsd.trar_ins_001.validators;

import pl.pd.emir.commons.StringUtil;
import kdpw.xsd.trar_ins_001.CounterpartyDetails;

public class CounterpartyDetailsValidator extends AbstractValidator<CounterpartyDetails> {

    @Override
    public boolean isEmpty(CounterpartyDetails object) {
        return object == null
                || (new InstitutionCodeValidator().isEmpty(object.getBrkrId())
                && new InstitutionCodeValidator().isEmpty(object.getClrMmbId())
                && StringUtil.isEmpty((object.getClrAcct() == null ? "" : object.getClrAcct().getValue()))
                && new InstitutionCodeValidator().isEmpty(object.getBnfcryId())
                && StringUtil.isEmpty(object.getTrdgCpcty())
                && StringUtil.isEmpty(object.getFinNonFinInd())
                && StringUtil.isEmpty((object.getCmmrclActvty() == null ? "" : object.getCmmrclActvty().getValue()))
                && StringUtil.isEmpty((object.getClrTrshld() == null ? "" : object.getClrTrshld().getValue()))
                && StringUtil.isEmpty((object.getCollPrtfl() == null ? "" : object.getCollPrtfl().getValue())));
    }

}
