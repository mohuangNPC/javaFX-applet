package com.netty.util;

import cn.hutool.core.lang.UUID;
import com.netty.Start;
import com.netty.protocol.RpcRequest;
import com.netty.protocol.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/30 13:51
 */
public class RpcClientDynamicProxy<T> implements InvocationHandler {
    private Class<T> clazz;

    public RpcClientDynamicProxy(Class<T> clazz) throws Exception {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        String requestId = UUID.randomUUID().toString();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        Class<?>[] parameterTypes = method.getParameterTypes();

        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(args);
        System.err.println("请求内容: {}"+request);

        //开启Netty 客户端，直连
        Start nettyClient = new Start("127.0.0.1", 8888);
        System.err.println("开始连接服务端：{}"+new Date());
        nettyClient.connect();
        System.err.println("开始调用");
        RpcResponse send = nettyClient.send(request);
        System.err.println("请求调用返回结果：{}"+send.getResult().toString());
        return send.getResult().toString();
    }
}
