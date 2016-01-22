package name.ilab.http.sample.generated;

import name.ilab.http.Utils;


public class commonModelA {
    public String commonModelAArg1;
    public int commonModelAArg2;

    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static commonModelA valueOf(String valueString) {
        return Utils.fromJson(valueString, commonModelA.class);
    }
}

