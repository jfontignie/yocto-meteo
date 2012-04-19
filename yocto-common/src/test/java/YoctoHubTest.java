import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:03 AM
 */
public class YoctoHubTest {

    private YoctoHub hub;
    private YoctoTemplate yoctoTemplate;
    private URL url;


    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("http://127.0.0.1");
        hub = new YoctoHub(url);
        yoctoTemplate = EasyMock.createMock(YoctoTemplate.class);
        hub.setYoctoTemplate(yoctoTemplate);
    }

    @Test
    public void testRefresh() throws Exception {

        EasyMock.expect(yoctoTemplate.refreshAll(url)).andReturn(new YoctoList());
    }
}
