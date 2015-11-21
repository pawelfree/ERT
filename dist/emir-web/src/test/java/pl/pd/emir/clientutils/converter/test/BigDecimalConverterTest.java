package pl.pd.emir.clientutils.converter.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BigDecimalConverterTest {

    public BigDecimalConverterTest() {
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
//    public void BigDecimalConverterTest1() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "1.00");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "1.00");
//    }
//
//    @Test
//    public void BigDecimalConverterTest2() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "1,00");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "1.00");
//    }
//
//    @Test
//    public void BigDecimalConverterTest3() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        String o = converter.getAsString(null, null, new BigDecimal(1.0000));
//        Assert.assertEquals(o, "1,00000");
//    }
//
//    @Test
//    public void BigDecimalConverterTest4() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        String o = converter.getAsString(null, null, new BigDecimal(1.000));
//        Assert.assertEquals(o, "1,00000");
//    }
//
//    @Test
//    public void BigDecimalConverterTest5() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        String o = converter.getAsString(null, null, new BigDecimal(1.00));
//        Assert.assertEquals(o, "1,00000");
//    }
//
//    @Test
//    public void BigDecimalConverterTest6() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        String o = converter.getAsString(null, null, new BigDecimal(1.0));
//        Assert.assertEquals("1,00000", o);
//    }
//
//    @Test
//    public void BigDecimalConverterTest7() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        String o = converter.getAsString(null, null, new BigDecimal(1));
//        Assert.assertEquals("1,00000", o);
//    }
//
//    @Test
//    public void BigDecimalConverterTest8() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "abcd");
//        Assert.assertEquals(o, null);
//    }
//
//    @Test
//    public void BigDecimalConverterTest9() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "");
//        Assert.assertEquals(o, null);
//    }
//
//    @Test
//    public void BigDecimalConverterTest10() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, " ");
//        Assert.assertEquals(o, null);
//    }
//
//    @Test
//    public void BigDecimalConverterTest11() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, null);
//        Assert.assertEquals(o, null);
//    }
//
//    @Test
//    public void BigDecimalConverterTest12() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "1,0012346");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "1.0012346");
//    }
//
//    @Test
//    public void BigDecimalConverterTest13() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "100 000 000,0012346");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "100000000.0012346");
//    }
//
//    @Test
//    public void BigDecimalConverterTest14() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "1");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "1");
//    }
//
//    @Test
//    public void BigDecimalConverterTest15() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "-1,0012346");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "-1.0012346");
//    }
//
//    @Test
//    public void BigDecimalConverterTest16() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "-0,0000000000");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "0E-10");
//    }
//
//    @Test
//    public void BigDecimalConverterTest17() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "-10000,0012346");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "-10000.0012346");
//    }
//
//    @Test
//    public void BigDecimalConverterTest18() {
//        BigDecimalConverter converter = new BigDecimalConverter();
//        Object o = converter.getAsObject(null, null, "-10000000000,0012346");
//        BigDecimal bg = (BigDecimal) o;
//        Assert.assertEquals(bg.toString(), "-10000000000.0012346");
//    }
}
