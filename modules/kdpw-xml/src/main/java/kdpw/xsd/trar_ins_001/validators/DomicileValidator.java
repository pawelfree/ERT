package kdpw.xsd.trar_ins_001.validators;

//import pl.pd.emir.commons.StringUtil;
//import kdpw.xsd.trar_ins_001.Domicile;
//
//public class DomicileValidator extends AbstractValidator<Domicile> {
//
//    public boolean hasAllRequired(final Domicile value) {
//        return value != null
//                && StringUtil.isNotEmpty((value.getCtry() == null ? "" : value.getCtry().getValue()))
//                && StringUtil.isNotEmpty((value.getTwnNm() == null ? "" : value.getTwnNm().getValue()));
//    }
//
//    @Override
//    public boolean isEmpty(Domicile value) {
//        return null == value
//                || (StringUtil.isEmpty((value.getCtry() == null ? "" : value.getCtry().getValue()))
//                && StringUtil.isEmpty((value.getPstCd() == null ? "" : value.getPstCd().getValue()))
//                && StringUtil.isEmpty((value.getTwnNm() == null ? "" : value.getTwnNm().getValue()))
//                && StringUtil.isEmpty((value.getStrtNm() == null ? "" : value.getStrtNm().getValue()))
//                && StringUtil.isEmpty((value.getBldgId() == null ? "" : value.getBldgId().getValue()))
//                && StringUtil.isEmpty((value.getPrmsId() == null ? "" : value.getPrmsId().getValue()))
//                && StringUtil.isEmpty((value.getDmclDtls() == null ? "" : value.getDmclDtls().getValue())));
//    }
//}
