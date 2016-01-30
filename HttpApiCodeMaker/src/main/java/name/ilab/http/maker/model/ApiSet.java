package name.ilab.http.maker.model;

import name.ilab.http.maker.Maker;

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
        Maker.logInfo("ApiSet : meta : " + meta);
        Maker.logInfo();
        Maker.logInfo("API : global : \n " + global);
        global.prepare();
        for (Map.Entry<String, Api> entry : local.entrySet()) {
            Maker.logInfo();
            Maker.logInfo("API : " + entry.getKey() + " [Original] : \n " + entry.getValue());
            entry.getValue().prepare();
            entry.getValue().merge(entry.getKey(), global);
            entry.getValue().refresh();
            Maker.logInfo("API : " + entry.getKey() + " [Refreshed] : \n " + entry.getValue());
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
