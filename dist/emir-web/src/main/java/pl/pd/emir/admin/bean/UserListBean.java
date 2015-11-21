package pl.pd.emir.admin.bean;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.criteria.UserSC;
import pl.pd.emir.entity.administration.User;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.reports.model.UserListWrapper;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

@SessionScoped
@ManagedBean(name = "userListBean")
public class UserListBean extends AbstractListBean<User, UserManager, UserSC> {

    public UserListBean() {
        super(UserSC.class);
    }

    private final transient ReportData<UserListWrapper> reportData = new ReportData<>();
    private final transient ReportType reportType = ReportType.USER_LIST;
    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();

    public ReportType getReportType() {
        return reportType;
    }

    public ReportData getReportData() {
        Collection<UserListWrapper> data = ((List<User>) service
                .findAll(criteria))
                .stream()
                .map(user -> {
                    UserListWrapper wrapper = new UserListWrapper();
                    if (user.isActive()) {
                        wrapper.setActive(BUNDLE.getString("admin.user.filter.active"));
                    } else {
                        wrapper.setActive(BUNDLE.getString("admin.user.filter.inactive"));
                    }
                    wrapper.setName(user.getFirstName());
                    wrapper.setLastname(user.getLastName());
                    wrapper.setLogin(user.getLogin() == null ? "" : user.getLogin());
                    wrapper.setRoles(user.getGroups() == null ? "" : user.getGroups().toString());
                    return wrapper;
                })
                .collect(Collectors.toList());

        ParametersWrapper parameters = new ParametersWrapper("c.login", "c.name", "c.lastname", "c.active");
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    public String getTranslateActive(boolean active) {
        if (active) {
            return BUNDLE.getString("admin.user.filter.active");
        } else {
            return BUNDLE.getString("admin.user.filter.inactive");
        }
    }

    @EJB
    private transient UserManager service;

    @Override
    public void initSearchCriteria() {
        LOGGER.info("initSearchCriteria");
        LOGGER.info("CurrentUserLogin: " + service.getCurrentUserLogin());
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        //OK
    }

    @Override
    public String getAction() {
        return "userList";
    }

    @Override
    public UserManager getService() {
        return service;
    }
}
