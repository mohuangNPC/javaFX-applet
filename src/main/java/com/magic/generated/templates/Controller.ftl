package ${classPath}.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${classPath}.Entity.${uTableName};
import ${classPath}.Service.${uTableName}Service;




@RestController
@RequestMapping("${uTableName}")
public class ${uTableName}Controller {
@Autowired
private ${uTableName}Service ${uTableName}Service;


    /**
    * count
    */
    @RequestMapping("/count")
    public Integer count(${uTableName} ${uTableName}){
        return ${uTableName}Service.get${uTableName}Count(${uTableName});
    }

    /**
    * list
    */
    @RequestMapping("/list")
    public List<${uTableName}> list(${uTableName} ${uTableName}){
        //查询列表数据
        List<${uTableName}> ${uTableName}List = ${uTableName}Service.get${uTableName}List(${uTableName});
        return ${uTableName}List;
     }

    /**
    * detail
    */
    @RequestMapping("/detail")
    public ${uTableName} info(String id){
        ${uTableName} ${uTableName} = ${uTableName}Service.get${uTableName}(id);
        return ${uTableName};
    }

    /**
    * save
    */
    @RequestMapping("/save")
    public Integer save(${uTableName} ${uTableName}){
        return ${uTableName}Service.add${uTableName}(${uTableName});
    }

    /**
    * update
    */
    @RequestMapping("/update")
    public Integer update(${uTableName} ${uTableName}){
        return ${uTableName}Service.update${uTableName}(${uTableName});
    }

    /**
    * delete
    */
    @RequestMapping("/delete")
    public Integer delete(${uTableName} ${uTableName}){
        return ${uTableName}Service.delete${uTableName}(${uTableName});
    }

    /**
    * deleteByList
    */
    @RequestMapping("/deleteList")
    public Integer delete(List<String> list){
        return ${uTableName}Service.delete${uTableName}ById(list);
    }
}