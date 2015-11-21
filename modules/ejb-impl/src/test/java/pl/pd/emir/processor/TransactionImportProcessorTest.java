package pl.pd.emir.processor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.parsers.TransactionCsvParserTmb;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author PawelDudek
 */
//@RunWith(MockitoJUnitRunner.class)
public class TransactionImportProcessorTest {

//    public String fileName = "src/test/resources/140807_TRANSAKCJE.csv";
//
//    @Mock
//    ClientManager clientManager;
//    @Mock
//    TransactionManager transactionManager;
//
//    Client client = new Client();
//
//    public TransactionImportProcessorTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @Test
//    public void testProcessCorrectNumberOfRows() throws Exception {
//        Mockito.when(clientManager.getClientByOrginalId(any(String.class))).thenReturn(client);
//
//        TransactionImportProcessor instance = new TransactionImportProcessor();
//        instance.transactionManager = transactionManager;
//        instance.clientManager = clientManager;
//
//        Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
//        BaseCsvParser parser = new TransactionCsvParserTmb(null, clientManager);
//
//        Date importFileDate = new Date(2014, 8, 7);
//        ImportLog importLog = new ImportLog(new Date(), "testLogin",
//                ImportScope.TRANSACTION_E, importFileDate, ImportStatus.DURING_PROCESSING, null);
//        importLog.setFileName(fileName);
//
//        boolean backloading = false;
//        ProcessingWarnings warnings = new ProcessingWarnings();
//
//        instance.process(reader, parser, fileName, importFileDate, importLog, backloading, warnings, new ImportOverview());
//
//        assertEquals(parser.getRowNum(), 107);
//    }

}
