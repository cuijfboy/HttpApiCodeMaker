package name.ilab.http.maker;

import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;

import java.util.*;

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
    // local & global
    // Map< "head" or "body", Map< fieldName, fieldClassName >>
    private Map<String, Map<String, String>> request;
    // local & global
    // Map< "head" or "body", < fieldName, fieldClassName >>
    private Map<String, Map<String, String>> response;
    // global
    private String packageName;
    // local & global
    private String hookName;
    // local & global
    private List<String> importList;
    // global
    private String codeFileFolder;
    // local & global
    // Map< className, Map< fieldName, fieldClassName >>
    private Map<String, Map<String, String>> model;
    // auto
    private ResponseType responseType;

    public void prepare() {
        request = request == null ? new HashMap<String, Map<String, String>>() : request;
        response = response == null ? new HashMap<String, Map<String, String>>() : response;
        importList = importList == null ? new ArrayList<String>() : importList;
        model = model == null ? new HashMap<String, Map<String, String>>() : model;
    }

    public void merge(String name, HttpApi api) {
        this.name = this.name == null ? name : this.name;
        this.method = this.method == null ? api.method : this.method;
        if (this.fullUrl == null) {
            this.urlBase = this.urlBase == null ? api.urlBase : this.urlBase;
            this.url = this.url == null ? api.url : this.url;
            this.fullUrl = this.urlBase + this.url;
        }
        mergeParameterMap(this.request, api.request);
        mergeParameterMap(this.response, api.response);
        this.packageName = this.packageName == null ? api.packageName : this.packageName;
        this.hookName = this.hookName == null ? api.hookName : this.hookName;
        mergeImportList(this.importList, api.importList);
        this.codeFileFolder = this.codeFileFolder == null ? api.codeFileFolder : this.codeFileFolder;
    }

    public void refresh() {
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

    private void mergeImportList(List<String> thisList, List<String> thatList) {
        List<String> diffList = new ArrayList<>(thatList);
        diffList.removeAll(thisList);
        thisList.addAll(diffList);
    }

    public String getName() {
        return name;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public Map<String, Map<String, String>> getRequest() {
        return request;
    }

    public Map<String, Map<String, String>> getResponse() {
        return response;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getHookName() {
        return hookName;
    }

    public List<String> getImportList() {
        return importList;
    }

    public String getCodeFileFolder() {
        return codeFileFolder;
    }

    public Map<String, Map<String, String>> getModel() {
        return model;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public void setRequest(Map<String, Map<String, String>> request) {
        this.request = request;
    }

    public void setResponse(Map<String, Map<String, String>> response) {
        this.response = response;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setHookName(String hookName) {
        this.hookName = hookName;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public void setCodeFileFolder(String codeFileFolder) {
        this.codeFileFolder = codeFileFolder;
    }

    public void setModel(Map<String, Map<String, String>> model) {
        this.model = model;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
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
                ", hookName='" + hookName + '\'' +
                ", importList=" + importList +
                ", codeFileFolder='" + codeFileFolder + '\'' +
                ", model=" + model +
                ", responseType=" + responseType +
                '}';
    }
}
