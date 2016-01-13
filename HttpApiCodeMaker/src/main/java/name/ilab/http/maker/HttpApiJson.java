package name.ilab.http.maker;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class HttpApiJson {
    private HttpApi global;
    private Map<String, HttpApi> local;

    public void refresh() {
        global.prepare();
        for (Map.Entry<String, HttpApi> entry : local.entrySet()) {
            entry.getValue().prepare();
            entry.getValue().merge(entry.getKey(), global);
            entry.getValue().refresh();
        }
    }

    public HttpApi getGlobalConfig() {
        return global;
    }

    public Map<String, HttpApi> getApiMap() {
        return local;
    }
}
