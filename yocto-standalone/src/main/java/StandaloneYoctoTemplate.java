import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:28 AM
 */
public class StandaloneYoctoTemplate implements YoctoTemplate {

    public static final String API_JSON = "api.json";
    public static final String WHITE_PAGES = "whitePages";
    public static final String SERVICES = "services";

    public StandaloneYoctoTemplate() {
    }


    public Map<String, Object> query(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String content = URLConnectionReader.getContent(url);

        return (Map<String, Object>) mapper.readValue(content, Map.class);
    }

    public void aSyncQuery(URL url, QueryListener listener) throws IOException {
        Thread thread = new Thread(new BackgroundQuerier(url, listener));
        thread.run();
    }

    public YoctoList refreshAll(URL url) throws IOException {
        YoctoList list = new YoctoList();
        URL api = new URL(url, API_JSON);
        Map<String, Object> map = query(api);
        Map<String,Object> services = (Map<String, Object>) map.get(SERVICES);
        List<Map<String,Object>> whitePages = (List<Map<String, Object>>) services.get(WHITE_PAGES);
        for (Map<String,Object> service : whitePages){
            YoctoProduct product = YoctoProduct.getFromProductId((Integer) service.get("productId"));
            YoctoObject object = createObject(product,service);
            if (object != null)
                list.add(object);
        }

        System.out.println(whitePages);
        return list;
    }

    private YoctoObject createObject(YoctoProduct product, Map<String, Object> service) {
        switch(product){
            case YOCTO_METEO:
                return new YoctoMeteo(service);
            case YOCTO_HUB:
                break;
            default:
                throw new IllegalStateException("Not implemented yet");
        }
        return null;
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
            StringBuffer buffer = new StringBuffer();
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
