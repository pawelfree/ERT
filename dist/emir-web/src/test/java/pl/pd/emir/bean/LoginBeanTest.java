package pl.pd.emir.bean;

import pl.pd.emir.bean.LoginBean;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LoginBeanTest {

    @Test
    public void testGetLogin() {
        LoginBean instance = new LoginBean();
        String expResult = "Jan_Kowalski";
        instance.setLoginTo(expResult);
        String result = instance.getLoginTo();
        assertEquals("getLogin", expResult, result);
    }

    @Test
    public void testGetPassword() {
        LoginBean instance = new LoginBean();
        String expResult = "expResult";
        instance.setPassword(expResult);
        String result = instance.getPassword();
        assertEquals("getPassword", expResult, result);
    }

    @Test
    public void testReset() {
        LoginBean instance = new LoginBean();
        instance.reset();
        assertEquals("getPassword", "", instance.getPassword());
        assertEquals("getLopgin", "", instance.getLoginTo());

    }
}
