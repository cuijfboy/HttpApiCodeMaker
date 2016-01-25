package name.ilab.http.preset;

import name.ilab.http.BaseResponse;
import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;
import name.ilab.http.Utils;
import java.util.Map;

import name.ilab.http.Utils;


public class BaseResponseModel extends BaseResponse {


    public BaseResponseModel(BaseResponse response) {
        super(response);
    }

    public BaseResponseModel(ResponseType responseType,
                             int statusCode, HttpMethod method, String url, Map<String, String> header) {
        super(responseType, statusCode, method, url, header);
    }

    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static BaseResponseModel valueOf(String valueString) {
        return Utils.fromJson(valueString, BaseResponseModel.class);
    }
}

