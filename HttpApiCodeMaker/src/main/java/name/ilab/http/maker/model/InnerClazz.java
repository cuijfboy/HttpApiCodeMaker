package name.ilab.http.maker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cuijfboy on 16/1/26.
 */
public class InnerClazz extends AbstractClazz {

    @SerializedName("static")
    public Boolean isStatic;

    public Clazz hostClazz;

    public void merge(String name, InnerClazz that) {
        super.merge(name, that);
        this.isStatic = this.isStatic == null ? that.isStatic : this.isStatic;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    public void refresh() {
        super.refresh();
        this.isStatic = this.isStatic == null ? false : this.isStatic;
    }

    @Override
    protected final void addImportItem(String name, String fullName) {
        hostClazz.addImportItem(name, fullName);
    }

    // ------------------------------------------------------------------------

    public Boolean getStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public Clazz getHostClazz() {
        return hostClazz;
    }

    public void setHostClazz(Clazz hostClazz) {
        this.hostClazz = hostClazz;
    }

    @Override
    public String toString() {
        return "InnerClazz{" +
                "isStatic=" + isStatic +
                ", super=" + super.toString() +
                '}';
    }
}
