package name.ilab.http.maker.model;

import name.ilab.http.maker.Maker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class ModelSet {
    public Meta meta;
    public Model global;
    public Map<String, Model> local = new HashMap<>();

    public void refresh() {
        Maker.logInfo("ModelSet : meta : " + meta);
        Maker.logInfo();
        Maker.logInfo("Model : global : \n " + global);
        global.prepare();
        for (Map.Entry<String, Model> entry : local.entrySet()) {
            Maker.logInfo();
            Maker.logInfo("Model : " + entry.getKey() + " [Original] : \n " + entry.getValue());
            entry.getValue().prepare();
            entry.getValue().merge(entry.getKey(), global);
            entry.getValue().refresh();
            Maker.logInfo("Model : " + entry.getKey() + " [Refreshed] : \n " + entry.getValue());
        }
    }

    // -------------------------------------------------------

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Model getGlobal() {
        return global;
    }

    public void setGlobal(Model global) {
        this.global = global;
    }

    public Map<String, Model> getLocal() {
        return local;
    }

    public void setLocal(Map<String, Model> local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "ModelSet{" +
                "meta=" + meta +
                ", global=" + global +
                ", local=" + local +
                '}';
    }
}
