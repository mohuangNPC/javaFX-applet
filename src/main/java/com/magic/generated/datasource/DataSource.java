package com.magic.generated.datasource;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 16:06
 */
public class DataSource {
    private static Logger log = Logger.getLogger(DataSource.class.getName());
    private static Connection conn;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/EAP5?useUnicode=true&amp;characterEncoding=utf8";
    private static String username = "root";
    private static String password = "root";
    private static Properties properties;
    private static String PACKAGE_PATH = "com.magic";
    private static String PACKAGE_FILE_PATH = "src\\main\\java\\com\\magic";
    private static String PACKAGE_RESOURCE_FILE_PATH = "src\\main\\resources";
    static {
        log.info("init mysql config");
        properties = new Properties();
        File f = new File(System.getProperty("user.dir")+"\\config.properties");
        if (!f.exists()) {
            log.info("Does not exist and creates");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Use default data source");
        }else {
            try {
                properties.load(new FileInputStream(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            PACKAGE_PATH = DataSource.getProperties().getProperty("packagePath");
            PACKAGE_FILE_PATH = DataSource.getProperties().getProperty("packageFilePath");
            PACKAGE_RESOURCE_FILE_PATH = DataSource.getProperties().getProperty("packageResourcePath");
        }
        log.info("=========MySQL configuration=========");
        log.info("driver:"+driver);
        log.info("url:"+url);
        log.info("username:"+username);
        log.info("password:"+password);
        log.info("PACKAGE_PATH:"+PACKAGE_PATH);
        log.info("PACKAGE_FILE_PATH:"+PACKAGE_FILE_PATH);
        log.info("PACKAGE_RESOURCE_FILE_PATH:"+PACKAGE_RESOURCE_FILE_PATH);
        log.info("=================end=================");
    }

    public static Connection getConn(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return conn;
    }

    /**
     * Get all tables in the database
     * @return
     */
    public static List<String> getDatabase() {
        Connection conn = getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "show tables;";
        List<String> list = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
        return list;
    }

    /**
     * Get database table information
     * @return
     */
    public static Map<String, Object> getDatabaseInfo(String tableName){
        Connection conn = getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "show columns from "+tableName;
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String,String>> list = new ArrayList<>();
        String allfield = "";
        String sAllfield = "";
        String name = "";
        String sName = "";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            Map<String,String> map;
            char[] cs;
            while (rs.next()){
                map = new HashMap<>();
                name = rs.getString(1);
                map.put("name",name);
                allfield = allfield + name + ",";
                cs=name.toCharArray();
                cs[0]-=32;
                map.put("uName",String.valueOf(cs));
                sName = "#{"+name+"}";
                sAllfield = sAllfield + sName + ",";
                map.put("sName",sName);
                String type = rs.getString(2);
                if(type.contains("varchar") | type.contains("char")){
                    map.put("type","String");
                }else if(type.contains("int")){
                    map.put("type","Integer");
                }else if(type.contains("float")){
                    map.put("type","Double");
                }else if(type.contains("datetime")){
                    map.put("type","Date");
                }else {
                    map.put("type","Object");
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
        dataMap.put("list",list);
        dataMap.put("allfield",allfield.substring(0,allfield.length()-1));
        dataMap.put("sAllfield",sAllfield.substring(0,allfield.length()-1));
        dataMap.put("tableName",tableName);
        dataMap.put("uTableName",toHump(tableName));
        dataMap.put("classPath", PACKAGE_PATH);
        dataMap.put("filePath", PACKAGE_FILE_PATH);
        return dataMap;
    }
    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getPackageFilePath() {
        return PACKAGE_FILE_PATH;
    }

    public static String getPackagePath() {
        return PACKAGE_PATH;
    }

    public static String getPackageResourceFilePath() {
        return PACKAGE_RESOURCE_FILE_PATH;
    }

    public static String toHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        String name = result.toString();
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
