package name.ilab.http.sample;

import name.ilab.http.IHttpClient;
import name.ilab.http.IHttpRequest;
import name.ilab.http.ResponseType;

import java.io.File;
import java.util.HashMap;

/**
 * Created by cuijfboy on 16/1/9.
 */
public class SampleHttpClient implements IHttpClient {
    @Override
    public void request(IHttpRequest request) {
        System.out.println();
        System.out.println("SampleHttpClient request.getMethod() = " + request.getMethod());
        System.out.println("SampleHttpClient request.getUrl() = " + request.getUrl());
        System.out.println("SampleHttpClient request.getHeader() = " + request.getHeader());
        System.out.println("SampleHttpClient request.getBody() = " + request.getBody());

        if(request.getResponseType() == ResponseType.FILE){
            request.onResponse(200, null, new File("12346789"));
        }else{
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
}
