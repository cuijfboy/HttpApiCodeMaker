package name.ilab.http;

import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.code.template.BaseResponse;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class EmptyHook implements IApiHook {

    @Override
    public void onRequestData(String apiName, Object data, Class dataClass) {

    }

    @Override
    public void onRequest(String apiName, BaseRequest request, Object data, Class dataClass) {

    }

    @Override
    public void onResponse(String apiName, ResponseType type, BaseResponse response) {

    }

    @Override
    public void onResponseData(String apiName, ResponseType type, BaseResponse data, Class dataClass) {

    }
}
