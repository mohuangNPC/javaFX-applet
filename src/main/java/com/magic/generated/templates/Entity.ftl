package ${classPath}.entity;

public class ${uTableName} {
<#list list as names>
    private ${names["type"]} ${names["name"]};
</#list>
<#list list as names>
    public ${names["type"]} get${names["uName"]}() {
        return ${names["name"]};
    }
    public void set${names["uName"]}(${names["type"]} ${names["name"]}) {
        this.${names["name"]} = ${names["name"]};
    }
</#list>
}