package name.ilab.http.maker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cuijfboy on 16/1/26.
 */
public abstract class AbstractClazz {

    private static Map<String, String> quickClassMap = new HashMap<String, String>() {
        {
            put("List", "java.util.List");
            put("Map", "java.util.Map");
            put("Set", "java.util.Set");
        }
    };

    public String name;

    @SerializedName("extends")
    public String parent;

    @SerializedName("implements")
    public Set<String> implementSet = new HashSet<>();

    @SerializedName(value = "filed", alternate = {"body"})
    public Map<String, String> ordinaryFieldMap = new HashMap<>();

    @SerializedName(value = "transient", alternate = {"header"})
    public Map<String, String> transientFieldMap = new HashMap<>();

    public void merge(String name, AbstractClazz that) {
        this.name = name == null ? this.name : name;
        this.parent = this.parent == null ? that.parent : this.parent;
        mergeSet(this.implementSet, that.implementSet);
        mergeMap(this.ordinaryFieldMap, that.ordinaryFieldMap);
        mergeMap(this.transientFieldMap, that.transientFieldMap);
    }

    public void prepare() {

    }

    public void refresh() {
        refreshImportSet(parent);
        refreshImportSet(implementSet);
    }

    protected void refreshImportSet(Set<String> itemSet) {
        for (String item : itemSet) {
            addImportItem(item, getQuickClass(item));
        }
    }

    protected void refreshImportSet(String item) {
        addImportItem(item, getQuickClass(item));
    }

    protected abstract void addImportItem(String name, String fullName);

    // ------------------------------------------------------------------------

    public static <T> void mergeSet(Set<T> thisSet, Set<T> thatSet) {
        Set<T> diffSet = new HashSet<>(thatSet);
        diffSet.removeAll(thisSet);
        thisSet.addAll(diffSet);
    }

    public static <T> void mergeList(List<T> thisList, List<T> thatList) {
        List<T> diffList = new ArrayList<>(thatList);
        diffList.removeAll(thisList);
        thisList.addAll(diffList);
    }

    public static <T> void mergeMap(Map<String, T> thisMap, Map<String, T> thatMap) {
        if (thisMap == null) {
            return;
        }
        if (thatMap == null || thatMap.isEmpty()) {
            removeUseless(thisMap);
        } else {
            addExtra(thisMap, thatMap);
            removeUseless(thisMap);
        }
    }

    public static <T> void addExtra(Map<String, T> thisMap,
                                    Map<String, T> thatMap) {
        Set<String> diffKeySet = new HashSet<>();
        diffKeySet.addAll(thatMap.keySet());
        diffKeySet.removeAll(thisMap.keySet());
        for (String key : diffKeySet) {
            thisMap.put(key, thatMap.get(key));
        }
    }

    public static <T> void removeUseless(Map<String, T> map) {
        Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, T> entry = iterator.next();
            if (entry.getValue() == null) {
                iterator.remove();
            }
        }
    }

    // ------------------------------------------------------------------------

    public static String getQuickClass(String name) {
        return quickClassMap.get(name);
    }

    public static Map<String, String> getQuickClassMap() {
        return quickClassMap;
    }

    public static void setQuickClassMap(Map<String, String> quickClassMap) {
        AbstractClazz.quickClassMap = quickClassMap;
    }

    public static void registerQuickClass(String name, String fullName) {
        quickClassMap.put(name, fullName);
    }

    public static void unregisterQuickClass(String name) {
        quickClassMap.remove(name);
    }

    // ------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Set<String> getImplementSet() {
        return implementSet;
    }

    public void setImplementSet(Set<String> implementSet) {
        this.implementSet = implementSet;
    }

    public Map<String, String> getOrdinaryFieldMap() {
        return ordinaryFieldMap;
    }

    public void setOrdinaryFieldMap(Map<String, String> ordinaryFieldMap) {
        this.ordinaryFieldMap = ordinaryFieldMap;
    }

    public Map<String, String> getTransientFieldMap() {
        return transientFieldMap;
    }

    public void setTransientFieldMap(Map<String, String> transientFieldMap) {
        this.transientFieldMap = transientFieldMap;
    }

    @Override
    public String toString() {
        return "AbstractClazz{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", implementSet=" + implementSet +
                ", ordinaryFieldMap=" + ordinaryFieldMap +
                ", transientFieldMap=" + transientFieldMap +
                '}';
    }


}
