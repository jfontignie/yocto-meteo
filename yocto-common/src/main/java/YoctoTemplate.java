import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:59 PM
 */
public interface YoctoTemplate {
    public YoctoList refreshAll(URL url) throws IOException;
}
