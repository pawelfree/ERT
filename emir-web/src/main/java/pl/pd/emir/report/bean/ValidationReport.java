package pl.pd.emir.report.bean;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.reports.utils.PrintUtils;

public class ValidationReport {

    protected final static ResourceBundle BUNDLE = PrintUtils.BUNDLE;

    protected boolean validation(List<String> selectedInstrumentType, List<String> selectedClients, Date dateFrom, Date dateTo) {
        boolean pass = true;
        if (Objects.isNull(dateFrom)) {
            BeanHelper.addReportMessageValidation(BUNDLE.getString("report.error.noparameter"), BUNDLE.getString("report.error.dateNullFrom"));
            pass = false;
        }
        if (Objects.isNull(dateTo)) {
            BeanHelper.addReportMessageValidation(BUNDLE.getString("report.error.noparameter"), BUNDLE.getString("report.error.dateNullTo"));
            pass = false;
        }
        if (selectedInstrumentType.isEmpty()) {
            BeanHelper.addReportMessageValidation(BUNDLE.getString("report.error.noparameter"), BUNDLE.getString("report.error.noparameter.typeinstrument"));
            pass = false;
        }
        if (selectedClients.isEmpty()) {
            BeanHelper.addReportMessageValidation(BUNDLE.getString("report.error.noparameter"), BUNDLE.getString("report.error.noparameter.client"));
            pass = false;
        }
        if (dateFrom.compareTo(dateTo) > 0) {
            BeanHelper.addReportMessageValidation(BUNDLE.getString("report.error.badparameter"), BUNDLE.getString("report.error.date"));
            pass = false;
        }
        return pass;
    }
}
