package name.ilab.http.maker.model;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class Meta {
    public String name;
    public String version;

    // -------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
