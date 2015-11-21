package pl.pd.emir.processor;

/**
 *
 * @author PawelDudek
 */
public class ProcessingWarnings {

    private boolean flagWarningPro = false;
    private boolean flagWarningVal = false;

    public boolean isFlagWarningPro() {
        return flagWarningPro;
    }

    public void setFlagWarningPro(boolean flagWarningPro) {
        this.flagWarningPro = flagWarningPro;
    }

    public boolean isFlagWarningVal() {
        return flagWarningVal;
    }

    public void setFlagWarningVal(boolean flagWarningVal) {
        this.flagWarningVal = flagWarningVal;
    }
}
