package name.ilab.http;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public abstract class BaseRequest implements IHttpRequest {
    protected HttpMethod method;
    protected String url;
    protected Map<String, String> header;
    protected String body;
    protected ResponseType responseType;

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

}
