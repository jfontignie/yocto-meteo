/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public interface YoctoObject {
    public String describe();

    public boolean isOnline();

    public void load(int ms);

    public String getLogicalName();

    public String setLogicalName(String name);

    YoctoProduct getProduct();
}
