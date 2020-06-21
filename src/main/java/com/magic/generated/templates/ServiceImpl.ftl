package ${classPath}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import ${classPath}.dao.${uTableName}Dao;
import ${classPath}.entity.${uTableName};
import ${classPath}.service.${uTableName}Service;



@Service("${uTableName}Service")
public class ${uTableName}ServiceImpl implements ${uTableName}Service {
    @Autowired
    private ${uTableName}Dao ${uTableName}Dao;

    @Override
    public int get${uTableName}Count(${uTableName} ${uTableName}){
        return ${uTableName}Dao.get${uTableName}Count(${uTableName});
    }

    @Override
    public List<${uTableName}> get${uTableName}List(${uTableName} ${uTableName}){
        return get${uTableName}List(${uTableName});
    }

    @Override
    public ${uTableName} get${uTableName}(String id){
        return ${uTableName} get${uTableName}(id);
    }

    @Override
    public int add${uTableName}(${uTableName} ${uTableName}){
        return add${uTableName}(${uTableName});
    }

    @Override
    public int update${uTableName}(${uTableName} ${uTableName}){
        return update${uTableName}(${uTableName});
    }

    @Override
    public int delete${uTableName}(${uTableName} ${uTableName}){
        return delete${uTableName}(${uTableName});
    }

    @Override
    public int delete${uTableName}ById(List<String> list list){
        return delete${uTableName}ById(list);
    }
}