package pl.pd.emir.kdpw.service.utils;

import pl.pd.emir.kdpw.service.utils.KdpwUtils;
import java.util.ArrayList;
import java.util.List;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import org.junit.Assert;
import org.junit.Test;

public class KdpwUtilsTest {

    @Test
    public void testGetMsgs() {
        String msgs = KdpwUtils.getMsgs("kdpw.event.message.insert");
        Assert.assertEquals("Typ komunikatu:", msgs);
    }

    @Test
    public void testAllWithResponseEmptyList() {
        List<KdpwMsgItem> items = new ArrayList<>();
        Assert.assertTrue("Wszystkie bez odpowiedzi bo pusta lista", KdpwUtils.allWithResponse(items));
    }

    @Test
    public void testAllWithResponseAllWithoutResponse() {
        List<KdpwMsgItem> items = new ArrayList<>();
        items.add(KdpwMsgItem.getResponse(null, Long.MAX_VALUE, null, null, null, null, Long.MAX_VALUE, null, null));
        Assert.assertFalse("Wszystkie bez odpowiedzi - jednoelementowa lista", KdpwUtils.allWithResponse(items));
    }

    @Test
    public void testAllWithResponseAllWithoutResponse2() {
        List<KdpwMsgItem> items = new ArrayList<>();
        items.add(KdpwMsgItem.getResponse(null, Long.MAX_VALUE, null, null, null, null, Long.MAX_VALUE, null, null));
        items.add(KdpwMsgItem.getResponse(null, Long.MAX_VALUE, null, null, null, null, Long.MAX_VALUE, null, null));
        items.add(KdpwMsgItem.getResponse(null, Long.MAX_VALUE, null, null, null, null, Long.MAX_VALUE, null, null));
        Assert.assertFalse("Wszystkie bez odpowiedzi - trzyelementowa lista", KdpwUtils.allWithResponse(items));
    }

    @Test
    public void testAllWithResponseAllWithResponse() {
        List<KdpwMsgItem> items = new ArrayList<>();
        KdpwMsgItem item = KdpwMsgItem.getRequest(1L, null);
        item.addResponse(KdpwMsgItem.getRequest(11L, null));
        items.add(item);
        Assert.assertTrue("Wszystkie z odpowiedzia - jednoelementowa lista", KdpwUtils.allWithResponse(items));
    }

    @Test
    public void testAllWithResponseAllWithResponse2() {
        List<KdpwMsgItem> items = new ArrayList<>();
        KdpwMsgItem item = KdpwMsgItem.getRequest(1L, null);
        item.addResponse(KdpwMsgItem.getRequest(11L, null));
        items.add(item);
        item = KdpwMsgItem.getRequest(2L, null);
        item.addResponse(KdpwMsgItem.getRequest(22L, null));
        items.add(item);
        item = KdpwMsgItem.getRequest(3L, null);
        item.addResponse(KdpwMsgItem.getRequest(33L, null));
        items.add(item);
        Assert.assertTrue("Wszystkie z odpowiedzia - trzyelementowa lista", KdpwUtils.allWithResponse(items));
    }

    @Test
    public void testAllWithResponseOneWithResponse2() {
        List<KdpwMsgItem> items = new ArrayList<>();
        items.add(KdpwMsgItem.getRequest(1L, null));
        KdpwMsgItem item = KdpwMsgItem.getRequest(2L, null);
        item.addResponse(KdpwMsgItem.getRequest(22L, null));
        items.add(item);
        items.add(KdpwMsgItem.getRequest(3L, null));
        items.add(KdpwMsgItem.getRequest(4L, null));
        Assert.assertFalse("Jeden z odpowiedzia - czteroelementowa lista", KdpwUtils.allWithResponse(items));
    }

}
