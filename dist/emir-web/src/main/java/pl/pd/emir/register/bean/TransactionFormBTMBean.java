package pl.pd.emir.register.bean;

import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.enums.ConfirmedStatus;

@SessionScoped
@ManagedBean(name = "transactionFormBean")
public class TransactionFormBTMBean extends TransactionFormBean {

    @ManagedProperty(value = "#{transactionListBean}")
    private TransactionListBTMBean listBean;

    @Override
    public String saveStep2() {
        final String result = super.saveStep2();
        listBean.setLastAddedTransId(getEntity().getId());
        return result;
    }

    public TransactionFormBTMBean() {
    }

    public TransactionListBTMBean getListBean() {
        return listBean;
    }

    public void setListBean(TransactionListBTMBean listBean) {
        this.listBean = listBean;
    }

    public void clientChanged() {
        if (entity.getClient().getOriginalId() != null) {
            fillContractorBeneficiary();
        }
    }

    public void confirmationChanged() {
        if (Objects.isNull(entity.getRiskReduce().getConfirmationType())) {
            entity.setConfirmed(null);
            entity.getRiskReduce().setConfirmationDate(null);
        } else if (entity.getRiskReduce().getConfirmationType().compareTo(ConfirmationType.N) == 0) {
            entity.setConfirmed(ConfirmedStatus.UNCONFIRMED);
            entity.getRiskReduce().setConfirmationDate(null);
        } else {
            entity.setConfirmed(ConfirmedStatus.CONFIRMED);
        }
    }

}
