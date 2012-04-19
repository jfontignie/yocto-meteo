import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public class YoctoMeteo implements YoctoObject{

    private String relativeURL;

    public YoctoMeteo(Map<String, Object> service) {
        setLogicalName(service.get("logicalName").toString());
        setRelativeUrl(service.get("networkUrl")+".json");
    }

    public String getRelativeURL() {
        return relativeURL;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeURL = relativeUrl;
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

    public String getLogicalName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String setLogicalName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public YoctoProduct getProduct() {
        return YoctoProduct.YOCTO_METEO;
    }
}
