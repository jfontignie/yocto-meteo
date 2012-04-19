import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:28 AM
 */
public class StandaloneYoctoTemplate implements YoctoTemplate {

    public StandaloneYoctoTemplate() {}

    public HashMap<String, YoctoRelay> refreshRelays(URL url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public Map<String,Object> query(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (Map<String,Object>) mapper.readValue(url.getContent().toString(),Map.class);
    }

    public void aSyncQuery(URL url, QueryListener listener) throws IOException {
        Thread thread = new Thread(new BackgroundQuerier(url,listener));
        thread.run();
    }

    private class BackgroundQuerier implements Runnable{

        private QueryListener listener;
        private URL url;

        public BackgroundQuerier(URL url, QueryListener listener) {
            this.url = url;
            this.listener = listener;
        }

        public void run() {
            try {
                listener.resultEvent(query(url));
            } catch (IOException e) {
                listener.exceptionEvent(e);
            }
        }
    }

    public interface QueryListener {
        public void resultEvent(Map<String,Object> result);

        public void exceptionEvent(IOException e);
    }
}
