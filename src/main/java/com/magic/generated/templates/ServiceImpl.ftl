package ${classPath}.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import ${classPath}.Dao.${uTableName}Dao;
import ${classPath}.Entity.${uTableName};
import ${classPath}.Service.${uTableName}Service;



@Service
public class ${uTableName}ServiceImpl implements ${uTableName}Service {
    @Autowired
    private ${uTableName}Dao ${uTableName}Dao;

    @Override
    public int get${uTableName}Count(${uTableName} ${uTableName}){
        return ${uTableName}Dao.get${uTableName}Count(${uTableName});
    }

    @Override
    public List<${uTableName}> get${uTableName}List(${uTableName} ${uTableName}){
        return ${uTableName}Dao.get${uTableName}List(${uTableName});
    }

    @Override
    public ${uTableName} get${uTableName}(String id){
        return ${uTableName}Dao.get${uTableName}(id);
    }

    @Override
    public int add${uTableName}(${uTableName} ${uTableName}){
        return ${uTableName}Dao.add${uTableName}(${uTableName});
    }

    @Override
    public int update${uTableName}(${uTableName} ${uTableName}){
        return ${uTableName}Dao.update${uTableName}(${uTableName});
    }

    @Override
    public int delete${uTableName}(${uTableName} ${uTableName}){
        return ${uTableName}Dao.delete${uTableName}(${uTableName});
    }

    @Override
    public int delete${uTableName}ById(List<String> list){
        return ${uTableName}Dao.delete${uTableName}ById(list);
    }
}