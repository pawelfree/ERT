package pl.pd.emir.clientutils.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.enums.FileType;

public abstract class AbstractFileImportBean implements Serializable {

    protected String fileName;

    protected boolean importComplete;

    protected UploadedFile uploadedFile = null;

    protected abstract void importFile(InputStream stream, String fileName);

    protected abstract List<FileType> getFileTypes();

    protected abstract void clean();

    protected abstract Logger getLogger();

    public void init() {
        doClean();
    }

    public void handleFileUpload(FileUploadEvent event) {

        final UploadedFile uploadedFile = event.getFile();
        getLogger().info("FileUpload: fileName={}", uploadedFile.getFileName());
        doClean();
        fileName = FilenameUtils.getName(uploadedFile.getFileName());
        try {
            importFile(uploadedFile.getInputstream(), uploadedFile.getFileName());
            importComplete = true;
        } catch (IOException ex) {
            getLogger().error("Upload file error: ", ex);
            BeanHelper.addErrorMessageFromResource("importFile.error.io");
        }
    }

    public void handleFileUpload() {
        getLogger().info("handleFileUpload()");
    }

    public void handleFile() {
        getLogger().info("handleFile()");
    }

    private void doClean() {
        fileName = null;
        importComplete = false;
        clean();
    }

    public String getFileTypesRegex() {
        List<FileType> fileTypes = getFileTypes();
        if (fileTypes == null || fileTypes.isEmpty()) {
            return "/(\\.|\\/)([\\w\\d]+)$/";
        }
        final StringBuilder builder = new StringBuilder("/(\\.|\\/)(");
        for (int i = 0; i < fileTypes.size(); i++) {
            FileType type = fileTypes.get(i);
            builder.append(type.getExt().toLowerCase());
            if (i < fileTypes.size() - 1) {
                builder.append("|");
            }
        }
        builder.append(")$/");
        return builder.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isImportComplete() {
        return importComplete;
    }

    public void setImportComplete(boolean importComplete) {
        this.importComplete = importComplete;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        getLogger().info("SET UPLOAD FILE: ");
        if (uploadedFile == null) {
            getLogger().info("NULL");
        } else {
            getLogger().info(uploadedFile.getFileName());
        }
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }
}
