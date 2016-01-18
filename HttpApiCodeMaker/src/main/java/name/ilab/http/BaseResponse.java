package name.ilab.http;

import java.util.Map;

/**
 * Created by cuijfboy on 16/1/9.
 */
public class BaseResponse {
    protected ResponseType responseType;
    protected int statusCode;
    protected HttpMethod method;
    protected String url;
    protected Map<String, String> header;
    protected String body;
    protected String fileSavePath;

    public BaseResponse() {
    }

    public BaseResponse(BaseResponse response) {
        set(response);
    }

    public BaseResponse(ResponseType responseType,
                        int statusCode, HttpMethod method, String url, Map<String, String> header) {
        this.responseType = responseType;
        this.statusCode = statusCode;
        this.method = method;
        this.url = url;
        this.header = header;
    }

    public void set(BaseResponse response) {
        this.responseType = response.responseType;
        this.statusCode = response.statusCode;
        this.method = response.method;
        this.url = response.url;
        this.header = response.header;
        this.body = response.body;
        this.fileSavePath = response.fileSavePath;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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