import java.io.IOException;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public class YoctoMeteo extends YoctoObjectImpl implements YoctoObject {

    private YoctoValue temperature;
    private YoctoValue humidity;

    public YoctoMeteo(YoctoTemplate template, String relativePath) throws IOException {
        super(YoctoProduct.YOCTO_METEO, template, relativePath + ".json");
    }

    public String describe() {
        throw new IllegalStateException("Not IMplemented yet");
    }

    public boolean isOnline() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load(int ms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public YoctoValue getTemperature() {
        return temperature;
    }

    public YoctoValue getHumidity() {
        return humidity;
    }


    @Override
    protected void refreshObject(Map<String, Object> result) {
        temperature = createValue(result, "temperature");
        humidity = createValue(result, "humidity");
    }

    private YoctoValue createValue(Map<String, Object> result, String name) {
        Map<String, Object> value = (Map<String, Object>) result.get(name);
        return new YoctoValue(value);
    }
}
