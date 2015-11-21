package pl.pd.emir.processor;

import pl.pd.emir.imports.ImportOverview;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.parsers.BaseCsvParser;

/**
 *
 * @author PawelDudek
 */
public interface IImportProcessor {

    public void process(Reader reader, BaseCsvParser parser, String fileName, Date importFileDate,
            ImportLog importLog, boolean backloading, ProcessingWarnings warnings, ImportOverview overview) throws IOException;

}
