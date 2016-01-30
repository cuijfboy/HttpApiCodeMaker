<#assign api = root>
<#assign class = root>
<#assign request = api.requestInnerClazz>
<#assign requestHeader = request.transientFieldMap>
<#assign requestBody = request.ordinaryFieldMap>
<#assign response = api.responseInnerClazz>
<#assign responseHeader = response.transientFieldMap>
<#assign responseBody = response.ordinaryFieldMap>
/******************************************************************************

            This file is automatically generated by HttpApiCodeMaker.
            Do not modify this file -- YOUR CHANGES WILL BE ERASED!

******************************** CONFIGURATION ********************************

"${api.name}": ${api.configuration}

******************************************************************************/

package ${class.packageName};

<#list class.importSet as import>
import ${import};
</#list>

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

public class ${class.name} extends HttpRequest {

    public static final String API_NAME =
            "${class.packageName}.${class.name}";
<#if class.clientName??>
    public static final String HTTP_CLIENT_NAME =
            "${class.clientName}";
<#else>
    public static final String HTTP_CLIENT_NAME = null;
</#if>

    public class Request <#if request.parent??>extends ${request.parent} </#if>{

        // header
<#list requestHeader?keys as name>
    <#assign type = requestHeader[name]>
        public transient ${type} ${name};
</#list>

        // body
<#list requestBody?keys as name>
    <#assign type = requestBody[name]>
        public ${type} ${name};
</#list>

        // url parameter
<#list api.urlParamMap?keys as name>
    <#assign type = api.urlParamMap[name]>
        public transient ${type} ${name};
</#list>

        private void generateMethod() {
            if (method == null) {
                method = HttpMethod.${api.method};
            }
        }

        private void generateUrl() {
            if (url == null) {
                url = "${api.fullUrl}";
<#list api.urlParamMap?keys as name>
    <#assign type = api.urlParamMap[name]/>
                url = url.replaceAll("\\{${name}(:${type})?\\}", String.valueOf(${name}));
</#list>
                if (HttpMethod.GET == method || HttpMethod.DELETE == method) {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
<#list requestBody?keys as name>
                    stringBuffer.append("${name}").append("=")
                            .append(${name}).append("&");
</#list>
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    if (stringBuffer.length() != url.length()) {
                        url = stringBuffer.toString();
                    }
                }
            }
        }

        private void generateHeader() {
<#if (requestHeader?size > 0)>
            if (header.isEmpty()) {
    <#list requestHeader?keys as name>
                header.put("${name}", String.valueOf(${name}));
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
            return HttpApiHelper.toJson(this);
        }

    }

    public static class Response extends ${response.parent!"HttpResponse"} {

        // header
<#list responseHeader?keys as name>
    <#assign type = responseHeader[name]>
        public transient ${type} ${name};
</#list>

        // body
<#list responseBody?keys as name>
    <#assign type = responseBody[name]>
        public ${type} ${name};
</#list>

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

    public ${class.name}() {
        this.request = new Request();
        this.responseType = ResponseType.${api.responseType};
<#list api.hookNameSet as hookName>
        hookNameSet.add("${hookName}");
</#list>
    }

    public ${class.name} go(IHttpClient httpClient) {
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

    public ${class.name} go() {
        return go(HttpApiHelper.getInstance().getHttpClient(HTTP_CLIENT_NAME));
    }

    private void generateResponseData(int statusCode, HttpMethod method, String url,
                                      Map<String, String> header, File file) {
        response = new Response(responseType, statusCode, method, url, header);
        response.setFileSavePath(fileSavePath);
<#if api.responseType == "FILE">
    <#list responseBody?keys as name>
        <#if responseBody[name] == "File">
        response.${name} = file;
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
    <#list responseBody?keys as name>
        <#if responseBody[name] == "byte[]">
        response.${name} = data;
            <#break>
        </#if>
    </#list>
</#if>
        fillResponseHeader(header);
    }

    private void fillResponseHeader(Map<String, String> header) {
<#if (responseHeader?size > 0)>
        if (header != null) {
            String valueString = null;
    <#list responseHeader?keys as name>
            valueString = header.get("${name}");
            response.${name} = ${parsePrimaryTypeData(responseHeader[name], "valueString")};
    </#list>
        }
</#if>
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