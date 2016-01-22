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


public class SampleApiA extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.sample.generated.SampleApiA";
    public static final String HTTP_CLIENT_NAME =
            "name.ilab.http.sample.SampleHttpClient";

    public class Request {
        public transient String sampleApiARequestHeaderArg1;
        public transient int sampleApiARequestHeaderArg2;
        public transient SampleApiAModelA sampleApiARequestHeaderArg3;
        public transient int commonRequestHeaderArg2;
        public transient String commonRequestHeaderArg1;
        public String sampleApiARequestBodyArg1;
        public int sampleApiARequestBodyArg2;
        public SampleApiAModelA sampleApiARequestBodyArg3;
        public String commonRequestBodyArg1;
        public int commonRequestBodyArg2;

        private void generateMethod() {
            if (method == null) {
                method = HttpMethod.GET;
            }
        }

        private void generateUrl() {
            if (url == null) {
                url = "http://www.example.com/sampleApiA";
                if (HttpMethod.GET == method) {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
                    stringBuffer.append("sampleApiARequestBodyArg1").append("=")
                            .append(sampleApiARequestBodyArg1).append("&");
                    stringBuffer.append("sampleApiARequestBodyArg2").append("=")
                            .append(sampleApiARequestBodyArg2).append("&");
                    stringBuffer.append("sampleApiARequestBodyArg3").append("=")
                            .append(sampleApiARequestBodyArg3).append("&");
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
                header.put("sampleApiARequestHeaderArg1", String.valueOf(sampleApiARequestHeaderArg1));
                header.put("sampleApiARequestHeaderArg2", String.valueOf(sampleApiARequestHeaderArg2));
                header.put("sampleApiARequestHeaderArg3", String.valueOf(sampleApiARequestHeaderArg3));
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

        public transient String sampleApiAResponseHeaderArg1;
        public transient int sampleApiAResponseHeaderArg2;
        public transient SampleApiAModelB sampleApiAResponseHeaderArg3;
        public transient int commonResponseHeaderArg2;
        public transient String commonResponseHeaderArg1;
        public String sampleApiAResponseBodyArg1;
        public int sampleApiAResponseBodyArg2;
        public SampleApiAModelB sampleApiAResponseBodyArg3;
        public String commonResponseBodyArg1;
        public int commonResponseBodyArg2;

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

    public static class SampleApiAModelA {
        public String sampleApiAModelAArg1;
        public int sampleApiAModelAArg2;

        @Override
        public String toString() {
            return Utils.toJson(this);
        }

        public static SampleApiAModelA valueOf(String valueString) {
            return Utils.fromJson(valueString, SampleApiAModelA.class);
        }

    }

    public static class SampleApiAModelB {
        public String sampleApiAModelBArg1;
        public int sampleApiAModelBArg2;

        @Override
        public String toString() {
            return Utils.toJson(this);
        }

        public static SampleApiAModelB valueOf(String valueString) {
            return Utils.fromJson(valueString, SampleApiAModelB.class);
        }

    }

    // --------------------------------------------------------------------------------------------

    public SampleApiA() {
        this.request = new Request();
        this.responseType = ResponseType.TEXT;
        hookNameList.add("name.ilab.http.sample.SampleHook");
    }

    public SampleApiA go(IHttpClient httpClient) {
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

    public SampleApiA go() {
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
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        if (header != null) {
            String valueString = null;
            valueString = header.get("sampleApiAResponseHeaderArg1");
            response.sampleApiAResponseHeaderArg1 = valueString;
            valueString = header.get("sampleApiAResponseHeaderArg2");
            response.sampleApiAResponseHeaderArg2 = valueString == null ? 0 : Integer.parseInt(valueString);
            valueString = header.get("sampleApiAResponseHeaderArg3");
            response.sampleApiAResponseHeaderArg3 = valueString == null ? null : SampleApiAModelB.valueOf(valueString);
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