import java.io.IOException;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public class YoctoRelay extends YoctoObjectImpl implements YoctoObject {


    public YoctoRelay(YoctoTemplate template, String relativePath) throws IOException {
        super(YoctoProduct.YOCTO_RELAY, template, relativePath + ".json");
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

    @Override
    protected void refreshObject(Map<String, Object> result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLogicalName(String name) {

    }


}
