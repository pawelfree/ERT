package pl.pd.emir.clientutils.converter;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pl.pd.emir.commons.interfaces.Identifiable;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !"".equals(value) && value instanceof Identifiable) {
            Identifiable entity = (Identifiable) value;
            this.addAttribute(component, entity);
            if (entity.getId() != null) {
                return entity.getId().toString();
            }
        }
        if (value == null) {
            value = "";
        }
        return value.toString();
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

    private void addAttribute(UIComponent component, Identifiable entity) {
        if (entity != null && entity.getId() != null) {
            String key = entity.getId().toString();
            this.getAttributesFrom(component).put(key, entity);
        }
    }
}
