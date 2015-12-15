package name.ilab.http.code.generated;

import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.HttpMethod;
import name.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest3 extends BaseRequest {
    public static final String API_NAME =
            "name.ilab.http.code.generated.LoginRequest3";
    public static final String HOOK_NAME =
            "name.ilab.http.code.generator.sample.SampleHook";

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
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
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
        getRequestData().generateMethod();
        getRequestData().generateUrl();
        getRequestData().generateHeader();
        getRequestData().generateBody();
        hook.onRequest(API_NAME, method, url, header, body, getRequestData(), getRequestData().getClass());
        httpClient.request(this);
        return this;
    }

    public LoginRequest3 go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseData(Map<String, String> header, String body) {
        try {
            response = Utils.getSerializeNullGson().fromJson(body, Response.class);
        } catch (Exception e) {
            response = null;
            e.printStackTrace();
        }
        response = response == null ? new Response() : response;
        response.session = header.get("session");
        hook.onResponseData(API_NAME, response, response.getClass(), header, body);
    }

// Fixed BEGIN ##################################

    public Request request;
    private Response response;
    private ResponseListener listener;
    private final IApiHook hook;

    public Request getRequestData() {
        return request;
    }

    public void setResponseListener(ResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        hook.onResponse(API_NAME, statusCode, header, body);
        generateResponseData(header, body);
        if (listener != null) {
            listener.onResponse(statusCode, response, header, body);
        } else {
            if (!onResponse(statusCode, response)) {
                onResponse(statusCode, response, header, body);
            }
        }
    }

    public boolean onResponse(int statusCode, Response data) {
        return false;
    }

    public boolean onResponse(int statusCode, Response data, Map<String, String> header, String body) {
        return false;
    }

    public interface ResponseListener {
        boolean onResponse(int statusCode, Response data, Map<String, String> header, String body);
    }
}

// Fixed END ####################################
