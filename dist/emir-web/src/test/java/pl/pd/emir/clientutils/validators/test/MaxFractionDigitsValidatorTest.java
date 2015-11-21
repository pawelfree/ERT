package pl.pd.emir.clientutils.validators.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import pl.pd.emir.clientutils.validators.MaxFractionDigitsValidator;

public class MaxFractionDigitsValidatorTest {

    private final MaxFractionDigitsValidator validator = new MaxFractionDigitsValidator();

    public MaxFractionDigitsValidatorTest() {
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
//    public void MaxFractionDigitsValidatorTest1() throws Exception {
//        try {
//            validator.validate(null, null, "1");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//    }
//
//    @Test
//    public void MaxFractionDigitsValidatorTest2() throws Exception {
//        try {
//            validator.validate(null, null, "1.000");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//
//    }
//
//    @Test
//    public void MaxFractionDigitsValidatorTest3() throws Exception {
//        try {
//            validator.validate(null, null, "1.00000");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(false);
//        }
//
//    }
//
//    @Test
//    public void MaxFractionDigitsValidatorTest4() throws Exception {
//        try {
//            validator.validate(null, null, "1.000000");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void MaxFractionDigitsValidatorTest5() throws Exception {
//        try {
//            validator.validate(null, null, "1.000000000");
//        } catch (ValidatorException ex) {
//            Assert.assertTrue(true);
//        }
//    }
}
