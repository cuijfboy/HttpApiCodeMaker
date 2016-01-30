package name.ilab.http.maker.model;

import name.ilab.http.HttpApiHelper;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class Model extends Clazz {

    private transient String configuration;

    public void merge(String name, Model that) {
        super.merge(name, that);
    }

    @Override
    public void refresh() {
        super.refresh();
        generateConfiguration();
    }

    private void generateConfiguration() {
        configuration = HttpApiHelper.generateConfiguration(name, this);
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "Model{" +
                "super=" + super.toString() +
                "}";
    }
}
