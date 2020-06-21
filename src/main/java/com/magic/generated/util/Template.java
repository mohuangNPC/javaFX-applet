package com.magic.generated.util;

import com.magic.generated.datasource.DataSource;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 16:40
 */
public class Template {
    private static final String TEMPLATE_PATH = "src/main/java/com/magic/generated/templates";
    private static final String CLASS_PATH = DataSource.getProperties().getProperty("exportDir");
    private static Logger log = Logger.getLogger(Template.class.getClass());
    public static void generated(Map<String,Object> dataMap,String type){
        Writer out = null;
        try {
            Configuration configuration = getTemplate();
            freemarker.template.Template template = configuration.getTemplate(type+".ftl");
            String dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName") + "\\"+ DataSource.getPackageFilePath()+ "\\" + type;
            if(type.equals("ServiceImpl")){
                dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName") + "\\"+ DataSource.getPackageFilePath()+ "\\" + "Service\\impl";
            }
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File docFile = new File(dirPath + "\\" + dataMap.get("uTableName") + type + ".java");
            if(!docFile.exists()){
                docFile.createNewFile();
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            template.process(dataMap, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void generatedResource(Map<String,Object> dataMap,String type){
        Writer out = null;
        try {
            Configuration configuration = getTemplate();
            freemarker.template.Template template = configuration.getTemplate(type+".ftl");
            String dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName") + "\\"+ DataSource.getPackageResourceFilePath()+ "\\" + type;
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File docFile = new File(dirPath + "\\" + dataMap.get("uTableName") + type + "Mapper.xml");
            if(!docFile.exists()){
                docFile.createNewFile();
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            template.process(dataMap, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static Configuration getTemplate(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        try {
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
