package name.ilab.http;

import java.util.HashMap;

/**
 * Created by cuijfboy on 15/12/1.
 */
public class DefaultHttpClient implements IHttpClient {

    @Override
    public void request(IHttpRequest request) {
        System.out.println();
        System.out.println("DefaultHttpClient request.getMethod() = " + request.getMethod());
        System.out.println("DefaultHttpClient request.getUrl() = " + request.getUrl());
        System.out.println("DefaultHttpClient request.getHeader() = " + request.getHeader());
        System.out.println("DefaultHttpClient request.getBody() = " + request.getBody());

        request.getMethod();
        request.getUrl();
        request.getHeader();
        request.getBody();

        request.onResponse(200,
                new HashMap<String, String>() {
                    {
                        put("session", "9876543210");
                    }
                }, "{\"userId\":\"123\"," +
                        "\"nickName\":\"administrator\"," +
                        "\"errorCode\":\"0\"," +
                        "\"errorInfo\":\"Everything goes well.\"}");
    }
}