package pl.pd.emir.enums;

/**
 *
 * @author PawelDudek
 */
public enum CounterpartyIndustry implements ValueMsgEnum {

    F_A("institution.financial.insurance.life","A"),
    F_C("institution.financial.credit","C"),
    F_F("institution.financial.investment","F"),
    F_I("institution.financial.insurance","I"),
    F_L("institution.financial.investment.fund","L"),
    F_O("institution.financial.pension","O"),
    F_R("institution.financial.reinsurance","R"),
    F_U("institution.financial.investment.collective","U"),
    N_1("institution.nonfinancial.agriculture","1"),
    N_2("institution.nonfinancial.mining","2"),
    N_3("institution.nonfinancial.processing","3"),
    N_4("institution.nonfinancial.energy","4"),
    N_5("institution.nonfinancial.water","5"),
    N_6("institution.nonfinancial.architecture","6"),
    N_7("institution.nonfinancial.trade","7"),
    N_8("institution.nonfinancial.transport","8"),
    N_9("institution.nonfinancial.accommodation","9"),
    N_10("institution.nonfinancial.communication","10"),
    N_11("institution.nonfinancial.finance","11"),
    N_12("institution.nonfinancial.property","12"),
    N_13("institution.nonfinancial.professional","13"),
    N_14("institution.nonfinancial.administration","14"),
    N_15("institution.nonfinancial.public","15"),
    N_16("institution.nonfinancial.education","16"),
    N_17("institution.nonfinancial.healthcare","17"),
    N_18("institution.nonfinancial.culture","18"),
    N_19("institution.nonfinancial.services","19"),
    N_20("institution.nonfinancial.households","20"),
    N_21("institution.nonfinancial.extraterritorial","21");
    
    private final String msgKey;
    private final String realValue;        
        
    private CounterpartyIndustry(final String msgKey, final String realValue) {
        this.msgKey = msgKey;
        this.realValue = realValue;
    }        
            
    @Override
    public String getMsg() {
        return msgKey;
    }
    
    @Override
    public String getValue() {
        return realValue;
    }
    
    public static String fromStringMsg(String value) {
        for (CounterpartyIndustry ind : CounterpartyIndustry.values()) {
            if (ind.getValue().equals(value)) {
                return ind.getMsg();
            }
        }
        return "";
    }
        
}
