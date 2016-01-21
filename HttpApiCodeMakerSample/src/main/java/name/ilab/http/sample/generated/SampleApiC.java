package name.ilab.http.sample.generated;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.ResponseType;
import name.ilab.http.BaseRequest;
import name.ilab.http.HttpMethod;
import name.ilab.http.Utils;
import name.ilab.http.BaseResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SampleApiC extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.sample.generated.SampleApiC";
    public static final String HOOK_NAME =
            "name.ilab.http.sample.SampleHook";

    public class Request {
        public transient String commonRequestHeaderArg2;
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
                url = "http://www.example.com/sampleApiC";
                if (HttpMethod.GET == method) {
                    StringBuffer sb = new StringBuffer(url);
                    sb.append("?");
                    sb.append("commonRequestBodyArg1").append("=").append(commonRequestBodyArg1).append("&");
                    sb.append("commonRequestBodyArg2").append("=").append(commonRequestBodyArg2).append("&");
                    sb.deleteCharAt(sb.length() - 1);
                    if (sb.length() != url.length()) {
                        url = sb.toString();
                    }
                }
            }
        }

        private void generateHeader() {
            if (header.isEmpty()) {
                header.put("commonRequestHeaderArg2", commonRequestHeaderArg2);
                header.put("commonRequestHeaderArg1", commonRequestHeaderArg1);
            }
        }

        private void generateBody() {
            if (body == null) {
                body = new Gson().toJson(this);
            }
        }
    }

    public class Response extends BaseResponse {

        public Response(BaseResponse response) {
            super(response);
        }

        public Response(ResponseType responseType,
                        int statusCode, HttpMethod method, String url, Map<String, String> header) {
            super(responseType, statusCode, method, url, header);
        }

        public transient String commonResponseHeaderArg2;
        public transient String commonResponseHeaderArg1;
        public File sampleApiCResponseBodyFile;
    }

    // --------------------------------------------------------------------------------------------

    public SampleApiC() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
        this.responseType = ResponseType.FILE;
    }

    public SampleApiC go(IHttpClient httpClient) {
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        if (hook != null) {
            hook.onRequestData(API_NAME, request, Request.class);
        }
        request.generateBody();
        if (hook != null) {
            hook.onRequest(API_NAME, this, request, Request.class);
        }
        httpClient.request(this);
        return this;
    }

    public SampleApiC go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      File file) {
        response = new Response(responseType, statusCode, method, url, header);
        response.setFileSavePath(fileSavePath);
        response.sampleApiCResponseBodyFile = file;
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      byte[] data) {
        response = new Response(responseType, statusCode, method, url, header);
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        if (header != null) {
            response.commonResponseHeaderArg2 = header.get("commonResponseHeaderArg2");
            response.commonResponseHeaderArg1 = header.get("commonResponseHeaderArg1");
        }
    }

// ################################################################################################

    public Request request;
    public Response response;
    public IApiHook hook;

    private void generateResponseData(BaseResponse baseResponse) {
        try {
            response = new GsonBuilder().serializeNulls().create()
                    .fromJson(baseResponse.getBody(), Response.class);
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
        if (hook != null) {
            hook.onResponse(API_NAME, responseType, baseResponse);
        }
        generateResponseData(baseResponse);
        if (hook != null) {
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
        if (hook != null) {
            hook.onResponse(API_NAME, responseType, response);
            hook.onResponseData(API_NAME, responseType, response, Response.class);
        }
        onResponse(response.getStatusCode(), response);
    }

    public boolean onResponse(int statusCode, Response response) {
        return false;
    }

}