<#macro package name>
package ${name};
</#macro>

<#macro import list>
    <#if list??>
        <#list list as import>
import ${import};
        </#list>
    </#if>
</#macro>

<#macro ordinaryField filedMap>
    <#if filedMap??>
        <#list filedMap?keys as name>
            <#assign type = filedMap[name]>
public ${type} ${name};
        </#list>
    </#if>
</#macro>

<#macro transientField filedMap>
    <#if filedMap??>
        <#list filedMap?keys as name>
            <#assign type = filedMap[name]>
public transient ${type} ${name};
        </#list>
    </#if>

<#macro field class>
    <#if class??>
        <@ordinaryField filedMap = class.ordinaryFieldMap/>
        <@transientField filedMap = class.transientFieldMap/>
    </#if>
</#macro>

