package name.ilab.http;

import name.ilab.http.maker.HttpApiCode;

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
        return null;
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
}
