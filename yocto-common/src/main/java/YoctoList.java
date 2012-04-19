import java.util.HashMap;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:16 AM
 */
public class YoctoList {
    private HashMap<YoctoProduct,HashMap<String, YoctoObject>> map;

    public YoctoList(){
          clear();
    }


    public YoctoObject get(YoctoProduct product, String name){
        HashMap<String,YoctoObject> objects = map.get(product);
        if (objects == null) return null;
        return objects.get(name);
    }

    protected void add(YoctoObject object){
        HashMap<String,YoctoObject> objects = map.get(object.getProduct());
        if (objects == null) {
            objects = new HashMap<String, YoctoObject>();
            map.put(object.getProduct(),objects);
        }
        objects.put(object.getLogicalName(),object);
    }

    protected void clear(){
        map = new HashMap<YoctoProduct, HashMap<String, YoctoObject>>();
    }

    public HashMap<String,YoctoObject> findAll(YoctoProduct product) {
        return map.get(product);
    }
}
