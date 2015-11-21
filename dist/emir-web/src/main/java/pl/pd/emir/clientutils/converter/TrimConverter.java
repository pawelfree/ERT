package pl.pd.emir.clientutils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.commons.StringUtil;

@FacesConverter(value = "trimConverter")
public class TrimConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrimConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LOGGER.debug("getAsObject: value={}", value);

        if (StringUtil.isBlank(value)) {
            return null;
        } else {
            return value.trim();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOGGER.debug("getAsString: value={}", value);

        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            return value.toString();
        }

        String tmp = value.toString();

        return tmp.trim();
    }
}
