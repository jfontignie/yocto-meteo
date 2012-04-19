/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:10 AM
 */
public enum YoctoProduct {

    YOCTO_HUB(0, "VirtualHub"),
    YOCTO_METEO(24, "Yocto-meteo"),
    YOCTO_RELAY(-1, "???");


    private int productId;
    private String productName;

    private YoctoProduct(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public static YoctoProduct getFromProductId(String productId) {
        return getFromProductId(Integer.getInteger(productId));
    }

    private boolean matches(int productId) {
        return this.productId == productId;
    }

    public static YoctoProduct getFromProductId(int productId) {
        for (YoctoProduct p : YoctoProduct.values()) {
            if (p.matches(productId)) return p;
        }
        return null;
    }


}
