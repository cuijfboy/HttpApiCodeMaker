package name.ilab.http;

import name.ilab.http.maker.HttpApiCode;

import java.util.*;

/**
 * Created by cuijfboy on 15/11/28.
 */
@HttpApiCode(configuration = "preset.api.json")
public abstract class HttpRequest implements IHttpRequest {
    protected HttpMethod method;
    protected String url;
    protected Map<String, String> header;
    protected String body;
    protected ResponseType responseType;
    protected String fileSavePath;
    protected Set<String> hookNameSet;
    private Set<IApiHook> hookSet;

    public HttpRequest() {
        this.header = new HashMap<>();
        this.hookNameSet = new HashSet<>();
    }

    protected Set<IApiHook> obtainHookList() {
        if (hookSet == null) {
            hookSet = new HashSet<>();
            for (String name : hookNameSet) {
                IApiHook hook = HttpApiHelper.getInstance().getApiHook(name);
                if (hook != null) {
                    hookSet.add(hook);
                }
            }
        }
        return hookSet;
    }

    protected void clearHookList() {
        hookSet = null;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public ResponseType getResponseType() {
        return responseType;
    }

    @Override
    public String getFileSavePath() {
        return fileSavePath;
    }

    public Set<String> getHookNameSet() {
        return hookNameSet;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public void setHookNameSet(Set<String> hookNameSet) {
        this.hookNameSet = hookNameSet;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                ", responseType=" + responseType +
                ", fileSavePath='" + fileSavePath + '\'' +
                ", hookNameSet=" + hookNameSet +
                '}';
    }
}
