import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
class YoctoHub {
    private URL url ;

    private YoctoList yoctoList;

    private YoctoTemplate yoctoTemplate;

    public YoctoHub(URL url) {
        this.url = url;
    }

    public void setYoctoTemplate(YoctoTemplate yoctoTemplate){
        this.yoctoTemplate = yoctoTemplate;
        refresh();
    }

    public YoctoRelay findRelay(String name) {
        return (YoctoRelay) yoctoList.get(YoctoProduct.YOCTO_RELAY,name);
    }

    public YoctoMeteo findMeteo(String name){
        return (YoctoMeteo) yoctoList.get(YoctoProduct.YOCTO_METEO,name);
    }

    public void refresh(){

    }

    protected void refreshAll() {

    }

    protected void refreshColor() {
        throw new IllegalStateException("Not implemented yet");
    }
}
