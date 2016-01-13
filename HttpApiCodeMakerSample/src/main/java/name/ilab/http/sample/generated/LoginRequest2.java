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


public class LoginRequest2 extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.sample.generated.LoginRequest2";
    public static final String HOOK_NAME =
            "name.ilab.http.sample.SampleHook";

    public class Request {
        public String userName;
        public String userPassword;

        private void generateMethod() {
            method = HttpMethod.POST;
        }

        private void generateUrl() {
            url = "http://www.example.com/login";
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
        public File myFile;
    }

    // --------------------------------------------------------------------------------------------

    public LoginRequest2() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
        this.responseType = ResponseType.FILE;
    }

    public LoginRequest2 go(IHttpClient httpClient) {
        hook.onRequestData(API_NAME, request, request.getClass());
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        request.generateBody();
        hook.onRequest(API_NAME, this, request, request.getClass());
        httpClient.request(this);
        return this;
    }

    public LoginRequest2 go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      File file) {
        response = new Response(statusCode, method, url, header);
        response.myFile = file;
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      byte[] data) {
        response = new Response(statusCode, method, url, header);
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