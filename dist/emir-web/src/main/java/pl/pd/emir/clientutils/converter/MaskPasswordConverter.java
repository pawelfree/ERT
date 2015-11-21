package pl.pd.emir.clientutils.converter;

import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pl.pd.emir.commons.StringUtil;

@FacesConverter("pl.pd.emir.clientutils.converter.MaskPasswordConverter")
public class MaskPasswordConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (StringUtil.isEmpty(value)) {
            return null;
        }

        return value;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        String tmp = "*";
        for (int i = 0; i <= value.toString().length(); i++) {
            tmp = tmp.concat(tmp);
        }
        return tmp;
    }
}
