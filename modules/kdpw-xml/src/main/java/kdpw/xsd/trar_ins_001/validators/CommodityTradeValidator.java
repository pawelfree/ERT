package kdpw.xsd.trar_ins_001.validators;

import pl.pd.emir.commons.StringUtil;
import kdpw.xsd.trar_ins_001.CommodityTrade;

public class CommodityTradeValidator extends AbstractValidator<CommodityTrade> {

    @Override
    public boolean isEmpty(CommodityTrade object) {
        return object == null
                || (StringUtil.isEmpty(object.getCmmdtyBase())
                && StringUtil.isEmpty(object.getCmmdtyDtls())
                && StringUtil.isEmpty((object.getDlvryPnt() == null ? "" : object.getDlvryPnt().getValue()))
                && StringUtil.isEmpty((object.getIntrcnnctnPnt() == null ? "" : object.getIntrcnnctnPnt().getValue()))
                && StringUtil.isEmpty((object.getLdTp() == null ? "" : object.getLdTp().getValue()))
                && new DateAndDateTimeChoiceValidator().isEmpty(object.getDlvryStartDtTm())
                && new DateAndDateTimeChoiceValidator().isEmpty(object.getDlvryEndDtTm())
                && StringUtil.isEmpty((object.getCntrctCpcty() == null ? "" : object.getCntrctCpcty().getValue()))
                && null == object.getQty()
                && null == object.getPric());
    }

}
