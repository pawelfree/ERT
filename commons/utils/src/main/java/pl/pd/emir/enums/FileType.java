package pl.pd.emir.enums;

import org.apache.commons.io.FilenameUtils;

public enum FileType {

    XML("xml"), PDF("pdf"), TXT("txt");

    private final String extension;

    private FileType(String extension) {
        this.extension = extension;
    }

    public String getExt() {
        return extension;
    }

    public static FileType recognize(final String fileName) {
        if (fileName != null) {
            final String extension = FilenameUtils.getExtension(fileName);
            if (extension != null) {
                for (FileType value : values()) {
                    if (value.name().equalsIgnoreCase(extension)) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    public String getExtension() {
        return "/(\\.|\\/)(" + this.getExt().toLowerCase() + ")$/";
    }
}
