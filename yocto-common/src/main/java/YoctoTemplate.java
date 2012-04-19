import java.net.URL;
import java.util.HashMap;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:59 PM
 */
public interface YoctoTemplate {
    public HashMap<String,YoctoRelay> refreshRelays(URL url);
}
