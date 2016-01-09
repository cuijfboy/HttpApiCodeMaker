package name.ilab.http;

import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.code.template.BaseResponse;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IApiHook {

    void onRequestData(final String apiName, final Object data, final Class dataClass);

    void onRequest(final String apiName, final BaseRequest request, final Object data, final Class dataClass);

    void onResponse(final String apiName, final BaseResponse response);

    void onResponseData(final String apiName, final BaseResponse data, final Class dataClass);

//    HttpMethod method, String url, Map<String, String> header, String body, Object data, Class dataClass);

//    void onResponse(final String apiName, int statusCode, Map<String, String> header, String body);

//    void onResponseDataObject(final String apiName, Object data, Class dataClass, Map<String, String> header, String body);

}
