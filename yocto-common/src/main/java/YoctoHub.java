import java.net.URL;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
class YoctoHub {
    private URL url;

    private HashMap<String, YoctoRelay> relays;

    private YoctoTemplate yoctoTemplate;

    public YoctoHub(URL url) {
        this.url = url;
        relays = new HashMap<String, YoctoRelay>();
    }

    public void setYoctoTemplate(YoctoTemplate yoctoTemplate){
        this.yoctoTemplate = yoctoTemplate;
        refresh();
    }

    public YoctoRelay findRelay(String name) {
        return relays.get(name);
    }

    public void refresh(){
        refreshRelays();
        refreshColor();
    }

    protected void refreshRelays() {
        relays = yoctoTemplate.refreshRelays(url);
    }

    protected void refreshColor() {
        throw new IllegalStateException("Not implemented yet");
    }
}
