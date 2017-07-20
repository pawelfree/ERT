package pl.pd.emir.reports.model;

import java.util.Date;
import pl.pd.emir.commons.DateUtils;

/**
 *
 * @author PawelDudek
 */
public class EucTradeDataWrapper {

    private final String contractNo;
    private final String customerCode;
    private final String productType;
    private final String ccy1;
    private final String amount1;
    private final String ccy2;
    private final String amount2;
    private final String dealDate;
    private final String startDate;
    private final String maturityDate;
    private final String mtm;

    public EucTradeDataWrapper(String contractNo, String customerCode, String productType,
            String ccy1, String amount1,
            String ccy2, String amount2,
            Date dealDate, Date startDate, Date maturityDate,
            String mtm) {
        this.contractNo = contractNo;
        this.customerCode = customerCode;
        this.productType = productType;
        this.ccy1 = ccy1;
        this.amount1 = amount1;
        this.ccy2 = ccy2;
        this.amount2 = amount2;
        this.dealDate = DateUtils.formatDate(dealDate, DateUtils.DATE_FORMAT_2);
        this.startDate = DateUtils.formatDate(startDate, DateUtils.DATE_FORMAT_2);
        this.maturityDate = DateUtils.formatDate(maturityDate, DateUtils.DATE_FORMAT_2);
        this.mtm = mtm;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getProductType() {
        return productType;
    }

    public String getCcy1() {
        return ccy1;
    }

    public String getAmount1() {
        return amount1;
    }

    public String getCcy2() {
        return ccy2;
    }

    public String getAmount2() {
        return amount2;
    }

    public String getDealDate() {
        return dealDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public String getMtm() {
        return mtm;
    }

}
