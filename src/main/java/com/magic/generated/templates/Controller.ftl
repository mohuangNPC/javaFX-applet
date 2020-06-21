package ${classPath}.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${classPath}.entity.${uTableName};
import ${classPath}.service.${uTableName}Service;




@RestController
@RequestMapping("${uTableName}")
public class ${uTableName}Controller {
@Autowired
private ${uTableName}Service ${uTableName}Service;


    /**
    * count
    */
    @RequestMapping("/count")
    public Integer count(@RequestParam ${uTableName} ${uTableName}){
        return ${uTableName}Service.get${uTableName}Count(${uTableName});
    }

    /**
    * list
    */
    @RequestMapping("/list")
    public List<${uTableName}> list(@RequestParam ${uTableName} ${uTableName}){
        //查询列表数据
        List<${uTableName}> ${uTableName}List = ${uTableName}Service.get${uTableName}Count(${uTableName});
        return ${uTableName}List;
     }

    /**
    * detail
    */
    @RequestMapping("/detail")
    public ${uTableName} info(@RequestParam String id){
        ${uTableName} ${uTableName} = ${uTableName}Service.${uTableName} get${uTableName}(id);
        return ${uTableName};
    }

    /**
    * save
    */
    @RequestMapping("/save")
    public Integer save(@RequestParam ${uTableName} ${uTableName}){
        return ${uTableName}Service.add${uTableName}(${uTableName});
    }

    /**
    * update
    */
    @RequestMapping("/update")
    public Integer update(@RequestParam ${uTableName} ${uTableName}){
        return ${uTableName}Service.update${uTableName}(${uTableName});
    }

    /**
    * delete
    */
    @RequestMapping("/delete")
    public Integer delete(@RequestParam ${uTableName} ${uTableName}){
        return ${uTableName}Service.delete${uTableName}(${uTableName});
    }

    /**
    * deleteByList
    */
    @RequestMapping("/deleteList")
    public Integer delete(@RequestParam List<String> list){
        return ${uTableName}Service.delete${uTableName}ById(list);
    }
}