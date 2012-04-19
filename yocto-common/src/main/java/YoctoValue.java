import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 12:32 PM
 */
public class YoctoValue {

    private Map<String, Object> service;

    public YoctoValue(Map<String, Object> service) {
        this.service = service;
    }

    public float getLogicalName() {
        return Float.valueOf(service.get("logicalName").toString());
    }

    public float getAdvertisedValue() {
        return Float.valueOf(service.get("advertisedValue").toString());
    }

    public float getCurrentValue() {
        return Float.valueOf(service.get("currentValue").toString());
    }

    public float getLowestValue() {
        return Float.valueOf(service.get("lowestValue").toString());
    }

    public float getHighestValue() {
        return Float.valueOf(service.get("highestValue").toString());
    }
}
