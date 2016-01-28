package name.ilab.http;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class SimpleHook implements IApiHook {

    @Override
    public void onRequestData(String apiName, Object data, Class dataClass) {

    }

    @Override
    public void onRequest(String apiName, HttpRequest request, Object data, Class dataClass) {

    }

    @Override
    public void onResponse(String apiName, ResponseType type, HttpResponse response) {

    }

    @Override
    public void onResponseData(String apiName, ResponseType type, HttpResponse data, Class dataClass) {

    }
}
