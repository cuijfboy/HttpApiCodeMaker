package ${api.packageName};

import name.ilab.http.Utils;

<#list api.importList as import>
import ${import};
</#list>

<#list api.model?keys as class>
public class ${class} {
    <#list api.model[class]?keys as parameter>
    public ${api.model[class][parameter]} ${parameter};
    </#list>

    @Override
    public String toString() {
        return Utils.toJson(this);
    }

    public static ${class} valueOf(String valueString) {
        return Utils.fromJson(valueString, ${class}.class);
    }
}
</#list>

