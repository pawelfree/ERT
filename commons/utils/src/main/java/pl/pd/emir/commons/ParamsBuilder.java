package pl.pd.emir.commons;

import java.util.HashMap;
import java.util.Map;

public class ParamsBuilder {

    Map<String, Object> parameters;

    public ParamsBuilder() {
        parameters = new HashMap<>();
    }

    public ParamsBuilder put(String key, Object value) {
        parameters.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return parameters;
    }
}
