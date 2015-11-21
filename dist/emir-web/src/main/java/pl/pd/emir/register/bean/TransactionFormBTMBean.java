package pl.pd.emir.register.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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

}
