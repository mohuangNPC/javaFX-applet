package ${classPath}.service;

import ${classPath}.entity.${uTableName};

import java.util.List;
import java.util.Map;


public interface ${uTableName}Service {

    int get${uTableName}Count(${uTableName} ${uTableName});

    List<${uTableName}> get${uTableName}List(${uTableName} ${uTableName});

    ${uTableName} get${uTableName}(String id);

    int add${uTableName}(${uTableName} ${uTableName});

    int update${uTableName}(${uTableName} ${uTableName});

    int delete${uTableName}(${uTableName} ${uTableName});

    int delete${uTableName}ById(List<String> list);
}
