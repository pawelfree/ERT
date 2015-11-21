package pl.pd.emir.reports.model;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import pl.pd.emir.reports.utils.PrintUtils;

public class ParametersWrapper {

    private final static ResourceBundle BUNDLE = PrintUtils.BUNDLE;
    private Map<String, Object> parameters = new HashMap<>();

    public ParametersWrapper(String... nameParameters) {
        int counter = 1;
        for (String name : nameParameters) {
            try {
                this.parameters.put("parameter" + counter, BUNDLE.getString(name));
                counter++;
            } catch (MissingResourceException ex) {
                this.parameters.put("parameter" + counter, name);
            }
        }
    }

    public ParametersWrapper() {
        super();
    }

    public void addParameter(String name, String label) {
        parameters.put(name, label);
    }

    public void setParametersWrapperWithoutBundle(String... nameParameters) {
        int counter = 1;
        for (String name : nameParameters) {
            this.parameters.put("parameter" + counter, name);
            counter++;
        }
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
