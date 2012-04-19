import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertNotNull;

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
        url = new URL("http://127.0.0.1:4444");
        yoctoTemplate = new StandaloneYoctoTemplate(url);
        fullUrl = new URL(url, "api.json");
    }

    @Test
    public void testQuery() throws IOException {
        assertNotNull(yoctoTemplate.query("api.json"));
    }


}
