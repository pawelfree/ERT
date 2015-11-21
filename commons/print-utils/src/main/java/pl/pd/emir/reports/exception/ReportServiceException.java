package pl.pd.emir.reports.exception;

import java.io.IOException;

public class ReportServiceException extends RuntimeException {

    public ReportServiceException(String message, IOException ex) {
        super(message, ex);
    }

    public ReportServiceException(String message, Exception ex) {
        super(message, ex);
    }
}
