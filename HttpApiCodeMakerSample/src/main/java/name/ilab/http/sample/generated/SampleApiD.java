package name.ilab.http.sample.generated;

import name.ilab.http.BaseRequest;
import name.ilab.http.BaseResponse;
import name.ilab.http.HttpApiHelper;
import name.ilab.http.HttpMethod;
import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.ResponseType;
import name.ilab.http.Utils;

import java.io.File;
import java.util.Map;


public class SampleApiD extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.sample.generated.SampleApiD";
    public static final String HTTP_CLIENT_NAME =
            "name.ilab.http.sample.SampleHttpClient";

    public class Request {
        public transient int commonRequestHeaderArg2;
        public transient String commonRequestHeaderArg1;
        public String commonRequestBodyArg1;
        public int commonRequestBodyArg2;

        private void generateMethod() {
            if (method == null) {
                method = HttpMethod.GET;
            }
        }

        private void generateUrl() {
            if (url == null) {
                url = "http://www.example.com/sampleApiD";
                if (HttpMethod.GET == method) {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
                    stringBuffer.append("commonRequestBodyArg1").append("=")
                            .append(commonRequestBodyArg1).append("&");
                    stringBuffer.append("commonRequestBodyArg2").append("=")
                            .append(commonRequestBodyArg2).append("&");
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    if (stringBuffer.length() != url.length()) {
                        url = stringBuffer.toString();
                    }
                }
            }
        }

        private void generateHeader() {
            if (header.isEmpty()) {
                header.put("commonRequestHeaderArg2", String.valueOf(commonRequestHeaderArg2));
                header.put("commonRequestHeaderArg1", String.valueOf(commonRequestHeaderArg1));
            }
        }

        private void generateBody() {
            if (body == null) {
                body = toString();
            }
        }

        @Override
        public String toString() {
            return Utils.toJson(this);
        }

    }

    public static class Response extends BaseResponse {

        public transient int commonResponseHeaderArg2;
        public transient String commonResponseHeaderArg1;
        public byte[] sampleApiCResponseBodyBinaryData;

        public Response(BaseResponse response) {
            super(response);
        }

        public Response(ResponseType responseType,
                        int statusCode, HttpMethod method, String url, Map<String, String> header) {
            super(responseType, statusCode, method, url, header);
        }

        public static Response valueOf(String valueString) {
            return Utils.fromJson(valueString, Response.class);
        }

    }

    // --------------------------------------------------------------------------------------------

    public SampleApiD() {
        this.request = new Request();
        this.responseType = ResponseType.BINARY;
        hookNameList.add("name.ilab.http.sample.SampleHook");
    }

    public SampleApiD go(IHttpClient httpClient) {
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        for (IApiHook hook : obtainHookList()) {
            hook.onRequestData(API_NAME, request, Request.class);
        }
        request.generateBody();
        for (IApiHook hook : obtainHookList()) {
            hook.onRequest(API_NAME, this, request, Request.class);
        }
        httpClient.request(this);
        return this;
    }

    public SampleApiD go() {
        return go(HttpApiHelper.getInstance().getHttpClient(HTTP_CLIENT_NAME));
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url,
                                      Map<String, String> header, File file) {
        response = new Response(responseType, statusCode, method, url, header);
        response.setFileSavePath(fileSavePath);
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url,
                                      Map<String, String> header, byte[] data) {
        response = new Response(responseType, statusCode, method, url, header);
        response.sampleApiCResponseBodyBinaryData = data;
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        if (header != null) {
            String valueString = null;
            valueString = header.get("commonResponseHeaderArg2");
            response.commonResponseHeaderArg2 = valueString == null ? 0 : Integer.parseInt(valueString);
            valueString = header.get("commonResponseHeaderArg1");
            response.commonResponseHeaderArg1 = valueString;
        }
    }

// ################################################################################################

    public Request request;
    public Response response;

    private void generateResponseData(BaseResponse baseResponse) {
        try {
            response = Response.valueOf(baseResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(baseResponse);
        }
        fillResponseHeader(baseResponse.getHeader());
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        BaseResponse baseResponse = new BaseResponse(responseType, statusCode, method, url, header);
        baseResponse.setBody(body);
        for (IApiHook hook : obtainHookList()) {
            hook.onResponse(API_NAME, responseType, baseResponse);
        }
        generateResponseData(baseResponse);
        for (IApiHook hook : obtainHookList()) {
            hook.onResponseData(API_NAME, responseType, response, Response.class);
        }
        onResponse(statusCode, response);
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, File file) {
        generateResponseData(statusCode, method, url, header, file);
        onResponse();
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, byte[] data) {
        generateResponseData(statusCode, method, url, header, data);
        onResponse();
    }

    private void onResponse() {
        for (IApiHook hook : obtainHookList()) {
            hook.onResponse(API_NAME, responseType, response);
            hook.onResponseData(API_NAME, responseType, response, Response.class);
        }
        clearHookList();
        onResponse(response.getStatusCode(), response);
    }

    public boolean onResponse(int statusCode, Response response) {
        return false;
    }

}