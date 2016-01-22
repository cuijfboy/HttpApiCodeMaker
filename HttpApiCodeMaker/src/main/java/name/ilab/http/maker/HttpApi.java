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

    // local
    private String name;

    // local & global
    private HttpMethod method;

    // local
    private String fullUrl;

    // local
    private String url;

    // global
    private String urlBase;

    // auto
    private transient Map<String, String> urlParameterMap;

    // local & global
    // Map< "head" or "body", Map< fieldName, fieldClassName >>
    private Map<String, Map<String, String>> request;

    // local & global
    // Map< "head" or "body", < fieldName, fieldClassName >>
    private Map<String, Map<String, String>> response;

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

    public void prepare() {
        request = request == null ? new HashMap<String, Map<String, String>>() : request;
        response = response == null ? new HashMap<String, Map<String, String>>() : response;
        importList = importList == null ? new ArrayList<String>() : importList;
        hookNameList = hookNameList == null ? new ArrayList<String>() : hookNameList;
        model = model == null ? new HashMap<String, Map<String, String>>() : model;
        urlParameterMap = new HashMap<>();
    }

    public void merge(String name, HttpApi that) {
        this.name = name == null ? this.name : name;
        this.method = this.method == null ? that.method : this.method;
        if (this.fullUrl == null) {
            this.urlBase = this.urlBase == null ? that.urlBase : this.urlBase;
            this.url = this.url == null ? that.url : this.url;
            this.fullUrl = this.urlBase + this.url;
        }
        mergeParameterMap(this.request, that.request);
        mergeParameterMap(this.response, that.response);
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
        Map<String, String> bodyMap = response.get("body");
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

    private void mergeParameterMap(Map<String, Map<String, String>> thisMap,
                                   Map<String, Map<String, String>> thatMap) {
        mergeParameterSubMap(getMapFromMap(thisMap, "header"), thatMap.get("header"));
        mergeParameterSubMap(getMapFromMap(thisMap, "body"), thatMap.get("body"));
    }

    private Map<String, String> getMapFromMap(Map<String, Map<String, String>> map, String key) {
        Map<String, String> value = map.get(key);
        if (value == null) {
            value = new HashMap<>();
            map.put(key, value);
        }
        return value;
    }

    private void mergeParameterSubMap(Map<String, String> thisMap,
                                      Map<String, String> thatMap) {
        if (thisMap == null) {
            thisMap = new HashMap<>();
            if (thatMap == null) {
                return;
            } else {
                addExtraParameter(thisMap, thatMap);
            }
        } else {
            if (thatMap == null) {
                removeUnnecessaryParameter(thisMap);
            } else {
                addExtraParameter(thisMap, thatMap);
                removeUnnecessaryParameter(thisMap);
            }
        }
    }

    private void addExtraParameter(Map<String, String> thisMap,
                                   Map<String, String> thatMap) {
        Set<String> diffKeySet = new HashSet<>();
        diffKeySet.addAll(thatMap.keySet());
        diffKeySet.removeAll(thisMap.keySet());
        for (String key : diffKeySet) {
            thisMap.put(key, thatMap.get(key));
        }
    }

    private void removeUnnecessaryParameter(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getValue() == null) {
                iterator.remove();
            }
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

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public Map<String, Map<String, String>> getRequest() {
        return request;
    }

    public void setRequest(Map<String, Map<String, String>> request) {
        this.request = request;
    }

    public Map<String, Map<String, String>> getResponse() {
        return response;
    }

    public void setResponse(Map<String, Map<String, String>> response) {
        this.response = response;
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

    public Map<String, String> getUrlParameterMap() {
        return urlParameterMap;
    }

    public void setUrlParameterMap(Map<String, String> urlParameterMap) {
        this.urlParameterMap = urlParameterMap;
    }

    @Override
    public String toString() {
        return "HttpApi{" +
                "name='" + name + '\'' +
                ", method=" + method +
                ", fullUrl='" + fullUrl + '\'' +
                ", url='" + url + '\'' +
                ", urlBase='" + urlBase + '\'' +
                ", request=" + request +
                ", response=" + response +
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
