package name.ilab.http;

import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.code.template.BaseResponse;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IApiHook {

    void onRequestData(final String apiName, final Object data, final Class dataClass);

    void onRequest(final String apiName, final BaseRequest request, final Object data, final Class dataClass);

    void onResponse(final String apiName, final ResponseType type, final BaseResponse response);

    void onResponseData(final String apiName, final ResponseType type, final BaseResponse data, final Class dataClass);

}
