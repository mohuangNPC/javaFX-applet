package com.magic.generated.util;

import com.magic.generated.datasource.DataSource;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 16:40
 */
public class Template {
    private static final String TEMPLATE_PATH = "src/main/java/com/magic/generated/templates";
    private static final String CLASS_PATH = DataSource.getProperties().getProperty("exportDir");
    private static Logger log = Logger.getLogger(Template.class.getClass());
    private static final int  BUFFER_SIZE = 2 * 1024;
    public static boolean generatedAll(String tableName){
        try {
            Map<String, Object> databaseInfo = DataSource.getDatabaseInfo(tableName);
            Template.generated(databaseInfo,"Start");
            Template.generated(databaseInfo,"Controller");
            Template.generated(databaseInfo,"ServiceImpl");
            Template.generated(databaseInfo,"Service");
            Template.generated(databaseInfo,"Dao");
            Template.generated(databaseInfo,"Entity");
            Template.generatedResource(databaseInfo,"Mapper");
            Template.generatedResource(databaseInfo,"application");
            Template.generatedPom(databaseInfo,"pom");
            FileOutputStream fos1 = new FileOutputStream(new File(CLASS_PATH+"\\"+databaseInfo.get("uTableName")+".zip"));
            toZip(CLASS_PATH+"\\"+databaseInfo.get("uTableName"), fos1,true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("zip complete,task time ：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }
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
            if("Entity".equals(type)){
                type = "";
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
                out.close();
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
            if ("application".equals(type)) {
                dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName") + "\\"+ DataSource.getPackageResourceFilePath();
            }
            System.err.println("========================="+DataSource.getPackageResourceFilePath());
            System.err.println("========================="+dirPath);
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File docFile = null;
            if ("application".equals(type)) {
                docFile = new File(dirPath + "\\" + "application.yml");
            }else {
                docFile = new File(dirPath + "\\" + dataMap.get("uTableName") + "Mapper.xml");
            }
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
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void generatedPom(Map<String,Object> dataMap,String type){
        Writer out = null;
        try {
            Configuration configuration = getTemplate();
            freemarker.template.Template template = configuration.getTemplate(type+".ftl");
            String dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName");
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File docFile  = new File(dirPath + "\\" + "pom.xml");;
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
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void generatedStart(Map<String,Object> dataMap,String type){
        Writer out = null;
        try {
            Configuration configuration = getTemplate();
            freemarker.template.Template template = configuration.getTemplate(type+".ftl");
            String dirPath =  CLASS_PATH + "\\" + dataMap.get("uTableName") + "\\"+ DataSource.getPackageFilePath();
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File docFile = new File(dirPath + "\\" + type + ".java");
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
                out.close();
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
