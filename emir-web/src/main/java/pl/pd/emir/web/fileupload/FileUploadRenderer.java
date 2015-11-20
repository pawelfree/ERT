package pl.pd.emir.web.fileupload;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class FileUploadRenderer extends org.primefaces.component.fileupload.FileUploadRenderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
        if (context.getExternalContext().getRequestContentType().toLowerCase().startsWith("multipart/")) {
            super.decode(context, component);
        }
    }

}
