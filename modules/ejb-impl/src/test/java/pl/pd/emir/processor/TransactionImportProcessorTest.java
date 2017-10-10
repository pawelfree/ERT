package pl.pd.emir.processor;

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
//        ProcessingWarnings warnings = new ProcessingWarnings();
//
//        instance.process(reader, parser, fileName, importFileDate, importLog, warnings, new ImportOverview());
//
//        assertEquals(parser.getRowNum(), 107);
//    }

}
