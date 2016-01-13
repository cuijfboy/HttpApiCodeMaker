package ${api.packageName};

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

<#list api.importList as import>
import ${import};
</#list>

public class ${api.name} extends BaseRequest {
    public static final String API_NAME =
            "${api.packageName}.${api.name}";
    <#if api.hookName??>
    public static final String HOOK_NAME =
            "${api.hookName}";
    <#else>
    public static final String HOOK_NAME = null;
    </#if>

    public class Request {
        <#list api.request.header?keys as parameter>
        public transient ${api.request.header[parameter]} ${parameter};
        </#list>
        <#list api.request.body?keys as parameter>
        public ${api.request.body[parameter]} ${parameter};
        </#list>

        private void generateMethod() {
            method = HttpMethod.${api.method};
        }

        private void generateUrl() {
            url = "${api.fullUrl}";
            <#if api.method == 'GET'>
            if (HttpMethod.GET == method) {
                StringBuffer sb = new StringBuffer(url);
                sb.append("?");
                <#list api.request.body?keys as parameter>
                sb.append("${parameter}").append("=").append(${parameter}).append("&");
                </#list>
                sb.deleteCharAt(sb.length() - 1);
                if (sb.length() != url.length()) {
                    url = sb.toString();
                }
            }
            </#if>
        }

        private void generateHeader() {
            header.clear();
            <#list api.request.header?keys as parameter>
            header.put("${parameter}", ${parameter});
            </#list>
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

        <#list api.response.header?keys as parameter>
        public transient ${api.response.header[parameter]} ${parameter};
        </#list>
        <#list api.response.body?keys as parameter>
        public ${api.response.body[parameter]} ${parameter};
        </#list>
    }

    <#if api.model??>
        <#list api.model?keys as class>
    public class ${class} {
            <#list api.model[class]?keys as parameter>
        public ${api.model[class][parameter]} ${parameter};
            </#list>
    }

        </#list>
    </#if>
    // --------------------------------------------------------------------------------------------

    public ${api.name}() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
        this.responseType = ResponseType.${api.responseType};
    }

    public ${api.name} go(IHttpClient httpClient) {
        hook.onRequestData(API_NAME, request, request.getClass());
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        request.generateBody();
        hook.onRequest(API_NAME, this, request, request.getClass());
        httpClient.request(this);
        return this;
    }

    public ${api.name} go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      File file) {
        response = new Response(statusCode, method, url, header);
        <#if api.responseType == "FILE">
            <#list api.response.body?keys as parameter>
                <#if api.response.body[parameter] == "File">
        response.${parameter} = file;
                <#break>
                </#if>
            </#list>
        </#if>
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url, Map<String, String> header,
                                      byte[] data) {
        response = new Response(statusCode, method, url, header);
        <#if api.responseType == "BINARY">
            <#list api.response.body?keys as parameter>
                <#if api.response.body[parameter] == "byte[]">
        response.${parameter} = data;
                <#break>
                </#if>
            </#list>
        </#if>
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        if (header != null) {
            <#list api.response.header?keys as parameter>
            response.${parameter} = header.get("${parameter}");
            </#list>
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