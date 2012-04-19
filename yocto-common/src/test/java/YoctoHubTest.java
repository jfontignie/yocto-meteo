import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:03 AM
 */
public class YoctoHubTest {

    private YoctoTemplate yoctoTemplate;


    private final static String CONTENT = "{\n" +
            "\"module\":{\"productName\":\"VirtualHub\",\"serialNumber\":\"VIRTHUB0-480741bdd3\",\"logicalName\":\"\",\"productId\":0,\"productRelease\":1,\"firmwareRelease\":\"6019\",\"persistentSettings\":0,\"luminosity\":50,\"beacon\":0,\"upTime\":3402559538,\"usbCurrent\":0,\"realmHTTP\":\"YoctoDevices\",\"adminPassword\":\"\",\"userPassword\":\"\",\"rebootCountdown\":0,\"usbBandwidth\":0},\n" +
            "\"services\":{\n" +
            "\"whitePages\":[\n" +
            "{\"serialNumber\":\"VIRTHUB0-480741bdd3\",\"logicalName\":\"\",\"productName\":\"VirtualHub\",\"productId\":0,\"networkUrl\":\"/api\",\"beacon\":0,\"index\":0},\n" +
            "{\"serialNumber\":\"METEOMK1-0268C\",\"logicalName\":\"\",\"productName\":\"Yocto-Meteo\",\"productId\":24,\"networkUrl\":\"/bySerial/METEOMK1-0268C/api\",\"beacon\":0,\"index\":1}],\n" +
            "\"yellowPages\":{\n" +
            "\"DataLogger\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.dataLogger\",\"logicalName\":\"\",\"advertisedValue\":\"OFF\",\"index\":0}],\n" +
            "\"Temperature\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.temperature\",\"logicalName\":\"\",\"advertisedValue\":\"20.7\",\"index\":1}],\n" +
            "\"Pressure\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.pressure\",\"logicalName\":\"\",\"advertisedValue\":\"945.0\",\"index\":2}],\n" +
            "\"Humidity\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.humidity\",\"logicalName\":\"\",\"advertisedValue\":\"53.0\",\"index\":3}]}}}";

    @Before
    public void setUp() throws IOException {
        yoctoTemplate = EasyMock.createMock(YoctoTemplate.class);
    }

    @Test
    public void testRefresh() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> content = mapper.readValue(CONTENT, Map.class);

        EasyMock.expect(yoctoTemplate.query("api.json")).andReturn(
                content);

        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        hub.isOnline();
    }

}
