/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:45 PM
 */
public class CachedObject {

    private int timeout;

    public CachedObject(){
        timeout = 1000;
    }

    public void setTimeout(int ms) {
        this.timeout = ms;
    }
}
