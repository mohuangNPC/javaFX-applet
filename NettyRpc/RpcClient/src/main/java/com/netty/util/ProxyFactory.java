package com.netty.util;

import java.lang.reflect.Proxy;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/30 14:05
 */
public class ProxyFactory {
    public static <T> T create(Class<T> interfaceClass) throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[] {interfaceClass}, new RpcClientDynamicProxy<T>(interfaceClass));
    }
}
