package name.ilab.http.code.maker.sample;

import name.ilab.http.IApiHook;
import name.ilab.http.HttpMethod;
import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.code.template.BaseResponse;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class SampleHook implements IApiHook {
    @Override
    public void onRequestData(String apiName, Object data, Class dataClass) {
        System.out.println();
        System.out.println("SampleHook onRequestData data = " + data);
        System.out.println("SampleHook onRequestData dataClass = " + dataClass);
    }

    @Override
    public void onRequest(String apiName, BaseRequest request, Object data, Class dataClass) {
        System.out.println();
        System.out.println("SampleHook onRequest name = " + apiName);
        System.out.println("SampleHook onRequest method = " + request.getMethod());
        System.out.println("SampleHook onRequest url = " + request.getUrl());
        System.out.println("SampleHook onRequest header = " + request.getHeader());
        System.out.println("SampleHook onRequest body = " + request.getBody());
        System.out.println("SampleHook onRequest data = " + data);
        System.out.println("SampleHook onRequest dataClass = " + dataClass);

        request.getHeader().put("a", "a");
        request.setBody("{}");
        request.setUrl("http://www.www.www.www");
        request.setMethod(HttpMethod.DELETE);

        System.out.println("After modified by hook :");
        System.out.println("SampleHook onRequest name = " + apiName);
        System.out.println("SampleHook onRequest method = " + request.getMethod());
        System.out.println("SampleHook onRequest url = " + request.getUrl());
        System.out.println("SampleHook onRequest header = " + request.getHeader());
        System.out.println("SampleHook onRequest body = " + request.getBody());
        System.out.println("SampleHook onRequest data = " + data);
        System.out.println("SampleHook onRequest dataClass = " + dataClass);
    }

    @Override
    public void onResponse(String apiName, BaseResponse response) {
        System.out.println();
        System.out.println("SampleHook onResponse name = " + apiName);
        System.out.println("SampleHook onResponse method = " + response.getMethod());
        System.out.println("SampleHook onResponse url = " + response.getUrl());
        System.out.println("SampleHook onResponse statusCode = " + response.getStatusCode());
        System.out.println("SampleHook onResponse header = " + response.getHeader());
        System.out.println("SampleHook onResponse body = " + response.getBody());
    }

    @Override
    public void onResponseData(String apiName, BaseResponse data, Class dataClass) {
        System.out.println();
        System.out.println("SampleHook onResponseData data = " + data);
        System.out.println("SampleHook onResponseData dataClass = " + dataClass);
    }

}
