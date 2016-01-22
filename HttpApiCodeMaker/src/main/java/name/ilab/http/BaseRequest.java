package name.ilab.http;

import name.ilab.http.maker.HttpApiCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
@HttpApiCode(configFile = "preset_api.json")
public abstract class BaseRequest implements IHttpRequest {
    protected HttpMethod method;
    protected String url;
    protected Map<String, String> header;
    protected String body;
    protected ResponseType responseType;
    protected String fileSavePath;
    protected List<String> hookNameList;
    private List<IApiHook> hookList;

    public BaseRequest() {
        this.header = new HashMap<>();
        this.hookNameList = new ArrayList<>();
    }

    protected List<IApiHook> obtainHookList() {
        if (hookList == null) {
            hookList = new ArrayList<>();
            for (String name : hookNameList) {
                IApiHook hook = HttpApiHelper.getInstance().getApiHook(name);
                if (hook != null) {
                    hookList.add(hook);
                }
            }
        }
        return hookList;
    }

    protected void clearHookList() {
        hookList = null;
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

    public List<String> getHookNameList() {
        return hookNameList;
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

    public void setHookNameList(List<String> hookNameList) {
        this.hookNameList = hookNameList;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                ", responseType=" + responseType +
                ", fileSavePath='" + fileSavePath + '\'' +
                ", hookNameList=" + hookNameList +
                '}';
    }
}
