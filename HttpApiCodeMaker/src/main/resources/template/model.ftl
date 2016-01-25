package ${api.packageName};

<#if api.name == "BaseResponseModel">
import name.ilab.http.BaseResponse;
import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;
import name.ilab.http.Utils;
import java.util.Map;
</#if>

import name.ilab.http.Utils;

<#list api.importList as import>
import ${import};
</#list>

public class ${api.name} <#if api.parentClass??>extends ${api.parentClass} </#if>{

    <#list api.requestParameterMap.header?keys as parameter>
    public transient ${api.requestParameterMap.header[parameter]} ${parameter};
    </#list>
    <#list api.requestParameterMap.body?keys as parameter>
    public ${api.requestParameterMap.body[parameter]} ${parameter};
    </#list>

    <#if api.name == "BaseResponseModel">
    public BaseResponseModel(BaseResponse response) {
        super(response);
    }

    public BaseResponseModel(ResponseType responseType,
                             int statusCode, HttpMethod method, String url, Map<String, String> header) {
        super(responseType, statusCode, method, url, header);
    }

    </#if>
    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static ${api.name} valueOf(String valueString) {
        return Utils.fromJson(valueString, ${api.name}.class);
    }
}

