package pl.pd.emir.clientutils.listener;

import javax.faces.application.FacesMessage;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import pl.pd.emir.bean.BeanHelper;

public class ValidateGrowlListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        if ((event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS || event.getPhaseId() == PhaseId.INVOKE_APPLICATION)
                && (event.getFacesContext().getMaximumSeverity() != null && event.getFacesContext().getMaximumSeverity().getOrdinal() >= 1)
                && (event.getFacesContext().getMessageList("growlError") == null || event.getFacesContext().getMessageList("growlError").isEmpty())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, BeanHelper.getMessage("form.errorField"), null);
            event.getFacesContext().addMessage("growlError", msg);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }
}
