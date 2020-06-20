package com.magic.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 11:09
 */
public class StaticResourcesConfig {
    public static Map<String,Object> map = new HashMap<String, Object>();
    public final static String LoginPath = "Main.fxml";
    private final static double LoginWidth = 300;
    private final static double LoginHeight = 275;

    public final static String IndexPath = "Index.fxml";
    private final static double IndexWidth = 800;
    private final static double IndexHeight = 600;

    public static Map<String,Object> getStyle(String page){
       if("Main.fxml".equals(page)){
           map.put("width",LoginWidth);
           map.put("height",LoginHeight);
       }else {
           map.put("width",IndexWidth);
           map.put("height",IndexHeight);
       }
       return map;
    }
}
