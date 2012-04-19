import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:05 AM
 */
public class StandaloneYoctoTemplateTest {

    private StandaloneYoctoTemplate yoctoTemplate;
    private URL fullUrl;
    private URL url;


    @Before
    public void setUp() throws MalformedURLException {
        yoctoTemplate = new StandaloneYoctoTemplate();
        url = new URL("http://127.0.0.1:4444");
        fullUrl = new URL(url,"api.json");
    }

    @Test
    public void testQuery() throws IOException {
        System.out.println(yoctoTemplate.query(fullUrl));
    }

    @Test public void testRefreshAll() throws IOException {
        YoctoList list = yoctoTemplate.refreshAll(url);
        HashMap<String,YoctoObject> meteos =list.findAll(YoctoProduct.YOCTO_METEO);
        assertEquals(meteos.size(),1);
    }
}
