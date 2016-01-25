package ${api.packageName};

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

<#list api.importList as import>
import ${import};
</#list>

public class ${api.name} extends BaseRequest {
    public static final String API_NAME =
            "${api.packageName}.${api.name}";
    <#if api.clientName??>
    public static final String HTTP_CLIENT_NAME =
            "${api.clientName}";
    <#else>
    public static final String HTTP_CLIENT_NAME = null;
    </#if>

    public class Request extends BaseRequestModel {
        <#list api.requestParameterMap.header?keys as parameter>
        public transient ${api.requestParameterMap.header[parameter]} ${parameter};
        </#list>
        <#list api.requestParameterMap.body?keys as parameter>
        public ${api.requestParameterMap.body[parameter]} ${parameter};
        </#list>
        <#list api.urlParameterMap?keys as parameter>
        public transient ${api.urlParameterMap[parameter]} ${parameter};
        </#list>

        private void generateMethod() {
            if (method == null) {
                method = HttpMethod.${api.method};
            }
        }

        private void generateUrl() {
            if (url == null) {
                url = "${api.fullUrl}";
                <#list api.urlParameterMap?keys as parameter>
                <#assign type = api.urlParameterMap[parameter]/>
                url = url.replaceAll("\\{${parameter}(:${type})?\\}", String.valueOf(${parameter}));
                </#list>
                if (HttpMethod.GET == method) {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
                    <#list api.requestParameterMap.body?keys as parameter>
                    stringBuffer.append("${parameter}").append("=")
                            .append(${parameter}).append("&");
                    </#list>
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    if (stringBuffer.length() != url.length()) {
                        url = stringBuffer.toString();
                    }
                }
            }
        }

        private void generateHeader() {
            <#if (api.requestParameterMap.header?size > 0)>
            if (header.isEmpty()) {
                <#list api.requestParameterMap.header?keys as parameter>
                header.put("${parameter}", String.valueOf(${parameter}));
                </#list>
            }
            </#if>
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

    public static class Response extends BaseResponseModel {

        <#list api.responseParameterMap.header?keys as parameter>
        public transient ${api.responseParameterMap.header[parameter]} ${parameter};
        </#list>
        <#list api.responseParameterMap.body?keys as parameter>
        public ${api.responseParameterMap.body[parameter]} ${parameter};
        </#list>

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

    <#if api.model??>
        <#list api.model?keys as class>
    public static class ${class} {
            <#list api.model[class]?keys as parameter>
        public ${api.model[class][parameter]} ${parameter};
            </#list>

        @Override
        public String toString() {
            return Utils.toJson(this);
        }

        public static ${class} valueOf(String valueString) {
            return Utils.fromJson(valueString, ${class}.class);
        }

    }

        </#list>
    </#if>
    // --------------------------------------------------------------------------------------------

    public ${api.name}() {
        this.request = new Request();
        this.responseType = ResponseType.${api.responseType};
        <#list api.hookNameList as hookName>
        hookNameList.add("${hookName}");
        </#list>
    }

    public ${api.name} go(IHttpClient httpClient) {
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

    public ${api.name} go() {
        return go(HttpApiHelper.getInstance().getHttpClient(HTTP_CLIENT_NAME));
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url,
                                      Map<String, String> header, File file) {
        response = new Response(responseType, statusCode, method, url, header);
        response.setFileSavePath(fileSavePath);
        <#if api.responseType == "FILE">
            <#list api.responseParameterMap.body?keys as parameter>
                <#if api.responseParameterMap.body[parameter] == "File">
        response.${parameter} = file;
                <#break>
                </#if>
            </#list>
        </#if>
        fillResponseHeader(header);
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url,
                                      Map<String, String> header, byte[] data) {
        response = new Response(responseType, statusCode, method, url, header);
        <#if api.responseType == "BINARY">
            <#list api.responseParameterMap.body?keys as parameter>
                <#if api.responseParameterMap.body[parameter] == "byte[]">
        response.${parameter} = data;
                <#break>
                </#if>
            </#list>
        </#if>
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
        <#if (api.responseParameterMap.header?size > 0)>
        if (header != null) {
            String valueString = null;
            <#list api.responseParameterMap.header?keys as parameter>
            valueString = header.get("${parameter}");
            <#assign type = api.responseParameterMap.header[parameter]/>
            response.${parameter} = ${parsePrimaryTypeData(type, "valueString")};
            </#list>
        }
        </#if>
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