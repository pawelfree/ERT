package kdpw.xsd.trar_ins_001.validators;

import pl.pd.emir.commons.StringUtil;
import kdpw.xsd.trar_ins_001.TradeAdditionalInformation;

public class TradeAdditionalInformationValidator extends AbstractValidator<TradeAdditionalInformation> {

    private final DateAndDateTimeChoiceValidator dateValidator = new DateAndDateTimeChoiceValidator();

    @Override
    public boolean isEmpty(TradeAdditionalInformation object) {
        return object == null
                || (StringUtil.isEmpty(object.getVenueOfExc())
                && StringUtil.isEmpty(object.getCmprssn())
                && new PriceChoiceValidator().isEmpty(object.getPric())
                && null == object.getNmnlAmt()
                && null == object.getPricMltplr()
                && null == object.getQty()
                && null == object.getUpPmt()
                && StringUtil.isEmpty(object.getDlvryTp())
                && dateValidator.isEmpty(object.getExecDtTm())
                && dateValidator.isEmpty(object.getFctvDt())
                && dateValidator.isEmpty(object.getMtrtyDt())
                && dateValidator.isEmpty(object.getTrmntnDt())
                && dateValidator.isEmpty(object.getSttlmtDt())
                && StringUtil.isEmpty((object.getMstrAgrmntTp() == null ? "" : object.getMstrAgrmntTp().getValue()))
                && null == object.getMstrAgrmntVrsn());
    }
}
