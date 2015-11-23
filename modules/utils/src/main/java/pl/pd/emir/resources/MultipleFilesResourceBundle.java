package pl.pd.emir.resources;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MultipleFilesResourceBundle extends ResourceBundle implements Serializable {

    protected static final String BUNDLE_NAME = "messages";
    protected static final String BUNDLE_EXTENSION = "properties";
    protected static final Control MRF_CONTROL = new MultipleFilesResourceBundleControl(BUNDLE_EXTENSION);

    public MultipleFilesResourceBundle() {
        this(BUNDLE_NAME);
    }

    public MultipleFilesResourceBundle(final String bundleName) {
        super();
        ResourceBundle newParent = ResourceBundle.getBundle(bundleName, MRF_CONTROL);
        setParent(newParent);
    }

    @Override
    protected Object handleGetObject(final String key) {
        return (null != key && null != parent) ? parent.getObject(key) : "";
    }

    @Override
    public Enumeration getKeys() {
        return parent.getKeys();
    }

    public static String getMessageResourceString(final ResourceBundle bundle, final String key, final Object params[]) {
        String text;
        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }
        if (params != null) {
            MessageFormat mf = new MessageFormat(text);
            text = mf.format(params, new StringBuffer(), null).toString();
        }
        return text;
    }
}
