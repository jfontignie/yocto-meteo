import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:28 AM
 */
public class StandaloneYoctoTemplate implements YoctoTemplate {

    private URL url;

    public StandaloneYoctoTemplate(URL url) {
        this.url = url;
    }


    private Map<String, Object> query(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String content = URLConnectionReader.getContent(url);

        return (Map<String, Object>) mapper.readValue(content, Map.class);
    }

    public void aSyncQuery(URL url, QueryListener listener) throws IOException {
        Thread thread = new Thread(new BackgroundQuerier(url, listener));
        thread.run();
    }

    public Map<String, Object> query(String relativePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String content = URLConnectionReader.getContent(new URL(url, relativePath));
        return (Map<String, Object>) mapper.readValue(content, Map.class);
    }

    private class BackgroundQuerier implements Runnable {

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
        public void resultEvent(Map<String, Object> result);

        public void exceptionEvent(IOException e);
    }

    private static class URLConnectionReader {
        public static String getContent(URL url) throws IOException {
            StringBuilder buffer = new StringBuilder();
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                buffer.append(inputLine);
            in.close();
            return buffer.toString();
        }
    }
}
