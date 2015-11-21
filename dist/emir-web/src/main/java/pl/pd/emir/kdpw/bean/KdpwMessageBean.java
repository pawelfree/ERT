package pl.pd.emir.kdpw.bean;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.clientutils.fileupload.AbstractFileImportBean;
import pl.pd.emir.enums.FileType;
import pl.pd.emir.kdpw.api.KdpwImportManager;

/**
 * Obsługa importu komunikatów z KDPW.
 *
 * http://forum.primefaces.org/viewtopic.php?f=3&t=19220
 */
@SessionScoped
@ManagedBean(name = "kdpwMessageBean")
public class KdpwMessageBean extends AbstractFileImportBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(KdpwMessageBean.class);

    protected static final String FILE_UPLOAD_DIALOG = "fileUploadDialog";

    protected static final String IMPORT_ERROR_DIALOG = "importErrorDialog";

    protected static final String IMPORT_RESULT_DIALOG = "importResultDialog";

    @EJB
    private KdpwImportManager kdpwImportManager;

    public KdpwMessageBean() {
        super();
    }

    @Override
    protected void importFile(final InputStream stream, final String name) {
        final String fileName = FilenameUtils.getName(name);
        LOGGER.info("Start import file: {}", FilenameUtils.getName(fileName));
        kdpwImportManager.importFile(stream, fileName);
    }

    @Override
    protected final List<FileType> getFileTypes() {
        return Arrays.asList(FileType.TXT, FileType.XML);
    }

    @Override
    protected void clean() {
        // TODO
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private void showDialog(final String dialogId) {
        RequestContext.getCurrentInstance().execute("PF('" + dialogId + "').show()");
    }

    private void hideDialog(final String dialogId) {
        RequestContext.getCurrentInstance().execute("PF('"+ dialogId + "').hide()");
    }

    protected final void processImport() {
        hideDialog(IMPORT_ERROR_DIALOG);
        showDialog(IMPORT_RESULT_DIALOG);
    }
}
