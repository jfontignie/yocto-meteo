import java.io.IOException;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:59 PM
 */
public interface YoctoTemplate {
    public Map<String, Object> query(String relativePath) throws IOException;
}
