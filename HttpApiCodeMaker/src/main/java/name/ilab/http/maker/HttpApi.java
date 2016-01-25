package name.ilab.http.maker;

import com.google.gson.annotations.SerializedName;
import name.ilab.http.HttpApiHelper;
import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApi {

    public static final String BASE_REQUEST = "BaseRequest";
    public static final String BASE_RESPONSE = "BaseResponse";
    public static final String BASE_REQUEST_MODEL = "BaseRequestModel";
    public static final String BASE_RESPONSE_MODEL = "BaseResponseModel";
    public static final String HEADER = "header";
    public static final String BODY = "body";

    // local
    private String name;

    // local & global
    private HttpMethod method;

    // local
    private String fullUrl;

    // local
    private String url;

    // global
    private String baseUrl;

    // auto
    private transient Map<String, String> urlParameterMap;

    // local & global
    // Map< "head" or "body", Map< fieldName, fieldClassName >>
    @SerializedName("request")
    private Map<String, Map<String, String>> requestParameterMap;

    // local & global
    // Map< "head" or "body", < fieldName, fieldClassName >>
    @SerializedName("response")
    private Map<String, Map<String, String>> responseParameterMap;

    // local & global
    @SerializedName("package")
    private String packageName;

    // local & global
    @SerializedName("hooks")
    private List<String> hookNameList;

    // local & global
    @SerializedName("imports")
    private List<String> importList;

    // local & global
    @SerializedName("output")
    private String outputPath;

    // local & global
    // Map< className, Map< fieldName, fieldClassName >>
    private Map<String, Map<String, String>> model;

    // auto
    private ResponseType responseType;

    // local & global
    @SerializedName("client")
    private String clientName;

    // auto
    private String parentClass;

    public void prepare() {
        requestParameterMap = requestParameterMap == null ? new HashMap<String, Map<String, String>>() : requestParameterMap;
        prepareParameterMap(requestParameterMap);
        responseParameterMap = responseParameterMap == null ? new HashMap<String, Map<String, String>>() : responseParameterMap;
        prepareParameterMap(responseParameterMap);
        importList = importList == null ? new ArrayList<String>() : importList;
        hookNameList = hookNameList == null ? new ArrayList<String>() : hookNameList;
        model = model == null ? new HashMap<String, Map<String, String>>() : model;
        urlParameterMap = new HashMap<>();
    }

    public static void prepareParameterMap(Map<String, Map<String, String>> parameterMap) {
        prepareParameterMap(HEADER, parameterMap);
        prepareParameterMap(BODY, parameterMap);
    }

    public static void prepareParameterMap(String key, Map<String, Map<String, String>> parameterMap) {
        if (parameterMap.get(key) == null) {
            parameterMap.put(key, new HashMap<String, String>());
        }
    }

    public void merge(String name, HttpApi that) {
        this.name = name == null ? this.name : name;
        this.method = this.method == null ? that.method : this.method;
        if (this.fullUrl == null) {
            this.baseUrl = this.baseUrl == null ? that.baseUrl : this.baseUrl;
            this.url = this.url == null ? that.url : this.url;
            this.fullUrl = this.baseUrl + this.url;
        }
        this.packageName = this.packageName == null ? that.packageName : this.packageName;
        mergeList(this.hookNameList, that.hookNameList);
        mergeList(this.importList, that.importList);
        this.outputPath = this.outputPath == null ? that.outputPath : this.outputPath;
        this.clientName = this.clientName == null ? that.clientName : this.clientName;
    }

    public void refresh() {
        refreshHttpClient();
        generateUrlParameterMap();
        generateResponseType();
    }

    private void refreshHttpClient() {
        clientName = clientName == null ? HttpApiHelper.DEFAULT_HTTP_CLIENT_NAME : clientName;
    }

    private void generateResponseType() {
        responseType = ResponseType.TEXT;
        Map<String, String> bodyMap = responseParameterMap.get(BODY);
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
            urlParameterMap.put(name, type == null ? "String" : type);
        }
    }

    private void mergeList(List<String> thisList, List<String> thatList) {
        List<String> diffList = new ArrayList<>(thatList);
        diffList.removeAll(thisList);
        thisList.addAll(diffList);
    }

    // -------------------------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Map<String, String> getUrlParameterMap() {
        return urlParameterMap;
    }

    public void setUrlParameterMap(Map<String, String> urlParameterMap) {
        this.urlParameterMap = urlParameterMap;
    }

    public Map<String, Map<String, String>> getRequestParameterMap() {
        return requestParameterMap;
    }

    public void setRequestParameterMap(Map<String, Map<String, String>> requestParameterMap) {
        this.requestParameterMap = requestParameterMap;
    }

    public Map<String, Map<String, String>> getResponseParameterMap() {
        return responseParameterMap;
    }

    public void setResponseParameterMap(Map<String, Map<String, String>> responseParameterMap) {
        this.responseParameterMap = responseParameterMap;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getHookNameList() {
        return hookNameList;
    }

    public void setHookNameList(List<String> hookNameList) {
        this.hookNameList = hookNameList;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Map<String, Map<String, String>> getModel() {
        return model;
    }

    public void setModel(Map<String, Map<String, String>> model) {
        this.model = model;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public String toString() {
        return "HttpApi{" +
                "name='" + name + '\'' +
                ", method=" + method +
                ", fullUrl='" + fullUrl + '\'' +
                ", url='" + url + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", requestParameterMap=" + requestParameterMap +
                ", responseParameterMap=" + responseParameterMap +
                ", packageName='" + packageName + '\'' +
                ", hookNameList=" + hookNameList +
                ", importList=" + importList +
                ", outputPath='" + outputPath + '\'' +
                ", model=" + model +
                ", responseType=" + responseType +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
