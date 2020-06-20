package com.magic.generated.util;

import com.magic.generated.datasource.DataSource;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

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
    public static void generatedEntity(List<Map<String,String>> list,String name){
        Writer out = null;
        try {
            Configuration configuration = getTemplate();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("classPath", "com.freemark.hello");
            dataMap.put("className", name);
            dataMap.put("list", list);
            freemarker.template.Template template = configuration.getTemplate("Entity.ftl");
            File docFile = new File(CLASS_PATH + "\\" + name+".java");
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
