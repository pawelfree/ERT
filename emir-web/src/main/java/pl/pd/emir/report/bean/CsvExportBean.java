package pl.pd.emir.report.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.reports.enums.ContentTypes;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "csvExportBean")
@SessionScoped
public class CsvExportBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(CsvExportBean.class.getName());

    transient private static final long serialVersionUID = 87654233456l;
    transient private StreamedContent file;

    public void generateFile(String contentExport) {
        //TODO PAWEL try catch exception?
        InputStream is = IOUtils.toInputStream(contentExport, Charset.defaultCharset());
        LOG.info("convert into inputStream...OK");
        file = new DefaultStreamedContent(is, ContentTypes.CSV.getType(), "export.CSV");
        LOG.info("convert into StreamedContent...OK");
        if (Objects.nonNull(is)) {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(CsvExportBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public StreamedContent getFile() {
        return file;
    }

}
