package pl.pd.emir.kdpw.api.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;

public class ImportResult implements Serializable {

    private static final long serialVersionUID = -3060671162081901751L;

    private final String fileName;

    private String fileAsString;

    private FileStatus fileStatus;

    private final String userName;

    private final List<KdpwMsgItem> items = new ArrayList<>();

    public ImportResult(final String fileName, final String userName) {
        this.fileName = fileName;
        this.userName = userName;
    }

    public final String getFileName() {
        return fileName;
    }

    public final String getFileAsString() {
        return fileAsString;
    }

    public final void setFileAsString(final String value) {
        this.fileAsString = value;
    }

    public final void addItem(final KdpwMsgItem value) {
        this.items.add(value);
    }

    public final List<KdpwMsgItem> getItems() {
        return items;
    }

    public String getUserName() {
        return userName;
    }

    public FileStatus getFileStatus() {
        return fileStatus;
    }

    public final void responseError() {
        this.fileStatus = FileStatus.A_INVALID_RESPONSE;
    }

    public final boolean isResponseError() {
        return FileStatus.A_INVALID_RESPONSE.equals(this.fileStatus);
    }
}
