package pl.pd.emir.kdpw.bean;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.criteria.MessageLogSC;
import pl.pd.emir.entity.kdpw.FileStatus;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.entity.kdpw.MessageDirection;
import pl.pd.emir.entity.kdpw.MessageLog;
import pl.pd.emir.entity.kdpw.MessageType;
import pl.pd.emir.kdpw.api.MessageLogManager;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.KDPWListWrapper;
import pl.pd.emir.reports.model.KDPWResponseWrapper;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.resources.MultipleFilesResourceBundle;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import pl.pd.emir.commons.StringUtil;

/**
 * Obsługa widoku listy komunikatów wymienianych z KDPW.
 *
 */
@SessionScoped
@ManagedBean(name = "kdpwListBean")
public class RepositoryMessageListBean extends AbstractListBean<MessageLog, MessageLogManager, MessageLogSC> {

    private static final String DEFAULT_ENCODING = "UTF-8";

    public static final String CONTENT_TYPE = "text/xml";

    public static final String FILE_EXT = "xml";

    @EJB
    private transient MessageLogManager service;

    private boolean inRelatedView = false;

    private String fileLocation;

    private transient MessageLog logToSave;

    /**
     * Domyslne sortowanie po polu.
     */
    private static final String DEFAULT_SORT_FIELD = "messageTime";

    /**
     * wydruk Exel z tabeli
     */
    private final transient ReportData<KDPWListWrapper> reportData = new ReportData<>();

    private final transient ReportType reportType = ReportType.KDPWLIST_TABLE;

    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();

    /**
     * raport z odpowiedzi z KDPW
     */
    private final transient ReportData<KDPWResponseWrapper> reportKDPWResponseData = new ReportData<>();

    private transient ReportType reportKDPWType = ReportType.KDPW_REQUEST_REPORT;

    private MessageLogSC mainCriteria;

    public RepositoryMessageListBean() {
        super(MessageLogSC.class);
    }

