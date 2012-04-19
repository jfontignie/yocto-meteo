import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 12:54 PM
 */
public class StandaloneYoctoHubTest {

    private YoctoHub hub;

    @Before
    public void setUp() throws IOException {
        YoctoTemplate template = new StandaloneYoctoTemplate(new URL("http://127.0.0.1:4444"));
        hub = new YoctoHub(template);
    }

    @Test
    public void test() throws IOException {
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (YoctoObject object : objects) {
            YoctoMeteo meteo = (YoctoMeteo) object;
            meteo.refresh();
            assertTrue(meteo.getTemperature().getCurrentValue() > 0);
        }
    }

}
