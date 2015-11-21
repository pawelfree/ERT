package pl.pd.emir.commons.interfaces;

public interface Selectable<PK extends Long> extends Identifiable<PK> {

    boolean isSelected();

    void setSelected(boolean selected);
}
