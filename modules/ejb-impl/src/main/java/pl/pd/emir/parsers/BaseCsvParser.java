package pl.pd.emir.parsers;

import com.googlecode.jcsv.reader.CSVEntryParser;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Extract;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;

public abstract class BaseCsvParser<E extends Extract> implements CSVEntryParser<ImportResult<E>> {

    protected transient int rowNum = 0;

    private transient ImportResult importResult;

    @Override
    public ImportResult parseEntry(String... data) {

        importResult = new ImportResult();

        rowNum += 1;

        if (getRowIdIndex() != null) {
            importResult.setRecordId(getRowId(data[getRowIdIndex()], rowNum));
        } else {
            importResult.setRecordId(getRowId("", rowNum));
        }

        E extract = parseAndValidateRow(importResult, data);

        importResult.setExtract(extract);

        return importResult;
    }

    public abstract E parseAndValidateRow(ImportResult importResult, String... data);

    public abstract Integer getRowIdIndex();

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    private String getRowId(String id, int rowNum) {
        String rowId = StringUtil.shorten(id, 100);
        rowId = String.format("%s %s", rowId,
                ImportFaillogUtils.getString(ImportFaillogUtils.ImportFaillogKey.LINE_INDICATOR, rowNum));
        return rowId;
    }

}
