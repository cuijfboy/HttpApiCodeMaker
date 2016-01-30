package name.ilab.http.maker.model;

import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class Clazz extends AbstractClazz {
    @SerializedName("source")
    public String sourceFolder;

    @SerializedName("package")
    public String packageName;

    @SerializedName("import")
    public Set<String> importSet = new HashSet<>();

    @SerializedName("inner")
    public Map<String, InnerClazz> innerClazzMap = new HashMap<>();

    @Override
    public void prepare() {
        super.prepare();
        for (InnerClazz innerClazz : innerClazzMap.values()) {
            innerClazz.setHostClazz(this);
        }
    }

    public void merge(String name, Clazz that) {
        super.merge(name, that);
        this.sourceFolder = this.sourceFolder == null ? that.sourceFolder : this.sourceFolder;
        this.packageName = this.packageName == null ? that.packageName : this.packageName;
        mergeSet(this.importSet, that.importSet);
        mergeMap(this.innerClazzMap, that.innerClazzMap);
    }

    @Override
    public void refresh() {
        super.refresh();
        Set<String> importSetBackup = new HashSet<>(importSet);
        refreshImportSet(importSetBackup);
    }

    @Override
    protected final void addImportItem(String name, String fullName) {
        if (name == null || fullName == null) {
            return;
        }
        boolean isImported = false;
        Iterator<String> iterator = importSet.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals(name)) {
                iterator.remove();
                break;
            } else if (item.endsWith("." + name)) {
                isImported = true;
                break;
            }
        }
        if (!isImported) {
            importSet.add(fullName);
        }
    }

    // ------------------------------------------------------------------------

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Set<String> getImportSet() {
        return importSet;
    }

    public void setImportSet(Set<String> importSet) {
        this.importSet = importSet;
    }

    public Map<String, InnerClazz> getInnerClazzMap() {
        return innerClazzMap;
    }

    public void setInnerClazzMap(Map<String, InnerClazz> innerClazzMap) {
        this.innerClazzMap = innerClazzMap;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "sourceFolder='" + sourceFolder + '\'' +
                ", packageName='" + packageName + '\'' +
                ", importSet=" + importSet +
                ", innerClazzMap=" + innerClazzMap +
                ", super=" + super.toString() +
                '}';
    }
}
