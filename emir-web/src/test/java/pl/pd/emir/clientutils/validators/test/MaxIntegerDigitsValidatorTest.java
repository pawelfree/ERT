package pl.pd.emir.clientutils.validators.test;

import pl.pd.emir.clientutils.validators.MaxIntegerDigitsValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class MaxIntegerDigitsValidatorTest {

    private final MaxIntegerDigitsValidator validator = new MaxIntegerDigitsValidator();

    public MaxIntegerDigitsValidatorTest() {
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
//    public void MaxIntegerDigitsValidator() throws Exception {
//        try {
//            validator.validate(null, null, "");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator1() throws Exception {
//        try {
//            validator.validate(null, null, "");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator2() throws Exception {
//        try {
//            validator.validate(null, null, "12345678901.00");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator3() throws Exception {
//        try {
//            validator.validate(null, null, "123456789012.00");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator4() throws Exception {
//        try {
//            validator.validate(null, null, "123456789012");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator5() throws Exception {
//        try {
//            validator.validate(null, null, "123456789012");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void MaxIntegerDigitsValidator6() throws Exception {
//        try {
//            validator.validate(null, null, "123456789012");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
}
