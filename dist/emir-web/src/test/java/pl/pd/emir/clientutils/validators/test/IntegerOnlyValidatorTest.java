package pl.pd.emir.clientutils.validators.test;

import pl.pd.emir.clientutils.validators.IntegerOnlyValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class IntegerOnlyValidatorTest {

    private final IntegerOnlyValidator validator = new IntegerOnlyValidator();

    public IntegerOnlyValidatorTest() {
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

//    @Test
//    public void IntegerOnlyValidator() throws Exception {
//        try {
//            validator.validate(null, null, "1000000");
//            Assert.assertTrue(true);
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//    }
//
//    @Test
//    public void IntegerOnlyValidator1() throws Exception {
//        try {
//            validator.validate(null, null, "1");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//    }
//
//    @Test
//    public void IntegerOnlyValidator2() throws Exception {
//        try {
//            validator.validate(null, null, "100,0000");
//            Assert.assertTrue(false);
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void IntegerOnlyValidator3() throws Exception {
//        try {
//            validator.validate(null, null, "100,");
//            Assert.assertTrue(false);
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void IntegerOnlyValidator4() throws Exception {
//        try {
//            validator.validate(null, null, "1,000000");
//            Assert.assertTrue(false);
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//    }
}
