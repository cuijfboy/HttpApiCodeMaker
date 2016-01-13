package name.ilab.http.code.generated;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.ResponseType;
import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.HttpMethod;
import name.ilab.http.code.maker.Utils;
import name.ilab.http.code.template.BaseResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class LoginRequest3 extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.code.generated.LoginRequest3";
    public static final String HOOK_NAME =
            "name.ilab.http.code.maker.sample.SampleHook";

    public class Request {
        public String userName;
        public String userPassword;

        private void generateMethod() {
            method = HttpMethod.GET;
        }

        private void generateUrl() {
            url = "http://www.example.com/login";
            if (HttpMethod.GET == method) {
                StringBuffer sb = new StringBuffer(url);
                sb.append("?");
                sb.append("userName").append("=").append(userName).append("&");
                sb.append("userPassword").append("=").append(userPassword).append("&");
                sb.deleteCharAt(sb.length() - 1);
                if (sb.length() != url.length()) {
                    url = sb.toString();
                }
            }
        }

        private void generateHeader() {
            header.clear();
        }

        private void generateBody() {
            body = new Gson().toJson(this);
        }
    }

    public class Response extends BaseResponse {

        public Response(int statusCode, HttpMethod method, String url, Map<String, String> header) {
            super(statusCode, method, url, header);
        }

        public Response(BaseResponse baseResponse) {
            set(baseResponse);
        }

        public transient String session;
        public byte[] myData;
    }

    // --------------------------------------------------------------------------------------------

    public LoginRequest3() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
        this.responseType = ResponseType.BINARY;
    }

    public LoginRequest3 go(IHttpClient httpClient) {
        hook.onRequestData(API_NAME, request, request.getClass());
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        request.generateBody();
        hook.onRequest(API_NAME, this, request, request.getClass());
        httpClient.request(this);
        return this;
    }

    public LoginRequest3 go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      File file) {
        response = new Response(statusCode, method, url, header);
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      byte[] data) {
        response = new Response(statusCode, method, url, header);
        response.myData = data;
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        if (header != null) {
            response.session = header.get("session");
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
        BaseResponse baseResponse = new BaseResponse(statusCode, method, url, header);
        baseResponse.setBody(body);
        hook.onResponse(API_NAME, responseType, baseResponse);
        generateResponseData(baseResponse);
        hook.onResponseData(API_NAME, responseType, response, response.getClass());
        onResponse(statusCode, response);
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, File file) {
        generateResponseData(statusCode, method, url, header, file);
        hook.onResponse(API_NAME, responseType, response);
        hook.onResponseData(API_NAME, responseType, response, response.getClass());
        onResponse(statusCode, response);
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, byte[] data) {
        generateResponseData(statusCode, method, url, header, data);
        hook.onResponse(API_NAME, responseType, response);
        hook.onResponseData(API_NAME, responseType, response, response.getClass());
        onResponse(statusCode, response);
    }

    public boolean onResponse(int statusCode, Response response) {
        return false;
    }

}