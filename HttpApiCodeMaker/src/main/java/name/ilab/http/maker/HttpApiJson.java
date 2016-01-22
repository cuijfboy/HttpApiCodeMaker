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
        System.out.println();
        System.out.println("[HttpApiCodeMaker] API : global : \n " + global);
        for (Map.Entry<String, HttpApi> entry : local.entrySet()) {
            entry.getValue().prepare();
            System.out.println();
            System.out.println("[HttpApiCodeMaker] API : " + entry.getKey() + " [Original] : \n " + entry.getValue());
            entry.getValue().merge(entry.getKey(), global);
            entry.getValue().refresh();
            System.out.println("[HttpApiCodeMaker] API : " + entry.getKey() + " [Refreshed] : \n " + entry.getValue());
        }
    }

    public HttpApi getGlobalConfig() {
        return global;
    }

    public Map<String, HttpApi> getApiMap() {
        return local;
    }
}
