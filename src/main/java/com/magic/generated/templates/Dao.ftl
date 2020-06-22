package ${classPath}.Dao;

import java.util.List;
import ${classPath}.Entity.${uTableName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ${uTableName}Dao {

    int get${uTableName}Count(${uTableName} ${uTableName});

    List<${uTableName}> get${uTableName}List(${uTableName} ${uTableName});

    ${uTableName} get${uTableName}(String id);

    int add${uTableName}(${uTableName} ${uTableName});

    int update${uTableName}(${uTableName} ${uTableName});

    int delete${uTableName}(${uTableName} ${uTableName});

    int delete${uTableName}ById(List<String> list);
}