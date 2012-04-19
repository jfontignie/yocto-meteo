import java.io.IOException;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 3:27 PM
 */
abstract class YoctoObjectImpl implements YoctoObject {
    protected YoctoTemplate template;
    private String relativePath;

    private String logicalName;
    private YoctoProduct product;
    private String serialNumber;

    public YoctoObjectImpl(YoctoProduct product, YoctoTemplate template, String relativePath) throws IOException {
        this.template = template;
        this.relativePath = relativePath;
        this.product = product;
    }

    private Map<String, Object> query() throws IOException {
        Map<String, Object> result = template.query(relativePath);
        System.out.println(result);
        return result;
    }

    public String getLogicalName() {
        return logicalName;
    }

    public YoctoProduct getProduct() {
        return product;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void refresh() throws IOException {
        Map<String, Object> result = query();
        Map<String, Object> module = (Map<String, Object>) result.get("module");
        logicalName = module.get("logicalName").toString();
        serialNumber = module.get("serialNumber").toString();
        refreshObject(result);
    }

    protected abstract void refreshObject(Map<String, Object> result) throws IOException;


}
