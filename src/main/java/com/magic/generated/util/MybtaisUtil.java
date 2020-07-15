package com.magic.generated.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/14 7:53
 */
public class MybtaisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        if(sqlSessionFactory != null){
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;
    }

    public SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }

}
