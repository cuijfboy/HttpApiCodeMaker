package name.ilab.http.code.generated;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.HttpMethod;
import name.ilab.http.code.maker.Utils;
import name.ilab.http.code.template.BaseResponse;

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
        public transient String session;
        public String userId;
        public String nickName;
        public int errorCode;
    }

    // ------------------------------------------

    public LoginRequest3() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
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

    private void generateResponseData(Map<String, String> header, String body) {
        try {
            response = new GsonBuilder().serializeNulls().create().fromJson(body, Response.class);
        } catch (Exception e) {
            response = null;
            e.printStackTrace();
        }
        response = response == null ? new Response() : response;
        response.session = header.get("session");
    }

// ############################################################

    public Request request;
    public Response response;
    public ResponseListener responseListener;
    public IApiHook hook;

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        BaseResponse baseResponse = new BaseResponse(statusCode, method, url, header, body);
        hook.onResponse(API_NAME, baseResponse);
        generateResponseData(header, body);
        response.set(baseResponse);
        hook.onResponseData(API_NAME, response, response.getClass());
        if (responseListener != null) {
            responseListener.onResponse(statusCode, response, header, body);
        } else {
            if (!onResponse(statusCode, response)) {
                onResponse(statusCode, response, header, body);
            }
        }
    }

    public boolean onResponse(int statusCode, Response response) {
        return false;
    }

    public boolean onResponse(int statusCode, Response response,
                              Map<String, String> header, String body) {
        return false;
    }

    public interface ResponseListener {
        boolean onResponse(int statusCode, Response response,
                           Map<String, String> header, String body);
    }
}