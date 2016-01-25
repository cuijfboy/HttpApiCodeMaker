package name.ilab.http.preset;


import name.ilab.http.Utils;


public class BaseRequestModel {


    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static BaseRequestModel valueOf(String valueString) {
        return Utils.fromJson(valueString, BaseRequestModel.class);
    }
}

