package com.magic.util;

import com.magic.Main;
import org.apache.log4j.Logger;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 11:03
 */
public class BashAction {
    public Logger logger(Class classname){
        return Logger.getLogger(classname.getClass());
    }
}
