package com.magic.generated.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/13 8:00
 */
public interface GenerateDao {
    List<Map<String,Object>> showTable(@Param("tableName") String tableName);
}
