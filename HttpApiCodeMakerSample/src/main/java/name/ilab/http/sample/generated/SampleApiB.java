/******************************************************************************

            This file is automatically generated by HttpApiCodeMaker.
            Do not modify this file -- YOUR CHANGES WILL BE ERASED!

******************************** CONFIGURATION ********************************

"SampleApiB": "SampleApiB": {
  "method": "GET",
  "fullUrl": "http://www.example.com/sampleB",
  "url": "sampleB",
  "baseUrl": "http://www.example.com/",
  "request": {
    "static": null,
    "name": "HttpRequest",
    "extends": "SampleRequestModelA",
    "implements": [],
    "body": {
      "sampleApiBRequestBodyArg1": "String",
      "sampleApiBRequestBodyArg2": "int",
      "commonRequestBodyArg2": "int"
    },
    "header": {
      "sampleApiBRequestHeaderArg1": "String",
      "sampleApiBRequestHeaderArg2": "int",
      "commonRequestHeaderArg2": "int"
    }
  },
  "response": {
    "static": null,
    "name": "HttpResponse",
    "extends": "SampleResponseModelA",
    "implements": [],
    "body": {
      "sampleApiBResponseBodyArg1": "String",
      "sampleApiBResponseBodyArg2": "int",
      "commonResponseBodyArg1": "String"
    },
    "header": {
      "sampleApiBResponseHeaderArg1": "String",
      "sampleApiBResponseHeaderArg2": "int",
      "commonResponseHeaderArg2": "int"
    }
  },
  "hook": [
    "name.ilab.http.sample.SampleHook"
  ],
  "client": "name.ilab.http.sample.SampleHttpClient",
  "responseType": "TEXT",
  "source": "./src/main/java/",
  "package": "name.ilab.http.sample.generated",
  "import": [
    "java.util.List",
    "java.util.Map",
    "java.util.Set"
  ],
  "inner": {},
  "name": "SampleApiB",
  "extends": null,
  "implements": [],
  "body": {},
  "header": {}
}

******************************************************************************/

package name.ilab.http.sample.generated;

import java.util.List;
import java.util.Map;
import java.util.Set;

import name.ilab.http.HttpRequest;
import name.ilab.http.HttpResponse;
import name.ilab.http.HttpApiHelper;
import name.ilab.http.HttpMethod;
import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.ResponseType;
import name.ilab.http.HttpApiHelper;

import java.io.File;
import java.util.Map;

public class SampleApiB extends HttpRequest {

    public static final String API_NAME =
            "name.ilab.http.sample.generated.SampleApiB";
    public static final String HTTP_CLIENT_NAME =
            "name.ilab.http.sample.SampleHttpClient";

    public class Request extends SampleRequestModelA {

        // header
        public transient String sampleApiBRequestHeaderArg1;
        public transient int sampleApiBRequestHeaderArg2;
        public transient int commonRequestHeaderArg2;

        // body
        public String sampleApiBRequestBodyArg1;
        public int sampleApiBRequestBodyArg2;
        public int commonRequestBodyArg2;

        // url parameter

        private void generateMethod() {
            if (method == null) {
                method = HttpMethod.GET;
            }
        }

        private void generateUrl() {
            if (url == null) {
                url = "http://www.example.com/sampleB";
                if (HttpMethod.GET == method || HttpMethod.DELETE == method) {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
                    stringBuffer.append("sampleApiBRequestBodyArg1").append("=")
                            .append(sampleApiBRequestBodyArg1).append("&");
                    stringBuffer.append("sampleApiBRequestBodyArg2").append("=")
                            .append(sampleApiBRequestBodyArg2).append("&");
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
                header.put("sampleApiBRequestHeaderArg1", String.valueOf(sampleApiBRequestHeaderArg1));
                header.put("sampleApiBRequestHeaderArg2", String.valueOf(sampleApiBRequestHeaderArg2));
                header.put("commonRequestHeaderArg2", String.valueOf(commonRequestHeaderArg2));
            }
        }

        private void generateBody() {
            if (body == null) {
                body = toString();
            }
        }

        @Override
        public String toString() {
            return HttpApiHelper.toJson(this);
        }

    }

    public static class Response extends SampleResponseModelA {

        // header
        public transient String sampleApiBResponseHeaderArg1;
        public transient int sampleApiBResponseHeaderArg2;
        public transient int commonResponseHeaderArg2;

        // body
        public String sampleApiBResponseBodyArg1;
        public int sampleApiBResponseBodyArg2;
        public String commonResponseBodyArg1;

        public Response(HttpResponse httpResponse) {
            super(httpResponse);
        }

        public Response(ResponseType responseType, int statusCode, HttpMethod method, String url,
                        Map<String, String> header) {
            super(responseType, statusCode, method, url, header);
        }

        public static Response valueOf(String valueString) {
            return HttpApiHelper.fromJson(valueString, Response.class);
        }

    }

    // --------------------------------------------------------------------------------------------

    public SampleApiB() {
        this.request = new Request();
        this.responseType = ResponseType.TEXT;
        hookNameSet.add("name.ilab.http.sample.SampleHook");
    }

    public SampleApiB go(IHttpClient httpClient) {
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

    public SampleApiB go() {
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
            valueString = header.get("sampleApiBResponseHeaderArg1");
            response.sampleApiBResponseHeaderArg1 = valueString;
            valueString = header.get("sampleApiBResponseHeaderArg2");
            response.sampleApiBResponseHeaderArg2 = valueString == null ? 0 : Integer.parseInt(valueString);
            valueString = header.get("commonResponseHeaderArg2");
            response.commonResponseHeaderArg2 = valueString == null ? 0 : Integer.parseInt(valueString);
        }
    }

// ################################################################################################

    public Request request;
    public Response response;

    private void generateResponseData(HttpResponse httpResponse) {
        try {
            response = Response.valueOf(httpResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(httpResponse);
        }
        fillResponseHeader(httpResponse.getHeader());
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        HttpResponse httpResponse = new HttpResponse(responseType, statusCode, method, url, header);
        httpResponse.setBody(body);
        for (IApiHook hook : obtainHookList()) {
            hook.onResponse(API_NAME, responseType, httpResponse);
        }
        generateResponseData(httpResponse);
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