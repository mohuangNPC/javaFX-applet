<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${classPath}.Dao.${uTableName}Dao">
    <sql id="Base_Column_List">
        ${allfield}
    </sql>
    <select id="get${uTableName}Count" resultType="int" parameterType="${classPath}.Entity.${uTableName}">
        select count(id) from ${tableName}
        <where>
            <trim suffixOverrides="and">
                <#list list as names>
                    <if test="${names["name"]} !=null ">${names["name"]} = ${names["sName"]} and</if>
                </#list>
            </trim>
        </where>
    </select>
    <select id="get${uTableName}List" resultType="${classPath}.Entity.${uTableName}" parameterType="${classPath}.Entity.${uTableName}">
        select
        <include refid="Base_Column_List"/>
        from
        ${tableName}
        <where>
            <trim suffixOverrides="and">
                <#list list as names>
                    <if test="${names["name"]} !=null ">${names["name"]} = ${names["sName"]} and</if>
                </#list>
            </trim>
        </where>
    </select>
    <select id="get${uTableName}" resultType="${classPath}.Entity.${uTableName}" parameterType="string">
        select
        <include refid="Base_Column_List"/>
        from ${tableName} where id=${r"#{value}"}
    </select>
    <insert id="add${uTableName}" parameterType="${classPath}.Entity.${uTableName}">
        insert into ${tableName}
        (${allfield})
        values (${sAllfield})
    </insert>
    <update id="update${uTableName}" parameterType="${classPath}.Entity.${uTableName}">
        update ${tableName}
        <trim prefix="set" suffixOverrides=",">
            <#list list as names>
                <#if (names["name"]!='id')>
                    <if test="${names["name"]} !=null ">${names["name"]} = ${names["sName"]} and</if>
                </#if>
            </#list>
        </trim>
        <where>
            id=${r"#{id}"}
        </where>
    </update>
    <delete id="delete${uTableName}" parameterType="${classPath}.Entity.${uTableName}">
        delete from ${tableName}
        <where>
            <if test="id !=null ">id=${r"#{id}"}</if>
        </where>
    </delete>
    <delete id="delete${uTableName}ById" parameterType="string">
        delete from ${tableName}
        <where>id in
            <foreach item="item" collection="array" open="(" separator="," close=")">
                ${r"#{item}"}
            </foreach>
        </where>
    </delete>
</mapper>
