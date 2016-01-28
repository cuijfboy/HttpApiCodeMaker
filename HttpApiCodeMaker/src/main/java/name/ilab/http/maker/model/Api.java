package name.ilab.http.maker.model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import name.ilab.http.HttpApiHelper;
import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class Api extends Clazz {

    // local & global
    public HttpMethod method;

    // local
    private String fullUrl;

    // local
    private String url;

    // global
    public String baseUrl;

    // auto
    private transient Map<String, String> urlParamMap = new HashMap<>();

    // local & global
    @SerializedName("request")
    private InnerClazz requestInnerClazz = new InnerClazz();

    // local & global
    @SerializedName("response")
    private InnerClazz responseInnerClazz = new InnerClazz();

    // local & global
    @SerializedName("hook")
    private Set<String> hookNameSet = new HashSet<>();

    // local & global
    @SerializedName("client")
    private String clientName;

    // auto
    private ResponseType responseType;

    // auto
    private transient String configuration;

    // ------------------------------------------------------------------------

    @Override
    public void prepare() {
        super.prepare();
    }

    public void merge(String name, Api that) {
        super.merge(name, that);
        this.method = this.method == null ? that.method : this.method;
        if (this.fullUrl == null) {
            this.baseUrl = this.baseUrl == null ? that.baseUrl : this.baseUrl;
            this.url = this.url == null ? that.url : this.url;
            this.fullUrl = this.baseUrl + this.url;
        }
        this.requestInnerClazz.merge("HttpRequest", that.requestInnerClazz);
        this.responseInnerClazz.merge("HttpResponse", that.responseInnerClazz);
        mergeSet(this.hookNameSet, that.hookNameSet);
        this.clientName = this.clientName == null ? that.clientName : this.clientName;
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshHttpClient();
        generateUrlParameterMap();
        generateResponseType();
        generateConfiguration();
    }

    private void refreshHttpClient() {
        clientName = clientName == null ? HttpApiHelper.DEFAULT_HTTP_CLIENT_NAME : clientName;
    }

    private void generateResponseType() {
        responseType = ResponseType.TEXT;
        Map<String, String> bodyMap = responseInnerClazz.getOrdinaryFieldMap();
        if (bodyMap == null || bodyMap.size() != 1) {
            return;
        }
        String fieldClassName = bodyMap.values().iterator().next().trim();
        if ("File".equals(fieldClassName)) {
            responseType = ResponseType.FILE;
        } else if ("byte[]".equals(fieldClassName)) {
            responseType = ResponseType.BINARY;
        }
    }

    private void generateUrlParameterMap() {
        Matcher matcher = Pattern.compile("\\{(\\w+)(:(\\w+))?\\}").matcher(fullUrl);
        while (matcher.find()) {
            String name = matcher.group(1);
            String type = matcher.group(3);
            urlParamMap.put(name, type == null ? "String" : type);
        }
    }

    private void generateConfiguration() {
        configuration = HttpApiHelper.toPrettyJson(this);
    }

    // ------------------------------------------------------------------------

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, String> getUrlParamMap() {
        return urlParamMap;
    }

    public void setUrlParamMap(Map<String, String> urlParamMap) {
        this.urlParamMap = urlParamMap;
    }

    public InnerClazz getRequestInnerClazz() {
        return requestInnerClazz;
    }

    public void setRequestInnerClazz(InnerClazz requestInnerClazz) {
        this.requestInnerClazz = requestInnerClazz;
    }

    public InnerClazz getResponseInnerClazz() {
        return responseInnerClazz;
    }

    public void setResponseInnerClazz(InnerClazz responseInnerClazz) {
        this.responseInnerClazz = responseInnerClazz;
    }

    public Set<String> getHookNameSet() {
        return hookNameSet;
    }

    public void setHookNameSet(Set<String> hookNameSet) {
        this.hookNameSet = hookNameSet;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "Api{" +
                "method=" + method +
                ", fullUrl='" + fullUrl + '\'' +
                ", url='" + url + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", urlParamMap=" + urlParamMap +
                ", requestInnerClazz=" + requestInnerClazz +
                ", responseInnerClazz=" + responseInnerClazz +
                ", hookNameSet=" + hookNameSet +
                ", clientName='" + clientName + '\'' +
                ", responseType=" + responseType +
                ", super=" + super.toString() +
                '}';
    }
}
