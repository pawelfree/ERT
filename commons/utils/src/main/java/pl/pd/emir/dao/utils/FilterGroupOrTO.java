package pl.pd.emir.dao.utils;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FilterGroupOrTO")
public class FilterGroupOrTO extends AbstractFilterTO {

    private final static long serialVersionUID = 1l;
    private transient final List<AbstractFilterTO> groupFilters = new ArrayList<>();

    public FilterGroupOrTO() {
        super();
    }

    public void add(FilterStringTO filterStringTO) {
        groupFilters.add(filterStringTO);
    }

    public void add(FilterIntegerTO filterIntegerTO) {
        groupFilters.add(filterIntegerTO);
    }

    public List<AbstractFilterTO> getGroupFilters() {
        return groupFilters;
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
