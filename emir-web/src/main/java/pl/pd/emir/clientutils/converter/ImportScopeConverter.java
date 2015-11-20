package pl.pd.emir.clientutils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.enums.ImportScope;

@FacesConverter(value = "importScopeConverter")
public class ImportScopeConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportScopeConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ImportScope result = null;
        if (StringUtil.isNotEmpty(value)) {
            try {
                return ImportScope.valueOf(value);
            } catch (IllegalArgumentException ex) {
                LOGGER.warn(String.format("Can't convert value %s to ImportScope.", value), ex);
            }
        }
        return result;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String result = "";
        if (value != null) {
            try {
                result = ((ImportScope) value).name();
            } catch (ClassCastException ex) {
                LOGGER.warn(String.format("Can't convert value %s to ImportScope.", value), ex);
            }
        }
        return result;
    }
}