    public String getFileExt() {
        return FILE_EXT;
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public ReportData getReportData() {
        Collection<KDPWListWrapper> data = service
                .findAll(criteria)
                .stream()
                .map((messageLog) -> {
                    KDPWListWrapper wrapper = new KDPWListWrapper();
                    wrapper.setFileId(messageLog.getFileId() == null ? "" : messageLog.getFileId());
                    wrapper.setFileTime(messageLog.getMessageTime() == null ? new Date() : messageLog.getMessageTime());
                    wrapper.setMessageName(messageLog.getMessageName() == null ? "" : messageLog.getMessageName());
                    wrapper.setStatus(messageLog.getFileStatus() == null ? "" : BUNDLE.getString(messageLog.getFileStatus().getMsgKey()));
                    wrapper.setUserLogin(messageLog.getUserLogin() == null ? "" : messageLog.getUserLogin());
                    return wrapper;
                })
                .collect(Collectors.toList());

        ParametersWrapper parameters = new ParametersWrapper("c.dateandtimefile", "c.generateRead", "c.message", "c.status", "c.identificatorFile");
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    public ReportType getReportKDPWType() {
        return reportKDPWType;
    }

    public ReportData getReportKDPWResponseData(final MessageLog msgLog) {
        final Collection<KDPWResponseWrapper> data = new ArrayList<>();
        ParametersWrapper parameters = new ParametersWrapper("c.statusMessageFile", "c.dateTimeLoading", "c.login",
                "c.idMessage", "c.idTransaction", "c.idClient",
                "c.statusMesasge", "c.codeError", "c.descError");
        KDPWResponseWrapper wrapper = new KDPWResponseWrapper();
        wrapper.setStatusMessageFile(BeanHelper.getMessage(msgLog.getFileStatus().getMsgKey()));
        wrapper.setDatatimeLoading(msgLog.getMessageTime());
        wrapper.setLogin(msgLog.getUserLogin());

        //Dla raportu dotyczącego plików otrzymanych z KDPW:
        if (CollectionsUtils.isNotEmpty(msgLog.getContentItems())) {
            for (KdpwMsgItem item : msgLog.getContentItems()) {
                //parameters = new ParametersWrapper("c.statusMessageFile","c.dateTimeLoading","c.login","c.idMessage","c.idTransaction","c.idClient");
                reportKDPWType = ReportType.KDPW_REQUEST_REPORT;
                wrapper.setIdMessage(item.getSndrMsgRef()); //Id komunikatu
                wrapper.setIdTransaction(item.getTransactionId()); // Identyfikator transakcji
                wrapper.setIdClients(item.getClientId()); // Identyfikator kontrahenta
                if (MessageDirection.INPUT.equals(msgLog.getDirection())) {
                    reportKDPWType = ReportType.KDPW_RESPONSE_REPORT;
                    wrapper.setStatusMessage(BeanHelper.getMessage(item.getStatus().getMsgKey())); // Status komunikatu
                    wrapper.setErrorCode(item.getResponseDetails().getStatusCode()); // Kod błędu
                    wrapper.setDescriptionError(item.getResponseDetails().getStatusDesc()); // Opis błędu
                }
                data.add(wrapper);
                wrapper = new KDPWResponseWrapper();
            }
        } else {
            data.add(wrapper);
        }

        reportKDPWResponseData.setParameters(parameters.getParameters());
        reportKDPWResponseData.setReportData(data);

        return reportKDPWResponseData;
    }

    @Override
    public final String init() {
        final String init = super.init();
        disableRelatedView();
        return init;
    }

    public final FileStatus[] getFileStatus() {
        return FileStatus.values();
    }

    public MessageType[] getMessageTypes() {
        return new MessageType[]{MessageType.TRANSACTION,
            MessageType.TRANSACTION_RESPONSE, MessageType.DATA_SET_RESPONSE, MessageType.RECONCILIATION};
    }

    @Override
    public void initSearchCriteria() {
        initCriteria();
    }

    @Override
    public void cleanSearchCriteria() {
        criteria.clear();
        initSearchCriteriaAfterClean();
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        initCriteria();
    }

    private void initCriteria() {
        setSortField(DEFAULT_SORT_FIELD);
        setSortOrder(SortOrder.DESCENDING);
        criteria.setMessageTimeFrom(DateUtils.getDayBegin(new Date()));
        criteria.setMessageTimeTo(DateUtils.getDayEnd(new Date()));
    }

    private void disableRelatedView() {
        criteria.setRelatedToInputId(null);
        criteria.setRelatedToOutputId(null);
        inRelatedView = false;
    }

    @Override
    public String getAction() {
        return "kdpwList";
    }

    @Override
    public MessageLogManager getService() {
        return service;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public final void addToSave(final MessageLog value) {
        this.logToSave = value;
        this.fileLocation = null;
    }

    public boolean hasChangeLog(final MessageLog value) {
        return StringUtil.isNotEmpty(value.getChangeLog());
    }

    public StreamedContent getChanges() {
        StreamedContent result = null;
        if (Objects.isNull(logToSave)) {
            LOGGER.error("Cannot download changes file! No data available!");
        } else {
            result = getFile(String.format("%s.%s", "changes", getFileExt()), logToSave.getChangeLog());
        }
        return result;
    }

    public StreamedContent getTransportFile() {
        StreamedContent result = null;
        if (Objects.isNull(logToSave)) {
            LOGGER.error("Cannot download transport file! No item selected!");
        } else {
            result = getFile(String.format("%s.%s", logToSave.getMessageName(), getFileExt()), logToSave.getTransportForm());
        }
        return result;
    }

    private StreamedContent getFile(final String fileName, final String data) {
        StreamedContent result = null;
        String temp = " ";//to do
        //do poprawienia ta fuszerka
        for (int i = 0; i < 4096; i++) {
            temp = temp.concat(" ");
        }
        String message = data;
        message = message.concat(temp);

        byte[] stream;
        try {
            stream = message.getBytes(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Encoding error: " + e);
            stream = message.getBytes();
        }
        result = new DefaultStreamedContent(new ByteArrayInputStream(stream),
                String.format("%s; %s", getContentType(), DEFAULT_ENCODING),
                fileName);
        if (stream.length < FacesContext.getCurrentInstance().getExternalContext().getResponseBufferSize()) {
            LOGGER.info("Start downloading file ResponseBufferSize: {}", fileName);
            FacesContext.getCurrentInstance().getExternalContext().setResponseBufferSize(stream.length);
        }

        return result;
    }

    public final void addToSearchCriteria(final MessageLog messageLog) {
        inRelatedView = true;
        mainCriteria = criteria.cloneWithoutRelated();
        cleanSearchCriteria();
        if (MessageDirection.OUTPUT.equals(messageLog.getDirection())) {
            criteria.setRelatedToOutputId(messageLog.getId());
        } else {
            criteria.setRelatedToInputId(messageLog.getId());
        }
        setFirstPage();
    }

    public final boolean isInRelatedView() {
        return inRelatedView;
    }

    public final void restoreSearchCriteria() {
        disableRelatedView();
        criteria = mainCriteria;
        initSearchCriteria();
        setFirstPage();
    }
}
