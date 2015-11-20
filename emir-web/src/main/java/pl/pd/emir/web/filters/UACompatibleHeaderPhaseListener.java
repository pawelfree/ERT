package pl.pd.emir.web.filters;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class UACompatibleHeaderPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent arg0) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        final FacesContext facesContext = event.getFacesContext();
        final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addHeader("X-UA-Compatible", "IE=edge");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
