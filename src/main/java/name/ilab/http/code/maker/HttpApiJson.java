package name.ilab.http.code.maker;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class HttpApiJson {
    private HttpApi global;
    private Map<String, HttpApi> local;

    public void refresh() {
        global.refresh();
        for (Map.Entry<String, HttpApi> entry : local.entrySet()) {
            entry.getValue().refresh();
            entry.getValue().combine(entry.getKey(), global);
        }
    }

    public HttpApi getGlobalConfig() {
        return global;
    }

    public Map<String, HttpApi> getApiMap() {
        return local;
    }
}
