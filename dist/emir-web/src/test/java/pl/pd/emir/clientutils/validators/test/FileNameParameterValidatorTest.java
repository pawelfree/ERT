package pl.pd.emir.clientutils.validators.test;

import javax.faces.validator.ValidatorException;
import pl.pd.emir.clientutils.validators.FileNameParameterValidator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileNameParameterValidatorTest {

    private final FileNameParameterValidator validator = new FileNameParameterValidator();

    public FileNameParameterValidatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void fileNameParameterValidatorTest1() throws Exception {
        try {
            validator.validate(null, null, "\"plik danych\"+03-12-12 12:12:12");
            assertTrue(true);
        } catch (ValidatorException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void fileNameParameterValidatorTest2() throws Exception {
        try {
            validator.validate(null, null, "\"a\"+03-12-12 12:12:12");
            assertTrue(true);
        } catch (ValidatorException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void fileNameParameterValidatorTest3() throws Exception {
        try {
            validator.validate(null, null, "\"plik 1 2 3\"+03-12-12 12:12:12");
            assertTrue(true);
        } catch (ValidatorException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void fileNameParameterValidatorTest4() throws Exception {
        try {
            validator.validate(null, null, "\"wielki plik o dziwnej nazwie\"+01-01-01 01:01:00");
            assertTrue(true);
        } catch (ValidatorException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void fileNameParameterValidatorTest5() throws Exception {
        try {
            validator.validate(null, null, "\"plikus\"+01-01-01 24:00:00");
            assertTrue(true);
        } catch (ValidatorException ex) {
            assertTrue(false);
        }
    }
}
