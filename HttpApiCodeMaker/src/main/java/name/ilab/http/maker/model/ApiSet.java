package name.ilab.http.maker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class ApiSet {
    public Meta meta;
    public Api global;
    public Map<String, Api> local = new HashMap<>();

    public void refresh() {
        System.out.println("[HttpApiCodeMaker] ApiSet : meta : " + meta);
        System.out.println();
        System.out.println("[HttpApiCodeMaker] API : global : \n " + global);
        global.prepare();
        for (Map.Entry<String, Api> entry : local.entrySet()) {
            System.out.println();
            System.out.println("[HttpApiCodeMaker] API : " + entry.getKey() + " [Original] : \n " + entry.getValue());
            entry.getValue().prepare();
            entry.getValue().merge(entry.getKey(), global);
            entry.getValue().refresh();
            System.out.println("[HttpApiCodeMaker] API : " + entry.getKey() + " [Refreshed] : \n " + entry.getValue());
        }
    }

    // -------------------------------------------------------

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Api getGlobal() {
        return global;
    }

    public void setGlobal(Api global) {
        this.global = global;
    }

    public Map<String, Api> getLocal() {
        return local;
    }

    public void setLocal(Map<String, Api> local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "ApiSet{" +
                "meta=" + meta +
                ", global=" + global +
                ", local=" + local +
                '}';
    }
}
