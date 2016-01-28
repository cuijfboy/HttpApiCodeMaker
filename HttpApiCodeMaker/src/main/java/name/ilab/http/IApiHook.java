package name.ilab.http;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IApiHook {

    void onRequestData(final String apiName, final Object data, final Class dataClass);

    void onRequest(final String apiName, final HttpRequest request, final Object data, final Class dataClass);

    void onResponse(final String apiName, final ResponseType type, final HttpResponse response);

    void onResponseData(final String apiName, final ResponseType type, final HttpResponse data, final Class dataClass);

}
