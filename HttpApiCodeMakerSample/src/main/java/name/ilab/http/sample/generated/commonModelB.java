package name.ilab.http.sample.generated;

import name.ilab.http.Utils;


public class commonModelB {
    public String commonModelBArg1;
    public int commonModelBArg2;

    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static commonModelB valueOf(String valueString) {
        return Utils.fromJson(valueString, commonModelB.class);
    }
}

