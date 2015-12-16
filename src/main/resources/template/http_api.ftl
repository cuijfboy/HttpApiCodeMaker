package ${api.packageName};

import name.ilab.http.IApiHook;
import name.ilab.http.IHttpClient;
import name.ilab.http.code.template.BaseRequest;
import name.ilab.http.HttpMethod;
import name.ilab.http.code.maker.Utils;

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
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
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
    // ------------------------------------------

    public ${api.name}() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.request = new Request();
    }

    public ${api.name} go(IHttpClient httpClient) {
        request.generateMethod();
        request.generateUrl();
        request.generateHeader();
        request.generateBody();
        hook.onRequest(API_NAME, method, url, header, body, request, request.getClass());
        httpClient.request(this);
        return this;
    }

    public ${api.name} go() {
        return go(Utils.getMockHttpClient());
    }

    private void generateResponseDataObject(Map<String, String> header, String body) {
        try {
            response = Utils.getSerializeNullGson().fromJson(body, Response.class);
        } catch (Exception e) {
            response = null;
            e.printStackTrace();
        }
        response = response == null ? new Response() : response;
        <#list api.response.header?keys as parameter>
        response.${parameter} = header.get("${parameter}");
        </#list>
        hook.onResponseDataObject(API_NAME, response, response.getClass(), header, body);
    }

// Fixed BEGIN ##################################

    public Request request;
    public Response response;
    public ResponseListener responseListener;
    public IApiHook hook;

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        hook.onResponse(API_NAME, statusCode, header, body);
        generateResponseDataObject(header, body);
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

// Fixed END ####################################
