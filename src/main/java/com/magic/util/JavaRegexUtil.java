package com.magic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/26 10:34
 */
public class JavaRegexUtil {
    /**
     * Extract numbers in a string
     * @param str string
     * @return Numeric string
     */
    public static String extractNumbers(String str){
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        String all = matcher.replaceAll("");
        return all;
    }
}
