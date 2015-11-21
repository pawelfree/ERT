package pl.pd.emir.criteria;

import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;

public class UserSC extends AbstractSearchCriteria {

    private String login;
    private String firstName;
    private String lastName;
    private Boolean active;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public String getActiveWrapper() {
        if (this.active == null) {
            return "NULL";
        } else if (this.active) {
            return "T";
        } else {
            return "N";
        }
    }

    public void setActiveWrapper(String active) {
        if (null != active) {
            switch (active) {
                case "T":
                    this.active = true;
                    break;
                case "F":
                    this.active = false;
                    break;
                default:
                    this.active = null;
                    break;
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        setLogin(null);
        setFirstName(null);
        setLastName(null);
        setActiveWrapper("NULL");
    }

    @Override
    public void addFilters() {
        clearFilters();
        if (active != null) {
            getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "active", "=", active));
        }
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "login", "%.%", login));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "lastName", "%.%", lastName));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "firstName", "%.%", firstName));
    }
}
