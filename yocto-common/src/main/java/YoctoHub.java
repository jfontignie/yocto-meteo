import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 */
class YoctoHub extends YoctoObjectImpl implements YoctoObject {

    private YoctoList yoctoList;

    public YoctoHub(YoctoTemplate template) throws IOException {
        super(YoctoProduct.YOCTO_HUB, template, "api.json");
        refresh();
    }


    public YoctoRelay findRelay(String name) {
        return (YoctoRelay) yoctoList.get(YoctoProduct.YOCTO_RELAY, name);
    }

    public YoctoMeteo findMeteo(String name) {
        return (YoctoMeteo) yoctoList.get(YoctoProduct.YOCTO_METEO, name);
    }


    public Collection<YoctoObject> findAll(YoctoProduct product) {
        return yoctoList.findAll(product).values();
    }

    @Override
    protected void refreshObject(Map<String, Object> result) throws IOException {
        yoctoList = new YoctoList();

        Map<String, Object> services = (Map<String, Object>) result.get("services");
        List<Map<String, Object>> whitePages = (List) services.get("whitePages");
        System.out.println(whitePages);

        for (Map<String, Object> service : whitePages) {
            System.out.println(service);
            YoctoProduct product = YoctoProduct.getFromProductId((Integer) service.get("productId"));
            yoctoList.add(createObject(product, service));
        }
    }

    private YoctoObject createObject(YoctoProduct product, Map<String, Object> service) throws IOException {
        String networkUrl = service.get("networkUrl").toString();
        switch (product) {
            case YOCTO_HUB:
                return this;
            case YOCTO_METEO:
                return new YoctoMeteo(template, networkUrl);
            default:
                throw new IllegalStateException("Not implemented yet");
        }
    }

    public String describe() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isOnline() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load(int ms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
