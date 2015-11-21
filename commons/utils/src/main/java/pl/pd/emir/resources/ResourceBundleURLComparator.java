package pl.pd.emir.resources;

import java.io.Serializable;
import java.net.URL;
import java.util.Comparator;

public class ResourceBundleURLComparator implements Comparator<URL>, Serializable {

    private final static long serialVersionUID = 1l;
    private final URL mainPropertyURL;

    public ResourceBundleURLComparator(URL mainPropertyURL) {
        this.mainPropertyURL = mainPropertyURL;
    }

    @Override
    public int compare(URL o1, URL o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            } else {
                return -1;
            }
        }
        if (o1.sameFile(mainPropertyURL)) {
            return 1;
        }
        return -1;
    }
}
